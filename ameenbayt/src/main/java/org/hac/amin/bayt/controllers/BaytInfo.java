package org.hac.amin.bayt.controllers;

import org.hac.amin.bayt.model.BaytConfig;
import org.hac.dropbox.ClickPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaytInfo {

	@Autowired
	BaytConfig baytConfig;
	
	@Autowired
	ClickPic sayCheese;
	
	//@RequestMapping(value="/myinfo", method = RequestMethod.GET)
	public BaytConfig getMyConfig(){
			
			return baytConfig;
	}
	
	@RequestMapping(value="/health", method = RequestMethod.GET)
	public String getGreeting(){
			
			return "I am Alive !!";
	}
	
	@RequestMapping(value="/activate", method = RequestMethod.GET)
	public String activate(){
		String status="OK:true";
		if(baytConfig!=null){
			if(baytConfig.isActivateSecurity()){
				status="AlreadyActive:true";
			}else{
				baytConfig.setActivateSecurity(true);
			}
		}
			
			return status;
	}
	
	@RequestMapping(value="/deactivate", method = RequestMethod.GET)
	public String deactivativate(){
		String status="OK:false";
		baytConfig.setActivateSecurity(false);
			
			return status;
	}
	
	
	@RequestMapping(value="/apiclick", method = RequestMethod.GET)
	public String apiClick(){
		String resp = sayCheese.apiClick();
		return resp;
	}
	
}
