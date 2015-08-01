<%@page import="umw.dahlgren.twitter.snapshot.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.*,java.sql.*,java.io.*" %>

<%
	int num = 0;
	int perc = 0;
    int theSize = 0;

    Hashtable<String, String> vars =
                (Hashtable<String, String>)
                        session.getAttribute("info");
	try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/"+vars.get("dbname"), "bholster", "datascience");

        Statement stmt = conn.createStatement();	
        String selNum = "SELECT count(*) AS 'COUNT' FROM Tweets";
        ResultSet st = stmt.executeQuery(selNum);
        if(st.next()){
            num = st.getInt("COUNT");
        }
    	conn.close();
	}catch(SQLException se){
        se.printStackTrace();
   	}catch(Exception e){ 
    }

    theSize = (int)session.getAttribute("numxfer");
    System.out.println("Size: "+theSize+" Num: "+num);

   
    perc = (num*100)/theSize;
    out.println("<div class='progress-bar' role='progressbar' aria-valuenow='"+perc+"' aria-valuemin='0' aria-valuemax='100' style='width: "+perc+"%;'>"+perc+"%</div>");

%>