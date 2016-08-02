package epartner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.EISConstants;
import common.EISTestBase;
import common.Page_;
import common.SFDCObject;
import common.Util;

/**
 * Representation of an ePartner SFDC Partner.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class Partner extends SFDCObject {
	//State variables 
	//  Some (like name, id, and url) are defined in the superclass
	//  Define only object-specific ones here

	//Make all Page objects private, but provide getters so that test code can have read
	//  access to them.  That will allow test code to access test data, metadata, constants,
	//  etc., through this SFDCObject instance.  That's important, because we don't want
	//  test code authors to declare Page objects when they don't need to; instead we want
	//  test code to call utilities wherever possible.  Those utilities typically create an
	//  SFDCObject, instantiate the Page objects it needs, and pass back the SFDCObject
	//  instance.  It is through that instance that test code can get access to this
	//  object's Page data
	private Page_ portalLandingPage;
	public Page_ getViewAccountPage() {
		return viewAccountPage;
	}

	public void setViewAccountPage(Page_ viewAccountPage) {
		this.viewAccountPage = viewAccountPage;
	}

	private Page_ createPartnerOpportunityPage;
	private Page_ viewPartnerOpportunityPage;
	private Page_ viewAccountDetailsPage;
	private Page_ createAccountFromAccountsLink;
	private Page_ createEndCustomerContractPage;
	private Page_ approveRejectByGmailPage;
	private Page_ viewAccountPage;
	private String randomAlphabet = "";
	private int randomNumber;
	private String opptyName=null;
	private String accountName=null;
	private String partnerAccountName=null;
	
	private String distributorAccountName 		=null;			
	private String distributorAccountCity 		=null;
	private String distributorAccountCountry 	=null;
	private String distributorAccountPartnerType =null;							

	private String projectedCloseDate=null;
	private String stage=null;
	private String contactRole=null;
	private String searchText=null;
	private String contactSelectionRadioButton=null;
	private String distributorSelectionRadioButton=null;
	private String distributorAccountCSN=null;
	//private String firstProductLineCheckbox=null;
	//private String firstProductSeatsText=null;
	private String opportunityNumber =null;
	private String opportunityName	=null;
	private String accountCSN=null;
	private String accountType=null;
	/**
	 * Class constructor specifying the Page objects necessary for interacting with an SFDC Partner.
	 * @param portalLandingPage the Page object that defines GUI elements on the portal landing page
	 */
	Partner(Page_ portalLandingPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_PARTNER, EISConstants.OBJECT_TYPE_PARTNER);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.portalLandingPage	= portalLandingPage;
	}

/*	Partner(Page_ createPartnerOpportunityPage,Page_ viewPartnerOpportunityPage, Page_ viewAccountDetailsPage, Page_ createAccountFromAccountsLink) {
		super(EISConstants.OBJECT_NAME_PREFIX_PARTNER, EISConstants.OBJECT_TYPE_PARTNER);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.createPartnerOpportunityPage	= createPartnerOpportunityPage;
		this.viewPartnerOpportunityPage	= viewPartnerOpportunityPage;
		this.viewAccountDetailsPage		= viewAccountDetailsPage;
		this.createAccountFromAccountsLink = createAccountFromAccountsLink;
	} */
	Partner(Page_ createPartnerOpportunityPage,Page_ viewPartnerOpportunityPage, Page_ viewAccountDetailsPage,Page_ createEndCustomerContractPage,Page_ viewAccountPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_PARTNER, EISConstants.OBJECT_TYPE_PARTNER);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.createPartnerOpportunityPage	= createPartnerOpportunityPage;
		this.viewPartnerOpportunityPage	= viewPartnerOpportunityPage;
		this.viewAccountDetailsPage		= viewAccountDetailsPage;
		this.createEndCustomerContractPage = createEndCustomerContractPage;
		this.viewAccountPage		= viewAccountPage;
		setName(generateObjectName(this.createPartnerOpportunityPage, EISConstants.OBJECT_NAME_FIELD_NAME_OPPTY));
	}
	
	/**
	 * @return the distributorAccountName
	 */
	public String getDistributorAccountName() {
		return distributorAccountName;
	}

	/**
	 * @return the distributorAccountCity
	 */
	public String getDistributorAccountCity() {
		return distributorAccountCity;
	}

	/**
	 * @return the distributorAccountCountry
	 */
	public String getDistributorAccountCountry() {
		return distributorAccountCountry;
	}

	/**
	 * @return the distributorAccountPartnerType
	 */
	public String getDistributorAccountPartnerType() {
		return distributorAccountPartnerType;
	}
	
	/**
	 * @return the distributorAccountCSN
	 */
	public String getDistributorAccountCSN() {
		return distributorAccountCSN;
	}

	/**
	 * @return the partnerAccountName
	 */
	public String getPartnerAccountName() {
		return partnerAccountName;
	}
	
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @return the projectedCloseDate
	 */
	public String getProjectedCloseDate() {
		return projectedCloseDate;
	}

	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @return the contactRole
	 */
	public String getContactRole() {
		return contactRole;
	}

	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @return the contactSelectionRadioButton
	 */
	public String getContactSelectionRadioButton() {
		return contactSelectionRadioButton;
	}

	/**
	 * @return the distributorSelectionRadioButton
	 */
	public String getDistributorSelectionRadioButton() {
		return distributorSelectionRadioButton;
	}

	/**
	 * @return the firstProductLineCheckbox
	 */
/*	public String getFirstProductLineCheckbox() {
		return firstProductLineCheckbox;
	}
*/
	
	/**
	 * @return the firstProductSeatsText
	 */
/*	public String getFirstProductSeatsText() {
		return firstProductSeatsText;
	}
*/

	/**
	 * @return the opportunityNumber
	 */
	public String getOpportunityNumber() {
		return opportunityNumber;
	}

	/**
	 * @return the opportunityName
	 */
	public String getOpportunityName() {
		return opportunityName;
	}

	/**
	 * @return the createPartnerOpportunityPage
	 */
	public Page_ getCreatePartnerOpportunityPage() {
		return createPartnerOpportunityPage;
	}

	/**
	 * @return the viewPartnerOpportunityPage
	 */
	public Page_ getViewPartnerOpportunityPage() {
		return viewPartnerOpportunityPage;
	}
	
	/**
	 * @return the viewAccountDetailsPage
	 */
	public Page_ getViewAccountDetailsPage() {
		return viewAccountDetailsPage;
	}
	
	
	/**
	 * Gets the Page object that defines GUI elements on the portal landing page
	 * @return The Page object that defines GUI elements on the portal landing page
	 */
	Page_ getPortalLandingPage() {
		return portalLandingPage;
	}

		
	protected void setCreateOpportunityStateVariables() {
		partnerAccountName = createPartnerOpportunityPage.getValueFromGUI("partnerAccountName");			
	}

	public String getAccountCSN() {
		return accountCSN;
	}

	public void setAccountCSN(String accountCSN) {
		this.accountCSN = accountCSN;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}	
	/**
	 * @return the CreateAccountFromAccountsLink
	 */
	public Page_ getCreateAccountFromAccountsLink() {
		return createAccountFromAccountsLink;
	}

	
	@Override
	public String toString() {
		return "Partner [super=" + super.toString() +
				", opptyName=" 		+ opptyName +
				", accountName="	+ accountName +
				", projectedCloseDate =" + projectedCloseDate +
				", stage=" 				+ stage +
				", contactRole=" + contactRole +
				", searchText=" + searchText +
				", contactSelectionRadioButton=" + contactSelectionRadioButton +
				", distributorSelectionRadioButton=" + distributorSelectionRadioButton +
				//", firstProductLineCheckbox=" +  firstProductLineCheckbox +
				//", firstProductSeatsText=" +  firstProductSeatsText +
				", opportunityNumber =" + opportunityNumber +
				", opportunityName	=" + opportunityName +
				", partnerAccountName=" + partnerAccountName +		

				", distributorAccountCSN=" + distributorAccountCSN +
				", distributorAccountName=" +			distributorAccountName +
				", distributorAccountCity=" +			distributorAccountCity +
				", distributorAccountCountry=" +		distributorAccountCountry +
				", distributorAccountPartnerType=" +	distributorAccountPartnerType +		
				", accountCSN="+	accountCSN+
				", accountType="+	accountType+
				
				"]";
	}

	/**
	 * @return the opptyName
	 */
	public String getOpptyName() {
		return opptyName;
	}

	@Override
	protected boolean open() {
		boolean pageLoaded;
		
		mainWindow.select();
		
		pageLoaded = super.open();
		if (pageLoaded) {
			waitForPageToSettle();
		}
		
		return pageLoaded;
	}
	
	protected String createOpportunity() {
		Util.printInfo("Creating Opportunity for Partner...");
		
		commonPage.click("newButton");
		mainWindow.select();
		
		createPartnerOpportunityPage.populate(); 
		setCreateOpportunityStateVariables();
		
		commonPage.clickToSubmit("saveButton", "editButton");
		
		//NOTES TO OFFSHORE - the URL you are saving here is for an opportunity, not a partner.  Speaking
		//  of which, please see the notes in the test method for my comments on the Partner class
     	setUrl();
     	setId();

     	opportunityNumber = viewPartnerOpportunityPage.getValueFromGUI("opportunityNumber");
     	setName(opportunityNumber);
     	
     	Util.printInfo("Created opportunity '" + opportunityNumber + "' (" + getUrl() + ")");

     	return opportunityNumber;
	}

	public String editOppty(String opptyName2) {
		// TODO Auto-generated method stub
		commonPage.click("editButton");
		createPartnerOpportunityPage.populateField("opptyName",opptyName2);
		commonPage.clickToSubmit("saveButton", "editButton");
		opportunityNumber = viewPartnerOpportunityPage.getValueFromGUI("opportunityName");
     	setName(opportunityNumber);
     	
     	Util.printInfo("Created opportunity '" + opportunityNumber + "' (" + getUrl() + ")");

     	return opportunityNumber;
	}
	protected String createOpportunities() {
		Util.printInfo("Creating Opportunity for Partner...");
		
		commonPage.click("newButton");
		mainWindow.select();
		try {
			randomAlphabet=getRandomString(5);
			randomNumber=getUniqueId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		createPartnerOpportunityPage.populateField("opptyName", "Oppty Test Auto "+randomAlphabet+randomNumber);
		createPartnerOpportunityPage.populate(); 
		setCreateOpportunityStateVariables();
		
		commonPage.clickToSubmit("saveButton", "editButton");
		
		//NOTES TO OFFSHORE - the URL you are saving here is for an opportunity, not a partner.  Speaking
		//  of which, please see the notes in the test method for my comments on the Partner class
     	setUrl();
     	setId();

     	opportunityNumber = viewPartnerOpportunityPage.getValueFromGUI("opportunityNumber");
     	setName(opportunityNumber);
     	
     	Util.printInfo("Created opportunity '" + opportunityNumber + "' (" + getUrl() + ")");

     	return opportunityNumber;
	}
	protected String createOpportunityWithNewAccount(String accName) {
		Util.sleep(2000);
        EISTestBase.switchDriverToFrame(2);
        Util.printInfo("Creating Opportunity for Partner...");
        createPartnerOpportunityPage.click("opportunity");
        commonPage.click("newButton");
        mainWindow.select();
        createPartnerOpportunityPage.setTestDataValue("accountNameText",accName);
       	createPartnerOpportunityPage.populate(); 
		setCreateOpportunityStateVariables();
		commonPage.clickToSubmit("saveButton", "editButton");
		
		//NOTES TO OFFSHORE - the URL you are saving here is for an opportunity, not a partner.  Speaking
		//  of which, please see the notes in the test method for my comments on the Partner class
     	setUrl();
     	setId();

     	opportunityNumber = viewPartnerOpportunityPage.getValueFromGUI("opportunityNumber");
     	setName(opportunityNumber);
     	
     	Util.printInfo("Created opportunity '" + opportunityNumber + "' (" + getUrl() + ")");

     	return opportunityNumber;
	}

	protected void verifyAddressForOpportunity() {
		//String returnValue = "";
		
	//	Util.printInfo("Verifying Address of  the opportunity '" + opportunityNumber + "'...");
				
		mainWindow.select();

		createPartnerOpportunityPage.waitForFieldPresent("editButton");
		Util.sleep(2000);
		createPartnerOpportunityPage.refresh(2000);
		Util.sleep(5000);
		createPartnerOpportunityPage.refresh();
		EISTestBase.switchDriverToFrame(3);
		
		if(createPartnerOpportunityPage.isFieldPresent("yesToAccept")){
		Util.sleep(5000);
		createPartnerOpportunityPage.click("yesToAccept");
		}
		if(createPartnerOpportunityPage.isFieldPresent("clickToCreateAccount")){
		Util.sleep(5000);
		createPartnerOpportunityPage.click("clickToCreateAccount");
		}
		//This button is in the portal, not the common page.  And we have defined searchText on
		//  createPartnerOpportunityPage, so perhaps the button belongs there
		//commonPage.click("goButton");
//		createPartnerOpportunityPage.click("useAsEntered");
		Util.printInfo("Verified Account for  the opportunity '" + opportunityNumber + "'");
		mainWindow.select();
		
	}
	//NOTES TO OFFSHORE - don't return something if it cannot be used by the caller
	//protected String addContactsToOpportunity() {
	protected void addContactsToOpportunity() {
		//String returnValue = "";
		
	//	Util.printInfo("Adding Contacts to the opportunity '" + opportunityNumber + "'...");
		Util.sleep(2000);
		EISTestBase.switchDriverToFrame(4);
		viewPartnerOpportunityPage.click("addContactButton");
		mainWindow.select();

		//NOTES TO OFFSHORE - It turns out we need to click the New button after clicking the Add button
		viewPartnerOpportunityPage.click("newContactButton");
		createPartnerOpportunityPage.waitForFieldPresent("contactRole");
		
		createPartnerOpportunityPage.populateInstance("ADD_CONTACTS");
		
		//This button is in the portal, not the common page.  And we have defined searchText on
		//  createPartnerOpportunityPage, so perhaps the button belongs there
		//commonPage.click("goButton");
		createPartnerOpportunityPage.click("searchButton");
		
		//NOTES TO OFFSHORE - Replaced sleep with call to waitForElementPresent
		//Util.sleep(15000);
		createPartnerOpportunityPage.waitForFieldPresent("contactSelectionRadioButton");

		createPartnerOpportunityPage.populateInstance("ADD_CONTACTS_1");
		
		//Since we had to click the New button, the controls are different
		//commonPage.clickToSubmit("saveButton", "editButton");
		//NOTES TO OFFSHORE - The empty "field name to wait for" will cause error checking to be performed, but
		//  no waiting for a field to appear. 
		createPartnerOpportunityPage.clickToSubmit("selectButton", "");
		commonPage.waitForFieldPresent("editButton");
		
		//NOTES TO OFFSHORE - you are again saving the URL of an oppty 
     	//setUrl();
     	//setId();
     	
		//NOTES TO OFFSHORE - don't return something if it cannot be used by the caller
     	//return returnValue;
		
		Util.printInfo("Added Contacts to the opportunity '" + opportunityNumber + "'");
	}
	
	
	
	//NOTES TO OFFSHORE - 
	//Please see the changes I made in addContactsToOpportunity, and apply them to:
	//  addDistributorsToOpportunity
	//  addProductsToOpportunity
	//  registerDealAndConfirm
	
	//location of controls, waitForElement instead of sleeping, etc.
	
	
	
	//protected String addDistributorsToOpportunity() {
	protected void addDistributorsToOpportunity() {
//		String returnValue = "";
//		Util.printInfo("Adding Distributors to the opportunity '" + opportunityNumber + "'...");
		
		mainWindow.select();
		Util.sleep(2000);
		EISTestBase.switchDriverToFrame(5);
		viewPartnerOpportunityPage.click("addDistributorButton");				
		createPartnerOpportunityPage.waitForFieldPresent("searchText");
		mainWindow.select();

		createPartnerOpportunityPage.populateInstance("ADD_DISTS");
		
		createPartnerOpportunityPage.clickToSubmit("goButton" , "");
		
		distributorAccountCSN = createPartnerOpportunityPage.getConstant("DIST_ACCOUNT_CSN");	
		
		createPartnerOpportunityPage.populateInstance("ADD_DISTS_1");
		
		createPartnerOpportunityPage.clickToSubmit("saveButton", "editButton");
		
		//NOTES TO OFFSHORE - perhaps control returned too soon, or an error (uncaught) caused us to never get to
		//  the view oppty page
		Util.sleep(2000);
		EISTestBase.switchDriverToFrame(5);
			
		mainWindow.select();

		Util.printInfo("Added Distributors to the opportunity '" + opportunityNumber + "'");
	}

	//protected String addProductsToOpportunity() {
	protected void addProductsToOpportunity() {
	//	Util.printInfo("Adding Products to the opportunity '" + opportunityNumber + "'...");

		mainWindow.select();
		viewPartnerOpportunityPage.click("addProductButton");
		Util.sleep(5000);
		if(createPartnerOpportunityPage.isAlertPresent())
		{
		createPartnerOpportunityPage.dismissAlert();
		}
		createPartnerOpportunityPage.waitForFieldPresent("productLineSearchBox");
		
		createPartnerOpportunityPage.populateInstance("ADD_PRODUCTS");
		createPartnerOpportunityPage.clickToSubmit("saveButton", "editButton");

		//NOTES TO OFFSHORE - you are again saving the URL of an oppty 
     	//setUrl();
     	//setId();
		
		//NOTES TO OFFSHORE - why return the accountName?
		//return accountName;		

		Util.printInfo("Added Products to the opportunity '" + opportunityNumber + "'");
	}

	protected void addRequiredProductsToOpportunity() {


		mainWindow.select();
		viewPartnerOpportunityPage.clickToSubmit("addProductButton" , "");
		Util.sleep(5000);
		if(createPartnerOpportunityPage.isAlertPresent())
		{
			createPartnerOpportunityPage.dismissAlert();		
		}
		createPartnerOpportunityPage.waitForFieldPresent("productLineSearchBox");
		
		createPartnerOpportunityPage.populateInstance("ADD_PRODUCTS_SEARCH");
		createPartnerOpportunityPage.clickToSubmit("searchButton", "searchButton");
		
		createPartnerOpportunityPage.populateInstance("ADD_PRODUCTS");
		createPartnerOpportunityPage.clickToSubmit("saveButton", "editButton");

		//NOTES TO OFFSHORE - you are again saving the URL of an oppty 
     	//setUrl();
     	//setId();
		
		//NOTES TO OFFSHORE - why return the accountName?
		//return accountName;		

		Util.printInfo("Added Products to the opportunity '" + opportunityNumber + "'");
	}

	//protected String registerDealAndConfirm() {
	protected void registerDealAndConfirm() {
	//	Util.printInfo("Registering the deal for opportunity '" + opportunityNumber + "'...");
		
		mainWindow.select();
		//viewPartnerOpportunityPage.clickToSubmit("registerDealButton","confirmButton");
		EISTestBase.driver.findElement(By.xpath("//td[contains(@id,'topButtonRow')]/input[contains(@value,'Register Deal')]")).click();
		if(viewPartnerOpportunityPage.isAlertPresent()){
			viewPartnerOpportunityPage.acceptAlert();
		}
		viewPartnerOpportunityPage.waitForFieldPresent("confirmButton");
		//Note that we are passing an override timeout value
		viewPartnerOpportunityPage.clickToSubmit("confirmButton", "addProductButton", EpartnerConstants.DEFAULT_OPPTY_SHARE_TIMEOUT);		

		//NOTES TO OFFSHORE - why return the oppty name?
		//return getName();		

		Util.printInfo("Registered the deal for opportunity '" + opportunityNumber + "'");
	}
	
	protected void waitForOpptyShare() {
		final int interval = 2000;
		
		String partnerOpportunityNumber = getOpportunityNumber();
		
		int timeout = EpartnerConstants.DEFAULT_OPPTY_SHARE_TIMEOUT;
		boolean opened = false;
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeout;
		
		Util.printInfo("Waiting for opportunity to be shared...");

		mainWindow.select();
		
		//TODO  Most (if not all of these elements should not be defined in Page_Common.  They should be in
		//  Page_CommonPortal instead!

		//Do a search on nothing, just to get the searchAgainBox box to appear
		commonPage.click("goButtoninSidebar");	
		
		//We only need to do this once, because the search box stays populated after every search (I BELIEVE!!!)
		commonPage.populateField("searchBox", partnerOpportunityNumber);
		
		while (System.currentTimeMillis() < endTime) {
			//We could call the clickAndWait(String fieldName, String fieldNameToWaitFor) version, and then add a
			//  Util.sleep(interval) call outside of the if block, but this is more succinct
//			if (commonPage. clickAndWait("searchButton", "editButton", interval)) {
//				opened = true;
//				Util.printInfo("Opportunity number " + partnerOpportunityNumber + " is visible to distributor");
//				break;
//			}
			commonPage.click("searchButton");
			Util.sleep(5000);
			if (commonPage.isFieldPresent("editButton")){
				opened = true;
				Util.printInfo("Opportunity number " + partnerOpportunityNumber + " is visible to distributor");
				break;				
			}else{
				Util.sleep(interval);
			}
			
		}
			
		if (!opened) {
			fail("Opportunity number " + partnerOpportunityNumber + " did not become visible to distributor after waiting " + (timeout / 1000) + " seconds");
		}		
		
		Util.printInfo("Opportunity was successfully shared");
	}
	protected void waitForAccountCSN() {
		final int interval = 2000;		
			
		int timeout = EpartnerConstants.DEFAULT_OPPTY_SHARE_TIMEOUT;
		boolean opened = false;
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeout;
		
		Util.printInfo("Waiting for Account CSN to be displayed...");

		
		while (System.currentTimeMillis() < endTime) {
			viewAccountDetailsPage.refresh(interval);
			accountCSN = viewAccountDetailsPage.getValueFromGUI("accountCSN");
			if (!accountCSN.isEmpty()) {
				opened = true;
				Util.printInfo("Account CSN " + accountCSN + " is displayed");
				break;
			}
		}
			
		if (!opened) {
			fail("Account CSN " + accountCSN + "  did not become visible after waiting " + (timeout / 1000) + " seconds");
		}		
		
	}
	
	public boolean searchOppty(String opptyNumber){
    	mainWindow.select();
    	
    	commonPage.populateField("searchBox", opptyNumber);
    	//commonPage.clickToSubmit("searchButton");
    	commonPage.clickToSubmit("searchButton", "searchAgainButton");
    	if(commonPage.isFieldPresent("searchAllButton")){
    	commonPage.clickToSubmit("searchAllButton", "searchAgainButton");
		}
    	return(commonPage.isFieldPresent("noMatchesFoundMsg"));

	}
	public boolean verifyAccountsInPartnerCenter(long timeToWaitMillis) throws InterruptedException {
		boolean isAccountButtonPresent = false;
		Page_ createPartnerOpportunityPage = getCreatePartnerOpportunityPage();
		WebDriver driver=EISTestBase.driver;
		//switch to frame
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='pbBody']//table//iframe")));
		
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeToWaitMillis;
		
		//keep refreshing until required for 5 minutes
		while (System.currentTimeMillis() < endTime) {
			if (createPartnerOpportunityPage.isFieldPresent("matchAccountText")) {
				if (createPartnerOpportunityPage.isFieldContains("matchAccountText", "Resubmit")) {
					driver.switchTo().defaultContent();
					createPartnerOpportunityPage.clickAndWait("resubmitButton","editButton");
				}
				synchronized (driver) {driver.wait(10000);}
				createPartnerOpportunityPage.refresh(5000);
				driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='pbBody']//table//iframe")));
			}
			else {
				break;
			}
		} 
		Util.printDebug("Actual Wait for Verification: " + String.valueOf(System.currentTimeMillis() - startTime));
		
		if(createPartnerOpportunityPage.isFieldPresent("clickToCreateAccount")) {
			createPartnerOpportunityPage.clickAndWait("clickToCreateAccount", "accountCSN");
			isAccountButtonPresent = true;
		}  
		else if (createPartnerOpportunityPage.isFieldPresent("yesToAccept")) {
			createPartnerOpportunityPage.clickAndWait("yesToAccept", "accountCSN");
			isAccountButtonPresent = true;
		}
		driver.switchTo().defaultContent();
		return EISTestBase.assertFieldVisibility(createPartnerOpportunityPage.getName(), "accountVerificationButton", isAccountButtonPresent, true);
		
	}

	public Page_ getCreateEndCustomerContractPage() {
		return createEndCustomerContractPage;
	}

	public void setCreateEndCustomerContractPage(
			Page_ createEndCustomerContractPage) {
		this.createEndCustomerContractPage = createEndCustomerContractPage;
	}
	
	/**
	 * @return the approveRejectByGmailPage
	 */
	public Page_ getApproveRejectByGmailPage() 
	{
		return approveRejectByGmailPage;
	}
	
	/**
	 * @set the approveRejectByGmailPage
	 */
	public void setApproveRejectByGmailPage (Page_ approveRejectByGmailPage) 
	{
		this.approveRejectByGmailPage = approveRejectByGmailPage;
	}
	 public int getUniqueId() {  
		 int value = (int)(Math.random() * 8847);
		return value;
	}  
	 

		public static String getRandomString(int length) throws Exception {

			StringBuffer buffer = new StringBuffer();
			String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			int charactersLength = characters.length();

			for (int i = 0; i < length; i++) {
				double index = Math.random() * charactersLength;
				buffer.append(characters.charAt((int) index));
			}
			return buffer.toString();
		}

		
}
