package dd;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import dd.DDConstants.ContentTypeOptions;
import dd.DDConstants.TabNameType;

import common.Page_;
import common.Util;

/**
 * Test class - TestTopLevelFilteringAndMenus
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestTopLevelFilteringAndMenus extends DDTestBase {
	public TestTopLevelFilteringAndMenus() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(DDConstants.BASE_URL);
		launchSalesforce();
	}

	@Test
	public void TEST_TopLevelFilteringAndMenus() throws Exception {
		Page_ myDocumentsPage;

		int numActual;
		int numExpected;
		boolean result;
		String productLine;
		String yearVersion;
		String tabFieldName;
		String softwareTabFieldName;
		String yearVersionFieldName;
		String softwareFileDetailsFileNamesFieldName;
		String displayName;
		
				
		
		
		//Some of these lists (esp. productLines) should live in DD (see TestPreviousVersionFunctionality.java
		//  for more notes on refactoring)
		
		
		
		
		List<String> productLines = new ArrayList<String>();
		List<String> productLineYearVersionFieldNamesExpected = new ArrayList<String>();
		List<String> tabFieldNames = new ArrayList<String>();
		List<String> fileNames = new ArrayList<String>();
		List<String> languageFilterOptions = new ArrayList<String>();
		List<String> platformFilterOptions = new ArrayList<String>();
		
		loginAsAutoUser();
		loginAsDDUser();
		
		DD dd = utilCreateDDObject();
		myDocumentsPage = dd.getMyDocumentsPage();


		//Even though this is not an "L10N test" we need to call localizeFieldLocators(myDocumentsPage)
		//  because many locators on that page are tokenized for L10N
		localizeFieldLocators(myDocumentsPage);

		//Be sure call localizeFieldLocators BEFORE calling the following methods!  Field objects whose
		//  locators contain non-L10N tokens are "cloned" into new Field objects with parsed non-L10N
		//  tokens.  We need to be sure we are doing that AFTER calling localizeFieldLocators to parse
		//  the L10N tokens!
		productLines = dd.getProductLines();
		productLineYearVersionFieldNamesExpected = dd.getProductLineYearVersionFieldNamesExpected();
		tabFieldNames = dd.getTabFieldNames(productLines);
		
		dd.openMyDocuments();
		myDocumentsPopUp.select();
				
		languageFilterOptions = myDocumentsPage.getPickListContents("language");
		numActual = languageFilterOptions.size();
		numExpected = DDConstants.NUM_LANGUAGE_PICKLIST_ENTRIES;
		assertTrueWithFlags("The number of options in the Language filter pick list (" + numActual + ") equals the expected number (" + numExpected + ")", numActual == numExpected);
		
		platformFilterOptions = myDocumentsPage.getPickListContents("platform");
		numActual = platformFilterOptions.size();
		numExpected = DDConstants.NUM_PLATFORM_PICKLIST_ENTRIES;
		assertTrueWithFlags("The number of options in the Platform filter pick list (" + numActual + ") equals the expected number (" + numExpected + ")", numActual == numExpected);
		
		//productLineConstant = myDocumentsPage.getConstant(DDConstants.DD_PRODUCT_LINE_CONSTANT_NAME);
		//productLine = myDocumentsPage.getConstant(productLineConstant);

		productLine = productLines.get(0);
		
		yearVersion = myDocumentsPage.getConstant(DDConstants.DD_YEAR_VERSION_CONSTANT_NAME);
		
		Util.printInfo("Verifying the '" + productLine + "' product line, version " + yearVersion);
		
		yearVersionFieldName = dd.getYearVersionFieldName(productLineYearVersionFieldNamesExpected, productLine, yearVersion);

		//softwareTabFieldName = dd.getContentFieldName(tabFieldNames, "softwareTab", productLine);
		softwareTabFieldName = dd.getContentFieldName(tabFieldNames, TabNameType.SOFTWARE.getFieldName(), productLine);
		
		myDocumentsPage.click(yearVersionFieldName);
		myDocumentsPage.click(softwareTabFieldName);
		
		//for (ContentTypeOptions contentTypeOption : ContentTypeOptions.values()) {
		//	displayName = contentTypeOption.getDisplayName();
		//	myDocumentsPage.populateField("contentType", displayName);
		//
		//	tabFieldName = dd.getContentFieldName(tabFieldNames, contentTypeOption.getTabFieldName(), productLine);
		//
		//	assertTrueWithFlags("The current tab is '" + displayName + "'", myDocumentsPage.getAttribute(tabFieldName, "class").equalsIgnoreCase("current"));
		//}
		
		for (TabNameType tabName : TabNameType.values()) {
			displayName = tabName.getDisplayName();
			myDocumentsPage.populateField("contentType", displayName);

			tabFieldName = dd.getContentFieldName(tabFieldNames, tabName.getFieldName(), productLine);

			assertTrueWithFlags("The current tab is '" + displayName + "'", myDocumentsPage.getAttribute(tabFieldName, "class").equalsIgnoreCase("current"));
		}
		
		//softwareFileDetailsFileNamesFieldName = dd.getContentFieldName(softwareFileDetailsFieldNames, "softwareFileDetailsFileNames", productLine);
		softwareFileDetailsFileNamesFieldName = myDocumentsPage.createFieldWithParsedFieldLocatorsTokens(DDConstants.FILE_DETAILS_FILE_NAMES_FIELD_NAME, productLine, true);				
		
		myDocumentsPage.click(softwareTabFieldName);
		
		//TODO L10N (productOptionAll)
		myDocumentsPage.populateField("platform", "All");

		ListIterator<String> languageFilterOptionsItr = languageFilterOptions.listIterator();
		while (languageFilterOptionsItr.hasNext()) {
			String languageFilterOption = languageFilterOptionsItr.next();
			
			myDocumentsPage.populateField("language", languageFilterOption);
			fileNames = myDocumentsPage.getMultipleTextValuesFromGUI(softwareFileDetailsFileNamesFieldName);

			ListIterator<String> fileNamesItr = fileNames.listIterator();
			while (fileNamesItr.hasNext()) {
				String fileName = fileNamesItr.next();
				
				//!!!!!Can't check for contains on the language options alone, because some options can contain others: "British English", "English"
				//  And we can't just check that the "super string" does not exist when testing for the presence of the "substring",
				//  because it is perfectly okay for the file name to contain both!
				//And we can't do a comparison involving the product line, because the product name in the file name is not always the
				//  same as the product line.
				//So what do we do?  I asked Kiran on 08/14, and he said not to worry about it.
				result = fileName.contains(languageFilterOption);
				
				assertTrueWithFlags("The file name '" + fileName + "' contains the Language filter option '" + languageFilterOption + "'", result);
			}
		}
		
		//TODO L10N Use default language - it will vary depend on language of user/contact
		//  who opened My Documents (will probably need to specify it in test properties,
		//  perhaps in whatever include file stores the other information for the user/contact).
		myDocumentsPage.populateField("language", "English");

		//TODO L10N (productOptionAll)
		platformFilterOptions.remove("All");
		
		ListIterator<String> platformFilterOptionsItr = platformFilterOptions.listIterator();
		while (platformFilterOptionsItr.hasNext()) {
			String platformFilterOption = platformFilterOptionsItr.next();
			
			myDocumentsPage.populateField("platform", platformFilterOption);
			fileNames = myDocumentsPage.getMultipleTextValuesFromGUI(softwareFileDetailsFileNamesFieldName);

			ListIterator<String> fileNamesItr = fileNames.listIterator();
			while (fileNamesItr.hasNext()) {
				String fileName = fileNamesItr.next();
				
				assertTrueWithFlags("The file name '" + fileName + "' contains the Platform filter option '" + platformFilterOption + "'", fileName.contains(platformFilterOption));
			}
		}
		
		dd.closeMyDocuments();
		
		logout();
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
