package org.hac.amin.bayt.util;

import javax.annotation.PostConstruct;

import org.hac.amin.bayt.model.BaytConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

@Component
public class PublishMessage implements SendAlert {

	@Autowired
	BaytConfig baytConfig;
	
	@Autowired
	AWSClientCredentials credentials;
	
	AmazonSNSClient snsClient;
	
	
	@PostConstruct
	public void init(){
		 snsClient = new AmazonSNSClient(credentials.getCredentials());		                           
		snsClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
	}
	
	
	@Override
	public String sendAlert( String topicMsg) {
				
		PublishRequest publishRequest = new PublishRequest(baytConfig.getAwsTopicArn(), topicMsg);
		PublishResult publishResult = snsClient.publish(publishRequest);
		//print MessageId of message published to SNS topic
		System.out.println("MessageId - " + publishResult.getMessageId());
		return publishResult.getMessageId();
	}
	
	
	
	

}
