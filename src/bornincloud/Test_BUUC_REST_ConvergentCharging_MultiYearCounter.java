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


/**
 * Test class - Test_REST_GetEntitlement_Negative_NonExistingUser
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_BUUC_REST_ConvergentCharging_MultiYearCounter extends BornInCloudTestBase{
	//String contract = "tflexmultiyear06";
	String response="";
	String contract="";
	String Request="";
	String ccResponse="";
	String RequestAfter="";
	String responseAfter="";
	
	
	public Test_BUUC_REST_ConvergentCharging_MultiYearCounter() throws IOException {
		super("Backend","Browser",getAppBrowser());		
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_BUUC_REST_ConvergentCharging_MultiYearCounter_Method() throws Exception {	
		SoapUIExampleTest suet = new SoapUIExampleTest();
		// Parsing test data values from test data file and creating a test data Hash
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
				
		String Request="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://www.autodesk.com/schemas/Business/ConvergentChargingV1.0\">   <soapenv:Header/>   <soapenv:Body>      <con:QuerySubscriptionBalanceRequest>         <ListOfContracts>         	<Contract>         		<ContractNumber>?</ContractNumber>            </Contract>         </ListOfContracts>      </con:QuerySubscriptionBalanceRequest>   </soapenv:Body></soapenv:Envelope>";
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
		String multiYearBucket=myConfig.getString("Unit(9).Type");
		String availableMultiYear1=myConfig.getString("Unit(9).Value");
		int availableMultiYear = Integer.parseInt(availableMultiYear1);
		
		System.out.println(available);
		System.out.println(availableMultiYear);
		System.out.println(multiYearBucket);
		
	//REST call to update usage
		
		String newDate = randomFutureDate();
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
			if(contentArr[i].contains("startTime")){
					contentArr[i]="\"startTime\""+":"+"\""+newDate+" 13:00:00"+"\"";				
			}
			if(contentArr[i].contains("stopTime")){
				contentArr[i]="\"stopTime\""+":"+"\""+newDate+" 15:00:00"+"\""+"}";				
			}
			 
			newContent.append(contentArr[i]+",");
		}
		
				 
		String str = newContent.toString();
		System.out.println("Finally::::"+str);
		
		
		DefaultHttpClient client = getClientNew(testDataMap.get("mutualAuthCertBuuc"), testDataMap.get("mutualAuthPassBuuc"));
		
		// Making a Put Rest call and retrieving the Json response in a string
		
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
		
		
		
		
		//soap call to validate token consumption
		
		String StrRequsetXMLAfter=Request.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+testDataMap.get("contract")+"</ContractNumber>");		
		System.out.println("Request XML:"+StrRequsetXMLAfter);
		
		File file1 = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		
		responseAfter = suet.getResponseForSoapRequest(testDataMap.get("contract"),"querySubscriptionBalance", file1.getName(), StrRequsetXMLAfter, testDataMap.get("ccurl"),getEnvironment());
		//write to an xml file and pull the info using xml config
		File responsexmlFile1= new File(System.getProperty("user.dir")+"\\"+testDataMap.get("contract")+".response.xml");
		FileUtils.writeStringToFile(responsexmlFile1, responseAfter,"UTF-8");
		//*******************
		XMLConfiguration xmlresponseFile1= new XMLConfiguration(responsexmlFile1);
		
		//read the xml file
		String respHeader1="soap:Body.ns2:QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges.ServicePrivilege(2).ListOfUnits";
		HierarchicalConfiguration myConfig1= xmlresponseFile1.configurationAt(respHeader1);
		
		  assertEquals(myConfig.getString("Unit(0).Type"),"provisioned");
		  assertEquals(myConfig.getString("Unit(1).Type"),"available");
		  assertEquals(myConfig.getString("Unit(2).Type"),"consumed");
		  assertEquals(myConfig.getString("Unit(3).Type"),"provisioned_year_1");
		  assertEquals(myConfig.getString("Unit(4).Type"),"provisioned_year_2");
		  assertEquals(myConfig.getString("Unit(5).Type"),"provisioned_year_3");
		  assertEquals(myConfig.getString("Unit(6).Type"),"provisioned_year_4");
		  assertEquals(myConfig.getString("Unit(7).Type"),"provisioned_year_5");
		  assertEquals(myConfig.getString("Unit(8).Type"),"provisioned_multiyear");
		  assertEquals(myConfig.getString("Unit(9).Type"),"available_multiyear");
		
		String availableAfter1=myConfig1.getString("Unit(1).Value");
		int availableAfter = Integer.parseInt(availableAfter1);
		String consumedAfter1=myConfig1.getString("Unit(2).Value");
		int consumedAfter = Integer.parseInt(consumedAfter1);
		String availableMutliYrAfter1=myConfig1.getString("Unit(9).Value");
		int availableMutliYrAfter = Integer.parseInt(availableMutliYrAfter1);
			
		System.out.println(availableAfter);
		System.out.println(consumedAfter);
		System.out.println(availableMutliYrAfter);
			
	if(availableAfter==0){
		
		assertTrue("available should remain same", availableAfter==available);
		assertTrue("Available multiyear should decrease", availableMutliYrAfter<availableMultiYear);
		//assertTrue("Multiyear available can be -ve", availableMutliYrAfter1.contains("-"));
		}else{
				EISTestBase.fail("credits available for current year: Change test data");
		}
	}
	
}


