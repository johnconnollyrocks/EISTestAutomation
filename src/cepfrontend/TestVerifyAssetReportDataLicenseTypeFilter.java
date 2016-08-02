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
 * Test class - TestVerifyAssetReportDataLicenseTypeFilter
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyAssetReportDataLicenseTypeFilter extends CEPTestBase {
	public TestVerifyAssetReportDataLicenseTypeFilter() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_AssetReportDataLicenseTypeFilter() throws Exception {

		loginAsCEPUser();

		CEP cep = utilCreateSubscriptionRenewal();
		
		loginPage = cep.getLoginPage();
		homePage = cep.getHomePage();
		mainWindow.select();

		
		homePage.click("showsFilerLink");
		Util.sleep(5000);

		String xpath = "//div[@id='filtersPanel']/div[2]/div[3]/div[2]/ul/li[1]//div/div[1]";
		findElementByXpath(xpath);
		
		Util.sleep(5000);
		homePage.waitForFieldPresent("productLine");
		String product = homePage.getValueFromGUI("productLine");
		Util.printMessage("License Type-----"+product);
		
		List<String> licenseTypeList = homePage.getTableColumn("licenseTypeInAssetReportDataTable");
		Util.printMessage("licenseTypeList"+licenseTypeList);
		for(int i = 0; i< licenseTypeList.size(); i++)
		{
			assertEquals(licenseTypeList.get(i), "Network - ASP");
		}
		
		String getStyle = homePage.getAttribute("paginationTable", "style");
		
		if(!getStyle.equalsIgnoreCase("display: none;")){
			while(!homePage.isFieldPresent("linkDisabled"))
			{
				homePage.click("paginatorNextButton");
			
				List<String> licenseTypeListInNextPages = homePage.getTableColumn("licenseTypeInAssetReportDataTable");
						Util.printMessage("licenseTypeListInNextPages"+licenseTypeListInNextPages);
						
				for(int j = 0; j< licenseTypeListInNextPages.size(); j++)
				{
					assertEquals(licenseTypeListInNextPages.get(j), "Network - ASP");
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
