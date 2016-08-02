package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_UserManagement_BulkUsers extends CustomerPortalTestBase {
	
	
	public Test_Verify_UserManagement_BulkUsers() throws IOException {
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
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.printInfo("clicking on user page :: ");
		homePage.click("users");
		
		Util.printInfo(">>>> Negative validations for Bulk Users <<<<");
		
		Util.printInfo("Validating Bulk user email by giving @ symbol for name in Email Address :: ");
		Util.sleep(4000);
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Navigating to Bulk Users tab ");
		homePage.click("BulkUser");
		String InvalidEmail=testProperties.getConstant("EndUser");
		Util.printInfo("Given Email ID :: "+InvalidEmail);
		driver.findElement(By.xpath(".//*[@id='bulk-input']")).sendKeys(InvalidEmail);
		homePage.click("BulkUsersSave");
		if(homePage.isFieldVisible("BulkUsersInvalidEmailIdOrFirstOrLastName")){
			homePage.verifyFieldExists("BulkUsersInvalidEmailIdOrFirstOrLastName");
			Util.sleep(4000);
			homePage.click("BulkUsersCancel");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		}else{
			EISTestBase.fail("Error message is not pressent for the email in Bulk Users :: "+InvalidEmail);
		}
		
		Util.printInfo("Validating Bulk user email by giving period . symbol for name in Email Address :: ");
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Navigating to Bulk Users tab ");
		homePage.click("BulkUser");
		String InvalidEmail1=testProperties.getConstant("EndUser1");
		Util.printInfo("Given Email ID :: "+InvalidEmail1);
		driver.findElement(By.xpath(".//*[@id='bulk-input']")).sendKeys(InvalidEmail1);
		homePage.click("BulkUsersSave");
		if(homePage.isFieldVisible("BulkUsersInvalidEmailIdOrFirstOrLastName")){
			homePage.verifyFieldExists("BulkUsersInvalidEmailIdOrFirstOrLastName");
			Util.sleep(4000);
			homePage.click("BulkUsersCancel");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		}else{
			EISTestBase.fail("Error message is not pressent for the email in Bulk Users :: "+InvalidEmail1);
		}
		
		Util.printInfo("Validating Bulk user email by giving period . symbol for domain in Email Address :: ");
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Navigating to Bulk Users tab ");
		homePage.click("BulkUser");
		String InvalidEmail2=testProperties.getConstant("EndUser2");
		Util.printInfo("Given Email ID :: "+InvalidEmail2);
		driver.findElement(By.xpath(".//*[@id='bulk-input']")).sendKeys(InvalidEmail2);
		homePage.click("BulkUsersSave");
		if(homePage.isFieldVisible("BulkUsersInvalidEmailIdOrFirstOrLastName")){
			homePage.verifyFieldExists("BulkUsersInvalidEmailIdOrFirstOrLastName");
			Util.sleep(4000);
			homePage.click("BulkUsersCancel");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		}else{
			EISTestBase.fail("Error message is not pressent for the email in Bulk Users :: "+InvalidEmail2);
		}
		
		Util.printInfo("Validating Bulk user email by giving period . symbol for ext or extension in Email Address :: ");
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Navigating to Bulk Users tab ");
		homePage.click("BulkUser");
		String InvalidEmail3=testProperties.getConstant("EndUser3");
		Util.printInfo("Given Email ID :: "+InvalidEmail2);
		driver.findElement(By.xpath(".//*[@id='bulk-input']")).sendKeys(InvalidEmail3);
		homePage.click("BulkUsersSave");
		if(homePage.isFieldVisible("BulkUsersInvalidEmailIdOrFirstOrLastName")){
			homePage.verifyFieldExists("BulkUsersInvalidEmailIdOrFirstOrLastName");
			Util.sleep(4000);
			homePage.click("BulkUsersCancel");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		}else{
			EISTestBase.fail("Error message is not pressent for the email in Bulk Users :: "+InvalidEmail3);
		}
		String ASCIIInvalidChars=testProperties.getConstant("ASCIIINVALIDCHAR");
		
		Util.printInfo("Validating Bulk Users for Invalid characters :: ");
		
		Util.printInfo("Invalid ASCII Characters are ::"+ASCIIInvalidChars);
		
		String[] InvalidCharacters=ASCIIInvalidChars.split(" ");
		
			for(String str : InvalidCharacters){
		
			homePage.clickAndWait("addUser", "email");
			String AppendEmailASCIIValues = testProperties.getConstant("EndUser4");
			String AppendASCIIInvalidChar=AppendEmailASCIIValues+" "+"<"+str+"name@domain.ext>;";
			Util.printInfo("Given Email ID :: "+AppendASCIIInvalidChar);
			
			driver.findElement(By.xpath(".//*[@id='bulk-input']")).sendKeys(AppendASCIIInvalidChar);
			
			homePage.click("BulkUsersSave");
			if(homePage.isFieldVisible("BulkUsersInvalidEmailIdOrFirstOrLastName")){
				homePage.verifyFieldExists("BulkUsersInvalidEmailIdOrFirstOrLastName");
				Util.sleep(4000);
				homePage.click("BulkUsersCancel");
				Util.sleep(4000);
				homePage.click("DontSaveButton");
			}else{
				EISTestBase.fail("Error message is not pressent for the email in Bulk Users :: "+AppendASCIIInvalidChar);
			}
			
			Util.sleep(4000);
			homePage.click("BulkUsersCancel");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
			}
		
		Util.printInfo(">>>> Validating First and last Names <<<<::");
		
		Util.printInfo("Validating Bulk user First and Last Name by giving numbers :: ");
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Navigating to Bulk Users tab ");
		homePage.click("BulkUser");
		String InvalidEmail21=testProperties.getConstant("FIRSTNAME");
		Util.printInfo("Given Name :: "+InvalidEmail21);
		driver.findElement(By.xpath(".//*[@id='bulk-input']")).sendKeys(InvalidEmail21+" "+"user"+"<name@domain.com>;");
		homePage.click("BulkUsersSave");
		if(homePage.isFieldVisible("BulkUsersInvalidEmailIdOrFirstOrLastName")){
			homePage.verifyFieldExists("BulkUsersInvalidEmailIdOrFirstOrLastName");
			Util.sleep(4000);
			homePage.click("BulkUsersCancel");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		}else{
			EISTestBase.fail("Error message is not pressent for the FirstName or LastName in Bulk Users :: "+InvalidEmail21);
		}
		
		Util.printInfo("Validating First and last Names ::");
		Util.printInfo("Validating Bulk user First and Last Name by giving Symbols :: ");
		
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Navigating to Bulk Users tab ");
		homePage.click("BulkUser");
		String InvalidEmail211=testProperties.getConstant("FIRSTNAMESY");
		Util.printInfo("Given Name :: "+InvalidEmail211);
		driver.findElement(By.xpath(".//*[@id='bulk-input']")).sendKeys(InvalidEmail211+" "+"user"+"<name@domain.com>;");
		homePage.click("BulkUsersSave");
		if(homePage.isFieldVisible("BulkUsersInvalidEmailIdOrFirstOrLastName")){
			homePage.verifyFieldExists("BulkUsersInvalidEmailIdOrFirstOrLastName");
			Util.sleep(4000);
			homePage.click("BulkUsersCancel");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		}else{
			EISTestBase.fail("Error message is not pressent for the FirstName or LastName in Bulk Users :: "+InvalidEmail211);
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
