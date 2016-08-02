package bornincloud;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test class - Need add new name
 * 
 * US2580:DEF: Payport: AddPaymentProfile
 * Validate country code in response
 * 
 * A encrypted VISA and Mastercard in DEV environment. 
 * Create a user in portal and make not of the the externalKey
 * Create the user in Pelican via the PSP API
 * AddPaymentProfile for VISA.
 * AddPaymentProfile for MASTERCARD.
 * Expected results:  After the step 3 we expect to receive created Mastercard profile.
 * 
 * Test_Rest_STG.json
 * {
    "testName": "Test_IPP_PortalCreateUser_PayportAddPaymentProfile_Method",
    "content": "<createNewUser request>##<addPaymentProfileMASETRCARD request>",
    "expectedJsonData": "<createNewUser response>##<addPaymentProfileMASETRCARD response>##<getPaymentProfiles[1]@@@getPaymentProfiles[2]\>"
    },
 * 
 * @author Denis Disciuc
 * @version 1.0.0
 */
public class Test_IPP_PortalCreateUser_PayportAddPaymentProfile extends BornInCloudTestBase {
	private String sessionID;	
	private String  userID;
	
	public Test_IPP_PortalCreateUser_PayportAddPaymentProfile() throws IOException {
		super("browser","chrome");					
	}
	@Rule
	public TestName tname = new TestName();
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void Test_IPP_PortalCreateUser_PayportAddPaymentProfile_Method() throws Exception {
		createNewUserPortal();
		
		System.out.println(" ---------- Method:" + tname.getMethodName() + " ---------- \n");
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		createNewUser(testDataMap);
		addPaymentProfileMASETRCARD(testDataMap);
		getPaymentProfiles(testDataMap);
	}
	
	private String createNewUserPortal() throws Exception{
		Map<String, String> userDetails= new TreeMap<String, String>();
		userDetails=createCustomerPortalUserID();
		sessionID=userDetails.get("sessionID");
		userID=userDetails.get("userID");
		logoutMyAutodeskPortal();
		return userID;
	}
	
	private void createNewUser(Map<String,String> testDataMap) throws Exception{
		System.out.println("--------------- createNewUser --------------- ");
		String actualRequestJson=getStringAfterReplacingData(testDataMap.get("content").split("##")[0]);
		postRequest(testDataMap, sessionID + "", actualRequestJson, testDataMap.get("expectedjsondata").split("##")[0]);
	}
	private void addPaymentProfileMASETRCARD(Map<String,String> testDataMap) throws Exception{
		System.out.println("--------------- addPaymentProfileMASETRCARD --------------- ");
		Thread.sleep(7000);
		String actualRequestJson=getStringAfterReplacingData(testDataMap.get("content").split("##")[1]);
		postRequest(testDataMap, sessionID + "/paymentProfiles", actualRequestJson, testDataMap.get("expectedjsondata").split("##")[1]);
		
	}
	
	private void getPaymentProfiles(Map<String,String> testDataMap) throws Exception{
		System.out.println("--------------- getPaymentProfiles --------------- ");
		Thread.sleep(7000);
		getPPRequest(testDataMap, sessionID + "/paymentProfiles", testDataMap.get("expectedjsondata").split("##")[2]);
	}
	
	private void postRequest(Map<String,String> testDataMap, String URL, String content, String expectedjsondata) throws Exception{
		HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
		HttpResponse response = createEntitlement(client, testDataMap.get("ippRequest"), URL, content, testDataMap.get("jsonMimetype"));
		/*debug*/System.out.println("url:" + testDataMap.get("ippRequest")+URL);
		/*debug*/System.out.println("request:" + content);
					    
		compareStrings("Assert : Verify the Status Code", new Integer(response.getStatusLine().getStatusCode()).toString(), new Integer(HttpStatus.SC_CREATED).toString());
		compareStrings("Assert : Verify the Repsonse Format", testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
	
		String actualjsondata = (readJsonFromResponse(response));
	    /*debug*/System.out.println("response:" + actualjsondata.replaceAll(" ", ""));
	    containStrings("Assert : Verifying the Json Response from Rest Create Service", actualjsondata.replaceAll(" ", ""), expectedjsondata.replaceAll(" ", "").split("@@@")[0]);
		containStrings("Assert : Verifying the Json Response from Rest Create Service", actualjsondata.replaceAll(" ", ""), expectedjsondata.replaceAll(" ", "").split("@@@")[1]);
	    client.getConnectionManager().shutdown();
	}
	
	private void getPPRequest(Map<String,String> testDataMap, String URL, String expectedjsondata) throws Exception{
		HttpResponse response =  getRequest(testDataMap, testDataMap.get("ippRequest"), URL);
		
		compareStrings("Assert : Verify the Status Code", new Integer(response.getStatusLine().getStatusCode()).toString(), new Integer(HttpStatus.SC_OK).toString());
		compareStrings("Assert : Verify the Repsonse Format", testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
	
		String actualjsondata = (readJsonFromResponse(response));
		 /*debug*///System.out.println("url:" + testDataMap.get("ippRequest")+ testDataMap.get("userId")+"/bluesnap");
		 /*debug*///System.out.println("response:" + actualjsondata.replaceAll(" ", ""));
		containStrings("Assert : Verifying the GET Json Response from Rest Create Service", actualjsondata.replaceAll(" ", ""), expectedjsondata.replaceAll(" ", "").split("@@@")[0]);
		containStrings("Assert : Verifying the GET Json Response from Rest Create Service", actualjsondata.replaceAll(" ", ""), expectedjsondata.replaceAll(" ", "").split("@@@")[1]);
	}
	
	public String getStringAfterReplacingData(String string){
		String temp1=string.replace("#devVISANumber#", testProperties.getConstant("devVISANumber"));
		string=temp1.replace("#devVISACVV#", testProperties.getConstant("devVISACVV"));
		temp1=string.replace("#devMCNumber#", testProperties.getConstant("devMCNumber"));
		string=temp1.replace("#devMCCVV#", testProperties.getConstant("devMCCVV"));
		
		temp1=string.replace("#stgVISANumber#", testProperties.getConstant("stgVISANumber"));
		string=temp1.replace("#stgVISACVV#", testProperties.getConstant("stgVISACVV"));
		temp1=string.replace("#stgMCNumber#", testProperties.getConstant("stgMCNumber"));
		string=temp1.replace("#stgMCCVV#", testProperties.getConstant("stgMCCVV"));
		return string;
	}

}
