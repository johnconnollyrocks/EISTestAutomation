package lm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lm.LMConstants;
import lm.LMConstants.CreateFrom;

import common.EISTestBase;
import common.Page_;


/**
 * Test class - TestCreateLeadInPortal
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateLeadInPortal extends LMTestBase {
	public TestCreateLeadInPortal() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(LMConstants.BASE_URL);
		launchSalesforce(getBasePartnerURL());
	}

	@Test
	public void TEST_CreateLeadInPortal() throws Exception {
 		//NOTES TO OFFSHORE - 08/03/2012
		//  By definition, this test creates the Lead in the portal, so we don't need this.
		//  You probably grabbed this code from ss/TestCreateCaseInPortal, and are using 
		//  CreateFrom here because this method also creates an object in the portal.  The
		//  difference, though, is that while in ss there are multiple portal types and
		//  therefore TestCreateCaseInPortal can be used to create the case from different places,
		//  in this method it can only be created from one place - the only portal
		CreateFrom createFrom 	= getInterfaceType(LMConstants.LM_CREATE_FROM_ENUM_CONSTANT_NAME);		
					
		loginAsLMPartnerUser(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), false);		

		Lead lead = utilCreateLead(createFrom);

		//NOTES TO OFFSHORE - 08/03/2012
		//  open in portal
		//lead.open();
		lead.openInPortal();
		
		Page_ viewLeadInPortalPage = lead.getViewLeadInPortalPage();
		
		viewLeadInPortalPage.verify();

		viewLeadInPortalPage.verifyFieldStartsWith("leadOwner", testProperties.getConstant("OWNER"));
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
