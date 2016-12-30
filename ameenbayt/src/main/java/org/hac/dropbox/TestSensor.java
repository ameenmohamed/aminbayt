package org.hac.dropbox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
	
	public final static GpioController gpioSensor = GpioFactory.getInstance(); 
	public final static  GpioPinDigitalInput sensor = gpioSensor.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
	
	
	@PostConstruct
	public void test(){
	
		MonitorSensor mssor = new MonitorSensor(kachack,baytConfig);
		Thread thread1 = new Thread(mssor, "MotionSensorThread");
        thread1.start();
	}

}
