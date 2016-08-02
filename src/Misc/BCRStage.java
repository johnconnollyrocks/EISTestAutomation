package Misc;


import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public final class BCRStage{
	
	@Test
	public void changeHosts() throws IOException {
			
		PrintWriter writer = new PrintWriter("C:\\Windows\\System32\\drivers\\etc\\hosts");
		writer.print("");
		writer.close();
	    
		PrintWriter writer1 = new PrintWriter("C:\\Windows\\System32\\drivers\\etc\\hosts");
		writer1.print("54.198.244.185 customer-stg.autodesk.com");
		writer1.close();
		}
	                                      
}
  