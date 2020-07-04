package com.arthurkoehl.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;

public class Search {

    public Search() {
    }


    public static void main(String[] args) throws IOException {

	Search search = new Search();

	System.out.println("Running Search");
	Analyzer analyzer = new StandardAnalyzer();

	Directory directory = FSDirectory.open(Paths.get("index"));
	DirectoryReader ireader = DirectoryReader.open(directory);
	IndexSearcher isearcher = new IndexSearcher(ireader);
	System.out.println(isearcher.collectionStatistics("Title"));
	System.out.println(isearcher.collectionStatistics("Year"));
	System.out.println(isearcher.collectionStatistics("Score"));

	QueryParser parser = new QueryParser("Title", analyzer);
	try {
	    Query query = parser.parse("dog");
	    System.out.println(isearcher.count(query));
	    ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;

	    // Iterate through the results:
	    for (int i = 0; i < hits.length; i++) {
		Document hitDoc = isearcher.doc(hits[i].doc);
		System.out.println(hitDoc);
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	}

	ireader.close();
	directory.close();
    }
}
