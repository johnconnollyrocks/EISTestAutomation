/* Use case : US1406

·         Access the IPP redirect url
·         Select a purchase option and click Buy
·         Fill out Billing page with a fake credit card (4444333322221111) and click onSubmit
·         Click on Submit Order
·         Validate the error message in the left pane
·         Validate the client side validation for all the fields on the Retry page
·         Enter another fake credit card (4111111111111111), fill out the rest of the form and click on Submit
·         Validate error message in the left pane 

Arguments : 
-DappDir=bornincloud 
-Denvironment=DEV 
-Dtestdatafile=Test_Rest_DEV.json
-DtestName=Test_Verify_Fake_Credit_Card_Details
-DtestPropertiesFilenameKey=TEST_VERIFY_FAKE_CREDIT_CARD_DETAILS_FILE

*/


package bornincloud;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_Pay_Edit extends BornInCloudTestBase{

	 	
	public Test_Verify_Pay_Edit() throws IOException {
		super("browser");						
	}
	
	@Before
	public void setUp() throws Exception {
	//URL Modification of "language" to "lang" as per Ranjitha's Comments
		String url=getBaseURL()+"userId="+ testProperties.getConstant("USER_ID")+"&country="+ testProperties.getConstant("COUNTRY")+"&lang="+ testProperties.getConstant("LANGUAGE");
		launchIPP(url);
	}
	
	
	@Test
	public void Test_VerifyIPPFrameWork() throws Exception {
		
		assertTrueCatchException("Buy IT button is disabled on launch ",homePage.checkIfElementExistsInPage("disableBuyItButton", 10));
		homePage.click("AnnualPlanButton");
		
		assertTrueCatchException("Buy IT button is enabled after selcting the plan ",homePage.checkIfElementExistsInPage("enableBuyItButton", 10));
		homePage.click("enableBuyItButton");
			
		homePage.waitForField("NameText", true, 10);
		
		if (homePage.checkIfElementExistsInPage("NameText", 10)){
			assertTrueCatchException("Sucessfully navigated to payment information page after clicking on Buy it. ",homePage.checkIfElementExistsInPage("NameText", 10));
		}else{
			EISTestBase.fail(" payment information page is not found after clicking on Buy it button");
				
		}
		
		Util.sleep(5000);
		
		//Enter valid data and place the order
				homePage.populateField("NameText","");
				homePage.populateField("NameText",testProperties.getConstant("NameText"));
				
				homePage.populateField("CardNumberText","");
				homePage.populateField("CardNumberText",testProperties.getConstant("CardNumberText"));
				
				homePage.populateField("SelectExpMonth","");
				homePage.populateField("SelectExpMonth",testProperties.getConstant("SelectExpMonth"));
				
				homePage.populateField("SelectExpYear"," ");
				homePage.populateField("SelectExpYear",testProperties.getConstant("SelectExpYear"));
				
				homePage.populateField("SecurityCodeText","");
				homePage.populateField("SecurityCodeText",testProperties.getConstant("SecurityCodeText"));
				
				homePage.populateField("BillingAddressText","");
				homePage.populateField("BillingAddressText",testProperties.getConstant("BillingAddressText"));
				
				homePage.populateField("cityText","");
				homePage.populateField("cityText",testProperties.getConstant("cityText"));
				
				homePage.populateField("SelectProvince"," ");
				homePage.populateField("SelectProvince",testProperties.getConstant("SelectProvince"));
				
				homePage.populateField("ZipCodeText","");
				homePage.populateField("ZipCodeText",testProperties.getConstant("ZipCodeText"));
				
				homePage.check("autoRenewal");
				Util.sleep(5000);
				
				homePage.click("ContinueEnable"); 
				
				
		
	 
		
	
		//****************PayEdit in Order summary page***************
		
		
		
		Util.sleep(5000);
		
		
		if(homePage.checkIfElementExistsInPage("PayEdit",30) ==true){
			homePage.click("PayEdit");
			Util.printInfo("Sucessfully Clicked on PayEdit");
			Util.sleep(5000);
			BillInfoPopulate();
		}
		
		else{
			Util.sleep(15000);
			if(homePage.checkIfElementExistsInPage("PayEdit",30) ==false){
				EISTestBase.fail("Fatal error: Submit Order Button is not displayed in Order Summary Page");
			}
				
				else{
					homePage.click("PayEdit");
					Util.printInfo("Sucessfully Clicked on PayEdit");
					Util.sleep(5000);
					BillInfoPopulate();
				
					
				}
			
		}
		
		
		
		
		
		
	}
	
	
		
	public void assertTrueCatchException(String message , boolean expected){
		try{
			assertTrue(message,expected);
		}catch(Exception e){
			Util.printInfo(message+"--FAILED");
		}
	}
	
	public void BillInfoPopulate() throws MetadataException{
		
		//Instruction to insert data in the fields ??
		
	//homePage.getAttribute(fieldName, attributeName)
		
				String customerName = homePage.getAttribute("NameText", "value");
				
				//Instruction to Evaluate to values ??
				
				compareStrings("John Mars", customerName);
				//***********************************************************************
				
				String cardNumber = homePage.getAttribute("CardNumberText", "value");
				
						compareStrings("4444333322221111", cardNumber);
				//***********************************************************************
				String expiDateMonth = homePage.getAttribute("SelectExpMonth", "value");
				
				compareStrings("04", expiDateMonth);
				//***********************************************************************
				String expYear =  homePage.getAttribute("SelectExpYear", "value");
				
				compareStrings("2015", expYear);
				//***********************************************************************
				String securityCode = homePage.getAttribute("SecurityCodeText", "value");
				
				compareStrings("123", securityCode);
				//***********************************************************************
				String billingAdress = homePage.getAttribute("BillingAddressText", "value");
				
				compareStrings("Invalid #43,Cross,7th 1sr-phase.", billingAdress);
				//***********************************************************************
				String city = homePage.getAttribute("cityText", "value");
				
				compareStrings("QueBeC", city);
				//***********************************************************************
				String state = homePage.getAttribute("SelectProvince", "value");
				
				compareStrings("California", state);
				//***********************************************************************
				String zip = homePage.getAttribute("ZipCodeText", "value");
				
				compareStrings("94444", zip);
				//***********************************************************************
				
				homePage.check("autoRenewalCheckBox");
				
				Util.sleep(5000);
		
		
		
		

		//***********Validate Continue button on Billing Information Page********** 
		
		Util.printInfo("Validation whether continue button is Enabled");
		
		if(homePage.checkIfElementExistsInPage("ContinueEnable",30) == true){
			
				homePage.click("ContinueEnable");  
				Util.sleep(10000);
		}
		else{
			Util.sleep(15000);
			 if(homePage.checkIfElementExistsInPage("ContinueEnable",30) == false){
			    	
			    	EISTestBase.fail("Fatal Error: Continue Button is not Enabled in the Billing Page");				
					
				}
			 else{
				 System.out.println("Checking Else Block when condition is True");
				 homePage.click("ContinueEnable");  
					Util.sleep(15000);
			 }
					
		}
		
		
	}
	
	//********************************************************************
	
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
