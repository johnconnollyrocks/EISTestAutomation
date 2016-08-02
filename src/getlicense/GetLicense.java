package getlicense;

import common.EISConstants;
import common.Page_;
import common.SFDCObject;

/**
 * Representation of a Major Accounts SFDC subscription renewal.
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
final class GetLicense extends SFDCObject {

	/**
	 * Indicator of case name to be used
	 */

	private Page_ homePage;

	public Page_ getHomePage() {
		return homePage;
	}

	public void setHomePage(Page_ homePage) {
		this.homePage = homePage;
	}

	/**
	 * Class constructor specifying the Page objects necessary for interacting
	 * with CEP.
	 * 
	 * @param loginPage
	 *            the Page object used for specifying the login objects
	 * @param homePage
	 *            the Page object used for creating the home page objects
	 */
	public GetLicense(Page_ loginPage, Page_ homePage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD,
				EISConstants.OBJECT_TYPE_LEAD);

		this.homePage = homePage;

	}

	@Override
	protected boolean open() {
		boolean pageLoaded;

		mainWindow.select();

		pageLoaded = super.open();
		if (pageLoaded) {
			waitForPageToSettle();
		}

		return pageLoaded;
	}

}
