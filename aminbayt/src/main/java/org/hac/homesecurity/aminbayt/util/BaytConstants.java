package org.hac.homesecurity.aminbayt.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.v1.DbxClientV1;

public final class BaytConstants {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
	 final static String APP_KEY = "qtpldsehgstmb4a";
     final static String APP_SECRET = "qtpldsehgstmb4a";
     static String ACCESS_TOKEN = "0oMJPCilPk8AAAAAAAAEPvCJEBOLgj1TeW92uxptYYKGQmAUsZVpBkKrbuJxstiv";
     public static String DROPBOX_PIC_LOCATION = "/pi/shafipi";
     public static String DROPBOX_VID_LOCATION = "/pi/shafipi/video";
     public final static int RESIZE_VALUE = 1550;
     
  
    public static DbxClientV1 client = null;
     
     public static void setupDropBox(){
    	 if(client == null){
    	 DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

         DbxRequestConfig config = new DbxRequestConfig("AMINBAYT/1.0", Locale.getDefault().toString());
         DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
          client = new DbxClientV1(config, ACCESS_TOKEN);
          System.out.println("Dropbox Client initialized");
    	 }else { System.out.println("Dropbox Client Already initialized");}
    	 
     }
}
