package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.syntax.jedit.InputHandler.home;
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
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_UsageReport_AddUser extends CustomerPortalTestBase {
	
	
	public Test_Verify_UsageReport_AddUser() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		/*
		if(getEnvironment().equalsIgnoreCase("DEV")){
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			Util.printInfo("Logging into the CM's account ");
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		}
	
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		String EachUserGaugevaluebefore=null;
		Util.sleep(5000);
		int TotalCreditvaluesbefore=0;
		
		Util.sleep(5000);
		homePage.clickAndWait("reporting","byUsers");
		homePage.click("byUsers");
		Util.printInfo("Validating total Cloud Credits and number of users consumed cloud creidts BEFORE adding new user :: ");
		
		int beforecount=0;
		List<WebElement> Numbersofusers= driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr"));
		for(int i = 1;i<=Numbersofusers.size();i++)
		{
			String NewUser=homePage.createFieldWithParsedFieldLocatorsTokens("GetUser", String.valueOf(i));
			String RemovedUser=homePage.createFieldWithParsedFieldLocatorsTokens("RemovedUser", String.valueOf(i));
			String RemovedStatus=homePage.getValueFromGUI(RemovedUser);
			if(!RemovedStatus.trim().contains("Removed")){
			String UserID=homePage.getValueFromGUI(NewUser);
			 
			 String NewUserConsumedCredits=homePage.createFieldWithParsedFieldLocatorsTokens("EachUserGaugevalue", UserID);
			 EachUserGaugevaluebefore=homePage.getValueFromGUI(NewUserConsumedCredits);
			
			 if(Integer.valueOf(EachUserGaugevaluebefore)>0){
				 beforecount=beforecount+1;
				 TotalCreditvaluesbefore=TotalCreditvaluesbefore + Integer.valueOf(EachUserGaugevaluebefore);
			 }
		   }
		 }
		Util.printInfo("Navigating to reporting page ::");
		homePage.click("reporting");
		
		String IndividualGaugeinReporting=homePage.getValueFromGUI("IndividualGaugeinReporting");
		String UsersConsuming=homePage.getValueFromGUI("UsersConsuming");
		
		assertEquals(TotalCreditvaluesbefore, Integer.valueOf(IndividualGaugeinReporting));
		
		
		System.out.println("Users consumed :: "+beforecount);
		System.out.println("Total credits consumed :: "+TotalCreditvaluesbefore);
		
		assertEquals(beforecount, Integer.valueOf(UsersConsuming));
		
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		
		Util.printInfo("Inviting a new user ");
		
		String FirstName=RandomStringUtils.randomAlphabetic(5);
		
		String ActualEmail=testProperties.getConstant("ENDUSEREMAILID");
		
		String MainEmail=ActualEmail+"@ssttest.net";
		
		String User = FirstName+" "+"Name";
		
		String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",User);
		
		homePage.populateField("UserSearch", MainEmail);
		
		Util.sleep(4000);
		
		if(homePage.isFieldVisible(Enduser1)){
			RemoveUser(User);
		}
		
		homePage.click("CloseSearch");
		Util.sleep(4000);
		
		String RenderingEmailId=ActualEmail+getUniqueId()+"@ssttest.net";
		
		//String User=FirstName+" "+"User";
		String ConsumedCredits=null;
		String Contracts=null;
		String EachUserGaugevalue=null;
		int TotalCreditvalues=0;
		
		addUser(RenderingEmailId, FirstName);
		
		homePage.click("SaveUser");
		
		Util.printInfo("New user created successfully and provisioning is successful");
		
		homePage.refresh();
		
		Util.sleep(20000);
		
		Util.printInfo("Navigating to Usage-by-Users page");
		
		homePage.clickAndWait("reporting","byUsers");
		homePage.click("byUsers");
		homePage.refresh();
		Util.sleep(35000);

		String IndividualGauge=homePage.createFieldWithParsedFieldLocatorsTokens("IndividualGaugeValue", User);
		String IndividualGauge1=homePage.getValueFromGUI(IndividualGauge);
		
		assertEquals("The Individual Gauge for newly created user is :","0",IndividualGauge1);
		
		Util.printInfo("verifying pooled credits for new user as No contracts assigned");
		Util.sleep(20000);
		
		String NoContractAssigned=homePage.createFieldWithParsedFieldLocatorsTokens("NoContractAssigned", User);
		if(homePage.isFieldPresent(NoContractAssigned)){
		homePage.verifyFieldExists(NoContractAssigned);
		}else{
			EISTestBase.fail("NoContractAssigned message is not displayed ::");
		}
		
		Util.printInfo("validatating total Cloud Credits and number of users consumed cloud creidts AFTER adding new user :: ");
		
		int count=0;
		List<WebElement> Numbersofusersafter= driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr"));
		for(int i = 1;i<=Numbersofusersafter.size();i++)
		{
			String NewUser=homePage.createFieldWithParsedFieldLocatorsTokens("GetUser", String.valueOf(i));
			String RemovedUser=homePage.createFieldWithParsedFieldLocatorsTokens("RemovedUser", String.valueOf(i));
			String RemovedStatus=homePage.getValueFromGUI(RemovedUser);
			if(!RemovedStatus.trim().contains("Removed")){
			String UserID=homePage.getValueFromGUI(NewUser);
					 
			 String NewUserConsumedCredits=homePage.createFieldWithParsedFieldLocatorsTokens("EachUserGaugevalue", UserID);
			 EachUserGaugevalue=homePage.getValueFromGUI(NewUserConsumedCredits);
			 
			 if(Integer.valueOf(EachUserGaugevalue)>0){
				 count=count+1;
				 TotalCreditvalues=TotalCreditvalues + Integer.valueOf(EachUserGaugevalue);
			 }
		   }
		 }
		Util.printInfo("Navigating to reporting page :: ");
		homePage.click("reporting");
		String IndividualGaugeinReportingafter=homePage.getValueFromGUI("IndividualGaugeinReporting");
		String UsersConsumingafter=homePage.getValueFromGUI("UsersConsuming");
		
		Util.printInfo("Validating number of credits consumed");
		assertEquals(TotalCreditvalues, Integer.valueOf(IndividualGaugeinReportingafter));
		
		
		System.out.println("Users consumed :: "+count);
		System.out.println("Total credits consumed :: "+TotalCreditvalues);
		
		assertEquals(count, Integer.valueOf(UsersConsumingafter));		
		
		
		assertEquals(count, beforecount);
		
		
		
		assertEquals(TotalCreditvaluesbefore, TotalCreditvalues);	
		
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		
		Util.printInfo("Assigning contracts to the same user.. "+User);
		String assignContracts=homePage.createFieldWithParsedFieldLocatorsTokens("assignContractsUser", User);
		homePage.click(assignContracts);
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		homePage.click("saveButton");
		homePage.refresh();
		Util.sleep(4000);
		String UserEmail=RenderingEmailId;
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
		}else{
		EISTestBase.fail("error in charge event response");
		}
		
		Util.printInfo("Logging out from CUstomerPortal");
		logoutMyAutodeskPortal();
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		
		homePage.clickAndWait("reporting","byUsers");
		homePage.click("byUsers");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);
		
		List<WebElement> usersTables = driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr"));
		for(int i = 1;i<=usersTables.size();i++)
		{
			String NewUser=homePage.createFieldWithParsedFieldLocatorsTokens("GetUser", String.valueOf(i));
			String UserID=homePage.getValueFromGUI(NewUser);
		 if(User.trim().equalsIgnoreCase(UserID.trim())){			 
			 String NewUserConsumedCredits=homePage.createFieldWithParsedFieldLocatorsTokens("UserCredits", String.valueOf(i));
			 ConsumedCredits=homePage.getValueFromGUI(NewUserConsumedCredits);
			 Util.printInfo("Validating individual credits after consuming ::");
			 assertEquals("The number of individual cloud credits consumed by user "+User+" increases ","10", ConsumedCredits.trim());
			 if(Integer.valueOf(ConsumedCredits)>0){
				 
			 }
			}
		 }
		
		List<WebElement> ContractsSize=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+User+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));
		
		for(WebElement Contract: ContractsSize){
		
		Contracts=Contract.getText();
		System.out.println("Contracts under pooled column :"+Contracts);
		String parsedContractValue =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+User+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+Contracts+"')]/ancestor::td[@class='number']/following-sibling::td")).getText();
		Util.printInfo("verifying pooled credits row ::");
		
		
		if (!parsedContractValue.trim().contains(
				"No Cloud Credits Purchased")) {
			Util.printInfo("pooled credits before consuming...");
			
			assertTrue("The Pooled Gauge ::" +parsedContractValue+" ",parsedContractValue.trim().contains("0"));
		}	
		else{
			Util.printError("No contracts have cloud credits Purchased under pooled column, So web service call also not possbile ");
		}
	}
		
		if(ContractsSize.size()==0){
			Util.printError("No contracts are showing under pooled column for this user even after assigning contracts so not able to consume pooled credits...");
		}
		
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		Util.printInfo("Validating total Cloud Credits and number of users consumed cloud creidts AFTER adding new user and consuming credits :: ");
		homePage.click("reporting");
		String IndividualGaugeinReportingafter1=homePage.getValueFromGUI("IndividualGaugeinReporting");
		String UsersConsumingafter1=homePage.getValueFromGUI("UsersConsuming");
		
		assertTrue("The sum of individual usage increases", Integer.valueOf(IndividualGaugeinReportingafter1)>TotalCreditvalues);
		
		Util.printInfo("Increased individual gauge is :: "+IndividualGaugeinReportingafter1);
		
		Util.printInfo("The number of consumers remains the same after consuming credits");
		assertTrue("The number of consumers remains the same after consuming credits",Integer.valueOf(UsersConsumingafter1)>count);
		
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		
		Util.sleep(5000);
		
		RemoveUser(User);
		Util.printInfo("added user removed successfully");
		homePage.refresh();
		Util.sleep(4000);
		
		homePage.click("reporting");
		String IndividualGaugeinReportingafter11=homePage.getValueFromGUI("IndividualGaugeinReporting");
		String UsersConsumingafter11=homePage.getValueFromGUI("UsersConsuming");
		
		Util.printInfo("The sum of individual usage decreases after Deleting consumed User :: ");
		
		Util.printInfo("The sum of consumers decreases by one :: ");
		
		assertEquals(count, Integer.valueOf(UsersConsumingafter11));
		
		homePage.refresh();
		
		Util.sleep(4000);

		String UserName=homePage.createFieldWithParsedFieldLocatorsTokens("RemovedUserInByUser", User);
		homePage.verifyFieldNotExists(UserName);

		logoutMyAutodeskPortal();
		*/
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
