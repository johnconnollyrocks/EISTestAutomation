package cep.portal.webservicestesting;

import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.Util;

public class getProductUpdatesTest extends EISWebService{	
	
	private static String propertiesFileName=Thread.currentThread().getStackTrace()[1].getFileName().split("\\.")[0];
	private static Properties testProperties =null; 
	
	private static String environment= System.getProperty("environment");
	private static String passWord	=null;
	private static String userName= null;
	private static String getProductUpdatesURL=null;
	private HttpClient myClient =null;
	private String requestPayload		= null;
	private String expRespData=null;
	
	
	//Static block to get the user name and password
	//The user name and password are hard coded here as the 
	static
	{
		testProperties=readPropertiesFile(propertiesFileName);
		userName=getLoginCredentialsBasedOnEnv("GETPRODUCTUPDATES")[0];
		passWord=getLoginCredentialsBasedOnEnv("GETPRODUCTUPDATES")[1];
		
	}

	public getProductUpdatesTest() {
		super(environment, userName, passWord);
		//modify the URL based on the environment
		getProductUpdatesURL=testProperties.getProperty("requestURL");
		requestPayload=getSpecificTestDataBasedOnEnv("requestPayLoad");
		expRespData=getSpecificTestDataBasedOnEnv("responseContent");
		getProductUpdatesURL=getProductUpdatesURL.replace("[environmentType]", environment.toLowerCase());
	}
	
	@BeforeClass
	public void setupBaseState() throws Exception {
		loginToCustomerPortal(environment);				
	}
	
	@Test
	public void testgetProductUpdatesService()  throws Exception{
		myClient=getHttpClient();
		getProductUpdatesURL=getProductUpdatesURL.replace("[guid]", getUserGUID()).replace("[token]", getGrantTokenOfSession());
		Util.printInfo("The URL which is being used is :"+getProductUpdatesURL);
		postRequest(requestPayload, getProductUpdatesURL);
		String respData=getResponseContent();
		cepAssert.assertEquals(respData, expRespData, "The getProductupdates.json response should be same as expected");
	}
	
	@AfterClass
	public void cleanUpTest() {
		closeConnection();
	}
	
}
