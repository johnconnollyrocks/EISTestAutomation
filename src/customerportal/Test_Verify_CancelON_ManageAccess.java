package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_CancelON_ManageAccess extends CustomerPortalTestBase {
	
	public List<WebElement> allChekboxsInManageAccessWindow;
	public List<WebElement> numberOfProductList;
	public int count;
	public int versionCount;
	
	public List<WebElement> versionsCount;
	public Test_Verify_CancelON_ManageAccess() throws IOException {
		super("Browser",getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_CancelON_ManageAccess_Window() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(10000);
		Util.printInfo("*******************************************************************************");
		if(homePage.verifyFieldExists("InstallNowButton")){
			assertTrue("Products are present for this user",homePage.verifyFieldExists("InstallNowButton"));
			assertTrue("Manage Access Icon is present just beside the seatnumbers available",homePage.verifyFieldExists("manageAccessIcon"));
			Util.printInfo("Clicking on the dropdown");
			homePage.click("productDropDown");
			homePage.click("manageAccess");
			assertTrue("The manage access close button X is present",homePage.verifyFieldExists("manageAccessCloseButton"));
			Util.printInfo("check the version status ,Do not modify the versions of the product and close the window");
			Util.printInfo("Make sure All the versions are selected or not if all the versions are not selected previously script will fail");
			assertEqualsWithFlags(" versions","allversions", (homePage.isFieldPresent("allversions")), true);
			homePage.click("manageAccessCloseButton");
			homePage.click("productDropDown");
			homePage.click("manageAccess");
			Util.printInfo("versions status should not be changed");
			assertTrue("versions are not changed",homePage.verifyFieldExists("allversions"));
			Util.printInfo("Apply one version and verify for the save and cancel buttons ");
			homePage.check("selectAllManageAccessCheckBox");
			homePage.click("applyVersions");
			homePage.check("version1");
			assertTrue("versions should be changed according to the versions selected ",homePage.verifyFieldExists("versionStatus"));
			homePage.click("manageAccessCloseButton");
			assertTrue("The manage access Warning message is getting displayed",homePage.verifyFieldExists("versionChangeWarning"));
			assertTrue("save and cancel are getting displayed",homePage.verifyFieldExists("saveAndCancelOnManageAccess"));
			homePage.click("saveVersionChanges");
			Util.sleep(2000);
			homePage.click("manageAccessIcon");
			assertEqualsWithFlags(" versions","version1Status", (homePage.isFieldPresent("version1Status")), true);
			homePage.check("selectAllManageAccessCheckBox");
			homePage.click("applyVersions");
			homePage.check("clickOnAll");
			homePage.uncheck("clickOnAll");
			homePage.click("manageAccessCloseButton");
			assertTrue("0 versions selected warning message is getting displayed",homePage.verifyFieldExists("warningFor0Versions"));
						
		}
		else{
			EISTestBase.fail(" OOpps !!!no products for this CM choose an another");
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
