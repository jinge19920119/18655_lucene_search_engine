package util;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jdbc.JdbcBuild;
import model.Article;

public class DomParser {
	ArrayList<Article> list = new ArrayList<Article> ();
	JdbcBuild jb=new JdbcBuild();
	public DomParser(){
		jb.buildConnect();
		jb.createDB();
		
	}
	public void initial(){
		readXML();
	}

	public void readXML(){
		try{
			DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
			DocumentBuilder builder= factory.newDocumentBuilder();
			Document document= builder.parse(ClassLoader.getSystemResourceAsStream("article.xml"));
			document.getDocumentElement().normalize();
			
//			System.out.println("Root element: "+ document.getDocumentElement().getNodeName());
			NodeList nodeList= document.getDocumentElement().getChildNodes();
			for(int i=0; i<2000; i++){
				Node node= nodeList.item(i);
				if(node instanceof Element){
					Article artic= new Article();
					artic.mdate= node.getAttributes().getNamedItem("mdate").getNodeValue();
					artic.key= node.getAttributes().getNamedItem("key").getNodeValue();
					NodeList childNodes= node.getChildNodes();
					for(int j=0;j<childNodes.getLength();j++){
						Node cNode= childNodes.item(j);
						if(cNode instanceof Element){
							String content= cNode.getLastChild().getTextContent().trim();
							switch (cNode.getNodeName()) {
							case "title":
								artic.title= content.replace("'", " ");
								break;
							case "pages":
								artic.pages= content;
								break;
							case "year":
								artic.year= content;
								break;
							case "volume":
								artic.volume= content;
								break;
							case "journal":
								artic.journal= content;
								break;
							case "number":
								artic.number= content;
								break;
							case "url":
								artic.url= content;
								break;
							case "ee":
								artic.ee= content;
								break;
							case "author":
								artic.authors.add(content);
								break;
							
							}
							
						}
					}
//					System.out.println(artic);
					list.add(artic);
					jb.addArticle(artic);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public ArrayList<Article> getArticle(){
		return list;
	}
	
	public ArrayList<String> getArticleFromDB(int left, int right){
		return jb.searchArticle(left, right);
	}
}
