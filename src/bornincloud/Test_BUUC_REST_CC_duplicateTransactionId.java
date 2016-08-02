package bornincloud;

import java.io.IOException;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test class - Test_REST_GetEntitlement_Negative_NonExistingUser
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_BUUC_REST_CC_duplicateTransactionId extends BornInCloudTestBase{
	
	String transactionId="";
	
	/*public Test_BUUC_REST_CC_duplicateTransactionId() throws IOException {
		super("Backend","Browser",getAppBrowser());		
	}*/
	
	@Rule
	public TestName tname = new TestName();	
	
	@Test
	public void Test_BUUC_REST_CC_duplicateTransactionId_Method() throws Exception {		
				
		// Parsing test data values from test data file and creating a test data Hash
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		DefaultHttpClient client = getClientNew(testDataMap.get("mutualAuthCertBuuc"), testDataMap.get("mutualAuthPassBuuc")); 

		HttpResponse putresponse = updateEntitlement(client, testDataMap.get("transactionId"), testDataMap.get("buucRequest"), testDataMap.get("content"), testDataMap.get("jsonMimetype"));
				
		// Verifying the Status Code 
	    System.out.println("Assert : Verify the Status Code" + "\n");
	    compareStrings(putresponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	    
	    // Verifying the Response Format
	    System.out.println("Assert : Verify the Repsonse Format" + "\n");
	    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(putresponse.getEntity()).getMimeType());
	    
	    String actualjsondataupdate = readJsonFromResponse(putresponse);	
	   
		// Verifying the Json Response from Rest Get Service 
		System.out.println("Assert : Verify the actual Json Repsonse" + "\n");
		compareStrings(testDataMap.get("expectedjsondata"), actualjsondataupdate);
		client.getConnectionManager().shutdown();
	}

}
