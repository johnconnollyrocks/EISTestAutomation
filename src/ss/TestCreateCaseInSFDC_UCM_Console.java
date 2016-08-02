package ss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import ss.SSConstants.AfterCreateCaseOperation;

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
public final class TestCreateCaseInSFDC_UCM_Console extends SSTestBase {
	public TestCreateCaseInSFDC_UCM_Console() {
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
		
		loginAsAutoUser();
	
		search(testProperties.getConstant("CONTACT_CSN"));
		
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='navigatortab']//iframe")));
//		swithToIframeWhereMyFieldExist(commonPage,"accountCsnInContactsRelatedList");
		commonPage.clickLinkInRelatedList("accountCsnInContactsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInContactsRelatedList");
		driver.switchTo().defaultContent();
		Case supportCase = utilCreateCase(createFrom, caseType,afterCreateCaseOperation);

		Page_ viewCasePage = supportCase.getViewCasePage();
		Page_ closeCasePage = supportCase.getCloseCasePage();
	
//		supportCase.sendEmail();
		

 		supportCase.call();

 		supportCase.comment();
 		driver.switchTo().defaultContent();
 		EISTestBase.swithToIframeWhereMyFieldExist(viewCasePage,"editOwner");
 		supportCase.changeOwner(testProperties.getConstant("changeOwner1"));

 		EISTestBase.swithToIframeWhereMyFieldExist(viewCasePage,"changedOwnerTo");
 		String newOwner=viewCasePage.getValueFromGUI("changedOwnerTo");
 		assertEquals(newOwner, testProperties.getConstant("NEW_OWNER"));
 		driver.switchTo().defaultContent();
 		EISTestBase.swithToIframeWhereMyFieldExist(viewCasePage,"editOwner");
 		supportCase.changeOwner(testProperties.getConstant("changeOwner2"));
 		EISTestBase.swithToIframeWhereMyFieldExist(viewCasePage,"changedOwnerTo");
 		newOwner=viewCasePage.getValueFromGUI("changedOwnerTo1");
 		assertEquals(newOwner, "GSS User1");
// 		viewCasePage.verifyFieldExists("emailSentMessage");
 		viewCasePage.verifyFieldExists("callActivityCheck");
 		viewCasePage.verifyFieldExists("commentsVerify");
 		viewCasePage.click("closeCase");
 		Util.sleep(5000);
 		driver.switchTo().defaultContent();
 		commonPage.click("salesforceTab");
// 		driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='navigatortab']//iframe[@id='ext-comp-1034']")));
 		EISTestBase.swithToIframeWhereMyFieldExist(closeCasePage,"nextButton");
 		closeCasePage.click("nextButton");
 		Util.sleep(5000);
 		String status=closeCasePage.getAttribute("closedSolvedRadioButton", "checked");
 		assertEquals(status, "true");
 		closeCasePage.populateField("internalCommentsTextArea", "Closing the case");
 		closeCasePage.click("nextButton");
 		
 		closeCasePage.refresh(5000);
 		driver.switchTo().defaultContent();
 		EISTestBase.swithToIframeWhereMyFieldExist(viewCasePage,"changedOwnerTo");
		commonPage.click("caseDetails");
		
		viewCasePage.verifyInstance("CASE_DETAILS");
		viewCasePage.verifyFieldContains("caseRecordtype", testProperties.getConstant("CASETYPE"));

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
				viewCasePage.setVerificationDataValue("supportedProduct", supportCase.getSupportedProduct());
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
			case DEV:
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
