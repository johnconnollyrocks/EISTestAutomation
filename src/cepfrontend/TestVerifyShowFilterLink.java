package cepfrontend;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;

/**
 * Test class - TestVerifyShowFilterLink
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestVerifyShowFilterLink extends CEPTestBase {
	public TestVerifyShowFilterLink() throws IOException {
		super("firefox");
	}

	@Before
	public void setUp() throws Exception {
		launchCEP(getBaseURL());
	}

	@Test
	public void TEST_ShowFilterLink() throws Exception {

		loginAsCEPUser();

		CEP cep = utilCreateSubscriptionRenewal();

		loginPage = cep.getLoginPage();
		homePage = cep.getHomePage();

		mainWindow.select();

		
		String logo = homePage.getAttribute("autodeskLogo", "alt");
		assertEquals(logo, "Autodesk");

		homePage.click("showsFilerLink");
		String clickShowFilter = homePage.getTextFromLink("showsFilerLink");
		assertEquals(clickShowFilter, "Hide Filters");

		homePage.click("showsFilerLink");
		String clickHideFilter = homePage.getTextFromLink("showsFilerLink");
		assertEquals(clickHideFilter, "Show Filters");

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
