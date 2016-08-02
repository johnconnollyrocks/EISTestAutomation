package bornincloud;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

/**
 * Test class - Test_REST_GetEntitlement_Negative_NonExistingUser
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_BUUC_SOAP_ConvergentCharging_MultiYearNegative extends BornInCloudTestBase{
	//String contract = "tflexmultiyear11";
	String contract=testProperties.getConstant("CONTRACT_DEV");
	
	public Test_BUUC_SOAP_ConvergentCharging_MultiYearNegative() throws IOException {
		super("Backend","Browser",getAppBrowser());		
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_BUUC_SOAP_ConvergentCharging_MultiYearNegative_Method() throws Exception {	
		SoapUIExampleTest suet = new SoapUIExampleTest();
				
		String Request=testProperties.getConstant("REQUEST");
		String StrRequsetXML=Request.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+contract+"</ContractNumber>");		
		System.out.println("Request XML:"+StrRequsetXML);
		
		File file = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		String response = suet.getResponseForSoapRequest("webservice","querySubscriptionBalance", file.getName(), StrRequsetXML,"http://uspetddccdata01:8080/quattro/services/ConvergentCharging",getEnvironment());
		//write to an xml file and pull the info using xml config
		File responsexmlFile= new File(System.getProperty("user.dir")+"\\"+contract+".response.xml");
		FileUtils.writeStringToFile(responsexmlFile, response,"UTF-8");
		//*******************
		XMLConfiguration xmlresponseFile= new XMLConfiguration(responsexmlFile);
		//read the xml file
		String respHeader="soap:Body.ns2:QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges.ServicePrivilege(2).ListOfUnits";
		HierarchicalConfiguration myConfig= xmlresponseFile.configurationAt(respHeader);
				
		
		String available1=myConfig.getString("Unit(1).Value");
		int available = Integer.parseInt(available1);
		String consumed1=myConfig.getString("Unit(2).Value");
		int consumed = Integer.parseInt(consumed1);
		
		String multiYearBucket=myConfig.getString("Unit(9).Type");
		String availableMultiYear1=myConfig.getString("Unit(9).Value");
		int availableMultiYear = Integer.parseInt(availableMultiYear1);
	
		System.out.println(available);
		System.out.println(availableMultiYear);
		System.out.println(multiYearBucket);
		
		//soap call to update consumption*****
		String ChargeEventRequest=testProperties.getConstant("REQUEST_UPDATE_CC");
		String StrRequsetXMLCC=ChargeEventRequest.replace("<UID>?</UID>", "<UID>"+contract+"</UID>");		
		System.out.println("Request XML:"+StrRequsetXMLCC);

		File fileCC = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		String responseCC = suet.getResponseForSoapRequest("webservice","ChargeEvent", fileCC.getName(), StrRequsetXMLCC,"http://uspetdeccprx001:8080/quattroCloud/services/ConvergentCharging",getEnvironment());
		
		// write to an xml file and pull the info using xml config
		
		File responsexmlFileCC= new File(System.getProperty("user.dir")+"\\"+contract+".response.xml");
		
		FileUtils.writeStringToFile(responsexmlFileCC, responseCC,"UTF-8");
		//*******************
		XMLConfiguration xmlresponseFileCC= new XMLConfiguration(responsexmlFileCC);
		
		//read the xml file
		String respHeaderCC="soap:Body.ns2:ChargeEventResponse";
		HierarchicalConfiguration myConfigCC= xmlresponseFileCC.configurationAt(respHeaderCC);
		String responseMsg=myConfigCC.getString("ResponseMsg");
			
		if(responseMsg.equals("Charge successfully applied")){
			Util.printInfo("charge event fired");
		}else{
			Util.printInfo("charge event not fired successfully");
		}
		
					
		//soap call to validate token consumption
		
		String RequestAfter=testProperties.getConstant("REQUEST");
		//String RequestAfter="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://www.autodesk.com/schemas/Business/ConvergentChargingExV1.0\">   <soapenv:Header/>   <soapenv:Body>      <con:QuerySubscriptionBalanceRequest>         <ListOfContracts>         	<Contract>         		<ContractNumber>?</ContractNumber>            </Contract>         </ListOfContracts>      </con:QuerySubscriptionBalanceRequest>   </soapenv:Body></soapenv:Envelope>";
		String StrRequsetXMLAfter=RequestAfter.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+contract+"</ContractNumber>");		
		System.out.println("Request XML:"+StrRequsetXMLAfter);
		
		File file1 = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		String response1 = suet.getResponseForSoapRequest("webservice","querySubscriptionBalance", file1.getName(), StrRequsetXML,"http://uspetddccdata01:8080/quattro/services/ConvergentCharging",getEnvironment());
		//write to an xml file and pull the info using xml config
		File responsexmlFile1= new File(System.getProperty("user.dir")+"\\"+contract+".response.xml");
		FileUtils.writeStringToFile(responsexmlFile1, response1,"UTF-8");
		//*******************
		XMLConfiguration xmlresponseFile1= new XMLConfiguration(responsexmlFile1);
		
		//read the xml file
		
		String respHeader1="soap:Body.ns2:QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges.ServicePrivilege(2).ListOfUnits";
		HierarchicalConfiguration myConfig1= xmlresponseFile1.configurationAt(respHeader1);
				
		
		String availableAfter1=myConfig1.getString("Unit(1).Value");
		int availableAfter = Integer.parseInt(availableAfter1);
		String consumedAfter1=myConfig1.getString("Unit(2).Value");
		int consumedAfter = Integer.parseInt(consumedAfter1);
		String availableMutliYrAfter1=myConfig1.getString("Unit(9).Value");
		int availableMutliYrAfter = Integer.parseInt(availableMutliYrAfter1);
				
		System.out.println(availableAfter);
		System.out.println(availableMutliYrAfter);
		
		
		
	if(availableAfter==0){
		
		//assertTrue("Consumed should increase", consumedAfter>consumed);
		assertTrue("Available multiyear should decrease", availableMutliYrAfter<availableMultiYear);

		assertTrue("Multiyear available can be -ve", availableMutliYrAfter1.contains("-"));
				
		}else{
			
			EISTestBase.fail("credits available for current year:Change Test data");
		}
	}
	
}


