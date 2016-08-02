package customerportal;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import common.EISTestBase;
import common.Util;

public class Test_Trails_Tab extends CustomerPortalTestBase {
	public Test_Trails_Tab() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Trails_Tab() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(2000);
		
		Util.printInfo("Validating that Trails tab should not pressent in main header ");
		
		homePage.verifyFieldNotExists("TrailsOnMainHeader");
		
		Util.printInfo("Trails tab does not exists in main header, so checking Trails tab in products and services page ");
		
		if(homePage.isFieldVisible("TrailsTabInProductsAndServicesPage")){
			homePage.verifyFieldExists("TrailsTabInProductsAndServicesPage");
			Util.printInfo("clicking on Trails tab pressent in products and services page ");
			homePage.click("TrailsTabInProductsAndServicesPage");
			Util.sleep(60000);
			assertTrue("Trials page loaded successfuly ", homePage.verifyFieldExists("TrialsPage"));
			
			if (homePage.isFieldVisible("TrailsDiscover")){
				homePage.verifyFieldExists("GoToCloudServicesLink");
				Util.printInfo("clicking on Goto cloud services link ");
				String winHandleBefore = driver.getWindowHandle();
				homePage.click("GoToCloudServicesLink");
				Util.sleep(6000);
				for(String winHandle : driver.getWindowHandles()){
				    driver.switchTo().window(winHandle); 
				}
				
				homePage.verifyFieldExists("SuccessfullyNavigatedToGoToCloudServicesLink");
				
				driver.switchTo().window(winHandleBefore);
				
			}else{
				EISTestBase.fail("There is no Discover text is displayed in Trails page, please check again ");
			}
			
		}else{
			EISTestBase.fail("There is no trials page displayed in products and services page ");
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
