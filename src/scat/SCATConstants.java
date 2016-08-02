package scat;

/**
 * Constants and enumerated types specific to the SCAT application.
 * 
 * @author Ravi Shankar
 * @version 1.0.0
 */
final class SCATConstants {
	// TODO Will need to handle users in a more intelligent manner. We don't
	// want to have to enter them in the test
	// properties file, not even in an include file. We should create one or
	// more collection objects (simple properties?)
	// to store user names and passwords. The user would be looked up by... what
	// - role? role and name? Note that we can
	// break out the environment component (e.g., "adsksfstg") and use it as a
	// key to one of the collections. We would pass
	// an environment code property to the test, which would be used in looking
	// up the user.
	static final String CONTINUE_TO_WEBSITE_LINK_TEXT="\"Continue to this website (not recommended).\"";
	
	static final String BASE_URL = "http://subscriptionportaldv-d3.autodesk.com/sc/localadmin";
	
	static final String BASE_URL1="http://ussclseapsub002.autodesk.com/adm/";
	static final String BASE_URL2 ="http://ussclseapsub003.autodesk.com/adm/";
	static final String BASE_URL3 ="http://ussclseapsub004.autodesk.com/adm/";
	static final String BASE_URL4 ="http://ussclseapsub005.autodesk.com/adm/";
	static final String BASE_URL5 ="http://ussclseapsub006.autodesk.com/adm/";
	static final String BASE_URL6 ="http://ussclseapsub007.autodesk.com/adm/";
	static final String BASE_URL7 ="http://ussclseapsub008.autodesk.com/adm/";
	static final String BASE_URL8 ="http://ussclseapsub009.autodesk.com/adm/";
	static final String BASE_URL9 ="http://ussclseapsub014.autodesk.com/adm/";
	static final String BASE_URL10 ="http://ussclseapsub015.autodesk.com/adm/";
	static final String BASE_URL11 ="http://ussclseapsub016.autodesk.com/adm/";
	static final String BASE_URL12 ="http://ussclseapsub017.autodesk.com/adm/";
	static final String BASE_URL13="http://ussclseapsub018.autodesk.com/adm/";

	static final String AUTO_USERNAME_DEV = "scat_super@ssttest.autodesk.com";
	static final String AUTO_PASSWORD_DEV = "Password1";

	static final String AUTO_USERNAME = AUTO_USERNAME_DEV;
	static final String AUTO_PASSWORD = AUTO_PASSWORD_DEV;
	
	static final String GUID_RESPONSE = "findByLoginNameResponse";

}