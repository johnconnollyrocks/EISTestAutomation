package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_Verify_Fusion360_Product extends CustomerPortalTestBase {
	public Test_Verify_Fusion360_Product() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_Fusion360_Product() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(20000);
		homePage.waitForFieldPresent("fusion360Product");
		homePage.verify();
		
		Util.printInfo("verifying the Fusion 360 image");
		
		String OnlineMapImage=homePage.getAttribute("fusion360Img", "src");
		String[] arr=OnlineMapImage.split("badges/");
		Util.printInfo(arr[0]);
		Util.printInfo(arr[1]);
//		homePage.verifyFieldExists("onlineMapImage");
		String ImageName=testProperties.getConstant("fusion360Image");
		assertEquals(ImageName, arr[1]);
		homePage.click("firstButtonToggleDrawer");
		Util.sleep(5000);
		homePage.verifyInstance("TOGGLE_DETAILS");
		
		
		
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
