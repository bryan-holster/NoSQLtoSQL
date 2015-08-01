package umw.dahlgren.twitter.snapshot;

import java.io.*;
import java.util.*;
import java.text.*;
import com.mongodb.*;

public class Interpreter {

	private MySQLObject myNewDB = null;
	private MongoObject myMongo = null;
	private ArrayList<String> dbNames = null;
	private java.util.Date startDate;
  	private java.util.Date endDate;
	private String db;
	private String user;
	private String pass;
	private double toplong;
	private double toplat;
	private double bottomlong;
	private double bottomlat;
	private String key1;
	private int ermin;
	private int ermax;
	private int eemin;
	private int eemax;
	private boolean success = false;
	private int numxfer;
	private ArrayList<String> tw_fields = null;
	private ArrayList<String> ur_fields = null;
	private ArrayList<String> pl_fields = null;
	private long run_start_time = 0;
	private long run_end_time = 0;
	
  	public Interpreter(ArrayList<String> tw, ArrayList<String> ur, ArrayList<String> pl,String u, String p, String start, String end, String dbname, double tg, double tt, double bg, double bt, String k1, int rn, int rx, int en, int ex) {
		run_start_time = System.nanoTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
                	startDate = sdf.parse(start + " 00:00:01");
                	endDate = sdf.parse(end + " 23:59:59");
		}catch(ParseException pe){
                        pe.printStackTrace();
                }
		toplong = tg;
		toplat = tt;
		bottomlong = bg;
		bottomlat = bt;
		key1 = k1;
		ermin = rn;
		ermax = rx;
		eemin = en;
		eemax = ex;
		tw_fields = tw;
		ur_fields = ur;
		pl_fields = pl;
		db = dbname;
		user = u;
		pass = p;
		myNewDB = new MySQLObject(u, p);
		myMongo = new MongoObject();
		myNewDB.setDBName(dbname); 
  	}
	
	public Interpreter(String u, String p) {
		myNewDB = new MySQLObject(u, p);
		dbNames = myNewDB.retrieveAllDBs();
	}
	
	public Interpreter(String u, String p, String name) {
		myNewDB = new MySQLObject(u, p);
		myNewDB.createDB(name);
	}
	
	public boolean successful() {
		boolean temp = this.success;
		this.success = false;
		return temp;
	}
	
	public int numXfered() {
		return numxfer;
	}

	public ArrayList<String> allDBs() {
		return dbNames;
	}

	public void mysqlTest() {
		myNewDB.createTables(tw_fields, ur_fields, pl_fields);
	}

	public void transferData() {
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println("Beginning transfer of data...");
		Thread t1 = new Thread(){
			public void run(){
				try{
                    DBCursor cursor1 = myMongo.createQuery(startDate, endDate, toplong, toplat, bottomlong,
						bottomlat, key1, ermin, ermax, eemin, eemax);
					TableShuttle tweetDB = new TableShuttle(user, pass, tw_fields, 1);
					tweetDB.setDBName(db);
					tweetDB.connect();
					tweetDB.insertTweet(cursor1, tw_fields);
					tweetDB.disconnect();
						
                                }catch(Exception pe){
					pe.printStackTrace();
				}
			}
		};t1.start();
	
		Thread t2 = new Thread(){
			public void run(){
				try{

                                	DBCursor cursor2 = myMongo.createQuery(startDate, endDate, toplong, toplat, bottomlong,
							 bottomlat, key1, ermin, ermax, eemin, eemax);
                                	TableShuttle userDB = new TableShuttle(user, pass, ur_fields, 2);
                                	userDB.setDBName(db);
                                	userDB.connect();
                                	userDB.insertUser(cursor2, ur_fields);
					userDB.disconnect();
				
                                }catch(Exception pe){
                                        pe.printStackTrace();
                                }
			}
		};t2.start();

		Thread t3 = new Thread(){
                	public void run(){
                        	try{
                                	DBCursor cursor3 = myMongo.createQuery(startDate, endDate, toplong, toplat, bottomlong,
							 bottomlat, key1, ermin, ermax, eemin, eemax);
                                	TableShuttle placeDB = new TableShuttle(user, pass, pl_fields, 3);
                                	placeDB.setDBName(db);
                                	placeDB.connect();
                                	placeDB.insertPlace(cursor3, pl_fields);
                                	placeDB.disconnect();
                        	
				}catch(Exception pe){
                                        pe.printStackTrace();
                                }
                	}
        	};t3.start();
		
		try{
			t1.join();
			t2.join();
			t3.join();	
		}catch(Exception ie){
			ie.printStackTrace();
		}

		myNewDB.disconnect();
        	myMongo.close();
        	this.success = true;
        	run_end_time = System.nanoTime();
        	System.out.println("Total time spent running: "+(double)((run_end_time - run_start_time)/1000000000.0)+" seconds");
	}
}


