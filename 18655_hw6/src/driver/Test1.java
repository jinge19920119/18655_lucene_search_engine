package driver;

import java.io.*;
import java.util.*;

import org.apache.lucene.queryparser.classic.ParseException;

import lucene.Lucene;
import model.Article;
import util.DomParser;

public class Test1 {
	public static void main(String[] args){
		DomParser dp= new DomParser();
		dp.initial();
		List<Article> list= new ArrayList<Article>();
		list= dp.getArticle();
	    int size= list.size();
	    String[] titleArr= new String[size];
	    String[] authorAndTitleArr= new String[size];
	    for(int i=0;i<size;i++){
	    	titleArr[i]= list.get(i).title;
	    	authorAndTitleArr[i]= list.get(i).title;
	    	for(int j=0; j<list.get(i).authors.size();j++)
	    		authorAndTitleArr[i]+= " "+list.get(i).authors.get(j);
	    }
	    Lucene lu= new Lucene();
	    System.out.println("please type in what you want to look for: ");
	    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
	    String queryStr= "Time";
	    int numResultsToReturn= 10;
		try {
			queryStr = br.readLine();
			System.out.println("please type in how many you want to search: ");
			numResultsToReturn= Integer.parseInt(br.readLine());
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    String[] result= null;
	    try {
			lu.initialization();
			lu.add(authorAndTitleArr, titleArr);
			result= lu.basicSearch(queryStr, numResultsToReturn);
			for(int i=0;i<result.length;i++){
				System.out.println((i+1)+". "+result[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	}
}
