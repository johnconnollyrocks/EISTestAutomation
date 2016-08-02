package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Util;

public class Test_Verify_EFlexProduct extends CustomerPortalTestBase {
	public Test_Verify_EFlexProduct() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_EFlexProduct_Available() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(30000);
		homePage.click("users");
		Util.sleep(20000);
		Util.printInfo("Clicking on end user edit access page");
		homePage.click("endUserEditAccessLink");
		Util.printInfo("Assigning benifts of EFlex product to the user");
		
			checkChecKBox("AssignEflexProduct","AssignEflexProductLabel");
			homePage.click("saveButton");
			homePage.click("EFlexToggleDrawer");
			homePage.verify();
			homePage.click("endUserEditAccessLink");
			boolean IsEFlexCheckBoxChecked= homePage.isChecked("IsEFlexCheckBoxChecked");
			if(IsEFlexCheckBoxChecked){
				assertTrue("",true);
			}
			else{
				EISTestBase.failTest("CheckBox is not checked");
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