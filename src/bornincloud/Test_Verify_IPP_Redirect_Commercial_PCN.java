package bornincloud;

import java.io.IOException;
import java.util.Map;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import common.Util;


/**
 * Test class - Test_IPP_Redirect_BillingPage
 * @author Brijesh Chavda
 * @updated praju
 * @version 1.1.0
 */
public final class Test_Verify_IPP_Redirect_Commercial_PCN extends BornInCloudTestBase  {
 
	
	public Test_Verify_IPP_Redirect_Commercial_PCN() throws IOException {
		super("browse");
		// TODO Auto-generated constructor stub
	}
	
		
	@Rule
	public TestName tname = new TestName();
	
	
/*	@Before
	public void setUp() throws Exception {
		//String url=getBaseURL()+"userId="+ testProperties.getConstant("USER_ID")+"&country="+ testProperties.getConstant("COUNTRY")+"&language="+ testProperties.getConstant("LANGUAGE");
		//launchIPP(url);
	}*/
	
	@Test
	public void Test_IPP_Redirect_Commercial_PCN_Method() throws Exception {	
		// This method executes a REST Post call and returns the response
		// Parsing test data values from test data file and creating a test data Hash
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
		HttpResponse postresponse = createEntitlement(client, testDataMap.get("url"), testDataMap.get("userId"), testDataMap.get("content"), testDataMap.get("jsonMimetype"));
	   
	    // Verifying the Status Code 
	    System.out.println("Assert : Verify the Status Code" + "\n");
	    compareStrings(postresponse.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
	    
	    // Verifying the Response Format
	    System.out.println("Assert : Verify the Repsonse Format" + "\n");
	    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(postresponse.getEntity()).getMimeType());
	    
	    String actualjsondata = readJsonFromResponse(postresponse);	
	    String contextId = findjsonelement(actualjsondata);
	    System.out.println("\n" + contextId + "\n");
	    Map<String,String> UserEntMap = createEntMap(actualjsondata, testDataMap.get("userId"), "Actual", "Create", "Trial");
	    
	    String expectedjsondata = readJsonFromoffering(testDataMap.get("offTable1"));
	    Map<String,String> OffrEntMap = createEntMap(expectedjsondata, testDataMap.get("userId"), "Expected", "Create", "Trial");
	    
	    System.out.println("\n" + "Assert : Verify the Created Entitlements(Trial)" + "\n");
	    compareMaps(UserEntMap, OffrEntMap);   
	    
	    String url=null;
	    if (getEnvironment().equalsIgnoreCase("DEV")) {
			url=testDataMap.get("ippURL")+"contextId="+contextId+"&userId="+testDataMap.get("userId")+"&country=US&lang=en";
		} else if (getEnvironment().equalsIgnoreCase("STG")) {
			String hash = getclicHash(testDataMap.get("userId"), "US" ,"EN");
			url=testDataMap.get("ippURL")+"contextId="+contextId+"&userId="+testDataMap.get("userId")+"&country=US&lang=en"+hash;
		}
		launchIPP(url);
		
		
			//getBrowserCurrentURL() function newly added in BorninCloudTestBase
			
			String URL =getCurrentURL();
				
			Boolean isPresent = homePage.checkIfElementExistsInPage("AnnualPlanButton",120);
		
		 	
		
		if (isPresent){
			System.out.println("\n" + URL +"\n");
			System.out.println("Choose Your Plan window exists : IPP Redirect works");
			homePage.click("AnnualPlanButton");
			Util.sleep(2000);
			
		}
		
		
		homePage.click("enableBuyItButton");
		Util.sleep(5000);
		
		
		String newURL = getCurrentURL();
		
		String updatedURL = newURL+"&DOTEST=1";
		Util.sleep(1000);
		open(updatedURL);

       //Instruction to insert data in the fields ??
			
		String customerName = homePage.getAttribute("NameText", "value");
		
		//Instruction to Evaluate to values ??
		
		compareStrings("John Test", customerName);
		//***********************************************************************
		
		String cardNumber = homePage.getAttribute("CardNumberText", "value");
		
				compareStrings("4111111111111111", cardNumber);
		//***********************************************************************
		String expiDateMonth = homePage.getAttribute("SelectExpMonth", "value");
		
		compareStrings("06", expiDateMonth);
		//***********************************************************************
		String expYear =  homePage.getAttribute("SelectExpYear", "value");
		
		compareStrings("2015", expYear);
		//***********************************************************************
		String securityCode = homePage.getAttribute("SecurityCodeText", "value");
		
		compareStrings("123", securityCode);
		//***********************************************************************
		String billingAdress = homePage.getAttribute("BillingAddressText", "value");
		
		compareStrings("111 McInnis Pkwy", billingAdress);
		//***********************************************************************
		String city = homePage.getAttribute("cityText", "value");
		
		compareStrings("San Rafael", city);
		//***********************************************************************
		String state = homePage.getAttribute("SelectProvince", "value");
		
		compareStrings("California", state);
		//***********************************************************************
		String zip = homePage.getAttribute("ZipCodeText", "value");
		
		compareStrings("94903", zip);
		//***********************************************************************
		
		homePage.check("autoRenewalCheckBox");
		
		
		//Verify EmailID in Billing Information page
		System.out.println("Email Address displayed on Portal :"+homePage.getTextFromLink("emailID")+ "\n");
		
		String emailadress = homePage.getTextFromLink("emailID");
		compareStrings(testDataMap.get("emailAddress"), emailadress);
				
		Util.sleep(1000);
		
			homePage.click("ContinueEnable");

		Util.sleep(5000);
		
		//Verify Submit order Button is Present
			
		Boolean issubmitorderPresent = homePage.isFieldPresent("submitOrder");
	
		
		if (issubmitorderPresent){
			System.out.println("\n" + URL +"\n");
			System.out.println("Successfully able to navigate to Submit Order Page ");
			homePage.click("submitOrder");
		}
		
		
		Boolean iscloseorderPresent = homePage.isFieldPresent("orderCompleteClose");
				
		Util.sleep(5000);	
		
		
		if (iscloseorderPresent){
			System.out.println("\n" + URL +"\n");
			homePage.click("orderCompleteClose");
			System.out.println("Successfully able to submit the order");
		}
		
		
		// Making a Get Rest call and retrieving the Json response in a string
		
		HttpResponse response = getEntitlement(client, testDataMap.get("url"), testDataMap.get("userId"), "?contextId="+contextId);
		String actualjsondata1 = readJsonFromResponse(response);
		
		Map<String,String> UserEntMap1 = createEntMap(actualjsondata1, testDataMap.get("userId"), "Actual", "Get", "Rental");
	    String expectedjsondata1 = readJsonFromoffering(testDataMap.get("offTable2"));
	    Map<String,String> OffrEntMap1 = createEntMap(expectedjsondata1, testDataMap.get("userId"), "Expected", "Get", "Rental");
		    
		// Verifying the Status Code 
		System.out.println("Assert : Verify the Status Code" + "\n");
		compareStrings(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
		    
		// Verifying the Response Format
		System.out.println("Assert : Verify the Repsonse Format" + "\n");
		compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
		
		// Verifying the Json Response from Rest Get Service 
		System.out.println("Assert : Verify the actual Json Repsonse" + "\n");
		compareMaps(UserEntMap1, OffrEntMap1);
		client.getConnectionManager().shutdown();		
	}
	
	
	
	
	//*******************************************************************************************************
	
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

