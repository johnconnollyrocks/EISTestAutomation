package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_Verify_Online_Map extends CustomerPortalTestBase {
	public Test_Verify_Online_Map() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_OnlineMap() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		homePage.click("onlineMapToggleDrawer");
		String ExpectedDescreption=testProperties.getConstant("ONLINE_MAP_DESCRIPTION");
		String ActualDescription =homePage.getValueFromGUI("onlineMapDescription");
		String[] arr1=ActualDescription.split(",,");
		assertEquals(arr1[1], ExpectedDescreption);
		
		Util.printInfo("verifying the online map image");
		
		String OnlineMapImage=homePage.getAttribute("onlineMapImage", "src");
		String[] arr=OnlineMapImage.split("badges/");
		Util.printInfo(arr[0]);
		Util.printInfo(arr[1]);
//		homePage.verifyFieldExists("onlineMapImage");
		String ImageName=testProperties.getConstant("OnlineMapImage");
		assertEquals(ImageName, arr[1]);
		
		assertEquals(homePage.getValueFromGUI("onliMapProductName"), "Online Map Data");
		
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
