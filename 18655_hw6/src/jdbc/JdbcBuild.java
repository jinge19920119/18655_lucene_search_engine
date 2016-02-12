package jdbc;

import java.sql.*;
import java.util.*;

import model.Article;

import java.io.*;


public class JdbcBuild {
	Connection connection= null;
	Statement statement= null;
	ResultSet resultSet=null;
	/*
	 * driver name for this database, location of the database, username and password for it
	 */
	static final String jdbcDriver = "com.mysql.jdbc.Driver";
	static final String dbUrl= "jdbc:mysql://localhost";
	static final String user= "root";
	static final String passwd= "";
	
    public JdbcBuild() {
		
	}
    /*
	 * build methods, initialization of the database
	 */
    public void buildConnect() {
		try {
			Class.forName(jdbcDriver);
			connection= DriverManager.getConnection(dbUrl, user, passwd);
			statement= connection.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
    /*
	 * create method, that read from a txt file and read the MySQL language and write to the database.
	 */
    public void createDB() {
		try {
			BufferedReader br= new BufferedReader(new FileReader("db_schema.txt"));
			String line= null;
			StringBuilder sb= new StringBuilder();
			while ((line=br.readLine())!=null){
				sb.append(line);
				if(sb.length()>0 && sb.charAt(sb.length()-1)==';'){//see if it is the end of one line of command
					statement.executeUpdate(sb.toString());
					sb= new StringBuilder();
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /*
	 * addArticle method. parse the Article object and add them to tables.
	 */
    public void addArticle(Article artic){
    	String title= artic.title;
    	int year= Integer.parseInt(artic.year);
    	String sql= null;
    	try{
    		sql= "insert into article (`title`, g) values('"+title+"', GeomFromText('POINT("+ year+" 0)'));";
//    		sql = "insert into article(g) values(GeomFromText('POINT(10 0)'));";
//    		System.out.println(sql);
    		statement.executeUpdate(sql);
    		
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public ArrayList<String> searchArticle(int left, int right){
    	ArrayList<String> titleList= new ArrayList<>();
    	
    	String sql= null;
    	try{
    		sql= "select * from article where MBRContains (GeomFromText('Polygon(("+left+" 0,"+left+" 0,"+right+" 0,"+right+" 0,"+left+" 0))'),g );";
    		ResultSet rs= statement.executeQuery(sql);
    		while(rs.next()){
    			String getStr = rs.getString("title");
    			titleList.add(getStr);
    		}
    	} catch (SQLException e){
    		e.printStackTrace();
    	}
    	
    	return titleList;
    }
    
    
	
}
