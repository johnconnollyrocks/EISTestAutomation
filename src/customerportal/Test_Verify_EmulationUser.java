package customerportal;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verify_EmulationUser extends CustomerPortalTestBase {
	SoapUIExampleTest suet=null;
	DOMXmlParser domParser=null;
	public Test_Verify_EmulationUser() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_OnlineMap() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		suet = new SoapUIExampleTest();
		domParser = new DOMXmlParser();
		mainWindow.select();
		ArrayList<String> ContractNumbers=new ArrayList<String>();
		ArrayList<String> EmulatorCOntracts=new ArrayList<String>();
		Util.sleep(2000);
		
		Util.printInfo("Logged in as end user " +testProperties.getConstant("USER_NAME"));
		Util.printInfo("clicking on contract and orders page ");
		
		homePage.click("ContractsAndOrders");
		
		java.util.List<WebElement> ListOfContracts=homePage.getMultipleWebElementsfromField("ContractList");
		
		Util.printInfo("The Total number of contracts pressent in end user account's "+testProperties.getConstant("USER_NAME")+" contracts and orders page is / are :: " +ListOfContracts.size());
		
		for(WebElement Contract : ListOfContracts){
			ContractNumbers.add(Contract.getText());
		}
		Util.printInfo("The total contracts pressent is / are :: " +ContractNumbers.toString());
		
		Util.printInfo("logging out from customer portal as end user :: "+testProperties.getConstant("USER_NAME"));
		
		homePage.click("logout");
		
		Util.printInfo("logging into customer portal as Admin :: "+testProperties.getConstant("USER_NAME"));
		String FirstName=testProperties.getConstant("FIRSTNAME");
		String LastName=testProperties.getConstant("LASTNAME");		
		String GUID = GetUserGUID();	
		loginAsMyAutodeskPortalUserToEmulate(FirstName,LastName,testProperties.getConstant("ADMIN") , testProperties.getConstant("ADMINPASSWORD"),GUID);
		
		if(homePage.isFieldVisible("Emulating")){
			Util.printInfo(homePage.getValueFromGUI("Emulating"));
			assertTrue("Logged into emulators Admin account successfully ", homePage.isFieldVisible("Emulating"));
			Util.printInfo("Logged in as emulating user " +testProperties.getConstant("ADMIN"));
			homePage.verifyFieldExists("Emulating");
			Util.printInfo("clicking on contract and orders page ");
			
			homePage.click("ContractsAndOrders");
			java.util.List<WebElement> ListOfEmulatedContracts=homePage.getMultipleWebElementsfromField("ContractList");
			
			Util.printInfo("The Total number of contracts pressent in Emulator account's "+testProperties.getConstant("ADMIN")+" contracts and orders page is / are :: " +ListOfContracts.size());
			
			for(WebElement Contracts : ListOfEmulatedContracts){
				EmulatorCOntracts.add(Contracts.getText());
			}
			Util.printInfo("The total contracts pressent is / are :: " +EmulatorCOntracts.toString());
			
			assertTrue("Emulated account "+testProperties.getConstant("ADMIN")+" details should exactly match without emulation account "+testProperties.getConstant("USER_NAME")+"", ContractNumbers.toString().equals(EmulatorCOntracts.toString()));
			
			assertTrue("Emulated account number of contracts should match without emulation account ",EmulatorCOntracts.size()==ContractNumbers.size());
		}else{
			EISTestBase.fail("Emulators account "+testProperties.getConstant("ADMIN")+" not loaded successfuly or it is not an emulator account, please try again ");
		}
		
		logoutMyAutodeskPortal();
		
		
}
	
	
		//Function To get user specific GUID
		public String GetUserGUID(){
		
		String UserEmail=testProperties.getConstant("USER_NAME");
		String GUIDRequest= testProperties.getConstant("REQUEST");
		String ActualRequest=suet.ReplaceRequest(GUIDRequest,UserEmail);
		File file = new File(System.getProperty("user.dir")+"\\UserService.wsdl");
		
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
		return GUID;
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
