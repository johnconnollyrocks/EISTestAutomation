package pelican;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasXPath;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.custommonkey.xmlunit.Diff;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.autodesk.schemas.business.convergentchargingexv1.QuerySubscriptionBalanceResponse;
import com.autodesk.schemas.business.convergentchargingexv1.SOAPFault_Exception;
import com.autodesk.schemas.business.convergentchargingexv1.ServicePrivilege;
import com.autodesk.schemas.business.convergentchargingexv1.ServicePrivilege.ListOfUnits;
import com.autodesk.schemas.business.convergentchargingexv1.Unit;

import common.EISTestBase;
import common.Page;
import common.Page_;
import common.TestProperties;
import common.Util;
import customerportal.getConvergentChargeSOAPService;
/**
 * @Description This is main class for all the Bic-Pelican WebServices testing
 * @author t_marus
 *
 */
public class bicPelicanWebService extends EISTestBase {

	/**
	 * This is standard authPartner key
	 */
	/*public final static String AUTH_PARTNERID				= "$apiactor";*/		
	public static String AUTH_PARTNERID				= null;

	/**
	 * This is default auth.FamilId 
	 */
	/*public static String auth_FamilyId						="8888";*/		
	public static String auth_FamilyId						=null;

	/**
	 * auth.Timestamp which will be used in hmacSigVal
	 */
	public static String auth_TimeStamp						= null;

	/**
	 * auth.Signature which is HMAC signature
	 */
	public static String hmacSignatureValue					= null;

	/**
	 * This secret key is constant, which i got it from other team. 
	 */
	private static String SECRET_KEY=null; 

	protected  HttpClient myClient= null;

	protected HttpPost myPostrequest = null;

	protected static Properties testProperties= null;

	protected static String environmentType=null;

	protected static String environmentURL=null;

	protected static final String PELICAN_APPDIR = "pelican";

	protected static final String PELICAN_APPNAME = "pelicanAdminTool";

	/*private static final String PELICAN_FOLDER = "src"+File.separator+PELICAN_APPDIR;*/

	public static  String PELICAN_FOLDER = null;

	protected static String PELICAN_TESTDATADIR = null;

	private HttpClient client;

	/**
	 * This identifier is  constant 
	 */
	protected final static String DEV_SECRET_KEY =  "Api123$%^";

	protected final static String STG_SECRET_KEY = "239FDef30-$df923";

	protected final static String FAMILYID_STG		= "9999";

	protected final static String AUTHPARTNER_ID_STG		= "$api";

	protected final static String FAMILYID_DEV		= "8888";

	protected final static String AUTHPARTNER_ID_DEV	= "$apiactor";

	protected final static int HttpStatusCode_OK =  HttpStatus.SC_OK;

	protected final static int HttpStatusCode_BAD_REQ =  HttpStatus.SC_BAD_REQUEST;
	
	protected final static int HttpStatusCreatedCode_OK	 = HttpStatus.SC_CREATED;

	public String xmlFileToWrite= null;

	public static String responseFileslabel= "responseFiles";

	public HttpResponse  response = null;	

	public static Page_ pelicanAdmPage = null;

	public static TestProperties testPropertiesClass;

	public static  String PELICAN_ROOT_DIR	=	null;

	public static String PELICAN_RESOURCE_DIR	 =  null;

	public static String pelicanAdminPagePropFileName = "Page_PelicanAdminTool.properties";

	public static String adminToolPropFileLoc		=  null;

	public static String adminToolURL		= null; 

	public static final String browserType = "chrome";

	public static String PELICAN_XMLRESPONSE_DIR	=  null;

	protected String o2_ID		= null;

	public static String portalURL		 =  null;
	
	public static String configID_SubmitPurchaseOrder =  null;
	
	

	
	/**
	 * Description :default const.	
	 */
	public bicPelicanWebService() {
		try {
			setEnvVariables();	//set main variables			
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}

	/**
	 * @Description This is used for Pelican Admin tool UI related tests
	 * @param browser
	 * @throws Exception 
	 */
	public bicPelicanWebService(String browser) throws Exception {
		super(PELICAN_APPNAME, PELICAN_APPDIR,browser);		
		doSetup();   
	}

	public static String getSecretKey() {
		return SECRET_KEY;
	}

	public static String getAUTH_PARTNERID() {
		return AUTH_PARTNERID;
	}

	public static String getAuth_FamilyId() {
		return auth_FamilyId;
	}
	/**
	 * @Description need to set  the secret key based on the environment
	 * @return
	 */
	public static void assignSecretKey() {
		if (getEnvironmentType().equalsIgnoreCase("dev")){			
			SECRET_KEY=DEV_SECRET_KEY;
			auth_FamilyId=FAMILYID_DEV;
			AUTH_PARTNERID=AUTHPARTNER_ID_DEV;
		}else if (getEnvironmentType().equalsIgnoreCase("stg")) 
		{
			SECRET_KEY=STG_SECRET_KEY;
			auth_FamilyId=FAMILYID_STG;
			AUTH_PARTNERID=AUTHPARTNER_ID_STG;
		}
		else {
			//TO DO for PROD 
		}

	}

	public static String EnvironmentURL(String environment) {

		if(environment.equalsIgnoreCase("DEV"))
			environmentURL=bicPelicanConstants.URL_DEV;
		else if(environment.equalsIgnoreCase("STG"))
			environmentURL=bicPelicanConstants.URL_STG;

		return environmentURL;

	}

	public static String getEnvironmentType() {
		return environmentType;
	}

	public static void setEnvironmentType(String environmentType) {
		bicPelicanWebService.environmentType = environmentType;
	}
	public  HttpPost getHttpPostMethod(String postURL,String requestParams) throws Exception {		
		/*String params="auth.partnerId="+AUTH_PARTNERID+"&auth.appFamilyId="+auth_FamilyId+"&auth.timestamp="+auth_TimeStamp+"&auth.signature="+hmacSignatureValue+"&userExternalKey=SSN015244&subscriptionOfferExternalKey=MAYALT-TRIAL";*/
		HttpPost myPostrequest=null;
		try{
			StringEntity entity= new StringEntity(requestParams);

			myPostrequest= new HttpPost(postURL);
			myPostrequest.addHeader("Content-type","application/x-www-form-urlencoded");
			myPostrequest.addHeader("Accept", "application/xml");
			myPostrequest.setEntity(entity);
		}
		catch (AssertionError ae) {
			Util.printError("Assertion error while execution the POST call: "+postURL + " with request params:"+requestParams);
			ae.printStackTrace();
			throw ae;
		}
		catch (Exception e) {
			Util.printError("Exception while execution the POST call: "+postURL+ "  with request params:"+requestParams);			
			e.printStackTrace();			
			throw e;
		}

		return myPostrequest;
	}

	/**
	 * @Description return the propery key based on the object property
	 * @param propertyKey
	 * @return
	 */
	public String getTestDataBasedOnEnvironment(String propertyKey) {
		String testdataBasedonEnv=null;
		if (!testProperties.isEmpty()) {
			if (getEnvironmentType().equalsIgnoreCase("dev")) {
				testdataBasedonEnv=testProperties.getProperty(propertyKey+"_DEV");
			}else if(getEnvironmentType().equalsIgnoreCase("stg")) {
				testdataBasedonEnv=testProperties.getProperty(propertyKey+"_STG");
			}else {
				//TO DO for PRod
			}			
		}
		return testdataBasedonEnv;
	}


	/**
	 * @Description return the HttpClient 
	 * @return
	 * @throws Exception
	 */
	public HttpResponse executeGET(String url) throws Exception {
		HttpResponse  response = null;
		HttpGet getRequest = null;
		try {
			Util.printInfo("GET URL: " + url);
			HttpClient client= HttpClientBuilder.create().build(); 
			long startTime = System.currentTimeMillis();
			getRequest= new HttpGet(url);
			// setting HMAC header
			getRequest.addHeader("Content-type","application/x-www-form-urlencoded");
			getRequest.addHeader("Accept", "application/xml");
			getRequest.addHeader("X-E2-PartnerId",getAUTH_PARTNERID());
			getRequest.addHeader("X-E2-AppFamilyId",getAuth_FamilyId());
			getRequest.addHeader("X-E2-HMAC-Timestamp",getTimeStampForHeader());
			getRequest.addHeader("X-E2-HMAC-Signature",getHMACSignatureValue());
			// executing request
			response = client.execute(getRequest);
			Util.printInfo("Time taken to complete the request :" + (double) (System.currentTimeMillis() - startTime)/1000 + " seconds" );

		} catch (AssertionError ae) {
			Util.printError("Assertion error while execution the GET call: "+url);
			ae.printStackTrace();			
			throw ae;
		}
		catch (Exception e) {
			Util.printError("Exception while execution the GET call: "+url);			
			e.printStackTrace();
			throw e;
		}

		return  response;
	}

	/**
	 * @Description return the HttpClient and use this closeableHttpClient to release the resources 
	 * @return
	 * @throws Exception
	 */

	public  CloseableHttpClient getHttpClient() throws Exception {
		CloseableHttpClient httpClient= HttpClientBuilder.create().build();

		return httpClient;
	}

	/**
	 * @Descriptiopn generates HmacSignature value. This is pulled from pelican Webservices API portal
	 * @return
	 * @throws Exception
	 */

	public static String getHMACSignatureValue() throws Exception{
		Mac mac = Mac.getInstance ("HmacSHA256");
		String secret = SECRET_KEY;	
		SecretKeySpec keySpec = new SecretKeySpec (secret.getBytes (), "HmacSHA256");
		mac.init (keySpec);
		String appFamilyId = auth_FamilyId;
		String partnerId = AUTH_PARTNERID;	
		String authTimestamp=getTimeStampForHeader();	
		String message = new StringBuilder().append (partnerId).append (appFamilyId).append (authTimestamp).toString ();		
		byte [] signatureBytes = mac.doFinal (message.getBytes ());
		String signature = Hex.encodeHexString(signatureBytes);
		hmacSignatureValue=signature;
		return hmacSignatureValue;

	}


	/**
	 * @Description get the timestamp which will be used to generate Hmac signature
	 * @return
	 * @throws Exception
	 */
	public static String getTimeStampForHeader() throws Exception{
		Calendar cal = Calendar.getInstance (TimeZone.getTimeZone ("UTC"));
		auth_TimeStamp = String.valueOf (cal.getTimeInMillis () / 1000);
		return auth_TimeStamp;
	}


	/**
	 * @Description returns the testProperties Object
	 * @param propertiesFilename
	 * @return
	 * @throws Exception
	 */
	public Properties readPropertiesFile(String propertiesFilename) throws Exception {		
		/*testProperties=Util.loadPropertiesFile(getPelicanRootDir()+File.separator+PELICAN_TESTDATADIR+File.separator+propertiesFilename+".properties");*/		
		testProperties=Util.loadPropertiesFile(PELICAN_TESTDATADIR+propertiesFilename+".properties");
		return testProperties;

	}

	/**
	 * @Description returns the testProperties Object
	 * @param propertiesFilename
	 * @return
	 * @throws Exception
	 *//*
	public Properties readPropertiesFile(String propertiesFilename) throws Exception {
		//using java NIO jdk 7		

		testProperties=Util.loadPropertiesFile(getPelicanRootDir()+File.separator+PELICAN_FOLDER+File.separator+propertiesFilename+".properties");		
		return testProperties;

	}*/
	public static String getPelicanRootDir() throws Exception{
		Path userDir= null;
		if (System.getProperty("user.dir").contains("build")) {			
			userDir= Paths.get(System.getProperty("user.dir")+"/..").toRealPath();		
		}else{
			userDir=Paths.get(System.getProperty("user.dir")).toRealPath();
		}
		return userDir.toRealPath().toString()+File.separator;
	}


	/**
	 * @Description Write the response data to a xml 
	 * @param contentToWrite
	 * @param fileName
	 * @return
	 * @throws Exception
	 */


	public String createXMlFileWithResponse(String contentToWrite,String fileName,String encodingFormat) throws Exception {	
		String xmlFileToWrite=null;
		File dirFile = new File(PELICAN_FOLDER+File.separator+responseFileslabel);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		xmlFileToWrite=PELICAN_FOLDER+File.separator+responseFileslabel+File.separator+fileName;
		File contentFile = new File(xmlFileToWrite);

		FileUtils.write(contentFile, contentToWrite,"UTF-8");
		//close the file

		return xmlFileToWrite;
	}

	/**
	 * @Description Write the response data to a xml 
	 * @param contentToWrite
	 * @param fileName
	 * @return
	 * @throws Exception
	 */

	public String createXMlFileWithResponse(String contentToWrite,String fileName) throws Exception {	
		String xmlFileToWrite=null;
		File dirFile = new File(PELICAN_FOLDER+File.separator+responseFileslabel);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		xmlFileToWrite=PELICAN_FOLDER+File.separator+responseFileslabel+File.separator+fileName;
		File contentFile = new File(xmlFileToWrite);
		//if exists delete it
		if (contentFile.exists()) {
			contentFile.delete();
		}
		contentFile.setWritable(true);	//explicitly make it to write
		FileUtils.write(contentFile, contentToWrite);
		//close the file

		return xmlFileToWrite;
	}
	
	/**
	 * @Description set the HttpClient 
	 * @return
	 * @throws Exception
	 */

	public void	 setHttpClient(HttpClient myHttpclient) throws Exception {
		client=myHttpclient;		
	}
	public synchronized HttpResponse executePOSTWithHMAC(String url, String requestBody, String contentType) throws Exception {
		HttpResponse  response = null;
		HttpPost postRequest =null;
		try {
			Util.printInfo("POST URL: " + url+" with request body is "+requestBody);
			HttpClient client= HttpClientBuilder.create().build(); 
			long startTime = System.currentTimeMillis();

			postRequest= new HttpPost(url);
			postRequest.addHeader("Content-type",contentType);
			postRequest.addHeader("Accept", "application/xml");
			postRequest.addHeader("X-E2-PartnerId",getAUTH_PARTNERID());
			postRequest.addHeader("X-E2-AppFamilyId",getAuth_FamilyId());
			postRequest.addHeader("X-E2-HMAC-Timestamp",getTimeStampForHeader());
			postRequest.addHeader("X-E2-HMAC-Signature",getHMACSignatureValue());

			StringEntity se = new StringEntity(requestBody); //XML as a string

			postRequest.setEntity(se);		


			response = client.execute(postRequest);
			Util.printInfo("Time taken to complete the request :" + (double) (System.currentTimeMillis() - startTime)/1000 + " seconds" );

		} catch (Exception e) {
			Util.printError("Error during execution Post request!");
			e.printStackTrace();
			throw new Exception("Unable to get the response for the request body:"+requestBody);
		}

		return  response;
	}

	public synchronized HttpResponse executePUT(String url, String requestBody, String contentType) throws Exception {
		HttpResponse  response = null;
		HttpPut putRequest =null;
		try {
			Util.printInfo("PUT request URL: " + url+" with request body is "+requestBody);
			/*HttpClient client= ttpClientBuilder.create().build();*/

			long startTime = System.currentTimeMillis();

			putRequest= new HttpPut(url);
			putRequest.addHeader("Content-type",contentType);
			putRequest.addHeader("Accept", "application/xml");
			putRequest.addHeader("X-E2-PartnerId",getAUTH_PARTNERID());
			putRequest.addHeader("X-E2-AppFamilyId",getAuth_FamilyId());
			putRequest.addHeader("X-E2-HMAC-Timestamp",getTimeStampForHeader());
			putRequest.addHeader("X-E2-HMAC-Signature",getHMACSignatureValue());

			StringEntity se = new StringEntity(requestBody); //XML as a string

			putRequest.setEntity(se);		


			response = client.execute(putRequest);
			Util.printInfo("Time taken to complete the request :" + (double) (System.currentTimeMillis() - startTime)/1000 + " seconds" );

		} catch (Exception e) {
			Util.printError("Error during execution Post request!");
			e.printStackTrace();
			throw new Exception("Unable to get the response for the PUT operation with request body:"+requestBody);
		}

		return  response;
	}

	public synchronized HttpResponse executePOSTWithAccssKey(String url, String requestBody, String contentType) throws Exception {
		HttpResponse  response = null;
		HttpPost postRequest =null;
		try {
			Util.printInfo("POST URL: " + url);
			HttpClient client= HttpClientBuilder.create().build(); 
			long startTime = System.currentTimeMillis();

			postRequest= new HttpPost(url);
			postRequest.addHeader("Content-type",contentType);
			postRequest.addHeader("Accept", "application/xml");


			StringEntity se = new StringEntity(requestBody); //XML as a string

			postRequest.setEntity(se);		


			response = client.execute(postRequest);
			Util.printInfo("Time taken to complete the request :" + (double) (System.currentTimeMillis() - startTime)/1000 + " seconds" );

		} catch (Exception e) {
			Util.printError("Error during execution !");
			e.printStackTrace();
			throw new Exception("Unable to get the response for the request body:"+requestBody);
		}

		return  response;
	}


	/*
	 * Function to validate the xapth and its corresponding value in xml response
	 */
	public Document validateXML(HttpResponse response, String[][] validationStack)
			throws Exception {
		Document responseDoc = null;

		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			HttpEntity entity = response.getEntity();

			if (response != null) {  
				InputStream inputStream = entity.getContent();  
				responseDoc = db.parse(inputStream);  
				responseDoc.getDocumentElement().normalize();


				if(validationStack!= null && validationStack.length > 0) {
					for (int i = 0; i < validationStack.length; i++) {
						Util.printInfo("\n " + (i + 1) + ") At the xPath - "
								+ validationStack[i][0] + " , VERIFYING for - "
								+ validationStack[i][1]);

						//assertThat(responseDoc,hasXPath(validationStack[i][0],validationStack[i][1].matches("^.+")));

						//assertThat(responseDoc,hasXPath(validationStack[i][0], equalTo(validationStack[i][1])));
						assertThat(responseDoc,hasXPath(validationStack[i][0], containsString(validationStack[i][1])));


						Util.printInfo("\n Validated Successfully !");


					}
				}
			}
		} catch (Exception e) {
			Util.printError("Error during execution !");
			e.printStackTrace();
			throw new Exception("Error is validation");


		}
		return responseDoc;
	}

	/*
	 * Function to count number of 1st child node in xml response and match it with total count
	 */

	protected void countNodes(HttpResponse response, String element)
			throws SAXException, IOException, ParserConfigurationException {

		Document responseDoc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		if (response != null) {
			InputStream inputStream = response.getEntity().getContent();
			responseDoc = db.parse(inputStream);
			// responseDoc = db.parse(file);
			responseDoc.getDocumentElement().normalize();

			Element rootNode = responseDoc.getDocumentElement();

			// int count =
			// readDoc.SelectNodes("purchaseOrders/purchaseOrder").Count;
			int totalCount = Integer.parseInt(rootNode.getAttribute("total"));

			// Returns a NodeList of all the Elements in document order with a
			// given tag name and are contained in the document.
			NodeList nodes = responseDoc.getElementsByTagName(element);
			int totalNodes = nodes.getLength();
			Util.printInfo("Total numer of " + element + " in response are: "
					+ totalNodes);

			if (totalCount != totalNodes) {
				throw new AssertionError("Total " + element + "=" + totalNodes
						+ ", but expected was " + totalCount);
			}

		}

	}

	/*
	 * Function to verify cloud credits
	 */
	
	protected BigInteger[] verifyCloudCredits(String url, String GUID) throws SAXException, IOException, ParserConfigurationException, SOAPFault_Exception {

		BigInteger availableUnits= null;
        BigInteger initialUnits= null;
        BigInteger grantedUnits= null;
        BigInteger[] credits= new BigInteger[3];
        
        getConvergentChargeSOAPService soapClientService=  new getConvergentChargeSOAPService(url);
        List<QuerySubscriptionBalanceResponse.ListOfContracts.Contract> lstContracts= soapClientService.getQuerySubscriptionBalance(GUID);
        for (int i=0;i<lstContracts.size();i++){
        	if(lstContracts.get(0).getStatus().equalsIgnoreCase("ErrorCode:10, ErrorMsg:Subscription does not exist")){
         	Util.PrintInfo("ErrorCode:10, ErrorMsg:Subscription does not exist");
        	assertUtils.assertEquals("Subscription does exist", "Subscription does not exist");
        	}
        	else{
               List<ServicePrivilege> lsServicePrivileges= lstContracts.get(0).getListOfServicePrivileges().getServicePrivilege();
               for(ServicePrivilege eachService:  lsServicePrivileges){                        

                      if (eachService.getName().equalsIgnoreCase("c3")){
                          
                    	  ListOfUnits lsUnits = eachService.getListOfUnits();
                          
                    	  List<Unit> serviceUnits = lsUnits.getUnit();
                    	  for(Unit mySerUnit: serviceUnits){
                    		  //get the type and value
                    		  if(mySerUnit.getType().equalsIgnoreCase("available")){
                    			  availableUnits=mySerUnit.getValue();
                    			  credits[0]=availableUnits;
                    		  }
                    		  if(mySerUnit.getType().equalsIgnoreCase("initial-granted")){
                    			  initialUnits=mySerUnit.getValue();
                    			  credits[1]=initialUnits;
                    		  }
                    		  if(mySerUnit.getType().equalsIgnoreCase("granted")){
                    			  grantedUnits=mySerUnit.getValue();
                    			  credits[2]=grantedUnits;
                    		  }
                    	  }
                      
                      }
               }
        }
        }
		return credits;


         
	}
	@Override
	protected void createAppWindows() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void chooseApp() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void setEnvironmentVariables() {

	}	
	public static void setEnvVariables() throws Exception {
		PELICAN_ROOT_DIR= getPelicanRootDir();
		PELICAN_FOLDER=PELICAN_ROOT_DIR+"src"+File.separator+"pelican"+File.separator;
		PELICAN_RESOURCE_DIR=PELICAN_FOLDER+"resource"+File.separator;

		PELICAN_TESTDATADIR=PELICAN_FOLDER+"testdata"+File.separator;
		adminToolPropFileLoc=PELICAN_RESOURCE_DIR+pelicanAdminPagePropFileName;
		PELICAN_XMLRESPONSE_DIR=PELICAN_FOLDER+"responseFiles"+File.separator;

	}
	/**
	 * Instantiates Pelican related web objects
	 * 
	 */

	//Changed the access modified as this will be consumed by bicPelicanWebservice 
	protected final void createAppPages() {
		//Sai:25-Sept.This is required for Pelican Admin tool. going forward we might need to use for all Admin tool tests
		if (driver!=null) {
			pelicanAdmPage= new Page(driver, adminToolPropFileLoc);
		}


	}
	/**
	 * Configures high-level bornincloud-specific objects&#58;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates CustomerPortal-specific Page objects<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates CustomerPortal-specific Window
	 * objects
	 * 
	 * @see #createAppWindows()
	 * @see #createAppPages()
	 */
	protected final void doSetup()  throws Exception{
		setEnvironmentVariables();
		/*createAppWindows();*/
		setEnvVariables();
		createAppPages();

		// MUST be called after super.setup!!!
		// NOTE that CEPUser is the equivalent of a user that is found in the
		// setup screen.
		// The user that logs in to the app is known as the auto user. Until we
		// figure out
		// whether that model applies, don't call this.
		// setCEPUser();
	}

	public void maximizeWindow() {
		driver.manage().window().maximize();

	}

	public String getPortalURL() {
		if (getEnvironmentType().equalsIgnoreCase("stg")){
			portalURL=bicPelicanConstants.PORTAL_URL_STG;
		}else if (getEnvironmentType().equalsIgnoreCase("dev")){
			portalURL=bicPelicanConstants.PORTAL_URL_DEV;
		}else {
			//TO DO for PROD if required
		}
		return portalURL;

	}
	public String getAdminToolURL() {
		if (getEnvironmentType().equalsIgnoreCase("stg")){
			adminToolURL=bicPelicanConstants.ADMINTOOL_URL_STG;
		}else if (getEnvironmentType().equalsIgnoreCase("dev")){
			adminToolURL=bicPelicanConstants.ADMINTOOL_URL_DEV;
		}else {
			//TO DO for PROD if required
		}
		return adminToolURL;

	}
	public void openAdminTool() throws Exception {		
		driver.get(getAdminToolURL());
		maximizeWindow();		
	}

	public void loginToAdminTool(String familyID,String userName,String password)  throws Exception{
		//Enter FamilyID, Username and Password . Login to pelican Admin tool
		if (!pelicanAdmPage.isFieldVisible("usersLinktab")) {			
			pelicanAdmPage.populateField("appFamilyIDloc", familyID);
			pelicanAdmPage.populateField("userIDloc", userName);
			pelicanAdmPage.populateField("passwordloc", password);
			pelicanAdmPage.click("loginButton");
		}else {
			Util.printInfo("The user has already logged into Admin tool. Hence skipping the login");
		}
	}

	/**
	 * @Description jump to specific URL . will be used when you need specific subscription or something else
	 * @param urlToNavigate
	 * @throws Exception
	 */
	public void jumpToSpecificURL(String urlToNavigate) throws Exception {
		driver.navigate().to(urlToNavigate);

	}

	public String getSubscriptionStatus() throws Exception{
		String subscriptionIDStatus=pelicanAdmPage.getValueFromGUI("expirationStatus").trim();
		if (subscriptionIDStatus.equalsIgnoreCase("-")) {
			//return empty value
			return "";
		}
		return subscriptionIDStatus;
	}

	public String getSubscriptionDate() throws Exception{
		String subscriptionExpirationDate=pelicanAdmPage.getValueFromGUI("expirationDate").trim();
		return subscriptionExpirationDate;
	}

	/**
	 * @Description this is used for the some tests where in requires a check whether subscription is active/cancelled/expred.
	 * Returns plain xml response for the caller to handle it accordingly
	 * @throws Exception
	 */
	public String findASubscriptionIdAndReturnResponse(String findBySubURL,String subscriptionID) throws Exception {
		String subReqParams=bicPelicanConstants.SUBSCRIPTION_ID_REQPARAM+subscriptionID;		
		HttpResponse findSubResponse = executeGET(findBySubURL+"/"+subscriptionID+"?"+subReqParams);
		if (findSubResponse.getStatusLine().getStatusCode()!=HttpStatusCode_OK) {
			fail("The find Subscription By Id with Sub id: "+subscriptionID+" did not execute succesfully");
		}
		//get the response entity and give it to the caller.
		String respEntity=EntityUtils.toString(findSubResponse.getEntity());		
		return respEntity;
	}

	/**
	 * @Description Do straight xml diff and find out if there is difference between two xml docs
	 * @param sourceXMLfile
	 * @param targetXmlfile
	 * @return
	 * @throws Exception 
	 */
	public boolean xmlDiff(String sourceXMLfile, String targetXmlfile) throws Exception {


		File sourceFile = new File(sourceXMLfile);
		File targetFile=  new File(targetXmlfile);
		try {
			//read the xml files and diff the files
			FileReader sourceReader= new FileReader(sourceFile);
			FileReader targetReader= new FileReader(targetFile);
			Diff differences = new Diff(sourceReader, targetReader);
			//find out if they are different or identical
			if (differences.similar()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not parse the xml files to compare. Please the source: "+sourceXMLfile+" and target xml files: "+targetXmlfile);
		}

		return false;
	}

	public ArrayList<String> createAndGetCustomerPortalUserID(String urlToLaunch) throws Exception{
		return createAndGetCustomerPortalUserID(urlToLaunch,false);
	}
	
	public ArrayList<String> createAndGetCustomerPortalUserID(String urlToLaunch, boolean inviteNewlyCreatedUser) throws Exception{
		//NOTE: The following user account will be used as CM to invite the user. Otherwise the GUID of the O2 user created via 
		//Portal will be unknown. Invite user should be used only validating the cloud credits part
		
		String cmUser = "219085955metawelcome@ssttest.net";
		String password = "Password1";
		
		ArrayList<String> o2UserIDinfo	= new ArrayList<>();	//we need only two fields
		String autodeskUserID="UserIDForTesting"+Util.getUniqueString(8);
		open(urlToLaunch);
		pelicanAdmPage.waitForFieldVisible("userID", 10000);				
		if (getEnvironmentType().equalsIgnoreCase("DEV")){
			if (!pelicanAdmPage.checkIfElementExistsInPage("toolTipForHelpLink", 1)){
				Process process = null;
				//			process=Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\build\\firefoxAutoIT.exe");
				String path = new java.io.File(".").getCanonicalPath();
				if (!path.contains("build")){
					path=path+"\\build";
				}
				process=Runtime.getRuntime().exec(path+"\\firefoxAutoIT.exe");
				process.waitFor();
				Util.sleep(2000);
			}
		}
		String emailId="pelicanUser"+Util.getUniqueString(10)+"@ssttest.net";
		if (!pelicanAdmPage.checkIfElementExistsInPage("CreateUser", 10)){
			open(urlToLaunch);
			if (!pelicanAdmPage.checkIfElementExistsInPage("CreateUser", 10)){
				EISTestBase.failTest("Create user button not found on customer portal login page");
			}
		}
		pelicanAdmPage.click("CreateUser");
		String firstName= "FirstName"+Util.getUniqueString(5);
		pelicanAdmPage.populateField("firstName",firstName);
		pelicanAdmPage.populateField("lasttName","LastName"+Util.getUniqueString(5));
		pelicanAdmPage.populateField("emailID",emailId);
		pelicanAdmPage.populateField("confirmEmailID",emailId);
		pelicanAdmPage.populateField("autodeskID",autodeskUserID);
		pelicanAdmPage.populateField("password","Password1");
		pelicanAdmPage.populateField("confirmPassword","Password1");
		pelicanAdmPage.click("confirmEmailNotificationCheckBox");
		pelicanAdmPage.click("iAgreeCheckBox");
		pelicanAdmPage.click("createAccountButton");	
		
		int i=0;
		//wait for 1 min and check if the user can login to portal
		while(!pelicanAdmPage.checkIfElementExistsInPage("ProductsandServices", 20) && i<6){			
				Util.sleep(10000);
				i=i+1;
		
		}
		
		String userID=getO2ID();
		String autoDeskID =getAutodeskID();
		String GUID =getGUID();
		
		o2UserIDinfo.add(userID);
		o2UserIDinfo.add(autoDeskID);
		o2UserIDinfo.add(GUID);
		o2UserIDinfo.add(emailId);
		
		assertTrue("User created sucesfully-UserID:"+autodeskUserID+"-SessionID:"+userID,pelicanAdmPage.checkIfElementExistsInPage("ProductsandServices", 20));
		
		return o2UserIDinfo;
	}
	public String getAutodeskID() throws Exception {
		String getAutodeskIDScript = "return Autodesk.app.userServiceData.attributes.AutodeskId";
		String autodeskID=null;
		try {			
			Object userIDobj=((JavascriptExecutor)(driver)).executeScript(getAutodeskIDScript);
			//conver userIDobj to String
			autodeskID=userIDobj.toString();			

		} catch (Exception e) {
			Util.printError("Unable to get the User Autodesk ID. Please check the login account");

		}	
		return autodeskID;
	}
	
	public String getGUID() throws Exception {
		String getAutodeskIDScript = "return Autodesk.app.userServiceData.attributes.GUID";
		String gUID=null;
		try {			
			Object userIDobj=((JavascriptExecutor)(driver)).executeScript(getAutodeskIDScript);
			//conver userIDobj to String
			gUID=userIDobj.toString();			

		} catch (Exception e) {
			Util.printError("Unable to get the User Autodesk ID. Please check the login account");
			
		}	
		return gUID;
	}
	public String getO2ID() throws Exception {
		try {
			String javaScriptToGeto2ID="return Autodesk.app.userServiceData.attributes.user.Id";
			Object o2Obj=((JavascriptExecutor)(driver)).executeScript(javaScriptToGeto2ID);
			//conver O2Obj to String
			o2_ID=o2Obj.toString();			

		} catch (Exception e) {
			Util.printError("Unable to get the User O2 ID. Please check the login account");

		}
		return o2_ID;
	}
	/**
	 * @Description create Subscription for the default currency USD
	 * @param environment
	 * @param userexternalKey
	 * @param productExternalKey
	 * @return
	 * @throws Exception
	 */
	public String createSubscription(String environment, String userexternalKey, String productExternalKey) throws Exception{

		return createSubscription(environment, userexternalKey, productExternalKey, "USD");
	}
	public String createSubscription(String environment, String userexternalKey, String subscriptionOfferExternalKey,String currencyName) throws Exception{
		String subscriptionId = null;
		Document responseDoc = null;
		String createSubsUrl="";

		if(environment.equalsIgnoreCase("STG"))
			createSubsUrl = "http://pelican-stg-2091252326.us-east-1.elb.amazonaws.com/tfel2rs/v2/subscription";
		else if(environment.equalsIgnoreCase("DEV"))
			createSubsUrl = "http://pelican-dev-lb-2025909158.us-west-1.elb.amazonaws.com/tfel2rs/v2/subscription";


		String createSubsRequest= bicPelicanConstants.USER_EXTERNALKEY_REQPARAM+userexternalKey+"&"+bicPelicanConstants.SUBSCRIPTION_OFFER_EXTERNALKEY+subscriptionOfferExternalKey+"&"+bicPelicanConstants.CURRENCY_NAME+currencyName;

		HttpResponse createSubsResponse = executePOSTWithHMAC(createSubsUrl,createSubsRequest,"application/x-www-form-urlencoded; charset=UTF-8");
		System.out.println("Request Body:  " +createSubsRequest);
		/*

		HttpEntity entity1 = createSubsResponse.getEntity();
        String xmlResp= EntityUtils.toString(entity1,"UTF-8");
        System.out.println(xmlResp);

		 */
		if (createSubsResponse != null && createSubsResponse.getStatusLine().getStatusCode() != 200 ){
			throw new AssertionError("The Response code is "+createSubsResponse.getStatusLine().getStatusCode()+", but expected was 200 \n . Found the response: as \n"+
					EntityUtils.toString(createSubsResponse.getEntity()));
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
	 * @Description cancel the subscription via Admin tool
	 * @param appFamilyIDName
	 * @param loginName
	 * @param password
	 * @param subscriptionId
	 * @param cancelNowOrLater : true for cancel at the end of billing . false for cancel immediately
	 * @throws Exception
	 */
	public void cancelSubscriptionInAdminTool(String appFamilyIDName, String loginName,String password,String subscriptionId,boolean cancelNowOrLater) throws Exception {
		openAdminTool();
		loginToAdminTool(appFamilyIDName,loginName,password);
		String curURL=driver.getCurrentUrl();
		Util.sleep(2000);
		//jump directly to specific url appending the subscription ID
		jumpToSpecificURL(curURL+"subscription/show?id="+subscriptionId);			
		//click on Cancel button on the admin tool
		Util.printInfo("Going to cancel the Subscription with ID: "+subscriptionId);			
		pelicanAdmPage.click("cancelSubscriptionBtn");
		Util.printInfo("Clicking on 'Cancel button' ");
		if (cancelNowOrLater) {			
			pelicanAdmPage.click("cancelLaterRadiobtn");// cancel later i.e at the end of billing period
		}else {
			pelicanAdmPage.click("cancelNowRadiobtn");// cancel now
		}

		pelicanAdmPage.click("confirmBtnInDiaog");
		Util.sleep(5000);	//to avoid race condition and admin tool takes time to update the status
		//also check if the subscription is found as cancelled or expired based on cancelnoworlater 
		if (cancelNowOrLater) {
			assertUtils.assertEquals("The Status of the Subscription with the ID: "+subscriptionId+ " with cancel at end of billing should be",getSubscriptionStatus(), "CANCELLED");
		}else {
			assertUtils.assertEquals("The Status of the Subscription with the ID: "+subscriptionId+ " with cancel immediately should be",getSubscriptionStatus(), "EXPIRED");
		}
	}
	public void printErrorResponse(HttpResponse response) throws ParseException, IOException {

		HttpEntity entity = response.getEntity();

		String xmlResp= EntityUtils.toString(entity,"UTF-8");
		Util.PrintInfo("Response is : "+xmlResp);
	}	




	public ArrayList<String> submitNewPurchaseOrdersNewUser(String purchaseOrderUrl,String prodName,String contentType,String upgradePurchaseSubId) throws Exception {
		
		ArrayList<String> lstPurchaseOrders= new ArrayList<>();
		String keyToExtract  = "lineItems.lineItem.subscriptionPlan.subscriptionPlanPurchaseResponse";
		String url = purchaseOrderUrl;
		ServiceUtils servUtil= new ServiceUtils();
		String[] userDetails= servUtil.createNewUserwithPortal(prodName);

		String request= createRequestBodyForPurchaseOrder(userDetails[0],prodName,upgradePurchaseSubId);
		System.out.println("Request Body" +request);

		// EXECUYTING RESPONSE
		HttpResponse response = (HttpResponse) executePOSTWithAccssKey(url,request,contentType);

		if (response != null && response.getStatusLine().getStatusCode() != HttpStatusCreatedCode_OK ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HttpStatusCreatedCode_OK);
		}
		//get the subscription id via SubmitPurchaseOrder 
		String respCont = EntityUtils.toString(response.getEntity());
		Util.printInfo("Fetch the Subscription ID for the newly created subscription via Submit Purchase Order");
		String respFile=createXMlFileWithResponse(respCont,"submitNewPurchaseOrdersNewUser.xml");			
		//fetch second line i.e subscription id tag.			
		String subscriptionId= getSubscriptionIdCreatedViaSubmitPurchaseOrder(respFile, keyToExtract,"subscriptionId");
		lstPurchaseOrders.add(subscriptionId);
		
		return lstPurchaseOrders;
	}

	public String createRequestBodyForPurchaseOrder(String UserId, String ProductName, String UpgradeSubsID){


		String currencyId="4";
		Random randomGenerator = new Random(); 
		int randomNumber=((1+ randomGenerator.nextInt(5)) * 10000 +randomGenerator.nextInt(10000));
		String CreditCardNumber="44444444444"+randomNumber;	
		String accessKey = null;
		if (getEnvironmentType().equalsIgnoreCase("dev")) {
			accessKey=bicPelicanConstants.ACCESSKEY_DEV;
		}else {
			accessKey=bicPelicanConstants.ACCESSKEY_STG;
		}
		String requestBody="<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"+
				"<purchaseOrder accessKey=\""+accessKey+"\" partnerId=\""+getAUTH_PARTNERID()+"\" appFamilyId=\""+getAuth_FamilyId()+"\" version=\"1.0\">"+
				"<buyerUser id=\""+UserId+"\" />"+
				"<orderCommand>CHARGE</orderCommand>"+
				" <lineItems>"+
				"<lineItem>"+
				"<subscriptionPlan>"+
				"<subscriptionPlanPurchaseRequest currencyId=\""+currencyId+"\" offerExternalKey=\""+ProductName+"\"  upgradeFromSubscriptionId=\""+UpgradeSubsID+"\"/>"+
				"	 </subscriptionPlan>"+
				"</lineItem>"+
				"</lineItems>"+
				"<payment configId=\""+getConfigID_SubmitPurchaseOrder()+"\">"+
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
	public static String getConfigID_SubmitPurchaseOrder() {
		if (getEnvironmentType().equalsIgnoreCase("dev")) {
			configID_SubmitPurchaseOrder=bicPelicanConstants.configID_DEV;
		}else {
			configID_SubmitPurchaseOrder=bicPelicanConstants.configID_STG;
		}		
		return configID_SubmitPurchaseOrder;
	}

	public String getSubscriptionIdCreatedViaSubmitPurchaseOrder(String xmlFileName,String keyString, String attributeToExtract ) throws ConfigurationException {
		File file = new File(xmlFileName);
		String subID = null;
		XMLConfiguration xmlConfigFile = new XMLConfiguration(file);	
		SubnodeConfiguration configurationAt = xmlConfigFile.configurationAt(keyString);
		List<ConfigurationNode> rootAttrs=configurationAt.getRootNode().getAttributes();
		for (ConfigurationNode eachAttr: rootAttrs) {
			if (eachAttr.getName().equalsIgnoreCase(attributeToExtract)) {
				// get the value of node ID
				subID=eachAttr.getValue().toString();
				break;
			}
		}
		return subID;
	}
	
	public void logingToPortal(String userName, String password) throws Exception {
		
		pelicanAdmPage.populateField("username", userName);
		pelicanAdmPage.populateField("password", password);

		pelicanAdmPage.clickToSubmit("loginButton");    	
		Util.sleep(2000);
		//wait for the product and services page to appear
		 WebDriverWait wb = new WebDriverWait (driver, 90);
		 pelicanAdmPage.isFieldPresent("ProductsandServices");
		 wb.until(ExpectedConditions.visibilityOf(pelicanAdmPage.getCurrentWebElement()));	
		
	}
	
}

