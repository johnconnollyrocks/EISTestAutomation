package qtpseleniumintegration;

import qtpseleniumintegration.EpartnerConstants;
import common.EISTestBase;
import common.Page_;
import common.TestProperties;
import common.Util;

/**
 * Representation of features and functionality specific to the ePartner application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class EpartnerTestBase extends EISTestBase {
	private static final String APP_NAME		= "qtpseleniumintegration";
	private static final String APP_BASE_DIR	= "qtpseleniumintegration";

    //TODO  We need to figure out what is an auto user, do they use users on the setup page,
    //  do they log in as an auto user and then as a partner user, etc.
	private String epartnerUser = "";
	
	//Define only ePartner-specific windows.  SFDC-scope windows (such as mainWindow) are static
	//  objects in the EISTestBase class
	
	//Define ePartner-specific Page objects that have no associated test properties. The pages
	//  defined here are those that:
	//    will never be referenced in a test properties file, AND
	//    contain ePartner-specific GUI elements
	//Note that if a test DOES need to specify test properties for one of these pages
	//  (e.g., search terms), it can create its own local version of the page, and pass
	//  the pagePropertiesFilenameKey argument

	/**
	 * Default constructor.  It calls the superclass constructor, passing the application's name and directory in the framework's source code hierarchy.
	 */
	EpartnerTestBase() {
		//TODO  Change to get APP_BASE_DIR from properties? (currently passed in as JVM arg)
	    super(APP_NAME, APP_BASE_DIR);
	    setup();
    }

	/**
	 * Gets the ePartner user name. (NOTE that it is not yet clear what constitutes an ePartner user,
	 * a partner user, an admin user, etc.)
	 * @return The ePartner user name
	 */
    final String getEpartnerUser() {
		return epartnerUser;
	}

	@Override
	public String toString() {
		return getAppName() + " ["
				+ "super= " + super.toString() 
				+ ", epartnerUser= " + epartnerUser
				+ "]";
	}
    
	/**
	 * Configures high-level ePartner-specific objects.
	 * @see #doSetup()
	 */
    @Override
	protected final void setup() {
    	super.setup();

    	doSetup();
    }

    /**
	 * Configures high-level ePartner-specific objects&#58;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates ePartner-specific Page objects<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates ePartner-specific Window objects
	 * @see #createAppWindows()
	 * @see #createAppPages()
	 */
    private final void doSetup() {
    	setEnvironmentVariables();
    	
    	createAppWindows();
    	
    	createAppPages();

    	//MUST be called after super.setup!!!
    	//NOTE that epartnerUser is the equivalent of a user that is found in the setup screen.
    	//    The user that logs in to the app is known as the auto user.  Until we figure out
    	//    whether that model applies, don't call this.
    	//setEpartnerUser();
    }
    
	/**
	 * Instantiates ePartner-specific Window objects.
	 */
    @Override
	protected final void createAppWindows() {
    	//Instantiate only ePartner-specific windows.  SFDC-scope windows (such as mainWindow) are static
    	//  objects in the EISTestBase class
    }

	/**
	 * Instantiates ePartner-specific Page objects.
	 */
	private final void createAppPages() {
		//Instantiate Page objects that have no associated test properties
		//Note that if a test DOES need to specify test properties for one of these pages
		//  (e.g., search terms), it can create its own local version of the page, and pass
		//  the pagePropertiesFilenameKey argument, OR create it here, by calling createPage
		//  instead of createStaticPage

		//Can also instantiate regular (i.e., with associated test properties) ePartner-specific
		//  Page objects here, but typically it is best for the test or utility methods to do that;
		//  if we do it here we may end up creating Page objects that never get used.
    }

	/**
	 * Instantiates a Partner object, using data in the specified TestProperties object.
	 * @param utilProperties a TestProperties object containing data necessary to create a Partner object
	 * @return A Partner object
	 */
	final Partner utilCreatePartnerObject(TestProperties utilProperties) {
		//Page_ portalLandingPage	= createPage(utilProperties, "PAGE_PORTAL_LANDING_PAGE_PROPERTIES_FILE");
		Page_ portalLandingPage	= createCommonPage("PAGE_PORTAL_LANDING_PAGE_PROPERTIES_FILE");

       	Partner partner = new Partner(portalLandingPage);

     	return partner;
    }

	/**
	 * Instantiates a Partner object, using data in the specified TestProperties object.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return A Partner object
	 */
    final Partner utilCreatePartnerObject(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

		return utilCreatePartnerObject(utilProperties);
    }
    
	/**
	 * Instantiates a Partner object, using data in the default TestProperties object.
	 * @return A Partner object
	 */
    final Partner utilCreatePartnerObject() {
		return utilCreatePartnerObject(testProperties);
    }
    
	final Partner utilCreatePartnerOpportunity(TestProperties utilProperties) {
		Partner partnerOpportunity = utilCreatePartnerOpportunityObject(utilProperties);
		partnerOpportunity.createOpportunity();
       	
       	return partnerOpportunity;
    }

	final Partner utilCreatePartnerOpportunity(String utilPropertiesFilenameKey) {
		TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

       	return utilCreatePartnerOpportunity(utilProperties);       	
     }
	
	final Partner utilCreatePartnerOpportunity() {	
       	return utilCreatePartnerOpportunity(testProperties);     	
       	
    }
	
	final Partner utilCreatePartnerOpportunityObject(TestProperties utilProperties) {
		Partner partnerOpportunity  = null;

		//Use longer pageRedrawDelay setting for slow pages 
		//Page_ createSubscriptionRenewalPage			= createPage(utilProperties, "PAGE_CREATE_SUBSCRIPTIONRENEWAL_PROPERTIES_FILE");
		Page_ createPartnerOpportunityPage			= createCommonPageInstance(utilProperties, "PAGE_CREATE_OPPTY_IN_PORTAL_PROPERTIES_FILE", 600);
		Page_ viewPartnerOpportunityPage			= createPage(utilProperties, "PAGE_VIEW_OPPTY_IN_PORTAL_PROPERTIES_FILE");
		Page_ viewAccountDetailsPage			= createPage(utilProperties, "PAGE_VIEW_ACCOUNT_DETAILS_IN_PORTAL_PROPERTIES_FILE");
	//	Page_ createAccountFromAccountsLink			= createPage(utilProperties, "PAGE_CREATE_ACCOUNT_FROM_ACCOUNTS_LINK_PROPERTIES_FILE");
	//	partnerOpportunity = new Partner(createPartnerOpportunityPage , viewPartnerOpportunityPage,viewAccountDetailsPage,createAccountFromAccountsLink);
		partnerOpportunity = new Partner(createPartnerOpportunityPage,viewPartnerOpportunityPage,viewAccountDetailsPage);
     	return partnerOpportunity;
    }

    final Partner utilCreatePartnerOpportunityObject(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

		return utilCreatePartnerOpportunityObject(utilProperties);
    }
	
    final Partner utilCreatePartnerOpportunityObject() {
		return utilCreatePartnerOpportunityObject(testProperties);
    }
   
	/**
	 * Logs in to the application as a partner user.
	 * @param userName the user name to use when logging in
	 * @param password the password to use when logging in
	 * @param launch whether to launch Salesforce first
	 * @see #launchSalesforce()
	 */
    protected void loginAsEpartnerUser(String userName, String password, boolean launch) {
		Page_ portalLandingPage = createCommonPage("PAGE_PORTAL_LANDING_PAGE_PROPERTIES_FILE");

		if (launch) {
    		launchSalesforce();
    	}
    	
    	mainWindow.select();
    	
    	loginPage.populateField("username", userName);
    	loginPage.populateField("password", password);

     	loginPage.click("loginButton");
     	portalLandingPage.waitForFieldPresent("privacyPolicy");
     	
     	handleServerMaintenanceNotification();
    	
     	//Deal with possible pop-ups, such as Reminders
     	Util.sleep(2000);
     	if (loginPage.closeAllPopUps(mainWindow.getLocator())) {
        	disableReminders();
     	}

    	mainWindow.select();
    	
    	Util.printInfo("Logged in as the partner user '" + userName + "'");
    }

    protected void loginAsEpartnerUser(String userName, String password) {
    	loginAsEpartnerUser(userName, password, false);
    }
    
    protected void loginAsEpartnerUser(boolean launch) {
    	loginAsEpartnerUser(getPartnerUserName(), getPartnerPassword(), launch);
    }
    
    protected void loginAsEpartnerUser() {
    	loginAsEpartnerUser(getPartnerUserName(), getPartnerPassword(), false);
    }
   
	/**
	 * Logs out the ePartner user.
	 * @see #setEpartnerUser()
	 */
    final void logoutAsEpartnerUser() {
		Page_ portalLandingPage = createCommonPage("PAGE_PORTAL_LANDING_PAGE_PROPERTIES_FILE");
		
		portalLandingPage.click("logout");
    }

    @Override
    protected final void chooseApp() {}

    protected final void setEnvironmentVariables() {
    	switch (getEnvironment().trim().toUpperCase()) {
			case "DEV":	{
				if (!EpartnerConstants.BASE_URL_DEV.isEmpty()) {
					setBaseURL(EpartnerConstants.BASE_URL_DEV);
				}
				
				setBasePartnerURL(EpartnerConstants.BASE_PARTNER_URL_DEV);
				
				setAutoUserName(EpartnerConstants.AUTO_USERNAME_DEV);
				setAutoPassword(EpartnerConstants.AUTO_PASSWORD_DEV);
				
				setPartnerUserName(EpartnerConstants.PARTNER_USERNAME_DEV);
				setPartnerPassword(EpartnerConstants.PARTNER_PASSWORD_DEV);
				
				break;
			}
			case "STG":
			default:	{
				if (!EpartnerConstants.BASE_URL_STG.isEmpty()) {
					setBaseURL(EpartnerConstants.BASE_URL_STG);
				}
				
				setBasePartnerURL(EpartnerConstants.BASE_PARTNER_URL_STG);
				
				setAutoUserName(EpartnerConstants.AUTO_USERNAME_STG);
				setAutoPassword(EpartnerConstants.AUTO_PASSWORD_STG);
				
				setPartnerUserName(EpartnerConstants.PARTNER_USERNAME_STG);
				setPartnerPassword(EpartnerConstants.PARTNER_PASSWORD_STG);
			}
     	}
    }
}
