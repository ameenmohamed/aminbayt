package org.hac.arduino.comm.serial.helper;

import org.hac.dropbox.ClickPic;

public class CaptureImageHelper {

		public static String captureImage(){
			 String filename = ClickPic.click();
			return filename;
		}
	
}
