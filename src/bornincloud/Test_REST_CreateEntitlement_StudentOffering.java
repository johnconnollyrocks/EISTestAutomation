package bornincloud;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test class - Test_REST_CreateEntitlement_StudentOffering 
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_REST_CreateEntitlement_StudentOffering extends BornInCloudTestBase {
	
	public Test_REST_CreateEntitlement_StudentOffering() throws IOException {
		super("Backend","Browser",getAppBrowser());
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_REST_CreateEntitlement_StudentOffering_Method() throws Exception {
		
		// Read the test data
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));	   
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		// This method executes a REST Post call and returns the response
		HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
		HttpResponse response = createEntitlement(client, testDataMap.get("url"), testDataMap.get("userId"), testDataMap.get("content"), testDataMap.get("jsonMimetype"));
	   
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
}
