package org.hac.dropbox;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hac.amin.bayt.model.BaytConfig;
import org.hac.amin.bayt.model.S3WriteComponent;
import org.hac.amin.bayt.model.WriteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dropbox.core.DbxException;

@Component
public class UploadFile {
	
	 private static final Logger logger = LogManager.getLogger(UploadFile.class);
	
	@Autowired
	ImageResizer imgrsz;
	
	@Autowired
	BaytConfig baytConfig;
	
	/*@Autowired
	DropBoxConfig dbxCfg;*/
	
	@Autowired
	S3WriteComponent s3Comp;
	
	public  void uploadToDropbox(String flLoc) {
		
	        File inputFile = new File(flLoc);
	      
	        if(!inputFile.exists()){
	        	for(int i=0;i<3;i++){
	        		try {	
	        			if(!inputFile.exists()){
	        			Thread.sleep(50);
	        			}else{break;}
					} catch (InterruptedException e) {logger.error("IOException"+e.getMessage());}
	        	}
	        	logger.debug("Upload File in req Does not exist :"+inputFile.getAbsolutePath());
	        	return;
	        }
	        try {
	       //  inputStream = new FileInputStream(inputFile);
	        	// file is resized in memory and written to drop box directly from inputstream 
	        	InputStream inputStream =null;
	        	double bytes = inputFile.length();
	        	double kilobytes = (bytes / 1024);
	        	
	        	if(kilobytes > 300){
		        	if(imgrsz.isImage(inputFile)){
		        	 inputStream = imgrsz.createResizedCopy(flLoc, Integer.parseInt(baytConfig.getImgResizeValue()));
		        	}else if(imgrsz.isMp4(inputFile)){
		        		inputStream = new FileInputStream(inputFile);
		        	}
	        	}else {
	        		inputStream = new FileInputStream(inputFile);
	        	}
	        	if(inputStream !=null){
	        		
	        		WriteRequest writeReq = new WriteRequest(inputFile.getName(),inputStream);
	        		
	        		String fileName = s3Comp.write(writeReq);
	        		
	        //    DbxEntry.File uploadedFile = dbxCfg.client.uploadFile(baytConfig.getDbPicLocation()+File.separator+inputFile.getName(), DbxWriteMode.add(), inputStream.available(), inputStream);
	            logger.info("Uploaded: " + fileName);
	        		}else {logger.debug("Could not setup s3 client ..");}
	            inputStream.close();
	        }  catch (IOException e) {
				logger.error("IOException"+e.getMessage());
			} 
	        
	}
	
}
