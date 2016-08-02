package pelican;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import common.Util;


public class testCreateSubscription extends bicPelicanWebService{

	
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
	public static String accessKeybody;
	public static String contentType;
	private static Properties propTestData = new 	Properties();
		
	private  final String XPATH_CREATEDATE = "//subscription /@createdDate";
	private  final String XPATH_OWNERID= "//subscription /@ownerId";
	private  final String XPATH_STATUS = "//subscription /@status";
	private ServiceUtils servUtil= null;
	
	

	@BeforeClass(groups={"SANITY", "ALL", "FUNC_CREATESUBSCRPTION"})
	@Parameters("environmentType")
	public void setup( String environment) {
		/*@Parameters(value = "environment")
		public void setup(@Optional(DEFAULT_ENVIRONMENT) String environment) {*/
		try {
			initializeWebDriver(browserType);
			doSetup();
			servUtil= new ServiceUtils();
			propTestData=readPropertiesFile(propertiesFileName);
			env = environment;
			setEnvironmentType(environment);
			assignSecretKey();
			
			urlBase= EnvironmentURL(environment)+propTestData.getProperty("URL");
			itemUrlBase= EnvironmentURL(environment)+propTestData.getProperty("ITEM_URL");		
			contentType=propTestData.getProperty("SUBS_POST_CONTENTTYPE");
			
			if(env.equalsIgnoreCase("STG")){
			
				convergingChage_url=bicPelicanConstants.CONVERGINGCHARGE_URL_STG;
			}
				else if(env.equalsIgnoreCase("DEV")){
					
					convergingChage_url=bicPelicanConstants.CONVERGINGCHARGE_URL_DEV;
				}
			
		} catch(Exception e) {
			Util.PrintInfo("Exception in setup method");
			e.printStackTrace();
		}
	}
	

	
	@Test (groups = { "SANITY", "ALL", "FUNC_CREATESUBSCRPTION" })
	public void createSubscriptionExistingUser() throws Exception {
		
		//code to create subscription with trial item
		String createSubsUrl = urlBase;
		
		String createSubsRequest= "userExternalKey="+propTestData.getProperty("1_USER_EXTERNALKEY_"+env)+"&subscriptionOfferExternalKey="+propTestData.getProperty("1_PRODUCT_EXTERNALKEY_"+env)+"&currencyName=USD";
				
		HttpResponse createSubsResponse = (HttpResponse) executePOSTWithHMAC(createSubsUrl,createSubsRequest,contentType);
		Util.PrintInfo("Request Body:  " +createSubsRequest);
		
		
		if (createSubsResponse != null && createSubsResponse.getStatusLine().getStatusCode() != 200 ){
			printErrorResponse(createSubsResponse);
			throw new AssertionError("The Response code is "+createSubsResponse.getStatusLine().getStatusCode()+", but expected was 200");
		}
	
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
		String[][] expectedOutput={{XPATH_CREATEDATE, ServiceUtils.GetUTCdatetimeAsString()},
				{XPATH_OWNERID, propTestData.getProperty("1_VALUE_OWNERID_"+env)},
				{XPATH_STATUS, "ACTIVE"},
				};
					
		// VERIFYING THE RESPONSE WITH EXPECTED OUTPUT
		Document createSubsResponseDoc= validateXML(createSubsResponse,expectedOutput);

		Element rootNode= createSubsResponseDoc.getDocumentElement();
						
		String	SubsID=rootNode.getAttribute("id");
		Util.PrintInfo("Subcription created sucessfully, Subscription id is "+SubsID);
		
	}
	
	@Test (groups = { "SANITY", "ALL", "FUNC_CREATESUBSCRPTION" })
	public void createSubscriptionNewUserPelican() throws Exception {
		
		//code to create subscription with trial item
		String createSubsUrl = urlBase;
		
		String[] userDetails= servUtil.createNewUserwithPelican(env);
		
		String createSubsRequest= "userExternalKey="+userDetails[1]+"&subscriptionOfferExternalKey="+propTestData.getProperty("1_PRODUCT_EXTERNALKEY_"+env)+"&currencyName=USD";
				
		HttpResponse createSubsResponse = (HttpResponse) executePOSTWithHMAC(createSubsUrl,createSubsRequest,contentType);
		Util.PrintInfo("Request Body:  " +createSubsRequest);
		
		
		if (createSubsResponse != null && createSubsResponse.getStatusLine().getStatusCode() != 200 ){
			printErrorResponse(createSubsResponse);
			throw new AssertionError("The Response code is "+createSubsResponse.getStatusLine().getStatusCode()+", but expected was 200");
		}
	
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
		String[][] expectedOutput={{XPATH_CREATEDATE, ServiceUtils.GetUTCdatetimeAsString()},
				{XPATH_OWNERID, userDetails[0]},
				{XPATH_STATUS, "ACTIVE"},
				};
					
		// VERIFYING THE RESPONSE WITH EXPECTED OUTPUT
		Document createSubsResponseDoc= validateXML(createSubsResponse,expectedOutput);

		Element rootNode= createSubsResponseDoc.getDocumentElement();
						
		String	SubsID=rootNode.getAttribute("id");
		Util.PrintInfo("Subcription created sucessfully, Subscription id is "+SubsID);
		
	}
	
	
	
	@DataProvider(name = "UpgradeSubscription_dp")
	public Object[][] UpgradeSubscription_dp() {
		return new Object[][] {
				{propTestData.getProperty("2_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("2_UPGRADE_PRODUCT_NAME_"+env)},
				{propTestData.getProperty("3_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("3_UPGRADE_PRODUCT_NAME_"+env)}
				
		};
	}
	
	@Test(dataProvider = "UpgradeSubscription_dp", groups = { "SANITY", "ALL", "FUNC_CREATESUBSCRPTION" })
	public void UpgradeSubscription(String trialExternalKey, String UpgradeExternalKey) throws Exception {
		
		
		String[] userDetails= servUtil.createNewUserwithPelican(env);
		
		String SubsId1=servUtil.createSubscription(env,userDetails[1],trialExternalKey);
		String SubsId2=servUtil.createSubscription(env,userDetails[1],UpgradeExternalKey);
		
		
		//verify all the item instances of trial order are expired
		
		String itemUrlSubs1 = itemUrlBase+"?&userExternalKey="+userDetails[1]+"&subscriptionId="+SubsId1+"&includeExpired=true&fr.skipCount=false";
		String itemUrlSubs2 = itemUrlBase+"?&userExternalKey="+userDetails[1]+"&subscriptionId="+SubsId2+"&includeExpired=true&fr.skipCount=false";
		
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
	
	@DataProvider(name = "createSubNewUserPortalcheckCloudCredits")
	public Object[][] createSubNewUserPortalcheckCloudCredits_dp() {
		return new Object[][] {
				{propTestData.getProperty("1_TRIAL_PRODUCT_NAME_"+env)},
				{propTestData.getProperty("4_TRIAL_PRODUCT_NAME_"+env)}
				
		};
	}
	@Test(dataProvider = "createSubNewUserPortalcheckCloudCredits", groups = { "SANITY", "ALL", "FUNC_CREATESUBSCRPTION" })
	public void createSubNewUserPortalcheckCloudCredits(String trialProduct) throws Exception {
		
		//code to create subscription with trial item
		String createSubsUrl = urlBase;
		BigInteger[] credits = new BigInteger[3];
		String[] userDetails= servUtil.createNewUserwithPortal(env);
		
		String createSubsRequest= "userExternalKey="+userDetails[1]+"&subscriptionOfferExternalKey="+trialProduct+"&currencyName=USD";
				
		HttpResponse createSubsResponse = (HttpResponse) executePOSTWithHMAC(createSubsUrl,createSubsRequest,contentType);
		Util.PrintInfo("Request Body:  " +createSubsRequest);
		
		
		if (createSubsResponse != null && createSubsResponse.getStatusLine().getStatusCode() != 200 ){
			printErrorResponse(createSubsResponse);
			throw new AssertionError("The Response code is "+createSubsResponse.getStatusLine().getStatusCode()+", but expected was 200");
		}
	
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
		String[][] expectedOutput={{XPATH_CREATEDATE, ServiceUtils.GetUTCdatetimeAsString()},
				{XPATH_OWNERID, userDetails[0]},
				{XPATH_STATUS, "ACTIVE"},
				};
					
		// VERIFYING THE RESPONSE WITH EXPECTED OUTPUT
		Document createSubsResponseDoc= validateXML(createSubsResponse,expectedOutput);

		Element rootNode= createSubsResponseDoc.getDocumentElement();
						
		String	SubsID=rootNode.getAttribute("id");
		Util.PrintInfo("Subcription created sucessfully, Subscription id is "+SubsID);
		
		Util.PrintInfo("GUID for this user is "+userDetails[2]);
		
		credits=verifyCloudCredits(convergingChage_url,userDetails[2]);
		//Util.PrintInfo(credits[1].intValue()+"         "+credits[2].intValue());
		if(credits[1].intValue()==100){
			Util.PrintInfo("Initial granted cloud credits");
			assertUtils.assertEquals(credits[1].intValue(),100);
		}
		if(credits[2].intValue()==100){
			Util.PrintInfo("verifying for Granted cloud credits");
			assertUtils.assertEquals(credits[2].intValue(),100);
		}
		
	}
	
	@DataProvider(name = "UpgradeSubscriptionCheckCloudCrerdits")
	public Object[][] UpgradeSubscriptionCheckCloudCrerdits_dp() {
		return new Object[][] {
				{propTestData.getProperty("2_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("2_UPGRADE_PRODUCT_NAME_"+env)},
				{propTestData.getProperty("3_TRIAL_PRODUCT_NAME_"+env),propTestData.getProperty("3_UPGRADE_PRODUCT_NAME_"+env)}
				
		};
	}
	
	@Test(dataProvider = "UpgradeSubscriptionCheckCloudCrerdits", groups = { "SANITY", "ALL", "FUNC_CREATESUBSCRPTION" })
	public void UpgradeSubscriptionCheckCloudCrerdits(String trialExternalKey, String UpgradeExternalKey) throws Exception {
		
		BigInteger[] credits = new BigInteger[3];
		String[] userDetails= servUtil.createNewUserwithPortal(env);
		String SubsId1=servUtil.createSubscription(env,userDetails[1],trialExternalKey);
		
		String SubsId2=servUtil.createSubscription(env,userDetails[1],UpgradeExternalKey);
		
		Util.printInfo("Id for trial Subs is "+SubsId1+" and for the upgrated subs "+UpgradeExternalKey+ " is "+SubsId2);
		//verify all the item instances of trial order are expired
		
		Util.PrintInfo("GUID for this user is "+userDetails[2]);
		
		credits=verifyCloudCredits(convergingChage_url,userDetails[2]);
		//Util.PrintInfo(credits[1].intValue()+"         "+credits[2].intValue());
		if(credits[1].intValue()==100){
			Util.PrintInfo("Initial granted cloud credits");
			assertUtils.assertEquals(credits[1].intValue(),100);
		}
		if(credits[2].intValue()==100){
			Util.PrintInfo("verifying for Granted cloud credits");
			assertUtils.assertEquals(credits[2].intValue(),100);
		}
		
}
	@AfterClass(groups= {"SANITY","ALL","FUNC_SUBMITORDER"})
	public void tearDown() throws Exception {
		driver.close();
		testProperties.clear();
		
	}

}

