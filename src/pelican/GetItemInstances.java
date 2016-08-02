package pelican;


import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.Util;

public class GetItemInstances extends bicPelicanWebService{

	public static final String DEFAULT_ENVIRONMENT = "DEV";
	private static String propertiesFileName=Thread.currentThread().getStackTrace()[1].getFileName().split("\\.")[0];
	public static String env ="STG";
	public static String urlBase;
	public static String PartnerId;
	public static String FamilyId;
	public static String hmacSignature;
	public static String Secretkey;
	public static String UserExternalKey;
	private static Properties propTestData = new 	Properties();
	Logger log = Logger.getLogger(GetItemInstances.class);	
		
	
	// Defining xpaths for sucess expectedresponse
	private  String XPATH_ITEMID = "//itemInstances/itemInstance/@itemId";
	private  String XPATH_SUBSID = "//itemInstances/itemInstance/@subscriptionId";
	private  String XPATH_OWNEREXTERNALKEY = "//itemInstances/itemInstance/@ownerExternalKey";
	private  String XPATH_PARENTITEMEXTERNALKEY = "//itemInstances/itemInstance/@parentItemExternalKey";
	private  String XPATH_LICENCEMODEL = "//itemInstances/itemInstance/@licensingModelExternalKey";
	private  String XPATH_ISACTIVE = "//itemInstances/itemInstance/@isActive";
	private  String XPATH_CREATEDATE = "//itemInstances/itemInstance/@createdDate";
	private  String XPATH_ITEMTYPE_EXTERNALKEY = "//itemInstances/itemInstance/item/itemType/@externalKey";
	
	
	
	// STATUS CODES
	public final int HTTP_STATUSCODE_SUCCESS = 200;
	public final int HTTP_BAD_STATUSCODE_ERROR = 400;
	public final int HTTP_ERROR_STATUSCODE = 404;
	
	
	
	@BeforeClass(groups={"SANITY", "ALL", "FUNC_ITEMINSTANCES"})
	@Parameters("environmentType")
	public void setup(String environment) {
	/*@Parameters(value = "environment")
	public void setup(@Optional(DEFAULT_ENVIRONMENT) String environment) {*/
		try {
			testProperties=readPropertiesFile(propertiesFileName);
			propTestData=testProperties;
			env = environment;
			setEnvironmentType(environment);
			assignSecretKey();
			urlBase= EnvironmentURL(environment)+propTestData.getProperty("URL");
		
		} catch(Exception e) {
			System.out.println("Exception in setup method");
			e.printStackTrace();
		}
	}
	
	
	@Test(groups={"SANITY","ALL","FUNC_ITEMINSTANCES"})
	public void getItemInstancesbyExternalKey() throws Exception {
		
		String url = urlBase+"?userExternalKey="+propTestData.getProperty("TC1_USER_EXTERNAL_KEY_"+env);
		
		// ARRAY CREATED WITH X[PATH AND EXPECTED VALUE
		String[][] expectedOutput={{XPATH_ITEMID, propTestData.getProperty("TC1_VALUE_ITEMID_"+env)},
				{XPATH_SUBSID, propTestData.getProperty("TC1_VALUE_SUBSID_"+env)},
				{XPATH_OWNEREXTERNALKEY, propTestData.getProperty("TC1_VALUE_OWNEREXTERNALKEY_"+env)},
				{XPATH_PARENTITEMEXTERNALKEY, propTestData.getProperty("TC1_VALUE_PARENTITEMEXTERNALKEY_"+env)},
				{XPATH_LICENCEMODEL, propTestData.getProperty("TC1_VALUE_LICENCEMODEL_"+env)},
				{XPATH_ISACTIVE, propTestData.getProperty("TC1_VALUE_ISACTIVE_"+env)},
				{XPATH_CREATEDATE, propTestData.getProperty("TC1_VALUE_CREATEDATE_"+env)},
				{XPATH_ITEMTYPE_EXTERNALKEY, propTestData.getProperty("TC1_VALUE_EXTERNALKEY_"+env)},
				};
		
		// EXECUYTING RESPONSE
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
		}
		// VERIFYING TRESPONSE WITH EXPECTED OUTPUT
		validateXML(response,expectedOutput);
	}
		
		
	@Test(groups={"SANITY","ALL","FUNC_ITEMINSTANCES"})
	public void getItemInstancesByExtnKeyNSubsId() throws Exception {
		
		String url = urlBase+"?userExternalKey="+propTestData.getProperty("TC2_USER_EXTERNAL_KEY_"+env)+"&subscriptionId="+propTestData.getProperty("TC2_SUBS_ID_"+env);
		
		String[][] expectedOutput={{XPATH_ITEMID, propTestData.getProperty("TC2_VALUE_ITEMID_"+env)},
				{XPATH_SUBSID, propTestData.getProperty("TC2_VALUE_SUBSID_"+env)},
				{XPATH_OWNEREXTERNALKEY, propTestData.getProperty("TC2_VALUE_OWNEREXTERNALKEY_"+env)},
				{XPATH_PARENTITEMEXTERNALKEY, propTestData.getProperty("TC2_VALUE_PARENTITEMEXTERNALKEY_"+env)},
				{XPATH_LICENCEMODEL, propTestData.getProperty("TC2_VALUE_LICENCEMODEL_"+env)},
				{XPATH_ISACTIVE, propTestData.getProperty("TC2_VALUE_ISACTIVE_"+env)},
				{XPATH_CREATEDATE, propTestData.getProperty("TC2_VALUE_CREATEDATE_"+env)},
				{XPATH_ITEMTYPE_EXTERNALKEY, propTestData.getProperty("TC2_VALUE_ITEM_TYPE_EXTERNALKEY_"+env)},
		};
		
		
		
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
		}
		
		validateXML(response,expectedOutput);
	
	}
	
	@Test(groups={"SANITY","ALL","FUNC_ITEMINSTANCES"})
	public void getItemInstancesByExtnKeyNParentNExrnKey() throws Exception {
		
		String url = urlBase+"?userExternalKey="+propTestData.getProperty("TC3_USER_EXTERNAL_KEY_"+env)+"&parentItemExternalKeys="+propTestData.getProperty("TC3_PARENTID_EXTERNAL_KEY_"+env);
		String[][] expectedOutput={{XPATH_ITEMID, propTestData.getProperty("TC3_VALUE_ITEMID_"+env)},
				{XPATH_SUBSID, propTestData.getProperty("TC3_VALUE_SUBSID_"+env)},
				{XPATH_OWNEREXTERNALKEY, propTestData.getProperty("TC3_VALUE_OWNEREXTERNALKEY_"+env)},
				{XPATH_PARENTITEMEXTERNALKEY, propTestData.getProperty("TC3_VALUE_PARENTITEMEXTERNALKEY_"+env)},
				{XPATH_LICENCEMODEL, propTestData.getProperty("TC3_VALUE_LICENCEMODEL_"+env)},
				{XPATH_ISACTIVE, propTestData.getProperty("TC3_VALUE_ISACTIVE_"+env)},
				{XPATH_CREATEDATE, propTestData.getProperty("TC3_VALUE_CREATEDATE_"+env)},
				{XPATH_ITEMTYPE_EXTERNALKEY, propTestData.getProperty("TC3_VALUE_ITEM_TYPE_EXTERNALKEY_"+env)}};
		
		
		
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
		}
		
		validateXML(response,expectedOutput);
	
	}
	
	
	@Test(groups={"SANITY","ALL","FUNC_ITEMINSTANCES"})
	public void getItemInstancesByExtnKeyNincludeExpired() throws Exception {
		
		String url = urlBase+"?userExternalKey="+propTestData.getProperty("TC4_USER_EXTERNAL_KEY_"+env)+"&includeExpired=true";
		String[][] expectedOutput={{XPATH_ITEMID, propTestData.getProperty("TC4_VALUE_ITEMID_"+env)},
				
				{XPATH_SUBSID, propTestData.getProperty("TC4_VALUE_SUBSID_"+env)},
				{XPATH_OWNEREXTERNALKEY, propTestData.getProperty("TC4_VALUE_OWNEREXTERNALKEY_"+env)},
				{XPATH_PARENTITEMEXTERNALKEY, propTestData.getProperty("TC4_VALUE_PARENTITEMEXTERNALKEY_"+env)},
				{XPATH_LICENCEMODEL, propTestData.getProperty("TC4_VALUE_LICENCEMODEL_"+env)},
				{XPATH_ISACTIVE, propTestData.getProperty("TC4_VALUE_ISACTIVE_"+env)},
				{XPATH_CREATEDATE, propTestData.getProperty("TC4_VALUE_CREATEDATE_"+env)},
				{XPATH_ITEMTYPE_EXTERNALKEY, propTestData.getProperty("TC4_VALUE_ITEM_TYPE_EXTERNALKEY_"+env)}
		};
		
		
		
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
		}
		
		validateXML(response,expectedOutput);
	
	}
	
	@Test(groups={"SANITY","ALL","FUNC_ITEMINSTANCES"})
	public void getItemInstancesByExtnKeyNitemExternalKey() throws Exception {
		
		String url = urlBase+"?userExternalKey="+propTestData.getProperty("TC5_USER_EXTERNAL_KEY_"+env)+"&itemExternalKeys="+propTestData.getProperty("TC5_ITEM_EXTERNAL_KEY_"+env);
		String[][] expectedOutput={{XPATH_ITEMID, propTestData.getProperty("TC5_VALUE_ITEMID_"+env)},
				
				{XPATH_SUBSID, propTestData.getProperty("TC5_VALUE_SUBSID_"+env)},
				{XPATH_OWNEREXTERNALKEY, propTestData.getProperty("TC5_VALUE_OWNEREXTERNALKEY_"+env)},
				{XPATH_PARENTITEMEXTERNALKEY, propTestData.getProperty("TC5_VALUE_PARENTITEMEXTERNALKEY_"+env)},
				{XPATH_LICENCEMODEL, propTestData.getProperty("TC5_VALUE_LICENCEMODEL_"+env)},
				{XPATH_ISACTIVE, propTestData.getProperty("TC5_VALUE_ISACTIVE_"+env)},
				{XPATH_CREATEDATE, propTestData.getProperty("TC5_VALUE_CREATEDATE_"+env)},
				{XPATH_ITEMTYPE_EXTERNALKEY, propTestData.getProperty("TC5_VALUE_ITEM_TYPE_EXTERNALKEY_"+env)},
		};
		
		
		
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
		}
		
		validateXML(response,expectedOutput);
	
	}
	
	@Test(groups={"SANITY","ALL","FUNC_ITEMINSTANCES"})
	public void getItemInstancesLicensingModelExpirationStatus() throws Exception {
		
		String url = urlBase+"?userExternalKey="+propTestData.getProperty("TC6_USER_EXTERNAL_KEY_"+env)+"&includeExpired=true&fr.blockSize=200&fr.skipCount=false";
				
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
		}
				validateExpirationStatus(response);
		
	
	}
	
	
	@Test(groups={"SANITY","ALL","FUNC_ITEMINSTANCES"})
	public void countItemInstances() throws Exception {
		
		String url = urlBase+"?userExternalKey="+propTestData.getProperty("TC7_USER_EXTERNAL_KEY_"+env)+"&fr.blockSize=100&fr.skipCount=false";
				
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
		}
		countNodes(response, "itemInstance");
		
	
	}
	
	private void validateExpirationStatus(HttpResponse response)
			throws Exception {
		Document responseDoc = null;

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			HttpEntity entity = response.getEntity();

			InputStream inputStream = entity.getContent();
			responseDoc = db.parse(inputStream);
			responseDoc.getDocumentElement().normalize();
			NodeList list = responseDoc.getElementsByTagName("itemInstance");
			System.out.println("Total Item Instances: " + list.getLength());
			for (int x = 0; x < list.getLength(); x++) {
				Node node = list.item(x);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element fin = (Element) node;

					// System.out.println(fin.getAttribute("licensingModelExternalKey")+" "+fin.getAttribute("expirationStatus"));
					if (fin.getAttribute("isActive").equalsIgnoreCase("false"))
						assertUtils.assertEquals(fin.getAttribute("expirationStatus"),"EXPIRED");
					else {
						if (fin.getAttribute("licensingModelExternalKey").equalsIgnoreCase("TRL#1"))
							assertUtils.assertEquals(fin.getAttribute("expirationStatus"),"EXPIRATION_DATE_SET");
						else if (fin.getAttribute("licensingModelExternalKey").equalsIgnoreCase("RTL#1"))
							assertUtils.assertEquals(fin.getAttribute("isActive"), "true");
						else if (fin.getAttribute("licensingModelExternalKey").equalsIgnoreCase("STD#1"))
							assertUtils.assertEquals(fin.getAttribute("expirationStatus"),"EXPIRATION_DATE_SET");
						else if (fin.getAttribute("licensingModelExternalKey").equalsIgnoreCase("SUP#1"))
							assertUtils.assertEquals(fin.getAttribute("expirationStatus"),"EXPIRATION_DATE_SET");
						else if (fin.getAttribute("licensingModelExternalKey").equalsIgnoreCase("OPN#1"))
							assertUtils.assertEquals(fin.getAttribute("expirationStatus"),"NO_EXPIRATION");
						else if (fin.getAttribute("licensingModelExternalKey").equalsIgnoreCase("CMP#1"))
							assertUtils.assertEquals(fin.getAttribute("expirationStatus"),"NO_EXPIRATION");
						else if (fin.getAttribute("licensingModelExternalKey").equalsIgnoreCase("CMB#1"))
							assertUtils.assertEquals(fin.getAttribute("expirationStatus"),"NO_EXPIRATION");
						else if (fin.getAttribute("licensingModelExternalKey").equalsIgnoreCase("ETH#1"))
							assertUtils.assertEquals(fin.getAttribute("expirationStatus"),"NO_EXPIRATION");
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

