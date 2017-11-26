
package org.hac.amin.bayt.util.pi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hac.amin.bayt.model.BaytConfig;
import org.hac.amin.bayt.util.Click;
import org.hac.dropbox.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.Exposure;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;

@Component
@Profile("!local")
public class ClickPic implements Click{

	private static final String flExt = ".jpg";
	private static final Logger logger = LogManager.getLogger(ClickPic.class);

	@Autowired
	BaytConfig baytConfig;

	@Autowired
	UploadFile flupload;

	/*
	 * public static void main(String[] args) throws ParseException { ClickPic
	 * cpic = new ClickPic(); if(args.length == 0){
	 * System.out.println("running default option click pic"); cpic.apiClick();
	 * } else if(args.length > 0){ String commex = args[0];
	 * if(commex.equalsIgnoreCase("pic")){ cpic.apiClick(); } else
	 * if(commex.equalsIgnoreCase("vid")){ if(args.length > 1 &&
	 * cpic.isNumeric(args[1])){ int duration = Integer.parseInt(args[1]);
	 * RecordVideo rv = new RecordVideo(duration); rv.recVid(); }else{
	 * RecordVideo rv = new RecordVideo(); rv.recVid(); } }else{
	 * System.out.println("Invalid Command:"+args[0]); } } }
	 */

	public String apiClick() {
		String flNameLoc = "";
		try {
			RPiCamera piCamera = new RPiCamera(baytConfig.getRaspistillPath());
			piCamera.setWidth(Integer.parseInt(baytConfig.getImgWidth()));
			piCamera.setHeight(Integer.parseInt(baytConfig.getImgHeight()));


			piCamera.setTimeout(2);
			piCamera.setDateTimeOn();
			piCamera.setFullPreviewOff();
			piCamera.setQuality(baytConfig.getImgClickQuality());
			// Add Raw Bayer data to image files created by Camera.

			int ltVal = 0;
			boolean lton = false;
			piCamera.setExposure(Exposure.AUTO);
				logger.debug("Camera Set to Auto mode");
			piCamera.setAddRawBayer(true);
			String _flName = getFileName();
			File flPic = piCamera.takeStill(getFileName());
			//Thread.sleep(baytConfig.getPicTimeout());

			if (flPic.exists()) {
				flupload.uploadToDropbox(flPic.getAbsolutePath());
				flNameLoc = "File:" + flPic.getAbsolutePath() + _flName + "Uploaded.";
				flPic.delete();

			} else {
				flNameLoc = "File:" + flPic.getAbsolutePath() + " Does not exist.";
				logger.info(flNameLoc);				
			}

		} catch (FailedToRunRaspistillException e) {

			logger.debug("FailedToRunRaspistillException"+e.getMessage());				
		} catch (IOException e) {

			logger.debug("IOException"+e.getMessage());
		} catch (InterruptedException e) {

			logger.debug("InterruptedException"+e.getMessage());
		} catch (ParseException e) {

			logger.debug("ParseException"+e.getMessage());
		}
		return flNameLoc;
	}

	public String click() {
		String stillpath = baytConfig.getRaspistillPath();
		String resp = "";
		try {
			StringBuilder sb = new StringBuilder("sudo raspistill");
			String flName = getFileName();
			sb.append(" -n -o " + stillpath + "/" + flName);
			// String extjavaComm = "java -cp \"/home/pi/dropbox-client.jar\"
			// org.hac.dropbox.ClickPic";

			// Invoke raspistill to take the photo.
			System.out.println("running .." + sb.toString());

			Process p = Runtime.getRuntime().exec(sb.toString());
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println("p:" + line);
			}
			// Pause to allow the camera time to take the photo.
			Thread.sleep(baytConfig.getPicTimeout());
			flupload.uploadToDropbox(stillpath + "/" + flName);
			File fl = new File(stillpath + "/" + flName);
			if (fl.exists()) {
				System.out.println("del :" + fl.getAbsolutePath());
				fl.delete();
			}
			resp = "File:" + flName + "Uploaded.";
		} catch (Exception e) {
			System.out.println(e.toString());

		}
		return resp;
	}

	public String testCmd() {
		List<String> command = new ArrayList<String>();
		command.add("ls");
		command.add("-l");
		command.add("/var/tmp");
		// ProcessBuilder
		return null;
	}

	private String getFileName() throws ParseException {
		Date now = new Date();
		String flName = "";

		SimpleDateFormat sdf = new SimpleDateFormat(baytConfig.getDateFormat());
		flName = sdf.format(now);

		System.out.println(flName + flExt);

		return flName + flExt;
	}

	private boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}
	
	private String getCurrentHour(){
		Date date = new Date();
		String strDateFormat = "HH";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		return sdf.format(date);
		
	}

	private boolean isLowLight(String hr) {
		int timeValue =0;
		if(hr==null){
			timeValue = Integer.parseInt(getCurrentHour());
		}else{
		timeValue = Integer.parseInt(hr);
		}
		if (  timeValue > 20 || timeValue < 04 ) {//12-14
			return true;
		} else {
			return false;
		}

	}

}
