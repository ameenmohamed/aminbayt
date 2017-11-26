package org.hac.amin.bayt.util.pc;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Component;

import jssc.SerialPort;

@Component
public class SerialWriter implements Runnable {

	SerialPort out;
      
      public void setOStream ( SerialPort sPort )
      {
          this.out =  sPort;
      }
      
      public void run ()
      {
          try
          {                
              int c = 0;
                         
          }
          catch ( Exception e )
          {
              e.printStackTrace();
          }            
      }

}
