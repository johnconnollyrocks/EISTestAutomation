package mja;

import org.junit.After; 
import org.junit.Before;
import org.junit.Test;

/**
 * Test class - TestEditUpdateUser
 * 
 * @author Ravi Shankar
 * @author : Modified by Brijesh Chavda
 * @version 1.0.0
 */
public final class TestEditUpdateUser extends MJATestBase {
	public TestEditUpdateUser() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(getAppServerBaseURL());
		launchSalesforce(testProperties.getConstant("URL_PRD"));
		
	}

	@Test
	public void TEST_EditUpdateUser() throws Exception {
		//Can include Selenium and WebDriver commands - but please don't!
		boolean updateUser = false;
		//loginAsAutoUser();
		login(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		//String[] userEmails = testProperties.getConstant("USER_EMAIL").split("\\;");
		String[] userURLs = testProperties.getConstant("USER_URL").split("\\;");
		String userApprovalRequestEmailStatus =  testProperties.getConstant("USER_APPROVAL_REQUEST_EMAIL_STATUS");
				
		//editUpdateUser(userEmails, userApprovalRequestEmailStatus);	
		editUpdateUserbyURL(userURLs, userApprovalRequestEmailStatus);
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
