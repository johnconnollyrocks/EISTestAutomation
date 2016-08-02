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
public final class Test_VerifyCMContractRptHeaders extends CustomerPortalTestBase {
	public Test_VerifyCMContractRptHeaders() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	
	@Test
	public void Test_VerifyCMContractRptHeaders_method() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.printInfo("click on reporting");
		homePage.clickAndWait("reporting", "contractReport");
		
		Util.printInfo("click on contract Report");
//		homePage.click("contractReport");
		
		System.out.println("url : "+testProperties.getConstant("NAVIGATEURL"));
		driver.navigate().to(testProperties.getConstant("NAVIGATEURL"));
		
		homePage.refresh();
		
		Util.sleep(5000);
		
		Util.printInfo("check if total columns = 8");
		int totalColumns = driver.findElements(By.xpath(".//*[@id='contract-report']/table/thead/tr/th")).size();
		Util.printInfo("total columns present : "+totalColumns);
		assertEquals("8", ""+totalColumns);
		
		Util.printInfo("verify the header values");
		homePage.verify();
		
	
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
