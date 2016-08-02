package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_Verify_AccessSavedButton extends CustomerPortalTestBase {

	
	public Test_Verify_AccessSavedButton () throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_AccessSavedButton_method() throws Exception {
		if(getEnvironment().equalsIgnoreCase("DEV")){
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
			}
			else if(getEnvironment().equalsIgnoreCase("STG")){
				loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
			}
			Util.sleep(5000);
			CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
			loginPage = customerPortal.getLoginPage();
			homePage = customerPortal.getHomePage();
			mainWindow.select();
			Util.sleep(5000);
			homePage.waitForField("users", true);
			homePage.clickAndWait("users","selectAllInUP");
			Util.sleep(5000);
			
			Util.printInfo("Adding a new User ");
			String Email=homePage.getConstant("EMAIL");
			
			String ActualEmail=Email+getUniqueId()+"@ssttest.net";
			
			addNewUser1(ActualEmail);
			
			homePage.click("DontSaveUser");
			homePage.waitForField("accessSaved", true);
			Util.sleep(5000);
			homePage.refresh();
			String User=testProperties.getConstant("USERNAME");
			RemoveUserBySearch(ActualEmail, User);
			Util.printInfo(testProperties.getConstant("USERNAME")+ "Removed ");
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
