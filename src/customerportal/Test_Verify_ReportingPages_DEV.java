package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;

public class Test_Verify_ReportingPages_DEV extends CustomerPortalTestBase {
	public Test_Verify_ReportingPages_DEV() throws IOException {
		super("Browser",getAppBrowser());
//		super();		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_Verify_ReportingPages_DEV() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.waitForFieldPresent("AllProductsandServices");

		Util.printInfo("clicking on Report Link and Verifying Individual and Shared cloud credits header in reporting page...");

		homePage.clickAndWait("reporting","byUsers");
		homePage.waitForFieldPresent("IndividualCloudCreditsOnReportingPage");
		if(homePage.isFieldVisible("IndividualCloudCreditsOnReportingPage") && homePage.isFieldVisible("SharedCloudCreditsOnReportingPage")){
			homePage.verifyFieldExists("IndividualCloudCreditsOnReportingPage");
			homePage.verifyFieldExists("SharedCloudCreditsOnReportingPage");
		}else{
			EISTestBase.fail("Reporting Page not loaded...");
		}
		Util.printInfo("Clicking on All Usage Link and verifying whether it is navigating to My Usage page or not...");
		
		homePage.click("MyUsageLink");
		Util.sleep(40000);
		if(driver.getCurrentUrl().contains("my-usage")){
			assertTrue("Navigated to My Usage Page after clicking on My Usage Link :: ", driver.getCurrentUrl().contains("my-usage"));
		}else{
			EISTestBase.fail("My Usage page is not displayed even after clicking on My Usage Link :: ");
		}
		Util.printInfo("Clicking on All Usage Link and verifying whether it is navigating to My Usage page or not...");
		
		homePage.click("AllUsageLink");
		Util.sleep(40000);
		if(!driver.getCurrentUrl().contains("my-usage")){
			assertTrue("Navigated to All Usage Page after clicking on All Usage Link :: ", !driver.getCurrentUrl().contains("my-usage"));
		}else{
			EISTestBase.fail("All Usage page is not displayed even after clicking on All Usage Link :: ");
		}
		homePage.click("byUsers");
		//homePage.waitForFieldPresent("HeaderNameInByUsersPage");
		Util.printInfo("Clicking on By Reporting --> By Users Link ");
		if(homePage.isFieldVisible("HeaderNameInByUsersPage")) { 
			homePage.verifyFieldExists("HeaderNameInByUsersPage");
			}
			else{EISTestBase.fail("Header Name or Individual or Shared does not exists in By Users Page :: ");}
		if(homePage.isFieldVisible("HeaderIndividualInByUsersPage")) { homePage.verifyFieldExists("HeaderIndividualInByUsersPage"); }
			else{EISTestBase.fail("Header Individual does not exists in By Users Page :: ");}
			if(homePage.isFieldVisible("HeaderSharedInByUsersPage")) { homePage.verifyFieldExists("HeaderSharedInByUsersPage"); }
				else { EISTestBase.fail("Header Shared does not exists in By Users Page :: ");}
		
		Util.printInfo("Clicking on Contract Report...");
		
		homePage.click("ContractsAndOrders");
		//homePage.waitForFieldPresent("AllContractInContractReportPage");
		if(homePage.isFieldVisible("AllContractInContractReportPage")){
			homePage.verifyFieldExists("AllContractInContractReportPage");
			Util.printInfo("Clicking on contract ");
			homePage.click("ContractsInContractReportPage");
			//homePage.waitForFieldPresent("SupportLevelInContractReport");
			if(homePage.isFieldVisible("SupportLevelInContractReport")){
				homePage.verifyFieldExists("SupportLevelInContractReport");
				String SupportLevelTextInCOntractReport=homePage.getValueFromGUI("SupportLevelTextInCOntractReport");
				Util.printInfo("The Support Level for this contract is :: " +SupportLevelTextInCOntractReport);
			}else{
				EISTestBase.fail("The Support Level for this contract does not exists...");
			}
		}else{
			EISTestBase.fail("Contract Report Page not loaded..");
		}
		
		logoutMyAutodeskPortal();
}


	@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}
}
