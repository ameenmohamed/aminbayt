package org.hac.arduino.comm.serial;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;



public class SerialRead {
	
	public static void main(String[] args) {
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2();
		Thread tobj1 =new Thread(t1 );  
		Thread tobj2 =new Thread(t2);  
	     tobj1.start();  
	     tobj2.start();  
		
	}
	

}
class Thread1 implements Runnable{
	
	@Override
	public void run() {
		while(true){
		System.out.println("Serial writer for ard ....");
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Reading input from console using Scanner in Java ");
        System.out.println("Please enter your input: ");
        String input = scanner.nextLine();
        if("quit".equalsIgnoreCase(input)){
        	System.exit(0);
        }
        System.out.println("Serial Write input:"+input);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
}

class Thread2 implements Runnable{
	@Override
	public void run() {
		
		while(true){
			System.out.println("Serial Resder ......");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
}