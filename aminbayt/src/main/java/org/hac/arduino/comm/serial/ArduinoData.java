package org.hac.arduino.comm.serial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

public @Data class ArduinoData implements Serializable {
	
	String mId ="M190316-1";
	//SR,MR,KT,LVR,
	String locId = "SR";
//	List<SensorData> sensorData= new ArrayList<SensorData>();
	Map<String,String> mapSensors = new HashMap<String,String>();
	
	
	String aMsg = "";
	
	

}
