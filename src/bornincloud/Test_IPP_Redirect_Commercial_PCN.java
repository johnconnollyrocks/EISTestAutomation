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
import org.openqa.selenium.By;

/**
 * Test class - Test_IPP_Redirect_BillingPage
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_IPP_Redirect_Commercial_PCN extends BornInCloudTestBase {
	
	public Test_IPP_Redirect_Commercial_PCN() throws IOException {
		super("Frontend","Browser",getAppBrowser());
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Before
	public void setUp() throws Exception {
		//launchIPP("https://depot-dev.autodesk.com/service/ipp/v1/ippredirect?userId=UGR8LDV4QW3P&country=US&language=en");		
	}

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
	    //String contextId = findjsonelement(actualjsondata, "context");
	    String contextId = findjsonelement(actualjsondata );
	    System.out.println("\n" + contextId + "\n");
	    Map<String,String> UserEntMap = createEntMap(actualjsondata, testDataMap.get("userId"), "Actual", "Create", "Trial");
	    
	    String expectedjsondata = readJsonFromoffering(testDataMap.get("offTable1"));
	    Map<String,String> OffrEntMap = createEntMap(expectedjsondata, testDataMap.get("userId"), "Expected", "Create", "Trial");
	    
	    System.out.println("\n" + "Assert : Verify the Created Entitlements(Trial)" + "\n");
	    compareMaps(UserEntMap, OffrEntMap);
	    if(System.getProperty("environment").equals("DEV")){
	    	//Url=testDataMap.get("ippURL")+"contextId="+contextId+"&userId="+testDataMap.get("userId")+"&country=US&lang=en";
	    	launchIPP(testDataMap.get("ippURL")+"contextId="+contextId+"&userId="+testDataMap.get("userId")+"&country=US&lang=en");
			}
		
			else if(System.getProperty("environment").equals("STG")){
				
				 String hash = getclicHash(testDataMap.get("userId"), testDataMap.get("country") ,testDataMap.get("lang"));
				 launchIPP(testDataMap.get("ippURL")+"contextId="+contextId+"&userId="+testDataMap.get("userId")+"&country="+testDataMap.get("country")+"&lang="+testDataMap.get("lang") + hash);
				// Url=testDataMap.get("ippURL")+"contextId="+contextId+"&userId="+testDataMap.get("userId")+"&country="+testDataMap.get("country")+"&lang="+testDataMap.get("lang") + hash;				 
					}
	    
		
		Boolean isPresent = driver.findElements(By.xpath("//div[contains(text(),'Annual')]")).size() > 0;
		String URL = driver.getCurrentUrl();
		
		if (isPresent){
			System.out.println("\n" + URL +"\n");
			System.out.println("Choose Your Plan window exists : IPP Redirect works");
			driver.findElement(By.xpath("//div[contains(text(),'Annual')]")).click();
			
		}
		//driver.findElement(By.xpath("//*[@id=\\\"bic-template\\\"]/div/div[2]/div[3]/div/div[1]/div[1]")).click();
		driver.findElement(By.xpath(".//*[@class='buyIt active']")).click();	
		String newURL = driver.getCurrentUrl();
		String updatedURL = newURL+"&DOTEST=1";
		Thread.sleep(15000);
		open(updatedURL);
//		launchIPP(URL+"&DOTEST=1");
		
		String customerName = driver.findElement(By.cssSelector("#nameoncard")).getAttribute("value");
		
		compareStrings("John Test", customerName);
		
		String cardNumber = driver.findElement(By.cssSelector("#tiCNumber")).getAttribute("value");
		compareStrings("4111111111111111", cardNumber);
		
		String expiDateMonth = driver.findElement(By.cssSelector("#cbExpMounth")).getAttribute("value");
		compareStrings("06", expiDateMonth);
		
		String expYear = driver.findElement(By.cssSelector("#cbExpYear")).getAttribute("value");
		compareStrings("2015", expYear);
		
		String securityCode = driver.findElement(By.cssSelector("#tiCVV")).getAttribute("value");
		compareStrings("123", securityCode);
		
		String billingAdress = driver.findElement(By.cssSelector("#address")).getAttribute("value");
		compareStrings("111 McInnis Pkwy", billingAdress);
		
		String city = driver.findElement(By.cssSelector("#city")).getAttribute("value");
		compareStrings("San Rafael", city);
		
		String state = driver.findElement(By.cssSelector("#state")).getAttribute("value");
		compareStrings("California", state);
		
		String zip = driver.findElement(By.cssSelector("#zipcode")).getAttribute("value");
		compareStrings("94903", zip);
		

		driver.findElement(By.cssSelector("#auto-renew-checkbox")).click();
		String emailadress = driver.findElement(By.cssSelector("#bill_email > td.order__checkout__form__input > div")).getText();
		//compareStrings("autotestippstg@ssttest.net", emailadress);
		compareStrings(testDataMap.get("emailAddress"), emailadress);
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#pagecode > div.button-next-container.billing-page > button.button-next.billing-page.enabled")).click();
								
		//WebDriverWait wait = new WebDriverWait(driver, 20000);	
		Thread.sleep(5000);	
		/*Boolean issubmitorderPresent = driver.findElements(By.cssSelector("#order_summary > div.button-next-container.order-summary-page.enabled.disabled > button.button-next.enabled.order-summary-page")).size() > 0;
		
		if (issubmitorderPresent){
			System.out.println("\n" + URL +"\n");
			System.out.println("Successfully able to navigate to Submit Order Page");
			driver.findElement(By.cssSelector("#order_summary > div.button-next-container.order-summary-page.enabled.disabled > button.button-next.enabled.order-summary-page")).click();
		}
		
		Boolean iscloseorderPresent = driver.findElements(By.cssSelector("#orderCompleteCloseBtn")).size() > 0;
		
		Thread.sleep(5000);	
		
		if (iscloseorderPresent){
			System.out.println("\n" + URL +"\n");
			System.out.println("Successfully able to submit the order");
			driver.findElement(By.cssSelector("#orderCompleteCloseBtn")).click();
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
		client.getConnectionManager().shutdown();	*/	
	}
	@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		//driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		
		//finish();
	}
}

