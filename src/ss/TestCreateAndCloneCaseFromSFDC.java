package ss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Case.CreateFrom;
import common.Case.CaseType;

import common.Case;
import common.EISTestBase;
import common.Page_;
import common.Util;

/**
 * Test class - TestCreateCaseInSFDC
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateAndCloneCaseFromSFDC extends SSTestBase {
	public TestCreateAndCloneCaseFromSFDC() {
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
		//AfterCreateCaseOperation afterCreateCaseOperation = getCaseOperation(SSConstants.SS_CASE_OPERATION_ENUM_CONSTANT_NAME);
		
		Page_ viewProductPage 	= createCommonPage("PAGE_VIEW_PRODUCT_PROPERTIES_FILE");
		Page_ viewContactPage	= createCommonPage("PAGE_VIEW_CONTACT_PROPERTIES_FILE");

		loginAsAutoUser();
		
		search(testProperties.getConstant("CONTACT_CSN"));
		
		//We search the results on Account CSN to get the contact we want; we don't search on the contact name itself
		//  because there are often duplicate names

		//NOTE that clickLinkInRelatedListAndWait does not exist anymore,
		//  so we may need to apply some "wait" logic here
		//DEBUG (07/30/2012) - do we need to do this?
		//commonPage.clickLinkInRelatedListAndWait("accountCsnInContactsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInContactsRelatedList");
		commonPage.clickLinkInRelatedList("accountCsnInContactsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInContactsRelatedList");
		viewContactPage.waitForFieldPresent("loginAsPortalUserLink");
		
		
		Case supportCase = utilCreateCase(createFrom, caseType);
		supportCase.open();
		commonPage.click("caseDetails");
		
		//Be sure to make this call before calling supportCase.getViewCasePage()!
		setViewCasePageVerificationData(supportCase);
		//Page_ createCasePage = supportCase.getCreateCasePage();
		Page_ viewCasePage = supportCase.getViewCasePage();
		
//	if (caseType == CaseType.TECH_SUPPORT) {
//			//This is done for TECH_SUPPORT cases - what about others?  Don't do things like
//			//  this (grabbing a value from a "one-off" page - viewProductPage in this case)
//			//  in setVerificationData because the page will differ between case types, and
//			//  we don't want to conditionally navigate to/from pages in that method.
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
//		}
		
		//Re-open the case to continue with the verification process
		supportCase.open();
		Util.printInfo("opened case . verify on view case page");
		viewCasePage.verify();

		if (caseType == CaseType.TECH_SUPPORT) {
			//Because the Created By field on the viewCasePage page contains not just the owner's name but also
	     	//  date and time, it requires special handling  
			viewCasePage.verifyFieldStartsWith("createdBy", supportCase.getOwner());
		}
		//search(supportCase.getNumber());
		
		viewCasePage.clickToSubmit("cloneCaseButton","");
		//viewCasePage.waitForFieldPresent("saveCaseButton");
			
     	viewCasePage.clickToSubmit("saveCaseButton","");
		viewCasePage.verifyInstance("VERIRY_PRODUCT");
	//	viewCasePage.verifyRelatedListCellInstance("nameInCaseMilestonesRelatedList", 0, "CHECKMILESTONE");
	//	assertTrueWithFlags(viewCasePage.getName(),"timeRemainingInCaseMilestonesRelatedList","within "+ SSConstants.DEFAULT_CASE_TIME_REMAINING_THRESHOLD+ " minutes of SLA Hours ("+ supportCase.getSlaHours() + ")",supportCase.checkTimeRemaining(0, SSConstants.DEFAULT_CASE_TIME_REMAINING_THRESHOLD));
	}
	
	
	private void setViewCasePageVerificationData(Case supportCase) {
		CaseType caseType = supportCase.getCaseType();
		Page_ viewCasePage = supportCase.getViewCasePage();

		switch (caseType) {
			case TECH_SUPPORT:	{
//				viewCasePage.setVerificationDataValue("caseOwner", supportCase.getOwner());
				viewCasePage.setVerificationDataValue("caseReason", supportCase.getReason());
				viewCasePage.setVerificationDataValue("severity", supportCase.getSeverity());
				viewCasePage.setVerificationDataValue("operatingSystem", supportCase.getOperatingSystem());
//				viewCasePage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
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
