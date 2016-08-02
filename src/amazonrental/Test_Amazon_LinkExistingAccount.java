package amazonrental;

import java.io.IOException;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;


public class Test_Amazon_LinkExistingAccount extends
		AmazonRentalTestBase {

	public Test_Amazon_LinkExistingAccount() throws IOException {
		super();

	}

	@Before
	public void setUp() throws Exception {
		launchamazonRental(testProperties.getConstant("PRODUCTURL"));
	}

	@Test
	public void Test_AmazonRental_AccountLinking()
			throws Exception {
		loginPage.click("getStartedButton");
		Thread.sleep(8000);
		loginAmazonProduct(testProperties.getConstant("AMAZON_Login"),testProperties.getConstant("AMAZON_Password"));
		Thread.sleep(8000);
		// account linking
		
		
		String accountLinkingResponse;
				
		productHome.populateField("selectionOptions",testProperties.getConstant("TESTCASE"));
		System.out.println("Selected Option .. " + testProperties.getConstant("TESTCASE") +"\n");
		
		accountLinking.populateField("username",testProperties.getConstant("USER_NAME"));
		System.out.println("Username .." + testProperties.getConstant("USER_NAME") +"\n");
		accountLinking.populateField("userEmail",testProperties.getConstant("EMAIL"));	
		System.out.println("Email ID.. " + testProperties.getConstant("EMAIL") +"\n");

		// Running  the account link Test
		accountLinking.click("linkExistingAccount");
		
		// Checking for the account Linking Test Report 
		Thread.sleep(8000);
		
		if (productHome.checkFieldExistence("linkingResponse")) {
			System.out.println("account Linking response present.. validatng the response " +"\n");
			Thread.sleep(8000);
			accountLinkingResponse = productHome.getValueFromGUI("linkingResponse");
			if (accountLinkingResponse.contains("linked successfully")) {
				System.out.println("account linking done successfull for the user "+testProperties.getConstant("USER_NAME")+"\n");
				System.out.println("account linking response "+accountLinkingResponse+"\n");
			}
			else{
				System.out.println("account linking is fail for the user "+testProperties.getConstant("USER_NAME")+"\n");
				System.out.println("account linking response "+accountLinkingResponse+"\n");
			}
			
		}

		else {
			EISTestBase.fail("account Linking  response is not present");
		}
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
