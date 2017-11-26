package org.hac.amin.bayt.util.pc;


import org.hac.amin.bayt.model.CurrentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jssc.SerialPort;

@Component
public class SerialReader implements Runnable {

	

   private final String  EOL ="EOL"; 
 
   @Autowired
   CurrentState currState;

   private SerialPort in;
   
   public void setStream(SerialPort sPort ){
    	 System.out.println("Initiating Serialport ...");
     	this.in = sPort;
     }
   
   
     public void run ()
     {
       
         StringBuffer  inString =new StringBuffer();
        try{
           
             while ( true)
             {
            	String  str = in.readString();
             	if(str!=null){
             		if(str.contains(EOL)){
            			int lasPo = str.indexOf(EOL); String substr = str.substring(0, lasPo);
            			inString.append(substr);
            			processLine(inString.toString());
            			inString.delete(0, inString.length());
            			
            			if(str.indexOf(EOL) < str.length())
            				inString.append(str.substring(str.indexOf(EOL)+2,str.length()));
            			
            		}else{
            			inString.append(str);
            		}
             	}
             	Thread.sleep(500);
             }                       
         }
         catch ( Exception  e )// | LineUnavailableException
         {
             e.printStackTrace();
         }            
     }
     
     void processLine(String inStr){
    	 String cleanStr = inStr.contains("\"") ?inStr.replace("\"", ""):inStr;
    	 currState.setSerialFeed(cleanStr);
    	
    	 
     }   
 }

