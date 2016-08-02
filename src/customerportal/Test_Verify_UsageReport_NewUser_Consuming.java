package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_UsageReport_NewUser_Consuming extends CustomerPortalTestBase {
	
	
	public Test_Verify_UsageReport_NewUser_Consuming() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		if(getEnvironment().equalsIgnoreCase("DEV")){
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			
			Util.printInfo("Logging into the CM's account");
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		}
		Util.sleep(5000);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		int CreditsConsumed=0;
		int ccConsumed=0;
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(2000);
		
		String UsersEmail=testProperties.getConstant("EMAIL");
		String fstname=RandomStringUtils.randomAlphabetic(5);
		String EMAIL=UsersEmail+getUniqueId()+"@ssttest.net"+" ";

		String UserName=fstname+" "+"User";
		
		Util.printInfo("Inviting a new user : "+UserName);
		/*String FirstName=homePage.getConstant("FIRSTNAME");
		String UserNewName=FirstName+getUniqueId();*/
		
		addUser(EMAIL, fstname);
		
		Util.printInfo("Assigned all contracts for User ::"  +UserName);
		
		checkChecKBox("inputForProductsDownloadCBInEditAccess", "labelForProductsDownloadCBInEditAccess");
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		
		homePage.click("saveButton");
		
		Util.printInfo("User added successfully..");
		Util.sleep(3000);		
		
		homePage.clickAndWait("reporting","byUsers");
		homePage.click("byUsers");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);
		ArrayList<String> parsedContract=new ArrayList<String>();
		parsedContract=null;
		String Contracts=null;
		String Credits=null;
		String UserCredits=null;
		int interval;
		int timeout;
		boolean ticket=false;
		int k;
		boolean reflected = false;
		long startTime;
		long endTime;
		
		List<WebElement> ContractsSize=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+UserName+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));
		
		for(WebElement Contract: ContractsSize){
		
		Contracts=Contract.getText();
		System.out.println("Contracts under pooled column :"+Contracts);
		String parsedContractValue =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+UserName+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+Contracts+"')]/ancestor::td[@class='number']/following-sibling::td")).getText();
		Util.printInfo("verifying pooled credits row ::");
		
			if (!parsedContractValue.trim().contains(
					"No Cloud Credits Purchased")) {
				Util.printInfo("pooled credits before consuming...");
				
				assertTrue("The Pooled Gauge ::" +parsedContractValue+" ",parsedContractValue.trim().contains("0"));
				String GUIDRequest = testProperties.getConstant("REQUEST");
				String ActualRequest = suet.ReplaceRequest(GUIDRequest, EMAIL);

				File file = new File(System.getProperty("user.dir")
						+ "\\build\\UserService.wsdl");

				String response = suet.getResponseForSoapRequest("webservice",
						"GetUserByEmail", file.getName(), ActualRequest,
						"https://stageservices-usscl.autodesk.com/UserService",
						getEnvironment());

				Document doc = domParser.getDocument(response);
				String GUID = null;
				NodeList Nodes = doc.getElementsByTagName("ns0:User");
				Util.printInfo(Nodes.getLength() + " : Nodes.getlength()");
				for (int s = 0; s < Nodes.getLength(); s++) {
					Node GUIDNode = domParser.getSubNode("ns0:GUID", Nodes.item(s));
					GUID = GUIDNode.getTextContent();
					Util.printInfo(GUIDNode.getTextContent());
				}
				String Request = testProperties.getConstant("ChargeRequest");
				String CurrentDate = suet.getCurrentDate();
				boolean flag = true;
				String ContractArray[] = Contracts.split("#");
				String StrRequsetXML = Request
						.replace("<UID>?</UID>", "<UID>" + ContractArray[1] + "</UID>")
						.replace("<GUID>?</GUID>", "<GUID>" + GUID + "</GUID>")
						.replace(
								"<StartTime>?</StartTime>         <EndTime>?</EndTime> ",
								"<StartTime>" + CurrentDate
										+ "</StartTime>         <EndTime>"
										+ CurrentDate + "</EndTime>");

				File file1 = new File(System.getProperty("user.dir")
						+ "\\build\\ConvergentCharging.wsdl");

				String ChargeEventResponse = suet
						.getSoapRequestForChargeEvent(
								"ChargeEvent",
								"ChargeEvent",
								file1.getName(),
								StrRequsetXML,
								"https://quattro-stg-vip.autodesk.com/quattroCloud/services/ConvergentCharging",
								getEnvironment());

				Document doc1 = domParser.getDocument(ChargeEventResponse);
				String Status = null;
				NodeList Nodes1 = doc1.getElementsByTagName("ns2:ChargeEventResponse");
				for (int s = 0; s < Nodes1.getLength(); s++) {
					Node StatusNode = domParser.getSubNode("ResponseStatus",
							Nodes1.item(s));
					Status = StatusNode.getTextContent();
					
					if(Status.equalsIgnoreCase("OK")){
						Util.printInfo("charge event response success...");
						Util.printInfo("pooled Cloud credit consumption is Successfull");
						assertEquals("OK", Status);
					}
				}
				break;
			}else{
				assertTrue("No contracts have cloud credits Purchased",parsedContractValue.trim().contains("No Cloud Credits Purchased"));
				Util.printError("No contracts have cloud credits Purchased, So web service call also not possbile ");
			}
		}
		
		if(ContractsSize.size()==0){
			Util.printInfo("No contracts are pressent under pooled column ::");
		}
		
				boolean flag = false;
				List<WebElement> usersTable = driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr"));
				for(k = 1;k<=usersTable.size();k++)
				{ 
					String NewUser=homePage.createFieldWithParsedFieldLocatorsTokens("GetUser", String.valueOf(k));
					String UserID=homePage.getValueFromGUI(NewUser);
				 if(UserName.trim().equalsIgnoreCase(UserID.trim())){
					ticket=true;
					Util.sleep(5000);
					
					Util.printInfo("verifying individual credits row ::");
					
					String NewUserCredits=homePage.createFieldWithParsedFieldLocatorsTokens("UserCredits", String.valueOf(k));
					UserCredits=homePage.getValueFromGUI(NewUserCredits);
					assertEquals("Individual credits for new user is 0 :: ","0", UserCredits.trim());
					Util.printInfo("Individual credits for new user ::" +UserName+ "is" +UserCredits);
					Util.printInfo("Individual credits remains zero even after consuming pooled credits...");
					break;
				  }
				}
				
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		
//		Get the GUID of the newly created user
		String UserEmail=EMAIL;
		String GUIDRequest= testProperties.getConstant("REQUEST");
		String ActualRequest=suet.ReplaceRequest(GUIDRequest,UserEmail);
		File file = new File(System.getProperty("user.dir")+"\\build\\UserService.wsdl");
		
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
		
		
		String Request=testProperties.getConstant("ChargeRequest");
		String CurrentDate=suet.getCurrentDate();
		String StrRequsetXML=Request.replace("<UID>?</UID>", "<UID>"+GUID+"</UID>").replace("<GUID>?</GUID>", "<GUID>"+GUID+"</GUID>").replace("<StartTime>?</StartTime>         <EndTime>?</EndTime> ", "<StartTime>"+CurrentDate+"</StartTime>         <EndTime>"+CurrentDate+"</EndTime>" );		
		
		File file1 = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
		
		String ChargeEventResponse=suet.getSoapRequestForChargeEvent("ChargeEvent", "ChargeEvent",file1.getName(), StrRequsetXML, "https://quattro-stg-vip.autodesk.com/quattroCloud/services/ConvergentCharging", getEnvironment());
		Document doc1 = domParser.getDocument(ChargeEventResponse);
		String Status=null;
		NodeList Nodes1 = doc1.getElementsByTagName("ns2:ChargeEventResponse");
		for(int s=0;s<Nodes1.getLength() ; s++ ){
			Node StatusNode= domParser.getSubNode("ResponseStatus", Nodes1.item(s));
			Status=StatusNode.getTextContent();
			assertEquals("OK", Status);
		}
		
		if(Status.equalsIgnoreCase("OK")){
		Util.printInfo("Individual Cloud credit consumption is Successfull");
		}
		
		Util.printInfo("Logging out from CUstomerPortal");
		logoutMyAutodeskPortal();
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		
		homePage.clickAndWait("reporting","byUsers");
		homePage.click("byUsers");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);
		String parsedContractValue=null;
		List<WebElement> ContractsSize1=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+UserName+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));
		
		for(WebElement Contract: ContractsSize1){
		
		Contracts=Contract.getText();
		System.out.println("Contracts under pooled column :"+Contracts);
		
			homePage.refresh();
			Util.sleep(4000);
			homePage.click("Logout");
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
			Util.sleep(4000);
			Util.printInfo("Navigating to reporting page :::");
			homePage.click("reporting");
			Util.sleep(5000);
			homePage.click("byUsers");
			parsedContractValue =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+UserName+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+Contracts+"')]/ancestor::td[@class='number']/following-sibling::td")).getText();
			
		
		interval = 2000;
		timeout = CustomerPortalConstants.POOLED_CREDITS_TIMEOUT;
		reflected = false;
		startTime = System.currentTimeMillis();
		endTime = startTime + timeout;
		
		
		Util.printInfo("verifying pooled credits row ::");
		
		while (System.currentTimeMillis() < endTime) {
		if (!parsedContractValue.trim().contains("No Cloud Credits Purchased")) {
			Util.printInfo("Validating pooled credits after consuming...");
			
			if(!parsedContractValue.trim().contains("0")){
				reflected = true;
				assertTrue("The number of pooled cloud credits consumed by user increases against the contract::" +parsedContractValue+" ",parsedContractValue.trim().contains("1"));
			}else{
//				EISTestBase.fail("consumed pooled credits are not reflected even after long time");
				Util.printInfo("Waiting for Cloud Credits to Reflect");
				homePage.refresh();
				Util.sleep(5000);
			}
		  }else{
//				EISTestBase.fail("consumed pooled credits are not reflected even after long time");
				Util.printInfo("Waiting for Cloud Credits to Reflect");
				homePage.refresh();
				Util.sleep(5000);
			}
		}
		if(!reflected) {
			EISTestBase.fail("consumed pooled credits are not reflected even after 20 Minutes");
		}
		}
			String ccCredits=null;
			boolean flag1 = false;
			String ConsumedCredits=null;
			List<WebElement> usersTables = driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr"));
			for(int i = 1;i<=usersTables.size();i++)
			{
				String NewUser=homePage.createFieldWithParsedFieldLocatorsTokens("GetUser", String.valueOf(i));
				String UserID=homePage.getValueFromGUI(NewUser);
			 if(UserName.trim().equalsIgnoreCase(UserID.trim())){			 
				 String NewUserConsumedCredits=homePage.createFieldWithParsedFieldLocatorsTokens("UserCredits", String.valueOf(i));
				 ConsumedCredits=homePage.getValueFromGUI(NewUserConsumedCredits);
				 Util.printInfo("Validating individual credits after consuming ::");
				 assertEquals("The number of individual cloud credits consumed by user "+UserName+" increases ","10", ConsumedCredits.trim());
				}
			 }
			
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		
		RemoveUser(UserName);
		
		Util.printInfo("User removed successfully");
		
		Util.printInfo("Navigating to Usage-by-Users page");
		homePage.clickAndWait("reporting","byUsers");
		homePage.click("byUsers");
		
//		homePage.click("testconsumingRemoveLink");
//		homePage.click("testconsumingConfirmRemoveButton");
		
//		homePage.verifyFieldNotExists(RemovedUser);
		
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
