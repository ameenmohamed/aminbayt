package org.hac.amin.bayt.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public @Data class CurrentState {
	
	private String serialFeed;
	
	
	
	private boolean alertRaised = false;
	
	
	public boolean shouldSendAlert(){
		if(!alertRaised){ 
			return true;
		}else{
			return false;
		}
	}

	public void resetAlert(){
		
		alertRaised=alertRaised==true?false:true;
	}
	
	
}
