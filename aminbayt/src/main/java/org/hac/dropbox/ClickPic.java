package org.hac.dropbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ClickPic {
	 static String dtFormat = "yyyy-MM-dd-HH-mm-ss"; 
	 static String flExt = ".jpg";
	// Define the path to the raspistill executable.
	   private static final String _raspistillPath = "/home/pi/mmal/pic";
	 //  private static final String _raspistillPath = "/home/pi/ammotion/pic";
	   // Define the amount of time that the camera will use to take a photo.
	   private static final int _picTimeout = 7000;
	   // Define the image quality.
	   private static final int _picQuality = 100;

	   // Specify a default image width.
	   private static int _picWidth = 1024;
	   // Specify a default image height.
	   private static int _picHeight = 768;
	 
	 
	   // Specify a default image encoding.
	   private static String _picType = "jpg";

	 
	 public static void main(String[] args) throws ParseException {
	
		 if(args.length == 0){
			 System.out.println("running default option click pic");
			 ClickPic.click();
		 }
		 else if(args.length > 0){
			 String commex = args[0];
			 if(commex.equalsIgnoreCase("pic")){
				 ClickPic.click();
			 }
			 else if(commex.equalsIgnoreCase("vid")){
				 if(args.length > 1 && isNumeric(args[1])){
					 int duration = Integer.parseInt(args[1]);
					 RecordVideo rv = new RecordVideo(duration);
					 rv.recVid();
				 }else{
					 RecordVideo rv = new RecordVideo();
					 rv.recVid();
				 }
				 
			 }else{
				 System.out.println("Invalid Command:"+args[0]);
			 }
		 }
		 
		 
	}
	
	 public static String click(){
		String resp = "";
		 try{
		 StringBuilder sb = new StringBuilder("sudo raspistill");
		 String flName = getFileName();
         sb.append(" -n -o " + _raspistillPath+"/"+flName);
       //  String extjavaComm = "java -cp \"/home/pi/dropbox-client.jar\" org.hac.dropbox.ClickPic";
         
         
         // Invoke raspistill to take the photo.
         System.out.println("running .."+sb.toString());
        
         Process p = Runtime.getRuntime().exec(sb.toString());
         BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			 System.out.println("p:"+line);
			}
         // Pause to allow the camera time to take the photo.
         Thread.sleep(_picTimeout);
         UploadFile.uploadToDropbox(_raspistillPath+"/"+flName);
         File fl = new File(_raspistillPath+"/"+flName);
         if(fl.exists()){
	         System.out.println("del :"+fl.getAbsolutePath());
	         fl.delete();
         }
         resp = "File:"+flName + "Uploaded.";
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
        
      }
		 return resp;
	 }
	 
	public static String getFileName() throws ParseException{
		 Date now = new Date();
		 String flName = "";
	       
	        SimpleDateFormat sdf = new SimpleDateFormat(dtFormat);
	        flName = sdf.format(now);
			
			System.out.println(flName+flExt);
	        return flName+flExt;
	}
	
	 public static boolean isNumeric(String str)
	    {
	        for (char c : str.toCharArray())
	        {
	            if (!Character.isDigit(c)) return false;
	        }
	        return true;
	    }
	
	

}
