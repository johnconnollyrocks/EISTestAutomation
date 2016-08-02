package lm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lm.LMConstants.CreateFrom;
import common.Contact;
import common.Oppty;
import common.Page_;
import common.EISConstants;
import common.EISTestBase;
import common.SFDCObject;
import common.Account;
import common.TestProperties;
import common.Util;


/**
 * Representation of a Lead Management SFDC Lead.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class Lead extends SFDCObject {
	//State variables 
	//  Some (like name, id, and url) are defined in the superclass
	//  Define only object-specific ones here
	private String product;
	private String leadID;
	private String eMail;
	private Account account = null;
	private Contact contact = null;
	private Oppty oppty = null;
	private String emailId = null;
	

	//Make all Page objects private, but provide getters so that test code can have read
	//  access to them.  That will allow test code to access test data, metadata, constants,
	//  etc., through this SFDCObject instance.  That's important, because we don't want
	//  test code authors to declare Page objects when they don't need to; instead we want
	//  test code to call utilities wherever possible.  Those utilities typically create an
	//  SFDCObject, instantiate the Page objects it needs, and pass back the SFDCObject
	//  instance.  It is through that instance that test code can get access to this
	//  object's Page data

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	private Page_ createLeadRTPage;
	private Page_ createLeadPage;
	private Page_ viewLeadPage;
	private Page_ commonPortalPage;
	private Page_ viewLeadInPortalPage;
	private Page_ portalLandingPage;

	/**
	 * Class constructor specifying the Page objects necessary for interacting with an SFDC Lead.
	 * @param createLeadRTPage the Page object used for specifying the record type when creating the SFDC Lead
	 * @param createLeadPage the Page object used for creating the SFDC Lead
	 * @param viewLeadPage the Page object used for viewing the SFDC Lead
	 */
/*	Lead(Page_ createLeadRTPage, Page_ createLeadPage, Page_ viewLeadPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD, EISConstants.OBJECT_TYPE_LEAD);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.createLeadRTPage	= createLeadRTPage;
		this.createLeadPage		= createLeadPage;
		this.viewLeadPage		= viewLeadPage;
		
		//The lead name is not entered when the lead is created.  We will set the name in create()
	}
*/
	
	Lead(Page_ createLeadRTPage, Page_ createLeadPage, Page_ viewLeadPage, Page_ viewLeadInPortalPage, Page_ portalLandingPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD, EISConstants.OBJECT_TYPE_LEAD);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.createLeadRTPage		= createLeadRTPage;
		this.createLeadPage			= createLeadPage;
		this.viewLeadPage			= viewLeadPage;
		this.viewLeadInPortalPage 	= viewLeadInPortalPage;
		this.portalLandingPage 		= portalLandingPage;
		
		//The name may be overwritten, depending on how the lead is created
		setName(generateObjectName(this.createLeadPage, EISConstants.OBJECT_NAME_FIELD_NAME_LEAD));
	}
	
	Lead(Page_ createLeadPage, Page_ viewLeadInPortalPage, Page_ portalLandingPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD, EISConstants.OBJECT_TYPE_LEAD);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;
		
		this.createLeadPage			= createLeadPage;
		this.viewLeadInPortalPage	= viewLeadInPortalPage;	
		this.portalLandingPage 		= portalLandingPage;
		
		//The lead name is not entered when the lead is created.  We will set the name in create()
	}
	Lead(Page_ createLeadPage, Page_ viewLeadPage, Page_ viewLeadInPortalPage, Page_ portalLandingPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD, EISConstants.OBJECT_TYPE_LEAD);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;
		this.createLeadPage			= createLeadPage;
		this.viewLeadPage			= viewLeadPage;
		this.viewLeadInPortalPage 	= viewLeadInPortalPage;
		this.portalLandingPage 		= portalLandingPage;
		
		//The name may be overwritten, depending on how the lead is created
				setName(generateObjectName(this.createLeadPage, EISConstants.OBJECT_NAME_FIELD_NAME_LEAD));
	}
	

	/**
	 * Gets the product.
	 * @return The product associated with the lead
	 */
	String getProduct() {
		return product;
	}
	
	/**
	 * @return the leadID
	 */
	public String getLeadID() {
		return leadID;
	}
	
	/**
	 * Gets the Account object.
	 * @return The Account object associated with the lead
	 */
	Account getAccount() {
		return account;
	}

	/**
	 * Gets the Account name.
	 * @return The name of the Account object associated with the lead
	 */
	String getAccountName() {
		return account.getName();
	}

	/**
	 * @return the commonPortalPage
	 */
	public Page_ getCommonPortalPage() {
		return commonPortalPage;
	}

	/**
	 * Gets the Contact object.
	 * @return The Contact object associated with the lead
	 */
	Contact getContact() {
		return contact;
	}

	/**
	 * Gets the Contact name.
	 * @return The name of the Contact object associated with the lead
	 */
	String getContactName() {
		return contact.getName();
	}

	/**
	 * @return the viewLeadInPortalPage
	 */
	public Page_ getViewLeadInPortalPage() {
		return viewLeadInPortalPage;
	}

	/**
	 * Gets the Oppty object.
	 * @return The Oppty object associated with the lead
	 */
	Oppty getOppty() {
		return oppty;
	}

	/**
	 * Gets the Oppty name.
	 * @return The name of the Oppty object associated with the lead
	 */
	String getOpptyName() {
		return oppty.getName();
	}

	/**
	 * Gets the Page object used for creating the SFDC Lead.
	 * @return The Page object that defines objects	used when creating the SFDC Lead
	 */
	Page_ getCreateLeadPage() {
		return createLeadPage;
	}

	/**
	 * Gets the Page object used for viewing the SFDC Lead.
	 * @return The Page object that defines objects	used when viewing the SFDC Lead
	 */
	Page_ getViewLeadPage() {
		return viewLeadPage;
	}

	@Override
	public String toString() {
		return "Lead [super=" + super.toString() +
				", product=" + product +
				", leadID=" + leadID +
				", accountName=" + getAccountName() +
				", contactName=" + getContactName() +
				", opptyName=" + getOpptyName() + 
				"]";
	}

	/**
	 * Opens the lead, if it is not already open.
	 * @return An indication of whether the lead was actually opened
	 */
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
	
	/**
	 * Opens the lead in the portal, if it is not already open.
	 * @return An indication of whether the lead was actually opened
	 */
	@Override
	protected boolean openInPortal() {
		//It is assumed that the user is logged in to the portal!
		
		boolean pageLoaded;
		
		mainWindow.select();
		
		pageLoaded = super.openInPortal();
		
		//TODO We can't do this, because we are not using commonPage.  We'll need to handle this situation.
		//if (pageLoaded) {
		//	waitForPageToSettle();
		//}
		
		return pageLoaded;
	}
	
	/**
	 * Creates an SFDC Lead.
	 * @return The name of the lead
	 */
	String create(CreateFrom createFrom) {
		String leadName = "";
		
		
		//NOTES TO OFFSHORE - Use switch statements with enumerated types where possible, and always
		//  fail test if an enum instance is not covered in the case clauses
		switch (createFrom) {
			case PORTAL:	{
				leadName = createInPortal();
				break;
			}
			case SFDC:	{
				leadName = createFromSFDC();
				break;
			}
			case BATCH_UPLOAD: {
				createFromFile();
				break;
			}
			case PLOCATOR: {
				createFromWebForm();
				break;
			}
			default:	{
				EISTestBase.fail("Unhandled member of lm.LMConstants.CreateFrom enumerated type: " + createFrom);
			}
		}
		
		return leadName;
	}
	
	

	private String createFromSFDC() {
		String leadName;
		String leadOwner;
		
     	Util.printInfo("Creating lead from SFDC...");

		mainWindow.select();
		
		//commonPage.clickAndWait("tabLeads");
		commonPage.clickAndWait("tabLeads", "newButton");
		
		//Ensure that we wait for the page to load, so that we can make a valid check for the presence
		//  of the recordType field
		//commonPage.clickAndWait("newButton");
		commonPage.clickToSubmit("newButton");

     	if (createLeadRTPage.isFieldPresent("recordType")) {
     		createLeadRTPage.populate();
     		
     		//createLeadRTPage.clickAndWait("continueButton");
     		createLeadRTPage.click("continueButton");
     		
     		//The problem with this is that it waits for privacyStatement, which never goes away
     		//commonPage.waitForPageToSettle();
     		commonPage.waitForFieldVisible("saveButton");
     	}

     	createLeadPage.populate();

		//commonPage.clickToSubmit("saveButton");
		commonPage.clickToSubmit("saveButton", "editButton");

     	setUrl();
     	setId();
     	
     	leadName = createLeadPage.getTestDataValue("contact");
     	if (leadName.isEmpty()) {
         	leadName = createLeadPage.getTestDataValue("contactText");
     	}
     	
     	setName(leadName);
     	
     	//The owner will  be the logged-in user only if the lead is not routed.  Otherwise
     	//  we will use the value defined in the OWNER constant in test properties
     	//setOwner(EISTestBase.getCurrentUserName());
     	leadOwner = createLeadPage.getConstant("OWNER");
     	if (leadOwner.isEmpty()) {
     		leadOwner = EISTestBase.getCurrentUserName();
     	}
     	
     	setOwner(leadOwner);
     	
     	product = createLeadPage.getTestDataValue("product");
     	
//     	leadID = viewLeadPage.getValueFromGUI("leadId");
     	
     	Util.printInfo("Created lead '" + getName() + "' (" + getUrl() + ")");
     	
     	return getName();
	}
	
	private String createInPortal() {
		String leadName;
		
     	Util.printInfo("Creating lead in portal...");

		mainWindow.select();
		
		//createLeadPage.click("leadManagementPilotLink");
		portalLandingPage.click("leadManagementPilotLink");
		commonPage.waitForFieldPresent("newButton");
		
		commonPage.click("newButton");
		
     	createLeadPage.populate();

		//commonPage.clickToSubmit("saveButton");
		commonPage.clickToSubmit("saveButton", "editButton");

 		//NOTES TO OFFSHORE - 08/03/2012
		//  We are in the portal, so we need to store the current URL as
		//  the object's portal URL, not its SFDC URL
     	//setUrl();
		setPortalUrl();
		
		//Normally we would set the SFDC (non-portal) URL here, using string replacement.
		//  However, the URLs in this portal and SFDC are so different that we can't do
		//  it the way we have been.  We may need to come up with another approach...
     	//setUrl();
 
		setId();
     	
     	leadName = createLeadPage.getTestDataValue("contact");
     	if (leadName.isEmpty()) {
         	leadName = createLeadPage.getTestDataValue("contactText");
     	}
     	
     	setName(leadName);
     	
     	setOwner(createLeadPage.getConstant("OWNER"));
     	
     	product = createLeadPage.getTestDataValue("productText");
     	
 		//NOTES TO OFFSHORE - 08/03/2012
     	//Util.printInfo("Created lead '" + getName() + "' (" + getUrl() + ")");
     	Util.printInfo("Created lead '" + getName() + "' (" + getPortalUrl() + ") in portal");
     	
     	return getName();
	}
	private void createFromFile() {
		commonPage.click("tabAllTabs");
		createLeadPage.click("workBench");
		EISTestBase.switchDriverToFrame(0);
		createLeadPage.waitForFieldPresent("data");
		createLeadPage.click("data");
		createLeadPage.click("insert");
		createLeadPage.populateField("objectType", "Lead");
		// assuming driver is a healthy WebDriver instance
		WebElement fileIn = EISTestBase.driver.findElement(By.xpath("//input[@type='file']"));
		EISTestBase.driver.switchTo().activeElement();
		//fileInput.sendKeys("//ecs-9844/DoNotDelete/JenkinsData/Automation_Leads.csv");
		fileIn.sendKeys("C://Users/Administrator/Desktop/Automation_Leads.csv");
		//EISTestBase.switchDriverToFrame(0);
		//createLeadPage.click("fileInput");
		//createLeadPage.click("next");
		EISTestBase.driver.findElement(By.xpath("//input[normalize-space(@value)='Next']"));
		EISTestBase.driver.switchTo().activeElement();
		Util.sleep(2000);
	//	createLeadPage.populateField("fileInput", "//ecs-9844/DoNotDelete/JenkinsData/Automation_Leads.csv");
		Util.sleep(2000);
		
	}
	
	String createFromWebForm() {
		WebDriver driver=EISTestBase.driver;
		if(!EISTestBase.testProperties.constantExists("DEV_URL")){
		driver.switchTo().frame(0);
		}
		createLeadPage.populateInstance("SEARCH");
		createLeadPage.clickAndWait("searchButton","haveAPartnerContactMe");
		createLeadPage.click("haveAPartnerContactMe");
		mainWindow.setLocator(EISTestBase.driver.getWindowHandle());
		String mainWindowLocator = mainWindow.getLocator();
		for(String winHandle : ((WebDriver) driver).getWindowHandles()){
		    ((WebDriver) driver).switchTo().window(winHandle);    
		}	
		createLeadPage.populateField("leadName");
		String leadName=createLeadPage.getValueFromGUI("leadName");
		emailId=leadName.concat("@ssttest.net");
		createLeadPage.setTestDataInstanceValue("eMail", emailId,"CREATE_LEAD");
		Util.printInfo("New Lead Email ID is :"+emailId);
		viewLeadPage.setVerificationDataValue("name", leadName);
		viewLeadPage.setVerificationDataValue("eMail", emailId.toLowerCase());
		createLeadPage.verifyInstance("DEFAULT_CHECK");
		createLeadPage.populateInstance("CREATE_LEAD");
		createLeadPage.clickToSubmit("save","close");
		mainWindow.select();
//		createLeadPage.closeAllPopUps(mainWindowLocator);
		return emailId;
		
	}
	/**
	 * Converts an SFDC Lead to an SFDC Opportunity.
	 * @param oppty an Oppty object
	 * @return An Oppty object representing an SFDC Opportunity
	 */
	//NOTE THAT ONLY ADMINS can convert opptys in the SFDC interface!!
	//Oppty convertToOppty(Oppty oppty) {
	String convertToOppty(Oppty oppty) {
     	Util.printInfo("Converting lead to oppty...");

     	mainWindow.select();   	
		
		open();
		
		viewLeadPage.click("convertToOpportunity");
		viewLeadPage.waitForFieldVisible("yes");
		
		viewLeadPage.verifyFieldExists("areYouSureText");

		viewLeadPage.clickToSubmit("yes");
		
		//Can't wait for the default privacyStatement field, because it never goes away
     	commonPage.waitForFieldVisible("editButton", EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT);
     	
		oppty.setUrl();
     	oppty.setId();
     	
     	oppty.setName(oppty.getViewOpptyPage().getValueFromGUI("opptyName"));
     	oppty.setOwner(getOwner());
     	
     	Util.printInfo("Converted lead '" + getName() + "' (" + getUrl() + ") to oppty '" + oppty.getName() + "' (" + oppty.getUrl() + ")");
     	
     	//return oppty;
     	return oppty.getName();
	}
	
	//NOTES TO OFFSHORE - 08/03/2012
	//  Changed to make it consistent with convertToOppty(Oppty oppty)
	//String convertToOpptyInPortal() {
	String convertToOpptyInPortal(Oppty oppty) {
     	Util.printInfo("Converting lead to oppty in portal...");
		
		mainWindow.select();
		
		//NOTES TO OFFSHORE - 08/03/2012
		//  When possible, do not assume that an object has been opened before accessing it.
		openInPortal();
		
		viewLeadInPortalPage.click("convertToOpportunity");
		viewLeadInPortalPage.waitForFieldVisible("yes");
		
		viewLeadInPortalPage.verifyFieldExists("areYouSureText");
		viewLeadInPortalPage.click("yes");
		
		//Can't wait for the default privacyStatement field, because it never goes away
     	commonPage.waitForFieldVisible("editButton", EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT);
     	
 		//NOTES TO OFFSHORE - 08/03/2012
     	//  don't call setUrl, setID, setName, etc. here!  Those calls are setters
     	//  for this class - Lead - and were called in the create() methods.  But at this line of
     	//  code we are looking at an Oppty, not a Lead.  So everything we do from here on I for
     	//  an Oppty, not a Lead, and using oppty.getViewOpptyInPortalPage(), not viewLeadInPortalPage.
     	//  I made changes here to echo what we do above in convertToOppty(Oppty oppty)

 		//NOTES TO OFFSHORE - 08/03/2012
     	//setUrl();
     	//setId();
		oppty.setPortalUrl();

		//Normally we would set the SFDC (non-portal) URL here, using string replacement.
		//  However, the URLs in this portal and SFDC are so different that we can't do
		//  it the way we have been.  We may need to come up with another approach...
     	//setUrl();

		oppty.setId();
     	
 		//NOTES TO OFFSHORE - 08/03/2012
     	//setName(viewLeadInPortalPage.getValueFromGUI("opptyName"));
     	//String opptyNumber = viewLeadInPortalPage.getValueFromGUI("opptyNumber");
     	oppty.setName(oppty.getViewOpptyInPortalPage().getValueFromGUI("opptyName"));
     	
 		//NOTES TO OFFSHORE - 08/03/2012
     	//setOwner(getOwner());
     	oppty.setOwner(getOwner());
     	
 		//NOTES TO OFFSHORE - 08/03/2012
     	//Util.printInfo("Converted lead to oppty. Opportunity Name: '" + getName() + "' Opportunity Number: '" + opptyNumber  + "' (" + getUrl() + ") '");
     	Util.printInfo("Converted lead '" + getName() + "' (" + getPortalUrl() + ") to oppty '" + oppty.getName() + "' (" + oppty.getPortalUrl() + ") in portal");

 		//NOTES TO OFFSHORE - 08/03/2012
     	//return getOwner();
     	return oppty.getName();
	}

	protected void waitForRouting() {
		final int interval = 2000;
		
		String leadIDNumber = getLeadID();
		
		int timeout = LMConstants.DEFAULT_LEAD_SHARE_TIMEOUT;
		boolean opened = false;
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeout;
		
		Util.printInfo("Waiting for Lead to be routed...");

		mainWindow.select();
		
		//commonPage.clickAndWait("leadManagementPilotLink","goButtoninSidebar");
		portalLandingPage.click("leadManagementPilotLink");
		commonPage.waitForFieldPresent("goButtoninSidebar");
		
		//TODO  Most (if not all of these elements should not be defined in Page_Common.  They should be in
		//  Page_CommonPortal instead!

		//Do a search on nothing, just to get the searchAgainBox box to appear
		commonPage.clickAndWait("goButtoninSidebar" , "searchBox");	
		
		//We only need to do this once, because the search box stays populated after every search (I BELIEVE!!!)
		commonPage.populateField("searchBox", leadIDNumber);
		
		while (System.currentTimeMillis() < endTime) {
			//We could call the clickAndWait(String fieldName, String fieldNameToWaitFor) version, and then add a
			//  Util.sleep(interval) call outside of the if block, but this is more succinct
			if (commonPage.clickAndWait("searchButton", "acceptButton", interval)) {
				opened = true;
				Util.printInfo("Lead number " + leadIDNumber + " is available");
				break;
			}
		}
			
		if (!opened) {
			fail("Lead number " + leadIDNumber + " did not become available after waiting " + (timeout / 1000) + " seconds");
		}		
		
		Util.printInfo("Lead was successfully routed");
		
		commonPage.click("acceptButton");	
		viewLeadInPortalPage.waitForFieldVisible("convertToOpportunity");
		
		//NOTES TO OFFSHORE - 08/03/2012
		//When the lead was created in SFDC, the method called setUrl() to store its SFDC URL.  Now that we are looking
		//  at the lead in the portal, we should take this opportunity to save its portal URL
		setPortalUrl();
	}
}
