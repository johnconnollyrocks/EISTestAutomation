package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ExportToCSVFunctionality extends CustomerPortalTestBase {
	
	String ContractNumber=null;
	public Test_Verify_ExportToCSVFunctionality() throws IOException {
		super("Browser",getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_OnlineMap() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.click("reporting");
		homePage.refresh();
		Util.sleep(6000);
		List<WebElement> ContractsList=homePage.getMultipleWebElementsfromField("UsageReportContractList");
		int count=0;
		for(WebElement Contract : ContractsList){
			/*final String downloadFilePath=System.getenv("USERPROFILE")+"\\Downloads\\"+Contract.getText()+".csv";
			File downloadfile = new File(downloadFilePath);*/
			String UsageReportContract=homePage.createFieldWithParsedFieldLocatorsTokens("SharedCreditsBar", Contract.getText());
			Util.sleep(2000);
			if(homePage.isFieldVisible(UsageReportContract)){
				homePage.click(UsageReportContract);
				Util.sleep(50000);
				String ExportToCSV=homePage.createFieldWithParsedFieldLocatorsTokens("ExportToCSV", Contract.getText());
				Util.sleep(50000);
				if(homePage.isFieldVisible(ExportToCSV)){
				count=count+1;
				Util.printInfo("Clicking on ExportToCSV Link ");
				homePage.click(ExportToCSV);
				Util.sleep(20000);
				ContractNumber=Contract.getText();
				Util.sleep(50000);
				Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\ExportToCSV.exe "+Contract.getText());
				Util.sleep(20000);
				waitUntilDownloadFileExist();
				break;
				}
			}
			homePage.click(UsageReportContract);
		}
		
		if(count==0){
			Util.printInfo("No cloud Credits are purchased or no shared cloud credits are available for this user");
			
		}
		
		logoutMyAutodeskPortal();
	}
	
	public void waitUntilDownloadFileExist(){
		final String downloadFilePath=System.getenv("USERPROFILE")+"\\Downloads\\"+ContractNumber+".csv";
		Util.sleep(20000);
		boolean downloadComplete=false;
		int numTimes=0;
		File partDownloadfile = new File(downloadFilePath);
		
		if(partDownloadfile.exists()){
			assertTrue("The Shared Cloud Credits file has downloaded successfully after clicking on ExportToCSV link ", partDownloadfile.exists());
			Util.printInfo("The File name and File path is :: "+partDownloadfile);
			if(partDownloadfile.exists()){
				Util.sleep(50000);
				Util.printInfo("Deleting the existing .csv file ");
				partDownloadfile.delete();
			}
		}else{
			EISTestBase.fail("File Not Downloaded successfully after clicking on ExportToCSV link ");
		}
	}

	

	@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}
}
