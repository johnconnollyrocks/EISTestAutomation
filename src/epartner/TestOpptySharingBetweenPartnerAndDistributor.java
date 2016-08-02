package epartner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Page_;

/**
 * Test class - TestOpptySharingBetweenPartnerAndDistributor
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestOpptySharingBetweenPartnerAndDistributor extends EpartnerTestBase {
	public TestOpptySharingBetweenPartnerAndDistributor() {
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(EpartnerConstants.BASE_URL);
		launchSalesforce(getBasePartnerURL());
	}

	@Test
	public void Test_OpptySharingBetweenPartnerAndDistributor() throws Exception {
	
		Partner partner = utilCreatePartnerObject();
		Page_ portalLandingPage = partner.getPortalLandingPage();
		
		loginAsEpartnerUser(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), false);
		partner.setUrl();
		
		portalLandingPage.clickAndWait("dealRegACELink", "partnerCenterHome");			
		
		Partner partner1 = utilCreatePartnerOpportunityObject();
		
		partner1.createOpportunity();
		
		Page_ viewPartnerOpportunityPage1 = partner1.getViewPartnerOpportunityPage();
		
		
		//NOTES TO OFFSHORE - better to set all verification data first, and then make a page-scope verify call.
		//  It is MUCH faster.  You can use instances like AFTER_CREATE_OPPTY, AFTER_ADD_CONTACTS, AFTER_ADD_DISTS,
		//  AFTER_ADD_PRODUCTS, etc.
		
		viewPartnerOpportunityPage1.verifyField("opportunityName");
		
		//NOTES TO OFFSHORE - There is no need to verify this.  In the Partner.createOpportunity method
		//  you scraped it from the GUI.  All we are doing here is verifying that same value.  The value does
		//  not need verifying here, but if the test were to navigate somewhere else (perhaps to the page of
		//  a contact or distributor, etc.) we might want to verify it there
		//viewPartnerOpportunityPage1.setVerificationDataValue("opportunityNumber", partner1.getOpportunityNumber());
		//viewPartnerOpportunityPage1.verifyField("opportunityNumber");
		
		partner1.addContactsToOpportunity();
		
		EISTestBase.switchDriverToFrame(4);
		commonPage.click("saveButton");
		viewPartnerOpportunityPage1.verifyInstance("AFTER_ADD_CONTACTS");
		mainWindow.select();
		
		//NOTES TO OFFSHORE - the page-scope verify call can check for existence as well
//		viewPartnerOpportunityPage1.verifyFieldExists("isPrimaryInOpportunityContactRoles");		
		
		//NOTES TO OFFSHORE - This is not how to verify this.  You are scraping it from the GUI, and then immediately
		//  verifying it against what is on the GUI, which does not accomplish anything.  Instead, set the expected
		//  value in test properties (I did it)
		//String contactEmailAddress = viewPartnerOpportunityPage1.getValueFromGUI("contactEmailInOpportunityContactRolesForDistributor");
		//viewPartnerOpportunityPage1.setVerificationDataValue("contactEmailInOpportunityContactRolesForDistributor", contactEmailAddress);		
		//viewPartnerOpportunityPage1.verifyField("contactEmailInOpportunityContactRolesForDistributor");
		
		partner1.addDistributorsToOpportunity();
		
		//*************************************************************************************************
		//NOTES TO OFFSHORE - This is not how to verify these.  You are scraping it from the GUI, and then immediately
		//  verifying them against what is on the GUI, which does not accomplish anything.  Instead, set the expected
		//values in test properties
//  (I DID NOT DO IT!!!  Please do it)
//		String distributorAccountName  = viewPartnerOpportunityPage1.getValueFromGUI("partnerNameInAssociatedPartners");		
//		viewPartnerOpportunityPage1.setVerificationDataInstanceValue("partnerNameInAssociatedPartners", distributorAccountName, "AFTER_ADD_DISTRIBITORS");
//		
//		String distributorAccountCity 		 = viewPartnerOpportunityPage1.getValueFromGUI("partnerCityInAssociatedPartners");
//		viewPartnerOpportunityPage1.setVerificationDataValue("partnerCityInAssociatedPartners", distributorAccountCity);		
//
//		String distributorAccountCountry 	 = viewPartnerOpportunityPage1.getValueFromGUI("partnerCountryInAssociatedPartners");
//		viewPartnerOpportunityPage1.setVerificationDataValue("partnerCountryInAssociatedPartners", distributorAccountCountry);
//		
//		String distributorAccountPartnerType = viewPartnerOpportunityPage1.getValueFromGUI("partnerTypeInAssociatedPartners");
//		viewPartnerOpportunityPage1.setVerificationDataValue("partnerTypeInAssociatedPartners", distributorAccountPartnerType);
//		
//		
//		viewPartnerOpportunityPage1.verifyField("partnerNameInAssociatedPartners");
//		viewPartnerOpportunityPage1.verifyField("partnerCSNInAssociatedPartners");
//		viewPartnerOpportunityPage1.verifyField("partnerCityInAssociatedPartners");
//		viewPartnerOpportunityPage1.verifyField("partnerCountryInAssociatedPartners");
//		viewPartnerOpportunityPage1.verifyField("partnerTypeInAssociatedPartners");		
		
		EISTestBase.switchDriverToFrame(5);
		viewPartnerOpportunityPage1.verifyInstance("AFTER_ADD_DISTS");
		mainWindow.select();
		
		partner1.addRequiredProductsToOpportunity();
		
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("productInproductsRelatedList", 0, "AFTER_ADD_PRODS");
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("estimatedSeatsInproductsRelatedList", 0, "AFTER_ADD_PRODS");
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("productTypeInproductsRelatedList", 0, "AFTER_ADD_PRODS");
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("licenseTypeInproductsRelatedList", 0, "AFTER_ADD_PRODS");		
//		viewPartnerOpportunityPage1.verifyInstance("AFTER_ADD_PRODS");
		
//		viewPartnerOpportunityPage1.verifyInstance("AFTER_ADD_PRODS");		
		//NOTES TO OFFSHORE - I don't see any verification of products happening
		//  Srikanth specified some on 07/27 - please implement
		
		partner1.registerDealAndConfirm();	
		
		//Srikanth says not to worry about this, because the rules are quite complex
		//String dealStatus = viewPartnerOpportunityPage1.getValueFromGUI("dealStatus");
		//viewPartnerOpportunityPage1.setVerificationDataValue("dealStatus", dealStatus);
		//viewPartnerOpportunityPage1.verifyInstance("AFTER_REGISTER_DEAL");
		
		partner.open();
		
		logoutAsEpartnerUser();
		
		loginAsEpartnerUser(testProperties.getConstant("DIST_USER_NAME"), testProperties.getConstant("DIST_PASSWORD"), false);
		partner.setUrl();
		
		portalLandingPage.clickAndWait("dealRegACELink", "partnerCenterHome");
		
		//We already printed a login message in the call to logoutAsEpartnerUser, above
		//Util.printInfo("Logged in as Distributor and searching for the opportunity number " + partnerOpportunityNumber);
		
//		commonPage.populateField("searchTextInSideBar", partnerOpportunityNumber);
		
		//Moved to Partner.waitForOpptyShare()
		//commonPage.click("goButtoninSidebar");	

/*		final int interval = 2000000;
		
		boolean opened = false;
		long startTime = System.currentTimeMillis();
		long endTime = startTime + interval;
		
		while (System.currentTimeMillis() < endTime) {
			commonPage.populateField("searchAgainBox", partnerOpportunityNumber);
			commonPage.click("searchButton");
			
			if(viewPartnerOpportunityPage1.isFieldPresent("opportunityName")){
				opened = true;
				Util.printInfo("Opportunity number : " + partnerOpportunityNumber + "and the its details have got synced");
				break;
			}
				else{
				opened = false;
				}
					
			}
		
		if(opened == false){
			fail("Opportunity number : " + partnerOpportunityNumber + "and the its details have not visible for distributor");
		}
*/
		
		//This new method contains a version of the above code 
		partner1.waitForOpptyShare();
		
		viewPartnerOpportunityPage1.verify();
//		viewPartnerOpportunityPage1.verifyField("opportunityNumber");
//		viewPartnerOpportunityPage1.verifyField("dealStatus");
//		
		EISTestBase.switchDriverToFrame(4);	
		viewPartnerOpportunityPage1.verifyInstance("AFTER_ADD_CONTACTS");
		mainWindow.select();
		
		EISTestBase.switchDriverToFrame(5);	
//		viewPartnerOpportunityPage1.verifyField("partnerNameInAssociatedPartners");
//		viewPartnerOpportunityPage1.verifyField("partnerCSNInAssociatedPartners");
//		viewPartnerOpportunityPage1.verifyField("partnerCityInAssociatedPartners");
//		viewPartnerOpportunityPage1.verifyField("partnerCountryInAssociatedPartners");
//		viewPartnerOpportunityPage1.verifyField("partnerTypeInAssociatedPartners");
		viewPartnerOpportunityPage1.verifyInstance("AFTER_ADD_DISTS");
		mainWindow.select();
		
		partner.open();
		logoutAsEpartnerUser();
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
