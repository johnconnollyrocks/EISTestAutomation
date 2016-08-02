
package customerportal;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verify_Display_Product_Under_Each_Contract extends CustomerPortalTestBase{

	public Test_Verify_Display_Product_Under_Each_Contract() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verify_Current_And_Previous_Version_Release_On_PandSPage() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		String EMAIL = testProperties.getConstant("EMAIL");
		String productNames=null;
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		String TotalProductsCM=null;
		String values=null;
//		ArrayList<String> ProductNamesFromGUI = new ArrayList<String>();
		String UserRole=null;
		int Eucount=0;
		Util.sleep(5000);
		homePage.clickAndWait("users","editAccess");
		
		int UsageReport=driver.findElements(By.xpath(".//*[@id='results']/li/section/div[2]")).size();
		
		Util.printInfo("Navigating to Users Page ::");
		
		for(int rownum=1;rownum<=UsageReport;rownum++){
			
			String EachUser=homePage.createFieldWithParsedFieldLocatorsTokens("EachUser", String.valueOf(rownum));
			homePage.click(EachUser);

			String AdminRole=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(rownum));
			UserRole=homePage.getValueFromGUI(AdminRole);
			Util.sleep(5000);
		
		  if(UserRole.contains("Contract Manage")){
		  Eucount=Eucount+1;
		  String User=homePage.createFieldWithParsedFieldLocatorsTokens("UserNames", String.valueOf(rownum));
		  String Username=homePage.getValueFromGUI(User);
		  
		  String CMEditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("CMEditAccess", Username);
		  homePage.click(CMEditAccess);
		  
		  break;
	}
  }
		
		List<WebElement> CM1Contracts=driver.findElements(By.xpath(".//*[contains(@id,'product-list')]/a/span[2]"));
		String ProductNamesFromGUI="";
		for(WebElement CMcontracts:CM1Contracts){
			String ContractsUnderCM1=CMcontracts.getText();
			String ContractsUnderCM[]=ContractsUnderCM1.split("#");
			Util.printInfo("ContractsUnderCM "+ContractsUnderCM[1].trim());
			Actions action = new Actions(driver);
			WebElement web = driver.findElement(By.xpath(".//*[contains(@id,'"+ContractsUnderCM[1].trim()+"')]/a/span[2]"));
//			action.moveToElement(web);
//			action.perform();
			
			
			//***********************************************************************
			/*Capabilities capabilities = ((RemoteWebDriver) event).getCapabilities(); 
			String browserName = capabilities.getBrowserName(); 
			if(!browserName.equalsIgnoreCase("safari")){ 
			 		 
				action.moveToElement(web);
				action.perform();
			Util.sleep(6000); 
			}else{ 
			try{   
				String Xloc ="//*[contains(@id,'"+ContractsUnderCM[1].trim()+"')]/a/span[2]";
				System.out.println("**********************"+Xloc);
				String GetXpath1 ="document.getElementByXPath = function(sValue){ var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0) { return a.snapshotItem(0); } };"; 
				String jsClick = "var click_ev = document.createEvent(\"MouseEvents\"); click_ev.initEvent(\"click\", true, true); document.getElementByXPath(\""+Xloc+ "\")";
				String jsClick1 = "var elem = document.getElementByXPath(\""+Xloc+ "\");"+ "if( document.createEvent) {"+ "var evObj = document.createEvent('MouseEvents');"+ "evObj.initEvent( 'mouseover', true, true );"+"elem.dispatchEvent(evObj);"+ "} else if( document.createEventObject ) {"+ "elem.fireEvent('onmouseover');"+ "}"; 
				System.out.println("Print Message **********************************"+jsClick);
				JavascriptExecutor js = (JavascriptExecutor) driver; 
				js.executeScript(GetXpath1); 
				Thread.sleep(5000); 
				js.executeScript(jsClick);
				js.executeScript(jsClick1);
				Thread.sleep(5000); 
			    
			} catch (Exception e){  
			   e.printStackTrace();  
			   System.out.println("Xpath not found "+ContractsUnderCM[1]);     
			    
			   } 
			}*/
			
			
			//***********************************************************************
			/*Point coordinates = web.getLocation();
			Robot robot = new Robot();
			robot.mouseMove(coordinates.getX(), coordinates.getY() + 65);*/
			Util.sleep(4000);
			if(driver.findElement(By.xpath(".//*[contains(@id,'"+ContractsUnderCM[1].trim()+"')]/div[4]/div[3]/div/ul/li")).isDisplayed()){
			Util.sleep(4000);
			TotalProductsCM=driver.findElement(By.xpath(".//*[contains(@id,'"+ContractsUnderCM[1].trim()+"')]/div[4]/div[3]/div/ul/li")).getText();
			Util.sleep(6000);
			}else{
				Util.printInfo("mouse hover not working can't verify or get value :: ");
			}
			Util.sleep(4000);
			/*robot.mouseMove(35,35);
			Util.sleep(2000);
			robot.mouseMove(35,35);*/
//			String AppendingText="Autodesk "+TotalProductsCM;
//			ProductNamesFromGUI.add(TotalProductsCM);
			ProductNamesFromGUI=ProductNamesFromGUI+" "+TotalProductsCM;
			
		}
		
        homePage.click("saveButton");
        Util.printInfo("Product names under CM in UI page is/are :: "+ProductNamesFromGUI);
        
        
		// Triggering Web service call to get GUID (input:email ID)
		String GUIDRequest= testProperties.getConstant("REQUEST1");		
		String ActualRequest=suet.ReplaceRequest(GUIDRequest,EMAIL);
		
		File file=new File(System.getProperty("user.dir")+"\\build\\UserService.wsdl");
		
		String response = suet.getResponseForSoapRequest("webservice","GetUserByEmail", file.getName(), ActualRequest,"https://stageservices-usscl.autodesk.com/UserService",getEnvironment());
	//	System.out.println(response);
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
	
	// triggering web service call to check the presence of productName(AUTODESK)(input:GUID)
		
		String Request=testProperties.getConstant("REQUEST2");
		String StrRequsetXML=Request.replace("<par:GUID>?</par:GUID>", "<par:GUID>"+GUID+"</par:GUID>");
		
		File file1=new File(System.getProperty("user.dir")+"\\build\\PartyService.wsdl");
		
		String Response=suet.getSoapRequestForChargeEvent("GetContactEntitlements", "GetContactEntitlements", file1.getName(), StrRequsetXML, "https://stageservices-usscl.autodesk.com/PartyService", getEnvironment());
		Document doc1 = domParser.getDocument(Response);
	//	Util.printInfo("Response:" +Response);
		
		NodeList ContactEntitlements= doc1.getElementsByTagName("ns:AssetProduct");
		Util.printInfo("ns:AssetProduct.getLength(): "+ContactEntitlements.getLength());
		String productnamesFromWbService="";
		Node productName ;
		String ActualProduct=null;
		
			for(int s=0;s<ContactEntitlements.getLength() ; s++ ){
			productName = domParser.getSubNode("ns1:ProductLine", ContactEntitlements.item(s) );
			String Displayname=productName.getTextContent();
			
			if(Displayname.contains("Autodesk")){
				String productnames[]=Displayname.split("Autodesk ");
				ActualProduct=productnames[1];
			}
			productnamesFromWbService=ActualProduct+" "+productnamesFromWbService;
		 }
			Util.printInfo("Products under CM "+ testProperties.getConstant("EMAIL")+ " in Customer portal GUI is/are :: "+ProductNamesFromGUI.trim());
			Util.printInfo("Products under CM " + testProperties.getConstant("EMAIL")+ " from webservice call is/are ::" +productnamesFromWbService.trim());
			
			assertTrue("Product Names in UI and WebService :: ",productnamesFromWbService.contains(ProductNamesFromGUI.trim()));
			
			/*Util.sleep(5000);
			homePage.clickAndWait("users","editAccess");
			
			int UsageReport1=driver.findElements(By.xpath(".//*[@id='results']/li/section/div[2]")).size();
			
			Util.printInfo("Navigating to Users Page ::");
			
			for(int rownum=1;rownum<=UsageReport1;rownum++){
				
				String EachUser=homePage.createFieldWithParsedFieldLocatorsTokens("EachUser", String.valueOf(rownum));
				homePage.click(EachUser);

				String AdminRole=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(rownum));
				UserRole=homePage.getValueFromGUI(AdminRole);
				Util.sleep(5000);
			
			  if(UserRole.contains("Contract Manager")){
			  Eucount=Eucount+1;
			  String User=homePage.createFieldWithParsedFieldLocatorsTokens("UserNames", String.valueOf(rownum));
			  String Username=homePage.getValueFromGUI(User);
			  
			  String CMEditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("CMEditAccess", Username);
			  homePage.click(CMEditAccess);
			  
			  break;
		}
	  }
			
			List<WebElement> CM1Contracts1=driver.findElements(By.xpath(".//*[contains(@id,'product-list')]/a/span[2]"));
			
			for(WebElement CMcontracts:CM1Contracts1){
				String ContractsUnderCM1=CMcontracts.getText();
				String ContractsUnderCM[]=ContractsUnderCM1.split("#");
				Util.printInfo("ContractsUnderCM "+ContractsUnderCM[1].trim());
				Actions action = new Actions(driver);
				WebElement web = driver.findElement(By.xpath(".//*[contains(@id,'"+ContractsUnderCM[1].trim()+"')]/a/span[2]"));
				Point coordinates = web.getLocation();
				Robot robot = new Robot();
				robot.mouseMove(coordinates.getX(), coordinates.getY() + 65);
				Util.sleep(4000);
				if(driver.findElement(By.xpath(".//*[contains(@id,'"+ContractsUnderCM[1].trim()+"')]/div[4]/div[3]/div/ul/li")).isDisplayed()){
				TotalProductsCM=driver.findElement(By.xpath(".//*[contains(@id,'"+ContractsUnderCM[1].trim()+"')]/div[4]/div[3]/div/ul")).getText();
				Util.sleep(6000);
				}else{
					Util.printInfo("mouse hover not working can't verify or get value :: ");
				}
				
				WebElement ValidateProduct=driver.findElement(By.xpath("//*[contains(@id,'"+ContractsUnderCM[1].trim()+"')]//div[@class='viewport']//li[contains(text(),'"+ActualProduct+"')]"));
				
				boolean flag=ValidateProduct.isDisplayed();
				
				Util.printInfo("comparing products in UI ::");
				assertEquals(true, flag);
				
			}
			
	        homePage.click("saveButton");
	        Util.printInfo("Product names under CM in UI page is/are :: ");
		}
*/		
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
