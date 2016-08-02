package customerportal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;

import common.Util;

/**
 * Test class - Test_VerifyStorageSpaceAvailability
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
@RunWith(Parameterized.class)
public final class Test_StorageSpaceAvailability_Parameterized extends CustomerPortalTestBase {
	
	private String browser;
	public Test_StorageSpaceAvailability_Parameterized(String browser) throws IOException {
		super("Browser",browser,"Para");
		// super();
		setup();
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
	public void Test_VerifyStorageSpaceAvailability() throws Exception {
		
		Util.printInfo("Login as CM and edit access by removing Storage service");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME"),testProperties.getConstant("CM_PASSWORD"));
		homePage.clickAndWait("users", "endUserEditAccessLink");
//		homePage.clickAndWait("endUserEditAccessLink","productsServicesFirstProductButtonToggleDrawer");
		
		homePage.clickAndWait("endUserProductToggleDrawer","endUserRevitLTSuitStorageService");
		unCheckChecKBox("endUserAutoCADRevitLTSuitStorageServiceCheckBox","endUserRevitLTSuitStorageServiceLabel");
		homePage.clickAndWait("saveButton", "endUserEditAccessLink");
		// Removed Storage service
		Util.printInfo("Removed Storage service");
		logoutMyAutodeskPortal();
		Util.printInfo("Login as End User to check Storage service Space");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME"),testProperties.getConstant("PASSWORD"));
		Util.printInfo("clicking on Reporting");
		homePage.clickAndWait("reporting", "myUsageStorageSpaceAvailable");
		homePage.refresh();
		homePage.refresh();
		Util.sleep(2000);
		String storageSpace = homePage.getValueFromGUI("myUsageStorageSpaceAvailable");
		Util.sleep(2000);
		String[] totalSpace = storageSpace.split("of");
		Util.printInfo("Storage space is " + totalSpace[1]);
		assertEquals(totalSpace[1].trim(), " 5 GB".trim());
		logoutMyAutodeskPortal();
		
		// Login as CM and edit access by giving Storage service
//		Util.printInfo("Login as CM and edit access by giving Storage service");
//		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME"),testProperties.getConstant("CM_PASSWORD"));
//		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
//		loginPage = customerPortal.getLoginPage();
//		homePage = customerPortal.getHomePage();
//		mainWindow.select();
//		homePage.clickAndWait("users", "endUserEditAccessLink");
//		homePage.clickAndWait("endUserEditAccessLink","productsServicesFirstProductButtonToggleDrawer");
//		homePage.clickAndWait("endUserProductToggleDrawer","endUserRevitLTSuitStorageService");
//		checkChecKBox("endUserAutoCADRevitLTSuitStorageServiceCheckBox","endUserRevitLTSuitStorageServiceLabel");
//		homePage.clickAndWait("saveButton", "endUserEditAccessLink");
//		// Added Storage service
//		Util.printInfo("Added Storage service");
//
//		Util.printInfo("Login as End User to check Storage service Space");
//		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME"),testProperties.getConstant("PASSWORD"));
//		Util.printInfo("clicking on Reporting");
//		homePage.clickAndWait("reporting", "myUsageStorageSpaceAvailable");
//		homePage.refresh();
//		homePage.refresh();
//		logoutMyAutodeskPortal();
//		
//		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME"),testProperties.getConstant("PASSWORD"));
//		Util.printInfo("clicking on Reporting");
//		homePage.clickAndWait("reporting", "myUsageStorageSpaceAvailable");
//		homePage.refresh();
//		homePage.refresh();
//		storageSpace = homePage.getValueFromGUI("myUsageStorageSpaceAvailable");
//		totalSpace = ((String) storageSpace).split("of");
//		Util.printInfo("Storage space is " + totalSpace[1]);
//		assertEquals(totalSpace[1].trim(), " 25 GB".trim());
//		logoutMyAutodeskPortal();
		// Login as CM and edit access by removing Storage service
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
