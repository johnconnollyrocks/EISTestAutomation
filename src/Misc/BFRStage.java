package Misc;


import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public final class BFRStage{
	
	@Test
	public void changeHosts() throws IOException {
		PrintWriter writer = new PrintWriter("C:\\Windows\\System32\\drivers\\etc\\hosts");
		writer.print("");
		writer.close();	
		
		}
	                                      
}
  