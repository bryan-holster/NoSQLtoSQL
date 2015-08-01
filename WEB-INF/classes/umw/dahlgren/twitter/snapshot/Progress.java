package umw.dahlgren.twitter.snapshot;

import java.io.*;
import java.util.*;
import java.text.*;
import com.mongodb.*;

public class Progress {
	private int size = 0;
	private MongoObject mobj = null;
	private Date startDate;
	private Date endDate;

	public Progress(String start, String end, double tg, double tt, double bg, double bt, String k1, int rn, int rx, int en, int ex) {
		System.out.println("Making progress object");
		this.mobj = new MongoObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
            startDate = sdf.parse(start + " 00:00:01");
            endDate = sdf.parse(end + " 23:59:59");
		}catch(ParseException pe){
            pe.printStackTrace();
        }
		this.size = mobj.getQueryCount(startDate,endDate,tg,tt,bg,bt,k1,rn,rx,en,ex);
		mobj.close();
		System.out.println("Made progress object");
	}

	public int getProg(){
		return size;
	}
}