package mja;

import java.util.ArrayList;

import org.junit.After; 
import org.junit.Before;
import org.junit.Test;

/**
 * Test class - TestGenerateApprovalChain
 * 
 * @author Ravi Shankar
 * @author : Modified by Brijesh Chavda
 * @version 1.0.0
 */
public final class TestViewUserAndExportAcctID extends MJATestBase {
	public TestViewUserAndExportAcctID() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(getAppServerBaseURL());
		launchSalesforce(testProperties.getConstant("URL_PRD"));
		
	}

	@Test
	public void TEST_ViewUserAndExportAcctID() throws Exception {
		//Can include Selenium and WebDriver commands - but please don't!
		String newUserURL ;
		boolean updateUser = false;
		//loginAsAutoUser();
		login(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		//String[] userEmails = testProperties.getConstant("USER_EMAIL").split("\\;");
		String[] userURLs = testProperties.getConstant("USER_URL").split("\\;");
		ArrayList<String> totalUserURLs = new ArrayList<String>();
		for(int i=0;i<userURLs.length;i++){
			newUserURL=null;
			StringBuffer sb = new StringBuffer();
			newUserURL =sb.append("https://na1.salesforce.com/").append(userURLs[i]).toString(); 
			totalUserURLs.add(newUserURL);
		}

		
		String[] allUsersURLArray = new String[totalUserURLs.size()];
		allUsersURLArray = totalUserURLs.toArray(allUsersURLArray);
//		String userApprovalRequestEmailStatus =  testProperties.getConstant("USER_APPROVAL_REQUEST_EMAIL_STATUS");
				
		String fileName =  testProperties.getConstant("OUTPUT_FILENAME");
				
		//editUpdateUser(userEmails, userApprovalRequestEmailStatus);	
		ViewUserAndExportAcctIDbyURL(allUsersURLArray, fileName);
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
