package bornincloud;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
/**
 * Test class - Need add new name
 * 
 * US2108	UCM Get Billing Address Integration dd 9/8 Testing 9/8 (carry over--testing in STG)
 * Validate country code in response
 * 
 * @author Denis Disciuc
 * @version 1.0.0
 */
public class Test_UCM_GETBilling_Address_PP_use_countrycode extends BornInCloudTestBase {
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_UCM_GETBilling_Address_PP_use_countrycode_US_Method() throws Exception{
		runTest();
	}
	
	@Test
	public void Test_UCM_GETBilling_Address_PP_use_countrycode_CA_Method() throws Exception{
		runTest();
	}
	
	private void runTest() throws Exception{
		System.out.println(" ---------- Method:" + tname.getMethodName() + " ---------- \n");
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		 
		HttpResponse response=null;
		int count = 0;
		do {
	    HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
			Thread.sleep(1000);
			response = getEntitlement(client, testDataMap.get("tempURL"),testDataMap.get("userId"), "");
			count++;
			client.getConnectionManager().shutdown();
		} while (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK && count < 10);
		
		compareStrings("Assert : Verify the Status Code", new Integer(response.getStatusLine().getStatusCode()).toString(), new Integer(HttpStatus.SC_OK).toString());
		compareStrings("Assert : Verify the Repsonse Format", testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
	
		String actualjsondata = (readJsonFromResponse(response));
		String tempStr = takeValue(actualjsondata, "\"country\":\"(.*?)\"");
		containStrings("Assert : Verifying the Json Response from Rest Create Service", tempStr, testDataMap.get("expectedjsondata"));
	}
	
	public String takeValue(String actualjsondata, String patteern){
		Pattern pattern = Pattern.compile(patteern);
		Matcher matcher = pattern.matcher(actualjsondata);

		String nameStr="";
		if(matcher.find())
		{
		    nameStr=matcher.group(1);
		}
		return nameStr;
	}
}
