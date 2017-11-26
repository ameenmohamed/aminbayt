package org.hac.amin.bayt.util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.annotation.PostConstruct;

import org.hac.amin.bayt.util.pc.EventJury;
import org.hac.amin.bayt.util.pc.SerialReader;
import org.hac.amin.bayt.util.pc.SerialWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

@Component
public class TwoWaySerialComm
{
	
	String portName  ;
	
	@Autowired
	ExecutorUtil execUtil;
	
	@Autowired
	SerialReader sreader;
	
	@Autowired
	SerialWriter swriter;
	
	@Autowired
	EventJury eventJury;
	
	@PostConstruct
    public void init(){
    	
		String[] portNames = SerialPortList.getPortNames();
	try {
	    for(int i = 0; i < portNames.length; i++){
	        String pName =portNames[i];
	        SerialPort serialPort = new SerialPort(pName);
	        
	        if(testPort(serialPort)){
	        	System.out.println("Succeeded "+serialPort.getPortName());
	        	boolean openStatus = serialPort.openPort();//Open serial port
	        	if(openStatus){
		            boolean paramStatus = serialPort.setParams(9600, 8, 1, 0);//Set params.
		            System.out.println(paramStatus);
		            sreader.setStream(serialPort);
			        swriter.setOStream(serialPort);
			        System.out.println("Starng threads");
			        execUtil.getExecutor().execute(new Thread(sreader));
			        execUtil.getExecutor().execute(new Thread(swriter));
			        execUtil.getExecutor().execute(new Thread(eventJury));
			        break;
		        }
	        }

	    }
 		       
		} catch (Exception e) {
			
			e.printStackTrace();
		}  
    	
    }
	
	
	private boolean testPort(SerialPort serialPort){
		boolean portCheck = false;
			try {
				System.out.println("testing port "+serialPort.getPortName());
				boolean openStatus = serialPort.openPort();//Open serial port				
				 if(openStatus){			
					 boolean paramStatus = serialPort.setParams(9600, 8, 1, 0);//Set params.
					 for (int i = 0; i < 5; i++) {
						 String  str = serialPort.readString();
			            	if(null != str){
			            		portCheck = true;
			            		break;
			            	}						 
						 Thread.sleep(1000);
					}
					 serialPort.closePort();
				 }
			} catch (SerialPortException | InterruptedException e) {
				
				e.printStackTrace();
			}
			System.out.println("test port "+serialPort.getPortName()+ " Status:"+ portCheck);
			return portCheck;
		
	}
}
