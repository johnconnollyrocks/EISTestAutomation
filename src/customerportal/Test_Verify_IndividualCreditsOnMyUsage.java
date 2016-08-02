package customerportal;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verify_IndividualCreditsOnMyUsage extends CustomerPortalTestBase{

	public String USERNAME = null;
	public String PASSWORD = null;
	SoapUIExampleTest suet = new SoapUIExampleTest();
	DOMXmlParser domParser = new DOMXmlParser();
	String ActICCConsumed =null;

	public Test_Verify_IndividualCreditsOnMyUsage() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());		
	}
	@Test
	public void Test_Verify_IndividualCloudCredits_MyUsage() throws Exception {
		if (getEnvironment().equalsIgnoreCase("dev")) {
			USERNAME = testProperties.getConstant("USERNAME_DEV");
			PASSWORD = testProperties.getConstant("PASSWORD_DEV");
		} else if (getEnvironment().equalsIgnoreCase("stg")) {
			USERNAME = testProperties.getConstant("USER_NAME_STG");
			PASSWORD = testProperties.getConstant("PASSWORD_STG");
		}
		loginAsMyAutodeskPortalUser(USERNAME , PASSWORD);
		
		Util.sleep(3000);
		homePage.click("reporting");
		Util.sleep(2000);
		
		 ActICCConsumed=productUpdatePage.getValueFromGUI("iccCredits");
		 Util.printInfo("Actuall cloud credits consumed----------->" + ActICCConsumed );
		
		// Triggering Web service call to get GUID (input:email ID)
				String GUIDRequest= testProperties.getConstant("REQUEST1");		
				String ActualRequest = suet.ReplaceRequest(GUIDRequest,USERNAME);
								
				File file=new File(System.getProperty("user.dir")+"\\build\\UserService.wsdl");
				String response = null;
				if (getEnvironment().equalsIgnoreCase("dev")) {
				
				 response = suet.getResponseForSoapRequest("webservice","GetUserByEmail", file.getName(), ActualRequest,"https://devservices.autodesk.com/dm/UserService",getEnvironment());
				}
				else{
				 response = suet.getResponseForSoapRequest("webservice","GetUserByEmail", file.getName(), ActualRequest,"https://stageservices-usscl.autodesk.com/UserService",getEnvironment());
				}
				Document docGUID = domParser.getDocument(response);
				String GUID=null;
				NodeList Nodes = docGUID.getElementsByTagName("ns0:User");
				Util.printInfo(Nodes.getLength() +" : Nodes.getlength()");
				for(int s=0;s<Nodes.getLength() ; s++ ){
					Node GUIDNode= domParser.getSubNode("ns0:GUID", Nodes.item(s));
					GUID=GUIDNode.getTextContent();
					Util.printInfo(GUIDNode.getTextContent());
				}
				
				Util.printInfo("GUID : "+GUID);

				//trigger the web service call to get the ICC from web service
				
				String Request=testProperties.getConstant("REQUEST2");
				String StrRequsetXML=Request.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+GUID+"</ContractNumber>");
		//		Util.printInfo("StrRequsetXML**************************" + StrRequsetXML);
				
				File file1=new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
				String querySubscriptionBalanceResponse = null;
				if (getEnvironment().equalsIgnoreCase("dev")) {
					querySubscriptionBalanceResponse=suet.getResponseForSoapRequest("querySubscriptionBalance", "querySubscriptionBalance", file1.getName(), StrRequsetXML, "http://uspetdeccprx001:8080/quattroCloud/services/ConvergentCharging", getEnvironment());	
				}
				else{
					 querySubscriptionBalanceResponse=suet.getResponseForSoapRequest("querySubscriptionBalance", "querySubscriptionBalance", file1.getName(), StrRequsetXML, "https://quattro-stg-vip.autodesk.com/quattroCloud/services/ConvergentCharging", getEnvironment());
				}
				
		//		Util.printInfo("querySubscriptionBalanceResponse:" +querySubscriptionBalanceResponse);
				
		//		NodeList ServicePrivilege = doc.getElementsByTagName("ServicePrivilege");
				Document doc = domParser.getDocument(querySubscriptionBalanceResponse);
				NodeList contracts = doc.getElementsByTagName("Contract");
				Util.printInfo("contracts.getLength()--->" + contracts.getLength());
				
				Node contractNode = domParser.getParentNodeFromSetOfNodeList("ContractNumber", contracts);
		//		Util.printInfo("contractNode.getNodeName()-->" + contractNode.getNodeName());
				
				Node ListOfServicePrivileges = domParser.getSubNode("ListOfServicePrivileges", contractNode);
				
				Node ServicePrivilege = domParser.getParentNodeFromSetOfNodeList("Description","Cloud Credits", ListOfServicePrivileges.getChildNodes());
				
				Node ListOfUnits = domParser.getSubNode("ListOfUnits", ServicePrivilege);
				
				Node provisionedUnit = domParser.getParentNodeFromSetOfNodeList("Type","provisioned", ListOfUnits.getChildNodes());
				Node availableUnit = domParser.getParentNodeFromSetOfNodeList("Type","available", ListOfUnits.getChildNodes());
				Node consumedUnit = domParser.getParentNodeFromSetOfNodeList("Type","consumed", ListOfUnits.getChildNodes());
				
				String provisioned = domParser.getNodeValue(domParser.getSubNode("Value", provisionedUnit));
				String available = domParser.getNodeValue(domParser.getSubNode("Value", availableUnit));
				String consumedICC = domParser.getNodeValue(domParser.getSubNode("Value", consumedUnit));
				Util.printInfo("provisioned : "+provisioned);
				Util.printInfo("available : "+available);
				Util.printInfo("consumed : "+consumedICC);
				Util.printInfo("Verifying the individual cloud credits on reporting page against the web service cal!!!!!!!!!!!!!");
				assertEquals(ActICCConsumed, String.valueOf(consumedICC));
				logoutMyAutodeskPortal();

	}
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}

