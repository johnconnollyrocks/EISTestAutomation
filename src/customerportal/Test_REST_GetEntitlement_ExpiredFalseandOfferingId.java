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
 * Test class - Test_REST_GetEntitlement_ExpiredFalseandOfferingId
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_REST_GetEntitlement_ExpiredFalseandOfferingId {
	
	@Test
	public void Test_REST_GetEntitlement_ExpiredFalseandOfferingId_Method() throws ClientProtocolException, IOException {
		
		String jsonMimeType = "application/json";	
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("https://ent-dev.autodesk.com/service/entitlements/v1/user/GM0KBG502JKF?includeExpired=false&offeringId=FSN360-RENTAL-2");
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
	    
	    String Expectedjsondata = "{\"statusCode\":\"ENTSYS-1000\",\"statusMessage\":\"Completed successfully\",\"entitlements\":[{\"userId\":\"GM0KBG502JKF\",\"entitlementId\":\"c34bb917-bebf-4ed3-abe2-7931fba4a477\",\"contextId\":\"20980661-21c0-4109-80ca-80fa1a1c364d\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"3333-12-31\",\"offeringId\":\"FSN360-RENTAL-2\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"ID1\",\"featureName\":\"Import\",\"featureType\":\"AO\",\"featureLicensingModel\":\"OAC\",\"active\":true},{\"userId\":\"GM0KBG502JKF\",\"entitlementId\":\"8136026a-157c-40fe-810e-14395720d299\",\"contextId\":\"20980661-21c0-4109-80ca-80fa1a1c364d\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"3333-12-31\",\"offeringId\":\"FSN360-RENTAL-2\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"ID2\",\"featureName\":\"Export\",\"featureType\":\"AO\",\"featureLicensingModel\":\"RNT\",\"active\":true},{\"userId\":\"GM0KBG502JKF\",\"entitlementId\":\"d5ffe661-e8be-44aa-9ae6-9dae8d87a9df\",\"contextId\":\"20980661-21c0-4109-80ca-80fa1a1c364d\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"3333-12-31\",\"offeringId\":\"FSN360-RENTAL-2\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"ID3\",\"featureName\":\"HB\",\"featureType\":\"CP\",\"featureLicensingModel\":\"OAC\",\"active\":true},{\"userId\":\"GM0KBG502JKF\",\"entitlementId\":\"e3728dc6-b29e-4244-90ff-16f335b7bc9f\",\"contextId\":\"20980661-21c0-4109-80ca-80fa1a1c364d\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"3333-12-31\",\"offeringId\":\"FSN360-RENTAL-2\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"FSN360\",\"featureName\":\"Fusion 360 Core Product\",\"featureType\":\"CP\",\"featureLicensingModel\":\"RNT\",\"active\":true},{\"userId\":\"GM0KBG502JKF\",\"entitlementId\":\"8b59684d-acbf-48c8-9942-f56374f8cff9\",\"contextId\":\"20980661-21c0-4109-80ca-80fa1a1c364d\",\"entitlementStartDate\":\"2014-00-24\",\"entitlementEndDate\":\"3333-12-31\",\"offeringId\":\"FSN360-RENTAL-2\",\"parentFeatureId\":\"FSN360\",\"featureId\":\"RenderingWS\",\"featureName\":\"Rendering\",\"featureType\":\"CS\",\"featureLicensingModel\":\"CNP\",\"active\":true}]}";
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
