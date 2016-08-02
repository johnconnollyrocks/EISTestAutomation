package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.Util;

public class Test_Verify_Product_Count_On_Contracts_Tab extends CustomerPortalTestBase {
	public Test_Verify_Product_Count_On_Contracts_Tab() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyFilters_On_User_Management_Tab() throws Exception {
		String[] ContractList = null;
		String contract1;
		String contract;
		ArrayList<String> alist=new ArrayList<String>();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(20000);
		homePage.click("users");
		homePage.click("CMToggle");
		Util.printInfo("Getting all Contracts under CM :: ");
		List<WebElement> contracts=driver.findElements(By.xpath("//div[@class='contracts']//ul[@class='contractList cm']//li"));
		Util.printInfo("The number of contracts pressent are :: "+contracts.size());
	
		for(WebElement EachContract : contracts ){
			
			String ContractNumber=EachContract.getText();
			ContractList=ContractNumber.split("#");
			contract1=ContractList[1].trim();
			Util.printInfo("contractNumber is :: "+contract1);
			alist.add(contract1);
			}
		homePage.click("contractReport");
		Util.sleep(10000);
		List<WebElement> products=driver.findElements(By.xpath("//a[@data-tracking='contract list: details']"));
		for(WebElement EachProduct : products ){
			String[] contractForProduct=EachProduct.getAttribute("href").split("#");
			Util.printInfo("Product is :: "+EachProduct.getText()+" for contract "+contractForProduct[2]);
			if(Integer.parseInt(EachProduct.getText())==0){
				fail("Product cannot be zero");
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
