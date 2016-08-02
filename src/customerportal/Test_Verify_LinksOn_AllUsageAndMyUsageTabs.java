package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_Verify_LinksOn_AllUsageAndMyUsageTabs  extends CustomerPortalTestBase {
	public Test_Verify_LinksOn_AllUsageAndMyUsageTabs() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_LinksOn_AllUsageAndMyUsageTabsOfReporting() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(10000);
		homePage.click("reporting");
		Util.sleep(15000);
	//	String Nocredits=homePage.getValueFromGUI("Nocredits");
		boolean Nocredits=homePage.getValueFromGUI("Nocredits") != null;
		
		if(Nocredits){
			homePage.click("LinkstoggleDrawer");
			homePage.click("Accessclouds");
			String newURL = testProperties.getConstant("pAndSPage");
			Util.printInfo("Clicking on Access your cloud services");
			String winHandleBefore = driver.getWindowHandle();
			Util.sleep(5000);
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);    
			}	
			Util.sleep(5000);
			String URL_New = driver.getCurrentUrl();
			Util.printInfo("Validating the accessNow link navigation");
			assertEquals(URL_New, newURL);
			homePage.refresh();
			Util.sleep(45000);
			homePage.click("reporting");
			Util.sleep(50000);
			homePage.click("LinkstoggleDrawer");
			Util.printInfo("Clicking on Try and learn more");
			homePage.click("learnMore");
			String newURL1 = testProperties.getConstant("learnMore");
			Util.printInfo("Clicking on learnMore cloud services");
			String winHandleBefore1 = driver.getWindowHandle();
			Util.sleep(5000);
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);    
			}	
			Util.sleep(5000);
			String URL_New1 = driver.getCurrentUrl();
			Util.printInfo("Validating the learnMore link navigation");
			assertEquals(URL_New1, newURL1);
			driver.close();
		}
		
		mainWindow.select();
		homePage.click("reporting");
		Util.sleep(30000);
		homePage.click("ExportLink");
		homePage.verify();
		homePage.refresh();
		Util.sleep(50000);
		homePage.click("myUsageTab");
		String newURL1 = testProperties.getConstant("myUsage");
		String winHandleBefore1 = driver.getWindowHandle();
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		String URL_New1 = driver.getCurrentUrl();
		Util.printInfo("Validating the myUsageTab link navigation");
		assertEquals(URL_New1, newURL1);
		
		mainWindow.select();
		homePage.click("reporting");
		Util.sleep(20000);
		homePage.click("toggleDrawer");
		homePage.click("usageByUsersLink");
		String newURL = testProperties.getConstant("usageByUsers");
		String winHandleBefore = driver.getWindowHandle();
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		String URL_New = driver.getCurrentUrl();
		Util.printInfo("Validating the usageByUsers link navigation");
		assertEquals(URL_New, newURL);
		logoutMyAutodeskPortal();
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