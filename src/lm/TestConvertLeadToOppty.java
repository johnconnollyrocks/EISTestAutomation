package lm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Oppty;
import common.Page_;

/**
 * Test class - TestConvertLeadToOppty
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestConvertLeadToOppty extends LMTestBase {
	public TestConvertLeadToOppty() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_ConvertLeadToOppty() throws Exception {
		//Can include Selenium and WebDriver commands - but please don't!
		
		loginAsAutoUser();
		
		Lead lead = utilCreateLead();
		lead.open();
		
		//Two options here.  The first involves a bit more Java, but 
		//  takes advantage of info panel caching that happens during
		//  page-scope verification.  For this test, the verification
		//  time is reduced from 28 seconds to 12.
		//Option 1
			Page_ viewLeadPage = lead.getViewLeadPage();
			viewLeadPage.setVerificationDataValue("leadOwner", lead.getOwner());
			viewLeadPage.verify();
		//Option 2
			//lead.getViewLeadPage().verify();
			//lead.getViewLeadPage().verifyField("leadOwner", lead.getOwner());
		
		Oppty oppty = utilCreateOpptyObject();
		lead.convertToOppty(oppty);
		
		oppty.getViewOpptyPage().verify();
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
