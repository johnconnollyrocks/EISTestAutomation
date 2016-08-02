package pelican;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Response;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import customerportal.getConvergentChargeSOAPService;
import common.Util;


public class SubmitPurchaseOrder extends bicPelicanWebService{

	public static final String DEFAULT_ENVIRONMENT = "DEV";
	private static String propertiesFileName=Thread.currentThread().getStackTrace()[1].getFileName().split("\\.")[0];
	public static String env ;
	public static String urlBase;
	public static String itemUrlBase;
	public static String convergingChage_url;
	public static String PartnerId;
	public static String FamilyId;
	public static String hmacSignature;
	public static String Secretkey;
	public static String UserExternalKey;
	public static String accessKeyxml;
	public static String ConfigId;
	public static String contentType;
	private static Properties propTestData = new 	Properties();
	Logger log = Logger.getLogger(SubmitPurchaseOrder.class);	
		
	
	// Defining xpaths for sucess expectedresponse
	private  final String XPATH_CREATETIME = "//purchaseOrder/@creationTime";
	private  final String XPATH_USERID = "//purchaseOrder/buyerUser/@id";
	private  final String XPATH_ORDERSTATE = "//purchaseOrder/orderState";
	private  final String XPATH_SUBSCRIPTIONID = "//purchaseOrder/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseResponse/@subscriptionId";
	private  final String XPATH_OrderID = "//purchaseOrder/@id";
	
	
	
	// STATUS CODES
	public final int HTTP_STATUSCODE_SUCCESS = 201;
	public final int HTTP_BAD_STATUSCODE_ERROR = 400;
	public final int HTTP_ERROR_STATUSCODE = 404;
	private ServiceUtils servUtil= null;
	
	@BeforeClass(groups = { "SANITY", "ALL", "FUNC_SUBMITORDER" })
	 @Parameters("environmentType")
	 public void setup( String environment) {
	//@Parameters(value = "environment")
	//public void setup(@Optional(DEFAULT_ENVIRONMENT) String environment) {
		try {

			initializeWebDriver(browserType);
			doSetup();
			servUtil = new ServiceUtils();

			propTestData = readPropertiesFile(propertiesFileName);
			env = environment;
			setEnvironmentType(environment);
			assignSecretKey();

			if (getEnvironment() == null) {

				setEnvironment(environment);

			}

			
			urlBase= EnvironmentURL(environment)+propTestData.getProperty("URL");
			itemUrlBase= EnvironmentURL(environment)+propTestData.getProperty("ITEM_URL");
			contentType=propTestData.getProperty("ORDER_POST_CONTENTTYPE");
						
			if(env.equalsIgnoreCase("STG")){
				accessKeyxml="ytt;!3vMli9p8&quot;vB";
				ConfigId="1002";
				convergingChage_url=bicPelicanConstants.CONVERGINGCHARGE_URL_STG;
			}
				else if(env.equalsIgnoreCase("DEV")){
					accessKeyxml="0O268Q55OGa";
					ConfigId="1001";
					convergingChage_url=bicPelicanConstants.CONVERGINGCHARGE_URL_DEV;
				}
			
		} catch(Exception e) {
			Util.PrintInfo("Exception in setup method");
			e.printStackTrace();
		}
	}
	
	
	@Test(groups={"SANITY","ALL","FUNC_SUBMITORDER"})
	public void submitNewPOExistingUser() throws Exception {
		
		String url = urlBase;
				
		String request= requestBody(propTestData.getProperty("1_BUYER_USER_ID_"+env),propTestData.getProperty("1_PRODUCT_NAME_"+env),"",false);

		// EXECUTING RESPONSE
			HttpResponse response = (HttpResponse) executePOSTWithAccssKey(url,request,contentType);
			Util.PrintInfo("Request Body" +request);
			
			if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
				printErrorResponse(response);
				throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
			}
		
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
				String[][] expectedOutput={{XPATH_CREATETIME, ServiceUtils.GetUTCdatetimeAsString()},
						{XPATH_USERID, propTestData.getProperty("1_BUYER_USER_ID_"+env)},
						{XPATH_ORDERSTATE, "CHARGED"},
						};
							
		// VERIFYING THE RESPONSE WITH EXPECTED OUTPUT
		Document responseDoc= validateXML(response,expectedOutput);
		
		//Print OrderId and subscriptionId
		printIds(responseDoc);
		
		
	}
	
	
	
	@Test(groups={"SANITY","ALL","FUNC_SUBMITORDER"})
	public void submitNewPONewUser() throws Exception {
		
		String url = urlBase;
		
		String[] userDetails= servUtil.createNewUserwithPelican(env);
		String request= requestBody(userDetails[0],propTestData.getProperty("2_PRODUCT_NAME_"+env),"",false);
		Util.PrintInfo("Request Body" +request);
				
		// EXECUYTING RESPONSE
			HttpResponse response = (HttpResponse) executePOSTWithAccssKey(url,request,contentType);
			
			if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
				printErrorResponse(response);
				throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
			}
			
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
				String[][] expectedOutput={{XPATH_CREATETIME, ServiceUtils.GetUTCdatetimeAsString()},
						{XPATH_USERID, userDetails[0]},
						{XPATH_ORDERSTATE, "CHARGED"},
						};
							
		// VERIFYING THE RESPONSE WITH EXPECTED OUTPUT
		Document responseDoc= validateXML(response,expectedOutput);
		
		//Print OrderId and subscriptionId
		printIds(responseDoc);
	}
	
		
	@DataProvider(name = "UpgradeToRentalPO")
	public Object[][] UpgradeToRentalPO_dp() {
		return new Object[][] {
				{propTestData.getProperty("3_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("3_UPGRADE_PRODUCT_NAME_"+env)},
				{propTestData.getProperty("4_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("4_UPGRADE_PRODUCT_NAME_"+env)},
				{propTestData.getProperty("5_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("5_UPGRADE_PRODUCT_NAME_"+env)},
				{propTestData.getProperty("6_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("6_UPGRADE_PRODUCT_NAME_"+env)}
				
		};
	}
	
	@Test(dataProvider = "UpgradeToRentalPO_dp", groups = { "SANITY", "ALL", "FUNC_SUBMITORDER" })
	public void UpgradeToRentalPO(String trialOrderExternalKey, String UpgradeOrderExternalKey) throws Exception {
		
		
		String[] userDetails= servUtil.createNewUserwithPelican(env);
		String SubsId=servUtil.createSubscription(env,userDetails[1],trialOrderExternalKey);
		
		String orderUrl = urlBase;
		String orderRequest= requestBody(userDetails[0],UpgradeOrderExternalKey,SubsId,false);
		Util.PrintInfo("Request Body" +orderRequest);
		
		
			
		// EXECUYTING RESPONSE
			HttpResponse orderResponse = (HttpResponse) executePOSTWithAccssKey(orderUrl,orderRequest,contentType);
			
			if (orderResponse != null && orderResponse.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
				printErrorResponse(orderResponse);
				throw new AssertionError("The Response code is "+orderResponse.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
			}
		
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
				String[][] expectedOutput1={{XPATH_CREATETIME, ServiceUtils.GetUTCdatetimeAsString()},
						{XPATH_USERID,userDetails[0]},
						{XPATH_ORDERSTATE, "CHARGED"},
						};
							
		// VERIFYING THE RESPONSE WITH EXPECTED OUTPUT
		Document orderResponseDoc= validateXML(orderResponse,expectedOutput1);
		
		//Print OrderId and subscriptionId
		String[] Ids= printIds(orderResponseDoc);
		
		//verify all the item instances of trial order are expired
		
		String itemUrlSubs1 = itemUrlBase+"?&userExternalKey="+userDetails[1]+"&subscriptionId="+SubsId+"&includeExpired=true&fr.skipCount=false";
		String itemUrlSubs2 = itemUrlBase+"?&userExternalKey="+userDetails[1]+"&subscriptionId="+Ids[1]+"&includeExpired=true&fr.skipCount=false";
		
		// EXECUYTING RESPONSE
		HttpResponse itemResponseSubs1 = (HttpResponse) executeGET(itemUrlSubs1);
		HttpResponse itemResponseSubs2 = (HttpResponse) executeGET(itemUrlSubs2);
	     
		if (itemResponseSubs1 != null && itemResponseSubs1.getStatusLine().getStatusCode() != 200 ){
			printErrorResponse(itemResponseSubs1);
			throw new AssertionError("The Response code is "+itemResponseSubs1.getStatusLine().getStatusCode()+", but expected was 200");
		}
		if (itemResponseSubs2 != null && itemResponseSubs2.getStatusLine().getStatusCode() != 200 ){
			printErrorResponse(itemResponseSubs2);
			throw new AssertionError("The Response code is "+itemResponseSubs2.getStatusLine().getStatusCode()+", but expected was 200");
		}
		
		servUtil.checkExpirationStatus(itemResponseSubs1,itemResponseSubs2);
}
	@Test(groups={"SANITY","ALL","FUNC_SUBMITORDER"})
	public void submitNewPOExistingUserCheckCloudCredits() throws Exception {
		
		String url = urlBase;
		BigInteger[] BeforeCredits = new BigInteger[3];
		BigInteger[] AfterCredits = new BigInteger[3];
		
		BeforeCredits=verifyCloudCredits(convergingChage_url,propTestData.getProperty("1_GUID_"+env));
		System.out.println("helloikikjfsdaf"+BeforeCredits[0].intValue());
		
		String request= requestBody(propTestData.getProperty("1_BUYER_USER_ID_"+env),propTestData.getProperty("1_PRODUCT_NAME_"+env),"",true);

		// EXECUTING RESPONSE
			HttpResponse response = (HttpResponse) executePOSTWithAccssKey(url,request,contentType);
			Util.PrintInfo("Request Body" +request);
			
			if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
				printErrorResponse(response);
				throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
			}
		
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
				String[][] expectedOutput={{XPATH_CREATETIME, ServiceUtils.GetUTCdatetimeAsString()},
						{XPATH_USERID, propTestData.getProperty("1_BUYER_USER_ID_"+env)},
						{XPATH_ORDERSTATE, "CHARGED"},
						};
							
		// VERIFYING THE RESPONSE WITH EXPECTED OUTPUT
		Document responseDoc= validateXML(response,expectedOutput);
		
		//Print OrderId and subscriptionId
		printIds(responseDoc);
		
		AfterCredits=verifyCloudCredits(convergingChage_url,propTestData.getProperty("1_GUID_"+env));
		
		int Cal= BeforeCredits[0].intValue()+400;
		Util.printMessage("Verifying cloud credits after placing order");
		assertUtils.assertEquals(AfterCredits[0].intValue(),Cal);
		
				
	}
	@Test(groups={"SANITY","ALL","FUNC_SUBMITORDER"})
	public void submitNewPONewUserfromPortalCheckCloudCredits() throws Exception {
		
		String url = urlBase;
		BigInteger[] credits = new BigInteger[3];
		String[] userDetails= servUtil.createNewUserwithPortal(env);
		String request= requestBody(userDetails[0],propTestData.getProperty("1_PRODUCT_NAME_"+env),"",true);

		// EXECUTING RESPONSE
			HttpResponse response = (HttpResponse) executePOSTWithAccssKey(url,request,contentType);
			Util.PrintInfo("Request Body" +request);
			
			if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
				printErrorResponse(response);
				throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
			}
		
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
				String[][] expectedOutput={{XPATH_CREATETIME, ServiceUtils.GetUTCdatetimeAsString()},
						{XPATH_USERID, userDetails[0]},
						{XPATH_ORDERSTATE, "CHARGED"},
						};
							
		// VERIFYING THE RESPONSE WITH EXPECTED OUTPUT
		Document responseDoc= validateXML(response,expectedOutput);
		
		//Print OrderId and subscriptionId
		printIds(responseDoc);
		
		Util.PrintInfo("guid for this user is "+userDetails[2]);
		//verfy cloud credits
				credits=verifyCloudCredits(convergingChage_url,userDetails[2]);
				//Util.PrintInfo(credits[0].intValue()+"         "+credits[1].intValue()+"    "+credits[2].intValue());
				
				if(credits[0].intValue()==400){
					Util.printMessage("Verifying available cloud credits after placing order");
					assertUtils.assertEquals(credits[0].intValue(),400);
				}
				if(credits[1].intValue()==100){
					Util.PrintInfo("Initial granted cloud credits");
					assertUtils.assertEquals(credits[1].intValue(),100);
				}
				if(credits[2].intValue()==100){
					Util.PrintInfo("verifying for Granted cloud credits");
					assertUtils.assertEquals(credits[2].intValue(),100);
				}
		
}
		
	@DataProvider(name = "UpgradeToRentalPOCheckCloudCredits")
	public Object[][] UpgradeToRentalPOCheckCloudCreditsdp() {
		return new Object[][] {
				{propTestData.getProperty("3_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("3_UPGRADE_PRODUCT_NAME_"+env)},
				{propTestData.getProperty("6_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("6_UPGRADE_PRODUCT_NAME_"+env)}
				
		};
	}
	
	@Test(dataProvider = "UpgradeToRentalPOCheckCloudCredits", groups = { "SANITY", "ALL", "FUNC_SUBMITORDER" })
	public void UpgradeToRentalPOCheckCloudCredits(String trialOrderExternalKey, String UpgradeOrderExternalKey) throws Exception {
		
		BigInteger[] credits = new BigInteger[3];
		String[] userDetails= servUtil.createNewUserwithPortal(env);
		String SubsId=servUtil.createSubscription(env,userDetails[1],trialOrderExternalKey);
		
		String orderUrl = urlBase;
		String orderRequest= requestBody(userDetails[0],UpgradeOrderExternalKey,SubsId,true);
		Util.PrintInfo("Request Body" +orderRequest);
		
		
			
		// EXECUYTING RESPONSE
			HttpResponse orderResponse = (HttpResponse) executePOSTWithAccssKey(orderUrl,orderRequest,contentType);
			
			if (orderResponse != null && orderResponse.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
				printErrorResponse(orderResponse);
				throw new AssertionError("The Response code is "+orderResponse.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
			}
		
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
				String[][] expectedOutput1={{XPATH_CREATETIME, ServiceUtils.GetUTCdatetimeAsString()},
						{XPATH_USERID,userDetails[0]},
						{XPATH_ORDERSTATE, "CHARGED"},
						};
							
		// VERIFYING THE RESPONSE WITH EXPECTED OUTPUT
		Document orderResponseDoc= validateXML(orderResponse,expectedOutput1);
		
		//Print OrderId and subscriptionId
		String[] Ids= printIds(orderResponseDoc);
		
		
		//verfy cloud credits
		credits=verifyCloudCredits(convergingChage_url,userDetails[2]);
		//Util.PrintInfo(credits[0].intValue()+"         "+credits[1].intValue()+"    "+credits[2].intValue());
		
		if(credits[0].intValue()==400){
			Util.printMessage("Verifying available cloud credits after placing order");
			assertUtils.assertEquals(credits[0].intValue(),400);
		}
		if(credits[1].intValue()==100){
			Util.PrintInfo("Initial granted cloud credits");
			assertUtils.assertEquals(credits[1].intValue(),100);
		}
		if(credits[2].intValue()==100){
			Util.PrintInfo("verifying for Granted cloud credits");
			assertUtils.assertEquals(credits[2].intValue(),100);
		}
}
	
	
	private String[] printIds(Document Responsedoc) {

		String[] Ids= new String[2];
		if (Responsedoc != null) {

			Element rootNode = Responsedoc.getDocumentElement();

			Util.printInfo("Order ID For this order is..."+ rootNode.getAttribute("id"));
			Ids[0]=rootNode.getAttribute("id");
			NodeList list = Responsedoc.getElementsByTagName("subscriptionPlanPurchaseResponse");
			Node node = list.item(0);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element fin = (Element) node;
				String SubsId = fin.getAttribute("subscriptionId");
				Util.printInfo("Subscription ID For this order is....."	+ SubsId);
				Ids[1]=SubsId;

			}
		}
		return Ids;

	}

	private String requestBody(String UserId, String ProductName, String UpgradeSubsID, boolean forex){
		
		
		String currencyId="4";
		String forexLineItem =null;
		Random randomGenerator = new Random(); 
		int randomNumber=((1+ randomGenerator.nextInt(5)) * 10000 +randomGenerator.nextInt(10000));
		String CreditCardNumber="44444444444"+randomNumber;	
		
		if(forex==true){
			forexLineItem=   " <lineItem>"+
				"<forexOffer>"+
         "<forexOfferRequest amountFromCurrency=\"400\" externalKey=\"CREDITPACKAGE\" />"+
         	"</forexOffer>"+
         		"</lineItem>";
		}
		else 
			forexLineItem= "";

		
		String requestBody="<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"+
		"<purchaseOrder accessKey=\""+accessKeyxml+"\" partnerId=\""+AUTH_PARTNERID+"\" appFamilyId=\""+auth_FamilyId+"\" version=\"1.0\">"+
		"<buyerUser id=\""+UserId+"\" />"+
		  "<orderCommand>CHARGE</orderCommand>"+
		 " <lineItems>"+
		    "<lineItem>"+
		      "<subscriptionPlan>"+
		         "<subscriptionPlanPurchaseRequest currencyId=\""+currencyId+"\" offerExternalKey=\""+ProductName+"\"  upgradeFromSubscriptionId=\""+UpgradeSubsID+"\"/>"+
		     "	 </subscriptionPlan>"+
		    "</lineItem>"+
		    forexLineItem+
		  "</lineItems>"+
		  "<payment configId=\""+ConfigId+"\">"+
		        "<creditCard>"+
		            "<billToStreetAddress>082606 Main St</billToStreetAddress>"+
		           " <billToZipCode>90210</billToZipCode>"+
		            "<creditCardNumber>"+CreditCardNumber+"</creditCardNumber> "+
		            " <creditCardType>VISA</creditCardType>"+
		            "<expDate>0522</expDate>"+
		            "<securityCode>000</securityCode>"+
		            "<firstName>Automation</firstName>"+
		            "<surname>Order</surname>"+
		            "<city>Los Angeles</city>"+
		            "<stateProvince>CA</stateProvince>"+
		            "<countryCode>US</countryCode>"+
		            "</creditCard>"+
		            "</payment>"+
		            "</purchaseOrder>";

		return requestBody;
		
	}
	
	@AfterClass(groups= {"SANITY","ALL","FUNC_SUBMITORDER"})
	public void tearDown() throws Exception {
		driver.close();
		testProperties.clear();
		
	}

	
}

