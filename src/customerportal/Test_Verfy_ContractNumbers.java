package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verfy_ContractNumbers extends CustomerPortalTestBase{

	public Test_Verfy_ContractNumbers() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verfy_ContractNumbers() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		Util.sleep(5000);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		
		
		String USERNAME = null;
		String EMAIL = null;
		String USER_NAME = null;
		String PASSWORD = null;
		String INSTANCE_NAME = null;
		String SCEmailId=null;
		String contractNumbr="";
		String Contracts=null;
		
		if(getEnvironment().equalsIgnoreCase("DEV")){
			 USERNAME = testProperties.getConstant("USERNAME_DEV");
			 EMAIL = testProperties.getConstant("EMAIL_DEV");
			 USER_NAME = testProperties.getConstant("USER_NAME_DEV");
			 PASSWORD = testProperties.getConstant("PASSWORD_DEV");
			 INSTANCE_NAME = "NEW_USER_DEV";
			
			}
			else if(getEnvironment().equalsIgnoreCase("STG")){
				 USERNAME = testProperties.getConstant("USER_NAME_STG");
//				 EMAIL = testProperties.getConstant("EMAIL_STG");
				// EMAIL = "automation_cm_769593@ssttest.net";
				 EMAIL="automation_cm_238693@ssttest.net";
				 USER_NAME = testProperties.getConstant("c");
				 PASSWORD = testProperties.getConstant("PASSWORD_STG");
				 INSTANCE_NAME = "NEW_USER_STG";
			}
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.click("users");
		Util.sleep(2000);
		homePage.click("editAccessPage");
		
		List<WebElement> NumberofContractsUnderCM=driver.findElements(By.xpath(".//*[contains(@id,'contract')]/div[2]/div[1]/div/div//span[2]"));
		String ContractsEditAccess="";
		String[] temp=null;
        for(WebElement Contract: NumberofContractsUnderCM){
                Contracts=Contract.getText();
                temp=Contracts.split("#");
                ContractsEditAccess=ContractsEditAccess+temp[1];                
        } 
        
		// Triggering Web service call to get GUID (input:email ID)
		String GUIDRequest= testProperties.getConstant("REQUEST1");		
		String ActualRequest=suet.ReplaceRequest(GUIDRequest,EMAIL);
		
		File file=new File(System.getProperty("user.dir")+"\\build\\UserService.wsdl");
		
		String response = suet.getResponseForSoapRequest("webservice","GetUserByEmail", file.getName(), ActualRequest,"https://stageservices-usscl.autodesk.com/UserService",getEnvironment());
	
		Document doc = domParser.getDocument(response);
		String GUID=null;
		NodeList Nodes = doc.getElementsByTagName("ns0:User");
		Util.printInfo(Nodes.getLength() +" : Nodes.getlength()");
		for(int s=0;s<Nodes.getLength() ; s++ ){
			Node GUIDNode= domParser.getSubNode("ns0:GUID", Nodes.item(s));
			GUID=GUIDNode.getTextContent();
			Util.printInfo(GUIDNode.getTextContent());
		}
		
		Util.printInfo("GUID : "+GUID);
	
	// triggering web service call to get the list of contract numbers(input:GUID)
		
		String Request=testProperties.getConstant("REQUEST2");
		String StrRequsetXML=Request.replace("<par:GUID>?</par:GUID>", "<par:GUID>"+GUID+"</par:GUID>");
		File file1=new File(System.getProperty("user.dir")+"\\build\\PartyService.wsdl");
		
		String GetContactEntitlementsResponse=suet.getSoapRequestForChargeEvent("GetContactEntitlements", "GetContactEntitlements", file1.getName(), StrRequsetXML, "https://stageservices-usscl.autodesk.com/PartyService", getEnvironment());
		Document doc1 = domParser.getDocument(GetContactEntitlementsResponse);
		Util.printInfo("GetContactEntitlementsResponse:" +GetContactEntitlementsResponse);
		
		NodeList ContactEntitlements= doc1.getElementsByTagName("pfx:Contract");
		Util.printInfo("pfx:Contract.getLength(): "+ContactEntitlements.getLength());
		String CCcontracts="";
		Node contractNode ;
		Node ContractStatus ;
		
			for(int s=0;s<ContactEntitlements.getLength() ; s++ ){
			ContractStatus = domParser.getSubNode("ns2:ContractStatus", ContactEntitlements.item(s) );
			String ActiveStatus=ContractStatus.getTextContent();
			contractNode = domParser.getSubNode("ns2:ContractNumber",ContactEntitlements.item(s));
			if( ContractStatus.getTextContent().equalsIgnoreCase("Active")){
			CCcontracts=CCcontracts+contractNode.getTextContent();
			}
		 }
			
			assertTrue("Contract number",ContractsEditAccess.equals(CCcontracts.trim()));
		logoutMyAutodeskPortal();
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