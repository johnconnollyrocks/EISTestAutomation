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
 * @author Raghavendra Yadav
 * @version 1.0.0
 */
public final class Test_Verify_severity_from_sorting_lists extends CustomerPortalTestBase {
	
	
	public Test_Verify_severity_from_sorting_lists() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {	
		launchMyAutodeskPortal(getBaseURL()); 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		
		String USERNAME = null;
		String EMAIL = null;
		String USER_NAME = null;
		String PASSWORD = null;
		String INSTANCE_NAME = null;
		if (getEnvironment().equalsIgnoreCase("DEV")) {
			USERNAME = testProperties.getConstant("USERNAME_DEV");
			EMAIL = testProperties.getConstant("EMAIL_DEV");
			USER_NAME = testProperties.getConstant("USER_NAME_DEV");
			PASSWORD = testProperties.getConstant("PASSWORD_DEV");
			INSTANCE_NAME = "NEW_USER_DEV";

		} else if (getEnvironment().equalsIgnoreCase("STG")) {
			USERNAME = testProperties.getConstant("USER_NAME_STG");
			EMAIL = testProperties.getConstant("EMAIL_STG");
			USER_NAME = testProperties.getConstant("USER_NAME_STG");
			PASSWORD = testProperties.getConstant("PASSWORD_STG");
			INSTANCE_NAME = "NEW_USER_STG";
		}
		Util.printInfo("Logging into CM's Account ");
		loginAsMyAutodeskPortalUser(USERNAME , PASSWORD);
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		Util.sleep(5000);
		homePage.click("ProductUpdates");
		homePage.click("Filter");
		
		CheckboxCheck("High");
			List<WebElement> ProductUpdates=driver.findElements(By.xpath("//div[@class='update-desc']/span[@class='name']"));
			for(WebElement ProductNames : ProductUpdates){
				String ActualProduct=ProductNames.getText();
				String ProductDetils=homePage.createFieldWithParsedFieldLocatorsTokens("ActualProduct", ActualProduct);
				homePage.click(ProductDetils);
				String ProductSeverity=homePage.createFieldWithParsedFieldLocatorsTokens("ProductSeverity", ActualProduct);
				String Severity=homePage.getValueFromGUI(ProductSeverity);
				Util.printInfo("The Severity for the prodcut "+ActualProduct+" is :: "+Severity);
				assertEquals("High",Severity);
			}
			homePage.click("Filter");
			CheckboxCheck("Medium");
			List<WebElement> ProductUpdates1=driver.findElements(By.xpath("//div[@class='update-desc']/span[@class='name']"));
			for(WebElement ProductNames : ProductUpdates1){
				String ActualProduct=ProductNames.getText();
				/*String ProductDetils=homePage.createFieldWithParsedFieldLocatorsTokens("ActualProduct", ActualProduct);
				homePage.click(ProductDetils);*/
				String ProductSeverity=homePage.createFieldWithParsedFieldLocatorsTokens("ProductSeverity", ActualProduct);
				String Severity=homePage.getValueFromGUI(ProductSeverity);
				Util.printInfo("The Severity for the prodcut "+ActualProduct+" is :: "+Severity);
				assertEquals("High",Severity);
			}
			homePage.click("Filter");
			CheckboxCheck("Low");
			List<WebElement> ProductUpdates11=driver.findElements(By.xpath("//div[@class='update-desc']/span[@class='name']"));
			for(WebElement ProductNames : ProductUpdates11){
				String ActualProduct=ProductNames.getText();
				/*String ProductDetils=homePage.createFieldWithParsedFieldLocatorsTokens("ActualProduct", ActualProduct);
				homePage.click(ProductDetils);*/
				String ProductSeverity=homePage.createFieldWithParsedFieldLocatorsTokens("ProductSeverity", ActualProduct);
				String Severity=homePage.getValueFromGUI(ProductSeverity);
				Util.printInfo("The Severity for the prodcut "+ActualProduct+" is :: "+Severity);
				assertEquals("High",Severity);
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
