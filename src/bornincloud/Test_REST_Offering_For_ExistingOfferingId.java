package bornincloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test class - Test_REST_GetEntitlement_OfferingId
 * 
 * @author Santosh Kumar
 * @version 1.0.0
 */
public final class Test_REST_Offering_For_ExistingOfferingId extends BornInCloudTestBase{
	
	public Test_REST_Offering_For_ExistingOfferingId() throws IOException {
		super("Backend","Browser",getAppBrowser());		
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_REST_Offering_For_OfferingID() throws Exception {		
				
		// Parsing test data values from test data file and creating a test data Hash
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		// Making a Get Rest call and retrieving the Json response in a string
		HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
		HttpResponse response = getEntitlement(client,testDataMap.get("url_Offering"), testDataMap.get("offeringId"), "");
		String actualjsondata = readJsonFromResponse(response);
		client.getConnectionManager().shutdown();    
		// Verifying the Status Code 
		System.out.println("Assert : Verify the Status Code" + "\n");
		compareStrings(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
		    
		// Verifying the Response Format
		System.out.println("Assert : Verify the Repsonse Format" + "\n");
		compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
		
		
		String expectedjsondata = readFileAsString(testDataMap.get("offTable1"));
		
		// Verifying the Json Response from Rest Get Service 
		System.out.println("Assert : Verify the actual Json Repsonse" + "\n");
		compareStrings(expectedjsondata, actualjsondata.replaceAll(" ", ""));
		
	}
}
