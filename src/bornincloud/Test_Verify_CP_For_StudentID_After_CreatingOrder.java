package bornincloud;

//@@ author: Santosh

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.jniwrapper.linux.x11.events.XClientMessageEvent.Data;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_CP_For_StudentID_After_CreatingOrder extends BornInCloudTestBase{
	
	public String sessionID;	
	public String  userID;
	
	public Test_Verify_CP_For_StudentID_After_CreatingOrder() throws IOException {
		super("browser","firefox");						
	}
	@Rule
	public TestName tname = new TestName();
	
	@Before
	public void setUp() throws Exception {
		
	}
	@Test
	public void Test_Verify_CP_For_StudentID_Method() throws Exception {
		
		Map<String, String> userDetails= new TreeMap<String, String>();
		userDetails=createCustomerPortalUserID();
		
		
		sessionID=userDetails.get("sessionID");
		userID=userDetails.get("userID");
		createOrderForUser();
		switchDriverToOtherBrowser("chrome");
		//Util.sleep(5000);
		Util.sleep(60000);
		launchIPP(getPortalURL());
		Util.sleep(5000);
		driver.navigate().refresh();
			
		Util.sleep(5000);
		
		portalValidationsForUser(userID, "Password1","Student");
		
	
	}
	
	public void createOrderForUser() throws Exception{
		// Read the test data
				String testdata = readJsonFromoffering(System.getProperty("testdatafile"));	   
				Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
				
				// This method executes a REST Post call and returns the response
				HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
				HttpResponse response = createEntitlement(client, testDataMap.get("url"), sessionID, testDataMap.get("content"), testDataMap.get("jsonMimetype"));
			   
			    // Verifying the Status Code 
			    System.out.println("Assert : Verify the Status Code" + "\n");
			    compareStrings(response.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
			    
			    // Verifying the Response Format
			    System.out.println("Assert : Verify the Repsonse Format" + "\n");
			    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
			    
			    String actualjsondata = readJsonFromResponse(response);
			    Map<String,String> UserEntMap = createEntMap(actualjsondata, testDataMap.get("userId"), "Actual", "Create", "Student");
			    
			    String expectedjsondata = readJsonFromoffering(testDataMap.get("offTable1"));
			    Map<String,String> OffrEntMap = createEntMap(expectedjsondata, testDataMap.get("userId"), "Expected", "Create", "Student");			
			    
			
			    System.out.println("Assert : Verify the Created Entitlements" + "\n");
			    compareMaps(UserEntMap, OffrEntMap);    
			    client.getConnectionManager().shutdown();
	}
	
	
	@After
	public void tearDown() throws Exception {
		
		
		driver.quit();

		
		finish();
}
}
