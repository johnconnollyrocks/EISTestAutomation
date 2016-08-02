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
 * Test class - Test_REST_GetEntitlement_UserId 
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_REST_UpdateEntitlement_RentalRevoke extends BornInCloudTestBase {
	
	public Test_REST_UpdateEntitlement_RentalRevoke() throws IOException {
		super("Backend","Browser",getAppBrowser());
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
		
	@Test
	public void Test_REST_UpdateEntitlement_RentalRevoke_Method() throws Exception {
		
		// Declaration of all the static variables needed for the script
		// This will be read from a Test properties files (json object) in near future
		
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));	   
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		/*String jsonMimeType = "application/json";
		String userId = "AutoScriptCancelReactivate";
		String contenttowrite = "{\r\n" + " \"offeringIds\":[\"FSN360-TRIAL\"]\r\n" + "}";
		String url = null;
		String offtable = null;
		String offtable1 = null;
		
		// This is also a test data set up for DEV and STG 	
		if (System.getProperty("environment").equalsIgnoreCase("DEV")){	
			url = "https://ent-dev.autodesk.com/service/entitlements/v1/user/";
			offtable = "Trial.json";
			offtable1 = "Rental1.json";
		}
		else if (System.getProperty("environment").equalsIgnoreCase("STG")){
			url = "https://ent-stg.autodesk.com/service/entitlements/v1/user/";
			offtable = "Trial_STG.json";
			offtable1 = "Rental1_STG.json";
		}	*/
		
		// *********** Creating a Trial Entitlement and Verifying the response ***************
	    
		// This method executes a REST Post call and returns the response
		
		HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
		HttpResponse postresponse = createEntitlement(client, testDataMap.get("url"), testDataMap.get("userId"), testDataMap.get("content"), testDataMap.get("jsonMimetype"));
		/*HttpClient client = getClient("localtesting-stg.pfx", "localtesting!@3");
	    HttpResponse postresponse = createEntitlement(client, url, userId, contenttowrite, jsonMimeType);*/
	   
	    // Verifying the Status Code 
	    System.out.println("Assert : Verify the Status Code" + "\n");
	    compareStrings(postresponse.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
	    
	    // Verifying the Response Format
	    System.out.println("Assert : Verify the Repsonse Format" + "\n");
	    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(postresponse.getEntity()).getMimeType());
	    
	    String actualjsondata = readJsonFromResponse(postresponse);	
	    String contextId = findjsonelement(actualjsondata);
	    Map<String,String> UserEntMap = createEntMap(actualjsondata, testDataMap.get("userId"), "Actual", "Create", "Trial");
	    
	    //String expectedjsondata = readJsonFromoffering(offtable);
	    String expectedjsondata = readJsonFromoffering(testDataMap.get("offTable1"));
	    Map<String,String> OffrEntMap = createEntMap(expectedjsondata, testDataMap.get("userId"), "Expected", "Create", "Trial");
	    
	    System.out.println("\n" + "Assert : Verify the Created Entitlements(Trial)" + "\n");
	    compareMaps(UserEntMap, OffrEntMap);
		
	    // ********** Updating the Trial Entitlement to Rental Entitlement *******************
	    
	    String contenttoupdate = "{\"contextId\": \""+ contextId + "\",\"offeringId\":\"FSN360-BASE-M\"}";
	    
	    HttpResponse putresponse = updateEntitlement(client, testDataMap.get("userId"), testDataMap.get("url"), contenttoupdate, testDataMap.get("jsonMimetype"));
	    
	    // Verifying the Status Code 
	    System.out.println("Assert : Verify the Status Code" + "\n");
	    compareStrings(putresponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	    
	    // Verifying the Response Format
	    System.out.println("Assert : Verify the Repsonse Format" + "\n");
	    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(putresponse.getEntity()).getMimeType());
	    
	    String actualjsondataupdate = readJsonFromResponse(putresponse);	
	    Map<String,String> UserEntMapupdate = createEntMap(actualjsondataupdate, testDataMap.get("userId"), "Actual", "Update", "Rental");	    
	    
	    //offtable = "Rental1.json";
	    //String expectedjsondataupdate = readJsonFromoffering(offtable1);
	    String expectedjsondataupdate = readJsonFromoffering(testDataMap.get("offTable2"));
	    Map<String,String> OffrEntMapupdate = createEntMap(expectedjsondataupdate, testDataMap.get("userId"), "Expected", "Update", "Rental");
	    
	    System.out.println("\n" + "Assert : Verify the updated Entitlements(Rental-1)" + "\n");
	    compareMaps(UserEntMapupdate, OffrEntMapupdate);
	    
	    
	    // ********** Updating the Rental to REVOKE ******************
	    
	    String contentcancel = "{\"contextId\": \""+ contextId + "\",\"offeringId\":\"FSN360-BASE-M\"}";
	    
	    HttpResponse putcancelresponse = updateEntitlement(client, testDataMap.get("userId")+"?action=REVOKE", testDataMap.get("url"), contentcancel, testDataMap.get("jsonMimetype"));
	    
	    // Verifying the Status Code 
	    System.out.println("Assert : Verify the Status Code" + "\n");
	    compareStrings(putcancelresponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	    
	    // Verifying the Response Format
	    System.out.println("Assert : Verify the Repsonse Format" + "\n");
	    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(putcancelresponse.getEntity()).getMimeType());
	    
	    String actualjsondatacancel = readJsonFromResponse(putcancelresponse);	
	    Map<String,String> UserEntMapcancel= createEntMap(actualjsondatacancel, testDataMap.get("userId"), "Actual", "Update", "Revoke");	    
	    
	    //offtable = "Rental1.json";
	    String expectedjsondatacancel= readJsonFromoffering(testDataMap.get("offTable2"));
	    Map<String,String> OffrEntMapcancel = createEntMap(expectedjsondatacancel, testDataMap.get("userId"), "Expected", "Update", "Revoke");
	    
	    System.out.println("\n" + "Assert : Verify the Revoked Entitlements" + "\n");
	    compareMaps(UserEntMapcancel, OffrEntMapcancel);	    
	   
	    
	   	// ********** Get all the Entitlements after REVOKE ******************
	    
	    HttpResponse getcancelresponse = getEntitlement(client, testDataMap.get("url"), testDataMap.get("userId"), "?contextId="+contextId+"&includeExpired=True");
	    
	    
	    // Verifying the Status Code 
	    System.out.println("Assert : Verify the Status Code" + "\n");
	    compareStrings(getcancelresponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	    
	    // Verifying the Response Format
	    System.out.println("Assert : Verify the Repsonse Format" + "\n");
	    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(getcancelresponse.getEntity()).getMimeType());
	    
	    String actgetcanceljsondata = readJsonFromResponse(getcancelresponse);	
	    Map<String,String> UserEntMapgetcancel= createEntMap(actgetcanceljsondata, testDataMap.get("userId"), "Actual", "Get", "Revoke");	    
	    
	    //offtable = "Rental1.json";
	    //String expgetcanceljsondata= readJsonFromoffering(offtable1);
	    String expgetcanceljsondata= readJsonFromoffering(testDataMap.get("offTable2"));
	    Map<String,String> OffrEntMapgetcancel = createEntMap(expgetcanceljsondata, testDataMap.get("userId"), "Expected", "Get", "Revoke");
	    
	    System.out.println("\n" + "Assert : Verify All the Entitlements from GET" + "\n");
	    compareMaps(UserEntMapgetcancel, OffrEntMapgetcancel);	
	    client.getConnectionManager().shutdown();
	}	
}
