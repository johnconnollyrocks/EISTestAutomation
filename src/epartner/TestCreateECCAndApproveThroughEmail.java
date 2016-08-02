/**
 * @author saxenas
 * 
 * ======================
 * File Updates for GMail
 * ======================
 *  # common/resource/		Page_EmailClient.properties 	[[GMail login page xpaths added]] 
 *  # epartner/				EpartnerTestBase.java			[[Added createPage ARGM to utilCreatePartnerOpportunityObject (); Added imports for WebElement and java.Util
 *  														  Added **getGmailUser() **loginToGmail(String username, String password) **logoutOfGmail()
 *  														  **verifyGmailExists(String searchText) **openFirstGmail() **replyToOpenedGmail() **verifyGmailTemplate (----)]]
 *  # epartner/				Partner.java					[[Added **declaration of approveRejectByGmailPage; **getApproveRejectByGmailPage(); **setApproveRejectByGmailPage(Page_);]] 
 *  
 * =================
 * New Files Created
 * =================
 *  # epartner/				TestCreateECCAndApproveThroughEmail.java
 *  # epartner/testdata/	Test_CreateECCAndApproveThroughEmail.properties
 *
 * =======================
 * Existing Files Modified
 * =======================
 *  # epartner/testdata/	TestManifest.properties			  [[TEST_CREATE_ECC_APPROVE_BY_EMAIL_PROPERTIES_FILE = Test_CreateECCAndApproveThroughEmail.properties]]
 *  # epartner/resource/	Page_EndCustAgreement.properties  [[Added fields]]
 *  # common/				Page.java & Page_.java			  [[Added **verifyAlert (String alertMessage)  **verifyIsFieldRequired(String fieldName);]]
 *  # common/				EISTestBase.java				  [[Comment out redundant launchSalesforce in login(username, password, launch)]]
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
import common.Window;
@SuppressWarnings("unused")



public final class TestCreateECCAndApproveThroughEmail extends EpartnerTestBase 
{
	
	public TestCreateECCAndApproveThroughEmail() 
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
	public void Test_OpenEndCustomerAccountAndVerifyEmailApprovals() throws Exception 
	{
	
		//EISTestBase.setIgnoreFailedAsserts(true);
		Partner partner = utilCreatePartnerOpportunityObject();
		
		//0. SET UP ECC PAGE AND LISTS OF STRINGS FOR EMAIL VERIFICATION
		Page_ endCustomerContractPage = partner.getCreateEndCustomerContractPage();
		List<String> listOfItemsToCheckInEmail = new ArrayList<>(Arrays.asList (
			testProperties.getConstant("ACCOUNT_NAME"),
			testProperties.getConstant("ACCOUNT_CSN"),
			testProperties.getConstant("REQUEST_TYPE"),
			testProperties.getConstant("AGREEMENT_TYPE"),
			testProperties.getConstant("VALIDITY"),
			"Volume Purchase", /*exhibit type*/
			testProperties.getConstant("EMAIL_BODY")
		)); //contractExhibitName added later from GUI
		
		List<String> listOfItemsToCheckInApprovalEmail = new ArrayList<>(Arrays.asList (
			testProperties.getConstant("FULL_APPROVAL_EMAIL"),
			testProperties.getConstant("MANAGER_NAME"),
			testProperties.getConstant("FBP_NAME"),
			testProperties.getConstant("GEO_CFS_NAME"),
			"Volume Purchase"
		));
			
		//1. LOGIN AS SALES REP
		login(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), true);
		partner.setUrl();		
		
		//2. OPEN END CUSTOMER ACCOUNT
		search(testProperties.getConstant("ACCOUNT_CSN"));
		commonPage.clickLinkInRelatedList("accountCsnInAccountsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInAccountsRelatedList");

		//3. CREATE NEW END CUSTOMER AGREEMENT & VERIFY NAMED ACCOUNT GROUP
		endCustomerContractPage.clickAndWait("newEndCustomerAgreement", "autodeskAgreementNo");
		endCustomerContractPage.populate();
		commonPage.click("saveButton");
		endCustomerContractPage.verifyField("namedAccountGroup", testProperties.getConstant("NAMED_ACCOUNT_GROUP"));
		
		//4. SUBMIT AND VERIFY THAT EXHIBITS MUST BE ADDED FIRST
		endCustomerContractPage.click("submitForApproval");
		endCustomerContractPage.verifyAlert(testProperties.getConstant("EXHIBIT_ALERT_TEXT"));
		endCustomerContractPage.acceptAlert();
		
		//5. ADD VOLUME PURCHASE EXHIBIT & VERIFY VALIDITY, COMMITMENT AND STD DISCOUNT SCHEME ARE MANDATORY
		endCustomerContractPage.clickAndWait("newAgreementExhibit", "exhibitType");
		endCustomerContractPage.populateField("exhibitType", "Volume Purchase");
		commonPage.click("saveButton");
		endCustomerContractPage.verifyFieldIsRequired("validity");
		endCustomerContractPage.verifyFieldIsRequired("commitment");
		endCustomerContractPage.verifyFieldIsRequired("stdDiscountScheme");
		endCustomerContractPage.populateInstance("AGREEMENT_EXHIBIT_DATA");
		commonPage.clickAndWait("saveButton", "editButton");
		listOfItemsToCheckInEmail.add (endCustomerContractPage.getValueFromGUI("contractExhibitName"));
		listOfItemsToCheckInApprovalEmail.add (endCustomerContractPage.getValueFromGUI("contractExhibitName"));
		
		//6. VERIFY MESSAGE TO ATTACH LIST OF SUBSIDIARY
		driver.switchTo().frame(driver.findElement(By.xpath("//table[@class='detailList']//iframe")));
		assertTrue("Error message \"" + testProperties.getConstant("SUBSIDIARY_ALERT_TEXT") + "\" is found", driver.findElement(By.xpath("//body//label")).getAttribute("innerHTML").contains(testProperties.getConstant("SUBSIDIARY_ALERT_TEXT")));
		driver.switchTo().defaultContent();
		endCustomerContractPage.clickAndWait("endCustomerAgreement", "submitForApproval");
		
		//6. SUBMIT FOR APPROVAL
		//driver.findElement(By.xpath("//td[contains(@id,'topButtonRow')]//input[@value='Submit For Approval']")).click();
		endCustomerContractPage.click("submitForApproval");
		
		//7. ACCEPT ALERT
		Util.printInfo("Alert message is:" + endCustomerContractPage.getAlert());
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
		
		//11. VERIFY DEALS OPPS RECEIVES MAIL (WAIT TIME OF 60 SEC)
		verifyGmailExists("is:Unread subject:(" + testProperties.getConstant("EMAIL_SUBJECT") + ") from:" + testProperties.getConstant("PARTNER_EMAIL"));
		
		//12. OPEN EMAIL
		openFirstGmail();
			
		//13. VERIFY EMAIL TEMPLATE
		verifyGmailTemplate(testProperties.getConstant("EMAIL_SUBJECT"), listOfItemsToCheckInEmail);		
		
		//14. APPROVE ECC BY EMAIL
		replyToOpenedGmail("Yes");
		
		//15. LOGOUT
		logoutOfGmail();

		/*
		 * =============================================================================================================
		 */
		
		//16. LOGIN TO MANAGER'S EMAIL
		loginToGmail(testProperties.getConstant("MANAGER_EMAIL"), testProperties.getConstant("MANAGER_EMAIL_PWD"));
		
		//17. VERIFY MANAGER RECEIVES MAIL; OPEN MAIL
		verifyGmailExists("is:Unread subject:(" + testProperties.getConstant("EMAIL_SUBJECT") + ") from:" + testProperties.getConstant("DEAL_OPPS_EMAIL"));
		openFirstGmail();
		
		//18. APPROVE ECC BY EMAIL
		replyToOpenedGmail("Yes");
		
		//19. LOGOUT
		logoutOfGmail();
		
		/*
		 * =============================================================================================================
		 */

		//19.5 LOGIN TO FBP SALESFORCE
		login(testProperties.getConstant("FBP_USER_NAME"), testProperties.getConstant("FBP_PASSWORD"), true);
		
		//20. LOGIN TO FBP EMAIL
		loginToGmail(testProperties.getConstant("FBP_EMAIL"), testProperties.getConstant("FBP_EMAIL_PWD"));

		//21. VERIFY FBP RECEIVES MAIL; OPEN MAIL
		verifyGmailExists ("is:Unread subject:(" + testProperties.getConstant("EMAIL_SUBJECT") + ") from:" + testProperties.getConstant("MANAGER_EMAIL"));
		openFirstGmail();
		
		//22. VERIFY ECC LINK IS PRESENT IN MAIL; CLICK LINK; LOGOUT OF GMAIL AND CHANGE WINDOWS
		if(emailClientPage.isFieldPresent("iTrustThis")) {emailClientPage.click("iTrustThis");}
		emailClientPage.waitForFieldPresent("emailLink");
		String winHandle = emailClientPage.clickAndWaitForPopUpToOpen("emailLink");
		logoutOfGmail();
		emailClientPage.closeWindow(emailClientPage.getWindowHandle());
		mainWindow.setLocator(winHandle);
		mainWindow.selectWindow();
		
		
		//23. APPROVE ECC
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.clickAndWait("approveButton", "newEndCustomerAgreement");		
		
		//24. VERIFY APPROVAL STATES AND LOGOUT
		endCustomerContractPage.verifyField("statusInViewPage", "Approved");
		logout();
		
		
		/*
		 * =============================================================================================================
		 */

		//24.5 LOGIN TO DEAL OPPS SALESFORCE
		login(testProperties.getConstant("DEAL_OPPS_USER_NAME"), testProperties.getConstant("DEAL_OPPS_PASSWORD"), true);

		//25. LOGIN TO DEALS OPP EMAIL  
		loginToGmail(testProperties.getConstant("DEAL_OPPS_EMAIL"), testProperties.getConstant("DEAL_OPPS_EMAIL_PWD"));

		//26. VERIFY DEALS OPP RECEIVES "FULLY APPROVED" MAIL; OPEN EMAIL
		verifyGmailExists ("is:Unread subject:(" + testProperties.getConstant("FULL_APPROVAL_EMAIL_SUBJECT") + ") from:" + testProperties.getConstant("FBP_EMAIL"));
		openFirstGmail();
		
		//27. VERIFY EMAIL SUBJECT AND TEMPLATE
		verifyGmailTemplate(testProperties.getConstant("FULL_APPROVAL_EMAIL_SUBJECT"), listOfItemsToCheckInApprovalEmail);
		
		//28. VERIFY ECC LINK IS PRESENT IN MAIL; CLICK LINK; LOGOUT OF GMAIL AND CHANGE WINDOWS
		if(emailClientPage.isFieldPresent("iTrustThis")) {emailClientPage.click("iTrustThis");}
		emailClientPage.waitForFieldPresent("emailLink");
		winHandle = emailClientPage.clickAndWaitForPopUpToOpen("emailLink");
		logoutOfGmail();
		emailClientPage.closeWindow(emailClientPage.getWindowHandle());
		mainWindow.setLocator(winHandle);
		mainWindow.selectWindow();
		
		//29. LOGIN TO SALESFORCE; VERIFY STATUS IS "In Progress"
		endCustomerContractPage.verifyFieldContains("statusInECCDetailPage", "In Progress");
		
		//30. VERIFY TASK UNDER OPEN ACTIVTIES; VERIFY ASSIGNATION TO DEAL OPPS; LOGOUT
		endCustomerContractPage.verifyField("openActivitiesSubject", "End Customer agreement for " + testProperties.getConstant("ACCOUNT_NAME") + " request fully approved");
		endCustomerContractPage.verifyField("openActivitiesAssignedTo", testProperties.getConstant("DEAL_OPPS_NAME"));
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


