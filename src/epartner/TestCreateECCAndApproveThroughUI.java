/**
 * @author saxenas
 * =================
 * New Files Created
 * =================
 *  # TestCreateECCAndApproveThroughUI.java
 *  # Test_CreateECCAndApproveThroughUI.properties
 *  
 * =======================
 * Existing Files Modified
 * =======================
 *  # Page_EndCustAgreement.properties
 *  # TestManifest.properties
 *  	+ TEST_CREATE_ECC_APPROVE_PROPERTIES_FILE = Test_CreateECCAndApproveThroughUI.properties
 *  
 * =============================
 * Modification Due to Framework
 * =============================
 *  # EpartnerTestBase.java
 *  	+ EpartnerTestBase(String strLaunchDriver)
 *  		REASON: Added constructor to allow framework specification from within test
 *  # ../../common/Page.java
 *  	+ doPopulateLookupField(Field, String)
 *  		LookupPopupPage.waitForField(Field, true)s
 *  		REASON: Framework sometimes being too fast to select item and causing failure
 */
package epartner;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import common.Account;
import common.EISTestBase;
import common.Page_;
import common.Util;



@SuppressWarnings("unused")
public final class TestCreateECCAndApproveThroughUI extends EpartnerTestBase 
{
	
	public TestCreateECCAndApproveThroughUI() 
	{
		super("chrome");
		//Util.debugMode = true;
	}
	
	@Before
	public void setUp() throws Exception 
	{
		launchSalesforce();
	}

	@Test
	public void Test_OpenEndCustomerAccountAndVerifyApprovals() throws Exception 
	{
	
		Partner partner = utilCreatePartnerOpportunityObject();
		Page_ endCustomerContractPage = partner.getCreateEndCustomerContractPage();
		
		//1. LOGIN AS SALES REP AND VERIFY GEO
		login(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), true);
		partner.setUrl();
		verifySfdcUserGeo(testProperties.getConstant("PARTNER_GEO"));
		
		//2. OPEN END CUSTOMER ACCOUNT
		search(testProperties.getConstant("ACCOUNT_CSN"));
		commonPage.clickLinkInRelatedList("accountCsnInAccountsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInAccountsRelatedList");

		//3. CREATE NEW END CUSTOMER AGREEMENT AND VERIFY NAMED ACCOUNT GROUP
		endCustomerContractPage.clickAndWait("newEndCustomerAgreement", "autodeskAgreementNo");
		endCustomerContractPage.populate();
		commonPage.click("saveButton");
		endCustomerContractPage.verifyField("namedAccountGroup", testProperties.getConstant("NAMED_ACCOUNT_GROUP"));
		
		//4. ADD GP SUBSIDIARY 	& VERIFY SALES REP IS UNATHORIZED TO DO SO
		endCustomerContractPage.clickAndWait("addSubsidiaryButton", "backNotAuthorizedButton");
		endCustomerContractPage.verifyField("addSubsNotAuthorizedText", testProperties.getConstant("NO_ACCESS_TEXT"));
		endCustomerContractPage.clickAndWait("backNotAuthorizedButton", "newAgreementExhibit");
		
		//5. ADD EXHIBITS & VERIFY ACTIVE FLAG IS CHECKED
		endCustomerContractPage.clickAndWait("newAgreementExhibit", "exhibitType");
		endCustomerContractPage.populateField("exhibitType", "Direct Terms");
		endCustomerContractPage.verifyFieldExists("checkActive");
		commonPage.click("saveButton");
		endCustomerContractPage.clickAndWait("endCustomerAgreement", "submitForApproval");
		
		//6. SUBMIT FOR APPROVAL
		endCustomerContractPage.click("submitForApproval");
		
		//7. ACCEPT ALERT
		Alert alert = driver.switchTo().alert();
		Util.printInfo("Alert message is: "+alert.getText());
		alert.accept();
		
		//8. VERIFY APPROVAL STATES AND ASSIGNATION; GET ECC URL
		endCustomerContractPage.verifyField("statusInViewPage", "Pending");
		endCustomerContractPage.verifyFieldContains("approvalStep", "Approval by Deal Operation Manager");
		endCustomerContractPage.verifyField("approvalAssignedTo", testProperties.getConstant("ASSIGNED_DEAL_OPPS_NAME"));
		String eccURL = endCustomerContractPage.getLocation();
		Util.printInfo("ECC URL is: " + eccURL);
		
		//9. LOGOUT AND NAVIGATE TO NEUTRAL PAGE
		logout();

		/*
		 * =============================================================================================================
		 */
		
		//10. LOGIN AS DEAL OPPS AND APPROVE 
		login(testProperties.getConstant("DEAL_OPPS_USER_NAME"), testProperties.getConstant("DEAL_OPPS_PASSWORD"), true);
		endCustomerContractPage.open(eccURL);
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.click("approveButton");
		
//		//11. ADD GP SUBSIDIARY
//		endCustomerContractPage.click("addSubsidiaryButton");
//		Util.sleep(5000); //Page is slow to load due to number of total ECC accounts
//		endCustomerContractPage.verifyFieldExists("addSubsIsAuthorizedText");
//		endCustomerContractPage.waitForFieldPresent("selectAuthorizedCheckBox");
//		endCustomerContractPage.check("selectAuthorizedCheckBox");
//		endCustomerContractPage.clickAndWait("addSubsidiaryButton", "newAgreementExhibit");
		
		//12. VERIFY APPROVAL STATES
		endCustomerContractPage.verifyField("statusInViewPage", "Pending");
		endCustomerContractPage.verifyFieldContains("approvalStep", "Approval by Manager");
		endCustomerContractPage.verifyField("approvalAssignedTo", testProperties.getConstant("MANAGER_NAME"));
		
		//13. LOGOUT
		logout();
		
		/*
		 * =============================================================================================================
		 */
		
		//13. LOGIN AS MANAGER AND APPROVE 
		login(testProperties.getConstant("MANAGER_USER_NAME"), testProperties.getConstant("MANAGER_PASSWORD"), true);
		endCustomerContractPage.open(eccURL);
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
		
		//16. LOGIN AS FBP AND APPROVE 
		login(testProperties.getConstant("FBP_USER_NAME"), testProperties.getConstant("FBP_PASSWORD"), true);
		endCustomerContractPage.open(eccURL);
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.clickAndWait("approveButton", "newEndCustomerAgreement");		
		
		//17. VERIFY APPROVAL STATES
		endCustomerContractPage.verifyField("statusInViewPage", "Pending");
		endCustomerContractPage.verifyFieldContains("approvalStep", "Approval by GEO CFS Manager");
		endCustomerContractPage.verifyField("approvalAssignedTo", testProperties.getConstant("ASSIGNED_GEO_CFS_NAME"));
		
		//18. LOGOUT
		logout();

		/*
		 * =============================================================================================================
		 */
		
		//19. LOGIN AS GEO CFS APPROVER
		login(testProperties.getConstant("GEO_CFS_USER_NAME"), testProperties.getConstant("GEO_CFS_PASSWORD"), true);
		endCustomerContractPage.open(eccURL);
		
		//20. VERIFY APPROVAL STATES
		endCustomerContractPage.verifyField("statusInViewPage", "Pending");
		
		//21. APPROVE AND VERIFY
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.clickAndWait("approveButton", "newEndCustomerAgreement");
		endCustomerContractPage.verifyField("statusInViewPage", "Approved");
		
		//22. VERIFY STATUS IS "In Progress"
		endCustomerContractPage.verifyFieldContains("statusInECCDetailPage", "In Progress");
		
		//23. VERIFY TASK UNDER OPEN ACTIVTIES; VERIFY ASSIGNATION TO DEAL OPPS
		endCustomerContractPage.verifyField("openActivitiesSubject", "End Customer agreement for " + testProperties.getConstant("ACCOUNT_NAME") + " request fully approved");
		endCustomerContractPage.verifyField("openActivitiesAssignedTo", testProperties.getConstant("ASSIGNED_DEAL_OPPS_NAME"));
		
		//24. LOGOUT
		logout();
		

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

