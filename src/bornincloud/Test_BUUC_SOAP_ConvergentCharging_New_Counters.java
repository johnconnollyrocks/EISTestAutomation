package bornincloud;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
 *
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_BUUC_SOAP_ConvergentCharging_New_Counters extends BornInCloudTestBase{
	String contract = "";
	String response="";
	
	public Test_BUUC_SOAP_ConvergentCharging_New_Counters() throws IOException {
		super("Backend","Browser",getAppBrowser());		
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_BUUC_SOAP_ConvergentCharging_New_Counters_Method() throws Exception {	
		SoapUIExampleTest suet = new SoapUIExampleTest();
		String Request=testProperties.getConstant("REQUEST");
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
		//write to an xml file and pull the info using xml config
		File responsexmlFile= new File(System.getProperty("user.dir")+"\\"+contract+".response.xml");
		FileUtils.writeStringToFile(responsexmlFile, response,"UTF-8");
		//*******************
		XMLConfiguration xmlresponseFile= new XMLConfiguration(responsexmlFile);
		
		//read the xml file
		String respHeader="soap:Body.ns2:QuerySubscriptionBalanceResponse.ListOfContracts.Contract.ListOfServicePrivileges.ServicePrivilege(2).ListOfUnits";
		HierarchicalConfiguration myConfig= xmlresponseFile.configurationAt(respHeader);
		
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
	 
	}
	
}


