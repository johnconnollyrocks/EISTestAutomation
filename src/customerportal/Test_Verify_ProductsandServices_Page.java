package customerportal;

import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_ProductsandServices_Page extends CustomerPortalTestBase {
	
	
	public Test_Verify_ProductsandServices_Page() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_Usage_By_User_method() throws Exception {
		//setDebugMode(true);
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME"), testProperties.getConstant("CM_PASSWORD"));
		
		Util.printInfo("Loggin as CM >>>>>>>>"+testProperties.getConstant("CM_USER_NAME"));
		
		List<WebElement> Services=driver.findElements(By.xpath(".//*[contains(@class,'service not')]/div[2]/div[1]//div/span[1]"));
		Util.printInfo("Total Services pressent :"+Services.size());
		
		for(WebElement EachService:Services){
			
			String ServiceName=EachService.getText();
			Util.printInfo("Services Pressent:"+ServiceName);
		}
		
		if (Services.size() == 0) {
			Util.printInfo("No Services pressent under this CM :");
		}
		
		List<WebElement> Products = driver
				.findElements(By
						.xpath(".//*[contains(@class,'product')]/div[2]/div[1]//div/span[1]"));
		Util.printInfo("Total products pressent :" + Products.size());

		List<WebElement> ProductContracts = driver
				.findElements(By
						.xpath(".//*[contains(@class,'product')]/div[2]/div[1]//div/span[2]"));
		
		Util.printInfo("verifying Browser Download,Install Now,Download Now Options for Products");
		
		for (WebElement EachContract : ProductContracts) {
			String ProductContract = EachContract.getText();
			String ContactNumber[] = ProductContract.split("#");
			String ProductList = homePage
					.createFieldWithParsedFieldLocatorsTokens(
							"ProductContracts", ContactNumber[1].trim());
			homePage.click(ProductList);
			
			List<WebElement> OptionsPressentforProduct=driver.findElements(By.xpath(".//*[contains(@id,'ContactNumber[1].trim()')]/ul/li/a"));
			assertEquals(3, OptionsPressentforProduct.size());
			
			verifyandClickProductInstallBtnPandS(ContactNumber[1].trim());
			String Installversion = getversion();
			String InstallPlatform = getPlatform();
			String Installlanguage = getLanguage();
			verifInstallBtnModalDailog();
			homePage.click("CloseModalDailog");
			verifyandClickProductDownloadNowBtnPandS(ContactNumber[1].trim());
			String DoownloadNowversion = getversion();
			String DoownloadNowPlatform = getPlatform();
			String DoownloadNowlanguage = getLanguage();
			verifInstallBtnModalDailog();
			homePage.click("CloseModalDailog");
			verifyandClickProductBrowserDownloadBtnPandS(ContactNumber[1].trim());
			String BrowserDownloadversion = getversion();
			String BrowserDownloadPlatform = getPlatform();
			String BrowserDownloadlanguage = getLanguage();
			verifInstallBtnModalDailog();
			homePage.click("CloseModalDailog");
		}
		if(Products.size()==0){
			Util.printInfo("No Products pressent under this CM :");
		}
		logoutMyAutodeskPortal();
//		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME"), testProperties.getConstant("CM_PASSWORD"));
		
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
