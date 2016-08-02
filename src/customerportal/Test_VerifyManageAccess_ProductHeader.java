package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

public class Test_VerifyManageAccess_ProductHeader extends CustomerPortalTestBase {
	public Test_VerifyManageAccess_ProductHeader() throws IOException {
		super("Browser",getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyManageAccess_ProductHeaders() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
			Util.printInfo("look for the product, (if it finds atleast 1 install button den click on manage access)");
			Util.sleep(3000);
			mainWindow.select();
			Util.sleep(3000);
			homePage.verifyFieldExists("InstallNowButton");
			String SeatsAct=homePage.getValueFromGUI("manageAccessSeatsOnPAndSPage");
			Util.printInfo("Clicking on the dropdown");
			homePage.click("productDropDown");
			homePage.verifyFieldExists("manageAccess");
			homePage.click("manageAccess");
			homePage.verify();
			String SeatsExp=homePage.getValueFromGUI("manageAccessSeats");
			assertEquals(SeatsAct, SeatsExp);
			homePage.click("manageAccessCloseButton");
				
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