package model;

import java.util.ArrayList;

public class Article {

	public String mdate;
	public String key;
	public String title;
	public String pages;
	public String year;
	public String volume;
	public String journal;
	public String number;
	public String url;
	public String ee;
	public ArrayList<String> authors = new ArrayList<String>();

	@Override
	public String toString() {
		String out= mdate+"\t "+key+"\t "+title+"\t "+pages+"\t "+year+"\t "
				+volume+"\t "+journal+"\t "+number+"\t "+url+"\t "+ee+"\t "+authors;
		return out;
	}
}
