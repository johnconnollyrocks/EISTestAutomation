package customerportal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import common.EISTestBase;
import common.Util;


public class Test_Verify_DevicesTabExportAll extends CustomerPortalTestBase{
	
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String INSTANCE_NAME = null;
	public String SCEmailId=null;
	public String contractNumbr="";
	public String Contracts=null;
	private static String downloadFilePath=System.getenv("USERPROFILE")+"\\Downloads\\";
	final String partDownloadFilePath=downloadFilePath+".part";
	public List<WebElement> numberProductUpdateList;
	public List<WebElement> numberDownloadButtons;
	public int count=0;
	File downloadfile = null;
	boolean downloadConfirmation;
	String downlodButtonhref="";
	private ArrayList<String> lsDeviceInfoInUI = new ArrayList();
	private String dynamicLoc=testProperties.getConstant("LOCATORSDYNAMICTOKENS");
	private String[] listDynamicLoc= dynamicLoc.split(",");
	
	private static  String[] prefValues= {"browser.download.folderList","2","browser.download.dir",downloadFilePath,"browser.helperApps.neverAsk.saveToDisk","application/excel"};
	
	public Test_Verify_DevicesTabExportAll() throws IOException {
		/*super("Browser",getAppBrowser());					*/
		super("Browser", getAppBrowser(), true, false, prefValues);
		downloadfile= new File(downloadFilePath+"Device-List.csv");
	}
	
	@Before
	public void setUp() throws Exception {
		
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_VerifyProductUpdateSaveUpdates() throws Exception {
		
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			USERNAME=getUserCredentials("EXPORTDEVICES")[0];
			PASSWORD=getUserCredentials("EXPORTDEVICES")[1];
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);	
					
		}
		mainWindow.select();
		Util.sleep(3000);
		Util.printInfo("Clicking on the " +" Devices"+" Tab");
		productUpdatePage.click("manageDevicesTab");
		deleteOldFileAndExportDevices();	
		
		 if(downloadfile.exists()){								 
			 assertTrue("File dowloaded sucessfully. File Path: "+downloadfile,true);
		 }else{
			 EISTestBase.fail("File Not found. File has been not downloaded");
		 }
		 assertTrueWithFlags("The data downloaded into CSV file is same as expected in UI", checkTheDataInUIIsSameAsCSV());
		 
		 //Now here check if the When sorting any device column whether data in csv maintain the same order as in UI
		 lsDeviceInfoInUI.clear();
		 Util.printInfo("Sort the Device column header :Last User and export the devices data. Verify that the sort order is maintained correctly and is as expected as in UI" );
		 // The below code does not work as this uses an explicit plugin called tablesorter jquery plugin
		 //0 being first colu Device, 1 ->Description, 2->Last user, 4--Status
		 //0 means ascending,1 being descending
		 String jqueryScript="$('table[role=\"grid\"]').trigger(\"sorton\",[[[2,1]]]);";	//sorting on last user column
		 /*String lastUserLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesColumnHeaders", "Last User");
		 productUpdatePage.click(lastUserLoc);	//click on the last user and sort the data in last user and verify that the data in csv file remains the same
*/		
		 ((JavascriptExecutor)(driver)).executeScript(jqueryScript); //jqueryScript // run the jquery script to sort the table header		 
		 Util.sleep(1000);
		 //refresh the page
		 driver.navigate().refresh();
		 Util.sleep(3000);
		 deleteOldFileAndExportDevices();
		 assertTrueWithFlags("The sorted data downloaded into CSV file is same as expected in UI", checkTheDataInUIIsSameAsCSV());
		 
		 //now search for the device and click on column header and export all devices.
		 ///Note the data in CSV file should be same as the data is showing same in CSV file ie.e filtered data
		 //refresh the page	//to avoid the multiple files download issue on Chrome brwoser. shouldnt harm if i dosame on FF
		 driver.navigate().refresh();
		 Util.sleep(3000);		 
		String searchWord= lsDeviceInfoInUI.get(6);
		Util.printInfo("enterString  ="+ lsDeviceInfoInUI.get(6));
		productUpdatePage.sendKeysInTextFieldSlowly("devicesPlaceHolderText",searchWord.substring(0,3));
		Util.sleep(1000);		 
		 jqueryScript="$('table[role=\"grid\"]').trigger(\"sorton\",[[[1,1]]]);";	//sorting on last user column
		 ((JavascriptExecutor)(driver)).executeScript(jqueryScript); //jqueryScript // run the jquery script to sort the table header		 
		 Util.sleep(1000);
		 deleteOldFileAndExportDevices();
		 
		 
	}
	public void deleteOldFiles(){
				File partDownloadfile = new File(downloadFilePath);
				File[] myFiles=partDownloadfile.listFiles(new FilenameFilter() {
					
					//the files will be always starting with Device
					@Override
					public boolean accept(File dir, String name) {
						return name.startsWith("Device");
					}
				});		
				for(File oldDownloadFile: myFiles){
					oldDownloadFile.delete();
				}
				partDownloadfile.deleteOnExit();
			 }	
			
	public void deleteOldFileAndExportDevices() throws Exception{
		deleteOldFiles();
		Util.printInfo("Get all the device info, description and last user from UI");
		//do the following way reading the csv file need to be read as row by row
		//leave the header row ie. start from 1
		int j=0;
		String[] deviceCols=productUpdatePage.getMultipleTextValuesfromField("deviceAllRowsInTable");
		int rowsNum=productUpdatePage.getMultipleTextValuesfromField("allRowsinDevicesTable").length;
		lsDeviceInfoInUI.addAll(Arrays.asList(deviceCols));
		while(j<rowsNum){
		
			for(int i=0;i<listDynamicLoc.length;i++){
				String dynamicLocator=listDynamicLoc[i];		
			
				//get each row info of specific td
				String getRowDevicesInfo=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesSpecificRowColumnData", String.valueOf(j+1 ),dynamicLocator);
				if (productUpdatePage.isFieldVisible(getRowDevicesInfo)){
					String devicerowInfo=productUpdatePage.getValueFromGUI(getRowDevicesInfo);	//only row info
					lsDeviceInfoInUI.add(devicerowInfo);		
				}
			}
			j++;
		}
		
		productUpdatePage.click("exportAll");
		Util.printInfo("Clicked on Export All");
		/* String buildPath=System.getProperty("user.dir");
		 if(!buildPath.contains("build"))
	     {
	    	 buildPath=buildPath+"\\build";
	     } 
	//	Runtime.getRuntime().exec(buildPath+"\\PU_AutoITFile.exe "+getAppBrowser()+" "+"save");
		Process proc= Runtime.getRuntime().exec(buildPath+"\\ExportallDevices.exe");
		proc.waitFor();*/
		Util.sleep(4000);
		waitUntilPartDownloadFileExist();
	}
	public boolean waitUntilPartDownloadFileExist(){
		boolean downloadComplete=false;
		int numTimes=0;
		
			while (!downloadComplete || numTimes==10){
				Util.sleep(5000);	//pause for 5 sec
				downloadComplete=isFileDownloaded();
				numTimes=numTimes+1;	
			}
		/*}*/
			

	return downloadComplete;
		
	}

	
	public boolean checkTheDataInUIIsSameAsCSV() throws Exception{
		 Util.printInfo("Verify the content in the csv file matches as expected with data showing up in UI");
		 ArrayList<String> actualCSVContent= new ArrayList<>();		 
		 try {
			BufferedReader bread= new BufferedReader(new FileReader(new File(gettheDownladedCSVFileName())));
			 String lineToRead=null;
			 while((lineToRead=bread.readLine())!=null){
				 String[] lineContents=lineToRead.split(",");	//split using comma
				 for ( int i=0;i<lineContents.length;i++){				 
					 actualCSVContent.add(lineContents[i]);
				 }			 
			 }
			 bread.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 //At the end compare whether the content is good
		 return  actualCSVContent.equals(lsDeviceInfoInUI);
		
		 
	}
  public boolean isFileDownloaded()  {
		
		File myFolder = new File(downloadFilePath);
		File[] allFiles=myFolder.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File myFolder, String name) {				
				return (name.toLowerCase().endsWith(".csv"));
			}
		});
		// i.e file got downloaded
		 if(allFiles.length>1){
			 return true;
		 }
		
		return false;	//default value
	}
  public String gettheDownladedCSVFileName()  {
		
		File myFolder = new File(downloadFilePath);
		File[] allFiles=myFolder.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File myFolder, String name) {				
				return (name.toLowerCase().endsWith(".csv") && name.toLowerCase().startsWith("device"));
			}
		});
		// i.e file got downloaded
		 if(allFiles.length>=1){
			 return allFiles[0].getAbsolutePath();
		}
		
		return null;	//default value
	}
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
}
}
