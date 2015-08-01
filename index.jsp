<%@page import="umw.dahlgren.twitter.snapshot.*"%>
<%@page import="java.util.*" %>
<%
	Hashtable<String, String> lg_info =
        	(Hashtable<String, String>)
                	session.getAttribute("lg_info");

	if(lg_info != null && lg_info.get("user") != "" && lg_info.get("pass") != ""){
		Interpreter forDBNames = new Interpreter(lg_info.get("user"),lg_info.get("pass"));
		ArrayList<String> dbNames = forDBNames.allDBs();

		Hashtable<String, String> vars =
        		(Hashtable<String, String>)
                		session.getAttribute("info");
		if(vars == null){
        		vars = new Hashtable<String, String>();
                	session.setAttribute("info", vars);
      		}

		int num = 0;
		if(session.getAttribute("numxfer")!=null){
			num = (int)session.getAttribute("numxfer");
		}
		String db = "";
		if(vars.get("dbname")!=null){
			db = vars.get("dbname");
		}

		History hist = new History();
	
%>
<html>
<head>
	<title>NoSQLtoSQL</title>
	<link rel="stylesheet" type="text/css" href="layout.css">
	<link rel="stylesheet" type="text/css" href="reg_log.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<!-- Optional theme -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css"> 
	<link rel="stylesheet" type="text/css"
    		href="http://js.api.here.com/v3/3.0/mapsjs-ui.css" />
  	<script type="text/javascript" charset="UTF-8"
    		src="http://js.api.here.com/v3/3.0/mapsjs-core.js"></script>
  	<script type="text/javascript" charset="UTF-8"
    		src="http://js.api.here.com/v3/3.0/mapsjs-service.js"></script>
  	<script type="text/javascript" charset="UTF-8"
    		src="http://js.api.here.com/v3/3.0/mapsjs-mapevents.js"></script>
  	<script type="text/javascript"  charset="UTF-8"
    		src="http://js.api.here.com/v3/3.0/mapsjs-ui.js"></script>
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	<script>	
		function validateForm() {
        		var x = document.forms["myForm"]["start"].value;
        		var y = document.forms["myForm"]["end"].value;
			var z = document.forms["myForm"]["db"].value;
        		if (x == null || x == "" || y == null || y == "" || z == "Choose existing database..." || z == "" || z == null) {
                		alert("A database must be selected, as well as both a start and end date.");
                	return false;
        		}	
		}
	</script>
	<%	
		Hashtable<String,String> h = hist.getHist();
		Set<String> keys = h.keySet();
		out.println("<script>");
		for(String key: keys){
			out.println("var "+key+" = "+h.get(key)+";");
		}
		out.println("</script>");
	%>	
	<script type="text/javascript">
	
   		function changeDb() {
    			var dbselect = document.getElementById("dbselect");
    			var selectedValue = dbselect.options[dbselect.selectedIndex].value;
			var sqlhelp = "mysql -u <%=lg_info.get("user")%> -p</br>show databases;</br>use "+ selectedValue +";</br>show tables;";
			if(this[selectedValue] != null){
					document.getElementById('mysqlhelp').innerHTML = sqlhelp;
					document.getElementById('end').value = this[selectedValue][0];
            		document.getElementById('start').value = this[selectedValue][1];
            		document.getElementById('eemax').value = this[selectedValue][2];
            		document.getElementById('eemin').value = this[selectedValue][3];
            		document.getElementById('ermax').value = this[selectedValue][4];
            		document.getElementById('ermin').value = this[selectedValue][5];
            		document.getElementById('keyword1').value = this[selectedValue][6];
            		document.getElementById('lat2').value = this[selectedValue][7];
            		document.getElementById('long2').value = this[selectedValue][8];
            		document.getElementById('lat1').value = this[selectedValue][9];
            		document.getElementById('long1').value = this[selectedValue][10];
            		for(i = 12; i < this[selectedValue].length; i++){
            			document.getElementById(this[selectedValue][i]).checked = true;
            		}
        	}
            document.myForm.db.value = selectedValue;
   		}
  	</script>
  	<script>
		var ld = 0;
		var counter = 0;

  		function next() {
      		if(counter == 2){
        		counter = 0;
      		}
		if(counter == 0){
			$('#mapModal').css('cursor', 'url(http://caladan.umw.edu:22224/NoSQLtoSQL/img/secursor.gif) 31 31,auto');
		}else{
			$('#mapModal').css('cursor', 'url(http://caladan.umw.edu:22224/NoSQLtoSQL/img/nwcursor.gif),auto');
		}
      		return counter +=1;
  		}
  		var last = 0;

  		var platform = new H.service.Platform({
      		app_id: 'DemoAppId01082013GAL',
      		app_code: 'AJKnXv84fjrb0KIHawS0Tg',
      		useCIT: true
  		});
  		var defaultLayers = platform.createDefaultLayers();
  	</script>

</head>
<body>
<div style="background-color:#333333">
<div class="container">
	<jsp:include page="menu.jsp"/>
        <div class="modal" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                        <div class="modal-content">
                                                <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                        <h4 class="modal-title" id="myModalLabel">Map</h4>
                                                </div>
                                                <div class="modal-body">
                                                        <div id="map" style="width:100%;height:500px;background:grey;">
                                                        </div>
                                                </div>
                                                <div class="modal-footer">
                                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Okay</button>
                                                </div>
                                        </div>
                                </div>
                        </div>
	<div class="row">
	<div class="col-md-3">
	<table class="table">
	<thead>
	<tr><th><p class="text-center">Create a new database: <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#dbModal"><span class="glyphicon glyphicon-plus"></span></button></p></th></tr>
	</thead>
	<tbody>
	<tr><td>
		<select class="form-control" id="dbselect" onchange="changeDb();">
		<option>Choose existing database...</option>
<%	for(String s: dbNames){
			if(!s.equals("gruntLabor")){
                out.println("<option>" + s + "</option>");
			}
	}
%>
		</select>
	</td></tr>
	</tbody></table>
	<p class="text-center"><button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#fieldModal">Change DB fields</button></p>
	<p class="text-center" style="color:red;font-size:12px;">Default table settings will be used if no changes are made</p>
	</div>
	<div class="modal fade bs-example-modal-sm" id="dbModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  		<div class="modal-dialog modal-sm">
    		<div class="modal-content">
			<div class="modal-body">
				<form action="createDBController.jsp" method="POST">
					<p class="text-center">
					<b>Enter new database name:</b>
					</p>
					<div class="well" style="font-size:10">
						Permitted characters in <u>unquoted identifiers</u>: ASCII: [0-9,a-z,A-Z$_] (basic Latin letters, 
					digits 0-9, dollar, underscore) Extended: U+0080 .. U+FFFF</br>
						Permitted characters in <u>quoted identifiers</u> include the full Unicode Basic Multilingual Plane 
					(BMP),except U+0000: ASCII: U+0001 .. U+007F Extended: U+0080 .. U+FFFF</div>
					<p class="text-center">
					<input type="text" name="newdbname" /></br></br>
				<button type="submit" class="btn btn-primary btn-sm">Create</button>
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancel</button>
					</p>
				</form>
			</div>
    		</div>
  		</div>
	</div>
	
	<div class="col-md-1"></div>
	<div class="col-md-4">
		<form name="myForm" action="indexController.jsp" onsubmit="return validateForm()" method="POST">
		<input type="hidden" id="db" name="db" value="">
			<div class="modal fade bs-example-modal-lg" id="fieldModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  		<div class="modal-dialog modal-lg">
    		<div class="modal-content">
			<div class="modal-body">
				<jsp:include page="options.jsp"/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Save & Close</button>
			</div>
    		</div>
  		</div>
	</div>
			<table class="table">
                        <thead>
                        <tr>
				<th><p class="text-center"><button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#mapModal">Open map <span class="glyphicon glyphicon-globe"></span></button></p></th>
                                <th><p class="text-center">Longitude</p></th>
				<th><p class="text-center">Latitude</p></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
				<td>Top-left coordinate</td>
                                <td><input class="form-control input-sm" type="text" name="toplong" id="long1" readonly></td>
                                <td><input class="form-control input-sm" type="text" name="toplat" id="lat1" readonly></td>
                        </tr>
			<tr>
				<td>Bottom-right coordinate</td>
                                <td><input class="form-control input-sm" type="text" name="bottomlong" id="long2" readonly></td>
                                <td><input class="form-control input-sm" type="text" name="bottomlat" id="lat2" readonly></td>
                        </tr>
                        </tbody>
                        </table>        
			<table class="table">
                        <thead>
                        <tr>
                                <th><p class="text-center">Keyword/Phrase/Regex</p></th>
                        </tr>
                        </thead>
			<tbody>
                        <tr>
                                <td><input class="form-control input-sm" type="text" name="keyword1" id="keyword1"></td>
                        </tr>
			</tbody>
                        </table>
			<table class="table">
                        <thead>
                        <tr>
                                <th></th>
                                <th><p class="text-center">Min</p></th>
                                <th><p class="text-center">Max</p></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                                <td>Follower count</td>
                                <td><input class="form-control input-sm" type="number" name="ermin" id="ermin"></td>
                                <td><input class="form-control input-sm" type="number" name="ermax" id="ermax"></td>
                        </tr>
                        <tr>
                                <td>Followee count</td>
                                <td><input class="form-control input-sm" type="number" name="eemin" id="eemin"></td>
                                <td><input class="form-control input-sm" type="number" name="eemax" id="eemax"></td>
                        </tr>
                        </tbody>
                        </table>

			<table class="table">
			<thead>
			<tr>
				<th><p class="text-center">Start Date</p></th>
				<th><p class="text-center">End Date</p></th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td><input class="form-control input-sm" type="date" name="start" id="start" value="2014-10-16"></td>
				<td><input class="form-control input-sm" type="date" name="end" id="end" value="2014-10-27"></td>
			</tr>
			</tbody>
			</table>
			<div class="text-center">
			<button type="submit" class="btn btn-primary">Update</button>
			</div>
		</form>
	</div>
	<div class="col-md-1"></div>
	<div class="col-md-3">
		<p></p>
		<jsp:include page="help.jsp"/>
	</div>
	</div>
	</div>
</div>
</div>	
<%
	if(lg_info.get("newdbtoset") != null && lg_info.get("newdbtoset") != ""){
		out.println("<script>$('#dbselect').val('"+lg_info.get("newdbtoset")+"');document.getElementById('db').value ='"+lg_info.get("newdbtoset")+"';</script>"); 
	}
	if(vars.get("success") == "true"){
		out.println("<script type='text/javascript'>$(document).ready(function() {myApp.showResult();});</script>");
	}
	vars.put("success", "false");
	lg_info.put("newdbtoset", "");
%>
<%
out.println("<script> var myApp; myApp = myApp || (function () { var resultDiv = $('<div class=\"modal bs-example-modal-sm\" id=\"resultDialog\" data-backdrop=\"static\" data-keyboard=\"false\" role=\"dialog\" aria-labelledby=\"basicModal\" aria-hidden=\"true\" tabindex=\"-1\"><div class=\"modal-dialog modal-sm\"><div class=\"modal-content\"><div class=\"modal-header\"><div class=\"text-center\"><h4>Successfully transferred <u>"+num+"</u> records to the database <b>"+db+"</b>!</h4></div></div><div class=\"modal-body\"><div class=\"text-center\"><button type=\"button\" class=\"btn btn-success btn-md\" data-dismiss=\"modal\"><span class=\"glyphicon glyphicon-ok\"></span></button></div></div></div></div></div></div></div>'); return { showResult: function () { resultDiv.modal(); }, hideResult: function () { resultDiv.modal('hide'); },}; })(); </script>");
%>
<script>
var sqlhelp = "mysql -u <%=lg_info.get("user")%> -p</br>show databases;</br>use database_name;</br>show tables;";
document.getElementById('mysqlhelp').innerHTML = sqlhelp;
</script>
<%/*<script src="myJS/history.js"></script>*/%>
<script src="myJS/maptoggle.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>
<%
	}else{
		response.sendRedirect("login.jsp");
	}

%>
