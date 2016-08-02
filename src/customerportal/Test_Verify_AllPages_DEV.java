package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;

public class Test_Verify_AllPages_DEV extends CustomerPortalTestBase {
	public Test_Verify_AllPages_DEV() throws IOException {
		super("Browser",getAppBrowser());
//		super();		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_Verify_AllPages_DEV() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.waitForFieldPresent("AllProductsandServices");
		Util.printInfo("Verifying Products and Services page...");
		if(homePage.isFieldVisible("AllProductsandServices") && homePage.isFieldPresent("ManageUsers")){
			homePage.verifyFieldExists("AllProductsandServices");
			homePage.verifyFieldExists("ManageUsers");
		}else{
			EISTestBase.fail("Products and services page not loaded :: ");
		}
		
		Util.printInfo("clicking on products Link and Verifying Products page...");
		homePage.click("ProductsLink");
		homePage.waitForFieldPresent("ProductsTextInProductsPage");
		if(homePage.isFieldVisible("ProductsTextInProductsPage")){
			homePage.verifyFieldExists("ProductsTextInProductsPage");
		}else{
			EISTestBase.fail("Products page not loaded ");
		}
		Util.printInfo("clicking on Services Link and Verifying Services page...");
		
		homePage.click("ServicesLink");
		homePage.waitForFieldPresent("ServicesTextInServicesPage");
		if(homePage.isFieldVisible("ServicesTextInServicesPage") && homePage.isFieldVisible("ManageUsersInServicesPage")){
			homePage.verifyFieldExists("ServicesTextInServicesPage");
			homePage.verifyFieldExists("ManageUsersInServicesPage");
		}else{
			EISTestBase.fail("Services page not loaded ");
		}
		
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
		homePage.waitForFieldPresent("HeaderNameInByUsersPage");
		Util.printInfo("Clicking on By Reporting --> By Users Link ");
		if(homePage.isFieldVisible("HeaderNameInByUsersPage") && homePage.isFieldVisible("HeaderIndividualInByUsersPage") && homePage.isFieldVisible("HeaderSharedInByUsersPage")){
			homePage.verifyFieldExists("HeaderNameInByUsersPage");
			homePage.verifyFieldExists("HeaderIndividualInByUsersPage");
			homePage.verifyFieldExists("HeaderSharedInByUsersPage");
		}else{
			EISTestBase.fail("Header Name or Individual or Shared does not exists in By Users Page :: ");
		}
		Util.printInfo("Clicking on Contract Report...");
		
		homePage.click("contractReport");
		homePage.waitForFieldPresent("AllContractInContractReportPage");
		if(homePage.isFieldVisible("AllContractInContractReportPage")){
			homePage.verifyFieldExists("AllContractInContractReportPage");
			Util.printInfo("Clicking on contract ");
			homePage.click("ContractsInContractReportPage");
			homePage.waitForFieldPresent("SupportLevelInContractReport");
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
		
		Util.printInfo("Clicking on UserManagement page and verifying all the fields in Users page :: ");
		
		homePage.click("users");
		homePage.waitForFieldPresent("AllUsersInUserManagementPage");
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
