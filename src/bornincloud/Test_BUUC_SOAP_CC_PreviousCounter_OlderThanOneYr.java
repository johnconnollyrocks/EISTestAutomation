
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

import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

/**
 * Test class - Test_REST_GetEntitlement_Negative_NonExistingUser
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_BUUC_SOAP_CC_PreviousCounter_OlderThanOneYr extends BornInCloudTestBase{
	String response="";
	String contract="";
	
	String ccResponse="";
	String RequestAfter="";
	String responseAfter="";
	String Request="";
	 
	
	public Test_BUUC_SOAP_CC_PreviousCounter_OlderThanOneYr() throws IOException {
		super("Backend","Browser",getAppBrowser());		
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_BUUC_SOAP_CC_PreviousCounter_OlderThanOneYr_Method() throws Exception {	
 SoapUIExampleTest suet = new SoapUIExampleTest();
		
		//available cloud credits
			
		Request = testProperties.getConstant("REQUEST");
		
		if(getEnvironment().equalsIgnoreCase("DEV")){
			contract = testProperties.getConstant("CONTRACT_DEV");
		}else if(getEnvironment().equalsIgnoreCase("STG")){
			contract = testProperties.getConstant("CONTRACT_STG");
		}
		
		String StrRequsetXML=Request.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+contract+"</ContractNumber>");		
		System.out.println("Request XML:"+StrRequsetXML);
		
		File file = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		if(getEnvironment().equalsIgnoreCase("DEV")){
			response = suet.getResponseForSoapRequest(contract,"querySubscriptionBalance", file.getName(), StrRequsetXML, testProperties.getConstant("URL_DEV"),getEnvironment());
		}else if(getEnvironment().equalsIgnoreCase("STG")){
			response = suet.getResponseForSoapRequest(contract,"querySubscriptionBalance", file.getName(), StrRequsetXML,testProperties.getConstant("URL_STG"),getEnvironment());
		}
		//String response = suet.getResponseForSoapRequest(contract,"querySubscriptionBalance", file.getName(), StrRequsetXML,"http://uspetdeccprx001:8080/quattroCloud/services/ConvergentCharging",getEnvironment());
		
		//write to an xml file and pull the info using xml config
		File responsexmlFile= new File(System.getProperty("user.dir")+"\\"+contract+".response.xml");
		FileUtils.writeStringToFile(responsexmlFile, response,"UTF-8");
		//*******************
		XMLConfiguration xmlresponseFile= new XMLConfiguration(responsexmlFile);
		
		//read the xml file
		String respHeader="soap:Body.ns2:QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges.ServicePrivilege(2).ListOfUnits";
		HierarchicalConfiguration myConfig= xmlresponseFile.configurationAt(respHeader);
				
		String provisioned=myConfig.getString("Unit(0).Value");
		//int provisioned = Integer.parseInt(provisioned1);
		String available1=myConfig.getString("Unit(1).Value");
		int available = Integer.parseInt(available1);
		String consumed1=myConfig.getString("Unit(2).Value");
		int consumed = Integer.parseInt(consumed1);
		String multiYearAvail1=myConfig.getString("Unit(9).Value");
		int multiYearAvail = Integer.parseInt(multiYearAvail1);
		
		System.out.println(provisioned);
		System.out.println(available);
		System.out.println(consumed);
		System.out.println(multiYearAvail);
		
		//*************************
		
		//fire charge event soap call
		String ccRequest = testProperties.getConstant("CCREQUEST");
		String StrRequsetXMLCC = ccRequest.replace("<UID>?</UID> ", "<UID>"+ contract + "</UID>");
		System.out.println("Request XML:" + StrRequsetXMLCC);
		
		File file1 = new File(System.getProperty("user.dir")+ "\\build\\ConvergentCharging.wsdl");
		
		if(getEnvironment().equalsIgnoreCase("DEV")){
			ccResponse = suet.getResponseForSoapRequest(contract,"ChargeEvent",file1.getName(),StrRequsetXMLCC,testProperties.getConstant("CCURL_DEV"),getEnvironment());
		}else if(getEnvironment().equalsIgnoreCase("STG")){
			ccResponse = suet.getResponseForSoapRequest(contract,"ChargeEvent",file1.getName(),StrRequsetXMLCC,testProperties.getConstant("CCURL_STG"),getEnvironment());
		}
		//write to an xml file and pull the info using xml config
				
		File responsexmlFileCC = new File(System.getProperty("user.dir") + "\\"	+ contract + ".response.xml");

		FileUtils.writeStringToFile(responsexmlFileCC, ccResponse, "UTF-8");
		//*******************
		XMLConfiguration xmlresponseFile1 = new XMLConfiguration(responsexmlFileCC);

		// read the xml file
		String respHeaderCC = "soap:Body.soap:Fault.detail.ns2:SOAPFault.Fault";
		HierarchicalConfiguration myConfigCC = xmlresponseFile1.configurationAt(respHeaderCC);
		String ErrorCode = myConfigCC.getString("ErrorCode");
		String ErrorMsg = myConfigCC.getString("ErrorMsg");
		
		
		assertTrue("Error Code matches", ErrorCode.equals("80"));
		assertTrue("Error Message matches", ErrorMsg.contains("ForbiddenChargeException - Not authorized - Message for NoAccess is : Cannot re-run transactions earlier than the previous year start date"));
		
		

		/*if (ErrorMsg.equals("Charge successfully applied")) {
			Util.printInfo("charge event fired");
		} else {
			Util.printInfo("charge event not fired successfully");
		}*/
		
		//***********************
					
		//soap call to validate token consumption after charge event
		String RequestAfter = testProperties.getConstant("REQUEST");
		
		String StrRequsetXMLAfter = RequestAfter.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+ contract + "</ContractNumber>");
		System.out.println("Request XML:" + StrRequsetXMLAfter);

		File file2 = new File(System.getProperty("user.dir")+ "\\build\\ConvergentCharging.wsdl");
		
		if(getEnvironment().equalsIgnoreCase("DEV")){
			responseAfter = suet.getResponseForSoapRequest(contract,"querySubscriptionBalance", file2.getName(), StrRequsetXMLAfter, testProperties.getConstant("URL_DEV"),getEnvironment());
		}else if(getEnvironment().equalsIgnoreCase("STG")){
			responseAfter = suet.getResponseForSoapRequest(contract,"querySubscriptionBalance", file2.getName(), StrRequsetXMLAfter,testProperties.getConstant("URL_STG"),getEnvironment());
		}
		
		// write to an xml file and pull the info using xml config
		File responsexmlFileAfter = new File(System.getProperty("user.dir") + "\\"+ contract + ".response.xml");
		FileUtils.writeStringToFile(responsexmlFileAfter, responseAfter, "UTF-8");
		XMLConfiguration ccxmlresponseFile = new XMLConfiguration(responsexmlFileAfter);

		// read the xml file
		String respHeaderAfter = "soap:Body.ns2:QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges.ServicePrivilege(2).ListOfUnits";
		HierarchicalConfiguration myConfig2 = ccxmlresponseFile.configurationAt(respHeaderAfter);

		
		String availableAfter1 = myConfig2.getString("Unit(1).Value");
		int availableAfter = Integer.parseInt(availableAfter1);
		String consumedAfter1 = myConfig2.getString("Unit(2).Value");
		int consumedAfter = Integer.parseInt(consumedAfter1);
		String multiYearAfter1 = myConfig2.getString("Unit(9).Value");
		int multiYearAfter = Integer.parseInt(multiYearAfter1);

		
		System.out.println(availableAfter);
		System.out.println(consumedAfter);
		
		
	/*	if(!availableAfter1.equals("0")){
			
				assertTrue("Available should be decreased", availableAfter<available);
				assertTrue("Cosnumed should increase", consumedAfter>consumed);
	}else{
		
		EISTestBase.fail("Available is 0 : Change test data");
	}*/
			
			assertEquals(availableAfter, available);
			assertEquals(consumedAfter, consumed);
			assertEquals(multiYearAfter, multiYearAvail);
			
			
		}
	
	
		

}


