<%@page import="umw.dahlgren.twitter.snapshot.*" %>
<%@page import="java.io.*" %>
<%@page import="java.util.Hashtable" %>	
<%@page import="java.util.ArrayList" %>	
<%
	Hashtable<String, String> vars =
		(Hashtable<String, String>)
			session.getAttribute("info");
	if(vars == null){
		vars = new Hashtable<String, String>();
	        session.setAttribute("info", vars);
	}
	
		String long1 = "181.00";
		String lat1 = "181.00";
		String long2 = "181.00";
		String lat2 = "181.00";
		String key1 = " ";
		String ermin = "-1";
		String ermax = "-1";
		String eemin = "-1";
		String eemax = "-1";

		String _1 = ""; String _2 = ""; String _3 = ""; String _4 = ""; String _5 = ""; String _6 = ""; String _7 = ""; String _8 = ""; String _9 = "";
	
		if(request.getParameter("toplong") != "" && request.getParameter("toplat") != "" && request.getParameter("bottomlong") != "" && request.getParameter("bottomlat") != ""){
			long1 = request.getParameter("toplong");
			lat1 = request.getParameter("toplat");	
			long2 = request.getParameter("bottomlong");
			lat2 = request.getParameter("bottomlat");
			_9 = long1;
			_8 = lat1;
			_7 = long2;
			_6 = lat2;
		}
		if(request.getParameter("keyword1") != null){
                	key1 = request.getParameter("keyword1");
			_5 = key1;
		}
		if(request.getParameter("ermin") != ""){
                	ermin = request.getParameter("ermin");
			_4 = ermin;
		}if(request.getParameter("ermax") != ""){
                	ermax = request.getParameter("ermax");
			_3 = ermax;
		}if(request.getParameter("eemin") != ""){
                	eemin = request.getParameter("eemin");
			_2 = eemin;
		}if(request.getParameter("eemax") != ""){
                	eemax = request.getParameter("eemax");
			_1 = eemax;
		}
		vars.put("long1", long1);
		vars.put("lat1", lat1);
		vars.put("long2", long2);
		vars.put("lat2", lat2);
                vars.put("key1", key1);
                vars.put("ermin", ermin);
                vars.put("ermax", ermax);
                vars.put("eemin", eemin);
                vars.put("eemax", eemax);
		vars.put("dbname", request.getParameter("db"));
                vars.put("start", request.getParameter("start"));
                vars.put("end", request.getParameter("end"));


        ArrayList<String> tw = new ArrayList<String>();
        	if(request.getParameter("dt_tw") != null){tw.add("dt");}
        	if(request.getParameter("t_tw") != null){tw.add("t");}
        	if(request.getParameter("l_tw") != null){tw.add("l");}
        	if(request.getParameter("gl_tw") != null){tw.add("gl");}
        	if(request.getParameter("s_tw") != null){tw.add("s");}
        	if(request.getParameter("rtu_tw") != null){tw.add("rtu");}
        	if(request.getParameter("rts_tw") != null){tw.add("rts");}
        ArrayList<String> ur = new ArrayList<String>();
        	if(request.getParameter("sn_ur") != null){ur.add("sn");}
        	if(request.getParameter("dt_ur") != null){ur.add("dt");}
        	if(request.getParameter("utc_ur") != null){ur.add("utc");}
        	if(request.getParameter("ds_ur") != null){ur.add("ds");}
        	if(request.getParameter("lc_ur") != null){ur.add("lc");}
        	if(request.getParameter("sc_ur") != null){ur.add("sc");}
        	if(request.getParameter("fv_ur") != null){ur.add("fv");}
        	if(request.getParameter("fw_ur") != null){ur.add("fw");}
        	if(request.getParameter("fr_ur") != null){ur.add("fr");}
        	if(request.getParameter("v_ur") != null){ur.add("v");}
		ArrayList<String> pl = new ArrayList<String>();
			if(request.getParameter("br_pl") != null){pl.add("br");}
			if(request.getParameter("fn_pl") != null){pl.add("fn");}
			if(request.getParameter("c_pl") != null){pl.add("c");}
			if(request.getParameter("cc_pl") != null){pl.add("cc");}
			if(request.getParameter("pt_pl") != null){pl.add("pt");}
			if(request.getParameter("sa_pl") != null){pl.add("sa");}

	    session.setAttribute("tw", tw);
	    session.setAttribute("ur", ur);
	    session.setAttribute("pl", pl);

		Hashtable<String, String> lg_info =
        	(Hashtable<String, String>)
                	session.getAttribute("lg_info");

		History hist = new History();
		String[] theHist = {request.getParameter("end"),request.getParameter("start"),_1,_2,_3,_4,_5,_6,_7,_8,_9,request.getParameter("db")};
		hist.save(theHist,tw,ur,pl);
%>

<html>
<head>
<title>Processing...</title>
	<link rel="stylesheet" type="text/css" href="layout.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
        <!-- Optional theme -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

	<style>
		.backg {
  			position: fixed;
  			top: 0;
  			right: 0;
  			bottom: 0;
  			left: 0;
  			z-index: 1030;
  			background-color: #333333;
  			opacity:0.8; // I ADDED THIS LINE 
    		}
	</style>
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	<script>
	function pingServer(){
		var yRequest1;

		if(window.XMLHttpRequest){
			yRequest1=new XMLHttpRequest();
		}else{
			yRequest1=new ActiveXObject("Microsoft.XMLHTTP");
		}

		yRequest1.onreadystatechange=function (){
			if((yRequest1.readyState==4) && (yRequest1.status==200)){
				document.getElementById('prog_bar').innerHTML=yRequest1.responseText;
			}
		}

		yRequest1.open("get","ping.jsp","true");
		yRequest1.send();
	}
	</script>
	<script>
		function startTransfer(){
			var xRequest1;

			if(window.XMLHttpRequest){
				xRequest1=new XMLHttpRequest();
			}else{
				xRequest1=new ActiveXObject("Microsoft.XMLHTTP");
			}

			xRequest1.onreadystatechange=function (){
				if((xRequest1.readyState==4) && (xRequest1.status==200)){
					window.location = "index.jsp";
				}
			}

			xRequest1.open("get","transit.jsp","true");
			xRequest1.send();
		}
	</script>
	<script>
	var myVar;

		function waiting(){
			var zRequest1;

			if(window.XMLHttpRequest){
				zRequest1=new XMLHttpRequest();
			}else{
				zRequest1=new ActiveXObject("Microsoft.XMLHTTP");
			}

			zRequest1.onreadystatechange=function (){
				if((zRequest1.readyState==4) && (zRequest1.status==200)){
    				myVar=setInterval(function () {pingServer()}, 5000);
    			}
    		}

    		zRequest1.open("get","size.jsp","true");
			zRequest1.send();
		}
	</script>
</head>
<body>
<div class="backg">
<div class="modal show" id="loadModal" tabindex="-1" role="dialog" aria-labelledby="loadModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1>Transferring Data...</h1>
			</div>
			<div class="modal-body">
				<div class="progress progress-striped active" id="prog_bar">	
					<div class="progress-bar" role="progressbar" aria-valuenow="5" aria-valuemin="0" aria-valuemax="100" style="width:5">
					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
    	startTransfer();
    	waiting();
    });
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>  	
</body>
</html>

