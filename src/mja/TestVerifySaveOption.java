package mja;

import mja.SubscriptionRenewal.CaseName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;

/**
 * Test class - TestVerifySaveOption
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestVerifySaveOption extends MJATestBase {
	public TestVerifySaveOption() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_VerifySaveOption() throws Exception {
	
		CaseName caseName	= getCaseName(MJAConstants.MJA_CASE_NAME_ENUM_CONSTANT_NAME);
		loginAsAutoUser();		
		
		SubscriptionRenewal subRenewal =  utilCreateSubscriptionRenewal(caseName);
		Page_ createSubscriptionRenewalPage = subRenewal.getCreateSubscriptionRenewalPage();
		
		logout();
		loginAsAutoUser(true);
		subRenewal.beforeOptionsPopulate();
		createSubscriptionRenewalPage.verify();
		logout();
		loginAsAutoUser(true);
		subRenewal.beforeOptionsPopulate();
		createSubscriptionRenewalPage.populateInstance("OPTIONS_OVERLAY");
		createSubscriptionRenewalPage.click("saveOptionsButton");
		createSubscriptionRenewalPage.waitForFieldAbsent("saveOptionsButton");
		logout();
		loginAsAutoUser(true);
		subRenewal.beforeOptionsPopulate();
		createSubscriptionRenewalPage.verifyInstance("OPTIONS_OVERLAY");
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
