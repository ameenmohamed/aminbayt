<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
 	<meta http-equiv="X-UA-Compatible" content="IE=edge">	
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href=css/bootstrap-theme.min.css" rel="stylesheet">
    
    
<title>Amin Bayt web sockets</title>
</head>
<body role="document">
 	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    
    <!-- Start of nav bars -->
     <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Ameen Bayt</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="container theme-showcase" role="main">

      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h2>Ameen Bayt (Secure House) :</h2> <div id="timeStamp"></div>
        <p>Basic Security system version 1 using Raspberry pi and Arduino . Arduino has all sensors and raspberry pi hosts webapplication and clicks pics.</p>
      </div>
    <!-- end of nav bars  -->
     <div>
        <input type="text" id="userinput" />  <input type="submit"  class="btn btn-sm btn-primary" 
            value="Send Message to Server" onclick="start()" />
        
    </div>
     <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Arduino Serial Communication</h3>
            </div>
         	<div class="panel-body" id="messages"> </div>
    </div>    
     <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Command response from Arduino </h3>
            </div>
         	<div class="panel-body" id="cmdresp"> </div>
    </div>    
     
  
    <script type="text/javascript">
        var webSocket = new WebSocket('ws://<%= request.getServerName() %>:<%= request.getServerPort() %>/aminbayt/websocket');

        webSocket.onerror = function(event) {
            onError(event)
        };

        webSocket.onopen = function(event) {
            onOpen(event)
        };

        webSocket.onmessage = function(event) {
            onMessage(event)
        };

        function onMessage(event) {
        	
            if(event.data.indexOf("Time") == 0){
            	var eventData = event.data;
            	var timeEnd = eventData.indexOf("{");
            	var timeStr = eventData.substring(0, timeEnd-1);
            	var jsonStr = eventData.substring(timeEnd, eventData.length);
            	var jsonPretty = JSON.stringify(JSON.parse(jsonStr),null,2);
            document.getElementById('messages').innerHTML =  jsonPretty;
            document.getElementById('timeStamp').innerHTML = timeStr;
            }else{
            	 document.getElementById("userinput").value = "";
            	 document.getElementById('cmdresp').innerHTML += '<br />' + event.data;
                }
        }

        function onOpen(event) {
            document.getElementById('cmdresp').innerHTML += 'Now Connection established';
        }

        function onError(event) {
            alert(event.data);
        }

        function start() {
            var text = document.getElementById("userinput").value;

            webSocket.send(text);
            return false;
        }
    </script>


</body>
</html>