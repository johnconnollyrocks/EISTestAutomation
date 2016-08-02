package ss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Case;
import common.Util;
import common.Page_;

import common.Case.CreateFrom;
import common.Case.CaseType;

/**
 * Test class - TestCreateCaseInPortal
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateAndCloneCaseFromSC extends SSTestBase {
	
  
	public TestCreateAndCloneCaseFromSC() {
		super();
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
		
     	//Verify default form values
		Page_ createCasePage = supportCase.getCreateCasePage();
		
		createCasePage.verifyInstance("DEFAULTS");
		
     	//Verify error messages on required form fields
		verifyRequiredFieldErrors(supportCase, caseType);
		
		supportCase = utilCreateCase(createFrom, caseType);
		
		//Be sure to make this call before calling supportCase.getViewCaseInPortalPage()!
		setViewCaseInPortalPageVerificationData(supportCase);
		Page_ viewCaseInPortalPage = supportCase.getViewCaseInPortalPage();

		viewCaseInPortalPage.verify();
		viewCaseInPortalPage.clickToSubmit("cloneCaseButton","");
		if (!testProperties.getConstant("NEW_SEVERITY").isEmpty()) {
			createCasePage.populateInstance("MODIFY_SEVERITY_VERFICATION");
			viewCaseInPortalPage.clickToSubmit("saveCaseButton", "");
			viewCaseInPortalPage.verifyInstance("MODIFY_SEVERITY_VERFICATION");

		}

		if (!testProperties.getConstant("NEW_PRODUCT").isEmpty()) {
			Page_ productPopUpPage = supportCase.getProductPopUpPage();
			String locator = createCasePage.clickAndWaitForPopUpToOpen("product");
			productPopUpPage.selectWindow(locator);
			productPopUpPage.parseFieldLocatorsTokens("productLineLink",productPopUpPage.getConstant("NEW_PRODUCT"));
			productPopUpPage.clickToSubmit("productLineLink", "");
			mainWindow.select();
			createCasePage.populateInstance("CHANGE_PRODUCT");
			viewCaseInPortalPage.clickToSubmit("saveCaseButton", "");
			viewCaseInPortalPage.verifyInstance("CHANGE_PRODUCT");

		}

		String caseNumber = viewCaseInPortalPage.getValueFromGUI("caseNumber");

		loginAsAutoUser(true);
		Util.sleep(50000);
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"),"caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Page_ viewCasePage = supportCase.getViewCasePage();
		
		if (!testProperties.getConstant("NEW_SEVERITY").isEmpty()) {
			viewCasePage.verifyInstance("MODIFY_SEVERITY_VERFICATION");
			viewCasePage.verifyRelatedListCellInstance("nameInCaseMilestonesRelatedList", 0, "CHECKMILESTONE");
			assertTrueWithFlags(viewCasePage.getName(),"timeRemainingInCaseMilestonesRelatedList","within "+ SSConstants.DEFAULT_CASE_TIME_REMAINING_THRESHOLD+ " minutes of SLA Hours ("+ supportCase.getSlaHours() + ")",supportCase.checkTimeRemaining(0, SSConstants.DEFAULT_CASE_TIME_REMAINING_THRESHOLD));
		}

		if (!testProperties.getConstant("NEW_PRODUCT").isEmpty()) {
			viewCasePage.verifyInstance("CHANGE_PRODUCT");
			viewCasePage.verifyRelatedListCellInstance("nameInCaseMilestonesRelatedList", 0, "CHECKMILESTONE");
			assertTrueWithFlags(viewCasePage.getName(),"timeRemainingInCaseMilestonesRelatedList","within "+ SSConstants.DEFAULT_CASE_TIME_REMAINING_THRESHOLD+ " minutes of SLA Hours ("+ supportCase.getSlaHours() + ")",supportCase.checkTimeRemaining(0, SSConstants.DEFAULT_CASE_TIME_REMAINING_THRESHOLD));
		}			
	}
		

		//***********************************************************************

		//***********************************************************************

		
	private void setViewCaseInPortalPageVerificationData(Case supportCase) {
		//NOTE that there MAY also be some interface-specific dependencies

		Page_ viewCaseInPortalPage = supportCase.getViewCaseInPortalPage();
		CaseType caseType = supportCase.getCaseType();

		switch (caseType) {
			case TECH_SUPPORT:	{
				viewCaseInPortalPage.setVerificationDataValue("caseReason", supportCase.getReason());
				viewCaseInPortalPage.setVerificationDataValue("severity", supportCase.getSeverity());
				viewCaseInPortalPage.setVerificationDataValue("operatingSystem", supportCase.getOperatingSystem());
			//	viewCaseInPortalPage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
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

				viewCaseInPortalPage.setVerificationDataValue("functionalArea", supportCase.getFunctionalArea());
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

				createCasePage.verifyErrorCheckFieldOnly("functionalArea", false);
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
