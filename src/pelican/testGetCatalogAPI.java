package pelican;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.Util;


public class testGetCatalogAPI extends bicPelicanWebService{

	private static String propertiesFileName=Thread.currentThread().getStackTrace()[1].getFileName().split("\\.")[0];	
	private String apiURL=null;
	private String reqParams = null;
	private String expResponseTagsToCheck=null;
	private ArrayList<String> actualResponseTags= null;
	private ArrayList<String> actResponseContent= null;
	private String testContentToVerify= null;
	private String billingOptionTag =null;
	private String keyToSearchInXML= "upgradeOfferings.subscriptionPlan";
	private String newHmac=null;
	private CloseableHttpClient testClient =null;
	/*private static final String envt= "STG";*/
	
	@BeforeClass(groups= {"SANITY","ALL","testGetCatalogAPI"})
	@Parameters("environmentType")	
	public void setUpTest(@Optional("STG") String environment) throws Exception{	
		System.out.println("getcatalog is executed");
		testProperties=readPropertiesFile(propertiesFileName);
		setEnvironmentType(environment);
		assignSecretKey();
		newHmac=getHMACSignatureValue();
		apiURL=  getTestDataBasedOnEnvironment("authURL");
		


	}
	/**
	 * @Description Verify that when get Catalog options with FSN Trial is given then the subscription to be seen are Base,Pro,Student and Ultimate (only in DEV)
	 * @throws Exception
	 */

	@Test(groups= {"SANITY","ALL","testGetCatalogAPI","scenarioTrial"})
	public void testGetCatalogAPITestWithFSNTrialVersion() throws Exception {
			
		try {

			reqParams=bicPelicanConstants.USER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("userExternalKey_Step1")+"&"+bicPelicanConstants.OFFER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("offerExternalKey_Step1")
					+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");


			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);
			
			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testGetCatalogAPITestWithFSNTrialVersion.xml");
			Util.printInfo("Verify that the Http status code of the get Catalog api with Trial to Base,Pro Student and Enthusiast is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The GetCatalogAPI Test with the requestParams for the scenario FSN Trial :"+reqParams+
					"OK.");		
			Util.printInfo("Verifying the GetCatalog API response xml");
			verifyXMLResponseGeneratedforGetCatalogAPI(respFile,"_Step1","FSN Trial to Base, Pro,Student and Enthusiast");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testGetCatalogAPITestWithFSNTrialVersion' ");			
		}		
	}
	/**
	 * @Description Verify that when get Catalog options with FSN Base is given then the subscription to be seen are Pro,Ultimate (only in DEV)
	 * @throws Exception
	 */
	@Test(groups= {"SANITY","ALL","testGetCatalogAPI","scenarioBase"})	
	public void testGetCatalogAPITestWithFSNBase() throws Exception{
		try {
			
			reqParams=bicPelicanConstants.USER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("userExternalKey_Step2")+"&"+bicPelicanConstants.SUBSCRIPTION_ID_REQPARAM+getTestDataBasedOnEnvironment("subscriptionID_Step2")+"&"+bicPelicanConstants.OFFER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("offerExternalKey_Step2")
					+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");
			
			/*HttpClient myClient=HttpClientBuilder.create().build();*/
			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);
			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testGetCatalogAPITestWithFSNBaseVersion.xml");
			Util.printInfo("Verify that the Http status code of the get Catalog api with FSN Base to Pro is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), Integer.valueOf(HttpStatusCode_OK),"The GetCatalogAPI Test scenario with FSN Base to Pro and with the requestParams :"+reqParams+
					" OK.");		
			Util.printInfo("Verifying the GetCatalog API response xml");
			verifyXMLResponseGeneratedforGetCatalogAPI(respFile,"_Step2","FSN Base to Pro");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testGetCatalogAPITestWithFSNBase' ");
		}		
	
		
		
	}
	/**
	 * @Description Verify that when get Catalog options with FSN Student is given then the subscription external keys to be seen are Base, Pro,Ultimate (only in DEV)
	 * @throws Exception
	 */

	@Test(groups= {"ALL","testGetCatalogAPI","scenarioStudent"})	
	public void testGetCatalogAPITestWithFSNStudent() throws Exception{
		try {
		
			reqParams=bicPelicanConstants.USER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("userExternalKey_Step3")+"&"+bicPelicanConstants.SUBSCRIPTION_ID_REQPARAM+getTestDataBasedOnEnvironment("subscriptionID_Step3")
					+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");
			
			/*myClient=getHttpClient();*/
			/*HttpClient myClient=HttpClientBuilder.create().build();*/
			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse= executeGET(apiURL+"?"+reqParams);
			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testGetCatalogAPITestWithFSNBaseVersion.xml");
			Util.printInfo("Verify that the Http status code of the get Catalog api with FSN Base to Pro is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), Integer.valueOf(HttpStatusCode_OK),"The GetCatalogAPI Test scenario with FSN Student to Base and Pro and with the requestParams :"+reqParams+
					" OK.");		
			Util.printInfo("Verifying the GetCatalog API response xml");
			verifyXMLResponseGeneratedforGetCatalogAPI(respFile,"_Step3","FSN Student to Base,Pro");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testGetCatalogAPITestWithFSNStudent' ");
		}		
			
	}
	/**
	 * @Description Verify that when get Catalog options with MayaLT Trial is given then the subscription to be seen are Base and Advanced
	 * @throws Exception
	 */

	@Test(groups= {"SANITY","ALL","testGetCatalogAPI","scenarioMayaLTTrial"})	
	public void testGetCatalogAPITestWithMayaLTTrial() throws Exception{
		try {

			reqParams=bicPelicanConstants.USER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("userExternalKey_Step4")+"&"+bicPelicanConstants.SUBSCRIPTION_ID_REQPARAM+getTestDataBasedOnEnvironment("subscriptionID_Step4")
					+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");
			/*myClient=getHttpClient();*/
			testClient=getHttpClient();
			setHttpClient(testClient);
			/*HttpClient myClient=HttpClientBuilder.create().build();
			setHttpClient(myClient);*/
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testGetCatalogAPITestWithMayaLTTrial.xml");
			Util.printInfo("Verify that the Http status code of the get Catalog api with MayaLT Trial to Base and Advanced is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), Integer.valueOf(HttpStatusCode_OK),"The GetCatalogAPI Test scenario with MayaLT Trial to Base and Advanced  and with the requestParams :"+reqParams+
					" OK.");		
			Util.printInfo("Verifying the GetCatalog API response xml");
			verifyXMLResponseGeneratedforGetCatalogAPI(respFile,"_Step4","MayaLT Trial to Base and Advanced");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testGetCatalogAPITestWithMayaLTTrial' ");
		}		
	
		
		
	}
	/**
	 * @Description Verify that when get Catalog options with MayaLT Base is given then the subscription to be seen is Advanced only
	 * @throws Exception
	 */

	@Test(groups= {"ALL","testGetCatalogAPI","scenarioMayaLTBase"})	
	public void testGetCatalogAPITestWithMayaLTBase() throws Exception{
		try {
		/*	reqParams="auth.partnerId="+getAUTH_PARTNERID()+"&"+"auth.appFamilyId="+getAuth_FamilyId()+"&auth.timestamp="+auth_TimeStamp+"&"+
					"auth.signature="+newHmac+"&"+getTestDataBasedOnEnvironment("subscriptionID_Step5")+"&"+getTestDataBasedOnEnvironment("offerExternalKey_Step5")
					+"&"+getTestDataBasedOnEnvironment("fr.skipCount");*/
			
			reqParams=bicPelicanConstants.OFFER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("offerExternalKey_Step5")
					+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");
			
			testClient=getHttpClient();
			setHttpClient(testClient);
			/*HttpClient myClient=getHttpClient();
			setHttpClient(myClient);*/
			/*HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);*/
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testGetCatalogAPITestWithMayaLTBase.xml");
			Util.printInfo("Verify that the Http status code of the get Catalog api with Maya LT Base to Advanced is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), Integer.valueOf(HttpStatusCode_OK),"The GetCatalogAPI Test scenario with MayaLT Base to  Advanced  and with the requestParams :"+reqParams+
					" OK.");		
			Util.printInfo("Verifying the GetCatalog API response xml");
			verifyXMLResponseGeneratedforGetCatalogAPI(respFile,"_Step5","MayaLT Trial to Base and Advanced");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testGetCatalogAPITestWithMayaLTTrial' ");
		}		
	
		
		
	}
	/**
	 * @Description NEGATIVE scenario Verify that when get Catalog options with MayaLT Trial is given with subscription ID as expired, then empty response with tags should return
	 * @throws Exception
	 */

	
	@Test( groups= {"SANITY","ALL","testGetCatalogAPI","scenarioMayaLTTrialWithExpired"})	
	public void testGetCatalogAPITestWithMayaLTTrialWithExpiredSubID() throws Exception{
		try {
			/*reqParams=getTestDataBasedOnEnvironment("subscriptionID_Step6")+"&"+getTestDataBasedOnEnvironment("offerExternalKey_Step6")
					+"&"+getTestDataBasedOnEnvironment("fr.skipCount");*/
			
			reqParams=bicPelicanConstants.USER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("userExternalKey_Step6")+"&"+bicPelicanConstants.SUBSCRIPTION_ID_REQPARAM+getTestDataBasedOnEnvironment("subscriptionID_Step6")
					+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");
			
			/*myClient=getHttpClient();*/
			testClient=getHttpClient();
			setHttpClient(testClient);
			/*HttpClient myClient=HttpClientBuilder.create().build();
			setHttpClient(myClient);*/
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testGetCatalogAPITestWithMayaLTTrialWithExpiredSubID.xml");
			Util.printInfo("Verify that the Http status code of the get Catalog api with Maya LT Trial with expired Sub ID is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), Integer.valueOf(HttpStatusCode_OK),"The GetCatalogAPI Test scenario with MayaLT Trial with expired Subscription ID and  with the requestParams :"+reqParams+
					" OK.");		
			Util.printInfo("Verifying the GetCatalog API response xml");
			/*verifyXMLResponseForNegativeScenarios(respFile,"_Step6","MayaLT Trial with expired subscription ID");*/
			verifyXMLResponseGeneratedforGetCatalogAPI(respFile, "_Step6", "testGetCatalogAPITestWithMayaLTTrialWithExpiredSubID");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testGetCatalogAPITestWithMayaLTTrialWithExpiredSubID' ");
		}	
	}
	/**
	 * @Description NEGATIVE scenario Verify that when get Catalog API with no user external key and subscription ID returns proper error message
	 * @throws Exception
	 */

	@Test(groups= {"ALL","testGetCatalogAPI"})	
	public void testGetCatalogAPITestWithNoUserExternalKeyAndSubID() throws Exception{
		try {
			/*reqParams="auth.partnerId="+getAUTH_PARTNERID()+"&"+"auth.appFamilyId="+getAuth_FamilyId()+"&auth.timestamp="+auth_TimeStamp+"&"+
					"auth.signature="+newHmac+"&"+getTestDataBasedOnEnvironment("fr.skipCount");*/
			
			/*myClient=getHttpClient();*/
			reqParams=bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");

			testClient=getHttpClient();
			setHttpClient(testClient);
			/*HttpClient myClient=HttpClientBuilder.create().build();
			setHttpClient(myClient);*/
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);
			String respCont=EntityUtils.toString(testReponse.getEntity());
			Util.printInfo("Verify that the Http status code of the get Catalog api with no Subscription ID and user External Key should be 400");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), Integer.valueOf(HttpStatusCode_BAD_REQ),"The GetCatalogAPI Test scenario with no Subcription ID and Offer externalKey should be 400 (bad Request) ");		
			Util.printInfo("Verifying the GetCatalog API response content");
			String expErrorMsg=testProperties.getProperty("errorMessage_Step7");
			assertUtils.assertTrueWithFlags("The getCatalogAPI response with no user external Key and Subscription ID should give proper error message", respCont.contains(expErrorMsg));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testGetCatalogAPITestWithNoUserExternalAndSubID' ");
		}			
	}
	
	/**
	 * @Description NEGATIVE scenario Verify that when get Catalog API with invalid external Key returns proper error message
	 * @throws Exception
	 */

	@Test(groups= {"ALL","testGetCatalogAPI"})	
	public void testGetCatalogAPITestWithInvalidOfferExternalKey() throws Exception{
		try {
			String offerExtKey=testProperties.getProperty("offerExternalKey_Step8");
			/*reqParams="auth.partnerId="+getAUTH_PARTNERID()+"&"+"auth.appFamilyId="+getAuth_FamilyId()+"&auth.timestamp="+auth_TimeStamp+"&"+
					"auth.signature="+newHmac+"&offerExternalKey="+offerExtKey+"&"+getTestDataBasedOnEnvironment("fr.skipCount");*/
			
			reqParams=bicPelicanConstants.OFFER_EXTERNALKEY_REQPARAM+offerExtKey+"&"+getTestDataBasedOnEnvironment("fr.skipCount");
			/*myClient=getHttpClient();*/
			testClient=getHttpClient();
			setHttpClient(testClient);
			/*HttpClient myClient=HttpClientBuilder.create().build();
			setHttpClient(myClient);*/
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);
			String respCont=EntityUtils.toString(testReponse.getEntity());
			Util.printInfo("Verify that the Http status code of the get Catalog api with invalid Offer External Key should be 400");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), Integer.valueOf(HttpStatusCode_BAD_REQ),"The GetCatalogAPI Test scenario with invalid Offer External Key and with the requestParams :"+reqParams+
					" should be 400 (bad Request)");		
			Util.printInfo("Verifying the GetCatalog API response content");
			String expErrorMsg=testProperties.getProperty("errorMessage_Step8")+" "+offerExtKey;
			assertUtils.assertTrueWithFlags("The getCatalogAPI response with Invalid external Key should give proper error message", respCont.contains(expErrorMsg));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testGetCatalogAPITestWithNoUserExternalAndSubID' ");
		}			
	}
	
	/**
	 * @Description NEGATIVE scenario Verify that when get Catalog API with invalid Subscription ID returns proper error message
	 * @throws Exception
	 */

	@Test(groups= {"ALL","testGetCatalogAPI"})	
	public void testGetCatalogAPITestWithInvalidSubscriptionID() throws Exception{
		try {
			String subID=testProperties.getProperty("subscriptionID_Step9");
			/*reqParams="auth.partnerId="+getAUTH_PARTNERID()+"&"+"auth.appFamilyId="+getAuth_FamilyId()+"&auth.timestamp="+auth_TimeStamp+"&"+
					"auth.signature="+newHmac+"&subscriptionID="+subID+"&"+getTestDataBasedOnEnvironment("fr.skipCount");*/
			reqParams=bicPelicanConstants.SUBSCRIPTION_ID_REQPARAM+subID+"&"+bicPelicanConstants.OFFER_EXTERNALKEY_REQPARAM+testProperties.getProperty("offerExternalKey_Step9")+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");
			
			/*myClient=getHttpClient();*/
			testClient=HttpClientBuilder.create().build();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);
			String respCont=EntityUtils.toString(testReponse.getEntity());
			Util.printInfo("Verify that the Http status code of the get Catalog api with invalid Subscription ID hould be 400");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), Integer.valueOf(HttpStatusCode_BAD_REQ),"The GetCatalogAPI Test scenario with invalid Offer External Key and with the requestParams :"+reqParams+
					" should be 400 (bad Request)");		
			Util.printInfo("Verifying the GetCatalog API response content");
			String expErrorMsg=testProperties.getProperty("errorMessage_Step9")+" "+subID;
			assertUtils.assertTrueWithFlags("The getCatalogAPI response with Invalid Subscription ID should give proper error message", respCont.contains(expErrorMsg));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testGetCatalogAPITestWithNoUserExternalAndSubID' ");
		}			
	}
	
	/**
	 * @ SCENARIO 10
	 * @Description This test verifies that exact xml response with the actual getcatalogApi response. Straight diff
	 * @throws Exception
	 */

	@Test(groups= {"ALL","testGetCatalogAPI"})	
	public void testGetCatalogAPITestWithExpectedgetCatalogAPIResponse() throws Exception{
		try {
			String expgetCatalogAPIResponse=null;
			reqParams=bicPelicanConstants.USER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("userExternalKey_Step10")+"&"+bicPelicanConstants.OFFER_EXTERNALKEY_REQPARAM+getTestDataBasedOnEnvironment("offerExternalKey_Step10")
					+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");		
			testClient=HttpClientBuilder.create().build();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);
			String respCont=EntityUtils.toString(testReponse.getEntity());
			//truncate the last ip address and UTC time zone 
			respCont=respCont.replaceAll("<!--.*?-->", "");
			String respFile=createXMlFileWithResponse(respCont,"testGetCatalogAPITestWithExpectedgetCatalogAPIResponse.xml");
			Util.printInfo("Verify that the Http status code of the get Catalog api with invalid Subscription ID should be 200");			
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The GetCatalogAPI Test scenario for static xml response comparison");
			
			//compare with the expected api catalog file
			if (getEnvironmentType().equalsIgnoreCase("dev")) {
				expgetCatalogAPIResponse=PELICAN_XMLRESPONSE_DIR+"expectedGetCatalogAPI_DEV.xml";
			}else {
				expgetCatalogAPIResponse=PELICAN_XMLRESPONSE_DIR+"expectedGetCatalogAPI_STG.xml";
			}
			
			assertUtils.assertTrue(xmlDiff(expgetCatalogAPIResponse, respFile),"The response for GetCatalog API should match");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error occurred while running test: 'testGetCatalogAPITestWithExpectedgetCatalogAPIResponse' ");
		}			
	}
	
	
	public void verifyXMLResponseForNegativeScenarios(String xmlFileToRead,String stepName,String scenarioName)  throws Exception {
		
		/*String expResponseTagToVerify=getTestDataBasedOnEnvironment("expResponseTagsToCheck"+stepName);*/
		String expResponseTagToVerify=testProperties.getProperty("expResponseTagsToCheck"+stepName);
		File file = new File(xmlFileToRead);
		XMLConfiguration xmlConfigFile = new XMLConfiguration(file);			
		String actualResponseTagVal=xmlConfigFile.getString(expResponseTagToVerify);
		if (actualResponseTagVal==null) {
			//assign blank value
			actualResponseTagVal="";
			//the value of the actual response tag should be empty					
			assertUtils.assertTrueWithFlags("getCatalogAPI", scenarioName, "test step: getCatalogAPI with expired subscription ID returns no response for FSN Trial", actualResponseTagVal.isEmpty());				
		}else {
			throw new AssertionError("Found unexpected response for test step: getCatalogAPI with expired subscription ID and FSN Trial. Expected to be empty");
		}
		
	}

	/**
	 * @Description: Common method to read the xml file
	 * @param xmlFileToRead
	 * @param stepName
	 * @param scenarioName
	 * @throws Exception
	 */
	
	public synchronized void verifyXMLResponseGeneratedforGetCatalogAPI(String xmlFileToRead,String stepName,String scenarioName) throws Exception{

		try {
			actualResponseTags= new ArrayList<>();
			actResponseContent = new ArrayList<>();
			billingOptionTag=getTestDataBasedOnEnvironment("billingOptionTag");
			testContentToVerify=testProperties.getProperty("testContentToVerify");		
			expResponseTagsToCheck=testProperties.getProperty("responseTagsToCheck");

			String[] responseTagarr= expResponseTagsToCheck.split(":");
			ArrayList<String> expResponseTags= new ArrayList<>(Arrays.asList(responseTagarr));
			String billingOptionKey = testProperties.getProperty("billingOptionsKey");
			String billingPeriodKey=  testProperties.getProperty("billingPeriodKey");
			String[] billOptionsInfo = getTestDataBasedOnEnvironment("billingOptionsValues"+stepName).split(":");
			ArrayList<String> expBillingOptions= new ArrayList<>(Arrays.asList(billOptionsInfo));

			String[] billPeriodInfo = getTestDataBasedOnEnvironment("billingPeriodValues"+stepName).split(":");
			ArrayList<String> expBillingPeriodOptions= new ArrayList<>(Arrays.asList(billPeriodInfo));

			String expProductExternalKeys=getTestDataBasedOnEnvironment("expProductExternalKeys"+stepName);		
			String[] exptestContent =  expProductExternalKeys.split(":");		
			ArrayList<String> expResponseContent= new ArrayList<>(Arrays.asList(exptestContent));

			
			File file = new File(xmlFileToRead);
			XMLConfiguration xmlConfigFile = new XMLConfiguration(file);			
			List<HierarchicalConfiguration> subscriptionPlans=xmlConfigFile.configurationsAt(keyToSearchInXML);
			for(HierarchicalConfiguration subPlan: subscriptionPlans) {
				String subscriptionId=null;
				ConfigurationNode planNode = subPlan.getRootNode();
				/*System.out.println(planNode.getName());*/
				List<ConfigurationNode> attrs=planNode.getAttributes();
				for(ConfigurationNode attrNode: attrs) {
					String nodeTagname=attrNode.getName();
					String nodeTagvalue=attrNode.getValue().toString();
					actualResponseTags.add(nodeTagname);	
					//get only the external Keys to arrayList or what ever the key you are looking for
					if (nodeTagname.equalsIgnoreCase(testContentToVerify)) {
						actResponseContent.add(nodeTagvalue);	
					}
					//this is used just for reporting purpose
					if (nodeTagname.equalsIgnoreCase("id")) {
						subscriptionId=nodeTagvalue;
					
					//Now check billing Cycle or plans for the same
					List<HierarchicalConfiguration> billingSubscriptionPlans=subPlan.configurationsAt(billingOptionTag);
					for(HierarchicalConfiguration node1: billingSubscriptionPlans) {
						ConfigurationNode billingPerNode=node1.getRootNode();						
						for(int i=0;i<billingPerNode.getAttributeCount();i++) {
							if (billingPerNode.getAttribute(i).getName().equalsIgnoreCase("type")) {
								String billVal=billingPerNode.getAttribute(i).getValue().toString();
								if (!expBillingOptions.contains(billVal)) {
									assertUtils.fail("The billing Option for the Subscription Id:  " +subscriptionId+ "  not found as expected. Found "+billVal+" expected to be "+expBillingOptions.toString());
								}
							}else if (billingPerNode.getAttribute(i).getName().equalsIgnoreCase("count")) {
								String billPeriodVal=billingPerNode.getAttribute(i).getValue().toString();
								if (!expBillingPeriodOptions.contains(billPeriodVal))
									assertUtils.fail("The billing Period for the Subscription Id:  "+subscriptionId+ "  not found as expected. Found "+billPeriodVal+" expected to be "+expBillingPeriodOptions.toString());
								
							}
						
						}
					}
				}
					
				}
				//Verify that all the subscription plans has expected response tags
				assertUtils.assertEqualsWithFlags("The subscription plan information for subscription ID: "+subscriptionId, "testGetCatalogAPI", scenarioName,actualResponseTags, expResponseTags,true);

			}
			//verify that externalKeys are showing up correctly for Trial
			assertUtils.assertEqualsWithFlags("testGetCatalogAPI", scenarioName, actResponseContent, expResponseContent);

			actualResponseTags.clear();
			actResponseContent.clear();
			expResponseTags.clear();
			
			/*System.out.println("ff");*/
		} catch (Exception e) {			
			e.printStackTrace();
			throw new Exception("Unable to parse the Response xml file :"+xmlFileToRead+" which is created for test GetCatalog API for test scenario"+stepName);
		}
		

		}
	
	@AfterClass
	public void tearDown() throws Exception {
		testClient.close();	//realease the resources
		System.out.println("Release the resources in getCatalogAPI");
		System.out.println("Have cleared the props");
		testProperties.clear();
	}
}

