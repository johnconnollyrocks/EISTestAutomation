package common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HtmlReport {
	private String fileName=null;
	public String reportFilePath;
	public String browserType=null;
	public String environment=null;
	public String testName=null;
	public int stepCount=1;
	public String reportFolderPath="C:/SeleniumTestResults";
	public String oldTextToPrint=null;
	public String oldDataToPrint=null;
	
	public HtmlReport(String browserType, String env, String testName) {
		fileName=browserType+".html";
		reportFilePath=reportFolderPath+"/"+fileName;
		environment=env;
		this.browserType=browserType;
		this.testName=testName;
		deleteOldFiles();
		createHtmlOutLine();
	}

	public void reportStep(String textToPrint, String dataToPrint) {
		
		if(!(oldDataToPrint==dataToPrint) && !(oldTextToPrint==textToPrint)){
			try {
				if(dataToPrint.isEmpty()){
					dataToPrint="&nbsp;";
				}
				File reportFile=new File(reportFilePath);
				FileWriter fw = new FileWriter(reportFile, true);
				 PrintWriter file = new PrintWriter(fw);
				 file.println("<tr>");
				 file.println("<td width='40%'><font size=2>"+stepCount+". "+textToPrint+"</font></td>");
			     file.println("<td width='25%'><font size=2>"+dataToPrint+"</font></td>");
			     file.println("<td width='35'>&nbsp;</td>");
			     file.println("</tr>");
				 file.close();
				 stepCount=stepCount+1;
				 oldDataToPrint=dataToPrint;
				 oldTextToPrint=textToPrint;
		    } catch (IOException e) {
		        System.err.println("Problem creating report file");
		    }
		}
	}
	public void reportValidation(String validationText,String status) {
		try {
			
			File reportFile=new File(reportFilePath);
			FileWriter fw = new FileWriter(reportFile, true);
			 PrintWriter file = new PrintWriter(fw);
			 file.println("<tr>");
			 file.println("<td width='40%'><font size=2>Validation</font></td>");
		     file.println("<td width='25%'><font size=2>&nbsp;</font></td>");
		     if (status.equalsIgnoreCase("PASSED")){
		    	 file.println("<td width='35'><font color='green' size=2>"+validationText+"</td>");
		     }else if(status.equalsIgnoreCase("FAILED")){
		    	 file.println("<td width='35'><font color='red' size=2>"+validationText+"</td>");
		     }
		     file.println("</tr>");
			 file.close();
			 stepCount=stepCount+1;
	    } catch (IOException e) {
	        System.err.println("Problem creating report file");
	    }
	}
	public void closeHtmlReport(){
		try {
			
			File reportFile=new File(reportFilePath);
			FileWriter fw = new FileWriter(reportFile, true);
			 PrintWriter file = new PrintWriter(fw);
			 file.println("</table>");
		     file.println("</td>");
		     file.println("</tr>");
		     file.println("</table>");
			 file.close();
	    } catch (IOException e) {
	        System.err.println("Problem creating report file");
	    }
	}
	public void reportTestStatus(boolean status){
		try {
			
			File reportFile=new File(reportFilePath);
			FileWriter fw = new FileWriter(reportFile, true);
			PrintWriter file = new PrintWriter(fw);
			 file.println("</table>");
		     file.println("</td>");
		     file.println("</tr>");
		     file.println("<p>");
		     if (status){
		    	 file.println("<br><b>Script status: <font size=3 color='green'>Passed</font></b>"); 
		     }else{
		    	 file.println("<br><b>Script status: <font size=3 color='red'>Failed</font></b>");
		     }
		     file.println("</p>");
		     file.println("</table>");
			 file.close();
	    } catch (IOException e) {
	        System.err.println("Problem creating report file");
	    }
	}
	
	public void createHtmlOutLine() {
		File reportFile=new File(reportFilePath);
		FileWriter fw;
		String strEC ="1";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date(); 
		String now=dateFormat.format(date);
		try {
			fw = new FileWriter(reportFile, true);
		
		 PrintWriter file = new PrintWriter(fw);
		 //Starting body of the html
		 file.println("<html>");
		 file.println("<body>");
		 file.println("<script>");
		 file.println("var toggledDisplay = new Object();");
		 for (int i=0;i<30;i++)
		 {		 
			 file.println("toggledDisplay['" + i + "'] = true;");
			 strEC= strEC + "'" + i + "',";
		 }
		 strEC=strEC.substring(1, strEC.length()-1);
			 file.println("function toggleDisplay(bDisplayed)");
			 file.println("{");
			 file.println("    if(!document.getElementById || toggleDisplay.arguments.length < 2) return;");
			 file.println("    var displayed = new Object();");
			 file.println("    displayed['true'] = 'block';");
			 file.println("    displayed['false'] = 'none';");
			 file.println("    for(var i = 1; i < toggleDisplay.arguments.length; i++)");
			 file.println("    {");
		     file.println("        oDisplay = document.getElementById(toggleDisplay.arguments[i]);");
		     file.println("        if(oDisplay)");
		     file.println("        {");
		     file.println("            oDisplay.style.display = displayed[bDisplayed];");
		     file.println("            if(bDisplayed);");
		     file.println("            {");
		     file.println("                oImages = oDisplay.getElementsByTagName('IMG');;");
		     file.println("                for(var j = 0; j < oImages.length; j++);");
		     file.println("                oImages[j].src = oImages[j].src;");
		     file.println("            }");
		     file.println("            if(typeof toggledDisplay[toggleDisplay.arguments[i]] != 'undefined');");
		     file.println("            toggledDisplay[toggleDisplay.arguments[i]] = !bDisplayed;");
		     file.println("        }");
		     file.println("    }");
		     file.println("}");
		     file.println("</script>");
//	     'WriteLine the header table
	        file.println("<table cols=3 border='1.5' bordercolor='black' rules='all'>");
	        file.println("<tr>");
	        file.println("<td width='20%' bgcolor='black'><font  color='white' size=3><b>Date</b></td>");
	        file.println("<td width='20%' bgcolor='black'><font  color='white' size=3><b>Environment</b></td>");
	        file.println("<td width='40%' bgcolor='black'><font  color='white' size=3><b>Script Name</b></td>");
	        file.println("<td width='20%' bgcolor='black'><font  color='white' size=3><b>Browser</b></td>");
	        file.println("</tr>");
	    
//	        'General Information
	        file.println("<tr>");
	        file.println("<td><b>" + now + "</b></td>");
	        file.println("<td><font  color='black' size=2><b>" + environment + "</b></font></td>");
	        file.println("<td><font  color='black' size=2><b>" + testName + "</b></font></td>");
	        file.println("<td><font  color='black' size=2><b>" + browserType + "</b></font></td>");
	        file.println("</tr>");
	        file.println("</table>");
	        file.println("<br>");
	        file.println("<a href=\"JavaScript://\" onclick=\"toggleDisplay(true," + strEC + ");\">Expand All</a> | <a href=\"JavaScript://\" onclick=\"toggleDisplay(false, " + strEC + ");\">Collapse All</a>");
	    
//	        ' To Insert the Header Structure for the Report
	        file.println("<table cols=3 border='1' bordercolor='black' rules='all'>");
	        file.println("<tr>");
	        file.println("<td width='40%' bgcolor='#444444'><font  color='white' size=2><b>Description</b></font></td>");
	        file.println("<td width='25%' bgcolor='#444444'><font  color='white' size=2><b>Test Data</b></font></td>");
	        file.println("<td width='35%' bgcolor='#444444'><font  color='white' size=2><b>Validations</b></font></td>");
	        file.println("</tr>");
	        file.println("<tr><td colspan=3><font  size=2><center><b><a style=\"text-decoration:none\" href=\" JavaScript://\" onclick=\"toggleDisplay(toggledDisplay['1'],'1')\" >"+  browserType + "</a><b></center></font></td></tr>");
	        file.println("<tr id='1'>");
	        file.println("<td colspan=3>");
	        file.println("<table cols=3 width='100%' border='1' bordercolor='black' rules='all'>");
	        file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteOldFiles(){
		File reportFile=new File(reportFilePath);
		File folderPath=new File(reportFolderPath);
		if(!folderPath.exists()){
			folderPath.mkdir();
		}
		if(reportFile.exists()){
			reportFile.delete();
		}
	}
}
