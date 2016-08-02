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
 * Test class - TestCreateCaseInPortal
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateCaseInCustmrPortalAddActivityCloseReopenCase extends SSTestBase {
	public TestCreateCaseInCustmrPortalAddActivityCloseReopenCase() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_CreateCaseInCustmrPortalCheckDefaultFunctnlty() throws Exception {
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
		createCasePage.clickAndWait("send","newComments");
		createCasePage.click("newComments");
		createCasePage.populateInstance("ADD_COMMENTS");
		commonPage.clickAndWait("saveButton","editButton");
		createCasePage.click("newDefect");
		createCasePage.populateInstance("ADD_DEFECT");
		commonPage.clickAndWait("saveButton","editButton");
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.sleep(10000);
		viewCasePage.verifyInstance("CHECK_ACTIVITY");
		
		loginAsPortalUser();
	 	mainWindow.select();	
		EISTestBase.open(CustUrl);
		viewCaseInPortalPage.clickAndWait("viewCasesTab","keywordCaseID");
	 	Util.sleep(10000);
		viewCaseInPortalPage.populateField("keywordCaseID", caseNumber);
		Util.sleep(10000);
		viewCaseInPortalPage.clickAndWait("caseNo","closeCase");
		Util.sleep(10000);
		viewCaseInPortalPage.verifyInstance("CHECK_OWNER");
		viewCaseInPortalPage.click("viewActivity");
		Util.sleep(5000);
		viewCaseInPortalPage.verifyInstance("CHECK_ACTIVITY_IN_PORTAL");
		viewCaseInPortalPage.click("closeCase");
		Util.sleep(5000);
		viewCaseInPortalPage.populateField("reasonForCaseClose", "Test Close");
		createCasePage.click("submit");
		Util.sleep(5000);
		viewCaseInPortalPage.click("closedCaseNo");
		Util.sleep(5000);
		viewCaseInPortalPage.verifyInstance("AFTER_CASE_CLOSE_PORTAL");
		loginAsAutoUser(true);
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList","Closed", "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.sleep(10000);
		viewCasePage.verifyInstance("AFTER_CASE_CLOSE");
		Util.sleep(5000);
		loginAsPortalUser();
	 	mainWindow.select();	
		EISTestBase.open(CustUrl);
		Util.sleep(5000);
		viewCaseInPortalPage.clickAndWait("viewCasesTab","keywordCaseID");
	 	Util.sleep(10000);
		viewCaseInPortalPage.populateField("keywordCaseID", caseNumber);
		Util.sleep(10000);
		viewCaseInPortalPage.click("caseNo");
		Util.sleep(10000);
		viewCaseInPortalPage.click("addCommentsAttachments");
		Util.sleep(5000);
		viewCaseInPortalPage.populateField("addComments", "Test Reopen");
		createCasePage.clickToSubmit("submit");
		Util.sleep(5000);
		viewCaseInPortalPage.clickToSubmit("closeLink");
		Util.sleep(5000);
		viewCaseInPortalPage.verifyInstance("CASE_REOPEN_STATUS_IN_PORTAL");
		loginAsAutoUser(true);
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList","Re-open", "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.sleep(10000);
		viewCasePage.verifyInstance("CASE_REOPEN_STATUS");
	}
		
	private void setViewCasePageVerificationData(Case supportCase) {
		Page_ viewCasePage = supportCase.getViewCasePage();
		CaseType caseType = supportCase.getCaseType();

		switch (caseType) {
			case TECH_SUPPORT:	{
				viewCasePage.setVerificationDataValue("caseReason", supportCase.getReason());
				viewCasePage.setVerificationDataValue("severity", supportCase.getSeverity());
				viewCasePage.setVerificationDataValue("operatingSystem", supportCase.getOperatingSystem());

				//As of early August, 2012, the actual content of the viewCasePage.supportedProduct field is the value
				//  that was populated in the createCasePage.supportedAssetText field when creating the case,
				//  instead of the createCasePage.supportedProductText field, where it belongs!
				//On August 13, 2012, JP gave me a new supported asset S/N that returns a valid value in
				//  the createCasePage.supportedProductText field.  I don't know if it works because
				//  the attributes of that particular asset are unique in that it returns the same value
				//  for both fields, or whether the previous S/N we were using was for an asset that was
				//  not configured correctly. All I know is that it works now!!!
				viewCasePage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
				//viewCasePage.setVerificationDataValue("supportedProduct", supportCase.getSupportedAsset());
				break;
			}
			case PROD_LIC_REG_AND_ACT:
				//In the tests we have created so far, we do not need to set additional verification data
				break;
			case API_SUPPORT:	{
				viewCasePage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
				viewCasePage.setVerificationDataValue("releaseVersion", supportCase.getReleaseVersion());
				viewCasePage.setVerificationDataValue("operatingSystem", supportCase.getOperatingSystem());
				viewCasePage.setVerificationDataValue("api", supportCase.getApi());
				break;
			}
			case SUBMIT_ORDER:
				//In the tests we have created so far, we do not need to set additional verification data
				break;
			case CRM_SUPPORT:	{
				viewCasePage.setVerificationDataValue("status", supportCase.getStatus());
				viewCasePage.setVerificationDataValue("severity", supportCase.getSeverity());
				viewCasePage.setVerificationDataValue("applicationType", supportCase.getApplicationType());

				//As of 06/29/2012, this element is no longer part of a CRM case
				//viewCasePage.setVerificationDataValue("industryType", supportCase.getIndustryType());

				//As of 08/14/2012, this element is no longer part of a CRM case
				//viewCasePage.setVerificationDataValue("functionalArea", supportCase.getFunctionalArea());

				viewCasePage.setVerificationDataValue("businessArea", supportCase.getBusinessArea());
				viewCasePage.setVerificationDataValue("caseArea", supportCase.getCaseArea());
				break;
			}
			case BUSINESS_SVC_REQUEST:	{
				fail("BUSINESS_SVC_REQUEST cases are not supported in the portal interface");
		     	break;
			}
			
			//We haven't defined any test properties or test cases for these yet
			case SUBMIT_RETURNS_CREDITS:
			case STATUS_ORDERS_RETURNS_CREDITS:
			case SUB_CONTRACT_INQUIRY:
			case SW_DOWNLOAD:
			case CHANGE_SW_COORD_OR_CONTRACT_MGR:	
			case MY_CONTRACT_INFO:
			case SUB_PROGRAM_INFO:
			case OTHER:
				break;
			case PROD_SUPPORT:	{
				viewCasePage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
				viewCasePage.setVerificationDataValue("severity", supportCase.getSeverity());
				viewCasePage.setVerificationDataValue("assetProduct", supportCase.getSupportedProduct());
		//		viewCasePage.setVerificationDataValue("assetDetailProductLine", supportCase.getSupportedProduct());
				break;
			}
			default:		{
				fail("Unhandled member of ss.SSConstants.CaseType enumerated type: " + caseType);
			}
		}		
	}

	private void setViewCaseInPortalPageVerificationData(Case supportCase) {
		//NOTE that there MAY also be some interface-specific dependencies

		Page_ viewCaseInPortalPage = supportCase.getViewCaseInPortalPage();
		CaseType caseType = supportCase.getCaseType();

		switch (caseType) {
			case TECH_SUPPORT:	{
				viewCaseInPortalPage.setVerificationDataValue("caseReason", supportCase.getReason());
				viewCaseInPortalPage.setVerificationDataValue("severity", supportCase.getSeverity());
				viewCaseInPortalPage.setVerificationDataValue("operatingSystem", supportCase.getOperatingSystem());

				//As of early August, 2012, the actual content of the viewCaseInPortalPage.supportedProduct field is the value
				//  that was populated in the createCasePage.supportedAssetText field when creating the case,
				//  instead of the createCasePage.supportedProductText field, where it belongs!
				//On August 13, 2012, JP gave me a new supported asset S/N that returns a valid value in
				//  the createCasePage.supportedProductText field.  I don't know if it works because
				//  the attributes of that particular asset are unique in that it returns the same value
				//  for both fields, or whether the previous S/N we were using was for an asset that was
				//  not configured correctly. All I know is that it works now!!!
				//viewCaseInPortalPage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
				//viewCaseInPortalPage.setVerificationDataValue("supportedProduct", supportCase.getSupportedAsset());
				viewCaseInPortalPage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
				break;
			}
			case PROD_LIC_REG_AND_ACT:
				//In the tests we have created so far, we do not need to set additional verification data
				break;
			case API_SUPPORT:	{
				viewCaseInPortalPage.setVerificationDataValue("releaseVersion", supportCase.getReleaseVersion());
				viewCaseInPortalPage.setVerificationDataValue("productLanguage", supportCase.getProductLanguage());
				viewCaseInPortalPage.setVerificationDataValue("operatingSystem", supportCase.getOperatingSystem());
				viewCaseInPortalPage.setVerificationDataValue("api", supportCase.getApi());
				break;
			}
			case SUBMIT_ORDER:
				//In the tests we have created so far, we do not need to set additional verification data
				break;
			case CRM_SUPPORT:	{
				viewCaseInPortalPage.setVerificationDataValue("status", supportCase.getStatus());
				viewCaseInPortalPage.setVerificationDataValue("severity", supportCase.getSeverity());
				viewCaseInPortalPage.setVerificationDataValue("applicationType", supportCase.getApplicationType());

				//As of 06/29/2012, this element is no longer part of a CRM case
				//viewCaseInPortalPage.setVerificationDataValue("industryType", supportCase.getIndustryType());

				//As of 08/14/2012, this element is no longer part of a CRM case
				//viewCaseInPortalPage.setVerificationDataValue("functionalArea", supportCase.getFunctionalArea());
				
				viewCaseInPortalPage.setVerificationDataValue("businessArea", supportCase.getBusinessArea());
				viewCaseInPortalPage.setVerificationDataValue("caseArea", supportCase.getCaseArea());
				break;
			}
			case BUSINESS_SVC_REQUEST:{
				fail("BUSINESS_SVC_REQUEST cases are not supported in the portal interface");
		     	break;
			}
			
			//We haven't defined any test properties or test cases for these yet
			case SUBMIT_RETURNS_CREDITS:
			case STATUS_ORDERS_RETURNS_CREDITS:
			case SUB_CONTRACT_INQUIRY:
			case SW_DOWNLOAD:
			case CHANGE_SW_COORD_OR_CONTRACT_MGR:	
			case MY_CONTRACT_INFO:
			case SUB_PROGRAM_INFO:
			case OTHER:
				break;
			default:		{
				fail("Unhandled member of ss.SSConstants.CaseType enumerated type: " + caseType);
			}
		}		
	}

	private void verifyRequiredFieldErrors(Case supportCase, CaseType caseType) {
		//Note that in many cases there are required fields in addition to the ones noted here,
		//  but because they have default values they do not pop an error message when the form
		//  is submitted before being populated

		Page_ createCasePage = supportCase.getCreateCasePage();
		Page_ commonPortalPage = supportCase.getCommonPortalPage();
		
		commonPortalPage.click("saveOrSubmitButton");
		
		switch (caseType) {
			case TECH_SUPPORT:	{			//SC	PC
				//Field level errors are not generated for TECH_SUPPORT cases (both SC and PC)
				createCasePage.verifyErrorCheckPageOnly(false);
				break;
			}
			case PROD_LIC_REG_AND_ACT:	{	//SC
				createCasePage.verifyErrorCheckFieldOnly("serialNumber", false);
				createCasePage.verifyErrorCheckFieldOnly("subject", false);
				createCasePage.verifyErrorCheckFieldOnly("description", false);
				break;
			}
			case API_SUPPORT:	{			//SC
				//Field level errors are not generated for API_SUPPORT cases
				createCasePage.verifyErrorCheckPageOnly(false);
				break;
			}
			case SUBMIT_ORDER:	{			//PC
				createCasePage.verifyErrorCheckFieldOnly("poNumber", false);
				createCasePage.verifyErrorCheckFieldOnly("productTotal", false);
				createCasePage.verifyErrorCheckFieldOnly("subscriptionTotal", false);
				createCasePage.verifyErrorCheckFieldOnly("subject", false);
				createCasePage.verifyErrorCheckFieldOnly("description", false);
				break;
			}
			case CRM_SUPPORT:	{			//PC
				createCasePage.verifyErrorCheckFieldOnly("applicationType", false);

				//As of 06/29/2012, this element is no longer part of a CRM case
				//createCasePage.verifyErrorCheckFieldOnly("industryType", false);

				//As of 08/14/2012, this element is no longer part of a CRM case
				//createCasePage.verifyErrorCheckFieldOnly("functionalArea", false);
				
				createCasePage.verifyErrorCheckFieldOnly("caseArea", false);
				createCasePage.verifyErrorCheckFieldOnly("subject", false);
				createCasePage.verifyErrorCheckFieldOnly("description", false);
				break;
			}
			case BUSINESS_SVC_REQUEST:	{
				fail("BUSINESS_SVC_REQUEST cases are not supported in the portal interface");
		     	break;
			}

			//We haven't defined any test properties or test cases for these yet
			case SUBMIT_RETURNS_CREDITS:
			case STATUS_ORDERS_RETURNS_CREDITS:
			case SUB_CONTRACT_INQUIRY:
			case SW_DOWNLOAD:
			case CHANGE_SW_COORD_OR_CONTRACT_MGR:	
			case MY_CONTRACT_INFO:
			case SUB_PROGRAM_INFO:
			case OTHER:
				break;
			default:		{
				fail("Unhandled member of ss.SSConstants.CaseType enumerated type: " + caseType);
			}
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
