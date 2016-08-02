package ss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Case.CreateFrom;
import common.Case.CaseType;
import ss.SSConstants.AfterCreateCaseOperation;
import common.Case;
import common.EISTestBase;
import common.Page_;

/**
 * Test class - TestCreateCaseInSFDC
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateAndCloseCaseInSFDC extends SSTestBase {
	public TestCreateAndCloseCaseInSFDC() {
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_CreateCaseInSFDC() throws Exception {
		
		CreateFrom createFrom 	= getInterfaceType(SSConstants.SS_CREATE_FROM_ENUM_CONSTANT_NAME);
		CaseType caseType		= getCaseType(SSConstants.SS_CASE_TYPE_ENUM_CONSTANT_NAME);
		AfterCreateCaseOperation afterCreateCaseOperation = getCaseOperation(SSConstants.SS_CASE_OPERATION_ENUM_CONSTANT_NAME);

	//	Page_ viewProductPage 	= createCommonPage("PAGE_VIEW_PRODUCT_PROPERTIES_FILE");
		Page_ viewContactPage	= createCommonPage("PAGE_VIEW_CONTACT_PROPERTIES_FILE");

		loginAsAutoUser();
		
		search(testProperties.getConstant("CONTACT_CSN"));
		
		//We search the results on Account CSN to get the contact we want; we don't search on the contact name itself
		//  because there are often duplicate names

		//NOTE that clickLinkInRelatedListAndWait does not exist anymore,
		//  so we may need to apply some "wait" logic here
		//DEBUG (07/30/2012) - do we need to do this?
		commonPage.clickLinkInRelatedList("accountCsnInContactsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInContactsRelatedList");
//		commonPage.clickLinkInRelatedList("accountCsnInContactsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInContactsRelatedList");
		viewContactPage.waitForFieldPresent("loginAsPortalUserLink");
		
		
		Case supportCase = utilCreateCase(createFrom, caseType, afterCreateCaseOperation);
		supportCase.open();
//		commonPage.click("caseDetails");
		//Be sure to make this call before calling supportCase.getViewCasePage()!
		setViewCasePageVerificationData(supportCase);
		Page_ viewCasePage = supportCase.getViewCasePage();
		Page_ closeCasePage = supportCase.getCloseCasePage();
		
		//Re-open the case to continue with the verification process
		supportCase.open();

		viewCasePage.verify();
		
		viewCasePage.click("closeCaseButton");
		closeCasePage.waitForFieldPresent("internalCommentsTextArea");

		closeCasePage.verifyInstance("BEFORE_CLOSE_CASE");

		closeCasePage.populateInstance("AT_CLOSE_CASE");
		closeCasePage.click("nextButton");
		
		if (caseType == CaseType.BUSINESS_SVC_REQUEST) {		
		
			closeCasePage.verifyInstance("FEEDBACK");
			
			closeCasePage.populateInstance("CLOSE_CASE");
			closeCasePage.click("nextButton");
		}
		
		viewCasePage.waitForFieldEnabled("closeCaseButton");
		viewCasePage.verifyInstance("AFTER_CLOSE_CASE");
	}
	
	private void setViewCasePageVerificationData(Case supportCase) {
		CaseType caseType = supportCase.getCaseType();
		Page_ viewCasePage = supportCase.getViewCasePage();

		switch (caseType) {
			case TECH_SUPPORT:	{
				viewCasePage.setVerificationDataValue("caseOwner", supportCase.getOwner());
				viewCasePage.setVerificationDataValue("caseReason", supportCase.getReason());
				viewCasePage.setVerificationDataValue("severity", supportCase.getSeverity());
				viewCasePage.setVerificationDataValue("operatingSystem", supportCase.getOperatingSystem());
			//	viewCasePage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
				break;
			}
				
			//We haven't defined any test properties or test cases for these yet
			case PROD_LIC_REG_AND_ACT:
			case API_SUPPORT:
			case SUBMIT_ORDER:
			case CRM_SUPPORT:
			case SUBMIT_RETURNS_CREDITS:
			case STATUS_ORDERS_RETURNS_CREDITS:
			case SUB_CONTRACT_INQUIRY:
			case BUSINESS_SVC_REQUEST:	
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
