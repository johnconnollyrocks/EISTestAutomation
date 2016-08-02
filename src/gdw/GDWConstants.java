package gdw;

import common.EISConstants;

/**
 * Constants and enumerated types specific to the Major Accounts application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class GDWConstants {
	//Used in the 'Verify SFDC Deletion Logic' tests
	public static enum TestPart {
		ONE, ONE_DB,
		TWO,			//Part two not working - need to figure out how to serialize the opptys from part one
		TWO_DB,
		THREE,			//Part three not working - need to figure out how to serialize the opptys from part one
		THREE_DB,
		ALL
	}
	
	//TODO  Will need to handle users in a more intelligent manner.  We don't want to have to enter them in the test
	//  properties file, not even in an include file.  We should create one or more  collection objects (simple properties?)
	//  to store user names and passwords.  The user would be looked up by... what - role?  role and name?  Note that we can
	//  break out the environment component (e.g., "adsksfstg") and use it as a key to one of the collections.  We would pass
	//  an environment code property to the test, which would be used in looking up the user.

	static final String BASE_URL_DEV									= "";
	static final String BASE_URL_STG									= "";

	static final String AUTO_USERNAME_DEV								= "oleg.obukhov@autodesk.com.adsksfdev";
	static final String AUTO_PASSWORD_DEV								= "Dataqa1298";

	static final String AUTO_USERNAME_STG								= "yasuko.hirao@autodesk.com.adsksfstg";
	static final String AUTO_PASSWORD_STG								= "Ganbare1204";
	
	//Default to STG, but it is expected that test code may make its own assignments
	//static String AUTO_USERNAME										= AUTO_USERNAME_STG;
	//static String AUTO_PASSWORD										= AUTO_PASSWORD_STG;

	static final EISConstants.App DEFAULT_GDW_APP						= EISConstants.App.ADSK_SALES;

	//**** Constants used for referencing test properties constants ******************************************************************
	//Following are constants that reference test properties constant names.  They work as follows:
	//  1) The user supplies a test-scope constant in the test properties file.  For example:
	//     	 GDW_USER = CONSTANT[qafs2]
	//  2) Framework code references the test properties constant.  For example:
	//    	 The GDW_USER_OVERRIDE_CONSTANT_NAME variable is referenced in the GDWTestBase.setGDWUser() method.  That
	//       code will search the test-scope constants store using the value of GDW_USER_OVERRIDE_CONSTANT_NAME, which is
	//       "GDW_USER".  If it is found, the value of the GDW_USER constant (which in this case is qafs0) will be used.
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
	//  in DEFAULT_GDW_USER
    static final String GDW_USER_OVERRIDE_CONSTANT_NAME 		= "GDW_USER";
 	//********************************************************************************************************************************

	//The view that by default will be selected on the setup page (used for filtering the list of users to only those used in GDW testing)
	static final String DEFAULT_USER_VIEW						= "[TBD]";
	
	static final String ADMIN_USER_VIEW							= "[TBD]";
}
