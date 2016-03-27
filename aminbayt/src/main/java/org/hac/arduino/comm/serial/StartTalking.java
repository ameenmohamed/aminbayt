package org.hac.arduino.comm.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.Scanner;

import javax.websocket.Session;

import org.hac.arduino.comm.serial.helper.MotionAction;
import org.hac.homesecurity.aminbayt.util.BaytConstants;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fazecast.jSerialComm.SerialPort;


public class StartTalking  {

	static ObjectMapper mapper = new ObjectMapper();
	static ArduinoData dataard = null;
	static String jsonStrFeed ="{}";
	static Session _session=null;

	public Session getSession() {
		return _session;
	}

	public void setSession(Session _session) {
		this._session = _session;
	}

	static {
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
	}
	public static BufferedReader input;
	public static OutputStream output;
	/** Milliseconds to block while waiting for port open */
	public static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	public static final int DATA_RATE = 9600;
	static SerialPort ubxPort=null;
	static OutputStream outputStream;
	static Scanner data;

	public void initialize() {
		//mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		boolean openedSuccessfully =false;
		SerialPort[] ports = SerialPort.getCommPorts();
		System.out.println("***** Available Ports:");
		for (int i = 0; i < ports.length; ++i){
			System.out.println(" [" + i + "] " + ports[i].getSystemPortName() + ": " + ports[i].getDescriptivePortName());		 
		}
		ubxPort = ports[0];
		try {
			ubxPort.closePort();
			openedSuccessfully = ubxPort.openPort();
			
			System.out.println("port Name ...."+ubxPort.getSystemPortName()+" Status:"+openedSuccessfully);
			
		} catch (Exception e) {
			System.out.println("Failed Opening " + ubxPort.getSystemPortName() + ": " + ubxPort.getDescriptivePortName() + ": " + openedSuccessfully);
		}
		if(openedSuccessfully){
			System.out.println("Sucess port " + ubxPort.getSystemPortName() + ": " + ubxPort.getDescriptivePortName() + ": " + openedSuccessfully);
		}
		if(ubxPort !=null){
		ubxPort.setBaudRate(DATA_RATE);
		ubxPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		InputStream instrm = ubxPort.getInputStream();
		if(instrm !=null){
		 data = new Scanner(instrm);
		}else {System.out.println("NO INPUT Stream from "+ubxPort.getSystemPortName());}
		 outputStream = ubxPort.getOutputStream();
		}else{
			System.out.println("ubxPort is NULL !!!");
		}
		if (!openedSuccessfully){
			//ubxPort.addDataListener(new SerialPortDataListener());
			return;
			}
		
	}
	
	public ArduinoData getArdData(){
		if(jsonStrFeed.contains("{")){
			try {
				 dataard = mapper.readValue(jsonStrFeed, ArduinoData.class);
				System.out.println("Ardobj:"+dataard.toString());
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return dataard;
	}
	
	public String getArdJsonFeed(){
		
		return jsonStrFeed;
	}
	
	public String closeConn(){
		StringBuffer sb = new StringBuffer();
		sb.append("port status:");
		sb.append(ubxPort.isOpen());
		if(ubxPort.isOpen()){
		sb.append("exec port close:");
		sb.append(ubxPort.closePort());
		}else{sb.append("port already close");}
		return sb.toString();
	}
	
	public void readData(){
		while(data.hasNextLine()){
			 jsonStrFeed = data.nextLine();
			System.out.println(jsonStrFeed);
		}
	}
	
	public void readSerialData(){
		ReadSerialThread sreader = new ReadSerialThread();
		Thread rdrThread = new Thread(sreader);
		rdrThread.start();
	}
	
	public void sendData(String ardComm){
		WriteSerialThread swriter = new WriteSerialThread(ardComm);
		Thread wrtThread = new Thread(swriter);		
		wrtThread.start();
	}
	
	public static boolean isPortOpen(){
		if(ubxPort !=null){
		return ubxPort.isOpen();
		}else{
			return false;
		}
	}
		
}
class ReadSerialThread  implements Runnable  {
	
	public ReadSerialThread() {
	
	}
 
	public void run() {
		while(StartTalking.isPortOpen()){
		while(StartTalking.data.hasNextLine()){
			String lineFeed = StartTalking.data.nextLine();
			StartTalking.jsonStrFeed = lineFeed;
			MotionAction maction = new MotionAction();
			maction.parseImageTask(StartTalking.jsonStrFeed);
			if(StartTalking._session !=null){
				String formattedDate = BaytConstants.dateFormat.format(new Date());
				try {
					StartTalking._session.getBasicRemote().sendText("Time :" +formattedDate + " "+ StartTalking.jsonStrFeed );
				} catch (IOException e) {				
					e.printStackTrace();
				}				
			}		
		}	
		}//wtrue
	}
}

class WriteSerialThread implements  Runnable {
	
	String ardCommand ;

	public WriteSerialThread() {
		
	}
	public WriteSerialThread(String msg) {
		ardCommand = msg;
	}
	
 
	public void run() {
		System.out.println("Serial writer for ard ....");
		try {
        	StartTalking.outputStream.write(ardCommand.getBytes());
        	System.out.println("Command Trasmission Done ...");
		} catch (IOException e) {	
			e.printStackTrace();
		}	
	}
}
