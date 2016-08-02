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



public final class TestCreateECCAndRejectByFBPThroughEmail extends EpartnerTestBase 
{
	
	public TestCreateECCAndRejectByFBPThroughEmail() 
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
	public void Test_CreateECCAndRejectByFBPThroughEmail() throws Exception 
	{
	
		Partner partner = utilCreatePartnerOpportunityObject();
		
		//0. SET UP ECC PAGE
		Page_ endCustomerContractPage = partner.getCreateEndCustomerContractPage();
		
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
			
		//5. ADD EXHIBIT DATA 
		endCustomerContractPage.clickAndWait("newAgreementExhibit", "exhibitType");
		Util.printInfo("Adding agreement exhibit of type Direct Terms");
		endCustomerContractPage.populateInstance("POPULATE_EXHIBIT_DATA");
		commonPage.click("saveButton");
			
		//7. SUBMIT FOR APPROVAL AND ACCEPT ALERT
		endCustomerContractPage.clickAndWait("endCustomerAgreement", "submitForApproval");
		endCustomerContractPage.click("submitForApproval");
		
		Util.printInfo("Alert message is: " + endCustomerContractPage.getAlert());
		endCustomerContractPage.acceptAlert();
		String urlECC = endCustomerContractPage.getLocation();
		Util.printInfo("ECC URL is: " + urlECC);
		
		//8. VERIFY APPROVAL STATES AND ASSIGNATION
		endCustomerContractPage.verifyField("statusInViewPage", "Pending");
		endCustomerContractPage.verifyFieldContains("approvalStep", "Approval by Deal Operation Manager");
		endCustomerContractPage.verifyField("approvalAssignedTo", testProperties.getConstant("DEAL_OPPS_NAME"));
		
		//9. LOGOUT
		logout();
		
		
		/*
		 * =============================================================================================================
		 */
		
		
		//10. LOGIN TO DEAL OPPS IN SFDC
		login(testProperties.getConstant("DEAL_OPPS_USER_NAME"), testProperties.getConstant("DEAL_OPPS_PASSWORD"), true);
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.click("approveButton");
		//11. VERIFY APPROVAL STATES
		endCustomerContractPage.verifyField("statusInViewPage", "Pending");
		endCustomerContractPage.verifyFieldContains("approvalStep", "Approval by Manager");
		endCustomerContractPage.verifyField("approvalAssignedTo", testProperties.getConstant("MANAGER_NAME"));
				
		//12. LOGOUT
		logout();		

		/*
		 * =============================================================================================================
		 */
		
		//13. LOGIN TO MANAGER EMAIL
		login(testProperties.getConstant("MANAGER_USER_NAME"), testProperties.getConstant("MANAGER_PASSWORD"), true);
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.click("approveButton");
		
		//14. VERIFY APPROVAL STATES
		endCustomerContractPage.verifyField("statusInViewPage", "Pending");
		endCustomerContractPage.verifyFieldContains("approvalStep", "Approval by FBP");
		endCustomerContractPage.verifyField("approvalAssignedTo", testProperties.getConstant("FBP_NAME"));
		
		//15. LOGOUT
		logout();
		/*
		 * =============================================================================================================
		 */
		
		//16. LOGIN TO FBP EMAIL
		loginToGmail(testProperties.getConstant("FBP_EMAIL"), testProperties.getConstant("FBP_EMAIL_PWD"));
		
		//17. VERIFY MANAGER RECEIVES MAIL
		verifyGmailExists("is:Unread subject:(" + testProperties.getConstant("EMAIL_SUBJECT") + ") from:" + testProperties.getConstant("MANAGER_EMAIL"));
		
		//18. OPEN EMAIL
		openFirstGmail();
		
		//19. VERIFY EMAIL TEMPLATE
		verifyGmailTemplate(testProperties.getConstant("EMAIL_SUBJECT"), new ArrayList<>(Arrays.asList(testProperties.getConstant("EMAIL_BODY"))));
		
		//20. REJECT ECC BY EMAIL AND VERIFY NO ERRORS DUE TO NO REASON (IE. NO NEW MAIL RECEIVED FOR THIS CONVERSATION)
		replyToOpenedGmail("No","Rejected because of incomplete information");
		Util.sleep(10000);
		emailClientPage.verifyFieldNotExists("showNewEmailMessage");
		
		//21. LOGOUT
		logoutOfGmail();

		/*
		 * =============================================================================================================
		 */
		
		
		//16. LOGIN TO SALES MANAGER IN SFDC TO CHECK REJECTION COMMENTS
		login(testProperties.getConstant("MANAGER_USER_NAME"), testProperties.getConstant("MANAGER_PASSWORD"), true);
		driver.navigate().to(urlECC);
		
		//20. VERIFY APPROVAL STATES
		endCustomerContractPage.verifyField("statusInViewPage", "Rejected");
		endCustomerContractPage.verifyField("rejectionCommentsByFBPInApprovalHistory", "Rejected because of incomplete information");
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
