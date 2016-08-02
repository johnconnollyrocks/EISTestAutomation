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

public class Test_Verify_Order_Error extends BornInCloudTestBase {
	String contractNumber="";
	String viewContractDetailXpath="";
	String expectedCustomerSupportURL="";

	public Test_Verify_Order_Error() {
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
			expectedCustomerSupportURL=testProperties.getConstant("expectedCustomerSupportURL_DEV");
			bic.login(testProperties.getConstant("USER_ID_DEV"),
					testProperties.getConstant("PASSWORD_DEV"));
		} else if (env.equalsIgnoreCase("STG")) {
			expectedCustomerSupportURL=testProperties.getConstant("expectedCustomerSupportURL_STG");
			bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
		}
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
				
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);
		
		bic.select("productlink");
		homePage.waitForFieldVisible("productslist", 30000);
		String[] errorContractNumbers=homePage.getMultipleTextValuesfromField("errorContractNumber");
		for(int i=0;i<errorContractNumbers.length;i++){
			contractNumber=errorContractNumbers[i];
			break;
		}
		assertTrue("Contract found with Order error warning . Contract Number: "+contractNumber, (contractNumber!=""));
		moveMouseOnOrderErroIconInPs("errorIconInDetailPage");
		validateEditPaymentButton();
        homePage.click("contractlist");
        homePage.waitForFieldVisible("errorIconONContractsPage",20);
        moveMouseOnOrderErroIconInPs("errorIconONContractsPage");
		validateEditPaymentButton();
		homePage.click("productsAndServicesLink");
		homePage.waitForFieldVisible("productlink");
		bic.select("productlink");
		moveMouseOnOrderErroIconInPs("errorIconInDetailPage");
		validateCustomerSupportButton();
		homePage.click("contractlist");
	    homePage.waitForFieldVisible("errorIconONContractsPage",20);
		moveMouseOnOrderErroIconInPs("errorIconONContractsPage");
		validateCustomerSupportButton();
		String contractLinkXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractLinkOnContractPage", contractNumber);
		homePage.click(contractLinkXpath);
		homePage.waitForFieldVisible("typeINContractPage", 10);
		moveMouseOnOrderErroIconInPs("orderErrorImInDetailSubscriptionPage");
		validateEditPaymentButton();
		moveMouseOnOrderErroIconInPs("orderErrorImInDetailSubscriptionPage");
		validateCustomerSupportButton();
		 
		// Logout
		homePage.click("arrow");

		homePage.click("signout");
	}
	
	public void moveMouseOnOrderErroIconInPs(String fieldName) throws MetadataException{
		viewContractDetailXpath=homePage.createFieldWithParsedFieldLocatorsTokens("viewProductDetailsButton", contractNumber);
		homePage.click(viewContractDetailXpath);
		String errorIconXpath=homePage.createFieldWithParsedFieldLocatorsTokens(fieldName, contractNumber);
		List<WebElement> erroIcon=homePage.getMultipleWebElementsfromField(errorIconXpath);
		mouseHover(erroIcon.get(0));
		Util.sleep(2000);
	}
	
	
	public void validateEditPaymentButton() throws MetadataException{
		assertTrue("Edit payment buttons should displayed after moving mouse on Order error icon", homePage.checkIfElementExistsInPage("editPaymentButton", 20));
		homePage.click("editPaymentButton");
		assertTrue("Edit renewal page should displayed after clicking on Edit Payment button", homePage.checkIfElementExistsInPage("editRenewalPage", 20));
		homePage.click("cancelRenewalButton");
		homePage.waitForFieldVisible("productlink",20);
	}
	
	public void validateCustomerSupportButton() throws MetadataException{
		assertTrue("Contact Support buttons should displayed after moving mouse on Order error icon", homePage.checkIfElementExistsInPage("contactSupportButton", 20));
		homePage.click("contactSupportButton");
		String winHandleBefore = homePage.getWindowHandle();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        String expectedURL=expectedCustomerSupportURL;
		assertTrueCatchException("Expected link found after clicking on Contact Support button. Actual URL:"+getCurrentURL()+" Expected URL: "+expectedURL, getCurrentURL().equalsIgnoreCase(expectedURL));
        driver.close();
        driver.switchTo().window(winHandleBefore);
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
