/**
 * @author saxenas
 * 
 * =================
 * New Files Created
 * =================
 *  # epartner/				TestEndCustAccountNoVPDataCreation.java
 *  # epartner/testdata/	Test_DataCreationAceOpptyReservedNoVP.properties
 *
 * =======================
 * Existing Files Modified
 * =======================
 *  # epartner/testdata/	TestManifest.properties			  [[TEST_CREATE_ECC_REJECT_BY_DEAL_OPPS_BY_EMAIL_PROPERTIES_FILE = Test_CreateECCAndRejectByDealOppsThroughEmail.properties]]
 *  # epartner/resource/	Page_EndCustAgreement.properties  [[Added fields 
 *  # common/				Page.java & Page_.java			  [[Added method **verifyIsFieldMandatory(fieldName [expected]) **verifyFieldRequiredness(fieldName, expected)]]
 */

package epartner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import common.Page_;
import common.Util;



public final class TestEndCustAccountNoVP extends EpartnerTestBase {
	
	public TestEndCustAccountNoVP() {
		super("chrome");
	} 
	
	@Before
	public void setUp() throws Exception {
		launchPortal(getBasePartnerURL());
	}

	@Test
	public void Test_ApproveAceOpptyInSFDC() throws Exception {
		
		//0. PAGE CREATION
		Partner partner = utilCreatePartnerOpportunityObject();	
		Page_ createPartnerOpportunityPage = partner.getCreatePartnerOpportunityPage();
		
		//18. LOGIN TO PARTNER CENTER AND CLICK ON DEAL REG (ACE)
		loginAsEpartnerUser(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), false);
		partner.setUrl();
		createPartnerOpportunityPage.clickAndWait("dealRegACELink", "partnerCenterHome");
		
		//19. GO TO OPPORTUNITIES
		driver.switchTo().frame("navigationLinks");
		driver.findElement(By.linkText("Opportunities")).click();
		driver.switchTo().defaultContent();
		
		//20. CLICK NEW AND ENTER DETAILS
		createPartnerOpportunityPage.clickAndWait("newButton", "saveButton");
		createPartnerOpportunityPage.populateInstance("OPPORTUNITY_DATA");
		createPartnerOpportunityPage.clickAndWait("saveButton", "editButton");
		
		//21. ADD CONTACT
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@title,'OpportunityContactRole')]")));
		createPartnerOpportunityPage.clickAndWait("addContactsButton", "contactSelectionRadioButton");
		driver.switchTo().defaultContent();
		createPartnerOpportunityPage.populateField("contactSelectionRadioButton", "select");
		createPartnerOpportunityPage.clickAndWait("saveButton", "editButton");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@title,'OpportunityContactRole')]")));
		commonPage.click("saveButton");
		driver.switchTo().defaultContent();
		
		//22.  ADD AND SELECT PRODUCT, ACCEPT ALERT
		createPartnerOpportunityPage.click("addProductButton");
//		createPartnerOpportunityPage.verifyAlert(testProperties.getConstant("USAGE_TYPE_ALERT"));
//		createPartnerOpportunityPage.acceptAlert();
		createPartnerOpportunityPage.check("firstProductLineCheckbox");
		createPartnerOpportunityPage.populateField("firstProductSeatsText", "1");
		createPartnerOpportunityPage.clickAndWait("saveButton", "editButton");
		
		//23. TRY TO REGISTER DEAL AND CHECK ERROR
		createPartnerOpportunityPage.click("registerDealButton");
		createPartnerOpportunityPage.verifyAlert(testProperties.getConstant("REGISTER_ALERT"));
		createPartnerOpportunityPage.acceptAlert();
		createPartnerOpportunityPage.verifyFieldContains("messageField", testProperties.getConstant("NOTE_MESSAGE"));
		
		//24. CANCEL; CHECK NULL; THEN OK
		createPartnerOpportunityPage.clickAndWait("cancelButton", "registerDealButton");
		createPartnerOpportunityPage.verifyField("dealStatus", "None");
		createPartnerOpportunityPage.click("registerDealButton");
		createPartnerOpportunityPage.verifyAlert(testProperties.getConstant("REGISTER_ALERT"));
		createPartnerOpportunityPage.acceptAlert();
		createPartnerOpportunityPage.verifyFieldContains("messageField", testProperties.getConstant("NOTE_MESSAGE"));
		createPartnerOpportunityPage.clickAndWait("confirmButton", "editButton"); 
		
		//25. VERIFY RESULT AND VALUES
		Util.printInfo("Expected Outcome: Opportunity should be " + testProperties.getConstant("TEST_EXPECTATION"));
		createPartnerOpportunityPage.verifyInstance("OPPORTUNITY_VERIFY");
		createPartnerOpportunityPage.verifyFieldContains("dealRejectionReasonCode", testProperties.getConstant("DEAL_REJECTION_REASON_CODE_A"));
		createPartnerOpportunityPage.verifyFieldContains("dealRejectionReasonCode", testProperties.getConstant("DEAL_REJECTION_REASON_CODE_B"));
		
		//26. LOG OUT OF PC
		createPartnerOpportunityPage.clickAndWait("partnerCenterHome", "logout");
		createPartnerOpportunityPage.clickAndWait("logout", "privacyPolicy");
		

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


