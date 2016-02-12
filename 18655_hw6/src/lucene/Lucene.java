package lucene;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class Lucene {
	StandardAnalyzer analyzer= null;
	Directory index= null;
	IndexWriterConfig config= null;
	IndexWriter w= null;
	public Lucene(){
		analyzer=new StandardAnalyzer();
		
	}
	public void initialization() throws IOException{
		index= new RAMDirectory();
		config= new IndexWriterConfig(analyzer);
		w= new IndexWriter(index,config); 
	}
	
	public void add(String[] arr1, String[] arr2) throws IOException{
		for(int i=0;i<arr1.length; i++){
			addDoc(w, arr1[i], arr2[i]);
		}
		w.close();
	}
	public String[] basicSearch(String queryStr, int num) throws ParseException, IOException{
		Query q= new QueryParser("title",analyzer).parse(queryStr);
		int hitsPerPage= num;
		IndexReader reader= DirectoryReader.open(index);
		IndexSearcher searcher= new IndexSearcher(reader); 
	    
		TopScoreDocCollector collector= TopScoreDocCollector.create(hitsPerPage);
		searcher.search(q,collector); 
		ScoreDoc[] hits= collector.topDocs().scoreDocs;
		String[] result= new String[hits.length];
		for(int i=0; i<hits.length; i++){
			int docId= hits[i].doc;
			Document d= searcher.doc(docId);
			result[i]= d.get("isbn");
		}
		reader.close();
		return result;
	}
	private static void addDoc(IndexWriter w, String title, String isbn) throws IOException{
		Document doc=new Document(); 
		doc.add(new TextField("title",title,Field.Store.YES));
		
		//use a string field for isbn because we don't want it tokenized 
		doc.add(new StringField("isbn",isbn,Field.Store.YES));
		w.addDocument(doc);
		}

}
