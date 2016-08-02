package mja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After; 
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Oppty;
import common.Page_;
import common.Util;

/**
 * Test class - TestGenerateApprovalChain
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestGenerateApprovalChain extends MJATestBase {
	public TestGenerateApprovalChain() {
//		super();
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce(getAppServerBaseURL());
	}

	@Test
	public void TEST_GenerateApprovalChain() throws Exception {
		Page_ viewDiscountApprovalRequestPage;
		String browser=EISTestBase.getAppBrowser();
		Util.printInfo(browser);
		
		//Can include Selenium and WebDriver commands - but please don't!
		login(testProperties.getConstant("USER_NAME"), testProperties.getConstant("PASSWORD"));
				
		Oppty oppty = utilCreateOppty();
		oppty.addProducts();		
		utilCreateDiscountApprovalRequest(oppty);
		viewDiscountApprovalRequestPage = oppty.getViewDiscountApprovalRequestPage();
		String DARUrl=viewDiscountApprovalRequestPage.getLocation();
		Util.printInfo("DAR URL is :"+DARUrl);
//		if(testProperties.getConstant("NUMBER_OF_PRODUCTS_TO_ADD").equalsIgnoreCase("fifty")){
//			
//			oppty.submitDiscountApprovalRequestForApproval();
//		} 
//		else{
//		oppty.verifyDiscountApprovalRequestApprovalChain();
//		}
		if(EISTestBase.testProperties.getConstant("SCRIPT").equalsIgnoreCase("CHECK_APPROVAL_FLOW")){
			oppty.submitForApproval();
		List<String> listOfItemsToCheckInEmail = new ArrayList<>(Arrays.asList (
				oppty.getName(), 
				testProperties.getConstant("ACCOUNT_NAME"),
				testProperties.getConstant("FULFILLMENT_CATEGORY"),
				testProperties.getConstant("OPPTY_OWNER"),
				viewDiscountApprovalRequestPage.getValueFromGUI("totalSRP"),
				viewDiscountApprovalRequestPage.getValueFromGUI("totalNetPrice"),
				viewDiscountApprovalRequestPage.getAttribute("discountReason","value"),
				viewDiscountApprovalRequestPage.getValueFromGUI("discountJustification"),
				viewDiscountApprovalRequestPage.getValueFromGUI("manualDAExpirationDate"),
				viewDiscountApprovalRequestPage.getValueFromGUI("discCategory"),
				viewDiscountApprovalRequestPage.getValueFromGUI("additionalDiscPct"),
				viewDiscountApprovalRequestPage.getValueFromGUI("totalEndUserDisc")
				));
		String SUBJECT="Sandbox: DISCOUNT APPROVAL NEEDED : HONDA GROUP [GP] - "+oppty.getName();
		
		
//////////////////////////////First Approver DAR approval through Gmail////////////////////////////////////////////
		loginToGmail(testProperties.getConstant("FIRST_APPROVER_GMAIL_ID"), testProperties.getConstant("APPROVER_PASSWORD"));
		
		//11. VERIFY DEALS OPPS RECEIVES MAIL (WAIT TIME OF 60 SEC)
		verifyGmailExists("is:Unread subject:(" + SUBJECT + ") from:" + testProperties.getConstant("USER_GMAIL_ID"));
		
		//12. OPEN EMAIL
		openFirstGmail();
		
		//13. VERIFY EMAIL TEMPLATE
		verifyGmailTemplate(SUBJECT, listOfItemsToCheckInEmail);
		
		//14. APPROVE ECC BY EMAIL
		replyToOpenedGmail("Yes");
		
		//15. LOGOUT
		logoutOfGmail();
//////////////////////////////Second Approver DAR approval through Gmail////////////////////////////////////////////
		loginToGmail(testProperties.getConstant("SECOND_APPROVER_GMAIL_ID"), testProperties.getConstant("APPROVER_PASSWORD"));
		
		//11. VERIFY DEALS OPPS RECEIVES MAIL (WAIT TIME OF 60 SEC)
		verifyGmailExists("is:Unread subject:(" + SUBJECT + ") from:" + testProperties.getConstant("FIRST_APPROVER_GMAIL_ID"));
		
		//12. OPEN EMAIL
		openFirstGmail();
				
		//14. APPROVE ECC BY EMAIL
		replyToOpenedGmail("Yes");
		
		//15. LOGOUT
		logoutOfGmail();
//////////////////////////////Third Approver DAR approval through Gmail////////////////////////////////////////////
		loginToGmail(testProperties.getConstant("THIRD_APPROVER_GMAIL_ID"), testProperties.getConstant("APPROVER_PASSWORD"));
		
		//11. VERIFY DEALS OPPS RECEIVES MAIL (WAIT TIME OF 60 SEC)
		verifyGmailExists("is:Unread subject:(" + SUBJECT + ") from:" + testProperties.getConstant("SECOND_APPROVER_GMAIL_ID"));
		
		//12. OPEN EMAIL
		openFirstGmail();
				
		//14. APPROVE ECC BY EMAIL
		replyToOpenedGmail("Yes");
		
		//15. LOGOUT
		logoutOfGmail();
//////////////////////////////Fourth Approver DAR approval through Gmail////////////////////////////////////////////
		loginToGmail(testProperties.getConstant("FOURTH_APPROVER_GMAIL_ID"), testProperties.getConstant("APPROVER_PASSWORD"));
		
		//11. VERIFY DEALS OPPS RECEIVES MAIL (WAIT TIME OF 60 SEC)
		verifyGmailExists("is:Unread subject:(" + SUBJECT + ") from:" + testProperties.getConstant("THIRD_APPROVER_GMAIL_ID"));
		
		//12. OPEN EMAIL
		openFirstGmail();
				
		//14. APPROVE ECC BY EMAIL
		replyToOpenedGmail("Yes","Completed Approval process");
		
		//15. LOGOUT
		logoutOfGmail();
		}
		else if(EISTestBase.testProperties.getConstant("SCRIPT").equalsIgnoreCase("ADD_ADDITIONAL_APPROVER")){
		login(testProperties.getConstant("USER_NAME"), testProperties.getConstant("PASSWORD"),true);
		open(DARUrl);
		Util.printInfo("Opened DAR in SFDC");
		viewDiscountApprovalRequestPage.clickAndWait("backToOpptyButton", "darStatusInOpptyPage");
		String comments=viewDiscountApprovalRequestPage.getValueFromGUI("approverComments");
		assertEquals(comments, "Completed Approval process");
		String darStatus=viewDiscountApprovalRequestPage.getValueFromGUI("darStatusInOpptyPage");
		assertEquals(darStatus, "Approved");
		}
		else if(EISTestBase.testProperties.getConstant("SCRIPT").equalsIgnoreCase("GENERATE_DAR_TWO_PRODUCTS")){
			oppty.submitForApproval();
			logout();
		}
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
