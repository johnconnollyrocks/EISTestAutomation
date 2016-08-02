package scat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Util;

/**
 * Test class - TestAuthentication
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestAuthentication1 extends SCATTestBase {
	public TestAuthentication1() {
		super("Browser" , "chrome");
	}

	@Before
	public void setUp() throws Exception {

		launchSCAT(SCATConstants.BASE_URL1);
	}

	@Test
	public void TEST_Login() throws Exception {

		// Method used to login to the application
		Util.printInfo("Opened the url : " + SCATConstants.BASE_URL1);
		Util.printInfo("Clicking on Contact Gateway in the SubCtr Admin screen");
		
		createSubCntrAdminHomePage.clickAndWait("contractGatewayLink", "securityGatewayLink");
		
		Util.printInfo("Clicking on Security Gateway in the contract gateway screen");
		createSubCntrAdminHomePage.click("securityGatewayLink");
		
		Util.printInfo("Entering the user id : " + testProperties.getConstant("LOGIN_NAME") +" into the user id field");
		
		createSubCntrSecGatewayAdminPage.populate();
		
		Util.printInfo("Clicking on send button");
		createSubCntrSecGatewayAdminPage.click("sendButton");
		
		if(!createSubCntrSecGatewayAdminPage.isFieldPresent("errorResponse")) {
			Util.printInfo("There is no error response shown. Printing the valid response showed on the web page");
			String guidResponse = createSubCntrSecGatewayAdminPage.createFieldWithParsedFieldLocatorsTokens(SCATConstants.GUID_RESPONSE, testProperties.getConstant("LOGIN_NAME"),true);
			String findbyLoginIDResponse = createSubCntrSecGatewayAdminPage.getValueFromGUI(guidResponse);
			Util.printInfo("The response for the findbyloginid with login id : " +testProperties.getConstant("LOGIN_NAME") + " is : " + findbyLoginIDResponse);
			String guidFromResponse = createSubCntrSecGatewayAdminPage.getValueFromGUI(guidResponse).split("UserId=")[1].split(",")[0];
			Util.printInfo("Enterprise GUID obtained from the response : " + guidFromResponse);
			
			Util.printInfo("Navigating back to the home page");
			driver.navigate().to(SCATConstants.BASE_URL5);
			
			Util.printInfo("Clicking on Contact Gateway in the SubCtr Admin screen");			
			createSubCntrAdminHomePage.clickAndWait("contractGatewayLink", "securityGatewayLink");
			
			Util.printInfo("Clicking on contact Gateway in the contract gateway screen");
			createSubCntrAdminHomePage.click("contactGatewayLinkTwo");
			
			Util.printInfo("Entering the Enterprise GUID obtained in the GUID Field in Contact Gateway and validating the response");
			createSubCntrSecGatewayAdminPage.populateField("enterpriseGUID",guidFromResponse);
			
			createSubCntrSecGatewayAdminPage.click("sendInContactGateway");
			
			if(!createSubCntrSecGatewayAdminPage.isFieldPresent("errorResponse")) {
				Util.printInfo("Validating the Enterprise User ID Details");
				String enterpriseGuidResponse = createSubCntrSecGatewayAdminPage.getValueFromGUI("enterpriseGUIDResponse");
				String enterpriseUserIDResponse =  enterpriseGuidResponse.split("ADSK EIDMUserID=")[1].split(",password=")[0];
				if(enterpriseUserIDResponse.equalsIgnoreCase(testProperties.getConstant("LOGIN_NAME"))){
					EISTestBase.assertEquals(enterpriseUserIDResponse, testProperties.getConstant("LOGIN_NAME"));
				}else{
					EISTestBase.fail("The login id is not available in the guid response : " + "Data used : " + "GUID : " + guidFromResponse + " Login name : " +testProperties.getConstant("LOGIN_NAME"));
				}
				
			}else{
				EISTestBase.fail("Error Response observed : " + createSubCntrSecGatewayAdminPage.getValueFromGUI("errorResponse"));
			}
			
			
		}else {
			EISTestBase.fail("Error Response observer : " + createSubCntrSecGatewayAdminPage.getValueFromGUI("errorResponse"));
		}
		// Method used to logout to the application
		

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
