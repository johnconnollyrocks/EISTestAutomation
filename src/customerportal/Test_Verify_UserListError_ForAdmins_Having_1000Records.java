package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Util;

public class Test_Verify_UserListError_ForAdmins_Having_1000Records extends CustomerPortalTestBase {
	public Test_Verify_UserListError_ForAdmins_Having_1000Records() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_OnlineMap() throws Exception {
		Util.printInfo("Logging into customer portal :: ");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(100000);
		homePage.clickAndWait("users", "selectAllInUP");
		Util.sleep(40000);
		if(homePage.isFieldVisible("UsersInUserPage") && homePage.isFieldVisible("addUser")){
			homePage.verifyFieldExists("UsersInUserPage");
			homePage.verifyFieldExists("addUser");
			Util.printInfo("User loaded successfully :: ");
		}else{
			EISTestBase.fail("error displayed while loading the users in users page :: ");
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
