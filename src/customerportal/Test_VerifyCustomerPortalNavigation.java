package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyCustomerPortalNavigation extends CustomerPortalTestBase {
	public Test_VerifyCustomerPortalNavigation() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyCancelContractsWindowURL() throws Exception {
		//String getStatus;
		
		driver.manage().deleteAllCookies();
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb=new WebDriverWait(driver,20000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		Util.printInfo("Username: " + testProperties.getConstant("USER_NAME"));
		Util.printInfo("Password: " + testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		 
		//Download other products navigation Punch Out Verification 
		
		
//		String winHandleBefore = driver.getWindowHandle();
//		String newURL = testProperties.getConstant("DOWNLOADOTHERPRODUCTSURL");
//		Util.printInfo("Clicking on download other products button");
//		homePage.click("downloadOtherProducts");
//		Util.sleep(2000);
		
		
/*		String URL_New = driver.getCurrentUrl();
		Util.printInfo (URL_New);
	    Util.printDebug("Handling possible web certificate error...");
	    
	    int exitCode = commonPage.clickLinkInIE(EISConstants.TEST_BASE_PATH + EISConstants.CLICK_LINK_IN_IE_PROCESS, URL_New , "\"Continue to this website (not recommended).\"");
		if (exitCode != EISConstants.PROCESS_EXIT_CODE_SUCCESS) {
			Util.printWarning("Error when clicking link on web certificate error page after launching salesforce download page: " + exitCode);
		}
		
		Util.printDebug("Handled possible web certificate error");    	*/
		
		
		//driver.get("javascript:document.getElementById('overridelink').click();");
		
//		for(String winHandle : driver.getWindowHandles()){
//		    driver.switchTo().window(winHandle);
//		}
//		Util.printInfo("Verifying whether the Download other products link has opened a new window or not");
//		String URL_New = driver.getCurrentUrl();
//		assertEquals(URL_New, newURL);
//		driver.close();
//		mainWindow.select();
		
		

	//	homePage.clickAndWait("reporting", "usageReport");		
		homePage.clickAndWait("productsAndServices","cancelContracts");
		
		String winHandleBefore = driver.getWindowHandle();
		/*String newURL = testProperties.getConstant("CANCELCONTRACTSURL");
		Util.printInfo("Clicking on Cancel Contracts button");
		Util.sleep(20000);
		homePage.click("cancelContracts");
		Util.sleep(3000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
		Util.printInfo("Verifying whether the Cancel Contracts Link has opened a new window or not");
		String URL_New = driver.getCurrentUrl();
		assertEquals(URL_New, newURL);
		driver.close();
		mainWindow.select();*/	
		
		
		Util.printInfo("Clicking on first contract toggle drawer button");
		homePage.clickAndWait("firstButtonToggleDrawer" , "firstViewAllInstalledProductsLink");
		winHandleBefore = driver.getWindowHandle();
		String newURL = testProperties.getConstant("VIEWALLINSTALLEDPRODUCTSURL");
		Util.printInfo("Clicking on View all installed Products button");
		homePage.click("firstViewAllInstalledProductsLink");
		Util.sleep(3000);		
		for(String winHandle : driver.getWindowHandles()){
			 driver.switchTo().window(winHandle);
		}
		Util.printInfo("Verifying whether the View All installed products Link has opened a new window or not");
		String URL_New = driver.getCurrentUrl();
		assertEquals(URL_New, newURL);	
		driver.close();
		driver.switchTo().window(winHandleBefore);
		
		
		newURL = testProperties.getConstant("MANAGEUSERSURL");
		Util.printInfo("Clicking on Manage Users button");
		homePage.click("manageUsers");		
		Util.sleep(3000);
		Util.printInfo("Verifying whether the Manage Users button click has navigated to the expected page or not");
		URL_New = driver.getCurrentUrl();
		
		assertEquals(URL_New, newURL);		
		//mainWindow.select();
		
		logoutMyAutodeskPortal();		

		Util.printInfo("Logging in as End User");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("ENDUSER_USER_NAME") , testProperties.getConstant("ENDUSER_PASSWORD"));
		WebDriverWait wb1=new WebDriverWait(driver,20000);
		wb1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		Util.printInfo("Logged in as End User");
		Util.printInfo("Clicking on Products and services");
		homePage.clickAndWait("productsAndServices", "downloadOtherProducts");
		Util.printInfo("Clicked on Products and services");
		Util.printInfo("Checking that 'Manage Users' button and 'Cancel Contracts' Link SHOULD  NOT  EXIST for the end user");
		homePage.verifyInstance("ENDUSER_VALIDATIONS");
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
