package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.Util;

/**
 * Test class - Test_VerifyInstallNowButton
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyServicesDescription extends CustomerPortalTestBase {
	public Test_VerifyServicesDescription() throws IOException {
		super("Browser",getAppBrowser());
//		super();
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyInstallNowButton() throws Exception {
		driver.manage().deleteAllCookies();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.printInfo("Verifying services description are available or not");
		homePage.clickAndWait("services","firstButtonToggleDrawer");
		Util.sleep(5000);
		Util.printInfo("Clicking on the first contract toggle drawer button");
		
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = capabilities.getBrowserName();	
		
  if(!browserName.equalsIgnoreCase("safari")) {
			
	homePage.click("firstButtonToggleDrawer");
			
			}
			else{	
		
		         try { 
		        	 
		         String firstButtonToggleDrawer = "var click_ev = document.createEvent(\"MouseEvents\"); click_ev.initEvent(\"click\", true, true); document.getElementByXPath(\"//div[@class='product-list']//article[1]//button[contains(@class,'details-toggle')]\").dispatchEvent(click_ev);";
		         JavascriptExecutor js = (JavascriptExecutor) driver;
		         js.executeScript(GetXpath); 
		         js.executeScript(firstButtonToggleDrawer); 
		         Util.sleep(5000);
		
		         } catch (Exception e){
		        	 e.printStackTrace();
		
		         }
		}
		
		
		
//		homePage.verifyFieldExists("storageDescription");
		homePage.click("secondButtonToggleDrawer");
		homePage.verifyFieldExists("autocad360Description");
		homePage.click("thirdButtonToggleDrawer");
		homePage.verifyFieldExists("autocad360MobileDescription");
//		homePage.click("fourthButtonToggleDrawer");
//		homePage.verifyFieldExists("liveMapDataDescription");
		logoutMyAutodeskPortal();   
	}

	@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.manage().deleteAllCookies();
		Util.sleep(2000);
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}
}
