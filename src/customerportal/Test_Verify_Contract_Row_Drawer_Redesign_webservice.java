package customerportal;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import java.util.List;
import java.util.Map;

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
 * Test class - Test_Verify_Contract_Row_Drawer_Redesign_webservice
 * 
 * @author Chaitra Shanti
 * @version 1.0.0
 */
public final class Test_Verify_Contract_Row_Drawer_Redesign_webservice extends CustomerPortalTestBase {
	public Test_Verify_Contract_Row_Drawer_Redesign_webservice() throws IOException {
		super("Browser",getAppBrowser());
		System.setProperty("testName", "Test_Verify_Contract_Row_Drawer_Redesign_webservice");
		System.setProperty("testPropertiesFilenameKey", "TEST_VERIFY_ROW_CONTRACT_DETAILS_WEBSERVICE_FILE");
		super.setup();
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	@Test
	public void Test_VerifyContractRowDrawerRedesign_method_webservice() throws Exception {
		
		Util.printInfo("Logging into CM's Account");		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.printInfo("Navigating to Reporting page ");
		Util.printInfo("Clicking on Reporting ");
		homePage.click("reporting");
		Util.sleep(3000);
		
		
		/*map1 : code - desc    -> from excel
		map2 : code - value   -> from webservice response
		map3 : desc - value   -> from map1 n map2
		map4 : desc - value   -> from GUI*/
//		TO read service codes from Excel
		HashMap<String, String> serviceCodeMap1 = ReadExcelData.getServiceCodes("ps.xls") ; 
		 Util.printInfo("Size of hashmap after call : "+ serviceCodeMap1.size());
		
		SoapUIExampleTest soapUIExampleTest = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		boolean flag=true;
		String reqXML = "";
		String toBeReplaced = "" ; 
		String requestStr = "<"+"ContractNumber"+">?</"+"ContractNumber"+">";		
		List<String> contractList = new ArrayList<>();
		
		int totalContracts = driver.findElements(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/article")).size();		
		Util.printInfo("totalContracts  : "+totalContracts);
		
		for(int row=1;row<=totalContracts;row++){
			
//			homePage.parseFieldLocatorsTokens("specificContractNum", String.valueOf(row));
			/*String parsedContractValue = homePage.createFieldWithParsedFieldLocatorsTokens("specificContractNum", String.valueOf(row));
			String contract_num = homePage.getValueFromGUI(parsedContractValue);*/
			String contract_num = driver.findElement(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/article["+row+"]/*[@class='contract-summary']/*[@class='left-col']/*[@class='inner']/div[1]/*[@class='value']")).getText().trim();
			  
			String ContractHasCloudCredits=homePage.createFieldWithParsedFieldLocatorsTokens("ContractHasCloudCredits", contract_num);
			
			if(!homePage.getValueFromGUI(ContractHasCloudCredits).contains("No Cloud Credits Purchased")){
			
//			  String contract_num = driver.findElement(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/article["+row+"]/*[@class='contract-summary']/*[@class='left-col']/*[@class='inner']/div[1]/*[@class='value']")).getText().trim();
			  contractList.add(contract_num);
			  toBeReplaced = "<"+"ContractNumber"+">"+ contract_num +"</"+"ContractNumber"+">";
			  reqXML = reqXML + toBeReplaced;
			  flag=false;
			  break;
			}
		}
		
		
		Util.printInfo("reqXML : "+reqXML);
		reqXML =(testProperties.getConstant("REQUEST")).replace(requestStr, reqXML); 
		Util.printInfo("reqXML : "+reqXML);
		String response = soapUIExampleTest.getResponseForSoapRequest(testProperties.getConstant("STR_APPEND"),testProperties.getConstant("REQUEST_METHOD"),testProperties.getConstant("WSDL_NAME"), reqXML,testProperties.getConstant("ENDPOINT_URL"), getEnvironment());
		
//		Util.printInfo("response : " + response);
		Document doc = domParser.getDocument(response);
		
		NodeList ccTransactionNodes = doc.getElementsByTagName("CcTransaction");
		
		Util.printInfo("ccTransactionNodes.getLength() : "+ccTransactionNodes.getLength());
		
		Util.printInfo("Size of contract list : " + contractList.size() );
	    for(int con=0;con < contractList.size();con++){
	    	
	    	
	    	Node unitType ;
	    	Node txnType ;
	    	Node serviceCode ;
	    	Node contractNode ;
	    	Node txnUnits ;
	    	Node assignedClouds ;
	    	int cloud_consumed = 0;	
			int cloud_assigned = 0;
			int cloud_remaining = 0;
			HashMap<String, Integer> serviceCodeMap2 = new HashMap<>();
			
			for(int s=0;s<ccTransactionNodes.getLength() ; s++ ){
				 unitType = domParser.getSubNode("UnitType", ccTransactionNodes.item(s) );
				  txnType = domParser.getSubNode("TxnType", ccTransactionNodes.item(s) );
				  serviceCode = domParser.getSubNode("ServiceCode", ccTransactionNodes.item(s) );
				 contractNode = domParser.getSubNode("ContractNumber", ccTransactionNodes.item(s) );
				 if(contractNode.getTextContent().equalsIgnoreCase(contractList.get(con).toString())){
					 if(unitType.getTextContent().equalsIgnoreCase("c3")){
							if( txnType.getTextContent().equalsIgnoreCase("CLOUD_CONSUMPTION")){
								if(!serviceCode.getTextContent().equalsIgnoreCase("NA")){
										 serviceCodeMap2.put(serviceCode.getTextContent(), 0);
								}
							}
					 }
				 }
			}
			
	    	
		for(int s=0;s<ccTransactionNodes.getLength() ; s++ ){
			  unitType = domParser.getSubNode("UnitType", ccTransactionNodes.item(s) );
			  txnType = domParser.getSubNode("TxnType", ccTransactionNodes.item(s) );
			  serviceCode = domParser.getSubNode("ServiceCode", ccTransactionNodes.item(s) );
			 contractNode = domParser.getSubNode("ContractNumber", ccTransactionNodes.item(s) );
			 
			
			//added credits: ADD PLC + NA + C3
			//used credits :CLOUD_CONSUMPTION + c3
			//if contract doesnt have only ADD PLC & no CLOUD_CONSUMPTION then no credits being consumed. 
			//remaining credits : added credits - used credits
			
			if(contractNode.getTextContent().equalsIgnoreCase(contractList.get(con).toString()) ){
				
				if(unitType.getTextContent().equalsIgnoreCase("c3")){
				
					if( txnType.getTextContent().equalsIgnoreCase("CLOUD_CONSUMPTION")){
				
					  txnUnits = domParser.getSubNode("TxnUnits", ccTransactionNodes.item(s) );
					
						//this much credits being used in this contract
						cloud_consumed = Integer.parseInt(txnUnits.getTextContent()) + cloud_consumed;
						// if(service code <> NA), add it to hash map n calculate.
							if(!serviceCode.getTextContent().equalsIgnoreCase("NA")){
								 txnUnits = domParser.getSubNode("TxnUnits", ccTransactionNodes.item(s) );
										 for (Map.Entry<String, Integer> entry : serviceCodeMap2.entrySet()) {
											 if(entry.getKey().toString().equalsIgnoreCase(serviceCode.getTextContent())){
												 int val = Integer.parseInt(txnUnits.getTextContent()) + entry.getValue() ;
												 entry.setValue(val);
											 }
											 
										 }
							}
						
						} 
						
						if( txnType.getTextContent().equalsIgnoreCase("ADD PLC") && serviceCode.getTextContent().equalsIgnoreCase("NA") ){
						 assignedClouds = domParser.getSubNode("TxnUnits", ccTransactionNodes.item(s) );
							cloud_assigned = Integer.parseInt(assignedClouds.getTextContent());
						}
						
				}
			
			} //big if
			
		} // inside for
		cloud_remaining = cloud_assigned - cloud_consumed ;
		 Util.printInfo("Contract Number   : "+ contractList.get(con).toString());
		 Util.printInfo("Credits assigned  : "+ cloud_assigned );
		 Util.printInfo("Credits consumed  : "+ cloud_consumed );
		 Util.printInfo("Credits remaining : "+ cloud_remaining); 
		 Util.printInfo("hashmap contents Map2  : " + serviceCodeMap2.toString());
		 
		 
		 if(cloud_assigned == 0){
			 Util.printInfo(" The contract : "+contractList.get(con).toString()+" has no cloud credits purchased");
		 }else if (cloud_consumed == 0){
			 Util.printInfo(" The contract : "+contractList.get(con).toString()+" has no cloud credits used on any service");
		 }else if (cloud_remaining == 0){
			 Util.printInfo(" The contract : "+contractList.get(con).toString()+" has no cloud credits remaining");
		 }
		 
		 
		 //put excel in loop, match hashmap key, then get string description and cloud value.
		 //while selenium contract loop, get services below it, match it with string description()regular expression, then get cloud value
		 //then match both cloud value
		 String servicDesc = "" ;
		 int servicCredit = 0;
		 TreeMap<String, Integer> serviceCodeMap3 = new TreeMap<>();
		 TreeMap<String, Integer> serviceCodeMap4 = new TreeMap<>();
		 
		 //2 is object
		 //1 is not
		 
		 for (Map.Entry<String, String> entry: serviceCodeMap1.entrySet()) {
			    // Check if the current value is a key in the 2nd map
			    if (serviceCodeMap2.containsKey(entry.getKey())) {
			        // Put your logic here..
			    	servicDesc = entry.getValue(); 
			    	servicCredit = serviceCodeMap2.get(entry.getKey());
			    	serviceCodeMap3.put(servicDesc, servicCredit);
			    	
			    }
			}
		 //ur final map is ready with service description + its respective credit consumed for the current (in loop) contract : serviceCodeMap3
		 Util.printInfo("hashmap contents Map3  : " + serviceCodeMap3.toString());
		 
		 
		 //Now : Need to verify servic UI values to WebService respon		 
		 //************* Validating ws response with UI values **************
		
		 
		 if(cloud_consumed != 0 ){
			 //clicking on contract drawer 
			 Util.printInfo("Clicking on contract Drawer ..");
			 
			 String parsedContractValue2 = homePage.createFieldWithParsedFieldLocatorsTokens("specificContractNum2", contractList.get(con).toString());
			 homePage.click(parsedContractValue2);
			 
			 Util.sleep(2000);
			 int servicTbl = driver.findElements(By.xpath(".//*[@id='contract-"+contractList.get(con).toString()+"']/div[3]/div/div[2]/table/tbody/tr")).size(); 
					  
			 Util.printInfo("Size of Service list under this contract : " + servicTbl);
			 for(int tr=1;tr < servicTbl;tr++ ){
				 String parsedService = homePage.createFieldWithParsedFieldLocatorsTokens("servicePath", contractList.get(con).toString(),String.valueOf(tr)); 
				 String service = homePage.getValueFromGUI(parsedService);
				 String parsedccUsage =  homePage.createFieldWithParsedFieldLocatorsTokens("ccUsagePath", contractList.get(con).toString(), String.valueOf(tr));
				 String ccUsage =  homePage.getValueFromGUI(parsedccUsage);
				 serviceCodeMap4.put(service,  Integer.parseInt(ccUsage) );
			
			
			 }
			 
			 String ignoreChar = "®";
			 Util.printInfo("hashmap contents Map4  : " + serviceCodeMap4.toString());
			 assertEquals("Number of services : "+serviceCodeMap3.size(), "Number of services : "+serviceCodeMap4.size());
			 
			/* for (String key1 : serviceCodeMap4.keySet()) {
				    for (String key2 : serviceCodeMap3.keySet()) {
				    	
				    	if(key1.equals(key2)){
					   Util.printInfo("key1 = " + key1.replaceAll("/[^a-zA-Z0-9]+/", " "));
					   Util.printInfo("key2 = " + key2.replaceAll(ignoreChar, " ").trim());
				    	
				    	}
					}
				}*/
			 
			  for (Map.Entry<String, Integer> entry: serviceCodeMap3.entrySet()) {
				    // Check if the current value is a key in the 2nd map
				 for (Map.Entry<String, Integer> entry2: serviceCodeMap4.entrySet()) {
					    // Check if the current value is a key in the 2nd map
					    if (entry.equals(entry2)) {
					    	String newEntry = entry.toString().replaceAll(ignoreChar, " ").trim(); 
					    	String newEntry2 = entry2.toString().replaceAll(ignoreChar, " ").trim();  
					        // Put your logic here..
					    	assertEquals(newEntry,newEntry2);
					    	
					    }
					}
				   
				} 
			 
		 }
		 
		 
		 
		 
		 
		/* for (Map.Entry<String, Integer> entry: serviceCodeMap3.entrySet()) {
			    // Check if the current value is a key in the 2nd map
			    if (serviceCodeMap4.containsKey(entry.getKey())) {
			        // Put your logic here..
			    	assertEquals(serviceCodeMap4.get(entry.getKey()), serviceCodeMap3.get(entry.getKey())); 
			    }
			}
	*/
	    	
	    } // outside for
		
	    logoutMyAutodeskPortal();   
	} //method close

	
	
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
