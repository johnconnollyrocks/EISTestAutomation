package sfdc;

import common.EISConstants;
import common.Page_;
import common.SFDCObject;
import common.Util;

/**
 * Constants and enumerated types specific to the SCAT application.
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
final class Contacts extends SFDCObject {
	// TODO Will need to handle users in a more intelligent manner. We don't
	// want to have to enter them in the test
	// properties file, not even in an include file. We should create one or
	// more collection objects (simple properties?)
	// to store user names and passwords. The user would be looked up by... what
	// - role? role and name? Note that we can
	// break out the environment component (e.g., "adsksfstg") and use it as a
	// key to one of the collections. We would pass
	// an environment code property to the test, which would be used in looking
	// up the user.

	private String searchContacts = "";
	private Page_ searchContactsPage;
	private Page_ viewContactsPage;

	/**
	 * Class constructor specifying the Page objects necessary for interacting
	 * with an SFDC Lead.
	 * 
	 * @param createLeadRTPage
	 *            the Page object used for specifying the record type when
	 *            creating the SFDC Lead
	 * @param createLeadPage
	 *            the Page object used for creating the SFDC Lead
	 * @param viewLeadPage
	 *            the Page object used for viewing the SFDC Lead
	 */
	Contacts(Page_ searchContactsPage, Page_ viewContactsPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD,
				EISConstants.OBJECT_TYPE_LEAD);

		// mainWindow = EISTestBase.mainWindow;
		// commonPage = EISTestBase.commonPage;

		this.searchContactsPage = searchContactsPage;
		this.viewContactsPage = viewContactsPage;
	}

	public String getSearchContacts() {
		return searchContacts;
	}

	public void setSearchContacts(String searchContacts) {
		this.searchContacts = searchContacts;
	}

	public Page_ getSearchContactsPage() {
		return searchContactsPage;
	}

	public void setSearchContactsPage(Page_ searchContactsPage) {
		this.searchContactsPage = searchContactsPage;
	}

	public Page_ getViewContactsPage() {
		return viewContactsPage;
	}

	public void setViewContactsPage(Page_ viewContactsPage) {
		this.viewContactsPage = viewContactsPage;
	}

	void searchContact() {
		mainWindow.select();
		Util.sleep(3000);

		searchContactsPage.waitForPageToSettle(searchContacts, true, 5000);
		searchContactsPage.click("searchContacts");
		searchContactsPage.populateField("searchContacts");

		searchContactsPage.clickAndWait("searchButton", "searchAgainButton");
		searchContactsPage.clickAndWait("contact", "shareButton");

		String locator = searchContactsPage
				.clickAndWaitForPopUpToOpen("viewContact");
		viewContactsPage.selectWindow(locator);
		Util.sleep(5000);
	}

}