<%@page import="umw.dahlgren.twitter.snapshot.*" %>
<%@page import="java.io.*" %>
<%@page import="java.util.Hashtable" %>
<%@page import="java.util.ArrayList" %>
<%
	int num = -1;
	Hashtable<String, String> lg_info =
        	(Hashtable<String, String>)
                	session.getAttribute("lg_info");

	Hashtable<String, String> vars =
                (Hashtable<String, String>)
		session.getAttribute("info");
	if(vars == null){
                //Do nothing
        }else{

		double toplong = Double.parseDouble(vars.get("long1"));
        	double toplat = Double.parseDouble(vars.get("lat1"));
        	double bottomlong = Double.parseDouble(vars.get("long2"));
        	double bottomlat = Double.parseDouble(vars.get("lat2"));
        	String key1 = vars.get("key1");
        	//String key2 = vars.get("key2");
        	//String key3 = vars.get("key3");
        	int ermin = Integer.parseInt(vars.get("ermin"));
        	int ermax = Integer.parseInt(vars.get("ermax"));
        	int eemin = Integer.parseInt(vars.get("eemin"));
        	int eemax = Integer.parseInt(vars.get("eemax"));
		String dbname = vars.get("dbname");
		String start = vars.get("start");
        	String end = vars.get("end");

                ArrayList<String> tw =
                        (ArrayList<String>)session.getAttribute("tw");
                ArrayList<String> ur =
                        (ArrayList<String>)session.getAttribute("ur");
                ArrayList<String> pl =
                        (ArrayList<String>)session.getAttribute("pl");

		Interpreter ling = new Interpreter(tw, ur, pl, lg_info.get("user"), lg_info.get("pass"), start, end, dbname, toplong, toplat, bottomlong, bottomlat, key1, ermin, ermax, eemin, eemax);

        	ling.mysqlTest();
        	ling.transferData();
		if(ling.successful()){
			vars.put("success", "true");
		}else{ vars.put("success", "false"); }
        }
	//response.sendRedirect("index.jsp");
%>

