package bornincloud;



import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;



public class Test_Verify_NegativeCasesInPaymentInformationPage extends BornInCloudTestBase{
	
	
	
	public Test_Verify_NegativeCasesInPaymentInformationPage() throws IOException {
		super("browse");						
	}
	
	@Before
	public void setUp() throws Exception {

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
		//Select PayPal and Verify CC Field Not Selected
		homePage.click("payPalCheckBox");
		Util.printInfo("Verify PayPal Option Selected and CC Field Not Displayed");
		//assertTrueCatchException("PayPal Options Selected, CC Field Not Dispalyed",homePage.checkFieldExistence("CardNumberText"));
		homePage.clickAndWaitForFieldAbsent("payPalCheckBox", "CardNumberText", 5);
//		homePage.populateAllInstance("FILLDETAILS");
		Util.sleep(2000);
		homePage.click("creditCardCheckBox");
		//Validate Error Message When First & Last Name not Entered
		homePage.populateField("NameText"," ");
		
		homePage.populateField("BillingAddressText",testProperties.getConstant("BillingAddressText"));
		Util.sleep(3000);
		Util.printInfo("Validate Error Message When First & Last Name not Entered");
		assertTrueCatchException("Error message is displayed When First & Last name Not Entered",homePage.checkFieldExistence("errorMsgNameDisplayed"));
		
		//Validate Error Field When Last name Not Entered
		homePage.populateField("NameText","we");
		
		homePage.populateField("BillingAddressText",testProperties.getConstant("BillingAddressText"));
		
		Util.sleep(3000);
		Util.printInfo("Validate Error Field When Last name Not Entered");
		assertTrueCatchException("Error message is displayed When Last name Not Entered",homePage.checkFieldExistence("errorMsgNameDisplayed"));
		homePage.populateField("NameText",testProperties.getConstant("NameText"));
		
		//Verify Error Message When CC Not Entered
		homePage.populateField("CardNumberText"," ");
		homePage.populateField("SelectExpMonth",testProperties.getConstant("SelectExpMonth"));
		Util.sleep(3000);
		Util.printInfo("Verify Error Message When CC Not Entered");
		assertTrueCatchException("Error message is displayed When Card Number Not Entered",homePage.checkFieldExistence("errorMsgCardNumberDisplayed"));
		homePage.populateField("CardNumberText","ab");
		homePage.populateField("SecurityCodeText",testProperties.getConstant("SecurityCodeText"));
		Util.sleep(3000);
		Util.printInfo("Validate Alphabet Not Entered in CC");
		String CC = homePage.getValueFromGUI("CardNumberText");
		assertEqualsCatchException("",CC);
		Util.printInfo("Verify Error Message When CC Value is  Entered Alphabet ");
		assertTrueCatchException("Error message is displayed When CC Value is  Entered Alphabet",homePage.checkFieldExistence("errorMsgCardNumberDisplayed"));
		homePage.populateField("CardNumberText","4");
		//Verify Error Message When Month Not Selected
		
		homePage.populateField("SelectExpMonth","");
		homePage.populateField("BillingAddressText",testProperties.getConstant("BillingAddressText"));
		Util.sleep(3000);
		Util.printInfo("Validation Error message is displayed for Expiration Month Not Selected");
		assertTrueCatchException("Validation Error message is displayed When Expiration Month Not Selected",homePage.checkFieldExistence("errorMsgDateDisplayed"));
		//Verify Error Message When Year Not Selected
		homePage.populateField("SelectExpYear","");
		homePage.populateField("SelectExpMonth",testProperties.getConstant("SelectExpMonth"));
		
		
		Util.sleep(3000);
		Util.printInfo("Validation Error message is displayed When Expiration Year Not Selected");
		assertTrueCatchException("Validation Error message is displayed When Expiration Year Not Selected",homePage.checkFieldExistence("errorMsgDateDisplayed"));
		homePage.populateField("SelectExpMonth",testProperties.getConstant("SelectExpMonth"));
		homePage.populateField("SelectExpYear",testProperties.getConstant("SelectExpYear"));
		
		//Verify Error Message When SecurityCodeText Not Entered
		homePage.populateField("SecurityCodeText"," ");
		homePage.populateField("BillingAddressText",testProperties.getConstant("BillingAddressText"));
		Util.sleep(3000);
		Util.printInfo("Validation Error message is displayed When Security Code Not Entered");
		assertTrueCatchException("Validation Error message is displayed When Security Code Not Entered",homePage.checkFieldExistence("errorMsgSecurityCodeDisplayed"));
		homePage.populateField("SecurityCodeText",testProperties.getConstant("SecurityCodeText"));
		
		//Verify Error Message When Billing Address Not Entered
		homePage.populateField("BillingAddressText"," ");
		homePage.populateField("cityText",testProperties.getConstant("cityText"));
		Util.sleep(3000);
		Util.printInfo("Validation Error message is displayed When Billing address Not Entered");
		assertTrueCatchException("Validation Error message is displayed When Billing address Not Entered",homePage.checkFieldExistence("errorMsgBillingAddress"));
		homePage.populateField("BillingAddressText",testProperties.getConstant("BillingAddressText"));
		
		//Verify Error Message When City Not Entered
		homePage.populateField("cityText"," ");
		homePage.populateField("BillingAddressText",testProperties.getConstant("BillingAddressText"));
		Util.sleep(3000);
		Util.printInfo("Validation Error message is displayed When City Not Entered");
		assertTrueCatchException("Error message is displayed When City Value Not Entered",homePage.checkFieldExistence("errorMsgCityDisplayed"));
		homePage.populateField("cityText",testProperties.getConstant("cityText"));
		
		//Verify Error Message When State Not Entered
		homePage.populateField("SelectProvince"," ");
		homePage.check("autoRenewal");
		Util.sleep(3000);
		Util.printInfo("Verify Error Message is Displayed When State Not Entered");
		assertTrueCatchException("Error message is displayed for State When value Not Entered ",homePage.checkFieldExistence("errorMsgStateAndZipCodeDisplayed"));
		homePage.populateField("SelectProvince",testProperties.getConstant("SelectProvince"));
		
		//Verify Error Message When ZipCode Not Entered
		homePage.populateField("ZipCodeText"," ");
		homePage.check("autoRenewal");
		Util.sleep(5000);		
		Util.printInfo("Verify Error Message is Displayed When ZipCode Not Entered");
		assertTrueCatchException("Error message is displayed for ZipCode When value Not Entered ",homePage.checkFieldExistence("errorMsgStateAndZipCodeDisplayed"));
		homePage.populateField("ZipCodeText",testProperties.getConstant("ZipCodeText"));
		homePage.populateField("SelectProvince",testProperties.getConstant("SelectProvince"));
		//homePage.populateField("ZipCodeText","pq");
		homePage.check("autoRenewal");
				
		//Verify Continue Button is Enabled			
		Util.printInfo("Validation whether continue button is Enabled");		
		assertTrueCatchException("Continue button is Enabled .",homePage.checkFieldExistence("ContinueEnable"));
		homePage.click("ContinueEnable");
		Util.sleep(3000);
		Util.printInfo("Verify Error Message When CC Value is Incorrect Not Entered");
		assertTrueCatchException("Error message is displayed When Card Number is Incorrect",homePage.checkFieldExistence("errorMsgCardNumberDisplayed"));
		
		//Enter Correct CC
		Util.sleep(5000);
		homePage.populateField("CardNumberText",testProperties.getConstant("validCardNumberText"));
		//Enter invalid Security Code
		homePage.populateField("SecurityCodeText",testProperties.getConstant("SecurityCodeText")+"5");
		homePage.populateField("ZipCodeText","pq");
		homePage.click("ContinueEnable");
		Util.sleep(3000);
		Util.printInfo("Verify Error Message is Displayed When ZipCode Not Correct");
		assertTrueCatchException("Error message is displayed for ZipCode When value Not Correct ",homePage.checkFieldExistence("errorMsgStateAndZipCodeDisplayed"));
		Util.printInfo("Validation Error message is displayed When Security Code not correct");
		assertTrueCatchException("Validation Error message is displayed When Security Code Not correct",homePage.checkFieldExistence("errorMsgSecurityCodeDisplayed"));
		Util.sleep(5000);
	
		//Verify Error Message for Security Code
		homePage.populateField("ZipCodeText",testProperties.getConstant("ZipCodeText"));
		Util.sleep(1000);
		
		homePage.populateField("SecurityCodeText",testProperties.getConstant("SecurityCodeText"));
		Util.printInfo("Validation whether continue button is Enabled After Entering Correct CC");
		
		homePage.check("autoRenewal");
		assertTrueCatchException("Continue button is Enabled .",homePage.checkFieldExistence("ContinueEnable"));
		homePage.click("ContinueEnable");
		Util.sleep(5000);
		assertTrueCatchException("Order Summary Page Displayed After Filling Required Data in Payment Page",homePage.checkFieldExistence("submitOrder"));
		
	}
	
	
	
	
	@After
	public void tearDown() throws Exception {
		
		
		driver.quit();

		finish();
}
}
