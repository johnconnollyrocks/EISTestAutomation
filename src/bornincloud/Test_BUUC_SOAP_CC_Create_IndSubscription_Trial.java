package bornincloud;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.autodesk.schemas.business.convergentchargingexv1.QuerySubscriptionBalanceResponse;
import com.autodesk.schemas.business.convergentchargingexv1.ServicePrivilege;
import com.autodesk.schemas.business.convergentchargingexv1.ServicePrivilege.ListOfUnits;
import com.autodesk.schemas.business.convergentchargingexv1.Unit;

import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;
import customerportal.getConvergentChargeSOAPService;

/**
 * Test class - Test_REST_GetEntitlement_Negative_NonExistingUser
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_BUUC_SOAP_CC_Create_IndSubscription_Trial extends BornInCloudTestBase{
	String response="";
	String contract="";
	String Request="";
	String RequestQSB="";
	DefaultHttpClient client;
	String responseAfter="";
	String sessionID="";
 	String userID="";
	
	
	public Test_BUUC_SOAP_CC_Create_IndSubscription_Trial() throws IOException {
		//super("Backend","Browser",getAppBrowser());	
		super("browser");
		//super("browser");
	}
	
	@Before
	public void setUp() throws Exception {
		/*Map<String, String> userDetails= new TreeMap<String, String>();
		userDetails=createCustomerPortalUserID();
		
		
		sessionID=userDetails.get("sessionID");
		userID=userDetails.get("userID");*/
		
	}
	
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_BUUC_SOAP_CC_Create_IndSubscription_Trial_Method() throws Exception {	
		SoapUIExampleTest suet = new SoapUIExampleTest();
		String newDate = randomFutureDate();
		SimpleDateFormat sFormat= new SimpleDateFormat("yyyy-MM-dd");
		Date date= new Date();
		String ContractStartDate = sFormat.format(date);
		// Parsing test data values from test data file and creating a test data Hash
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		String orderNum="order"+getUniqueString(9);
				
		Request="<soapenv:Envelope xmlns:soapenv=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\" xmlns:con=\\\"http://www.autodesk.com/schemas/Business/ConvergentChargingV1.0\\\">   <soapenv:Header/>   <soapenv:Body>      <con:ProvisionCreditsRequest>        <Contract>            <ContractEndDate>3333-12-31</ContractEndDate>            <ContractStartDate>2014-09-26</ContractStartDate>            <OrderNumber>201401100015-test4441</OrderNumber>            <PONumber>SUBSCRIPTIONUSER</PONumber>            <ContractOfferingType>[A360] Individual Offer</ContractOfferingType>            <IsNewContract>true</IsNewContract>            <IsRenewContract>false</IsRenewContract>            <ContractUsageType>Any</ContractUsageType>            <OxygenId>6Z44JQLU5AR5</OxygenId>         </Contract>         <EndUserAccount/>         <ListOfProducts>            <Product>               <ProductContractStartDate>2014-09-26</ProductContractStartDate>               <ProductContractEndDate>3333-12-31</ProductContractEndDate>               <ProductLineCode>SUBSCRIPTIONUSER</ProductLineCode>               <ProductUsage>Any</ProductUsage>               <Quantity>1</Quantity>               <SubscriptionLevel>User</SubscriptionLevel>            </Product>         </ListOfProducts>      </con:ProvisionCreditsRequest>   </soapenv:Body></soapenv:Envelope>";
						
		String StrRequsetXML=Request.replace("<OrderNumber>?</OrderNumber>", "<OrderNumber>"+orderNum+"</OrderNumber>");
		String StrRequsetXML1=StrRequsetXML.replace("<ContractStartDate>?</ContractStartDate>", "<ContractStartDate>"+ContractStartDate+"</ContractStartDate>");
		//String StrRequsetXML2=StrRequsetXML1.replace("<OxygenId>?</OxygenId>","<OxygenId>"+sessionID+"</OxygenId>");
		String StrRequsetXML2=StrRequsetXML1.replace("<OxygenId>?</OxygenId>","<OxygenId>46TEKU8HV6RT</OxygenId>");
		String StrRequsetXML3=StrRequsetXML2.replace("<ProductContractStartDate>?</ProductContractStartDate>", "<ProductContractStartDate>"+ContractStartDate+"</ProductContractStartDate>");
		
		
		System.out.println("Request XML:"+StrRequsetXML3);
		
		File file = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
			
		response = suet.getResponseForSoapRequest("","", file.getName(), StrRequsetXML3, testDataMap.get("ccurl"),getEnvironment());
						
		//write to an xml file and pull the info using xml config
		File responsexmlFile= new File(System.getProperty("user.dir")+"\\"+testDataMap.get("contract")+".response.xml");
		FileUtils.writeStringToFile(responsexmlFile, response,"UTF-8");
		XMLConfiguration xmlresponseFile= new XMLConfiguration(responsexmlFile);
		
		//read the xml file
		String respHeader="soap:Body.ns2:ProvisionCreditsResponse";
		HierarchicalConfiguration myConfig= xmlresponseFile.configurationAt(respHeader);
		
		String responseCode = myConfig.getString("ResponseCode");
		String responseStatus = myConfig.getString("responseStatus");
		String responseMsg = myConfig.getString("ResponseMsg");
		String temp[] = responseMsg.split(":");
		String temp1[]=temp[1].split(" ");
		String temp2[]=temp[2].split(" ");
		String Contract = temp1[0];
		String OxygenId = temp2[0];
		
		if (responseMsg.equals("Guid contract: "+ Contract +" for oxygenId: "+OxygenId+" successfully created in CC")) {
			Util.printInfo("Guid contract is created successfuly");
		} else {
			Util.printInfo("Guid contract not created successfully");
		}
		
		RequestQSB="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://www.autodesk.com/schemas/Business/ConvergentChargingV1.0\">   <soapenv:Header/>   <soapenv:Body>      <con:QuerySubscriptionBalanceRequest>         <ListOfContracts>         	<Contract>         		<ContractNumber>?</ContractNumber>            </Contract>         </ListOfContracts>      </con:QuerySubscriptionBalanceRequest>   </soapenv:Body></soapenv:Envelope>";
		
		String StrRequsetXMLQSB=RequestQSB.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+contract+"</ContractNumber>");		
		System.out.println("Request XML:"+StrRequsetXMLQSB);
		
		File file1 = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
			
		responseAfter = suet.getResponseForSoapRequest(testDataMap.get("contract"),"querySubscriptionBalance", file1.getName(), StrRequsetXMLQSB, testDataMap.get("ccurl"),getEnvironment());
						
		//write to an xml file and pull the info using xml config
		File responsexmlFileNew= new File(System.getProperty("user.dir")+"\\"+"webservice"+".response.xml");
		FileUtils.writeStringToFile(responsexmlFile, responseAfter,"UTF-8");
		XMLConfiguration xmlresponseFileNew= new XMLConfiguration(responsexmlFileNew);
		
		//read the xml file
	/*	String respHeaderNew="soap:Body.ns2:QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges.ServicePrivilege(2).ListOfUnits";
		HierarchicalConfiguration myConfigNew= xmlresponseFile.configurationAt(respHeaderNew);*/
		
		BigInteger grantedUnits;
		BigInteger initialGrantedUnits;
		
		
		getConvergentChargeSOAPService soapClientService=  new getConvergentChargeSOAPService(testDataMap.get("ccurl"));
		List<QuerySubscriptionBalanceResponse.ListOfContracts.Contract> lstContracts= soapClientService.getQuerySubscriptionBalance(Contract);
		for (int i=0;i<lstContracts.size();i++){
			List<ServicePrivilege> lsServicePrivileges= lstContracts.get(0).getListOfServicePrivileges().getServicePrivilege();
			for(ServicePrivilege eachService:  lsServicePrivileges){				

				if (eachService.getName().equalsIgnoreCase("c3")){
					//get valeus of prov and cons
					List<ListOfUnits> lsUnits = (List<ListOfUnits>) eachService.getListOfUnits();
					for(ListOfUnits eachUnit: lsUnits){
						for(int unit=0;unit<eachUnit.getUnit().size();unit++){
							if (eachUnit.getUnit().get(unit).getType().equalsIgnoreCase("granted")){
								grantedUnits=eachUnit.getUnit().get(unit).getValue();
								int igrantUnit=grantedUnits.intValue();
								System.out.println(grantedUnits);
								if(grantedUnits!=null){
									assertTrue("Granted should be 100", igrantUnit==100);
								}
							}
							else if (eachUnit.getUnit().get(unit).getType().equalsIgnoreCase("initial-granted")){
								initialGrantedUnits=eachUnit.getUnit().get(unit).getValue();
								int initGrantUnit= initialGrantedUnits.intValue();
								System.out.println(initialGrantedUnits);
								if(initialGrantedUnits!=null){
									assertTrue("Initial Grated should be 100", initGrantUnit==100);
								}
								
							}
							
						}
						
					}
				
				}
			}
		}
		 String available1=myConfig.getString("Unit(1).Value");
		int available = Integer.parseInt(available1);
		String consumed1=myConfig.getString("Unit(2).Value");
		int consumed = Integer.parseInt(consumed1);
		
		
		
		
		//*********************************************
				
		
		
		
}
}
	


