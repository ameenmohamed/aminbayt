package org.hac.dropbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hac.homesecurity.aminbayt.util.BaytConstants;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.Exposure;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;

public class ClickPic {
	 static String dtFormat = "yyyy-MM-dd-HH-mm-ss"; 
	 static String flExt = ".jpg";
	// Define the path to the raspistill executable.
	   public static final String _raspistillPath = "/home/pi/mmal/pic";
	 //  private static final String _raspistillPath = "/home/pi/ammotion/pic";
	   // Define the amount of time that the camera will use to take a photo.
	   public static final int _picTimeout = 7000;
	   // Define the image quality.
	   public static final int _picQuality = 100;

	   // Specify a default image width.
	   public static int _picWidth = 1024;
	   // Specify a default image height.
	   public static int _picHeight = 768;
	 
	 
	   // Specify a default image encoding.
	   private static String _picType = "jpg";

	 
	 public static void main(String[] args) throws ParseException {
	
		 if(args.length == 0){
			 System.out.println("running default option click pic");
			 ClickPic.apiClick();
		 }
		 else if(args.length > 0){
			 String commex = args[0];
			 if(commex.equalsIgnoreCase("pic")){
				 ClickPic.apiClick();
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
	 
	 
	 
	 public static String apiClick(){
		 String flNameLoc = "";
		 try {
			RPiCamera piCamera = new RPiCamera(_raspistillPath);
			piCamera.setWidth(500); 
			piCamera.setHeight(500);

			//Adjust Camera's brightness setting.
			piCamera.setBrightness(75);

			//Set Camera's exposure.
			piCamera.setExposure(Exposure.AUTO);

			//Set Camera's timeout.
			piCamera.setTimeout(2);
			piCamera.setDateTimeOn();
			piCamera.setFullPreviewOff();
			piCamera.setQuality(BaytConstants.RESIZE_VALUE);
			//Add Raw Bayer data to image files created by Camera.
			piCamera.setAddRawBayer(true);
			File flPic = piCamera.takeStill(getFileName());
			 Thread.sleep(_picTimeout);
			
			
	         if(flPic.exists()){
	        	 UploadFile.uploadToDropbox(flPic.getAbsolutePath());
	        	 flNameLoc = "File:"+flPic.getAbsolutePath()+flPic.getName() + "Uploaded.";
		         System.out.println("del :"+flPic.getAbsolutePath());
		         flPic.delete();
	         }else{
	        	 flNameLoc = "File:"+flPic.getAbsolutePath() + " Does not exist.";
	         }
	        
			 
		 } catch (FailedToRunRaspistillException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		 return flNameLoc;
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
	 
	 public String testCmd(){
		 List<String> command = new ArrayList<String>();
		 command.add("ls");
		 command.add("-l");
		 command.add("/var/tmp");
		 //ProcessBuilder 
		 return null;
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
