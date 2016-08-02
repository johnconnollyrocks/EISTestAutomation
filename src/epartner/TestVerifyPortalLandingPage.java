package epartner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Page_;

/**
 * Test class - TestVerifyPortalLandingPage
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestVerifyPortalLandingPage extends EpartnerTestBase {
	public TestVerifyPortalLandingPage() {
		super("firefox");
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(EpartnerConstants.BASE_URL);
		launchSalesforce(getBasePartnerURL());
	}

	@Test
	public void TEST_VerifyPortalLandingPage() throws Exception {
		
		Partner partner = utilCreatePartnerObject();
		Page_ portalLandingPage = partner.getPortalLandingPage();
		
		loginAsEpartnerUser(testProperties.getConstant("USER_NAME"), testProperties.getConstant("PASSWORD"), false);
		
		partner.setUrl();
		
		portalLandingPage.verify();
		
		//Common links ****************************************************************
		portalLandingPage.clickAndWait("changeAccountLink", "partnerCenterHome");
		portalLandingPage.verifyFieldExists("onlyOneAccountMessage");

		partner.open();
		
		portalLandingPage.clickAndWait("myProfileLink", "partnerCenterHome");
		portalLandingPage.verifyFieldExists("editUserProfileHeader");

		partner.open();

		portalLandingPage.clickAndWait("customerSearchLink", "partnerCenterHome");
		portalLandingPage.verifyFieldExists("accountsViewList");

		partner.open();
		//*****************************************************************************

		//Vertical-specific links ******************************************************
		if (portalLandingPage.isFieldPresent("dealRegACELink")) {
			portalLandingPage.clickAndWait("dealRegACELink", "partnerCenterHome");
			portalLandingPage.verifyFieldExists("opptysViewList");
			
			partner.open();
		}
		
		if (portalLandingPage.isFieldPresent("businessServicesLink")) {
			portalLandingPage.clickAndWait("businessServicesLink", "partnerCenterHome");
			portalLandingPage.verifyFieldExists("casesViewList");
			
			partner.open();
		}
		
		if (portalLandingPage.isFieldPresent("channelPlansLink")) {
			portalLandingPage.clickAndWait("channelPlansLink", "partnerCenterHome");
			portalLandingPage.verifyFieldExists("recentChannelPlansHeader");
			
			partner.open();
		}
		
		if (portalLandingPage.isFieldPresent("productSupportLink")) {
			portalLandingPage.clickAndWait("productSupportLink", "partnerCenterHome");
			portalLandingPage.verifyFieldExists("casesViewList");
			
			partner.open();
		}
		//*****************************************************************************
		
		logoutAsEpartnerUser();
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
