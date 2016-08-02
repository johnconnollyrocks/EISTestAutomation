package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.common.base.Strings;



public class summaryReport {
	

	/** contains the test suite name. */
	private String testSuiteName;

	/** contains the path of overall report path. */
	private String overAllReportPath;

	/** contains the number of tests passed. */
	private int numOfTestsPassed=0;

	/** contains the number of tests failed. */
	private int numOfTestsFailed=0;

	/** contains number of tests did not run*/
	private int numOfTestsDidNotRun=0;	
	
	/** contains the number of tests executed. */
	private int numOfTests;

	/** The filewriter for overall report. */
	private FileWriter fileWriter;

	/** The bufferedwriter for overall report. */
	private BufferedWriter bufferedWriter;

	/** contains the list of Test cases. */
	private String overAllTestCaseList ="";

	/** contains the test case start time */
	private String testStartTime="";	//leave it this way if in future something is required, can reuse this

	/** contains the test case end time. */
	private String testEndTime;  //leave it this way if in future something is required, can reuse this
	
	/**Contains the name of the main level Jenkins job folder */
/*	private String mainFolderForJenkinsJobs="Portal2";

	*//**Contains the Jenkins jobs location till the desired folder *//*
    private String allJenkinsJobLocation= "C:\\CustomerPortal2\\JenkinsJobs";*/
    
    /**Contains the name of the main level Jenkins job folder */
    private String folderToreadJobs  =  System.getProperty("jenkinsJobLocation")+File.separator;
	
    /* global variable to read the jenkins jobs*/
    private File dirFileholder  = null;    
    
    /**Contains the string to parse for failure builds */
    private final static String BUILD_FAILED = "BUILD FAILED";
    
    
    /**Contains the string to parse for sucessful builds */
    private final static String BUILD_PASSED = "BUILD SUCCESSFUL";
    
    /**Contains the name of test reports folder */
    private String  individualTCReportpath= Util.getTestRootDir()+File.separator+"test-reports";
    
    /**Contains the Regression Report title */
    private String reportTitle=System.getProperty("reportTitle");
    
    private static int Slno=0;
    
    private static String jenkinsMainConsoleURLPathOfJob="http://uspetddldrct001.ads.autodesk.com/job";
    
    private static String applicationURL="";
        
    private static String keyWordForURL="Launched";
    
    private static String overAllTime= null;
    
    private static int iMinutes=0;
    
    private static int iSeconds=0;
    
    private static String jenkinsReportpath="";
    
    private static int noOfBuildFolders=0;
    
    /*If the user is interested to run only portion of tests then generate the report only for those test. Add jvm arg to name the tests */
    
    private static String ListOfTest= System.getProperty("filterTests");
    
	/**
	 * Create folders for overall report ,files before writing the overall summary report(basically does the ground work before writing overall summary report).
	 * @author t_marus
	 * @throws IOException.
	 */
	private void createHeaders() throws IOException {
		Util.printDebug("Creating Headers for overall summary report ");
		File checkFolderExist;
		File file;
		String overallResultsFile = null;
		String overallPath;
		
		overAllReportPath=Util.getTestRootDir()+File.separator+"test-reports"+File.separator;
		checkFolderExist = new File(overAllReportPath);
		if (!checkFolderExist.exists()) {
			checkFolderExist.mkdir();
		} /*else {
			deleteDirectory(checkFolderExist);	
			checkFolderExist.mkdir();
		} 	*/					  
		overallPath = overAllReportPath + "TestSummaryReport" ;
		overallResultsFile = overallPath+".html";	   

		file = new File(overallResultsFile);
		if (file.exists()){
			FileUtils.forceDelete(file);
		}		
			file.createNewFile();
			fileWriter = new FileWriter(overallResultsFile,true);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(" <html><HEAD><TITLE>"+reportTitle+" Automation Test Results</TITLE></HEAD><body><h5 align=\"center\"><FONT FACE=\"Arial\" SIZE=\"4\"> "+reportTitle+" AUTOMATION TEST RESULTS </h5>");
			bufferedWriter.write(" <table  border=0 cellspacing=3 cellpadding=3 > <tr>");
			bufferedWriter.write(" <h4> <FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=3> Test Details :</h4>");
			bufferedWriter.write(" <td width=150 align=\"left\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2.75><b>Run Date</b></td>");
			bufferedWriter.write(" <td width=150 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2.75>"+ DateTimeUtils.dateNow() +"</td></tr>");		        		        
			      
		Util.printInfo("Completed  Creating Headers for overall summary report ");
	}

	/**
	 * Write overall summary report at testcases/high level.	
	 *  
	 * @throws IOException
	 */
	public void summary() throws IOException {

		int numTotal;
		int successRate;
		int failRate;
		int passwidth;
		int failwidth;
		int didNotRunWidth;
		
		createHeaders();		
		numTotal = numOfTestsPassed + numOfTestsFailed+numOfTestsDidNotRun;
		if (numOfTestsPassed ==0 ) {
			successRate = 0;	
		} else {
			successRate =  (numOfTestsPassed * 100 /(numOfTestsPassed+numOfTestsFailed));
		}	    
		successRate= Math.round(successRate);
		failRate = 100 - successRate;
		passwidth = (300 * successRate)/100;
		failwidth = 300 - passwidth;
		didNotRunWidth=1;
		testEndTime =  DateTimeUtils.timeNow();
		int totalMinutes= iMinutes+(iSeconds/60);	
		if (totalMinutes>60){
			int iTotalHrs= (int) TimeUnit.MINUTES.toHours(totalMinutes);
			int totalMin=totalMinutes%60;
			overAllTime=iTotalHrs+" Hours"+":"+totalMin+" minutes";
		
		}else{			
			int iTotalMin=(int) TimeUnit.MINUTES.toMinutes(totalMinutes);			
			overAllTime="00:"+iTotalMin+" Minutes";			
		}
		
		
		Util.printInfo("Creating summary report ");     	
		/*bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2.75><b>Run Start Time</b></td>");
		bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2.75>" + testStartTime +"</td></tr>");*/
		/*bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2.75><b>Run End Time</b></td>");
		bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2.75>"+testEndTime+"</td></tr>");*/
/*		bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2.75><b>Total Execution Time</b></td>");
		bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2.75>"+overAllTime+"</td></tr>");*/
		bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2.75><b>Test Type</b></td>");
		bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2.75>"+System.getProperty("testType")+"</td></tr>");
		bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2.75><b>Enviroment</b></td>");
		bufferedWriter.write("<td width=150 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2.75 ><a target=\"_blank\" href="+applicationURL+">"+System.getProperty("environment")+"</a> </td></tr>");
		bufferedWriter.write("</table>");
		bufferedWriter.write("<h4> <FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=3> Test Result Summary :</h4><table border=0 cellspacing=1 cellpadding=1 ></table>");
		bufferedWriter.write("<table border=0 cellspacing=1 cellpadding=1 ><tr>" +
				"<td width=70><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>Total Test</b></td>" +
				"<td width=10><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>:</b></td>" +
				"<td width=35><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>"+numTotal+"</b></td> "+
				"<td width=300 bgcolor=\"#0000FF\"></td> "+
				"<td width=20><FONT COLOR=\"#000000\" FACE=\"Arial\" SIZE=1><b>100%</b></td></tr></table>");

		bufferedWriter.write("<table border=0 cellspacing=1 cellpadding=1 ><tr> "+
				"<td width=70><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>Total Pass</b></td>" +
				"<td width=10><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>:</b></td>" +
				"<td width=35><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>"+numOfTestsPassed+"</b></td>" +
				"<td width="+passwidth+" bgcolor=\"#00FF00\"></td>" +
				"<td width=20><FONT COLOR=\"#000000\" FACE=\"Arial\" SIZE=1><b>"+successRate+"%</b></td>" +
				" </tr></table>");

		bufferedWriter.write("<table border=0 cellspacing=1 cellpadding=1 ><tr> "+
				"<td width=70><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>Total Fail</b></td>" +
				"<td width=10><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>:</b></td>"+
				"<td width=35><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>"+numOfTestsFailed+"</b></td>"+
				"<td width="+failwidth+" bgcolor=\"#FF0000\"></td> "+
				"<td width=20><FONT COLOR=\"#000000\" FACE=\"Arial\" SIZE=1><b>"+failRate+"%</b></td>"+
				"</tr></table>");				

		if (numOfTestsDidNotRun>0){
			bufferedWriter.write("<table border=0 cellspacing=1 cellpadding=1 ><tr> "+
					"<td width=70><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>Total DidNotRun</b></td>" +
					"<td width=10><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>:</b></td>"+
					"<td width=35><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>"+numOfTestsDidNotRun+"</b></td>"+
					"<td width="+didNotRunWidth+" bgcolor=\"#FFFF00\"></td> "+					
					"</tr></table>");					
		}
		bufferedWriter.write("<h4> <FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=3> Detailed Report :</h4>");
		bufferedWriter.write("<table  border=0 cellspacing=3 cellpadding=3 >"+
				"<tr>" +
				"<td width=80  align=\"center\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2><b>SI No.</b></td>"+
				"<td width=90 align=\"center\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2><b>Test Scenario ID</b></td>"+
				"<td width=300 align=\"center\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2><b>Test Scenario Description</b></td>"+
				"<td width=75  align=\"center\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2><b>Status</b></td>"+
				"<td width=200 align=\"center\" bgcolor=\"#a6caf0\"><FONT FACE=\"Arial\" SIZE=2><b>Execution Time</b></td>"+
				"</tr>");
		Util.printInfo("===============================================");
		if (numOfTestsDidNotRun>0){			
			numTotal=numTotal-numOfTestsDidNotRun;
		}
		Util.printInfo("Number Of Tests Executed: "+ numTotal);	
		Util.printInfo("Number Of Tests Passed  : "+numOfTestsPassed);
		Util.printInfo("Number Of Tests Failed  : "+numOfTestsFailed);
		if (numOfTestsDidNotRun>0){
			Util.printInfo("Number Of Tests Did not run  : "+numOfTestsDidNotRun);	
		}
		Util.printInfo("===============================================");	
		injectOverAllReport();
	}

	/**
	 * Write overall summary report for each test cases in a test suite.
	 * @param strTestCase
	 * @param strTestCaseDescription
	 * @param strTestCaseReportPath
	 * @param strOverAllStatus
	 * @param strDateTime
	 */
	public void writeOverallReport(String strTestCase,String strTestCaseDescription, String strTestCaseReportPath,String strOverAllStatus, String strDateTime) {

		String statusHighBgColor = null;
		String tempPath=folderToreadJobs;
		String jenkinsJobPath=null;
		String[] myPath=tempPath.split(":");
		String pathToAppend="";
		if (myPath.length>1){			
			String[] tempArr=myPath[1].split(File.separator+"\\");
			for(String s1:tempArr){
				pathToAppend+=s1+"/";
			}
			//remove the last letter ie. jobs to job
			
			//get the no of buildfolders and construct the console output url
			
			jenkinsJobPath=jenkinsMainConsoleURLPathOfJob+pathToAppend.substring(0,pathToAppend.length()-2)+"/"+strTestCase+"/"+noOfBuildFolders+"/"+"console";
			
			//replace jobs to job as the jenkins recognize with job keyword
			jenkinsJobPath=jenkinsJobPath.replaceAll("jobs", "job").replaceAll("Jobs", "job");
			//also the selenium keyword, the first letter should in caps
			jenkinsJobPath=jenkinsJobPath.replace("selenium", "Selenium");
		}
		
		Util.printInfo("Writes Overall Report.");
		if (strOverAllStatus == objDetailedReport.Status.PASS.toString() || strOverAllStatus.equalsIgnoreCase("Passed")) {
			statusHighBgColor="#00FF00";
			numOfTestsPassed++;	    	
		} else if ( strOverAllStatus == objDetailedReport.Status.FAIL.toString() ||  strOverAllStatus.equalsIgnoreCase("Failed")) {
			statusHighBgColor = "#FF0000";
			numOfTestsFailed++;	  	
		} else if ( strOverAllStatus == objDetailedReport.Status.WARNING.toString()) {
			statusHighBgColor="#FFFF33";		

		}else{	//did not run
			statusHighBgColor="#FFFF00";
			numOfTestsDidNotRun++;
		}
		Slno++;
		//dont write the timeout if the status is did not run
		if (strOverAllStatus.equalsIgnoreCase("did not run")){
			overAllTestCaseList = overAllTestCaseList + "<tr><td width=80 align=\"center\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2>"+(Slno)+"</td>" +
					"<td width=90 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2><b><a target=\"_blank\" href="+jenkinsJobPath + ">" + strTestCase + "</a></b></td>" +
					"<td width=300 align=\"left\" bgcolor=\"#eeeee0\" ><FONT FACE=\"Arial\" SIZE=2>"+ strTestCaseDescription +"</td>" +
					"<td width=75 align=\"center\" bgcolor=" + statusHighBgColor + "><FONT COLOR=\"#153E7E\" FACE=\"Arial\" SIZE=2>"+ strOverAllStatus +"</td>" +
					"<td width=200 align=\"center\" bgcolor=\"#eeeee0\" ><FONT FACE=\"Arial\" SIZE=2></td></tr>";
		}else{
			
			overAllTestCaseList = overAllTestCaseList + "<tr><td width=80 align=\"center\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2>"+(Slno)+"</td>" +
					"<td width=90 align=\"left\" bgcolor=\"#eeeee0\"><FONT FACE=\"Arial\" SIZE=2><b><a target=\"_blank\" href="+jenkinsJobPath + ">" + strTestCase + "</a></b></td>" +
					"<td width=300 align=\"left\" bgcolor=\"#eeeee0\" ><FONT FACE=\"Arial\" SIZE=2>"+ strTestCaseDescription +"</td>" +
					"<td width=75 align=\"center\" bgcolor=" + statusHighBgColor + "><FONT COLOR=\"#153E7E\" FACE=\"Arial\" SIZE=2>"+ strOverAllStatus +"</td>" +
					"<td width=200 align=\"center\" bgcolor=\"#eeeee0\" ><FONT FACE=\"Arial\" SIZE=2>"+ strDateTime +"</td></tr>";
		}
		
	}

	/**
	 * Inject overall report.
	 *
	 * @throws IOException
	 */
	public void injectOverAllReport() throws IOException {
		Util.printInfo("injectOverAllReport method");
		bufferedWriter.write(overAllTestCaseList);		
	}

	/**
	 * Close overall report.
	 *
	 * @throws IOException
	 */
	public void close() throws IOException {
		Util.printInfo("closing the report");
		bufferedWriter.close();
		fileWriter.close();			
		//Only for Dev regression suite do the following
		if (folderToreadJobs.contains("Dev_Regression")) {
			//verify if the Devbuildflow suite has failed. This is mainly used for Dev regression suite. there is one of the alternative found to fail the summary report test.
			if (finddevBuildFlowJobStatus().equalsIgnoreCase("fail")) {
				EISTestBase.fail("The Dev regression Build suite job has failed. Please check the summary report for further information about tests that are failed");
			}else if (finddevBuildFlowJobStatus().equalsIgnoreCase("pass")) {
				System.out.println("The dev build suite has passed");
			}else {
				System.out.println("The Dev build flow suite is aborted");
			}
		}
			
	}

	/**
	 * @Description THis is the main method which reads the Jenkins jobs folder in master machine. And extracts the info to collection and injects to html report
	 * Here collection is just used to store the data now and not consumed anywhere, but may be for any future developments we can use the same.
	 * @throws Exception
	 */

	public void getListOfJenkinsJobs() throws Exception{
		dirFileholder= new File(folderToreadJobs);		
		SimpleDateFormat dformat= new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		ArrayList<String> lstJobNames= new ArrayList<>();
		ArrayList<String> filteredListOfTests= null;
		File[] lstDirectory= dirFileholder.listFiles();	//get all the list of the test case names and dump them into
		String descriptionPathInConfigXml= "description";
		String FeaturesVerifiedDescription=null;
		LinkedHashMap<String, ArrayList<String>> TCExecutionDetails= new LinkedHashMap<>();
		ArrayList<String> TCExecutionresultsInfo = new ArrayList<>();
		String overAllTCStatus = "";
		String execTime=null;
		boolean testsAreFiltered=false;
		
		int countTotalTests=0;
		System.out.println(System.getProperty("jenkinsJobLocation"));
		/*For the selected tests do the following*/
		if (!Strings.isNullOrEmpty(ListOfTest)){
			
			//if required to run all the tests then generate the report for all types			
			if (ListOfTest.equalsIgnoreCase("all")){
				testsAreFiltered=false;	
			}else{				
				testsAreFiltered=true;			
				String[] myTests=ListOfTest.split(",");
				//put all myTests Into ArrayList. this is better as we can easily find if expected test exists 
				filteredListOfTests= new ArrayList<>(Arrays.asList(myTests));
			}
		}else{
			System.out.println("No Test scripts have been selected to generate the HTML report. Hence HTML report will not be generated. Please provide either 'All' or individual test script names " +
					"separated by comma");
			/*System.exit(0);	/// terminate the program abnormally
			 
*/			
		}
		
		
		boolean foundTheFilterJob=false;
		for(File eachDir: lstDirectory){
			
			if (testsAreFiltered){
				foundTheFilterJob=false;	
			}
//			if (eachDir.isDirectory() && (eachDir.getName().contains("verify")||eachDir.getName().contains("Verify"))){
			//NOTE:NEW COMMENTS FROM BIC TEAM:BRIJESH, HAVE ENFORCED THE USER TO GIVE THE LIST OF TESTS IN filterTests PARAM as default value as 'all' or individual tests separated by comma.
			if (eachDir.isDirectory() && !eachDir.getName().contains("GenerateSummaryReport") && !eachDir.getName().contains("BuildFlow")){
					lstJobNames.add(eachDir.getName());	//get all the list of tc names
					countTotalTests++;
				
			
				//get the Job name to HashMap
				String jenkinsJobName=eachDir.getName();
			
				if (testsAreFiltered){
					//check if the job name exists ih the filtered list
					foundTheFilterJob=(filteredListOfTests.contains(jenkinsJobName))?true:false;				
					if (!foundTheFilterJob){
						continue;	//skip this iterator moves to next directory
					}
				}
				
				
				//Read config.xml inside each Jenkins jobs folder to get the description of the job.
				String jobConfigXmlpath= eachDir.getAbsolutePath()+File.separator+"config.xml";
				XMLConfiguration xmlFileToRead = new XMLConfiguration(new File(jobConfigXmlpath));
				FeaturesVerifiedDescription=xmlFileToRead.getString(descriptionPathInConfigXml);
				TCExecutionresultsInfo.add(FeaturesVerifiedDescription);	//0 level TC description
				
				
				xmlFileToRead=null;	//close this
				
				//now get the files inside each folder
				String buildLatestFolderOfJob= eachDir.getAbsolutePath()+File.separator+"builds";
				//in this builds folder find the last modified folder
				File[] lastModifiedDir= new File(buildLatestFolderOfJob).listFiles();			
				ArrayList<String> buildFolderDates = new ArrayList<>();
				noOfBuildFolders=0;//reset 
				for(File lastDir: lastModifiedDir){
					if (lastDir.isDirectory() ){
						buildFolderDates.add(lastDir.getName());//get all the build folder names 
					}
				}
				/*noOfBuildFolders=buildFolderDates.size();*/	
				//08=Oct-2014
				//NOTE: As the jobs being copied from one folder to another, the console output folder number is changing on Jenkins
				// The logic of pulling last console output is not working. Hence reading the nextBuildNumber file and get the value
				int buildNumberFromFolder= getTheBuildNumberFile(eachDir.getAbsolutePath()+File.separator+"nextBuildNumber");
				noOfBuildFolders=buildNumberFromFolder;
				
				//this build folder size is considered to link the console output of each job
				
				Collections.sort(buildFolderDates);	//sort them in ascending order and get the last one i.e latest build
				if (buildFolderDates.size()>0 && buildFolderDates!=null){
					String latestBuildFolderForTheCurrentJob=buildFolderDates.get(buildFolderDates.size()-1);
					//For Debugging print the latest build folder
					System.out.println("Picked the folder: " +latestBuildFolderForTheCurrentJob+" for the job:"+jenkinsJobName);
					
					File  origLatestbuildFolderlogFile =new File(buildLatestFolderOfJob+File.separator+latestBuildFolderForTheCurrentJob+File.separator+"log");			
					File latestBuildfolderLogFile = new File(buildLatestFolderOfJob+File.separator+latestBuildFolderForTheCurrentJob+File.separator+"newlog.txt");
								
					//copy the log file just to avoid file corruption			
					FileUtils.copyFile(origLatestbuildFolderlogFile, latestBuildfolderLogFile);
					
					//get the build log file and parse if build failed or build successfull is found
					//NOTE: USing bufferedReader some content is getting missed hence trying with scanner
					FileInputStream bread1 = new FileInputStream(latestBuildfolderLogFile);			
					Scanner scan= new Scanner(bread1,"UTF-8");
					scan.useDelimiter("\n");	
					String logFileContent="";
					
					//read till the end of line			
					overAllTCStatus="";	//set to empty value;
					while(scan.hasNext()){
						logFileContent=scan.next();
						logFileContent=logFileContent.trim();
		//				System.out.println(logFileContent);				
						// i.e the last part of the log				
						if (logFileContent.contains("BUILD")){
							if(logFileContent.contains(BUILD_FAILED)){
								overAllTCStatus="Failed";							
							}else if (logFileContent.contains(BUILD_PASSED)){
								overAllTCStatus="Passed";							
							}
						
						}
						//also get the app url Here look for the 'launched keyword and pasrse with 'at' 
						if (logFileContent.contains(keyWordForURL) && logFileContent.contains("at")){
							//split using 'at'
							String[] urlParse=logFileContent.split("at");
							if (urlParse.length>1){
								applicationURL=urlParse[1].replace("'", "");
							}
						}
										//get the overall execution time for the job which has run
						if (logFileContent.contains("Total time:")){
							//parse the string and pull the total time
							execTime= logFileContent.split(":")[1].trim();	//the first pos is the actual execution time
							TCExecutionresultsInfo.add(overAllTCStatus);	//add the overall tc status 
							TCExecutionresultsInfo.add(execTime);
							// Count the time into minutes and seconds
							String[] parseTime=execTime.split(" ");
							if (parseTime.length>3){
								iMinutes+= Integer.valueOf(parseTime[0].trim()); //assuming minutes at position is 0 pos and seconds at 2nd pos This wont chagne this is jenkins log
								iSeconds+= Integer.valueOf(parseTime[2].trim()); 
							}
							break; // break the while loop not required to read the file
						}
					}
					//ie. the test did not run successfully
					if (overAllTCStatus==""){
						overAllTCStatus="Did not Run";
					}
					
					// Add the execution details into to hash map
					TCExecutionDetails.put(jenkinsJobName, TCExecutionresultsInfo);
					
					//Now write the status of each test info into HTML report
					writeOverallReport(jenkinsJobName, FeaturesVerifiedDescription,individualTCReportpath+File.separator+jenkinsJobName+".html", overAllTCStatus, execTime);
					
					numOfTests+=countTotalTests;
				 }	
			}
		}
	}
	
	public String finddevBuildFlowJobStatus() {
		String statusOfDevBuildFlow=null;
	
		try {
			//THis hard coded here, as 
			// The email ext plugin is not working for Build flow job due to older version of jenkins and due to this limitation getting the status of Build Flow job for Dev regression suite.
			// Also summary report job will be always successful and this wont publish right status via jenkins plugin
			String devBuildFlowJobName =  "DevSuiteBuildFlow";			
			//Read the all the jobs and get the devbuildflow job
			File buildFlowjob = new File(folderToreadJobs+devBuildFlowJobName+File.separator+"builds");
			//get the list of folders with last modified
			File[] buidFlowJobs= buildFlowjob.listFiles();
			ArrayList<String> lastModifiedBuildFlowJobInfo = new ArrayList<>();
			for (File myjob: buidFlowJobs) {
				lastModifiedBuildFlowJobInfo.add(myjob.getName());	// add all the 
			}		
			//sort the arraylist and get latest folder
			Collections.sort(lastModifiedBuildFlowJobInfo);
			if (lastModifiedBuildFlowJobInfo.size()>0 && lastModifiedBuildFlowJobInfo!=null){
				String devbuidlFlowsuiteFolder=lastModifiedBuildFlowJobInfo.get(lastModifiedBuildFlowJobInfo.size()-1);
				//For Debugging print the latest build folder
				System.out.println("Picked the folder: " +devbuidlFlowsuiteFolder+" for the job: DEVBUILDSUITE");
				String buildLatestFolderOfJob=buildFlowjob.getAbsolutePath();
				File  origLatestbuildFolderlogFile =new File(buildLatestFolderOfJob+File.separator+devbuidlFlowsuiteFolder+File.separator+"build.xml");		

				File newBuidlFile = new File(buildLatestFolderOfJob+File.separator+devbuidlFlowsuiteFolder+File.separator+"newbuild.xml");
							
				//copy the build.xml file just to avoid file corruption			
				FileUtils.copyFile(origLatestbuildFolderlogFile, newBuidlFile);
				
				//get the build log file and parse if build failed or build successful is found
									
				XMLConfiguration xmlFileToRead = new XMLConfiguration(newBuidlFile);
				statusOfDevBuildFlow=xmlFileToRead.getString("result");
				if (statusOfDevBuildFlow.equalsIgnoreCase("failure")) {
					return "fail" ;
				}else if (statusOfDevBuildFlow.equalsIgnoreCase("success")) {
					return "pass";
				}
				
			}
		}
		
			catch (Exception e) {
				System.out.println("Something went wrong in reading the build.xml of DevSuiteBuildflow job"+ e.getMessage());
			}
		
		
		return "";		//send empty string if something breaks
	}
	
	public int getTheBuildNumberFile(String filePath) throws FileNotFoundException {
		File buildNumFile = new File(filePath);
		FileInputStream fStream = new FileInputStream(buildNumFile);
		String fileContent= null;
		Scanner readScan = new Scanner(fStream);
		while(readScan.hasNext()) {
			fileContent= readScan.next();
		}
		fileContent=fileContent.trim();
		//As the file contains only the next build number hence read it and conver the string to int
		int buildNum = Integer.valueOf(fileContent)-1;	//get the current one, hence -1
		readScan.close();
		return buildNum;
		
	}
	@Test	
	public void runSummaryReport() throws Exception {
		getListOfJenkinsJobs();
		summary();
		close();
	}
/*	public static void main(String[] args) throws Exception {
		summaryReport summ = new summaryReport();
		summ.finddevBuildFlowJobStatus();
	}*/

	/**
	 * Gets the test suite name.
	 *
	 * @return the test suite name
	 */
	public String getTestSuiteName(){

		return testSuiteName;
	}
	
	

}
