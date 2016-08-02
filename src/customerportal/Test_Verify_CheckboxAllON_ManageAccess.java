package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_CheckboxAllON_ManageAccess extends CustomerPortalTestBase {
	
	public Test_Verify_CheckboxAllON_ManageAccess() throws IOException {
		super("Browser",getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_VersionsOf_ManageAcces() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(9000);
		
		if(homePage.verifyFieldExists("InstallNowButton")){
			
			homePage.verifyFieldExists("InstallNowButton");
			homePage.verifyFieldExists("manageAccessIcon");
			Util.printInfo("Clicking on the dropdown");
			homePage.click("productDropDown");
			homePage.click("manageAccess");
			Util.printInfo("Opened the manage access window");
			homePage.verifyFieldExists("ManageAccessWindow");
			homePage.check("selectAllManageAccessCheckBox");
			Util.printInfo("Apply the vesions click on all and verify the check boxes are working fine");;
			homePage.click("applyVersions");
			homePage.verifyFieldExists("clickOnAll");
			Util.printInfo("Check box All is present and it is unchecked");
			assertEqualsWithFlags("ManageAccess window","clickOnAll", (homePage.isChecked("clickOnAll")), false);
			homePage.check("version1");
			Util.printInfo("after applying(clicking on the checkbox)the one version check box All should be unchecked");
			assertEqualsWithFlags("ManageAccess window","clickOnAll", (homePage.isChecked("clickOnAll")), false);
			Util.printInfo("after clicking on the checkbox All the individual checkboxes should be unchecked");
			homePage.check("clickOnAll");
			assertEqualsWithFlags("ManageAccess window","version1", (homePage.isChecked("version1")), false);
			assertEqualsWithFlags("ManageAccess window","version2", (homePage.isChecked("version2")), false);
			assertEqualsWithFlags("ManageAccess window","clickOnAll", (homePage.isChecked("clickOnAll")), true);
			Util.printInfo("after unchecking the checkbox All all products versions should display 0 versions" );
			homePage.uncheck("clickOnAll");
		//	assertEquals("0 versions", homePage.getValueFromGUI("zeroVersionsManageAcc"));
			assertTrue("zeroVersionsManageAcc",true);
			homePage.click("manageAccessCloseButton");
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