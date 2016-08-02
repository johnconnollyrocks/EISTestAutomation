package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.DOMXmlParser;
import common.SoapUIExampleTest;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_By_User_RemoveUser extends CustomerPortalTestBase {
	
	
	public Test_Verify_By_User_RemoveUser() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_By_User_RemoveUser_method() throws Exception {
		//setDebugMode(true);
		

		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		if(getEnvironment().equalsIgnoreCase("DEV")){
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		}

		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		
		
//		String removeUserLink = homePage.createFieldWithParsedFieldLocatorsTokens("specificRemoveLink", homePage.getConstant("EMAIL_STG"));
		homePage.click("removestgUserLink");
		Util.sleep(2000);
//		String removeconfirmbutton = homePage.createFieldWithParsedFieldLocatorsTokens("specificConfirmRemoveButton", homePage.getConstant("EMAIL_STG"));
		homePage.click("removestgConfirmRemoveButton");
		Util.sleep(4000);
		homePage.click("reporting");
		Util.sleep(2000);
		homePage.click("byUsers");
		Util.sleep(2000);
		
		homePage.refresh();
		Util.sleep(10000);
		int rows = driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();
		
		for(int i =1;i<=rows;i++){
			String name = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/*[@class='name']")).getText().trim();
//			String email = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/*[@class='email']")).getText().trim();
			
//			if(testProperties.getConstant("EMAIL").equalsIgnoreCase(email)){
					assertEquals(name, testProperties.getConstant("USERNAME"));
//					assertEquals(email, testProperties.getConstant("EMAIL"));
//			}
		}
		
		homePage.click("users");
		Util.sleep(20000);
		Util.printInfo("Adding a User");
		homePage.clickAndWait("addUser", "email");
		homePage.populate();
		WebElement saveContbutton = driver.findElement(By.xpath("//div[contains(@id,'save-cancel')]//a[contains(@class,'save-user')]"));
		
//		if(!driver.findElement(By.xpath(".//*[@id='access-toggle']")).getAttribute("class").contains("checked")){
//			driver.findElement(By.xpath(".//*[@id='access-toggle']")).click();
//		}
		
		saveContbutton.click();
		Util.sleep(2000);
		
		
		checkChecKBox("productDowloadsCheckboxInput", "productDowloadsCheckboxLabel");
		assignAllBenefits();
		assignAllProducts();
//		String changedBenefitsSeatsAvailableForContract=homePage.getValueFromGUI("benefitsSeatsAvailableForContract1");
//		Util.printInfo("Changed benefits Seats available for the Contract are "+changedBenefitsSeatsAvailableForContract);
//				
//		String changedProductSeatsAvailableForContract1=homePage.getValueFromGUI("productsServicesFirstProductSeatsAvailable");
//		Util.printInfo("Changed Products Seats available for the Contract are "+changedProductSeatsAvailableForContract1);
			
		homePage.click("saveButton");
		Util.sleep(5000);
		
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
