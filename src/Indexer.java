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

public class Indexer {

    public Indexer() {
    }

    public static void main(String[] args) throws IOException {
	Indexer indexer = new Indexer();
	System.out.println("Running Main");

	System.out.println("Building Index");
	Directory indexDir = FSDirectory.open(Paths.get("index"));
	IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
	IndexWriter writer = new IndexWriter(indexDir, config);
	writer.deleteAll();
	writer.commit();

	String[] texts = {
	    "Agrarian practices in the west",
	    "western climates",
	    "environmental changes over time",
	    "settlement and colonial history",
	    "urban identity and landscapes",
	    "self isolation in hermits",
	    "practices and beliefs of the poor",
	    "sailors and their lives",
	    "small villages in alsace",
	    "urban organization and communities",
	};

	for (int i = 0; i < texts.length; i++) {
	    Document doc = new Document();
	    doc.add(new TextField("title", texts[i], Field.Store.YES));
	    writer.addDocument(doc);
	}

	writer.close();
    }

}
