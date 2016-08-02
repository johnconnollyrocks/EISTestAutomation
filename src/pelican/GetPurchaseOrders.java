package pelican;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;


public class GetPurchaseOrders extends bicPelicanWebService{

	public static final String DEFAULT_ENVIRONMENT = "DEV";
	private static String propertiesFileName=Thread.currentThread().getStackTrace()[1].getFileName().split("\\.")[0];
	public static String env ;
	public static String urlBase;
	public static String PartnerId;
	public static String FamilyId;
	public static String hmacSignature;
	public static String Secretkey;
	public static String UserExternalKey;
	private static Properties propTestData = new 	Properties();
	Logger log = Logger.getLogger(GetPurchaseOrders.class);	
		
	
	// Defining xpaths for sucess expectedresponse
	private   String XPATH_CREATETIME;
	private   String XPATH_LASTMODIFIED;
	private   String XPATH_OrderID;
	private   String XPATH_FULFILMENTSTATUS;
	private   String XPATH_USERID;
	private   String XPATH_ORDERSTATE;
	private   String XPATH_SUBS_CURRENCYID;
	private   String XPATH_SUBS_CURRENCYNAME;
	private   String XPATH_OFFEREXTERNALKEY;
	private   String XPATH_SUBSID;
	private   String XPATH_UNITPRICE;
	private   String XPATH_PLANNAME;
	private   String XPATH_SUBSOFFER;
	private   String XPATH_SUBSRESPONSE_currencyName;
	private   String XPATH_NEXTBILLINGDATE;
	private   String XPATH_EVENTGROUPID;
	private   String XPATH_TIMESTAMP;
	private   String XPATH_CURRENCY;
	private   String XPATH_CONFIGID;
	private   String XPATH_STATE;
	private   String XPATH_TRANSID;
	private   String XPATH_TRANSTYPE;
	private   String XPATH_TRANSDATE;
	private   String XPATH_TRANSSTATE;
	private   String XPATH_AMMOUNTCHARGED;
	private   String XPATH_AMMOUNTCHARGED_CURRENCYID;
	private   String XPATH_AMMOUNTCHARGED_CURRENCYCODE;
	private   String XPATH_GATEWAYDETAILNAME;
	private   String XPATH_TRANSACTION_CONFIGID;
	private   String XPATH_AMOOUNTINUSD;
	private   String XPATH_EXCHANGERATE;
	private   String XPATH_ALLOWEDCOMMANDS1;
	private   String XPATH_ALLOWEDCOMMANDS2;
	private   String XPATH_ALLOWEDCOMMANDS3;
		
	// STATUS CODES
	public final int HTTP_STATUSCODE_SUCCESS = 200;
	
	@BeforeClass(groups={"SANITY", "ALL", "FUNC_GET_ORDERS"})
	@Parameters("environmentType")
	/*@Parameters(value = "environment")
	public void setup(@Optional(DEFAULT_ENVIRONMENT) String environment) {*/
	public void setup( String environment) {
		try {
			propTestData=readPropertiesFile(propertiesFileName);
			env = environment;
			setEnvironmentType(environment);
			assignSecretKey();
			urlBase= EnvironmentURL(environment)+propTestData.getProperty("URL");
			
			
		} catch(Exception e) {
			System.out.println("Exception in setup method");
			e.printStackTrace();
		}
	}
	
	
	@Test(groups={"SANITY","ALL","FUNC_GET_ORDERS"})
	public void getPurchaseOrdersbyExternalKey() throws Exception {
		
		String url = urlBase+"?&buyerUserExternalKey="+propTestData.getProperty("TC1_USER_EXTERNAL_KEY_"+env)+"&fr.blockSize=100&purchaseType=ANY&fr.skipCount=false";
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
		}
		
		// EXECUYTING RESPONSE
				HttpResponse response1 = (HttpResponse) executeGET(url);
				
				String[][] validationStack=expectedOutput(response);
				// VERIFYING TRESPONSE WITH EXPECTED OUTPUT
				validateXML(response1,validationStack);
	}
	
	
		
	@Test(groups={"SANITY","ALL","FUNC_GET_ORDERS"})
	public void countPurchaseOrderForUser() throws Exception {
		
		String url = urlBase+"?&buyerUserExternalKey="+propTestData.getProperty("TC2_USER_EXTERNAL_KEY_"+env)+"&fr.blockSize=100&purchaseType=ANY&fr.skipCount=false";
		
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
		}
		// count that total number of orders match with index number
		countNodes(response, "purchaseOrder");
	
	}
	
	public String[][] expectedOutput(HttpResponse response) throws  SAXException, IOException, ParserConfigurationException{
		int xpath_x=0;
		Document responseDoc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
        DocumentBuilder db = dbf.newDocumentBuilder(); 
         
         if (response != null) {  
            InputStream inputStream = response.getEntity().getContent();
            responseDoc = db.parse(inputStream);  
            responseDoc.getDocumentElement().normalize();
						
			NodeList list = responseDoc.getElementsByTagName("purchaseOrder");
            
            for(int x=0;x<list.getLength();x++){
            	Node node = list.item(x);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
    				Element fin = (Element) node;
    				String orderId = fin.getAttribute("id");
    				if(orderId.equalsIgnoreCase(propTestData.getProperty("VALUE_OrderID_"+env))){
    					xpath_x=x+1;
    					XPATH_CREATETIME = "//purchaseOrders/purchaseOrder["+xpath_x+"]/@creationTime";
    	    			XPATH_LASTMODIFIED = "//purchaseOrders/purchaseOrder["+xpath_x+"]/@lastModified";
    	    			XPATH_OrderID = "//purchaseOrders/purchaseOrder["+xpath_x+"]/@id";
    	    			XPATH_FULFILMENTSTATUS = "//purchaseOrders/purchaseOrder["+xpath_x+"]/@fulfillmentStatus";
    	    			XPATH_USERID = "//purchaseOrders/purchaseOrder["+xpath_x+"]/buyerUser/@id";
    	    			XPATH_ORDERSTATE = "//purchaseOrders/purchaseOrder["+xpath_x+"]/orderState";
    	    			XPATH_SUBS_CURRENCYID = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseRequest/@currencyId";
    	    			XPATH_SUBS_CURRENCYNAME = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseRequest/@currencyName";
    	    			XPATH_OFFEREXTERNALKEY = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseRequest/@offerExternalKey";
    	    			XPATH_SUBSID = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseResponse/@subscriptionId";
    	    			XPATH_UNITPRICE = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseResponse/@unitPrice";
    	    			XPATH_PLANNAME = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseResponse/@planName";
    	    			XPATH_SUBSOFFER = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseResponse/@subscriptionOfferId";
    	    			XPATH_SUBSRESPONSE_currencyName = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseResponse/@currencyName";
    	    			XPATH_NEXTBILLINGDATE = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseResponse/@nextBillingDate";
    	    			XPATH_EVENTGROUPID = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseResponse/@eventGroupId";
    	    			XPATH_TIMESTAMP = "//purchaseOrders/purchaseOrder["+xpath_x+"]/lineItems/lineItem/subscriptionPlan/subscriptionPlanPurchaseResponse/@timestamp";
    	    			XPATH_CURRENCY = "//purchaseOrders/purchaseOrder["+xpath_x+"]/payment/@currencyId";
    	    			XPATH_CONFIGID = "//purchaseOrders/purchaseOrder["+xpath_x+"]/payment/@configId";
    	    			XPATH_STATE = "//purchaseOrders/purchaseOrder["+xpath_x+"]/payment/recorder/state";
    	    			XPATH_TRANSID = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/@id";
    	    			XPATH_TRANSTYPE = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/@type";
    	    			XPATH_TRANSDATE = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/gatewayResponse/@txnDate";
    	    			XPATH_TRANSSTATE = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/gatewayResponse/@state";
    	    			XPATH_AMMOUNTCHARGED = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/gatewayResponse/@amountCharged";
    	    			XPATH_AMMOUNTCHARGED_CURRENCYID = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/gatewayResponse/@amountChargedCurrencyId";
    	    			XPATH_AMMOUNTCHARGED_CURRENCYCODE = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/gatewayResponse/@amountChargedCurrencyISOCode";
    	    			XPATH_GATEWAYDETAILNAME = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/gatewayResponse/@detailName";
    	    			XPATH_TRANSACTION_CONFIGID= "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/gatewayResponse/@configId";
    	    			XPATH_AMOOUNTINUSD = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/gatewayResponse/@amountChargedInUsd";
    	    			XPATH_EXCHANGERATE = "//purchaseOrders/purchaseOrder["+xpath_x+"]/transactions/transaction/gatewayResponse/@exchangeRateToUsd";
    	    			XPATH_ALLOWEDCOMMANDS1 = "//purchaseOrders/purchaseOrder["+xpath_x+"]/allowedCommands/command[1]";
    	    			XPATH_ALLOWEDCOMMANDS2 = "//purchaseOrders/purchaseOrder["+xpath_x+"]/allowedCommands/command[2]";
    	    			XPATH_ALLOWEDCOMMANDS3 = "//purchaseOrders/purchaseOrder["+xpath_x+"]/allowedCommands/command[3]";
    				}

    			}
    			
	}
         }
		
		// ARRAY CREATED WITH XPATH AND EXPECTED VALUE
		String[][] outputStack={{XPATH_CREATETIME, propTestData.getProperty("VALUE_CREATETIME_"+env)},
				{XPATH_LASTMODIFIED, propTestData.getProperty("VALUE_LASTMODIFIED_"+env)},
				{XPATH_OrderID, propTestData.getProperty("VALUE_OrderID_"+env)},
				{XPATH_FULFILMENTSTATUS, propTestData.getProperty("VALUE_FULFILMENTSTATUS_"+env)},
				{XPATH_USERID, propTestData.getProperty("VALUE_USERID_"+env)},
				{XPATH_ORDERSTATE, propTestData.getProperty("VALUE_ORDERSTATE_"+env)},
				{XPATH_OFFEREXTERNALKEY, propTestData.getProperty("VALUE_OFFEREXTERNALKEY_"+env)},
				{XPATH_SUBS_CURRENCYID, propTestData.getProperty("VALUE_SUBS_CURRENCYID_"+env)},
				{XPATH_SUBS_CURRENCYNAME, propTestData.getProperty("VALUE_SUBS_CURRENCYNAME_"+env)},
				{XPATH_SUBSID, propTestData.getProperty("VALUE_SUBSID_"+env)},
				{XPATH_UNITPRICE, propTestData.getProperty("VALUE_UNITPRICE_"+env)},
				{XPATH_PLANNAME, propTestData.getProperty("VALUE_PLANNAME_"+env)},
				{XPATH_SUBSOFFER, propTestData.getProperty("VALUE_SUVBOFFERID_"+env)},
				{XPATH_SUBSRESPONSE_currencyName, propTestData.getProperty("VALUE_SUBSRESPONSE_currencyName_"+env)},
				{XPATH_NEXTBILLINGDATE, propTestData.getProperty("VALUE_NEXTBILLINGDATE_"+env)},
				{XPATH_EVENTGROUPID, propTestData.getProperty("VALUE_EVENTGROUPID_"+env)},
				{XPATH_TIMESTAMP, propTestData.getProperty("VALUE_TIMESTAMP_"+env)},
				{XPATH_CONFIGID, propTestData.getProperty("VALUE_CONFIGID_"+env)},
				{XPATH_CURRENCY, propTestData.getProperty("VALUE_CURRENCY_"+env)},
				{XPATH_STATE, propTestData.getProperty("VALUE_STATE_"+env)},
				{XPATH_TRANSID, propTestData.getProperty("VALUE_TRANSID_"+env)},
				{XPATH_TRANSTYPE, propTestData.getProperty("VALUE_TRANSTYPE_"+env)},
				{XPATH_TRANSDATE, propTestData.getProperty("VALUE_TRANSDATE_"+env)},
				{XPATH_TRANSSTATE, propTestData.getProperty("VALUE_TRANSSTATE_"+env)},
				{XPATH_AMMOUNTCHARGED, propTestData.getProperty("VALUE_AMMOUNTCHARGED_"+env)},
				{XPATH_AMMOUNTCHARGED_CURRENCYID, propTestData.getProperty("VALUE_AMMOUNTCHARGED_CURRENCYID_"+env)},
				{XPATH_AMMOUNTCHARGED_CURRENCYCODE, propTestData.getProperty("VALUE_AMMOUNTCHARGED_CURRENCYCODE_"+env)},
				{XPATH_GATEWAYDETAILNAME, propTestData.getProperty("VALUE_GATEWAYDETAILNAME_"+env)},
				{XPATH_TRANSACTION_CONFIGID, propTestData.getProperty("VALUE_TRANSACTION_CONFIGID_"+env)},
				{XPATH_AMOOUNTINUSD, propTestData.getProperty("VALUE_AMOOUNTINUSD_"+env)},
				{XPATH_EXCHANGERATE, propTestData.getProperty("VALUE_EXCHANGERATE_"+env)},
				{XPATH_ALLOWEDCOMMANDS1, propTestData.getProperty("VALUE_ALLOWEDCOMMANDS1_"+env)},
				{XPATH_ALLOWEDCOMMANDS2, propTestData.getProperty("VALUE_ALLOWEDCOMMANDS2_"+env)},
				{XPATH_ALLOWEDCOMMANDS3, propTestData.getProperty("VALUE_ALLOWEDCOMMANDS3_"+env)}
				};
         
		return outputStack;
		
	}
	
	
	
}

