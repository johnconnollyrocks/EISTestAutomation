package bornincloud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;
import common.exception.MetadataException;

public class Test_Verify_DoTest extends BornInCloudTestBase{
	
	String email = null;

	public Test_Verify_DoTest() {
		super("browser");
		// TODO Auto-generated constructor stub
	}
	
	@Before
	
	public void launchBrowser(){
		
		String USERNAME = null;
		String COUNTRY = null;
		String LANGUAGE = null;
		String currencyType = null;
		
		
		
						
		if(getEnvironment().equalsIgnoreCase("DEV")){
			 USERNAME = testProperties.getConstant("USER_ID");
			 COUNTRY = testProperties.getConstant("COUNTRY");
			 LANGUAGE = testProperties.getConstant("LANGUAGE");
			 currencyType=testProperties.getConstant("currencyType");
				
			}
			else if(getEnvironment().equalsIgnoreCase("STG")){
				 USERNAME = testProperties.getConstant("USER_ID_STG");			 
				 COUNTRY= testProperties.getConstant("COUNTRY_STG");
				 LANGUAGE = testProperties.getConstant("LANGUAGE_STG");
				 currencyType=testProperties.getConstant("currencyType_STG");
				 				 
			}
		String url=getBaseURL()+"userId="+ USERNAME+"&country="+ testProperties.getConstant("COUNTRY")+"&lang="+ testProperties.getConstant("LANGUAGE");
		launchIPP(url);
		/*String BaseURL = getBaseURL()+"userId="+ testProperties.getConstant("USER_ID")+"&country="+ testProperties.getConstant("COUNTRY")+"&lang="+ testProperties.getConstant("LANGUAGE");
		launchIPP(BaseURL);*/
	}
	@Test
	public void Test_Verify_DoTest_Validation() throws MetadataException{
		
		
//		try{assertTrue("Buy Button is Not Enabled Before Selecting Plan", homePage.checkFieldExistence("disableBuyItButton"));}
//		catch(Exception e){
//			Util.printInfo("Buy Button is Enabled Before Selecting Plan");
//		}
		//Verify the Buy Button is Not Enabled Before Selecting Plan
		assertTrueCatchException("Buy Button is Not Enabled Before Selecting Plan", homePage.checkFieldExistence("disableBuyItButton"));
		//Select Annual Plan
		homePage.click("AnnualPlanButton");
		//Verify the Buy Button is Enabled After Selecting Plan
		assertTrueCatchException("Buy Button is Enabled After Selecting Plan", homePage.checkFieldExistence("enableBuyItButton"));
		Util.sleep(5000);
		//Click on Buyit Button
		homePage.click("enableBuyItButton");
		Util.sleep(5000);
		//homePage.checkIfElementExistsInPage("enableBuyItButton", 5000);
		Util.printInfo("Name Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("NameText"));
		Util.printInfo("Credit Card Number Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("CardNumberText"));
		Util.printInfo("ExpMonth Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("SelectExpMonth"));
		Util.printInfo("ExpYear Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("SelectExpYear"));
		Util.printInfo("Security Code Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("SecurityCodeText"));
		Util.printInfo("Billing Address Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("BillingAddressText"));
		Util.printInfo("City Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("cityText"));
		Util.printInfo("State Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("SelectProvince"));
		Util.printInfo("ZipCode Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("ZipCodeText"));
		
		String currentUrl = getCurrentURL()+"&DOTEST=1";
			
		launchIPP(currentUrl);
		Util.sleep(5000);
		if(getEnvironment().equalsIgnoreCase("DEV")){
			email=testProperties.getConstant("eMail");
			}
			else if(getEnvironment().equalsIgnoreCase("STG")){
				email=testProperties.getConstant("eMail_STG");
			}
		/*Util.printInfo("Name Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("NameText"));
		Util.printInfo("Credit Card Number Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("CardNumberText"));
		Util.printInfo("ExpMonth Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("SelectExpMonth"));
		Util.printInfo("ExpYear Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("SelectExpYear"));
		Util.printInfo("Security Code Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("SecurityCodeText"));
		Util.printInfo("Billing Address Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("BillingAddressText"));
		Util.printInfo("City Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("cityText"));
		Util.printInfo("State Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("SelectProvince"));
		Util.printInfo("ZipCode Should be Empty");
		assertEqualsCatchException("", homePage.getValueFromGUI("ZipCodeText"));*/
		Util.printInfo("Country Should Not be be Empty");
		assertEqualsCatchException(testProperties.getConstant("VerifyCountry"), homePage.getValueFromGUI("countryInPayMentPage"));
		Util.printInfo("Email Should Not be Empty");
		assertEqualsCatchException(email, homePage.getValueFromGUI("emailID"));
		
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
