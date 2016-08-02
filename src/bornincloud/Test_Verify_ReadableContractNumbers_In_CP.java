package bornincloud;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;

public class Test_Verify_ReadableContractNumbers_In_CP extends BornInCloudTestBase {

	public String href;
	public String winHandleBefore;
	public String expectedURL;
	List<WebElement> productslist = null;
	String name = null;
	public Test_Verify_ReadableContractNumbers_In_CP() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Verify_ReadableContractNumbers() throws Exception {

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

		homePage.waitForElementToDisappear("pageLoadImg", 100000);
		homePage.waitForFieldVisible("titleproductservices", 300000);

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed.", homePage.checkIfElementExistsInPage("titleproductservices", 20));
		
		homePage.click("productlink");
		homePage.waitForFieldVisible("productslist", 30000);
		
		if (homePage.checkFieldExistence("productslist")) {
			productslist = homePage
					.getMultipleWebElementsfromField("productslist");
			List<WebElement> productname = homePage
					.getMultipleWebElementsfromField("productname");			
			List<WebElement> versioncontract = homePage
					.getMultipleWebElementsfromField("versioncontract");
			for (int i = 0; i < productslist.size(); i++) {
				name = productname.get(i).getText();
				if (name.contains("Fusion 360")) {
					String contractNumber1=versioncontract.get(i).getText();
					String[] temp=contractNumber1.split("# ");
					String contractNumber=temp[1];
					if(!contractNumber.equals(null)){
						String firstLetter = String.valueOf(contractNumber.charAt(0));
						if (contractNumber.matches("[A-Z0-9]+") && contractNumber.length() == 8 && !(firstLetter=="1") && !(firstLetter=="3") && !(firstLetter=="4") && !(firstLetter=="7")){
							assertTrue("BIC contract format is displayed as defined in US1640. Contract Number: "+contractNumber,true);
							break;
						}else{
							EISTestBase.failTest("BIC contract format is not displayed as defined in US1640. Contract Number: "+contractNumber);
						}
					}
				}
			}
		}
		

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
