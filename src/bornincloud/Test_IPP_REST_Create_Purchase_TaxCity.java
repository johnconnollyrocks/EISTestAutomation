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

/**
 * Test class - Test_IPP_REST_Create_PaymentProfile 
 * 
 * @author santosh
 * @version 1.0.0
 */
public final class Test_IPP_REST_Create_Purchase_TaxCity extends BornInCloudTestBase {
	
	String firstName=getUniqueString(6);
	String lastName=getUniqueString(6);
	String address=getUniqueString(15);
	String city=getUniqueString(9);
	String state="CA";
	String country="US";
	String postalCode="94561";
	
	public Test_IPP_REST_Create_Purchase_TaxCity() throws IOException {
		super("Backend","Browser",getAppBrowser());
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_IPP_REST_Create_Purchase_TaxCity_Method() throws Exception {
		
				
		
		// Parsing test data values from test data file and creating a test data Hash
				String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
				Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
				String actualRequestJson=getStringAfterReplacingData(testDataMap.get("content"));
				String expectedResponceJson=getStringAfterReplacingData(testDataMap.get("expectedjsondata"));
				// Making a Get Rest call and retrieving the Json response in a string
				HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
				HttpResponse response = createEntitlement(client, testDataMap.get("ippRequest"), testDataMap.get("userId"), actualRequestJson, testDataMap.get("jsonMimetype"));	
				
				
							    
				// Verifying the Status Code 
				System.out.println("Assert : Verify the Status Code" + "\n");
				compareStrings(response.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
				    
				// Verifying the Response Format
				System.out.println("Assert : Verify the Repsonse Format" + "\n");
				compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
				
			
//				// Verifying the Json Response from Rest Get Service
//				String actualjsondata = (readJsonFromResponse(response));
//
//			    System.out.println("Assert : Verifying the Json Response from Rest Create Service" + "\n");
//		    	assertTrue("Should get Expected responce", actualjsondata.contains(testDataMap.get("expectedjsondata").replaceAll(" ", "")));  
			    client.getConnectionManager().shutdown();
			    
	   
	}

	public String getStringAfterReplacingData(String string){
		
		String temp1=string.replace("USER_FIRSTNAME", firstName);
		String temp2=temp1.replace("USER_LASTNAME", lastName);
		temp1=temp2.replace("USER_ADDRESS", address);
		temp2=temp1.replace("USER_CITY", city);
		temp1=temp2.replace("USER_STATE", state);
		temp2=temp1.replace("USER_COUNTRY", country);
		temp1=temp2.replace("USER_POSTAL", postalCode);
		
		String acutalString=temp1;
		return acutalString;
	}
	
}
