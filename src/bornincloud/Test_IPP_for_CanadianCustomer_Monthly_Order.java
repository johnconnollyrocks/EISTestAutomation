package bornincloud;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

/**
 * Test class - Test_IPP_Redirect_BillingPage
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_IPP_for_CanadianCustomer_Monthly_Order extends BornInCloudTestBase {
	
	public Test_IPP_for_CanadianCustomer_Monthly_Order() throws IOException {
		super("browser");
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
	
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
	public void Test_IPP_for_CanadianCustomer_Monthly_Order_Method() throws Exception {
		
			String URL = driver.getCurrentUrl();
			Thread.sleep(1000);
			homePage.click("MonthlyPlanButton");
			Util.sleep(1000);
			
			String actCurrencyType = homePage.getTextFromLink("currencyTypeHomepage");
			if (actCurrencyType.equals("CAD")){
				assertTrueCatchException("Canadian currency available", actCurrencyType.equals("CAD"));
			}else{
				EISTestBase.fail("Canadian currency not available");
			}
			
			homePage.click("buyItButton");
			Util.sleep(1000);
			
			String newURL = driver.getCurrentUrl();
									
			//enter billing info & place order			
			String updatedURL = newURL+"&DOTEST=1";
			Util.sleep(15000);
			
			open(updatedURL);
						
			homePage.check("autoRenewalCheckBox");
			
			Util.printInfo("Click on continue button and check whether it navigates to Order summary page");
			homePage.click("ContinueEnable");
			Util.sleep(10000);
						
			homePage.waitForField("submitOrder", true, 20);
			
			if (homePage.getTextFromLink("taxRate").equalsIgnoreCase("Plus Tax 12%")){
				assertTrueCatchException("Tax rate is correct", homePage.getTextFromLink("taxRate").equalsIgnoreCase("Plus Tax 12%"));
			}else{
				EISTestBase.fail("Tax rate is not calculated properly");
			}
						
			
			Util.printInfo("Click on submit order button in Order summary page");
			if(homePage.checkFieldExistence("submitOrder")) {
				homePage.click("submitOrder");
				Util.sleep(2000);
			}
			
			if (homePage.checkIfElementExistsInPage("orderNumTxt", 10)){
				assertTrueCatchException("Sucessfully navigated to Thank you page. ",homePage.checkIfElementExistsInPage("orderNumTxt", 10));
			}else{
				EISTestBase.fail("order page is not found after clicking on submit button");
			}
			
			if (homePage.checkIfElementExistsInPage("orderCloseIcon", 10)){
				homePage.click("orderCloseIcon");
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

