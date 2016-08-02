package customerportal;

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

/**
 * Test class - Test_VerifyStorageSpaceAvailability
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyContractReport_Webservice extends
		CustomerPortalTestBase {
	public Test_VerifyContractReport_Webservice() throws IOException {
		super("Browser", getAppBrowser());
		// super();

	}

	@Before
	public void setUp() throws Exception {
		// launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyContractReport_Webservice_method() throws Exception {

	
		SoapUIExampleTest soapUIExampleTest = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		String contractNumber = testProperties.getConstant("ContractNumber");
				
		String response = soapUIExampleTest.getResponseForSoapRequest("webservice","querySubscriptionBalance","ConvergentCharging.wsdl", testProperties.getConstant("REQUEST"),"http://uspetdeccprx001:8080/quattroCloud/services/ConvergentCharging",getEnvironment());
	//	Util.printInfo("response : " + response);

		Document doc = domParser.getDocument(response);

		NodeList contracts = doc.getElementsByTagName("Contract");
		
		Node contractNode = domParser.getParentNodeFromSetOfNodeList("ContractNumber", testProperties.getConstant("ContractNumber"),contracts);
		Node ListOfServicePrivileges = domParser.getSubNode("ListOfServicePrivileges", contractNode);
		Node ServicePrivilege = domParser.getParentNodeFromSetOfNodeList("Description","Cloud Credits", ListOfServicePrivileges.getChildNodes());
		
		Node ListOfUnits = domParser.getSubNode("ListOfUnits", ServicePrivilege);
		
		Node provisionedUnit = domParser.getParentNodeFromSetOfNodeList("Type","provisioned", ListOfUnits.getChildNodes());
		Node availableUnit = domParser.getParentNodeFromSetOfNodeList("Type","available", ListOfUnits.getChildNodes());
		Node consumedUnit = domParser.getParentNodeFromSetOfNodeList("Type","consumed", ListOfUnits.getChildNodes());
		
		String provisioned = domParser.getNodeValue(domParser.getSubNode("Value", provisionedUnit));
		String available = domParser.getNodeValue(domParser.getSubNode("Value", availableUnit));
		String consumed = domParser.getNodeValue(domParser.getSubNode("Value", consumedUnit));
		
		Util.printInfo("provisioned : "+provisioned);
		Util.printInfo("available : "+available);
		Util.printInfo("consumed : "+consumed);
		
		
		
//		assertEquals(contractNumber, checkContractNumber);
//		assertEquals("0", consumedValue);
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
