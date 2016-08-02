package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verify_Products_And_Services extends CustomerPortalTestBase{

	public Test_Verify_Products_And_Services() throws IOException {
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
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		String SerialNumberandProductKey1="";
		ArrayList<String> str=new ArrayList<String>();
		ArrayList<String> strFromWeb=new ArrayList<String>();
		
		List<WebElement> totalSerialNumberandProductKey=driver.findElements(By.xpath("//article[1]//div[3]/div[2]/div/div/section[1]/table/tbody/tr/td[2]"));
		
			if(homePage.isFieldVisible("contractNums")){
				homePage.click("toggleDrawer");	
				if(homePage.isFieldVisible("version")){
					for(WebElement SerialNumber : totalSerialNumberandProductKey){
						String EachSerialNumber=SerialNumber.getText();
							String[] temp=EachSerialNumber.split("/");
							SerialNumberandProductKey1=temp[0].trim();
							str.add(EachSerialNumber);
						}
					}
			}else{
				EISTestBase.fail("No products available for this :: ");
			
		}

		
		// Triggering Web service call to get GUID (input:email ID)
					String GUIDRequest= testProperties.getConstant("REQUEST1");	
					String EMAIL=testProperties.getConstant("USER_NAME_STG");
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
	// triggering web service call to get the list of contract numbers(input:GUID)
		
		String Request=testProperties.getConstant("REQUEST2");
		String StrRequsetXML=Request.replace("<par:GUID>?</par:GUID>", "<par:GUID>"+GUID+"</par:GUID>");
		Util.printInfo("StrRequsetXML ****************************=" + StrRequsetXML);
		File file1=new File(System.getProperty("user.dir")+"\\build\\PartyService.wsdl");
		
		String GetContactEntitlementsResponse=suet.getSoapRequestForChargeEvent("GetContactEntitlements", "GetContactEntitlements", file1.getName(), StrRequsetXML, "https://stageservices-usscl.autodesk.com/PartyService", getEnvironment());
		Document doc1 = domParser.getDocument(GetContactEntitlementsResponse);
		Util.printInfo("GetContactEntitlementsResponse*************************:" +GetContactEntitlementsResponse);
		
		NodeList ContactEntitlements= doc1.getElementsByTagName("ns2:Asset");
		Util.printInfo("pfx:Contract.getLength(): "+ContactEntitlements.getLength());
		Node Release;
		String SerialANDProdKey ="";
		String AppendString1="";
			for(int s=0;s<ContactEntitlements.getLength() ; s++ ){
				Node SerialNumber= domParser.getSubNode("ns:SerialNumber",ContactEntitlements.item(s));
				System.out.println(SerialNumber.getTextContent());
				Node AssetProduct= domParser.getSubNode("ns:AssetProduct",ContactEntitlements.item(s));
				Node ProductKey= domParser.getSubNode("ns1:ProductKey",AssetProduct);
				System.out.println(ProductKey.getTextContent());
				Release=domParser.getSubNode("ns1:Release", AssetProduct);
				System.out.println(Release.getTextContent());
				
				if(Release.getTextContent().equalsIgnoreCase("2014") || Release.getTextContent().equalsIgnoreCase("2015")  && !Release.getTextContent().equalsIgnoreCase("CLOUD")&& SerialNumberandProductKey1.equals(SerialNumber.getTextContent())){
					SerialANDProdKey=" "+SerialNumber.getTextContent().trim()+" / "+ProductKey.getTextContent().trim();
					
					strFromWeb.add(SerialANDProdKey.trim());
				}
			}	
					if(strFromWeb.equals(str)){
						assertTrue("The SerialNumberandProductKey of the version, from Customer portal UI and webservice are same", strFromWeb.equals(str));
						
					}else{
						EISTestBase.fail("The SerialNumberandProductKey of the version, from Customer portal UI and webservice are not same");
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