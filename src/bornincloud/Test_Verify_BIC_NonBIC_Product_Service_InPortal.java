package bornincloud;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebElement;
import common.EISTestBase;
import common.Util;

public class Test_Verify_BIC_NonBIC_Product_Service_InPortal extends
		BornInCloudTestBase {
	String expireDate = null;
	String contracts = null;

	public Test_Verify_BIC_NonBIC_Product_Service_InPortal() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_ExpiresContract() throws Exception {

		boolean renewal = true;

		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;
		boolean BIC = false;
		boolean nonBIC = false;

		env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("USER_ID_DEV"),
					testProperties.getConstant("PASSWORD_DEV"));
			expireDate = testProperties.getConstant("EXPIRESDATE_DEV");
			contracts = testProperties.getConstant("CONTRACT_NO_DEV");
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
			expireDate = testProperties.getConstant("EXPIRESDATE_STG");
			contracts = testProperties.getConstant("CONTRACT_NO_STG");
		}
		// assertEquals(homePage.getValueFromGUI("titleproductservices"),
		// "Products & Services");
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
				
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);

		// Select Product Link
		bic.select("productlink");
		
		homePage.waitForFieldVisible("productslist", 100000);
		if (homePage.checkFieldExistence("productslist")) {
			List<WebElement> productslist = homePage
					.getMultipleWebElementsfromField("productslist");
			List<WebElement> productname = homePage
					.getMultipleWebElementsfromField("productname");
			List<WebElement> middlecolumn = homePage
					.getMultipleWebElementsfromField("middlecolumn");
			List<WebElement> rightcolumn = homePage
					.getMultipleWebElementsfromField("rightcolumn");
			for (int i = 0; i < productslist.size(); i++) {
				String name = productname.get(i).getText();
				if (name.contentEquals("Fusion 360 Monthly")) {
					productslist.get(i).click();
					Util.sleep(3000);
					for (WebElement j : middlecolumn) {
						String content = j.getText();
						Util.printInfo(content);
						if (content.contains("Auto-renews")) {
							BIC = true;
							assertTrueCatchException(
									"BIC Fusion 360 Monthly Displayed", true);

							String date = homePage.getValueFromGUI("date");
							Util.printInfo(date);

							if (date.contains(expireDate)) {
								assertTrueCatchException(
										"Expired Date is Displayed", true);
							}

							for (WebElement k : rightcolumn) {
								String content1 = k.getText();
								if (content1.contains("Edit Renewal")) {
									assertTrueCatchException(
											"Edit Renewal Displayed", true);
									renewal = true;
								}
							}
							if (!renewal) {
								assertTrueCatchException(
										"Edit Renewal Not Displayed ", false);
							}

						}

					}

				}
				productslist.get(i).click();
				Util.sleep(5000);
			}
		} else {
			Util.printError("Not a Single Product Displayed in Products");
		}
		if (!BIC) {
			assertTrueCatchException(
					"BIC Fusion 360 Monthly Not Displayed In Product", false);
		}
		bic.select("serviceslink");
		Util.sleep(3000);
		homePage.waitForFieldVisible("serviceslist", 30000);
		if (homePage.checkFieldExistence("serviceslist")) {
			List<WebElement> serviceslist = homePage
					.getMultipleWebElementsfromField("serviceslist");
			List<WebElement> servicesname = homePage
					.getMultipleWebElementsfromField("servicesname");
			List<WebElement> contractlist = homePage
					.getMultipleWebElementsfromField("contractvalue");
			for (int i = 0; i < serviceslist.size(); i++) {
				String name = servicesname.get(i).getText();
				if (name.contentEquals("Autodesk® Fusion 360")) {
					assertTrueCatchException(
							"Autodesk® Fusion 360 is Displayed in Services",
							name.contains("Autodesk® Fusion 360"));
					nonBIC = true;
					break;

				}
			}

			for (int i = 0; i < serviceslist.size(); i++) {
				String contract = contractlist.get(i).getText();
				if (contract.contentEquals(contracts)) {
					assertTrueCatchException(
							"Autodesk® Fusion 360 Contract is Displayed in Services",
							contract.contains(contracts));
					nonBIC = true;
					break;
				}
			}
		} else {
			Util.printError("Not a Single Product Displayed in Services");
		}

		if (!nonBIC) {
			assertTrueCatchException(
					"Non BIC Autodesk® Fusion 360 Displayed in Services",
					false);
		}

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
