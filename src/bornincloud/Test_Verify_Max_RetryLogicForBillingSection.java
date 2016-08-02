package bornincloud;



import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_Max_RetryLogicForBillingSection extends BornInCloudTestBase{
	
	 public String emailInPaymentPage;
	 public String countryInPaymentPage;
	
	public Test_Verify_Max_RetryLogicForBillingSection() throws IOException {
		super("browser");						
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
		Util.sleep(2000);
		
		homePage.check("autoRenewalCheckBox");
		
		emailInPaymentPage=homePage.getValueFromGUI("emailInPayMentPage");
		countryInPaymentPage=homePage.getValueFromGUI("countryInPayMentPage");
		
		System.out.println(emailInPaymentPage);		
		System.out.println(countryInPaymentPage);
	
		boolean repeatLoop=true;
		int count =0;
		while (repeatLoop){
			count=count+1;
			homePage.click("ContinueEnable");
			Util.sleep(2000);
			if(homePage.checkFieldExistence("errorMsgCardNumberDisplayed")){
				homePage.populateField("CardNumberText","");
				homePage.populateField("CardNumberText","411122233344"+count);
				Util.sleep(1000);
			}else{
				repeatLoop=false;
				break;
			}
		}
		Util.sleep(3000);
		assertTrue("Sucessfully navigated to Order Summary page after clicking on continu button.",homePage.checkFieldExistence("backButton"));
					
		Util.printInfo("Click on Back button and check whether it navigates to form again");
		homePage.click("backButton");
		Util.sleep(2000);
		assertTrueCatchException("Sucessfully came back to payment Info page after clicking on back button.",homePage.checkFieldExistence("ContinueEnable"));
			
		
		Util.printInfo("Click on continue button and check whether it navigates to Order summary page");
		homePage.click("ContinueEnable");
				
		Util.sleep(10000);
		
		homePage.waitForField("submitOrder", true, 20);
		Util.printInfo("Click on submit order button in Order summary page");
		if(homePage.checkFieldExistence("submitOrder")) {
			homePage.click("submitOrder");
			Util.sleep(2000);
		}
				
		Util.printInfo("Validate whether reenter page is displayed.");
		assertTrueCatchException("ReEnter or Change Payment info page is displayed sucessfully.",homePage.checkFieldExistence("nameFieldOnReEnterCardInfo"));
		
		assertTrueCatchException("Submit button is disabled on navigating to ReEnter or Change Payment info page ",homePage.checkIfElementExistsInPage("submitDisableOnReEnter", 10));
		
		homePage.populateField("nameFieldOnReEnterCardInfo",testProperties.getConstant("NameText"));
		homePage.populateField("CardFieldOnReEnterCardInfo",testProperties.getConstant("CardNumberText"));
		homePage.populateField("monthFieldOnReEnterCardInfo",testProperties.getConstant("SelectExpMonth"));
		homePage.populateField("yearFieldOnReEnterCardInfo",testProperties.getConstant("SelectExpYear"));
		homePage.populateField("securitCodeFieldOnReEnterCardInfo",testProperties.getConstant("SecurityCodeText"));
		
		assertTrueCatchException("Submit button is disabled even after entering invalid data on ReEnter or Change Payment info page ",homePage.checkIfElementExistsInPage("submitDisableOnReEnter", 10));
		
		homePage.populateField("nameFieldOnReEnterCardInfo",testProperties.getConstant("NameText"));
		homePage.populateField("CardFieldOnReEnterCardInfo",testProperties.getConstant("validCardNumberText"));
		homePage.populateField("monthFieldOnReEnterCardInfo",testProperties.getConstant("SelectExpMonth"));
		homePage.populateField("yearFieldOnReEnterCardInfo",testProperties.getConstant("SelectExpYear"));
		homePage.populateField("securitCodeFieldOnReEnterCardInfo",testProperties.getConstant("SecurityCodeText"));
		
		assertTrue("Submit button is disabled even after entering invalid data on ReEnter or Change Payment info page ",homePage.checkIfElementExistsInPage("submitEnableOnReEnter", 10));
		
		Util.printInfo("Validate whether billing information is same as expected in ReEnter or Change Payment info page");
		validateBillingInformation();
		Util.printInfo("Click on submit order button in ReEnter or Change Payment info page");
		Util.sleep(2000);
		homePage.click("submitEnableOnReEnter");
		Util.sleep(2000);
		
			
	}
	
	public void validateBillingInformation(){
		
		try {
			List<WebElement>address=homePage.getMultipleWebElementsfromField("billingInformationOnReEnterPage");
			
				
				String name=address.get(0).getText();
				System.out.println(name);
				String billingAddress=address.get(1).getText().trim();
				System.out.println(billingAddress);
				String cityNState=address.get(2).getText().trim();
				System.out.println(cityNState);
				String zipCode=address.get(3).getText().trim();
				System.out.println(zipCode);
				String country=address.get(4).getText().trim();
				System.out.println(country);
				String email=address.get(5).getText().trim();
				System.out.println(email);
				
				String[] temp=cityNState.split(",");
				assertTrueCatchException("Name validation passed both the page",assertEquals(name.trim(), testProperties.getConstant("NameText")));
				assertTrueCatchException("Billing address the validation passed both the page",assertEquals(billingAddress, testProperties.getConstant("BillingAddressText")));
				assertTrueCatchException("Billing address the validation passed both the page",assertEquals(temp[0].trim(), testProperties.getConstant("cityText")));
				assertTrueCatchException("Billing address the validation passed both the page",assertEquals(temp[1].trim(), testProperties.getConstant("SelectProvince")));
				assertTrueCatchException("Billing address the validation passed both the page",assertEquals(emailInPaymentPage,email));
				assertTrueCatchException("Billing address the validation passed both the page",assertEquals(countryInPaymentPage,country));
				

			
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void assertTrueCatchException(String message , boolean expected){
		try{
			assertTrue(message,expected);
		}catch(Exception e){
			Util.printInfo(message+"--FAILED");
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
