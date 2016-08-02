package customerportal;


import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import common.EISTestBase;
import common.Util;

public class Test_Verify_ALL_Tool_Tips extends CustomerPortalTestBase{

	public Test_Verify_ALL_Tool_Tips() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verify_Current_And_Previous_Version_Release_On_PandSPage() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		homePage.click("reporting");
		
		Util.sleep(2000);
		js_mouse_ToolTip("tooltip1");
		
		
				Util.sleep(4000);
				if(driver.findElement(By.xpath("//div[@id='contract-usage']//span[contains(text(),'What Are Individual Cloud Credits')]")).isDisplayed()){
					Util.printInfo("Verifying Tool tip for reporting-->Individual Cloud credits is working");
					homePage.verifyFieldExists("ToolTipValidationIndividual");
					}else{
						EISTestBase.fail("Tool tip for reporting-->Individual Cloud credits is not working");
					}
				
				Util.sleep(2000);
				
				
				//*****************************************************************************//Reporting--> Shared
				
				js_mouse_ToolTip("tooltip2");
				Util.sleep(2000);
				
				if(driver.findElement(By.xpath("//span[contains(text(),'What Are Shared Cloud Credits')]")).isDisplayed()){
					Util.printInfo("Verifying Tool tip for reporting-->shared Cloud credits is working");
					homePage.verifyFieldExists("ToolTipValidationShared");
					}else{
						EISTestBase.fail("Tool tip for reporting-->shared Cloud credits is not working");
					}
		logoutMyAutodeskPortal();
		Util.sleep(2000);
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
