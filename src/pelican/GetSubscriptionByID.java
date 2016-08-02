package pelican;


import java.util.Properties;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class GetSubscriptionByID extends bicPelicanWebService{

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
	Logger log = Logger.getLogger(GetSubscriptionByID.class);	
		
	
	// Defining xpaths for sucess expectedresponse
	private final String XPATH_SUBSID = "//subscription/@id";
	private final String XPATH_OWNERID = "//subscription/@ownerId";
	private final String XPATH_STATUS = "//subscription/@status";
	private final String XPATH_IS_AUTORENEWAL_ENABLED = "//subscription/@isAutoRenewEnabled";
	private final String XPATH_PAYMENT_PROFILE_ID = "//subscription/@storedPaymentProfileId";
	private final String XPATH_OFFER_ID = "//subscription/@nextBillingOfferId";
	private final String XPATH_CREDIT_DAYS = "//subscription/@creditDays";
	private final String XPATH_NEXTBILL_AMT = "//subscription/@nextBillingPriceAmount";
	private final String XPATH_NEXTBILL_CURRENCYID = "//subscription/@nextBillingPriceCurrencyId";
	private final String XPATH_NEXTBILL_CURRENCY_NAME = "//subscription/@nextBillingPriceCurrencyName";
	private final String XPATH_NEXTBILL_DATE = "//subscription/@nextBillingDate";
	private final String XPATH_EXP_DATE = "//subscription/@expirationDate";
	
	private final String XPATH_CURRENT_OFFERID = "//subscription/currentOffer/@id";
	private final String XPATH_CURRENT_NAME = "//subscription/currentOffer/@name";
	private final String XPATH_CURRENT_EXTERNALKEY = "//subscription/currentOffer/@externalKey";
	
	private final String XPATH_BILLINGOPTION_ID = "//subscription/billingOption/@id";
	private final String XPATH_BILLINGOPTION_NAME = "//subscription/billingOption/@name";
	private final String XPATH_BILLINGOPTION_EXTERNALKEY ="//subscription/billingOption/@externalKey";
	private final String XPATH_BILLINGOPTION_PLANEXTERNALKEY = "//subscription/billingOption/@planExternalKey";
	private final String XPATH_BILLINGOPTION_BILLINGPERIOD_COUNT = "//subscription/billingOption/billingPeriod/@count";
	private final String XPATH_BILLINGOPTION_BILLINGPERIODTYPE = "//subscription/billingOption/billingPeriod/@type";
	private final String XPATH_BILLINGOPTION_BILLINGDATE = "//subscription/billingOption/billingDate/@isCustom";

	private final String XPATH_PRICE_USD = "//subscription/billingOption/prices/price[1]/@name";
	private final String XPATH_PRICE_USD_AMT = "//subscription/billingOption/prices/price[1]/@amount";
	private final String XPATH_PRICE_CAD = "//subscription/billingOption/prices/price[2]/@name";
	private final String XPATH_PRICE_CAD_AMT = "//subscription/billingOption/prices/price[2]/@amount";
	
	private final String XPATH_SUBSPLAN_ID = "//subscription/subscriptionPlan/@id";
	private final String XPATH_SUBSPLAN_NAME = "//subscription/subscriptionPlan/@name";
	private final String XPATH_SUBSPLAN_USAGE_TYPE = "//subscription/subscriptionPlan/@usageType";
	private final String XPATH_SUBSPLANTIER = "//subscription/subscriptionPlan/@tier";
	private final String XPATH_SUBSPLAN_MODULE = "//subscription/subscriptionPlan/@isModule";
	private final String XPATH_SUBSPLAN_EXTERNALKEY = "//subscription/subscriptionPlan/@externalKey";
	private final String XPATH_SUBSPLAN_URL = "//subscription/subscriptionPlan/@smallImageURL";
	private final String XPATH_SUBSPLAN_TAXCODE = "//subscription/subscriptionPlan/@taxCode";
	
	private final String XPATH_SUBS_STATUS = "//subscription/subscriptionPlan/status";
	private final String XPATH_CANCEL_POLICY = "//subscription/subscriptionPlan/cancellationPolicy";
	
	private final String XPATH_ENTL_COUNT = "//subscription/subscriptionPlan/entitlementPeriod/@count";
	private final String XPATH_ENTL_TYPE = "//subscription/subscriptionPlan/entitlementPeriod/@type";
	
	private final String XPATH_OFFRERING_DETAILCOUNT = "//subscription/subscriptionPlan/offeringDetail/@code";
	private final String XPATH_OFFRERING_DETAIL_DESC = "//subscription/subscriptionPlan/offeringDetail/@description";
	
	private final String XPATH_PRODUCTCODE = "//subscription/subscriptionPlan/productLine/@code";
	private final String XPATH_PRODUCTNAME = "//subscription/subscriptionPlan/productLine/@name";


	
	
	// STATUS CODES
	public final int HTTP_STATUSCODE_SUCCESS = 200;
	public final int HTTP_BAD_STATUSCODE_ERROR = 400;
	public final int HTTP_ERROR_STATUSCODE = 404;
	
	
	
	@BeforeClass(groups={"SANITY", "ALL", "FUNC_SUBS_BYID"})
	@Parameters("environmentType")
	public void setup( String environment) {
		/*@Parameters(value = "environment")
	public void setup(@Optional(DEFAULT_ENVIRONMENT) String environment) {*/
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
	
	@DataProvider(name = "getSubcriptionByID")
	public Object[][] getSubcriptionByID_DP(){
		return new Object[][]{
			{	propTestData.getProperty("1_SUBCRRPTION_ID_"+env),
				 new String[][]{
					{XPATH_SUBSID, propTestData.getProperty("1_SUBCRRPTION_ID_"+env)},
					{XPATH_OWNERID, propTestData.getProperty("1_VALUE_OWNERID_"+env)},
					{XPATH_STATUS, propTestData.getProperty("1_VALUE_STATUS_"+env)},
					{XPATH_IS_AUTORENEWAL_ENABLED, propTestData.getProperty("1_VALUE_IS_AUTORENEWAL_ENABLED_"+env)},
					{XPATH_PAYMENT_PROFILE_ID, propTestData.getProperty("1_VALUE_PAYMENT_PROFILE_ID_"+env)},
					{XPATH_OFFER_ID, propTestData.getProperty("1_VALUE_OFFER_ID_"+env)},
					{XPATH_CREDIT_DAYS, propTestData.getProperty("1_VALUE_CREDIT_DAYS_"+env)},
					{XPATH_NEXTBILL_AMT, propTestData.getProperty("1_VALUE_NEXTBILL_AMT_"+env)},
					{XPATH_NEXTBILL_CURRENCYID, propTestData.getProperty("1_VALUE_NEXTBILL_CURRENCYID_"+env)},
					{XPATH_NEXTBILL_CURRENCY_NAME, propTestData.getProperty("1_VALUE_NEXTBILL_CURRENCY_NAME_"+env)},
					{XPATH_NEXTBILL_DATE, propTestData.getProperty("1_VALUE_NEXTBILL_DATE_"+env)},
					{XPATH_CURRENT_OFFERID, propTestData.getProperty("1_VALUE_CURRENT_OFFERID_"+env)},
					{XPATH_CURRENT_NAME, propTestData.getProperty("1_VALUE_CURRENT_NAME_"+env)},
					{XPATH_CURRENT_EXTERNALKEY, propTestData.getProperty("1_VALUE_CURRENT_EXTERNALKEY_"+env)},
					{XPATH_BILLINGOPTION_ID, propTestData.getProperty("1_VALUE_BILLINGOPTION_ID_"+env)},
					{XPATH_BILLINGOPTION_NAME, propTestData.getProperty("1_VALUE_BILLINGOPTION_NAME_"+env)},
					{XPATH_BILLINGOPTION_EXTERNALKEY, propTestData.getProperty("1_VALUE_BILLINGOPTION_EXTERNALKEY_"+env)},
					{XPATH_BILLINGOPTION_PLANEXTERNALKEY, propTestData.getProperty("1_VALUE_BILLINGOPTION_PLANEXTERNALKEY_"+env)},
					{XPATH_BILLINGOPTION_BILLINGPERIOD_COUNT, propTestData.getProperty("1_VALUE_BILLINGOPTION_BILLINGPERIOD_COUNT_"+env)},
					{XPATH_BILLINGOPTION_BILLINGPERIODTYPE, propTestData.getProperty("1_VALUE_BILLINGOPTION_BILLINGPERIODTYPE_"+env)},
					{XPATH_BILLINGOPTION_BILLINGDATE, propTestData.getProperty("1_VALUE_BILLINGOPTION_BILLINGDATE_"+env)},
					{XPATH_PRICE_USD, propTestData.getProperty("1_VALUE_PRICE_USD_"+env)},
					{XPATH_PRICE_USD_AMT, propTestData.getProperty("1_VALUE_PRICE_USD_AMT_"+env)},
					{XPATH_PRICE_CAD, propTestData.getProperty("1_VALUE_PRICE_CAD_"+env)},
					{XPATH_PRICE_CAD_AMT, propTestData.getProperty("1_VALUE_PRICE_CAD_AMT_"+env)},
					{XPATH_SUBSPLAN_ID, propTestData.getProperty("1_VALUE_SUBSPLAN_ID_"+env)},
					{XPATH_SUBSPLAN_NAME, propTestData.getProperty("1_VALUE_SUBSPLAN_NAME_"+env)},
					{XPATH_SUBSPLAN_USAGE_TYPE, propTestData.getProperty("1_VALUE_SUBSPLAN_USAGE_TYPE_"+env)},
					{XPATH_SUBSPLANTIER, propTestData.getProperty("1_VALUE_SUBSPLANTIER_"+env)},
					{XPATH_SUBSPLAN_MODULE, propTestData.getProperty("1_VALUE_SUBSPLAN_MODULE_"+env)},
					{XPATH_SUBSPLAN_EXTERNALKEY, propTestData.getProperty("1_VALUE_SUBSPLAN_EXTERNALKEY_"+env)},
					{XPATH_SUBSPLAN_URL, propTestData.getProperty("1_VALUE_SUBSPLAN_URL_"+env)},
					{XPATH_SUBS_STATUS, propTestData.getProperty("1_VALUE_SUBS_STATUS_"+env)},
					{XPATH_SUBSPLAN_TAXCODE,propTestData.getProperty("1_VALUE_SUBSPLAN_TAXCODE_"+env)},
					{XPATH_CANCEL_POLICY, propTestData.getProperty("1_VALUE_CANCEL_POLICY_"+env)},
					{XPATH_ENTL_COUNT, propTestData.getProperty("1_VALUE_ENTL_COUNT_"+env)},
					{XPATH_ENTL_TYPE, propTestData.getProperty("1_VALUE_ENTL_TYPE_"+env)},
					{XPATH_OFFRERING_DETAILCOUNT, propTestData.getProperty("1_VALUE_OFFRERING_DETAILCOUNT_"+env)},
					{XPATH_OFFRERING_DETAIL_DESC, propTestData.getProperty("1_VALUE_OFFRERING_DETAIL_DESC_"+env)},
					{XPATH_PRODUCTCODE, propTestData.getProperty("1_VALUE_PRODUCTCODE_"+env)},
					{XPATH_PRODUCTNAME, propTestData.getProperty("1_VALUE_PRODUCTNAME_"+env)}
					}				
					
		},
		{	propTestData.getProperty("2_SUBCRRPTION_ID_"+env),
			 new String[][]{
				{XPATH_SUBSID, propTestData.getProperty("2_SUBCRRPTION_ID_"+env)},
				{XPATH_OWNERID, propTestData.getProperty("2_VALUE_OWNERID_"+env)},
				{XPATH_STATUS, propTestData.getProperty("2_VALUE_STATUS_"+env)},
				{XPATH_IS_AUTORENEWAL_ENABLED, propTestData.getProperty("2_VALUE_IS_AUTORENEWAL_ENABLED_"+env)},
				{XPATH_PAYMENT_PROFILE_ID, propTestData.getProperty("2_VALUE_PAYMENT_PROFILE_ID_"+env)},
				{XPATH_OFFER_ID, propTestData.getProperty("2_VALUE_OFFER_ID_"+env)},
				{XPATH_CREDIT_DAYS, propTestData.getProperty("2_VALUE_CREDIT_DAYS_"+env)},
				{XPATH_NEXTBILL_AMT, propTestData.getProperty("2_VALUE_NEXTBILL_AMT_"+env)},
				{XPATH_NEXTBILL_CURRENCYID, propTestData.getProperty("2_VALUE_NEXTBILL_CURRENCYID_"+env)},
				{XPATH_NEXTBILL_CURRENCY_NAME, propTestData.getProperty("2_VALUE_NEXTBILL_CURRENCY_NAME_"+env)},
				{XPATH_NEXTBILL_DATE, propTestData.getProperty("2_VALUE_NEXTBILL_DATE_"+env)},
				{XPATH_EXP_DATE, propTestData.getProperty("2_VALUE_EXP_DATE_"+env)},
				{XPATH_CURRENT_OFFERID, propTestData.getProperty("2_VALUE_CURRENT_OFFERID_"+env)},
				{XPATH_CURRENT_NAME, propTestData.getProperty("2_VALUE_CURRENT_NAME_"+env)},
				{XPATH_CURRENT_EXTERNALKEY, propTestData.getProperty("2_VALUE_CURRENT_EXTERNALKEY_"+env)},
				{XPATH_BILLINGOPTION_ID, propTestData.getProperty("2_VALUE_BILLINGOPTION_ID_"+env)},
				{XPATH_BILLINGOPTION_NAME, propTestData.getProperty("2_VALUE_BILLINGOPTION_NAME_"+env)},
				{XPATH_BILLINGOPTION_EXTERNALKEY, propTestData.getProperty("2_VALUE_BILLINGOPTION_EXTERNALKEY_"+env)},
				{XPATH_BILLINGOPTION_PLANEXTERNALKEY, propTestData.getProperty("2_VALUE_BILLINGOPTION_PLANEXTERNALKEY_"+env)},
				{XPATH_BILLINGOPTION_BILLINGPERIOD_COUNT, propTestData.getProperty("2_VALUE_BILLINGOPTION_BILLINGPERIOD_COUNT_"+env)},
				{XPATH_BILLINGOPTION_BILLINGPERIODTYPE, propTestData.getProperty("2_VALUE_BILLINGOPTION_BILLINGPERIODTYPE_"+env)},
				{XPATH_BILLINGOPTION_BILLINGDATE, propTestData.getProperty("2_VALUE_BILLINGOPTION_BILLINGDATE_"+env)},
				{XPATH_SUBSPLAN_ID, propTestData.getProperty("2_VALUE_SUBSPLAN_ID_"+env)},
				{XPATH_SUBSPLAN_NAME, propTestData.getProperty("2_VALUE_SUBSPLAN_NAME_"+env)},
				{XPATH_SUBSPLAN_USAGE_TYPE, propTestData.getProperty("2_VALUE_SUBSPLAN_USAGE_TYPE_"+env)},
				{XPATH_SUBSPLANTIER, propTestData.getProperty("2_VALUE_SUBSPLANTIER_"+env)},
				{XPATH_SUBSPLAN_MODULE, propTestData.getProperty("2_VALUE_SUBSPLAN_MODULE_"+env)},
				{XPATH_SUBSPLAN_EXTERNALKEY, propTestData.getProperty("2_VALUE_SUBSPLAN_EXTERNALKEY_"+env)},
				{XPATH_SUBS_STATUS, propTestData.getProperty("2_VALUE_SUBS_STATUS_"+env)},
				{XPATH_CANCEL_POLICY, propTestData.getProperty("2_VALUE_CANCEL_POLICY_"+env)},
				{XPATH_ENTL_COUNT, propTestData.getProperty("2_VALUE_ENTL_COUNT_"+env)},
				{XPATH_ENTL_TYPE, propTestData.getProperty("2_VALUE_ENTL_TYPE_"+env)},
				{XPATH_OFFRERING_DETAILCOUNT, propTestData.getProperty("2_VALUE_OFFRERING_DETAILCOUNT_"+env)},
				{XPATH_OFFRERING_DETAIL_DESC, propTestData.getProperty("2_VALUE_OFFRERING_DETAIL_DESC_"+env)},
				{XPATH_PRODUCTCODE, propTestData.getProperty("2_VALUE_PRODUCTCODE_"+env)},
				{XPATH_PRODUCTNAME, propTestData.getProperty("2_VALUE_PRODUCTNAME_"+env)}
				}				
				
	},
		};
	}	
	
	@Test(dataProvider="getSubcriptionByID",groups={"SANITY","ALL","FUNC_SUBS_BYID"})
	public void getActiveSubcriptionByID(String SubID, String[][] expectedOutput) throws Exception {
		
		String url = urlBase+"/"+SubID;
		
		// EXECUYTING RESPONSE
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
			
		}
		      
		// VERIFYING TRESPONSE WITH EXPECTED OUTPUT
		validateXML(response,expectedOutput);
	}
		
	@DataProvider(name = "getExpiredSubcriptionByID")
	public Object[][] getExpiredSubcriptionByID_dp(){
		return new Object[][]{
				{	propTestData.getProperty("3_SUBCRRPTION_ID_"+env),
					 new String[][]{
						{XPATH_SUBSID, propTestData.getProperty("3_SUBCRRPTION_ID_"+env)},
						{XPATH_OWNERID, propTestData.getProperty("3_VALUE_OWNERID_"+env)},
						{XPATH_STATUS, propTestData.getProperty("3_VALUE_STATUS_"+env)},
						{XPATH_IS_AUTORENEWAL_ENABLED, propTestData.getProperty("3_VALUE_IS_AUTORENEWAL_ENABLED_"+env)},
						{XPATH_PAYMENT_PROFILE_ID, propTestData.getProperty("3_VALUE_PAYMENT_PROFILE_ID_"+env)},
						{XPATH_OFFER_ID, propTestData.getProperty("3_VALUE_OFFER_ID_"+env)},
						{XPATH_CREDIT_DAYS, propTestData.getProperty("3_VALUE_CREDIT_DAYS_"+env)},
						{XPATH_NEXTBILL_AMT, propTestData.getProperty("3_VALUE_NEXTBILL_AMT_"+env)},
						{XPATH_NEXTBILL_CURRENCYID, propTestData.getProperty("3_VALUE_NEXTBILL_CURRENCYID_"+env)},
						{XPATH_NEXTBILL_CURRENCY_NAME, propTestData.getProperty("3_VALUE_NEXTBILL_CURRENCY_NAME_"+env)},
						{XPATH_NEXTBILL_DATE, propTestData.getProperty("3_VALUE_NEXTBILL_DATE_"+env)},
						{XPATH_CURRENT_OFFERID, propTestData.getProperty("3_VALUE_CURRENT_OFFERID_"+env)},
						{XPATH_CURRENT_NAME, propTestData.getProperty("3_VALUE_CURRENT_NAME_"+env)},
						{XPATH_CURRENT_EXTERNALKEY, propTestData.getProperty("3_VALUE_CURRENT_EXTERNALKEY_"+env)},
						{XPATH_BILLINGOPTION_ID, propTestData.getProperty("3_VALUE_BILLINGOPTION_ID_"+env)},
						{XPATH_BILLINGOPTION_NAME, propTestData.getProperty("3_VALUE_BILLINGOPTION_NAME_"+env)},
						{XPATH_BILLINGOPTION_EXTERNALKEY, propTestData.getProperty("3_VALUE_BILLINGOPTION_EXTERNALKEY_"+env)},
						{XPATH_BILLINGOPTION_PLANEXTERNALKEY, propTestData.getProperty("3_VALUE_BILLINGOPTION_PLANEXTERNALKEY_"+env)},
						{XPATH_BILLINGOPTION_BILLINGPERIOD_COUNT, propTestData.getProperty("3_VALUE_BILLINGOPTION_BILLINGPERIOD_COUNT_"+env)},
						{XPATH_BILLINGOPTION_BILLINGPERIODTYPE, propTestData.getProperty("3_VALUE_BILLINGOPTION_BILLINGPERIODTYPE_"+env)},
						{XPATH_BILLINGOPTION_BILLINGDATE, propTestData.getProperty("3_VALUE_BILLINGOPTION_BILLINGDATE_"+env)},
						{XPATH_PRICE_USD, propTestData.getProperty("3_VALUE_PRICE_USD_"+env)},
						{XPATH_PRICE_USD_AMT, propTestData.getProperty("3_VALUE_PRICE_USD_AMT_"+env)},
						{XPATH_PRICE_CAD, propTestData.getProperty("3_VALUE_PRICE_CAD_"+env)},
						{XPATH_PRICE_CAD_AMT, propTestData.getProperty("3_VALUE_PRICE_CAD_AMT_"+env)},
						{XPATH_SUBSPLAN_ID, propTestData.getProperty("3_VALUE_SUBSPLAN_ID_"+env)},
						{XPATH_SUBSPLAN_NAME, propTestData.getProperty("3_VALUE_SUBSPLAN_NAME_"+env)},
						{XPATH_SUBSPLAN_USAGE_TYPE, propTestData.getProperty("3_VALUE_SUBSPLAN_USAGE_TYPE_"+env)},
						{XPATH_SUBSPLANTIER, propTestData.getProperty("3_VALUE_SUBSPLANTIER_"+env)},
						{XPATH_SUBSPLAN_MODULE, propTestData.getProperty("3_VALUE_SUBSPLAN_MODULE_"+env)},
						{XPATH_SUBSPLAN_EXTERNALKEY, propTestData.getProperty("3_VALUE_SUBSPLAN_EXTERNALKEY_"+env)},
						{XPATH_SUBSPLAN_URL, propTestData.getProperty("3_VALUE_SUBSPLAN_URL_"+env)},
						{XPATH_SUBS_STATUS, propTestData.getProperty("3_VALUE_SUBS_STATUS_"+env)},
						{XPATH_CANCEL_POLICY, propTestData.getProperty("3_VALUE_CANCEL_POLICY_"+env)},
						{XPATH_ENTL_COUNT, propTestData.getProperty("3_VALUE_ENTL_COUNT_"+env)},
						{XPATH_ENTL_TYPE, propTestData.getProperty("3_VALUE_ENTL_TYPE_"+env)},
						{XPATH_SUBSPLAN_TAXCODE,propTestData.getProperty("3_VALUE_SUBSPLAN_TAXCODE_"+env)},
						{XPATH_OFFRERING_DETAILCOUNT, propTestData.getProperty("3_VALUE_OFFRERING_DETAILCOUNT_"+env)},
						{XPATH_OFFRERING_DETAIL_DESC, propTestData.getProperty("3_VALUE_OFFRERING_DETAIL_DESC_"+env)},
						{XPATH_PRODUCTCODE, propTestData.getProperty("3_VALUE_PRODUCTCODE_"+env)},
						{XPATH_PRODUCTNAME, propTestData.getProperty("3_VALUE_PRODUCTNAME_"+env)}
						}		
					
		}
		
		};
	}	
	
	@Test(dataProvider="getExpiredSubcriptionByID",groups={"ALL","FUNC_SUBS_BYID"})
	public void getExpiredSubcriptionByID(String SubID, String[][] expectedOutput) throws Exception {
		
		String url = urlBase+"/"+SubID;
		
		// EXECUYTING RESPONSE
		HttpResponse response = (HttpResponse) executeGET(url);
		
		if (response != null && response.getStatusLine().getStatusCode() != HTTP_STATUSCODE_SUCCESS ){
			printErrorResponse(response);
			throw new AssertionError("The Response code is "+response.getStatusLine().getStatusCode()+", but expected was "+HTTP_STATUSCODE_SUCCESS);
			
		}
		
		// VERIFYING TRESPONSE WITH EXPECTED OUTPUT
		validateXML(response,expectedOutput);
	}
		
		
	
	
	
}

