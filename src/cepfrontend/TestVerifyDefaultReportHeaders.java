package cepfrontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

/**
 * Test class - TestVerifyDefaultReportHeaders
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyDefaultReportHeaders extends CEPTestBase {
	public TestVerifyDefaultReportHeaders() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_DefaultReportHeaders() throws Exception {

		ArrayList<String> expectedTableHeaders = new ArrayList<String>(
				Arrays.asList("Product", "Release", "Seats",
						"Serial # / Product Key", "License Type",
						"Subscription", "Support", "Subscription End Date"));
		loginAsCEPUser();

		CEP cep = utilCreateSubscriptionRenewal();

		loginPage = cep.getLoginPage();
		homePage = cep.getHomePage();

		mainWindow.select();

		
		String xpath = "//table[normalize-space(@class)='ui-cep-grid-table table table-bordered']";
		findElementByXpath(xpath);
//		WebElement webElement = driver.findElement(By.xpath("//table[normalize-space(@class)='ui-cep-grid-table table table-striped table-bordered']"));
//		Util.printMessage("webElement------"+webElement);

		List<String> actualTableHeaders = homePage.getTableRow("assetReportDataTable",
				0, true);

		assertEquals(actualTableHeaders.size(), expectedTableHeaders.size());
		
		for (int i = 0; i < actualTableHeaders.size(); i++) 
			assertEquals(actualTableHeaders.get(i), expectedTableHeaders.get(i));

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
