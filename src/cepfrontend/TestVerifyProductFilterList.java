package cepfrontend;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

/**
 * Test class - TestVerifyProductFilterList
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyProductFilterList extends CEPTestBase {
	public TestVerifyProductFilterList() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_ProductFilterList() throws Exception {

		loginAsCEPUser();

		CEP cep = utilCreateSubscriptionRenewal();

		loginPage = cep.getLoginPage();
		homePage = cep.getHomePage();

		mainWindow.select();

		
		String logo = homePage.getAttribute("autodeskLogo", "alt");
		assertEquals(logo, "Autodesk");

		homePage.verifyInstance("BEFORE_SHOW_FILTER");
		homePage.click("showsFilerLink");
		
		String xpath = "//div[normalize-space(text())='Product']/following-sibling::div/ul[1]/li[1]/div/div[1]";
		findElementByXpath(xpath);
		Util.sleep(5000);
		homePage.waitForFieldPresent("productLine");
		String product = homePage.getValueFromGUI("productLine");
		Util.printMessage("product-----"+product);

		homePage.verifyInstance("AFTER_SHOW_FILTER");

		findElementByXpath(xpath);
		Util.sleep(5000);
		homePage.verifyInstance("UNCHECK_PRODUCT_LINE");

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
