package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;

public class Test_Verify_PNSPage_DEV extends CustomerPortalTestBase {
	public Test_Verify_PNSPage_DEV() throws IOException {
		super("Browser",getAppBrowser());
//		super();		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_Verify_PNSPage_DEV() throws Exception {
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
