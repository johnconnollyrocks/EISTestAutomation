package bornincloud;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test class - Test_REST_GetEntitlement_Negative_HttpRequest
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_REST_GetEntitlement_Negative_HttpRequest extends BornInCloudTestBase{
	
	public Test_REST_GetEntitlement_Negative_HttpRequest() throws IOException {
		super("Backend","Browser",getAppBrowser());		
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_REST_GetEntitlement_Negative_HttpRequest_Method() throws Exception {		
				
		// Parsing test data values from test data file and creating a test data Hash
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		// Making a Get Rest call and retrieving the Json response in a string
		HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
		HttpResponse response = getEntitlement(client, testDataMap.get("httpUrl"), testDataMap.get("userId"), "");
		client.getConnectionManager().shutdown();
		
		// Verifying the Status Code 
		System.out.println("Assert : Verify the Status Code" + "\n");
		compareStrings(response.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);		    
			
	}
}

