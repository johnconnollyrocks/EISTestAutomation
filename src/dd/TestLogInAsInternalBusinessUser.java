package dd;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;

/**
 * Test class - TestLogInAsInternalBusinessUser
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestLogInAsInternalBusinessUser extends DDTestBase {
	public TestLogInAsInternalBusinessUser() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(DDConstants.BASE_URL);
		launchSalesforce();
	}

	@Test
	public void TEST_LogInAsInternalBusinessUser() throws Exception {
		Page_ viewContactPage;

		loginAsAutoUser();
		loginAsDDUser();
		
		DD dd = utilCreateDDObject();
		
		viewContactPage = dd.getViewContactPage();
		
		
		viewContactPage.verify();
		
		logout();
	}

	@After
	public void tearDown() throws Exception {
		//Close the browser. Call stop on the WebDriverBackedSelenium instance
		//  instead of calling driver.quit(). Otherwise, the JVM will continue
		//  running after the browser has been closed.
		driver.quit();
		
		//TODO  Figure out how to determine if the test code has failed in a
		//  manner other than by EISTestBase.fail() being called.  Otherwise,
		//  finish() will always print the default passed message to the console.
		finish();
	}
}
