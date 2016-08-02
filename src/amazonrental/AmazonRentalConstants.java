package amazonrental;

/**
 * Constants and enumerated types specific to CustomerPortal application.
 * 
 * @author 
 * @version 1.0.0
 */
final class AmazonRentalConstants {

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
	
	static final String appBrowser = "chrome";
	static final String CONTINUE_TO_WEBSITE_LINK_TEXT						= "\"Continue to this website (not recommended).\"";

	static final String BASE_URL_DEV = "https://developer.amazon.com/instantaccess/profiles.html";
	static final String BASE_URL_STG = "";

	static final String AUTO_USERNAME_DEV = "eis.ea.ebs.pj2.amazon.rentals.all@autodesk.com";
	static final String AUTO_PASSWORD_DEV = "amazon";

	static final String AUTO_USERNAME_STG = "";
	static final String AUTO_PASSWORD_STG = "";
	
	static final String BASE_URL_SFDC = "https://test.salesforce.com";
	

}
