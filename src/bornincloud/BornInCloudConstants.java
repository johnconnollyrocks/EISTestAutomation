package bornincloud;

/**
 * Constants and enumerated types specific to BornInCloud application.
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
final class BornInCloudConstants {

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
	
	static final String JSON_MIME_TYPE = "application/json";
	

	static final String BASE_URL_DEV = "https://ipp-dev.aws.autodesk.com";
	static final String BASE_URL_STG = "https://ipp-stg.aws.autodesk.com";
	static final String EMAIL_URL_STG = "https://login.secureserver.net/?app=wbe";
	static final String PORTAL_URL_DEV = "https://customer-dev.autodesk.com";
	static final String PORTAL_URL_STG = "https://customer-stg.autodesk.com";
	static final String PELICANL_URL_DEV = "http://pelican-dev-lb-2025909158.us-west-1.elb.amazonaws.com/tfel2rs/doc/v2/subscription/post.jsp";
	static final String PELICANL_URL_DEV_SEARCH = "http://pelican-dev-lb-2025909158.us-west-1.elb.amazonaws.com/admin/subscription/findForm";
	static final String PELICANL_URL_STG_SEARCH = "http://pelican-stg-2091252326.us-east-1.elb.amazonaws.com/admin/subscription/findForm";
	static final String PELICANL_URL_STG = "http://internal-BIC-IPP-DEV-1703959650.us-east-1.elb.amazonaws.com/r?signature=hY8qJthhDe%2F1ogCm%2BwdrrZu8NVJcRarkZPxKe0Rfg8I%3D&timestamp=2014-09-10T06:16:18Z&userId=FEQBKFN25C58&state=US&&subId=1160";
	static final String IPP_INVAID_URL_DEV="";
	static final String adSecretKey = "h1p4[pwm79?!83T]*z|sd24OF0d3^@bc";

	static final String AUTO_USERNAME_DEV = "";
	static final String AUTO_PASSWORD_DEV = "";

	static final String AUTO_USERNAME_STG = "";
	static final String AUTO_PASSWORD_STG = "";
	
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
	// filtering the list of users to only those used in BornInCloud testing)
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
