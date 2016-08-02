package dd;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISConstants;
import common.Util;

import common.Page_;
import dd.DDConstants.TabNameType;

/**
 * Test class - TestL10N
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestL10N extends DDTestBase {
	public TestL10N() {
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(DDConstants.BASE_URL);
		launchSalesforce();
	}

	@Test
	public void TEST_L10N() throws Exception {
		Page_ myDocumentsPage;

		int numActual;
		int numExpected;
		String tabFieldName;
		String tabDisplayName = null;
		String yearVersionFieldName;
		String softwareTabFieldName;
		String fileDetailsFileNamesFieldName;
		String fileNameActual;
		String productLine;
		String yearVersion;
		String localeString;
		
		List<String> productLines = new ArrayList<String>();
		List<String> productLineYearVersionFieldNamesExpected = new ArrayList<String>();
		List<String> tabFieldNames = new ArrayList<String>();
		List<String> languageFilterOptionsActual = new ArrayList<String>();
		List<String> platformFilterOptionsActual = new ArrayList<String>();
		List<String> downloadPreferenceOptionsActual = new ArrayList<String>();
		List<String> fileNamesActual = new ArrayList<String>();
		List<String> buttonFieldNames = new ArrayList<String>();
		List<String> dropDownValueFieldNames = new ArrayList<String>();

		loginAsAutoUser();
		loginAsDDUser();
		
		DD dd = utilCreateDDObject();
		myDocumentsPage = dd.getMyDocumentsPage();
		
		
		localeString = getLocaleString(EISConstants.LANGUAGE_CODE_ENUM_CONSTANT_NAME, EISConstants.COUNTRY_CODE_ENUM_CONSTANT_NAME);
		setResourceBundle(DDConstants.DEFAULT_L10N_BUNDLE + localeString);
		
		//localizeFieldLocators(myDocumentsPage, DDConstants.DEFAULT_L10N_BUNDLE + localeString);
		//localizeTabNames(DDConstants.DEFAULT_L10N_BUNDLE + localeString);
		localizeFieldLocators(myDocumentsPage);
		localizeTabNames();
		
		//Be sure call localizeFieldLocators BEFORE calling the following methods!  Field objects whose
		//  locators contain non-L10N tokens are "cloned" into new Field objects with parsed non-L10N
		//  tokens.  We need to be sure we are doing that AFTER calling localizeFieldLocators to parse
		//  the L10N tokens!
		productLines = dd.getProductLines();
		productLineYearVersionFieldNamesExpected = dd.getProductLineYearVersionFieldNamesExpected();
		tabFieldNames = dd.getTabFieldNames(productLines);
		buttonFieldNames = dd.getFileDetailsButtonFieldNames(productLines);
		dropDownValueFieldNames = dd.getFileDetailsDropDownValuesFieldNames(productLines);
		
		dd.openMyDocuments("viewContactsProductDownloadsInContactsLanguageLink");
		myDocumentsPopUp.select();
		
		//Verify the elements in the page header and footer (start)
		myDocumentsPage.verifyFieldStartsWith("welcomeMessage", getLocalizedString("welcome"));
		myDocumentsPage.verifyField("myProfile", getLocalizedString("myProfile"));
		myDocumentsPage.verifyField("signOut", getLocalizedString("signOut"));
		myDocumentsPage.verifyField("search", getLocalizedString("search"));
		myDocumentsPage.verifyField("privacyPolicy", getLocalizedString("privacyPolicy"));
		myDocumentsPage.verifyField("legalNoticesAndTrademarks", getLocalizedString("legalNoticesAndTrademarks"));
		//Verify the elements in the page header and footer (end)
		
		//Verify the content of the Export Disclosure (start)
		myDocumentsPage.verifyField("exportDisclosureLabel", getLocalizedString("exportDisclosureLabel"));
		myDocumentsPage.verifyField("exportDisclosureContent", getLocalizedString("exportDisclosureContent"));
		//Verify the content of the Export Disclosure (end)
		
		//Verify the top level filters (start)
		myDocumentsPage.verifyField("languageLabel", getLocalizedString("language"));
		myDocumentsPage.verifyField("platformLabel", getLocalizedString("platform"));
		myDocumentsPage.verifyField("contentTypeLabel", getLocalizedString("contentType"));
		myDocumentsPage.verifyField("productSearchLabel", getLocalizedString("productSearch"));

		languageFilterOptionsActual = myDocumentsPage.getPickListContents("language");
		numActual = languageFilterOptionsActual.size();
		numExpected = DDConstants.NUM_LANGUAGE_PICKLIST_ENTRIES;
		assertTrueWithFlags("The number of options in the Language filter pick list (" + numActual + ") equals the expected number (" + numExpected + ")", numActual == numExpected);
		
		ListIterator<String> l10nLanguageFilterOptionsExpectedItr = DDConstants.EXPECTED_L10N_LANGUAGE_FILTER_OPTIONS.listIterator();
//		while (l10nLanguageFilterOptionsExpectedItr.hasNext()) {
//			String l10nLanguageFilterOptionExpected = l10nLanguageFilterOptionsExpectedItr.next();
//			
//			String languageFilterOptionExpected = getLocalizedString(l10nLanguageFilterOptionExpected);
//			
//			assertTrueWithFlags("The language '" + languageFilterOptionExpected + "' was found in the Language filter pick list", languageFilterOptionsActual.contains(languageFilterOptionExpected));
//		}

		platformFilterOptionsActual = myDocumentsPage.getPickListContents("platform");
		numActual = platformFilterOptionsActual.size();
		numExpected = DDConstants.NUM_PLATFORM_PICKLIST_ENTRIES;
		assertTrueWithFlags("The number of options in the Platform filter pick list (" + numActual + ") equals the expected number (" + numExpected + ")", numActual == numExpected);

		assertTrueWithFlags("The platform 'All' was found in the Platform filter pick list", platformFilterOptionsActual.contains(getLocalizedString("platformOptionAll")));
		
		myDocumentsPage.verifyField("goButton", getLocalizedString("goButton"));
		
		myDocumentsPage.populateField("productSearch", "blah");
		myDocumentsPage.click("goButton");
		myDocumentsPage.verifyField("clearButton", getLocalizedString("clearButton"));
		//Verify the top level filters (end)
		
		//Verify the options in the Download Preference pick list (start)
		downloadPreferenceOptionsActual = myDocumentsPage.getPickListContents("downloadPreference");
		numActual = downloadPreferenceOptionsActual.size();
		numExpected = DDConstants.NUM_DOWNLOAD_PREFERENCE_PICKLIST_ENTRIES;
		assertTrueWithFlags("The number of options in the Download Preference pick list (" + numActual + ") equals the expected number (" + numExpected + ")", numActual == numExpected);
		
//		ListIterator<String> l10nDownloadPreferenceOptionsExpectedItr = DDConstants.EXPECTED_L10N_DOWNLOAD_PREFERENCE_OPTIONS.listIterator();
//		while (l10nDownloadPreferenceOptionsExpectedItr.hasNext()) {
//			String l10nDownloadPreferenceOptionExpected = l10nDownloadPreferenceOptionsExpectedItr.next();
//			
//			String downloadPreferenceOptionExpected = getLocalizedString(l10nDownloadPreferenceOptionExpected);
//			
//			assertTrueWithFlags("The '" + downloadPreferenceOptionExpected + "' option was found in the Download Preference pick list", downloadPreferenceOptionsActual.contains(downloadPreferenceOptionExpected));
//		}
		//Verify the options in the Download Preference pick list (end)
		
		//Verify navigation elements on left side (start)
		if(!localeString.equals("_fr")){
		myDocumentsPage.verifyField("subscriptionHome", getLocalizedString("subscriptionHome"));
		myDocumentsPage.verifyField("needHelpContactUs", getLocalizedString("needHelpContactUs"));
		}
		myDocumentsPage.verifyField("downloads", getLocalizedString("downloads"));
		myDocumentsPage.verifyField("relatedInformationText", getLocalizedString("relatedInformation"));
		
		//Verify navigation elements on left side (end)
		
		productLine = productLines.get(0);
		
		yearVersion = myDocumentsPage.getConstant(DDConstants.DD_YEAR_VERSION_CONSTANT_NAME);
		
		Util.printInfo("Verifying the tab text for the '" + productLine + "' product line, version " + yearVersion);
		
		//Verify the tab names on the product line (start)
		yearVersionFieldName = dd.getYearVersionFieldName(productLineYearVersionFieldNamesExpected, productLine, yearVersion);

		myDocumentsPage.click(yearVersionFieldName);

		for (TabNameType tabName : TabNameType.values()) {
			tabDisplayName = tabName.getDisplayName();

			tabFieldName = dd.getContentFieldName(tabFieldNames, tabName.getFieldName(), productLine);

			//assertTrueWithFlags("The current tab is '" + displayName + "'", myDocumentsPage.getAttribute(tabFieldName, "class").equalsIgnoreCase("current"));
			myDocumentsPage.verifyFieldStartsWith(tabFieldName, tabDisplayName);
		}
		//Verify the tab names on the product line (end)

		//Verify the split button text and drop down options (start)		
		Util.printInfo("Verifying a split button on the Software tab");

		softwareTabFieldName = dd.getContentFieldName(tabFieldNames, TabNameType.SOFTWARE.getFieldName(), productLine);
		myDocumentsPage.click(softwareTabFieldName);

		fileDetailsFileNamesFieldName = myDocumentsPage.createFieldWithParsedFieldLocatorsTokens(DDConstants.FILE_DETAILS_FILE_NAMES_FIELD_NAME, productLine, true);				
		fileNamesActual = myDocumentsPage.getMultipleTextValuesFromGUI(fileDetailsFileNamesFieldName);
		fileNameActual = fileNamesActual.get(0);

		ListIterator<String> buttonFieldNamesItr = DDConstants.FILE_DETAILS_BUTTON_FIELD_NAMES.listIterator();
		while (buttonFieldNamesItr.hasNext()) {
			String buttonFieldName = buttonFieldNamesItr.next();
			
			String tokenizedFileDetailsButtonFieldName = dd.getContentFieldName(buttonFieldNames, buttonFieldName, productLine);
			String fileDetailsButtonFieldName = myDocumentsPage.createFieldWithParsedFieldLocatorsTokens(tokenizedFileDetailsButtonFieldName, fileNameActual, true);				
						
			
			//TODO:  use an enumeration
			switch (buttonFieldName) {
				case "fileDetailsGetSoftwareButton":	{
					myDocumentsPage.verifyFieldExists(fileDetailsButtonFieldName);
					break;
				}
				case "fileDetailsGetSoftwareButtonDropdown":	{
					if (myDocumentsPage.isFieldPresent(fileDetailsButtonFieldName)) {
						
						//Since the locator for the drop down option Field contains the actual text
						//  of the button, all we need to do in order to verify the text is to verify
						//  that it exists
						ListIterator<String> dropDownValuesFieldNamesItr = DDConstants.FILE_DETAILS_DROP_DOWN_VALUES_FIELD_NAMES.listIterator();
						while (dropDownValuesFieldNamesItr.hasNext()) {
							String dropDownValueFieldName = dropDownValuesFieldNamesItr.next();
							
							String tokenizedFileDetailsDropDownValueFieldName = dd.getContentFieldName(dropDownValueFieldNames, dropDownValueFieldName, productLine);
							String fileDetailsDropDownValueFieldName = myDocumentsPage.createFieldWithParsedFieldLocatorsTokens(tokenizedFileDetailsDropDownValueFieldName, fileNameActual, true);
							myDocumentsPage.verifyFieldExists(fileDetailsDropDownValueFieldName);
						}
					}
					
					break;
				}
			}
		}
		//Verify the split button text and drop down options (start)
	}

	@After
	public void tearDown() throws Exception {
		//Close the browser. Call stop on the WebDriverBackedSelenium instance
		//  instead of calling driver.quit(). Otherwise, the JVM will continue
		//  running after the browser has been closed.
		driver.quit();
		
		//TODO  Figure out how to determine if the test code has failed in a
		//  manner other than by EISTestBase.fail() being called.  Otherwise,
		//  finish() will always print the default passed message to the console.
		finish();
	}
}
