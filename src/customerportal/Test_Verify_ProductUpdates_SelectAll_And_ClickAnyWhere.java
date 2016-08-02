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

public class Test_Verify_ProductUpdates_SelectAll_And_ClickAnyWhere extends CustomerPortalTestBase {
	public Test_Verify_ProductUpdates_SelectAll_And_ClickAnyWhere() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_ProductUpdates_SelectAll_ClickAnyWhere() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.click("productsUpdate");
		Util.printInfo("Click any where to see the view port or toggle drawer");
		List<WebElement> ProductsLst=driver.findElements(By.xpath("//div[@class='update-desc']/span[@class='name']"));
		
		for(WebElement eachProduct:ProductsLst){
			String ActualProduct=eachProduct.getText();
			String ProductName=homePage.createFieldWithParsedFieldLocatorsTokens("ActualProduct", ActualProduct);
			homePage.click(ProductName);
			Util.printInfo("Clicked on " +ProductName + "toggle drawer");
		}
		Util.printInfo("Clicked on all the products toggle drawer");
		
		homePage.populateField("ProductUpdateSelectAll");
		List<WebElement> allCheckBoxes=driver.findElements(By.xpath("//*[@id='updates-all']/div/div[@class='updates']/article/div[@class='btn-toggle-drawer']/input"));
		int checkBoxNumber = 1;
		for(WebElement prodCheckBox : allCheckBoxes) {
			if(prodCheckBox.isSelected()){
				Util.printInfo("The check box for the row number : " + checkBoxNumber + " is Selected");
				assertTrue("The check box for the row number : " + checkBoxNumber + " is Selected", prodCheckBox.isSelected());
				checkBoxNumber = checkBoxNumber+1;
			}
			else
				EISTestBase.fail("The check box for the row number : " + checkBoxNumber + " is not Selected");	
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