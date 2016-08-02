package pelican;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import common.Util;



public class testCancelSubscriptions extends bicPelicanWebService{

	private static String propertiesFileName=Thread.currentThread().getStackTrace()[1].getFileName().split("\\.")[0];	
	
	private static final String  tempenv  ="DEV";
	private String apiURL=null;
	private String reqBody = null;
	private CloseableHttpClient testClient =null;
		
	private String loginName = null;
	private String appFamilyIDName = null;
	private String password = null;
	private String expiredStatus ="EXPIRED";
	private String cancelStatus ="CANCELLED";
	private String noExpirationstatus="NO_EXPIRATION";
	public String expTodayDate =null;		
	private String subscriptionId=null;
	private String submitPurchaseOrderURL = null;
	private HashMap<String, String>  cancelledSubscriptionID = new HashMap<>();
	
	
	@BeforeClass(groups= {"SANITY","ALL","testCancelSubscriptions"})
	@Parameters("environmentType")	
	public void setUpTest(@Optional(tempenv) String environment) throws Exception{		
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
		
		submitPurchaseOrderURL = EnvironmentURL(tempenv)+testProperties.getProperty("submitPO_URL");
		
		//create Subscription before running this test  and then cancel it
		

	}
	
	/**
	 * @SCENARIO 1
	 * @Description Verify that the user can cancel the subscription plans and check the expiration status
	 * @throws Exception
	 */

	@Test( groups= {"SANITY","ALL","testCancelSubscriptions"})
	public void testCancelSubscriptionsImmediately() throws Exception {
			/*createAndGetCustomerPortalUserID(getPortalURL());*/
			
			String subscriptionOfferExtKey= getTestDataBasedOnEnvironment("subscriptionOfferExternalKey_Step1");
			

			
			//NOTE: For all rental products, do not run the create subscription call directly do it via SubmitPurchase orderapi
			ArrayList<String> submitNewPurchaseOrdersNewUser = submitNewPurchaseOrdersNewUser(submitPurchaseOrderURL, subscriptionOfferExtKey, bicPelicanConstants.SUBMITPO_CONTENTTYPE, "");
			subscriptionId=submitNewPurchaseOrdersNewUser.get(0);	//the first val is Sub id
			
			cancelSubscriptionInAdminTool(appFamilyIDName,loginName,password,subscriptionId,false);
			//Now fetch the status of the Subscription ID
			assertUtils.assertEquals("The Status of the Subscription with the ID: "+subscriptionId+ " with cancel immediately",getSubscriptionStatus(), expiredStatus);
			cancelledSubscriptionID.put("cancelnow", subscriptionId);
			//get the date and check if the date is the date of cancelled date
			Date curdate= new Date();
			expTodayDate=Util.getCurrentUTCTime(curdate, "MM/dd/yyyy");
			assertUtils.assertTrue(getSubscriptionDate().contains(expTodayDate),"The Expiration date for the Subscription with the ID: "+subscriptionId+ " with cancel immediately should be expected");			
			//Now click on all the Grant entitlements and check if they are expired as well.
			verifyAllEntitlementsExpirationAndDate("cancelNow",true,true);
			assertUtils.assertEquals("The subscription ID for Cancel immediately scenario,  is found as expired when checked via Find Subscription By Id api",checkthatSubscriptionIsCancelledViaFindBySubscriptionId(subscriptionId),expiredStatus);
			
	}
	
	public String checkthatSubscriptionIsCancelledViaFindBySubscriptionId(String subscriptionID) throws Exception{
		String findSubByIDApiUrl=getTestDataBasedOnEnvironment("authURL_FINDBYID");
		String findByIdRespCont=findASubscriptionIdAndReturnResponse(findSubByIDApiUrl, subscriptionID);
		/// parse the resp content and fetch status
		String respFile=createXMlFileWithResponse(findByIdRespCont,"createSubscriptionPlan.xml");		
		String statusOfSubscription=readXMLFileToFetchAttributeInfoOfTheRootNodeSubscription(respFile,"status");
		return statusOfSubscription;		
		
	}
	/**
	 * @SCENARIO 2
	 * @Description Verify that the user can cancel the subscription plans at the end of billing date and verify the expiration status
	 * @throws Exception
	 */

	@Test( groups= {"SANITY","ALL","testCancelSubscriptions"})
	public void testCancelSubscriptionsAPIWithCancelAtEndOfBilling() throws Exception {
			
			String subscriptionOfferExtKey= getTestDataBasedOnEnvironment("subscriptionOfferExternalKey_Step2");	
	
			//NOTE: For all rental products, do not run the create subscription call directly do it via SubmitPurchase orderapi
			ArrayList<String> submitNewPurchaseOrdersNewUser = submitNewPurchaseOrdersNewUser(submitPurchaseOrderURL, subscriptionOfferExtKey, bicPelicanConstants.SUBMITPO_CONTENTTYPE, "");
			subscriptionId=submitNewPurchaseOrdersNewUser.get(0);	//the first val is Sub id
			
			cancelSubscriptionInAdminTool(appFamilyIDName,loginName,password,subscriptionId,true);			
			//Now fetch the status of the Subscription ID
			assertUtils.assertEquals("The Status of the Subscription with the ID: "+subscriptionId+ " with cancel at end of billing should be",getSubscriptionStatus(), cancelStatus);
			cancelledSubscriptionID.put("cancellater", subscriptionId);
			//get the date and check if the date is the date of cancelled date
			Date curdate= new Date();
			if (getEnvironmentType().equalsIgnoreCase("STG")) {
				/// for STG FSN360 Base-A i.e  next year will be cancelled date
				expTodayDate=Util.getNextYearDateInUTCFormat(curdate, "MM/dd/yyyy");
			}
			else {
				//For dev FSN 360 BASE -M is being used ie.   next month will be cancelled date
				expTodayDate=Util.getNextMonthDateInUTCFormat(curdate, "MM/dd/yyyy");
			}
			assertUtils.assertTrue(getSubscriptionDate().contains(expTodayDate),"The Expiration date for the Subscription with the ID: "+subscriptionId+ " with cancel at the end of billing");		
			
			//Now click on all the Grant entitlements and check if they are expired as well.
			verifyAllEntitlementsExpirationAndDate("cancelLater",false,false);
			assertUtils.assertEquals("The subscription ID for Cancel at the end of billing period scenario is found as expired when checked via Find Subscription By Id api",checkthatSubscriptionIsCancelledViaFindBySubscriptionId(subscriptionId),cancelStatus);
			
	}
	
	
	public String readXMLFileToFetchAttributeInfoOfTheRootNodeSubscription(String xmlFileName,String attributeName) throws Exception {
		File file = new File(xmlFileName);
		String subID = null;
		XMLConfiguration xmlConfigFile = new XMLConfiguration(file);	
		Node rootNode=xmlConfigFile.getRoot();
		List<ConfigurationNode> rootAttrs=rootNode.getAttributes();
		for (ConfigurationNode eachAttr: rootAttrs) {
			if (eachAttr.getName().equalsIgnoreCase(attributeName)) {
				// get the value of node ID
				subID=eachAttr.getValue().toString();
			}
		}
		
		return subID;
		
		
	}
	
	public void verifyAllEntitlementsExpirationAndDate(String cancelNowOrLater,boolean isSubscriptionCancelledNow , boolean dateToVerify) throws Exception {
		List<WebElement> entitlementsList=pelicanAdmPage.getMultipleWebElementsfromField("allGrantEntitlementsLinks");
		ArrayList<String> entitlementVals= new ArrayList<>();
		for(WebElement entitlement: entitlementsList) {
			entitlementVals.add(entitlement.getText().trim());
		}
		// go through all the entitlements and check if expired status and date is as expected
		for(int i=0;i<entitlementsList.size();i++) {
			entitlementsList.get(i).click();
			//avoid staleelement errors
			Util.sleep(3000);
			//get the expiration status
			//special cond for the one of the entitlements
			/*if (entitlementVals.get(i).contains("ReCap 360")) {
				assertUtils.assertEquals("The Status of the grant Entitlements : "+entitlementVals.get(i)+" when "+cancelNowOrLater,getSubscriptionStatus(), noExpirationstatus);
			}*/
			// for Autodesk Recap 360 there is no expiration date and status. however need to confirm  
			
				
				if (isSubscriptionCancelledNow) {					
					assertUtils.assertEquals("The Status of the grant Entitlement : "+entitlementVals.get(i)+" when "+cancelNowOrLater,getSubscriptionStatus(), expiredStatus);
				}else {
					// for cancel later the subscription expiration status is empty
					//for STG env the status of collaboration,rendering,basic support is '' but in DEV it is NO_EXPIRATIOn
					if (getEnvironmentType().equalsIgnoreCase("STG")) {
						if (entitlementVals.get(i).equalsIgnoreCase("basic support")||
						    entitlementVals.get(i).equalsIgnoreCase("rendering")||
						    entitlementVals.get(i).equalsIgnoreCase("collaboration")){
							assertUtils.assertEquals("The Status of the grant Entitlement : "+entitlementVals.get(i)+" when "+cancelNowOrLater,getSubscriptionStatus(), "");
							continue;
						}
					}
					
					if (entitlementVals.get(i).equalsIgnoreCase("fusion 360")|| 
							entitlementVals.get(i).equalsIgnoreCase("storage")||
							entitlementVals.get(i).contains("Remote")||
							entitlementVals.get(i).contains("ReCap 360")) {	
						//for this product the expiation status entitlement is empty
						assertUtils.assertEquals("The Status of the grant Entitlement : "+entitlementVals.get(i)+" when "+cancelNowOrLater,getSubscriptionStatus(), "");
					}else {		
							assertUtils.assertEquals("The Status of the grant Entitlement : "+entitlementVals.get(i)+" when "+cancelNowOrLater,getSubscriptionStatus(), noExpirationstatus);
					}

				}
				if (dateToVerify) {					
					assertUtils.assertTrue(getSubscriptionDate().contains(expTodayDate),"The Expiration date the grant Entitlement : "+entitlementVals.get(i)+" when " +cancelNowOrLater+" should be expected ");
				}
			
			//go back to the main page
			driver.navigate().back();
			Util.sleep(2000);
			 entitlementsList=pelicanAdmPage.getMultipleWebElementsfromField("allGrantEntitlementsLinks");	//reidentify
		}
	}
	@AfterClass(groups= {"SANITY","ALL","testCancelSubscriptions"})
	public void tearDown() throws Exception {
		driver.close();
		/*killProcess(browserType);*/
		if (testClient!=null) {			
			testClient.close();
		}
		System.out.println("Have cleared the FindSubscriptionPlans API props");
		testProperties.clear();
		
	}
}

