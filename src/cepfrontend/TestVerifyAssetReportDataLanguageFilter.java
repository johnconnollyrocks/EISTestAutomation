package cepfrontend;

import java.io.IOException;
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
 * Test class - TestVerifyAssetReportDataLanguageFilter
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyAssetReportDataLanguageFilter extends CEPTestBase {
	public TestVerifyAssetReportDataLanguageFilter() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_AssetReportDataLanguageFilter() throws Exception {

		loginAsCEPUser();

		CEP cep = utilCreateSubscriptionRenewal();
		
		loginPage = cep.getLoginPage();
		homePage = cep.getHomePage();
		mainWindow.select();
		
		
		homePage.click("showsFilerLink");
		Util.sleep(5000);
		
		//Clicking on the language check box
		
		String xpath = "//div[@id='filtersPanel']/div[2]/div[4]/div[2]/ul/li[2]/div/div[1]";
		findElementByXpath(xpath);
		
		Util.sleep(5000);
		homePage.waitForFieldPresent("productLine");
		String product = homePage.getValueFromGUI("productLine");
		Util.printMessage("Language-----"+product);
		
		//Clicking on the Display drop down button
	
		String xpath1 = "//div[@class='ui-dropdown-header']/div";
		findElementByXpath(xpath1);

		Util.sleep(5000);
		
		//Clicking on the check box in Display drop down button

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
				

		String xpath2 = "//div[@class='ui-dropdown-list']/div/ul/li[6]/div/div[1]";
		findElementByXpath(xpath2);
		
		Util.sleep(2000);
		
		List<String> languageList = homePage.getTableColumn("languageInAssetReportDataTable");
		Util.printMessage("languageList"+languageList);
		for(int i = 0; i< languageList.size(); i++)
		{
			assertEquals(languageList.get(i), "English, International");
		}
		
		
		String getStyle = homePage.getAttribute("paginationTable", "style");
		
		if(!getStyle.equalsIgnoreCase("display: none;")){
			while(!homePage.isFieldPresent("linkDisabled"))
			{
				homePage.click("paginatorNextButton");
			
				List<String> languageListInNextPages = homePage.getTableColumn("languageInAssetReportDataTable");
						Util.printMessage("languageListInNextPages"+languageListInNextPages);
						
				for(int j = 0; j< languageListInNextPages.size(); j++)
				{
					assertEquals(languageListInNextPages.get(j), "English, International");
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
