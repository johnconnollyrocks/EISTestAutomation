package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public final class Test_VerifyCMContractRptHeadersAndContracts extends CustomerPortalTestBase {
	public Test_VerifyCMContractRptHeadersAndContracts() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	
	@Test
	public void Test_VerifyCMContractRptHeadersAndContracts_method() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		homePage.clickAndWait("users", "addUser");
		Util.sleep(2000);
		
		
		int totalUsers = driver.findElements(By.xpath(".//*[@id='results']/li")).size();
		List<String> contracts = new ArrayList<>();
		
		for(int i =1;i<=totalUsers;i++){
			int totalContracts = driver.findElements(By.xpath(".//*[@id='results']/li["+i+"]//*[@class='contracts']//li")).size();
			for(int j =1;j<totalContracts;j++){
				String text =driver.findElement(By.xpath(".//*[@id='results']/li["+i+"]//*[@class='contracts']//li["+j+"]")).getAttribute("innerHTML");
				text = text.substring(text.indexOf("</span>")+8,text.length()).trim();
				contracts.add(text);
			}
		}
		
		Util.printInfo("contracts from users page : "+contracts);
		
		homePage.clickAndWait("reporting", "contractReport");
		
		Util.printInfo("click on contract Report");
//		homePage.click("contractReport");
		
		System.out.println("url : "+testProperties.getConstant("NAVIGATEURL"));
		driver.navigate().to(testProperties.getConstant("NAVIGATEURL"));
		
		homePage.waitForField("headerContract", true);
		
		Util.sleep(5000);
		
		Util.printInfo("check if total columns = 8");
		int totalColumns = driver.findElements(By.xpath(".//*[@id='contract-report']/table/thead/tr/th")).size();
		Util.printInfo("total columns present : "+totalColumns);
		assertEquals("8", ""+totalColumns);
		
		Util.printInfo("verify the header values");
		homePage.verify();
		
		
		int totalContractsCR = driver.findElements(By.xpath(".//*[@id='contract-report']/table/tbody//td[@class='number']")).size();
		List<String> crContracts = new ArrayList<>();
		for(int i=1;i<=totalContractsCR;i++){
			String textCR = driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[@class='number']/a")).getText().trim();
			crContracts.add(textCR);
		}
		Util.printInfo("contracts from contract report page : "+crContracts);
		
		assertTrue("should contain all contracts : ",crContracts.containsAll(contracts));
		
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
