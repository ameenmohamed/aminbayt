<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Amin Bayt web sockets</title>
</head>
<body>
    <div>
        <input type="text" id="userinput" /> <br> <input type="submit"
            value="Send Message to Server" onclick="start()" />
    </div>
    <div id="messages"></div>
    <div id="cmdresp"></div>
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
            document.getElementById('messages').innerHTML = '<br />' + event.data;
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