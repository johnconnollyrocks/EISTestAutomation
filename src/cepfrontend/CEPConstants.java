package cepfrontend;

/**
 * Constants and enumerated types specific to CEP application.
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
final class CEPConstants {

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
	static final String BASE_URL_STG = "https://cep-stg.autodesk.com";

	static final String AUTO_USERNAME_DEV = "";
	static final String AUTO_PASSWORD_DEV = "";

//	static final String AUTO_USERNAME_STG = "contract.manager1";
//	static final String AUTO_PASSWORD_STG = "Contractm1";

//	static final String AUTO_USERNAME_STG = "1212234835email@ssttest.net";
	static final String AUTO_USERNAME_STG = "Michael.hall@autodesk.com";
	static final String AUTO_PASSWORD_STG = "Password1";
 
	// Default to STG, but it is expected that test code may make its own
	// assignments

	static final String[] APPROVAL_USERS = { "qafs1", "qafs2", "qafs3",
			"qafs4", "qafs5", "qafs6" };

	// static final EISConstants.App DEFAULT_CEP_APP = EISConstants.App.CEP;
	//
	static final String CEP_CASE_TYPE_ENUM_CONSTANT_NAME = "CASE_TYPE_ENUM";

	static final String CEP_CREATE_FROM_ENUM_CONSTANT_NAME = "CREATE_FROM_ENUM";

	// **** Constants used for referencing test properties constants
	// ******************************************************************

	static final String CEP_USER_OVERRIDE_CONSTANT_NAME = "CEP_USER";

	static final String CEP_CASE_NAME_ENUM_CONSTANT_NAME = "CASE_NAME_ENUM";
	// ********************************************************************************************************************************
	static final String CEP_TRANSFER_LINE_ITEM_ENUM_CONSTANT_NAME = "TRANSFER_LINE_ITEM_ENUM";

	// The view that by default will be selected on the setup page (used for
	// filtering the list of users to only those used in CEP testing)
	static final String DEFAULT_USER_VIEW = "[TBD]";

	static final String ADMIN_USER_VIEW = "[TBD]";

}
