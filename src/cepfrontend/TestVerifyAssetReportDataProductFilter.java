package cepfrontend;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import net.sourceforge.htmlunit.corejs.javascript.tools.shell.ConsoleTextArea;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.opera.core.systems.scope.protos.ConsoleLoggerProtos.ConsoleMessage;

import common.EISTestBase;
import common.Util;

/**
 * Test class - TestVerifyAssetReportDataProductFilter
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyAssetReportDataProductFilter extends CEPTestBase {
	public TestVerifyAssetReportDataProductFilter() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_AssetReportDataProductFilter() throws Exception {

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
		
		String xpath1 = "//table[normalize-space(@class)='ui-cep-grid-table table table-bordered']";
		findElementByXpath(xpath1);
		Util.sleep(5000);
		
		List<String> productNameList = homePage.getTableColumn("productNameInAssetReportDataTable");
		Util.printMessage("productNameList"+productNameList);
		for(int i = 0; i< productNameList.size(); i++)
		{
			assertEquals(productNameList.get(i), "AutoCAD Architecture");
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
					assertEquals(productNameListInNextPages.get(j), "AutoCAD Architecture");
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
