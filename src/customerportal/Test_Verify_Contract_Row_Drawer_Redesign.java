package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_Contract_Row_Drawer_Redesign extends CustomerPortalTestBase {
	public Test_Verify_Contract_Row_Drawer_Redesign() throws IOException {
		super("Browser",getAppBrowser());
		System.setProperty("testName", "Test_Verify_Contract_Row_Drawer_Redesign");
		System.setProperty("testPropertiesFilenameKey", "TEST_VERIFY_CONTRACT_ROW_DRAWER_REDESIGN_PROPERTIES_FILE");
		super.setup();
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	@Test
	public void Test_VerifyContractRowDrawerRedesign() throws Exception {
		
		Util.printInfo("Logging into CM's account");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
//		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.printInfo("Verifying Contract Row-Drawer Redesign ");
		Util.printInfo("Navigating to Reporting page ");
		homePage.click("reporting");
		Util.sleep(6000);
		String contract_num=null;
		int sum=0;
		boolean ticket=true;
		boolean flag=true;
		
		Util.sleep(2000);
		
		int totalContracts = driver.findElements(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/article")).size();		
		Util.printInfo("totalContracts  : "+totalContracts);
		
		for(int row=1;row<=totalContracts;row++){
			
		contract_num = driver.findElement(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/article["+row+"]/*[@class='contract-summary']/*[@class='left-col']/*[@class='inner']/div[1]/*[@class='value']")).getText().trim();
			  
		Util.printInfo("Opening the drawers under the contract :"+contract_num);
		
		String ContractHasCloudCredits=homePage.createFieldWithParsedFieldLocatorsTokens("ContractHasCloudCredits", contract_num);
		
		if(!homePage.getValueFromGUI(ContractHasCloudCredits).contains("No Cloud Credits Purchased")){
			
		flag=false;			
		driver.findElement(By.xpath(".//*[@id='contract-"+contract_num+"']/div[1]/button")).click();
		Util.sleep(5000);
		Util.printInfo("Verifing Drawer elements..");
	//	String UsageByService=homePage.createFieldWithParsedFieldLocatorsTokens("UsageByService", contract_num);
		
		String usagebyService=homePage.createFieldWithParsedFieldLocatorsTokens("usagebyService", contract_num);
		homePage.verifyFieldExists(usagebyService);
		
		String textBelowUsagebyService=homePage.createFieldWithParsedFieldLocatorsTokens("textBelowUsagebyService", contract_num);
		homePage.verifyFieldExists(textBelowUsagebyService);
		
		String optimizationService=homePage.createFieldWithParsedFieldLocatorsTokens("optimizationService", contract_num);
		
		if(homePage.isFieldVisible(optimizationService)){
		Util.printInfo("validating cloudcredits used by particular service :: ");
		homePage.verifyFieldExists(optimizationService);
		}else{
			ticket=false;
			Util.printInfo("Validating No cloud credits Used message :: ");
			String noCloudCreditsUsedMsg=homePage.createFieldWithParsedFieldLocatorsTokens("NoCloudCreditsUsed", contract_num);
			Util.printInfo("Verifying AccessYourCloudServices Link is pressent or Not :: ");
			String AccessYourCloudServicesLink=homePage.createFieldWithParsedFieldLocatorsTokens("AccessYourCloudServices", contract_num);
			Util.printInfo("Verifying Try and learn more link is pressent or not :: ");
			String TryandLearnMoreLink=homePage.createFieldWithParsedFieldLocatorsTokens("TryandLearnMore", contract_num);
			homePage.verifyFieldExists(noCloudCreditsUsedMsg);
			homePage.verifyFieldExists(AccessYourCloudServicesLink);
			homePage.verifyFieldExists(TryandLearnMoreLink);
		}
		String optimizationServiceNumeric=homePage.createFieldWithParsedFieldLocatorsTokens("optimizationServiceNumeric",contract_num);
		if(homePage.isFieldVisible(optimizationServiceNumeric)){
		Util.printInfo("validating cloud credits consumed number :: ");
		homePage.verifyFieldExists(optimizationServiceNumeric);
		}
		
		String optimizationServiceGuage=homePage.createFieldWithParsedFieldLocatorsTokens("optimizationServiceGuage",contract_num);
		if(homePage.isFieldVisible(optimizationServiceGuage)){
		Util.printInfo("validating cloud credits gauge :: ");
		homePage.verifyFieldExists(optimizationServiceGuage);
		}
		
		/*String simulMoldflowService=homePage.createFieldWithParsedFieldLocatorsTokens("simulMoldflowService",contract_num);
		homePage.verifyFieldExists(simulMoldflowService);
		
		String simulMoldflowServiceNumeric=homePage.createFieldWithParsedFieldLocatorsTokens("simulMoldflowServiceNumeric",contract_num);
		homePage.verifyFieldExists(simulMoldflowServiceNumeric);*/
		
		String simulMoldflowServiceGuage=homePage.createFieldWithParsedFieldLocatorsTokens("simulMoldflowServiceGuage",contract_num);
		if(homePage.isFieldPresent(simulMoldflowServiceGuage)){
		homePage.verifyFieldExists(simulMoldflowServiceGuage);
		}
		
		String contractRenderingService=homePage.createFieldWithParsedFieldLocatorsTokens("contractRenderingService",contract_num);
		if(homePage.isFieldPresent(contractRenderingService)){
			homePage.verifyFieldExists(contractRenderingService);
			}
		
		
		String renderingServiceNumeric=homePage.createFieldWithParsedFieldLocatorsTokens("renderingServiceNumeric",contract_num);
		if(homePage.isFieldPresent(renderingServiceNumeric)){
		homePage.verifyFieldExists(renderingServiceNumeric);
		}
		
		String renderingServiceGuage=homePage.createFieldWithParsedFieldLocatorsTokens("renderingServiceGuage",contract_num);
		if(homePage.isFieldPresent(renderingServiceGuage)){
		homePage.verifyFieldExists(renderingServiceGuage);
		}
		
		String cloudCredUsedText=homePage.createFieldWithParsedFieldLocatorsTokens("cloudCredUsed",contract_num);
		if(homePage.isFieldVisible(cloudCredUsedText)){
		Util.printInfo("validating cloud credits used Text :: ");
		homePage.verifyFieldExists(cloudCredUsedText);
		}
		
		String cloudCredUsedNumeric=homePage.createFieldWithParsedFieldLocatorsTokens("cloudCredUsedNumeric",contract_num);
		if(homePage.isFieldVisible(cloudCredUsedNumeric)){
		Util.printInfo("validating cloud credits consumed value :: ");
		homePage.verifyFieldExists(cloudCredUsedNumeric);
		}
		
		Util.sleep(2000);
		int optimizationServiceNumericvalue=0;
		int totalCCUsed=0;
		if(ticket){
		totalCCUsed = Integer.parseInt(homePage.getValueFromGUI(cloudCredUsedNumeric));		
		optimizationServiceNumericvalue = Integer.parseInt(homePage.getValueFromGUI(optimizationServiceNumeric));
		}
		int renderingServiceNumericvalue=0;
		if(homePage.isFieldPresent(renderingServiceNumeric)){
		 renderingServiceNumericvalue = Integer.parseInt(homePage.getValueFromGUI(renderingServiceNumeric));
		}
		
		sum = optimizationServiceNumericvalue + renderingServiceNumericvalue ;
		
		Util.printInfo("optimizationServiceNumeric from GUI value : totalCCUsed : "+totalCCUsed);
		Util.printInfo("sum : totalCCUsed : "+sum);
		Util.printInfo("The UI page is in accordance to the new design");
		assertEquals(totalCCUsed, sum);
//		break;
		driver.findElement(By.xpath(".//*[@id='contract-"+contract_num+"']/div[1]/button")).click();
		}
		
	}
		
		if(flag){
			
			String UsageByService1=homePage.createFieldWithParsedFieldLocatorsTokens("UsageByService", contract_num);
			String ContractUsage=homePage.getValueFromGUI(UsageByService1);
			
			//We should Fail the TestCase Here.
			Util.printError("No Cloud Credits Purchased (OR) No cloud credits used on any service for :"+contract_num);
		}
		/*int totalContracts = driver.findElements(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/article")).size();		
		Util.printInfo("totalContracts  : "+totalContracts);
		
		for(int row=1;row<=totalContracts;row++){
			
			  String contract_num = driver.findElement(By.xpath(".//*[@id='contract-usage']/*[@class='viewport']/article["+row+"]/*[@class='contract-summary']/*[@class='left-col']/*[@class='inner']/div[1]/*[@class='value']")).getText().trim();
			  
		}*/
		
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
