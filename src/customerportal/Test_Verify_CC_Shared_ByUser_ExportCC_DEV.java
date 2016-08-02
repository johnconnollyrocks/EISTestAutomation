package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_CC_Shared_ByUser_ExportCC_DEV extends CustomerPortalTestBase{
	
	

	public Test_Verify_CC_Shared_ByUser_ExportCC_DEV() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());		
	}
	@Test
	public void Test_Verify_Sorting_Of_Contracts_OnReportingPage()throws Exception {
		
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
			
		
		homePage.waitForFieldPresent("AllProductsandServices");
		
		
		IndividualCC();
		//SharedCC();
		ByUser();
		MyUsage();
		ExportCC();
		
		
		logoutMyAutodeskPortal();
		}

	public void IndividualCC() throws Exception{
		
		Util.printInfo("clicking on Report Link and Verifying Individual and Shared cloud credits header in reporting page...");
		homePage.clickAndWait("reporting","byUsers");
		homePage.waitForFieldPresent("IndividualCloudCreditsOnReportingPage");
		if(homePage.isFieldVisible("IndividualCloudCreditsOnReportingPage") && homePage.isFieldVisible("SharedCloudCreditsOnReportingPage")){
			homePage.verifyFieldExists("IndividualCloudCreditsOnReportingPage");
			homePage.verifyFieldExists("SharedCloudCreditsOnReportingPage");
		}else{
			EISTestBase.fail("Reporting Page not loaded...");
		}
		
		String IndividualCCused = driver.findElement(By.xpath("//*[@id='contract-usage']//span[contains(@class,'credits')]")).getText();
		String IndividualCConsume =driver.findElement(By.xpath(".//*[@id='contract-usage']/div[2]/div[3]/div/div[1]/span")).getText();
	}
	
	public void IndividualCC1() throws Exception{
		

	}

	public void SharedCC() throws Exception{
			
	}
	
	public void ByUser(){
				
		
	}
	
	public void MyUsage(){
		homePage.clickAndWait("reporting","byUsers");
		homePage.click("MyUsageLink");
		Util.sleep(40000);
		if(driver.getCurrentUrl().contains("my-usage")){
			assertTrue("Navigated to My Usage Page after clicking on My Usage Link :: ", driver.getCurrentUrl().contains("my-usage"));
		}else{
			EISTestBase.fail("My Usage page is not displayed even after clicking on My Usage Link :: ");
		}	
	}
	
	public void ExportCC() throws MetadataException{
		
		
	
	}

	public void waitUntilDownloadFileExist(String ContractNumber){
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
			driver.quit();
			finish();
		}

}
