package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyManageUsersNavigation extends CustomerPortalTestBase {
	public Test_VerifyManageUsersNavigation() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyDwnldOtherProductsWindowURL() throws Exception {
		String getStatus;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();	
		
		String newURL = testProperties.getConstant("MANAGEUSERSURL");
		homePage.click("productsAndServices");
		homePage.click("all");
		homePage.click("manageUsers");
		String URL_New = driver.getCurrentUrl();
		assertEquals(URL_New, newURL);		
		mainWindow.select();
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
