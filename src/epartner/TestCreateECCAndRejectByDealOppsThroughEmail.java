/**
 * @author saxenas
 * 
 * =================
 * New Files Created
 * =================
 *  # epartner/				TestCreateECCAndRejectByDealOppsThroughEmail.java
 *  # epartner/testdata/	Test_CreateECCAndRejectByDealOppsThroughEmail.properties
 *
 * =======================
 * Existing Files Modified
 * =======================
 *  # epartner/testdata/	TestManifest.properties			  [[TEST_CREATE_ECC_REJECT_BY_DEAL_OPPS_BY_EMAIL_PROPERTIES_FILE = Test_CreateECCAndRejectByDealOppsThroughEmail.properties]]
 *  # epartner/resource/	Page_EndCustAgreement.properties  [[Added fields 
 *  # common/				Page.java & Page_.java			  [[Added method **verifyIsFieldMandatory(fieldName [expected]) **verifyFieldRequiredness(fieldName, expected)]]
 */

package epartner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.Account;
import common.EISConstants;
import common.EISTestBase;
import common.Page_;
import common.Util;
@SuppressWarnings("unused")



public final class TestCreateECCAndRejectByDealOppsThroughEmail extends EpartnerTestBase 
{
	
	public TestCreateECCAndRejectByDealOppsThroughEmail() 
	{
		//super();
		super("chrome");
		//Util.debugMode = true;
	}
	
	@Before
	public void setUp() throws Exception 
	{
		launchSalesforce();
	}

	@Test
	public void Test_OpenEndCustomerAccountAndVerifyDealOppsEmailRejection() throws Exception 
	{
	
		Partner partner = utilCreatePartnerOpportunityObject();
		
		List<String> listOfItemsToCheckInEmail = new ArrayList<String> (Arrays.asList (
			testProperties.getConstant("ACCOUNT_NAME"),
			testProperties.getConstant("ACCOUNT_CSN"),
			testProperties.getConstant("REQUEST_TYPE"),
			testProperties.getConstant("AGREEMENT_TYPE"),
			testProperties.getConstant("VALIDITY"),
			"Extra Territory Right", /*exhibit type*/
			testProperties.getConstant("EMAIL_BODY")
		)); //contractExhibitName added later from GUI
		
		List<String> listOfItemsToCheckInRejectionEmail = new ArrayList<String> (Arrays.asList (
			testProperties.getConstant("EMAIL_REJECTION_LINE") + " " + testProperties.getConstant("DEAL_OPPS_NAME"),
			"Please liaise with them directly. Note that no contract will be prepared until you submit a new request that get fully approved.",
			"Reason:" + testProperties.getConstant("REJECTION_REASON"), /*reason for rejection*/
			"Full details of the new request including subsidiary list and exhibit options can be found here"
		));
			
		//0. SET UP ECC PAGE
		Page_ endCustomerContractPage = partner.getCreateEndCustomerContractPage();
		
		//1. LOGIN AS SALES REP AND VERIFY GEO IS APAC
		login(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), true);
		partner.setUrl();		
		verifySfdcUserGeo(testProperties.getConstant("PARTNER_GEO"));
		
		//2. OPEN END CUSTOMER ACCOUNT
		search(testProperties.getConstant("ACCOUNT_CSN"));
		commonPage.clickLinkInRelatedList("accountCsnInAccountsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInAccountsRelatedList");

		//3. CLICK ON NEW END CUSTOMER AGREEMENT
		endCustomerContractPage.clickAndWait("newEndCustomerAgreement", "autodeskAgreementNo");
		
		//4.VERIFY DEFAULT STATUS IS "NEW REQUEST" AND DEFAULT REQUIRED FIELDS
		endCustomerContractPage.verifyField("status", testProperties.getConstant("STATUS"));
		endCustomerContractPage.verifyFieldIsRequired("account");
		endCustomerContractPage.verifyFieldIsRequired("validity");
		endCustomerContractPage.verifyFieldIsRequired("startDate");
		endCustomerContractPage.verifyFieldIsRequired("requestType");
		endCustomerContractPage.verifyFieldIsRequired("agreementType");
				
		//5.POPULATE FIELDS AND SAVE AND VERIFY NO ERROR APPEARS
		endCustomerContractPage.populate();
		commonPage.click("saveButton");
		endCustomerContractPage.waitForFieldPresent("autodeskAgreementNo");
		endCustomerContractPage.verifyFieldNotVisible("invalidDataError");
		
		//6. VERIFY AUTODESK AGREEMENT NUMBER IS ALWAYS CHANGED TO "ACCOUNT NAME-NEW REQUEST"; VERIFY NAMED ACCOUNT GROUP
		endCustomerContractPage.verifyField("autodeskAgreementNo", testProperties.getConstant("ACCOUNT_NAME")+"-New Request");
		endCustomerContractPage.clickAndWait("editButton", "autodeskAgreementNo");
		endCustomerContractPage.populateField("autodeskAgreementNo", testProperties.getConstant("AGREEMENT_NO"));
		commonPage.click("saveButton");
		endCustomerContractPage.verifyField("autodeskAgreementNo", testProperties.getConstant("ACCOUNT_NAME")+"-New Request");
		endCustomerContractPage.verifyField("namedAccountGroup", testProperties.getConstant("NAMED_ACCOUNT_GROUP"));
		
		//7.  EDIT NOTES AND ATTACHMENT; AND VERIFY END CUSTOMER CONTRACTS FIELD IS READ ONLY
		endCustomerContractPage.clickAndWait("attachFileButton", "endCustomerContract");
		//*****is the following verification enough for read only?
		endCustomerContractPage.verifyFieldExists("endCustomerContract");
		endCustomerContractPage.clickAndWait("endCustomerContract", "submitForApproval");
		
		
		//8. ADD ETR EXHIBIT & VERIFY MANDATORY FIELDS
		/* Required Bar appears for first consultingIncluded, etrServerAdminCountry and paymentTypeETRFees
		 * after selecting ETR as exhibitType. But validity and etrOptions don't give Required Bar at all 
		 * and error is given only after we attempt to submit with just the previous 3 fields filled in.
		 */
		endCustomerContractPage.clickAndWait("newAgreementExhibit", "exhibitType");
		endCustomerContractPage.populateField("exhibitType", "Extra Territory Right");
		
		endCustomerContractPage.verifyFieldIsRequired("consultingIncluded");
		endCustomerContractPage.verifyFieldIsRequired("etrServerAdminCountry");
		endCustomerContractPage.verifyFieldIsRequired("paymentTypeETRFees");
		endCustomerContractPage.populateInstance("AGREEMENT_EXHIBIT_DATA_1");
		commonPage.click("saveButton");
		
		endCustomerContractPage.verifyFieldIsRequired("validity");
		endCustomerContractPage.verifyFieldIsRequired("etrOptions");
		endCustomerContractPage.populateInstance("AGREEMENT_EXHIBIT_DATA_2");
		commonPage.click("saveButton");
		
		//9. GET EXHIBIT NUMBER; SUBMIT FOR APPROVAL AND ACCEPT ALERT
		listOfItemsToCheckInEmail.add (endCustomerContractPage.getValueFromGUI("contractExhibitName"));
		endCustomerContractPage.clickAndWait("endCustomerAgreement", "submitForApproval");
		endCustomerContractPage.click("submitForApproval");
		
		Util.printInfo("Alert message is: " + endCustomerContractPage.getAlert());
		endCustomerContractPage.acceptAlert();
		Util.sleep(10000);
		//8. VERIFY APPROVAL STATES AND ASSIGNATION
		endCustomerContractPage.verifyField("statusInViewPage", "Pending");
		endCustomerContractPage.verifyFieldContains("approvalStep", "Approval by Deal Operation Manager");
		endCustomerContractPage.verifyField("approvalAssignedTo", testProperties.getConstant("DEAL_OPPS_NAME"));
		
		//9. LOGOUT
		logout();
		
		
		/*
		 * =============================================================================================================
		 */
		
		
		//10. LOGIN TO DEAL OPPS EMAIL
		loginToGmail(testProperties.getConstant("DEAL_OPPS_EMAIL"), testProperties.getConstant("DEAL_OPPS_EMAIL_PWD"));
		
		//11. VERIFY DEALS OPPS RECEIVES MAIL
		verifyGmailExists("is:Unread subject:(" + testProperties.getConstant("EMAIL_SUBJECT") + ") from:" + testProperties.getConstant("PARTNER_EMAIL"));
		
		//12. OPEN EMAIL
		openFirstGmail();
		
		//13. VERIFY EMAIL TEMPLATE
		verifyGmailTemplate(testProperties.getConstant("EMAIL_SUBJECT"), listOfItemsToCheckInEmail);
		
		//14. REJECT ECC BY EMAIL
		replyToOpenedGmail("No");
		
		//15. LOGOUT
		logoutOfGmail();

		/*
		 * =============================================================================================================
		 */
		
		//16. LOGIN TO SALES REP EMAIL
		loginToGmail(testProperties.getConstant("PARTNER_EMAIL"), testProperties.getConstant("PARTNER_EMAIL_PWD"));
		
		//17. VERIFY SALES REP RECEIVES MAIL; OPEN MAIL
		verifyGmailExists("is:Unread subject:(" + testProperties.getConstant("EMAIL_REJECTION_SUBJECT") + ") from:" + testProperties.getConstant("DEAL_OPPS_EMAIL"));
		openFirstGmail();
		
		//18. VERIFY REJECTION EMAIL TEMPLATE
		verifyGmailTemplate(testProperties.getConstant("EMAIL_REJECTION_SUBJECT"), listOfItemsToCheckInRejectionEmail);
		
		//19. LOGOUT
		logoutOfGmail();
		
		/*
		 * =============================================================================================================
		 */
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