package bornincloud;

import static org.junit.Assert.assertEquals;



import common.EISConstants;
import common.Page_;
import common.SFDCObject;
import common.Util;

/**
 * Representation of a My Autodesk Portal.
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
final class BornInCloud extends SFDCObject {

	/**
	 * Indicator of case name to be used
	 */

	private Page_ loginPage;
	private Page_ homePage;
	private Page_ emailPage;


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
	
	public Page_ getEmailPage() {
		return emailPage;
	}

	public void setEmailPage(Page_ emailPage) {
		this.emailPage = emailPage;
	}



	/**
	 * Class constructor specifying the Page objects necessary for interacting
	 * with BornInCloud.
	 * 
	 * @param loginPage
	 *            the Page object used for specifying the login objects
	 * @param homePage
	 *            the Page object used for creating the home page objects
	 */
	public BornInCloud(Page_ loginPage, Page_ homePage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD,
				EISConstants.OBJECT_TYPE_LEAD);

		this.loginPage = loginPage;
		this.homePage = homePage;
	}

	public BornInCloud(Page_ loginPage, Page_ homePage, Page_ emailPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD,
				EISConstants.OBJECT_TYPE_LEAD);

		this.loginPage = loginPage;
		this.homePage = homePage;
		this.emailPage = emailPage;

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
	
	public void login(String login, String password){
		loginPage.populateField("userID", login);
		loginPage.populateField("password", password);
		loginPage.click("submit");
		Util.printInfo("Loging to Customer portal Using below Credentials.");
		Util.printInfo("UserID:"+login);
		Util.printInfo("Password:"+password);
	}
	
	public void select(String name){
		homePage.click(name);
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
	 
	 public void assertStrings(String expected, String actual) {
		 try{				
				assertEquals(expected,actual);
				System.out.println("Assert Passed" + "\n");
		    }
		    catch (AssertionError e){
		    	System.out.println("Assertion Failed : Expected = " + expected + ", Actual = " + actual + "\n");
		    }
	    }
	 public void assertInts(int expected, int actual) {
			// TODO Auto-generated method stub
		 try{				
				assertEquals(expected,actual);
				System.out.println("Assert Passed" + "\n");
		    }
		    catch (AssertionError e){
		    	System.out.println("Assertion Failed : Expected = " + expected + ", Actual = " + actual + "\n");
		    }
		}
	 
}
