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


public class Test_Verify_Fake_Credit_Card_Details extends BornInCloudTestBase{

	 	
	public Test_Verify_Fake_Credit_Card_Details() throws IOException {
		super("browser");						
	}
	
	@Before
	public void setUp() throws Exception {
	//URL Modification of "language" to "lang" as per Ranjitha's Comments
		String USERNAME = null;
		String COUNTRY = null;
		String LANGUAGE = null;
		String currencyType = null;	
		String url = null;
							
		if(getEnvironment().equalsIgnoreCase("DEV")){
			 USERNAME = testProperties.getConstant("USER_ID");
			 COUNTRY = testProperties.getConstant("COUNTRY");
			 LANGUAGE = testProperties.getConstant("LANGUAGE");
			 currencyType=testProperties.getConstant("currencyType");
			 url=getBaseURL()+"userId="+ USERNAME+"&country="+ testProperties.getConstant("COUNTRY")+"&lang="+ testProperties.getConstant("LANGUAGE");	
			}
		
			else if(getEnvironment().equalsIgnoreCase("STG")){
				 USERNAME = testProperties.getConstant("USER_ID_STG");			 
				 COUNTRY= testProperties.getConstant("COUNTRY_STG");
				 LANGUAGE = testProperties.getConstant("LANGUAGE_STG");
				 currencyType=testProperties.getConstant("currencyType_STG");
				 String hash = getclicHash(USERNAME, COUNTRY ,LANGUAGE);
				 url=getBaseURL()+"userId="+ USERNAME+"&country="+ testProperties.getConstant("COUNTRY")+"&lang="+ testProperties.getConstant("LANGUAGE") + hash ;				 
			}
		
		
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
		
		//Populating Fields without validation Billing Page ( Handled in Negative Scenario Test Case)
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

		
		
		//***********Validate Continue button on Billing Information Page********** 
		
		
		
		if(homePage.checkIfElementExistsInPage("ContinueEnable",30) == true){
			
				System.out.println("Checking IF Block when condition is True");
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
				
	   //*********Redirecting to Order Summary Page*************** 			
				
	
				if(homePage.checkIfElementExistsInPage("submitOrder",30) ==true){
					homePage.click("submitOrder");
					Util.printInfo("Sucessfully Clicked on submit order button in Order summary page");
					Util.sleep(10000);
				}
				
				else{
					Util.sleep(15000);
					if(homePage.checkIfElementExistsInPage("submitOrder",30) ==false){
						EISTestBase.fail("Fatal error: Submit Order Button is not displayed in Order Summary Page");
					}
						
						else{
							homePage.click("submitOrder");
							Util.printInfo("Sucessfully Clicked on submit order button in Order summary page After wait statement");
							Util.sleep(10000);
						
							
						}
					
				}
		
		
			
	//*************Validate Change Card Option Page is Displayed *******************************
				
				
			Util.printInfo("Validating Whether CreditCard re-enter page is displayed");
				
				
				
				if(homePage.checkIfElementExistsInPage("ErrorMsgCC",30) ==true){
					assertTrueCatchException("Change Payment Option page is displayed sucessfully.",homePage.checkIfElementExistsInPage("ErrorMsgCC",30));
					Util.printInfo("Error message for Credit Card is Displayed");			
					}
				else{
						Util.sleep(10000);
						if(homePage.checkIfElementExistsInPage("ErrorMsgCC",30) ==false){
							EISTestBase.fail("Validation error message in the left pane FAILED");	
						}
						else{
							Util.printInfo("Error message for Credit Card is Displayed after wait statement");						
							}
				}
		
		///Change Payment options Verification
		Util.printInfo("Change Payment options Verification");
		Util.sleep(3000);
		homePage.click("ChangeOption");
		
		//Looping through all the options  the list box
		homePage.populateField("SelectPayOption",testProperties.getConstant("SelectDiscover"));
		Util.printInfo("Validating 'Discover' in Test Method");
		ValidatePaySelected(2);
		Util.sleep(3000);
		homePage.populateField("SelectPayOption",testProperties.getConstant("SelectAmericanExpress"));
		Util.printInfo("Validating AmericanExpress in Test Method");
		ValidatePaySelected(2);
		Util.sleep(3000);
		homePage.populateField("SelectPayOption",testProperties.getConstant("SelectVisa"));
		Util.printInfo("Validating Visa in Test Method");
		ValidatePaySelected(1);
		homePage.populateField("SelectPayOption",testProperties.getConstant("SelectPayPal"));
		Util.printInfo("Validating PayPal in Test Method");
		
	}
	
	
		
	public void assertTrueCatchException(String message , boolean expected){
		try{
			assertTrue(message,expected);
		}catch(Exception e){
			Util.printInfo(message+"--FAILED");
		}
	}
	


public void ValidatePaySelected(int Scase) throws MetadataException {

	
	
	//*******************************Non Visa Card Validation*********************************
if (Scase == 1){
	/*
	 * Method is called for Visa Card verification
	 */
    			Util.printInfo("After Visa  SelectPayPal");
					
				//populate value and Verify assert statements in UserName section of Non Visa Card 
	
				
				homePage.populateField("nameFieldOnReEnterCardInfo",testProperties.getConstant("nameFieldOnReEnterCardInfo1"));	
				assertFalse("Error message is Displayed for NAME fields  Visa Card",homePage.isFieldVisible("cvvErr"));
				
				//Assert Card details >
				
	            homePage.populateField("CardFieldOnReEnterCardInfo",testProperties.getConstant("CardFieldOnReEnterCardInfo1"));
	            assertFalse("Error message is  displayed Credit Card Details of Visa Card",homePage.isFieldVisible("CardErr"));
				
					//populate value and Verify assert statements in Month section of Non Visa Card>
			
					
				homePage.populateField("monthFieldOnReEnterCardInfo",testProperties.getConstant("monthFieldOnReEnterCardInfo1"));
				
				homePage.populateField("yearFieldOnReEnterCardInfo",testProperties.getConstant("yearFieldOnReEnterCardInfo1"));
				assertFalse("Error message is NoT displayed for Date listbox of Visa Card",homePage.isFieldVisible("MMYYErr"));
				
					//populate value and Verify assert statements in CVV section of Non Visa Card  
		
				
				homePage.populateField("securitCodeFieldOnReEnterCardInfo",testProperties.getConstant("securitCodeFieldOnReEnterCardInfo1"));		
				assertFalse("Error message is Displayed for CVV of Visa Card",homePage.isFieldVisible("cvvErr"));

				
				
				
				
			//Validating for Submit button Enabled in Change Pay option page for VISA CARD
				
	if(homePage.checkFieldExistence("ChangePageSubmitEnable")==true){
						homePage.click("ChangePageSubmitEnable");
					}
		else{Util.sleep(15000);
				if(homePage.checkFieldExistence("ChangePageSubmitEnable")==false){
					assertTrueCatchException("Submit Button is Enabled for Visa Card Option",homePage.checkIfElementExistsInPage("ChangePageSubmitEnable",30));
					EISTestBase.fail("Fatal Error: Submit Button is not Enable when all the fields are populated with correct data");	
				}
				else{
					homePage.click("ChangePageSubmitEnable");
				}
				}
								
				Util.printInfo("Successfully   Validated all the fields in the Change option ");
}

//*******************************Non Visa Card Validation*********************************
else if (Scase ==2){

	assertTrueCatchException("Submit Button is disabled non Visa Card",homePage.isFieldVisible("ChangePageSubmitDisable"));
	//populate value and Verify assert statements in card section of Non Visa Card
	
	homePage.populateField("CardFieldOnReEnterCardInfo","4");
	Util.sleep(2000);
	homePage.populateField("monthFieldOnReEnterCardInfo","04");
	assertTrueCatchException("1Error message is  displayed Credit Card Details of NON-Visa Card",homePage.isFieldVisible("CardErr"));
	homePage.populateField("CardFieldOnReEnterCardInfo",testProperties.getConstant("CardFieldOnReEnterCardInfo1"));
	assertFalse("2Error message is  displayed for Credit Card Details NON-Visa Card",homePage.isFieldVisible("CardErr"));
	
	Util.sleep(2000);	
	//populate value and Verify assert statements in Month section of Non Visa Card
	homePage.populateField("monthFieldOnReEnterCardInfo"," ");
	homePage.populateField("nameFieldOnReEnterCardInfo","");
	homePage.populateField("yearFieldOnReEnterCardInfo"," ");
	//assertTrueCatchException("1Error message is  displayed for Month of NON-Visa Card",homePage.isFieldVisible("MMYYErr"));		
	homePage.populateField("monthFieldOnReEnterCardInfo",testProperties.getConstant("monthFieldOnReEnterCardInfo1"));
	//assertTrueCatchException("2Error message is  displayed for Year field after Month for NON-Visa Card",homePage.isFieldVisible("MMYYErr"));	
	homePage.populateField("yearFieldOnReEnterCardInfo",testProperties.getConstant("yearFieldOnReEnterCardInfo1"));
	//assertFalse("3Error message is NoT displayed for Date listbox of NON-Visa Card",homePage.isFieldVisible("MMYYErr"));
	
		//populate value and Verify assert statements in CVV section of Non Visa Card  
	homePage.populateField("securitCodeFieldOnReEnterCardInfo"," ");
	homePage.populateField("nameFieldOnReEnterCardInfo"," ");
	assertTrueCatchException("1Error message is Displayed for CVV of non Visa Card",homePage.isFieldVisible("cvvErr"));
	homePage.populateField("securitCodeFieldOnReEnterCardInfo",testProperties.getConstant("securitCodeFieldOnReEnterCardInfo1"));		
	//assertTrueCatchException("Error message is Displayed for CVV of non Visa Card",homePage.isFieldVisible("cvvErr"));
	
	
	//populate value and Verify assert statements in UserName section of Non Visa Card 
	
	homePage.populateField("nameFieldOnReEnterCardInfo","May");	
	homePage.populateField("securitCodeFieldOnReEnterCardInfo",testProperties.getConstant("securitCodeFieldOnReEnterCardInfo1"));	
	Util.sleep(2000);
	assertTrueCatchException("1Error message is Displayed for NAME.FN fields non Visa Card",homePage.isFieldVisible("NameErr"));
	homePage.populateField("nameFieldOnReEnterCardInfo",testProperties.getConstant("nameFieldOnReEnterCardInfo1"));	
	homePage.populateField("securitCodeFieldOnReEnterCardInfo","123");
	Util.sleep(2000);
	assertFalse("Error message is Displayed for NAME.LN2 fields non Visa Card",homePage.isFieldVisible("NameErr"));

	Util.sleep(3000);
				
	

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
