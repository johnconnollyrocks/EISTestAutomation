package bornincloud;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

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
 * 
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_BUUC_REST_ConvergentCharging_Complex_Product_Mapping extends BornInCloudTestBase{
	
	//String contract = testProperties.getConstant("CONTRACT_DEV");
	String response="";
	String Request="";
	DefaultHttpClient client=null;
	String responseAfter="";
	String response1="";
	
	public Test_BUUC_REST_ConvergentCharging_Complex_Product_Mapping() throws IOException {
		super("Backend","Browser",getAppBrowser());		
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_BUUC_REST_ConvergentCharging_Complex_Product_Mapping_Method() throws Exception {	
		SoapUIExampleTest suet = new SoapUIExampleTest();
		String newDate = randomFutureDate();
		// Parsing test data values from test data file and creating a test data Hash
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
				
		
		Request="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://www.autodesk.com/schemas/Business/ConvergentChargingV1.0\">   <soapenv:Header/>   <soapenv:Body>      <con:QuerySubscriptionBalanceRequest>         <ListOfContracts>         	<Contract>         		<ContractNumber>?</ContractNumber>            </Contract>         </ListOfContracts>      </con:QuerySubscriptionBalanceRequest>   </soapenv:Body></soapenv:Envelope>";
				
		String StrRequsetXML=Request.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+testDataMap.get("contract")+"</ContractNumber>");		
		System.out.println("Request XML:"+StrRequsetXML);
		
		File file = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		
		response = suet.getResponseForSoapRequest(testDataMap.get("contract"),"querySubscriptionBalance", file.getName(), StrRequsetXML, testDataMap.get("ccurl"),getEnvironment());
				
		//write to an xml file and pull the info using xml config
		File responsexmlFile= new File(System.getProperty("user.dir")+"\\"+testDataMap.get("contract")+".response.xml");
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
		
		System.out.println(available);
		System.out.println(consumed);
		
		//REST call to update usage		
		
			
		String transactionId="Testburnrate"+getUniqueString(9);
					
		//to get the start time
		String contentAsString=testDataMap.get("content");
		//split using ":"
		String[] contentArr=contentAsString.split(",");
		StringBuffer newContent= new StringBuffer();
		
		for(int i=0;i<contentArr.length;i++){
			
			if(contentArr[i].contains("contractNumber")){
				contentArr[i]="\"contractNumber\""+":"+"\""+testDataMap.get("contract")+"\"";				
			}
			if(contentArr[i].contains("productCode")){
				contentArr[i]="\"productCode\""+":"+"\""+"INVNTOR"+"\"";				
			}
			if(contentArr[i].contains("startTime")){
					contentArr[i]="\"startTime\""+":"+"\""+newDate+" 13:00:00"+"\"";				
			}
			if(contentArr[i].contains("stopTime")){
				contentArr[i]="\"stopTime\""+":"+"\""+newDate+" 14:59:00"+"\""+"}";				
			}
			 
			newContent.append(contentArr[i]+",");
		}
		
				 
		String str = newContent.toString();
		System.out.println("Finally::::"+str);
		
		
		// Making a Put Rest call and retrieving the Json response in a string
		DefaultHttpClient client = getClientNew(testDataMap.get("mutualAuthCertBuuc"), testDataMap.get("mutualAuthPassBuuc"));
		
		HttpResponse putresponse = updateEntitlement(client, transactionId, testDataMap.get("buucRequest"), str, testDataMap.get("jsonMimetype"));
		
		
		// Verifying the Status Code 
	    System.out.println("Assert : Verify the Status Code" + "\n");
	    compareStrings(putresponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	    
	    // Verifying the Response Format
	    System.out.println("Assert : Verify the Repsonse Format" + "\n");
	    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(putresponse.getEntity()).getMimeType());
	    
	    String actualjsondataupdate = readJsonFromResponse(putresponse);	
	  		
		// Verifying the Json Response from Rest Get Service 
		System.out.println("Assert : Verify the actual Json Repsonse" + "\n");
		compareStrings(testDataMap.get("expectedjsondata"), actualjsondataupdate);	
		
		//*****************************
		
		//soap call to verify token consumption
		
		String StrRequsetXMLAfter=Request.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+testDataMap.get("contract")+"</ContractNumber>");		
		System.out.println("Request XML:"+StrRequsetXMLAfter);
		
		File file1 = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		
		response1 = suet.getResponseForSoapRequest(testDataMap.get("contract"),"querySubscriptionBalance", file1.getName(), StrRequsetXML, testDataMap.get("ccurl"),getEnvironment());
				
		//write to an xml file and pull the info using xml config
		File responsexmlFile1= new File(System.getProperty("user.dir")+"\\"+testDataMap.get("contract")+".response.xml");
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
		
		System.out.println(availableAfter);
		System.out.println(consumedAfter);
		
		
		if(!availableAfter1.equals("0")){
			
			assertTrue("available should decrease", availableAfter<available);
			assertTrue("consumed should increase", consumedAfter>consumed);
					
		}else{
				
				EISTestBase.fail("Available is 0");
			}
		
		//REST call to update usage for other product in same product mapping 
		
		testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		testDataMap = parseTestData(testdata, tname.getMethodName());
		
		transactionId="Testburnrate"+getUniqueString(9);
					
		//to get the start time
		contentAsString=testDataMap.get("content");
		//split using ":"
		contentArr=contentAsString.split(",");
		newContent= new StringBuffer();
		
		for(int i=0;i<contentArr.length;i++){
			if(contentArr[i].contains("contractNumber")){
				contentArr[i]="\"contractNumber\""+":"+"\""+testDataMap.get("contract")+"\"";				
			}
			if(contentArr[i].contains("productCode")){
				contentArr[i]="\"productCode\""+":"+"\""+"INVPROSA"+"\"";				
			}
			if(contentArr[i].contains("startTime")){
				contentArr[i]="\"startTime\""+":"+"\""+newDate+" 15:00:00"+"\"";				
			}
			if(contentArr[i].contains("stopTime")){
				contentArr[i]="\"stopTime\""+":"+"\""+newDate+" 16:59:00"+"\""+"}";				
			}
			newContent.append(contentArr[i]+",");
		}
		str = newContent.toString();
		System.out.println("Finally::::"+str);
		
		
		HttpResponse putresponseProduct2 = updateEntitlement(client, transactionId, testDataMap.get("buucRequest"), str, testDataMap.get("jsonMimetype"));
		
		
		// Verifying the Status Code 
	    System.out.println("Assert : Verify the Status Code" + "\n");
	    compareStrings(putresponseProduct2.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	    
	    // Verifying the Response Format
	    System.out.println("Assert : Verify the Repsonse Format" + "\n");
	    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(putresponse.getEntity()).getMimeType());
	    
	    actualjsondataupdate = readJsonFromResponse(putresponseProduct2);	
	  		
		// Verifying the Json Response from Rest Get Service 
		System.out.println("Assert : Verify the actual Json Repsonse" + "\n");
		compareStrings(testDataMap.get("expectedjsondata"), actualjsondataupdate);
			
		
		//SOAP call to verify credits
		
		StrRequsetXMLAfter=Request.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+testDataMap.get("contract")+"</ContractNumber>");		
		System.out.println("Request XML:"+StrRequsetXMLAfter);
		
		file1 = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		
		responseAfter = suet.getResponseForSoapRequest(testDataMap.get("contract"),"querySubscriptionBalance", file1.getName(), StrRequsetXMLAfter, testDataMap.get("ccurl"),getEnvironment());
				
		//write to an xml file and pull the info using xml config
		responsexmlFile1= new File(System.getProperty("user.dir")+"\\"+testDataMap.get("contract")+".response.xml");
		FileUtils.writeStringToFile(responsexmlFile1, responseAfter,"UTF-8");
		//*******************
		xmlresponseFile1= new XMLConfiguration(responsexmlFile1);
		//read the xml file
		respHeader1="soap:Body.ns2:QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges.ServicePrivilege(2).ListOfUnits";
		myConfig1= xmlresponseFile1.configurationAt(respHeader1);
				
		 availableAfter1=myConfig1.getString("Unit(1).Value");
		 int availableNew = Integer.parseInt(availableAfter1);
		 consumedAfter1=myConfig1.getString("Unit(2).Value");
		 int consumedNew = Integer.parseInt(consumedAfter1);
		
		System.out.println(availableNew);
		System.out.println(consumedNew);
		
		
		if(!availableAfter1.equals("0")){
			
			assertTrue("available should decrease by 3 credits", availableNew==availableAfter-4);
			assertTrue("consumed should increase by 3 credits", consumedNew==consumedAfter+4);
					
		}else{
				
				EISTestBase.fail("Available is 0 : change testdata");
			}
		
		//Rest call to update usage
		
		testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		testDataMap = parseTestData(testdata, tname.getMethodName());
		
		transactionId="Testburnrate"+getUniqueString(9);
					
		//to get the start time
		contentAsString=testDataMap.get("content");
		//split using ":"
		contentArr=contentAsString.split(",");
		newContent= new StringBuffer();
		
		for(int i=0;i<contentArr.length;i++){
			if(contentArr[i].contains("contractNumber")){
				contentArr[i]="\"contractNumber\""+":"+"\""+testDataMap.get("contract")+"\"";				
			}
			if(contentArr[i].contains("productCode")){
				contentArr[i]="\"productCode\""+":"+"\""+"INVPROSA"+"\"";				
			}
			if(contentArr[i].contains("startTime")){
				contentArr[i]="\"startTime\""+":"+"\""+newDate+" 17:00:00"+"\"";				
			}
			if(contentArr[i].contains("stopTime")){
				contentArr[i]="\"stopTime\""+":"+"\""+newDate+" 19:59:00"+"\""+"}";				
			}
			newContent.append(contentArr[i]+",");
		}
		str = newContent.toString();
		System.out.println("Finally::::"+str);
				
		HttpResponse putresponseFinal = updateEntitlement(client, transactionId, testDataMap.get("buucRequest"), str, testDataMap.get("jsonMimetype"));
		
		
		// Verifying the Status Code 
	    System.out.println("Assert : Verify the Status Code" + "\n");
	    compareStrings(putresponseFinal.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	    
	    // Verifying the Response Format
	    System.out.println("Assert : Verify the Repsonse Format" + "\n");
	    compareStrings(testDataMap.get("jsonMimetype"), ContentType.getOrDefault(putresponse.getEntity()).getMimeType());
	    
	    actualjsondataupdate = readJsonFromResponse(putresponseFinal);	
	  		
		// Verifying the Json Response from Rest Get Service 
		System.out.println("Assert : Verify the actual Json Repsonse" + "\n");
		compareStrings(testDataMap.get("expectedjsondata"), actualjsondataupdate);
		client.getConnectionManager().shutdown();
		
		
	//Query balance call	
		
		
		String StrRequsetXMLFinal=Request.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+testDataMap.get("contract")+"</ContractNumber>");		
		System.out.println("Request XML:"+StrRequsetXMLAfter);
		
		File file2 = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		
		response1 = suet.getResponseForSoapRequest(testDataMap.get("contract"),"querySubscriptionBalance", file2.getName(), StrRequsetXMLFinal, testDataMap.get("ccurl"),getEnvironment());
				
		//write to an xml file and pull the info using xml config
		File responsexmlFile2= new File(System.getProperty("user.dir")+"\\"+testDataMap.get("contract")+".response.xml");
		FileUtils.writeStringToFile(responsexmlFile1, response1,"UTF-8");
		//*******************
		XMLConfiguration xmlresponseFile2= new XMLConfiguration(responsexmlFile2);
		//read the xml file
		String respHeader2="soap:Body.ns2:QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges.ServicePrivilege(2).ListOfUnits";
		HierarchicalConfiguration myConfig2= xmlresponseFile2.configurationAt(respHeader2);
				
		String availableFinal1=myConfig2.getString("Unit(1).Value");
		int availableFinal = Integer.parseInt(availableFinal1);
		String consumedFinal1=myConfig2.getString("Unit(2).Value");
		int consumedFinal = Integer.parseInt(consumedFinal1);
		
		System.out.println(availableFinal);
		System.out.println(consumedFinal);
		
		
		if(!availableFinal1.equals("0")){
			
			assertTrue("available should remain same", availableFinal==availableNew);
			assertTrue("consumed should remain same", consumedFinal==consumedNew);
					
		}else{
				
				EISTestBase.fail("Available is 0");
			}
		
		
		
		
		
		
}
		
		

}


