<%@page import="umw.dahlgren.twitter.snapshot.*" %>
<%@page import="java.io.*" %>
<%@page import="java.util.Hashtable" %>
<%
	Hashtable<String, String> lg_info =
        	(Hashtable<String, String>)
                	session.getAttribute("lg_info");

	Interpreter ling = new Interpreter(lg_info.get("user"), lg_info.get("pass"), request.getParameter("newdbname"));
	lg_info.put("newdbtoset", request.getParameter("newdbname"));
	response.sendRedirect("index.jsp");
%>

