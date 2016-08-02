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

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Raghavendra Yadav
 * @version 1.0.0
 */
public final class Test_Verify_CM_IndividualUsage extends CustomerPortalTestBase {
	
	
	public Test_Verify_CM_IndividualUsage() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {	
		launchMyAutodeskPortal(getBaseURL()); 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		
		Util.printInfo("Logging into CM's Account ");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CMUSER") , testProperties.getConstant("CMPASSWORD"));
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		String UserRole=null;
		int count=0;
		int Eucount=0;
		String CMAdmingauge=null;
		String CMIndividualGaugebyUsers=null;
		
//		homePage.clickAndWait("users","selectAllInUP");
		
		/*int UsageReport=driver.findElements(By.xpath(".//*[@id='results']/li/section/div[2]")).size();
		
		for(int rownum=1;rownum<UsageReport;rownum++){
			
				String EachUser=homePage.createFieldWithParsedFieldLocatorsTokens("EachUser", String.valueOf(rownum));
				homePage.click(EachUser);

				String AdminRole=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(rownum));
				UserRole=homePage.getValueFromGUI(AdminRole);
				Util.sleep(5000);
			
		if(UserRole.contains("End User") && UserRole.contains("")){
			  Eucount=Eucount+1;
			  String User=homePage.createFieldWithParsedFieldLocatorsTokens("UserNames", String.valueOf(rownum));
			  String Username=homePage.getValueFromGUI(User);
			  Util.printInfo("Removing End User :: ");
			  RemoveUser(Username);
			  Util.sleep(4000);
			  Util.printInfo("End user Removed successfully..");
			  Util.sleep(4000);
			  homePage.refresh();
		}
	  }*/
		
		
		homePage.click("reporting");
		 Util.sleep(4000);
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
			System.out.println("*******************************************116*****************************************************");
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
		System.out.println("*******************************************130*****************************************************");
		String GUIDRequest = testProperties.getConstant("REQUEST");
		String ActualRequest = suet.ReplaceRequest(GUIDRequest, testProperties.getConstant("CMUSER"));
		File file = new File(System.getProperty("user.dir")
				+ "\\build\\UserService.wsdl");

		String response = suet.getResponseForSoapRequest("webservice",
				"GetUserByEmail", file.getName(), ActualRequest,
				"https://stageservices-usscl.autodesk.com/UserService",
				getEnvironment());
		
		System.out.println("*******************************************137*****************************************************");
		Document doc = domParser.getDocument(response);
		String GUID = null;
		NodeList Nodes = doc.getElementsByTagName("ns0:User");
		Util.printInfo(Nodes.getLength() + " : Nodes.getlength()");
		for (int s = 0; s < Nodes.getLength(); s++) {
			Node GUIDNode = domParser.getSubNode("ns0:GUID", Nodes.item(s));
			GUID = GUIDNode.getTextContent();
			Util.printInfo(GUIDNode.getTextContent());
		}
		System.out.println("*******************************************148*****************************************************");
		String Request = testProperties.getConstant("ChargeRequest");
		String CurrentDate = suet.getCurrentDate();
		
		String StrRequsetXML = Request
				.replace("<UID>?</UID>", "<UID>" + GUID + "</UID>")
				.replace("<GUID>?</GUID>", "<GUID>" + GUID + "</GUID>")
				.replace(
						"<StartTime>?</StartTime>         <EndTime>?</EndTime> ",
						"<StartTime>" + CurrentDate
								+ "</StartTime>         <EndTime>"
								+ CurrentDate + "</EndTime>");
		System.out.println("*******************************************160*****************************************************");
		File file1 = new File(System.getProperty("user.dir")
				+ "\\build\\ConvergentCharging.wsdl");
		System.out.println("*******************************************163*****************************************************");
		String ChargeEventResponse = suet
				.getSoapRequestForChargeEvent(
						"ChargeEvent",
						"ChargeEvent",
						file1.getName(),
						StrRequsetXML,
						"https://quattro-stg-vip.autodesk.com/quattroCloud/services/ConvergentCharging",
						getEnvironment());
		System.out.println("*******************************************172*****************************************************");
		Document doc1 = domParser.getDocument(ChargeEventResponse);
		String Status = null;
		NodeList Nodes1 = doc1.getElementsByTagName("ns2:ChargeEventResponse");
		for (int s = 0; s < Nodes1.getLength(); s++) {
			Node StatusNode = domParser.getSubNode("ResponseStatus",
					Nodes1.item(s));
			Status = StatusNode.getTextContent();
			assertEquals("Individual cloud credits consumption is successful ","OK", Status);
		}
		System.out.println("*******************************************182*****************************************************");
		if (!Status.trim().equalsIgnoreCase("OK")) {
			EISTestBase.fail("Error in Charge Event Response");
		}
		System.out.println("*******************************************186*****************************************************");
		homePage.refresh();
		Util.sleep(50000);
		System.out.println("*******************************************189*****************************************************");
		homePage.click("reporting");
		Util.sleep(30000);
		homePage.click("byUsers");
		Util.sleep(3000);
		CMIndividualGaugebyUsers=homePage.getValueFromGUI(CMAdmingauge);
		Util.printInfo("Navigating to reporting page :: ");
		homePage.click("reporting");
		
		Util.sleep(15000);
		System.out.println("*******************************************199*****************************************************");
		Util.printInfo("The aggregate summary :: "+CMIndividualGaugebyUsers);
		
		String IndividualGaugeinReportingafter=homePage.getValueFromGUI("IndividualGaugeinReporting");
		String UsersConsumingafter=homePage.getValueFromGUI("UsersConsuming");
		System.out.println("*******************************************204*****************************************************");
		assertEquals(Integer.valueOf(CMIndividualGaugebyUsers), Integer.valueOf(IndividualGaugeinReportingafter));
		
		assertEquals("Number of Users cloud credits consuming in reporting page ","1",UsersConsumingafter);
		
		Util.printInfo("The aggregate summary is equal to the individual usage of the CM");
			 
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
