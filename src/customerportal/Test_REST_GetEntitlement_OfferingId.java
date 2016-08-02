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
 * Test class - Test_REST_GetEntitlement_OfferingId
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_REST_GetEntitlement_OfferingId {
	
	@Test
	public void Test_REST_GetEntitlement_OfferingId_Method() throws ClientProtocolException, IOException {
		
		String jsonMimeType = "application/json";	
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("https://ent-dev.autodesk.com/service/entitlements/v1/user/PW8SLA273YFD?offeringId=FSN360-TRIAL");
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
	    
	    String Expectedjsondata = "{\"statusCode\":\"ENTSYS-1000\",\"statusMessage\":\"Completed successfully\",\"entitlements\":[{\"userId\":\"PW8SLA273YFD\",\"entitlementId\":\"a07bfe96-c19a-4df0-80f5-3d7d8c1dca81\",\"contextId\":\"4ff80b61-6306-4588-9f45-dba2b3ba8428\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"2014-03-13\",\"offeringId\":\"FSN360-TRIAL\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"ID3\",\"featureName\":\"HB\",\"featureType\":\"CP\",\"featureLicensingModel\":\"OAC\",\"active\":true},{\"userId\":\"PW8SLA273YFD\",\"entitlementId\":\"a7fe9d6f-6d68-454d-b453-742d5d031335\",\"contextId\":\"4ff80b61-6306-4588-9f45-dba2b3ba8428\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"2014-03-13\",\"offeringId\":\"FSN360-TRIAL\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"ID1\",\"featureName\":\"Import\",\"featureType\":\"AO\",\"featureLicensingModel\":\"OAC\",\"active\":true},{\"userId\":\"PW8SLA273YFD\",\"entitlementId\":\"5fab7198-6e73-4a13-b498-9885004b9eff\",\"contextId\":\"4ff80b61-6306-4588-9f45-dba2b3ba8428\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"2014-03-13\",\"offeringId\":\"FSN360-TRIAL\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"RenderingWS\",\"featureName\":\"Rendering\",\"featureType\":\"CS\",\"featureLicensingModel\":\"CNP\",\"active\":true},{\"userId\":\"PW8SLA273YFD\",\"entitlementId\":\"87ea352e-b0b3-4be2-9b66-aee9310b4e76\",\"contextId\":\"4ff80b61-6306-4588-9f45-dba2b3ba8428\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"2014-03-13\",\"offeringId\":\"FSN360-TRIAL\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"ID2\",\"featureName\":\"Export\",\"featureType\":\"AO\",\"featureLicensingModel\":\"TRL\",\"active\":true},{\"userId\":\"PW8SLA273YFD\",\"entitlementId\":\"1f1af800-1d3c-491f-bb55-47394fcfdf83\",\"contextId\":\"4ff80b61-6306-4588-9f45-dba2b3ba8428\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"2014-03-13\",\"offeringId\":\"FSN360-TRIAL\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"FSN360\",\"featureName\":\"Fusion 360 Core Product\",\"featureType\":\"CP\",\"featureLicensingModel\":\"TRL\",\"active\":true}]}";
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