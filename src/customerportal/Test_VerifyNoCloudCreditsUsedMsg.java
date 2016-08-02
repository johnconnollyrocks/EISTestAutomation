package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_VerifyNoCloudCreditsUsedMsg
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyNoCloudCreditsUsedMsg extends CustomerPortalTestBase {
	public Test_VerifyNoCloudCreditsUsedMsg() throws IOException {
		super("Browser",getAppBrowser());
	//	super();
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyNoCloudCreditsUsedMsg() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		
		mainWindow.select();
		if(loginPage.isFieldPresent("acceptButton")){
	    	loginPage.click("acceptButton");
	    	Util.sleep(5000);
	    }
		Util.printInfo("clicking on Reporting");
		homePage.clickAndWait("reporting","myUsageButton");
		homePage.clickAndWait("byUsers","noCloudCreditsConsumedMsg");
		homePage.verify();
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
