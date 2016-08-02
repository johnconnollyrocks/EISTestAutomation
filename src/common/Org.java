package common;

/**
 * Representation of an SFDC organization.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class Org extends SFDCObject {
	static final int ORG_SIGNUP_TIMEOUT = 600000;		

	//State variables 
	//  Some (like name, id, and url) are defined in the superclass
	//  Define only object-specific ones here
	protected String edition = "";
	protected String userName = "";
	protected String loginUserName = "";
	protected String loginUserPassword = "";

	protected boolean	isChatterEdition = false;

	//Make all Page objects on-public, but provide getters so that test code can have read
	//  access to them.  That will allow test code to access test data, metadata, constants,
	//  etc., through this SFDCObject instance.  That's important, because we don't want
	//  test code authors to declare Page objects when they don't need to; instead we want
	//  test code to call utilities wherever possible.  Those utilities typically create an
	//  SFDCObject, instantiate the Page objects it needs, and pass back the SFDCObject
	//  instance.  It is through that instance that test code can get access to this
	//  object's Page data
	
	private Page_ createAccountPage;
	private Page_ blackTabPage;
	private Page_ signupPage;

	public Org(Page_ createAccountPage, Page_ signupPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_ORG, EISConstants.OBJECT_TYPE_ORG);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.blackTabPage		= EISTestBase.blackTabPage;
		
		this.createAccountPage	= createAccountPage;
		this.signupPage			= signupPage;
	}

	public String getEdition() {
		return edition;
	}

	public String getUserName() {
		return userName;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public String getLoginUserPassword() {
		return loginUserPassword;
	}

	public boolean isChatterEdition() {
		return isChatterEdition;
	}

	public Page_ getCreateAccountPage() {
		return createAccountPage;
	}

	public Page_ getSignupPage() {
		return signupPage;
	}

	public Page_ getBlackTabPage() {
		return blackTabPage;
	}

	@Override
	public String toString() {
		return "Org [super=" + super.toString() +
		       ", edition="	+ edition +
			   ", userName=" + userName +
		       ", loginUserName=" + loginUserName +
			   ", loginUserPassword=" + loginUserPassword +
		       ", isChatterEdition=" + isChatterEdition +
			   "]";
	}

	@Override
	public boolean open() {
		boolean pageLoaded;
		
		mainWindow.select();
		
		pageLoaded = super.open();
		if (pageLoaded) {
			waitForPageToSettle();
		}
		
		return pageLoaded;
	}
	
	@Override
	public void openForEdit() {
		mainWindow.select();
		open();
		
		//commonPage.clickAndWait("editButton");
		commonPage.clickAndWait("editButton", "saveButton");
	}
	
	protected void signup(String fieldValueSuffix, String accountName, String accountId) {
     	Util.printInfo("Signing up account...");
     	
		mainWindow.select();

		//commonPage.clickAndWait("tabSysAdmin");
		commonPage.click("tabSysAdmin");
		blackTabPage.waitForFieldPresent("signupLink");

		//blackTabPage.clickAndWait("signupLink");
		blackTabPage.click("signupLink");
		commonPage.waitForFieldPresent("saveButton");
		
		setCalculatedTestDataFields(fieldValueSuffix);
		
		signupPage.populate();

		commonPage.clickToSubmit("saveButton");
		
		setName(signupPage.getTestDataValue("companyName"));
		setId(signupPage.getValueFromGUI("orgIdLink"));

		edition = signupPage.getTestDataValue("edition");
		userName = signupPage.getTestDataValue("userName");
		loginUserName = signupPage.getTestDataValue("email");
		loginUserPassword = signupPage.getTestDataValue("password");
		
		//commonPage.clickAndWait("tabSysAdmin");
		commonPage.click("tabSysAdmin");
		blackTabPage.waitForFieldPresent("searchBox");
		
		blackTabPage.populateField("searchBox", getId());

		//blackTabPage.clickAndWait("searchButton");
		blackTabPage.clickAndWait("searchButton", "orgNameLink");
		
		//We have gotten rid of clickAndWait(String fieldName) calls, so we have to specify
		//  something for which to wait - but what?
		//blackTabPage.clickAndWait("orgNameLink");
		blackTabPage.click("orgNameLink");
		
		waitForOrgSignup();
		
		setUrl();
		
		//blackTabPage.clickAndWait("editProvisionedFeatures");
		blackTabPage.clickAndWait("editProvisionedFeatures", "checkoutOrderCenterAccess");

		blackTabPage.check("checkoutOrderCenterAccess");
		blackTabPage.check("apiEnabled");

		commonPage.clickToSubmit("saveButton");
		
		//blackTabPage.clickAndWait("accountIdChooser");
		blackTabPage.clickAndWait("accountIdChooser", "accountIdBox");
		
		blackTabPage.populateField("accountIdBox", accountId);

     	blackTabPage.clickToSubmit("setAccountIdButton");
		
		Util.printInfo("Signed up account '" + accountName + "' to org '" + getName() + "' (" + getUrl() + ")");
	}

	protected void setCalculatedTestDataFields(String fieldValueSuffix) {
		signupPage.setTestDataValue("companyName", getSfdcObjectPrefix() + fieldValueSuffix, true);
		signupPage.setTestDataValue("userName", EISConstants.OBJECT_NAME_PREFIX_ORG_USER + fieldValueSuffix, true);
		signupPage.setTestDataValue("email", EISConstants.OBJECT_NAME_PREFIX_ORG_USER + fieldValueSuffix + "@abc.com", true);
		signupPage.setTestDataValue("userFirstName", EISConstants.OBJECT_NAME_PREFIX_ORG_USER + fieldValueSuffix + "_first", true);
		signupPage.setTestDataValue("userLastName", EISConstants.OBJECT_NAME_PREFIX_ORG_USER + fieldValueSuffix + "_last", true);
		signupPage.setTestDataValue("address", createAccountPage.getTestDataValue("billingStreet"), true);
		signupPage.setTestDataValue("city", createAccountPage.getTestDataValue("billingCity"), true);
		signupPage.setTestDataValue("stateProvince", createAccountPage.getTestDataValue("billingStateProvince"), true);
		signupPage.setTestDataValue("zipCode", createAccountPage.getTestDataValue("billingPostalCode"), true);
		signupPage.setTestDataValue("country", createAccountPage.getTestDataValue("billingCountry"), true);
	}

	protected boolean waitForOrgSignup() {
		boolean success = blackTabPage.waitForFieldWhileRefreshing("accountIdChooser", true, ORG_SIGNUP_TIMEOUT);
		
		if (!success) {
			fail("Org did not sign up within " + (ORG_SIGNUP_TIMEOUT / 1000) + " seconds");
		}
		
		return success;
	}
}
