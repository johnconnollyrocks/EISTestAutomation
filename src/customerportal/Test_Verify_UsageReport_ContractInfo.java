package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_UsageReport_ContractInfo extends CustomerPortalTestBase {
	
	public Test_Verify_UsageReport_ContractInfo() throws IOException {
//		super("firefox");
		super("Browser",getAppBrowser());
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_UsageReport_ContractInfo() throws Exception {
		
		driver.manage().deleteAllCookies();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb=new WebDriverWait(driver,20000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		
		mainWindow.select();
		Util.printInfo("clicking on Reporting");
		homePage.click("reporting");
		Util.sleep(10000);
		homePage.click("usageReport");
		Util.sleep(20000);
		Util.printInfo("Verifying the quarterly Contract");	
		Util.sleep(5000);
		
//		Util.printInfo("Verifying the monthly Contract");
//		homePage.verifyInstance("MONTHLY_VERIFY");		
//		Util.printInfo("Clicking no Get Cloud credits button on the monthly contract");
//		
		   //******************************************String winHandleBefore = driver.getWindowHandle();
//		homePage.click("monthlyContractGetCloudCredits");		
//		for(String winHandle : driver.getWindowHandles()){
//		    driver.switchTo().window(winHandle);		    
//		}		
//		homePage.waitForFieldPresent("contractGetCloudCreditsNewPageCheckOut");
//		Util.printInfo("Verifying whether the contract number is displayed correctly in the new window on the monthly contract after clicking on get cloud credits button");
//		homePage.verifyInstance("MONTHLY_CONTRACT_VERIFY");
//		driver.close();
//		mainWindow.select();	
		

		
		
		homePage.verifyInstance("QUARTERLY_VERIFY");		
		String winHandleBefore = driver.getWindowHandle();
		Util.printInfo("Clicking no Get Cloud credits button on the quarterly contract");
		homePage.click("quarterlyContractGetCloudCredits");		
		Util.sleep(10000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(5000);
		homePage.waitForFieldPresent("contractGetCloudCreditsNewPageCheckOut");
		Util.printInfo("Verifying whether the contract number is displayed correctly in the new window on the quarterly contract after clicking on get cloud credits button");
		homePage.verifyInstance("QUARTERLY_CONTRACT_VERIFY");
		driver.close();
		mainWindow.select();
		logoutMyAutodeskPortal();
		//check for the cancelled monthly contracts at a future date
//		loginAsMyAutodeskPortalUser(testProperties.getConstant("MONTHLY_CANCELLED_USER_NAME") , testProperties.getConstant("MONTHLY_CANCELLED_PASSWORD"));
//		mainWindow.select();
//		Util.printInfo("clicking on Reporting");
//		homePage.click("reporting");
//		Util.sleep(2000);
//		homePage.click("usageReport");
//		Util.sleep(2000);
//		Util.printInfo("Verifying the monthly cancelled contract at a future date for contract number, contract type, message, data guage(non existance) to be displayed");
//		homePage.verifyInstance("MONTHLY_CANCELLED_VERIFY");		
//		logoutMyAutodeskPortal();			
		//		Commented since there are no cancelled contracts in STG
		loginAsMyAutodeskPortalUser(testProperties.getConstant("NEW_USER_NAME") , testProperties.getConstant("NEW_PASSWORD"));
		WebDriverWait wb1=new WebDriverWait(driver,20000);
		wb1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		homePage.checkIfElementExistsInPage("genericLoadingIcon", 40);	//wait for the loading icon to disappear
		mainWindow.select();
		Util.printInfo("clicking on Reporting");
		homePage.click("reporting");
		Util.sleep(2000);
		homePage.click("usageReport");
		Util.sleep(2000);
		Util.printInfo("Verifying the monthly cancelled Contract at a future date for contract number,contract status,contract type and data guage for consumed and available");
		homePage.checkIfElementExistsInPage("genericLoadingIcon", 20);	//wait for the loading icon to disappear
//		homePage.verifyInstance("CANCELLED_CONTRACT_VERIFY");		
		
//		Util.printInfo("Consumed Cloud Credits for the contract are : - " +homePage.getValueFromGUI("monthlyCancelledContractConsumedCredits") + " Available Cloud Credits for the contract are : - " + homePage.getValueFromGUI("monthlyCancelledContractAvailableCredits"));
		
		Util.printInfo("Clicking no Get Cloud credits button on the monthly cancelled contract");
		homePage.click("monthlyCancelledContractGetCloudCredits");	
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);		    
		}
		Util.sleep(5000);
		homePage.waitForFieldPresent("contractGetCloudCreditsNewPageCheckOut");
		Util.printInfo("Verifying whether the contract number is displayed correctly in the new window on the cancelled contract after clicking on get cloud credits button");
		homePage.verifyInstance("CANCELLED_CONTRACT_VERIFY1");
		driver.close();
		mainWindow.select();		
		Util.printInfo("Verifying tooltip displayed for the monthly cancelled contract");
		homePage.checkIfElementExistsInPage("genericLoadingIcon", 20);	//wait for the loading icon to disappear
	//	homePage.hoverOver("monthlyCancelledContractTypeHover");
		homePage.verifyInstance("CANCELLED_CONTRACT_VERIFY_TOOLTIP");	
		
		// Not working ... has to find out an alternative
		
//		homePage.hoverOver("whatAreCloudCreditsLabel");
//		homePage.click("whatAreCloudCreditsLearnMore");
//		for(String winHandle : driver.getWindowHandles()){
//		    driver.switchTo().window(winHandle);		    
//		}
//		homePage.verifyInstance("CLOUDCREDITS_LEARNMORE");
//		driver.close();
		
		logoutMyAutodeskPortal();		
		
		//Verify for Quarterly cancelled contract
//		loginAsMyAutodeskPortalUser(testProperties.getConstant("QUARTERLY_CANCELLED_USER_NAME") , testProperties.getConstant("QUARTERLY_CANCELLED_PASSWORD"));
//		mainWindow.select();
//		Util.printInfo("clicking on Reporting");
//		homePage.click("reporting");
//		Util.sleep(10000);
//		homePage.click("usageReport");
//		Util.sleep(2000);
//		Util.printInfo("Verifying the quarterlycancelled Contract for contract number,contract type and contract status");
//		homePage.verifyInstance("QUARTERLY_CANCELLED_CONTRACT_VERIFY");		
//		logoutMyAutodeskPortal();	
		
		//Verify for Quarterly Expired contract
		Util.sleep(5000);
		loginAsMyAutodeskPortalUser(testProperties.getConstant("QUARTERLY_EXPIRED_USER_NAME") , testProperties.getConstant("QUARTERLY_EXPIRED_PASSWORD"));
		homePage.checkIfElementExistsInPage("genericLoadingIcon", 30);	//wait for the loading icon to disappear
		mainWindow.select();		
		Util.printInfo("clicking on Reporting");
		homePage.click("reporting");
		Util.sleep(2000);
		homePage.checkIfElementExistsInPage("genericLoadingIcon", 20);	//wait for the loading icon to disappear
		homePage.click("usageReport");
		Util.sleep(2000);		
		Util.printInfo("Verifying the quarterly expired Contract for contract number not to be displayed");
		homePage.verifyInstance("QUARTERLY_EXPIRED_CONTRACT_VERIFY");		
		logoutMyAutodeskPortal();
		Util.sleep(1000);
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
