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

public class Test_Verify_Update_of_PaymentProfile extends BornInCloudTestBase {
	String [] versioncontract = null;
	String renewalLinkDisplayed="";
	String contractNumber="";
	String viewDetailButton="";
	String editRenewalLink="";
	boolean stopAutoRenewal = false;
	boolean startAutoRenewal = false;

	public Test_Verify_Update_of_PaymentProfile() {
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
			
		}
		
		updatePaymentProfile();

		// Logout
		homePage.click("arrow");

		homePage.click("signout");
	}
	public void updatePaymentProfile() throws MetadataException{
		editRenewalLink=homePage.createFieldWithParsedFieldLocatorsTokens("renewalLinkDisplayed", contractNumber);
		homePage.click(editRenewalLink);
		assertTrue("payment method page should display", homePage.checkIfElementExistsInPage("pamentMethodPage", 15));
		String[] uncheckedPaymentMethodsName=homePage.getMultipleTextValuesfromField("uncheckedpayementRadioButtonsName");
		for(int i=0;i<uncheckedPaymentMethodsName.length;i++){
			if(!uncheckedPaymentMethodsName[i].equals("****")){
				String [] cardNumber=uncheckedPaymentMethodsName[i].split(" ");
				String radioButtonXpath=homePage.createFieldWithParsedFieldLocatorsTokens("uncheckedPaymentRadioButtons", cardNumber[1]);
				homePage.check(radioButtonXpath);
				homePage.click("saveButton");
				homePage.waitForFieldVisible("productslist", 10);
				viewDetailButton=homePage.createFieldWithParsedFieldLocatorsTokens("viewDetailsButton", contractNumber);
				homePage.click(viewDetailButton);
				String newCardDisplayXpath=homePage.createFieldWithParsedFieldLocatorsTokens("isaNameDisplayedForProduct", contractNumber,cardNumber[1]);
				System.out.println(homePage
						.getFieldLocators(newCardDisplayXpath));
				assertTrue("New card information '"+uncheckedPaymentMethodsName[i]+"' should display for the contract "+contractNumber, homePage.checkIfElementExistsInPage(newCardDisplayXpath, 10));
				break;
			}
		}		
		
		
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
