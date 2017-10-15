package org.hac.amin.bayt.model;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.StorageClass;



@Component
public class S3WriteComponent {
	private static final Logger logger = LogManager.getLogger(S3WriteComponent.class);
	
	
	//AWSCredentials credentials = new ProfileCredentialsProvider("default").getCredentials();
	
	AWSCredentials credentials = new BasicAWSCredentials("AKIAIJCXWPZFCYBPSYWQ", "/MPLk5x4JrtRQUkzNE0EgB6bF8NxHfCvHUDqtVyS");
	AmazonS3 s3client = new AmazonS3Client(credentials); 
//	AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
	
	@Autowired
	BaytConfig baytConfig;
	
	private static final String SUFFIX = "/";
	
	public S3WriteComponent() {
		

		// s3 = S3Client.builder().credentialsProvider(new ProfileCredentialsProvider()).region(region).build();
		 logger.info("s3 Instantiated ****************************************");
	}
	
	
	public String write(WriteRequest writeReq){
		
		String flName = writeReq.getWindowsSafeName();
		String versionId = "";
		int flLen = 0;
		ObjectMetadata objmeta = new ObjectMetadata();
		try {
			objmeta.setContentLength(writeReq.getFileio().available());
			logger.info("**** >>> s3root: {}  file: {} ",baytConfig.getS3root().trim(),baytConfig.getS3fileloc().trim()+SUFFIX+flName.trim());
			PutObjectRequest putObj = new PutObjectRequest(baytConfig.getS3root().trim(),baytConfig.getS3fileloc().trim()+SUFFIX+flName.trim(),writeReq.getFileio(),objmeta)
					.withStorageClass(StorageClass.ReducedRedundancy);
			flLen = writeReq.getFileio().available();
			PutObjectResult result = s3client.putObject(putObj);
			 versionId = result.getVersionId();
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		//  
		//.builder().bucket(baytConfig.getS3root()).key(baytConfig.getS3fileloc()+flName).storageClass(StorageClass.REDUCED_REDUNDANCY).build();  
		
		
	
		logger.info("**** >>> responseFl {} size {} put to s3 version {} ",baytConfig.getS3root()+baytConfig.getS3fileloc()+SUFFIX+flName,flLen,versionId);
		
		return flName;
	}

}
