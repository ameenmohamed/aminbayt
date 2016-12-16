package org.hac.amin.bayt.sns;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hac.amin.bayt.model.BaytConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
*/
@Component
public class SendMessage {
	
	/*@Autowired
	BaytConfig baytConfig;
	
	AmazonSNSClient snsClient ;
	
	@PostConstruct
	public void init(){
		 snsClient = new AmazonSNSClient();
		 String message = "My SMS message";
	        String phoneNumber = "+353868072514";
	        Map<String, MessageAttributeValue> smsAttributes =
			        new HashMap<String, MessageAttributeValue>();
			smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
			        .withStringValue("ameenbayt") //The sender ID shown on the device.
			        .withDataType("String"));
			smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
			        .withStringValue("0.50") //Sets the max price to 0.50 USD.
			        .withDataType("Number"));
			smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
			        .withStringValue("Promotional") //Sets the type to promotional.
			        .withDataType("String"));
	        //<set SMS attributes>
	        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
	}
	
	
	
	
	public  void sendSMSMessage(AmazonSNSClient snsClient, String message, 
			String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
	        PublishResult result = snsClient.publish(new PublishRequest()
	                        .withMessage(message)
	                        .withPhoneNumber(phoneNumber)
	                        .withMessageAttributes(smsAttributes));
	    //    System.out.println(result); // Prints the message ID.
	}*/

}
