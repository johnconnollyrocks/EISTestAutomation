package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_VerifyStudentStorage
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyStudentStorage extends CustomerPortalTestBase {
	public Test_VerifyStudentStorage() throws IOException {
		super("Browser",getAppBrowser());
//		super();
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyStudentStorage() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		
		mainWindow.select();
		Util.sleep(40000);
		homePage.verify();
		String storageSpace=homePage.getValueFromGUI("storageServiceSpaceAvailable");
		String[] totalSpace=storageSpace.split("of");
		Util.printInfo("Storage space is"+totalSpace[1]);
		Util.printInfo("Verifying Storage space at All Products & Services tab");
		assertEquals(totalSpace[1].trim(), "25 GB");
		
		Util.printInfo("clicking on Reporting");
		homePage.clickAndWait("reporting","myUsageButton");
		homePage.clickAndWait("myUsageButton","myUsageFirstContractCloudCriditsUsed");
		homePage.verifyInstance("REPORTING_VERIFY");
		storageSpace=homePage.getValueFromGUI("myUsageStorageSpaceAvailable");
		totalSpace=storageSpace.split("of");
		Util.printInfo("Storage space is"+totalSpace[1]);
		Util.printInfo("Verifying Storage space at Reporting and My Usage tab");
		assertEquals(totalSpace[1].trim(), "25 GB");
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
