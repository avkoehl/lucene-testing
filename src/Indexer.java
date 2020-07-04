// adapted from http://web.cs.ucla.edu/classes/winter15/cs144/projects/lucene/index.html
package com.arthurkoehl.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {

    public void indexReference(String line, IndexWriter writer) throws IOException {

	System.out.println("Indexing: " + line);
	Document doc = new Document();
	doc.add(new StringField("title", line, Field.Store.YES));
	writer.addDocument(doc);
    }

    public void buildIndexes() throws IOException {
	System.out.println("Building Index");
	Directory indexDir = FSDirectory.open(Paths.get("index"));
	IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
	IndexWriter writer = new IndexWriter(indexDir, config);

	String line = "Agrarian practices in the American West";
	try {
	    this.indexReference(line, writer);
	}
	catch(IOException e) {
	    e.printStackTrace();
	}

	writer.close();
    }

    public static void main(String[] args) {
	Indexer indexer = new Indexer();
	System.out.println("Running Main");
	try {
	    indexer.buildIndexes();
	}
	catch(IOException e) {
	    e.printStackTrace();
	}
    }

}
