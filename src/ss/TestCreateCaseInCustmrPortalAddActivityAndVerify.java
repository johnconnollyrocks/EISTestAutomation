package ss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Case;
import common.EISTestBase;
import common.Page_;
import common.Util;

import common.Case.CreateFrom;
import common.Case.CaseType;

/**
 * Test class - TestCreateCaseInCustmrPortalAddActivityAndVerify
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class TestCreateCaseInCustmrPortalAddActivityAndVerify extends SSTestBase {
	public TestCreateCaseInCustmrPortalAddActivityAndVerify() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_CreateCaseInCustmrPortalAddActivityAndVerify() throws Exception {
		String CustUrl ="https://autodesk--adsksfdev.cs12.my.salesforce.com/apex/Case_CustomerPortalLandingPage#";
		CreateFrom createFrom 	= getInterfaceType(SSConstants.SS_CREATE_FROM_ENUM_CONSTANT_NAME);
		CaseType caseType		= getCaseType(SSConstants.SS_CASE_TYPE_ENUM_CONSTANT_NAME);
	
		loginAsAutoUser();
		loginAsPortalUser();
		
		Case supportCase = utilCreateCaseObject(createFrom);

		Page_ createCasePage = supportCase.getCreateCasePage();
		open(CustUrl);
		supportCase = utilCreateCase(createFrom, caseType);
		Page_ viewCaseInPortalPage = supportCase.getViewCaseInPortalPage();
		String caseNumber= supportCase.getName();
		 
		viewCaseInPortalPage.clickAndWait("caseNo","closeCase");
		viewCaseInPortalPage.verify();
		String clause=EISTestBase.testProperties.getConstant("OPEN_DETAILS");
	 	if(clause.equalsIgnoreCase("Yes")){
			viewCaseInPortalPage.click("openDetails");
			viewCaseInPortalPage.verifyInstance("HOST_DETAILS");
			
		}
		loginAsAutoUser(true);
		Util.sleep(50000);
		search(caseNumber);
		
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.printInfo("opened case verify on view case page");
		Page_ viewCasePage = supportCase.getViewCasePage();
        
      
		if(caseType ==CaseType.PROD_SUPPORT||caseType ==CaseType.INSTAL_CONFIG_ISSUE_CASE||caseType ==CaseType.IDEAS_SUGGESTION_CASE) {
			//Don't do things like this (grabbing a value from a "one-off" page - viewProductPage in this case)
			//  in setVerificationData because the page will differ between case types, and we don't want to
			//  conditionally navigate to/from pages in that method.
			
			Page_ viewProductPage	= createCommonPage("PAGE_VIEW_PRODUCT_PROPERTIES_FILE");
			viewCasePage.clickLinkInInfoPanel("product");
			viewProductPage.waitForFieldPresent("productDetailHeader");
			viewCasePage.setVerificationDataValue("assetDetailProductLine", viewProductPage.getValueFromGUI("primaryProductLine"));
			viewCasePage.setVerificationDataValue("assetProduct", viewProductPage.getValueFromGUI("productLine"));
						
			//Re-open the case to continue with the verification process
			search(caseNumber);
			commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
			commonPage.click("caseDetails");
			Util.sleep(10000);
		}
		viewCasePage.verify();
		createCasePage.click("changeCaseOwner");
		createCasePage.populateInstance("CHANGE_OWNER");
		commonPage.clickAndWait("saveButton","editButton");
		createCasePage.click("logACall");
		createCasePage.populateInstance("LOG_A_CALL");
		commonPage.clickAndWait("saveButton","editButton");
		createCasePage.click("sendAnEmail");
		createCasePage.populateInstance("SEND_A_MAIL");
		createCasePage.click("send");
		createCasePage.click("newComments");
		createCasePage.populateInstance("ADD_COMMENTS");
		commonPage.clickAndWait("saveButton","editButton");
		
		if(caseType ==CaseType.PROD_SUPPORT) {
		createCasePage.click("newDefect");
		createCasePage.populateInstance("ADD_DEFECT");
		commonPage.clickAndWait("saveButton","editButton");
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.sleep(10000);
		}
		viewCasePage.verifyInstance("CHECK_ACTIVITY");
		
		loginAsPortalUser();
	 	mainWindow.select();	
		EISTestBase.open(CustUrl);
		viewCaseInPortalPage.clickAndWait("viewCasesTab","keywordCaseID");
	 	Util.sleep(10000);
		viewCaseInPortalPage.populateField("accountInViewCase","All");
		Util.sleep(10000);
		commonPage.clickToSubmit("searchButton");
		viewCaseInPortalPage.populateField("keywordCaseID", caseNumber);
		Util.sleep(10000);
		viewCaseInPortalPage.click("caseNo");
		Util.sleep(10000);
		viewCaseInPortalPage.verifyInstance("CHECK_OWNER");
		viewCaseInPortalPage.click("viewActivity");
		viewCaseInPortalPage.verifyInstance("CHECK_ACTIVITY_IN_PORTAL");
		
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
