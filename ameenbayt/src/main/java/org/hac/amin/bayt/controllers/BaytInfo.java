package org.hac.amin.bayt.controllers;

import org.hac.amin.bayt.model.BaytConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaytInfo {

	@Autowired
	BaytConfig baytConfig;
	
	@RequestMapping(value="/myinfo", method = RequestMethod.GET)
	public BaytConfig getMyConfig(){
			
			return baytConfig;
	}
	
}
