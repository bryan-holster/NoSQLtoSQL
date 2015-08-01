<%@page import="umw.dahlgren.twitter.snapshot.*"%>
<%@page import="java.util.*"%>

<%
	Hashtable<String, String> lg_info =
        	(Hashtable<String, String>)
                	session.getAttribute("lg_info");

	lg_info.put("user", "");
	lg_info.put("pass", "");

	response.sendRedirect("login.jsp");
%>