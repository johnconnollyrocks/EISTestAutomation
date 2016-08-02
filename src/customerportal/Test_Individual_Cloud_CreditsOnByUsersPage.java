package customerportal;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Individual_Cloud_CreditsOnByUsersPage extends CustomerPortalTestBase{
	public String USERNAME = null;
	public String PASSWORD = null;
	SoapUIExampleTest suet = new SoapUIExampleTest();
	DOMXmlParser domParser = new DOMXmlParser();
	String UserRole=null;
	int count=0;
	int Eucount=0;
	String CMAdmingauge=null;
	String CMIndividualGaugebyUsers=null;
	

	public Test_Individual_Cloud_CreditsOnByUsersPage() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());		
	}
	@Test
	public void Test_Verify_Sorting_Of_Contracts_OnReportingPage()throws Exception {
		if (getEnvironment().equalsIgnoreCase("dev")) {
			USERNAME = testProperties.getConstant("USERNAME_DEV");
			PASSWORD = testProperties.getConstant("PASSWORD_DEV");
		} else if (getEnvironment().equalsIgnoreCase("stg")) {
			USERNAME = testProperties.getConstant("USER_NAME_STG");
			PASSWORD = testProperties.getConstant("PASSWORD_STG");
		}
		loginAsMyAutodeskPortalUser(USERNAME , PASSWORD);
		
		homePage.click("reporting");
		 Util.sleep(2000);
		homePage.click("byUsers");
		
		int ReportUsersList=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();
		
		for(int rownum=1;rownum<=ReportUsersList;rownum++)
		{
			homePage.clickAndWait("users","selectAllInUP");
			
			Util.printInfo("Clicking on each user to retrive Contract manager :: ");
			String EachUser=homePage.createFieldWithParsedFieldLocatorsTokens("EachUser", String.valueOf(rownum));
			homePage.click(EachUser);

			String AdminRole=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(rownum));
			UserRole=homePage.getValueFromGUI(AdminRole);
			Util.sleep(5000);
			
		    if(UserRole.contains("Contract Manager")){
			
			String EmailId=homePage.createFieldWithParsedFieldLocatorsTokens("EmailID", String.valueOf(rownum));
			String CMEmailId=homePage.getAttribute(EmailId, "title");
			
			String CMAdmin=homePage.createFieldWithParsedFieldLocatorsTokens("SCAdmin", String.valueOf(rownum));
			String ContractMangaer=homePage.getValueFromGUI(CMAdmin);
			Util.printInfo("Contract Mangaer found :"+ContractMangaer);
			homePage.click("reporting");
			 Util.sleep(4000);
			homePage.click("byUsers");
			for(int j=1;j<=ReportUsersList;j++)
			{
			
			String Users=homePage.createFieldWithParsedFieldLocatorsTokens("ByUsersList", String.valueOf(j));
			String ByUser=homePage.getValueFromGUI(Users);
			if(ByUser.equalsIgnoreCase(ContractMangaer)){				
				
				Util.printInfo("Checking Individual Gauge of Contract Manager :: ");
				CMAdmingauge=homePage.createFieldWithParsedFieldLocatorsTokens("AdminGauge", String.valueOf(j));
				homePage.verifyFieldExists(CMAdmingauge);
				CMIndividualGaugebyUsers=homePage.getValueFromGUI(CMAdmingauge);
				Util.printInfo("Individual Cloud Credit gauge value for Contract Manager " +ContractMangaer+" is " +CMIndividualGaugebyUsers);
				break;
			}
		 }
		break;
	  }
  }
		/*String GUIDRequest = testProperties.getConstant("REQUEST");
		String Request = testProperties.getConstant("ChargeRequest");*/
		String CurrentDate = suet.getCurrentDate();
		
		//=========================================
		//Using the newly developed SOAP client web services for UserService and Convergent Charging
		getUserByEmailSOAPService getUserGUID = null;
		getConvergentChargeSOAPService  ccSoapServce = null;
		if (getEnvironment().equalsIgnoreCase("dev")) {
			getUserGUID= new getUserByEmailSOAPService("https://devservices.autodesk.com/dm/UserService", USERNAME);
			String guidVal=getUserGUID.getUserByEmailResponse();			
			//call the convergent charge soap services
			ccSoapServce= new getConvergentChargeSOAPService("http://uspetdeccprx001:8080/quattroCloud/services/ConvergentCharging");
			String respStatus=ccSoapServce.getChargeEvent(testProperties.getConstant("SID"), guidVal, guidVal, testProperties.getConstant("SERVICECATEGORY"),
					testProperties.getConstant("QUANTITY"),  testProperties.getConstant("SERVICECODE"), CurrentDate,  testProperties.getConstant("PRODUCTLINECODE"));
			
			
		}else {
			// for STG
			getUserGUID= new getUserByEmailSOAPService("https://stageservices-usscl.autodesk.com/UserService", USERNAME);
			String guidVal=getUserGUID.getUserByEmailResponse();			
			//call the convergent charge soap services
			ccSoapServce= new getConvergentChargeSOAPService("https://quattro-stg-vip.autodesk.com/quattroCloud/services/ConvergentCharging");
			String respStatus=ccSoapServce.getChargeEvent(testProperties.getConstant("SID"), guidVal, guidVal, testProperties.getConstant("SERVICECATEGORY"),
					testProperties.getConstant("QUANTITY"),  testProperties.getConstant("SERVICECODE"), CurrentDate,  testProperties.getConstant("PRODUCTLINECODE"));
		}
		
		//=========================================
		String ActualRequest = null;
		/*if (getEnvironment().equalsIgnoreCase("dev")) {
		 ActualRequest=suet.ReplaceRequest(GUIDRequest,testProperties.getConstant("USERNAME_DEV"));
		 }
		else
		{
			ActualRequest=suet.ReplaceRequest(GUIDRequest,testProperties.getConstant("USER_NAME_STG"));	
		}
	//
		Util.printInfo("ActualRequest******************" +ActualRequest);
		File file = new File(System.getProperty("user.dir")
				+ "\\UserService.wsdl");
		String response = null;
		if (getEnvironment().equalsIgnoreCase("dev")) {
		
		 response = suet.getResponseForSoapRequest("webservice",
				"GetUserByEmail", file.getName(), ActualRequest,
				"https://devservices.autodesk.com/dm/UserService",
				getEnvironment());
		}
		else{
			response = suet.getResponseForSoapRequest("webservice",
					"GetUserByEmail", file.getName(), ActualRequest,
					"https://stageservices-usscl.autodesk.com/UserService",
					getEnvironment());
			
		}
		Document doc = domParser.getDocument(response);
		String GUID = null;
		NodeList Nodes = doc.getElementsByTagName("ns0:User");
		Util.printInfo(Nodes.getLength() + " : Nodes.getlength()");
		for (int s = 0; s < Nodes.getLength(); s++) {
			Node GUIDNode = domParser.getSubNode("ns0:GUID", Nodes.item(s));
			GUID = GUIDNode.getTextContent();
			Util.printInfo(GUIDNode.getTextContent());
		}*/
		/*String Request = testProperties.getConstant("ChargeRequest");
		String CurrentDate = suet.getCurrentDate();*/
		
		
		/*String StrRequsetXML = Request
				.replace("<UID>?</UID>", "<UID>" + GUID + "</UID>")
				.replace("<GUID>?</GUID>", "<GUID>" + GUID + "</GUID>")
				.replace(
						"<StartTime>?</StartTime>         <EndTime>?</EndTime> ",
						"<StartTime>" + CurrentDate
								+ "</StartTime>         <EndTime>"
								+ CurrentDate + "</EndTime>");
		File file1 = new File(System.getProperty("user.dir")
				+ "\\ConvergentCharging.wsdl");
		String ChargeEventResponse = null;
		if (getEnvironment().equalsIgnoreCase("dev")) {
		 ChargeEventResponse = suet
				.getSoapRequestForChargeEvent(
						"ChargeEvent",
						"ChargeEvent",
						file1.getName(),
						StrRequsetXML,
						"http://uspetdeccprx001:8080/quattroCloud/services/ConvergentCharging",
						getEnvironment());
		}
		else {
			ChargeEventResponse = suet
					.getSoapRequestForChargeEvent(
							"ChargeEvent",
							"ChargeEvent",
							file1.getName(),
							StrRequsetXML,
							"https://quattro-stg-vip.autodesk.com/quattroCloud/services/ConvergentCharging",
							getEnvironment());
		}
		Document doc1 = domParser.getDocument(ChargeEventResponse);
		String Status = null;
		NodeList Nodes1 = doc1.getElementsByTagName("ns2:ChargeEventResponse");
		for (int s = 0; s < Nodes1.getLength(); s++) {
			Node StatusNode = domParser.getSubNode("ResponseStatus",
					Nodes1.item(s));
			Status = StatusNode.getTextContent();
			assertEquals("Individual cloud credits consumption is successful ","OK", Status);
		}
		if (!Status.trim().equalsIgnoreCase("OK")) {
			EISTestBase.fail("Error in Charge Event Response");
		}*/
		homePage.refresh();
		Util.sleep(50000);
		homePage.click("reporting");
		Util.sleep(30000);
		homePage.click("byUsers");
		Util.sleep(3000);
		CMIndividualGaugebyUsers=homePage.getValueFromGUI(CMAdmingauge);
		Util.printInfo("Navigating to reporting page :: ");
		homePage.click("reporting");
		
		Util.sleep(15000);
		homePage.refresh();
		Util.sleep(2000);
		Util.printInfo("The aggregate summary :: "+CMIndividualGaugebyUsers);
		
		String IndividualGaugeinReportingafter=homePage.getValueFromGUI("IndividualGaugeinReporting");
		String UsersConsumingafter=homePage.getValueFromGUI("UsersConsuming");
		assertEquals(Integer.valueOf(CMIndividualGaugebyUsers), Integer.valueOf(IndividualGaugeinReportingafter));
		
		assertEquals("Number of Users cloud credits consuming in reporting page ","1",UsersConsumingafter);
		
		Util.printInfo("The aggregate summary is equal to the individual usage of the CM");
			 
		logoutMyAutodeskPortal();	
	}
		@After
		public void tearDown() throws Exception {
			driver.quit();
			finish();
		}

}
