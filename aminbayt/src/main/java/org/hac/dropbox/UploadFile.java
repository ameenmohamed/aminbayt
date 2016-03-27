package org.hac.dropbox;
import com.dropbox.core.*;
import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v1.DbxWriteMode;
import com.dropbox.core.DbxException;

import java.io.*;
import java.nio.file.Path;
import java.util.Locale;

import org.hac.homesecurity.aminbayt.util.BaytConstants;

public class UploadFile {
	public static void main(String[] args) {
		uploadToDropbox("/home/amin/amroot/pics/cam1.jpg");
	
	}
	
	public static void uploadToDropbox(String flLoc) {
		
	        File inputFile = new File(flLoc);
	        if(!inputFile.exists()){
	        	for(int i=0;i<3;i++){
	        		try {	
	        			if(!inputFile.exists()){
	        			Thread.sleep(1000);
	        			}else{break;}
					} catch (InterruptedException e) {e.printStackTrace();	}
	        	}
	        	System.out.println("File Does not exist :"+inputFile.getAbsolutePath());
	        	return;
	        }
	        try {
	       //  inputStream = new FileInputStream(inputFile);
	        	InputStream inputStream =null;
	        	if(ImageResizer.isImage(inputFile)){
	        	 inputStream = ImageResizer.createResizedCopy(flLoc, BaytConstants.RESIZE_VALUE);
	        	}else if(ImageResizer.isMp4(inputFile)){
	        		inputStream = new FileInputStream(inputFile);
	        	}
	        	if(inputStream !=null){
	        		if(BaytConstants.client ==null){BaytConstants.setupDropBox();}
	        		if( BaytConstants.client !=null){
	            DbxEntry.File uploadedFile = BaytConstants.client.uploadFile(BaytConstants.DROPBOX_PIC_LOCATION+File.separator+inputFile.getName(), DbxWriteMode.add(), inputStream.available(), inputStream);
	            System.out.println("Uploaded: " + uploadedFile.toString());
	        		}else {System.out.println("Could not setup dropbox client ..");}
	            inputStream.close();
	        	}else{
	        		System.out.println("Dropbox upload failed ...");
	        	}
	        } catch (DbxException e) {
				

				e.printStackTrace();
			} catch (IOException e) {
				

				e.printStackTrace();
			} 
	        
	}
	
}
