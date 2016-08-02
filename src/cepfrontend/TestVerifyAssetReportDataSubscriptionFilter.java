package cepfrontend;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

/**
 * Test class - TestVerifyAssetReportDataSubscriptionFilter
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyAssetReportDataSubscriptionFilter extends CEPTestBase {
	public TestVerifyAssetReportDataSubscriptionFilter() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_AssetReportDataSubscriptionFilter() throws Exception {

		loginAsCEPUser();

		CEP cep = utilCreateSubscriptionRenewal();
		
		loginPage = cep.getLoginPage();
		homePage = cep.getHomePage();
		mainWindow.select();

		
		homePage.click("showsFilerLink");
		Util.sleep(5000);

		String xpath = "//div[@id='filtersPanel']/div[2]/div[6]/div[2]/ul/li[1]/div/div[1]";
		findElementByXpath(xpath);
		Util.sleep(5000);
		
		List<String> subscriptionList = homePage.getTableColumn("subscriptionInAssetReportDataTable");
		Util.printMessage("subscriptionList"+subscriptionList);
		for(int i = 0; i< subscriptionList.size(); i++)
		{
			assertEquals(subscriptionList.get(i), "Active");
		}
		
		String getStyle = homePage.getAttribute("paginationTable", "style");
		
		if(!getStyle.equalsIgnoreCase("display: none;")){
			while(!homePage.isFieldPresent("linkDisabled"))
			{
				homePage.click("paginatorNextButton");
			
				List<String> subscriptionListInNextPages = homePage.getTableColumn("subscriptionInAssetReportDataTable");
						Util.printMessage("subscriptionListInNextPages"+subscriptionListInNextPages);
						
				for(int j = 0; j< subscriptionListInNextPages.size(); j++)
				{
					assertEquals(subscriptionListInNextPages.get(j), "Active");
				}
			}
		}
		
		findElementByXpath(xpath);
		
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
