package bornincloud;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebElement;
import common.EISTestBase;
import common.Util;

public class Test_Verify_EnthusiastUser_E3_E4 extends BornInCloudTestBase {

	public Test_Verify_EnthusiastUser_E3_E4() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Verify_EnthusiastUser() throws Exception {

		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();

		String env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("USER_ID_DEV"),
					testProperties.getConstant("PASSWORD_DEV"));
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
		}
		// assertEquals(homePage.getValueFromGUI("titleproductservices"),
		// "Products & Services");

		homePage.waitForElementToDisappear("pageLoadImg", 10000);

		
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);

		// Select Services Link
		bic.select("serviceslink");
		homePage.waitForFieldVisible("serviceslist", 30000);
		boolean service_fusion = false;
		if (homePage.checkFieldExistence("serviceslist")) {
			List<WebElement> serviceslist = homePage
					.getMultipleWebElementsfromField("serviceslist");
			for (int i = 0; i < serviceslist.size(); i++) {
				List<WebElement> servicesname = homePage
						.getMultipleWebElementsfromField("servicesname");
				String name = servicesname.get(i).getText();
				if (name.contains("Fusion 360 Enthusiast")) {
					assertTrueCatchException(
							"Fusion 360 Enthusiast is Displayed in Services",
							name.contains("Fusion 360"));
					service_fusion = true;
				}
			}
		} else {
			Util.printError("Not a Single Product Displayed in Services");
		}
		if (!service_fusion) {
			assertTrueCatchException(
					"Fusion 360 Enthusiast Should be Displayed in Services", false);
			Util.printError("Fusion 360 Enthusiast Not Displayed in Services");
		}

		// Select Product Link
		bic.select("productlink");
		homePage.waitForFieldVisible("productslist", 30000);
		boolean product_fusion = false;
		if (homePage.checkFieldExistence("productslist")) {
			List<WebElement> productslist = homePage
					.getMultipleWebElementsfromField("productslist");
			for (int i = 0; i < productslist.size(); i++) {
				List<WebElement> productname = homePage
						.getMultipleWebElementsfromField("productname");
				String name = productname.get(i).getText();
				if (name.contains("Fusion 360 Enthusiast")) {
					assertTrueCatchException(
							"Fusion 360 Enthusiast is Displayed in Product",
							name.contains("Fusion 360"));
					product_fusion = true;
				}
			}
		} else {
			Util.printError("Not a Single Product Displayed in Products");	
		}
		if (!product_fusion) {
			assertTrueCatchException(
					"Fusion 360 Enthusiast Should be Displayed in Product", false);
			Util.printError("Fusion 360 Enthusiast Not Displayed in Product");
		}

		// Verify Fusion 360 is Displayed
		// assertEqualsCatchException("Fusion 360 Student",homePage.getValueFromGUI("productfusion"));
		//
		// //Verify Student is Displayed
		// assertEqualsCatchException("Student",homePage.getValueFromGUI("student"));
		//
		// //Verify DownLoad is Displayed
		// assertEqualsCatchException("Download Now",homePage.getValueFromGUI("download"));
		//
		// //Expand Product Link
		// homePage.click("viewproductdeatils");
		//
		// //Verify End Date is Displayed
		// assertEqualsCatchException("End Date",homePage.getValueFromGUI("enddate"));
		//
		// //Verify Expires is Displayed
		// assertEqualsCatchException("Expires",homePage.getValueFromGUI("expires"));

		// Logout
		homePage.click("arrow");

		homePage.click("signout");
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
