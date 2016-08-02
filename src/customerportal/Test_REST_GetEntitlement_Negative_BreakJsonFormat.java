package customerportal;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

/**
 * Test class - Test_REST_GetEntitlement_Negative_BreakJsonFormat
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_REST_GetEntitlement_Negative_BreakJsonFormat {
	
	@Test
	public void Test_REST_GetEntitlement_Negative_BreakJsonFormat_Method() throws ClientProtocolException, IOException {
		
		String jsonMimeType = "application/json";	
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("https://ent-dev.autodesk.com/service/entitlements/v1/user/K8GZPMN7AJ2E");
		HttpResponse response = client.execute(request);
		
		//Verify that the OK response is retrieved
		try{
			System.out.println("Assert : Verify if the OK response was retrieved" + "\n");
			assertEquals(response.getStatusLine().getStatusCode(),HttpStatus.SC_OK);
			System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = " + HttpStatus.SC_OK + ", Actual = " + response.getStatusLine().getStatusCode() + "\n");
	    }
		
		//Verify that the response has the json format
		String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
				
		try{
			System.out.println("Assert : Verify if the response has a json format" + "\n");
			assertEquals( jsonMimeType, mimeType );
			System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = " + mimeType + ", Actual = " + jsonMimeType + "\n");
	    }
		   		   
	    BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
	    String line = "";
	    String  actualjsondata = null;
	    while ((line = rd.readLine()) != null) {	    	
	    	System.out.println("Request Response: "+ "\n" + line);	    
	    	actualjsondata = line;
	    }
	    
	    String Expectedjsondata = "{\"statusCode\":\"ENTSYS-1000\",\"statusMessage\":\"Completed successfully\",\"entitlements\":[{\"userId\":\"K8GZPMN7AJ2E\",\"entitlementId\":\"2daa3b1d-d275-4cdb-a6f2-f14d6b31646d\",\"contextId\":\"0251b8f8-e017-491f-8cc2-fb2a81cbdeac\",\"entitlementStartDate\":\"2014-41-24\",\"entitlementEndDate\":\"2014-06-05\",\"offeringId\":\"FSN360-TRIAL}\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"ID1]\",\"featureName\":\"Import\",\"featureType\":\"AO\",\"featureLicensingModel\":\"OAC\",\"active\":true},{\"userId\":\"K8GZPMN7AJ2E\",\"entitlementId\":\"424e48b3-fddd-45e9-8a67-3af5e5618238\",\"contextId\":\"0251b8f8-e017-491f-8cc2-fb2a81cbdeac\",\"entitlementStartDate\":\"2014-41-24\",\"entitlementEndDate\":\"2014-06-05\",\"offeringId\":\"FSN360-TRIAL\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"FSN360\",\"featureName\":\"Fusion 360 Core Product\",\"featureType\":\"CP\",\"featureLicensingModel\":\"TRL\",\"active\":true},{\"userId\":\"K8GZPMN7AJ2E\",\"entitlementId\":\"ce6b12d7-6ad4-4d78-be0c-2ba869d9631b\",\"contextId\":\"0251b8f8-e017-491f-8cc2-fb2a81cbdeac\",\"entitlementStartDate\":\"2014-41-24\",\"entitlementEndDate\":\"2014-06-05\",\"offeringId\":\"FSN360-TRIAL\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"RenderingWS\",\"featureName\":\"Rendering\",\"featureType\":\"CS\",\"featureLicensingModel\":\"CNP\",\"active\":true},{\"userId\":\"K8GZPMN7AJ2E\",\"entitlementId\":\"d88426f4-b01d-498b-8b82-046cc72080cc\",\"contextId\":\"0251b8f8-e017-491f-8cc2-fb2a81cbdeac\",\"entitlementStartDate\":\"2014-41-24\",\"entitlementEndDate\":\"2014-06-05\",\"offeringId\":\"FSN360-TRIAL\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"ID2\",\"featureName\":\"Export\",\"featureType\":\"AO\",\"featureLicensingModel\":\"TRL\",\"active\":true},{\"userId\":\"K8GZPMN7AJ2E\",\"entitlementId\":\"da30fa17-6e1e-490a-94f2-bbd0d558ea94\",\"contextId\":\"0251b8f8-e017-491f-8cc2-fb2a81cbdeac\",\"entitlementStartDate\":\"2014-41-24\",\"entitlementEndDate\":\"2014-06-05\",\"offeringId\":\"FSN360-TRIAL\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"ID3\",\"featureName\":\"HB\",\"featureType\":\"CP\",\"featureLicensingModel\":\"OAC\",\"active\":true}]}";
	    try{
	    	System.out.println("Assert : Verify the json Response" + "\n");
	    	assertEquals(Expectedjsondata,actualjsondata);
	    	System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = "+ Expectedjsondata +", Actual = " + actualjsondata + "\n");
	    }	
	}
}
