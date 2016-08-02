package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_VerifyInstallNowButton
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_Contract_Info_Prod_URL_InstallNowButton extends CustomerPortalTestBase {
	public Test_Verify_Contract_Info_Prod_URL_InstallNowButton() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyInstallNowButton() throws Exception {
		String getStatus;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		Util.printInfo("Get the number of users from users page and compare with Products and services page");
		
//		int numberOfUserAssigned = getNumberOfUsersAssigned(driver, driver.findElement(By.xpath("//*[@id='products-all']//article[1]/div[2]/div[@class='left-col']//*[contains(text(),'Contract#')]/ancestor::span[1]")).getText());
//		boolean compare = String.valueOf(numberOfUserAssigned).equalsIgnoreCase(driver.findElement(By.xpath(".//*[@id='383-94427757']//label[contains(text(),'Users Assigned')]/following-sibling::span")).getText());
		
		
//		boolean compare = compareNoOfUsers(driver,driver.findElement(By.xpath(".//*[@id='383-94427757']//label[contains(text(),'Users Assigned')]/following-sibling::span")).getText());
//		Util.printInfo("number of users Same : "+compare);
//		assertTrue("No of Users should match", compare);
		
		homePage.clickAndWait("product","firstButtonToggleDrawer");
		Util.printInfo("Clicking on the first contract toggle drawer button");
		homePage.clickAndWait("firstButtonToggleDrawer" , "firstViewAllInstalledProductsLink");
		Util.printInfo("Validating whether 'Seats,Users,Version,Language,Serial-Number,Product Key,Contract,Contract type' values are populated or not");
		homePage.verify();
		mainWindow.select();
		String winHandleBefore = driver.getWindowHandle();
		String newURL = testProperties.getConstant("SUITS_URL");
		homePage.click("firstProductViewProdInfo");
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
		Util.printInfo("Validating the SUIT URL navigation");
		String URL_New = driver.getCurrentUrl();
		assertEquals(URL_New, newURL);		
		mainWindow.select();
		
		//verify Install Now,Download Now, Browser Download dropdown functionality
		Util.printInfo("verify Install Now,Download Now, Browser Download dropdown functionality");
		homePage.clickAndWait("firstContractInstallNowButton", "languageSelectDropDown");
		homePage.verifyFieldExists("autodeskRevitLTInstallNow");
		homePage.verifyFieldExists("autodeskAutoCADLTInstallNow");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Install Now functionality");
		
		homePage.clickAndWait("firstContractDownloadNowButton", "languageSelectDropDown");
		homePage.verifyFieldExists("autodeskRevitLTDownloadNow");
		homePage.verifyFieldExists("autodeskAutoCADLTDownloadNow");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Download Now functionality");
		
		homePage.clickAndWait("firstContractBrowserDownloadButton", "languageSelectDropDown");
		homePage.verifyFieldExists("autodeskRevitLTBrowserDownload");
		homePage.verifyFieldExists("autodeskAutoCADLTBrowserDownload");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Browser Download functionality");
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
