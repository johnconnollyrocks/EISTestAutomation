package cepfrontend;

import common.EISConstants;
import common.Page_;
import common.SFDCObject;

/**
 * Representation of a Major Accounts SFDC subscription renewal.
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
final class CEP extends SFDCObject {

	/**
	 * Indicator of case name to be used
	 */

	private Page_ loginPage;
	private Page_ homePage;

	public Page_ getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(Page_ loginPage) {
		this.loginPage = loginPage;
	}

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
	public CEP(Page_ loginPage, Page_ homePage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD,
				EISConstants.OBJECT_TYPE_LEAD);

		this.loginPage = loginPage;
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
