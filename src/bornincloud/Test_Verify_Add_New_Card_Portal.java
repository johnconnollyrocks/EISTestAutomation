package bornincloud;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_Add_New_Card_Portal extends BornInCloudTestBase {
	String [] versioncontract = null;
	String renewalLinkDisplayed="";
	String contractNumber="";
	String viewDetailButton="";
	String editRenewalLink="";
	boolean addCardToEditRenewal = false;
	boolean addCardToTrunOnAutoRenwal = false;

	public Test_Verify_Add_New_Card_Portal() {
		//FirefoxProfile profile = getWebDriverPreferencesForFirefoxProfile();				
		super("Browser",getAppBrowser(),false,true);	
		
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_Add_New_Card() throws Exception {

		
		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;
		env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("USER_ID_DEV"),testProperties.getConstant("PASSWORD_DEV"));
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("USER_ID_STG"),testProperties.getConstant("PASSWORD_STG"));
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
					addCardToEditRenewal();
					addCardToTurnOnAutoRenewal();
					break;
				}
				case "turn on auto-renewal":{
					addCardToTurnOnAutoRenewal();
					addCardToEditRenewal();
					break;
				}
			}
		}
		
		if (!addCardToEditRenewal) {
			EISTestBase.failTest("Contract with 'Edit Renewal' link not found, so could not add new card and validate, Change Test Data");
		}
		if (!addCardToTrunOnAutoRenwal) {
			EISTestBase.failTest("Contract with 'Turn on Auto-Renew' link not found, so could not add new card and validate , Change Test Data");
		}

		// Logout
		homePage.click("arrow");

		homePage.click("signout");
	}
	public void addCardToEditRenewal() throws MetadataException{
		editRenewalLink=homePage.createFieldWithParsedFieldLocatorsTokens("renewalLinkDisplayed", contractNumber);
		homePage.click(editRenewalLink);
		enterNewCardDetails();		
		homePage.click("saveButtonOnAddCardPage");
		validateAfterAddingCard();
		addCardToEditRenewal=true;
	}
	public void addCardToTurnOnAutoRenewal() throws MetadataException{
		editRenewalLink=homePage.createFieldWithParsedFieldLocatorsTokens("renewalLinkDisplayed", contractNumber);
		homePage.click(editRenewalLink);
		enterNewCardDetails();		
		homePage.click("turnOnAutoRenewButtonOnAddCardPage");
		validateAfterAddingCard();
		addCardToTrunOnAutoRenwal=true;
	}
	public void enterNewCardDetails() throws MetadataException{
		homePage.waitForFieldVisible("addnewCardLink", 10);
		assertTrueCatchException("Add new Card link should display in Edit renewal page",homePage.checkIfElementExistsInPage("addnewCardLink",20));
		homePage.click("addnewCardLink");
		homePage.waitForFieldVisible("firstNameField", 10);
		homePage.populateField("firstNameField",getUniqueString(5));
		homePage.populateField("lastNameField",getUniqueString(5));
		homePage.populateField("cardNumberField",testProperties.getConstant("cardNumber_"+getEnvironment().toUpperCase()));
		homePage.populateField("expireMonthSelectBox",testProperties.getConstant("expMonth_"+getEnvironment().toUpperCase()));
		homePage.populateField("expireYearSelectBox",testProperties.getConstant("expYear_"+getEnvironment().toUpperCase()));
		homePage.populateField("securityCodeField",testProperties.getConstant("securityCode_"+getEnvironment().toUpperCase()));
	}
	
	public void validateAfterAddingCard() throws MetadataException{
		viewDetailButton=homePage.createFieldWithParsedFieldLocatorsTokens("viewDetailsButton", contractNumber);
		homePage.waitForFieldVisible(viewDetailButton, 10);
		homePage.click(viewDetailButton);
		String cardNumber=testProperties.getConstant("cardNumber_"+getEnvironment().toUpperCase());
		int cardNumberLen=cardNumber.length();
		String lastFourDigits=cardNumber.substring(cardNumberLen-4, cardNumberLen);
		Util.printInfo("Last Four digits of card: "+lastFourDigits);
		String checkNewCardDisplayedXpath=homePage.createFieldWithParsedFieldLocatorsTokens("validateContract", "****"+testProperties.getConstant("cardDetails"));
		assertTrueCatchException("New card details '"+lastFourDigits+"' should be displayed for the contract: "+contractNumber,homePage.checkIfElementExistsInPage(checkNewCardDisplayedXpath, 20));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}
