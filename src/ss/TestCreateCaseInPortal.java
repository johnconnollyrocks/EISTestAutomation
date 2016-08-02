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
public final class TestCreateCaseInPortal extends SSTestBase {
	public TestCreateCaseInPortal() {
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_CreateCaseInPortal() throws Exception {
	
		
		CreateFrom createFrom 	= getInterfaceType(SSConstants.SS_CREATE_FROM_ENUM_CONSTANT_NAME);
		CaseType caseType		= getCaseType(SSConstants.SS_CASE_TYPE_ENUM_CONSTANT_NAME);
	
		loginAsAutoUser();
		loginAsPortalUser();
		
		Case supportCase = utilCreateCaseObject(createFrom);
		supportCase.openCreateCaseFormInPortal();
		
     	//Verify default form values (some test specs don't require that we do this, so ignore the warning message to the console)
		Page_ createCasePage = supportCase.getCreateCasePage();
//		createCasePage.verifyInstance("DEFAULTS");
		
     	//Verify error messages on required form fields
		verifyRequiredFieldErrors(supportCase, caseType);
		
		supportCase = utilCreateCase(createFrom, caseType);
		
		//Be sure to make this call before calling supportCase.getViewCaseInPortalPage()!
		setViewCaseInPortalPageVerificationData(supportCase);
		
		Page_ viewCaseInPortalPage = supportCase.getViewCaseInPortalPage();
		viewCaseInPortalPage.verify();

		loginAsAutoUser(true);
		Util.sleep(10000);
		supportCase.open();
		commonPage.click("caseDetails");
		//Be sure to make this call before calling supportCase.getViewCasePage()!
		setViewCasePageVerificationData(supportCase);
		Page_ viewCasePage = supportCase.getViewCasePage();
		
//		//Update this condition if/when it is found that tests for other case types require this verification
//		if (caseType == CaseType.TECH_SUPPORT) {
//			//Don't do things like this (grabbing a value from a "one-off" page - viewProductPage in this case)
//			//  in setVerificationData because the page will differ between case types, and we don't want to
//			//  conditionally navigate to/from pages in that method.
//			
//			Page_ viewProductPage	= createCommonPage("PAGE_VIEW_PRODUCT_PROPERTIES_FILE");
//
//			//Get the expected value to use when verifying assetProduct
//
//			//NOTE that clickLinkInInfoPanelAndWait does not wait anymore
//			//viewCasePage.clickLinkInInfoPanelAndWait("supportedProduct");
//			viewCasePage.clickLinkInInfoPanel("supportedProduct");
//			//viewProductPage.waitForFieldPresent("primaryProductLine");
//			viewProductPage.waitForFieldPresent("productDetailHeader");
//			
////			viewCasePage.setVerificationDataValue("assetProduct", viewProductPage.getValueFromGUI("primaryProductLine"));
//			
//			//Re-open the case to continue with the verification process
//			supportCase.open();
//		}
		viewCasePage.verify();
		
		//Verify this first, since we don't want the time remaining to fall beneath the threshold while the length page-scope verification is being done
		//Update this condition if/when it is found that tests for other case types require this verification
		//As of August 2012, the Case Milestones related list is not present for a CRM Support case
		//if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.CRM_SUPPORT) || (caseType == CaseType.PROD_LIC_REG_AND_ACT)) {
		if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.PROD_LIC_REG_AND_ACT)) {
			assertTrueWithFlags(viewCasePage.getName(), "timeRemainingInCaseMilestonesRelatedList", "within " + SSConstants.DEFAULT_CASE_TIME_REMAINING_THRESHOLD + " minutes of SLA Hours (" + supportCase.getSlaHours() + ")", supportCase.checkTimeRemaining(0, SSConstants.DEFAULT_CASE_TIME_REMAINING_THRESHOLD));
		}
		
		
		
		//Update this condition if/when it is found that tests for other case types require this verification
		//As of August 2012, the Case Milestones related list is not present for a CRM Support case
		//if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.CRM_SUPPORT) || (caseType == CaseType.PROD_LIC_REG_AND_ACT)) {
		if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.PROD_LIC_REG_AND_ACT)) {
			viewCasePage.verifyRelatedListCell("nameInCaseMilestonesRelatedList", 0);
		}
		
		//Update this condition if/when it is found that tests for other case types require this verification
		//if (caseType == CaseType.CRM_SUPPORT) {
	    // 	//Because the Case Record Type field on the viewCasePage page contains not just the type but also
	    // 	//  "[Change]", it requires special handling  
		//	viewCasePage.verifyFieldStartsWith("caseRecordType", testProperties.getConstant("CASE_TYPE"));
		//}

		//Update this condition if/when it is found that tests for other case types require this verification
		if (caseType == CaseType.TECH_SUPPORT) {
			//Because the Created By field on the viewCasePage page contains not just the contact's name but also
	     	//  date and time, it requires special handling  
			viewCasePage.verifyFieldStartsWith("createdBy", supportCase.getContactName());
		}
		
		//***********************************************************************
		if (testProperties.constantExists("COMMENT")) {
			loginAsPortalUser();
	 		supportCase.openInPortal();
	
			supportCase.addComment(testProperties.getConstant("COMMENT"));
	
			viewCaseInPortalPage.verifyInstance("AFTER_ADD_COMMENT");
			
			loginAsAutoUser(true);
			supportCase.open();
			
			assertTrueWithFlags("Comment present in Case Comments related list on the Page '" + viewCasePage.getName() + "'", (viewCasePage.getNumRowsInRelatedList("caseCommentsRelatedList") == 1));
		}
		//***********************************************************************

		//***********************************************************************
		if (testProperties.constantExists("REQUEST_CLOSE_COMMENT")) {
			loginAsPortalUser();
			supportCase.openInPortal();
	
			supportCase.requestClose(testProperties.getConstant("REQUEST_CLOSE_COMMENT"));
			viewCaseInPortalPage.verifyInstance("AFTER_REQUEST_CLOSE");
			
			loginAsAutoUser(true);
			supportCase.open();
			
			viewCasePage.verifyInstance("AFTER_REQUEST_CLOSE");
		}
		//***********************************************************************
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
//				viewCasePage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
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
			case SW_DOWNLOAD:	{
				viewCasePage.setVerificationDataValue("severity", supportCase.getSeverity());
			}
				
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
				//viewCaseInPortalPage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
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
		Util.sleep(1500);
		
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
