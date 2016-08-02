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
public final class Test_Verify_UsageReport_EndUser_MyUsage extends CustomerPortalTestBase {
	public Test_Verify_UsageReport_EndUser_MyUsage() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_UsageReport_EndUser_MyUsage() throws Exception {
		
		Util.printInfo("Logging in as End User");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("ENDUSER_USER_NAME") , testProperties.getConstant("ENDUSER_PASSWORD"));
		Util.printInfo("Logged in as End User");
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		
		mainWindow.select();
		Util.printInfo("clicking on Reporting");
		homePage.clickAndWait("reporting", "usageReport");
		
		Util.printInfo("Verifying the My Usage Existence and All Usage Non existence for the  End User Login");
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
