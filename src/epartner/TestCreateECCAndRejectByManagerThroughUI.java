package epartner;

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


/**
 * Test class - TestCreateNewEndCustomerAccountAndVerifyAccountType
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateECCAndRejectByManagerThroughUI extends EpartnerTestBase {
	public TestCreateECCAndRejectByManagerThroughUI() {
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(EpartnerConstants.BASE_URL);
		launchSalesforce();
	}

	@Test
	public void Test_CreateNewEndCustomerAccountAndVerifyAccountType() throws Exception {
	
		Partner partner = utilCreatePartnerOpportunityObject();
		Page_ endCustomerContractPage = partner.getCreateEndCustomerContractPage();
		
		//Login as Sales Rep
		login(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), true);
		partner.setUrl();
		search(testProperties.getConstant("ACCOUNT_CSN"));
		commonPage.clickLinkInRelatedList("accountCsnInAccountsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInAccountsRelatedList");
		//Create End Customer Contract
		endCustomerContractPage.clickAndWait("newEndCustomerAgreement", "autodeskAgreementNo");
		endCustomerContractPage.populate();
		commonPage.click("saveButton");
		endCustomerContractPage.waitForFieldPresent("newAgreementExhibit");
		//Add Agreement  Exhibits
		endCustomerContractPage.clickAndWait("newAgreementExhibit", "exhibitType");
		endCustomerContractPage.populateField("exhibitType", "Direct Terms");
		commonPage.click("saveButton");
		endCustomerContractPage.clickAndWait("endCustomerAgreement", "submitForApproval");
	//	endCustomerContractPage.clickToSubmit("submitForApproval","");
		driver.findElement(By.xpath("//td[contains(@id,'topButtonRow')]//input[@value='Submit For Approval']")).click();
		Alert alert = driver.switchTo().alert();
		Util.printInfo("Alert message is:"+alert.getText());
		alert.accept();
		logout();
		//Login as Deal_OPPS_Manager to approve ECC
		login(testProperties.getConstant("DEAL_OPPS_USER_NAME"), testProperties.getConstant("DEAL_OPPS_PASSWORD"), true);
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.click("approveButton");
		logout();
		//Login as Manager to reject ECC
		login(testProperties.getConstant("MANAGER_USER_NAME"), testProperties.getConstant("MANAGER_PASSWORD"), true);
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		driver.findElement(By.xpath("//input[@value='Reject']")).click();
		alert = driver.switchTo().alert();
		Util.printInfo("Alert message is:"+alert.getText());
		alert.accept();
//		endCustomerContractPage.click("rejectButton");
//		if(endCustomerContractPage.isAlertPresent())
//		{
//			endCustomerContractPage.acceptAlert();
//		}
		endCustomerContractPage.populateField("comments", "Test Reject");
		endCustomerContractPage.clickAndWait("rejectButton","statusInViewPage");
		endCustomerContractPage.verifyField("statusInViewPage", "Rejected");
		logout();
		
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
