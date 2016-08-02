package amazonrental;

import java.io.IOException;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;


public class Test_AmazonRental_instantaccess_OrderCreation extends
		AmazonRentalTestBase {

	public Test_AmazonRental_instantaccess_OrderCreation() throws IOException {
		super();

	}

	@Before
	public void setUp() throws Exception {
		launchamazonRental(getBaseURL());
	}

	@Test
	public void Test_AmazonRental_Instantaccess_AccountLinking()
			throws Exception {
		loginAmazon(getAutoUserName(), getAutoPassword());

		// account linking
		int customerID;
		String arrUserID[], accountLinkingTestReportResponse, UserID;
		customerID = getUniqueId();
		instantAccessPage.populateField("customerID",Integer.toString(customerID));
		System.out.println("Customer ID.. " + customerID +"\n");
		instantAccessPage.populateField("username",testProperties.getConstant("USER_NAME"));
		System.out.println("Username .." + testProperties.getConstant("USER_NAME") +"\n");
		instantAccessPage.populateField("email",testProperties.getConstant("EMAIL"));	
		System.out.println("Email ID.. " + testProperties.getConstant("EMAIL") +"\n");

		// Running  the account link Test
		instantAccessPage.click("accountLinkRunTest");
		
		// Checking for the account Linking Test Report 

		if (instantAccessPage.checkFieldExistence("accountLinkingTestReport")) {
			System.out.println("account Linking Test Report present.. click on report " +"\n");
			instantAccessPage.click("accountLinkingTestReport");
		}

		else {
			EISTestBase.fail("account Linking Test is not present server is down");
		}

		accountLinkingTestReportResponse = instantAccessPage.getValueFromGUI("accountLinkingTestReportResponse");
		
		// Validating the response 

		if (accountLinkingTestReportResponse.contains("OK")) {
			System.out.println("account Linking Test Report Response present");
		}

		else {
			EISTestBase.fail("account Linking Test response got error " + accountLinkingTestReportResponse);
		}

		// Getting the USERID from response 
		
		arrUserID = accountLinkingTestReportResponse.split("\"");
		UserID = arrUserID[arrUserID.length - 2];

		System.out.println("User id got generated " + UserID +"\n");

		//Subscription test

		int amazoncustomerID, subscriptionID, numberofattempts;
		String subscriptionTestReportResponse, subscriptionResponse;
		amazoncustomerID = getUniqueId();
		subscriptionID = getUniqueId();
		numberofattempts = 1;

		while (!(numberofattempts > 4)) {

			instantAccessPage.clickAndWait("subscriptionTestDropDown","TESTCASE");
			instantAccessPage.populateField("testcase",testProperties.getConstant("TESTCASE"));
			instantAccessPage.populateField("amazoncustomerID",Integer.toString(amazoncustomerID));
			instantAccessPage.populateField("subscriptionID",Integer.toString(subscriptionID));
			instantAccessPage.populateField("userID", UserID);
			instantAccessPage.populateField("productID",testProperties.getConstant("PRODUCTID"));
			
			// Running the subscription test
			
			instantAccessPage.click("subscriptionRunTest");
			
			// Checking for the subscription test Report

			if (instantAccessPage.checkFieldExistence("subscriptionTestReport")) {
				instantAccessPage.click("subscriptionTestReport");
				System.out.println("Subscription Activate Report is present ..clicking on report " +"\n");

				break;

			}

			else {
				System.out.println("Subscription Activate response is getting server down error trying one more time" +"\n");
				instantAccessPage.refresh();
				numberofattempts = numberofattempts + 1;

			}
		}

		if (instantAccessPage.checkFieldExistence("subscriptionTestReportResponse")) {
			instantAccessPage.click("subscriptionTestReportResponse");
			System.out.println("subscription  Linking Test Report present" +"\n");
		}

		else {
			EISTestBase.fail("subscription activation response is not present after 4 attempts.. server is down");
		}
		
		// validating the subscription activation response 

		subscriptionTestReportResponse = instantAccessPage.getValueFromGUI("subscriptionTestReportResponse");
		arrUserID = subscriptionTestReportResponse.split("\"");

		subscriptionResponse = arrUserID[arrUserID.length - 2];

		if (subscriptionResponse == "OK") {

			System.out.println("SubscriptionActivate is sucessfull for Subscription ID  " + subscriptionID +"\n");

		} else {
			System.out.println("Subscription Activation  is getting error " + subscriptionTestReportResponse +"\n");
		}
		
		//Validating Order sync in SFDC 
				
		launchSFDC(AmazonRentalConstants.BASE_URL_SFDC);
		 // Login to SFDC
		
		loginSFDC(testProperties.getConstant("SFDC_UserName"),testProperties.getConstant("SFDC_Password"));
		
		// Querying the subscription ID  
		
		sfdcPage.populateField("searchSubscription",Integer.toString(subscriptionID));
		sfdcPage.click("searchButton");
		Thread.sleep(3000);
		
		swithToIframeWhereMyFieldExist(sfdcPage,"assets");
		
		if(sfdcPage.checkFieldExistence("assets")){
			System.out.println("Query returns the data ....clicking on assets"+"\n");
			sfdcPage.click("assets");
		}
			else {
				EISTestBase.fail("Query returns empty results Subscription not sync to SFDC");
				
		}
		driver.switchTo().defaultContent();
		
		swithToIframeWhereMyFieldExist(sfdcPage,"productLineCode");
		
		//getting the data from SFDC 
		
		
		String productLinecode = sfdcPage.getValueFromGUI("productLineCode");
		
		System.out.println("product Line code is : "+productLinecode +"\n");
		
		//Clicking on Contract line items
		
		driver.switchTo().defaultContent();
		
		swithToIframeWhereMyFieldExist(sfdcPage,"contractLineItemLink");
		
		sfdcPage.click("contractLineItemLink");
		
		Thread.sleep(3000);
		
		//Getting values
		
		String agreementNumber=sfdcPage.getValueFromGUI("agreementNumber");
		System.out.println("agreement Number is:   "+agreementNumber +"\n");
		String serialNumber = sfdcPage.getValueFromGUI("serialNumber");
		System.out.println("serial Number is:  "+serialNumber +"\n");
			
		// verifying the asset status 
			
		String assetstatus = sfdcPage.getValueFromGUI("assetstatus");
		System.out.println("Assert : Checking asset status" + "\n");
		compareStrings("Registered", assetstatus);
			
		//verifying the end customer 
		String endCustomer = sfdcPage.getValueFromGUI("endCustomer");
		System.out.println("Assert : Checking end customer " + "\n");
		compareStrings("Amazon Rentals", endCustomer);
		
		String startDate = sfdcPage.getValueFromGUI("startDate");
		System.out.println("start date is :  "+startDate +"\n");
		String endDate = sfdcPage.getValueFromGUI("endDate");
		System.out.println("end date is :  "+endDate +"\n");
		
		//loging out SFDC 
		
		logoutAsSFDCUser(testProperties.getConstant("SFDC_UserName"));
		
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
