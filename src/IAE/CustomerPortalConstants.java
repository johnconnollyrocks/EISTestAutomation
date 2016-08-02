package IAE;

/**
 * Constants and enumerated types specific to CustomerPortal application.
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
final class CustomerPortalConstants {

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

	static final String BASE_URL_DEV = "https://customer-dev.autodesk.com";
	static final String BASE_URL_STG = "https://customer-stg.autodesk.com";
	static final String BASE_URL_PRD = "https://manage.autodesk.com";
	static final String EMAIL_URL_STG = "https://login.secureserver.net/?app=wbe";
	static final String SCAT_STG_URL  ="https://subscription-stg.autodesk.com/sc/localadmin";
	static final String CONTINUE_TO_WEBSITE_LINK_TEXT						= "\"Continue to this website (not recommended).\"";

	static final String AUTO_USERNAME_DEV = "Madonna@ssttest.net";
	static final String AUTO_PASSWORD_DEV = "Password1";

	static final String AUTO_USERNAME_STG = "contract.manager1";
	static final String AUTO_PASSWORD_STG = "Contractm1";
	
	static final int EMAIL_WAIT_TIMEOUT = 60000;

//	static final String AUTO_USERNAME_STG = "";
//	static final String AUTO_PASSWORD_STG = "";

	
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
	// filtering the list of users to only those used in CustomerPortal testing)
	static final String DEFAULT_USER_VIEW = "[TBD]";

	static final String ADMIN_USER_VIEW = "[TBD]";
	static final String CONTRACT_TOGGLE_BUTTON = "contractToggleButtonPrdsNSrvcs";
	static final String CONTRACT_PRODUCT_LINE = "contractProductLinePrdsNSrvcs";
	static final String CONTRACT_SEATS = "contractSeatsPrdsNSrvcs";
	static final String CONTRACT_USERS = "contractUsersPrdsNSrvcs";
	static final String CONTRACT_SERVICES = "validService";
	static final String USER_DETAIL = "userDetail";
	static final String CONTRACT_INFORMATION = "contractInformationPrdsNSrvcs";
	static final String USER_DETAIL_CONTRACT_NO = "userDetailContractNumber";
	static final String USAGE_REPORT_AVAILABLE_CLOUD_CREDITS = "usageReportAvailableCloudCredits";
	static final String NO_CLOUD_CREDITS_PURCHASED_MESSAGE = "noCloudCreditsPurchasedMsg";
	static final String RENEWAL_NOTIFICATION_DATE = "renewalNofiticationDate";
	 static final int POOLED_CREDITS_TIMEOUT = 1200000;
}
