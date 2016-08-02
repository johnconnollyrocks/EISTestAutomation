package bornincloud;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import bornincloud.BornInCloudConstants;

/**
 * Test class - Test_REST_CreateEntitlement_EnthusiastOffering 
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_IPP_REST_Get_IPP_Initializer extends BornInCloudTestBase {
	
	public Test_IPP_REST_Get_IPP_Initializer() throws IOException {
		super("Backend","Browser",getAppBrowser());
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_IPP_REST_Get_IPP_Initializer_Method() throws Exception {
		
		// Parsing test data values from test data file and creating a test data Hash
				String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
				Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
				
				// Making a Get Rest call and retrieving the Json response in a string
				//"http://internal-bic-ipp-dev-1703959650.us-east-1.elb.amazonaws.com/services/v1/initializer?grantToken=abcd&userId=F8PKPV9NJBPT&country=US&offeringId=FSN360-TRIAL&subId=1009"
				String userIdextended="/services/v1/initializer?grantToken=" + testDataMap.get("grantToken") + "&userId=" + testDataMap.get("userId") + "&country=" + testDataMap.get("country") +"&offeringId=" + testDataMap.get("offeringId") + "&subId=" + testDataMap.get("subId");
				HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
				HttpResponse response = getEntitlement(client,getBaseURL(),userIdextended ,"");
				String actualjsondata = readJsonFromResponse(response);
				client.getConnectionManager().shutdown();
			    
				// Verifying the Status Code 
				System.out.println("Assert : Verify the Status Code" + "\n");
				compareStrings(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
				    
				// Verifying the Response Format
				System.out.println("Assert : Verify the Repsonse Format" + "\n");
				compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
				
				// Verifying the Json Response from Rest Get Service 
				System.out.println("Assert : Verify the actual Json Repsonse" + "\n");
				compareStrings(testDataMap.get("expectedjsondata"), actualjsondata);
							    
					
	   
	}	
}
