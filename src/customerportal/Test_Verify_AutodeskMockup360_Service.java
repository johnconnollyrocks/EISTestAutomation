package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test_Verify_AutodeskMockup360_Service extends CustomerPortalTestBase {
	public Test_Verify_AutodeskMockup360_Service() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_AutodeskMockup360() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.verifyFieldExists("mockUp360Service");
		homePage.click("mockUpToggleDrawer");
		homePage.verifyFieldExists("mockUpContractNumber");
		homePage.verifyFieldExists("mockUpAccessButton");
		
		String ExpectedDescreption=testProperties.getConstant("MOCKUP_360_DESCRIPTION");
		String ActualDescription =homePage.getValueFromGUI("mockUpDescription");
		assertEquals(ActualDescription, ExpectedDescreption);


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
