package pelican;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eviware.soapui.support.StringUtils;

import bsh.StringUtil;

import common.Util;



public class testFindSubscriptionPlansAPI extends bicPelicanWebService{

	private static String propertiesFileName=Thread.currentThread().getStackTrace()[1].getFileName().split("\\.")[0];	
	private String apiURL=null;
	private String reqParams = null;	
	private String newHmac=null;
	private CloseableHttpClient testClient =null;
	private String prodLinecode=null;
	
	@BeforeClass(groups= {"SANITY","ALL","testFindSubscriptionPlansAPI"})
	@Parameters("environmentType")	
	public void setUpTest(String environment) throws Exception{	
		System.out.println("testFindSubscriptionPlansAPI is being executed");
		testProperties=readPropertiesFile(propertiesFileName);
		setEnvironmentType(environment);
		assignSecretKey();
		newHmac=getHMACSignatureValue();
		apiURL=  getTestDataBasedOnEnvironment("authURL");

	}
	
	/**
	 * @SCENARIO 1
	 * @Description Verify that find Subscription plans with Maya LT is given, the Product line code is found in all sub plans 
	 * @throws Exception
	 */

	@Test( groups= {"SANITY","ALL","testFindSubscriptionPlansAPI"})
	public void testFindSubscriptionPlansWithProductLineCodeMayaLT() throws Exception {
			
		try {
			prodLinecode= testProperties.getProperty("productLine_Step1");
			String subscriptionKeyTag = testProperties.getProperty("subscriptionPlanXMLTagExtract");			
			reqParams=bicPelicanConstants.PRODUCT_LINE+prodLinecode+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+testProperties.getProperty("fr.skipCount_Step1");

			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testFindSubscriptionPlansWithProductLineCodeMayaLT.xml");
			Util.printInfo("Verify that the Http status code of the find Subscription Plans api with Product Line code is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The Find Subscription Plans API Test with the requestParams for the scenario Product Line code :"+reqParams+
					"OK.");		
			Util.printInfo("Verifying the find Subscription Plans response xml");
			verifyXMLResponseFile(respFile,subscriptionKeyTag,"testFindSubscriptionPlans With ProductLineCode MayaLT","_Step1");
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testFindSubscriptionPlansWithProductLineCodeMayaLT' ");			
		}		
	}
	
	/**
	 * @SCENARIO 2
	 * @Description Verify that find Subscription plans with FSN360 and Usage type -COM is given, the Product line code and usage type is found in all sub plans 
	 * @throws Exception
	 */

	@Test(groups= {"SANITY","ALL","testFindSubscriptionPlansAPI"})
	public void testFindSubscriptionPlansWithProductLineCodeFSN360AndUsageTypeCOM() throws Exception {
			
		try {
			prodLinecode= testProperties.getProperty("productLine_Step2");
			String usageType=testProperties.getProperty("usageType_Step2");
			String subscriptionKeyTag = testProperties.getProperty("subscriptionPlanXMLTagExtract");			
			reqParams=bicPelicanConstants.USAGE_TYPE+usageType+"&"+bicPelicanConstants.PRODUCT_LINE+prodLinecode+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+testProperties.getProperty("fr.skipCount_Step2");

			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testFindSubscriptionPlansWithProductLineCodeFSN360AndUsageTypeCOM.xml");
			Util.printInfo("Verify that the Http status code of the find Subscription Plans api with Product Line code :"+prodLinecode+" and Usage type :" +usageType+" is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The Find Subscription Plans API Test with the requestParams:"+reqParams+" OK.");		
			Util.printInfo("Verifying the find Subscription Plans response xml");
			verifyXMLResponseFile(respFile,subscriptionKeyTag,"testFindSubscriptionPlans With ProductLineCode and Usage Type","_Step2");
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testFindSubscriptionPlansWithProductLineCodeMayaLT' ");			
		}		
	}

	/**
	 * @ SCENARIO 3
	 * @Description Verify that find Subscription plans with only Usage type -COM is given is found in all sub plans 
	 * @throws Exception
	 */

	@Test(groups= {"SANITY","ALL","testFindSubscriptionPlansAPIWithUsageType"})
	public void testFindSubscriptionPlansWithOnlyUsageType() throws Exception {
			
		try {
			String usageType=testProperties.getProperty("usageType_Step3");
			String subscriptionKeyTag = testProperties.getProperty("subscriptionPlanXMLTagExtract");			
			reqParams=bicPelicanConstants.USAGE_TYPE+usageType+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+testProperties.getProperty("fr.skipCount_Step3");

			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testFindSubscriptionPlansWithOnlyUsageTypeCOM.xml");
			Util.printInfo("Verify that the Http status code of the find Subscription Plans api with Usage type :" +usageType+" is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The Find Subscription Plans API Test with the requestParams:"+reqParams+" OK.");		
			Util.printInfo("Verifying the find Subscription Plans response xml");
			verifyXMLResponseFile(respFile,subscriptionKeyTag,"testFindSubscriptionPlans With Usage Type","_Step3");
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testFindSubscriptionPlansWithProductLineCodeMayaLT' ");			
		}		
	}
	
	/**
	 * @ SCENARIO 4
	 * @Description Verify that find Subscription plans with only single OfferExternal Keyis given is found in all sub plans 
	 * @throws Exception
	 */

	@Test(groups= {"SANITY","ALL","testFindSubscriptionPlansWithOnlyExternalKey"})
	public void testFindSubscriptionPlansWithOnlyExternalKey() throws Exception {
			
		try {
			String extKey=testProperties.getProperty("offerExternalKey_Step4");
			String subscriptionKeyTag = testProperties.getProperty("subscriptionPlanXMLTagExtract");			
			reqParams=bicPelicanConstants.OFFER_EXTERNALKEYS_REQPARAM+extKey+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+testProperties.getProperty("fr.skipCount_Step4");

			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testFindSubscriptionPlansWithOnlyExternalKey.xml");
			Util.printInfo("Verify that the Http status code of the find Subscription Plans api with single ExternalKey :" +extKey+" is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The Find Subscription Plans API Test with the requestParams:"+reqParams+" OK.");		
			Util.printInfo("Verifying the find Subscription Plans response xml");
			verifyXMLResponseFile(respFile,subscriptionKeyTag,"testFindSubscriptionPlans With External Key","_Step4");
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testFindSubscriptionPlansWithOnlyExternalKey' ");			
		}		
	}
	
	/**
	 * @ SCENARIO 5
	 * @Description Verify that find Subscription plans with multiple OfferExternal Key is given and same key is found in all sub plans 
	 * @throws Exception
	 */

	@Test(groups= {"ALL","testFindSubscriptionPlansWithMulitpleExternalKeys"})
	public void testFindSubscriptionPlansWithMulitpleExternalKeys() throws Exception {
			
		try {
			String extKey=getTestDataBasedOnEnvironment("offerExternalKeys_Step5");
			String subscriptionKeyTag = testProperties.getProperty("subscriptionPlanXMLTagExtract");			
			reqParams=bicPelicanConstants.OFFER_EXTERNALKEYS_REQPARAM+extKey+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+testProperties.getProperty("fr.skipCount_Step5");

			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testFindSubscriptionPlansWithMulitpleExternalKeys.xml");
			Util.printInfo("Verify that the Http status code of the find Subscription Plans api with Multiple ExternalKeys :" +extKey+" is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The Find Subscription Plans API Test with the requestParams:"+reqParams+" OK.");		
			Util.printInfo("Verifying the find Subscription Plans response xml");
			verifyXMLResponseFile(respFile,subscriptionKeyTag,"testFindSubscriptionPlans With multiple External Keys","_Step5");
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testFindSubscriptionPlansWithMulitpleExternalKeys' ");			
		}		
	}
	
	/**
	 * @ SCENARIO 6
	 * @Description Verify that find Subscription plans without any input should return all subscription Plans
	 * @throws Exception
	 */

	@Test(groups= {"ALL","testFindSubscriptionPlansWithNoInputtype"})
	public void testFindSubscriptionPlansWithNoInputtype() throws Exception {
			
		try {
			
			String subscriptionKeyTag = testProperties.getProperty("subscriptionPlanXMLTagExtract");			
			reqParams=bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+testProperties.getProperty("fr.skipCount_Step6");

			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testFindSubscriptionPlansWithNoInputtype.xml");
			Util.printInfo("Verify that the Http status code of the find Subscription Plans api with no input type is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The Find Subscription Plans API Test with the requestParams:"+reqParams+" OK.");		
			Util.printInfo("Verifying the find Subscription Plans response xml");
			verifyXMLResponseFile(respFile,subscriptionKeyTag,"testFindSubscriptionPlans With No Input type","_Step6");
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testFindSubscriptionPlansWithNoInputtype' ");			
		}		
	}
	
	/**
	 * @ SCENARIO 7
	 * @Description Verify that find Subscription plans with incorrect product line code should return error
	 * @throws Exception
	 */

	@Test(groups= {"ALL","testFindSubscriptionPlansWithNoInputtype"})
	public void testFindSubscriptionPlansWithIncorrectProductLineCodeReturnError() throws Exception {
			
		try {
			String prodLine=testProperties.getProperty("productLine_Step7");
			String subscriptionKeyTag = testProperties.getProperty("");			
			reqParams=bicPelicanConstants.PRODUCT_LINE+prodLine+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+testProperties.getProperty("fr.skipCount_Step7");
			
			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testFindSubscriptionPlansWithIncorrectProductLineCodeReturnError.xml");
			Util.printInfo("Verify that the Http status code of the find Subscription Plans api with incorrect product line code is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_OK,"The Find Subscription Plans API Test with incorrect product line code :"+reqParams+" OK.");		
			Util.printInfo("Verifying the find Subscription Plans response xml");
			verifyXMLResponseFile(respFile,subscriptionKeyTag,"testFindSubscriptionPlans with incorrect product line code","_Step7");
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testFindSubscriptionPlansWithIncorrectProductLineCodeReturnError' ");			
		}		
	}
	
	/**
	 * @ SCENARIO 8
	 * @Description Verify that find Subscription plans with incorrect Usage Type should return error
	 * @throws Exception
	 */

	@Test(groups= {"SANITY","ALL","testFindSubscriptionPlansWithIncorrectUsageTypeCodeReturnError"})
	public void testFindSubscriptionPlansWithIncorrectUsageTypeCodeReturnError() throws Exception {
			
		try {
			String usageType=testProperties.getProperty("usageType_Step8");
			String subscriptionKeyTag = testProperties.getProperty("subscriptionPlanXMLTagExtract");			
			reqParams=bicPelicanConstants.USAGE_TYPE+usageType+"&"+bicPelicanConstants.FR_SKIP_COUNT_REQPARAM+getTestDataBasedOnEnvironment("fr.skipCount");
			
			testClient=getHttpClient();
			setHttpClient(testClient);
			HttpResponse testReponse=executeGET(apiURL+"?"+reqParams);			
			String respCont=EntityUtils.toString(testReponse.getEntity());
			String respFile=createXMlFileWithResponse(respCont,"testFindSubscriptionPlansWithIncorrectProductLineCodeReturnError.xml");
			Util.printInfo("Verify that the Http status code of the find Subscription Plans api with incorrect product line code is as expected");
			assertUtils.assertEquals(testReponse.getStatusLine().getStatusCode(), HttpStatusCode_BAD_REQ,"The Find Subscription Plans API Test with incorrect Usage type :"+reqParams+" OK.");		
			Util.printInfo("Verifying the find Subscription Plans response xml");
			//look for the error tag			
			assertUtils.assertTrueWithFlags("Find Subscription Plans", "with incorrect usage type", "the response returned should give error code", respCont.contains("<error>"));
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the response for the test: 'testFindSubscriptionPlansWithIncorrectProductLineCodeReturnError' ");			
		}		
	}
		
	public void verifyXMLResponseFile(String respXMLFileName,String keyToSearchInXML, String scenarioName,String stepName) throws Exception {
		try {
			String productName=null;
			ArrayList<String> lstExpContentInfo=null;
			String expusageTypeAttr=testProperties.getProperty("expContentUsageTypeAttrToCheck"+stepName);
			String productLineCodeInResponse=testProperties.getProperty("expContentProdLineTagToCheck"+stepName);
			String expAttr1=testProperties.getProperty("expContentProdLineTagAttribute1ToExtract"+stepName);
			String expAttr2=testProperties.getProperty("expContentProdLineTagAttribute2ToExtract"+stepName);
			String[] expContentInfo=null;
			
			/// for Step name 5, for multiple offer external keys the prod line codes are different in STG and DEV hence need to maintain the following way
			
			if (stepName.equalsIgnoreCase("_Step5")) {
				expContentInfo= getTestDataBasedOnEnvironment("expContentToVerify"+stepName).split(":");
				lstExpContentInfo= new ArrayList<>(Arrays.asList(expContentInfo));
			}else {
				if (testProperties.getProperty("expContentToVerify"+stepName)!=null) {
					
					expContentInfo= testProperties.getProperty("expContentToVerify"+stepName).split(":");
					lstExpContentInfo= new ArrayList<>(Arrays.asList(expContentInfo));
				}
			}
				
			
			ArrayList<String>  lstActContent = new ArrayList<>();
			
			File file = new File(respXMLFileName);
			XMLConfiguration xmlConfigFile = new XMLConfiguration(file);	
			
			//For negative scenarios
			//for Step7 & Step8 just read the one of the subcription key in xml file and confirm it doesnt exist
			if (stepName.equalsIgnoreCase("_Step7")){
				String verifySubKey=xmlConfigFile.getString("expXMLKeyStringToExtract"+stepName);				
				assertUtils.assertTrueWithFlags("Find Subscription Plans", scenarioName, "the response returned should be empty", StringUtils.isNullOrEmpty(verifySubKey));
				return;
			}
			List<HierarchicalConfiguration> subscriptionPlans=xmlConfigFile.configurationsAt(keyToSearchInXML);
			//look through all the subscription plans
			for(HierarchicalConfiguration subPlan: subscriptionPlans) {				
				String nodeVal=null;
				boolean foundUsageType=false;
				ConfigurationNode planNode = subPlan.getRootNode();				
				List<ConfigurationNode> attrs=planNode.getAttributes();
				//get the name attribute for better reporting purpose				
				for(ConfigurationNode attrNode: attrs) {
					String nodeTagname=attrNode.getName();
					nodeVal=attrNode.getValue().toString();
					if (nodeTagname.equalsIgnoreCase("name")) {
						productName=attrNode.getValue().toString();
					}
					//if scenario has usage type ie. for Step2 assign the boolean value. The usage type for the response should be there
				 if (nodeTagname.equalsIgnoreCase(expusageTypeAttr)) {
						 foundUsageType=true;
						 if (stepName.equalsIgnoreCase("_Step2")) {	
							 //get the attribute value of usage type	this is required for Step 2
							 lstActContent.add(attrNode.getValue().toString());							 
						 }
					}
					
				}
				//if scenario has usage type ie. for Step2 verify the following
				//Applicable for Step3 or scenario 3
				if (stepName.equalsIgnoreCase("_Step2")) {					
					assertUtils.assertTrueWithFlags("Find Subscription Plans", scenarioName, "the usage type attribute for the Sub plan name: "+productName+"  exist",  foundUsageType);					
				}
				if (stepName.equalsIgnoreCase("_Step3")){
					String usageTypeAttval=testProperties.getProperty("expContentToVerify_Step3");
					assertUtils.assertTrueWithFlags("Find Subscription Plans", scenarioName, "the usage type attribute for the Sub plan name: "+productName+"  exist",  foundUsageType);
					//also check that node val is as expected
					assertUtils.assertTrueWithFlags("Find Subscription Plans", scenarioName, "the usage type attribute value for the Sub plan name: "+productName+"  exist", usageTypeAttval.equalsIgnoreCase(nodeVal));
				}
				//for the step4 , need to check only external key and certain node level
				if (stepName.equalsIgnoreCase("_Step4") || stepName.equalsIgnoreCase("_Step5") || stepName.equalsIgnoreCase("_Step6")) {	
					String expExternalKeyAttr= testProperties.getProperty("expExternalKeyAttrToCheck"+stepName);
					String xmlKeytoCheck=testProperties.getProperty("expXMLKeyStringToExtract"+stepName);
					List<HierarchicalConfiguration> subOfferPlans = subPlan.configurationsAt(xmlKeytoCheck);
					for(HierarchicalConfiguration subOfferPlan:subOfferPlans) {
						//get the attributes
						ConfigurationNode eachSubOfferrootnode = subOfferPlan.getRootNode();
						for (int i=0;i<eachSubOfferrootnode.getAttributeCount();i++) {
							String subOfferNodename=eachSubOfferrootnode.getAttribute(i).getName();
							if (subOfferNodename.equalsIgnoreCase(expExternalKeyAttr)) {
								//get the node attribute val						
								lstActContent.add(eachSubOfferrootnode.getAttribute(i).getValue().toString());								
							}
						}
						
					}					
				}
				
				if (stepName.equalsIgnoreCase("_Step1") || stepName.equalsIgnoreCase("_Step2")) {		
				//get product line code				
				String actualProdLinecodeAttrValue=subPlan.getProperty(productLineCodeInResponse+"[@"+expAttr1+"]").toString();
				String actualProdLineNameAttrValue=subPlan.getProperty(productLineCodeInResponse+"[@"+expAttr2+"]").toString();				
				
				//add the code and name to list and verify 
				lstActContent.add(actualProdLinecodeAttrValue);
				lstActContent.add(actualProdLineNameAttrValue);
				
				//for Steps: Step1 need to verify the following										
				assertUtils.assertEqualsWithFlags("Find subscription Plan for Product : "+productName, scenarioName,lstActContent, lstExpContentInfo);				
				}
													
				//clear the actual array list only when stepname is step2
				if (stepName.equalsIgnoreCase("_Step2")||stepName.equalsIgnoreCase("_Step1")){
					lstActContent.clear();	
				}	
         			
			}
		//requried at the end of loop for Step4 and Step5
			if (stepName.equalsIgnoreCase("_Step4")){
				assertUtils.assertEqualsWithFlags("Find subscription Plan for Product : "+productName+"  with only  External key", scenarioName,lstActContent, lstExpContentInfo);
			}else if (stepName.equalsIgnoreCase("_Step5")){
				assertUtils.assertEqualsWithFlags("Find subscription Plan for Product : "+productName+"  with Multiple  External keys", scenarioName,lstActContent, lstExpContentInfo);
			}
			
			
			//this is requried only for Step6 ie all Subscription offer external Keys will be verified
			if (stepName.equalsIgnoreCase("_Step6")){
				//verify each value as there are dummy products in DEV or STG env hence dont do deep verification , just pull each value and verify
				assertUtils.assertEqualsWithFlags("Find subscription Plans  with no input type","", scenarioName,lstActContent, lstExpContentInfo,true);
			}
			lstActContent.clear();	//also clear at the end 
			
		} catch (Exception e) {			
			e.printStackTrace();
			throw new Exception("Unable to parse the Response xml file :"+respXMLFileName+" which is created for Find subscription Plans API for test scenario"+scenarioName);
		}
		
		
	}
	@AfterClass
	public void tearDown() throws Exception {
		testClient.close();
		System.out.println("Have cleared the FindSubscriptionPlans API props");
		testProperties.clear();
		
	}
}

