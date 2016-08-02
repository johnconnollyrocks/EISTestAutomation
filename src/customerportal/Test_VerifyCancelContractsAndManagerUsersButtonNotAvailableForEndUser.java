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
 * VerifyCancelContractsAndManagerUsersButtonNotAvailableForEndUser
 */
public final class Test_VerifyCancelContractsAndManagerUsersButtonNotAvailableForEndUser extends CustomerPortalTestBase {
	public Test_VerifyCancelContractsAndManagerUsersButtonNotAvailableForEndUser() throws IOException {
//		super("Browser",getAppBrowser());
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyCancelContractsWindowURL() throws Exception {
		String getStatus;
		Util.printInfo("Logging in as End User");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("ENDUSER_USER_NAME") , testProperties.getConstant("ENDUSER_PASSWORD"));
		WebDriverWait wb=new WebDriverWait(driver,30000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		Util.printInfo("Logged in as End User");
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		
		mainWindow.select();
		Util.printInfo("clicking on Reporting");
		homePage.clickAndWait("reporting", "usageReport");
		homePage.verify();
			
		logoutMyAutodeskPortal();
		
		Util.printInfo(" Now logging in as CM with all expired contracts");
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME") , testProperties.getConstant("CM_PASSWORD"));
		WebDriverWait wb1=new WebDriverWait(driver,30000);
		wb1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		Util.printInfo("Logged in as CM with all expired contracts");
		
		mainWindow.select();	
		
		Util.printInfo("Verify that 'Manage Users' tab is not present");
		homePage.verify();
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
