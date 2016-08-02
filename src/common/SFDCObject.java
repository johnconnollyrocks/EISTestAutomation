package common;

import common.Page_;

/**
 * Describes features and functionality common to all SFDC objects.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public abstract class SFDCObject {
	//State variables
	//  Those that are common to all SFDC objects are defined here
	//  SFDC object-specific state variables are declared in SDFC object-specific classes
	//  All state variables are set directly by test code
	private String name			= "";	//The SFDC object name
	private String id			= "";	//The SFDC object id
	private String url 			= "";	//The full reference to the SFDC object
	private String portalUrl 	= "";	//The full reference to the SFDC object in a portal
	private String type 		= "";
	private String owner		= "";
	private String createdBy	= "";

	protected Window_ mainWindow = null;
	protected Page_ commonPage = null;

    //Contains the SFDC object-specific prefix.  Used when generating object names
	private final String sfdcObjectPrefix;
	
	//Implementing classes should set this to the appropriate SFDC object type (e.g., "account", "oppty")
	//  It is used primarily in messages
	private final String sfdcObjectType;

	//Used when generating object names.  We save it here in case other SFDC objects
	//  want to use it as part of an object name (e.g., accounts and orgs)
	private String objectTimestamp = "";

	public SFDCObject(String sfdcObjectPrefix, String sfdcObjectType) {
		this.sfdcObjectPrefix = sfdcObjectPrefix;
		this.sfdcObjectType = sfdcObjectType;
		
		mainWindow = EISTestBase.mainWindow;
		commonPage = EISTestBase.commonPage;
	}

	public SFDCObject(String sfdcObjectType) {
		this("", sfdcObjectType);
	}

	public SFDCObject() {
		this("", EISConstants.OBJECT_TYPE_SFDC_OBJECT);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}
	
	public final void setId() {
		String id = "";
		
		if (!url.isEmpty()) {
			id = Util.getSfdcObjectId(url);
		}

		setId(id);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl() {
		setUrl(commonPage.getLocation());
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	public String getPortalUrl() {
		return portalUrl;
	}

	public void setPortalUrl() {
		setPortalUrl(commonPage.getLocation());
	}

	public final void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getSfdcObjectPrefix() {
		return sfdcObjectPrefix;
	}

	public String getSfdcObjectType() {
		return sfdcObjectType;
	}

	public String getObjectTimestamp() {
		return objectTimestamp;
	}

	/**
	 * Gets the common Page object.
	 * @return The common (application-independent) Page object that defines objects
	 * not specific to any one page in SFDC applications
	 */
	public Page_ getCommonPage() {
		return commonPage;
	}

	@Override
	public String toString() {
		return "SFDCObject [sfdcObjectPrefix=" + sfdcObjectPrefix
				+ ", sfdcObjectType=" + sfdcObjectType
				+ ", name=" + name
				+ ", id=" + id
				+ ", url=" + url
				+ ", portalUrl=" + portalUrl
				+ ", type=" + type
				+ ", owner=" + owner
				+ ", createdBy=" + createdBy + "]";
	}

	protected final String generateObjectName(Page_ page, String fieldName, boolean force) {
		//Generate a field value, if necessary (this will usually be the name of an SFDC object)
		//  Whether or not a new value is generated, return the value of the field; caller may
		//  use it to set the SFDCObject name
		String value = page.getTestDataValue(fieldName);

		if ((value.isEmpty()) || (force)) {
			//Save the value of the timestamp, in case other SFDC objects need it
			//  (e.g., org signup creates values using the timestamp that was used
			//  to create the name of the associated account)
			objectTimestamp = Util.getTimestamp();
			value = sfdcObjectPrefix + objectTimestamp;

			page.setTestDataValue(fieldName, value);			
		}

		return value;
	}

	protected final String generateObjectName(Page_ page, String fieldName) {
		return generateObjectName(page, fieldName, false);
	}
	
/*	protected void open() {
		//To save time, open the object only if it is not already open
		if (!url.equals(gui.getLocation())) {
			gui.open(url);
		}
	}
*/
	
	protected boolean open(boolean force) {
		boolean opened;
		//To save time, open the object only if:
		//  it is not already open
		//    or
		//  force == true
		if (force) {
			opened = true;
		} else {
			//opened = !url.equals(gui.getLocation());
			opened = !url.equals(commonPage.getLocation());
		}
		
		if (opened) {
			//gui.open(url);
			commonPage.open(url);
		} else {
			Util.printDebug("A request to open the " + sfdcObjectType + " '" + name + "' (" + url + ") was ignored because it was already open");
		}
		
		return opened;
	}
	
	protected boolean open() {
		return open(false);
	}
	
	protected boolean openInPortal(boolean force) {
		//It is assumed that the user is logged in to the portal!
		
		boolean opened;
		//To save time, open the object only if:
		//  it is not already open
		//    or
		//  force == true
		if (force) {
			opened = true;
		} else {
			//opened = !portalUrl.equals(gui.getLocation());
			opened = !portalUrl.equals(commonPage.getLocation());
		}
		
		if (opened) {
			//gui.open(url);
			commonPage.open(portalUrl);
		} else {
			Util.printDebug("A request to open the " + sfdcObjectType + " '" + name + "' (" + portalUrl + ") in a portal was ignored because it was already open");
		}
		
		return opened;
	}
	
	protected boolean openInPortal() {
		//It is assumed that the user is logged in to the portal!
		
		return openInPortal(false);
	}
	
	protected void openForEdit() {
		mainWindow.select();
		open();
		
		//commonPage.clickAndWait("editButton");
		commonPage.clickAndWait("editButton", "saveButton");
	}
	
/*	protected void waitForPageToSettle(String referenceElement) {
		int timeToWaitMillis	= EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT;
		int timeToWaitSeconds	= timeToWaitMillis / 1000;
		
		if (!EISTestBase.commonPage.waitForPageToSettle(referenceElement, timeToWaitMillis)) {
			Util.printDebug("The page did not 'settle' within " + timeToWaitSeconds  + " seconds when loading the " + getSfdcObjectType() + " '" + getName() + "' Page at '" + url + "'; will wait " + timeToWaitSeconds + " more seconds");

			if (!EISTestBase.commonPage.waitForPageToSettle(referenceElement, timeToWaitMillis)) {
				fail("The page did not 'settle' within " + (timeToWaitSeconds * 2) + " seconds when loading the " + getSfdcObjectType() + " '" + getName() + "' Page at '" + getUrl() + "'");
			}
		}
		
		//Whether or not referenceElement was found, sleep a bit ("just to be sure...")
		Util.sleep(1500);
	}
*/
	
	protected boolean waitForPageToSettle(String referenceElement, boolean waitForPresent) {
		int timeToWaitMillis	= EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT;
		int timeToWaitSeconds	= timeToWaitMillis / 1000;
		boolean success;
		
		success = commonPage.waitForPageToSettle(referenceElement, waitForPresent, timeToWaitMillis);
		if (!success) {
			Util.printDebug("The page did not 'settle' within " + timeToWaitSeconds  + " seconds when loading the " + getSfdcObjectType() + " '" + getName() + "' Page at '" + url + "'; will wait " + timeToWaitSeconds + " more seconds");

			success = commonPage.waitForPageToSettle(referenceElement, waitForPresent, timeToWaitMillis);
			if (!success) {
				fail("The page did not 'settle' within " + (timeToWaitSeconds * 2) + " seconds when loading the " + getSfdcObjectType() + " '" + getName() + "' Page at '" + getUrl() + "'");
			}
		}
		
		//Whether or not page settled, sleep a bit ("just to be sure...")
		Util.sleep(500);
		
		return success;
	}
    
	protected boolean waitForPageToSettle() {
		//This is the locator of the "Loading..." text (next to a spinning image) that is displayed while
		//  the related lists are being populated as the page loads.  It *should* work on all SFDC pages...
		String referenceElement = EISConstants.WAIT_FOR_WINDOW_ELEMENT_XPATH.replace(EISConstants.WAIT_FOR_WINDOW_ID_TOKEN, getId());

		return waitForPageToSettle(referenceElement, false);
	}
        
    protected void fail(String message) {
    	EISTestBase.fail(message);
    }
    
    protected void failTest(String message) {
    	EISTestBase.failTest(message);
    }
}
