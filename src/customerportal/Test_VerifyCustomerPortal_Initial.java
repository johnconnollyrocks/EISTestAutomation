package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyCustomerPortal_Initial extends CustomerPortalTestBase {
	public Test_VerifyCustomerPortal_Initial() throws IOException {
		//super("firefox");
		super("Browser",getAppBrowser());
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyCustomerPortal_Initial() throws Exception {
		String cmPassword;
			
		launchMyAutodeskPortal(CustomerPortalConstants.EMAIL_URL_STG);
		Util.printInfo("Logging into SST mailbox to get the username and password for the new CM");
		loginToSSTMail("itqa@ssttest.net", "Password1");
		cmPassword = searchForCMWelcomeKit(testProperties.getConstant("QUERYCONTACT"));
		logoutOfSSTMail();
		Util.sleep(5000);
    	switch (getEnvironment().trim().toUpperCase()) {
		case "DEV":	{
			launchMyAutodeskPortal(CustomerPortalConstants.BASE_URL_DEV);
			}
		case "STG":
		default:	{
			launchMyAutodeskPortal(CustomerPortalConstants.BASE_URL_STG);
			}		
			loginAsMyAutodeskPortalUserAndChangePassword(testProperties.getConstant("QUERYCONTACT") , cmPassword);
			logoutMyAutodeskPortal();
    	}
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
