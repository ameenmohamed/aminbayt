package org.hac.amin.bayt.util;

import javax.annotation.PostConstruct;

import org.hac.amin.bayt.model.BaytConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

import lombok.Data;


@Component
public @Data class AWSClientCredentials {
	
	private AWSCredentials credentials;
	
	@Autowired
	BaytConfig baytConfig;
	
	@PostConstruct
	public void init(){
	
		credentials = new BasicAWSCredentials(baytConfig.getAwsakId(), baytConfig.getAwsseckey());
	}
	
	

}
