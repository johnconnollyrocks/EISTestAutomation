package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ProductUpdates_ProductType extends CustomerPortalTestBase {
	public Test_Verify_ProductUpdates_ProductType() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_ProductUpdates_Types() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.click("productsUpdate");
		Util.printInfo("Clicking  toggle drawer");
		List<WebElement> ProductsLst=driver.findElements(By.xpath("//div[@class='update-desc']/span[@class='name']"));
		for(WebElement eachProduct:ProductsLst){
			homePage.click("toggleDrawer");
			String TypeOfProduct=homePage.getValueFromGUI("TypeOfProduct");
		
			if(TypeOfProduct.equalsIgnoreCase("Hot Fix")){
				assertTrue("", true);
			}
			else
			{
				EISTestBase.failTest("Type of the product is Service pack ");
			}
					
		}	
			logoutMyAutodeskPortal();   
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
		