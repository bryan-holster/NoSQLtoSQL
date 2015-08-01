package umw.dahlgren.twitter.snapshot;

import java.util.*;
import java.sql.*;

public class MySQLObject {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://localhost/";

        static String USER;
        static String PASS;
	
	private String DBName = null;
	private Connection conn = null;

	public MySQLObject(String u, String p) {
		USER = u;
		PASS = p;
	}

	public void setDBName(String dbname) {
		this.DBName = dbname;
	}

	public String retrieveDBName() {
		return DBName;
	}

	public Connection connect() {
		try{
                        Class.forName("com.mysql.jdbc.Driver");

                        System.out.println("Connecting to a selected database...");
                        conn = DriverManager.getConnection(DB_URL+DBName, USER, PASS);
                        System.out.println("Connected database successfully...");
		}catch(SQLException se){
                        se.printStackTrace();
                }catch(Exception e){
                        e.printStackTrace();
                }
		return conn;
	}

	public void disconnect() {
		try{
			System.out.println("MySQL connection closing...");
			conn.close();
			System.out.println("MySQL connection closed...");
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}        
	}

	public void createDB(String db) {
                Connection ccon = null;
                Statement stmt = null;

                try{
                        Class.forName("com.mysql.jdbc.Driver");

                        System.out.println("Connecting to database...");
                        ccon = DriverManager.getConnection(DB_URL, USER, PASS);

                        System.out.println("Creating database...");
                        stmt = ccon.createStatement();

                        String sql = "CREATE DATABASE " + db;
                        stmt.executeUpdate(sql);
                        System.out.println("Database created successfully...");
			ccon.close();

                }catch(SQLException se){
                        se.printStackTrace();
                }catch(Exception e){
                        e.printStackTrace();
                }
        }

	public ArrayList<String> retrieveAllDBs() {
		Connection retConn = null;
		ArrayList<String> dbNames = new ArrayList<String>();
			
		try{
			Class.forName("com.mysql.jdbc.Driver");
			retConn = DriverManager.getConnection(DB_URL, USER, PASS);

			DatabaseMetaData meta = retConn.getMetaData();
      			ResultSet res = meta.getCatalogs(); 
      			while (res.next()) {
         			dbNames.add(res.getString("TABLE_CAT"));
      			}
      			res.close();

      			retConn.close();
		
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dbNames;
	}
	public void createTables(ArrayList<String> tw, ArrayList<String> ur, ArrayList<String> pl) {
		this.conn = connect();
   		Statement stmt = null;
		Hashtable<String,String> tweet = new Hashtable<String,String>();
		tweet.put("dt",",  date_created datetime DEFAULT NULL");
		tweet.put("t",",  text VARCHAR(165) DEFAULT NULL");
		tweet.put("l",",  lang VARCHAR(10) DEFAULT NULL");
		tweet.put("gl",",  lati float DEFAULT NULL,  longi float DEFAULT NULL");
		tweet.put("s",",  source VARCHAR(255) DEFAULT NULL");
		tweet.put("rtu",",  inreplytouserid VARCHAR(18) DEFAULT NULL");
		tweet.put("rts",",  inreplytostatusid VARCHAR(18) DEFAULT NULL");
		Hashtable<String,String> user = new Hashtable<String,String>();
		user.put("sn",",  screenname VARCHAR(30) DEFAULT NULL");
		user.put("dt",",  date_created datetime DEFAULT NULL");
		user.put("utc",",  utcoffset int(11) DEFAULT NULL");
		user.put("ds",",  description VARCHAR(160) DEFAULT NULL");
		user.put("lc",",  location VARCHAR(45) DEFAULT NULL");
		user.put("sc",",  statusescount int(11) DEFAULT NULL");
		user.put("fv",",  favouritescount int(11) DEFAULT NULL");
		user.put("fw",",  followerscount int(11) DEFAULT NULL");
		user.put("fr",",  friendscount int(11) DEFAULT NULL");
		user.put("v",",  verified TINYINT(1) DEFAULT NULL");
		Hashtable<String,String> place = new Hashtable<String,String>();
		place.put("br",",  nw_lati float DEFAULT NULL,  nw_longi float DEFAULT NULL,  se_lati float DEFAULT NULL,  se_longi float DEFAULT NULL");
		place.put("fn",",  fullname VARCHAR(60) DEFAULT NULL");
		place.put("c",",  country VARCHAR(40) DEFAULT NULL");
		place.put("cc",",  countrycode VARCHAR(3) DEFAULT NULL");
		place.put("pt",",  placetype VARCHAR(20) DEFAULT NULL");
		place.put("sa",",  streetaddress VARCHAR(45) DEFAULT NULL");


   		try{
      			System.out.println("Creating table in given database...");
      			stmt = conn.createStatement();
     			String dropTweets = "DROP TABLE IF EXISTS Tweets;"; 
      			String tweetTable = "CREATE TABLE Tweets " + "(id char(18) NOT NULL, " + 
				" userid VARCHAR(18) NOT NULL, " + 
				" placeid VARCHAR(18) DEFAULT NULL";
 			for(int i = 0; i < tw.size(); i++){
				tweetTable += tweet.get(tw.get(i));
			}
			tweetTable += ")  ENGINE=MyISAM DEFAULT CHARSET=latin1;";
			System.out.println(tweetTable);
			stmt.executeUpdate(dropTweets);
			stmt.executeUpdate(tweetTable);
                        System.out.println("Created tweetTable in given database..." + DBName);

			String dropUsers = "DROP TABLE IF EXISTS Users;";
			String userTable = "CREATE TABLE Users " + "(userid VARCHAR(18) NOT NULL";
			for(int i = 0; i < ur.size(); i++){
				userTable += user.get(ur.get(i));
			}
			userTable += ")  ENGINE=MyISAM DEFAULT CHARSET=latin1;";
			System.out.println(userTable);
			stmt.executeUpdate(dropUsers);
                        stmt.executeUpdate(userTable);
                        System.out.println("Created userTable in given database..." + DBName);

			String dropPlaces = "DROP TABLE IF EXISTS Places;";
			String placeTable = "CREATE TABLE Places " + "(id VARCHAR(18) NOT NULL DEFAULT ''";
			for(int i = 0; i < pl.size(); i++){
				placeTable += place.get(pl.get(i));
			}
			placeTable += ")  ENGINE=MyISAM DEFAULT CHARSET=latin1;";
			System.out.println(placeTable);
  			stmt.executeUpdate(dropPlaces);
                        stmt.executeUpdate(placeTable);
                        System.out.println("Created placeTable in given database..." + DBName);

   		}catch(SQLException se){
      			se.printStackTrace();
   		}catch(Exception e){
      			e.printStackTrace();
   		}		
	}
}

/*
	String dropHashtags = "DROP TABLE IF EXISTS Hashtags;"; 
    String hashtagTable = "CREATE TABLE Hashtags " + 
    					"(id char(18) NOT NULL, " +
    					" text VARCHAR(50) DEFAULT NULL) " +
    					" ENGINE=MyISAM DEFAULT CHARSET=latin1;";

    String dropMedia = "DROP TABLE IF EXISTS Media;"; 
    String mediaTable = "CREATE TABLE Media " + 
    					"(id char(18) NOT NULL, " +
    					" m_id VARCHAR(50) DEFAULT NULL, " +
    					" mediaURL VARCHAR(50) DEFAULT NULL, " +
    					" URL VARCHAR(50) DEFAULT NULL, " +
    					" displayURL VARCHAR(50) DEFAULT NULL, " +
    					" expandedURL VARCHAR(50) DEFAULT NULL, " +
    					" mediaType VARCHAR(50) DEFAULT NULL) " +
    					" ENGINE=MyISAM DEFAULT CHARSET=latin1;";

    String dropUrl = "DROP TABLE IF EXISTS Urls;"; 
    String urlTable = "CREATE TABLE Urls " + 
    					"(id char(18) NOT NULL, " +
    					" displayURL VARCHAR(50) DEFAULT NULL, " +
    					" expandedURL VARCHAR(50) DEFAULT NULL, " +
    					" URL VARCHAR(50) DEFAULT NULL) " +
    					" ENGINE=MyISAM DEFAULT CHARSET=latin1;";

    String dropSymbols = "DROP TABLE IF EXISTS Symbols;"; 
    String symbolTable = "CREATE TABLE Symbols " + 
    					"(id char(18) NOT NULL, " +
    					" text VARCHAR(50) DEFAULT NULL) " +
    					" ENGINE=MyISAM DEFAULT CHARSET=latin1;";

    String dropMentions = "DROP TABLE IF EXISTS Mentions;"; 
    String mentionsTable = "CREATE TABLE Mentions " + 
    					"(userid VARCHAR(18) NOT NULL, " +
    					" m_id VARCHAR(18) DEFAULT NULL, " +
    					" name VARCHAR(50) DEFAULT NULL, " +
    					" screenname VARCHAR(50) DEFAULT NULL) " +
    					" ENGINE=MyISAM DEFAULT CHARSET=latin1;";
*/