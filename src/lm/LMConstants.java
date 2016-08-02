package lm;

import common.EISConstants;

/**
 * Constants and enumerated types specific to the Lead Management application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class LMConstants {
	//TODO  Will need to handle users in a more intelligent manner.  We don't want to have to enter them in the test
	//  properties file, not even in an include file.  We should create one or more  collection objects (simple properties?)
	//  to store user names and passwords.  The user would be looked up by... what - role?  role and name?  Note that we can
	//  break out the environment component (e.g., "adsksfstg") and use it as a key to one of the collections.  We would pass
	//  an environment code property to the test, which would be used in looking up the user.

	public static enum CreateFrom {
		SFDC,		//This initiates lead creation through the SFDC interface
		PORTAL,		//This initiates lead creation through a portal interface
		BATCH_UPLOAD, //This initiates lead creation through bunch of excel data
		PLOCATOR 	//This initiates lead creation through Plocator window
	}	
	
	static final String BASE_URL_DEV									= "";
	static final String BASE_URL_STG									= "";
	
	static final String BASE_PARTNER_URL_DEV							= "";
	static final String BASE_PARTNER_URL_STG							= "https://autodesk.adsksfstg.cs12.force.com/partner";
//	static final String BASE_PARTNER_URL_STG							= "https://autodesk.adsksfbfix.cs12.force.com/partner";
	
	static final String AUTO_USERNAME_DEV								= "test.employeenumber@autodesk.com.adsksfdev";
	static final String AUTO_PASSWORD_DEV								= "autodesk12#";

//	static final String AUTO_USERNAME_STG								= "autodeskbusinessadmin@autodesk.com.adsksfstg";
//	static final String AUTO_PASSWORD_STG								= "autodesk12#";
//	static final String AUTO_USERNAME_STG								= "vamsi.gundapuneni@autodesk.com.adsksfstg";  
//	static final String AUTO_PASSWORD_STG								= "Sai11ias";
//	static final String AUTO_USERNAME_STG								= "kirankumar.bellary@autodesk.com.adsksfstg";  
//	static final String AUTO_PASSWORD_STG								= "ADSKr25test";
	static final String AUTO_USERNAME_STG								= "kirankumar.bellary@autodesk.prd.com.adsksfstg";  
	static final String AUTO_PASSWORD_STG								= "Aut0d3sk";
//	static final String AUTO_USERNAME_STG								= "kirankumar.bellary@autodesk.com.prd.adsksfbfix";
//	static final String AUTO_PASSWORD_STG								= "Kalyani1";
	
	static final String PARTNER_USERNAME_DEV							= "";
	static final String PARTNER_PASSWORD_DEV							= "";

	static final String PARTNER_USERNAME_STG							= "";
	static final String PARTNER_PASSWORD_STG							= "";
	
	//Default to STG, but it is expected that test code may make its own assignments
	//static final String BASE_URL										= BASE_URL_STG;
	//static String AUTO_USERNAME										= AUTO_USERNAME_STG;
	//static String AUTO_PASSWORD										= AUTO_PASSWORD_STG;

	static final EISConstants.App DEFAULT_LM_APP						= EISConstants.App.LEAD_MANAGEMENT;

	//**** Constants used for referencing test properties constants ******************************************************************
	//Following are constants that reference test properties constant names.  They work as follows:
	//  1) The user supplies a test-scope constant in the test properties file.  For example:
	//     	 LM_USER = CONSTANT[qafs2]
	//  2) Framework code references the test properties constant.  For example:
	//    	 The LM_USER_OVERRIDE_CONSTANT_NAME variable is referenced in the LMTestBase.setLMUser() method.  That
	//       code will search the test-scope constants store using the value of LM_USER_OVERRIDE_CONSTANT_NAME, which is
	//       "LM_USER".  If it is found, the value of the LM_USER constant will be used.
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
	//  in DEFAULT_LM_USER
    static final String LM_USER_OVERRIDE_CONSTANT_NAME 			= "LM_USER";
    static final String LM_CREATE_FROM_ENUM_CONSTANT_NAME		= "CREATE_FROM_ENUM";
 	//********************************************************************************************************************************

	//The view that by default will be selected on the setup page (used for filtering the list of users to only those used in LM testing)
	static final String DEFAULT_USER_VIEW						= "[TBD]";
	
	static final String ADMIN_USER_VIEW							= "[TBD]";
	
	static final int DEFAULT_LEAD_SHARE_TIMEOUT					= 1200000;	//20 minutes
}
