package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import lucene.Lucene;
import model.Article;
import util.DomParser;

public class Test2 {
	public static void main(String[] args){
		DomParser dp= new DomParser();
		dp.initial();
		List<String> list= new ArrayList<String>();
		System.out.println("plese type in from which year you want to search:");
		int startYear=0;
		int endYear= 0;
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		try{
			startYear= Integer.parseInt(br.readLine());
			System.out.println("plese type in from which year you want to search:");
			endYear= Integer.parseInt(br.readLine());
		}catch (IOException e){
			e.printStackTrace();
		}
		
		list= dp.getArticleFromDB(startYear, endYear);
		String[] titleArr= new String[list.size()];
//		System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			titleArr[i]= list.get(i);
//			System.out.println("title:"+titleArr[i]);
		}
		Lucene lu= new Lucene();
		System.out.println("please type in what you want to look for: ");
	    
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
			lu.add(titleArr, titleArr);
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
