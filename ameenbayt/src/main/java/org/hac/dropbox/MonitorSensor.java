package org.hac.dropbox;

import org.hac.amin.bayt.model.BaytConfig;
import org.springframework.beans.factory.annotation.Autowired;

import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class MonitorSensor implements Runnable {

	ClickPic kachack;

	BaytConfig baytConfig;
	
	public MonitorSensor(ClickPic _kachack,BaytConfig _baytConfig) {
		kachack=_kachack;
		baytConfig=_baytConfig;
	}
	


	@Override
	public void run() {
		System.out.println("<--Pi4J--> GPIO Listen Example ... started.");
		System.out.println("ispulldown "+TestSensor.sensor.isPullResistance(PinPullResistance.PULL_DOWN));
		
		// create and register gpio pin listener            
		TestSensor.sensor.addListener(new GpioPinListenerDigital() {           
		    @Override       
		    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {        

		        if(event.getState().isHigh()){  
		        	if(baytConfig.isActivateSecurity()){
		        		for (int i = 0; i < 12; i++) {
		        			kachack.apiClick();
						}
		        		
		        	}
		        	
		            System.out.println("Motion Detected!"); 		           
		        }   
		        if(event.getState().isLow()){   
		            System.out.println("All is quiet...");		           
		        }   
		    }       
		});         

		try {           
		    // keep program running until user aborts       
		    for (;;) {      
		        Thread.sleep(500);  
		    }       
		}           
		catch (final Exception e) {         
		    System.out.println(e.getMessage());     
		}

	}

}
