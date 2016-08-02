package IAE;

import static org.junit.Assert.assertEquals;

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
final class CustomerPortal extends SFDCObject {

	/**
	 * Indicator of case name to be used
	 */

	private Page_ loginPage;
	private Page_ homePage;
	private Page_ emailPage;
	private Page_ productUpdatePage;


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
	 * @Description get the Product update page
	 */
	
	public Page_ getProductUpdatePage() {
		
		return productUpdatePage;
	} 
	public void setProductUpdatePage(Page_ productUpdatepage) {
		this.productUpdatePage = productUpdatepage;
	}



	/**
	 * Class constructor specifying the Page objects necessary for interacting
	 * with CustomerPortal.
	 * 
	 * @param loginPage
	 *            the Page object used for specifying the login objects
	 * @param homePage
	 *            the Page object used for creating the home page objects
	 */
	public CustomerPortal(Page_ loginPage, Page_ homePage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD,
				EISConstants.OBJECT_TYPE_LEAD);

		this.loginPage = loginPage;
		this.homePage = homePage;
	}

	public CustomerPortal(Page_ loginPage, Page_ homePage, Page_ emailPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD,
				EISConstants.OBJECT_TYPE_LEAD);

		this.loginPage = loginPage;
		this.homePage = homePage;
		this.emailPage = emailPage;

	}
	/**
	 * @Description Adding another constructor for Product Update page
	 * @param loginPage
	 * @param homePage
	 * @param emailPage
	 * @param productUpdatePage
	 */
	public CustomerPortal(Page_ loginPage, Page_ homePage, Page_ emailPage, Page_ productUpdatePage) {		
		/* Using a different SFDC obj constant
		 * I guess it doesnt mattter if you use anu constant, the only constraint is with super constructor*/ 
		super(EISConstants.OBJECT_NAME_PREFIX_ACCT,
				EISConstants.OBJECT_TYPE_ACCT);

		this.loginPage = loginPage;
		this.homePage = homePage;
		this.emailPage = emailPage;
		this.productUpdatePage=productUpdatePage;

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
