package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import org.junit.Test;

public class UpdateHostFile {
	
	final String HOST_FILE_PATH="C:/Windows/System32/drivers/etc/hosts";
	
	@Test
	public void updateHost(){
		File hostFile = new File(HOST_FILE_PATH);
	    FileWriter writer;
	    BufferedWriter bufferWriter=null;
	    String deployServer=System.getProperty("deployServer");
	    try {
	        writer = new FileWriter(hostFile, false);
	        bufferWriter = new BufferedWriter(writer);
	        bufferWriter.write(getStringToWrite(deployServer));
	        System.out.println("[Info]-->updated host file with text: "+getStringToWrite(deployServer));
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally{
	    	try {
				bufferWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	public String getStringToWrite(String deployServer){
		String retrunString="";
		switch(deployServer.toUpperCase()){
			case "BFR1":{
				retrunString="10.37.57.85 customer-dev.autodesk.com";
				break;
			}
			case "BFR2":{
				retrunString="10.37.57.86 customer-dev.autodesk.com";
				break;
			}
			case "BFR3":{
				retrunString="10.37.57.90 customer-dev.autodesk.com";
				break;
			}
			case "BFR4":{
				retrunString="10.37.57.91 customer-dev.autodesk.com";
				break;
			}
			case "BFR5":{
				retrunString="10.37.57.92 customer-dev.autodesk.com";
				break;
			}
		}
		return retrunString;
	}
	
}
