
package bornincloud;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.exception.MetadataException;

public class Test_Verify_Next_Renewal_Price extends BornInCloudTestBase {
	
	String activeContractNumber="";
	String canceledContractNumber="";
	String expectedNextBillingPrice="";
	String expectedNextBillingPeriod="";
	
	public Test_Verify_Next_Renewal_Price() {
		//FirefoxProfile profile = getWebDriverPreferencesForFirefoxProfile();				
		super("Browser",getAppBrowser(),false,true);	
		
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_Rental_Entitl() throws Exception {

		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;

		env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			activeContractNumber=testProperties.getConstant("expectedContractNumberForActiveRenewal_DEV");
			canceledContractNumber=testProperties.getConstant("expectedContractNumberForCanceledRenewal_DEV");
			expectedNextBillingPrice=testProperties.getConstant("expectedNextBillingPrice_DEV");
			expectedNextBillingPeriod=testProperties.getConstant("expectedNextBillingPeriod_DEV");
			bic.login(testProperties.getConstant("USER_ID_DEV"),testProperties.getConstant("PASSWORD_DEV"));
		} else if (env.equalsIgnoreCase("STG")) {
			activeContractNumber=testProperties.getConstant("expectedContractNumberForActiveRenewal_STG");
			canceledContractNumber=testProperties.getConstant("expectedContractNumberForCanceledRenewal_STG");
			expectedNextBillingPrice=testProperties.getConstant("expectedNextBillingPrice_STG");
			expectedNextBillingPeriod=testProperties.getConstant("expectedNextBillingPeriod_STG");
			bic.login(testProperties.getConstant("USER_ID_STG"),testProperties.getConstant("PASSWORD_STG"));
		}
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
				
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);

		// Select Product Link
		bic.select("productlink");
		homePage.waitForFieldVisible("productslist", 30000);
		validateActiveContract();
		validateCanceledContract();
		validateActiveContractInContractPage();
		validateCancelContractInContractPage();
		
		// Logout
		homePage.click("arrow");

		homePage.click("signout");
	}
	public void validateActiveContract() throws MetadataException{
		
		String viewDetailsXpath=homePage.createFieldWithParsedFieldLocatorsTokens("viewProductDetailsButton", activeContractNumber);
		String nextBillingPriceXpath=homePage.createFieldWithParsedFieldLocatorsTokens("nextBillingPriceINPSPage", activeContractNumber,expectedNextBillingPrice,expectedNextBillingPeriod);
		homePage.click(viewDetailsXpath);
		assertTrue("Next bill price "+expectedNextBillingPrice+" should be displayed for the active Auto-renew contract: "+activeContractNumber, homePage.checkIfElementExistsInPage(viewDetailsXpath, 20));
		homePage.click(viewDetailsXpath);
	}
	public void validateCanceledContract() throws MetadataException{
		
		String viewDetailsXpath=homePage.createFieldWithParsedFieldLocatorsTokens("viewProductDetailsButton", canceledContractNumber);
		String nextBillingPriceXpath=homePage.createFieldWithParsedFieldLocatorsTokens("negativeNextBillingPriceINPSPage", canceledContractNumber,"$");
		homePage.click(viewDetailsXpath);
		assertFalse("Next bill price should not be displayed for the canceled Auto-renew contract: "+canceledContractNumber, homePage.checkIfElementExistsInPage(viewDetailsXpath, 20));
		homePage.click(viewDetailsXpath);
	}
	
	public void validateActiveContractInContractPage() throws MetadataException{
		 homePage.click("contractlist");
		 homePage.waitForFieldVisible("contracttable", 10);
		 String contractLinkXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractLinkOnContractPage", activeContractNumber);
		 homePage.click(contractLinkXpath);
		 homePage.waitForFieldVisible("typeINContractPage", 10);
		 String nextBillingPriceXpath=homePage.createFieldWithParsedFieldLocatorsTokens("nextBillingPriceINContractPage", "#"+activeContractNumber,expectedNextBillingPrice,expectedNextBillingPeriod);
		 assertTrue("Next bill price should be displayed in Contracts and Orders Page for the canceled Auto-renew contract: "+canceledContractNumber, homePage.checkIfElementExistsInPage(nextBillingPriceXpath, 20));
	}
	public void validateCancelContractInContractPage() throws MetadataException{
		 homePage.click("contractlist");
		 homePage.waitForFieldVisible("contracttable", 10);
		 String contractLinkXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractLinkOnContractPage", "#"+canceledContractNumber);
		 homePage.click(contractLinkXpath);
		 homePage.waitForFieldVisible("typeINContractPage", 10);
		 String nextBillingPriceXpath=homePage.createFieldWithParsedFieldLocatorsTokens("negativeNextBillingPriceINContractPage", activeContractNumber,"$");
		 assertFalse("Next bill price should be not displayed in Contracts and Orders Page for the canceled Auto-renew contract: "+canceledContractNumber, homePage.checkIfElementExistsInPage(nextBillingPriceXpath, 20));
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







