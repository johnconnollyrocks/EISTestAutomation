package customerportal;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

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
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_verifyContractRptDescription extends CustomerPortalTestBase {
	public Test_verifyContractRptDescription() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	@Test
	public void Test_verifyContractRptDescription_method() throws Exception {
		
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		homePage = customerPortal.getHomePage();
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		mainWindow.select();
		
		Util.sleep(40000);
		Util.printInfo("click on reporting");
		homePage.clickAndWait("reporting", "contractReport");

		Util.printInfo("click on contract Report");
		homePage.clickAndWait("contractReport","reporting");
		
//		Util.printInfo("url : "+testProperties.getConstant("NAVIGATEURL"));
//		driver.navigate().to(testProperties.getConstant("NAVIGATEURL"));
		homePage.waitForField("headerContract", true);
		Util.sleep(5000);
		
		int tablesize = driver.findElements(By.xpath(".//*[@id='contract-report']/table/tbody/tr")).size();//homePage.getNumRowsInTable("contractReportTable");
		Util.printInfo("tablesize : "+tablesize);
		for(int i =1;i<=tablesize;i++){
			
			/*//checking only till 30 contracts for testing purpose
			if(i == 30)break;*/
			
			String contractNumber = driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[1]/a")).getText().trim();
			
			Util.printInfo("contractNumber : "+contractNumber);
			
			String products = driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[4]")).getText().trim();
			String term = driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[2]")).getText().trim();
			String startDate = driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[3]")).getText().trim();
			String endDate = driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[3]")).getText().trim();
			boolean RenewalBtnExists = driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[7]/a")).getAttribute("class").equalsIgnoreCase("renew-button");
			
			Util.printInfo("clicking on contract  --> "+i);
			
			String xpath = ".//*[@id='contract-report']/table/tbody/tr[" + i + "]/td[1]/a" ;
			driver.findElement(By.xpath(xpath)).click();
			
			Util.sleep(20000);
			
//			Util.printInfo("Description page : veri ");
			String descriptionHeader = homePage.getValueFromGUI("contractRptDescriptionHeader");
			Util.printInfo("descriptionHeader : "+descriptionHeader);
			assertEquals(descriptionHeader.trim(),testProperties.getConstant("DETAILS")+contractNumber);
			
			String typeOfRenewal =homePage.getValueFromGUI("typeOfRenewal");
			String term1=homePage.getValueFromGUI("term");
			 assertTrue("Type of renewal" +typeOfRenewal ,typeOfRenewal.contains(homePage.getValueFromGUI("term")));
			 
			 assertEquals(startDate,homePage.getValueFromGUI("startDate"));
			 assertEquals(endDate,homePage.getValueFromGUI("endDate"));
			 
			 String contractManager =homePage.getValueFromGUI("ContractManager");
			/* if(homePage.isFieldPresent(contractManager)){
			 homePage.verifyFieldExists("ContractManager");
			 }else{
				 EISTestBase.fail("ContractManager details not displayed :: ");
			 }
			 */
		//	 assertEquals("Contract Manger",contractManager,testProperties.getConstant("USER_NAME"));
			
			//Below 2 asserts are not applicable
//			assertEquals(homePage.getValueFromGUI("cm_name"), testProperties.getConstant("CM_NAME"));
//			assertEquals(homePage.getValueFromGUI("cm_email"), testProperties.getConstant("CM_EMAIL"));
		
			
			int totalSeatCount = 0; int rowIndex;int seats2 = 0;
			int rowcount = driver.findElements(By.xpath(".//*[@id='asset-list']/tbody/tr")).size();
			Util.printInfo("Total number of rows : "+rowcount);
			
			 HashSet<String> hsFrmHeading = new HashSet<String>();
			 HashSet<String> hsFrmTable = new HashSet<String>();
			 
			for(rowIndex=1; rowIndex <= rowcount; rowIndex++) {
				Util.printInfo("Inside -rowIndex- for : " + rowIndex );
				if ((rowIndex % 2)!= 0){
				seats2 = Integer.parseInt(driver.findElement(By.xpath(".//*[@id='asset-list']/tbody/tr[" + rowIndex + "]/td[4]")).getText().toString());
				Util.printInfo("seats : "+seats2);
				totalSeatCount = totalSeatCount + seats2 ;	
				hsFrmTable.add(driver.findElement(By.xpath(".//*[@id='asset-list']/tbody/tr["+rowIndex+"]/td[5]")).getText().trim());
				Util.printInfo("Hash set values from table : "+ hsFrmTable.toString());
				
				if(RenewalBtnExists){
					
					homePage.verifyInstance("CHECK_RENEWAL_MSG1");
					homePage.verifyInstance("CHECK_RENEWAL_MSG2");
					
					homePage.verifyInstance("CHECK_RENEWAL_NUM_COL");
					
					
//					String renewalNumber = driver.findElement(By.xpath(".//*[@id='asset-list']/tbody/tr["+rowIndex+"]/td[7]")).getText().trim();
					
					String renewalNumber=homePage.createFieldWithParsedFieldLocatorsTokens("RenewalNumber", String.valueOf(rowIndex));
					
//					assertEquals(renewalNumber, testProperties.getConstant("RENEWAL_NUMBER"));
					homePage.verifyFieldExists(renewalNumber);	
					Util.printInfo("renewalNumber :" + homePage.getValueFromGUI(renewalNumber));
					
					
					Util.printInfo("Clicking on renew ");
					driver.findElement(By.xpath(".//*[@id='asset-list']/tbody/tr["+rowIndex+"]/*[@class='renew']/div")).click();
					Util.sleep(3000);
					Util.printInfo("Clicked on Renew ");
					
					String contactResTxt = driver.findElement(By.xpath(".//*[@id='asset-list']/tbody/tr[1]/td[8]/div/ul/li/div/strong/span")).getText(); 
					String status=driver.findElement(By.xpath(".//*[@id='asset-list']/tbody/tr[2]/td/div/ul/li[4]/strong")).getText();	
					System.out.println("contactResTxt" +contactResTxt);
					assertEquals(contactResTxt, "Contact Reseller");
					//assertEquals(reseller, "Reseller 3000_stg 001329");
					String reSeller =homePage.createFieldWithParsedFieldLocatorsTokens("Reseller", String.valueOf(rowIndex));
					homePage.verifyFieldExists(reSeller);
				//	assertEquals(status, "Active");
					Util.printInfo(status+ "STATUS");
				}
								
				}
				
			}
			 String EmailId="automation00303@ssttest.net";
			 String CurrentDate = suet.getCurrentDate();
			 String GUIDRequest= testProperties.getConstant("REQUEST1");		
				String ActualRequest=suet.ReplaceRequest(GUIDRequest,EmailId);
				
				File file=new File(System.getProperty("user.dir")+"\\build\\UserService.wsdl");
				
				String response = suet.getResponseForSoapRequest("webservice","GetUserByEmail", file.getName(), ActualRequest,"https://stageservices-usscl.autodesk.com/UserService",getEnvironment());
		//		Util.printInfo( "response : :"+response);
				Document doc = domParser.getDocument(response);
				String GUID=null;
				NodeList Nodes = doc.getElementsByTagName("ns0:User");
				Util.printInfo(Nodes.getLength() +" : Nodes.getlength()");
				for(int s=0;s<Nodes.getLength() ; s++ ){
					Node GUIDNode= domParser.getSubNode("ns0:GUID", Nodes.item(s));
					GUID=GUIDNode.getTextContent();
					Util.printInfo(GUIDNode.getTextContent());
				}
				
				Util.printInfo("GUID : "+GUID);
				Util.printInfo("contract number : "+contractNumber);

				/*String Request=testProperties.getConstant("REQUEST2");
				String StrRequsetXML=Request.replace("<par:GUID>?</par:GUID>", "<par:GUID>"+GUID+"</par:GUID>");
				File file1=new File(System.getProperty("user.dir")+"\\build\\PartyService.wsdl");
				
				String GetContactEntitlementsResponse=suet.getSoapRequestForChargeEvent("GetContactEntitlements", "GetContactEntitlements", file1.getName(), StrRequsetXML, "http://stageservices.autodesk.com/PartyService", getEnvironment());
				Document doc1 = domParser.getDocument(GetContactEntitlementsResponse);
				Util.printInfo("GetContactEntitlementsResponse:" +GetContactEntitlementsResponse);*/
			
			
			Util.printInfo("Outside for : totalSeatCount : "+totalSeatCount);
			
			//Total seat count verification
			assertEquals(totalSeatCount,Integer.parseInt(driver.findElement(By.xpath(".//*[@id='contract-report-details']/div[1]/section[1]/div[1]/figure[2]/p")).getText()));
			
		//	 String creditsRemaining=homePage.getValueFromGUI("cloudcreditsRemaining");
		//	 String creditsused = homePage.getValueFromGUI("cloudcreditsUsed");
		
		//	 if(creditsRemaining != "0"){
				homePage.verify();
		//	}
			 
			 
			 
			 
				
			hsFrmHeading.add(driver.findElement(By.xpath(".//*[@id='contract-report-details']/div[1]/section[1]/div[1]/figure[3]/p")).getText());
			Util.printInfo("Hash set values from Heading : "+ hsFrmHeading.toString());
			Util.printInfo(" hsFrmHeading.equals(hsFrmTable): " +hsFrmHeading.equals(hsFrmTable));
			
			//support level verification
			assertEquals(hsFrmHeading.toString(), hsFrmTable.toString());
			
			Util.printInfo("Clicking on back button ...");
			driver.findElement(By.xpath(".//*[@id='contract-report-details']/div[1]/header/h1/a")).click();
			Util.sleep(3000);
			Util.printInfo("Back button clicked..");
			
		}
	     
		logoutMyAutodeskPortal();}

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
