package amazonrental;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISConstants;
import common.EISTestBase;
import common.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.amazon.dtasdk.v2.serialization.messages.GetUserIdSerializableRequest;
import com.amazon.dtasdk.v2.serialization.messages.GetUserIdSerializableResponse;
import com.amazon.dtasdk.v2.serialization.messages.GetUserIdSerializableResponseValue;
import com.amazon.dtasdk.v2.serialization.messages.InstantAccessOperationValue;
import com.amazon.dtasdk.v2.serialization.messages.InstantAccessRequest;
import com.amazon.dtasdk.v2.serialization.messages.InstantAccessResponse;
import com.amazon.dtasdk.v2.serialization.messages.SubscriptionActivateRequest;
import com.amazon.dtasdk.v2.serialization.messages.SubscriptionDeactivateRequest;
import com.amazon.dtasdk.v2.serialization.messages.SubscriptionPeriodValue;
import com.amazon.dtasdk.v2.serialization.messages.SubscriptionReasonValue;
import com.amazon.dtasdk.v2.serialization.messages.SubscriptionResponse;
import com.amazon.dtasdk.v2.serialization.messages.SubscriptionResponseValue;
import com.amazon.dtasdk.v2.serialization.serializer.JacksonSerializer;
import com.amazon.dtasdk.v2.serialization.serializer.SerializationException;
import com.amazon.dtasdk.v2.signature.Credential;
import com.amazon.dtasdk.v2.signature.Request;
import com.amazon.dtasdk.v2.signature.Request.Method;
import com.amazon.dtasdk.v2.signature.Signer;
import com.amazon.dtasdk.v2.signature.SigningException;

public class Test_AMZ_OrderCreation_SFDC_Validation_WebService
		extends AmazonRentalTestBase {

	private final Log log = LogFactory
			.getLog(Test_AMZ_OrderCreation_SFDC_Validation_WebService.class);

	private static String LINK_URL = "https://enterprise-api-stg.autodesk.com/amazon/v1/accountlink?apikey=egfZJPRowhoHpc1ayKajkA5BgyNhQfg7";
	private static String FULFILLMENT_URL = "https://enterprise-api-stg.autodesk.com/amazon/v1/fulfillment?apikey=egfZJPRowhoHpc1ayKajkA5BgyNhQfg7";

	private HttpClient client;
	private JacksonSerializer serializer;
	private Signer signer;
	private Credential credential;

	public Test_AMZ_OrderCreation_SFDC_Validation_WebService()
			throws IOException {
		super();

		client = HttpClients.createDefault();
		serializer = new JacksonSerializer();
		signer = new Signer();
		credential = new Credential("c95f1188-48c4-40e5-83e1-8f2b2830aadf", "ab97fa37-bc3e-4c8c-aace-ddfa24dd7ebe");

	}

	@Before
	public void setUp() throws Exception {

		System.out.println("\n"
				+ "Test Account link and Activate Subscription stared......"
				+ "\n");

	}

	@Test
	public void Test_AmazonRental_Instantaccess_AccountLinking()
			throws Exception {

		run();

	}

	private void run() throws SigningException, SerializationException,
			IOException,Exception {

		List<String> productLines = new ArrayList<String>();
		productLines = getProductLines("SKU");

		for (int i = 0; i < productLines.size(); i++) {

			System.out.println("\n" + "Linking account started for user : "
					+ testProperties.getConstant("EMAIL") + "\n");
			GetUserIdSerializableResponse response = linkAccount(
					testProperties.getConstant("OxygenUserID"),
					testProperties.getConstant("EMAIL"), null);

			if (!response.getResponse().equals(
					GetUserIdSerializableResponseValue.OK)) {
				log.error("LINK ERROR");
				EISTestBase.fail("\n"
						+ " LINKING is not done getting error : "
						+ response.getResponse());
				return;
			}

			System.out.println("\n"
					+ "Linking account is Succesfully Done for user : "
					+ testProperties.getConstant("OxygenUserID")
					+ " and Email Id : " + testProperties.getConstant("EMAIL")
					+ "\n");
			System.out.println("Activate Subscription started for user : "
					+ testProperties.getConstant("OxygenUserID")
					+ " and Email Id : " + testProperties.getConstant("EMAIL")
					+ "\n");
			String user = response.getUserId();
			int SubscriptionID = getUniqueId();
			System.out.println("SubscriptionID : " + SubscriptionID + "\n");
			System.out.println("ProductID : " + productLines.get(i) + "\n");
			System.out.println("UserID : " + user + "\n");
			SubscriptionResponse response1 = activateSubscription(
					Integer.toString(SubscriptionID), productLines.get(i), user);

			if (!response1.getResponse().equals(SubscriptionResponseValue.OK)) {
				log.error("SUB ERROR");
				EISTestBase.fail("\n"
						+ " Activate Subscription is not done getting error : "
						+ response1.getResponse());
				
				return;
			}
			System.out
					.println("\n"
							+ "Activate Subscription is Succesfully Done And Subscription ID is : "
							+ SubscriptionID + "\n");

			// deactivateSubscription("SUBID01",
			// SubscriptionPeriodValue.REGULAR,
			// SubscriptionReasonValue.PAYMENT_PROBLEM);

			// Validating Order sync in SFDC
			Thread.sleep(120000);

			launchSFDC(AmazonRentalConstants.BASE_URL_SFDC);
			 // Login to SFDC
			
			loginSFDC(testProperties.getConstant("SFDC_UserName"),testProperties.getConstant("SFDC_Password"));
			
			// Querying the subscription ID  
			
			sfdcPage.populateField("searchSubscription",Integer.toString(SubscriptionID));
			sfdcPage.click("searchButton");
			Thread.sleep(6000);
			
			
			
			if(sfdcPage.checkFieldExistence("assets")){
				System.out.println("Query returns the data ....clicking on assets"+"\n");
				sfdcPage.click("assets");
			}
				else {
					EISTestBase.fail("Query returns empty results Subscription not sync to SFDC");
					
			}
			
				
			
			//getting the data from SFDC 
			
			
			String productLinecode = sfdcPage.getValueFromGUI("productLineCode");
			
			System.out.println("product Line code is : "+productLinecode +"\n");
			
			//Clicking on Contract line items
			
			//swithToIframeWhereMyFieldExist(sfdcPage, "contractLineItemLink");
			
							
			sfdcPage.click("contractLineItemLink");
			
			Thread.sleep(6000);
			
			//Getting values
			
			String agreementNumber=sfdcPage.getValueFromGUI("agreementNumber");
			System.out.println("agreement Number is:   "+agreementNumber +"\n");
			String serialNumber = sfdcPage.getValueFromGUI("serialNumber");
			System.out.println("serial Number is:  "+serialNumber +"\n");
				
			// verifying the asset status 
				
			String assetstatus = sfdcPage.getValueFromGUI("assetstatus");
			System.out.println("Assert : Checking asset status" + "\n");
			compareStrings("Registered", assetstatus);
				
			//verifying the end customer 
			String endCustomer = sfdcPage.getValueFromGUI("endCustomer");
			System.out.println("endCustomer is  " + "\n");
			//compareStrings("Amazon Rentals", endCustomer);
			
			String startDate = sfdcPage.getValueFromGUI("startDate");
			System.out.println("start date is :  "+startDate +"\n");
			String endDate = sfdcPage.getValueFromGUI("endDate");
			System.out.println("end date is :  "+endDate +"\n");
			
			//logging out SFDC 
			
			logoutAsSFDCUser(testProperties.getConstant("SFDC_UserName"));
			
		}

	}

	private GetUserIdSerializableResponse linkAccount(String infoField1,
			String infoField2, String infoField3)
			throws SerializationException, SigningException, IOException {
		GetUserIdSerializableRequest getUserId = new GetUserIdSerializableRequest();
		getUserId.setOperation(InstantAccessOperationValue.GETUSERID);
		getUserId.setInfoField1(infoField1);
		getUserId.setInfoField2(infoField2);
		getUserId.setInfoField3(infoField3);

		Request request = createRequest(LINK_URL, getUserId);
		return sendRequest(request, GetUserIdSerializableResponse.class);
	}

	private SubscriptionResponse activateSubscription(String subscriptionId,
			String productId, String userId) throws SerializationException,
			SigningException, IOException {
		SubscriptionActivateRequest activateSubscription = new SubscriptionActivateRequest();
		activateSubscription
				.setOperation(InstantAccessOperationValue.SUBSCRIPTIONACTIVATE);
		activateSubscription.setSubscriptionId(subscriptionId);
		activateSubscription.setProductId(productId);
		activateSubscription.setUserId(userId);

		Request request = createRequest(FULFILLMENT_URL, activateSubscription);
		return sendRequest(request, SubscriptionResponse.class);
	}

	private SubscriptionResponse deactivateSubscription(String subscriptionId,
			SubscriptionPeriodValue period, SubscriptionReasonValue reason)
			throws SerializationException, SigningException, IOException {
		SubscriptionDeactivateRequest deactivateSubscription = new SubscriptionDeactivateRequest();
		deactivateSubscription
				.setOperation(InstantAccessOperationValue.SUBSCRIPTIONDEACTIVATE);
		deactivateSubscription.setSubscriptionId(subscriptionId);
		deactivateSubscription.setPeriod(period);
		deactivateSubscription.setReason(reason);

		Request request = createRequest(FULFILLMENT_URL, deactivateSubscription);
		return sendRequest(request, SubscriptionResponse.class);
	}

	private Request createRequest(String url, InstantAccessRequest iaRequest)
			throws SerializationException, SigningException {
		Request request = new Request(url, Method.POST, "application/json");
		request.setBody(serializer.encode(iaRequest));
		signer.sign(request, credential);

		log.info("Authorization header:" + request.getHeader("Authorization"));
		return request;
	}

	private <T extends InstantAccessResponse<?>> T sendRequest(Request request,
			Class<T> responseClass) throws IOException, SerializationException {
		HttpPost post = new HttpPost(request.getUrl());

		for (String header : request.getHeaderNames()) {
			post.setHeader(header, request.getHeader(header));
		}

		post.setEntity(new StringEntity(request.getBody()));

		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			public String handleResponse(HttpResponse response)
					throws IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status == 200) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException(
							"Unexpected response status: " + status);
				}
			}

		};

		log.info(String.format("Sending request [%s] body [%s]",
				post.getRequestLine(), EntityUtils.toString(post.getEntity())));

		String responseBody = null;
		try {
			responseBody = client.execute(post, responseHandler);
			log.info(String.format("Received response [%s]", responseBody));
		} catch (IOException e) {
			log.error("Unable to send request", e);
			throw e;
		}

		return serializer.decode(responseBody, responseClass);
	}

	protected List<String> getProductLines(String constantName) {
		String productLineConstantsString;
		List<String> productLineConstants = new ArrayList<String>();
		List<String> productLines = new ArrayList<String>();

		productLineConstantsString = testProperties.getConstant(constantName);
		if (productLineConstantsString.isEmpty()) {
			fail("No product line constants were supplied in a CONSTANT_SET named '"
					+ constantName + "' in test properties");
		}

		productLineConstants = java.util.Arrays
				.asList(productLineConstantsString.split(";"));

		ListIterator<String> itr = productLineConstants.listIterator();
		while (itr.hasNext()) {
			String productLineConstant = itr.next();

			productLines.add(productLineConstant);
		}

		return productLines;

	}

	@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}

}
