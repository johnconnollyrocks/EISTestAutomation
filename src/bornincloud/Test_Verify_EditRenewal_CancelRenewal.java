package bornincloud;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_EditRenewal_CancelRenewal extends BornInCloudTestBase {
	String [] versioncontract = null;
	String renewalLinkDisplayed="";
	String contractNumber="";
	String viewDetailButton="";
	String editRenewalLink="";
	boolean stopAutoRenewal = false;
	boolean startAutoRenewal = false;

	public Test_Verify_EditRenewal_CancelRenewal() {
		//FirefoxProfile profile = getWebDriverPreferencesForFirefoxProfile();				
		super("Browser",getAppBrowser(),false,true);	
		
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_EditRenewal_CancelRenewal() throws Exception {

		
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
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
		}
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
				
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);

		// Select Product Link
		bic.select("productlink");
				
		
		homePage.waitForFieldVisible("productslist", 30000);
		if (homePage.checkFieldExistence("productslist")) {
			versioncontract = homePage.getMultipleTextValuesfromField("fusioncontractNumbers");
			for (int i = 0; i < versioncontract.length; i++) {
				contractNumber = versioncontract[i];
				viewDetailButton=homePage.createFieldWithParsedFieldLocatorsTokens("viewDetailsButton", contractNumber);
				editRenewalLink=homePage.createFieldWithParsedFieldLocatorsTokens("renewalLinkDisplayed", contractNumber);
				homePage.click(viewDetailButton);
				if(homePage.checkIfElementExistsInPage(editRenewalLink, 10)){
					renewalLinkDisplayed=homePage.getValueFromGUI(editRenewalLink);
					break;
				}
			}
			switch(renewalLinkDisplayed.toLowerCase()){
				case "edit renewal":{
					stopAutoRenew();
					startAutoRenew();
					break;
				}
				case "turn on auto-renewal":{
					startAutoRenew();
					stopAutoRenew();
					break;
				}
			}
		}
		
		if (!stopAutoRenewal) {
			EISTestBase.failTest("Contract with 'Edit Renewal' link not found, so could not test the Stop Auto Renew Process , Change Test Data");
		}
		if (!startAutoRenewal) {
			EISTestBase.failTest("Contract with 'Turn on Auto-Renew' link not found, so could not test the Turn on Auto-Renew Process , Change Test Data");
		}

		// Logout
		homePage.click("arrow");

		homePage.click("signout");
	}
	public void stopAutoRenew() throws MetadataException{
		
		String contractStatusXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractStatusINPSPage", contractNumber);
		String contractStatus=homePage.getValueFromGUI(contractStatusXpath);
		String contractDateXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractDateINPSPage", contractNumber);
		String contractDate=homePage.getValueFromGUI(contractDateXpath);
		String contractNameXpath=homePage.createFieldWithParsedFieldLocatorsTokens("productNameFOrContractINPSPage", contractNumber);
		String contractProductName=homePage.getValueFromGUI(contractNameXpath);
		
		editRenewalLink=homePage.createFieldWithParsedFieldLocatorsTokens("renewalLinkDisplayed", contractNumber);
		homePage.click(editRenewalLink);
		homePage.waitForFieldVisible("cancelRenewal", 10);
		assertTrueCatchException("Cancel Renewal Link is Displayed",homePage.checkIfElementExistsInPage("cancelRenewal",20));
		validateRenewalPage(contractStatus, contractProductName, contractDate);
		homePage.click("cancelRenewal");
		homePage.waitForFieldVisible("refundcancel", 10);
		assertTrueCatchException("Refund and cancellation policy Link shold Display",homePage.checkIfElementExistsInPage("refundcancel",10));
		homePage.check("cancelconfirmation");
		homePage.waitForFieldVisible("confirmconfirmationenable", 10);
		assertTrueCatchException("Confirm Cancellation should Enabled After Selecting Yes, cancel my future renewals",homePage.checkIfElementExistsInPage("confirmconfirmationenable",10));
		homePage.click("gobacktoedit");
		homePage.waitForFieldVisible("cancelRenewal", 10);
		assertTrueCatchException("Cancel Renewal Link should Displayed After Selecting GO BACK TO EDIT Button",homePage.checkIfElementExistsInPage("cancelRenewal",10));
		homePage.click("cancelRenewal");
		homePage.click("confirmconfirmationenable");
		viewDetailButton=homePage.createFieldWithParsedFieldLocatorsTokens("viewDetailsButton", contractNumber);
		homePage.waitForFieldVisible(viewDetailButton, 10);
		homePage.click(viewDetailButton);
		String expiresStatusXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractStatusINPSPage", contractNumber);
		assertTrueCatchException("Contract status should change to 'Expires' after canceling the Auto-Renew ",homePage.getValueFromGUI(expiresStatusXpath).equalsIgnoreCase("Expires"));
		stopAutoRenewal=true;
	}
	public void startAutoRenew() throws MetadataException{
		String contractStatusXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractStatusINPSPage", contractNumber);
		String contractStatus=homePage.getValueFromGUI(contractStatusXpath);
		String contractDateXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractDateINPSPage", contractNumber);
		String contractDate=homePage.getValueFromGUI(contractDateXpath);
		String contractNameXpath=homePage.createFieldWithParsedFieldLocatorsTokens("productNameFOrContractINPSPage", contractNumber);
		String contractProductName=homePage.getValueFromGUI(contractNameXpath);
		
		editRenewalLink=homePage.createFieldWithParsedFieldLocatorsTokens("renewalLinkDisplayed", contractNumber);
		homePage.click(editRenewalLink);
		homePage.waitForFieldVisible("turnOnAutoRenew", 10);
		assertTrueCatchException("Turn on Auto Renew button should be displayed",homePage.checkIfElementExistsInPage("turnOnAutoRenew",10));
		validateRenewalPage(contractStatus, contractProductName, contractDate);
		homePage.click("turnOnAutoRenew");
		viewDetailButton=homePage.createFieldWithParsedFieldLocatorsTokens("viewDetailsButton", contractNumber);
		homePage.waitForFieldVisible(viewDetailButton, 10);
		homePage.click(viewDetailButton);
		String expiresStatusXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractStatusINPSPage", contractNumber);
		assertTrueCatchException("Contract status should change to 'Auto-Renew' after Turning on auto-Renew ",homePage.getValueFromGUI(expiresStatusXpath).equalsIgnoreCase("Auto-renews"));
		startAutoRenewal=true;
	}
	public void validateRenewalPage(String typeOfContract, String expectedProductName, String expectedDate) throws MetadataException{
		String contract="";
		if(typeOfContract.contains("Auto")){
			String [] justAutoString=typeOfContract.split("-");
			contract=justAutoString[0];
		}else{
			contract=typeOfContract;
		}
		String contractStatusXpath=homePage.createFieldWithParsedFieldLocatorsTokens("ProductStatusOnRenewalPage", contract);
		assertTrueCatchException("contract Status should show as "+typeOfContract+" on Edit renewal page", homePage.checkIfElementExistsInPage(contractStatusXpath, 10));
		assertTrueCatchException("contract Name should show as "+expectedProductName+" Edit on renewal page", homePage.getValueFromGUI("productNameOnRenewalPage").equalsIgnoreCase(expectedProductName));
		assertTrueCatchException("contract Date should show as "+expectedDate+" on Edit renewal page", homePage.getValueFromGUI("dateOnRenewalPage").equalsIgnoreCase(expectedDate));		
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
