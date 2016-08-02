package customerportal;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;





import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_ProductVersions extends CustomerPortalTestBase {
	public Test_ProductVersions() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_OnlineMap() throws Exception {
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(60000);
		ArrayList<String> str=new ArrayList<String>();
		ArrayList<String> str1=new ArrayList<String>();
		String VersionNumbers="";
		String VersionNumbers1="";
		Util.printInfo("Getting all products under CM :: ");
		List<WebElement> Products=driver.findElements(By.xpath(".//*[contains(@id,'BDSADV')]/div[2]/div[1]/div/div/div/span/span"));
		for(WebElement EachProduct : Products ){
			String ContractNumber=EachProduct.getText();
			Util.printInfo("The number of version tool tips pressent for product is / are :: "+Products.size());
			WebElement ProductVersion=driver.findElement(By.xpath(".//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[1]/div/div/div/div[1]/p/span[1]"));
			Point coordinates = ProductVersion.getLocation();
			Util.sleep(6000);
			
			//***********************************************************************
			Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities(); 
			String browserName = capabilities.getBrowserName(); 
			if(!browserName.equalsIgnoreCase("safari")){ 
			 
			 
			Robot robot = new Robot(); 
			robot.mouseMove(coordinates.getX(), coordinates.getY() + 65); 
			Util.sleep(6000); 
			}else{ 
			try{   
			 
			String GetXpath1 ="document.getElementByXPath = function(sValue){ var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0) { return a.snapshotItem(0); } };"; //document.getElementsByXPath = function(sValue){ var aResult = new Array();var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);for ( var i = 0 ; i < a.snapshotLength ; i++ ){aResult.push(a.snapshotItem(i));} return aResult;};  
			 
			String jsClick1 = "var elem = document.getElementByXPath(\".//*[contains(@id,'"+ContractNumber+ "')]/div[2]/div[1]/div/div/div/div[1]/p/span[1]\");"+ "if( document.createEvent) {"+ "var evObj = document.createEvent('MouseEvents');"+ "evObj.initEvent( 'mouseover', true, false );"+"elem.dispatchEvent(evObj);"+ "} else if( document.createEventObject ) {"+ "elem.fireEvent('onmouseover');"+ "}"; 
			 
			JavascriptExecutor js = (JavascriptExecutor) driver;  
			System.out.println(jsClick1); 
			js.executeScript(GetXpath1);  
			Thread.sleep(2000);  
			js.executeScript(jsClick1); 
			Thread.sleep(5000);  
			 
			    
			} catch (Exception e){  
			   e.printStackTrace();  
			   System.out.println("Xpath not found "+ContractNumber);     
			    
			   } 
			}
			
			
			//***********************************************************************
			Util.sleep(6000);
			String ToolTipVersions=homePage.createFieldWithParsedFieldLocatorsTokens("ToolTipVersion", ContractNumber);
			if(homePage.isFieldVisible(ToolTipVersions)){
			int count =0;
			String ProductVersions=homePage.getValueFromGUI(ToolTipVersions).trim();
			String[] temp=ProductVersions.split(" ");
			String ProductVersions1=temp[0]+temp[1];
			str.add(ProductVersions1);
			Util.printInfo("The two versions of the product from customer portal UI under "+ContractNumber+" are :: "+str);
			Util.printInfo(str.get(count));
			}else{
				EISTestBase.fail("Tool tip doesnot exists for product versions :: ");
			}
			
		}
			// Triggering Web service call to get GUID (input:email ID)
			String GUIDRequest= testProperties.getConstant("REQUEST1");	
			String EMAIL=testProperties.getConstant("USER_NAME");
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
			
//			String GUID="201402191717307";
			String Request=testProperties.getConstant("REQUEST2");
			String StrRequsetXML=Request.replace("<par:GUID>?</par:GUID>", "<par:GUID>"+GUID+"</par:GUID>");
			File file1=new File(System.getProperty("user.dir")+"\\build\\PartyService.wsdl");
			
			String GetContactEntitlementsResponse=suet.getSoapRequestForChargeEvent("GetContactEntitlements", "GetContactEntitlements", file1.getName(), StrRequsetXML, "https://stageservices-usscl.autodesk.com/PartyService", getEnvironment());
			Document doc1 = domParser.getDocument(GetContactEntitlementsResponse);
			
			NodeList ContactEntitlements= doc1.getElementsByTagName("ns2:ListOfAsset");
			Util.printInfo("pfx:Contract.getLength(): "+ContactEntitlements.getLength());
			NodeList ContactEntitlements1= doc1.getElementsByTagName("ns2:Asset");
			String CCcontracts="";
			Node AssetProduct;
			Node AssetProduct1;
			Node ListOfAsset;
			Node Release;
			Node Release1;
			Node Asset;
			Node SerialNumber1;
			Node SerialNumber;
			String AppendString="";
			String AppendString1="";
			
			/*for(int s=0;s<ContactEntitlements.getLength() ; s++ ){
				ListOfAsset = domParser.getSubNode("ns2:ListOfAsset",ContactEntitlements.item(s));
				Asset=domParser.getSubNode("ns2:Asset", ContactEntitlements.item(s));
				SerialNumber=domParser.getSubNode("ns:SerialNumber", Asset);
				System.out.println(SerialNumber.getTextContent());
				AssetProduct=domParser.getSubNode("ns:AssetProduct", Asset);
				Release=domParser.getSubNode("ns1:Release", AssetProduct);
				if(!Release.getTextContent().equalsIgnoreCase("CLOUD")){
				System.out.println(Release.getTextContent());
				}
			}*/
			
			for(int s=0;s<ContactEntitlements1.getLength() ; s++ ){
				SerialNumber1=domParser.getSubNode("ns:SerialNumber", ContactEntitlements1.item(s));
				AssetProduct1=domParser.getSubNode("ns:AssetProduct", ContactEntitlements1.item(s));
				Release1=domParser.getSubNode("ns1:Release", AssetProduct1);
				if(!Release1.getTextContent().equalsIgnoreCase("CLOUD")){
				Util.printInfo("The version of the product from Webservice for "+SerialNumber1.getTextContent()+ " is :: "+Release1.getTextContent());
				AppendString=AppendString+Release1.getTextContent()+",";
			 }
			}
			AppendString1=AppendString.substring(0,AppendString.length()-1);
			str1.add(AppendString1);
			
			if(str.equals(str1)){
				assertTrue("The versions of the product from Customer portal UI and webservice are same", str.equals(str1));
			}else{
				EISTestBase.fail("The versions of the product from Customer portal UI and webservice are not same");
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
