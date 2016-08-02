package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_verify_ContractReport_DEV extends CustomerPortalTestBase {
	public Test_verify_ContractReport_DEV() throws IOException {
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
		Util.sleep(60000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		Util.sleep(2000);
		TreeMap<String, String> responseMap = new TreeMap<String,String>();
		TreeMap<String, String> ContractsAndOrdersMap = new TreeMap<String,String>();
		String nextContract=null;
		String nextContractTerm=null;
		String serialNum = null;
		String serialNum1= null;
		Node serialNumbNode;
		Node serialNumbNode1;
		String nextSerialNumber=null;
		String nextProductName=null;
		Node Node1,Node2,Node3,Node4;
		Node AssetProductNode;
		
		Util.printInfo("******** Triggered GCE Webservice call ********");
		//Triggered web service call to get GUID			
		String GUIDRequest= testProperties.getConstant("REQUEST1");		
		String ActualRequest=suet.ReplaceRequest(GUIDRequest,testProperties.getConstant("USER_NAME"));
		
		File file=new File(System.getProperty("user.dir")+"\\UserService.wsdl");
		
		String response = suet.getResponseForSoapRequest("webservice","GetUserByEmail", file.getName(), ActualRequest,"https://devservices.autodesk.com/dm/UserService",getEnvironment());
	
		Document doc = domParser.getDocument(response);
		String GUID=null;
		NodeList Nodes = doc.getElementsByTagName("ns0:User");
		Util.printInfo(Nodes.getLength() +" : Nodes.getlength()");
		for(int s=0;s<Nodes.getLength() ; s++ ){
			Node GUIDNode= domParser.getSubNode("ns0:GUID", Nodes.item(s));
			GUID=GUIDNode.getTextContent();
			//Util.printInfo(GUIDNode.getTextContent());
		}
		
		Util.printInfo("GUID of user "+testProperties.getConstant("USER_NAME")+" is : "+GUID);
		
		//Triggered GCE Webservice call to get each contract details
		
		String Request=testProperties.getConstant("REQUEST2");
		String StrRequsetXML=Request.replace("<par:GUID>?</par:GUID>", "<par:GUID>"+GUID+"</par:GUID>");
		File file1=new File(System.getProperty("user.dir")+"\\PartyService.wsdl");
		
		//String GetContactEntitlementsResponse=suet.getSoapRequestForChargeEvent("GetContactEntitlements", "GetContactEntitlements", file1.getName(), StrRequsetXML, "https://stageservices-usscl.autodesk.com/PartyService", getEnvironment());
		String GetContactEntitlementsResponse=suet.getSoapRequestForChargeEvent("GetContactEntitlements", "GetContactEntitlements", file1.getName(), StrRequsetXML, "https://devservices.autodesk.com/dm/PartyService", getEnvironment());
		Document doc1 = domParser.getDocument(GetContactEntitlementsResponse);
		Util.printInfo("GetContactEntitlementsResponse:" +GetContactEntitlementsResponse);
		
		NodeList ContactEntitlements= doc1.getElementsByTagName("pfx:Contract");
		
		NodeList ContactEntitlements1= doc1.getElementsByTagName("ns2:Asset");
		Util.printInfo("pfx:Contract.getLength(): "+ContactEntitlements.getLength());
		
		if(ContactEntitlements.getLength()==0){
			EISTestBase.fail("There is an error in Party Service Response or party service is down, please check after sometime ");
		}
		String CCcontracts="";
		Node contractNode ;
		Node ContractStatus ;
		Node ContractTerm1;
		Node SerialNumber;
		Node AssetProduct1;
		Node Product;
		Node AssetProduct;
		Node ProductKey;
		String nextProductKey;
		
		for(int j=0;j<ContactEntitlements.getLength();j++){
			ContractStatus = domParser.getSubNode("ns2:ContractNumber", ContactEntitlements.item(j) );
			String ContractNumber=ContractStatus.getTextContent();				
			ContractTerm1 = domParser.getSubNode("ns2:RecurringTerm", ContactEntitlements.item(j) );
			String Term=ContractTerm1.getTextContent();
			serialNumbNode = domParser.getSubNode("ns2:ListOfContractItem",ContactEntitlements.item(j));
			Node1 = domParser.getSubNode("ns2:ContractItem",serialNumbNode);
			Node2 = domParser.getSubNode("ns2:ListOfAsset",Node1);
			Node3 = domParser.getSubNode("ns2:Asset",Node2);
			Node4 = domParser.getSubNode("ns:SerialNumber",Node3);
//			serialNumbNode1= serialNumbNode.getFirstChild().getFirstChild().getNextSibling().getNextSibling().getFirstChild().getFirstChild().getNextSibling();
			serialNum1 = Node4.getTextContent();
//			ns:SerialNumber
//			for(int ss = 0;ss<serialNumbNode1.getLength();ss++ ){
//				if (serialNumbNode1.item(ss).equals("ns2:ListOfAsset")){
//					serialNum1 = serialNumbNode1.getTextContent();
//					serialNum1=serialNumbNode1.item(ss).getTextContent();
//					break;
//				}
//				
//			}
						
			AssetProduct=domParser.getSubNode("ns:AssetProduct", Node4);
			AssetProductNode=domParser.getSubNode("ns:AssetProduct", Node3);
			AssetProduct = domParser.getSubNode("ns1:ProductLine", AssetProductNode);				
			String Product1=AssetProduct.getTextContent();
			
			ProductKey=domParser.getSubNode("ns1:ProductKey", AssetProductNode);
			String ProductKeys=ProductKey.getTextContent();
			if ((responseMap.containsKey("ContractNo"))) {
			nextContract = responseMap.get("ContractNo").concat(";"+ContractNumber);
			responseMap.put("ContractNo", nextContract);
			nextContractTerm = responseMap.get("ContractTerm").concat(";"+Term);
			responseMap.put("ContractTerm", nextContractTerm);
			if (responseMap.containsKey("SerialNumber") && !(responseMap.get("SerialNumber").isEmpty())) {
				nextSerialNumber = responseMap.get("SerialNumber").concat(";"+serialNum1);
				responseMap.put("SerialNumber", nextSerialNumber);
			}
			
			nextProductName = responseMap.get("ProductName").concat(";"+Product1);
			responseMap.put("ProductName", nextProductName);
			nextProductKey = responseMap.get("ProductKey").concat(";"+ProductKeys);
			responseMap.put("ProductKey", nextProductKey);

			} else if (!(responseMap.containsKey("ContractNo"))) {
				responseMap.put("ContractNo", ContractNumber);
				responseMap.put("ContractTerm", Term);
				responseMap.put("SerialNumber", serialNum1);
				responseMap.put("ProductName", Product1);
				responseMap.put("ProductKey", ProductKeys);
				}
		break;
		}
		String[] ContractNumber=null;
		String[] SerialNum=null;
		String[] ProductK=null;
		String[] ProductN=null;
		String[] ContractTerm=null;
		int i=0;
		Util.printInfo("************************ Verifying Customer Portal contract report page functionality *********************");
		homePage.click("ContractsAndOrders");
		for(Map.Entry<String, String> entry : responseMap.entrySet()){
			String value=entry.getValue();
			System.out.println(value);
			if(entry.getKey().equalsIgnoreCase("ContractNo")){
				ContractNumber=value.split(";");
			}else if(entry.getKey().equalsIgnoreCase("ContractTerm")){
				ContractTerm=value.split(";");
			}else if(entry.getKey().equalsIgnoreCase("ProductKey")){
				ProductK=value.split(";");
			}else if(entry.getKey().equalsIgnoreCase("ProductName")){
				ProductN=value.split(";");
			}else {
				SerialNum=value.split(";");
			}
		}
		
		Util.sleep(4000);
		
		if(homePage.isFieldVisible("ContractsHeader")){
			assertTrue("Contract Report page loaded successfully ", homePage.isFieldVisible("ContractsHeader"));
		}else{
			EISTestBase.fail("Contract Report Page not loaded successfully try after sometime");
		}
		
		assertTrue("Term Header should exists in contract Report page ",homePage.isFieldVisible("TermHeaderInContractAndOrdersPage"));
		assertTrue("Renew/End Date Header should exists in contract Report page ",homePage.isFieldVisible("RenewHeaderOrEndDateInContractAndOrdersPage"));
		assertTrue("Product Header should exists in contract Report page ",homePage.isFieldVisible("ProductHeaderOrEndDateInContractAndOrdersPage"));
		
		Util.printInfo(" ************************ verifying total number of contracts pressent in contracts and orders page ********************************** ");
		
		List<WebElement> ListOfContracts=homePage.getMultipleWebElementsfromField("ContractList");
		
		Util.printInfo("The Total number of contracts pressent in contracts and orders page is / are :: " +ListOfContracts.size());
		
		for(String ContractNum : ContractNumber){
			Util.printInfo("The contract from GCE is :: "+ContractNum);
			String ContractT=homePage.createFieldWithParsedFieldLocatorsTokens("ContractTerm", ContractNum.trim());
			String ContractTermValue=homePage.getValueFromGUI(ContractT);
			Util.printInfo("The Term for contract "+ContractNum+ " is :: "+ContractTermValue);
			String RenewEndDate=homePage.createFieldWithParsedFieldLocatorsTokens("RenewOrEndDateStatus", ContractNum.trim());
			Util.printInfo("The Renew / End Date status of contract "+ContractNum+ " is :: " +homePage.getValueFromGUI(RenewEndDate));
			
			Util.printInfo("************* Clicking on each contract ****************");
			
			Util.printInfo("Clicking on contract "+ContractNum.trim());
			
			//ContractsAndOrdersMap.put("CPContract", EachContract.getText());
			
			//EachContract.click();
			
			String CPContract=homePage.createFieldWithParsedFieldLocatorsTokens("Contract", ContractNum.trim());
			
			if(homePage.isFieldVisible(CPContract)){
				homePage.click(CPContract);
			}else{
				EISTestBase.fail("The contract which we got from GCE call doesnot exist in customer portal UI ");
			}
			
			
			Util.sleep(40000);
			
			/*String DetailsOfSubscription=homePage.createFieldWithParsedFieldLocatorsTokens("DetailsOfSubscription", ContractNum.trim());
			
			Util.sleep(20000);
			
			if(homePage.isFieldVisible(DetailsOfSubscription)){
				homePage.verifyFieldExists(DetailsOfSubscription);
			}else{
				EISTestBase.fail("Deatails of Subscription doesnot exists for contract :: "+ContractNum);
			}*/
			
			homePage.verifyFieldExists("SupportLevel");
			
			Util.printInfo("The Support Level value of contract "+ContractNum+" is :: "+homePage.getValueFromGUI("SupportLevelValue"));
			
			homePage.verifyFieldExists("GetCloudCredits");
			
			homePage.verifyFieldExists("ProductsAndServices");
			
			homePage.verifyFieldExists("ContractsAndOrdersExportButton");
			
			Util.printInfo("Clicking on contract and orders Export Button");
			
			Util.sleep(4000);
			
			if(homePage.isFieldVisible("ContractReportProducts")){
				
				
			Util.printInfo("The Contract Term from customer portal UI for contract :: "+ContractNum+ " is :: "+ ContractTermValue.trim());
			
			Util.printInfo("The Contract Term from GCE web service call for contract :: "+ContractNum+ " is :: "+ ContractTerm[i].trim());
			
			assertTrue("The customer portal UI Contract term and GCE webservice call contract term are same for contract "+ContractNum+" :: ",ContractTermValue.trim().equals(ContractTerm[i].trim()));
			
			String ProductName=homePage.getValueFromGUI("ContractReportProducts");
			
			Util.printInfo("The product name from customer portal UI for contract :: "+ContractNum+ " is :: "+ ProductName);
			
			Util.printInfo("The product name from GCE web service call for contract :: "+ContractNum+ " is :: "+ ProductN[i]);
			
			String ContractReportSerialNumber=homePage.getValueFromGUI("SerialNuberOrProductKey");
			String[] SerialAndProductKey=ContractReportSerialNumber.split("/");
			
			Util.printInfo("The serial number from customer portal UI for contract :: "+ContractNum+ "is :: "+ SerialAndProductKey[0]);
			
			Util.printInfo("Verifying Customer portal UI Serial number of contract :: "+ContractNum+" and GCE call serial number of contract "+SerialAndProductKey[0]);
			
			Util.printInfo("The serial number from GCE web service call for contract :: "+ContractNum+ " is :: "+ SerialNum[i]);
			
			assertTrue("The customer portal UI serial number and GCE webservice call serial number are same for contract "+ContractNum+" :: ",SerialAndProductKey[0].trim().equals(SerialNum[i].trim()));
			
			Util.printInfo("The product key from customer portal UI for contract :: "+ContractNum+ " is :: "+ SerialAndProductKey[1]);
			
			Util.printInfo("The product key from GCE web service call for contract :: "+ContractNum+ " is :: "+ ProductK[i]);
			
			assertTrue("The customer portal UI product key and GCE webservice call product key are same for contract "+ContractNum+" :: ",SerialAndProductKey[1].trim().equals(ProductK[i].trim()));
			i++;	
			Util.sleep(4000);
			
			}else{
			i++;
				EISTestBase.fail("There are no products exists for the contract "+ContractNum+" trying for another contract");
			}
			
			/*ContractsAndOrdersMap.put("CPProductName", ProductName);
			
			ContractsAndOrdersMap.put("CPContractTerm", ContractTermValue);
			
			ContractsAndOrdersMap.put("CPContractSerialNumber", ContractReportSerialNumber);*/
			
			/*homePage.click("ContractsAndOrdersExportButton");
			Util.sleep(40000);
			
			Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\build\\Export.exe "+ContractNum);
			Util.sleep(50000);
			
			final String downloadFilePath=System.getenv("USERPROFILE")+"\\Downloads\\Contract_"+ContractNum+".csv";
			Util.sleep(2000);
			boolean downloadComplete=false;
			int numTimes=0;
			File partDownloadfile = new File(downloadFilePath);
			
			if(partDownloadfile.exists()){
				assertTrue("The file has downloaded successfully after clicking on Export link on contracts and orders page ", partDownloadfile.exists());
				Util.printInfo("The File name and File path is :: "+partDownloadfile);
				if(partDownloadfile.exists()){
					Util.sleep(5000);
					Util.printInfo("Deleting the existing .csv file ");
					partDownloadfile.delete();
				}
			}else{
				EISTestBase.fail("File Not Downloaded successfully after clicking on Export link in Contracts and Orders Page");
			}*/
			
			//homePage.click("BackButton");
			break;
		}
		
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
