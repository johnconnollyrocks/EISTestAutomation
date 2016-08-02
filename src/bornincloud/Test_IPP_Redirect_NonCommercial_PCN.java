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
public final class Test_IPP_Redirect_NonCommercial_PCN extends BornInCloudTestBase {
	
	public String Url = null;  
	
	public Test_IPP_Redirect_NonCommercial_PCN() throws IOException {
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
	public void Test_IPP_Redirect_NonCommercial_PCN_Method() throws Exception {	
				
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
	    
	    //launchIPP(testDataMap.get("ippURL")+"contextId="+contextId+"&userId="+testDataMap.get("userId")+"&country=US&lang=en");
	    
	   // launchIPP("Url");
	    
	 	   
	    
		Boolean isPresent = driver.findElements(By.cssSelector("#nonCommercialTxt")).size() > 0;
		String URL = driver.getCurrentUrl();
		if (isPresent){
			System.out.println("\n" + URL +"\n");
			System.out.println("NonCommercial Registration option exist");
			driver.findElement(By.cssSelector("#nonCommercialTxt")).click();			
		}		
		/*driver.findElement(By.cssSelector("#choosePlanAccordian > div.buyIt.active > div")).click();			
		Thread.sleep(15000);*/	
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#radiobtn > div > input.radiobtn1")).click();
		//driver.findElement(By.cssSelector("#radiobtn > div > input.radiobtn2")).click();
		driver.findElement(By.cssSelector("#bic-template > div > div.content > div.nonCommercialPlan > div.registerContent > form > input")).click();
		driver.findElement(By.cssSelector("#bic-template > div > div.content > div.nonCommercialPlan > div.registerContent > form > button")).click();
		Thread.sleep(15000);		
				 
		Boolean issubmitorderPresent = driver.findElements(By.cssSelector("#bic-template > div > div.content > div.orderComplete > div > button")).size() > 0;		
		if (issubmitorderPresent){
			System.out.println("\n" + URL +"\n");
			driver.findElement(By.cssSelector("#bic-template > div > div.content > div.orderComplete > div > button")).click();
			Thread.sleep(6000);
			System.out.println("Successfully able to navigate to Registration complete page");
		}
		
				
		// Parsing test data values from test data file and creating a test data Hash
		//String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		//Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		// Making a Get Rest call and retrieving the Json response in a string
		
		HttpResponse response = getEntitlement(client,testDataMap.get("url"), testDataMap.get("userId"), "?contextId="+contextId);
		String actualjsondata1 = readJsonFromResponse(response);
		
		
		//String actualjsondata = readJsonFromResponse(postresponse);	
	    //String contextId = findjsonelement(actualjsondata);
	    Map<String,String> UserEntMap1 = createEntMap(actualjsondata1, testDataMap.get("userId"), "Actual", "Get", "Student");
	    
	    
	    String expectedjsondata1 = readJsonFromoffering(testDataMap.get("offTable2"));
	    Map<String,String> OffrEntMap1 = createEntMap(expectedjsondata1, testDataMap.get("userId"), "Expected", "Get", "Student");
		    
		// Verifying the Status Code 
		System.out.println("Assert : Verify the Status Code" + "\n");
		compareStrings(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
		    
		// Verifying the Response Format
		System.out.println("Assert : Verify the Repsonse Format" + "\n");
		compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
		
		// Verifying the Json Response from Rest Get Service 
		System.out.println("Assert : Verify the actual Json Repsonse" + "\n");
////		compareStrings(actualjsondata1, expectedjsondata1);
//		expectedjsondata1 = readFileAsString(testDataMap.get("offTable2"));
//		compareStrings( actualjsondata1.replaceAll(" ", ""),expectedjsondata1);
		
		compareMaps(UserEntMap1, OffrEntMap1);	
		client.getConnectionManager().shutdown();
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
		
		//finish();
	}
}

