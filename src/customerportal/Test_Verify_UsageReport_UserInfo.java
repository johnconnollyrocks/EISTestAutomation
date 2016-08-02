package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_UsageReport_UserInfo extends CustomerPortalTestBase {
	public Test_Verify_UsageReport_UserInfo() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_UsageReport_ContractInfo() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		
		mainWindow.select();
		homePage.verify();
		Util.printInfo("clicking on Reporting");
		homePage.clickAndWait("reporting", "usageReport");
		Util.sleep(5000);
		Util.printInfo("Verifying What are Cloud Credits Hover functionality");
		homePage.hoverOver("whatAreCloudCreditsHover");
		homePage.verifyFieldExists("whatAreCloudCreditsLearnMore");
//		homePage.click("firstButtonToggleDrawer");
//		homePage.verifyFieldExists("firstContractUsageByUsersLink");
//		Util.printInfo("Verifying first contract Hover functionality");
//		homePage.hoverOver("firstContractUsageHover");
//		homePage.verifyFieldExists("firstContractExportCSVToolTip");
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
