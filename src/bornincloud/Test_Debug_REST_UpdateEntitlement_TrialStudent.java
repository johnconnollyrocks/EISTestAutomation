package bornincloud;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;

/**
 * Test class - Test_Debug_REST_UpdateEntitlement_TrialStudent 
 * 
 * @author Michael Kandrashoff
 * @version 1.0.0
 */
public final class Test_Debug_REST_UpdateEntitlement_TrialStudent {
	
	@Test
	public void Test_UpdateEntitlement_TrialStudent_Method() throws ClientProtocolException, IOException, JSONException, ParseException {
		
		String jsonMimeType = "application/json";	
		HttpClient client = new DefaultHttpClient();
		String userId = "AutoScriptTrialStudent";
		
		String url = null;
		String offtable = null;
		String contextId = null;
		Long entLength = new Long(1095);
		Long trialLength = new Long(30);
		
		if (System.getProperty("environment").equalsIgnoreCase("DEV")){	
			url = "https://ent-dev.autodesk.com/service/entitlements/v1/user/";
			offtable = "Trial.json";
		}
		else if (System.getProperty("environment").equalsIgnoreCase("STG")){
			url = "https://ent-stg.autodesk.com/service/entitlements/v1/user/";
			offtable = "Trial_STG.json";
		}
		
		//HttpGet request = new HttpGet("https://ent-dev.autodesk.com/service/entitlements/v1/user/AutoTest2");
		HttpPost post = new HttpPost(url+userId);
		//List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	    //nameValuePairs.add(new BasicNameValuePair("offeringIds","FSN360-STUDENT"));
	    
	    String content = "{\r\n" + " \"offeringIds\":[\"FSN360-TRIAL\"]\r\n" + "}";
	    StringEntity entity = new StringEntity(content);
	    post.addHeader("Content-Type", "Application/json");
	    post.setEntity(entity);
	    //post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    HttpResponse response = client.execute(post);	
		
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
	    	System.out.println("Assertion Failed : Expected = " + jsonMimeType + ", Actual = " + mimeType + "\n");
	    }
		   		   
	    BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
	    String line = "";
	    String  actualjsondata = null;
	    while ((line = rd.readLine()) != null) {	    	
	    	System.out.println("Request Response: "+ "\n" + line);	    
	    	actualjsondata = line;
	    }
		
		ObjectMapper objectMapper = new ObjectMapper();		
		User user = objectMapper.readValue(actualjsondata, User.class);
		List<Entitlements> ActEntitlementList = user.getEntitlements();
		Map<String,String> UserEntMap = new HashMap<String,String>();
		Map<String,Long> ExpEntLengths = new HashMap<String,Long>();
		
		for (int i=0; i<ActEntitlementList.size(); i++) {
				String UserEntKey = userId + "|" + ActEntitlementList.get(i).getFeatureId();
				String UserEntValue = ActEntitlementList.get(i).getOfferingId() + "|" +
						ActEntitlementList.get(i).getOfferingLegalName() + "|" +
						ActEntitlementList.get(i).getOfferingTemplateId() + "|" +
				//		ActEntitlementList.get(i).getParentFeatureId() + "|" +
						ActEntitlementList.get(i).getFeatureName() + "|" +
						ActEntitlementList.get(i).getFeatureType() + "|" +
				//		ActEntitlementList.get(i).getFeatureNumDays() + "|" +
						ActEntitlementList.get(i).getFeatureLicensingModel() + "|" +
						ActEntitlementList.get(i).getactive();
		        if(ActEntitlementList.get(i).getEntitlementEndDate() == null || ActEntitlementList.get(i).getEntitlementEndDate().isEmpty()){
		        	//ExpEntLengths.put(ActEntitlementList.get(i).getFeatureName(), entLength);		        	
		        }
		        else {
		        	ExpEntLengths.put(ActEntitlementList.get(i).getFeatureName(), trialLength + entLength);
		        }
				UserEntMap.put(UserEntKey,UserEntValue);
		        contextId = ActEntitlementList.get(i).getContextId(); 
		}
			
		String path = new java.io.File(".").getCanonicalPath();
		path = path.replace("\\build", "");
		String fileLocation = path + "\\src\\bornincloud\\"+offtable;
		//InputStream inputStream = BornInCloud.class.getResourceAsStream("Trial.json");
		String offeringjsondata = new Scanner(new File(fileLocation)).useDelimiter("\\Z").next();
		Offering offering = objectMapper.readValue(offeringjsondata, Offering.class);
		List<FeatureList> ExpEntitlementList = offering.getFeatureList();
		Map<String,String> OffrEntMap = new HashMap<String,String>();
		
		for (int i=0; i<ExpEntitlementList.size(); i++) {
			if (ExpEntitlementList.get(i).getFeatureAddToEntsTable()){
				String UserEntKey = userId + "|" + ExpEntitlementList.get(i).getFeatureId();
				String UserEntValue = ExpEntitlementList.get(i).getOfferingId() + "|" +
						offering.getOfferingLegalName() + "|" +
						offering.getOfferingTemplate() + "|" +
				//		ExpEntitlementList.get(i).getParentFeatureId() + "|" +
						ExpEntitlementList.get(i).getFeatureName() + "|" +
						ExpEntitlementList.get(i).getFeatureType() + "|" +
			//			ExpEntitlementList.get(i).getFeatureNumDays() + "|" +
						ExpEntitlementList.get(i).getFeatureLicensingModel() + "|" +
						ExpEntitlementList.get(i).getFeatureAddToEntsTable();
				OffrEntMap.put(UserEntKey,UserEntValue);
			}
			
		}
		
		// Compare Hashmap and Print it to Console //
		assert user.getStatusCode().equals("ENTSYS-1000");		
		assert user.getStatusMessage().equals("Completed Successfully");		
		try{
	    	System.out.println("Assert : Verify the json Response" + "\n");
	    	assertEquals(OffrEntMap,UserEntMap);
	    	System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = "+ OffrEntMap +", Actual = " + UserEntMap + "\n");
	    	throw e;
	    }
		
		//Prepare and send Update Call //
		HttpPut put = new HttpPut(url+userId);
		String offtable1 = null;
		
		if (System.getProperty("environment").equalsIgnoreCase("DEV")){	
			url = "https://ent-dev.autodesk.com/service/entitlements/v1/user/";
			offtable1 = "Student.json";
		}
		else if (System.getProperty("environment").equalsIgnoreCase("STG")){
			url = "https://ent-stg.autodesk.com/service/entitlements/v1/user/";
			offtable1 = "Student_STG.json";
		}

		
	    String content1 = "{\r\n" + " \"offeringId\":\"FSN360-STUDENT\"\r\n" + ",\"contextId\":\"" + contextId + "\"\r\n" + "}";
	    
	    StringEntity entity1 = new StringEntity(content1);
	    put.addHeader("Content-Type", "Application/json");
	    put.setEntity(entity1);
	    HttpResponse response1 = client.execute(put);
	    
		//Verify that the OK response is retrieved
		try{
			System.out.println("Assert : Verify if the OK response was retrieved" + "\n");
			assertEquals(response1.getStatusLine().getStatusCode(),HttpStatus.SC_OK);
			System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = " + HttpStatus.SC_OK + ", Actual = " + response1.getStatusLine().getStatusCode() + "\n");
	    }
		
		//Verify that the response has the json format
		String mimeType1 = ContentType.getOrDefault(response1.getEntity()).getMimeType();
				
		try{
			System.out.println("Assert : Verify if the response has a json format" + "\n");
			assertEquals( jsonMimeType, mimeType1 );
			System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = " + jsonMimeType + ", Actual = " + mimeType1 + "\n");
	    }
		   		   
	    BufferedReader rd1 = new BufferedReader (new InputStreamReader(response1.getEntity().getContent()));
	    String line1 = "";
	    String  actualjsondata1 = null;
	    while ((line1 = rd1.readLine()) != null) {	    	
	    	System.out.println("Request Response: "+ "\n" + line1);	    
	    	actualjsondata1 = line1;
	    }
				
		User user1 = objectMapper.readValue(actualjsondata1, User.class);
		List<Entitlements> ActEntitlementList1 = user1.getEntitlements();
		Map<String,String> UserEntMap1 = new HashMap<String,String>();
		Map<String, Long> ActEntLengths = new HashMap<String, Long>();
		
		for (int i=0; i<ActEntitlementList1.size(); i++) {
				String UserEntKey = userId + "|" + ActEntitlementList1.get(i).getFeatureId();
				String UserEntValue = ActEntitlementList1.get(i).getOfferingId() + "|" +
						ActEntitlementList1.get(i).getOfferingLegalName() + "|" +
						ActEntitlementList1.get(i).getOfferingTemplateId() + "|" +
				//		ActEntitlementList1.get(i).getParentFeatureId() + "|" +
						ActEntitlementList1.get(i).getFeatureName() + "|" +
						ActEntitlementList1.get(i).getFeatureType() + "|" +
				//		ActEntitlementList1.get(i).getFeatureNumDays() + "|" +
						ActEntitlementList1.get(i).getFeatureLicensingModel() + "|" +
						ActEntitlementList1.get(i).getactive();
				
				if(ActEntitlementList1.get(i).getEntitlementEndDate() == null || ActEntitlementList1.get(i).getEntitlementEndDate().isEmpty()){
		        	//ExpEntLengths.put(ActEntitlementList.get(i).getFeatureName(), entLength);		        	
		        }
		        else {
		        	Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(ActEntitlementList1.get(i).getEntitlementStartDate());
					Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(ActEntitlementList1.get(i).getEntitlementEndDate());
					Long lengthDays = new Long((endDate.getTime() - startDate.getTime()) / 86400000);
					ActEntLengths.put(ActEntitlementList1.get(i).getFeatureName(), lengthDays);
		        }
				
//				Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(ActEntitlementList1.get(i).getEntitlementStartDate());
//				Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(ActEntitlementList1.get(i).getEntitlementEndDate());
//				Long lengthDays = new Long((endDate.getTime() - startDate.getTime()) / 86400000);
//				ActEntLengths.put(ActEntitlementList1.get(i).getFeatureName(), lengthDays);
				UserEntMap1.put(UserEntKey,UserEntValue); 
		}
			
		String path1 = new java.io.File(".").getCanonicalPath();
		path = path1.replace("\\build", "");
		String fileLocation1 = path1 + "\\src\\bornincloud\\"+offtable1;
		//InputStream inputStream = BornInCloud.class.getResourceAsStream("Trial.json");
		String offeringjsondata1 = new Scanner(new File(fileLocation1)).useDelimiter("\\Z").next();
		Offering offering1 = objectMapper.readValue(offeringjsondata1, Offering.class);
		List<FeatureList> ExpEntitlementList1 = offering1.getFeatureList();
		Map<String,String> OffrEntMap1 = new HashMap<String,String>();
		
		for (int i=0; i<ExpEntitlementList1.size(); i++) {
			if (ExpEntitlementList1.get(i).getFeatureAddToEntsTable()){
				String UserEntKey = userId + "|" + ExpEntitlementList1.get(i).getFeatureId();
				String UserEntValue = ExpEntitlementList1.get(i).getOfferingId() + "|" +
						offering1.getOfferingLegalName() + "|" +
						offering1.getOfferingTemplate() + "|" +
				//		ExpEntitlementList1.get(i).getParentFeatureId() + "|" +
						ExpEntitlementList1.get(i).getFeatureName() + "|" +
						ExpEntitlementList1.get(i).getFeatureType() + "|" +
			//			ExpEntitlementList1.get(i).getFeatureNumDays() + "|" +
						ExpEntitlementList1.get(i).getFeatureLicensingModel() + "|" +
						ExpEntitlementList1.get(i).getFeatureAddToEntsTable();
				OffrEntMap1.put(UserEntKey,UserEntValue);
			}
			
		}
		
		// Compare Hashmap and Print it to Console //
		assert user1.getStatusCode().equals("ENTSYS-1000");		
		assert user1.getStatusMessage().equals("Completed Successfully");		
		try{
	    	System.out.println("Assert : Verify the json Response" + "\n");
	    	assertEquals(OffrEntMap1,UserEntMap1);
	    	System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = "+ OffrEntMap1 +", Actual = " + UserEntMap1 + "\n");
	    	throw e;
	    }
	    
		try{
	    	System.out.println("Assert : Honor the Trial" + "\n");
	    	assertEquals(ExpEntLengths, ActEntLengths);
	    	System.out.println("Assert Passed" + "\n");
	    }
	    catch (AssertionError e){
	    	System.out.println("Assertion Failed : Expected = "+ ExpEntLengths +", Actual = " + ActEntLengths + "\n");
	    	throw e;
	    }
		
		
	    //Run GET to confirm entitlements were updated properly//
		String url1 = url + userId + "?contextId=" + contextId;
		HttpGet get = new HttpGet(url1);
		
	    HttpResponse response2 = client.execute(get);
		
	  //Verify that the OK response is retrieved
	  		try{
	  			System.out.println("Assert : Verify if the OK response was retrieved" + "\n");
	  			assertEquals(response2.getStatusLine().getStatusCode(),HttpStatus.SC_OK);
	  			System.out.println("Assert Passed" + "\n");
	  	    }
	  	    catch (AssertionError e){
	  	    	System.out.println("Assertion Failed : Expected = " + HttpStatus.SC_OK + ", Actual = " + response2.getStatusLine().getStatusCode() + "\n");
	  	    }
	  		
	  		//Verify that the response has the json format
	  		String mimeType2 = ContentType.getOrDefault(response2.getEntity()).getMimeType();
	  				
	  		try{
	  			System.out.println("Assert : Verify if the response has a json format" + "\n");
	  			assertEquals( jsonMimeType, mimeType2 );
	  			System.out.println("Assert Passed" + "\n");
	  	    }
	  	    catch (AssertionError e){
	  	    	System.out.println("Assertion Failed : Expected = " + jsonMimeType + ", Actual = " + mimeType2 + "\n");
	  	    }
	  		   		   
	  	    BufferedReader rd2 = new BufferedReader (new InputStreamReader(response2.getEntity().getContent()));
	  	    String line2 = "";
	  	    String  actualjsondata2 = null;
	  	    while ((line2 = rd2.readLine()) != null) {	    	
	  	    	System.out.println("Request Response: "+ "\n" + line2);	    
	  	    	actualjsondata2 = line2;
	  	    }
	  				
	  		User user2 = objectMapper.readValue(actualjsondata1, User.class);
	  		List<Entitlements> ActEntitlementList2 = user2.getEntitlements();
	  		Map<String,String> UserEntMap2 = new HashMap<String,String>();
	  		
	  		
	  		for (int i=0; i<ActEntitlementList2.size(); i++) {
	  				String UserEntKey = userId + "|" + ActEntitlementList2.get(i).getFeatureId();
	  				String UserEntValue = ActEntitlementList2.get(i).getOfferingId() + "|" +
	  						ActEntitlementList2.get(i).getOfferingLegalName() + "|" +
	  						ActEntitlementList2.get(i).getOfferingTemplateId() + "|" +
	  				//		ActEntitlementList2.get(i).getParentFeatureId() + "|" +
	  						ActEntitlementList2.get(i).getFeatureName() + "|" +
	  						ActEntitlementList2.get(i).getFeatureType() + "|" +
	  				//		ActEntitlementList2.get(i).getFeatureNumDays() + "|" +
	  						ActEntitlementList2.get(i).getFeatureLicensingModel() + "|" +
	  						ActEntitlementList2.get(i).getactive();
	  		        UserEntMap2.put(UserEntKey,UserEntValue); 
	  		}
	  		
			// Compare Hashmap and Print it to Console //
			assert user2.getStatusCode().equals("ENTSYS-1000");		
			assert user2.getStatusMessage().equals("Completed Successfully");		
			try{
		    	System.out.println("Assert : Verify the json Response" + "\n");
		    	assertEquals(OffrEntMap1,UserEntMap2);
		    	System.out.println("Assert Passed" + "\n");
		    }
		    catch (AssertionError e){
		    	System.out.println("Assertion Failed : Expected = "+ OffrEntMap1 +", Actual = " + UserEntMap2 + "\n");
		    	throw e;
		    }
	  		
	}
}
