package ddlogin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;
import common.Util;

/**
 * Test class - TestLogInAsInternalBusinessUser
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestLogInAsAccountsAutodesk extends DDLoginTestBase {
	public TestLogInAsAccountsAutodesk() {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_TestLogInAsAccountsAutodesk() throws Exception {
		Page_ viewContactPage;
		Page_ myDocumentsPage;
		loginAsAutoUser();

		DDLogin dd = utilCreateDDObject();

		viewContactPage = dd.getViewContactPage();
		myDocumentsPage = dd.getMyDocumentsPage();

		String locator = viewContactPage.clickAndWaitForPopUpToOpen(
				"downloadProducts", 5000);

		myDocumentsPopUp.setLocator(locator);
		myDocumentsPopUp.select();

		myDocumentsPage.verifyInstance("PRODUCT_DOWNLOAD_PAGE");

		myDocumentsPage.isFieldPresent("downloads");
		Util.printInfo("User successfully logged in");
		logout();
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
