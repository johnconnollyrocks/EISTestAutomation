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
 * Test class - TestVerifyAssetReportDataProductProductFilter
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyAssetReportDataProductProductFilter extends CEPTestBase {
	public TestVerifyAssetReportDataProductProductFilter() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TestVerifyAssetReportDataProductProductFilter() throws Exception {
		ArrayList<String> expectedProductList = new ArrayList<String>(
				Arrays.asList("AutoCAD Architecture", "AutoCAD MEP"));
		
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
		
		String xpath1 = "//div[normalize-space(text())='Product']/following-sibling::div/ul[1]/li[4]/div/div[1]";
		findElementByXpath(xpath1);
		Util.sleep(5000);
		
		List<String> productNameList = homePage.getTableColumn("productNameInAssetReportDataTable");
		Util.printMessage("productNameList"+productNameList);
				
		for(int i = 0; i< productNameList.size(); i++)
		{
			String actualProductName = productNameList.get(i);
			assertTrueWithFlags("The actual product name '" + actualProductName + "' was found in the list of expected product name list " , expectedProductList.contains(actualProductName));
		}
		
		String getStyle = homePage.getAttribute("paginationTable", "style");
		
		if(!getStyle.equalsIgnoreCase("display: none;")){
			while(!homePage.isFieldPresent("linkDisabled"))
			{
				homePage.click("paginatorNextButton");
			
				List<String> productNameListInNextPages = homePage.getTableColumn("productNameInAssetReportDataTable");
				Util.printMessage("productNameListInNextPages"+productNameListInNextPages);
						
						
				for(int j = 0; j< productNameListInNextPages.size(); j++)
				{
					String actualProductNameInNextPages = productNameListInNextPages.get(j);
					assertTrueWithFlags("The actual product name '" + actualProductNameInNextPages + "' was found in the list of expected product name list " , expectedProductList.contains(actualProductNameInNextPages));
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
