package bornincloud;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

/**
 * Test class - Test_REST_GetEntitlement_FeatureId
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_IPP_Redirect_Header {
	
	@Test
	public void Test_REST_GetEntitlement_FeatureId_Method() throws ClientProtocolException, IOException {
		
		HttpClient client = new DefaultHttpClient();
		                     
		String url = null;
		String Expecteddata  = null;
		
		if (System.getProperty("environment").equalsIgnoreCase("DEV")){	
			url = "https://depot-dev.autodesk.com/service/ipp/v1/ippredirect";
			Expecteddata = "https://secure.avangate.com/order/pf.php?MERCHANT=AUTODESK&BILL_EMAIL=randomtest@test.com&BILL_COUNTRYCODE=UnitedStates&URL=https%3A%2F%2Fsecure.avangate.com%2Forder%2Fcheckout.php%3FPRODS%3D4614521%2C4614466%26QTY%3D1%2C1%26CLEAN_CART%3D1%26ORDERSTYLE%3DnLXOnJWppn4%3D%26REF%3D9999999999%26CUSTOMERID%3Drandomid%26LANG%3DEnglish";
		}
		else if (System.getProperty("environment").equalsIgnoreCase("STG")){
			url = "https://ent-stg.autodesk.com/service/entitlements/v1/user/";
			Expecteddata = "";
		}
		
		HttpContext localContext  = new BasicHttpContext(); 
		
		HttpParams params = new BasicHttpParams();
		params.setParameter("http.protocol.handle-redirects",false);
		
		// Create a local instance of cookie store
		CookieStore cookieStore = new BasicCookieStore();

		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		
		HttpGet request = new HttpGet(url+"?contextId=9999999999&userId=randomid&lang=English&country=UnitedStates&email=randomtest@test.com");
		request.setParams(params);
		HttpResponse response = client.execute(request, localContext);
		// HTTP parameters stores header etc.
		String redirectLocation = null;
		// obtain redirect target
		Header locationHeader = response.getFirstHeader("location");
		if (locationHeader != null) {
		    redirectLocation = locationHeader.getValue();
		    //System.out.println("loaction: " + redirectLocation);
		} else {
		  // The response is invalid and did not provide the new location for
		  // the resource.  Report an error or possibly handle the response
		  // like a 404 Not Found error.
		}
				
		try{
	    	System.out.println("Assert : Verify the URL" + "\n");
	    	assertEquals(Expecteddata,redirectLocation);
	    	System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = "+ Expecteddata +", Actual = " + redirectLocation + "\n");
	    	throw e;
	    }	
	
		
//		//Verify that the OK response is retrieved
//		try{
//			System.out.println("Assert : Verify if the OK response was retrieved" + "\n");
//			assertEquals(response.getStatusLine().getStatusCode(),HttpStatus.SC_OK);
//			System.out.println("Assert Passed" + "\n");
//	    }
//	    catch (AssertionError e){
//	    	System.out.println("Assertion Failed : Expected = " + HttpStatus.SC_OK + ", Actual = " + response.getStatusLine().getStatusCode() + "\n");
//	    }
//		
//		HttpUriRequest currentReq = (HttpUriRequest) localContext.getAttribute(ExecutionContext.HTTP_REQUEST);
//		HttpHost currentHost = (HttpHost)  localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
//		String currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString() : (currentHost.toURI() + currentReq.getURI());
//		
	

	}
}
