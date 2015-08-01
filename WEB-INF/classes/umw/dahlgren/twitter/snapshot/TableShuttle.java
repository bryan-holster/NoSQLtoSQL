package umw.dahlgren.twitter.snapshot;

import java.util.*;
import java.sql.*;
import com.mongodb.*;

public class TableShuttle {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://localhost/";

        static String USER;
        static String PASS;
	
	private String DBName = null;
	private Connection conn = null;

	private String tw_prep_stmt;
	private String ur_prep_stmt;
	private String pl_prep_stmt;

	public TableShuttle(String u, String p, ArrayList<String> alp, int f) {
		USER = u;
		PASS = p;
		if(f == 1){
			Hashtable<String,String> tweet = new Hashtable<String,String>();
            	tweet.put("dt",",date_created");
            	tweet.put("t",",text");
            	tweet.put("l",",lang");
            	tweet.put("s",",source");
            	tweet.put("rtu",",inreplytouserid");
            	tweet.put("rts",",inreplytostatusid");

			tw_prep_stmt = "INSERT INTO Tweets(id,userid,placeid";
			for(int i = 0; i < alp.size(); i++){
				if(alp.get(i).equals("gl")){
					tw_prep_stmt += ",lati,longi";
				}else{
					tw_prep_stmt += tweet.get(alp.get(i));
				}
			}
			tw_prep_stmt += ")VALUES(?,?,?";
			for(int i = 0; i < alp.size(); i++){
				tw_prep_stmt += ",?";
			}
			if(alp.contains("gl")){tw_prep_stmt += ",?)";}
			else{tw_prep_stmt += ")";}
			System.out.println(tw_prep_stmt);
		}
		if(f == 2){
			Hashtable<String,String> user = new Hashtable<String,String>();
                user.put("sn",",screenname");
                user.put("dt",",date_created");
                user.put("utc",",utcoffset");
                user.put("ds",",description");
                user.put("lc",",location");
                user.put("sc",",statusescount");
                user.put("fv",",favouritescount");
                user.put("fw",",followerscount");
                user.put("fr",",friendscount");
                user.put("v",",verified");
		
			ur_prep_stmt = "INSERT INTO Users(userid";
			for(int i = 0; i < alp.size(); i++){
                ur_prep_stmt += user.get(alp.get(i));
            }
            ur_prep_stmt += ")VALUES(?";
            for(int i = 0; i < alp.size(); i++){
                ur_prep_stmt += ",?";
            }
            ur_prep_stmt += ")";
			System.out.println(ur_prep_stmt);
		}
		if(f == 3){
			Hashtable<String,String> place = new Hashtable<String,String>();
                place.put("fn",",fullname");
                place.put("c",",country");
                place.put("cc",",countrycode");
                place.put("pt",",placetype");
                place.put("sa",",streetaddress");	
		
			pl_prep_stmt = "INSERT INTO Places(id";
            for(int i = 0; i < alp.size(); i++){
            	if(alp.get(i).equals("br")){
					pl_prep_stmt += ",nw_lati,nw_longi,se_lati,se_longi";
				}else{
                	pl_prep_stmt += place.get(alp.get(i));
                }
            }
            pl_prep_stmt += ")VALUES(?";
            for(int i = 0; i < alp.size(); i++){
            	if(alp.get(i).equals("br")){
                	pl_prep_stmt += ",?,?,?,?";
            	}else{
	                pl_prep_stmt += ",?";
	            }
            }
            pl_prep_stmt += ")";
			System.out.println(pl_prep_stmt);
		}
	}

	public void setDBName(String dbname) {
		this.DBName = dbname;
	}

	public void connect() {
		try{
                        Class.forName("com.mysql.jdbc.Driver");

                       // System.out.println("Connecting to a selected database...");
                        this.conn = DriverManager.getConnection(DB_URL+DBName+"?rewriteBatchedStatements=true", USER, PASS);
                       // System.out.println("Connected database successfully...");
		}catch(SQLException se){
                        se.printStackTrace();
                }catch(Exception e){
                        e.printStackTrace();
                }
	}

	public void disconnect() {
		try{
			//System.out.println("MySQL connection closing...");
			conn.close();
			//System.out.println("MySQL connection closed...");
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}        
	}
	
	public void insertTweet(DBCursor cursor, ArrayList<String> tw) {
		double lt = 0;
        double lg = 0;
		int c = 1;
		String p = " ";
		int ct = 0;

		try{
			PreparedStatement twtStmt = conn.prepareStatement(tw_prep_stmt);
			while(cursor.hasNext()) {
                DBObject doc = (DBObject)cursor.next();
	//			long start = System.nanoTime();
				DBObject usr = (DBObject)doc.get("user");

                long id = (long)doc.get("id");
				twtStmt.setLong(c++,id);

				long uid = (long)usr.get("id");
                twtStmt.setLong(c++,uid);

				if(doc.get("place") != null){
                    DBObject plc = (DBObject)doc.get("place");
                    p = (String)plc.get("id");
                }
                twtStmt.setString(c++,p);

                if(tw.contains("dt")){
                    java.util.Date dt = (java.util.Date)doc.get("createdAt");
					twtStmt.setObject(c++,dt);
				}

				if(tw.contains("t")){
                    String t = (String)doc.get("text");
					twtStmt.setString(c++,t);
				}

				if(tw.contains("l")){
                    String l = (String)usr.get("lang");
                    twtStmt.setString(c++,l);
                }

                if(tw.contains("rtu")){
                    long rtu = (long)doc.get("inReplyToUserId");
                    twtStmt.setLong(c++,rtu);
                }

                if(tw.contains("rts")){
                    long rts = (long)doc.get("inReplyToStatusId");
                    twtStmt.setLong(c++,rts);
                }

                if(tw.contains("gl")){
                    if(doc.get("geoLocation") != null){
                        DBObject geo = (DBObject)doc.get("geoLocation");
                        lt = (double)geo.get("latitude");
                        twtStmt.setDouble(c++,lt);
                        lg = (double)geo.get("longitude");
                    	twtStmt.setDouble(c++,lg); 
                	}else{
                		twtStmt.setNull(c++,Types.DOUBLE);
                		twtStmt.setNull(c++,Types.DOUBLE);
                	}
                }

                if(tw.contains("s")){
						String s = (String)doc.get("source");
						twtStmt.setString(c++,s);
				}

				twtStmt.addBatch();
				ct++;
				if(ct >= 8000){
					twtStmt.executeBatch();
					ct = 0;
				}

	//			long end = System.nanoTime();
          //                    total += end - start;
				c = 1;
			}
			twtStmt.executeBatch();
			System.out.println("tweets done");
	//		System.out.println("Tweets table inserts complete in "+(double)((total)/1000000000.0)+" seconds");
   		}catch(SQLException se){
   			System.out.println("SQL fail in tweet insert");
      			se.printStackTrace();
			disconnect();
   		}catch(Exception e){
   			System.out.println("fail in tweet insert");
      			e.printStackTrace();
			disconnect();
   		}
	}

	public void insertUser(DBCursor cursor, ArrayList<String> ur) {
		int b = 1;
		int ct = 0;

		try{			
			PreparedStatement usrStmt = conn.prepareStatement(ur_prep_stmt);
                        while(cursor.hasNext()){
				DBObject doc = (DBObject)cursor.next();
		//		long start = System.nanoTime();
				DBObject usr = (DBObject)doc.get("user");
				long uid = (long)usr.get("id");
                usrStmt.setLong(b++,uid);
				
				if(ur.contains("sn")){
                    String sn = (String)usr.get("screenName");
					usrStmt.setString(b++,sn);
				}

				if(ur.contains("dt")){
                    java.util.Date dt = (java.util.Date)usr.get("createdAt");
					usrStmt.setObject(b++,dt);
				}

				if(ur.contains("utc")){
                    int utc = (int)usr.get("utcOffset");
					usrStmt.setInt(b++,utc);
				}

				if(ur.contains("ds")){
                    String tz  = (String)usr.get("description");
					usrStmt.setString(b++,tz);
				}

				if(ur.contains("lc")){
					String lc = (String)usr.get("location");
					usrStmt.setString(b++, lc);
				}

				if(ur.contains("sc")){
					int sc = (int)usr.get("statusesCount");
					usrStmt.setInt(b++, sc);
				}

				if(ur.contains("fv")){
					int fv =(int)usr.get("favouritesCount");
					usrStmt.setInt(b++, fv);
				}

				if(ur.contains("fw")){
                    int fwc = (int)usr.get("followersCount");
					usrStmt.setInt(b++,fwc);
				}

				if(ur.contains("fr")){
                    int fdc = (int)usr.get("friendsCount");
					usrStmt.setInt(b++,fdc);
				}

				if(ur.contains("v")){
					boolean v = (boolean)usr.get("verified");
					usrStmt.setBoolean(b++, v);
				}

			
				usrStmt.addBatch();
				ct++;
				if(ct >= 8000){
					usrStmt.executeBatch();
					ct = 0;
				}
		//		long end = System.nanoTime();
                //              total += end - start;
                b = 1;
			}
			usrStmt.executeBatch();
			System.out.println("users done");
		//	System.out.println("Users table inserts complete in "+(double)((total)/1000000000.0)+" seconds");
                }catch(SQLException se){
                	System.out.println("SQL fail in usr insert");
                        se.printStackTrace();
			disconnect();
                }catch(Exception e){
                	System.out.println("fail in usr insert");
                        e.printStackTrace();
			disconnect();
                }
	}	

	public void insertPlace(DBCursor cursor, ArrayList<String> pl) {
		String fn = "";
        String c = "";
        String cc = "";
        String id = "";
		String pt = "";
		String sa = "";
		double nw_lati = 0;
		double nw_longi = 0;
		double se_lati = 0;
		double se_longi = 0;
		int a = 1;
		int ct = 0;

		try{
			PreparedStatement plcStmt = conn.prepareStatement(pl_prep_stmt);
            while(cursor.hasNext()){
                DBObject doc = (DBObject)cursor.next();
		//		long start = System.nanoTime();
				if(doc.get("place") != null){
                    DBObject plc = (DBObject)doc.get("place");
					id = (String)plc.get("id");
					plcStmt.setString(a++,id);
					
					if(pl.contains("br")){
						if(plc.get("boundingBoxCoordinates") != null){
							BasicDBList br = (BasicDBList)plc.get("boundingBoxCoordinates");
							BasicDBList bri = (BasicDBList)br.get(0);
							BasicDBObject nw = (BasicDBObject)bri.get(1);
							nw_lati = nw.getDouble("latitude");
							nw_longi = nw.getDouble("longitude");
							plcStmt.setDouble(a++,nw_lati);
							plcStmt.setDouble(a++,nw_longi);
							BasicDBObject se = (BasicDBObject)bri.get(3);
							se_lati = se.getDouble("latitude");
							se_longi = se.getDouble("longitude");
							plcStmt.setDouble(a++,se_lati);
							plcStmt.setDouble(a++,se_longi);
						}else{
							plcStmt.setNull(a++,Types.DOUBLE);
							plcStmt.setNull(a++,Types.DOUBLE);
							plcStmt.setNull(a++,Types.DOUBLE);
							plcStmt.setNull(a++,Types.DOUBLE);
						}
					}

					if(pl.contains("fn")){
						if((fn = (String)plc.get("fullName")) != null){
							plcStmt.setString(a++,fn);
						}else{
							plcStmt.setNull(a++,Types.VARCHAR);
						}
					}

					if(pl.contains("c")){
						if((c = (String)plc.get("country")) != null){
							plcStmt.setString(a++,c);
						}else{
							plcStmt.setNull(a++,Types.VARCHAR);
						}
					}

					if(pl.contains("cc")){
						if((cc = (String)plc.get("countryCode")) != null){
							plcStmt.setString(a++,cc);
						}else{
							plcStmt.setNull(a++,Types.VARCHAR);
						}
					}

					if(pl.contains("pt")){
						if((pt = (String)plc.get("placeType")) != null){
							plcStmt.setString(a++,pt);
						}else{
							plcStmt.setNull(a++,Types.VARCHAR);
						}
	                }

	                if(pl.contains("sa")){
						if((sa = (String)plc.get("streetAddress")) != null){
							plcStmt.setString(a++,sa);
						}else{
							plcStmt.setNull(a++,Types.VARCHAR);
						}
					}

					plcStmt.addBatch();
				}
				ct++;
				if(ct >= 8000){
					plcStmt.executeBatch();
					ct = 0;
				}
		//		long end = System.nanoTime();
		//		total += end - start;
				a = 1;
			}
			plcStmt.executeBatch();
			System.out.println("places done");
		//	System.out.println("Places table inserts complete "+(double)((total)/1000000000.0)+" seconds");
        }catch(SQLException se){
        	System.out.println("SQL fail in plc insert");
            se.printStackTrace();
			disconnect();
        }catch(Exception e){
        	System.out.println("fail in plc insert");
             e.printStackTrace();
			disconnect();
        }
	}
}
