package org.hac.amin.bayt.model;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.v1.DbxClientV1;

@Component
public class DropBoxConfig {
	
	@Autowired
	BaytConfig baytConfig;
	
	   public static DbxClientV1 client = null;
	     
	 //  @PostConstruct wll never be instantiated as v1 api is dicontinued using s3 instead 
	     public  void setupDropBox(){
	    	 if(client == null){
	    	 DbxAppInfo appInfo = new DbxAppInfo(baytConfig.getAppKey(), baytConfig.getAppSecret());

	         DbxRequestConfig config = new DbxRequestConfig("AMINBAYT/1.0", Locale.getDefault().toString());
	         DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
	          client = new DbxClientV1(config, baytConfig.getAccessToken());
	          System.out.println("Dropbox Client initialized");
	    	 }else { System.out.println("Dropbox Client Already initialized");}
	    	 
	     }

}
