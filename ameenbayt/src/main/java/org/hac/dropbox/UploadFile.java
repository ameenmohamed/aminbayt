package org.hac.dropbox;
import com.dropbox.core.*;
import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v1.DbxWriteMode;
import com.dropbox.core.DbxException;

import java.io.*;
import java.nio.file.Path;
import java.util.Locale;

import org.hac.amin.bayt.model.BaytConfig;
import org.hac.amin.bayt.model.DropBoxConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadFile {
	
	@Autowired
	ImageResizer imgrsz;
	
	@Autowired
	BaytConfig baytConfig;
	
	@Autowired
	DropBoxConfig dbxCfg;
	
	public  void uploadToDropbox(String flLoc) {
		
	        File inputFile = new File(flLoc);
	      
	        if(!inputFile.exists()){
	        	for(int i=0;i<3;i++){
	        		try {	
	        			if(!inputFile.exists()){
	        			Thread.sleep(500);
	        			}else{break;}
					} catch (InterruptedException e) {e.printStackTrace();	}
	        	}
	        	System.out.println("File Does not exist :"+inputFile.getAbsolutePath());
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
	        		
	        		
	            DbxEntry.File uploadedFile = dbxCfg.client.uploadFile(baytConfig.getDbPicLocation()+File.separator+inputFile.getName(), DbxWriteMode.add(), inputStream.available(), inputStream);
	            System.out.println("Uploaded: " + uploadedFile.toString());
	        		}else {System.out.println("Could not setup dropbox client ..");}
	            inputStream.close();
	        } catch (DbxException e) {
				

				e.printStackTrace();
			} catch (IOException e) {
				

				e.printStackTrace();
			} 
	        
	}
	
}
