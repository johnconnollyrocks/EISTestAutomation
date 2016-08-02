package customerportal;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_TrailProducts extends CustomerPortalTestBase {
	public Test_Verify_TrailProducts() throws IOException {
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
		int count=0;
		Util.printInfo("Validating that Trails tab should not pressent in main header ");
		
		homePage.verifyFieldNotExists("TrailsOnMainHeader");
		
		Util.printInfo("Trails tab does not exists in main header, so checking Trails tab in products and services page ");
		
		if(homePage.isFieldVisible("TrailsTabInProductsAndServicesPage")){
			homePage.verifyFieldExists("TrailsTabInProductsAndServicesPage");
			Util.printInfo("clicking on Trails tab pressent in products and services page ");
			homePage.click("TrailsTabInProductsAndServicesPage");
			Util.sleep(60000);
			assertTrue("Trials page loaded successfuly ", homePage.verifyFieldExists("TrialsPage"));
			List<WebElement> Products=homePage.getMultipleWebElementsfromField("TrailProducts");
			if(Products.size()>0){
			for(WebElement EachProduct : Products ){
				count=count+1;
				Util.printInfo("Clicking on Trail product :: " + EachProduct.getText());
				EachProduct.click();
				Util.sleep(4000);
				String ProductDescription=homePage.createFieldWithParsedFieldLocatorsTokens("EachTrialsProductDescription", String.valueOf(count));
				Util.printInfo("Checking description for product :: " +EachProduct.getText());
				homePage.verifyFieldExists(ProductDescription);
				/*Util.printInfo("verifying cloud credits header for product :: "+EachProduct.getText());
				String CloudCredits=homePage.createFieldWithParsedFieldLocatorsTokens("CloudCreditsForEachProduct", String.valueOf(count));
				homePage.verifyFieldExists(CloudCredits);*/
				EachProduct.click();
			}
		}else{
			EISTestBase.fail("There are no trials products displayed in trials page, please check again ");
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
