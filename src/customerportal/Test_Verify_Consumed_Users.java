package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.SoapUIExampleTest;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_Consumed_Users extends CustomerPortalTestBase {
	
	
	public Test_Verify_Consumed_Users() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_Consumed_Users_method() throws Exception {
		//setDebugMode(true);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		if(getEnvironment().equalsIgnoreCase("DEV")){
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		}

		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		HashMap<String, Integer> usersConsuming = getNumberOfUsersAssigned(driver);
		Util.printInfo("usersConsuming : "+usersConsuming);
		homePage.click("reporting");
		Util.sleep(5000);
		Set<String> contractsList = usersConsuming.keySet();
		int totalConsumption = 0;
		for(String contract : contractsList){
			
			Map<String,String> requestProperties = new HashMap<>();
			requestProperties.put("ContractNumber", contract);
			
			String ExpiredContract=driver.findElement(By.xpath(".//*[@id='contract-"+contract+"']/div[2]/div[1]")).getAttribute("class");
			
			if(!ExpiredContract.trim().equalsIgnoreCase("contract-details")){
			
			String Request=testProperties.getConstant("REQUEST");
			String StrRequsetXML=Request.replace("<ContractNumber>?</ContractNumber>", "<ContractNumber>"+contract+"</ContractNumber>");		
			System.out.println("Request XML:"+StrRequsetXML);
			
			File file = new File(System.getProperty("user.dir")+"\\build\\ConvergentCharging.wsdl");
			
			String response = suet.getResponseForSoapRequest(contract,"querySubscriptionBalance", file.getName(), StrRequsetXML,"https://quattro-stg-vip.autodesk.com/quattroCloud/services/ConvergentCharging",getEnvironment());
		
			Document doc = domParser.getDocument(response);

			NodeList contracts = doc.getElementsByTagName("Contract");
			
			Node contractNode = domParser.getParentNodeFromSetOfNodeList("ContractNumber", contract,contracts);
			Node ListOfServicePrivileges = domParser.getSubNode("ListOfServicePrivileges", contractNode);
			Node ServicePrivilege = domParser.getParentNodeFromSetOfNodeList("Description","Cloud Credits", ListOfServicePrivileges.getChildNodes());
			
			Node ListOfUnits = domParser.getSubNode("ListOfUnits", ServicePrivilege);
			
			Node provisionedUnit = domParser.getParentNodeFromSetOfNodeList("Type","provisioned", ListOfUnits.getChildNodes());
			Node availableUnit = domParser.getParentNodeFromSetOfNodeList("Type","available", ListOfUnits.getChildNodes());
			Node consumedUnit = domParser.getParentNodeFromSetOfNodeList("Type","consumed", ListOfUnits.getChildNodes());
			
			String provisioned = domParser.getNodeValue(domParser.getSubNode("Value", provisionedUnit));
			String available = domParser.getNodeValue(domParser.getSubNode("Value", availableUnit));
			String consumed = domParser.getNodeValue(domParser.getSubNode("Value", consumedUnit));
			String Validate=homePage.createFieldWithParsedFieldLocatorsTokens("Validate", contract);
			Util.printInfo("provisioned : "+provisioned);
			Util.printInfo("available : "+available);
			Util.printInfo("consumed : "+consumed);
			
			 if(provisioned.equals("0") && ! homePage.getValueFromGUI(Validate).contains("Remaining")){
				 String NoContractsPurchased=homePage.createFieldWithParsedFieldLocatorsTokens("ContractsAssigned", contract);
				 String ContractStatus=homePage.getValueFromGUI(NoContractsPurchased);
				 
				 assertTrue("No Cloud Credits Purchased:",ContractStatus.trim().equalsIgnoreCase("No Cloud Credits Purchased"));
			 }else{
				 String ContractButton=homePage.createFieldWithParsedFieldLocatorsTokens("ContractButton", contract);
				 homePage.click(ContractButton);
				 
				 String UsedServices=homePage.createFieldWithParsedFieldLocatorsTokens("UsedServices", contract);
				 String ContractServices=homePage.getValueFromGUI(UsedServices);	
				 if(ContractServices.contains("No cloud credits used on any service")){
				 Util.printInfo("ContractServices"+ContractServices);				 
				 assertTrue("No cloud credits used on any service:",ContractServices.trim().contains("No cloud credits used on any service"));
				 }else{
					String TotalCloudCreditsConsumed=homePage.createFieldWithParsedFieldLocatorsTokens("CloudCreditsUsedCP", contract);
					homePage.verifyFieldExists(TotalCloudCreditsConsumed);
					Util.printInfo("CC consumed for this contract :"+homePage.getValueFromGUI(TotalCloudCreditsConsumed));
				  }
				 }
				 
			 if(!provisioned.equals("0")){
			
			Util.printInfo("contract : "+contract);
//			String contractFromGui = driver.findElement(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/*[@id='contract-"+contract+"']//*[@class='consuming']//*[@class='users']")).getText();
			String availableFromGui = driver.findElement(By.xpath(".//*[@id='contract-"+contract+"']//*[@class='available']/span")).getText();
			String consumedFromGui = driver.findElement(By.xpath(".//*[@id='contract-"+contract+"']//*[@class='consumed']/span")).getText();
//			Util.printInfo("contractFromGui : "+contractFromGui);
			int indivitualConsuming = usersConsuming.get(contract);
//			assertEquals(contractFromGui, String.valueOf(indivitualConsuming));
			assertEquals(consumedFromGui, String.valueOf(consumed));
			assertEquals(availableFromGui, String.valueOf(available));
			
			totalConsumption = totalConsumption + indivitualConsuming;
		}
	  }else{
		  
		  Util.printInfo("The Contract "+contract+" has been Expired ");
	  }
	}
		Util.printInfo("totalConsumption : "+totalConsumption);
//		assertEquals(totalConsumption, Integer.parseInt(homePage.getValueFromGUI("individualConsuming")));
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
