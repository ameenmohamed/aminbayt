package org.hac.arduino.comm.serial.helper;

import org.hac.dropbox.ClickPic;

public class MotionAction {
	String fileName = "";
	
	
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public  String parseImageTask(String jsonStr){
		if(jsonStr == null){
			return "";
		}
		String filename = "";
		ClickMotionPic mpic = new ClickMotionPic(jsonStr, this);
		Thread mThread = new Thread(mpic);
		mThread.start();
		
		return filename;
	}

}

 class ClickMotionPic implements Runnable{

	String jsonStr = "";
	MotionAction mAction;
	public ClickMotionPic(String _jsonStr,MotionAction _mAction) {
		if(_jsonStr !=null){
		jsonStr = _jsonStr;
		}
		mAction = _mAction;
	}
	@Override
	public void run() {
		String filename = "";
		if(jsonStr.contains("\"Motion\":\"1\"")){ // 
			// click pic save to dropbox 
			 filename = ClickPic.click();
			try {Thread.sleep(5000);	} catch (InterruptedException e) {e.printStackTrace();
			filename +=" ,"+ ClickPic.click();
			try {Thread.sleep(5000);	} catch (InterruptedException e1) {e1.printStackTrace();
			filename +=" ,"+ ClickPic.click();
			}
			}
		}
		mAction.setFileName(filename);
	}
}
