package ddlogin;

//import java.util.Calendar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Constants and enumerated types specific to the Digital Delivery application.
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
final class DDLoginConstants {
	//TODO  Will need to handle users in a more intelligent manner.  We don't want to have to enter them in the test
	//  properties file, not even in an include file.  We should create one or more  collection objects (simple properties?)
	//  to store user names and passwords.  The user would be looked up by... what - role?  role and name?  Note that we can
	//  break out the environment component (e.g., "adsksfstg") and use it as a key to one of the collections.  We would pass
	//  an environment code property to the test, which would be used in looking up the user.

	static final String BASE_URL_DEV			= "http://accounts-staging.autodesk.com";
	static final String BASE_URL_STG			= "http://accounts-staging.autodesk.com";

	static final String BASE_PARTNER_URL_DEV	= "";
	static final String BASE_PARTNER_URL_STG	= "";

	static final String AUTO_USERNAME_DEV		= "";
	static final String AUTO_PASSWORD_DEV		= "";

	static final String AUTO_USERNAME_STG		= "kirankumar.bellary@autodesk.com.adsksfstg";
	static final String AUTO_PASSWORD_STG		= "Kalyani1";
	
	static final String PARTNER_USERNAME_DEV	= "";
	static final String PARTNER_PASSWORD_DEV	= "";

	static final String PARTNER_USERNAME_STG	= "";
	static final String PARTNER_PASSWORD_STG	= "";
	
	//Default to STG, but it is expected that test code may make its own assignments
	//static final String BASE_URL										= BASE_URL_STG;
	//static String AUTO_USERNAME										= AUTO_USERNAME_STG;
	//static String AUTO_PASSWORD										= AUTO_PASSWORD_STG;

	static final String DEFAULT_L10N_BUNDLE		= "dd.l10n.UIResources";
	static final String CONTINUE_TO_WEBSITE_LINK_TEXT						= "\"Continue to this website (not recommended).\"";
	
	//**** Constants used for referencing test properties constants ******************************************************************
	//Following are constants that reference test properties constant names.  They work as follows:
	//  1) The user supplies a test-scope constant in the test properties file.  For example:
	//     	 DD_USER = CONSTANT[qafs2]
	//  2) Framework code references the test properties constant.  For example:
	//    	 The DD_USER_OVERRIDE_CONSTANT_NAME variable is referenced in the DDTestBase.setDDUser() method.  That
	//       code will search the test-scope constants store using the value of DD_USER_OVERRIDE_CONSTANT_NAME, which is
	//       "DD_USER".  If it is found, the value of the DD_USER constant will be used.
	//  3) When framework code references a test properties constant, but the constant does not exist, the result is context-dependent.
	//       For example, in the case described here in item 2, if the test-scope constant is not found, the code will use a default
	//       user name.  However, some constants are meant to provide "expected results" like the expected quote approval level.  When
	//       such constants are not provided, tests will generally fail!
	//
	//  Tips:
	//    1) Try to include the word OVERRIDE in the variable name when the associated test properties constant contains an override
	//       of some other value, and therefore will not cause a test to fail
	//    2) Try to include the word EXPECTED in the variable name when the associated test properties constant contains an expected
	//       results, and therefore will likely cause a test to fail
	//    3) For any other test properties constants whose absence may cause a test to fail, give an appropriate name to the variable
	//       that will be used to look it up
	//    4) Note that in items 1 and 2 it is suggested that variable names (declared below) contain the key words, not necessarily
	//       the name of the constant in the test properties
	//    5) Comment all variable declarations (below), and comment the constant declarations in test properties files!!

	//When a constant with the name specified by this variable is defined in test properties, it is used instead of the value defined
	//  in DEFAULT_DD_USER
    static final String DD_USER_OVERRIDE_CONSTANT_NAME 						= "DD_USER";
    
	//When a CONSTANT_SET with the name specified by this variable is defined in test properties, it is expected to contain a set of 
    //  names of CONSTANTS, each of which is expected to specify a product line
    static final String DD_PRODUCT_LINE_CONSTANT_SET_NAME 					= "PRODUCT_LINES";
    
	//When a CONSTANT_SET with the name specified by this variable is defined in test properties, it is expected to contain a set of 
    //  names of CONSTANT_SETS, each of which is expected to specify the name of a constant that specifies a product line,
    //  followed by a list of year versions
    static final String DD_PRODUCT_LINE_YEAR_VERSIONS_CONSTANT_SET_NAME 	= "PRODUCT_LINE_YEAR_VERSIONS";
    
	//When a CONSTANT with the name specified by this variable is defined in test properties, it is expected to contain the 
    //  value of a year version
    static final String DD_YEAR_VERSION_CONSTANT_NAME 						= "YEAR_VERSION";

    //When a CONSTANT_SET with the name that STARTS WITH the value specified by this variable is defined in test properties,
    //  it is expected to contain a set of file names.  It is also expected to have a four-digit year appended to it, e.g.,
    //  FILE_NAMES_YEAR_VERSION_2010
    static final String DD_FILE_NAMES_YEAR_VERSION_CONSTANT_SET_NAME_PREFIX = "FILE_NAMES_YEAR_VERSION_";
    
	//When a CONSTANT with the name specified by this variable is defined in test properties, it is expected to contain the 
    //  name of a tab (see the TabNameType enumeration below for values)
    static final String DD_TAB_NAME_ENUM_CONSTANT_NAME 						= "TAB_NAME_ENUM";
 	//********************************************************************************************************************************

	//The view that by default will be selected on the setup page (used for filtering the list of users to only those used in Digital Delivery testing)
	static final String DEFAULT_USER_VIEW						= "[TBD]";
	
	static final String ADMIN_USER_VIEW							= "[TBD]";
	
	//My Documents constants
	//static final int PRODUCT_VERSION_MAX_YEAR							= Calendar.getInstance().get(Calendar.YEAR);
	static final int PRODUCT_VERSION_MAX_YEAR							= 2013;
	static final int PRODUCT_VERSION_MIN_YEAR							= 2010;
	static final int PRODUCT_VERSION_MAX_NUM_OTHER_THAN_CURRENT_YEAR	= 3;

	//NOTE that these values have been moved into testdata.Data_Product_Lines.properties
	//  (but leave this here until the design is settled)
	//NOTE THAT KIRAN SAID ON 08/11/2012 THAT THE PRODUCT LINES WILL BE CONSTANT ACROSS ALL GEOS AND USER TYPES
	//static final List<String> productLines = new ArrayList<String>(Arrays.asList("AutoCAD", "AutoCAD Civil", "AutoCAD Inventor Professional Suite", "Autodesk Product Design Suite Premium", "Revit Architecture"));

	//Names of Fields as defined in Page_myDownloads.properties, and in test code.  They are used in test code to generate product line specific locators.
	//!!! NOTE that changes made here must also be made in the properties file, and in test code
	static final String FILE_DETAILS_FILE_NAMES_FIELD_NAME				= "fileDetailsFileNames";
	static final String FILE_DETAILS_FILE_NAMES_FIELD_SIZE				= "fileDetailsFileSize";

	static final List<String> TAB_FIELD_NAMES							= new ArrayList<String>(DDLoginConstants.TabNameType.getFieldNames());

	static final List<String> FILE_DETAILS_BUTTON_FIELD_NAMES			= new ArrayList<String>(Arrays.asList("fileDetailsGetSoftwareButton",
																										      "fileDetailsGetSoftwareButtonDropdown"));
	static final List<String> FILE_DETAILS_DROP_DOWN_VALUES_FIELD_NAMES	= new ArrayList<String>(Arrays.asList("fileDetailsGetSoftwareButtonDropdownDownloadNow",
																											  "fileDetailsGetSoftwareButtonDropdownBrowserDownload",
																											  "fileDetailsGetSoftwareButtonDropdownInstallNow",
																									  	      "fileDetailsGetSoftwareButtonDropdownHelpMeDecide"));
	
	//The contents of this list are keys into the resource bundles, not the text of the options themselves!!
	static final List<String> EXPECTED_L10N_LANGUAGE_FILTER_OPTIONS	= new ArrayList<String>(Arrays.asList("britishEnglish",
			  																							  "czech",
			  																							  "dutch",
			  																							  "english",
			  																							  "europeanEnglish",
			  																							  "french",
			  																							  "german",
			  																							  "hungarian",
			  																							  "italian",
			  																							  "japanese",
			  																							  "korean",
			  																							  "nordic",
			  																							  "polish",
			  																							  "portuguese",
			  																							  "russian",
			  																							  "simplifiedChinese",
			  																							  "spanish",
			  																							  "traditionalChinese",
			  																							  "vietnamese"));

	//The contents of this list are keys into the resource bundles, not the text of the options themselves!!
	static final List<String> EXPECTED_L10N_DOWNLOAD_PREFERENCE_OPTIONS	= new ArrayList<String>(Arrays.asList("browserDownload", "installNow", "downloadNow"));
			  																							  
	//static final int NUM_LANGUAGE_PICKLIST_ENTRIES			= 19;
	static final int NUM_LANGUAGE_PICKLIST_ENTRIES				= EXPECTED_L10N_LANGUAGE_FILTER_OPTIONS.size();
	static final int NUM_PLATFORM_PICKLIST_ENTRIES				= 15;
	static final int NUM_DOWNLOAD_PREFERENCE_PICKLIST_ENTRIES	= EXPECTED_L10N_DOWNLOAD_PREFERENCE_OPTIONS.size();
	
	//In test properties files used for testing a particular tab, there must be a CONSTANT with the name specified
	//  in DD_TAB_NAME_ENUM_CONSTANT_NAME.  The value of that CONSTANT must be one of the following (left hand values)
	//
	//NOTE In L10N tests, sure to call DDTestBase.localizeTabNames(String bundle) before accessing these element,
	//  either directly or via the TAB_FIELD_NAMES constant above
/*	public enum TabNameType {
		SOFTWARE		("Software",		"softwareTab"),
		DOCUMENTATION	("Documentation",	"documentationTab"),
		LANGUAGE_PACKS	("Language Packs",	"languagePacksTab"),
		EXTRAS			("Extras",			"extrasTab"),
		SERIAL_NUMBERS	("Serial Numbers",	"serialNumbersTab"),
		DOWNLOAD_LOGS	("Download Logs",	"downloadLogsTab");
		
		private String displayName;
		private final String fieldName;
		
		TabNameType(String displayName, String fieldName) {
			this.displayName	= displayName;
			this.fieldName		= fieldName;
		}
			
		public String getDisplayName() {
			return displayName;
		}
		
		//Useful in L10N testing
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		
		public static List<String> getFieldNames() {
			List<String> fieldNames = new ArrayList<String>();
			
			for (TabNameType tabName : TabNameType.values()) {
				fieldNames.add(tabName.fieldName);
			}
			
			return fieldNames;
		}
			
		public String getFieldName() {
			return fieldName;
		}
	}
*/
	public enum TabNameType {
		SOFTWARE		("Software",		"softwareTab",		"software"),
		DOCUMENTATION	("Documentation",	"documentationTab",	"documentation"),
		LANGUAGE_PACKS	("Language Packs",	"languagePacksTab",	"languagePacks"),
		EXTRAS			("Extras",			"extrasTab",		"extras"),
		SERIAL_NUMBERS	("Serial Numbers",	"serialNumbersTab",	"serialNumbers"),
		DOWNLOAD_LOGS	("Download Logs",	"downloadLogsTab",	"downloadLogs");
		
		private String displayName;
		private final String fieldName;
		private final String l10nKey;
		
		TabNameType(String displayName, String fieldName, String l10nKey) {
			this.displayName	= displayName;
			this.fieldName		= fieldName;
			this.l10nKey		= l10nKey;
		}
			
		public String getDisplayName() {
			return displayName;
		}
		
		//Useful in L10N testing
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		
		public static List<String> getFieldNames() {
			List<String> fieldNames = new ArrayList<String>();
			
			for (TabNameType tabName : TabNameType.values()) {
				fieldNames.add(tabName.fieldName);
			}
			
			return fieldNames;
		}
			
		public String getFieldName() {
			return fieldName;
		}

		public String getL10nKey() {
			return l10nKey;
		}
	}
}
