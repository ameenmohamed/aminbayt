package org.hac.dropbox;

import javax.annotation.PostConstruct;

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
	
	@Autowired
	ClickPic kachack;
	
	@Autowired
	BaytConfig baytConfig;
	
	public final static GpioController gpioSensor = GpioFactory.getInstance(); 
	public final static GpioPinDigitalInput sensor = gpioSensor.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
	
	@PostConstruct
	public void test(){
		MonitorSensor mssor = new MonitorSensor(kachack,baytConfig);
		Thread thread1 = new Thread(mssor, "MotionSensorThread");
        thread1.start();

		
	}
	
	

}
