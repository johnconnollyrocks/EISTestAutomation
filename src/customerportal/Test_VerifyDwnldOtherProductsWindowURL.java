package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyDwnldOtherProductsWindowURL extends CustomerPortalTestBase {
	public Test_VerifyDwnldOtherProductsWindowURL() throws IOException {
		super("Browser",getAppBrowser());
//		super();
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
 		 		
	}

	@Test
	public void Test_VerifyDwnldOtherProductsWindowURL() throws Exception {
		String getStatus;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb1=new WebDriverWait(driver,20000);
		wb1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		String newURL = testProperties.getConstant("DOWNLOADOTHERPRODUCTSURL");
		homePage.click("downloadOtherProducts");
		
		
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = capabilities.getBrowserName();

		if(browserName.equalsIgnoreCase("safari")){
			SafariCertificate();
						}
		
		
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
		//Handling IE security certificate Issue
				if(getAppBrowser().equalsIgnoreCase("ie") && (!homePage.isFieldPresent("autodeskName")))
					driver.navigate().to("javascript:document.getElementById('overridelink').click()");
				
				//END - Handling IE security certificate Issue
		String URL_New = driver.getCurrentUrl();
		assertEquals(URL_New, newURL);		
		mainWindow.select();
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
