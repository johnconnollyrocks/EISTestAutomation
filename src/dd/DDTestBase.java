package dd;

import common.EISTestBase;
import common.Page_;
import common.TestProperties;
import common.Window_;
import dd.DDConstants.TabNameType;

/**
 * Representation of features and functionality specific to the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class DDTestBase extends EISTestBase {
	private static final String APP_NAME		= "Digital Delivery";
	private static final String APP_BASE_DIR	= "dd";

    //TODO  We need to figure out what is an auto user, do they use users on the setup page,
    //  do they log in as an auto user and then as a Digital Delivery user, etc.
	private String ddUser = "";
	
	//Define only DD-specific windows.  SFDC-scope windows (such as mainWindow) are static
	//  objects in the EISTestBase class
	Window_ myDocumentsPopUp = null;
	
	//Define DD-specific Page objects that have no associated test properties. The pages
	//  defined here are those that:
	//    will never be referenced in a test properties file, AND
	//    contain DD-specific GUI elements
	//Note that if a test DOES need to specify test properties for one of these pages
	//  (e.g., search terms), it can create its own local version of the page, and pass
	//  the pagePropertiesFilenameKey argument

	/**
	 * Default constructor.  It calls the superclass constructor, passing the application's name and directory in the framework's source code hierarchy.
	 */
	DDTestBase() {
		//TODO  Change to get APP_BASE_DIR from properties? (currently passed in as JVM arg)
	    super(APP_NAME, APP_BASE_DIR);
	    setup();
    }
	DDTestBase(String launchDriver) {
		//TODO  Change to get APP_BASE_DIR from properties? (currently passed in as JVM arg)
	    super(APP_NAME, APP_BASE_DIR,launchDriver);
	    setup();
    }

	/**
	 * Gets the Digital Delivery user name. (NOTE that it is not yet clear what constitutes a Digital Delivery user,
	 * an admin user, etc.)
	 * @return The Digital Delivery user name
	 */
    final String getDDUser() {
		return ddUser;
	}

	@Override
	public String toString() {
		return getAppName() + " ["
				+ "super= " + super.toString() 
				+ ", ddUser= " + ddUser
				+ "]";
	}
    
	/**
	 * Configures high-level DD-specific objects.
	 * @see #doSetup()
	 */
    @Override
	protected final void setup() {
    	super.setup();

    	doSetup();
    }

    /**
	 * Configures high-level DD-specific objects&#58;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates DD-specific Page objects<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates DD-specific Window objects
	 * @see #createAppWindows()
	 * @see #createAppPages()
	 */
    private final void doSetup() {
    	setEnvironmentVariables();
    	
    	createAppWindows();
    	
    	createAppPages();

    	//Set the default resource bundle.  This necessary even for non-L10N tests, because even they
    	//  need to call localizeFieldLocators()
		setResourceBundle(DDConstants.DEFAULT_L10N_BUNDLE);
		
		//MUST be called after super.setup!!!
    	//NOTE that ddUser is the equivalent of a user that is found in the setup screen.
    	//    The user that logs in to the app is known as the auto user.  Until we figure out
    	//    whether that model applies, don't call this.
    	//setDDUser();
    }
    
	/**
	 * Instantiates DD-specific Window objects.
	 */
    @Override
	protected final void createAppWindows() {
    	//Instantiate only DD-specific windows.  SFDC-scope windows (such as mainWindow) are static
    	//  objects in the EISTestBase class
    	myDocumentsPopUp = createWindow("WINDOW_MY_DOCUMENTS_POPUP_PROPERTIES_FILE");
    }

	/**
	 * Instantiates DD-specific Page objects.
	 */
	private final void createAppPages() {
		//Instantiate Page objects that have no associated test properties
		//Note that if a test DOES need to specify test properties for one of these pages
		//  (e.g., search terms), it can create its own local version of the page, and pass
		//  the pagePropertiesFilenameKey argument, OR create it here, by calling createPage
		//  instead of createStaticPage

		//Can also instantiate regular (i.e., with associated test properties) DD-specific
		//  Page objects here, but typically it is best for the test or utility methods to do that;
		//  if we do it here we may end up creating Page objects that never get used.
    }

	/**
	 * Instantiates a DD object, using data in the specified TestProperties object.
	 * @param utilProperties a TestProperties object containing data necessary to create a DD object
	 * @return A DD object
	 */
	final DD utilCreateDDObject(TestProperties utilProperties) {
		Page_ myDocumentsPage	= createPage(utilProperties, "PAGE_MY_DOWNLOADS_PROPERTIES_FILE");
		Page_ viewContactPage	= createPage(utilProperties, "PAGE_VIEW_CONTACT_PROPERTIES_FILE");

       	DD dd = new DD(myDocumentsPopUp, myDocumentsPage, viewContactPage);

     	return dd;
    }

	/**
	 * Instantiates a DD object, using data in the specified TestProperties object.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return A DD object
	 */
    final DD utilCreateDDObject(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

		return utilCreateDDObject(utilProperties);
    }
    
	/**
	 * Instantiates a DD object, using data in the default TestProperties object.
	 * @return A DD object
	 */
    final DD utilCreateDDObject() {
		return utilCreateDDObject(testProperties);
    }
    
    /**
	 * Logs in as the automation user, optionally launching Salesforce (default user is DDConstants.AUTO_USERNAME).
	 * @param launch the setting that determines whether to launch Salesforce before logging in
	 * @return The automation user name
	 */
    @Override
    protected final String loginAsAutoUser(boolean launch) {
    	//login(DDConstants.AUTO_USERNAME, DDConstants.AUTO_PASSWORD, launch);
    	super.login(getAutoUserName(), getAutoPassword(), launch);

    	chooseApp();

    	return getCurrentUser();
    }
    
	/**
	 * Logs in as the automation user, without launching Salesforce.
	 * @return The automation user name
	 */
    final String loginAsAutoUser() {
    	return loginAsAutoUser(false);
    }

    final void loginAsDDUser() {
		//It is assumed that a user is logged in and has the SFDC interface open

		//Page_ viewContactPage = createCommonPage("PAGE_VIEW_CONTACT_PROPERTIES_FILE");
		Page_ viewContactPage = createPage("PAGE_VIEW_CONTACT_PROPERTIES_FILE");
		commonPage.clickAndWait("tabContacts", "newButton");
		search(testProperties.getConstant("CONTACT_CSN"));
		
		//We search the results on Account CSN to get the contact we want; we don't search on the contact name itself
		//  because there are often duplicate names

		//commonPage.clickLinkInRelatedList("accountCsnInContactsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInContactsRelatedList");
		//commonPage.clickLinkInRelatedList("accountCsnInReadOnlyContactsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInReadOnlyContactsRelatedList");
		commonPage.clickLinkInRelatedList("accountCsnInReadOnlyContactsRelatedList", testProperties.getConstant("ACCOUNT_CSN"), "nameInReadOnlyContactsRelatedList");
		//viewContactPage.waitForFieldPresent("loginAsPortalUserLink");
		viewContactPage.waitForFieldPresent("contactDetailHeader");
	}

    @Override
    protected final void chooseApp() {}

    protected final void setEnvironmentVariables() {
    	switch (getEnvironment().trim().toUpperCase()) {
			case "DEV":	{
				if (!DDConstants.BASE_URL_DEV.isEmpty()) {
					setBaseURL(DDConstants.BASE_URL_DEV);
				}
				
				setBasePartnerURL(DDConstants.BASE_PARTNER_URL_DEV);
				
				setAutoUserName(DDConstants.AUTO_USERNAME_DEV);
				setAutoPassword(DDConstants.AUTO_PASSWORD_DEV);
				
				setPartnerUserName(DDConstants.PARTNER_USERNAME_DEV);
				setPartnerPassword(DDConstants.PARTNER_PASSWORD_DEV);
				
				break;
			}
			case "STG":
			default:	{
				if (!DDConstants.BASE_URL_STG.isEmpty()) {
					setBaseURL(DDConstants.BASE_URL_STG);
				}
				
				setBasePartnerURL(DDConstants.BASE_PARTNER_URL_STG);
				
				setAutoUserName(DDConstants.AUTO_USERNAME_STG);
				setAutoPassword(DDConstants.AUTO_PASSWORD_STG);
				
				setPartnerUserName(DDConstants.PARTNER_USERNAME_STG);
				setPartnerPassword(DDConstants.PARTNER_PASSWORD_STG);
			}
     	}
    }

    //TODO  Create properties files to store [fieldName = L10N Key] pairs.  Then pass its
	//  file name to this method (instead of hard-coding the Fields that need to be parsed).
	//
    //Then move this method (and the one below it) to EISTestBase
    //
    //This method parses the L10N_TOKEN1 token in locators of Fields that require the 
    //  element's text attribute in order to form an xpath
/*	protected void localizeFieldLocators(Page_ page) {
		String text;
		ResourceBundle resBun = getResourceBundle();
		
		text = resBun.getString("welcome");
		page.parseL10NFieldLocatorsTokens("welcomeMessage", text);

		text = resBun.getString("myProfile");
		page.parseL10NFieldLocatorsTokens("myProfile", text);
		
		text = resBun.getString("signOut");
		page.parseL10NFieldLocatorsTokens("signOut", text);
		
		text = resBun.getString("search");
		page.parseL10NFieldLocatorsTokens("search", text);
		
		text = resBun.getString("language");
		page.parseL10NFieldLocatorsTokens("languageLabel", text);
		
		text = resBun.getString("platform");
		page.parseL10NFieldLocatorsTokens("platformLabel", text);
		
		text = resBun.getString("contentType");
		page.parseL10NFieldLocatorsTokens("contentTypeLabel", text);
		
		text = resBun.getString("productSearch");
		page.parseL10NFieldLocatorsTokens("productSearchLabel", text);
		
		text = resBun.getString("goButton");
		page.parseL10NFieldLocatorsTokens("goButton", text);
		
		text = resBun.getString("clearButton");
		page.parseL10NFieldLocatorsTokens("clearButton", text);
		
		text = resBun.getString("downloadPreference");
		page.parseL10NFieldLocatorsTokens("downloadPreferenceLabel", text);
		
		text = resBun.getString("noDownloadsMatch");
		page.parseL10NFieldLocatorsTokens("noDownloadsMatchMessage", text);
		
		text = resBun.getString("nothingDownloadedToDate");
		page.parseL10NFieldLocatorsTokens("nothingDownloadedToDateMessage", text);
		
		text = resBun.getString("youDoNotHaveSNs");
		page.parseL10NFieldLocatorsTokens("youDoNotHaveSerialNumbersMessage", text);
		
		text = resBun.getString("software");
		page.parseL10NFieldLocatorsTokens("softwareTab", text);
		
		text = resBun.getString("documentation");
		page.parseL10NFieldLocatorsTokens("documentationTab", text);
		
		text = resBun.getString("languagePacks");
		page.parseL10NFieldLocatorsTokens("languagePacksTab", text);
		
		text = resBun.getString("extras");
		page.parseL10NFieldLocatorsTokens("extrasTab", text);
		
		text = resBun.getString("serialNumbers");
		page.parseL10NFieldLocatorsTokens("serialNumbersTab", text);
		
		text = resBun.getString("downloadLogs");
		page.parseL10NFieldLocatorsTokens("downloadLogsTab", text);

		text = resBun.getString("downloadNow");
		page.parseL10NFieldLocatorsTokens("fileDetailsGetSoftwareButtonDropdownDownloadNow", text);

		text = resBun.getString("browserDownload");
		page.parseL10NFieldLocatorsTokens("fileDetailsGetSoftwareButtonDropdownBrowserDownload", text);

		text = resBun.getString("helpMeDecide");
		page.parseL10NFieldLocatorsTokens("fileDetailsGetSoftwareButtonDropdownHelpMeDecide", text);

		text = resBun.getString("installNow");
		page.parseL10NFieldLocatorsTokens("fileDetailsGetSoftwareButtonDropdownInstallNow", text);

		text = resBun.getString("privacyPolicy");
		page.parseL10NFieldLocatorsTokens("privacyPolicy", text);

		text = resBun.getString("legalNoticesAndTrademarks");
		page.parseL10NFieldLocatorsTokens("legalNoticesAndTrademarks", text);

		text = resBun.getString("subscriptionHome");
		page.parseL10NFieldLocatorsTokens("subscriptionHome", text);

		text = resBun.getString("downloads");
		page.parseL10NFieldLocatorsTokens("downloads", text);

		text = resBun.getString("needHelpContactUs");
		page.parseL10NFieldLocatorsTokens("needHelpContactUs", text);

		text = resBun.getString("relatedInformation");
		page.parseL10NFieldLocatorsTokens("relatedInformationText", text);
	}
*/
	protected void localizeFieldLocators(Page_ page) {
		page.parseL10NFieldLocatorsTokens("welcomeMessage", getLocalizedString("welcome"));
		page.parseL10NFieldLocatorsTokens("myProfile", getLocalizedString("myProfile"));
		page.parseL10NFieldLocatorsTokens("signOut", getLocalizedString("signOut"));
		page.parseL10NFieldLocatorsTokens("search", getLocalizedString("search"));
		page.parseL10NFieldLocatorsTokens("languageLabel", getLocalizedString("language"));
		page.parseL10NFieldLocatorsTokens("platformLabel", getLocalizedString("platform"));
		page.parseL10NFieldLocatorsTokens("contentTypeLabel", getLocalizedString("contentType"));
		page.parseL10NFieldLocatorsTokens("productSearchLabel", getLocalizedString("productSearch"));
		page.parseL10NFieldLocatorsTokens("goButton", getLocalizedString("goButton"));
		page.parseL10NFieldLocatorsTokens("clearButton", getLocalizedString("clearButton"));
		page.parseL10NFieldLocatorsTokens("downloadPreferenceLabel", getLocalizedString("downloadPreference"));
		page.parseL10NFieldLocatorsTokens("noDownloadsMatchMessage", getLocalizedString("noDownloadsMatch"));
		page.parseL10NFieldLocatorsTokens("nothingDownloadedToDateMessage", getLocalizedString("nothingDownloadedToDate"));
		page.parseL10NFieldLocatorsTokens("youDoNotHaveSerialNumbersMessage", getLocalizedString("youDoNotHaveSNs"));
		page.parseL10NFieldLocatorsTokens("softwareTab", getLocalizedString("software"));
		page.parseL10NFieldLocatorsTokens("documentationTab", getLocalizedString("documentation"));
		page.parseL10NFieldLocatorsTokens("languagePacksTab", getLocalizedString("languagePacks"));
		page.parseL10NFieldLocatorsTokens("extrasTab", getLocalizedString("extras"));
		page.parseL10NFieldLocatorsTokens("serialNumbersTab", getLocalizedString("serialNumbers"));
		page.parseL10NFieldLocatorsTokens("downloadLogsTab", getLocalizedString("downloadLogs"));
		page.parseL10NFieldLocatorsTokens("fileDetailsGetSoftwareButtonDropdownDownloadNow", getLocalizedString("downloadNow"));
		page.parseL10NFieldLocatorsTokens("fileDetailsGetSoftwareButtonDropdownBrowserDownload", getLocalizedString("browserDownload"));
		page.parseL10NFieldLocatorsTokens("fileDetailsGetSoftwareButtonDropdownHelpMeDecide", getLocalizedString("helpMeDecide"));
		page.parseL10NFieldLocatorsTokens("fileDetailsGetSoftwareButtonDropdownInstallNow", getLocalizedString("installNow"));
		page.parseL10NFieldLocatorsTokens("privacyPolicy", getLocalizedString("privacyPolicy"));
		page.parseL10NFieldLocatorsTokens("legalNoticesAndTrademarks", getLocalizedString("legalNoticesAndTrademarks"));
		page.parseL10NFieldLocatorsTokens("subscriptionHome", getLocalizedString("subscriptionHome"));
		page.parseL10NFieldLocatorsTokens("downloads", getLocalizedString("downloads"));
		page.parseL10NFieldLocatorsTokens("needHelpContactUs", getLocalizedString("needHelpContactUs"));
		page.parseL10NFieldLocatorsTokens("relatedInformationText", getLocalizedString("relatedInformation"));
	}

	protected void localizeTabNames() {
		for (TabNameType tabName : TabNameType.values()) {
			tabName.setDisplayName(getLocalizedString(tabName.getL10nKey()));
		}
	}
}
