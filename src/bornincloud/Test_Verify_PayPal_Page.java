package bornincloud;



import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISConstants;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_PayPal_Page extends BornInCloudTestBase{
	
	 public String emailInPaymentPage;
	 public String countryInPaymentPage;
	
	public Test_Verify_PayPal_Page() throws IOException {
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
		
		Util.printInfo("Validate Buy IT button is disabled on launch");
		assertTrueCatchException("Buy IT button is disabled on launch ",homePage.checkIfElementExistsInPage("disableBuyItButton", 10));
		Util.printInfo("Click on Annual Plan button");
		homePage.click("AnnualPlanButton");
		Util.printInfo("Validate Whether Buy IT button is enabled after selecting plan");
		assertTrueCatchException("Buy IT button is enabled after selcting the plan ",homePage.checkIfElementExistsInPage("enableBuyItButton", 10));
		homePage.click("enableBuyItButton");
		
		homePage.waitForField("NameText", true, 10);
		
		
		if (homePage.checkIfElementExistsInPage("NameText", 10)){
			assertTrueCatchException("Sucessfully navigated to payment information page after clicking on Buy it. ",homePage.checkIfElementExistsInPage("NameText", 10));
		}else{
			EISTestBase.fail(" payment information page is not found after clicking on Buy it button");
		}
		
		Util.printInfo("Select Paypal as a payment type by clicking on Paypal radio button");
		homePage.click("payPalCheckBox");
		
		Util.sleep(3000);		
		//Enter valid data and place the order
		Util.printInfo("Enter the value in Name field");
		homePage.populateField("NameText","");
		homePage.populateField("NameText",testProperties.getConstant("NameText"));
		
		Util.printInfo("Enter the value in Billing address field");
		homePage.populateField("BillingAddressText","");
		homePage.populateField("BillingAddressText",testProperties.getConstant("BillingAddressText"));
		
		Util.printInfo("Enter the value in City field");
		homePage.populateField("cityText","");
		homePage.populateField("cityText",testProperties.getConstant("cityText"));
		
		Util.printInfo("Enter the value in state field");
		homePage.populateField("SelectProvince"," ");
		homePage.populateField("SelectProvince",testProperties.getConstant("SelectProvince"));
		
		Util.printInfo("Enter the value in ZipCode field");
		homePage.populateField("ZipCodeText","");
		homePage.populateField("ZipCodeText",testProperties.getConstant("ZipCodeText"));
		Util.sleep(2000);
		
		Util.printInfo("Click on check box- \"I have read the Auto-Renewal and Purchase Terms.\"");
		homePage.check("autoRenewalCheckBox");
		
		emailInPaymentPage=homePage.getValueFromGUI("emailInPayMentPage");
		countryInPaymentPage=homePage.getValueFromGUI("countryInPayMentPage");
		
		Util.printInfo("Click on continue button and validate whether it redirected to Order Summary page");
		homePage.click("ContinueEnable");
	
		Util.sleep(3000);
		
		assertTrue("Sucessfully navigated to Order Summary page after clicking on continu button.",homePage.checkFieldExistence("backButton"));

		homePage.waitForField("submitOrder", true, 20);
		
		Util.printInfo("Validate whether Paypal image is displayed under payment information.");
		assertTrueCatchException("Paypal image is displayed under payment information.",homePage.checkFieldExistence("payPalImage"));
		Util.printInfo("Click on submit order button in Order summary page");
		if(homePage.checkFieldExistence("submitOrder")) {
			homePage.click("submitOrder");
			Util.sleep(2000);
		}
		
		//Validating US53-TC146 (IPP--Billing Information Page UI (PayPal) - PayPal page)
		Util.printInfo("Validate whether user navigated to avangate page.");
		//if (homePage.getValueFromGUI("payPalPageHeader").equalsIgnoreCase("Avangate B.V.")){
			if (homePage.getValueFromGUI("payPalPageHeader").equalsIgnoreCase("Autodesk")){
		assertTrueCatchException("Sucessfully navigated to Autodesk page.",homePage.checkFieldExistence("payPalPageHeader"));
		}else{
			EISTestBase.fail("Autodesk is not displayed in Header.");
		}
		
		//Validating US53-TC332(IPP--Billing Information Page UI (PayPal) - Currency information displayed in PayPal Order Summary)
		Util.printInfo("Validate the  currency type.");
		assertTrueCatchException("Currency type matched.",homePage.getValueFromGUI("grandTotalAtPaypalPage").contains(testProperties.getConstant("currencyType")));
		
		//Validating US53-TC333(IPP--Billing Information Page UI (PayPal) - Cancel from PayPal log in page)
		homePage.click("cancelLinkOnPaypalPage");
		Util.sleep(2000);
		assertTrueCatchException("Sucessfully navigated to Cancellation page after clicking on cancel.",homePage.checkFieldExistence("orderCancelPageForPayPal"));
			
	}
	
	
	
	@After
	public void tearDown() throws Exception {
		driver.quit();	
		finish();
}
}
