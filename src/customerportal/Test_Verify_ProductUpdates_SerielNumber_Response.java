package customerportal;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import common.DOMXmlParser;
import common.DatabaseHandler;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verify_ProductUpdates_SerielNumber_Response extends CustomerPortalTestBase {
	public Test_Verify_ProductUpdates_SerielNumber_Response() throws IOException {
		super("Browser",getAppBrowser());
	//	super();
		
	}

	@Before
	public void setUp() throws Exception {
 	//	launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void ProductUpdates_SerielNumber_Response() throws Exception {
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		
		String SerialNumber=null;
		String ProductLine=null;
		String ContractNumber=null;
		String ContractType=null;
		String Release=null;
		String ProductUsage=null;
		String ErrorMessage=null;
		Connection con;
		Statement stmt;
		String ResultData="";
		
		Util.printInfo("triggering the request");
		String Request=testProperties.getConstant("REQUEST1");
		
		File file1=new File(System.getProperty("user.dir")+"\\build\\GetAgreementBySerialNumber.wsdl");
		System.out.println(file1.getName());
		
		String GetAgreementBySerialNumber=suet.getResponseForSoapRequest("webservice", "GetAgreementBySerialNumber", file1.getName(), Request, "https://devservices.autodesk.com/dm/AssetService", getEnvironment());
		
		Document doc1 = domParser.getDocument(GetAgreementBySerialNumber);
		
		NodeList GetAgreementBySerialNumberLst= doc1.getElementsByTagName("pfx2:ProductLineCode");
		Util.printInfo("pfx:Contract.getLength(): "+GetAgreementBySerialNumberLst.getLength());
		
		NodeList Nodes = doc1.getElementsByTagName("pfx2:SerialNumberDetails");
		String Serialtemp="";
		String SerialNumbersList=null;
		String ResultTemp=null;
		String ProdTemp="";
		ArrayList<String> arr1=new ArrayList<String>();
		
		for (int s = 0; s < Nodes.getLength(); s++) {
			boolean flag=true;
			Node SerialNumberNode = domParser.getSubNode("pfx2:SerialNumber", Nodes.item(s));
			Node ProductLineCode  =domParser.getSubNode("pfx2:ProductLineCode", Nodes.item(s));
			Node ContractTypeNode  =domParser.getSubNode("pfx2:ListOfContract", Nodes.item(s));
			
			SerialNumber = SerialNumberNode.getTextContent();
			ProductLine=ProductLineCode.getTextContent();
			Util.sleep(3000);
			if(domParser.getSubNode("pfx2:ListOfContract", Nodes.item(s)).hasChildNodes()){
				flag=false;
			Node ListOfServicePrivileges = domParser.getSubNode("pfx2:Contract", ContractTypeNode);
			Node ContractNumberNode = domParser.getSubNode("ns:ContractNumber", ListOfServicePrivileges);
			Node EachContractTypeNode = domParser.getSubNode("ns:ContractType", ListOfServicePrivileges);
			ContractType = EachContractTypeNode.getTextContent();
			ContractNumber	= ContractNumberNode.getTextContent();
			}
			
			Util.printInfo("Serial Number :: " +SerialNumber);
			Util.printInfo("Product Line of the Serial Number :: "+ProductLine);
			
			if(flag){
				ContractNumber=" ";
				Util.printInfo("ContractNumber for the Serial Number ::"+SerialNumber+" :: is not pressent");
				Util.printInfo("ContractType for the Serial Number ::"+SerialNumber+" :: is not pressent");
			}else{
				Util.printInfo("ContractNumber for the Serial Number ::"+ContractNumber);
				Util.printInfo("ContractType for the Serial Number ::"+ContractType);
			}
			arr1.add(ProductLine);
			Serialtemp="'"+SerialNumber+"'"+","+Serialtemp;
			SerialNumbersList = Serialtemp.substring(0, Serialtemp.length()-1);
			ProdTemp=ProductLine+";"+ProductLine;
		}
		
		Util.printInfo("Validating Product lines of the serial numbers against DataMart DB :: ");
		
		DatabaseHandler dbob = new DatabaseHandler();
		con=dbob.getDataMartDbConnectionDEV();
		stmt=con.createStatement();
		String Result=null;
		ResultSet Rquery=dbob.ExecuteDMartQuery("select * from CONTRACT_DETAILS where SERIAL_NUM In ("+SerialNumbersList+")");
		int size=0;
		while(Rquery.next())
		{
			Result=Rquery.getString(3);
			size++;
				if(arr1.contains(Result)){
					assertTrue("The product line from DataMart DB and Product line from Multiple serial number request for rover Webservice is same :: ",arr1.contains(Result));
				}
		}
		
		Util.printInfo("The size of the Data from WebService is :: "+arr1.size());
		Util.printInfo("The size of the Data from DMart Data Base is :: "+size);
		
		if(!(size<arr1.size())){
		assertEquals("The Number of products from Webservice and DataMart Data Base are equal ",String.valueOf(arr1.size()), String.valueOf(size));
		}else{
		
		Util.printInfo("Validating the same set of data against sieble DataBase:: ");
		con=dbob.getSiebleDbConnectionDEV();
		stmt=con.createStatement();
		String Query=testProperties.getConstant("SiebelQuery");
		String ActualQuery=Query.replace("<?>", "In ("+SerialNumbersList+")");
		ResultSet NewQuery=dbob.ExecuteSiebelDBQuery(ActualQuery);
		int count=0;
		String SiebleResult=null;
		while(NewQuery.next())
		{
			SiebleResult=NewQuery.getString(1);
			count++;
		}
		
		if(!(count<arr1.size())){
			assertEquals("The Number of products from Webservice and Sieble Data Base are equal ",String.valueOf(arr1.size()), String.valueOf(count));
		}
		
		DatabaseHandler.closeConnection();
	}
		String Request1=testProperties.getConstant("REQUEST2");
		
		File file11=new File(System.getProperty("user.dir")+"\\build\\GetAgreementBySerialNumber.wsdl");
		System.out.println(file11.getName());
		
		String GetAgreementBySerialNumber1=suet.getResponseForSoapRequest("webservice", "GetAgreementBySerialNumber", file11.getName(), Request1, "https://devservices.autodesk.com/dm/AssetService", getEnvironment());
		
		Document doc11 = domParser.getDocument(GetAgreementBySerialNumber1);
		
		NodeList Nodes1= doc11.getElementsByTagName("SOAP-ENV:Fault");
		
		for (int s = 0; s < Nodes1.getLength(); s++) {
		boolean flag=true;
		Node ErrorMessageNode = domParser.getSubNode("faultcode", Nodes1.item(s));
//		Node FaultNode = domParser.getSubNode("ns:ErrorMsg", ErrorMessageNode);
		ErrorMessage	= ErrorMessageNode.getTextContent();
		if(ErrorMessage.trim().equalsIgnoreCase("SOAP-ENV:Server")){
			assertTrue("Got Error Message Due to Exceed of 10 Serial Numbers :: ",ErrorMessage.trim().equalsIgnoreCase("SOAP-ENV:Server"));
		}else{
			EISTestBase.fail("Error Message is not displayed even after giving more than 10 serial numbers :: ");
		}
	  }
		
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