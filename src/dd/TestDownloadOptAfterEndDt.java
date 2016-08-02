package dd;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;

/**
 * Test class - TestDownloadOptAfterEndDt
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
public final class TestDownloadOptAfterEndDt extends DDTestBase {
	public TestDownloadOptAfterEndDt() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_DownloadOptAfterEndDt() throws Exception {
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
		viewContactPage = dd.getViewContactPage();
		viewContactPage.verify();

		dd.openMyDocuments();
		myDocumentsPopUp.select();
				
		ListIterator<String> prodLinePanelFieldNamesItr = productLinePanelFieldNames.listIterator();
		while (prodLinePanelFieldNamesItr.hasNext()) {
			String prodLinePanelFieldName = prodLinePanelFieldNamesItr.next();
			
			myDocumentsPage.verifyFieldNotExists(prodLinePanelFieldName);
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
