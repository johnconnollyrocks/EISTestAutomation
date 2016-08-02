package myautodeskportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

/**
 * Test class - TestVerifyLoginPasswordChangeValidate
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class TestVerifyLoginPasswordChangeValidate extends MyAutodeskPortalTestBase {
	public TestVerifyLoginPasswordChangeValidate() throws IOException {
		super("firefox");
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void TestVerifyLoginPasswordChangeValidate() throws Exception {
		String getStatus;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_1"));
		
		MyAutodeskPortal myAutodeskPortal = utilCreateMyAutodeskPortal();
		loginPage = myAutodeskPortal.getLoginPage();
		homePage = myAutodeskPortal.getHomePage();
		mainWindow.select();
		
	
		homePage.click("editProfile");
		homePage.click("updatePassword");
		//Change password from Password_1 to Password_2
		myAutodeskPortal.changepassword(testProperties.getConstant("PASSWORD_1"),testProperties.getConstant("PASSWORD_2"));
		logoutMyAutodeskPortal();
		
		//Login to Oxygen Page
		open(testProperties.getConstant("OXYGEN_URL"));
		login(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_2"));
		homePage.waitForFieldPresent("logOut");
		Util.printInfo("Successfully logged in to Oxygen page using the new password");
		logoutMyAutodeskPortal();   
		
		open(getBaseURL());
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_2"));
		homePage.click("editProfile");
		homePage.click("updatePassword");
		//Change password from Password_2 to Password_1
		myAutodeskPortal.changepassword(testProperties.getConstant("PASSWORD_2"),testProperties.getConstant("PASSWORD_1"));
		logoutMyAutodeskPortal();  
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_1"));			
		homePage.click("editProfile");
		homePage.click("updatePassword");
		//Change password from Password_1 to abaaaaa2
		myAutodeskPortal.changepassword(testProperties.getConstant("PASSWORD_1"), testProperties.getConstant("PASSWORD_3"));
		logoutMyAutodeskPortal(); 
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_3"));
		Util.printInfo("Logged in using new password");
		logoutMyAutodeskPortal();
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_3"));
		homePage.click("editProfile");
		homePage.click("updatePassword");
		//Change password from Password_1 to Aaaaaaa2
		myAutodeskPortal.changepassword(testProperties.getConstant("PASSWORD_3"), testProperties.getConstant("PASSWORD_4"));
		logoutMyAutodeskPortal(); 
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_4"));
		Util.printInfo("Logged in using new password");
		logoutMyAutodeskPortal();
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_4"));
		homePage.click("editProfile");
		homePage.click("updatePassword");
		//Change password from Password_1 to aaaaaaa2
		homePage.populateField("oldPassword",testProperties.getConstant("PASSWORD_4")) ;
		homePage.populateField("newPassword",testProperties.getConstant("PASSWORD_5")) ;
		homePage.click("savePassword");
		homePage.verifyFieldExistence("passwordDynamicValidationDistinct",true);
		homePage.click("cancelPassword");
		
		//Revert the password to Password1
		myAutodeskPortal.changepassword(testProperties.getConstant("PASSWORD_4"),testProperties.getConstant("PASSWORD_1"));
		logoutMyAutodeskPortal(); 			
	
		//Change Account details(First Name, Last Name, Email,Language)
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_1"));			
		homePage.click("editProfile");
		homePage.click("updateAccount");
		myAutodeskPortal.changeAccountDetails("EDIT_PROFILE");
		
		//Change Account Details, Click Cancel adn verify Save button
		homePage.populateField("firstName", "he");
		homePage.populateField("lastName", "i");
		homePage.populateField("email", "michael.hall@ssttest.net");
		homePage.click("cancelAccount");
		getStatus = homePage.getAttribute("saveAccount", "disabled");
		if(getStatus==null){
		Util.printInfo("The Save button is enabled");	
		}
		else if(getStatus.equalsIgnoreCase("true")){
		Util.printInfo("The Save button is disabled");
		}
		homePage.verifyField("firstName", "harry1");
		homePage.verifyField("lastName", "potter1");
		homePage.verifyField("email", "harry1.dev.potter@ssttest.net");
		logoutMyAutodeskPortal();
		
		//Validate account details
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_1"));
		homePage.click("editProfile");
		homePage.click("updateAccount");
		myAutodeskPortal.verifyAccountDetails("VERIFY_EDIT_PROFILE");
		logoutMyAutodeskPortal();		
		
		//Login to Oxygen Page
		open(testProperties.getConstant("OXYGEN_URL"));
		login(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_1"));
		homePage.waitForFieldPresent("logOut");
		Util.printInfo("Logged in to Oxygen page to verify updated user info..");
		homePage.click("editProfile");
		myAutodeskPortal.verifyAccountDetails("VERIFY_EDIT_PROFILE1");
		logoutMyAutodeskPortal();		
		
		open(getBaseURL());	
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_1"));
		//Revert the account details
		homePage.click("editProfile");
		homePage.click("updateAccount");
		myAutodeskPortal.changeAccountDetails("EDIT_PROFILE1");
		Util.printInfo("Reverted the Account Details...");
		logoutMyAutodeskPortal();			
		
		
		//Verify Fields(Update Account, Email,Update Password)
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD_1"));		
		homePage.click("editProfile");
		homePage.verifyFieldExists("updateAccount");
		homePage.verifyFieldExists("autodeskID");
		homePage.click("updatePassword");
		homePage.verifyFieldExists("oldPassword");				
		homePage.click("updateAccount");
		Util.printInfo("Verified Field validations");
		
		//validate Save button
		homePage.populateField("firstName", "he");
		getStatus = homePage.getAttribute("saveAccount", "disabled");
		if(getStatus==null){
		Util.printInfo("The Save button is enabled");	
		}
		else if(getStatus.equalsIgnoreCase("true")){
		Util.printInfo("The Save button is disabled");
		}
		
		homePage.populateField("firstName", "he");
		homePage.clickToSubmit("cancelAccount",5000);
		getStatus = homePage.getAttribute("saveAccount", "disabled");
		if(getStatus==null){
		Util.printInfo("The Save button is enabled");	
		}
		else if(getStatus.equalsIgnoreCase("true")){
		Util.printInfo("The Save button is disabled");
		}
		
		//Validate already existing email
		homePage.populateField("email", "dan.meyer@ssttest.net");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("email",false);
		
		//Validate invalid email
		homePage.populateField("email", "harry");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("email",false); 	
		Util.printInfo("Finished Email validations");	
		
		//Validate all possible combinations of First Name 
		homePage.populateField("firstName", "");
		String fstName=homePage.getValueFromGUI("firstName").toString();
		if(fstName.isEmpty()){
		Util.printInfo("The field First Name is empty");	
		}
	
		homePage.populateField("firstName", "i");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("firstName",true);
		
		homePage.populateField("firstName", "he");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("firstName",true);
		
		homePage.populateField("firstName", "Mary Beth");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("firstName",true);
		
		homePage.populateField("firstName", "Jo-Anne");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("firstName",true);
		
		homePage.populateField("firstName", "1341345");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("firstName",true);
		
		homePage.populateField("firstName", "&*((@Q!@#");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("firstName",true);
		
		homePage.populateField("firstName", "'ng!ck");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("firstName",true);
		Util.printInfo("Finished First Name validations");
		
		//Validate all possible combinations of Last Name 
		homePage.populateField("lastName", "");
		String lstName=homePage.getValueFromGUI("lastName").toString();
		if(lstName.isEmpty()){
		Util.printInfo("The field Last Name is empty");	
		}
		
		homePage.populateField("lastName", "i");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("lastName",true);
		
		homePage.populateField("lastName", "he");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("lastName",true);
			
		homePage.populateField("lastName", "Jo-Anne");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("lastName",true);
		
		homePage.populateField("lastName", "1341345");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("lastName",true);
		
		homePage.populateField("lastName", "&*((@Q!@#");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("lastName",true);		
		
		homePage.populateField("lastName", "'ng!ck");
		homePage.clickToSubmit("saveAccount",5000);
		homePage.verifyErrorCheckFieldOnly("lastName",true);
		
		homePage.populateField("lastName", "frsty35");
		homePage.clickToSubmit("cancelAccount",5000);
		homePage.verifyField("lastName", "potter");					
	
		Util.printInfo("Finished Last Name validations");
		
		//Validate all possible combinations of Password Change 
		homePage.click("updatePassword");
		homePage.populateField("oldPassword","Password1") ;
		homePage.populateField("newPassword","Password") ;
		homePage.click("savePassword");
		homePage.verifyFieldExistence("passwordDynamicValidationNumber",true);
		homePage.click("cancelPassword");
		
		String pass=homePage.getValueFromGUI("oldPassword").toString();
		if(pass.isEmpty()){
		Util.printInfo("The field Current Password is empty");	
		}
		String pass1=homePage.getValueFromGUI("newPassword").toString();
		if(pass1.isEmpty()){
		Util.printInfo("The field New Password is empty");	
		}
		
		// Validate alpha characters for new password
		homePage.populateField("oldPassword","Password1");
		homePage.populateField("newPassword","Password");
		homePage.clickToSubmit("savePassword",5000);
		homePage.verifyFieldExistence("passwordDynamicValidationNumber",true);
		homePage.click("cancelPassword") ;
			
		// Validate Numeric characters for new password
		homePage.populateField("oldPassword","Password1");
		homePage.populateField("newPassword","123456789012");
		homePage.clickToSubmit("savePassword",5000);
		homePage.verifyFieldExistence("passwordDynamicValidationLetter",true);
		homePage.clickToSubmit("cancelPassword",5000);		
		
		// Validate new password and confirm password mismatch
		homePage.populateField("oldPassword","Password1");
		homePage.populateField("newPassword","Password");
		homePage.populateField("confirmNewPassword","Passw");
		homePage.click("oldPassword");
		homePage.verifyErrorCheckFieldOnly("confirmNewPassword",false);
		
		Util.printInfo("Finished Password Change validations");					
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
