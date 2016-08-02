package sfdc;

/**
 * Constants and enumerated types specific to the SCAT application.
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
final class SFDCConstants {
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
	static final String CONTINUE_TO_WEBSITE_LINK_TEXT						= "\"Continue to this website (not recommended).\"";
	
	static final String BASE_URL = "https://cs12.salesforce.com ";

	static final String AUTO_USERNAME_STG = "praveenkumar.mahadev@autodesk.com.stg.adsksfstg";
	static final String AUTO_PASSWORD_STG = "Prax_10987";

	static final String AUTO_USERNAME = AUTO_USERNAME_STG;
	static final String AUTO_PASSWORD = AUTO_PASSWORD_STG;
	
	static final String SEARCH_CONTACT = "tomeka henriquez";
	

}