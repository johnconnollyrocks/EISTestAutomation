package customerportal;

import java.awt.Robot;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import common.Util;

public class Test_Verify_Products_And_Services_Version_Hover extends CustomerPortalTestBase{

	public Test_Verify_Products_And_Services_Version_Hover() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verfy_ContractNumbers() throws Exception {
		String TotalProductsCM=null;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		Util.sleep(5000);
//		SoapUIExampleTest suet = new SoapUIExampleTest();
//		DOMXmlParser domParser = new DOMXmlParser();
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		Actions action = new Actions(driver);
		WebElement web = driver.findElement(By.xpath("//article[1]//div[@class='version']//span[text()='2 Versions']"));
		Point coordinates = web.getLocation();
		Robot robot = new Robot();
		robot.mouseMove(coordinates.getX(), coordinates.getY() + 65);
		Util.sleep(4000);
		if(driver.findElement(By.xpath("//article[1]//div[@class='tooltip-content']//div[text()='2014, 2015']")).isDisplayed()){
		TotalProductsCM=driver.findElement(By.xpath("//article[1]//div[@class='tooltip-content']//div[text()='2014, 2015']//following-sibling::span")).getText();
		Util.sleep(6000);
		}else{
			Util.printInfo("mouse hover not working can't verify or get value :: ");
		}
		Util.sleep(4000);
		robot.mouseMove(35,35);
		Util.sleep(2000);
		robot.mouseMove(35,35);
//		String AppendingText="Autodesk "+TotalProductsCM;
//		ProductNamesFromGUI.add(TotalProductsCM);
				

    Util.printInfo("Text is :: "+TotalProductsCM);
	// triggering web service call to get the list of contract numbers(input:GUID)
		
		/*String Request=testProperties.getConstant("REQUEST2");
		File file1=new File(System.getProperty("user.dir")+"\\build\\PartyService.wsdl");
		
		String GetContactEntitlementsResponse=suet.getSoapRequestForChargeEvent("GetContactEntitlements", "GetContactEntitlements", file1.getName(), Request, "https://stageservices-usscl.autodesk.com/PartyService", getEnvironment());
		Document doc1 = domParser.getDocument(GetContactEntitlementsResponse);
		Util.printInfo("GetContactEntitlementsResponse:" +GetContactEntitlementsResponse);
		
		NodeList ContactEntitlements= doc1.getElementsByTagName("pfx:Contract");
		Util.printInfo("pfx:Contract.getLength(): "+ContactEntitlements.getLength());
		String CCcontracts="";
		Node contractNode ;
		Node assetSerialNumber;
		Node productKey;
		Node release;
			for(int s=0;s<ContactEntitlements.getLength() ; s++ ){
//			ContractStatus = domParser.getSubNode("ns2:ContractStatus", ContactEntitlements.item(s) );
//			String ActiveStatus=ContractStatus.getTextContent();
			contractNode = domParser.getSubNode("ns2:ContractNumber",ContactEntitlements.item(s));
			if( contractNode.getTextContent().equalsIgnoreCase("110000715857")){
				NodeList ContactItem= doc1.getElementsByTagName("ns2:ContractItem");
				NodeList ListOfAsset= doc1.getElementsByTagName("ns2:ListOfAsset");
				NodeList Asset= doc1.getElementsByTagName("ns2:Asset");
				assetSerialNumber=  domParser.getSubNode("ns:SerialNumber", Asset.item(s));
				NodeList AssetProduct= doc1.getElementsByTagName("ns:AssetProduct");
				productKey = domParser.getSubNode("ns1:ProductKey", AssetProduct.item(s));
				release=domParser.getSubNode("ns1:Release",AssetProduct.item(s));
			Util.printInfo("GetContactEntitlementsResponse:prodKey" +productKey.getTextContent());
			Util.printInfo("GetContactEntitlementsResponse:assetSerialNumber" +assetSerialNumber.getTextContent());
			Util.printInfo("GetContactEntitlementsResponse:release" +release.getTextContent());
			}
		 }
			
//			assertTrue("Contract number",ContractsEditAccess.contains(CCcontracts.trim()));
*/		logoutMyAutodeskPortal();
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