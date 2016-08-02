package eurp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verify_Portal_EURP_ProductUsage_Products extends EURPTestBase{

	public Test_Verify_Portal_EURP_ProductUsage_Products() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verfy_ContractNumbers() throws Exception {
		Util.printInfo("Logging into customer portal ");
		ArrayList<String> SortedPrdNamesList=new ArrayList<String>();
		ArrayList<String> UnSortedPrdNamesList=new ArrayList<String>();
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		if(homePage.isFieldVisible("ManageUsers")){
			assertTrue("Successfully logged into EURP ", homePage.isFieldVisible("ManageUsers"));
		}else{
			EISTestBase.fail("Error occured while logging in ");
		}
		
		Util.printInfo("Clicking on reporting page");
		
		Util.printInfo("Verifying ProductUsage tab");
		
		driver.get("https://customer-stg.autodesk.com/cep/#reporting/product-usage");
		Util.sleep(40000);
		if(homePage.isFieldVisible("ProductUsage")){
			homePage.verifyFieldExists("SccussfullyNavigatedToProductUsage");
		}else{
			EISTestBase.fail("ProductUsage tab doesnot pressent in reporting page, so unable to click on ProductUsage");
		}
		
		List<WebElement> Frames=driver.findElements(By.tagName("iframe"));
		
		 for (WebElement iframe : Frames) {
		        driver.switchTo().frame(iframe);
				if(homePage.isFieldVisible("TotalTokensUsed"))
					break;
				driver.switchTo().defaultContent();
		    }
		
		 if(homePage.isFieldVisible("TotalTokensUsed")){
			 homePage.verifyFieldExists("TotalTokensUsed");
			 }else{
				 homePage.refresh();
				 Util.sleep(40000);
				 if(homePage.isFieldVisible("TotalTokensUsed")){
					 homePage.verifyFieldExists("TotalTokensUsed");
					 }else{
						 EISTestBase.fail("ProductUsage tab not loaded properly, please check again ");
					 }
			 }
		homePage.verifyFieldExists("TotalTokensUsed");
		Util.sleep(4000);
		homePage.verifyFieldExists("TotalTokensUsedValue");
		Util.sleep(4000);
		Util.printInfo("The total tokens used is / are :: "+homePage.getValueFromGUI("TotalTokensUsedValue"));
		homePage.click("SortyByProducts");
		Util.sleep(4000);
		List<WebElement> NumberOfProducts = homePage.getMultipleWebElementsfromField("ProductNames");
		
		for(WebElement EachProduct : NumberOfProducts){
			UnSortedPrdNamesList.add(EachProduct.getText());
		}
		
		Collections.sort(UnSortedPrdNamesList);
		
		homePage.click("ProductUsageSortBy");
		Util.sleep(4000);
		homePage.click("ListProductsName");
		Util.sleep(4000);
		List<WebElement> NumberOfProductsAfterSort = homePage.getMultipleWebElementsfromField("ProductNames");
		
		for(WebElement Product : NumberOfProductsAfterSort){
			SortedPrdNamesList.add(Product.getText());
		}
		
		Collections.sort(SortedPrdNamesList);
		
		Util.printInfo(" Verifying sorted list of product names ");
		
		assertTrue("Product Names are sorted successfully ", UnSortedPrdNamesList.equals(SortedPrdNamesList));
		
		Util.printInfo("Validating Export To CSV functionality in products usage page ");
		
		homePage.click("ProductUsageExportUsers");
		
		Util.sleep(4000);
		
		driver.switchTo().defaultContent();
		
		logoutMyAutodeskPortal();
		Util.printInfo("Successfully logged out ");
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