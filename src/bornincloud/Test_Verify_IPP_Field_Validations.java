package bornincloud;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_IPP_Field_Validations extends BornInCloudTestBase{

	String UserID="";
 	String offerings="";
 	String country="";
 	String expectedHeader="";
 	String plan="";
 	String editionValue="";	
	public Test_Verify_IPP_Field_Validations() throws IOException {
		super("browser");						
	}
	
	@Before
	public void setUp() throws Exception {
		if(getEnvironment().equalsIgnoreCase("dev")){
			UserID=testProperties.getConstant("userId_DEV");
			offerings=testProperties.getConstant("offeringId_DEV");
			country=testProperties.getConstant("country_DEV");
			plan=testProperties.getConstant("plan_DEV");
			editionValue=testProperties.getConstant("editionValue_DEV");
			expectedHeader=testProperties.getConstant("expectedHeader_DEV");
		}else{
			UserID=testProperties.getConstant("userId_STG");
			offerings=testProperties.getConstant("offeringId_STG");
			country=testProperties.getConstant("country_STG");
			plan=testProperties.getConstant("plan_STG");
			editionValue=testProperties.getConstant("editionValue_STG");
			expectedHeader=testProperties.getConstant("expectedHeader_STG");
		}
		String url=getBaseURL() + "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings ;
		launchIPP(url);		
		Util.sleep(4000);
	}
	
	
	@Test
	public void Test_VerifyIPPFrameWork() throws Exception {
		String actualHeader=homePage.getValueFromGUI("headerINIPPHomePage");
		System.out.println(actualHeader);
		assertTrueCatchException("Expected Header is displayed after launching the IPP. Expected: "+expectedHeader+" Actual: "+actualHeader,actualHeader.equals(expectedHeader));
		assertTrueCatchException("Continue button is disabled after launching the IPP",homePage.checkIfElementExistsInPage("disabledContinueButton", 10));
		Util.printInfo("Clicking on 'Register for Non-Commercial Use' link.");
		homePage.click("registerNonUserLink");
		Util.printInfo("Validating whether submit button is disabled after navigating to 'Register For Non-Commercial' Page");
		assertTrueCatchException("Submit button is disabled after launching the IPP",homePage.checkIfElementExistsInPage("disabledSubmitButton", 10));
		if(!homePage.isChecked("iAcceptCheckBox")){
			assertTrueCatchException("Check box is unchecked after navigating to 'Register For Non-Commercial' Page",true);
		}else{
			assertTrueCatchException("Check box is unchecked after navigating to 'Register For Non-Commercial' Page",false);
		}
		String actualUserOptionSelected=homePage.getValueFromGUI("selectedUserByDefault");
		System.out.println(actualUserOptionSelected);
		if(actualUserOptionSelected.contains("Student")){
			assertTrueCatchException("Student is selected as default after navigating to 'Register For Non-Commercial' Page",true);
		}else{
			assertTrueCatchException("Student is selected as default after navigating to 'Register For Non-Commercial' Page",false);
		}
		Util.printInfo("Select accept terms and conditions. i.e click on I accept check box and validate whether continue button is enabled");
		homePage.check("iAcceptCheckBox");
		assertTrueCatchException("Continue button should be enabled after use accepts terms and conditions",homePage.checkIfElementExistsInPage("enabledSubmitButton", 10));
		Util.printInfo("Clicking on 'Register For Non-Commercial' link to go back to home page");
		homePage.click("registerNonUserLink");
		validateHover();
		String selectEditionXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectBasicOrAdvanceOption", editionValue);
		Util.printInfo("Select Edition as "+editionValue );
		homePage.click(selectEditionXpath);
		Util.printInfo("Validating whether continue button is enabled after selecting Edition: "+editionValue);
		assertTrueCatchException("Continue button should be enabled after use accepts terms and conditions",homePage.checkIfElementExistsInPage("enabledContinueButton", 10));
		Util.printInfo("Click on Continue button");
		homePage.click("enabledContinueButton");
		homePage.waitForFieldVisible("editionValue", 10);
		assertTrueCatchException("Edition value is displayed as expected in 'Select your plan' page. Expected: "+editionValue ,homePage.getValueFromGUI("editionValue").equalsIgnoreCase(editionValue));
		assertTrueCatchException("Plan value is displayed as expected in 'Select your plan' page before selecting plan. Expected: null" ,homePage.getValueFromGUI("planTitleDisplayed").equalsIgnoreCase(""));
		assertTrueCatchException("continue button is disabled before selecting the plan",homePage.checkIfElementExistsInPage("disabledContinueButtonOnSelectPlanPage", 10));
		String selectPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectPlan", plan);
		Util.printInfo("selecting plan "+plan);
		homePage.click(selectPlanXpath);
		assertTrueCatchException("Plan value is displayed as expected in 'Select your plan' page after selecting plan. Expected: "+plan ,homePage.getValueFromGUI("planTitleDisplayed").equalsIgnoreCase(plan));
		String currencyPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("planCurrency", plan);
		String expectedCurrencyValue=homePage.getValueFromGUI(currencyPlanXpath);
		assertTrueCatchException("Plan Currency value is displayed as expected in 'Select your plan' page after selecting plan. Expected: "+expectedCurrencyValue ,homePage.getValueFromGUI("planPriceDisplayed").contains(expectedCurrencyValue));
		assertTrueCatchException("continue button is enabled after selecting the plan",homePage.checkIfElementExistsInPage("enabledContinueButtonOnSelectPlanPage", 10));
		
	
	}
	
	public void validateHover(){
		Util.printInfo("Need to find way to validate Hover. Work in Progress");
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
