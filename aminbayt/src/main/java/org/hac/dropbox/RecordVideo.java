package org.hac.dropbox;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RecordVideo {
	
	
	private static String flExtRaw=".h264";
	int duration = 30;

	
	String vidComm = "raspivid -o %1.%2 -w %3d -h %4d -t %5d";
	
						//MP4Box -fps 30 -add myvid.h264 myvid.mp4
	String vidMp4Comm =  "MP4Box -fps 30 -add %1.h264 %2d.mp4";
	
	public RecordVideo(int _duration) {
		duration = _duration;
	}
	
	public RecordVideo() {
		
	}

	public  void recVid(){
		AsyncVideoRec vidrec = new AsyncVideoRec(this);
		Thread vidThread = new Thread(vidrec);
		vidThread.start();
	}
	
}

class AsyncVideoRec implements Runnable{
	private RecordVideo rv;
	
	private static String flExtRaw=".h264";

	int vidWidth = 1280;
	int vidHeight = 720;
					//raspivid -o myvid.h264 -w 1280 -h 720 -t 60000
	String vidComm = "raspivid -o %s%s -w %d -h %d -t %d";	
						//MP4Box -fps 30 -add myvid.h264 myvid.mp4
	String vidMp4Comm =  "MP4Box -fps 30 -add %s.h264 %s.mp4";
	public AsyncVideoRec(RecordVideo _rv) {
		rv= _rv;
	}
	@Override
	public void run() {
		String flNameLoc = getFileNameLoc();
		String vidCommand = String.format(vidComm,flNameLoc,flExtRaw, vidWidth, vidHeight,rv.duration);
		String mp4Command = String.format(vidMp4Comm, flNameLoc,flNameLoc);
		
         // Invoke raspistill to take the photo.
         System.out.println("running .."+vidCommand);
        
         try {
			Runtime.getRuntime().exec(vidCommand);
			System.out.println("Started recording ..." +vidCommand);
			
			//System.out.println("preparing for mp4 ..." + mp4Command);
			//Runtime.getRuntime().exec(mp4Command);
			//System.out.println("mp4 complete...");
			//UploadFile.uploadToDropbox(flNameLoc+".mp4");
			//System.out.println("Upload Complete:"+flNameLoc+".mp4");
	         File fl = new File(flNameLoc+flExtRaw);
	         System.out.println("Vid File :"+fl.getAbsolutePath());
	       //  fl.delete();
	       
		} catch (Exception e) {
			
			System.out.println(e.toString());
		}
		
	}
	
	public String getFileNameLoc(){
		
		return DropBoxConstants._raspiVidPath+ getFileName();
	}
	
	public  String getFileName(){
		 Date now = new Date();
		 	String flName = "";
	        try {
				flName = DropBoxConstants.sdf.format(now);
			} catch (Exception e) {
				flName =  UUID.randomUUID().toString();
			}
			return flName;
	}
	
	
}
