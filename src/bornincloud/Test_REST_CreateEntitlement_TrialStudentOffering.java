package bornincloud;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test class - Test_REST_CreateEntitlement_TrialOffering 
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_REST_CreateEntitlement_TrialStudentOffering extends BornInCloudTestBase {
	
	public Test_REST_CreateEntitlement_TrialStudentOffering() throws IOException {
		super("Backend","Browser",getAppBrowser());
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_REST_CreateEntitlement_TrialStudentOffering_Method() throws Exception {
		
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
	    Map<String,String> UserEntMap = createEntMap(actualjsondata, testDataMap.get("userId"), "Actual", "Create", "Trial");
	    	    
	    String expectedjsondata1 = readJsonFromoffering(testDataMap.get("offTable1"));
	    Map<String,String> OffrEntMapTrial = createEntMap(expectedjsondata1, testDataMap.get("userId"), "Expected", "Create", "Trial");
	    String expectedjsondata2 = readJsonFromoffering(testDataMap.get("offTable2"));
	    Map<String,String> OffrEntMapStudent = createEntMap(expectedjsondata2, testDataMap.get("userId"), "Expected", "Create", "Student");
	    Map<String,String> combinedMap = new HashMap<String,String>();	
	    
	    combinedMap.putAll(OffrEntMapTrial);
	    combinedMap.putAll(OffrEntMapStudent);
	    
	    System.out.println("Assert : Verify the Created Entitlements" + "\n");
	    compareMaps(UserEntMap, combinedMap);    
	    client.getConnectionManager().shutdown();
	}	
}
