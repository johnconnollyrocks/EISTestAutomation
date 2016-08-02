package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_NavigateToUserManagementFromSubCenter
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_NavigateToUserManagementFromSubCenter extends CustomerPortalTestBase {
	public Test_NavigateToUserManagementFromSubCenter() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchSalesforce(testProperties.getConstant("BASE_URL_STG"));
	}

	@Test
	public void Test_NavigateToUserManagementFromSubCenter() throws Exception {
		custPortallogin(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.clickAndWait("contractAdministration", "manageUsers");
		Util.printInfo("Navigating to Customer Portal application");
		homePage.click("manageUsers");
		Util.sleep(10000);
		homePage.waitForFieldVisible("addUser");
		//Handling IE security certificate Issue
				if(getAppBrowser().equalsIgnoreCase("ie") && (!homePage.isFieldPresent("users")))
					driver.navigate().to("javascript:document.getElementById('overridelink').click()");
				
				//END - Handling IE security certificate Issue
				Util.sleep(2000);
		homePage.verifyFieldExists("addUser");
		Util.printInfo("In Customer Portal Manage Users page");
		Util.sleep(2000);
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
