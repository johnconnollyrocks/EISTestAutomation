package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_VerifyStorageSpaceAvailability
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_AddCMAsEndUser_VerifyContractInfo extends CustomerPortalTestBase {
	public Test_AddCMAsEndUser_VerifyContractInfo() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyStorageSpaceAvailability() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME_1") , testProperties.getConstant("CM_PASSWORD_1"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		//Login as CM1 check any products & services are assinged to CM2
		homePage.click("users");
		Util.sleep(5000);
		homePage.click("editAccessLinkForCM2");
		Util.sleep(5000);
		String getStatus = homePage.getAttribute("productsServicesFirstProductCheckBox", "class");
	 	if(getStatus.equalsIgnoreCase("toggle-switch enabled-false")){
	 		Util.printInfo("The check box is unchecked");
	 		homePage.click("productsServicesFirstProductCheckBox");
	 	}
 	 	else if(getStatus.equalsIgnoreCase("toggle-switch enabled-true")){
 	 		Util.printInfo("The check box is checked");	
 	 	}
		
		homePage.click("saveButton");
		Util.sleep(10000);
		logoutMyAutodeskPortal();
		//Login as CM2 to check whether the contract is displayed or not
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME_2") , testProperties.getConstant("CM_PASSWORD_2"));
		Util.sleep(10000);
		homePage.verify();
		Util.printInfo("clicking on Reporting");
		homePage.click("reporting");
		Util.sleep(10000);
		homePage.click("myUsageButton");
		homePage.verifyInstance("VERIFY_MY_USAGE_TAB");
		
		Util.sleep(10000);
		homePage.verifyInstance("VERIFY_MY_USAGE_TAB");
		Util.sleep(2000);
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
