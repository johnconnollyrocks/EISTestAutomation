package customerportal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebElement;

import common.Parallelized;
import common.ScreenshotTestRule;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
@RunWith(Parallelized.class)
public final class Test_AddUsersOnlyInNAMUApplication_Para extends CustomerPortalTestBase {
	@Rule
	public MethodRule ScreenshotTestRule  = new ScreenshotTestRule();
	public Test_AddUsersOnlyInNAMUApplication_Para(String browser) throws IOException {
		super("Browser",browser,"Para");
		sysProps.setProperty("testName","Test_AddUsersOnlyInNAMUApplication_Para");
		sysProps.setProperty("testPropertiesFilenameKey","TEST_VERIFY_NOT_ON_SUBSCRIPTION_FILE");
		setup();
//		super();
		
	}
	@Parameters
	public static Collection<String[]>  browserData(){
		String[][] browsers = new String[][]{{"ie"},{"chrome"},{"firefox"}};
		return Arrays.asList(browsers);
		
	}
	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_AddUsersOnlyInNAMUApplication() throws Exception {
		//setDebugMode(true);
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebElement asdlfjasdkl;
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.printInfo("Adding a User");
		homePage.click("users");
		
		Util.sleep(2000);
		
		
		homePage.verifyFieldExists("addUser");
		logoutMyAutodeskPortal();	
	}

	/*@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}*/
}
