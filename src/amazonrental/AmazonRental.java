package amazonrental;

import static org.junit.Assert.assertEquals;
import common.EISConstants;
import common.Page_;
import common.SFDCObject;
import common.Util;

/**
 * Representation of a My Autodesk Portal.
 * 
 * @author 
 * @version 1.0.0
 */
final class AmazonRental extends SFDCObject {

	/**
	 * Indicator of case name to be used
	 */

	private Page_ loginPage;
	private Page_ instantAccessPage;
	


	public Page_ getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(Page_ loginPage) {
		this.loginPage = loginPage;
	}

	public Page_ instantAccessPage() {
		return instantAccessPage;
	}
	
	public void setinstantAccessPage(Page_ instantAccessPage) {
		this.instantAccessPage = instantAccessPage;
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
	public AmazonRental(Page_ loginPage, Page_ instantAccessPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD,
				EISConstants.OBJECT_TYPE_LEAD);

		this.loginPage = loginPage;
		this.instantAccessPage = instantAccessPage;
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
	
	public void loginAmazon(String login, String password){
		loginPage.populateField("userID", login);
		loginPage.populateField("password", password);
		loginPage.click("signInButton");
		Util.printInfo("Loging to amazon rental Using below Credentials.");
		Util.printInfo("UserID:"+login);
		Util.printInfo("Password:"+password);
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
