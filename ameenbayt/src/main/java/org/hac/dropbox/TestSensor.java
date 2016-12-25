package org.hac.dropbox;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hac.amin.bayt.model.BaytConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;


@Component
public class TestSensor {
	 private static final Logger logger = LogManager.getLogger(TestSensor.class);
	
	@Autowired
	ClickPic kachack;
	
	@Autowired
	BaytConfig baytConfig;
	
	static  GpioPinDigitalInput sensor;
	static  GpioController gpioSensor;
	
	@PostConstruct
	public void test(){
		
		if("test".equalsIgnoreCase(baytConfig.getSystemState())){
		 gpioSensor = GpioFactory.getInstance(); 
		 sensor = gpioSensor.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
		}
		MonitorSensor mssor = new MonitorSensor(kachack,baytConfig);
		Thread thread1 = new Thread(mssor, "MotionSensorThread");
        thread1.start();
		

		
	}
	
	

}
