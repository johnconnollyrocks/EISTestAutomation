package eurp;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verify_Portal_EURP_Dashboard_TokenRates extends EURPTestBase{

	public Test_Verify_Portal_EURP_Dashboard_TokenRates() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verfy_ContractNumbers() throws Exception {
		Util.printInfo("Logging into customer portal ");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		if(homePage.isFieldVisible("ManageUsers")){
			assertTrue("Successfully logged into EURP ", homePage.isFieldVisible("ManageUsers"));
		}else{
			EISTestBase.fail("Error occured while logging in ");
		}
		
		Util.printInfo("Clicking on reporting page");
		homePage.click("reporting");
		
		Util.printInfo("Verifying Token Rates tab");
		
		if(homePage.isFieldVisible("ProductUsage")){
			Util.printInfo("ProductUsage tab pressent in reporting page, so clicking on ProductUsage tab ");
			homePage.click("ProductUsage");
			Util.sleep(40000);
			homePage.verifyFieldExists("SccussfullyNavigatedToProductUsage");
			homePage.verifyFieldExists("TokenRates");
			Util.sleep(4000);
			if(homePage.isFieldVisible("TokenRates")){
				 homePage.verifyFieldExists("TokenRates");
				 }else{
					 EISTestBase.fail("There is a problem in loading Reporting page, please check again ");
				}
			homePage.click("TokenRates");
			Util.sleep(4000);
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle); 
			}
			
			homePage.verifyFieldExists("TokenRatesProductName");
			Util.sleep(4000);
			homePage.verifyFieldExists("ConsumptionRate");
			Util.sleep(4000);
			homePage.click("CloseButton");
		}else{
			EISTestBase.fail("ProductUsage tab doesnot pressent in reporting page, so unable to click on ProductUsage");
		}
		
		
		logoutMyAutodeskPortal();
		Util.printInfo("Successfully logged out ");
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