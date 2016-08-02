package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_VerifyInstallNowButton
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_InstallNowButton_DEV extends CustomerPortalTestBase {
	public Test_Verify_InstallNowButton_DEV() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyInstallNowButton() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		//verify Install Now,Download Now, Browser Download dropdown functionality
		Util.printInfo("verify Install Now,Download Now, Browser Download dropdown functionality in DEV environment");
		Util.sleep(5000);
		homePage.clickAndWait("secondContractInstallNowLink", "autodeskRevitLTInstallNow");
		homePage.verifyFieldExists("autodeskRevitLTInstallNow");
		homePage.verifyFieldExists("autodeskAutoCADLTInstallNow");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Install Now functionality");
		
		homePage.clickAndWait("secondContractDownloadNowLink", "languageSelectDropDown");
		homePage.verifyFieldExists("autodeskRevitLTDownloadNow");
		homePage.verifyFieldExists("autodeskAutoCADLTDownloadNow");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Download Now functionality");
		
		homePage.clickAndWait("secondContractBrowserDownloadLink", "languageSelectDropDown");
		homePage.verifyFieldExists("autodeskRevitLTBrowserDownload");
		homePage.verifyFieldExists("autodeskAutoCADLTBrowserDownload");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Browser Download functionality");
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
