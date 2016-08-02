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
 * Test class - Test_DEV_REST_CreateEntitlement_Negative_NonActiveOfferingId
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_DEV_REST_CreateEntitlement_Negative_NonActiveOfferingId extends BornInCloudTestBase{
	
	public Test_DEV_REST_CreateEntitlement_Negative_NonActiveOfferingId() throws IOException {
		super("Backend","Browser",getAppBrowser());		
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_DEV_REST_CreateEntitlement_Negative_NonActiveOfferingId_Method() throws Exception {		
				
		// Parsing test data values from test data file and creating a test data Hash
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		// This method executes a REST Post call and returns the response
		HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
	    HttpResponse response = createEntitlement(client, testDataMap.get("url"), testDataMap.get("userId"), testDataMap.get("content"), testDataMap.get("jsonMimetype"));
		String actualjsondata = readJsonFromResponse(response);
		    
		// Verifying the Status Code 
		System.out.println("Assert : Verify the Status Code" + "\n");
		compareStrings(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
		    
		// Verifying the Response Format
		System.out.println("Assert : Verify the Repsonse Format" + "\n");
		compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
		
		// Verifying the Json Response from Rest Get Service 
		System.out.println("Assert : Verify the actual Json Repsonse" + "\n");
		compareStrings(testDataMap.get("expectedjsondata"), actualjsondata);	
		client.getConnectionManager().shutdown();
	}
}
