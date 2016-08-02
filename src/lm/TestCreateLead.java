package lm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Page_;

/**
 * Test class - TestCreateLead
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateLead extends LMTestBase {
	public TestCreateLead() {
		super("firefox");
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_CreateLead() throws Exception {
		
		loginAsAutoUser();

		Lead lead = utilCreateLead();
		lead.open();
		
		Page_ viewLeadPage = lead.getViewLeadPage();
		
		viewLeadPage.verify();

		//If the OWNER constant IS NOT defined in test properties, we will verify that the
		//  lead owner is the name of the user who was logged in when the lead was created;
		//  if the OWNER constant IS defined in test properties, we verified its value in
		//  the verify() call above opty Opty 
		if (!testProperties.constantExists("OWNER")) {
			viewLeadPage.verifyFieldStartsWith("leadOwner", getCurrentUserName());
		}
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
