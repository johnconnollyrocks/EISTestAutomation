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
 * Test class - TestVerifyAssetReportDataSupportFilter
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyAssetReportDataSupportFilter extends CEPTestBase {
	public TestVerifyAssetReportDataSupportFilter() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_AssetReportDataSupportFilter() throws Exception {

		loginAsCEPUser();

		CEP cep = utilCreateSubscriptionRenewal();
		
		loginPage = cep.getLoginPage();
		homePage = cep.getHomePage();
		mainWindow.select();

		
		homePage.click("showsFilerLink");
		Util.sleep(5000);

		String xpath = "//div[@id='filtersPanel']/div[2]/div[7]/div[2]/ul/li[2]/div/div[1]";
		findElementByXpath(xpath);
		Util.sleep(5000);
		
		List<String> supportList = homePage.getTableColumn("supportInAssetReportDataTable");
		Util.printMessage("supportList"+supportList);
		for(int i = 0; i< supportList.size(); i++)
		{
			assertEquals(supportList.get(i), "Web, Phone");
		}
		
		String getStyle = homePage.getAttribute("paginationTable", "style");
		
		if(!getStyle.equalsIgnoreCase("display: none;")){
			while(!homePage.isFieldPresent("linkDisabled"))
			{
				homePage.click("paginatorNextButton");
			
				List<String> supportListInNextPages = homePage.getTableColumn("supportInAssetReportDataTable");
						Util.printMessage("supportListInNextPages"+supportListInNextPages);
						
				for(int j = 0; j< supportListInNextPages.size(); j++)
				{
					assertEquals(supportListInNextPages.get(j), "Web, Phone");
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
