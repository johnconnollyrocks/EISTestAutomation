package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_UsageReport_NamedUser_MyUsageInfo extends CustomerPortalTestBase {
	public Test_Verify_UsageReport_NamedUser_MyUsageInfo() throws IOException {
		super("Browser",getAppBrowser());
//		super();
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_UsageReport_ContractInfo() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		
		mainWindow.select();
		Util.printInfo("clicking on Reporting");
		homePage.click("reporting");
		Util.sleep(5000);
		homePage.click("usageReport");
		Util.sleep(5000);
		homePage.verify();
		String storageSpace=homePage.getValueFromGUI("myUsageStorageSpaceAvailable");
		String[] totalSpace=storageSpace.split("of");
		Util.printInfo("Storage space is"+totalSpace[1]);
		
//		String newURL = testProperties.getConstant("LANDING_PAGE_URL");
//		homePage.click("myUserStorageAccessNowButton");
//		Util.sleep(5000);
//		for(String winHandle : driver.getWindowHandles()){
//		    driver.switchTo().window(winHandle);
//		}
//		String URL_New = driver.getCurrentUrl();
//		assertEquals(URL_New, newURL);		
		mainWindow.select();
		logoutMyAutodeskPortal();
		
		Util.printInfo("Now verify that user should only see My Usage tab and All Usage should not be available");
		Util.printInfo("Logging in as end user1 ");
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_1") , testProperties.getConstant("PASSWORD1"));
		
		mainWindow.select();
		Util.printInfo("clicking on Reporting");
		homePage.clickAndWait("reporting","myUsageForEndUser");
		
		homePage.verifyAllExistenceInstance("myUsageForEndUser");
		homePage.verifyAllInstance("MY_USAGE_CHECK");
		mainWindow.select();
		logoutMyAutodeskPortal();
		
		Util.printInfo("Logging in as end user2 ");
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_2") , testProperties.getConstant("PASSWORD2"));
				
		mainWindow.select();
		Util.printInfo("clicking on Reporting");
		homePage.clickAndWait("reporting","myUsageForEndUser");
	
		homePage.verifyAllInstance("MY_USAGE_CHECK");
				
				
	
	
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
