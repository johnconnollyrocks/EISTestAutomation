package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;

public class Test_Verify_AllPages_GL extends CustomerPortalTestBase {
	public Test_Verify_AllPages_GL() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_OnlineMap() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(20000);
		Util.printInfo("Verifying Products and Services page...");
		if(homePage.isFieldVisible("AllProductsandServices") && homePage.isFieldPresent("ManageUsers")){
			homePage.verifyFieldExists("AllProductsandServices");
			homePage.verifyFieldExists("ManageUsers");
		}else{
			EISTestBase.fail("Products and services page not loaded :: ");
		}
		
		Util.printInfo("clicking on products Link and Verifying Products page...");
		homePage.click("ProductsLink");
		Util.sleep(8000);
		if(homePage.isFieldVisible("ProductsTextInProductsPage")){
			homePage.verifyFieldExists("ProductsTextInProductsPage");
		}else{
			EISTestBase.fail("Products page not loaded ");
		}
		Util.printInfo("clicking on Services Link and Verifying Services page...");
		Util.sleep(40000);
		homePage.click("ServicesLink");
		if(homePage.isFieldVisible("ServicesTextInServicesPage") && homePage.isFieldVisible("ManageUsersInServicesPage")){
			homePage.verifyFieldExists("ServicesTextInServicesPage");
			homePage.verifyFieldExists("ManageUsersInServicesPage");
		}else{
			EISTestBase.fail("Services page not loaded ");
		}
		
		Util.printInfo("clicking on Report Link and Verifying Individual and Shared cloud credits header in reporting page...");
		Util.sleep(40000);
		homePage.clickAndWait("reporting","byUsers");
		Util.sleep(4000);
		if(homePage.isFieldVisible("IndividualCloudCreditsOnReportingPage") && homePage.isFieldVisible("SharedCloudCreditsOnReportingPage")){
			homePage.verifyFieldExists("IndividualCloudCreditsOnReportingPage");
			homePage.verifyFieldExists("SharedCloudCreditsOnReportingPage");
		}else{
			EISTestBase.fail("Reporting Page not loaded...");
		}
		Util.printInfo("Clicking on My Usage Link and verifying whether it is navigating to My Usage page or not...");
		Util.sleep(4000);
		homePage.click("MyUsageLink");
		Util.sleep(4000);
		if(driver.getCurrentUrl().contains("my-usage")){
			assertTrue("Navigated to My Usage Page after clicking on My Usage Link :: ", driver.getCurrentUrl().contains("my-usage"));
		}else{
			EISTestBase.fail("My Usage page is not displayed even after clicking on My Usage Link :: ");
		}
		Util.printInfo("Clicking on All Usage Link and verifying whether it is navigating to All Usage page or not...");
		Util.sleep(4000);
		homePage.click("AllUsageLink");
		Util.sleep(4000);
		if(!driver.getCurrentUrl().contains("my-usage")){
			assertTrue("Navigated to All Usage Page after clicking on All Usage Link :: ", !driver.getCurrentUrl().contains("my-usage"));
		}else{
			EISTestBase.fail("All Usage page is not displayed even after clicking on All Usage Link :: ");
		}
		homePage.click("byUsers");
		Util.sleep(40000);
		Util.printInfo("Clicking on By Reporting --> By Users Link ");
		if(homePage.isFieldVisible("HeaderNameInByUsersPage") && homePage.isFieldVisible("HeaderIndividualInByUsersPage") && homePage.isFieldVisible("HeaderSharedInByUsersPage")){
			homePage.verifyFieldExists("HeaderNameInByUsersPage");
			homePage.verifyFieldExists("HeaderIndividualInByUsersPage");
			homePage.verifyFieldExists("HeaderSharedInByUsersPage");
		}else{
			EISTestBase.fail("Header Name or Individual or Shared does not exists in By Users Page :: ");
		}
		Util.printInfo("Clicking on Contract Report...");
		Util.sleep(4000);
		homePage.click("contractReport");
		Util.sleep(4000);
		if(homePage.isFieldVisible("AllContractInContractReportPage")){
			homePage.verifyFieldExists("AllContractInContractReportPage");
			Util.printInfo("Clicking on contract ");
			homePage.click("ContractsInContractReportPage");
			Util.sleep(40000);
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
		Util.sleep(40000);
		Util.printInfo("Clicking on UserManagement page and verifying all the fields in Users page :: ");
		Util.sleep(4000);
		homePage.clickAndWait("users","selectAllInUP");
		if(homePage.isFieldVisible("AllUsersInUserManagementPage") && homePage.isFieldVisible("AddusersInUserManagementPage")){
			homePage.verifyFieldExists("AllUsersInUserManagementPage");
			homePage.verifyFieldExists("AddusersInUserManagementPage");
		}else{
			EISTestBase.fail("User Management Page not loaded..");
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
