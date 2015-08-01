<%@page import="umw.dahlgren.twitter.snapshot.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.*,java.io.*" %>
<%

	Hashtable<String, String> vars =
        		(Hashtable<String, String>)
                		session.getAttribute("info");

 	System.out.println("Getting size for progress bar");
	Progress prog = new Progress((String)vars.get("start"), (String)vars.get("end"), Double.parseDouble(vars.get("long1")),Double.parseDouble(vars.get("lat1")), Double.parseDouble(vars.get("long2")),Double.parseDouble(vars.get("lat2")), vars.get("key1"), Integer.parseInt(vars.get("ermin")), Integer.parseInt(vars.get("ermax")), Integer.parseInt(vars.get("eemin")), Integer.parseInt(vars.get("eemax")));

    session.setAttribute("numxfer", prog.getProg());
	System.out.println("Got size for progress bar");
%>
