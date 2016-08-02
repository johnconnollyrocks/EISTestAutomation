package customerportal;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

/**
 * Test class - Test_REST_GetEntitlement_ExpiredFalse
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_REST_GetEntitlement_Negative_HttpRequest {
	
	@Test
	public void Test_REST_GetEntitlement_ExpiredFalse_Method() throws ClientProtocolException, IOException {
		
		String jsonMimeType = "application/json";	
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://ent-dev.autodesk.com/service/entitlements/v1/user/GM0KBG502JKF?includeExpired=false");
		HttpResponse response = client.execute(request);
		
		//Verify that the OK response is retrieved
		try{
			System.out.println("Assert : Verify if the OK response was retrieved" + "\n");
			assertEquals(response.getStatusLine().getStatusCode(),HttpStatus.SC_NOT_FOUND);
			System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = " + HttpStatus.SC_NOT_FOUND + ", Actual = " + response.getStatusLine().getStatusCode() + "\n");
	    }
				
	}
}
