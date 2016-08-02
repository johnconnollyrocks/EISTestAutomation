package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_Users_Not_On_Subscription extends CustomerPortalTestBase {
	
	
	public Test_Verify_Users_Not_On_Subscription() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_AddUsersOnlyInNAMUApplication() throws Exception {
		//setDebugMode(true);
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		assertTrue("Storage should be free", homePage.getValueFromGUI("storageFromProductAndServices").equalsIgnoreCase("Free"));
		String availability = homePage.getValueFromGUI("storageAvailabilityInPAS");
		String[] storageValue = availability.split("of");
		assertTrue("check storage 5 GB",storageValue[0].trim().equalsIgnoreCase("5 GB") && storageValue[1].trim().equalsIgnoreCase("5 GB"));
		
		homePage.click("reporting");
		homePage.verify();
		
		
		availability = homePage.getValueFromGUI("storageAvailabilityInReporting");
		String[] storageValue1 = availability.split("of");
		assertTrue("check storage 5 GB",storageValue1[0].trim().equalsIgnoreCase("5 GB") && storageValue1[1].trim().equalsIgnoreCase("5 GB"));
		
		
		
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
