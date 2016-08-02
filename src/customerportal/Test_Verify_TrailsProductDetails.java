package customerportal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.JsonParser;
import common.Util;

public class Test_Verify_TrailsProductDetails extends CustomerPortalTestBase {
	public Test_Verify_TrailsProductDetails() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_TrailsProductDetails() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		JsonParser json=new JsonParser();
		ArrayList<String> TrialTypes=new ArrayList<String>();
		ArrayList<String> Names=new ArrayList<String>();
		ArrayList<String> CreditsUsed=new ArrayList<String>();
		ArrayList<String> CreditsAvailable=new ArrayList<String>();
		ArrayList<String> TotalCredits=new ArrayList<String>();
		ArrayList<String> UIProductNameList=new ArrayList<String>();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(2000);
		
		Util.printInfo("Validating that Trails tab should not pressent in main header ");
		
		homePage.verifyFieldNotExists("TrailsOnMainHeader");
		Util.sleep(4000);
		homePage.click("TrailsTabInProductsAndServicesPage");
		Util.sleep(4000);
		Wini ini = null;
		int count=0;
		String GUID=getGUIDForUser(testProperties.getConstant("USER_NAME"));
		Util.sleep(4000);
		String SessionId=getUserSessionID();
		String GrantToken=getGrantToken();
		List<WebElement> Products=homePage.getMultipleWebElementsfromField("TrailProducts");
		String winHandleBefore = driver.getWindowHandle();
		if(Products.size()>0){
		
				String url="https://customer-dev.autodesk.com/service/entitlement/get/trials.json/"+GUID+"/"+SessionId+"?grantToken="+GrantToken+"&grantToken="+GrantToken+"";
				openNewTab(url);
				Util.sleep(6000);
				Util.printInfo("************New Tab************");
				for(String winHandle : driver.getWindowHandles()){
				    driver.switchTo().window(winHandle);
				}
				Util.sleep(4000);
				Util.printInfo("************Getting Response************");
				String UIResponse=homePage.getValueFromGUI("RestResponse");
				Util.printInfo("************Got Response************");
				Util.sleep(4000);
				//CreateJSONFile(UIResponse);
				Util.sleep(4000);
				driver.close();
				Util.sleep(4000);
				driver.switchTo().window(winHandleBefore);
				String Status=json.GetStringFromJsonObject("status",UIResponse);
				if(Status.equalsIgnoreCase("OK")){
				ArrayList<String> TrialType1=json.GetStringArrayElementsFromJSON("trials", "trialType");
				ArrayList<String> TrialTypeProduct=json.GetStringArrayElementsFromJSON("trials", "name");
				
				for(WebElement EachProduct : Products ){
					
				switch(TrialType1.get(count)){
					
				case  "Time" :
					
					Util.printInfo("Trial type is :: "+TrialType1.get(count));
					Util.printInfo("So validating Trial products with Trial type :: "+TrialType1.get(count));
					String RestAndUITrialsProductName=homePage.createFieldWithParsedFieldLocatorsTokens("TrialProductName", TrialTypeProduct.get(count));
					Util.printInfo("Validating Product name from customer portal UI and REST Response ");
					homePage.verifyFieldExists(RestAndUITrialsProductName);
					String DaysLeft=homePage.createFieldWithParsedFieldLocatorsTokens("TrialTypeNameTime", TrialTypeProduct.get(count));
					Util.printInfo("Validating Dates from customer portal UI and REST Response ");
					homePage.verifyFieldExists(DaysLeft);
					break;
				
				case "UsageAndTime" : 
					Util.printInfo("Trial type is :: "+TrialType1.get(count));
					Util.printInfo("So validating Trial products with Trial type :: "+TrialType1.get(count));
					String TrialProductName=homePage.createFieldWithParsedFieldLocatorsTokens("TrialProductName", TrialTypeProduct.get(count));
					Util.printInfo("Validating Product name from customer portal UI and REST Response ");
					homePage.verifyFieldExists(TrialProductName);
					
					String DaysLeftForProducts=homePage.createFieldWithParsedFieldLocatorsTokens("TrialTypeNameTime", TrialTypeProduct.get(count));
					Util.printInfo("Validating Dates and Usage from customer portal UI and REST Response ");
					homePage.verifyFieldExists(DaysLeftForProducts);
					
					String ProductUsage=homePage.createFieldWithParsedFieldLocatorsTokens("TrialTypeUsage", TrialTypeProduct.get(count));
					homePage.verifyFieldExists(ProductUsage);	
					break;
				
				case "Usage" :
					Util.printInfo("Trial type is :: "+TrialType1.get(count));
					Util.printInfo("So validating Trial products with Trial type :: "+TrialType1.get(count));
					String UsageTrialProductName=homePage.createFieldWithParsedFieldLocatorsTokens("TrialProductName", TrialTypeProduct.get(count));
					Util.printInfo("Validating Product name from customer portal UI and REST Response ");
					homePage.verifyFieldExists(UsageTrialProductName);
					
					/*String ProductUsageCloudCredits=homePage.createFieldWithParsedFieldLocatorsTokens("TrialTypeUsage", TrialTypeProduct.get(count));
					homePage.verifyFieldExists(ProductUsageCloudCredits);*/
					
					String TrialProductUnitsUsed=homePage.createFieldWithParsedFieldLocatorsTokens("TrialProductUnitsUsed", TrialTypeProduct.get(count));
					Util.printInfo("Validating Usage from customer portal UI and REST Response ");
					homePage.verifyFieldExists(TrialProductUnitsUsed);
					
					String TrialProductUnitsAvail=homePage.createFieldWithParsedFieldLocatorsTokens("TrialProductUnitsAvail", TrialTypeProduct.get(count));
					homePage.verifyFieldExists(TrialProductUnitsAvail);
					break;
				 }
				count=count+1;
				}
			  }
		 	}
		driver.switchTo().window(winHandleBefore);
		/*driver.close();
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
		driver.close();*/
		
		logoutMyAutodeskPortal();
		driver.close();
	}
	

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
