package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_VerifyStorageSpaceAvailability
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyStorageSpaceAvailability extends
		CustomerPortalTestBase {
	public Test_VerifyStorageSpaceAvailability() throws IOException {
		super("Browser", getAppBrowser());
		// super();

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
		homePage.click("endUserEditAccessLink");
//	"productsServicesFirstProductButtonToggleDrawer");
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
		if(totalSpace[1].trim().equalsIgnoreCase("5 GB")){
		assertEquals(totalSpace[1].trim(), " 5 GB".trim());
		}else{
			EISTestBase.fail("Storage space is still showing wrong ");
		}
		logoutMyAutodeskPortal();

		// Login as CM and edit access by giving Storage service
//		Util.printInfo("Login as CM and edit access by giving Storage service");
//		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME"),testProperties.getConstant("CM_PASSWORD"));
//		BornInCloud customerPortal = utilCreateMyAutodeskPortal();
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
