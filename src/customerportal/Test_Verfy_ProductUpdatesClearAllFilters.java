package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_Verfy_ProductUpdatesClearAllFilters extends CustomerPortalTestBase{

	public Test_Verfy_ProductUpdatesClearAllFilters() throws IOException {
		super("Browser",getAppBrowser());
		
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_ProductUpdatesClearAllFilters() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.click("productUpdates");
		Util.sleep(2000);
		String currentlySorted=homePage.getValueFromGUI("sortBy");
		assertEquals(currentlySorted, "Date");
		homePage.click("filter");
//		homePage.clickAndWait("dateFilter", "appliedFilterDate");
		homePage.click("productFilter");
		homePage.click("severityFilter");
		homePage.click("typeFilter");
		homePage.verifyFieldExists("appliedFilterProduct");
		homePage.verifyFieldExists("appliedFilterSeverity");
		homePage.verifyFieldExists("appliedFilterType");
		homePage.click("product1Toggle");
		homePage.click("product2Toggle");
		homePage.verify();
		homePage.click("clearAll");
		homePage.verifyFieldNotExists("appliedFilterProduct");
		homePage.verifyFieldNotExists("appliedFilterSeverity");
		homePage.verifyFieldNotExists("appliedFilterType");
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