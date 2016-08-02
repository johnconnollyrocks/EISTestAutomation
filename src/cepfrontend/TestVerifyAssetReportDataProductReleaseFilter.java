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
 * Test class - TestVerifyAssetReportDataProductReleaseFilter
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyAssetReportDataProductReleaseFilter extends CEPTestBase {
	public TestVerifyAssetReportDataProductReleaseFilter() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_AssetReportDataProductReleaseFilter() throws Exception {

		loginAsCEPUser();

		CEP cep = utilCreateSubscriptionRenewal();
		
		loginPage = cep.getLoginPage();
		homePage = cep.getHomePage();
		mainWindow.select();

		
		homePage.click("showsFilerLink");
		Util.sleep(5000);

		String xpath = "//div[normalize-space(text())='Product']/following-sibling::div/ul[1]/li[1]/div/div[1]";
		findElementByXpath(xpath);
		Util.sleep(5000);
		
		String xpath1 = "//div[@id='filtersPanel']/div[2]/div[2]/div[2]/ul/li[2]/div/div[1]";
		findElementByXpath(xpath1);
		Util.sleep(5000);
		
		List<String> productNameList = homePage.getTableColumn("productNameInAssetReportDataTable");
		List<String> releaseList = homePage.getTableColumn("releaseNameInAssetReportDataTable");
		Util.printMessage("productNameList"+productNameList);
		Util.printMessage("releaseList"+releaseList);
		
		for(int i = 0; i< productNameList.size(); i++)
		{
			assertEquals(productNameList.get(i), "AutoCAD Architecture");
			assertEquals(releaseList.get(i), "2013");
		}
		
		String getStyle = homePage.getAttribute("paginationTable", "style");
		
		if(!getStyle.equalsIgnoreCase("display: none;")){
			while(!homePage.isFieldPresent("linkDisabled"))
			{
				homePage.click("paginatorNextButton");
				
				String xpath2 = "//table[normalize-space(@class)='ui-cep-grid-table table table-bordered']";
				findElementByXpath(xpath2);
				Util.sleep(5000);
				List<String> productNameListInNextPages = homePage.getTableColumn("productNameInAssetReportDataTable");
				List<String> releaseNameListInNextPages = homePage.getTableColumn("releaseNameInAssetReportDataTable");
						Util.printMessage("productNameListInNextPages"+productNameListInNextPages);
						Util.printMessage("releaseNameListInNextPages"+releaseNameListInNextPages);
						
				for(int j = 0; j< productNameListInNextPages.size(); j++)
				{
					assertEquals(productNameListInNextPages.get(j), "AutoCAD Architecture");
					assertEquals(releaseNameListInNextPages.get(j), "2013");
				}
			}
		}
		
		findElementByXpath(xpath);
		Util.sleep(5000);
		findElementByXpath(xpath1);
		
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
