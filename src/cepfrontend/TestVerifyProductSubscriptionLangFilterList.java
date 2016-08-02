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
 * Test class - TestVerifyProductSubscriptionLangFilterList
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyProductSubscriptionLangFilterList extends CEPTestBase {
	public TestVerifyProductSubscriptionLangFilterList() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_ProductSubscriptionLangFilterList() throws Exception {

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
		Util.sleep(2000);
		homePage.waitForFieldPresent("productLine");
		String product = homePage.getValueFromGUI("productLine");
		Util.printMessage("productLineSelected1-----"+product);

		String xpath1 = "//div[@id='filtersPanel']/div[2]/div[6]/div[2]/ul/li[1]/div/div[1]";
		findElementByXpath(xpath1);
//		driver.findElement(By.xpath("//div[@id='filtersPanel']/div[2]/div[6]/div[2]/ul/li[1]/div/div[1]")).click();	
		Util.sleep(2000);
		homePage.waitForFieldPresent("productLineSelected2");
		String subscription = homePage.getValueFromGUI("productLineSelected2");
		Util.printMessage("productLineSelected2-----"+subscription);

		String xpath2 = "//div[@id='filtersPanel']/div[2]/div[4]/div[2]/ul/li[1]/div/div[1]";
		findElementByXpath(xpath2);
//		driver.findElement(By.xpath("//div[@id='filtersPanel']/div[2]/div[4]/div[2]/ul/li[2]/div/div[1]")).click();	
		Util.sleep(2000);
		homePage.waitForFieldPresent("productLineSelected3");
		String language = homePage.getValueFromGUI("productLineSelected3");
		Util.printMessage("productLineSelected3-----"+language);

		homePage.verifyInstance("AFTER_SHOW_FILTER");

		findElementByXpath(xpath2);
//		driver.findElement(By.xpath("//div[@id='filtersPanel']/div[2]/div[4]/div[2]/ul/li[2]/div/div[1]")).click();
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
