/**
 * @author saxenas
 * 
 * =================
 * New Files Created
 * =================
 *  # epartner/				TestCreateECCAndRejectByManagerThroughEmail.java
 *  # epartner/testdata/	Test_CreateECCAndRejectByManagerThroughEmail.properties
 *
 * =======================
 * Existing Files Modified
 * =======================
 *  # epartner/testdata/	TestManifest.properties			  [[TEST_CREATE_ECC_REJECT_BY_MANAGER_BY_EMAIL_PROPERTIES_FILE = Test_CreateECCAndRejectByManagerThroughEmail.properties]]
 *  # epartner/resource/	Page_EndCustAgreement.properties  [[Added fields ]]
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



public final class TestCreateECCAndRejectByManagerThroughEmail extends EpartnerTestBase 
{
	
	public TestCreateECCAndRejectByManagerThroughEmail() 
	{
		//super();
		super("firefox");
		//Util.debugMode = true;
	}
	
	@Before
	public void setUp() throws Exception 
	{
		launchSalesforce();
	}

	@Test
	public void Test_OpenEndCustomerAccountAndVerifyManagerEmailRejection() throws Exception 
	{
	
		Partner partner = utilCreatePartnerOpportunityObject();
		
		//0. SET UP ECC PAGE; INITIALIZE LIST OF STRING TO STORE AGREEMENT DETAILS TO BE CHECKED IN EMAIL LATER
		Page_ endCustomerContractPage = partner.getCreateEndCustomerContractPage();
		
		List<String> listOfItemsToCheckInEmail = new ArrayList<String> (Arrays.asList (
			testProperties.getConstant("ACCOUNT_NAME"),
			testProperties.getConstant("ACCOUNT_CSN"),
			testProperties.getConstant("REQUEST_TYPE"),
			testProperties.getConstant("AGREEMENT_TYPE"),
			testProperties.getConstant("VALIDITY"),
			testProperties.getConstant("EMAIL_BODY")
		)); //contractExhibitName added later from GUI
		
		List<String> listOfItemsToCheckInRejectionEmail = new ArrayList<String> (Arrays.asList (
			testProperties.getConstant("EMAIL_REJECTION_BODY") + " " + testProperties.getConstant("MANAGER_NAME"),
			"Please liaise with them directly. Note that no contract will be prepared until you submit a new request that get fully approved.",
			"Reason:" + testProperties.getConstant("REJECTION_REASON"), /*reason for rejection*/
			"Full details of the new request including subsidiary list and exhibit options can be found here:"
		));
		
			
		
		//1. LOGIN AS SALES REP
		login(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), true);
		partner.setUrl();		
		
		//2. OPEN END CUSTOMER ACCOUNT
		search(testProperties.getConstant("ACCOUNT_CSN"));
		commonPage.clickLinkInRelatedList("accountCsnInAccountsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInAccountsRelatedList");

		//3. CLICK ON NEW END CUSTOMER AGREEMENT
		endCustomerContractPage.clickAndWait("newEndCustomerAgreement", "autodeskAgreementNo");
				
		//4.POPULATE FIELDS AND SAVE AND VERIFY NO ERROR APPEARS
		endCustomerContractPage.populate();
		commonPage.click("saveButton");
		endCustomerContractPage.waitForFieldPresent("autodeskAgreementNo");
		endCustomerContractPage.verifyFieldNotVisible("invalidDataError");
			
		//5. ADD SUPPORT EXHIBIT; VERIFY MANDATORY FIELDS; GET EXHIBIT NAME 
		endCustomerContractPage.clickAndWait("newAgreementExhibit", "exhibitType");
		endCustomerContractPage.populateInstance("AGREEMENT_EXHIBIT_DATA");
		commonPage.click("saveButton");
//		endCustomerContractPage.verifyFieldIsRequired("supportOptions");
//		assertTrue("Support Options Provided are 'Enterprise Priority' and 'Advanced' ", endCustomerContractPage.areValuesInPickList("supportOptions", "Enterprise Priority", "Advanced"));
//		endCustomerContractPage.populateField("supportOptions", "Enterprise Priority");
		commonPage.click("saveButton");
		listOfItemsToCheckInEmail.add (endCustomerContractPage.getValueFromGUI("contractExhibitName"));
		endCustomerContractPage.clickAndWait("endCustomerAgreement", "submitForApproval");
		
			
		//6. ADD GP SUBSIDIARY 	& VERIFY SALES REP IS UNATHORIZED TO DO SO
		endCustomerContractPage.clickAndWait("addSubsidiaryButton", "backNotAuthorizedButton");
		endCustomerContractPage.verifyField("addSubsNotAuthorizedText", testProperties.getConstant("NO_ACCESS_TEXT"));
		endCustomerContractPage.clickAndWait("backNotAuthorizedButton", "newAgreementExhibit");
		
		//7. SUBMIT FOR APPROVAL AND ACCEPT ALERT
		endCustomerContractPage.click("submitForApproval");
		
		Util.printInfo("Alert message is: " + endCustomerContractPage.getAlert());
		endCustomerContractPage.acceptAlert();
		
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
		replyToOpenedGmail("Yes");
		
		//15. LOGOUT
		logoutOfGmail();

		/*
		 * =============================================================================================================
		 */
		
		//16. LOGIN TO MANAGER EMAIL
		loginToGmail(testProperties.getConstant("MANAGER_EMAIL"), testProperties.getConstant("MANAGER_EMAIL_PWD"));
		
		//17. VERIFY MANAGER RECEIVES MAIL
		verifyGmailExists("is:Unread subject:(" + testProperties.getConstant("EMAIL_SUBJECT") + ") from:" + testProperties.getConstant("DEAL_OPPS_EMAIL"));
		//verifyGmailExists("subject:(" + testProperties.getConstant("EMAIL_SUBJECT") + ") from:" + testProperties.getConstant("DEAL_OPPS_EMAIL"));
		
		//18. OPEN EMAIL
		openFirstGmail();
		
		//19. VERIFY EMAIL TEMPLATE
		verifyGmailTemplate(testProperties.getConstant("EMAIL_SUBJECT"), listOfItemsToCheckInEmail);
		
		//20. REJECT ECC BY EMAIL AND VERIFY NO ERRORS DUE TO NO REASON (IE. NO NEW MAIL RECEIVED FOR THIS CONVERSATION)
		replyToOpenedGmail("Rejected");
		Util.sleep(10000);
		emailClientPage.verifyFieldNotExists("showNewEmailMessage");
		
		//21. LOGOUT
		logoutOfGmail();

		/*
		 * =============================================================================================================
		 */
		
		
		//16. LOGIN TO SALES REP EMAIL
		loginToGmail(testProperties.getConstant("PARTNER_EMAIL"), testProperties.getConstant("PARTNER_EMAIL_PWD"));
		
		//17. VERIFY SALES REP RECEIVES MAIL; OPEN MAIL
		verifyGmailExists("is:Unread subject:(" + testProperties.getConstant("EMAIL_REJECTION_SUBJECT") + ") from:" + testProperties.getConstant("MANAGER_EMAIL"));
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
