package mja;

import common.EISConstants;

/**
 * Constants and enumerated types specific to the Major Accounts application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class MJAConstants {
	public static enum ContractLineStatusType {
		EXPIRED, ACTIVE
	}

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

	static final String BASE_URL_DEV = "";
	static final String BASE_URL_STG = "";
	
	static final String BASE_PARTNER_URL_STG= "https://autodesk.adsksfstg.cs12.force.com/partner";

	static final String AUTO_USERNAME_DEV = "spencer.adams@autodesk.com.adsksfdev";
	static final String AUTO_PASSWORD_DEV = "sfdev1234";

	static final String AUTO_USERNAME_STG = "xavier.pitard@autodesk.com.adsksfstg";
	static final String AUTO_PASSWORD_STG = "sfstg1234";
//	static final String AUTO_USERNAME_STG = "spencer.adams@autodesk.com.adsksfbfix";
//	static final String AUTO_PASSWORD_STG = "sfbfix1234";
//	static final String AUTO_USERNAME_STG								= "vamsi.gundapuneni@autodesk.com.adsksfstg";  
//	static final String AUTO_PASSWORD_STG								= "Sai11ias";
	
	static final String PARTNER_USERNAME_STG							= "BredinaHaden";
	static final String PARTNER_PASSWORD_STG							= "Password1";
	
	// Default to STG, but it is expected that test code may make its own
	// assignments
	// static final String AUTO_USERNAME = AUTO_USERNAME_STG;
	// static final String AUTO_PASSWORD = AUTO_PASSWORD_STG;

	// NOTE that this array should must contain one user of each approval level,
	// starting at level 1
	// Use this as a model for discount approval chains
	// Users MUST be in ascending order of approval level
	static final String[] APPROVAL_USERS = { "qafs1", "qafs2", "qafs3",
			"qafs4", "qafs5", "qafs6" };

	static final EISConstants.App DEFAULT_MJA_APP = EISConstants.App.ADSK_SALES;

	static final String MJA_CASE_TYPE_ENUM_CONSTANT_NAME = "CASE_TYPE_ENUM";

	static final String MJA_CREATE_FROM_ENUM_CONSTANT_NAME = "CREATE_FROM_ENUM";

	// **** Constants used for referencing test properties constants
	// ******************************************************************
	// Following are constants that reference test properties constant names.
	// They work as follows:
	// 1) The user supplies a test-scope constant in the test properties file.
	// For example:
	// MJA_USER = CONSTANT[qafs2]
	// 2) Framework code references the test properties constant. For example:
	// The MJA_USER_OVERRIDE_CONSTANT_NAME variable is referenced in the
	// MJATestBase.setMJAUser() method. That
	// code will search the test-scope constants store using the value of
	// MJA_USER_OVERRIDE_CONSTANT_NAME, which is
	// "MJA_USER". If it is found, the value of the MJA_USER constant (which in
	// this case is qafs0) will be used.
	// 3) When framework code references a test properties constant, but the
	// constant does not exist, the result is context-dependent.
	// For example, in the case described here in item 2, if the test-scope
	// constant is not found, the code will use a default
	// user name. However, some constants are meant to provide
	// "expected results" like the expected quote approval level. When
	// such constants are not provided, tests will generally fail!
	//
	// Tips:
	// 1) Try to include the word OVERRIDE in the variable name when the
	// associated test properties constant contains an override
	// of some other value, and therefore will not cause a test to fail
	// 2) Try to include the word EXPECTED in the variable name when the
	// associated test properties constant contains an expected
	// results, and therefore will likely cause a test to fail
	// 3) For any other test properties constants whose absence may cause a test
	// to fail, give an appropriate name to the variable
	// that will be used to look it up
	// 4) Note that in items 1 and 2 it is suggested that variable names
	// (declared below) contain the key words, not necessarily
	// the name of the constant in the test properties
	// 5) Comment all variable declarations (below), and comment the constant
	// declarations in test properties files!!

	// When a constant with the name specified by this variable is defined in
	// test properties, it is used instead of the value defined
	// in DEFAULT_MJA_USER
	static final String MJA_USER_OVERRIDE_CONSTANT_NAME = "MJA_USER";

	static final String MJA_CASE_NAME_ENUM_CONSTANT_NAME = "CASE_NAME_ENUM";
	// ********************************************************************************************************************************
	static final String MJA_TRANSFER_LINE_ITEM_ENUM_CONSTANT_NAME = "TRANSFER_LINE_ITEM_ENUM";

	// The view that by default will be selected on the setup page (used for
	// filtering the list of users to only those used in MJA testing)
	static final String DEFAULT_USER_VIEW = "[TBD]";

	static final String ADMIN_USER_VIEW = "[TBD]";

	static final int DEFAULT_SUB_RENEWAL_SEARCH_TIMEOUT = 360000; // 6 minutes
	static final int DEFAULT_CREATE_OPPTY_FROM_SUB_RENEWAL_SEARCH_TIMEOUT = 180000; // 3
																					// minutes

}
