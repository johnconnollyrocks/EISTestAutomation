package pelican;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.Util;


public class ServiceUtils extends bicPelicanWebService {

	static final String DATEFORMAT = "MM/dd/yyyy";

	public static String GetUTCdatetimeAsString()
	{
		final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		final String utcTime = sdf.format(new Date());

		return utcTime;
	}

	public static String GetNextMonthdateAsString() throws ParseException
	{

		String dt = GetUTCdatetimeAsString();  
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.MONTH, 1);  //
		dt = sdf.format(c.getTime());
		return dt;
	}


	public String createSubscription(String environment, String externalKey, String productExternalKey) throws Exception{
		String subscriptionId = null;
		Document responseDoc = null;
		String createSubsUrl="";

		createSubsUrl= EnvironmentURL(environment)+"/tfel2rs/v2/subscription";

		String createSubsRequest= "userExternalKey="+externalKey+"&subscriptionOfferExternalKey="+productExternalKey+"&currencyName=USD";

		HttpResponse createSubsResponse = (HttpResponse) executePOSTWithHMAC(createSubsUrl,createSubsRequest,"application/x-www-form-urlencoded; charset=UTF-8");
		System.out.println("Request Body:  " +createSubsRequest);

		if (createSubsResponse != null && createSubsResponse.getStatusLine().getStatusCode() != 200 ){
			printErrorResponse(createSubsResponse);
			throw new AssertionError("The Response code is "+createSubsResponse.getStatusLine().getStatusCode()+", but expected was 200");
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		DocumentBuilder db = dbf.newDocumentBuilder();  
		HttpEntity entity = createSubsResponse.getEntity();

		if (createSubsResponse != null) {  
			InputStream inputStream = entity.getContent();  
			responseDoc = db.parse(inputStream);  
			responseDoc.getDocumentElement().normalize();


			Element rootNode= responseDoc.getDocumentElement();

			subscriptionId=rootNode.getAttribute("id");

		}
		return subscriptionId;
	}
	
	/**
	 * @Description create user in pelican and assoicate with existing O2 id
	 * @param environment
	 * @param associatedExternalO2Key
	 * @return
	 * @throws Exception
	 */

	
	public  String[] createNewUserwithPelican(String environment) throws Exception{
		

		String[] userDetails = new String[2];
		Document getResponseDoc = null;
		Document createResponseDoc = null;
		String createUserUrl="";
		String getUserUrl="";
		Random randomGenerator = new Random(); 
		int randomNumber=((1+ randomGenerator.nextInt(5)) * 10000 +randomGenerator.nextInt(10000));
	
		createUserUrl= EnvironmentURL(environment)+"/tfel2rs/v2/user";
		
		String createUserRequest= "name=AutomationUser"+randomNumber;
		Util.PrintInfo("User name is "+createUserRequest);

		HttpResponse createUserResponse = (HttpResponse) executePOSTWithHMAC(createUserUrl,createUserRequest,"application/x-www-form-urlencoded; charset=UTF-8");

		if (createUserResponse != null && createUserResponse.getStatusLine().getStatusCode() != HttpStatusCreatedCode_OK ){
			printErrorResponse(createUserResponse);
			throw new AssertionError("The Response code for create User is "+createUserResponse.getStatusLine().getStatusCode()+", but expected was 201");
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		DocumentBuilder db = dbf.newDocumentBuilder();  
		HttpEntity entity = createUserResponse.getEntity();

		if (createUserResponse != null) {  
			InputStream inputStream = entity.getContent();  
			createResponseDoc = db.parse(inputStream);  
			createResponseDoc.getDocumentElement().normalize();
			Element rootNode= createResponseDoc.getDocumentElement();
			
			userDetails[0]=rootNode.getAttribute("id");

		}
		getUserUrl= EnvironmentURL(environment)+"/tfel2rs/v2/user/"+userDetails[0];

		// EXECUTING RESPONSE
		HttpResponse getUserResponse = (HttpResponse) executeGET(getUserUrl);
		if (getUserResponse != null && getUserResponse.getStatusLine().getStatusCode() != HttpStatusCode_OK ){
			printErrorResponse(getUserResponse);
			throw new AssertionError("The Response code for get User details is "+getUserResponse.getStatusLine().getStatusCode()+", but expected was 200");
		}


		DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();  
		DocumentBuilder db1 = dbf.newDocumentBuilder();  
		HttpEntity entity1 = getUserResponse.getEntity();

		if (createUserResponse != null) {  
			InputStream inputStream = entity1.getContent();  
			getResponseDoc = db.parse(inputStream);  
			getResponseDoc.getDocumentElement().normalize();


			Element rootNode= getResponseDoc.getDocumentElement();

			userDetails[1]=rootNode.getAttribute("externalKey");

		}
		return userDetails;
	}


public String[] createNewUserwithPortal(String environment) throws Exception{
		
		String[] userDetails= new String[3];
		Document createResponseDoc = null;
		String createUserUrl="";
		String PortalUrl="";
		
		if(environment.equalsIgnoreCase("DEV"))
			PortalUrl=bicPelicanConstants.PORTAL_URL_DEV;
		else if(environment.equalsIgnoreCase("STG"))
			PortalUrl=bicPelicanConstants.PORTAL_URL_STG;
		
		ArrayList<String> User=createAndGetCustomerPortalUserID(PortalUrl);
		userDetails[1]=User.get(0); // oxygen id
		userDetails[2]=User.get(2); //GUID
		System.out.println("email address is "+User.get(3));
		createUserUrl= EnvironmentURL(environment)+"/tfel2rs/v2/user";
				
		String createUserRequest= "name="+User.get(1)+"&externalKey="+userDetails[1];
		Util.PrintInfo("User name is "+User.get(1)); // pelican user name or portal autodeskID
				
		HttpResponse createUserResponse = (HttpResponse) executePOSTWithHMAC(createUserUrl,createUserRequest,"application/x-www-form-urlencoded; charset=UTF-8");
		
		if (createUserResponse != null && createUserResponse.getStatusLine().getStatusCode() != 201 ){
			printErrorResponse(createUserResponse);
			throw new AssertionError("The Response code for create User is "+createUserResponse.getStatusLine().getStatusCode()+", but expected was 201");
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder db = dbf.newDocumentBuilder();  
	    HttpEntity entity = createUserResponse.getEntity();
	  
		if (createUserResponse != null) {  
	        InputStream inputStream = entity.getContent();  
	        createResponseDoc = db.parse(inputStream);  
	        createResponseDoc.getDocumentElement().normalize();
	       
		
	        Element rootNode= createResponseDoc.getDocumentElement();
			
	        userDetails[0]=rootNode.getAttribute("id");// pelican user id/buyer id
	     
		}
		return userDetails;
		
	}
		


	public void checkExpirationStatus(HttpResponse itemResponseSubs1,HttpResponse itemResponseSubs2) throws Exception {
		Document itemresponseDoc1 = null;
		Document itemresponseDoc2 = null;

		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			HttpEntity entity1 = itemResponseSubs1.getEntity();
			HttpEntity entity2 = itemResponseSubs2.getEntity();

			if (itemResponseSubs1 != null && itemResponseSubs2 != null) {
				InputStream inputStream1 = entity1.getContent();
				itemresponseDoc1 = db.parse(inputStream1);
				itemresponseDoc1.getDocumentElement().normalize();

				InputStream inputStream2 = entity2.getContent();
				itemresponseDoc2 = db.parse(inputStream2);
				itemresponseDoc2.getDocumentElement().normalize();

				NodeList list = itemresponseDoc1.getElementsByTagName("itemInstance");

				for (int x = 0; x < list.getLength(); x++) {
					Node node = list.item(x);
					String Status = "EXPIRED";
					String expDate = ServiceUtils.GetUTCdatetimeAsString();
					String itemId = null;

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element fin = (Element) node;
						itemId = fin.getAttribute("itemId");

						if (fin.getAttribute("isActive").equalsIgnoreCase("true")) {
							if(fin.getAttribute("expirationStatus").equalsIgnoreCase("NO_EXPIRATION"))
								Util.printMessage("Status of Item Id "+ fin.getAttribute("itemId")+ " in trial subscription "+ fin.getAttribute("subscriptionId")+ " is Active =  " + fin.getAttribute("isActive") +" and it has no expiration date");
							else{
								if(fin.getAttribute("expirationDate").substring(0, 10).equalsIgnoreCase(ServiceUtils.GetNextMonthdateAsString()))
									Util.printMessage("Item Id "+ fin.getAttribute("itemId")+ " in trial subscription "+ fin.getAttribute("subscriptionId")+ " is Active =  " + fin.getAttribute("isActive") +" will be expired on "+fin.getAttribute("expirationDate").substring(0, 10));
								else
									Util.printMessage("Status of Item Id "+ fin.getAttribute("itemId")+ " in trial subscription "+ fin.getAttribute("subscriptionId")+ " is Active =  " + fin.getAttribute("isActive") +" will be expired on "+fin.getAttribute("expirationDate").substring(0, 10));
							}
						}
						NodeList list2 = itemresponseDoc2.getElementsByTagName("itemInstance");

						for (int x2 = 0; x2 < list2.getLength(); x2++) {
							Node node2 = list2.item(x2);
							if (node2.getNodeType() == Node.ELEMENT_NODE) {
								Element fin2 = (Element) node2;

								if (itemId.equalsIgnoreCase(fin2.getAttribute("itemId"))) {
									assertUtils.assertEquals(fin.getAttribute("expirationStatus"),Status);
									assertUtils.assertEquals(fin.getAttribute("expirationDate").substring(0, 10),expDate);
									//if (Status.equalsIgnoreCase(fin	.getAttribute("expirationStatus"))) {
									//if (expDate.equalsIgnoreCase(fin.getAttribute("expirationDate").substring(0, 10))) {

									Util.printMessage("Status of Item Id "+ itemId+ " in trial subscription "+ fin.getAttribute("subscriptionId")+ " is Expired on " + expDate);
									Util.printMessage("Status of Item Id "+ itemId+ " in Upgrated Subscription "+ fin2.getAttribute("subscriptionId")+ " is Active = "+ fin2.getAttribute("isActive"));
								}
								/*	}
					}*/


							}
						}
					}

				}
			}
		} catch (Exception e) {
			Util.printError("Error during execution !");
			e.printStackTrace();
			throw new Exception("Error is validation");

		}

	}



}
