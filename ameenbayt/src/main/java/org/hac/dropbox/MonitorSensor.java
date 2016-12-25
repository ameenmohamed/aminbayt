package org.hac.dropbox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hac.amin.bayt.model.BaytConfig;

import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class MonitorSensor implements Runnable {
	
	 private static final Logger logger = LogManager.getLogger(MonitorSensor.class);
	 SimpleDateFormat format ;


	ClickPic kachack;

	BaytConfig baytConfig;
	
	long  timeNow = System.currentTimeMillis();
	
	public MonitorSensor(ClickPic _kachack,BaytConfig _baytConfig) {
		kachack=_kachack;
		baytConfig=_baytConfig;
		format = new SimpleDateFormat(baytConfig.getTimeFormat());
	}
	


	@Override
	public void run() {
		logger.debug("<--Pi4J--> GPIO Listen Example ... started.");
		logger.debug("ispulldown "+TestSensor.sensor.isPullResistance(PinPullResistance.PULL_DOWN));
		
		// create and register gpio pin listener            
		TestSensor.sensor.addListener(new GpioPinListenerDigital() {           
		    @Override       
		    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {        
		    	long justNow = System.currentTimeMillis();
		    	boolean isPauseTimeElapsed = isPauseTimeElapsed(justNow);
		        if(event.getState().isHigh() && isPauseTimeElapsed){  
		        	 logger.debug("Motion Detected! time:"+format.format(new Date()));
		        	if(baytConfig.isActivateSecurity()){//if security is active click pics as long as motion is detected
		        		for (int i = 0; i < 12; i++) {
		        			if(!"test".equalsIgnoreCase(baytConfig.getSystemState())){ // clicl pics if not in test mode
		        			kachack.apiClick();}
		        			else{
		        				logger.debug("test mode click count:"+i +" time:"+format.format(new Date()));
		        			}		        		
						}		        		
		        	}		        	
		            		           
		        }   
		        else if(event.getState().isLow()){   
		        	//logger.debug("All is quiet... isPauseTimeElapsed:"+isPauseTimeElapsed);		           
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
			logger.error(e.getMessage());     
		}

	}
	
	public boolean isPauseTimeElapsed(long t2){
		long tmDiff = t2-timeNow;
		long diff = TimeUnit.MILLISECONDS.toMinutes(tmDiff);
		if(diff >= baytConfig.getMotionPauseTime()){ 
			timeNow=System.currentTimeMillis();//reset time at elapsed time
			return true;
		}else return false;
	}

}
