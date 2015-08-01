package umw.dahlgren.twitter.snapshot;

import java.io.*; 
import java.util.*;

public class History {

	private Hashtable<String,String> histTable = null;

	public History(){
		histTable = new Hashtable<String,String>();
		File thisDirectory = new File("../webapps/NoSQLtoSQL/userFiles");
		String[] histFileNames = thisDirectory.list(new HistFileFilter());
		for(int i = 0; i < histFileNames.length; i++){
			try{
				load(new File(histFileNames[i]));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public Hashtable<String,String> getHist(){
		return histTable;
	}

	public void load(File file) throws Exception{
		String filename = file.getName();
		System.out.println(filename);
		String dbname = filename.substring(0, filename.length()-7);
		
		FileReader fr = new FileReader("../webapps/NoSQLtoSQL/userFiles/" + file);
		BufferedReader br = new BufferedReader(fr);
		List<String> lines = new ArrayList<String>();
		String[] hist = {};

		String str = "";
		while((str=br.readLine())!=null){
			lines.add(str);
		}
		br.close();
		hist = lines.toArray(new String[lines.size()]);
		
		histTable.put(dbname, getHistory(hist));
	}

	public String getHistory(String[] h){
		StringBuffer sb = new StringBuffer();
    		sb.append("[");
    		for(int i=0; i<h.length; i++){ 
        		sb.append("\"").append(h[i]).append("\"");
        		if(i+1 < h.length){
            			sb.append(",");
        		}
    		}
    		sb.append("]");
    		return sb.toString();
	}

	public void save(String[] newhist, ArrayList<String> tw, ArrayList<String> ur, ArrayList<String> pl){
		try{
			File f = new File("../webapps/NoSQLtoSQL/userFiles/" + newhist[11] + ".dbhist");
			FileWriter fw = new FileWriter(f);
			PrintWriter pw = new PrintWriter(fw);

			for(int j = 0; j < newhist.length; j++){
				pw.println(newhist[j]);
			}
			for(String t: tw){
				pw.println(t+"_tw");
			}
			for(String u: ur){
				pw.println(u+"_ur");
			}
			for(String p: pl){
				pw.println(p+"_pl");
			}
			pw.close();
		}catch(Exception e){
			System.out.println("Could not save history");
			e.printStackTrace();
		}
	}
	
/*
	public History(String usr){
		this.username = usr;
		load();
	}

	public void load(){
		try{
			FileReader fr = new FileReader("../webapps/NoSQLtoSQL/userFiles/" + username + ".hist");
			BufferedReader br = new BufferedReader(fr);
			List<String> lines = new ArrayList<String>();
			String str = "";
			while((str=br.readLine())!=null){
				lines.add(str);
			}
			br.close();
			hist = lines.toArray(new String[lines.size()]);
		}catch(FileNotFoundException fnf){
			System.out.println("No history file found for "+username);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public void save(String[] newhist){
		int dup = 0;
		int place = 12;
		for(int a = 0; a < newhist.length; a++){
			if(newhist[a].equals(hist[hist.length-place])){
				dup++;
			}
			place--;
		}if(dup < 12){
			try{
				File f = new File("../webapps/NoSQLtoSQL/userFiles/" + username + ".hist");
				FileWriter fw = new FileWriter(f);
				PrintWriter pw = new PrintWriter(fw);
				for(int i = 0; i < hist.length; i++){
					pw.println(hist[i]);
				}
				for(int j = 0; j < newhist.length; j++){
					pw.println(newhist[j]);
				}
				pw.close();
			}catch(Exception e){
				System.out.println("Could not save history");
				e.printStackTrace();
			}
		}else{
			System.out.println("Duplicate query, did not re-save history");
		}
	}

	public String getHistory(){
		StringBuffer sb = new StringBuffer();
    		sb.append("[");
    		for(int i=0; i<hist.length; i++){ 
        		sb.append("\"").append(hist[i]).append("\"");
        		if(i+1 < hist.length){
            			sb.append(",");
        		}
    		}
    		sb.append("]");
    		return sb.toString();
	}*/
}
