package dd;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;

/**
 * Test class - TestDownloadPreferenceFunctionality
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestDownloadPreferenceFunctionality extends DDTestBase {
	public TestDownloadPreferenceFunctionality() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(DDConstants.BASE_URL);
		launchSalesforce();
	}

	@Test
	public void TEST_DownloadPreferenceFunctionality() throws Exception {
		Page_ myDocumentsPage;

		List<String> downloadPreferenceOptionsActual = new ArrayList<String>();
		int numActual;
		int numExpected;
		
		loginAsAutoUser();
		loginAsDDUser();
		
		DD dd = utilCreateDDObject();
		myDocumentsPage = dd.getMyDocumentsPage();
		
		//Even though this is not a "L10N test" we need to call localizeFieldLocators(myDocumentsPage)
		//  because many locators on that page are tokenized for L10N
		localizeFieldLocators(myDocumentsPage);

		dd.openMyDocuments();
		myDocumentsPopUp.select();
				
		downloadPreferenceOptionsActual = myDocumentsPage.getPickListContents("downloadPreference");
		numActual = downloadPreferenceOptionsActual.size();
		numExpected = DDConstants.NUM_DOWNLOAD_PREFERENCE_PICKLIST_ENTRIES;
		assertTrueWithFlags("The number of options in the Download Preference pick list (" + numActual + ") equals the expected number (" + numExpected + ")", numActual == numExpected);
		
		ListIterator<String> l10nDownloadPreferenceOptionsExpectedItr = DDConstants.EXPECTED_L10N_DOWNLOAD_PREFERENCE_OPTIONS.listIterator();
		while (l10nDownloadPreferenceOptionsExpectedItr.hasNext()) {
			String l10nDownloadPreferenceOptionExpected = l10nDownloadPreferenceOptionsExpectedItr.next();
			
			String downloadPreferenceOptionExpected = getLocalizedString(l10nDownloadPreferenceOptionExpected);
			
			assertTrueWithFlags("The '" + downloadPreferenceOptionExpected + "' option was found in the Download Preference pick list", downloadPreferenceOptionsActual.contains(downloadPreferenceOptionExpected));
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
