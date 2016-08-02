package common;

//TODO This class needs to be updated to suit Autodesk contacts

//TODO This class will needs to be enhanced  to support creating contacts from the SFDC UI

/**
 * Representation of an application-independent SFDC Contact.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class Contact extends SFDCObject {
	//State variables 
	//  Some (like name, id, and url) are defined in the superclass
	//  Define only object-specific ones here
	private String firstName = "";
	private String lastName = "";
	private String fullName = "";
	private String email = "";
	private String phone = "";
	private String fax = "";
	private String contactTitle = "";

	//Make all Page objects private, but provide getters so that test code can have read
	//  access to them.  That will allow test code to access test data, metadata, constants,
	//  etc., through this SFDCObject instance.  That's important, because we don't want
	//  test code authors to declare Page objects when they don't need to; instead we want
	//  test code to call utilities wherever possible.  Those utilities typically create an
	//  SFDCObject, instantiate the Page objects it needs, and pass back the SFDCObject
	//  instance.  It is through that instance that test code can get access to this
	//  object's Page data
	
	private Page_ createContactRTPage;
	private Page_ createContactPage;
	private Page_ viewContactPage;
	
	public Contact(Page_ createContactRTPage, Page_ createContactPage, Page_ viewContactPage) {
		super(EISConstants.OBJECT_TYPE_CONTACT);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.createContactRTPage	= createContactRTPage;
		this.createContactPage		= createContactPage;
		this.viewContactPage		= viewContactPage;
		
		//The contact name is not entered when the lead is created.  We will set the name in create()
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	/**
	 * Gets the Page object used for creating the SFDC Contact.
	 * @return The Page object that defines objects	used when creating the SFDC Contact
	 */
	Page_ getCreateContactPage() {
		return createContactPage;
	}

	/**
	 * Gets the Page object used for viewing the SFDC Contact.
	 * @return The Page object that defines objects	used when viewing the SFDC Contact
	 */
	Page_ getViewContactPage() {
		return viewContactPage;
	}
	
	@Override
	public String toString() {
		return "Contact [super=" + super.toString() + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", fax=" + fax
				+ ", contactTitle=" + contactTitle + "]";
	}

	@Override
	public boolean open() {
		return true;
	}
	
	@Override
	public void openForEdit() {}

	public String create() {
     	Util.printInfo("Creating contact...");
     	
		mainWindow.select();
		
		//commonPage.clickAndWait("tabContacts");
		commonPage.clickAndWait("tabContacts", "newButton");
		
		//Ensure that we wait for the page to load, so that we can make a valid check for the presence
		//  of the recordType field
		//commonPage.clickAndWait("newButton");
		commonPage.clickToSubmit("newButton");

     	if (createContactRTPage.isFieldPresent("recordType")) {
     		createContactRTPage.populate();
     		
     		//createContactRTPage.clickAndWait("continueButton");
     		createContactRTPage.click("continueButton");
     		
     		//The problem with this is that it waits for privacyStatement, which never goes away
     		//commonPage.waitForPageToSettle();
     		commonPage.waitForFieldVisible("saveButton");
     	}

     	createContactPage.populate();
     	
     	commonPage.clickToSubmit("saveButton");

     	setUrl();
     	setId();
     	
     	setOwner(EISTestBase.getCurrentUserName());

     	Util.printInfo("Created contact '" + getName() + "' (" + getUrl() + ")");
     	
     	return getName();
	}
}
