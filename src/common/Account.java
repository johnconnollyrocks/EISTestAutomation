package common;

import org.openqa.selenium.By;

/**
 * Representation of an application-independent SFDC Account.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class Account extends SFDCObject {
	//State variables
	//  Some (like name, id, and url) are defined in the super-class
	//  Define only object-specific ones here
	private Org org = null;

	private Address billingAddress = null;
	private Address shippingAddress = null;
	private String currency = null;

	//Make all Page objects non-public, but provide getters so that test code can have read
	//  access to them.  That will allow test code to access test data, metadata, constants,
	//  etc., through this SFDCObject instance.  That's important, because we don't want
	//  test code authors to declare Page objects when they don't need to; instead we want
	//  test code to call utilities wherever possible.  Those utilities typically create an
	//  SFDCObject, instantiate the Page objects it needs, and pass back the SFDCObject
	//  instance.  It is through that instance that test code can get access to this
	//  object's Page data

	private Page_ createAccountRTPage;
	private Page_ createAccountPage;
	private Page_ viewAccountPage;
	private Page_ createAccountFromAccountsLink;
	private Page_ createPartnerOpportunityPage;
	
	//private Page_ signupPage;

	//public Account(Page_ createAccountRTPage, Page_ createAccountPage, Page_ viewAccountPage, Page_ signupPage) {
	public Account(Page_ createAccountRTPage, Page_ createAccountPage, Page_ viewAccountPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_ACCT, EISConstants.OBJECT_TYPE_ACCT);

		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.createAccountRTPage	= createAccountRTPage;
		this.createAccountPage		= createAccountPage;
		this.viewAccountPage		= viewAccountPage;
		
		//this.signupPage				= signupPage;

		setName(generateObjectName(this.createAccountPage, EISConstants.OBJECT_NAME_FIELD_NAME_ACCT));
	}
	public Account(Page_ createAccountRTPage, Page_ createAccountPage, Page_ viewAccountPage,Page_ createPartnerOpportunityPage,Page_ createAccountFromAccountsLink) {
		super(EISConstants.OBJECT_NAME_PREFIX_ACCT, EISConstants.OBJECT_TYPE_ACCT);

		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.createAccountRTPage	= createAccountRTPage;
		this.createAccountPage		= createAccountPage;
		this.viewAccountPage		= viewAccountPage;
		this.createPartnerOpportunityPage=createPartnerOpportunityPage;
		this.createAccountFromAccountsLink=createAccountFromAccountsLink;
		
		//this.signupPage				= signupPage;

		setName(generateObjectName(this.createAccountPage, EISConstants.OBJECT_NAME_FIELD_NAME_ACCT));
	}
	public Org getOrg() {
		return org;
	}

	public String getOrgName() {
		return org.getName();
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getCurrency() {
		return currency;
	}
	
	//getUserName(), getLoginUserName(), and getLoginUserPassword() are provided here
	//  as a convenience, since those variables are associated with accounts in the
	//  minds of some.  To get other org variables, a call like this can be made:
	//    myAccount.getOrg().getEdition()
	public String getUserName() {
		return getOrg().getUserName();
	}

	public String getLoginUserName() {
		return getOrg().getLoginUserName();
	}

	public String getLoginUserPassword() {
		return getOrg().getLoginUserPassword();
	}

	public Page_ getCreateAccountPage() {
		return createAccountPage;
	}

	public Page_ getViewAccountPage() {
		return viewAccountPage;
	}
	public Page_ getCreateAccountFromAccountsLink() {
		return createAccountFromAccountsLink;
	}
	
	public Page_ getCreatePartnerOpportunityPage() {
		return createPartnerOpportunityPage;
	}
	

	@Override
	public String toString() {
		return "Account [super=" + super.toString() +
		       ", orgName=" + getOrgName() +
		       ", loginUserName=" + getLoginUserName() +
			   ", loginUserPassword=" + getLoginUserPassword() +
			   ", billingAddress=" + billingAddress.toString() +
			   ", shippingAddress=" + shippingAddress.toString() +
		   	   ", currency=" + getCurrency() + "]";
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

/*	public String createAndSignup() {
		create();
		signup();

     	return getName();
	}
*/
	
	public String create() {
     	Util.printInfo("Creating account...");

		mainWindow.select();

		//commonPage.clickAndWait("tabAccounts");
		commonPage.clickAndWait("tabAccounts", "newButton");

		//Ensure that we wait for the page to load, so that we can make a valid check for the presence
		//  of the recordType field
		//commonPage.clickAndWait("newButton");
		commonPage.clickToSubmit("newButton");

     	if (createAccountRTPage.isFieldPresent("recordType")) {
     		createAccountRTPage.populate();
     		
     		//createAccountRTPage.clickAndWait("continueButton");
     		createAccountRTPage.click("continueButton");
     		
     		//The problem with this is that it waits for privacyStatement, which never goes away
     		//commonPage.waitForPageToSettle();
     		commonPage.waitForFieldVisible("saveButton");
     	}

     	createAccountPage.populate();
     	createAccountPage.click("copyBillingToShippingLink");

     	commonPage.clickToSubmit("saveButton");

     	setUrl();
     	setId();

     	billingAddress = new Address(createAccountPage.getTestDataValue("billingStreet"), createAccountPage.getTestDataValue("billingCity"), createAccountPage.getTestDataValue("billingStateProvince"), createAccountPage.getTestDataValue("billingPostalCode"), createAccountPage.getTestDataValue("billingCountry"));
     	shippingAddress = new Address(billingAddress);

     	currency = createAccountPage.getTestDataValue("currency").toUpperCase();

     	Util.printInfo("Created account '" + getName() + "' (" + getUrl() + ")");

     	return getName();
	}
	
	public String createForOppty(String accName){
		
		String accName1;
		accName1=accName;
		Util.printInfo("Creating New Account...");
		
		//2. GO TO ACCOUNTS
		EISTestBase.driver.switchTo().frame("navigationLinks");
		EISTestBase.driver.findElement(By.linkText("Accounts")).click();
		EISTestBase.driver.switchTo().defaultContent();
//		EISTestBase.switchDriverToFrame(2);
//		createPartnerOpportunityPage.click("account");
//		mainWindow.select();
		commonPage.click("newButton");
	/*	mainWindow.select();
		String locator =createPartnerOpportunityPage.clickAndWaitForPopUpToOpen("accountNameLink");
        createAccountFromAccountsLink.selectWindow(locator);
       //Lookup is navigating to a new window. Need to handle Cretificate Error. As of now using an existing account name.	
        createAccountFromAccountsLink.click("certificateError");*/
		createAccountFromAccountsLink.click("createAccountButton");
		createAccountFromAccountsLink.setTestDataValue("accountName",accName1);
		createAccountFromAccountsLink.setTestDataValue("address1", "4040 Civic Center Drive".concat(accName1));
		createAccountFromAccountsLink.populate();
		commonPage.click("saveButton");
		return getName();
	}

/*	public void signup() {
		org = new Org(createAccountPage, signupPage);
		String suffix;
		String name = getName();

		suffix = name.replace(EISConstants.OBJECT_NAME_PREFIX_ACCT, "");

		org.signup(suffix, name, getId());
	}
*/
}
