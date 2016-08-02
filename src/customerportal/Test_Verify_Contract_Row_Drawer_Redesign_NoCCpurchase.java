package customerportal;

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
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_Contract_Row_Drawer_Redesign_NoCCpurchase extends CustomerPortalTestBase {
	public Test_Verify_Contract_Row_Drawer_Redesign_NoCCpurchase() throws IOException {
		super("Browser",getAppBrowser());
		System.setProperty("testName", "Test_Verify_Contract_Row_Drawer_Redesign_NoCCused");
		System.setProperty("testPropertiesFilenameKey", "TEST_VERIFY_NO_CC_USED_PROPERTIES_FILE");
		super.setup();
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	
	@Test
	public void Test_VerifyContractRowDrawerRedesign_NoCCused() throws Exception {
		
		Util.printInfo("Logging into CM's Account");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		String contract_num=null;
		boolean flag=true;
		Util.printInfo("Verifying Contract Row-Drawer Redesign cloud credit usage for no cloud credit usage ");
		Util.printInfo("Navigating to Reporting Page");
		homePage.click("reporting");
		homePage.waitForFieldPresent("IndividualCloudCreditsOnReportingPage");
		
		int totalContracts = driver.findElements(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/article")).size();		
		Util.printInfo("TotalContracts in reporting : "+totalContracts);
		
		for(int row=1;row<=totalContracts;row++){
			
		contract_num = driver.findElement(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/article["+row+"]/*[@class='contract-summary']/*[@class='left-col']/*[@class='inner']/div[1]/*[@class='value']")).getText().trim();
			  
		String ContractHasCloudCredits=homePage.createFieldWithParsedFieldLocatorsTokens("ContractHasCloudCredits", contract_num);
		Util.printInfo(" clicking on contract drawer");
		 
		 if(!homePage.getValueFromGUI(ContractHasCloudCredits).contains("No Cloud Credits Purchased")){
			 flag=false;
			driver.findElement(By.xpath(".//*[@id='contract-"+contract_num+"']/div[@class='btn-toggle-drawer']/button")).click();
			break;
		 }
		}
		
		if(flag){
			EISTestBase.fail("No cloud credits purchased for any contract: please chose any other CM who has used cloud credits...");
		}
//		 homePage.click("contractDrawerNoCCused");
		 Util.sleep(2000);	 
		
		
		SoapUIExampleTest soapUIExampleTest = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
	
		String reqXML = "";
		String toBeReplaced = "" ; 
		String requestStr = "<"+"ContractNumber"+">?</"+"ContractNumber"+">";		
			  toBeReplaced = "<"+"ContractNumber"+">"+ contract_num +"</"+"ContractNumber"+">";
			  reqXML = reqXML + toBeReplaced;
		
		
		
		Util.printInfo("reqXML : "+reqXML);
		reqXML =(testProperties.getConstant("REQUEST")).replace(requestStr, reqXML); 
		Util.printInfo("reqXML : "+reqXML);
		String response = soapUIExampleTest.getResponseForSoapRequest(testProperties.getConstant("STR_APPEND"),testProperties.getConstant("REQUEST_METHOD"),testProperties.getConstant("WSDL_NAME"), reqXML,testProperties.getConstant("ENDPOINT_URL"),getEnvironment());
		
	//	Util.printInfo("response : " + response);
		Document doc = domParser.getDocument(response);
		
		NodeList ccTransactionNodes = doc.getElementsByTagName("CcTransaction");
		
		Util.printInfo("ccTransactionNodes.getLength() : "+ccTransactionNodes.getLength());
	    
	    	Node unitType ;
	    	Node txnType ;
	    	Node serviceCode ;
	    	Node contractNode ;
	    	Node txnUnits ;
	    	Node assignedClouds ;
	    	int cloud_consumed = 0;	
			int cloud_assigned = 0;
			int cloud_remaining = 0;
			boolean ticket=true;
	    	
		for(int s=0;s<ccTransactionNodes.getLength() ; s++ ){
			  unitType = domParser.getSubNode("UnitType", ccTransactionNodes.item(s) );
			  txnType = domParser.getSubNode("TxnType", ccTransactionNodes.item(s) );
			  serviceCode = domParser.getSubNode("ServiceCode", ccTransactionNodes.item(s) );
			  contractNode = domParser.getSubNode("ContractNumber", ccTransactionNodes.item(s) );
			 
			
			//added credits: ADD PLC + NA + C3
			//used credits :CLOUD_CONSUMPTION + c3
			//if contract doesnt have only ADD PLC & no CLOUD_CONSUMPTION then no credits being consumed. 
			//remaining credits : added credits - used credits
			
			if(contractNode.getTextContent().contentEquals(contract_num)){
				
				if(unitType.getTextContent().contentEquals("c3")){
					Util.printInfo("txn Type:" +txnType.getTextContent());
					if( txnType.getTextContent().contentEquals("CLOUD_CONSUMPTION")){
					  ticket=false;
					  txnUnits = domParser.getSubNode("TxnUnits", ccTransactionNodes.item(s) );
					
					//this much credits being used in this contract
					cloud_consumed = Integer.parseInt(txnUnits.getTextContent()) + cloud_consumed;
					
					} 					
					if( txnType.getTextContent().contentEquals("ADD PLC") && serviceCode.getTextContent().contentEquals("NA") ){
					 assignedClouds = domParser.getSubNode("TxnUnits", ccTransactionNodes.item(s) );
						cloud_assigned = Integer.parseInt(assignedClouds.getTextContent());
					}
					
				}
			
			} //big if
			
			cloud_remaining = cloud_assigned - cloud_consumed ;
			 Util.printInfo("Contract Number   : "+ contract_num.toString());
			 Util.printInfo("Credits assigned  : "+ cloud_assigned );
			 Util.printInfo("Credits consumed  : "+ cloud_consumed );
			 Util.printInfo("Credits remaining : "+ cloud_remaining); 
		} // inside for
		
	String cloudCredUsedNumeric=homePage.createFieldWithParsedFieldLocatorsTokens("cloudCredUsedNumeric", contract_num);
	String CloudCredUsedfromUI=homePage.getValueFromGUI(cloudCredUsedNumeric);
	
	Util.printInfo("validating cloud credits from UI and Web Service call.. ");
	
	if(cloud_consumed==0){
		String clouds_consumed="No cloud credits used on any service";
		assertEquals(CloudCredUsedfromUI,clouds_consumed);	
	}else{
	assertEquals(CloudCredUsedfromUI, String.valueOf(cloud_consumed));
	}
	
	if(ticket){
		 Util.printInfo(" The contract : "+contract_num+" has no cloud credits used");
		
		 Util.printInfo("verifying fields under contract drawer");
		 String UsageByService=homePage.createFieldWithParsedFieldLocatorsTokens("UsageByService", contract_num);
		 homePage.verifyFieldExists(UsageByService);
		 
		 String accessCloudSvcLink=homePage.createFieldWithParsedFieldLocatorsTokens("accessCloudSvcLink", contract_num);
		 if(homePage.isFieldPresent(accessCloudSvcLink)){
		 homePage.verifyFieldExists(accessCloudSvcLink);
		 }
		 
		 String trynLrnLink=homePage.createFieldWithParsedFieldLocatorsTokens("trynLrnLink", contract_num);
		 if(homePage.isFieldPresent(trynLrnLink)){
		 homePage.verifyFieldExists(trynLrnLink);
		 }
		 Util.printInfo("All contracts are verified");
//		 EISTestBase.fail("No cloud credits purchased for any contract: please chose any other CM who has used cloud credits...");
		
	      }
		 
		 
		 /************* Validating ws response with UI values **************/ 
		 
		 /************no cc purchase *******************/
		 
		
		 
		 
		 
		 /************end of no cc purchase ********************/ 
		 
		 
	    
		
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
