package customerportal;

import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
public final class Test_Verify_ReportingPage_Services extends CustomerPortalTestBase {
	
	
	public Test_Verify_ReportingPage_Services() throws IOException {
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
			Util.printInfo("Logging into CM's Account");
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		}
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		homePage.click("reporting");
		Util.sleep(5000);
		List<WebElement> contracts=driver.findElements(By.xpath(".//*[contains(@id,'contract')]/div[2]/div[1]/div/div[1]/span[2]"));
		
		for(WebElement contract:contracts)
		{
				String Actualcontract=contract.getText();
				String ReportingPageContracts=homePage.createFieldWithParsedFieldLocatorsTokens("ReportingPageContracts", Actualcontract);
				String ReportingPageContracts1=homePage.getValueFromGUI(ReportingPageContracts);
				if(ReportingPageContracts1.trim().contains("No Cloud Credits Purchased"))
				{
					assertTrue("The message No cloud credit purchased is seen", ReportingPageContracts1.trim().contains("No Cloud Credits Purchased"));
					break;
				}else{
					EISTestBase.fail("The message No cloud credit purchased is not displayed, so please chose an CM account which has No cloud credit purchased message ::");
				}
		}		
				
		for(WebElement contract:contracts){
			String Actualcontract=contract.getText();
			String ReportingPageContracts=homePage.createFieldWithParsedFieldLocatorsTokens("ReportingPageContracts", Actualcontract);
			String ReportingPageContracts1=homePage.getValueFromGUI(ReportingPageContracts);
			
				if(!ReportingPageContracts1.trim().contains("No Cloud Credits Purchased"))
				{
					String NoCloudCreditsUsed=homePage.createFieldWithParsedFieldLocatorsTokens("NoCloudCreditsUSed", Actualcontract);
					String NoCloudCreditsUsed1=homePage.getValueFromGUI(NoCloudCreditsUsed);
					if(NoCloudCreditsUsed1.trim().contains("No cloud credit used"))
					{
						assertTrue("No cloud credit used", NoCloudCreditsUsed1.trim().contains("No cloud credit used"));
						break;
					}
				}else{
					EISTestBase.fail("No cloud credit used is not pressent, please select use a CM which has No cloud credits used message");
				}
		}
		
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
