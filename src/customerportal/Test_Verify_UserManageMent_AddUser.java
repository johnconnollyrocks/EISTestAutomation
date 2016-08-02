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
//import customerportal.resource.UserDetailsDTO;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Raghavendra Yadav
 * @version 1.0.0
 */
public final class Test_Verify_UserManageMent_AddUser extends CustomerPortalTestBase {
	
	
	public Test_Verify_UserManageMent_AddUser() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}
	
	@Before
	public void setUp() throws Exception {	
		launchMyAutodeskPortal(getBaseURL()); 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(5000);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		
		Util.printInfo("clicking on user page :: ");
		homePage.click("users");
		Util.printInfo("Verifying add user fields :: ");

		Util.printInfo("Verifying Email address field :: ");
		Util.sleep(4000);
		
		Util.printInfo(">>>> Negative validations for email field <<<<");
		
		homePage.clickAndWait("addUser", "email");
		Util.sleep(4000);
		Util.printInfo("Validating email field domain name by giving @ symbol :: ");
		Util.printInfo("Example: name@domain.ext@");
		String improperEmail = testProperties.getConstant("EMAIL");
		Util.sleep(6000);
		Util.printInfo("Given Email ID :: "+improperEmail);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				improperEmail);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");

		if (homePage.isFieldVisible("InvalidEmailID")) {
			homePage.verifyFieldExists("InvalidEmailID");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		} else {
			EISTestBase
					.fail("Negative validation fail, error message is not pressent in email field");
		}
		
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email field by giving @ symbol in the name of the email Address :: ");
		String emailStartingwithatSymbol = testProperties.getConstant("EMAIL1");
		Util.printInfo("Given Email ID :: "+emailStartingwithatSymbol);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailStartingwithatSymbol);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");

		if (homePage.isFieldVisible("InvalidEmailID")) {
			homePage.verifyFieldExists("InvalidEmailID");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		} else {
			EISTestBase
					.fail("Negative validation fail, error message is not pressent in email field after giving @ symbol in name of email address");
		}

		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email field by giving period . in the end of the name of email Address :: ");
		String emailStartingwithdot = testProperties.getConstant("EMAIL2");
		Util.printInfo("Given Email ID :: "+emailStartingwithdot);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailStartingwithdot);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");

		if (homePage.isFieldVisible("InvalidEmailID")) {
			homePage.verifyFieldExists("InvalidEmailID");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		} else {
			EISTestBase
					.fail("Negative validation fail, error message is not pressent in email field");
		}
				
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email field by giving period . in the end of the domain of email Address :: ");
		String emailnameEndwithdot = testProperties.getConstant("EMAIL4");
		Util.printInfo("Given Email ID :: "+emailnameEndwithdot);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailnameEndwithdot);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");

		if (homePage.isFieldVisible("InvalidEmailID")) {
			homePage.verifyFieldExists("InvalidEmailID");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		} else {
			EISTestBase
					.fail("Negative validation fail, error message is not pressent in email field "
							+ emailnameEndwithdot);
		}
		
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email field by giving period . in the end of the ext or extension of email Address :: ");
		String emailnameEndwithdot1 = testProperties.getConstant("EMAIL5");
		Util.printInfo("Given Email ID :: "+emailnameEndwithdot1);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailnameEndwithdot1);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");

		if (homePage.isFieldVisible("InvalidEmailID")) {
			homePage.verifyFieldExists("InvalidEmailID");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		} else {
			EISTestBase
					.fail("Negative validation fail, error message is not pressent in email field "
							+ emailnameEndwithdot1);
		}
		
		Util.printInfo("Validating email field by giving Invalid ACSII characters :: ");
		
		String InvalidACSIIcharacters=testProperties.getConstant("ASCIIINVALIDCHAR");
		
		Util.printInfo("Invalid ASCII Characters are ::"+InvalidACSIIcharacters);
		
		String[] InvalidCharacters=InvalidACSIIcharacters.split(" ");
		
			for(String str : InvalidCharacters){
		
			homePage.clickAndWait("addUser", "email");
			String AppendEmailASCIIValues = testProperties.getConstant("EMAIL6");
			String ASCIIInvalidChar=str+AppendEmailASCIIValues;
			Util.printInfo("Given Email ID :: "+ASCIIInvalidChar);
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
					ASCIIInvalidChar);
			driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");

			if (homePage.isFieldVisible("InvalidEmailID")) {
				homePage.verifyFieldExists("InvalidEmailID");
				homePage.click("CancleButton");
				Util.sleep(4000);
				homePage.click("DontSaveButton");
			} else {
				EISTestBase
						.fail("Negative validation fail, error message is not pressent in email field "
								+ ASCIIInvalidChar);
			}
			
		}
		
		
		/*homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email field by giving - symbol in starting of the email name :: ");
		String emailnameEndwithhyphen1 = testProperties.getConstant("EMAILSTARTWITHHYPHEN");
		Util.printInfo("Given Email ID :: "+emailnameEndwithhyphen1);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailnameEndwithhyphen1);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");

		if (homePage.isFieldPresent("InvalidEmail")) {
			homePage.verifyFieldExists("InvalidEmail");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		} else {
			EISTestBase
					.fail("Negative validation fail, error message is not pressent in email field "
							+ emailnameEndwithdot);
		}*/
		
		Util.printInfo(">>>> Positive Validations for email filed <<<<<");
		
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email field by giving - symbol in middle of the email name :: ");
		String emailnamewithhyphon = testProperties.getConstant("VALIDEMAIL");
		Util.printInfo("Given Email ID :: "+emailnamewithhyphon);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailnamewithhyphon);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");
		
		Util.printInfo("Validating by giving valid email address :: ");
		assertEquals(
				false,
				driver.findElement(
						By.xpath("//span[contains(text(),'Email')]//following-sibling::span[contains(@class,'validation-msg')]/img"))
						.isDisplayed());
		homePage.click("CancleButton");
		Util.sleep(4000);
		homePage.click("DontSaveButton");
		
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email name field by giving valid combination of letters :: ");
		String emailnamewithhyphon1 = testProperties.getConstant("VALIDLETTERS");
		Util.printInfo("Given Email ID :: "+emailnamewithhyphon1);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailnamewithhyphon1);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");
		
		assertEquals(
				false,
				driver.findElement(
						By.xpath("//span[contains(text(),'Email')]//following-sibling::span[contains(@class,'validation-msg')]/img"))
						.isDisplayed());
		Util.printInfo("Validated email name field by giving valid combination of letters :: ");
		homePage.click("CancleButton");
		Util.sleep(4000);
		homePage.click("DontSaveButton");
		
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email domain name by giving valid characters :: ");
		String emailnamewithhyphon11 = testProperties.getConstant("VALIDLETTERS");
		Util.printInfo("Given Email ID :: "+emailnamewithhyphon11);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailnamewithhyphon11);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");
		
		assertEquals(
				false,
				driver.findElement(
						By.xpath("//span[contains(text(),'Email')]//following-sibling::span[contains(@class,'validation-msg')]/img"))
						.isDisplayed());
		Util.printInfo("Error message should not pressent ::");
		Util.printInfo("Validated email domain name by giving valid characters :: ");
		homePage.click("CancleButton");
		Util.sleep(4000);
		homePage.click("DontSaveButton");
		
		Util.printInfo("Validating email field by giving Numbers :: ");
		
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email name by giving valid numbers [0-9] :: ");
		String emailnamewithNumber = testProperties.getConstant("EMAIL7");
		Util.printInfo("Given Email ID :: "+emailnamewithNumber);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailnamewithNumber);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");
		
		assertEquals(
				false,
				driver.findElement(
						By.xpath("//span[contains(text(),'Email')]//following-sibling::span[contains(@class,'validation-msg')]/img"))
						.isDisplayed());
		Util.printInfo("Error message should not pressent ::");
		Util.printInfo("Validated email domain name by giving valid characters :: ");
		homePage.click("CancleButton");
		Util.sleep(4000);
		homePage.click("DontSaveButton");
		
		Util.printInfo("Validating email field by giving Capital Letters from [A-Z] :: ");
		
		homePage.clickAndWait("addUser", "email");
		Util.printInfo("Validating email field by giving Capital LEtters from [A-Z] :: ");
		String emailnamewithCapitalLetters= testProperties.getConstant("EMAIL8");
		Util.printInfo("Given Email ID :: "+emailnamewithCapitalLetters);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				emailnamewithCapitalLetters);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");
		
		assertEquals(
				false,
				driver.findElement(
						By.xpath("//span[contains(text(),'Email')]//following-sibling::span[contains(@class,'validation-msg')]/img"))
						.isDisplayed());
		Util.printInfo("Error message should not pressent ::");
		Util.printInfo("Validated email domain name by giving valid characters :: ");
		homePage.click("CancleButton");
		Util.sleep(4000);
		homePage.click("DontSaveButton");
		
		Util.printInfo("Validating email field by giving Invalid ACSII characters :: ");
		
		String validACSIIcharacters1=testProperties.getConstant("ASCIIVALIDCHAR");
		
		Util.printInfo("Valid ASCII Characters are ::"+validACSIIcharacters1);
		
		String[] validCharacters=validACSIIcharacters1.split(" ");
		
			for(String str : validCharacters){
		
			homePage.clickAndWait("addUser", "email");
			String ProperEmail = testProperties.getConstant("EMAIL6");
			String AppendEmailASCIIValues=str+ProperEmail;
			Util.printInfo("Given Email ID :: "+AppendEmailASCIIValues);
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
					AppendEmailASCIIValues);
			driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");

			assertEquals(
					false,
					driver.findElement(
							By.xpath("//span[contains(text(),'Email')]//following-sibling::span[contains(@class,'validation-msg')]/img"))
							.isDisplayed());
			Util.printInfo("Error message should not pressent ::");
			Util.printInfo("Validated email domain name by giving valid characters :: ");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		}
			
			homePage.clickAndWait("addUser", "email");
			Util.printInfo("Validating email field by giving Apostrophe :: ");
			String emailnamewithCapitalLetters1= testProperties.getConstant("APOSTRAPHE");
			String AppendemailnamewithCapitalLetters1="na"+emailnamewithCapitalLetters1+"me@domain.com";
			Util.printInfo("Given Email ID :: "+AppendemailnamewithCapitalLetters1);
			driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
					AppendemailnamewithCapitalLetters1);
			driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys("User");
			
			assertEquals(
					false,
					driver.findElement(
							By.xpath("//span[contains(text(),'Email')]//following-sibling::span[contains(@class,'validation-msg')]/img"))
							.isDisplayed());
			Util.printInfo("Error message should not pressent ::");
			Util.printInfo("Validated email domain name by giving valid characters :: ");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		
		homePage.clickAndWait("addUser", "email");
		
		Util.printInfo(">>> Validating First Name and Last Name Fields");
		
		Util.printInfo("Validating FirstName and LastName field by giving numbers :: ");
		String FirstNameField = testProperties.getConstant("USERNAME1");
		String LastNameField = testProperties.getConstant("LAST_NAME");
		Util.printInfo("Given First Name :: "+FirstNameField);
		Util.printInfo("Given Last Name :: "+LastNameField);

		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys(
				FirstNameField);
		driver.findElement(By.xpath(".//*[@id='last-name']")).sendKeys(
				LastNameField);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				"test123@ssttest.net");

		if (homePage.isFieldVisible("CannotContainNumbersOrSymbolsForFirstOrLastName")
				&& homePage.isFieldPresent("CannotContainNumbersOrSymbolsForFirstOrLastName")) {
			homePage.verifyFieldExists("CannotContainNumbersOrSymbolsForFirstOrLastName");
			homePage.verifyFieldExists("CannotContainNumbersOrSymbolsForFirstOrLastName");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		} else {
			EISTestBase
					.fail("validation got fail, error message is not pressent in FirstName or LastName field "
							+ FirstNameField );
		}

		Util.printInfo("Validating FirstName and LastName field by giving Symbols :: ");
		String FirstNameField1 = testProperties.getConstant("USERNAME2");
		String LastNameField1 = testProperties.getConstant("LAST_NAME1");
		
		Util.printInfo("Given First Name :: "+FirstNameField1);
		Util.printInfo("Given Last Name :: "+LastNameField1);
		
		homePage.clickAndWait("addUser", "email");
		
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys(
				FirstNameField1);
		driver.findElement(By.xpath(".//*[@id='last-name']")).sendKeys(
				LastNameField1);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
				"test123@ssttest.net");

		if (homePage.isFieldVisible("CannotContainNumbersOrSymbolsForFirstOrLastName")
				&& homePage.isFieldPresent("CannotContainNumbersOrSymbolsForFirstOrLastName")) {
			homePage.verifyFieldExists("CannotContainNumbersOrSymbolsForFirstOrLastName");
			homePage.verifyFieldExists("CannotContainNumbersOrSymbolsForFirstOrLastName");
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		} else {
			EISTestBase
					.fail("validation got fail, error message is not pressent in FirstName or LastName field "
							+ FirstNameField);
		}
		
		Util.printInfo(">>>> Positive Validations for First and Last Name <<<<<");
		
		Util.printInfo("Validating FirstName and LastName field by giving Apostrophe Positive validation :: ");
		String FirstNameFieldApostrophe = testProperties.getConstant("USERNAMEAP");
		String LastNameFieldApostrophe = testProperties.getConstant("USERNAMELASTNAMEAP");
		
		Util.printInfo("Given First Name :: "+FirstNameFieldApostrophe);
		Util.printInfo("Given Last Name :: "+LastNameFieldApostrophe);
		
		homePage.clickAndWait("addUser", "email");
		
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys(
				FirstNameFieldApostrophe);
		driver.findElement(By.xpath(".//*[@id='last-name']")).sendKeys(
				LastNameFieldApostrophe);
//		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(
//				"test123@ssttest.net");

		Util.printInfo("Validating for Apostrophe in First and Last Name :: ");
		
		if (homePage.isFieldVisible("CannotContainNumbersOrSymbolsForFirstOrLastName")
				&& homePage.isFieldPresent("CannotContainNumbersOrSymbolsForFirstOrLastName")) {
			EISTestBase
			.fail("validation got fail, error message is pressent in FirstName or LastName field for Apostrophe "
					+ FirstNameFieldApostrophe);
			homePage.click("CancleButton");
			Util.sleep(4000);
			homePage.click("DontSaveButton");
		} else {
			assertEquals(false, driver.findElement(By.xpath("//span[contains(text(),'First Name')]//following-sibling::span[contains(@class,'validation-msg')]/img")).isDisplayed());
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
