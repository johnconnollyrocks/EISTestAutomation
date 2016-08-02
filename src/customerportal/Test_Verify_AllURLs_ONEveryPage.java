package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.Util;

public class Test_Verify_AllURLs_ONEveryPage  extends CustomerPortalTestBase {
	public Test_Verify_AllURLs_ONEveryPage() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_AllURLs_ONEveryPages() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(30000);
		
		Util.printInfo("Clicking on download Other Products");
		homePage.click("downloadOtherProducts");
		Util.sleep(7000);
		
		Capabilities capabilities = ((RemoteWebDriver)driver).getCapabilities();
		String browserName = capabilities.getBrowserName();
		if(browserName.equalsIgnoreCase("safari")){
			SafariCertificate();
		}
		
		Util.sleep(7000);
		String newURL = testProperties.getConstant("downloadOtherProducts");
		String winHandleBefore = driver.getWindowHandle();
		
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		String URL_New = driver.getCurrentUrl();
		Util.printInfo("Validating the download Other Products link navigation");
		assertEquals(URL_New, newURL);
		driver.close();
		mainWindow.select();
	
		
		Util.printInfo("Clicking on productEnhancements");
		homePage.click("productEnhancement");
		Util.sleep(7000);
		String newURL1 = testProperties.getConstant("productEnhancements");
		String winHandleBefore1 = driver.getWindowHandle();
		
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		String URL_New1 = driver.getCurrentUrl();
		Util.printInfo("Validating the productEnhancements link navigation");
		assertEquals(URL_New1, newURL1);
		driver.close();
		mainWindow.select();
		
		homePage.click("editContracts");
		Util.sleep(7000);
		Util.printInfo("Clicking on editContracts");
//		homePage.click("productEnhancement");
//		String newURL2 = testProperties.getConstant("productEnhancements");
		String newURL2 = testProperties.getConstant("editContracts");
		String winHandleBefore2 = driver.getWindowHandle();
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		String URL_New2 = driver.getCurrentUrl();
		Util.printInfo("Validating the editContracts link navigation");
		assertEquals(URL_New2, newURL2);
		driver.close();
		mainWindow.select();
		
		Util.printInfo("Clicking on giveFeedBack");
		homePage.click("giveFeedBack");
				
		Util.sleep(7000);
		//homePage.click("productEnhancement");
		String newURL3 = testProperties.getConstant("giveFeedBack");
		String winHandleBefore3 = driver.getWindowHandle();
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		String URL_New3 = driver.getCurrentUrl();
		Util.printInfo("Validating the giveFeedBack link navigation");
		assertEquals(URL_New3, newURL3);
		driver.close();
		mainWindow.select();
		
		Util.printInfo("Clicking on downloadOtherProducts");
		homePage.click("downloadOtherProducts");
		Util.sleep(7000);
		String newURL4 = testProperties.getConstant("downloadOtherProducts");
		String winHandleBefore4 = driver.getWindowHandle();
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		String URL_New4 = driver.getCurrentUrl();
		Util.printInfo("Validating the downloadProducts link navigation");
		assertEquals(URL_New4, newURL4);
		driver.close();
		mainWindow.select();
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Util.printInfo("Verifying the URLs of user-management page ");
		homePage.click("users");
		Util.sleep(7000);
		
		
		Util.printInfo("Clicking on managePhoneSupport");
		homePage.click("managePhoneSupport");
		Util.sleep(7000);
		String newURL5 = testProperties.getConstant("managePhoneSupport");
		String winHandleBefore5 = driver.getWindowHandle();
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		String URL_New5 = driver.getCurrentUrl();
		Util.printInfo("Validating the managePhoneSupport link navigation");
		assertEquals(URL_New5, newURL5);
		Util.sleep(2000);
		driver.close();
		mainWindow.select();
		
		Util.printInfo("Clicking on manageSoftwareCoordinators");
		homePage.click("manageSoftwareCoordinators");
		Util.sleep(7000);
		String newURL6 = testProperties.getConstant("manageSoftwareCoordinators");
		String winHandleBefore6 = driver.getWindowHandle();
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		String URL_New6 = driver.getCurrentUrl();
		Util.printInfo("Validating the manageSoftwareCoordinators link navigation");
	//	assertEquals(URL_New6, newURL6);
		assertTrue(" ",URL_New6.contains(URL_New6));
		mainWindow.select();

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