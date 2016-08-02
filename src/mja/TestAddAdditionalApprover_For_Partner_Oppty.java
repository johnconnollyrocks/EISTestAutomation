package mja;

import org.junit.After; 
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Oppty;
import common.Page_;
import common.Util;

/**
 * Test class - TestAddAdditionalApprover
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class TestAddAdditionalApprover_For_Partner_Oppty extends MJATestBase {
	public TestAddAdditionalApprover_For_Partner_Oppty() {
//		super();
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce(getBasePartnerURL());
	}

	@Test
	public void TEST_AddAdditionalApprover() throws Exception {
		
		Page_ viewDiscountApprovalRequestPage;
		loginAsPartnerUser(false);
		Oppty oppty = utilCreatePartnerOppty();
		Page_ portalLandingPage = oppty.getPortalLandingPage();
		Page_ viewPartnerOpportunityPage = oppty.getViewOpptyInPortalPage();	
		portalLandingPage.clickAndWait("dealRegACELink", "partnerCenterHome");			
		String opptyName=oppty.createPartnerOpportunity();
		Util.printInfo("Opportunity Name is :"+opptyName);
		oppty.setUrl();
//		viewPartnerOpportunityPage.verifyField("opportunityNumber");
		oppty.addContactsToOpportunity();
		oppty.addRequiredProductsToOpportunity();	
		oppty.registerDealAndConfirm();
		
		open("https://test.salesforce.com");
		login(testProperties.getConstant("OPPTY_STATUS_CHANGE_USER_NAME"), testProperties.getConstant("OPPTY_STATUS_CHANGE_PASSWORD"),false);
		search(opptyName);
		commonPage.clickLinkInRelatedList("opptyNameInopportunitiesRelatedList", opptyName, "opptyNameInopportunitiesRelatedList");
		oppty.changeOpptyStatusToApproved();
		logout();
		open("https://test.salesforce.com");	
		login(testProperties.getConstant("USER_NAME"), testProperties.getConstant("PASSWORD"),false);
				
		search(opptyName);
		commonPage.clickLinkInRelatedList("opptyNameInopportunitiesRelatedList", opptyName, "opptyNameInopportunitiesRelatedList");
				
		utilCreateDiscountApprovalRequest(oppty);
		viewDiscountApprovalRequestPage = oppty.getViewDiscountApprovalRequestPage();
		String DARUrl=viewDiscountApprovalRequestPage.getLocation();
		Util.printInfo("DAR URL is :"+DARUrl);
		String additionalApproverName;

		oppty.displayDiscountApprovalRequestApprovalChain();

		oppty.addAdditionalApprovers();
	
//		//There may be a timing issue in dar.addAdditionalApprovers, as sometimes this is not seen
		viewDiscountApprovalRequestPage.verifyFieldExists("additionalApproversHeader");
//		
//		//Because the "table" in which the additional approvers are listed does not contain a header row (as most tables do),
//		//  the easiest way to grab a value from it is to return the entire column, because we have a flavor of the
//		//  Page_.getTableColumn method that allows us to specify that we do want to return the first row.  (Note that we
//		//  could create that flavor for all of the other table access methods, but that would be overkill, given that
//		//  the tables on viewDiscountApprovalRequestPage are the first header-less tables we have seen thus far.)
//		//  Besides, this approach allows us an easier way forward should we modify the test to allow adding (and verifying)
//		//  multiple additional approvers.
		String actual = viewDiscountApprovalRequestPage.getValueFromGUI("appitionalApproverName");

		String expected = testProperties.getConstant("ADDITIONAL_APPROVER");
		EISTestBase.assertEqualsWithFlags(viewDiscountApprovalRequestPage.getName(), "approverNameInAdditionalApproversTable ", actual, expected);
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


