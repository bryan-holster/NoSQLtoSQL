package umw.dahlgren.twitter.snapshot;

import com.mongodb.*;
import java.util.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class MongoObject {

	private DB db = null;
	private MongoClient mongoClient = null;
	private String collectionName = "tweetsGoodFields";

	public MongoObject() {
		//Instantiate
	}

	public void setCollection(String colName) {
		this.collectionName = colName;
	}


	public void connect() {
		try{
			this.mongoClient = new MongoClient( "localhost" , 27017 );
		}catch(Exception uhe){
			System.out.println("UnknownHostException");
			mongoClient.close();
			uhe.printStackTrace();
		}
		this.db = mongoClient.getDB( "twitterDB" );
	}

	public Set<String> getCollections() {
		return db.getCollectionNames();
	}
	
	public void close(){
		mongoClient.close();
	}

	public DBCursor createQuery(Date start, Date end, double toplong, double toplat, double bottomlong, double bottomlat, String key1, int ermin, int ermax, int eemin, int eemax) {
	
		connect();	
	
		DBCollection coll = db.getCollection(collectionName);
		BasicDBObject query = new BasicDBObject("createdAt", new BasicDBObject("$gte", start).append("$lte", end));
		
		if(toplong != 181.00 && toplat != 181.00 && bottomlong != 181.00 && bottomlat != 181.00){
			query.append("geoLocation.longitude", new BasicDBObject("$gte", toplong).append("$lte", bottomlong));
			query.append("geoLocation.latitude", new BasicDBObject("$gte", bottomlat).append("$lte", toplat));
		}
		if(key1 != ""){
  			query.append("text", new BasicDBObject("$regex", ".*" + key1 + ".*"));
		}
		if(ermin >= 0 && ermax >= 0 && ermin < ermax){
 			query.append("user.followersCount", new BasicDBObject("$gte", ermin).append("$lte", ermax));
		}if(eemin >= 0 && eemax >= 0 && eemin < eemax){
			query.append("user.friendsCount", new BasicDBObject("$gte", eemin).append("$lte", eemax));
		}
		
		DBCursor cursor = coll.find(query);

		return cursor;
	}	

	public int getQueryCount(Date start, Date end, double toplong, double toplat, double bottomlong, double bottomlat, String key1, int ermin, int ermax, int eemin, int eemax) {
	
		connect();	
	
		DBCollection coll = db.getCollection(collectionName);
		BasicDBObject query = new BasicDBObject("createdAt", new BasicDBObject("$gte", start).append("$lte", end));
		
		if(toplong != 181.00 && toplat != 181.00 && bottomlong != 181.00 && bottomlat != 181.00){
			query.append("geoLocation.longitude", new BasicDBObject("$gte", toplong).append("$lte", bottomlong));
			query.append("geoLocation.latitude", new BasicDBObject("$gte", bottomlat).append("$lte", toplat));
		}
		if(key1 != ""){
  			query.append("text", new BasicDBObject("$regex", ".*" + key1 + ".*"));
		}
		if(ermin >= 0 && ermax >= 0 && ermin < ermax){
 			query.append("user.followersCount", new BasicDBObject("$gte", ermin).append("$lte", ermax));
		}if(eemin >= 0 && eemax >= 0 && eemin < eemax){
			query.append("user.friendsCount", new BasicDBObject("$gte", eemin).append("$lte", eemax));
		}
		
		int theCount = coll.find(query).count();

		return theCount;
	}
}
