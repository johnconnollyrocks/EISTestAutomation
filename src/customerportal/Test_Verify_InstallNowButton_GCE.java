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

/**
 * Test class - Test_VerifyInstallNowButton
 * 
 * @author Raghavendra
 * @version 1.0.0
 */
public final class Test_Verify_InstallNowButton_GCE extends CustomerPortalTestBase {
	public Test_Verify_InstallNowButton_GCE() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyInstallNowButton() throws Exception {
		String getStatus;
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		String InstallNowSerialNumber="";
		String InstallNowVersions="";
		String ProductKey="";
		String CPProductLineCode="";
		String AppendString="";
		String AppendStringUI="";
		String AppendString1="";
		String AppendStringUI1="";
		String ArrProductkey="";
		String ArrProductkey1="";
		String ArrProductkey11="";
		String CPLanguageOnInstallWindow="";
		String[] arr=null;
		ArrayList<String> str=new ArrayList<String>();
		ArrayList<String> str1=new ArrayList<String>();
		ArrayList<String> CPstr=new ArrayList<String>();
		ArrayList<String> WSstr=new ArrayList<String>();
		ArrayList<String> CPstr1=new ArrayList<String>();
		ArrayList<String> WSstr1=new ArrayList<String>();
		//verify Install Now,Download Now, Browser Download dropdown functionality with GCE calls.
		Util.printInfo("verify Install Now,Download Now, Browser Download dropdown functionality");
		homePage.clickAndWait("firstContractInstallNowButton", "languageSelectDropDown");
		if(homePage.isFieldVisible("autodeskInstallNowWindow")){
			homePage.verifyFieldExists("autodeskInstallNowWindow");
			homePage.click("ProductVersions");
		}else{
			Util.printInfo("Install now Window doesnot exists even after clicking on Instal now button :: ");
		}
		
		List<WebElement> SerialNumbers=driver.findElements(By.xpath(".//*[@id='scroll-area-serial']/div[2]/div/table/tbody/tr/td[2]"));
		for(WebElement EachSerialNumber : SerialNumbers){
			InstallNowSerialNumber=EachSerialNumber.getText();
			arr=InstallNowSerialNumber.split("/");
			ArrProductkey=ArrProductkey+arr[1];
			Util.printInfo("The Product key of the Product on Install Now window from CP UI is :: "+arr[1]);
			Util.printInfo("The Serial Number of the product on Install Now window is :: "+InstallNowSerialNumber);
		}

		List<WebElement> ProductVersions=driver.findElements(By.xpath("//tr/td[contains(text(),'"+arr[0].trim()+"')]/preceding-sibling::td[@class='version']"));
		for(WebElement EachVersion : ProductVersions){
			InstallNowVersions=EachVersion.getText();
			Util.printInfo("The version of the product / serial number on Install now window from CP UI is :: " +InstallNowVersions);
			CPProductLineCode=homePage.getValueFromGUI("ProductLineCode");
			Util.printInfo("The Product line code from customer portal on Install Now window is :: "+CPProductLineCode);
			CPLanguageOnInstallWindow=homePage.getValueFromGUI("InstallNowWindowLanguage");
			Util.printInfo("The Language of Install now window of Customer portal is :: "+CPLanguageOnInstallWindow);
			homePage.verifyFieldExists("InstallButtonOnInstallWindow");
			AppendStringUI=AppendStringUI+InstallNowVersions+",";
		}
		AppendStringUI1=AppendStringUI.substring(0,AppendStringUI.length()-1);
		str.add(AppendStringUI1);
		homePage.click("ProductVersions");
		homePage.click("CloseWindow");
		Util.sleep(4000);
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
					
//					String GUID="201402191717307";
					String Request=testProperties.getConstant("REQUEST2");
					String StrRequsetXML=Request.replace("<par:GUID>?</par:GUID>", "<par:GUID>"+GUID+"</par:GUID>");
					File file1=new File(System.getProperty("user.dir")+"\\build\\PartyService.wsdl");
					
					String GetContactEntitlementsResponse=suet.getSoapRequestForChargeEvent("GetContactEntitlements", "GetContactEntitlements", file1.getName(), StrRequsetXML, "https://stageservices-usscl.autodesk.com/PartyService", getEnvironment());
					Document doc1 = domParser.getDocument(GetContactEntitlementsResponse);
					
					NodeList ContactEntitlements= doc1.getElementsByTagName("pfx:Contact");
					Util.printInfo("pfx:Contract.getLength(): "+ContactEntitlements.getLength());
					NodeList ContactEntitlements1= doc1.getElementsByTagName("ns2:Asset");
					String CCcontracts="";
					String ProductLine="";
					Node Language=null;
					Node AssetProduct;
					Node ProductLineCode;
					Node AssetProduct1;
					Node ListOfAsset;
					Node Release;
					Node Release1;
					Node Asset;
					Node SerialNumber1;
					Node SerialNumber;
					Node ProductKey1;
					String WebServiceProductKey="";
					String WebServiceProductKey1="";
					for(int j=0;j<ContactEntitlements.getLength();j++){
						Language=domParser.getSubNode("pfx:Language", ContactEntitlements.item(j));
						Util.printInfo("Language from GCE Web Service is :: "+Language.getTextContent());
					}
					for(int s=0;s<ContactEntitlements1.getLength() ; s++ ){
						SerialNumber1=domParser.getSubNode("ns:SerialNumber", ContactEntitlements1.item(s));
						AssetProduct1=domParser.getSubNode("ns:AssetProduct", ContactEntitlements1.item(s));
						Release1=domParser.getSubNode("ns1:Release", AssetProduct1);
						if(!Release1.getTextContent().equalsIgnoreCase("CLOUD")){
						Util.printInfo("The version of the product from Webservice for "+SerialNumber1.getTextContent()+ " is :: "+Release1.getTextContent());
						ProductLineCode=domParser.getSubNode("ns1:ProductLine", AssetProduct1);
						ProductKey1=domParser.getSubNode("ns1:ProductKey", AssetProduct1);
						WebServiceProductKey=ProductKey1.getTextContent();
						WebServiceProductKey1=WebServiceProductKey1+" "+WebServiceProductKey;
						ProductLine=ProductLineCode.getTextContent();
						AppendString=AppendString+Release1.getTextContent()+",";
					 }
					}
						AppendString1=AppendString.substring(0,AppendString.length()-1);
						str1.add(AppendString1);
					if(str.equals(str1)){
						assertTrue("The versions of the product from Customer portal Install Now window and webservice are same", str.equals(str1));
					}else{
						EISTestBase.fail("The versions of the product from Customer portal Install Now window and webservice are not same");
					}if(CPProductLineCode.equalsIgnoreCase(ProductLine)){
						assertTrue("The Product line Code from Customer Portal on Install now window and From webservice is / are same ",CPProductLineCode.equalsIgnoreCase(ProductLine));
					}else{
						EISTestBase.fail("The Product line Code from Customer Portal on Install now window and From webservice is / are not same ");
					}if(ArrProductkey.trim().equalsIgnoreCase(WebServiceProductKey1.trim())){
							assertTrue("The Product key from webservice " +WebServiceProductKey1.trim()+ " and on Install Now Window of customer portal UI "+ArrProductkey.trim()+" are same :: ", ArrProductkey.trim().equalsIgnoreCase(WebServiceProductKey1.trim()));
						}else{
							EISTestBase.fail("The Product key from webservice "+WebServiceProductKey1.trim()+" and on Install Now Window of customer portal UI "+ArrProductkey.trim()+" are not same");
					}if(CPLanguageOnInstallWindow.toLowerCase().contains(Language.getTextContent())){
						assertTrue("The language from web service and customer portal Install now window are same ", CPLanguageOnInstallWindow.toLowerCase().contains(Language.getTextContent()));
					}else{
						EISTestBase.fail("The Language from Web service "+Language.getTextContent()+" and Install now window of customer portal "+CPLanguageOnInstallWindow+" are different");
					}
					
						
							
					
		Util.printInfo("verified Install Now functionality");
		
		Util.printInfo("Started verifying Download Now functionality");
		homePage.click("DownloadNowButton");
		homePage.clickAndWait("firstContractDownloadNowButton", "languageSelectDropDown");
		if(homePage.isFieldVisible("DownloadNowTextOnDownloadNowWindow")){
			homePage.verifyFieldExists("DownloadNowTextOnDownloadNowWindow");
			homePage.click("ProductVersions");
		}else{
			Util.printInfo("Download Text is not pressent on download window :: ");
		}
		
		List<WebElement> SerialNumbers1=driver.findElements(By.xpath(".//*[@id='scroll-area-serial']/div[2]/div/table/tbody/tr/td[2]"));
		for(WebElement EachSerialNumber : SerialNumbers1){
			InstallNowSerialNumber=EachSerialNumber.getText();
			arr=InstallNowSerialNumber.split("/");
			ArrProductkey1=ArrProductkey1+arr[1];
			Util.printInfo("The Product key of the Product from CP UI is :: "+arr[1]);
			Util.printInfo("The Serial Number of the product in Download now window is :: "+InstallNowSerialNumber);
		}
		String AppendWebServiceString="";
		String AppendWebServiceString1="";
		
		List<WebElement> ProductVersions1=driver.findElements(By.xpath("//tr/td[contains(text(),'"+arr[0].trim()+"')]/preceding-sibling::td[@class='version']"));
		for(WebElement EachVersion : ProductVersions1){
			InstallNowVersions=EachVersion.getText();
			Util.printInfo("The version of the product / serial number from CP UI in download window is :: " +InstallNowVersions);
			CPProductLineCode=homePage.getValueFromGUI("ProductLineCode");
			Util.printInfo("The Product line code from customer portal on Download Now window is :: "+CPProductLineCode);
			CPLanguageOnInstallWindow=homePage.getValueFromGUI("InstallNowWindowLanguage");
			Util.printInfo("The Language of Download now window of Customer portal is :: "+CPLanguageOnInstallWindow);
			homePage.verifyFieldExists("DownLoadNowOnDownloadWindow");
			AppendWebServiceString=AppendWebServiceString+InstallNowVersions+",";
		}
		AppendWebServiceString1=AppendWebServiceString.substring(0,AppendWebServiceString.length()-1);
		CPstr.add(AppendWebServiceString1);
		
		homePage.click("ProductVersions");
		homePage.click("CloseWindow");
		Util.sleep(4000);
		
		if(CPstr.equals(str1)){
			assertTrue("The versions of the product from Customer portal on Download Now window and webservice are same", str.equals(str1));
		}else{
			EISTestBase.fail("The versions of the product from Customer portal on Download Now window and webservice are not same");
		}if(CPProductLineCode.equalsIgnoreCase(ProductLine)){
			assertTrue("The Product line Code from Customer Portal on Download now window and From webservice is / are same ",CPProductLineCode.equalsIgnoreCase(ProductLine));
		}else{
			EISTestBase.fail("The Product line Code from Customer Portal on Download now window and From webservice is / are not same ");
		}if(ArrProductkey1.trim().equalsIgnoreCase(WebServiceProductKey1.trim())){
			assertTrue("The Product keys from webservice " +WebServiceProductKey1.trim()+ " and on Download Now Window of customer portal UI "+ArrProductkey1.trim()+" are same :: ", ArrProductkey.trim().equalsIgnoreCase(WebServiceProductKey1.trim()));
		}else{
			EISTestBase.fail("The Product keys from webservice "+WebServiceProductKey1.trim()+" and on Download Now Window of customer portal UI "+ArrProductkey1.trim()+" are not same");
		}if(CPLanguageOnInstallWindow.toLowerCase().contains(Language.getTextContent())){
			assertTrue("The language from web service and customer portal Download now window are same ", CPLanguageOnInstallWindow.toLowerCase().contains(Language.getTextContent()));
		}else{
			EISTestBase.fail("The Language from Web service "+Language.getTextContent()+" and Download now window of customer portal "+CPLanguageOnInstallWindow+" are different");
		}
		
		
		Util.printInfo("Verified Download now fucntionality ");
		Util.printInfo("Started Verifying Browser Download fucntionality ");
		
		homePage.click("DownloadNowButton");
		homePage.clickAndWait("firstContractBrowserDownloadButton", "languageSelectDropDown");
		
		if(homePage.isFieldVisible("BrowserDownloadWindow")){
			homePage.verifyFieldExists("BrowserDownloadWindow");
			homePage.click("ProductVersions");
		}else{
			Util.printInfo("Browser Download Text is not pressent on download window :: ");
		}
		
		List<WebElement> SerialNumbers11=driver.findElements(By.xpath(".//*[@id='scroll-area-serial']/div[2]/div/table/tbody/tr/td[2]"));
		for(WebElement EachSerialNumber : SerialNumbers11){
			InstallNowSerialNumber=EachSerialNumber.getText();
			arr=InstallNowSerialNumber.split("/");
			ArrProductkey11=ArrProductkey11+arr[1];
			Util.printInfo("The Product key of the Product from CP on Browser Download window is :: "+arr[1]);
			Util.printInfo("The Serial Number of the product on Browser Download window is :: "+InstallNowSerialNumber);
		}
		String AppendWebServiceString11="";
		String AppendWebServiceString111="";
		List<WebElement> ProductVersions11=driver.findElements(By.xpath("//tr/td[contains(text(),'"+arr[0].trim()+"')]/preceding-sibling::td[@class='version']"));
		CPProductLineCode=homePage.getValueFromGUI("ProductLineCode");
		Util.printInfo("The Product line code from customer portal on Browser Download window is :: "+CPProductLineCode);
		CPLanguageOnInstallWindow=homePage.getValueFromGUI("InstallNowWindowLanguage");
		Util.printInfo("The Language on Browser Download window of Customer portal is :: "+CPLanguageOnInstallWindow);
		homePage.verifyFieldExists("BrowserDownLoadOnBrowserDownloadWindow");
		for(WebElement EachVersion : ProductVersions11){
			InstallNowVersions=EachVersion.getText();
			Util.printInfo("The version of the product / serial number from CP UI on Browser download window is :: " +InstallNowVersions);
			AppendWebServiceString11=AppendWebServiceString11+InstallNowVersions+",";
		}
		AppendWebServiceString111=AppendWebServiceString11.substring(0,AppendWebServiceString11.length()-1);
		CPstr1.add(AppendWebServiceString111);
		
		if(CPstr1.equals(str1)){
			assertTrue("The versions of the product from Customer portal on Browser Download window and webservice are same", str.equals(str1));
		}else{
			EISTestBase.fail("The versions of the product from Customer portal on Browser Download window and webservice are not same");
		}if(CPProductLineCode.equalsIgnoreCase(ProductLine)){
			assertTrue("The Product line Code from Customer Portal on Browser Download window and From webservice is / are same ",CPProductLineCode.equalsIgnoreCase(ProductLine));
		}else{
			EISTestBase.fail("The Product line Code from Customer Portal on Browser Download window and From webservice is / are not same ");
		}if(ArrProductkey11.trim().equalsIgnoreCase(WebServiceProductKey1.trim())){
			assertTrue("The Product keys from webservice " +WebServiceProductKey1.trim()+ " and on Browser Download Window of customer portal UI "+ArrProductkey11.trim()+" are same :: ", ArrProductkey.trim().equalsIgnoreCase(WebServiceProductKey1.trim()));
		}else{
			EISTestBase.fail("The Product keys from webservice "+WebServiceProductKey1.trim()+" and on Browser Download Window of customer portal UI "+ArrProductkey11.trim()+" are not same");
		}if(CPLanguageOnInstallWindow.toLowerCase().contains(Language.getTextContent())){
			assertTrue("The language from web service and customer portal Browser Download window are same ", CPLanguageOnInstallWindow.toLowerCase().contains(Language.getTextContent()));
		}else{
			EISTestBase.fail("The Language from Web service "+Language.getTextContent()+" and Browser Download window of customer portal "+CPLanguageOnInstallWindow+" are different");
		}
		
		homePage.click("ProductVersions");
		homePage.click("CloseWindow");
		Util.sleep(4000);
		
		/*homePage.clickAndWait("firstContractDownloadNowButton", "languageSelectDropDown");
		homePage.verifyFieldExists("autodeskRevitLTDownloadNow");
		homePage.verifyFieldExists("autodeskAutoCADLTDownloadNow");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Download Now functionality");
		
		homePage.clickAndWait("firstContractBrowserDownloadButton", "languageSelectDropDown");
		homePage.verifyFieldExists("autodeskRevitLTBrowserDownload");
		homePage.verifyFieldExists("autodeskAutoCADLTBrowserDownload");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Browser Download functionality");*/
		
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
