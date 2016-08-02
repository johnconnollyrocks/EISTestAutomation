package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyEUProdEnhncmnt_Enable extends CustomerPortalTestBase {
	public Test_VerifyEUProdEnhncmnt_Enable() throws IOException {
		super("Browser",getAppBrowser());
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	
	@Test
	public void Test_VerifyEUProdEnhncmnt_Enable() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(20000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
//		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(50000);
		Util.printInfo("Verifying Product Enhancement Link availabity for CM login");
		homePage.verify();
		Util.sleep(2000);
		
		homePage.click("users");
		Util.sleep(5000);
		
		homePage.click("endUserEditAccessLink");
		Util.sleep(5000);

		checkChecKBox("inputBenefitsAssignAll","labelBenefitsAssignAll");
		Util.sleep(2000);
		
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = capabilities.getBrowserName();
		
		if(!browserName.equalsIgnoreCase("safari")) {
			
			checkChecKBox("inputProductsAssignAll","labelProductsAssignAll");
			
			}
			else{
				
				
				if(!homePage.isChecked("inputProductsAssignAll")){
					Util.sleep(5000);
					Util.printInfo("The check box is unchecked");
					Util.printInfo("Clicking on checkbox..");
					Util.sleep(2000);
					jsToolTip("labelProductsAssignAll");
					//	 		homePage.click("endUserAutoCADRevitLTSuitRenderingServiceCheckBox");
					Util.sleep(2000);
					Util.printInfo("The check box is checked now..");
				}
				else {
					Util.printInfo("The check box is checked");	
					Util.sleep(2000);
				}
			}
		Util.sleep(5000);	
		
		homePage.click("saveButton");
		Util.sleep(3000);
		logoutMyAutodeskPortal();   
		Util.sleep(3000);
				
		loginAsMyAutodeskPortalUser(testProperties.getConstant("ENDUSER_USER_NAME") , testProperties.getConstant("ENDUSER_PASSWORD"));
		Util.sleep(30000);
		homePage.refresh();
//		logoutMyAutodeskPortal();  
		Util.sleep(60000);
//		loginAsMyAutodeskPortalUser(testProperties.getConstant("ENDUSER_USER_NAME") , testProperties.getConstant("ENDUSER_PASSWORD"));
//		homePage.refresh();
		mainWindow.select();
//		homePage.verify();
		
		if(homePage.isFieldVisible("productEnhancement")){
			homePage.verifyFieldExists("productEnhancement");
			}else{
				EISTestBase.fail("product Enhancement Link is not Pressent");
			}
		logoutMyAutodeskPortal();  
				
	}

	

	@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.manage().deleteAllCookies();
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}
}
