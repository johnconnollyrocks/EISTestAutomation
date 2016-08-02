package pelican;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import bornincloud.BornInCloudTestBase;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;



public class testRestartSubscriptions extends bicPelicanWebService{

	private static String propertiesFileName=Thread.currentThread().getStackTrace()[1].getFileName().split("\\.")[0];	

	private String apiURL=null;
	private String reqBody = null;
	private CloseableHttpClient testClient =null;
	private String testContentType="application/x-www-form-urlencoded; charset=UTF-8";

	private String loginName = null;
	private String appFamilyIDName = null;
	private String password = null;		
	private String expErrorMsg = null;
	private String  restartUrl = "{subID}/restart";
	private String subId_NegativeTest=null;
	private String submitPurchaseOrderURL = null;

	@BeforeClass(groups= {"SANITY","ALL","testCancelSubscriptions"})
	@Parameters("environmentType")	
	public void setUpTest(@Optional("DEV") String environment) throws Exception{		
		//doing this way, as i dont want to launch the browser before starting the setup method
		initializeWebDriver(browserType);	
		doSetup();
		System.out.println("Test Cancel Subscriptions is being executed");
		testProperties=readPropertiesFile(propertiesFileName);
		setEnvironmentType(environment);
		assignSecretKey();
		apiURL=  getTestDataBasedOnEnvironment("authURL");
		appFamilyIDName = getTestDataBasedOnEnvironment("appFamilyIDUI");
		loginName= getTestDataBasedOnEnvironment("loginNameUI");
		password=getTestDataBasedOnEnvironment("passwordUI");
		
		submitPurchaseOrderURL = EnvironmentURL("DEV")+testProperties.getProperty("submitPO_URL");
	}


	@Test(groups= {"SANITY","ALL","testRestartSubscriptionWithCancelAtTheEndOfBillingPeriod"})
	public void testRestartSubscriptionWithCancelAtTheEndOfBillingPeriod() throws Exception {

		try 
		{
			String offerExtKey= getTestDataBasedOnEnvironment("subscriptionOfferExternalKey_Step1");
			/*String usrExternalKey = getTestDataBasedOnEnvironment("userExternalKey_Step1");
			String offerExtKey= getTestDataBasedOnEnvironment("subscriptionOfferExternalKey_Step1");
			
			//create Subscription before running this test  and then cancel it
			Util.printInfo("Creating the subscription before cancelling it for the user:"+usrExternalKey);*/
			
			//NOTE: For all rental products, do not run the create subscription call directly do it via SubmitPurchase orderapi
			ArrayList<String> submitNewPurchaseOrdersNewUser = submitNewPurchaseOrdersNewUser(submitPurchaseOrderURL, offerExtKey, bicPelicanConstants.SUBMITPO_CONTENTTYPE, "");
			String subscriptionId=submitNewPurchaseOrdersNewUser.get(0);	//the first val is Sub id
			/*String subId=createSubscription(getEnvironmentType(), usrExternalKey, offerExtKey);*/
			subId_NegativeTest=subscriptionId;
			 reqBody = bicPelicanConstants.SUBSCRIPTION_RESTART_ID_REQPARAM+subscriptionId;
			 //change the api URL as 
			String restartApiURL=apiURL+restartUrl;
			restartApiURL=restartApiURL.replace("{subID}", subscriptionId);
			//cancel the subscription the subscription
			Util.printInfo("Cancelling the subscription with id: "+subscriptionId);
			cancelSubscriptionInAdminTool(appFamilyIDName, loginName, password, subscriptionId,true);
			Util.printInfo("Cancelled the subscription");
			Util.printInfo("Now going to restart the subscription");
			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse restartResponse = executePUT(restartApiURL, reqBody, testContentType);
			assertUtils.assertEquals(restartResponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The Test Restart subscription with the reqBody :"+reqBody+" OK");
		 
		} catch (Exception e) {			
			e.printStackTrace();
			throw new Exception("Error occured while running the restarting the subscription");
		}

	}

	@Test(groups= {"SANITY","ALL","testRestartSubscriptionForCancelImmediately"})
	public void testRestartSubscriptionForCancelImmediately() throws Exception {
		
		try {
			expErrorMsg = testProperties.getProperty("expErrorMsg");
			String offerExtKey= getTestDataBasedOnEnvironment("subscriptionOfferExternalKey_Step2");
	/*		String usrExternalKey = getTestDataBasedOnEnvironment("userExternalKey_Step2");
			
			
			//create Subscription before running this test  and then cancel it
			Util.printInfo("Creating the subscription for the scenario the user:"+usrExternalKey);
			String subId=createSubscription(getEnvironmentType(), usrExternalKey, offerExtKey);*/
			
			//NOTE: For all rental products, do not run the create subscription call directly do it via SubmitPurchase orderapi
			ArrayList<String> submitNewPurchaseOrdersNewUser = submitNewPurchaseOrdersNewUser(submitPurchaseOrderURL, offerExtKey, bicPelicanConstants.SUBMITPO_CONTENTTYPE, "");
			String subscriptionId=submitNewPurchaseOrdersNewUser.get(0);	//the first val is Sub id
			
			 reqBody = bicPelicanConstants.SUBSCRIPTION_RESTART_ID_REQPARAM+subscriptionId;
			//cancel the subscription the subscription
			Util.printInfo("Cancelling the subscription with id: "+subscriptionId);
			cancelSubscriptionInAdminTool(appFamilyIDName, loginName, password, subscriptionId,false);
			Util.printInfo("Cancelled the subscription");
			Util.printInfo("Try to restart the subscription");
			
			testClient=getHttpClient();
			setHttpClient(testClient);
			String restartApiURL=apiURL+restartUrl;
			restartApiURL=restartApiURL.replace("{subID}", subscriptionId);
			HttpResponse restartResponse = executePUT(restartApiURL, reqBody, testContentType);
			String actResponsecont=EntityUtils.toString(restartResponse.getEntity());
			String respFile=createXMlFileWithResponse(actResponsecont,"testRestartSubscriptionForCancelImmediately.xml");
			Util.printInfo("Found error message as :"+actResponsecont);
			assertUtils.assertTrue(readAndGetXMLErrorMessage(respFile).contains(expErrorMsg),"Test Restart expired Subscription error message should match");			
			assertUtils.assertEquals(restartResponse.getStatusLine().getStatusCode(), HttpStatusCode_BAD_REQ,"The test Restart subscription for Cancel immediately with the reqBody :"+reqBody+" 400");
		 
		} catch (Exception e) {			
			e.printStackTrace();
			throw new Exception("Error occured while running the restarting the subscription");
		}

	}
	/**
	 * @Description Negative scenario try to restart subscription which is made as active for a cancelled one
	 * @throws Exception
	 */
	@Test(dependsOnMethods="testRestartSubscriptionWithCancelAtTheEndOfBillingPeriod",groups= {"SANITY","ALL","testRestartSubscriptionForCancelImmediately"})
	public void testRestartSubscriptionAgainForActiveSubscriptionID() throws Exception {
		
		try {
			expErrorMsg = testProperties.getProperty("activeSubErrorMsg");
			
			Util.printInfo("Try to restart the subscription again which made as active for subscription with id: "+subId_NegativeTest);
			if (testClient!=null) {
				testClient=getHttpClient();
				setHttpClient(testClient);
			}
			String restartApiURL=apiURL+restartUrl;
			restartApiURL=restartApiURL.replace("{subID}", subId_NegativeTest);
			reqBody = bicPelicanConstants.SUBSCRIPTION_RESTART_ID_REQPARAM+subId_NegativeTest;
			HttpResponse restartResponse = executePUT(restartApiURL, reqBody, testContentType);
			String actResponsecont=EntityUtils.toString(restartResponse.getEntity());
			String respFile=createXMlFileWithResponse(actResponsecont,"testRestartSubscriptionAgainForActiveSubscriptionID.xml");
			Util.printInfo("Found error message as :"+actResponsecont);
			assertUtils.assertTrue(readAndGetXMLErrorMessage(respFile).contains(expErrorMsg),"Test Restart again for active Subscription error message should match");			
			assertUtils.assertEquals(restartResponse.getStatusLine().getStatusCode(), HttpStatusCode_BAD_REQ,"The test Restart subscription again for active Subscription :"+reqBody+" 400");
		 
		} catch (Exception e) {			
			e.printStackTrace();
			throw new Exception("Error occured while running the restarting the subscription");
		}

	}

	public String readAndGetXMLErrorMessage(String xmlFileName) throws Exception {
		File file = new File(xmlFileName);
		XMLConfiguration xmlConfigFile = new XMLConfiguration(file);	
		String errorMessage=xmlConfigFile.getString("message");
		return errorMessage;
		
		
	}
	
	@AfterClass(groups= {"SANITY","ALL","testRestartSubscriptions"})
	public void tearDown() throws Exception {
		driver.close();
		testClient.close();
		killProcess(browserType);		
		System.out.println("Have cleared the restart Subscription API props");
		testProperties.clear();

	}
}

