package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Test_Verify_GetCurrent_And_PreviousVersion_OfProduct extends CustomerPortalTestBase{

	public Test_Verify_GetCurrent_And_PreviousVersion_OfProduct() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verify_GetCurrent_And_PreviousVersion_Product() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.click("autoCADInventorLTSuite");
		
		String ProductVersion=homePage.getValueFromGUI("ProductVerion");
		
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