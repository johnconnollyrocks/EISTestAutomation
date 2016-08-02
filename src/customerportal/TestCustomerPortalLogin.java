package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - TestCustomerPortalLogin
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class TestCustomerPortalLogin extends CustomerPortalTestBase {
	public TestCustomerPortalLogin() throws IOException {
		super("Browser",getAppBrowser());
//				super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void TestVerifyLoginPasswordChangeValidate() throws Exception {
		Util.sleep(5000);
		Util.printInfo("Verifying whether 'customer.stg.autodesk.com' has navigated to the correct URL or Not");
		String openedURL = driver.getCurrentUrl();
		String redirectedURL = "https://".concat(openedURL.split("//")[1].split("/")[0]); 
		assertEquals(redirectedURL, testProperties.getConstant("EXPECTED_URL"));
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		String homePageURL = driver.getCurrentUrl();		
		Util.printInfo("Verifying whether the page has navigated to products and services url after login or not");
		assertEquals(homePageURL, testProperties.getConstant("HOMEPAGE_URL"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();		 
		mainWindow.select();
		
		logoutMyAutodeskPortal();
		Util.sleep(2000);
		Util.printInfo("Verifying whether the page has navigated to accounts url after logging out or Not");
		String logoutURL = driver.getCurrentUrl();
		String logoutRedirectedURL = "https://".concat(openedURL.split("//")[1].split("/")[0]); 
		assertEquals(logoutRedirectedURL, testProperties.getConstant("EXPECTED_URL"));
		
		
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
