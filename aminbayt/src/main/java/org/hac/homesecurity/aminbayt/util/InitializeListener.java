package org.hac.homesecurity.aminbayt.util;

import java.util.Locale;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hac.arduino.comm.serial.StartTalking;
import org.hac.homesecurity.aminbayt.controller.BaytWebSocket;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.v1.DbxClientV1;

@WebListener
public class InitializeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("load on Startup ..");
        BaytWebSocket.talkArd = new StartTalking();
        BaytWebSocket.talkArd.initialize();
        System.out.println("Wait to Initalize Sensors ....");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println("Initalize Sensors .... Done");
        BaytWebSocket.talkArd.readSerialData();
		/*new ReadSerialThread(data).run();
		new WriteSerialThread(outputStream).run();*/
        
        BaytConstants.setupDropBox();

        
		
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("On shutdown web app");
    }

}