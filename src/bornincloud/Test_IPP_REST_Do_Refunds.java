package bornincloud;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * 
 * TC770	PSP Handler--Issue Refunds--Full Refund dd 9/3 Refunds
 * TC775	PSP Handler--Issue Refunds--Full Refund dd 9/3 Testing 9/8 - Multiple Payment Profiles
 * 
 * @author Denis Disciuc
 * @version 1.0.0
 */
public class Test_IPP_REST_Do_Refunds extends BornInCloudTestBase {
	private String value;
	
	public Test_IPP_REST_Do_Refunds() throws IOException {
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_IPP_REST_Do_Refunds_VISA_Method() throws Exception{
		testScript();
	}
	
	@Test
	public void Test_IPP_REST_Do_Refunds_MASTERCARD_Method() throws Exception{
		testScript();
	}
	
	@Test
	public void Test_IPP_REST_Do_Refunds_Multiple_VISA_Method() throws Exception{
		testScript();
	}
	
	@Test
	public void Test_IPP_REST_Do_Refunds_Multiple_MASTERCARD_Method() throws Exception{
		testScript();
	}
	
	private void testScript() throws Exception{
		// Parsing test data values from test data file and creating a test data Hash
		System.out.println(" ---------- Method:" + tname.getMethodName() + " ---------- \n");
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		doPurchase(testDataMap);
		refund(testDataMap);
	}
	
	private void doPurchase(Map<String,String> testDataMap) throws Exception{
		Thread.sleep(7000);
		// Making a Get Rest call and retrieving the Json response in a string
		HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
		HttpResponse response = createEntitlement(client, testDataMap.get("ippRequest"), testDataMap.get("userId"), testDataMap.get("content"), testDataMap.get("jsonMimetype"));
					    
		compareStrings("Assert : Do purchase. Verify the Status Code", new Integer(response.getStatusLine().getStatusCode()).toString(), new Integer(HttpStatus.SC_CREATED).toString());
		compareStrings("Assert : Do purchase. Verify the Repsonse Format", testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
	
		// Verifying the Json Response from Rest Get Service
		String actualjsondata = (readJsonFromResponse(response));
		setValue(actualjsondata);

	    containStrings("Assert : Do purchase. Verifying the Json Response from Rest Create Service", actualjsondata, testDataMap.get("expectedjsondata").replaceAll(" ", ""));
	    client.getConnectionManager().shutdown();
	}
	
	public void refund(Map<String,String> testDataMap) throws Exception{
		String URL = testDataMap.get("ippRequest").split("user")[0] + "refund/" + getValue();
		HttpResponse response=null;
		int count = 0;
		do {
	    HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
			Thread.sleep(5000);
			/*debug*/System.out.println("URL:" + URL);
			response = updateEntitlement(client, "", URL, "", testDataMap.get("jsonMimetype"));
			count++;
			client.getConnectionManager().shutdown();
		} while (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK && count < 10);
		
		compareStrings("Assert : Refund. Verify the Status Code", new Integer(response.getStatusLine().getStatusCode()).toString(), new Integer(HttpStatus.SC_OK).toString());
		compareStrings("Assert : Refund. Verify the Repsonse Format", testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
	
		String actualjsondata = (readJsonFromResponse(response));
	    containStrings("Assert : Refund. Verifying the Json Response from Rest Create Service", actualjsondata, testDataMap.get("expectedJsonDataRefund").replaceAll(" ", ""));
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		Pattern pattern = Pattern.compile("\"id\":\"(\\d.*?)\"");
		Matcher matcher = pattern.matcher(value);

		String nameStr="";
		if(matcher.find())
		{
		    nameStr=matcher.group(1);
		}
		this.value = nameStr;
		/*debug*/ System.out.println("Purchase Order:" + value);
	}
	
}
