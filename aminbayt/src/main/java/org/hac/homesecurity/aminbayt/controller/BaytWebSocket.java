package org.hac.homesecurity.aminbayt.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.hac.arduino.comm.serial.ArduinoData;
import org.hac.arduino.comm.serial.StartTalking;
import org.hac.arduino.comm.serial.helper.CaptureImageHelper;
 
@ServerEndpoint("/websocket")
public class BaytWebSocket {
	
	public static StartTalking talkArd = new StartTalking();

	@OnMessage
    public void onMessage(String message, Session session) throws IOException,InterruptedException {
    	String inMsg = message;
    	session.getBasicRemote().sendText("Server recieved Command ..."+message );
    	int timeInSec = 60;
        System.out.println("User input: " + message);
        if(talkArd.getSession() == null || talkArd.getSession()!=session){      
        	talkArd.setSession(session);
        }
        if(message.startsWith("recvid")){
        	String[] vidSplit = message.split("-");
        	inMsg=vidSplit[0];
        	if(vidSplit[1]!=null && isNumeric(vidSplit[1])){
        		int recVal = Integer.parseInt(vidSplit[1]);
        		timeInSec = recVal;
        	}
        }
        
        switch (inMsg) {
        case "reset":
        	talkArd.closeConn();
        	talkArd = new StartTalking();
        	talkArd.initialize();
            talkArd.readSerialData();
            session.getBasicRemote().sendText("ReSet Complete ..." );
            break;
        case "clickpic":
        	String flName = CaptureImageHelper.captureImage();
        	session.getBasicRemote().sendText("ev:Image uplaoded to Dropbox. " + flName);
        	break;
        case "recvid":
        	//dosomething
        	break;
        case "closeconn":
        	talkArd.closeConn();
        	session.getBasicRemote().sendText("Connection Closed ");
        default:
        	talkArd.sendData(message);
        
        
        }
     //   session.getBasicRemote().sendText("Hello world Mr. " + adata);
        // Sending message to client each 1 second
       
    }
 
    
    
    @OnOpen
    public void onOpen() {
        System.out.println("Client connected");
    }
 
    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }
    
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
