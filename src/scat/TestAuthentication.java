package scat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class - TestAuthentication
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestAuthentication extends SCATTestBase {
	public TestAuthentication() {
		super("Browser" , "chrome");
	}

	@Before
	public void setUp() throws Exception {

		launchSCAT(SCATConstants.BASE_URL);
	}

	@Test
	public void TEST_Login() throws Exception {

		// Method used to login to the application
		loginAsSCATUser();

		// Method used to logout to the application
		logoutAsSCATUser();

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
