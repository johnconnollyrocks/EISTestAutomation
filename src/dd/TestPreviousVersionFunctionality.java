package dd;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;
import common.Util;

/**
 * Test class - TestPreviousVersionFunctionality
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestPreviousVersionFunctionality extends DDTestBase {
	public TestPreviousVersionFunctionality() {
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(DDConstants.BASE_URL);
		launchSalesforce();
	}

	@Test
	public void TEST_PreviousVersionFunctionality() throws Exception {
		Page_ viewContactPage;
		Page_ myDocumentsPage;
		
		
		
		//A lot of the verification (esp. that having to do with dynamically generated
		//  Field objects) should be put into new DD methods.  Also, some of these lists
		//  (esp. productLines) should live in DD.
		
		//See other dd test methods for more stuff
		//  that should be refactored, including stuff like we have here, but also
		//  hard-coded values, etc.
		
		
		
		List<String> productLines = new ArrayList<String>();
		List<String> productLinePanelFieldNames = new ArrayList<String>();
		List<String> productLineYearVersionFieldNamesSuperset = new ArrayList<String>();
		List<String> productLineYearVersionFieldNamesExpected = new ArrayList<String>();
		List<String> productLineYearVersionFieldNamesNotExpected;
		List<String> productLineYearVersionFieldNamesActual = new ArrayList<String>();
		
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
		productLinePanelFieldNames = dd.getProductLinePanelFieldNames();
		productLineYearVersionFieldNamesSuperset = dd.getProductLineYearVersionFieldNamesSuperset(productLines);
		productLineYearVersionFieldNamesExpected = dd.getProductLineYearVersionFieldNamesExpected();
		productLineYearVersionFieldNamesNotExpected = new ArrayList<String>(productLineYearVersionFieldNamesSuperset);
		productLineYearVersionFieldNamesNotExpected.removeAll(productLineYearVersionFieldNamesExpected);
		
		viewContactPage = dd.getViewContactPage();
		viewContactPage.verify();

		dd.openMyDocuments();
		myDocumentsPopUp.select();
				
		ListIterator<String> prodLinePanelFieldNamesItr = productLinePanelFieldNames.listIterator();
		while (prodLinePanelFieldNamesItr.hasNext()) {
			String prodLinePanelFieldName = prodLinePanelFieldNamesItr.next();
			
			myDocumentsPage.verifyFieldExists(prodLinePanelFieldName);
		}
		
		ListIterator<String> productLinesItr = productLines.listIterator();
		while (productLinesItr.hasNext()) {
			String productLine = productLinesItr.next();
			
			String productLineSubstring = "_" + productLine + "_";
			
			//We need to strip spaces out of the product line sub string. In building the field names we need to search here, 
			//  spaces were removed from the product line names.  Therefore we need to do the same here if we expect the
			//  search to function properly
			productLineSubstring = productLineSubstring.replace(" ", "");
			
			productLineYearVersionFieldNamesActual.clear();

			ListIterator<String> productLineYearVersionFieldNamesExpectedItr = productLineYearVersionFieldNamesExpected.listIterator();
			while (productLineYearVersionFieldNamesExpectedItr.hasNext()) {
				String productLineYearVersionFieldNameExpected = productLineYearVersionFieldNamesExpectedItr.next();
				
				if (productLineYearVersionFieldNameExpected.contains(productLineSubstring)) {
					myDocumentsPage.verifyFieldExists(productLineYearVersionFieldNameExpected);
					
					productLineYearVersionFieldNamesActual.add(productLineYearVersionFieldNameExpected);
				}
			}

			ListIterator<String> productLineYearVersionFieldNamesNotExpectedItr = productLineYearVersionFieldNamesNotExpected.listIterator();
			while (productLineYearVersionFieldNamesNotExpectedItr.hasNext()) {
				String productLineYearVersionFieldNameNotExpected = productLineYearVersionFieldNamesNotExpectedItr.next();
				
				if (productLineYearVersionFieldNameNotExpected.contains(productLineSubstring)) {
					myDocumentsPage.verifyFieldNotExists(productLineYearVersionFieldNameNotExpected);
				}
			}
			
			String fieldName = myDocumentsPage.createFieldWithParsedFieldLocatorsTokens("2009Button", productLine, true);
			myDocumentsPage.verifyFieldNotExists(fieldName);
			
			int index = Util.listOfStringFindSubstring(productLineYearVersionFieldNamesActual, Integer.toString(DDConstants.PRODUCT_VERSION_MAX_YEAR));
			if (index >= 0) {
				productLineYearVersionFieldNamesActual.remove(index);
			}
			
			assertTrueWithFlags("For the product line '" + productLine + "', the number of year versions other than the current year (" + DDConstants.PRODUCT_VERSION_MAX_YEAR + ") is " + DDConstants.PRODUCT_VERSION_MAX_NUM_OTHER_THAN_CURRENT_YEAR + " or less", (productLineYearVersionFieldNamesActual.size() <= DDConstants.PRODUCT_VERSION_MAX_NUM_OTHER_THAN_CURRENT_YEAR)); 
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
