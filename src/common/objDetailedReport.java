package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.adbs.utils.Base64;
/**
 * Description: Used to generate a detailed report. This report will be useful for Business team to understand the status of each test
 * @author t_marus(Sai)
 *
 */

public class objDetailedReport {

	/** The result HTML file name. */
	private static String resultFileName;

	/** The script start time and date. */
	private static String scriptStartTimeAndDate;	

	/** holds the name of test case ID. */
	private static String testCaseId =null;

	/** holds browser details. */
	private static String browser=null;

	/** holds the report folder path*/
	private  static String reportFolderPath = Util.getTestRootDir()+File.separator+"test-reports";
	

	/** holds the result file path. */
	private static String reportPath = null;
			
	/** Holds the screen shot folder path */
	private static String resultScreenShotPath = null;

	/** The file writer variable for detailed report. */
	private static FileWriter fileWriter = null;

	/** The buffered writer variable for detailed report. */
	private static BufferedWriter bufferedWriter = null ;

	/** holds the step number. */
	private static int stepNo;

	/** Holds the screen shot counter. */
	private static int screenShotCount;
	
	private static WebDriver driver=null;
	
	public enum Status {
		PASS,
		FAIL,
		DIDNOTRUN,
		WARNING;
			
	}
	
	//Creating a static block once. Due to the constraint of EISTestBase asserts static methods, got to define the objDetailedReport in static manner.
	
	static{
		driver=EISTestBase.driver;
		browser=EISTestBase.getAppBrowser();
		testCaseId=EISTestBase.getCallerClassNameWithOutPackage();
		//Remove the folder name as well.
		testCaseId=testCaseId.substring(testCaseId.lastIndexOf(".")+1,testCaseId.length());
	}

	public static void createHeaders() throws IOException  {	 

			String scriptStartTimeDR = null;
			String scriptDate=null;
			File checkFolderExist;
			File file;

			scriptStartTimeDR= DateTimeUtils.timeNow().toString(); 
			scriptDate= DateTimeUtils.dateNow().toString(); 
			scriptStartTimeAndDate = scriptDate + " " + scriptStartTimeDR;
			checkFolderExist = new File(reportFolderPath);
			if (!checkFolderExist.exists()) {
				checkFolderExist.mkdir();
			}
			reportFolderPath = reportFolderPath + File.separatorChar ;
			reportPath = testCaseId+".html";
			resultFileName =  reportPath;
			reportPath = reportFolderPath + reportPath;
			resultScreenShotPath = reportFolderPath + "Screenshots";
			file = new File(reportPath);
			
			
			if (file.exists()) {
				//delete it and create a new one
				file.delete();
			}
			
				file.createNewFile();
				Util.printInfo(" Creation of Result File ");	      
				fileWriter = new FileWriter(reportPath,true);
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(" <html><HEAD><TITLE>Detailed Test Results</TITLE><link rel=\"stylesheet\" type=\"text/css\" href=\"./../../lib/style.css\" /></HEAD>");
				bufferedWriter.write("<style type=\"text/css\"> ");
				bufferedWriter.write("table tr td, table tr th { font-size: 68%;}table.details tr th{ font-weight: bold;text-align:left; background:#a6caf0; }table.details tr td{ background:#eeeee0;}");
				bufferedWriter.write("</style>");
				bufferedWriter.write("<script type=\"text/javascript\" language=\"javascript\" src=\"./../../lib/script.js\"></script>");
				bufferedWriter.write("<body><h5 align=\"center\"><FONT FACE=\"Arial\"SIZE=4><b> Detailed Report - "+ testCaseId +"</b></h5>");
				bufferedWriter.write(" <table cellspacing=3 cellpadding=3   border=0> <tr>");
				bufferedWriter.write(" <h1> <FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=3> Test Details :</h1>");				
				bufferedWriter.write(" <td width=150 align=\"left\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2.75><b>Run Date</b></td>");
				bufferedWriter.write(" <td width=150 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2.75>" + scriptDate+" "+scriptStartTimeDR +"</td></tr>");
				bufferedWriter.write(" <tr  border=1><td width=150 align=\"left\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2.75><b>Browser</b></td>");
				bufferedWriter.write(" <td width=150 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2.75>"+ browser.toUpperCase() +"</td></tr>");
				bufferedWriter.write("</table>");
				bufferedWriter.write(" <h4> <FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=3> Detailed Report :</h4><table border=0 cellspacing=3 cellpadding=3 ><tr>");
				bufferedWriter.write(" <th width=80  align=\"center\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2><b>Step#</b></th>");
				bufferedWriter.write(" <th width=75 align=\"center\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2><b>Status</b></th>");
				bufferedWriter.write(" <th width=600 align=\"center\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2><b>Expected Result</b></th>");
				bufferedWriter.write(" <th width=600 align=\"center\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2><b>Actual Result</th></tr>");
		
		}
	
	/**
	 * @Description This is used to write the status of each step & its contents to html detailed report
	 * @param testStepStatus
	 * @param expectedResult
	 * @param actualResult
	 * @throws Exception
	 */
	public static  void writeDetailedLog(String testStepStatus,String expectedResult, String actualResult)  {
		String strScreenshotPath = null;
		String strBackgroundColor = null;
		String strStatusColumn = "" ;
		String link = "";
		
			
		if(testStepStatus.equalsIgnoreCase(Status.PASS.toString())) {
			strBackgroundColor="#00FF00";
			strStatusColumn = "<td width=75 align=\"center\" bgcolor="+strBackgroundColor+"><FONT FACE=\"Arial\" SIZE=2>Pass</td>";
		}else if (testStepStatus.equalsIgnoreCase(Status.FAIL.toString())) {			
			strBackgroundColor = "#FF3000";	//Dilute the color litle bit instead of bright red 
			strScreenshotPath = takeScreenshot(driver);
			strStatusColumn = "<td width=75 align=\"center\" bgcolor="+strBackgroundColor+"><FONT FACE=\"Arial\" SIZE=2><a href=\""+strScreenshotPath  + "\">Fail</a></td>";
		}else if(testStepStatus.equalsIgnoreCase(Status.WARNING.toString())) {
			strBackgroundColor= "#FFFF00";
			strStatusColumn = "<td width=75 align=\"center\" bgcolor="+strBackgroundColor+"><FONT FACE=\"Arial\" SIZE=2>Warning</td>";
		}
		try	{
			String expectedValue = expectedResult;
			String actualValue = actualResult;
			bufferedWriter.write("<tr><td width=80 align=\"center\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2>"+ (++stepNo) +"</td>");
			bufferedWriter.write(strStatusColumn);
			bufferedWriter.write("<td width=600 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2>"+link+expectedValue +"</td>");
			bufferedWriter.write("<td width=600 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2>"+actualValue+"</td></tr>");

	
//			Util.printInfo(testDescription+" Expected Value :" +expectedResult + "; Actual Value :" + actualResult);
			Util.printInfo(" Expected Value :" +expectedResult + "; Actual Value :" + actualResult);
		} catch(IOException io) {
			Util.printError(testCaseId+" IOException in writeLog method: \n");
			throw new RuntimeException(testCaseId+" IOException in writeLog method: \n");
			//		    closeReport();
		} catch(Exception e) {
			Util.printError(testCaseId+" Exception in writeLog method: \n");
			throw new RuntimeException(testCaseId+" Exception in writeLog method: \n");
			//	        closeReport();
		}	  
		Util.printDebug(testStepStatus+" writeLog method is completed");	
	}
	
	
	
	/**
	 * Take full screenshot of the Portal app
	 * @param objWebDriver
	 * @return
	 */
	public static String takeScreenshot(WebDriver objWebDriver) {

		File file; 
		long  id;
		String strScreenShotFileName;
		String screenShotPath=null;
		byte[] decodedScreenShot;
		FileOutputStream fos;

		file = new File(resultScreenShotPath);
		if (!file.exists()) {
			file.mkdir();
//			file.delete();
		}	
		try {
			id= Thread.currentThread().getId();
			screenShotCount  = screenShotCount  + 1;
			strScreenShotFileName = "Screenshot" + "_"+ DateTimeUtils.dateNow() + "_" + DateTimeUtils.timeNow()+id + ".jpeg";
			strScreenShotFileName = strScreenShotFileName.replace(":", "-");
			screenShotPath = resultScreenShotPath  + File.separatorChar + strScreenShotFileName;	
			Util.printDebug(testCaseId+" =>>Taking screenshot...");			 
			decodedScreenShot = Base64.decode(((TakesScreenshot)objWebDriver).getScreenshotAs(OutputType.BASE64).getBytes()); 
			fos = new FileOutputStream(new File(screenShotPath)); 
			fos.write(decodedScreenShot); 
			fos.close();
			Util.printDebug(testCaseId+" =>> =>>Screenshot taken successfully");			
//			screenShotPath= "Screenshots"+File.separatorChar + strScreenShotFileName;

		
		} catch(Exception e) {
			Util.printError(testCaseId+" Something went wrong while capturing Screenshot method: \n");
			e.printStackTrace();
			Util.printInfo(e.getMessage());
			throw new RuntimeException(testCaseId+" Something went wrong while capturing Screenshot method: \n");
			
		}
		return screenShotPath;		
	}
	/**
	 * Description: Close the report when it has finished
	 */
	
	public static void closeDetailedReport() {
		try{
			Util.printDebug(testCaseId+" =>>closing report");
			bufferedWriter.close();
			fileWriter.close();	
		} catch(Exception exp) {
			Util.printDebug(testCaseId+" Exception in closeReport method: \n");
			Util.printError(exp.getMessage());
			exp.printStackTrace();
		} 
		
	}
	
}
