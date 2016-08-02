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
 * Test class - TestCreateCaseUsingWebForm
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class TestCreateCaseUsingWebForm extends SSTestBase {
	public TestCreateCaseUsingWebForm() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
	
		launchSalesforce(testProperties.getConstant("URL_PRD"));
	}

	@Test
	public void TEST_CreateCaseUsingWebForm() throws Exception {
		String caseNumber=null;
		CreateFrom createFrom 	= getInterfaceType(SSConstants.SS_CREATE_FROM_ENUM_CONSTANT_NAME);
		CaseType caseType=null;
		Case supportCase = utilCreateCaseObject(createFrom);
		supportCase = utilCreateCase(createFrom, caseType);
		 Page_ viewCasePage = supportCase.getViewCasePage();
		launchSalesforce(getAppServerBaseURL());
		loginAsAutoUser(true);
		commonPage.click("tabCases");
		viewCasePage.populateInstance("CASE_SEARCH");
		viewCasePage.click("goButton");
		viewCasePage.click("firstCaseInCasesList");
		commonPage.click("caseDetails");
		Util.printInfo("opened case . verify on view case page");
      	viewCasePage.verify();
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
