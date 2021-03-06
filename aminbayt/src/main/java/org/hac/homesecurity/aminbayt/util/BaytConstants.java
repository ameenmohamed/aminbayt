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
 //    public final static int RESIZE_VALUE = 500;
     public final static int RESIZE_QUALITY = 1550;//75
     public final static int NIGHT_SHUTTER_SPEED = 250000;
     public final static int NIGHT_LIGHT_START = 1020;
     public final static int NIGHT_LIGHT_ONDELAY = 2000;
     public final static int NIGHT_ISO = 800;
     
     public final static String LIGHT_ONCMD = "ltons1";
	public static final String LIGHT_OFFCMD = "ltoffs1";
	public static final int PICWIDTH = 1550;
	public static final int PICHEIGHT = 1163;
     
     public static boolean ACTIVE_SECURITY = false;
  
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
