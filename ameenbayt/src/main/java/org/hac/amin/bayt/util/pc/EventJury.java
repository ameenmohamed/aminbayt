package org.hac.amin.bayt.util.pc;

import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hac.amin.bayt.model.BaytConfig;
import org.hac.amin.bayt.model.CurrentState;
import org.hac.amin.bayt.model.EventDetails;
import org.hac.amin.bayt.util.Click;

import org.hac.amin.bayt.util.SendAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;




@Component
public class EventJury implements Runnable {

	private static final Logger logger = LogManager.getLogger(EventJury.class);
	
	@Autowired
	CurrentState currState;
	
	@Autowired
	BaytConfig baytConfig;
	
	@Autowired
	Click sayCheese;
	
	@Autowired
	SendAlert alert;
	
	private long startTime;
	private  ObjectMapper mapper = new ObjectMapper();
	boolean alertGap = false;
	
	int picCount =0;
	
	String mid ="";
	
	@Override
	public void run() {
		while(true){
			String feed = currState.getSerialFeed();
			if(!getMsgId(feed).equals(mid))
				picAction(feed);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}

	}
	
	public void picAction(String feed){
		if(null !=feed  && feed.indexOf("Motion:1") >0 && !alertGap && baytConfig.isActivateSecurity()){ //if alert is not raised(1st time) only then start timer		
			mid=getMsgId(feed);
			startTime = System.currentTimeMillis(); // current time
			
		//	for(int i=0;i<baytConfig.getBurstCount();i++){
				picCount++;
				System.out.println("pic Count"+picCount);
				sayCheese.apiClick();
				raiseAlertEvent(feed);				
			//  }			
		}
	}
	
	public boolean isWaitTimeElapsed() {
		   long endTime = System.currentTimeMillis();
		   long gap = endTime - startTime;
		   long minutes = TimeUnit.MILLISECONDS.toMinutes(gap);   
		   
		    if (  minutes >= 1) {
		    	startTime = endTime;
		    	alertGap= false;
		    	picCount = 0;
		    	System.out.println("Minutes passed "+minutes);
		    	return true;
		    } else {
		    	return false;
		    }
		  }
	
	private String getMsgId(String feed){
		if(null!= feed){
		int midPos = feed.lastIndexOf("mid");
		String _pmid = feed.substring((midPos+"mid".length()+1), feed.lastIndexOf(","));
	//	System.out.println("mid"+_pmid);
		return _pmid;
		}else
			return "";
	}
	
	public String raiseAlertEvent(String feed){
		if(!currState.isAlertRaised()){
			return "alertRaised";
		}	
		
		 Date now = new Date();
		 EventDetails eventDetail = new EventDetails();
		 
		 eventDetail.setTime(DateFormat.getInstance().format(now));
		 eventDetail.setFeedData(feed);
		 eventDetail.setRaspiID(baytConfig.getS3fileloc());
		 
		 
		 String jsonInString="";
		try {
			jsonInString = mapper.writeValueAsString(eventDetail);
		} catch (JsonProcessingException e) {
			
			logger.error(e.getMessage());
		}
		 
		String mId=  alert.sendAlert(jsonInString);
		logger.debug("Alert Event raised ....... msgID"+mId);
		currState.resetAlert();
		return mId;
	}

}
