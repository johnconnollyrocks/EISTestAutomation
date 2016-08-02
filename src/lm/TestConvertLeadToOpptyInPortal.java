package lm;

import lm.LMConstants.CreateFrom;

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
public final class TestConvertLeadToOpptyInPortal extends LMTestBase {
	public TestConvertLeadToOpptyInPortal() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_ConvertLeadToOpptyInPortal() throws Exception {
			
		loginAsAutoUser();
		CreateFrom createFrom 	= getInterfaceType(LMConstants.LM_CREATE_FROM_ENUM_CONSTANT_NAME);	
		
    	Lead lead = utilCreateLead(createFrom);
	    lead.open();
		
		Page_ viewLeadPage = lead.getViewLeadPage();
		viewLeadPage.verify();		
		viewLeadPage.verifyFieldStartsWith("leadOwner", testProperties.getConstant("OWNER"));
		
		//logoutAsLMUser();
		logout();
		
		//launchSalesforce(LMConstants.BASE_URL);		
		launchSalesforce(getBasePartnerURL());
		loginAsLMPartnerUser(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), false);	
		
		lead.waitForRouting();
		
		//Verify Fields before converting the lead to opportunity. 
		Page_ viewLeadInPortalPage = lead.getViewLeadInPortalPage();
		
		//NOTES TO OFFSHORE - 08/03/2012
		//  Remember to open the object before verifying it.  And note that we are calling openInPortal(),
		//  not open()
		lead.openInPortal();
		viewLeadInPortalPage.setVerificationDataValue("leadID", lead.getLeadID());
		viewLeadInPortalPage.verify();
		viewLeadInPortalPage.verifyFieldStartsWith("leadOwner",testProperties.getConstant("OWNER"));

		//NOTES TO OFFSHORE - 08/03/2012
		//  Whenever we create an SFDC object, we should have an instance of an SFDCObject subclass to
		//  encapsulate it.  For instance, when we create a Lead, we make a call like this
		//    Lead lead = utilCreateLead(createFrom);
		//  However, we are creating an Oppty here by calling lead.convertToOpptyInPortal() instead of
		//    Oppty oppty = utilCreateOppty()
		//  so how do we get an instance of the Oppty class? We do it in a way very similar to how we do it in
		//  TestConvertLeadToOppty.java (see lead.convertToOpptyInPortal for more notes)
		//lead.convertToOpptyInPortal();		
		//lead.convertToOpptyInPortal();		
		Oppty oppty = utilCreateOpptyObject();
		lead.convertToOpptyInPortal(oppty);

		//NOTES TO OFFSHORE - 08/03/2012
		//  Remember to open the object before verifying it.  And note that we are calling openInPortal(),
		//  not open()
		//
		//NOTES TO OFFSHORE - 08/03/2012 (following comment added 08/06/2012)
		//  See comments below for the reason these lines were commented out
		//lead.openInPortal();
		//viewLeadInPortalPage.verifyInstance("AFTER_CONVERT_TO_OPPTY");
		
		//NOTES TO OFFSHORE - 08/03/2012
		//  I don't see you opening the Oppty and verifying it.  TestConvertLeadToOppty.java does that,
		//  and we need to do it here as well.  Please add that code, and be sure to open it in portal
		//  (by calling oppty.openInPortal()) and verify it in the portal (on oppty.getViewOpptyInPortalPage())
		//NOTES TO OFFSHORE - 08/03/2012 (following comment added 08/06/2012)
		//  !!! Note below that I have modified test properties such that we are verifying two fields in the oppty,
		//  so there is nothing you need to do (ignore above comment)
		//
		//NOTES TO OFFSHORE - 08/03/2012 (following comment added 08/06/2012)
		//*** Actually, I did find one field that you were verifying after converting to oppty - opptyName.
		//  However, you were verifying it on viewLeadInPortalPage.  Since I moved that field to the
		//  Page_ViewOpptyInPortal.properties page, in common, I changed the code to verify it there.
		//  But that leaves nothing to verify on viewLeadInPortalPage.  Therefore I have commented
		//  the lines that verify viewLeadInPortalPage (above) and added lines to verify viewOpptyInPortalPage
		oppty.openInPortal();
		Page_ viewOpptyInPortalPage = oppty.getViewOpptyInPortalPage();
		viewOpptyInPortalPage.verify();
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
