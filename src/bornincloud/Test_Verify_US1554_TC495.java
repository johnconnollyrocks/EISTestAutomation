package bornincloud;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebElement;
import common.EISTestBase;
import common.Util;

public class Test_Verify_US1554_TC495 extends BornInCloudTestBase {
	String expireDate = null;
	String renewalDate = null;
	String name = null;
	String creditCard = null;
	String editcreditCard = null;
	List<WebElement> productslist = null;

	public Test_Verify_US1554_TC495() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_Edit_BIC_Contract_POPUp() throws Exception {

		boolean renewal = false;
		boolean editRenewal = false;

		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;

		ArrayList<String> contract = new ArrayList<String>();

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
		// assertEquals(homePage.getValueFromGUI("titleproductservices"),
		// "Products & Services");
		homePage.waitForElementToDisappear("pageLoadImg", 10000);
				
		homePage.waitForFieldVisible("titleproductservices", 30000);
		
		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");
				
		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);

		// Select Product Link
		bic.select("productlink");
		
		homePage.waitForFieldVisible("productslist", 100000);
		if (homePage.checkFieldExistence("productslist")) {
			productslist = homePage
					.getMultipleWebElementsfromField("productslist");
			List<WebElement> productname = homePage
					.getMultipleWebElementsfromField("productname");
			List<WebElement> recurringbilling = homePage
					.getMultipleWebElementsfromField("recurringbilling");
			List<WebElement> rightcolumn = homePage
					.getMultipleWebElementsfromField("rightcolumn");
			List<WebElement> versioncontract = homePage
					.getMultipleWebElementsfromField("versioncontract");
			for (int i = 0; i < productslist.size(); i++) {
				name = productname.get(i).getText();
				productslist.get(i).click();
				if (name.contains("Fusion 360")) {
					
					Util.sleep(5000);
					if (homePage.isFieldVisible("recurringbilling")) {
						for (WebElement j : recurringbilling) {
							String content = j.getText();
							Util.printInfo(content);
							String[] data = content.split(" ");
							renewalDate = data[1] + " " + data[2] + " "
									+ data[3];
							creditCard = data[5] + data[6];
							Util.printInfo(creditCard);
							break;
						}
						for (WebElement k : versioncontract) {
							contract.add(k.getText());
						}
						for (WebElement j : rightcolumn) {
							String content = j.getText();
							if (content.contentEquals("Edit Renewal")) {
								renewal = true;
								j.click();
								
								break;
							}
						}
						
						homePage.waitForFieldPresent("editrenewalcheckbox",
								10000);
						if(homePage.checkIfElementExistsInPage("editRenewalCCError", 10)){
							EISTestBase.assertEquals("Expected Error Message Displayed", "Expected Error Message Displayed");
							homePage.click("closeeditrenewal");
							homePage.waitForElementToDisappear("closeeditrenewal", 10);
						}
						else{
							EISTestBase.failTest("Expected Error Message Not Displayed-We were unable to load your saved payment methods due to network problems. Please try again later");
						}
						

					}

				}
				
						
				productslist.get(i).click();
				Util.sleep(5000);
				

				if (renewal) {
					break;
				}
			}
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
