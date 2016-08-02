package ss;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.DismissAlert;

import common.Case;
import common.EISTestBase;
import common.Page_;
import common.Util;

import common.Case.CreateFrom;
import common.Case.CaseType;

/**
 * Test class - TestCreateCaseInPortal
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateCaseInSCCloseReopenCase extends SSTestBase {
	public TestCreateCaseInSCCloseReopenCase() {
		super("firefox");
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce(SSConstants.BASE_PARTNER_URL_STG);
	}

	@Test
	public void TEST_CreateCaseInCustmrPortalCheckDefaultFunctnlty() throws Exception {
		String subPortalUrl=SSConstants.BASE_PARTNER_URL_STG;
		CreateFrom createFrom 	= getInterfaceType(SSConstants.SS_CREATE_FROM_ENUM_CONSTANT_NAME);
		CaseType caseType		= getCaseType(SSConstants.SS_CASE_TYPE_ENUM_CONSTANT_NAME);
		custPortallogin(testProperties.getConstant("EIDM_ID"),testProperties.getConstant("PASSWORD"));
	
		Case supportCase = utilCreateCaseObject(createFrom);

		Page_ createCasePage = supportCase.getCreateCasePage();
		if(createCasePage.isAlertPresent()){
		createCasePage.dismissAlert();
			
		}
		if(createCasePage.isFieldPresent("supportRequest")){
			String Url=createCasePage.getAttribute("supportRequest", "href");
			open(Url);
			createCasePage.waitForFieldVisible("softwareDownloadsCase");
			Util.sleep(2000);
		}
		else{
		createCasePage.click("createASupportCase");
		}
		supportCase = utilCreateCase(createFrom, caseType);
		Page_ viewCaseInPortalPage = supportCase.getViewCaseInPortalPage();
		String caseNumber= supportCase.getName();
		 
		viewCaseInPortalPage.clickAndWait("caseNo","closeCase");
		Util.sleep(2000);
		viewCaseInPortalPage.verify();
		String clause=EISTestBase.testProperties.getConstant("OPEN_DETAILS");
	 	if(clause.equalsIgnoreCase("Yes")){
			viewCaseInPortalPage.click("openDetails");
			viewCaseInPortalPage.verifyInstance("HOST_DETAILS");
			
		}
	 	salesforceLoginStage();
		Util.sleep(50000);
		search(caseNumber);
		
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.printInfo("opened case verify on view case page");
		Page_ viewCasePage = supportCase.getViewCasePage();
        
      
//		if(caseType ==CaseType.PROD_SUPPORT||caseType ==CaseType.INSTAL_CONFIG_ISSUE_CASE||caseType ==CaseType.IDEAS_SUGGESTION_CASE) {
//			//Don't do things like this (grabbing a value from a "one-off" page - viewProductPage in this case)
//			//  in setVerificationData because the page will differ between case types, and we don't want to
//			//  conditionally navigate to/from pages in that method.
//			
//			Page_ viewProductPage	= createCommonPage("PAGE_VIEW_PRODUCT_PROPERTIES_FILE");
//			viewCasePage.clickLinkInInfoPanel("assetDetailSupportedProduct");
//			viewProductPage.waitForFieldPresent("productDetailHeader");
//			viewCasePage.setVerificationDataValue("assetDetailProductLine", viewProductPage.getValueFromGUI("primaryProductLine"));
//			viewCasePage.setVerificationDataValue("assetProduct", viewProductPage.getValueFromGUI("productLine"));
//						
//			//Re-open the case to continue with the verification process
//			search(caseNumber);
//			commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
//			Util.sleep(10000);
//		}
		viewCasePage.verify();
				
		open(subPortalUrl);
		if(createCasePage.isFieldPresent("supportRequest")){
			String Url=createCasePage.getAttribute("supportRequest", "href");
			open(Url);
			createCasePage.waitForFieldVisible("softwareDownloadsCase");
			Util.sleep(2000);
		}
		else{
		createCasePage.click("createASupportCase");
		}
	 	mainWindow.select();	
	
	 	viewCaseInPortalPage.clickAndWait("viewCasesTab","keywordCaseID");
	 	Util.sleep(10000);
		viewCaseInPortalPage.populateField("accountInViewCase","All");
		Util.sleep(10000);
		commonPage.click("searchButton");
		Util.sleep(5000);
		viewCaseInPortalPage.populateField("keywordCaseID", caseNumber);
		Util.sleep(10000);
		viewCaseInPortalPage.clickAndWait("caseNo","closeCase");
		Util.sleep(10000);
		viewCaseInPortalPage.click("closeCase");
		Util.sleep(10000);
		viewCaseInPortalPage.populateField("reasonForCaseClose", "Test Close");
		createCasePage.click("submit");
		Util.sleep(10000);
		viewCaseInPortalPage.clickAndWait("closedCaseNo","customerSpecialist");
		Util.sleep(10000);
		viewCaseInPortalPage.verifyInstance("AFTER_CASE_CLOSE_PORTAL");
		Util.sleep(10000);
		salesforceLoginStage();
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList","Closed", "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.sleep(10000);
		viewCasePage.verifyInstance("AFTER_CASE_CLOSE");
		open(subPortalUrl);
		if(createCasePage.isFieldPresent("supportRequest")){
			String Url=createCasePage.getAttribute("supportRequest", "href");
			open(Url);
			createCasePage.waitForFieldVisible("softwareDownloadsCase");
			Util.sleep(2000);
		}
		else{
		createCasePage.click("createASupportCase");
		}
	 	mainWindow.select();	
	 	viewCaseInPortalPage.clickAndWait("viewCasesTab","keywordCaseID");
	 	Util.sleep(10000);
		viewCaseInPortalPage.populateField("accountInViewCase","All");
		Util.sleep(10000);
		commonPage.click("searchButton");
		Util.sleep(50000);		
		viewCaseInPortalPage.populateField("keywordCaseID", caseNumber);
		Util.sleep(10000);
		viewCaseInPortalPage.clickAndWait("caseNo","customerSpecialist");
		Util.sleep(10000);
		viewCaseInPortalPage.click("addCommentsAttachments");
		viewCaseInPortalPage.populateField("addComments", "Test Reopen");
		//createCasePage.click("submit");
		driver.findElement(By.xpath("//input[normalize-space(@value)='Submit']")).click();
		Util.sleep(5000);
		List<WebElement> OKList =driver.findElements(By.xpath("//*[@class='ui-dialog-buttonset']"));
		for(WebElement element : OKList){
			boolean flag = element.isDisplayed();
			
			if(flag)
			element.findElement(By.tagName("button")).click();
		
		}
		
//		driver.findElement(By.xpath("//div[contains(@aria-labelledby,'ui-id-4')]//span[contains(text(),'Ok')]/ancestor::button[1]")).click();
//		viewCaseInPortalPage.clickAndWait("okCloseCase","customerSpecialist");
		Util.sleep(10000);
		viewCaseInPortalPage.verifyInstance("CASE_REOPEN_STATUS_IN_PORTAL");
		salesforceLoginStage();
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList","Open", "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.sleep(10000);
		viewCasePage.verifyInstance("CASE_REOPEN_STATUS");
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