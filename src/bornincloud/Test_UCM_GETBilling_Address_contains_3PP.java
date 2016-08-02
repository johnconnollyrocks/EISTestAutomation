package bornincloud;

import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
/**
 * Test class - Need add new name
 * 
 * US2108	UCM Get Billing Address Integration dd 9/8 Testing 9/8 (carry over--testing in STG)
 * Validate response if user has payment profiles in UCM
 * 
 * @author Denis Disciuc
 * @version 1.0.0
 */
public class Test_UCM_GETBilling_Address_contains_3PP extends BornInCloudTestBase {
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_UCM_GETBilling_Address_contains_3PP_Method() throws Exception{
		System.out.println(" ---------- Method:" + tname.getMethodName() + " ---------- \n");
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		 
		HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
		HttpResponse response = createEntitlement(client, testDataMap.get("tempURL"), "", testDataMap.get("content"), testDataMap.get("jsonMimetype"));
					    
		compareStrings("Assert : Do purchase. Verify the Status Code", new Integer(response.getStatusLine().getStatusCode()).toString(), new Integer(HttpStatus.SC_OK).toString());
		compareStrings("Assert : Do purchase. Verify the Repsonse Format", testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
	
		String actualjsondata = (readJsonFromResponse(response));
		
		JSONObject jsonObject = findJsonOject(actualjsondata, "ListOfContacts,Contact,ContactPaymentProfile");
        JSONArray lang= (JSONArray) jsonObject.get("PaymentProfile");
         
	    containStrings("Assert : Do purchase. Verifying the Json Response from Rest Create Service", Integer.toString(lang.size()), testDataMap.get("expectedjsondata").replaceAll(" ", ""));
	    client.getConnectionManager().shutdown();
	}
	
}
