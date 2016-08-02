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
public final class Test_Verify_IndividualCCOnly_NewUser extends CustomerPortalTestBase {
	
	
	public Test_Verify_IndividualCCOnly_NewUser() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_IndividualCCOnly_NewUser_method() throws Exception {
		//setDebugMode(true);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		
		Util.printInfo("Adding a User");
		homePage.clickAndWait("addUser", "email");
		homePage.populate();
		WebElement saveContbutton = driver.findElement(By.xpath("//div[contains(@id,'save-cancel')]//a[contains(@class,'save-user')]"));
		
//		if(!driver.findElement(By.xpath(".//*[@id='access-toggle']")).getAttribute("class").contains("checked")){
//			driver.findElement(By.xpath(".//*[@id='access-toggle']")).click();
//		}
		
		saveContbutton.click();
		Util.sleep(2000);
		
		//user added
		//edit access and assign some service under some contract
		
		homePage.click("editAccess");
		Util.sleep(2000);
		
		//click on assign all check box for 1st contract all services under it
		Util.printInfo("Clicking on checkbox for assigning to contract :: services");
		
		driver.findElement(By.xpath(".//*[@id='products']//label[text()='Assign']/ancestor::form/input[contains(@id,'services-238381-0')]/following-sibling::label")).click();
		Util.sleep(2000);
		
		
		Util.printInfo("User assigned to that contract and services under it");
		
		
		homePage.click("reporting");
		Util.sleep(2000);
		homePage.click("byUsers");
		Util.sleep(2000);
		
		homePage.refresh();
		int rows = driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();
		
		for(int i =1;i<=rows;i++){
			String name = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/*[@class='name']")).getText().trim();
			String usageforUser = driver.findElement(By.xpath("//*[@id='usage-by-users']//tr["+i+"]//td[@class='individual-usage']/div[@class='gauge']//span[@class='numerator']")).getText().trim();
			
			assertEquals(name, testProperties.getConstant("USERNAME"));
			assertEquals(usageforUser, "0");
			if (name.contentEquals(testProperties.getConstant("USERNAME"))){
				//make webservice call to consume credits. 
				//if response is successful then go to reporting page and check the number of pooled cloud credits consumed by user increases against the contract the service belonged to and the individual cloud credit remains zero
				//Under shared : it should show as no pooled credits purchased.
			}
			
			
			

		}
		
//		
//		
//		checkChecKBox("productDowloadsCheckboxInput", "productDowloadsCheckboxLabel");
//		assignAllBenefits();
//		assignAllProducts();
////		String changedBenefitsSeatsAvailableForContract=homePage.getValueFromGUI("benefitsSeatsAvailableForContract1");
////		Util.printInfo("Changed benefits Seats available for the Contract are "+changedBenefitsSeatsAvailableForContract);
////				
////		String changedProductSeatsAvailableForContract1=homePage.getValueFromGUI("productsServicesFirstProductSeatsAvailable");
////		Util.printInfo("Changed Products Seats available for the Contract are "+changedProductSeatsAvailableForContract1);
//			
//		homePage.click("saveButton");
//		Util.sleep(5000);
		
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
