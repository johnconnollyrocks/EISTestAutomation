package bornincloud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import common.Util;
import common.exception.GUIException;
import common.exception.MetadataException;

public class Test_Verify_DoTest_ExistingUser extends BornInCloudTestBase {

	public Test_Verify_DoTest_ExistingUser() {
		super("browser");
		// TODO Auto-generated constructor stub
	}
@Before
	public void launchBrowser() {
	
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
		
		/*String baseURL = getBaseURL()+"userId="+testProperties.getConstant("USER_ID")+"&country="+testProperties.getConstant("COUNTRY")+"&lang="+testProperties.getConstant("LANGUAGE");
		launchIPP(baseURL);*/

	}
@Test
public void Test_Verify_DoTest_ExistingUser_Validation() throws GUIException{
	
	String eMail = null;
	String cityText = null;
	String SelectProvince = null;
	String ZipCodeText = null;
	String NameText = null;
	String BillingAddressText = null;
	//Verify Buy Button is Displayed Before Selecting Plan
	assertTrueCatchException("Buy Button is Displayed Before Selecting Plan",homePage.checkFieldExistence("disableBuyItButton"));
	//Select Yearly Plan
	homePage.click("AnnualPlanButton");
	//Wait till Buy Button Enabled
	homePage.checkIfElementExistsInPage("enableBuyItButton", 5000);
	//Verify Buy Button is Enabled After Selecting Plan
	assertTrueCatchException("Buy Button is Enabled After Selecting Plan",homePage.checkFieldExistence("enableBuyItButton"));
	homePage.check("enableBuyItButton");
	//Wait till Payment Page Displayed
	homePage.checkIfElementExistsInPage("emailInPayMentPage", 5000);
	//Get the Current URL
	String currentURL = getCurrentURL()+"&DOTEST=1";
	launchIPP(currentURL);
	//Wait till Page Reload
	Util.sleep(5000);
	
	if(getEnvironment().equalsIgnoreCase("DEV")){
			eMail = testProperties.getConstant("eMail");
			cityText = testProperties.getConstant("cityText");
			SelectProvince = testProperties.getConstant("SelectProvince");
			ZipCodeText=testProperties.getConstant("ZipCodeText");
			NameText=testProperties.getConstant("NameText");
			BillingAddressText=testProperties.getConstant("BillingAddressText");
			
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			eMail = testProperties.getConstant("eMail_STG");
			cityText = testProperties.getConstant("cityText_STG");
			SelectProvince = testProperties.getConstant("SelectProvince_STG");
			ZipCodeText=testProperties.getConstant("ZipCodeText_STG");
			NameText=testProperties.getConstant("NameText_STG");
			 				 
		}
	
	//driver.findElement(By.xpath("//select[@id='state']")).sendKeys(Keys.ALT, Keys.ARROW_LEFT);
	homePage.checkIfElementExistsInPage("emailInPayMentPage", 5000);
	//Verify the Payment Details
	//assertEqualsCatchException(testProperties.getConstant("NameText"),homePage.getValueFromGUI("NameText"));
	assertEqualsCatchException(NameText,homePage.getValueFromGUI("NameText"));
	assertEqualsCatchException(testProperties.getConstant("CardNumberText"),homePage.getValueFromGUI("CardNumberText"));
	String Month = homePage.getValueFromGUI("SelectExpMonth").trim();
	
	assertEqualsCatchException(testProperties.getConstant("SelectExpMonth"),Month);
	String Year = homePage.getValueFromGUI("SelectExpYear").trim();
	
	assertEqualsCatchException(testProperties.getConstant("SelectExpYear"),Year);
	assertEqualsCatchException(testProperties.getConstant("SecurityCodeText"),(homePage.getValueFromGUI("SecurityCodeText")));
	//assertEqualsCatchException(testProperties.getConstant("BillingAddressText"), homePage.getValueFromGUI("BillingAddressText"));
	assertEqualsCatchException(BillingAddressText, homePage.getValueFromGUI("BillingAddressText"));
	//assertEqualsCatchException(testProperties.getConstant("cityText"), homePage.getValueFromGUI("cityText"));
	assertEqualsCatchException(cityText, homePage.getValueFromGUI("cityText"));
	//assertEqualsCatchException(testProperties.getConstant("SelectProvince"), homePage.getValueFromGUI("SelectProvince"));
	assertEqualsCatchException(SelectProvince, homePage.getValueFromGUI("SelectProvince"));
	//assertEqualsCatchException(testProperties.getConstant("ZipCodeText"), homePage.getValueFromGUI("ZipCodeText"));
	assertEqualsCatchException(ZipCodeText, homePage.getValueFromGUI("ZipCodeText"));
	assertEqualsCatchException(testProperties.getConstant("VerifyCountry"), homePage.getValueFromGUI("countryInPayMentPage"));
	assertEqualsCatchException(eMail, homePage.getValueFromGUI("emailInPayMentPage"));
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
