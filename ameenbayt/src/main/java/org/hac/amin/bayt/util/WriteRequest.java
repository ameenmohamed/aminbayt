package org.hac.amin.bayt.util;


import java.io.InputStream;

import lombok.Data;

public @Data class WriteRequest {
	
	
	private InputStream fileio;
	
	private String fileName;
	
	public WriteRequest(String name, InputStream inputStream) {
		fileName = name;
		fileio = inputStream;
	}

	public String getWindowsSafeName(){
		int pos = fileName.lastIndexOf(".jpg");
		
		return fileName.substring(0,pos).replaceAll("[^a-zA-Z0-9]", "")+".jpg";
	}

	
	
}
