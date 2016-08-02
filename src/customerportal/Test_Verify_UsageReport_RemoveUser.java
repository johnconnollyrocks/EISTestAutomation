package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
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
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_UsageReport_RemoveUser extends CustomerPortalTestBase {
	
	
	public Test_Verify_UsageReport_RemoveUser() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_UsageReport_RemoveUser_method() throws Exception {
		// setDebugMode(true);
		String USERNAME = null;
		String EMAIL = null;
		String USER_NAME = null;
		String PASSWORD = null;
		String INSTANCE_NAME = null;

		if (getEnvironment().equalsIgnoreCase("DEV")) {
			USERNAME = testProperties.getConstant("USERNAME_DEV");
			EMAIL = testProperties.getConstant("EMAIL_DEV");
			USER_NAME = testProperties.getConstant("USER_NAME_DEV");
			PASSWORD = testProperties.getConstant("PASSWORD_DEV");
			INSTANCE_NAME = "NEW_USER_DEV";

		} else if (getEnvironment().equalsIgnoreCase("STG")) {
			USERNAME = testProperties.getConstant("USER_NAME_STG");
			EMAIL = testProperties.getConstant("EMAIL_STG");
			USER_NAME = testProperties.getConstant("USER_NAME_STG");
			PASSWORD = testProperties.getConstant("PASSWORD_STG");
			INSTANCE_NAME = "NEW_USER_STG";
		}

		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		String CMName = testProperties.getConstant("CMNAME");
		Util.printInfo("Logged into CM's account");
		loginAsMyAutodeskPortalUser(USER_NAME, PASSWORD);

		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);

		homePage.clickAndWait("reporting", "byUsers");
		homePage.click("byUsers");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);
		String individualUsed = null;
		int interval;
		int timeout;
		boolean reflected = false;
		long startTime;
		long endTime;
		Map<String, String> contractsWithCredits = new HashMap<>();

		String contracts = ".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'TOKEN1')]/ancestor::tr//td[@class='pooled-usage']//tr";

		/*
		 * String specificUserRowInByUsersPage=homePage.
		 * createFieldWithParsedFieldLocatorsTokens
		 * ("specificUserRowInByUsersPage", USERNAME);
		 * 
		 * homePage.verifyFieldExists(specificUserRowInByUsersPage);
		 */

		/*
		 * String
		 * specificIndividualUsed=homePage.createFieldWithParsedFieldLocatorsTokens
		 * ("specificIndividualUsed", USERNAME); individualUsed =
		 * homePage.getValueFromGUI(specificIndividualUsed);
		 */

		/*
		 * contracts = contracts.replace("TOKEN1", CMName); int size =
		 * driver.findElements(By.xpath(contracts)).size(); for(int
		 * i=1;i<=size;i++){ String contractNumber =
		 * driver.findElement(By.xpath(
		 * contracts+"["+i+"]//td[@class='number']/span")).getText();
		 * contractNumber
		 * =contractNumber.substring(contractNumber.indexOf("#")+1).trim();
		 * Util.printInfo("contractNumber : "+contractNumber);
		 * 
		 * String creditsUsed = driver.findElement(By.xpath(contracts+"["+i+
		 * "]//td[@class='usage']//*[@class='value']")).getText();
		 * Util.printInfo("creditsUsed : "+creditsUsed);
		 * 
		 * contractsWithCredits.put(contractNumber, creditsUsed); }
		 */

		homePage.click("reporting");
		homePage.click("byUsers");
		/*
		 * String UsersEmail=testProperties.getConstant("EMAIL_STG"); String
		 * EMAIL1=UsersEmail+getUniqueId()+"@ssttest.net"+" ";
		 */
		String EMAIL1 = homePage.getConstant("EMAIL_STG");
		String EmailId = EMAIL1 + getUniqueId() + "@ssttest.net";
		String fstname=RandomStringUtils.randomAlphabetic(5);
		String USERNAME1 = fstname+" "+"User";
		String UserRole=null;
		String EUEmailId=null;
		String EndUser=null;
		String UserCredits=null;
		boolean ticket=false;
		
		int ReportUsersList=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();
		Util.sleep(4000);
		homePage.clickAndWait("users", "selectAllInUP");
		for(int rownum=1;rownum<=ReportUsersList;rownum++)
		{
			Util.printInfo("Clicking on each user to retrive existing Enduser:");
			String EachUser=homePage.createFieldWithParsedFieldLocatorsTokens("EachUser", String.valueOf(rownum));
			homePage.click(EachUser);

			String EndUserRole=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(rownum));
			UserRole=homePage.getValueFromGUI(EndUserRole);
			Util.sleep(5000);
			
		if(UserRole.contains("End User") || UserRole.equals(" ")){
			
			String EmailId1=homePage.createFieldWithParsedFieldLocatorsTokens("EmailID", String.valueOf(rownum));
			EUEmailId=homePage.getAttribute(EmailId1, "title");
			
			String SCAdmin=homePage.createFieldWithParsedFieldLocatorsTokens("SCAdmin", String.valueOf(rownum));
			EndUser=homePage.getValueFromGUI(SCAdmin);
			Util.printInfo("End User found :"+EndUser);
			String EditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("EditAccess", String.valueOf(rownum));
			homePage.click(EditAccess);
			unCheckChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
			unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
			checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
			unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
			Util.printInfo("disassociated the user from all the contracts... ");
			Util.printInfo("Navigating to Reporting By users Page...");
			homePage.click("saveButton");
			homePage.click("reporting");
			homePage.click("byUsers");
			break;
			}
		}
		
		for(int k = 1;k<=ReportUsersList;k++)
		{ 
			String NewUser=homePage.createFieldWithParsedFieldLocatorsTokens("UsagebyUser", String.valueOf(k));
			String ExistingEndUser=homePage.getValueFromGUI(NewUser);
		 if(EndUser.trim().equalsIgnoreCase(ExistingEndUser.trim())){
			ticket=true;
			Util.sleep(5000);
			
			Util.printInfo("verifying individual credits row ::");
			
			String ExistingUserCredits=homePage.createFieldWithParsedFieldLocatorsTokens("UserCredits", String.valueOf(k));
			UserCredits=homePage.getValueFromGUI(ExistingUserCredits);
	//		assertEquals("The individual usage gauge is still seen for user " +EndUser+ " after disassociating all contracts also  :: ","0", UserCredits.trim());
			List<WebElement> pooledContractsSize=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+EndUser+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));
			if(pooledContractsSize.size()!=0){
				Util.printInfo("Gauge is shwoing under pooled column for this user ::" +EndUser);
			}
			 break;
		  }
		}
		
		homePage.clickAndWait("users", "selectAllInUP");
		
		Util.printInfo("Assigning contracts back to the End user.. "+EndUser);
		String assignContracts=homePage.createFieldWithParsedFieldLocatorsTokens("editEndUserEditAccess", EndUser);
		homePage.click(assignContracts);
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		homePage.click("saveButton");
		homePage.refresh();
		Util.sleep(2000);
		addUser(EmailId, fstname);		

		
		/*
		 * String editAccessForSpecUserNAme=homePage.
		 * createFieldWithParsedFieldLocatorsTokens("editAccessForSpecUserNAme",
		 * USERNAME1); homePage.click(editAccessForSpecUserNAme);
		 */

		// CheckChecKBox("inputForProductsDownloadCBInEditAccess",
		// "labelForProductsDownloadCBInEditAccess");
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		// unCheckChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		// unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");

		homePage.click("saveButton");
		Util.sleep(10000);
		homePage.clickAndWait("reporting", "byUsers");
		homePage.click("byUsers");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);
		System.out.println("***************************************236******************************************");
		String NewlyAddedUserInByUsersPage = homePage
				.createFieldWithParsedFieldLocatorsTokens(
						"specificUserRowInByUsersPage", USERNAME1);
		
		System.out.println("***************************************241******************************************");
		homePage.verifyFieldExists(NewlyAddedUserInByUsersPage);
		System.out.println("***************************************243******************************************");
		String specificIndividualUsed1 = homePage
				.createFieldWithParsedFieldLocatorsTokens(
						"specificIndividualUsed", USERNAME1);
		assertEquals("The Cloud Gauge for new User :", "0",
				homePage.getValueFromGUI(specificIndividualUsed1));
		System.out.println("***************************************249******************************************");
		/*
		 * int size1 = driver.findElements(By.xpath(contracts)).size(); for(int
		 * i=1;i<=size1;i++){ String contractNumber =
		 * driver.findElement(By.xpath
		 * (contracts+"["+i+"]//td[@class='number']/span")).getText();
		 * contractNumber
		 * =contractNumber.substring(contractNumber.indexOf("#")+1).trim();
		 * Util.printInfo("contractNumber : "+contractNumber);
		 * 
		 * String creditsUsed = driver.findElement(By.xpath(contracts+"["+i+
		 * "]//td[@class='usage']//*[@class='value']")).getText();
		 * Util.printInfo("creditsUsed : "+creditsUsed);
		 * 
		 * assertEquals("0", contractsWithCredits.get(contractNumber));
		 * 
		 * }
		 */
		// String uniqueString=RandomStringUtils.randomAlphabetic(5);

		String GUIDRequest = testProperties.getConstant("REQUEST");
		String ActualRequest = suet.ReplaceRequest(GUIDRequest, EmailId);

		File file = new File(System.getProperty("user.dir")
				+ "\\build\\UserService.wsdl");
		System.out.println("***************************************274******************************************");
		String response = suet.getResponseForSoapRequest("webservice",
				"GetUserByEmail", file.getName(), ActualRequest,
				"https://stageservices-usscl.autodesk.com/UserService",
				getEnvironment());
		System.out.println("***************************************279******************************************");
		Document doc = domParser.getDocument(response);
		String GUID = null;
		NodeList Nodes = doc.getElementsByTagName("ns0:User");
		Util.printInfo(Nodes.getLength() + " : Nodes.getlength()");
		for (int s = 0; s < Nodes.getLength(); s++) {
			Node GUIDNode = domParser.getSubNode("ns0:GUID", Nodes.item(s));
			GUID = GUIDNode.getTextContent();
			Util.printInfo(GUIDNode.getTextContent());
		}
		System.out.println("***************************************289******************************************");
		homePage.clickAndWait("reporting", "byUsers");
		homePage.click("byUsers");

		String Request = testProperties.getConstant("ChargeRequest");
		String CurrentDate = suet.getCurrentDate();
		String Contracts = null;
		boolean flag = true;

		List<WebElement> NumberofContractsUnderCM = driver
				.findElements(By
						.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"
								+ USERNAME1
								+ "')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));

		for (WebElement Contract : NumberofContractsUnderCM) {
			Contracts = Contract.getText();
			String NoCloudCredits = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//td[@class='name']/label[contains(text(),'"+USERNAME1+"')]/ancestor::td[@class='name']/following-sibling::td[contains(@class,'pooled')]//tr/td/span[contains(text(),'"+Contracts+"')]/ancestor::td/following-sibling::td")).getText();
			if (!NoCloudCredits.trim().equalsIgnoreCase("No Cloud Credits Purchased")) {
				String parsedContractValue = driver
						.findElement(
								By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"
										+ USERNAME1
										+ "')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"
										+ Contracts
										+ "')]/ancestor::td[@class='number']/following-sibling::td"))
						.getText();
				if (!parsedContractValue.trim().contains(
						"No Cloud Credits Purchased")) {
					flag = false;
					break;
				}
			}
		}
		System.out.println("***************************************323******************************************");
		if (flag) {
			EISTestBase.fail("No contracts have cloud credits purchased");
		}
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
		System.out.println("***************************************348******************************************");
		Document doc1 = domParser.getDocument(ChargeEventResponse);
		String Status = null;
		NodeList Nodes1 = doc1.getElementsByTagName("ns2:ChargeEventResponse");
		for (int s = 0; s < Nodes1.getLength(); s++) {
			Node StatusNode = domParser.getSubNode("ResponseStatus",
					Nodes1.item(s));
			Status = StatusNode.getTextContent();
			assertEquals("OK", Status);
		}

		if (!Status.trim().equalsIgnoreCase("OK")) {
			EISTestBase.fail("Error in Charge Event Response");
		}
		Util.sleep(2000);
		homePage.clickAndWait("users", "selectAllInUP");
		homePage.refresh();
		RemoveUser(USERNAME1);
		Util.printInfo("User Removed Successfully");
		/*
		 * String
		 * specificRemoveLink=homePage.createFieldWithParsedFieldLocatorsTokens
		 * ("specificRemoveLink", USERNAME1);
		 * homePage.click(specificRemoveLink);
		 * 
		 * String specificConfirmRemoveButton=homePage.
		 * createFieldWithParsedFieldLocatorsTokens("RemoveLink", USERNAME1);
		 * homePage.click(specificConfirmRemoveButton); Util.sleep(5000);
		 */
		System.out.println("***************************************377******************************************");
		homePage.clickAndWait("reporting", "byUsers");
		homePage.click("byUsers");
		Util.sleep(5000);
		System.out.println("***************************************381******************************************");
		homePage.refresh();
		Util.sleep(50000);
		homePage.click("logout");
		System.out.println("***************************************385******************************************");
		loginAsMyAutodeskPortalUser(USER_NAME, PASSWORD);
		Util.sleep(5000);
		homePage.clickAndWait("reporting", "byUsers");
		homePage.click("byUsers");
		Util.sleep(5000);
		String specificIndividualRemovedCol = homePage
				.createFieldWithParsedFieldLocatorsTokens(
						"specificIndividualRemovedCol", USERNAME1);
		homePage.clickAndWait("reporting", "byUsers");
		homePage.click("byUsers");
		Util.printInfo("Verifying pool cloud credits used by the user :: ");
		homePage.refresh();
		Util.sleep(50000);
		System.out.println("***************************************399******************************************");
//		String poolcloudcreditsusedbytheNewuser=homePage.createFieldWithParsedFieldLocatorsTokens("poolcloudcreditsusedbytheuser", USERNAME1);
//		String poolcloudcreditsusedbytheuser=homePage.getValueFromGUI(poolcloudcreditsusedbytheNewuser);
//		Util.printInfo("pooled credits used for new user is :: "+poolcloudcreditsusedbytheuser);
		/*if(homePage.isFieldVisible(poolcloudcreditsusedbytheNewuser)){
		homePage.verifyFieldExists(poolcloudcreditsusedbytheNewuser);
		}else{
			EISTestBase.fail("pooled credits gauge is not pressent for new user :: "+USERNAME1);
		}*/
		
		interval = 2000;
		timeout = CustomerPortalConstants.POOLED_CREDITS_TIMEOUT;
		reflected = false;
		startTime = System.currentTimeMillis();
		endTime = startTime + timeout;
		System.out.println("***************************************408******************************************");
		while (System.currentTimeMillis() < endTime) {
			
			if (homePage.isFieldPresent(specificIndividualRemovedCol)) {
				reflected = true;
				assertEquals(
						homePage.getValueFromGUI(specificIndividualRemovedCol),
						"Removed");
				break;
			}
			else {
					Util.printInfo("Waiting for Removed button to Reflect");
					homePage.refresh();
					Util.sleep(5000);
			}
			
		}
		if(!reflected) {
			EISTestBase.fail("Removed button is not pressent even after waiting for long time");
		}
		
				/*
		 * addNewUserWithInstance(EMAIL,INSTANCE_NAME);
		 * 
		 * checkChecKBox("inputForProductsDownloadCBInEditAccess",
		 * "labelForProductsDownloadCBInEditAccess");
		 * checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		 * checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		 * 
		 * homePage.click("saveButton");
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
