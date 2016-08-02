package bornincloud;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebElement;
import common.EISTestBase;
import common.Util;

public class Test_Verify_ExpiresContract extends BornInCloudTestBase {
	String expireDate = null;

	public Test_Verify_ExpiresContract() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_ExpiresContract() throws Exception {

		boolean renewal = false;

		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;

		env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("USER_ID_DEV"),
					testProperties.getConstant("PASSWORD_DEV"));
			expireDate = testProperties.getConstant("EXPIRESDATE_DEV");
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
			expireDate = testProperties.getConstant("EXPIRESDATE_STG");
		}

		homePage.waitForElementToDisappear("pageLoadImg", 100000);
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices")
				.contains("PRODUCTS & SERVICES");

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
				if (name.contains("Fusion 360")) {
					productslist.get(i).click();
					Util.sleep(5000);
					for (WebElement j : middlecolumn) {
						String content = j.getText();

						if (content.contains(testProperties
								.getConstant("EXPIRES"))) {

							String date = homePage.getValueFromGUI("date");
							String enddate = homePage
									.getValueFromGUI("enddate");

							if (date.contains(expireDate)) {
								assertTrueCatchException(
										"Expired Date is Displayed", true);
								renewal = true;
							}

							if (enddate.contains(testProperties
									.getConstant("ENDDATE"))) {
								assertTrueCatchException(
										"End Date Text is Displayed", true);

							}else{
								EISTestBase.failTest("End Date MisMatch");
							}
							for (WebElement k : rightcolumn) {
								String content1 = k.getText();
								if (content1.contains("Edit Renewal")) {
									EISTestBase
											.failTest("Edit Renewal Displayed for Expired Product");

								}
							}
							if (renewal) {
								assertTrueCatchException(
										"Edit Renewal Not Displayed for Expired Product",
										true);
								break;
							}

						}

					}
					if (renewal) {
						break;
					}
				}
				productslist.get(i).click();
				Util.sleep(5000);
			}
		} else {
			EISTestBase
					.failTest("Expires Product Not Displated, Change Test Data");
		}
		
		if (!renewal){
			EISTestBase.failTest("Product with staus Expired Not Displayed, Change the Test Data");
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
