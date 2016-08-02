package IAE;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

public class Test_Verfy_Portal_IAE_Tabs extends IAETestBase{

	public Test_Verfy_Portal_IAE_Tabs() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verfy_Portal_IAE_Tabs() throws Exception {
		Util.printInfo("Logging into customer portal ");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		if(homePage.isFieldVisible("ManageUsers")){
			assertTrue("Successfully logged into IAE ", homePage.isFieldVisible("ManageUsers"));
		}else{
			EISTestBase.fail("Error occured while logging in ");
		}
		Util.printInfo("verifying Dashboard , Products usage , By Day tabs pressent or not in reporting page ");
		homePage.click("reporting");
		if(homePage.isFieldVisible("ReportingPageDashboard")){
			assertTrue("Dashboard tab is exists ", homePage.isFieldVisible("ReportingPageDashboard"));
		}else{
			EISTestBase.fail("Error : Dashboard tab does not exist ");
		}
		
		if(homePage.isFieldVisible("ReportingPageProductUsage")){
			assertTrue("ProductUsage tab is exists ", homePage.isFieldVisible("ReportingPageProductUsage"));
		}else{
			EISTestBase.fail("Error : Product update tab does not exist ");
		}
		
		if(homePage.isFieldVisible("ReportingPageByDay")){
			assertTrue("By Day tab is exists ", homePage.isFieldVisible("ReportingPageByDay"));
		}else{
			EISTestBase.fail("Error : By Day tab does not exist ");
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