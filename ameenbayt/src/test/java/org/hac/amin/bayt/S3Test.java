package org.hac.amin.bayt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.StorageClass;

public class S3Test {

	
	/*public static void main(String[] args) throws FileNotFoundException {
		
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJEIW36SNMMBZHA4A", "hbAPkR8SrrsbjYuV7da7mTKfJX3NoHaByhjKUErS");
		AmazonS3 s3client = new AmazonS3Client(credentials); 
		String flName = "TestFile.jpg";
		File fl = new File("E:/root/amdata/gausiaacademy/logo.jpg");
		FileInputStream fio = new FileInputStream(fl);
		String versionId = "";
		int flLen = 0;
		ObjectMetadata objmeta = new ObjectMetadata();
		try {
			objmeta.setContentLength(fio.available());
			System.out.println("**** >>> s3root: {ameenbayt}  file: {/tvroom/"+flName);
			PutObjectRequest putObj = new PutObjectRequest("ameenbayt","tvroom/"+flName,fio,objmeta)
					.withStorageClass(StorageClass.ReducedRedundancy);
			flLen = fio.available();
			PutObjectResult result = s3client.putObject(putObj);
			 versionId = result.getVersionId();
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}		
		
	}*/
	
}
