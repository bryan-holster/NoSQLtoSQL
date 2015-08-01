<%@page import="umw.dahlgren.twitter.snapshot.*"%>
<%@page import="java.util.*" %>
<%
	Hashtable<String, String> lg_info =
        	(Hashtable<String, String>)
                	session.getAttribute("lg_info");
	int err = 0;
	if(lg_info != null){
		if(lg_info.get("error") != null || lg_info.get("error") != ""){
			err = 1;
		}
	}
%>
<html>

<head>
<title>Login</title>
<link rel="stylesheet" type="text/css" href="layout.css">
<link rel="stylesheet" type="text/css" href="reg_log.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script>
	function validateLogin() {
		var x = document.forms["loginForm"]["username"].value;
		var y = document.forms["loginForm"]["password"].value;
		if (x == null || x == "" || y == null || y == "") {
			alert("You must enter both a username and password.");
			return false;
		}
	}
</script>

</head>

<body>
<div style="background-color:#333333">
<div class="container">
        <div class="row">
	<div class="col-md-4"></div>
	<div class="col-md-4">
	<div class="innerContainer effect" style="padding-top:5px;padding-bottom:10px;margin-top:40px;">

<h1 style="color:#333333">NoSQLtoSQL</h1></br>
<form name="loginForm" action="loginController.jsp" onsubmit="return validateLogin()" method="POST">

<input type="text" size="32" placeholder="MySQL Username..." name="username">
<input type="password" size="32" placeholder="MySQL Password..." name="password">
</br></br>
<div class="text-center">
	<button type="submit" class="btn btn-primary">Login</button>
</div>
</form>
<p></p>

</div>
<p></p>
<%
	if(err == 1){
		out.println("<div class='text-center' style='color:red;font-size:16px;'>"+lg_info.get("error")+"</div>");
	}
%>
</div>
<div class="col-md-4"></div>
</div></div></div>
</body>
</html>
