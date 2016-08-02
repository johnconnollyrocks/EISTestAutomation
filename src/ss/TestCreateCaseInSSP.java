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
 * Test class - TestCreateCaseInSSP
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class TestCreateCaseInSSP extends SSTestBase {
	public TestCreateCaseInSSP() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
	
		launchSalesforce(testProperties.getConstant("URL_PRD"));
	}

	@Test
	public void TEST_CreateCaseInSSP() throws Exception {
		String caseNumber=null;
		CreateFrom createFrom 	= getInterfaceType(SSConstants.SS_CREATE_FROM_ENUM_CONSTANT_NAME);
		CaseType caseType=null;
		
		
		loginForSSP(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"), false);
		Case supportCase = utilCreateCaseObject(createFrom);
		
     	//Verify default form values
	//	Page_ createCasePage = supportCase.getCreateCasePage();
		supportCase = utilCreateCase(createFrom, caseType);
		launchSalesforce(getAppServerBaseURL());
		loginAsAutoUser(true);
		caseNumber= supportCase.getName();
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.printInfo("opened case . verify on view case page");
        Page_ viewCasePage = supportCase.getViewCasePage();
		viewCasePage.verify();
		assertTrueWithFlags("Comment present in Case Comments related list on the Page '" + viewCasePage.getName() + "'", (viewCasePage.getNumRowsInRelatedList("caseCommentsRelatedList") == 1));
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
