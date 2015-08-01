<%@page import="umw.dahlgren.twitter.snapshot.*"%>
<%@page import="java.util.*"%>

<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	User current = new User(username, password);
	Hashtable<String, String> lg_info = new Hashtable<String, String>();
	session.setAttribute("lg_info", lg_info);
	
	lg_info.put("error", "");

	if(current.login() == 1){
		lg_info.put("user", username);
		lg_info.put("pass", password);
		response.sendRedirect("index.jsp");
	}else{
		lg_info.put("error", "Invalid username and/or password");
		response.sendRedirect("login.jsp");
	}
%>

