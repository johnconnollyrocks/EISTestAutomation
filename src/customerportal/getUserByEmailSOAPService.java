package customerportal;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

import com.autodesk.schemas.business.partyv2.GetUserByEmailRequest;
import com.autodesk.schemas.business.partyv2.GetUserByEmailResponse;
import com.autodesk.ws.user._1.SOAPFault;
import com.autodesk.ws.user._1.UserPort;
import com.autodesk.ws.user._1.UserService;
import com.jniwrapper.win32.hook.SysMsgProcEvent;

import common.EISTestBase;
import common.Util;

/**
 * 
 * @author t_marus
 *
 */

public class getUserByEmailSOAPService extends EISTestBase{
	/*private String endPointURL_DEV = "https://devservices.autodesk.com/dm/UserService";
	private String endPointURL_STG = "https://stageservices.autodesk.com/UserService";*/

	private String endPointURL_DEV = null;
	private String endPointURL_STG = null;
	private String emailIDforUserService=null;
	
	public getUserByEmailSOAPService(String endPointURL, String userEmailId) {
		super();
		if (getEnvironment().equalsIgnoreCase("dev")) {
			this.endPointURL_DEV=endPointURL;			
		}else {
			//for STG
			this.endPointURL_STG=endPointURL;
			
		}
		this.emailIDforUserService=userEmailId;
	}
	/**
	 * @Description this is setup method for the User Service wsdl
	 * @return
	 */
	private UserPort getWSPort() {
		URL wsdlURL = null;

		try {
			System.out.println("Found the user directory as "+System.getProperty("user.dir"));
			if (System.getProperty("user.dir").contains("build")) {
				wsdlURL = new URL("file:\\///"+System.getProperty("user.dir")+File.separator+"UserService.wsdl");
			}else {
				wsdlURL = new URL("file:\\///"+System.getProperty("user.dir")+File.separator+"build"+File.separator+"UserService.wsdl");	
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		QName SERVICE_NAME = new QName("http://www.autodesk.com/ws/User/1.0",
				"UserService");

		UserService ss = new UserService(wsdlURL, SERVICE_NAME);
		UserPort userPORT = ss.getUser();
		((BindingProvider) userPORT).getRequestContext().put(
				org.apache.cxf.headers.Header.HEADER_LIST,
				generateHeaders("getUserByEmail"));
		if (getEnvironment().equalsIgnoreCase("dev")) {
			((BindingProvider) userPORT).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL_DEV);
			
		}else {
			
			((BindingProvider) userPORT).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL_STG);
		}

		SSLContext sslContext = null;
		try {	
			//for STG and DEV same cert will do
			/*if (getEnvironment().equalsIgnoreCase("dev")) {*/		
			//while running from jenkins
			if (System.getProperty("user.dir").contains("build")) {
				sslContext = getSSLContext(
						System.getProperty("user.dir")+File.separator+"AutodeskInternal_DEVQA_exp2016.p12",
						"Adsk2016");
			}else {
				
				sslContext = getSSLContext(
						System.getProperty("user.dir")+File.separator+"build"+File.separator+"AutodeskInternal_DEVQA_exp2016.p12",
						"Adsk2016");
			}
			/*}*/
				//for STG  TO DO
			/*else {
				sslContext = getSSLContext(
						Util.getTestRootDir()+File.separator+"build"+File.separator+"AutodeskInternal_DEVQA_exp2016","Adsk2016");
			  }*/ 
			
		} catch (Exception e) {
				Util.printError("Unable to generate the Get User by Email service request");
				System.out.println(e.getMessage());
				e.printStackTrace();
		}
		//cert is required only for STG		
		TLSClientParameters tlsParams = new TLSClientParameters();
		tlsParams.setSecureSocketProtocol("TLS");
		tlsParams.setSSLSocketFactory(sslContext.getSocketFactory());
		if (getEnvironment().equalsIgnoreCase("stg")) {			
			tlsParams.setDisableCNCheck(true);
		}
		Client c = ClientProxy.getClient(userPORT);
		HTTPConduit conduit = (HTTPConduit) c.getConduit();
		conduit.setTlsClientParameters(tlsParams);

		return userPORT;
	}

	private static List<org.apache.cxf.headers.Header> generateHeaders(
			String webServiceName) {
		List<org.apache.cxf.headers.Header> headers = new ArrayList<org.apache.cxf.headers.Header>();
		try {
			String prefixUri = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-", wsaPrefixUri = "http://schemas.xmlsoap.org/ws/2004/08/addressing";
			String uri = prefixUri + "wssecurity-secext-1.0.xsd";
			String uta = prefixUri + "wssecurity-utility-1.0.xsd";
			String ta = prefixUri + "username-token-profile-1.0#PasswordText";
			String wssePrefix = "wsse", wsaPrefix = "wsa";

			String userName = "qa_prod_test1";
			String passWord1 = "Password1";
			SOAPFactory sf = SOAPFactory.newInstance();

			// SoapHeader
			SOAPElement securityElem = sf.createElement("Security", wssePrefix,
					uri);
			SOAPElement tokenElem = sf.createElement("UsernameToken",
					wssePrefix, uri);
			tokenElem.addAttribute(QName.valueOf("wsu:Id"), "UsernameToken-2");
			tokenElem.addAttribute(QName.valueOf("xmlns:wsu"), uta);
			SOAPElement userElem = sf
					.createElement("Username", wssePrefix, uri);
			userElem.addTextNode(userName);
			SOAPElement pwdElem = sf.createElement("Password", wssePrefix, uri);
			pwdElem.addTextNode(passWord1);
			pwdElem.addAttribute(QName.valueOf("Type"), ta);
			tokenElem.addChildElement(userElem);
			tokenElem.addChildElement(pwdElem);
			securityElem.addChildElement(tokenElem);

			SOAPElement authElement = sf
					.createElement(new QName(
							"http://www.autodesk.com/schemas/Technical/Common/RequestHeaderV1.0",
							"Header"));
			SOAPElement messageIdentifer = authElement
					.addChildElement("MessageIdentifier");
			SOAPElement messageName = messageIdentifer
					.addChildElement("MessageName");
			messageName.addTextNode("test");
			SOAPElement messageVersion = messageIdentifer
					.addChildElement("MessageVersion");
			messageVersion.addTextNode("1");
			// adding transaction node
			SOAPElement transactionElement = authElement
					.addChildElement("Transaction");
			SOAPElement transactionIDElement = transactionElement
					.addChildElement("TransactionID");
			transactionIDElement.addTextNode("test");
			SOAPElement transactionTypeElement = transactionElement
					.addChildElement("TransactionType");
			transactionTypeElement.addTextNode("test");
			SOAPElement transactionDomainElement = transactionElement
					.addChildElement("TransactionDomain");
			transactionDomainElement.addTextNode("test");

			SOAPElement requestingSystem = authElement
					.addChildElement("RequestingSystem");
			requestingSystem.addAttribute(QName.valueOf("responseLanguage"),
					"en");
			SOAPElement CorrelationIDElement = requestingSystem
					.addChildElement("CorrelationID");
			CorrelationIDElement.addTextNode("test");
			SOAPElement requestingApplicationName = requestingSystem
					.addChildElement("RequestingApplicationName");
			requestingApplicationName.addTextNode("PerfTestSimulate");
			SOAPElement requestingServerElement = requestingSystem
					.addChildElement("RequestingServer");
			requestingServerElement.addTextNode("test");
			SOAPElement requestingProcessIDElement = requestingSystem
					.addChildElement("RequestingProcessId");
			requestingProcessIDElement.addTextNode("test");

			SOAPElement properties = authElement.addChildElement("Properties");
			SOAPElement cachedDataAccess = properties
					.addChildElement("CachedDataAccess");
			cachedDataAccess.addTextNode("true");

			SOAPElement wsaElement = sf.createElement(new QName(
					"http://schemas.xmlsoap.org/ws/2004/08/addressing", "wsa"));

			String wsaActionText = null;
			if (webServiceName.equalsIgnoreCase("getUserByEmail")) {
				wsaActionText = "GetUserByEmailAction";
			}
			SOAPElement wsaActionElement = wsaElement.addChildElement("Action");
			wsaActionElement.addTextNode(wsaActionText);
			SOAPElement wsaMsgElement = wsaElement.addChildElement("MessageID");
			wsaMsgElement
					.addTextNode("urn:uuid:ebe85384-9c91-44a1-a364-fa9e4b7ddafa");
			SOAPElement wsaReplytoElement = wsaElement
					.addChildElement("ReplyTo");
			SOAPElement wsaAddressEle = wsaReplytoElement
					.addChildElement("Address");
			wsaAddressEle
					.addTextNode("http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous");

			SOAPElement wsaToElement = wsaElement.addChildElement("To");
			wsaToElement
					.addTextNode("http://localhost:4019/services/UserService");

			SoapHeader securityHeader = new SoapHeader(
					new QName(
							"http://www.autodesk.com/schemas/Technical/Common/RequestHeaderV1.0",
							"Header"), securityElem);
			SoapHeader tokenHeader = new SoapHeader(
					new QName(
							"http://www.autodesk.com/schemas/Technical/Common/RequestHeaderV1.0",
							"Header"), authElement);
			headers.add(securityHeader);
			headers.add(tokenHeader);

		} catch (SOAPException ex) {
			System.out
					.println("EnterprisePartyServiceClient - generateHeaders :  Exception generating Header"
							+ ex);
		}
		return headers;
	}

	public String getUserByEmailResponse() throws SOAPFault {
		GetUserByEmailRequest userByEmailRequest = new GetUserByEmailRequest();		
		userByEmailRequest.setEmail(emailIDforUserService);

		UserPort wsPort = getWSPort();
		GetUserByEmailResponse userByEmail = wsPort
				.getUserByEmail(userByEmailRequest);		

		JAXBElement<String> guid = userByEmail.getUser().getGUID();
		
		System.out.println( guid.getValue());
		/*org.junit.Assert.assertEquals(GUIDtoVerify, guid.getValue());*/

		return guid.getValue();

	}
	//for debug use this

	/*public static void main(String[] args) throws SOAPFault {
		new newSOAP().getUserByEmailResponse();

	}
*/
	
	private static SSLContext getSSLContext(String keyStoreFilePath,
			String keyStorePassword) throws Exception {
		try {

			SSLContext context = SSLContext.getInstance("TLS");

			InputStream keyInput = new FileInputStream(keyStoreFilePath);
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(keyInput, keyStorePassword.toCharArray());
			keyInput.close();

			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, keyStorePassword.toCharArray());

			context.init(kmf.getKeyManagers(), null, new SecureRandom());

			return context;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void createAppWindows() {
		
	}
	@Override
	protected void chooseApp() {
		
	}
	@Override
	protected void setEnvironmentVariables() {
		
	}
	
}
