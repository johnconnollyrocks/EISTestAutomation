package myautodeskportal;

import common.EISConstants;
import common.Page_;
import common.SFDCObject;
import common.Util;

/**
 * Representation of a My Autodesk Portal.
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
final class MyAutodeskPortal extends SFDCObject {

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
	 * with MyAutodeskPortal.
	 * 
	 * @param loginPage
	 *            the Page object used for specifying the login objects
	 * @param homePage
	 *            the Page object used for creating the home page objects
	 */
	public MyAutodeskPortal(Page_ loginPage, Page_ homePage) {
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
	
	 public void changepassword(String oldPswd,String newPswd){
		 homePage.populateField("oldPassword",oldPswd) ;
		 homePage.populateField("newPassword",newPswd) ;
		 homePage.populateField("confirmNewPassword",newPswd) ;
		 homePage.click("savePassword") ;
		 Util.sleep(5000);
		 Util.printInfo("Changed password from "+ oldPswd+" to "+newPswd);
	 }
	 public void  changeAccountDetails(String instance){
		 homePage.populateInstance(instance) ; 
		 homePage.click("saveAccount");
		 Util.sleep(5000);
		 Util.printInfo("Changed Account details");
	 }
	 public void  verifyAccountDetails(String instance){
		 homePage.verifyInstance(instance) ; 
	 }
}
