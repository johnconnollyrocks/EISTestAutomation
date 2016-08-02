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
 * Test class - Test_VerifyAdminLoginToNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_LoginToNAMUApplicationAfterSubOrderCreation extends CustomerPortalTestBase {
	public Test_LoginToNAMUApplicationAfterSubOrderCreation() throws IOException {
//		super("firefox");
		super("Browser",getAppBrowser());
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyAdminLoginToNAMUApplication() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb=new WebDriverWait(driver,30000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));

		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.click("users");
		Util.sleep(20000);
		homePage.clickAndWait("firstButtonToggleDrawerUserPage", "contractManager1Roles");
		Util.printInfo("Verifying Agreements, Benefits and Products assigned");
		homePage.verify();
//		Util.printInfo("Verifying Services");
//		homePage.hoverOver("servicesforProductHover");
//	
//		homePage.verifyInstance("SERVICES_TOOL_TIP_CONTENT_CHECK");
		Util.sleep(2000);
		String benefitsAdded=homePage.getValueFromGUI("benefitsNoAdded");
		String[] noBenifitsAdded=benefitsAdded.split(" ");
		String[] totalBenefitsAdded=noBenifitsAdded[1].split("\\(");
		Util.printInfo("Total number of benefits added is "+totalBenefitsAdded);
	
		String productsAdded=homePage.getValueFromGUI("productsNoAdded");
		String[] noproductsAdded=productsAdded.split(" ");
		String[] totalProductsAdded=noproductsAdded[1].split("\\(");
		Util.printInfo("Total number of products added is "+totalProductsAdded);
	
		homePage.click("editAccessPage");
		String benefitsSeatsAvailableForContract1=homePage.getValueFromGUI("benefitsSeatsAvailableForContract1");
		Util.printInfo("Benefits Seats available for Contract 1 are "+benefitsSeatsAvailableForContract1);
		//EISTestBase.assertEquals("29", benefitsSeatsAvailableForContract1);
//		EISTestBase.assertEquals(benefitsSeatsAvailableForContract1,"5");
		
//		String productSeatsAvailableForContract1=homePage.getValueFromGUI("productsServicesFirstProductSeatsAvailable");
//		Util.printInfo("Products Seats available for Contract 1 are "+productSeatsAvailableForContract1);
//		//EISTestBase.assertEquals("3", productSeatsAvailableForContract1);
//		EISTestBase.assertEquals(productSeatsAvailableForContract1,"1");
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
