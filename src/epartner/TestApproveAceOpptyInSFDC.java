package epartner;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import common.EISTestBase;
import common.Page_;
import common.Util;

/**
 * Test class - TestOpptySharingBetweenPartnerAndDistributor
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestApproveAceOpptyInSFDC extends EpartnerTestBase {
	public TestApproveAceOpptyInSFDC() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(EpartnerConstants.BASE_URL);
		launchSalesforce(getBasePartnerURL());
	}

	@Test
	public void Test_ApproveAceOpptyInSFDC() throws Exception {
		
		Partner partner1 = utilCreatePartnerObject();
		Page_ portalLandingPage = partner1.getPortalLandingPage();
		
		loginAsEpartnerUser(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), false);
		partner1.setUrl();
		
		portalLandingPage.clickAndWait("dealRegACELink", "partnerCenterHome");			
		
		Partner partner = utilCreatePartnerOpportunityObject();
		
		partner.createOpportunities();
		
		Page_ viewPartnerOpportunityPage1 = partner.getViewPartnerOpportunityPage();
		
		viewPartnerOpportunityPage1.verifyField("opportunityName");
		
		
		partner.addContactsToOpportunity();
		
		EISTestBase.switchDriverToFrame(4);
		commonPage.click("saveButton");
		viewPartnerOpportunityPage1.verifyInstance("AFTER_ADD_CONTACTS");
		mainWindow.select();

		partner.addDistributorsToOpportunity();
		mainWindow.select();

		partner.addRequiredProductsToOpportunity();
		
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("productInproductsRelatedList", 0, "AFTER_ADD_PRODS");
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("estimatedSeatsInproductsRelatedList", 0, "AFTER_ADD_PRODS");
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("productTypeInproductsRelatedList", 0, "AFTER_ADD_PRODS");
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("licenseTypeInproductsRelatedList", 0, "AFTER_ADD_PRODS");		
//		viewPartnerOpportunityPage1.verifyInstance("AFTER_ADD_PRODS");
		
		
		partner.registerDealAndConfirm();	
		
		
		partner1.open();
		
		
		logoutAsEpartnerUser();
		
		
		launchSalesforce(getAppServerBaseURL());

		
		loginAsAutoUser(true);

		partner.searchOppty(partner.getOpportunityNumber());

		commonPage.clickLinkInRelatedList("opptyNameInopportunitiesRelatedList", 0);
		
		Util.printInfo("Changing the deal status of the oppty : " + partner.getOpportunityNumber() + " to Approved" );
		commonPage.clickToSubmit("editButton", "dealStatusList");
		commonPage.populateField("dealStatusList", "Approved");
		
		//Handling alert
		driver.findElement(By.xpath("//td[@id='topButtonRow']/input[normalize-space(@class)='btn' and normalize-space(@value)='Save']")).click();
		if(commonPage.isAlertPresent()){
			commonPage.acceptAlert();
		}
	//	commonPage.click("saveButton");
		
		
		
		Util.printInfo("Changed the deal status of the oppty : " + partner.getOpportunityNumber() + " to Approved" );
		
		Util.printInfo("Changing the deal status of the oppty : " + partner.getOpportunityNumber() + " to Ordered" );
		commonPage.clickToSubmit("editButton", "dealStatusList");
		commonPage.populateField("dealStatusList", "Ordered");
		commonPage.clickToSubmit("saveButton", "editButton");
		Util.printInfo("Changed the deal status of the oppty : " + partner.getOpportunityNumber() + " to Ordered" );		
		String shipToCSN = commonPage.getValueFromGUI("CSN");
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("OpptyNumber", partner.getOpportunityNumber());
		map.put("SoldToParty", testProperties.getConstant("SOLDTO"));
		map.put("ShipToParty", shipToCSN);
		Util.writeToExcel(testProperties.getConstant("OUTPUT_FILENAME"), map);
		
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
