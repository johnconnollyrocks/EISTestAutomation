package cepfrontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

/**
 * Test class - TestVerifyReportHeadersWithOutSupport
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyReportHeadersWithOutSupport extends CEPTestBase {
	public TestVerifyReportHeadersWithOutSupport() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_ReportHeadersWithOutSupport() throws Exception {

		ArrayList<String> tableHeadersWithSupport = new ArrayList<String>(
				Arrays.asList("Product", "Release", "Seats",
						"Serial # / Product Key", "License Type",
						"Subscription", "Support", "Subscription End Date"));
		
		ArrayList<String> tableHeadersWithOutSupport = new ArrayList<String>(
				Arrays.asList("Product", "Release", "Seats",
						"Serial # / Product Key", "License Type",
						"Subscription", "Subscription End Date"));
		
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

		assertEquals(actualTableHeaders.size(), tableHeadersWithSupport.size());
		
		for (int i = 0; i < actualTableHeaders.size(); i++) 
			assertEquals(actualTableHeaders.get(i), tableHeadersWithSupport.get(i));
		Util.sleep(5000);
		
		//Clicking on the Display drop down button
		
		String xpath1 = "//div[@class='ui-dropdown-header']/div";
		findElementByXpath(xpath1);
			
			Util.sleep(5000);
			
			//Clicking on the support check box in Display drop down button

			for (int second = 0;; second++) {
	            if (second >= 60) Assert.fail("timeout");
	            try
	            {
	                if (driver.findElement(By.xpath("//div[@class='ui-dropdown-list']/div/ul/li[9]/div/div[1]")).isDisplayed()) break ;
	            }
	            catch (Exception ex)
	            {}
	            Util.sleep(1000);
	        }
					
			String xpath2 = "//div[@class='ui-dropdown-list']/div/ul/li[9]/div/div[1]";
			findElementByXpath(xpath2);
			Util.sleep(2000);
			
			findElementByXpath(xpath);
//			WebElement webElementTable = driver.findElement(By.xpath("//table[normalize-space(@class)='ui-cep-grid-table table table-striped table-bordered']"));
//			Util.printMessage("webElementTable------"+webElementTable);

			List<String> tableHeadersAfterSupportUnSelect = new ArrayList<>();
			 tableHeadersAfterSupportUnSelect = homePage.getTableRow("assetReportDataTable",
					0, true);
			 if (tableHeadersAfterSupportUnSelect.get(6).equalsIgnoreCase(""))
				 tableHeadersAfterSupportUnSelect.remove(6);
			assertEquals(tableHeadersAfterSupportUnSelect.size(), tableHeadersWithOutSupport.size());
			
			for (int i = 0; i < tableHeadersAfterSupportUnSelect.size(); i++) 
				assertEquals(tableHeadersAfterSupportUnSelect.get(i), tableHeadersWithOutSupport.get(i));

			
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
