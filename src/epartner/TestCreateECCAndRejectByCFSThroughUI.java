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


/**
 * Test class - TestCreateNewEndCustomerAccountAndVerifyAccountType
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateECCAndRejectByCFSThroughUI extends EpartnerTestBase {
	public TestCreateECCAndRejectByCFSThroughUI() {
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
		endCustomerContractPage.verifyField("namedAccountGroup", testProperties.getConstant("NAMED_ACCOUNT_GROUP"));
		String eccURL=driver.getCurrentUrl();
		Util.printInfo("ECC URL is: "+eccURL);
		//Add Agreement  Exhibits
		Util.printInfo("Adding agreement exhibit of type Direct terms");
		endCustomerContractPage.clickAndWait("newAgreementExhibit", "exhibitType");
		endCustomerContractPage.populateInstance("POPULATE_EXHIBIT_DATA_1");
		commonPage.click("saveButton");
		endCustomerContractPage.clickAndWait("endCustomerAgreement", "submitForApproval");
		driver.findElement(By.xpath("//td[contains(@id,'topButtonRow')]//input[@value='Submit For Approval']")).click();
		Alert alert = driver.switchTo().alert();
		Util.printInfo("Alert message is:"+alert.getText());
		alert.accept();
		Util.sleep(2000);
		String dealOppsMngr=endCustomerContractPage.getValueFromGUI("dealOppsManagerNameInApprovalHistor");
		assertEquals(dealOppsMngr, testProperties.getConstant("DEAL_OPPS_MANAGER_NAME"));
		logout();
		//Login as Deal_OPPS_Manager to approve ECC
		login(testProperties.getConstant("DEAL_OPPS_USER_NAME"), testProperties.getConstant("DEAL_OPPS_PASSWORD"), true);
		open(eccURL);
		endCustomerContractPage.clickAndWait("dealOppsManagerApproveRejectLinkinApprovalHistory", "approveButton");
//		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.click("approveButton");
		Util.sleep(2000);
		String Manager_Name=endCustomerContractPage.getValueFromGUI("managerNameInApprovalHistory");
		assertEquals(Manager_Name, testProperties.getConstant("MANAGER_NAME"));
		logout();
		//Login as Manager to approve ECC
		login(testProperties.getConstant("MANAGER_USER_NAME"), testProperties.getConstant("MANAGER_PASSWORD"), true);
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.click("approveButton");
		Util.sleep(2000);
		String fbp_Name=endCustomerContractPage.getValueFromGUI("fbpNameInApprovalHistory");
		assertEquals(fbp_Name, testProperties.getConstant("FBP_NAME"));
		logout();
		//Login as FBP to approve ECC
		login(testProperties.getConstant("FBP_USER_NAME"), testProperties.getConstant("FBP_PASSWORD"), true);
		endCustomerContractPage.clickAndWait("approveRejectLinkForFirstAgreement", "approveButton");
		endCustomerContractPage.click("approveButton");
		Util.sleep(2000);
		String cfs_Name=endCustomerContractPage.getValueFromGUI("cfsNameInApprovalHistory");
		assertEquals(cfs_Name, testProperties.getConstant("GEO_CFS_NAME"));
		logout();
		//Login as CFS_MANAGER to reject ECC
		login(testProperties.getConstant("GEO_CFS_USER_NAME"), testProperties.getConstant("GEO_CFS_PASSWORD"), true);
		open(eccURL);
		endCustomerContractPage.clickAndWait("cfsApproveRejectLinkinApprovalHistory", "rejectButton");
		driver.findElement(By.xpath("//input[@value='Reject']")).click();
		alert = driver.switchTo().alert();
		Util.printInfo("Alert message is:"+alert.getText());
		alert.accept();
		endCustomerContractPage.populateField("comments", "Please fill End Date for the ECC");
		endCustomerContractPage.clickAndWait("rejectButton","statusInViewPage");
		endCustomerContractPage.verifyField("statusInViewPage", "Rejected");
		logout();
		//Login as Sales Rep and edit the ECC according to the comments
		login(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), true);
		open(eccURL);
		String comments=endCustomerContractPage.getValueFromGUI("rejectionCommentsByCFSInApprovalHistory");
		Util.printInfo("Comments are:"+comments);
		endCustomerContractPage.clickAndWait("editButton","endDate");
		endCustomerContractPage.populateField("endDate",testProperties.getConstant("END_DATE"));
		commonPage.click("saveButton");
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
