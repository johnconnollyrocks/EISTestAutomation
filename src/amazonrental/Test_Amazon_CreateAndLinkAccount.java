package amazonrental;

import java.io.IOException;
import java.util.Set;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;

import common.EISTestBase;


public class Test_Amazon_CreateAndLinkAccount extends
		AmazonRentalTestBase {

	public Test_Amazon_CreateAndLinkAccount() throws IOException {
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
		
		
		String accountLinkingResponse,strpassword="Password1";
		String firstName,lastName,autodeskID;
		
		firstName=getRandomString(6)+"First";
		lastName=getRandomString(6)+"Last";
		autodeskID=firstName+getUniqueId()+"@ssttest.net";
		String parentHandle = driver.getWindowHandle(); // get the current window handle
		
		
				
		productHome.populateField("selectionOptions",testProperties.getConstant("TESTCASE"));
		System.out.println("Selected Option .. " + testProperties.getConstant("TESTCASE") +"\n");
		
		
		// Running  the account link Test
		accountLinking.click("createAndLinkAccount");
		
		System.out.println("creating new account  ...."+"\n");
		
		// creating new account  
		
		Thread.sleep(8000);
		
		for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
		
	if (createAccount.checkFieldExistence("firstName")) {
		System.out.println("New account creation page is opened " +"\n");
		Thread.sleep(3000);
		
		
		createAccount.populateField("firstName",firstName);
		System.out.println("firstName is .. " + firstName +"\n");
		
		createAccount.populateField("lastName",lastName);
		System.out.println("lastName is .. " +lastName +"\n");
		
		createAccount.populateField("autodeskID",autodeskID);
		System.out.println("autodeskID is .. " + autodeskID +"\n");
		
		createAccount.populateField("newEmail",autodeskID);
		System.out.println("email ID  is  .. " + autodeskID +"\n");
		
		createAccount.populateField("confirmemail",autodeskID);
		System.out.println("confirm email is .. " + autodeskID +"\n");
		
		createAccount.populateField("newPassword",strpassword);
		System.out.println("Password is .. " + strpassword +"\n");
		
		createAccount.populateField("confirmPassword",strpassword);
		System.out.println("confirm Password is .. " + strpassword +"\n");
		
		System.out.println("selecting the terms... ");
		
		createAccount.check("TnC1");
		createAccount.check("TnC2");
		
		
		System.out.println("Clicking on sign up And Link .. ");
		
		createAccount.click("signupAndLink");
		Thread.sleep(5000);
		
		driver.close(); // close newly opened window when done with it
		driver.switchTo().window(parentHandle); // switch back to the original window
		
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
		 else{
		
		     EISTestBase.fail("account Linking response is not present..Create and Link account is failed");
		}
	}

	else {
		EISTestBase.fail("New account creation pop is not coming after clicking on Create and Link button");
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
