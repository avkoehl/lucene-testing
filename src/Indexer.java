package com.arthurkoehl.lucene;

import java.io.Reader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

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

    public List<String[]> readCSV(String fname) throws Exception {
	Reader reader = Files.newBufferedReader(Paths.get(fname));
	CSVReader csvReader = new CSVReader(reader);
	List<String[]> list = new ArrayList<>();
	list = csvReader.readAll();
	reader.close();
	csvReader.close();
	return list;
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

	List<String[]> data = new LinkedList<String[]>();
	List<String> fields = new LinkedList<String>();
	try {
	    data = indexer.readCSV("data/deniro.csv");
	    for (int i = 0; i < data.get(0).length; i++)
	    {
		fields.add(data.get(0)[i]);
	    }
	    for (int i = 1; i < data.size(); i++)
	    {
	        Document doc = new Document();
		for (int j = 0; j < data.get(i).length; j++) {
		    doc.add(new TextField(fields.get(j), data.get(i)[j], 
				Field.Store.YES));
		}
		writer.addDocument(doc);
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	}

	writer.close();
    }

}
