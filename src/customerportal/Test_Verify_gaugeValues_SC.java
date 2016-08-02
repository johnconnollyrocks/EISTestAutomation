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
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Raghavendra Yadav
 * @version 1.0.0
 */
public final class Test_Verify_gaugeValues_SC extends CustomerPortalTestBase {
	
	
	public Test_Verify_gaugeValues_SC() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {	
		launchMyAutodeskPortal(getBaseURL()); 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		Util.printInfo("Logging into CM's Account");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(5000);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.click("reporting");
		homePage.click("byUsers");
		int ccConsumed=0;
		int CreditsConsumed=0;
		String UserStatus=null;
		String ByUservalue=null;
		String Admingauge=null;
		String SCAdmingauge=null;
		String Admin=null;	
		String UserRole=null;
		String SoftwareCoordinator=null;
		String SCgauge=null;
		String SCEmailId=null;
		String SCIndividualGauge=null;
		List<WebElement> ContractSize=null;
		int j;
		int ReportUsersList=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();
		for(int i=1;i<=ReportUsersList;i++)
		{
//			 UserStatus=driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+(i+1)+"]/td[2]/div/div/span")).getText();
			 Admin=driver.findElement(By.xpath(".//*[@id='username']")).getText();
			 ByUservalue=homePage.createFieldWithParsedFieldLocatorsTokens("ByUsersList", String.valueOf(i));
			 String CMUser=homePage.getValueFromGUI(ByUservalue);
		 if(CMUser.equalsIgnoreCase(Admin))
		 	{
			 Util.printInfo("Cheking Individual Cloud Credit gauge for CM :" +Admin);
			 Admingauge=homePage.createFieldWithParsedFieldLocatorsTokens("AdminGauge", String.valueOf(i));
			 homePage.verifyFieldExists(Admingauge);
			 String CMIndividualGauge=homePage.getValueFromGUI(Admingauge);
			 Util.printInfo("Individual Cloud Credit value for CM	" +Admin+ "is" +CMIndividualGauge);
			 
			 Util.printInfo("Cheking Pooled Cloud Credit gauge for CM:");
			 ContractSize=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+Admin+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));
				boolean flag=true;
				String Contracts=null;
				for(WebElement Contract: ContractSize){				
				 Contracts=Contract.getText();
//				homePage.verifyFieldExists(Contract);		
//				String parsedContractValue =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+Admin+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+Contracts+"')]/ancestor::td[@class='number']/following-sibling::td[@class='usage']/div[@class='numeric']/span[@class='value']")).getText();
				String PooledGauge =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+Admin+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+Contracts+"')]/ancestor::td[@class='number']/following-sibling::td")).getText();
				if(PooledGauge.trim().contains("0")){
				Util.printInfo("Pooled Cloud Credit gauge available for CM for contract:"+Contracts+":"+PooledGauge);
				flag=false;
				break;
					}
				}
			if(flag)
				Util.printError("No pooled Cloud Credits Purchased for any Contracts of CM");
		 		}
		 	}
		if(ContractSize.size()==0){
		Util.printInfo("No contracts are pressent under pooled column ::");
		}
		
		homePage.clickAndWait("users","selectAllInUP");
		for(int rownum=1;rownum<=ReportUsersList;rownum++)
		{
			Util.printInfo("Clicking on each user to retrive existing Software Coordinator:");
			String EachUser=homePage.createFieldWithParsedFieldLocatorsTokens("EachUser", String.valueOf(rownum));
			homePage.click(EachUser);

			String AdminRole=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(rownum));
			UserRole=homePage.getValueFromGUI(AdminRole);
			Util.sleep(5000);
			
		if(UserRole.contains("Software Coordinator")){
			
			String EmailId=homePage.createFieldWithParsedFieldLocatorsTokens("EmailID", String.valueOf(rownum));
			SCEmailId=homePage.getAttribute(EmailId, "title");
			
			String SCAdmin=homePage.createFieldWithParsedFieldLocatorsTokens("SCAdmin", String.valueOf(rownum));
			SoftwareCoordinator=homePage.getValueFromGUI(SCAdmin);
			Util.printInfo("Software Coordinator found :"+SoftwareCoordinator);
			homePage.click("reporting");
			homePage.click("byUsers");
			for(j=1;j<ReportUsersList;j++)
			{
			
			String Users=homePage.createFieldWithParsedFieldLocatorsTokens("ByUsersList", String.valueOf(j));
			String ByUser=homePage.getValueFromGUI(Users);
			
			if(ByUser.equalsIgnoreCase(SoftwareCoordinator)){				
				
				Util.printInfo("Checking Individual Gauge of Software Coordinator ");
				
				SCAdmingauge=homePage.createFieldWithParsedFieldLocatorsTokens("AdminGauge", String.valueOf(j));
				homePage.verifyFieldExists(Admingauge);
				SCIndividualGauge=homePage.getValueFromGUI(SCAdmingauge);
				Util.printInfo("Individual Cloud Credit gauge value for Software Coordinator"  +SoftwareCoordinator+ "is" +SCIndividualGauge);
				
				assertEquals(SCIndividualGauge, SCIndividualGauge);
				
				Util.printInfo("Checking Pooled Gauge for SC");
				List<WebElement> ContractsSize=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+SoftwareCoordinator+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));
				
				for(WebElement Contract: ContractsSize){
					
					String Contracts=Contract.getText();
					System.out.println("Contract:"+Contracts);
//					homePage.verifyFieldExists(Contract);		
//					String parsedContractValue =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+SoftwareCoordinator+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+Contracts+"')]/ancestor::td[@class='number']/following-sibling::td[@class='usage']/div[@class='numeric']/span[@class='value']")).getText();
					String PooledGauge =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+SoftwareCoordinator+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+Contracts+"')]/ancestor::td[@class='number']/following-sibling::td")).getText();
					Util.printInfo("PooledGauge is available for SC and Credits are:" +Contracts+ ":"+ PooledGauge+	"Credits");
					}
				break;
			}
		}	
	break;
	}
}
		String UserEmail=SCEmailId;
		String GUIDRequest= testProperties.getConstant("REQUEST");		
		String ActualRequest=suet.ReplaceRequest(GUIDRequest,UserEmail);
		
		File file=new File(System.getProperty("user.dir")+"\\UserService.wsdl");
		
		String response = suet.getResponseForSoapRequest("webservice","GetUserByEmail", file.getName(), ActualRequest,"https://stageservices-usscl.autodesk.com/UserService",getEnvironment());
		
		Document doc = domParser.getDocument(response);
		String GUID=null;
		NodeList Nodes = doc.getElementsByTagName("ns0:User");
		for(int s=0;s<Nodes.getLength() ; s++ ){
			Node GUIDNode= domParser.getSubNode("ns0:GUID", Nodes.item(s));
			GUID=GUIDNode.getTextContent();
			Util.printInfo("SoftwareCoordinator GUID:" +GUIDNode.getTextContent());
		}
		
		String Request=testProperties.getConstant("ChargeRequest");
		String CurrentDate=suet.getCurrentDate();
		String StrRequsetXML=Request.replace("<UID>?</UID>", "<UID>"+GUID+"</UID>").replace("<GUID>?</GUID>", "<GUID>"+GUID+"</GUID>").replace("<StartTime>?</StartTime>         <EndTime>?</EndTime> ", "<StartTime>"+CurrentDate+"</StartTime>         <EndTime>"+CurrentDate+"</EndTime>" );
		
		File file1=new File(System.getProperty("user.dir")+"\\ConvergentCharging.wsdl");
		
		String ChargeEventResponse=suet.getSoapRequestForChargeEvent("ChargeEvent", "ChargeEvent", file1.getName(), StrRequsetXML, "https://quattro-stg-vip.autodesk.com/quattroCloud/services/ConvergentCharging", getEnvironment());
		
		Document doc1 = domParser.getDocument(ChargeEventResponse);
		String Status=null;
		NodeList Nodes1 = doc1.getElementsByTagName("ns2:ChargeEventResponse");
		for(int s=0;s<Nodes1.getLength() ; s++ ){
			Node StatusNode= domParser.getSubNode("ResponseStatus", Nodes1.item(s));
			Status=StatusNode.getTextContent();
			assertEquals("OK", Status);
		}
		
		if(!Status.equalsIgnoreCase("OK")){
		EISTestBase.fail("Error in Charge Event Response");
		}else{
			Util.printInfo("Charge Event Response Successfull");
		}
		
		homePage.refresh();
		
		for(int i=1;i<=ReportUsersList;i++)
		{
			 ByUservalue=homePage.createFieldWithParsedFieldLocatorsTokens("ByUsersList", String.valueOf(i));
			 String SCUser=homePage.getValueFromGUI(ByUservalue);
		 if(SCUser.equalsIgnoreCase(SoftwareCoordinator))
		 	{
			 Util.printInfo("Cheking Individualgauge for SoftwareCoordinator after Consuming:" +SoftwareCoordinator);
			 Admingauge=homePage.createFieldWithParsedFieldLocatorsTokens("AdminGauge", String.valueOf(i));
			 homePage.verifyFieldExists(Admingauge);
			 String SCIndividualConsumedGauge=homePage.getValueFromGUI(Admingauge);
			 int SCIndividualGauge1=Integer.valueOf(SCIndividualConsumedGauge);
			 Util.printInfo("Individual Cloud Credit value for SC	"+SoftwareCoordinator+ "After Consuming is :" +SCIndividualGauge1);
			 
			 int ScConsumedValue=Integer.valueOf(SCIndividualGauge);
			 
			 
			 assertTrue("Individual Cloud Credits for SC increased",SCIndividualGauge1 > ScConsumedValue);
			 //Consuming Pooled Cloud Credits Yet to be done
			}
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
