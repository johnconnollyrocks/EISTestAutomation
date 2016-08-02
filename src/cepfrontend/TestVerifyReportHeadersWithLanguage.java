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
public final class TestVerifyReportHeadersWithLanguage extends CEPTestBase {
	public TestVerifyReportHeadersWithLanguage() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_ReportHeadersWithOutSupport() throws Exception {

		ArrayList<String> tableHeadersWithLanguage = new ArrayList<String>(
				Arrays.asList("Product", "Release", "Seats",
						"Serial # / Product Key", "License Type", "Language",
						"Subscription", "Support", "Subscription End Date"));
		
	
		loginAsCEPUser();

		CEP cep = utilCreateSubscriptionRenewal();

		loginPage = cep.getLoginPage();
		homePage = cep.getHomePage();

		mainWindow.select();

		
		Util.sleep(5000);
		
		//Clicking on the Display drop down button
		
		String xpath = "//div[@class='ui-dropdown-header']/div";
		findElementByXpath(xpath);
//			WebElement webElement1 = driver.findElement(By.xpath("//div[@class='ui-dropdown-header']/div"));
//			Util.sleep(3000);
//			Util.printMessage("webElement------"+webElement1);
//			driver.switchTo().activeElement();
//			driver.findElement(By.xpath("//div[@class='ui-dropdown-header']/div")).click();
			Util.sleep(5000);
			
			//Clicking on the language check box in Display drop down button

			for (int second = 0;; second++) {
	            if (second >= 60) Assert.fail("timeout");
	            try
	            {
	                if (driver.findElement(By.xpath("//div[@class='ui-dropdown-list']/div/ul/li[6]/div/div[1]")).isDisplayed()) break ;
	            }
	            catch (Exception ex)
	            {}
	            Util.sleep(1000);
	        }
					
			String xpath1 = "//div[@class='ui-dropdown-list']/div/ul/li[6]/div/div[1]";
			findElementByXpath(xpath1);
//			WebElement support = driver.findElement(By.xpath("//div[@class='ui-dropdown-list']/div/ul/li[6]/div/div[1]"));
//			Util.printMessage("support------"+support);
//			driver.switchTo().activeElement();
//			driver.findElement(By.xpath("//div[@class='ui-dropdown-list']/div/ul/li[6]/div/div[1]")).click();
			Util.sleep(2000);
			
			String xpath2 = "//table[normalize-space(@class)='ui-cep-grid-table table table-bordered']";
			findElementByXpath(xpath2);
//			WebElement webElementTable = driver.findElement(By.xpath("//table[normalize-space(@class)='ui-cep-grid-table table table-striped table-bordered']"));
//			Util.printMessage("webElementTable------"+webElementTable);

			List<String> tableHeadersAfterLanguageSelect = new ArrayList<>();
			tableHeadersAfterLanguageSelect = homePage.getTableRow("assetReportDataTable",
					0, true);
			 
			assertEquals(tableHeadersAfterLanguageSelect.size(), tableHeadersWithLanguage.size());
			
			for (int i = 0; i < tableHeadersAfterLanguageSelect.size(); i++) 
				assertEquals(tableHeadersAfterLanguageSelect.get(i), tableHeadersWithLanguage.get(i));

			
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
