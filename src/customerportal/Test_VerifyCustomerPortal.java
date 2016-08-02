package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_VerifyCustomerPortal
 * 
 * @author Ravi Shankar
 * @version 2.0.0
 */
public final class Test_VerifyCustomerPortal extends CustomerPortalTestBase {
	public Test_VerifyCustomerPortal() throws IOException {
		super("Browser", getAppBrowser());
		// super();

	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyCustomerPortal() throws Exception {
		String cmPassword = "Password1";
		boolean changePassword = false;
		boolean resetPassword = false;
		boolean isPasswordReset = false;
		String contractType = null;
		String contractDesc = null;
		int cloudCredits;
		String cloudCreditstobedisplayed;
		String[] serialNumbers;
		String[] services;
		
		changePassword = loginAsNewUserAndCheckPassword(testProperties.getConstant("USER_NAME"), cmPassword);

		if (changePassword) {
			launchMyAutodeskPortal(CustomerPortalConstants.EMAIL_URL_STG);
			Util.printInfo("Logging into SST mailbox to get the username and password for the new CM");
			loginToSSTMail("itqa@ssttest.net", "Password1");
			cmPassword = searchForCMWelcomeKit(testProperties.getConstant("USER_NAME"));
			logoutOfSSTMail();
			Util.sleep(1000);

			if (!cmPassword.equalsIgnoreCase("Not yet set")) {
				switch (getEnvironment().trim().toUpperCase()) {
				case "DEV": {
					launchMyAutodeskPortal(CustomerPortalConstants.BASE_URL_DEV);
				}
				case "STG":
				default: {
					launchMyAutodeskPortal(CustomerPortalConstants.BASE_URL_STG);
				}
					loginAsMyAutodeskPortalUserAndChangePassword(testProperties.getConstant("USER_NAME"), cmPassword);
					logoutMyAutodeskPortal();
				}
			}

			if (cmPassword.equalsIgnoreCase("Not yet set")) {
				Util.printInfo("Trying to send the forgot password email from customer portal to the mail id so that the password can be reset to Password1");
				launchMyAutodeskPortal(CustomerPortalConstants.BASE_URL_STG);
				resetPassword = resetPasswordfromCustomerPortal(testProperties.getConstant("USER_NAME"));
				if (resetPassword) {
					Util.printInfo("Logging into the sst mail box to get the link of reset password and change the password to password1");
					driver.close();
					ReLaunchtheDriver(getAppBrowser());
					launchMyAutodeskPortal(CustomerPortalConstants.EMAIL_URL_STG);
					loginToSSTMail("itqa@ssttest.net", "Password1");
					isPasswordReset = searchForPasswordResetEmailAndResetPassword(testProperties.getConstant("USER_NAME"),"Password1");
				}
			}

		}

		if (changePassword) {
			driver.close();
			ReLaunchtheDriver(getAppBrowser());
			launchMyAutodeskPortal(CustomerPortalConstants.BASE_URL_STG);
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME"),testProperties.getConstant("PASSWORD"));
			
		}

		String parsedFieldName = null;
		String actualValue = null;
		
		

			if (testProperties.getConstant("VALIDATION").equalsIgnoreCase("OVERALL")) {
				CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
				loginPage = customerPortal.getLoginPage();
				homePage = customerPortal.getHomePage();
				Util.sleep(35000);
				serialNumbers = testProperties.getConstant("SERIAL_NO").split(";");
				for (String serialNum : serialNumbers) {
	
						Util.printInfo("Clicking on the contract toggle drawer button");
//						parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.CONTRACT_TOGGLE_BUTTON,serialNum, true);
						parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.CONTRACT_TOGGLE_BUTTON,testProperties.getConstant("CONTRACT_NO"),true);
					if (homePage.isFieldPresent(parsedFieldName)){
							homePage.click(parsedFieldName);
							Util.sleep(3000);
//						if (testProperties.getConstant("CONTRACT_TYPE").contains(";")){
//							contractDesc = testProperties.getConstant("CONTRACT_TYPE").split(";")[0];
//						}else {
//							contractDesc = testProperties.getConstant("CONTRACT_TYPE").trim();
//						}
//						
//						Util.printInfo("Verifying the Contract type");
//						if (contractDesc.equalsIgnoreCase("Quarterly")) {
//							contractType = contractDesc.trim().concat(" (Renewal)");
//						} else if (contractDesc.equalsIgnoreCase("Monthly")) {
//							contractType = contractDesc.trim().concat(" (Recurring)");
//						} else if (contractDesc.equalsIgnoreCase("Annual")) {
//							contractType = contractDesc.trim().concat(" (Renewal)");
//						} else {
//							contractType = contractDesc.trim();
//						}
						
//						parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.CONTRACT_INFORMATION,serialNum, true);
//						actualValue = homePage.getValueFromGUI(parsedFieldName);
//						EISTestBase.assertEquals(actualValue, contractType);
			
						// Util.printInfo("Verifying the Product Line");
						// parsedFieldName =
						// homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.CONTRACT_PRODUCT_LINE,
						// testProperties.getConstant("SERIAL_NO"), true);
						// actualValue= homePage.getValueFromGUI(parsedFieldName);
						// EISTestBase.assertEquals(actualValue,
						// testProperties.getConstant("PRODUCT_LINE"));
			
//						Util.printInfo("Verifying the number of seats");
//						parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.CONTRACT_SEATS,testProperties.getConstant("CONTRACT_NO"), true);
//						actualValue = homePage.getValueFromGUI(parsedFieldName);
//						EISTestBase.assertEquals(actualValue,testProperties.getConstant("SEATS"));
						
						Util.printInfo("Verifying the number of users assigned");
						Util.printInfo("Commented the code on 02/02/2014 as users assigned on the contract is not showing up on the products and services page");
//						parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.CONTRACT_USERS,serialNum, true);
////						if (homePage.isFieldPresent(parsedFieldName)){
//							actualValue = homePage.getValueFromGUI(parsedFieldName);
//							EISTestBase.assertEquals(actualValue,testProperties.getConstant("USERS"));
//						}
					}
				}
				Util.printInfo("Click on Services under products and services");
				homePage.click("services");
				Util.sleep(10000);
	
				services = testProperties.getConstant("SERVICES").split(";");
				for (String service : services) {
					if (!service.equalsIgnoreCase("Desktop Access") && !service.equalsIgnoreCase("Character Generator") && !service.equalsIgnoreCase("Autodesk® Flow Design") && !service.equalsIgnoreCase("storage")) {
						
						Util.printInfo("Verifying the correct services are populated or not");
						if(service.contains("\\u2122")) {
							service=service.replace("\\u2122", "");
						}
						if(service.equalsIgnoreCase("Storage")){
							service=service.toLowerCase();
							}
						if (service.contains(":Desktop Access")){
							service=service.replace(":Desktop Access", "");
							}
							parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.CONTRACT_SERVICES,service, true);
							homePage.verifyFieldExists(parsedFieldName);
					}
	
				}
	
				Util.printInfo("clicking on Reporting");
				homePage.clickAndWait("reporting", "usageReport");
				Util.sleep(25000);
				
				cloudCredits = Integer.parseInt(testProperties.getConstant("CLOUD_CREDITS"));			
				if (cloudCredits == 0) {
					Util.printInfo("Checking for the existence of  No Cloud Credits Purchased message for contract with cloud credits Zero");
					parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.NO_CLOUD_CREDITS_PURCHASED_MESSAGE,testProperties.getConstant("CONTRACT_NO"), true);
					EISTestBase.assertEquals(homePage.isFieldPresent(parsedFieldName),true);			
				}
				else if (testProperties.getConstant("CLOUD_CREDITS").isEmpty()) {
					Util.printInfo("Checking for the existence of  No Cloud Credits Purchased message for contract with cloud credits Zero");
					parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.NO_CLOUD_CREDITS_PURCHASED_MESSAGE,testProperties.getConstant("CONTRACT_NO"), true);
					EISTestBase.assertEquals(homePage.isFieldPresent(parsedFieldName),true);	
				}
				else if (cloudCredits > 0) {
					parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.USAGE_REPORT_AVAILABLE_CLOUD_CREDITS,testProperties.getConstant("CONTRACT_NO"), true);
					actualValue = homePage.getValueFromGUI(parsedFieldName);
					EISTestBase.assertEquals(actualValue,testProperties.getConstant("CLOUD_CREDITS"));
				}
	
				Util.printInfo("Click on Users Tab");
				homePage.clickAndWait("users", "addUsers");
				Util.sleep(30000);
	
				Util.printInfo("verifying whether the correct Contract Manager name is displayed under users tab or not");
				parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.USER_DETAIL,testProperties.getConstant("CM_NAME"), true);
				homePage.verifyFieldExists(parsedFieldName);
	
				homePage.click(parsedFieldName);
				Util.sleep(15000);
				Util.printInfo("verifying whether the appropriate contract id is associated to the user");
				parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.USER_DETAIL_CONTRACT_NO,testProperties.getConstant("CONTRACT_NO"), true);
				homePage.verifyFieldExists(parsedFieldName);
			}

		if (testProperties.getConstant("VALIDATION").equalsIgnoreCase("OVERALLOCATION")) {

			Util.printInfo("Clicking on users");
			homePage.clickAndWait("users", "addUsers");
			Util.sleep(30000);
			Util.printInfo("Verifying whether the seats overallocated message alert is present or not");
			homePage.verifyFieldExists("seatsOverAssignedMessage");

			// Util.printInfo("Go to reporting tab");
			// homePage.clickAndWait("reporting", "usageReport");
			// Util.printInfo("Verifying the remaining cloud credits");
			// parsedFieldName =
			// homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.USAGE_REPORT_AVAILABLE_CLOUD_CREDITS,
			// testProperties.getConstant("CONTRACT_NO"), true);
			// actualValue= homePage.getValueFromGUI(parsedFieldName);
			// EISTestBase.assertEquals(actualValue,
			// testProperties.getConstant("CLOUD_CREDITS"));

		}

		if (testProperties.getConstant("VALIDATION").equalsIgnoreCase("RENEWALNOTIFICATION")) {

			Util.printInfo("Clicking on reporting");
			homePage.clickAndWait("reporting", "usageReport");
			Util.sleep(25000);
			Util.printInfo("Verifying the renewal notification date");
			parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.RENEWAL_NOTIFICATION_DATE,testProperties.getConstant("CONTRACT_NO"), true);
			actualValue = homePage.getValueFromGUI(parsedFieldName);
			EISTestBase.assertEquals(actualValue,testProperties.getConstant("RENEWAL_NOTIFICATION_DATE"));
			//
			// Util.printInfo("Verifying the remaining cloud credits");
			// parsedFieldName =
			// homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.USAGE_REPORT_AVAILABLE_CLOUD_CREDITS,
			// testProperties.getConstant("CONTRACT_NO"), true);
			// actualValue= homePage.getValueFromGUI(parsedFieldName);
			// EISTestBase.assertEquals(actualValue,
			// testProperties.getConstant("CLOUD_CREDITS"));

		}

		if (testProperties.getConstant("VALIDATION").equalsIgnoreCase("CLOUDCREDITS_VALIDATION")) {

			Util.printInfo("clicking on Reporting");
			homePage.clickAndWait("reporting", "usageReport");
			Util.sleep(25000);
			
			cloudCredits = Integer.parseInt(testProperties.getConstant("CLOUD_CREDITS"));			
			if (cloudCredits == 0) {
				Util.printInfo("Checking for the existence of  No Cloud Credits Purchased message for contract with cloud credits Zero");
				parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.NO_CLOUD_CREDITS_PURCHASED_MESSAGE,testProperties.getConstant("CONTRACT_NO"), true);
				EISTestBase.assertEquals(true,homePage.isFieldPresent(parsedFieldName));			
			}
			else if (testProperties.getConstant("CLOUD_CREDITS").isEmpty()) {
				Util.printInfo("Checking for the existence of  No Cloud Credits Purchased message for contract with cloud credits Zero");
				parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.NO_CLOUD_CREDITS_PURCHASED_MESSAGE,testProperties.getConstant("CONTRACT_NO"), true);
				EISTestBase.assertEquals(true,homePage.isFieldPresent(parsedFieldName));			
			}
			else if (cloudCredits > 0) {
				parsedFieldName = homePage.createFieldWithParsedFieldLocatorsTokens(CustomerPortalConstants.USAGE_REPORT_AVAILABLE_CLOUD_CREDITS,testProperties.getConstant("CONTRACT_NO"), true);
				actualValue = homePage.getValueFromGUI(parsedFieldName);
				EISTestBase.assertEquals(actualValue,testProperties.getConstant("CLOUD_CREDITS"));
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
