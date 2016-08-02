package cep.portal.webservicestesting;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.EISTestBase;
import common.TestProperties;
import common.Util;

/**
 * @Description This is base class for testing all the webservices on Portal
 * NOTE: WRITING NEW METHODS HERE JUST FOR LAUNCHING PHANTOM WEBDRIVER 
 * NOT USING THE EXISTING CUSTOMER PORTALTESTBASE/EISTEST BASE CLASS JUST TO BE INDEPENDENT AND MAINTAINANCE WILL BE EASIER 
 * @author t_marus
 *
 */

public class EISWebService {


	private String URL_DEV="https://customer-dev.autodesk.com/cep";	
	private String URL_STG="https://customer-stg.autodesk.com/cep";
	private String URL_PRD="https://manage.autodesk.com";

	private final static String WEBSERVICES_LOCATION			=	 "\\src\\cep\\portal\\webservicestesting\\";

	public static  Properties testProperties=null;
	private String environmentURL=null;

	private String userGUID =null;
	private String grantToken =null;
	private Date cookieExpiry=null;
	/**
	 * The following metadata locators remains here we dont need any separate prop file to maintain. As this is only required
	 * to login to Portal to pull the grant token
	 */
	private String userNameMetadataLocator=	"//*[@id='userName_str']";
	private String passwordMetadataLocator="//*[@id='password_str']";
	private String loginButtonMetadataLocator="//*[@id='login_container']/div/p[1]/button";
	private String expectedElementTobePresent ="//*[contains(text(),'All Products')]";


	private String userName			= null;
	private String passWord			= null;
	private String sessionIDVal		= null;
	private static String environmentType=System.getProperty("environment");
	private PhantomJSDriver driver=null;
	private BasicCookieStore cookieStore =null;
	private BasicClientCookie cookie = null; 
	private HttpClient client = null;	
	private HttpResponse response =null;


	/* Logger for better logging purpose. Not yet consumed. TO DO*/
	public static Logger  logger=Logger.getLogger(EISWebService.class.getName());

	public EISWebService() {
		//nothing just a default constructor explicitly defining it
	}
	public EISWebService(String testEnvironment,String userName,String passWord) {
		setEnvironmentURL(testEnvironment);
		this.userName=userName;
		this.passWord=passWord;

	}

	/**
	 * @Description Instantiates the Selenium headlesss Phantom driver object here and sets the capabilities to point to the phantomjsdriver.exe 
	 * @throws Exception
	 */
	public void loginToPortalAsHeaderLess() throws Exception{
		/**
		 * Using Phantom js driver to launch the portal and get the grant token
		 */
		try {
			DesiredCapabilities capa= new DesiredCapabilities();
			capa.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, Util.getTestRootDir()+"\\build\\phantomjs.exe");
			driver= new PhantomJSDriver(capa);
			

		} catch (Exception driverEx) {
			Util.printError("Unable to instantiate the Phantom driver object. Please check the phantomjs.exe location exists in the build folder of EISAutomation");
			logger.error("Unable to instantiate the Phantom driver object. Please check the phantomjs.exe location exists in the build folder of EISAutomation");
			driverEx.printStackTrace();
		}

	}

	/**
	 * @Description: Extracts the Grant token of the logged user via JavaScriptExecutor engine
	 * @return
	 */
	public String getGrantTokenOfSession() {		
		return this.grantToken.replace("\"", "");
	}
	
	public HttpClient getClient() {
		return this.client;
	}
	/**
	 * @Description  Sets the Grant token to the identifier of the current object via running Javascript in Phantom
	 * @throws Exception
	 */
	private void setGrantToken() throws Exception {
		try {
			String javaScriptTogetGrantToken="return window.sessionStorage.getItem(\"userdata:grantToken\")";			
			Object tokenObj=driver.executeScript(javaScriptTogetGrantToken);
			//convert tokenObj to String
			this.grantToken=tokenObj.toString();

		} catch (Exception e) {
			Util.printError("Unable to fetch the grant token of the logged in user");
			logger.error("Unable to fetch the grant token of the logged in user"+ "============\n");
			e.printStackTrace();	
		}
	}

	public String getUserGUID() {
		return userGUID.replace("\"", "");
	}

	/**
	 * @Description Sets the user GUID to the userGUID identifies of the current object via Javascript which runs in phantomjs
	 * @throws Exception
	 */
	public void setUserGUID() throws Exception {
		try {
			String javaScriptToGetGUID="return Autodesk.app.userServiceData.attributes.GUID";
			Object guidObj=driver.executeScript(javaScriptToGetGUID);
			//conver guidobj to String
			this.userGUID=guidObj.toString();
			captureScreenshot();

		} catch (Exception e) {
			Util.printError("Unable to get the User GUID. Please check the login account");
			captureScreenshot();
		}
	}

	/**
	 * @Description Logs into the Customer portal and to the get the Grant token, GUID. This is mandatory step. Without proper login credentials no test will run properly.
	 * @param environmentType
	 * @throws Exception
	 */

	public void loginToCustomerPortal(String environmentType) throws Exception {
		try {

			loginToPortalAsHeaderLess();
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			setEnvironmentURL(environmentType);		
			if (environmentType.equalsIgnoreCase("dev")){
				driver.get(URL_DEV);			
			}else if (environmentType.equalsIgnoreCase("stg")){
				driver.get(URL_STG);
			}else{

				// TO DO for prod environment if required
			}
			
			
			
			driver.findElement(By.xpath(userNameMetadataLocator)).sendKeys(userName);
			driver.findElement(By.xpath(passwordMetadataLocator)).sendKeys(passWord);
			driver.findElement(By.xpath(loginButtonMetadataLocator)).click();
			Util.sleep(7000);
			//Wait till the services are laoded
			WebDriverWait waitForElement = new WebDriverWait(driver, 60, 2);
			waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(expectedElementTobePresent)));
			setUserGUID();
			setGrantToken();
		} catch (Exception e) {
			captureScreenshot();
			Util.printError("Unable to login to Customer Portal : "+environmentType+ " please check increase the time out duration");
		}

	}
	/**
	 * @Description Capture screenshot if something goes wrong while trying to login to customer portal with the given credentials
	 * @throws Exception
	 */
	public void captureScreenshot() throws Exception{
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//Copy the screenshot to reports folder
		String screenshotPath=Util.getTestRootDir()+"\\test-output\\driverException"+Util.getTimestamp()+".png";
		FileUtils.copyFile(srcFile, new File(screenshotPath));
	} 

	/**
	 * @Description sets the Environment URL based on the environment type
	 * @param environmentType
	 */

	public void setEnvironmentURL(String environmentType) {
		if (environmentType.equalsIgnoreCase("DEV")){
			environmentURL=URL_DEV;
		}else if (environmentType.equalsIgnoreCase("STG")){
			environmentURL=URL_STG;
		}else{
			environmentURL=URL_PRD;
		}

	}

	/**
	 * @Description loads the properties file required for the test
	 * @param propFileName
	 */
	public static Properties readPropertiesFile(String propFileName) {
		//get the test Properties file of the test you wanted
		try{			
			testProperties=Util.loadPropertiesFile(Util.getTestRootDir()+WEBSERVICES_LOCATION+propFileName+".properties");
			Util.printInfo("Successfully loaded test properties file name:"+propFileName);
			logger.info("Successfully loaded test properties file name:"+propFileName);
		}
		catch (Exception e) {
			logger.error("Unable to load the test properties file name:"+propFileName+". Please check if the file exists in webservicestesting folder");
		}
		return testProperties;		
	} 

	/**
	 * @Description gets the environment type.Its a just getter method
	 * @return
	 */
	public static String getEnvironmentType() {
		return environmentType;
	}
	/**
	 * @Description Gets the session Id by reading the cookies of the current session this is mandatory stuff to do 
	 * @return
	 */

	public String getSessionId() {
		Set<Cookie> lsCookies=driver.manage().getCookies();

		for (Cookie cookie : lsCookies) {
			/*System.out.println(cookie.getName());
			System.out.println(cookie.getValue() + "\n");*/
			if(cookie.getName().equalsIgnoreCase("sessionID")) {
				Util.printInfo("Found the Session id");
				Util.printInfo("The Session id value is :"+cookie.getValue());				
				sessionIDVal=cookie.getValue();
				Util.printInfo("The Session id Expiry is :"+cookie.getExpiry());				
				cookieExpiry=cookie.getExpiry();
				break;
			}
		}
		return sessionIDVal;
	}

	/**
	 * @Description: Set the cookie store
	 */
	public void setCookieStore() {
		sessionIDVal=getSessionId();
		cookieStore= new BasicCookieStore();
		cookie= new BasicClientCookie("sessionId", sessionIDVal);
		cookie.setDomain(".autodesk.com");
		cookie.setPath("/");
		cookie.setExpiryDate(cookieExpiry);
		cookieStore.addCookie(cookie);
	}

	/**
	 *  @Description Gets the current httpclient object to the caller 
	 */
	public HttpClient getHttpClient() {
		setCookieStore();
		client=HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
		return client;
	}

	/**
	 * @Description post the request with the required payload
	 * @param requestPayLoad
	 * @param requestURL
	 * @return
	 * @throws Exception
	 */
	public HttpResponse postRequest(String requestPayLoad, String requestURL) throws Exception {
		HttpPost postRequest= new HttpPost(requestURL);
		postRequest.setHeader("Content-Type", "application/json");
		//Define the String Entity for post
		StringEntity sEntity= new StringEntity(requestPayLoad);
		postRequest.setEntity(sEntity);				
		response= client.execute(postRequest);
		return response;
	}

	/**
	 * @Description get the response content in form of string
	 * @param requestURL
	 * @return
	 * @throws Exception
	 */

	public String getResponseContent()  throws Exception {
		String respContent=null;
		try {
			respContent=EntityUtils.toString(response.getEntity());		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("** Unable to get the response object content. Please check if response object is created before you call");					
		}
		return respContent;
	}

	/**
	 * @Description Does get request to the specifed URL
	 * @param requestURL
	 * @return
	 * @throws Exception
	 */
	public HttpResponse getRequest(String requestURL) throws Exception {
		HttpGet getRequest= new HttpGet(requestURL);
		getRequest.setHeader("Content-Type", "application/json");						
		response= client.execute(getRequest);
		return response;
	}

	/**
	 * @Description check if the response is OK or 200.
	 * @return
	 * @throws Exception
	 */
	public boolean isResponseOK() throws Exception {
		int respCode=response.getStatusLine().getStatusCode();
		if (respCode==HttpStatus.SC_OK) {
			Util.printInfo("** Response status is OK.");
			return true;
		}
		else {
			String reasonPhrase=response.getStatusLine().getReasonPhrase();
			Util.printInfo("*** Failed! The response status is not 200.Found the actual response as :"+respCode+ " the reason is "+reasonPhrase);			
		}
		return false;
	}

	/**
	 * @Description this is return to make it easy in terms of extracting the test data for specific environment
	 * @param testName
	 * @return
	 */
	public static String[] getLoginCredentialsBasedOnEnv(String testName) {
		String[] userCredentials= new String[2];//always 2 username, password
		if (getEnvironmentType().equalsIgnoreCase("dev")) {
			userCredentials[0]=testProperties.getProperty(testName+"_USERNAME_DEV");
			userCredentials[1]=testProperties.getProperty(testName+"_PASSWORD_DEV");		
		}else{
			userCredentials[0]=testProperties.getProperty(testName+"_USERNAME_STG");
			userCredentials[1]=testProperties.getProperty(testName+"_PASSWORD_STG");
		}		
		return userCredentials;
	}
	/**
	 * @Description Extract test data based on environment
	 * @param testDataProperty
	 * @return
	 */
	public String getSpecificTestDataBasedOnEnv(String testDataProperty) {
		String testDataPropValue=null;
		if(getEnvironmentType().equalsIgnoreCase("dev")){						
			testDataPropValue=testProperties.getProperty(testDataProperty+"_DEV");
		}else {
			testDataPropValue=testProperties.getProperty(testDataProperty+"_STG");			
		}		
		return testDataPropValue;
	}

	/**
	 * @Description Kill the phantomjs object at the end
	 */
	public void closeConnection() {
		driver.quit();
	}
	/**
	 * @Description This is used to add certificate to cacert folder. Usually while trying to run this command the user should have elevated privileges like Admin.
	 *  			This works in our ECS nodes as by default the logged in user is as an admin.
	 * @param certificateDetails
	 * @throws Exception
	 */
	public void addCertificateToCerts(Map<String, String> certificateDetails) throws Exception{
		String javaHome=System.getenv("JAVA_HOME");
		javaHome=javaHome.substring(0,javaHome.length()-1);
		Process proc =null;
		int retVal=-1;
		try {
			Random rand = new Random();
			int randInt=rand.nextInt(10);
			for(Map.Entry<String, String> certsSet:certificateDetails.entrySet()) {
				proc=Runtime.getRuntime().exec("keytool -import -trustcacerts -noprompt -alias \"Autodesk-Issuing"+randInt+"\"  -file "+Util.getTestRootDir()+"\\build\\"+certsSet.getKey()+" -keystore " + "\"" + javaHome + "\\jre\\lib\\security\\cacerts\" -storepass "+certsSet.getValue());
				retVal=proc.waitFor();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Util.printError("** Unable to add certificate to certs folder in Java directory. Please try to add it manually and proceed");
		}

	}
	
}

