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
public final class Test_Verify_Accounts_Default_Services extends CustomerPortalTestBase {
	public Test_Verify_Accounts_Default_Services() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyStorageSpaceAvailability() throws Exception {
		
		//Login as CM and edit access by removing Storage service
		Util.printInfo("Login as CM and check no storage access is present");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME") , testProperties.getConstant("CM_PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.clickAndWait("users","editAccessPage");
		homePage.clickAndWait("editAccessPage", "checkProduct1");
		unCheckChecKBox("firstProductAssignInput", "fristProductAssignLabel");
		homePage.clickAndWait("saveButton","editAccessPage");
		//Removed Storage service
		Util.printInfo("Removed Storage service");
		homePage.refresh();
		Util.sleep(5000);
		logoutMyAutodeskPortal();
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME") , testProperties.getConstant("CM_PASSWORD"));
		Util.sleep(5000);
		
		if(homePage.isFieldPresent("storageServiceType")){
			homePage.verifyFieldExists("storageServiceType");
		}else{
			EISTestBase.fail("Storage link is not pressent due to sync issue ::");;
		}
		
		// Commented out becuase of storage space updation issue on 11/26/2013 : Ravi Shankar. Once the issue gets resolved it will be uncommented
		// Defects DE3720 and DE3718
		
//		Util.printInfo("Verifying Storage service of 5 GB");
//		String storageSpace=homePage.getValueFromGUI("storageServiceSpaceAvailable");
//		Util.sleep(5000);
//		String[] totalSpace=((String)storageSpace).split("of");
//		Util.printInfo("Storage space is "+totalSpace[1]);
//		assertEquals(totalSpace[1].trim(),"5 GB");
//		Util.printInfo("Verifying in Reporting Tab-My Usage");
//		Util.printInfo("clicking on Reporting");
//		homePage.clickAndWait("reporting","myUsageButton");
//		homePage.refresh();
//		homePage.clickAndWait("myUsageButton","myUsageStorageSpaceAvailable");
//		Util.sleep(2000);
//		storageSpace=homePage.getValueFromGUI("myUsageStorageSpaceAvailable");
//		Util.sleep(2000);
//		totalSpace=((String)storageSpace).split("of");
//		Util.printInfo("Storage space is "+totalSpace[1]);
//		
//		assertEquals(totalSpace[1].trim(),"5 GB");
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
