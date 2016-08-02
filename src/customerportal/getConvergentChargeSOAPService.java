package customerportal;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

import pelican.bicPelicanWebService;

import com.autodesk.schemas.business.convergentchargingexv1.ChargeDataType;
import com.autodesk.schemas.business.convergentchargingexv1.ConvergentChargingExPortType;
import com.autodesk.schemas.business.convergentchargingexv1.ConvergentChargingExService;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcTransactionSummaryRequest;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcTransactionSummaryRequest.ListOfGuids;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcTransactionSummaryResponse;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcTransactionsRequest;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcTransactionsRequest.ListOfContractNumbers;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcTransactionsResponse;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcTransactionsResponse.ListOfCcTransactions;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcUsageSummaryRequest;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcUsageSummaryResponse;
import com.autodesk.schemas.business.convergentchargingexv1.GetCcUsageSummaryResponse.ListOfCcUsageSummary.CcSummaryUsage;
import com.autodesk.schemas.business.convergentchargingexv1.QuerySubscriptionBalanceRequest;
import com.autodesk.schemas.business.convergentchargingexv1.QuerySubscriptionBalanceRequest.ListOfContracts;
import com.autodesk.schemas.business.convergentchargingexv1.QuerySubscriptionBalanceRequest.ListOfContracts.Contract;
import com.autodesk.schemas.business.convergentchargingexv1.QuerySubscriptionBalanceResponse;
import com.autodesk.schemas.business.convergentchargingexv1.ResponseType;
import com.autodesk.schemas.business.convergentchargingexv1.SOAPFault_Exception;
import common.EISTestBase;

public class getConvergentChargeSOAPService extends EISTestBase{
	
	private String endPOINTURL_DEV=null;
	private String endPOINTURL_STG=null;
	private final String JOB_UID="Test Job ID 6";

	private String END_TIME="2016-12-10";	//default value

	URL wsdlURL=null;
	ChargeDataType chargeEvent =null;
	GetCcTransactionsRequest  ccTransactionReq =null;
	GetCcTransactionSummaryRequest ccTransactionSummaryRequest = null;
	QuerySubscriptionBalanceRequest  querySubBalance = null;
	GetCcUsageSummaryRequest ccUsageSummaryRequest = null;
	
	public getConvergentChargeSOAPService(String endpointURL) {
	/*	this.SID=SID;
		this.UID=uid;
		this.GUID=GUID;
		this.SERVICE_CATEGORY=serviceCategory;
		this.QUANTITY=quantity;
		this.SERVICE_CODE=serviceCode;
		this.START_TIME= startTime;
		this.END_TIME= endTime;*/
		/*System.setProperty("environment", "dev");	*/	
    	//get the thread stack and pull the caller information. Except Pelican tests, all other test using the getEnvironment
		boolean pelicanTest=false;
		StackTraceElement[] callingThread= Thread.currentThread().getStackTrace();
		for(StackTraceElement threadEle: callingThread){
			if (threadEle.getFileName()!=null){
				if (threadEle.getFileName().contains("Pelican")){
					pelicanTest=true;
					break;
				}
			}
		}
		//For Pelican tests, the environment val is not being provided via jvm args and its given via testng xml 
		if (pelicanTest){
			String environmentType = bicPelicanWebService.getEnvironmentType();
			setEnvironment(environmentType);
		}
	  if (getEnvironment().equalsIgnoreCase("dev")) {	
			this.endPOINTURL_DEV=endpointURL;
		}else {
			this.endPOINTURL_STG=endpointURL;
		}
		
	}
			
	/**
	 * @Description for the ChargeEvent
	 * @return
	 */
	public String getChargeEvent(String SID,String uid,String GUID,String serviceCategory,String quantity,String serviceCode,
			String startTime,String productLinecode) throws Exception{
		chargeEvent= new ChargeDataType();		
		chargeEvent.setJobUID(JOB_UID);
		chargeEvent.setUID(uid);
		/*chargeEvent.setSID(SID);*/
		chargeEvent.setGUID(GUID);
		chargeEvent.setSID(SID);
		chargeEvent.setServiceCode(serviceCode);
		chargeEvent.setStartTime(getXMLGregorianValueForDate(startTime));		
		/*chargeEvent.setEndTime(getXMLGregorianValueForDate(END_TIME));*/
		chargeEvent.setStatus("OK");		
		chargeEvent.setProductLinecode(productLinecode);				
		/*chargeEvent.setComments(COMMENTS);*/
		chargeEvent.setServiceCategory(serviceCategory);
		chargeEvent.setQuantity(Double.valueOf(quantity));
		ResponseType chargeEvent2 = getPort().chargeEvent(chargeEvent);		
		System.out.println(chargeEvent2.getResponseMsg());		
		return chargeEvent2.getResponseStatus();		
	
	}
	
	/**
	 * @Description enter the contract number to retrieve  CC transactions :get CCTransactions
	 * @param contractNumber
	 * @throws SOAPFault_Exception 
	 */
	public List<GetCcTransactionsResponse.ListOfCcTransactions.CcTransaction>  getCCTransactions(String contractNumber) throws SOAPFault_Exception {
		 ccTransactionReq = new GetCcTransactionsRequest();		
		GetCcTransactionsRequest.ListOfContractNumbers listOfContractNumbers = new ListOfContractNumbers();
		listOfContractNumbers.getContractNumber().add(contractNumber);
		ccTransactionReq.setListOfContractNumbers(listOfContractNumbers);		
		GetCcTransactionsResponse ccTransactions = getPort().getCcTransactions(ccTransactionReq);
		ListOfCcTransactions listOfCcTransactions = ccTransactions.getListOfCcTransactions();
		return listOfCcTransactions.getCcTransaction();
		
	}
	/**
	 * @Description : enter the user GUID get the USER to fetch the list of cc Transaction Summary
	 * @param userGUID
	 * @return
	 * @throws SOAPFault_Exception
	 */
	public List<GetCcTransactionSummaryResponse.ListOfCcTransactionSummary.CcSummaryTransaction> getccTransactionSummary(String userGUID) throws SOAPFault_Exception {
		ccTransactionSummaryRequest = new GetCcTransactionSummaryRequest();
		GetCcTransactionSummaryRequest.ListOfGuids  listOfGuids = new ListOfGuids();
		listOfGuids.getGUID().add(userGUID);
		ccTransactionSummaryRequest.setListOfGuids(listOfGuids);
		GetCcTransactionSummaryResponse ccTransactionSummary = getPort().getCcTransactionSummary(ccTransactionSummaryRequest);
		return ccTransactionSummary.getListOfCcTransactionSummary().getCcSummaryTransaction();		
	}
	
	/**
	 * @Description get query Subscription Balance for any given Contract number
	 * @param contractNumber
	 * @throws SOAPFault_Exception
	 */
	public List<QuerySubscriptionBalanceResponse.ListOfContracts.Contract> getQuerySubscriptionBalance(String contractNumber) throws SOAPFault_Exception {
		querySubBalance = new QuerySubscriptionBalanceRequest();
		QuerySubscriptionBalanceRequest.ListOfContracts listofContracts = new ListOfContracts();
		Contract contractToAdd = new Contract();
		contractToAdd.setContractNumber(contractNumber);
		listofContracts.getContract().add(contractToAdd);
		querySubBalance.setListOfContracts(listofContracts);
		//get the response
		QuerySubscriptionBalanceResponse querySubscriptionBalance = getPort().querySubscriptionBalance(querySubBalance);
		return querySubscriptionBalance.getListOfContracts().getContract();
		
	}
	/**
	 * @Description get CCUsage Summary for the Contract Number and GUID
	 * @param contractNumber
	 * @param GUID
	 * @return
	 * @throws SOAPFault_Exception
	 */
	public List<CcSummaryUsage> getccUsageSummary(String contractNumber,String GUID) throws SOAPFault_Exception {
		ccUsageSummaryRequest=  new GetCcUsageSummaryRequest();
		GetCcUsageSummaryRequest.ListOfContracts contractList = new GetCcUsageSummaryRequest.ListOfContracts();
		contractList.getContractNumber().add(contractNumber);
		GetCcUsageSummaryRequest.ListOfGuids guidList = new GetCcUsageSummaryRequest.ListOfGuids();
		guidList.getGUID().add(GUID);
		ccUsageSummaryRequest.setListOfContracts(contractList);
		ccUsageSummaryRequest.setListOfGuids(guidList);
		//get CCUsage Summary Response
		GetCcUsageSummaryResponse ccUsageSummary = getPort().getCcUsageSummary(ccUsageSummaryRequest);
		return ccUsageSummary.getListOfCcUsageSummary().getCcSummaryUsage();
		
	}
	
	/**
	 * Description this is used to set the port and endpoint url for the service request 
	 * @return
	 */
	public ConvergentChargingExPortType getPort() {
		try {
			
			if (getEnvironment().equalsIgnoreCase("dev")) {
				wsdlURL = new URL(endPOINTURL_DEV+"/ConvergentCharging?wsdl");
			}else {
				//NOTE: For STG required Cert to open the wsdl file hence use the local wsdl file
				if (System.getProperty("user.dir").contains("build")) {
					wsdlURL= new URL("file:\\///"+System.getProperty("user.dir")+File.separator+"build"+File.separator+"ConvergentCharging.wsdl");
				}else {
					
					wsdlURL= new URL("file:\\///"+System.getProperty("user.dir")+File.separator+"ConvergentCharging.wsdl");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		QName SERVICE_NAME = new QName("http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0",
				"ConvergentChargingExService");
		
		
		ConvergentChargingExService	ss = new ConvergentChargingExService(wsdlURL, SERVICE_NAME);
		ConvergentChargingExPortType convergentChargingExPort = ss.getConvergentChargingExPort();
		
		SSLContext sslContext = null;
		try {
			/*if (getEnvironment().equalsIgnoreCase("dev")) {				
				sslContext = getSSLContext(
						System.getProperty("user.dir")+File.separator+"build"+File.separator+"AutodeskInternal_DEVQA_exp2016.p12",
						"Adsk2016");
			}*/
			if (getEnvironment().equalsIgnoreCase("stg")) {
				//TO verify in STG
				//via Jenkins
				if (System.getProperty("user.dir").contains("build")) {					
					sslContext = getSSLContext(
							System.getProperty("user.dir")+File.separator+"cepstg.pfx","yWPjYIfQjVEwHhhjYohKTR3f");
				}else {
					sslContext = getSSLContext(
							System.getProperty("user.dir")+File.separator+"build"+File.separator+"cepstg.pfx","yWPjYIfQjVEwHhhjYohKTR3f");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (getEnvironment().equalsIgnoreCase("stg")) {
			//SHOULD BE DONE ONLY FOR STG		 			
			//point to STG
			((BindingProvider) convergentChargingExPort).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPOINTURL_STG);
			
			TLSClientParameters tlsParams = new TLSClientParameters();
			tlsParams.setSecureSocketProtocol("TLS");
			tlsParams.setSSLSocketFactory(sslContext.getSocketFactory());
			tlsParams.setDisableCNCheck(true);			
			Client c = ClientProxy.getClient(convergentChargingExPort);
			HTTPConduit conduit = (HTTPConduit) c.getConduit();
			conduit.setTlsClientParameters(tlsParams);
		}
		return convergentChargingExPort;
	}
	
	public String runChargeEventAndGetResponse(String SID,String uid,String GUID,String serviceCategory,String quantity,String serviceCode,
			String startTime,String productLinecode) throws Exception {
		/*getChargeEvent("201408271437519", "201408271437519", "SCVal000001", 0.1, "SimWS Multi", "2014-09-11");*/
		return getChargeEvent(SID,uid,GUID,serviceCategory, quantity,serviceCode,startTime,productLinecode);
		
	}

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
/*	public static void main(String[] args) throws Exception{
		new convergentChargEventRequestSOAPService().runChargeEvent();
	}*/
	
	public XMLGregorianCalendar getXMLGregorianValueForDate(String dateTime) throws Exception {
		Date dob=null;
		/*DateFormat df=new SimpleDateFormat("dd/MM/yyyy");*/
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		dob=df.parse(dateTime);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dob);
		XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
		/*System.out.println(xmlDate.toString());*/
		return xmlDate;	
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
