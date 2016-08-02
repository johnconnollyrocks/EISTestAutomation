package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import bornincloud.BornInCloudTestBase;
import common.exception.GUIException;
import common.exception.MetadataException;
import common.exception.TestDataException;
import customerportal.CustomerPortalTestBase;

/**
 * Representation of a page in an SFDC application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class Page implements Page_ {
	//Used when finding page-level metadata entries in the properties object
	private static final String PAGE_NAME				= "pageName";
	private static final String CONTAINER_WINDOW_NAME	= "containerWindowName";
	private static final String PAGE_MESSAGE_LOCATOR	= "pageMessageLocator";
	private static final String FIELD_DEPENDENCIES		= "fieldDependencies";

	//Page-level metadata, from the page properties file
	//   (be sure to initialize optional properties where appropriate, in case they are not specified in the properties file)
	private String name;															//required (no default)
	private String containerWindowName;												//required (no default)
	private List<String> messageLocators				= new ArrayList<String>();	//optional (defaults to empty)
	private List<String> fieldDependencies				= new ArrayList<String>();	//optional (defaults to empty)

	//Used for accessing the page properties file
	private String propertiesFilename;

	//Used to time page redraws (e.g., Ajax).  This delay is dependent more on the Window object than the Page objects in
	//  it, so it is specified in window metadata.  The Page constructor can be passed a value for this member; the
	//  default is EISTestBase.defaultPageRedrawDelay
	private int pageRedrawDelay;
	
	//Staging area for page properties (page-level and field-level metadata)
	//  (we can use a Properties object because duplicates are not allowed)
	private Properties pageProperties = new Properties();

	private final GUI gui;

	private final TestProperties testProperties;

	//Was used in doPopulate to simulate Selenium clickAndWait calls.  It has no use now, because "...AndWait" methods are deprecated
	//private int waitTime = 0;

	//Used in doPopulate in determining whether to make isAlertPresent() calls (see comments
	//  in doPopulateLookupField for details)
	//TODO  driver.switchTo().alert() really slows things down, so I am turning off alert checking
	private boolean checkForAlertsDefault = false;
	private boolean checkForAlerts = checkForAlertsDefault;

	//Used by page-scope verify methods to optimize verification of multiple elements
	//  in a single info panel
	private boolean usingInfoPanelCache = false;
	private Hashtable<String, List<List<String>>> infoPanelCache = new Hashtable<String, List<List<String>>>();
		
	//WebDriver does not differentiate between alerts and confirmations
	//private String confirmationMessage;
	private String alertMessage;
	
	//Test data (not verification data) lives here
	private List<FieldDataInstance_> testData = new ArrayList<FieldDataInstance_>();

	//Verification data (not test data) lives here
	private List<FieldDataInstance_> verificationData = new ArrayList<FieldDataInstance_>();

	//Constants live here
	private Properties constants = new Properties();

	private int numTestDataRecords = 0;
	private int numVerificationDataRecords = 0;
	private int numConstants = 0;

	//Will contain error info found by error checking methods
	private List<String> errors = new ArrayList<String>();

	//Will contain the names of fields that do not meet the conditions set by page-scope
	//  verification methods (e.g., verify(), verifyAll(), verifyAllExistence(), and others);
	//  see comments at those methods for information on the conditions.
	//Note that this list is cleared at entrance to any of those methods
	private List<String> unverifiedFieldNames = new ArrayList<String>();

	private List<Field> fieldMetadata = new ArrayList<Field>();

	private WebDriver driver = null;
//	private EventFiringWebDriver driver=null;
	
	

	public Page(WebDriver driver, TestProperties testProperties, String pagePropertiesFilename, int pageRedrawDelay ) {
		this.testProperties = testProperties;
		this.driver = driver;
//		this.event=event;
		propertiesFilename = pagePropertiesFilename;
		pageProperties = Util.loadPropertiesFile(propertiesFilename);

		gui = new GUI(driver);

		this.pageRedrawDelay = pageRedrawDelay;

		//Because this class is instantiated directly from the test code, and we want to shield the user from having
		//  to write too much Java just to catch errors that require failing the test, we are not propagating the
		//  exception handling up the call chain
		//
		//Note that we are calling EISTestBase.fail(message) instead of org.junit.Assert.fail(message),
      	//  as we may (but shouldn't!) be doing elsewhere.
		try {
			setPageMetadata();
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		try {
			createFieldMetadataObjects();
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		loadConstants();
		numConstants = constants.size();

		loadTestData();
		numTestDataRecords = testData.size();

		//We don't resolve constants in verification data; that's done on a "just in time" basis in verification methods
		resolveConstantsInTestData();

		loadVerificationData();
		numVerificationDataRecords = verificationData.size();

		//Sort on FieldDataInstance.instance
		Collections.sort(testData);

		if ((numTestDataRecords > 0) && (fieldDependencies.size()) > 0) {
			try {
				implementFieldDependencies();
			} catch (MetadataException me) {
				EISTestBase.fail(me.getMessage());
			}
		}
	}
	
		
	
	public Page(WebDriver driver, TestProperties testProperties, String pagePropertiesFilename) {
		this(driver, testProperties, pagePropertiesFilename, EISTestBase.getDefaultPageRedrawDelay());
	}
	

	public Page(WebDriver driver, String pagePropertiesFilename, int pageRedrawDelay) {
		//This constructor is typically called from EISTestBase.createStaticPage.  It is used for
		//  creating Page objects for which no test properties are required, such as pages that
		//  have no form fields, OR pages that do have form fields, but for which the user does
		//  not specify test data, such as a login page (login credentials are generally specified
		//  as properties or constants).
		testProperties = null;
		this.driver = driver;
//		this.event=event;
		propertiesFilename = pagePropertiesFilename;
		pageProperties = Util.loadPropertiesFile(propertiesFilename);

		gui = new GUI(driver);

		this.pageRedrawDelay = pageRedrawDelay;

		//Because this class is instantiated directly from the test code, and we want to shield the user from having
		//  to write too much Java just to catch errors that require failing the test, we are not propagating the
		//  exception handling up the call chain
		//
		//Note that we are calling EISTestBase.fail(message) instead of org.junit.Assert.fail(message),
      	//  as we may (but shouldn't!) be doing elsewhere.
		try {
			setPageMetadata();
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		try {
			createFieldMetadataObjects();
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		constants.clear();
		testData.clear();
		verificationData.clear();
	}

	public Page(WebDriver driver, String pagePropertiesFilename) {
		this(driver, pagePropertiesFilename, EISTestBase.getDefaultPageRedrawDelay());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getContainerWindowName() {
		return containerWindowName;
	}

	@Override
	public List<String> getMessageLocators() {
		return new ArrayList<String>(messageLocators);
	}

	@Override
	public List<String> getFieldLocators(String fieldName) {
		Field field = null;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		return field.getLocators();
	}
	
	public List<String> getFieldLocators(String fieldName,boolean ignoreCase) {
		Field field = null;
		
		try {
			field = getFieldMetadata(fieldName,ignoreCase);
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		return field.getLocators();
	}

	@Override
	public String getFirstFieldLocator(String fieldName) {
		return getFieldLocators(fieldName).get(0);
	}

	@Override
	public Map<String, List<String>> getAllFieldLocators() {
		Map<String, List<String>> fieldLocators = new HashMap<String, List<String>>();
		String fieldName;

		ListIterator<Field> itr = fieldMetadata.listIterator();
		while (itr.hasNext()) {
			fieldName = itr.next().getName();
			
			fieldLocators.put(fieldName, getFieldLocators(fieldName));
		}
		
		return fieldLocators;
	}
	
	@Override
	public void setFieldLocators(String fieldName, List<String> locators) {
		int index;
		
		try {
			index = getFieldMetadataRecordIndex(fieldName.trim());
			
			fieldMetadata.get(index).setLocators(locators);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while setting locators for the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
	}

	@Override
	/**
	 * Description: Replace the any contents with dynamic text
	 */
	public void setFieldLocators(String fieldName, String actualText,String replaceDynamicText) {
		int index;
		
		try {
			index = getFieldMetadataRecordIndex(fieldName.trim());
			List<String> origLocators=fieldMetadata.get(index).getLocators();
			for(int i=0;i<origLocators.size();i++){
								
				origLocators.get(i).replace("#{"+actualText+"}", replaceDynamicText);
			}
			fieldMetadata.get(index).setLocators(origLocators);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while setting locators for the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
	}

	/**
	 * Sets the locators associated with a collection of Field objects
	 * @param fieldLocators a map keyed on Field.fieldName, containing lists of Field locators
	 * @see #setFieldLocators(String fieldName, List locators)
	 */
	@Override
    public void setFieldLocators(Map<String, List<String>> fieldLocators) {
	    for (Entry<String, List<String>> entry : fieldLocators.entrySet()) {
	    	setFieldLocators(entry.getKey(), entry.getValue());
	    }
	}

	@Override
	public List<String> getFieldMessageLocators(String fieldName) {
		Field field = null;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		return field.getMessageLocators();
	}

	@Override
	public List<String> getFieldDependencies() {
		return new ArrayList<String>(fieldDependencies);
	}

	@Override
	public String getPropertiesFilename() {
		return propertiesFilename;
	}

	@Override
	public int getPageRedrawDelay() {
		return pageRedrawDelay;
	}

	@Override
	public void setPageRedrawDelay(int pageRedrawDelay) {
		this.pageRedrawDelay = pageRedrawDelay;
	}
	
	@Override
	public Properties getPageProperties() {
		//This deep copy is necessary (Properties objects are mutable)
		Properties newPageProperties = new Properties();
		Enumeration<?> keys;
		String key;

		keys = pageProperties.keys();
		while (keys.hasMoreElements()) {
			key = (String) keys.nextElement();
			newPageProperties.setProperty(key, pageProperties.getProperty(key));
		}

		return newPageProperties;
	}

/*	@Override
	//wait was used in doPopulate to simulate Selenium clickAndWait calls.  It has no use now, because "...AndWait" methods are deprecated
	public int getWaitTime() {
		return waitTime;
	}
*/

/*	@Override
	//wait was used in doPopulate to simulate Selenium clickAndWait calls.  It has no use now, because "...AndWait" methods are deprecated
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
*/

	@Override
	public boolean isCheckForAlerts() {
		return checkForAlerts;
	}

	@Override
	public void setCheckForAlerts(boolean checkForAlerts) {
		this.checkForAlerts = checkForAlerts;
	}

	@Override
	public void setCheckForAlerts() {
		checkForAlerts = checkForAlertsDefault;
	}

	@Override
	public boolean isCheckForAlertsDefault() {
		return checkForAlertsDefault;
	}

	@Override
	public void setCheckForAlertsDefault(boolean checkForAlertsDefault) {
		this.checkForAlertsDefault = checkForAlertsDefault;
	}

	//WebDriver does not differentiate between alerts and confirmations
/*	@Override
	public boolean isConfirmationMessagePresent() {
		return !(confirmationMessage.isEmpty());
	}
*/
	
	//WebDriver does not differentiate between alerts and confirmations
/*	@Override
	public String getConfirmationMessage() {
		return confirmationMessage;
	}
*/
	
	@Override
	public boolean isAlertMessagePresent() {
		return !(alertMessage.isEmpty());
	}

	@Override
	public String getAlertMessage() {
		return alertMessage;
	}

	@Override
	public int getNumTestDataRecords() {
		return numTestDataRecords;
	}

	@Override
	public int getNumVerificationDataRecords() {
		return numVerificationDataRecords;
	}

	@Override
	public int getNumConstants() {
		return numConstants;
	}

	@Override
	public List<String> getUnverifiedFieldNames() {
		return new ArrayList<String>(unverifiedFieldNames);
	}

	@Override
	public int getNumUnverifiedFieldNames() {
		return unverifiedFieldNames.size();
	}

	@Override
	public boolean areErrors() {
		return getNumErrors() > 0;
	}

	@Override
	public int getNumErrors() {
		return errors.size();
	}

	@Override
	public String getError(String fieldOrPageName) {
		String message = "";

		if (areErrors()) {
			for (String record : errors) {
				if (Util.containsToken(record, fieldOrPageName, EISConstants.PROPERTY_TYPE_DELIM, true)) {
					message = record;

					break;
				}
			}
		}

		return message;
	}

	@Override
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}

	@Override
	public void printErrors() {
		Util.listPrint(errors);
	}

	@Override
	public String getErrorMessage(String fieldOrPageName) {
		String message = "";

		if (areErrors()) {
			for (String record : errors) {
				if (Util.containsToken(record, fieldOrPageName, EISConstants.PROPERTY_TYPE_DELIM, true)) {
					message = record.split(EISConstants.PROPERTY_TYPE_DELIM)[1];

					break;
				}
			}
		}

		return message;
	}

	@Override
	public List<String> getErrorMessages() {
		List<String> errorMessages = new ArrayList<String>();

		if (areErrors()) {
			for (String record : errors) {
				errorMessages.add(record.split(EISConstants.PROPERTY_TYPE_DELIM)[1]);
			}
		}

		return errorMessages;
	}

	@Override
	public void printErrorMessages() {
		Util.listPrint(getErrorMessages());
	}

	@Override
	public Properties getConstants() {
		//This deep copy is necessary (Properties objects are mutable)
		Properties newConstantsProperties = new Properties();
		Enumeration<?> keys;
		String key;

		keys = constants.keys();
		while (keys.hasMoreElements()) {
			key = (String) keys.nextElement();
			newConstantsProperties.setProperty(key, constants.getProperty(key));
		}

		return newConstantsProperties;
	}

	@Override
	public String getConstant(String constantName) {
		String value = getPageScopeConstant(constantName);

		//If found in the page store, we're good; if not, check the test-scope store
		if (value.isEmpty()) {
			value = getTestScopeConstant(constantName);

			if (value.isEmpty()) {
				Util.printWarning("The constant '" + constantName.toUpperCase() + "' was not found in the constant store of Page '" + name + "', nor in the test-scope constant store; returning empty string");
			} else {
				Util.printDebug("The constant '" + constantName.toUpperCase() + "' was not found in the constant store of Page '" + name + "'; the test-scope constant was used instead");
			}
		}

		return value;
	}	

	private String getPageScopeConstant(String constantName) {
		return constants.getProperty(constantName.toUpperCase(), "");
	}

	private String getTestScopeConstant(String constantName) {
		return testProperties.getConstant(constantName);
	}

	@Override
	public boolean constantExists(String constantName) {
		boolean exists;

		exists = constants.containsKey(constantName.toUpperCase());
		if (!exists) {
			exists = testProperties.constantExists(constantName);
		}

		return exists;
	}

	@Override
	public String getMetadata() {
		return	"Page name:                     " + name + "\n" +
		"Message locators:              " + messageLocators + "\n" +
		"Parent window:                 " + containerWindowName + "\n" +
		"Field dependencies:            " + fieldDependencies;
	}

	@Override
	public String toString() {
		return	getMetadata() + "\n" +
		"Properties file:               " + propertiesFilename + "\n" +
		"Redraw delay:                  " + pageRedrawDelay + "\n" +
		"Num test data records:         " + numTestDataRecords + "\n" +
		"Num verification data records: " + numVerificationDataRecords + "\n" +
		"Num constants:                 " + numConstants;
	}

	@Override
	public List<FieldData_> getInstanceFieldData(int instance) {
		List<FieldData_> instanceFieldData = new ArrayList<FieldData_>();

		for (FieldDataInstance_ fieldDataInstance : testData) {
			if (fieldDataInstance.getInstance() == instance) {
				instanceFieldData.add((FieldData) fieldDataInstance.getFieldData().clone());
			}
		}
		
		return instanceFieldData;
	}

	@Override
	public List<FieldData_> getInstanceFieldData(String instanceName) {
		return getInstanceFieldData(testProperties.getInstanceNumber(instanceName));
	}
	
	@Override
	public boolean setTestDataInstanceValue(String fieldName, String value, int instance, boolean setOnlyIfBlank) {
		//This method allows the test to override one field of the page's test data with a custom value.
		//  Common uses:
		//    1) the generateTestDataValue() method (which is typically used for creating SFDC object names)
		//       calls it
		//    2) test code uses it to set a calculated value, for example, grabbing the account name
		//       from the account test data, and then setting an account name field in the oppty test data
		int index;
		String oldValue;
		boolean setIt;
		boolean wasSet = false;

		index = getTestDataRecordIndex(fieldName, instance);
		if (index >= 0) {
			setIt = true;
			if (setOnlyIfBlank) {
				oldValue = testData.get(index).getValue();
				if (!oldValue.isEmpty()) {
					setIt = false;
				}
			}

			if (setIt) {
				testData.get(index).setValue(value);

				wasSet = true;
			}
		}
		return wasSet;
	}

	@Override
	public boolean setTestDataInstanceValue(String fieldName, String value, String instanceName, boolean setOnlyIfBlank) {
		return setTestDataInstanceValue(fieldName, value, testProperties.getInstanceNumber(instanceName), setOnlyIfBlank);
	}

	@Override
	public boolean setTestDataValue(String fieldName, String value, boolean setOnlyIfBlank) {
		return setTestDataInstanceValue(fieldName, value, 1, setOnlyIfBlank);
	}

	@Override
	public boolean setTestDataInstanceValue(String fieldName, String value, int instance) {
		return setTestDataInstanceValue(fieldName, value, instance, false);
	}

	@Override
	public boolean setTestDataInstanceValue(String fieldName, String value, String instanceName) {
		return setTestDataInstanceValue(fieldName, value, testProperties.getInstanceNumber(instanceName), false);
	}

	@Override
	public boolean setTestDataValue(String fieldName, String value) {
		return setTestDataInstanceValue(fieldName, value, 1, false);
	}

	@Override
	public String getTestDataInstanceValue(String fieldName, int instance) {
		String value = "";
		FieldData_ fieldData;

		fieldData = getTestDataRecord(fieldName, instance);
		if (fieldData != null) {
			value = fieldData.getValue();
		}

		return value.trim();
	}

	@Override
	public String getTestDataInstanceValue(String fieldName, String instanceName) {
		return getTestDataInstanceValue(fieldName, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public String getTestDataValue(String fieldName) {
		return getTestDataInstanceValue(fieldName, 1);
	}

	@Override
	public FieldData_ getTestDataInstanceRecord(String fieldName, int instance) {
		return getTestDataRecord(fieldName, instance);
	}

	@Override
	public FieldData_ getTestDataInstanceRecord(String fieldName, String instanceName) {
		return getTestDataInstanceRecord(fieldName, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public FieldData_ getTestDataRecord(String fieldName) {
		return getTestDataInstanceRecord(fieldName, 1);
	}

	@Override
	public boolean deleteTestDataInstanceRecord(String fieldName, int instance) {
		boolean deletedRecord = false;
		int index;
		
		index = getTestDataRecordIndex(fieldName, instance);
		
		if (index >= 0) {
			testData.remove(index);
			
			deletedRecord = true;
		}

		return deletedRecord;
	}

	@Override
	public boolean deleteTestDataInstanceRecord(String fieldName, String instanceName) {
		return deleteTestDataInstanceRecord(fieldName, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public boolean deleteTestDataRecord(String fieldName) {
		return deleteTestDataInstanceRecord(fieldName, 1);
	}
	
	@Override
	public boolean setVerificationDataInstanceValue(String fieldName, String value, int instance, boolean setOnlyIfBlank) {
		//This method allows the test to override one field of the page's verification data with a custom value.
		int index;
		String oldValue;
		boolean setIt;
		boolean wasSet = false;

		index = getVerificationDataRecordIndex(fieldName, instance);
		if (index >= 0) {
			setIt = true;
			if (setOnlyIfBlank) {
				oldValue = verificationData.get(index).getValueParm();
				if (!oldValue.isEmpty()) {
					setIt = false;
				}
			}

			if (setIt) {
				verificationData.get(index).setValueParm(value);

				wasSet = true;
			}
		}
		return wasSet;
	}

	@Override
	public boolean setVerificationDataInstanceValue(String fieldName, String value, String instanceName, boolean setOnlyIfBlank) {
		return setVerificationDataInstanceValue(fieldName, value, testProperties.getInstanceNumber(instanceName), setOnlyIfBlank);
	}

	@Override
	public boolean setVerificationDataValue(String fieldName, String value, boolean setOnlyIfBlank) {
		return setVerificationDataInstanceValue(fieldName, value, 1, setOnlyIfBlank);
	}

	@Override
	public boolean setVerificationDataInstanceValue(String fieldName, String value, int instance) {
		return setVerificationDataInstanceValue(fieldName, value, instance, false);
	}

	@Override
	public boolean setVerificationDataInstanceValue(String fieldName, String value, String instanceName) {
		return setVerificationDataInstanceValue(fieldName, value, testProperties.getInstanceNumber(instanceName), false);
	}

	@Override
	public boolean setVerificationDataValue(String fieldName, String value) {
		return setVerificationDataInstanceValue(fieldName, value, 1, false);
	}

	@Override
	public String getVerificationDataInstanceValue(String fieldName, int instance) {
		String value = "";
		FieldData_ fieldData;
		EISConstants.ParameterizedVerificationDataValueType parameterizedVerificationDataValueType;

		fieldData = getVerificationDataRecord(fieldName, instance);
		if (fieldData != null) {
			parameterizedVerificationDataValueType = fieldData.getParameterizedVerificationDataValueType();
			
			switch (parameterizedVerificationDataValueType) {
				case VERIFY:
				case VERIFY_VALUE:
				//The caller will need to ignore the return value from this method for types
				//  VERIFY_VALUES and VERIFY_VALUE_SET.  Instead, the caller should call
				//  FieldDataInstance.getValueSet() 
				case VERIFY_VALUES:
				case VERIFY_VALUE_SET:	{
					value = fieldData.getValueParm();
					break;
				}
				case VERIFY_CONSTANT:	{
					value = resolveConstantValue(fieldData.getValueParm(), fieldName);
					break;
				}
				case VERIFY_NULL:		{
					value = EISConstants.FieldExistenceCheckType.YES.toString();
					break;
				}
				case VERIFY_NOT_NULL:	{
					value = EISConstants.FieldExistenceCheckType.NO.toString();
					break;
				}
				case VERIFY_EXISTS:		{
					value = EISConstants.FieldExistenceCheckType.YES.toString();
					break;
				}
				case VERIFY_NOT_EXISTS:	{
					value = EISConstants.FieldExistenceCheckType.NO.toString();
					break;
				}
				case VERIFY_VISIBLE:	{
					value = EISConstants.FieldVisibilityCheckType.YES.toString();
					break;
				}
				case VERIFY_NOT_VISIBLE:	{
					value = EISConstants.FieldVisibilityCheckType.NO.toString();
					break;
				}
				default:	{
					EISTestBase.fail("Unhandled member of common.EISConstants.ParameterizedVerificationDataValueType enumerated type: " + parameterizedVerificationDataValueType);
				}
			}
		}

		return value.trim();
	}

	@Override
	public String getVerificationDataInstanceValue(String fieldName, String instanceName) {
		return getVerificationDataInstanceValue(fieldName, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public String getVerificationDataValue(String fieldName) {
		return getVerificationDataInstanceValue(fieldName, 1);
	}

	@Override
	public FieldData_ getVerificationDataInstanceRecord(String fieldName, int instance) {
		return getVerificationDataRecord(fieldName, instance);
	}

	@Override
	public FieldData_ getVerificationDataInstanceRecord(String fieldName, String instanceName) {
		return getVerificationDataInstanceRecord(fieldName, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public FieldData_ getVerificationDataRecord(String fieldName) {
		return getVerificationDataInstanceRecord(fieldName, 1);
	}

	@Override
	public boolean deleteVerificationDataInstanceRecord(String fieldName, int instance) {
		boolean deletedRecord = false;
		int index;
		
		index = getVerificationDataRecordIndex(fieldName, instance);
		
		if (index >= 0) {
			verificationData.remove(index);
			
			deletedRecord = true;
		}

		return deletedRecord;
	}

	@Override
	public boolean deleteVerificationDataInstanceRecord(String fieldName, String instanceName) {
		return deleteVerificationDataInstanceRecord(fieldName, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public boolean deleteVerificationDataRecord(String fieldName) {
		return deleteVerificationDataInstanceRecord(fieldName, 1);
	}
	
	private FieldData_ getTestDataRecord(String fieldName, int instance) {
		return doGetFieldDataRecord(fieldName, instance, testData);
	}

	private FieldData_ getVerificationDataRecord(String fieldName, int instance) {
		return doGetFieldDataRecord(fieldName, instance, verificationData);
	}

	private FieldData_ doGetFieldDataRecord(String fieldName, int instance, List<FieldDataInstance_> fieldDataInstanceList) {
		FieldData_ fieldData = null;
		int index;

		if (!fieldName.isEmpty()) {
			index = doGetFieldDataInstanceRecordIndex(fieldName, instance, fieldDataInstanceList);
			if (index >= 0) {
				//fieldData = ((FieldData) fieldDataInstanceList.get(index).getFieldData().clone())
				//Clone should not be necessary, because FieldDataInstance.getFieldData()
				//  implements defensive copying
				fieldData = (fieldDataInstanceList.get(index).getFieldData());
			}
		}

		return fieldData;
	}

	private int getTestDataRecordIndex(String fieldName, int instance) {
		return doGetFieldDataInstanceRecordIndex(fieldName, instance, testData);
	}

	private int getVerificationDataRecordIndex(String fieldName, int instance) {
		return doGetFieldDataInstanceRecordIndex(fieldName, instance, verificationData);
	}

	private int getTestDataRecordIndex(String fieldName, int instance, List<FieldDataInstance_> testData) {
		//This version is provided primarily for the convenience of implementFieldDependencies()
		return doGetFieldDataInstanceRecordIndex(fieldName, instance, testData);
	}

	private int doGetFieldDataInstanceRecordIndex(String fieldName, int instance, List<FieldDataInstance_> fieldDataInstanceList) {
		int index = -1;
		FieldDataInstance_ fieldDataInstance;

		//Cleaner to use iteration here instead of for-each, because we don't need to increment index
		//	inside the loop and manage a recordFound flag
		if (!fieldName.isEmpty()) {
			ListIterator<FieldDataInstance_> itr = fieldDataInstanceList.listIterator();
			while (itr.hasNext()) {
				fieldDataInstance = itr.next();

				if (fieldDataInstance.getFieldName().equalsIgnoreCase(fieldName)) {
					if (fieldDataInstance.getInstance() == instance) {
						index = itr.nextIndex() - 1;

						break;
					}
				}
			}
		}

		return index;
	}

	protected Field getFieldMetadata(String fieldName) throws MetadataException {
		Field field = null;
		int index;

		index = getFieldMetadataRecordIndex(fieldName.trim());
		field = ((Field) fieldMetadata.get(index).clone());

		return field;
	}
	/**
	 * @Description : Get the locators without ignoring case sensitive
	 * @param fieldName
	 * @return
	 * @throws MetadataException
	 */
	protected Field getFieldMetadata(String fieldName, boolean ignoreCase) throws MetadataException {
		Field field = null;
		int index;

//		index = getFieldMetadataRecordIndex(fieldName.trim());
		index = getFieldMetadataRecordIndex(fieldName.trim(),false);
		field = ((Field) fieldMetadata.get(index).clone());

		return field;
	}
	/**
	 * @Descriptiopn This needs to be used only when you wish to add a dynamic locator which is created out of dynamic locator to the existing fieldMetadata list
	 * @param fieldName
	 * @param fieldLocatorvalue
	 * @throws MetadataException
	 */
	public void addNewFieldToExistingfieldMetadataList(String fieldName, String fieldLocatorvalue) throws MetadataException{
		fieldMetadata.add(new Field(fieldName, fieldLocatorvalue));
	}
	
	/**
	 * @Descriptiopn
	 * @param fieldName
	 * @param fieldLocatorvalue
	 * @throws MetadataException
	 */
	public void removeFieldToExistingfieldMetadataList(String fieldName) throws MetadataException{
			fieldMetadata.remove(getFieldMetadataRecordIndex(fieldName));	//remove newly added field
	}
	protected void setFieldMetadata(String fieldName, Field field) throws MetadataException {
		int index;

		index = getFieldMetadataRecordIndex(fieldName.trim());
		fieldMetadata.set(index, field);
	}

	private void setPageMetadata() throws MetadataException {
		String temp;

		//NOTE  It is important to remove the page-level metadata from pageProperties, because in createFieldMetadataObjects()
		//  we loop through all records.  If page-level metadata properties are there, they will be interpreted as field-level
		//  metadata

		name = pageProperties.getProperty(PAGE_NAME, "");
		if (name.isEmpty()) {
			throw new MetadataException("The '" + PAGE_NAME + "' property was not found in the page properties file '" + propertiesFilename + "'");
		}
		pageProperties.remove(PAGE_NAME);

		containerWindowName = pageProperties.getProperty(CONTAINER_WINDOW_NAME, "");
		if (containerWindowName.isEmpty()) {
			throw new MetadataException("The '" + CONTAINER_WINDOW_NAME + "' property was not found in the page properties file '" + propertiesFilename + "'");
		}
		pageProperties.remove(CONTAINER_WINDOW_NAME);

		temp = pageProperties.getProperty(PAGE_MESSAGE_LOCATOR, "");
		if (!temp.isEmpty()) {
			messageLocators = Arrays.asList(temp.split(EISConstants.PROPERTY_INSTANCE_DELIM));
			messageLocators = Util.listOfStringTrim(messageLocators);
		}
		pageProperties.remove(PAGE_MESSAGE_LOCATOR);

		temp = pageProperties.getProperty(FIELD_DEPENDENCIES, "");
		if (!temp.isEmpty()) {
			fieldDependencies = Arrays.asList(temp.split(EISConstants.PROPERTY_INSTANCE_DELIM));
			fieldDependencies = Util.listOfStringTrim(fieldDependencies);
		}
		pageProperties.remove(FIELD_DEPENDENCIES);
	}
	
	//This method is used in conjunction with tokenized Selenium locators in page properties files.
	//  At run time, the test code can replace those tokens with a known value by calling this
	//  method.  For each locator in the referenced Field object, one or all tokens are replaced by
	//  the value (by a call to parseFieldLocatorsTokens(String fieldName, String value, boolean
	//  parseFirstTokenOnly)).  If at least one token was found and replaced, a new Field object
	//  is added to the fieldMetadata list, and the name of that field is passed back to the caller.
	//  This new Field object can be treated just like any other.  (The reason we don't just modify
	//  the found Field object - instead of creating another - is that we want to preserve it for
	//  possible use later in the test; if we obliterate its tokens, that would not be possible.)
	@Override
	public String createFieldWithParsedFieldLocatorsTokens(String fieldName, String value, boolean parseFirstTokenOnly) {
		String newFieldName;
		Field field = null;
		List<String> parsedLocators = new ArrayList<String>();

		//First check to see if we have previously parsed the locator tokens of this Field object with
		//  this value; if so, we don't need to parse it again (set the return value to the name of the
		//  Field object we are looking for, in case we find it)
		//newFieldName = fieldName + "_" + value;
		newFieldName = fieldName + "_" + value + "_";
		
		try {
			field = getFieldMetadata(newFieldName);
		} catch (MetadataException me) {
			//If the Field object IS NOT found, it means we haven't previously parsed the locator tokens of this
			//  Field object with this value, so let's try doing it now (set the return value to the passed in
			//  fieldName, in case that Field object is not found or does not need to be parsed)
			newFieldName = fieldName;
			
			try {
				field = getFieldMetadata(fieldName);
			} catch (MetadataException me2) {
				EISTestBase.fail("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me2.getMessage());
			}
			
			parsedLocators = parseFieldLocatorsTokens(field.getLocators(), value, parseFirstTokenOnly);

			//newFieldName += "_" + value;
 			newFieldName += "_" + value + "_";
			newFieldName = newFieldName.replace(" ", "");

			try {
				fieldMetadata.add(new Field(newFieldName, field.getType(), parsedLocators, field.getRequirednessLevel(), field.getMessageLocators()));
			} catch (MetadataException me3) {
				EISTestBase.fail(me3.getMessage());
			}
		}

		return newFieldName;
	}
	
	@Override
	public String createFieldWithParsedFieldLocatorsTokens(String fieldName, String value) {
		return createFieldWithParsedFieldLocatorsTokens(fieldName, value, false);
	}

	//This method is used in conjunction with tokenized Selenium locators in page properties files.
	//  At run time, the test code can replace those tokens with known values by calling this
	//  method.  For each locator in the referenced Field object, each token is replaced in turn
	//  by one of the values (by a call to parseFieldLocatorsTokens(String fieldName, String... value)).
	//  If at least one token was found and replaced, a new Field object is added to the fieldMetadata
	//  list, and the name of that field is passed back to the caller.  This new Field object can
	//  be treated just like any other.  (The reason we don't just modify the found Field object
	//  - instead of creating another - is that we want to preserve it for possible use later in
	//  the test; if we obliterate its tokens, that would not be possible.)
	@Override
	public String createFieldWithParsedFieldLocatorsTokens(String fieldName, String... inValues) {
		String newFieldName;
		String newFieldNameSuffix = "";
		Field field = null;
		List<String> parsedLocators = new ArrayList<String>();
		List<String> values = new ArrayList<String>(Arrays.asList(inValues));

		//First check to see if we have previously parsed the locator tokens of this Field object with
		//  these values; if so, we don't need to parse it again (set the return value to the name of the
		//  Field object we are looking for, in case we find it)
		ListIterator<String> valueItr = values.listIterator();
		while (valueItr.hasNext()) {
			String value = valueItr.next();
			
			//newFieldNameSuffix += "_" + value;
			newFieldNameSuffix += "_" + value + "_";
		}

		newFieldName = fieldName + newFieldNameSuffix;
		
		try {
			field = getFieldMetadata(newFieldName);
		} catch (MetadataException me) {
			//If the Field object IS NOT found, it means we haven't previously parsed the locator tokens of this
			//  Field object with these values, so let's try doing it now (set the return value to the passed in
			//  fieldName, in case that Field object is not found or does not need to be parsed)
			newFieldName = fieldName;
			
			try {
				field = getFieldMetadata(fieldName);
			} catch (MetadataException me2) {
				EISTestBase.fail("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me2.getMessage());
			}
			
			parsedLocators = parseFieldLocatorsTokens(field.getLocators(), inValues);

			newFieldName = fieldName + newFieldNameSuffix;
			newFieldName = newFieldName.replace(" ", "");

			try {
				fieldMetadata.add(new Field(newFieldName, field.getType(), parsedLocators, field.getRequirednessLevel(), field.getMessageLocators()));
			} catch (MetadataException me3) {
				EISTestBase.fail(me3.getMessage());
			}
		}

		return newFieldName;
	}
	
	//This method is used in conjunction with tokenized Selenium locators in page properties files.
	//  At run time, the test code can replace those tokens with a known value by calling this
	//  method.  For each locator in the referenced Field object, one or all tokens are replaced by
	//  the value (by a call to parseFieldLocatorsTokens(String fieldName, String value, boolean
	//  parseFirstTokenOnly)).  Locators without tokens are preserved.
	@Override
	public void parseFieldLocatorsTokens(String fieldName, String value, boolean parseFirstTokenOnly) {
		Field field = null;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while parsing locator tokens in the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		field.setLocators(parseFieldLocatorsTokens(field.getLocators(), value, parseFirstTokenOnly));
		
		try {
			setFieldMetadata(fieldName, field);
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}
	}

	@Override
	public void parseFieldLocatorsTokens(String fieldName, String value) {
		parseFieldLocatorsTokens(fieldName, value, false);
	}

	//This method is used in conjunction with tokenized Selenium locators in page properties files.
	//  At run time, the test code can replace those tokens with known values by calling this
	//  method.  For each locator in the referenced Field object, each token is replaced in turn
	//  by one of the values (by a call to parseFieldLocatorsTokens(String fieldName, String... value)).
	//  Locators without tokens are preserved.
	@Override
	public void parseFieldLocatorsTokens(String fieldName, String... inValues) {
		Field field = null;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while parsing locator tokens in the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		field.setLocators(parseFieldLocatorsTokens(field.getLocators(), inValues));
		
		try {
			setFieldMetadata(fieldName, field);
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}
	}
	
	@Override
	public void parseAllFieldLocatorsTokens(String value, boolean parseFirstTokenOnly) {
		String fieldName;
		
		ListIterator<Field> itr = fieldMetadata.listIterator();
		while (itr.hasNext()) {
			fieldName = itr.next().getName();
			
			parseFieldLocatorsTokens(fieldName, value, parseFirstTokenOnly);
		}
	}

	@Override
	public void parseAllFieldLocatorsTokens(String value) {
		parseAllFieldLocatorsTokens(value, false);
	}

	@Override
	public void parseAllFieldLocatorsTokens(String... inValues) {
		String fieldName;
		
		ListIterator<Field> itr = fieldMetadata.listIterator();
		while (itr.hasNext()) {
			fieldName = itr.next().getName();
			
			parseFieldLocatorsTokens(fieldName, inValues);
		}
	}

	//This method is used in conjunction with L10N-tokenized Selenium locators in page properties files.
	//  At run time, the test code can replace those L10N tokens with a known value by calling this
	//  method.  For each locator in the referenced Field object, one or all L10N tokens are replaced by
	//  the value.  Locators without tokens are preserved.
	@Override
	public void parseL10NFieldLocatorsTokens(String fieldName, String value, boolean parseFirstTokenOnly) {
		List<String> parsedLocators = new ArrayList<String>();
		Field field = null;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while parsing locator L10N tokens in the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		ListIterator<String> itr = field.getLocators().listIterator();
		while (itr.hasNext()) {
			String locator = itr.next();

			if (!parseFirstTokenOnly) {
				parsedLocators.add(locator.replaceAll(EISConstants.L10N_TOKEN_REGEX_TERM, value));
			} else {
				parsedLocators.add(locator.replaceFirst(EISConstants.L10N_TOKEN_REGEX_TERM, value));
			}
		}
		
		field.setLocators(parsedLocators);		
		
		try {
			setFieldMetadata(fieldName, field);
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}
	}
	
	@Override
	public void parseL10NFieldLocatorsTokens(String fieldName, String value) {
		parseL10NFieldLocatorsTokens(fieldName, value, false);
	}
	
	private List<String> parseFieldLocatorsTokens(List<String> locators, String value, boolean parseFirstTokenOnly) {
		List<String> parsedLocators = new ArrayList<String>();

		ListIterator<String> itr = locators.listIterator();
		while (itr.hasNext()) {
			String locator = itr.next();

			if (!parseFirstTokenOnly) {
				parsedLocators.add(locator.replaceAll(EISConstants.TOKEN_REGEX_TERM, value));
			} else {
				parsedLocators.add(locator.replaceFirst(EISConstants.TOKEN_REGEX_TERM, value));
			}
		}
		
		return parsedLocators;
	}
	
	@SuppressWarnings("unused")
	private List<String> parseFieldLocatorsTokens(List<String> locators, String value) {
		return parseFieldLocatorsTokens(locators, value, false);
	}
	
	private List<String> parseFieldLocatorsTokens(List<String> locators, String... inValues) {
		List<String> parsedLocators = new ArrayList<String>();
		List<String> values = new ArrayList<String>(Arrays.asList(inValues));

		ListIterator<String> locatorItr = locators.listIterator();
		while (locatorItr.hasNext()) {
			String locator = locatorItr.next();

			ListIterator<String> valueItr = values.listIterator();
			while (valueItr.hasNext()) {
				String value = valueItr.next();
				
				locator = locator.replaceFirst(EISConstants.TOKEN_REGEX_TERM, value);
			}

			parsedLocators.add(locator);
		}
		
		return parsedLocators;
	}
	
	private void createFieldMetadataObjects() throws MetadataException {
		Enumeration<?> keys;
		String name;
		String metadata;

		keys = pageProperties.keys();
		while (keys.hasMoreElements()) {
			name = (String)keys.nextElement();
			metadata = pageProperties.getProperty(name);

			try {
				fieldMetadata.add(new Field(name, metadata));
			} catch (MetadataException me) {
				throw new MetadataException("The metadata for the field '" + name + "' on the page '" + this.name + "' is invalid: " + me.getMessage());
			}
		}
	}

	private void loadConstants() {
		constants = testProperties.getPageConstants(name);
	}

	private void loadTestData() {
		testData = testProperties.getPageTestData(name);
	}

	private void loadVerificationData() {
		verificationData = testProperties.getPageVerificationData(name);
	}

	private void resolveConstantsInTestData() {
		String regexTerm = ".*" + EISConstants.PARAMETERS_REGEX_TERM;
		String tempType;
		String value;
		String constantName;
		boolean hasConstant;

		for (FieldDataInstance_ fieldDataInstance : testData) {
			hasConstant = false;

			value = fieldDataInstance.getValue();

			if (value.matches(regexTerm)) {
				//We now have text that matches the regex term, but that text is not necessarily a parm type
				//  and a delimited value; it could be part of a "pure value" that just happens to match the
				//  regex.  We'll grab the text to the left of the parms start delimiter, and see if it is
				//  of the type EISConstants.ParameterizedTestDataValueType
				tempType = Util.left(value, value.indexOf(EISConstants.PARAMETERS_START_DELIM)).trim().toUpperCase();

				try {
					EISConstants.ParameterizedTestDataValueType.valueOf(tempType);

					//It contains an EISConstants.ParameterizedTestDataValueType element, but we only care about VALUE_CONSTANT
					if (EISConstants.ParameterizedTestDataValueType.VALUE_CONSTANT.toString().equalsIgnoreCase(tempType)) {
						hasConstant = true;
					}
				} catch (IllegalArgumentException e) {}
			}

			if (hasConstant) {
				String fieldName = fieldDataInstance.getFieldName();

				constantName = Util.getField(value, EISConstants.PARAMETERS_START_DELIM, EISConstants.PARAMETERS_END_DELIM);
				value = resolveConstantValue(constantName, fieldName);

				fieldDataInstance.setValue(value);
			}
		}
	}

	private String resolveConstantValue(String constantName, String fieldName) {
		String value = getConstant(constantName);

		if (value.isEmpty()) {
			Util.printWarning("While resolving the constant in the value property of the Field '" + fieldName + "', the constant '" + constantName + "' was not found in the constant store of Page '" + name + "', nor in the test-scope constant store; returning empty string");
		}

		return value;
	}

	private void implementFieldDependencies() throws MetadataException {
		List<FieldDependencySet> fieldDependencySets = new ArrayList<FieldDependencySet>();
		Hashtable<Integer, List<FieldDependencySet>> fieldDependencySetsInstances = new Hashtable<Integer, List<FieldDependencySet>>();
		Hashtable<Integer, List<FieldDataInstance_>> pageTestDataInstance = new Hashtable<Integer, List<FieldDataInstance_>>();

		String[] prereqAndDepFields = new String[2];
		String prerequisiteField;
		String dependentFields;

		//Parse the strings in fieldDependencies into a list of FieldDependencySet objects.
		for (String dependencySetString : fieldDependencies) {
			//prereqAndDepFields = dependencySetString.split(EISConstants.PROPERTY_TYPE_DELIM, 2);
			prereqAndDepFields = dependencySetString.split(EISConstants.PROPERTY_TYPE_DELIM);

			if (prereqAndDepFields.length != 2) {
				throw new MetadataException("The field dependency set '" + dependencySetString + "' is not in the format '[prerequisiteField]" + EISConstants.PROPERTY_TYPE_DELIM + "[dependentField(s)]' or '[prerequisiteField]" + EISConstants.PROPERTY_TYPE_DELIM + "*'");
			}

			prerequisiteField = prereqAndDepFields[0].trim();
			dependentFields = prereqAndDepFields[1].trim();

			fieldDependencySets.add(new FieldDependencySet(prerequisiteField, dependentFields));
		}

		//The test data may contain multiple instances of a given field.  The set of all fields with the same instance can
		//  be seen as a "page test data instance."  Each of these instances needs to have the field dependency sets applied
		//  to it separately from the other instances.  To accomplish that, let's first create the page test data instances
		//  (keep in mind that we expect testData to be sorted on FieldDataInstance.instance).  Also, generate an
		//  associated list of field dependency sets
		for (FieldDataInstance_ fieldDataInstance : testData) {
			int instance = fieldDataInstance.getInstance();

			//If there is not already a pageTestDataInstance object to hold fields with this instance, create one
			if (!pageTestDataInstance.containsKey(instance)) {
				pageTestDataInstance.put(instance, new ArrayList<FieldDataInstance_>());
				//Create an associated list of field dependency sets (initially each list will contain the same
				//  sets; we will groom them later)
				fieldDependencySetsInstances.put(instance, fieldDependencySets);
			}

			//pageTestDataInstance = pageTestDataInstance.get(instance);
			//pageTestDataInstance.add(new FieldDataInstance(fieldDataInstance));
			pageTestDataInstance.get(instance).add(new FieldDataInstance(fieldDataInstance));
		}

		//Loop through the pageTestDataInstances object, grooming the associated list of field dependency sets,
		//  and moving fields in the pageTestDataInstance object:
		//  	For each field dependency set in the list:
		//   		if the prerequisiteField is found in the pageTestDataInstance object
		//      		if the set is not a "global" set (e.g., containing a * instead of a list of dependent fields)
		//        			for each field in the set's dependentFields list, if it is found in the pageTestDataInstance object
		//         				add its location to a list of dependent field indices
		//					sort the list of dependent field indices
		//					move the prerequisite field to the position in the pageTestDataInstance object just before the first dependent field
		//			ELSE
		//        		move the field referenced by prerequisiteField to the top of the pageTestDataInstance object
		//Then clear the testData object and load the pageTestDataInstance objects into it
		int instanceNum;
		int prerequisiteFieldIndex;
		int dependentFieldIndex;
		List<Integer> dependentFieldIndices = new ArrayList<Integer>();
		FieldDataInstance_ prerequisiteFieldDataInstance;

		Iterator<Entry<Integer, List<FieldDataInstance_>>> itr = pageTestDataInstance.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Integer, List<FieldDataInstance_>> entry = itr.next();

			List<FieldDataInstance_> aPageTestDataInstance = entry.getValue();
			instanceNum = entry.getKey();
			List<FieldDependencySet> aFieldDependencySetsInstance = fieldDependencySetsInstances.get(instanceNum);

			for (FieldDependencySet aFieldDependencySet : aFieldDependencySetsInstance) {
				prerequisiteFieldIndex = getTestDataRecordIndex(aFieldDependencySet.getPrerequisiteField(), instanceNum, aPageTestDataInstance);
				if (prerequisiteFieldIndex >= 0) {
					//Remove the prerequisite field from the list so that the indices of the dependent fields are calculated correctly
					prerequisiteFieldDataInstance = aPageTestDataInstance.remove(prerequisiteFieldIndex);

					if (!aFieldDependencySet.getDependentFields().get(0).startsWith("*")) {
						dependentFieldIndices.clear();

						for (String dependentField : aFieldDependencySet.getDependentFields()) {
							dependentFieldIndex = getTestDataRecordIndex(dependentField, instanceNum, aPageTestDataInstance);
							if (dependentFieldIndex >= 0) {
								dependentFieldIndices.add(dependentFieldIndex);
							}
						}

						if (!dependentFieldIndices.isEmpty()) {
							Collections.sort(dependentFieldIndices);

							//Add the prerequisite field back into the list, just before the first dependent field
							aPageTestDataInstance.add(dependentFieldIndices.get(0), prerequisiteFieldDataInstance);
						}
						else
						{
							//There were no dependent fields; return the prerequisite field to its original position in the list 
							aPageTestDataInstance.add(prerequisiteFieldIndex, prerequisiteFieldDataInstance);
						}

					} else {
						aPageTestDataInstance.add(0, prerequisiteFieldDataInstance);
					}
				}
			}
		}

		//Empty out testData and load it with the newly ordered test data instances
		testData.clear();

		Enumeration<Integer> keys = pageTestDataInstance.keys();
		while (keys.hasMoreElements()) {
			testData.addAll(new ArrayList<FieldDataInstance_>(pageTestDataInstance.get(keys.nextElement())));
		}

		return;
	}

/*	private int getFieldMetadataRecordIndex(String fieldName) {
		int index = -1;
		Field field;

		//Cleaner to use iteration here instead of for-each, because we don't need to increment index
		//	inside the loop and manage a recordFound flag
		if (!fieldName.isEmpty()) {
			ListIterator<Field> itr = fieldMetadata.listIterator();
			while (itr.hasNext()) {
				field = itr.next();

				if (field.getName().equalsIgnoreCase(fieldName)) {
					index = itr.nextIndex() - 1;

					break;
				}
			}
		}

		return index;
	}
*/
	
	private int getFieldMetadataRecordIndex(String inFieldName) throws MetadataException {
		/*int index = -1;
		Field field;
		String fieldName = inFieldName.trim();
		
		//Cleaner to use iteration here instead of for-each, because we don't need to increment index
		//	inside the loop and manage a recordFound flag
		if (!fieldName.isEmpty()) {
			ListIterator<Field> itr = fieldMetadata.listIterator();
			while (itr.hasNext()) {
				field = itr.next();

				if (field.getName().equalsIgnoreCase(fieldName)) {
					index = itr.nextIndex() - 1;

					break;
				}
			}
		}

		if (index == -1) {
			throw new MetadataException("Invalid field name: '" + fieldName + "'");
		}
		
		return index;*/
		return getFieldMetadataRecordIndex(inFieldName,true);
	}
	private int getFieldMetadataRecordIndex(String inFieldName,boolean ignoreCase) throws MetadataException {
		int index = -1;
		Field field;
		String fieldName = inFieldName.trim();
		
		//Cleaner to use iteration here instead of for-each, because we don't need to increment index
		//	inside the loop and manage a recordFound flag
		if (!fieldName.isEmpty()) {
			ListIterator<Field> itr = fieldMetadata.listIterator();
			while (itr.hasNext()) {
				boolean foundMatch=false;
				field = itr.next();
				
				//dont mention OR operator here 
				if(ignoreCase){					
					if (field.getName().equalsIgnoreCase(fieldName)) {
						foundMatch=true;						
					}
				}else{	//dont ignore case here
					if (field.getName().equals(fieldName)) {
						foundMatch=true;
					}
				}
				if(foundMatch){
					index = itr.nextIndex() - 1;
					break;
				}
				//orig code
				/*if (field.getName().equalsIgnoreCase(fieldName)) {
						foundMatch=true;						
					}*/
			}
		}

		if (index == -1) {
			throw new MetadataException("Invalid field name: '" + fieldName + "'");
		}
		
		return index;
	}
	

	private int doPopulate(List<FieldData> populateData) throws GUIException {
		final int MAX_TRIES = 2;

		//We return a record count instead of a boolean here, because:
		//  1) returning a boolean doesn't make sense, because it would always be true - the operation either
		//     succeeds (true), or throws an exception
		//  2) it's nice to return something
		int numFieldsPopulated = 0;
		String fieldName;
		String value;
		Field field;
		String guiErrorMessage;
		boolean success;
		EISConstants.FieldType fieldType;

		for (FieldData_ fd : populateData) {
			fieldName = fd.getName();
			value = fd.getValue();

			try {
				field = getFieldMetadata(fieldName);
			} catch (MetadataException me) {
				throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
			}

			fieldType = field.getType();
			switch (fieldType) {
				case BUTTON:
				case LINK:	{
					guiErrorMessage = "Error while clicking the " + fieldType + " Field '" + fieldName + "' on the Page '" + name + "': ";
					break;
				}
				case LOOKUP:	{
					guiErrorMessage = "Error while populating the " + fieldType + " Field '" + fieldName + "' on the Page '" + name + "' (using the lookup pop-up) with the value '" + value + "': ";
					break;
				}
				default:	{
					guiErrorMessage = "Error while populating the " + fieldType + " Field '" + fieldName + "' on the Page '" + name + "' with the value '" + value + "': ";
					break;
				}
			}

			//Spend less time trying to find fields with a "requiredness" level of VARIABLE
			if (!field.getRequirednessLevel().equals(EISConstants.RequirednessLevel.VARIABLE)) {
				gui.setWindowWaitTimeout(EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
			} else {
				gui.setWindowWaitTimeout(EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT / 2);
			}

			Util.printDebug("Populating the " + fieldType + " Field '" + fieldName + "' on the Page '" + name + "' with the value '" + value + "'.");

			success = false;
			for (int tryNum = 1; tryNum <= MAX_TRIES; tryNum++) {
				try {
					switch (fieldType) {
						case HOVER:			{
							gui.populateHoverField(field);
							break;
						}

						case TEXT:			{
							gui.populateTextField(field, value);
							break;
						}
						case HIDDENTEXT: 	{
							gui.populateHiddenTextField(field, value);
							break;
						}
						case HIDDENTEXT_2: 	{
							gui.populateHiddenTextField(field, value);
							break;
						}
						case DATE:			{
							gui.populateDateField(field, value);
							break;
						}
						case PICKLIST:		{
							gui.selectFromPicklist(field, value);
							break;
						}
						case MULTISELECT:	{
							//NOTE that we support selecting only one value at a time!!!
							gui.selectFromMultiSelectList(field, value);
							break;
						}
						case CHECKBOX:		{
							gui.clickCheckbox(field, value);
							break;
						}
						case RADIOBUTTON:	{
							gui.clickRadioButton(field, value);
							break;
						}
						case BUTTON:		{
							gui.clickButtonOrLink(field);
							break;
						}
						case LINK:			{
							gui.clickButtonOrLink(field);
							break;
						}
						case LOOKUP:		{
							//doPopulateLookupField calls gui.clickButtonOrLink(), but only as part of the new (WebDriver migration) logic for waiting for the pop-up
							doPopulateLookupField(field, value);
							break;
						}
	
						//Fields of these types are used for verification purposes, not GUI population
						case READ_ONLY:
						case READ_ONLY_DATE:	{
							throw new TestDataException("Fields of type " +  fieldType + " are read-only; they should not be specified as test data (perhaps you intended to specify the field as verification data?)");
						}
						default:	{
							throw new MetadataException("Unhandled member of common.EISConstants.FieldType enumerated type: " + fieldType);
						}
					}

					success = true;
				} catch (TestDataException te) {
					throw new GUIException(guiErrorMessage + te.getMessage(), te);
				} catch (MetadataException me) {
					if (field.getRequirednessLevel().equals(EISConstants.RequirednessLevel.VARIABLE)) {
						//We should be careful here, because it is possible that the MetadataException occurred for some reason other than
						//  a failure to find the locator of a field that is not required to be populated:  What if the field IS required to
						//  be populated on the form, and indeed it is there, but we specified the locator incorrectly in the metadata?
						//  What if the field type is invalid?  What if the exception occurred because of the timing issue we are dealing
						//  with here?
						Util.printWarning("The Field '" + fieldName + "' (isRequired = " + EISConstants.RequirednessLevel.VARIABLE.toString() + ") was specified in the test data, but was not found on the Page '" + name + "'.  Not that there's anything wrong with that...");

						success = true;
					} else {
						if (tryNum != MAX_TRIES) {
							//Util.printDebug("MetadataException trapped on try number " + tryNum + " of " + MAX_TRIES + ": " + guiErrorMessage + me.getMessage() + " (will sleep and try " + (MAX_TRIES - tryNum) + " more time(s))");
							Util.printWarning("(After " + tryNum + " attempt(s)) " + guiErrorMessage + me.getMessage() + " (will sleep and make " + (MAX_TRIES - tryNum) + " more attempt(s))");
						} else {
							throw new GUIException("(After " + MAX_TRIES + " attempt(s)) " + guiErrorMessage + me.getMessage());
						}
					}
				}

				if (success) {
					break;
				}

				if (tryNum != MAX_TRIES) {
					Util.sleep(EISConstants.POPULATE_RETRY_DELAY);
				}
			}

			numFieldsPopulated++;

			//if (gui.isConfirmationPresent()) {
			//    Util.printDebug("Got confirmation dialog on Field " + fieldName + ": " + gui.getConfirmation());
			//}

			//WebDriver does not differentiate between alerts and confirmations
			//if (isConfirmationPresent()) {
			//	confirmationMessage = getConfirmation();
			//	Util.printInfo("Got a confirmation dialog after populating the Field '" + fieldName + "' on the Page '" + name + "': " + confirmationMessage);
			//}

			//Checking for alerts every time we populate a field really slows things down, so we are not doing it anymore
			//The user should anticipate when alerts may appear, and handle them himself 
			//if (checkForAlerts) {
			//	if (isAlertPresent()) {
			//		alertMessage = getAlert();
			//		Util.printInfo("Got an alert dialog after populating the Field '" + fieldName + "' on the Page '" + name + "': '" + alertMessage + "'");
			//	}
			//}

			//wait was used in to simulate Selenium clickAndWait calls.  It has no use now, because "...AndWait" methods are deprecated
			//if (waitTime > 0) {
			//	//Util.printDebug("Calling gui.waitForPageToLoad() after populating " + name + "." + fieldName + " (waitTime is " + waitTime + ")");
			//	Util.printWarning("waitTime is set (" + waitTime + ") while populating '" + name + "." + fieldName + "'; IT SHOULD NOT BE USED! ");
			//
			//	//try {
			//	//	gui.waitForPageToLoad(waitTime);
			//	//} catch (GUIException ge) {
			//	//	Util.printDebug(ge.getMessage() + " (Page = '" + name + "', Field = '" + fieldName + "'); will continue");
			//	//}
			//}

			//wait was used in to simulate Selenium clickAndWait calls.  It has no use now, because "...AndWait" methods are deprecated
			//Reset waitTime (in case it had been set by an "...AndWait" method - which it shouldn't, because "...AndWait" methods are deprecated)
			//waitTime = 0;
			
			//Checking for alerts every time we populate a field really slows things down, so we are not doing it anymore
			//Reset checkForAlerts (in case it had been changed, perhaps in doPopulateLookupField)
			//checkForAlerts = checkForAlertsDefault;
			
			//Reset gui.windowWaitTimeout (in case it was changed while populating a field with a "requiredness" level of VARIABLE)
			gui.setWindowWaitTimeout(EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);

			//This code is necessary in order to solve the problem wherein the speed with which the fields get populated
			//  and the speed of the window "redraws" (not real refreshes, but not always Ajax either) get out of synch,
			//  causing some fields which had already been populated to get cleared.
			//IF for some reason this is found not to work for some cases, keep in mind that I have tried waiting for
			//  element, and waiting for page load, neither of which worked well.  Sleeping worked, but needlessly
			//  slowed things up
			//It turns out that the waitForCondition call by itself does not work too well, so I tried replacing it
			//  with a setSpeed call at the entrance to this method.  However, setSpeed applies to ALL selenium calls
			//  (not just those that touch the GUI), so it slows us down too much.  Besides, sometimes we DO want to
			//  wait on the cursor.  So, let's keep the waitForCondition call
			//
			//TODO  Perhaps we can/should do this for all pages, not just those in the "slow to settle" windows?  Or at least
			//  be more intelligent about determining which pages/windows need it?
			//if (getContainerWindowName().equalsIgnoreCase("[some slow window]")) {
			//	Util.printDebug("Calling gui.waitForCondition() after populating " + getPageName() + "." + fieldName);
			//
			//	try {
			//		//The Selenium 1.0 version
			//		//gui.waitForCondition("selenium.browserbot.getCurrentWindow().document.body.style.cursor!='wait'", 2000);
			//		//In WebDriver, the Javascript code must contain a "return" call, and should not reference browserbot,
			//		//  which is not supported
			//		//The WebDriver version
			//		gui.waitForCondition("return document.body.style.cursor!='wait'", 2000);
			//	} catch (GUIException ge) {
			//		Util.printDebug("A GUI exception (" + ge.getMessage() + ") occurred during gui.waitForCondition call (Page = '" + getPageName() + "', Field = '" + fieldName + "'); will continue");
			//	}
			//}

			Util.sleep(pageRedrawDelay);
		}

		return numFieldsPopulated;
	}
	

/*	private void doPopulateLookupField(Field field, String inValue) throws TestDataException, MetadataException {
		Set<String> existingWindows = null;
		String parentWindow 		= null;
		Window_ lookupPopUp			= EISTestBase.lookupPopUp;
		Page_ lookupPopUpPage		= EISTestBase.lookupPopUpPage;

		//It is assumed that this method was called from doPopulate,
		//  which means that the error message generated here will
		//  be appended to doPopulate's guiErrorMessage string

		parentWindow = getWindowHandle();

		existingWindows = getWindowHandles();
		gui.clickButtonOrLink(field);

		//lookupPopUp.waitForPopUpToOpen();	
		lookupPopUp.setLocator(waitForPopUpToOpen(lookupPopUp, existingWindows));		
		lookupPopUp.select();

		lookupPopUpPage.populateField("searchBox", inValue);
		lookupPopUpPage.click("goButton");

		//doPopulate is not checking for alerts anymore, so this line is unnecessary
		//lookupPopUpPage.setCheckForAlerts(false);
		
		//Experience has shown that sometimes when we click the first row we get the value that was there
		//  before we initiated the search (and it may not have been a "value" at all - it may have been
		//  the "noRecordsMessage" message).  Perhaps control returns before the search was finished?  We'll
		//  sleep and see, although the possibility remains that the go button did not get clicked properly
		//Util.sleep(1000);
		Util.sleep(2000);
		
		if (!lookupPopUpPage.isFieldPresent("noRecordsMessage")) {
			//Some flavors of the lookup window do not show the "noRecordsMessage" message - they
			//  just show nothing
			if (lookupPopUpPage.isFieldPresent("firstFoundObject")) {
				lookupPopUpPage.click("firstFoundObject");
			} else {
				throw new TestDataException("The search in the lookup window did not return any records");
			}
		} else {
			//If it is later determined that we should just print a warning, and/or pass back a boolean
			//  or whatever, be sure to close the window!
			throw new TestDataException("The search in the lookup window did not return any records");
		}
		
		gui.selectWindow(parentWindow);
	}
*/
/*	private void doPopulateLookupField(Field field, String inValue) throws TestDataException, MetadataException {
		Set<String> existingWindows = null;
		String parentWindow 		= null;
		Window_ lookupPopUp			= EISTestBase.lookupPopUp;
		Page_ lookupPopUpPage		= EISTestBase.lookupPopUpPage;

		//It is assumed that this method was called from doPopulate,
		//  which means that the error message generated here will
		//  be appended to doPopulate's guiErrorMessage string

		parentWindow = getWindowHandle();

		existingWindows = getWindowHandles();
		gui.clickButtonOrLink(field);
		
		//We are having all kinds of timing issues, so sleep here...
		Util.sleep(2000);

		lookupPopUp.setLocator(waitForPopUpToOpen(lookupPopUp, existingWindows));		
		lookupPopUp.select();
		
		//We are having timing issues, especially when the lookup window is raised by clicking
		//  PageCreateCase.supportedAssetSerialNum.  So let's take some extra care here...
		Util.sleep(500);

		lookupPopUpPage.populateField("searchBox", inValue);
		lookupPopUpPage.click("goButton");

		//doPopulate is not checking for alerts anymore, so this line is unnecessary
		//lookupPopUpPage.setCheckForAlerts(false);
		
		//Experience has shown that sometimes when we click the first row we get the value that was there
		//  before we initiated the search (and it may not have been a "value" at all - it may have been
		//  the "noRecordsMessage" message).  Perhaps control returns before the search was finished?  We'll
		//  sleep and see, although the possibility remains that the go button did not get clicked properly
		//Util.sleep(1000);
		//Util.sleep(2000);
		
		//We are having timing issues, especially when the lookup window is raised by clicking
		//  PageCreateCase.supportedAssetSerialNum.  So let's take some extra care here...
		if (lookupPopUpPage.waitForElementPresent("firstFoundObject", 5000)) {
			lookupPopUpPage.waitForElementVisible("firstFoundObject", 5000);
			
			Util.sleep(500);
			
			lookupPopUpPage.click("firstFoundObject");
		} else {
			throw new TestDataException("The search in the lookup window did not return any records");
		}
		
		//if (!lookupPopUpPage.isFieldPresent("noRecordsMessage")) {
		//	//Some flavors of the lookup window do not show the "noRecordsMessage" message - they
		//	//  just show nothing
		//	if (lookupPopUpPage.isFieldPresent("firstFoundObject")) {
		//		lookupPopUpPage.click("firstFoundObject");
		//	} else {
		//		throw new TestDataException("The search in the lookup window did not return any records");
		//	}
		//} else {
		//	//If it is later determined that we should just print a warning, and/or pass back a boolean
		//	//  or whatever, be sure to close the window!
		//	throw new TestDataException("The search in the lookup window did not return any records");
		//}
		
		gui.selectWindow(parentWindow);
	}
*/	
/*	private void doPopulateLookupField(Field field, String inValue) throws TestDataException, MetadataException {
		Set<String> existingWindows = null;
		String parentWindow 		= null;
		String locator;
		Window_ lookupPopUp			= EISTestBase.lookupPopUp;
		Page_ lookupPopUpPage		= EISTestBase.lookupPopUpPage;

		//It is assumed that this method was called from doPopulate,
		//  which means that the error message generated here will
		//  be appended to doPopulate's guiErrorMessage string

		parentWindow = getWindowHandle();

		existingWindows = getWindowHandles();
		gui.clickButtonOrLink(field);
		
		locator = waitForPopUpToOpen(lookupPopUp, existingWindows);
		
		//Util.printDebug("**************** In Page.doPopulateLookupField()");
		//Util.printDebug("Parent:   " + parentWindow);
		//Util.printDebug("Existing: " + existingWindows);
		//Util.printDebug("Pop up:   " + locator);
		
		lookupPopUp.setLocator(locator);		
		lookupPopUp.select();
		
		//DEBUG *************
		//We are having timing issues, especially when the lookup window is raised by clicking
		//  PageCreateCase.supportedAssetSerialNum.  So let's take some extra care here...
		//Util.sleep(500);	//old production version
		//Util.sleep(5000);	//debug version
		Util.sleep(2000);	//new production version
		//NOTE that the waitForElementVisible call below is debug code - it was not in the original code, and has
		//  been commented here, to be un-commented the next time we need to debug
		//This is failing on VM.  GUI.waitForElementVisible never sees it as visible. There could be a problem with the
		//  way I am checking - see the comment near the driver.findElement(By.xpath(seleniumLocator)).isDisplayed call.
		//(without sleeping first)
		//lookupPopUpPage.waitForElementVisible("searchBox");
		//*******************

		lookupPopUpPage.populateField("searchBox", inValue);
		lookupPopUpPage.click("goButton");

		//doPopulate is not checking for alerts anymore, so this line is unnecessary
		//lookupPopUpPage.setCheckForAlerts(false);
		
		//Experience has shown that sometimes when we click the first row we get the value that was there
		//  before we initiated the search (and it may not have been a "value" at all - it may have been
		//  the "noRecordsMessage" message).  Perhaps control returns before the search was finished?  We'll
		//  sleep and see, although the possibility remains that the go button did not get clicked properly
		//Util.sleep(1000);
		//Util.sleep(2000);
		
		//We are having timing issues, especially when the lookup window is raised by clicking
		//  PageCreateCase.supportedAssetSerialNum.  So let's take some extra care here...
		
		//NOTE that I changed this code after rewriting waitForElementVisible
		//if (lookupPopUpPage.waitForElementPresent("firstFoundObject", 5000)) {
		//	lookupPopUpPage.waitForElementVisible("firstFoundObject", 5000);
		if (lookupPopUpPage.waitForElementVisible("firstFoundObject")) {
			
			Util.sleep(500);
			
			lookupPopUpPage.click("firstFoundObject");
		} else {
			throw new TestDataException("The search in the lookup window did not return any records");
		}
		
		//!!! We are not making this check anymore, because in some lookups the message is not displayed
		//  when no records are found!
		//if (!lookupPopUpPage.isFieldPresent("noRecordsMessage")) {
		//	//Some flavors of the lookup window do not show the "noRecordsMessage" message - they
		//	//  just show nothing
		//	if (lookupPopUpPage.isFieldPresent("firstFoundObject")) {
		//		lookupPopUpPage.click("firstFoundObject");
		//	} else {
		//		throw new TestDataException("The search in the lookup window did not return any records");
		//	}
		//} else {
		//	//If it is later determined that we should just print a warning, and/or pass back a boolean
		//	//  or whatever, be sure to close the window!
		//	throw new TestDataException("The search in the lookup window did not return any records");
		//}
		
		gui.selectWindow(parentWindow);
	}
*/
	private void doPopulateLookupField(Field field, String inValue) throws TestDataException, MetadataException {
		//It is assumed that this method was called from doPopulate,
		//  which means that the error message generated here will
		//  be appended to doPopulate's guiErrorMessage string

		
		Set<String> existingWindows = null;
		String parentWindow 		= null;
		String locator;
		int numRowsInTable;
		int rowNumber;
		int searchTimeout			= EISConstants.DEFAULT_SEARCH_TIMEOUT;
		
		//Create the appropriate Window_ and Page_ objects and get window handles
		Window_ lookupPopUp			= EISTestBase.lookupPopUp;
		Page_ lookupPopUpPage		= EISTestBase.lookupPopUpPage;
		parentWindow = getWindowHandle();
		existingWindows = getWindowHandles();
		
		//Click Lookup link; set locator of LookupPopUpWindow and select
		gui.clickButtonOrLink(field);
		locator = waitForPopUpToOpen(lookupPopUp, existingWindows);
		lookupPopUp.setLocator(locator);		
		lookupPopUp.select();
		
		
		//VERIFY TYPE OF POPUP WINDOW (frame or non-frame)
		boolean frameTypeLookUp = false;
		List<WebElement> listFrames = driver.findElements(By.tagName("frame"));
		if(!listFrames.isEmpty()) {
			frameTypeLookUp = true;
			
			//debug code
			String debugText = listFrames.size() + " frames are found in lookupPopUp window with ids : ";
			for (WebElement eachFrame : listFrames) {
				debugText+= eachFrame.getAttribute("id") + " ";
			} Util.printDebug(debugText);
		} 
		
		
		//====================================
		// SWITCH TO SEARCHFRAME IF FRAMETYPE
		//====================================
		if(frameTypeLookUp) {
			driver.switchTo().frame("searchFrame");
		}
		
		//Wait for window to finish loading
		lookupPopUpPage.waitForField("searchBox", true);
		
		//Search, if value to search for is provided
		if (!inValue.isEmpty()) {
			lookupPopUpPage.populateField("searchBox", inValue);
			lookupPopUpPage.click("goButton");
		}
	
		
		//====================================
		// SWITCH TO RESULTSFRAME IF FRAMETYPE
		//====================================
		if(frameTypeLookUp) {
			driver.switchTo().defaultContent();
			driver.switchTo().frame("resultsFrame");
		}
		
		//Wait for first result to be found 
		lookupPopUpPage.waitForField("firstFoundObject", true);
		Util.sleep(2000);
		
		//Check if no records are found instead
		if (!lookupPopUpPage.waitForFieldVisible("firstFoundObject", searchTimeout)) {
			throw new TestDataException("The search for "+ inValue +"in the lookup window did not return any records");
		}
		
		//Click on row with value searched for, or first row
		numRowsInTable = lookupPopUpPage.getNumRowsInTable("resultsTable", false);	
		
		Util.sleep(1000);	//newest production version
		lookupPopUpPage.waitForField("nameInResultsTable", true);
		
		//debug code for getting all values in selected column
		String debugText ="Relevant Column: \n";
		for (String eachCell : lookupPopUpPage.getTableColumn("nameInResultsTable")){ 
			debugText += eachCell + " | ";
		} Util.printDebug(debugText);
				
		//nested ifs with code duplication removed
		if (inValue.isEmpty() || (numRowsInTable == 1)) {
			lookupPopUpPage.clickLinkInTable("nameInResultsTable", 0);
		}
		else {
			//int rowNumber1 = lookupPopUpPage.getTableRowNum("nameInResultsTable", inValue); //case insensitive replaced by case sensitive
			rowNumber = lookupPopUpPage.getTableRowNum("nameInResultsTable", inValue, false);
			rowNumber = (rowNumber == -1) ? 0 : rowNumber;
			lookupPopUpPage.clickLinkInTable("nameInResultsTable", rowNumber);
		}
		
		Util.sleep(3000);
		
		//Select Parent window
		gui.selectWindow(parentWindow);
		
		
		//============================
		// THIS DOESN'T WORK
		//============================
    	/*if (!lookupPopUpPage.isFieldPresent("searchBox")) {
	    	Util.printDebug("Handling possible web certificate error...");
	    	
			//int exitCode = lookupPopUpPage.clickLinkInIE(EISConstants.TEST_BASE_PATH + EISConstants.CLICK_LINK_IN_IE_PROCESS, EISConstants.ACCOUNT_LOOKUP_POPUP_URL, EISConstants.CONTINUE_TO_WEBSITE_LINK_TEXT);
			int exitCode = lookupPopUpPage.clickLinkInIE(EISConstants.TEST_BASE_PATH + EISConstants.CLICK_LINK_IN_IE_PROCESS, lookupPopUpPage.getLocation(), EISConstants.CONTINUE_TO_WEBSITE_LINK_TEXT);

			if (exitCode != EISConstants.PROCESS_EXIT_CODE_SUCCESS) {
				Util.printWarning("Error when clicking link on web certificate error page after opening lookup window: " + exitCode);
			} else {
		    	//Clicking the web certificate error link causes a new pop-up to open, so we need to handle it
				lookupPopUp.close();
				
				gui.selectWindow(parentWindow);
				
				existingWindows = getWindowHandles();
				gui.clickButtonOrLink(field);
				
				locator = waitForPopUpToOpen(lookupPopUp, existingWindows);

				lookupPopUp.setLocator(locator);		
				lookupPopUp.select();
			}
			
			Util.printDebug("Handled possible web certificate error");
    	}*/		

		/*if (!lookupPopUpPage.isFieldPresent("searchBox")) {
	    	Util.printDebug("Handling possible web certificate error...");
	    	
			lookupPopUp.close();
			
			gui.selectWindow(parentWindow);
			
			existingWindows = getWindowHandles();
			gui.clickButtonOrLink(field);
			
			locator = waitForPopUpToOpen(lookupPopUp, existingWindows);

			lookupPopUp.setLocator(locator);		
			lookupPopUp.select();
			
			Util.printDebug("Handled possible web certificate error");
    	}*/


    	//Util.sleep(500);	//old production version
		//Util.sleep(5000);	//debug version
		//Util.sleep(2000);	//new production version
		//Util.sleep(1000);	//newest production version
		/** //replaced, shown here for documentation purposes only; code in use above
		 * =====lookupPopUpPage.waitForField("searchBox", true);
		 */
		
		
		//NOTE that the waitForElementVisible call below is debug code - it was not in the original code, and has
		//  been commented here, to be un-commented the next time we need to debug
		//This is failing on VM.  GUI.waitForElementVisible never sees it as visible. There could be a problem with the
		//  way I am checking - see the comment near the driver.findElement(By.xpath(seleniumLocator)).isDisplayed call.
		//(without sleeping first)
		//lookupPopUpPage.waitForElementVisible("searchBox");
		
		/** if (!inValue.isEmpty()) {
		 * lookupPopUpPage.populateField("searchBox", inValue);
		 * lookupPopUpPage.click("goButton");} 
		 */
		
		
//		else{
//			lookupPopUpPage.click("firstFoundObject");
//		}
			
		//doPopulate is not checking for alerts anymore, so this line is unnecessary
		//lookupPopUpPage.setCheckForAlerts(false);
		
		//Experience has shown that sometimes when we click the first row we get the value that was there
		//  before we initiated the search (and it may not have been a "value" at all - it may have been
		//  the "noRecordsMessage" message).  Perhaps control returns before the search was finished?  We'll
		//  sleep and see, although the possibility remains that the go button did not get clicked properly
		//Util.sleep(1000);
		//Util.sleep(2000);
		
		//We are having timing issues, especially when the lookup window is raised by clicking
		//  PageCreateCase.supportedAssetSerialNum.  So let's take some extra care here...
		
		//NOTE that I changed this code after rewriting waitForElementVisible
		//if (lookupPopUpPage.waitForElementPresent("firstFoundObject", 5000)) {
		//	lookupPopUpPage.waitForElementVisible("firstFoundObject", 5000);
		//In some cases (at least in the pop-up we access when adding a DAR approver)
		//  the default search timeout (EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT) is
		//  not long enough.  So let's change it to something much longer
		//if (!lookupPopUpPage.waitForElementVisible("firstFoundObject")) {
		
		
		/**lookupPopUpPage.waitForField("firstFoundObject", true);
		 * Util.sleep(1000);
		 * if (!lookupPopUpPage.waitForFieldVisible("firstFoundObject", searchTimeout)) {
		 * throw new TestDataException("The search in the lookup window did not return any records");}
		 * Util.sleep(1000);
		 */
		
		//If there is only one result, click the "name" column in row 0; otherwise, search the "name"
		//  column for the value that was used in the search.
		//CAUTION:  sometimes the value that was used in the search is not displayed in the "name" column!
		//  An example of this is when searching using CSNs
		
		//Determining the number of results in the table is not an easy task, as the way they are rendered
		//  varies quite a bit.  For example, it is almost impossible to determine whether a table has a
		//  header row (which we can ignore) because an existing header row may not use the thead tag, and
		//  sometimes DATA rows use th tags!  So the best way is to check the number of rows in the results
		//  table before searching, and compare it to the number of rows after searching.  (We are assuming
		//  that if the table has a header row, it will be present before the search.)
		//BUT we can't do that, because often the results table contains a list of results BEFORE the
		//  search!  So, we will assume that the results table ALWAYS contains a header row, and call
		//  getNumRowsInTable to return the number of rows NOT INCLUDING the header row
		/**numRowsInTable = lookupPopUpPage.getNumRowsInTable("resultsTable", false);*/
		//if (numRowsInTable == 1)  {
		//	lookupPopUpPage.clickLinkInTable("nameInResultsTable", 0);			
		////} else if ((numRowsInTable >= 1) && (inValue.toString().length() == 0)) {
		//} else if ((numRowsInTable >= 1) && (inValue.isEmpty())) {
		//	lookupPopUpPage.clickLinkInTable("nameInResultsTable", 0);
		//} else {
		//	lookupPopUpPage.clickLinkInTable("nameInResultsTable", inValue, "nameInResultsTable");
		//}
		
		//The logic here is as follows:
		//	if the passed-in value is empty
		//		there is nothing to search for in the table, so we click the link in the name column in the first row
		//  else (the passed-in value is not empty)
		//		if there is only one row in the table
		//			there is no reason to search the table, so we click the link in the name column in the first row
		//		else (there is more than one row in the table)
		//			search the name column for the passed-in value
		//			if the value is found (rowNumber is not -1)
		//				click the link in the name column of rowNumber
		//			else (the value is not found)
		//				click the link in the name column in the first row
		//TODO:  add code to handle cases where there is a blank row in the table between the header row
		// and the first row of results (see comments in Page_LookupPopUp.properties, at firstFoundObject) 
		
		//DEBUG code!
		//lookupPopUpPage.clickLinkInTable("nameInResultsTable", 1);
		
		
		/**Util.sleep(1000);	//newest production version
		lookupPopUpPage.waitForField("nameInResultsTable", true);
		//==========--------------------------------------------==========
		if (!inValue.isEmpty())  {
			if (numRowsInTable == 1)  {
				lookupPopUpPage.clickLinkInTable("nameInResultsTable", 0);
			} else {
				//If inValue is found, click in that row.  Otherwise, click in the first row.  The
				//  latter action handles cases where we search using a value of one type of thing
				//  (e.g., a serial number), but the search returns a list of a different type of
				//  thing (e.g., asset numbers).  Searching the table will not return a row number,
				//  since we can only search on inValue.  Therefore, all we can do is click in
				//  the first row 
				rowNumber = lookupPopUpPage.getTableRowNum("nameInResultsTable", inValue);
				rowNumber = (rowNumber == -1) ? 0 : rowNumber;
				
				lookupPopUpPage.clickLinkInTable("nameInResultsTable", rowNumber);
			}
		} else {
			lookupPopUpPage.clickLinkInTable("nameInResultsTable", 0);
		}*/
		
		//!!! We are not making this check anymore, because in some lookups the message is not displayed
		//  when no records are found!
		//if (!lookupPopUpPage.isFieldPresent("noRecordsMessage")) {
		//	//Some flavors of the lookup window do not show the "noRecordsMessage" message - they
		//	//  just show nothing
		//	if (lookupPopUpPage.isFieldPresent("firstFoundObject")) {
		//		lookupPopUpPage.click("firstFoundObject");
		//	} else {
		//		throw new TestDataException("The search in the lookup window did not return any records");
		//	}
		//} else {
		//	//If it is later determined that we should just print a warning, and/or pass back a boolean
		//	//  or whatever, be sure to close the window!
		//	throw new TestDataException("The search in the lookup window did not return any records");
		//}
		
		//Sleep a bit to allow the field in the main window to be populated
		/** Util.sleep(3000);
		 * gui.selectWindow(parentWindow);
		 */

		//NOTE that none of this works.  I have addressed the issue involving the fields below in
		//  Case.createInPCPortal().
		
		//Sometimes clicking a link in a lookup results table causes more than one field on the page
		//  in the main window to be populated.  In those cases, sometimes control returns to Page.doPopulate
		//  before one of those fields gets populated, but it goes ahead and populates the rest of the form,
		//  which can cause the field to never get populated.  So we need to sleep here in those cases:
		//if ((name.equalsIgnoreCase("PageCreateCase")) && ((field.getName().equalsIgnoreCase("supportedAssetSerialNum")) || (field.getName().equalsIgnoreCase("supportedAsset")))) {
			//Util.sleep(3000);
			//Can't do this, because it clears the field
			//click("supportedAssetText");
			
			//This has no effect
			//click("supportedProductText");
		//}
	}
	
	@Override
	public int populateInstance(int instance) {
		int numFieldsPopulated = 0;
		List<FieldData> populateTestData = new ArrayList<FieldData>();

		for (FieldDataInstance_ fieldDataInstance : testData) {
			if (fieldDataInstance.getInstance() == instance) {
				populateTestData.add((FieldData) fieldDataInstance.getFieldData().clone());
			}
		}

		try {
			numFieldsPopulated = doPopulate(populateTestData);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return numFieldsPopulated;
	}

	@Override
	public int populateInstance(String instanceName) {
		return populateInstance(testProperties.getInstanceNumber(instanceName));
	}
	
	@Override
	public int populate() {
		return populateInstance(1);
	}

	@Override
	public int populateAllInstance(int instance) {
		return populateInstance(instance);
	}

	@Override
	public int populateAllInstance(String instanceName) {
		return populateAllInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int populateAll() {
		return populateAllInstance(1);
	}

	@Override
	public int populatePageInstance(int instance) {
		return populateInstance(instance);
	}

	@Override
	public int populatePageInstance(String instanceName) {
		return populatePageInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int populatePage() {
		return populatePageInstance(1);
	}

	@Override
	public boolean populateFieldInstance(String fieldName, int instance) {
		//We return a boolean for all methods that click or populate only one field, because if a bad fieldName was passed, it is an error
		int numFieldsPopulated = 0;
		List<FieldData> populateTestData = new ArrayList<FieldData>();
		FieldData_ fieldData;

		fieldData = getTestDataRecord(fieldName, instance);
		if (fieldData != null) {
			populateTestData.add((FieldData) fieldData.clone());

			try {
				numFieldsPopulated = doPopulate(populateTestData);
			} catch (GUIException ge) {
				EISTestBase.fail(ge.getMessage());
			}
		}

		return (numFieldsPopulated == 1);
	}

	@Override
	public boolean populateFieldInstance(String fieldName, String instanceName) {
		return populateFieldInstance(fieldName, testProperties.getInstanceNumber(instanceName));
	}
	
	/**
	 * @Description Type the kyes slowly.
	 * @param fieldName
	 * @param value
	 * @throws Exception
	 */
	public void sendKeysInTextFieldSlowly(String fieldName, String value) throws Exception{
		Field field=getFieldMetadata(fieldName);
		gui.populateTextFieldSlowly(field, value);
	}

	@Override
	public boolean populateField(String fieldName) {
		return populateFieldInstance(fieldName, 1);
	}

	@Override
	public boolean populateField(String fieldName, String value) {
		//We return a boolean for all methods that click or populate only one field, because if a bad fieldName was passed, it is an error
		int numFieldsPopulated = 0;
		List<FieldData> populateTestData = new ArrayList<FieldData>();

		//We create a FieldData object to pass to doPopulate (rather than looking one up) because:
		//	1) there may not be one in the test data (a value to put into a search pick list, for example)
		//	2) the caller wants to, in effect, override the test data
		//	3) the object type (e.g., BUTTON, LINK) does not require any data
		//However, doPopulate WILL still expect to find an associated Field object!
		populateTestData.add(new FieldData(fieldName, value));

		try {
			numFieldsPopulated = doPopulate(populateTestData);
			/*Util.sleep(10000);*/ //Sai: Why this is required here?. This is  expensive operation might slow down the execution performance. Pls do not add static delay in a generic method unless it is sensitive
			
		} catch (GUIException ge) {
            EISTestBase.fail(ge.getMessage());
		}

		return (numFieldsPopulated == 1);
	}

	//***** page-scope verify methods *****************************************
	@Override
	public int verifyInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.ALL);
	}

	@Override
	public int verifyInstance(String instanceName) {
		return verifyInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verify() {
		return verifyInstance(1);
	}

	@Override
	public int verifyAllInstance(int instance) {
		return verifyInstance(instance);
	}

	@Override
	public int verifyAllInstance(String instanceName) {
		return verifyAllInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAll() {
		return verifyAllInstance(1);
	}

	@Override
	public int verifyPageInstance(int instance) {
		return verifyInstance(instance);
	}

	@Override
	public int verifyPageInstance(String instanceName) {
		return verifyPageInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyPage() {
		return verifyPageInstance(1);
	}

	@Override
	public int verifyAllValuesInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.VALUES_ONLY);
	}

	@Override
	public int verifyAllValuesInstance(String instanceName) {
		return verifyAllValuesInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllValues() {
		return verifyAllValuesInstance(1);
	}

	@Override
	public int verifyAllExistenceInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.EXISTENCE_ONLY);
	}

	@Override
	public int verifyAllExistenceInstance(String instanceName) {
		return verifyAllExistenceInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllExistence() {
		return verifyAllExistenceInstance(1);
	}

	@Override
	public int verifyAllExistsInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.EXISTS_ONLY);
	}

	@Override
	public int verifyAllExistsInstance(String instanceName) {
		return verifyAllExistsInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllExists() {
		return verifyAllExistsInstance(1);
	}

	@Override
	public int verifyAllNotExistsInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.NOT_EXISTS_ONLY);
	}

	@Override
	public int verifyAllNotExistsInstance(String instanceName) {
		return verifyAllNotExistsInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllNotExists() {
		return verifyAllNotExistsInstance(1);
	}

	@Override
	public int verifyAllVisibilityInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.VISIBILITY_ONLY);
	}

	@Override
	public int verifyAllVisibilityInstance(String instanceName) {
		return verifyAllVisibilityInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllVisibility() {
		return verifyAllVisibilityInstance(1);
	}

	@Override
	public int verifyAllVisibleInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.VISIBLE_ONLY);
	}

	@Override
	public int verifyAllVisibleInstance(String instanceName) {
		return verifyAllVisibleInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllVisibleInstance() {
		return verifyAllVisibleInstance(1);
	}

	@Override
	public int verifyAllNotVisibleInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.NOT_VISIBLE_ONLY);
	}

	@Override
	public int verifyAllNotVisibleInstance(String instanceName) {
		return verifyAllNotVisibleInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllNotVisible() {
		return verifyAllNotVisibleInstance(1);
	}

	@Override
	public int verifyAllNullnessInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.NULLNESS_ONLY);
	}

	@Override
	public int verifyAllNullnessInstance(String instanceName) {
		return verifyAllNullnessInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllNullness() {
		return verifyAllNullnessInstance(1);
	}

	@Override
	public int verifyAllNullInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.NULL_ONLY);
	}

	@Override
	public int verifyAllNullInstance(String instanceName) {
		return verifyAllNullInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllNullInstance() {
		return verifyAllNullInstance(1);
	}

	@Override
	public int verifyAllNotNullInstance(int instance) {
		return doVerify(instance, EISConstants.PageVerificationCategoryType.NOT_NULL_ONLY);
	}

	@Override
	public int verifyAllNotNullInstance(String instanceName) {
		return verifyAllNotNullInstance(testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public int verifyAllNotNull() {
		return verifyAllNotNullInstance(1);
	}
		
/*	private int doVerify(int instance, EISConstants.PageVerificationCategoryType pageVerificationCategory) {
		List<String> verifyFieldNames = new ArrayList<String>(getVerifyFieldNames(instance, pageVerificationCategory));

		for (String verifyFieldName : verifyFieldNames) {
			verifyFieldInstance(verifyFieldName, instance);
		}

		return unverifiedFieldNames.size();
	}
*/
	
	private int doVerify(int instance, EISConstants.PageVerificationCategoryType pageVerificationCategory) {
		List<String> verifyFieldNames = new ArrayList<String>(getVerifyFieldNames(instance, pageVerificationCategory));

		usingInfoPanelCache = true;
		infoPanelCache.clear();

		for (String verifyFieldName : verifyFieldNames) {
			verifyFieldInstance(verifyFieldName, instance);
		}

		usingInfoPanelCache = false;
		infoPanelCache.clear();

		return unverifiedFieldNames.size();
	}
	

	private List<String> getVerifyFieldNames(int instance, EISConstants.PageVerificationCategoryType pageVerificationCategory) {
		//Create an initial list of eligible verification fields to verify.  To be considered eligible a verification field
		//  must meet one of these three conditions in respect to its EISConstants.ParameterizedVerificationDataValueType
		//  and value:
		//  1) have an EISConstants.ParameterizedVerificationDataValueType value of VERIFY_EXISTS, VERIFY_NOT_EXISTS, VERIFY_VISIBLE, VERIFY_NOT_VISIBLE, VERIFY_NULL, or VERIFY_NOT_NULL
		//  2) have an EISConstants.ParameterizedVerificationDataValueType value of VERIFY_CONSTANT
		//  3) have an EISConstants.ParameterizedVerificationDataValueType value of VERIFY, VERIFY_VALUE, VERIFY_VALUES, or VERIFY_VALUE_SET
		//     AND
		//     have a value (i.e., not an empty string)
		//	The goal of these conditions is a list that excludes verification fields that require a value but do not have one
		//    at run-time.  The value (aka "expected result") of such a field is determined by the test method logic, and the
		//    verification of the field is accomplished via a direct call to verifyField(String fieldName, String expected) or
		//    a similar method
		//
		//  In addition, to be considered eligible a verification field must meet ONE of these two conditions in respect to
		//  the Field instance to which it maps:
		//   1) reference a Field type with a hasParameterizedLocator value of FALSE (i.e., the Field is not a table or related list)
		//   OR
		//   2) if the EISConstants.ParameterizedVerificationDataValueType value IS NOT VERIFY_EXISTS, VERIFY_NOT_EXISTS, VERIFY_VISIBLE, VERIFY_NOT_VISIBLE, VERIFY_NULL, or VERIFY_NOT_NULL,
		//      reference a Field type with a hasParameterizedLocator value of TRUE
		//      AND
		//      an EISConstants.ParameterizedLocatorType value of INFO_PANEL_LOOKUP
		//	The goal of these conditions is a list that excludes verification fields that obtain their expected result from
		//    a row/column table, such as a related list.  Since the row number cannot be specified in the field's value
		//    property at run-time, verification of such fields is accomplished by calls to verifyRelatedListCell or
		//    verifyTableCell or their variants
		//
		//NOTE that the conditions described above must be modified as we add new members to either the
		//  EISConstants.ParameterizedVerificationDataValueType or EISConstants.ParameterizedLocatorType
		//  enumerated type
		//
		//Eligible fields will be added to the verifyFieldNames list, the members of which will be passed to
		//  verifyField(String fieldName, int instance).
		//Ineligible fields will be added to the unverifiedFieldNames list, and the size of that list will be returned
		//  by this method.  The contents and size of that list are accessible via the getUnverifiedFieldNames() and
		//  getNumUnverifiedFieldNames() methods.
		//
		//NOTE that when we call getFieldMetadata to access the Field instance associated with a fieldName, we DO NOT
		//  throw an exception if it is not found!  In fact, we add it the list of fieldNames that will be passed to
		//  verifyField(String fieldName, int instance)!  The reason is that we don't want to duplicate the error
		//  handling that is done in doVerifyField()

		List<FieldData> instanceVerificationData = new ArrayList<FieldData>();
		List<String> verifyFieldNames = new ArrayList<String>();
		List<EISConstants.ParameterizedVerificationDataValueType> verificationTypes = new ArrayList<EISConstants.ParameterizedVerificationDataValueType>();
		Field field;
		String fieldName;
		boolean hasParameterizedLocator;

		unverifiedFieldNames.clear();

		//First get all of the verification fields for the given instance
		for (FieldDataInstance_ fieldDataInstance : verificationData) {
			if (fieldDataInstance.getInstance() == instance) {
				instanceVerificationData.add((FieldData) fieldDataInstance.getFieldData().clone());
			}
		}

		if (!instanceVerificationData.isEmpty()) {
			switch (pageVerificationCategory) {
				case ALL:				{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_VALUE);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_VALUES);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_VALUE_SET);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_CONSTANT);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NULL);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NOT_NULL);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_EXISTS);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NOT_EXISTS);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_VISIBLE);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NOT_VISIBLE);
					break;
				}
				case VALUES_ONLY:		{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_VALUE);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_VALUES);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_VALUE_SET);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_CONSTANT);
					break;
				}
				case EXISTENCE_ONLY:	{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_EXISTS);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NOT_EXISTS);
					break;
				}
				case EXISTS_ONLY:		{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_EXISTS);
					break;
				}
				case NOT_EXISTS_ONLY:	{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NOT_EXISTS);
					break;
				}
				case VISIBILITY_ONLY:	{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_VISIBLE);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NOT_VISIBLE);
					break;
				}
				case VISIBLE_ONLY:		{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_VISIBLE);
					break;
				}
				case NOT_VISIBLE_ONLY:	{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NOT_VISIBLE);
					break;
				}
				case NULLNESS_ONLY:	{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NULL);
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NOT_NULL);
					break;
				}
				case NULL_ONLY:		{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NULL);
					break;
				}
				case NOT_NULL_ONLY:	{
					verificationTypes.add(EISConstants.ParameterizedVerificationDataValueType.VERIFY_NOT_NULL);
					break;
				}
				default:	{
					EISTestBase.fail("Unhandled member of common.EISConstants.PageVerificationCategoryType enumerated type: " + pageVerificationCategory);
				}
			}

			for (FieldData_ fieldData : instanceVerificationData) {
				fieldName = fieldData.getName();

				if (!verificationTypes.contains(fieldData.getParameterizedVerificationDataValueType())) {
					unverifiedFieldNames.add(fieldName);

					continue;
				}

				try {
					field = getFieldMetadata(fieldName);
				} catch (MetadataException me) {
					//If the associated Field instance IS NOT found, add it to the list and let the error be handled
					//  down the call chain (see method comments for the logic behind this)
					verifyFieldNames.add(fieldName);

					continue;
				}

				//The verification field has an associated Field instance; now let's determine its eligibility			
				hasParameterizedLocator = field.isLocatorParameterized();

				//Handle VERIFY_EXISTS, VERIFY_NOT_EXISTS, VERIFY_VISIBLE, and VERIFY_NOT_VISIBLE types
				if ((isExistenceTypeField(fieldName, instance)) || (isVisibilityTypeField(fieldName, instance))) {
					if (!hasParameterizedLocator) {
						verifyFieldNames.add(fieldName);

						continue;
					}

					unverifiedFieldNames.add(fieldName);

					continue;
				}

				//Handle other verification types, starting with the parameterized locator type (if any) of the
				//  associated Field instance
				if (hasParameterizedLocator) {
					if (field.getParameterizedLocatorType() != EISConstants.ParameterizedLocatorType.INFO_PANEL_LOOKUP) {
						unverifiedFieldNames.add(fieldName);

						continue;
					}
				}

				//We now have a verification field that meets all of the qualifications except a non-blank value;
				//  let's check that
				if (!getVerificationDataInstanceValue(fieldName, instance).isEmpty()) {
					verifyFieldNames.add(fieldName);
				} else {
					unverifiedFieldNames.add(fieldName);
				}
			}
		}

		return verifyFieldNames;
	}
	//*************************************************************************

	//***** verifyFieldExists and verifyFieldNotExists methods ****************
	//We don't specify instance when calling verifyFieldExists, verifyFieldNotExists, or
	//  verifyFieldExistence because they are meant to be called directly by the test
	//  method, in which case the instance is immaterial anyway:  the caller wants to
	//  know if the field exists (or not) at that point in time
	//
	//If the verification data is set up like this:
	//	PageMyWonderfulPage.refineResultsPanel				=VERIFY_EXISTS[]##1
	//	PageMyWonderfulPage.refineResultsPanel				=VERIFY_NOT_EXISTS[]##2
	//then these two calls to verifyField (or a page-scope equivalent) will accomplish the user's intent:
	//  PageMyWonderfulPage.verifyField("refineResultsPanel", 1)
	//  PageMyWonderfulPage.verifyField("refineResultsPanel", 2)

	@Override
	public boolean verifyFieldExists(String fieldName) {
		return verifyFieldExistence(fieldName, true);
	}

	@Override
	public boolean verifyFieldNotExists(String fieldName) {
		return verifyFieldExistence(fieldName, false);
	}

	@Override
	public boolean verifyFieldExistence(String fieldName, boolean expected) {
		boolean success = false;

		try {
			success = doVerifyFieldExistence(fieldName, expected);
		} catch (MetadataException me) {
            EISTestBase.fail(me.getMessage());
		}

		return success;
	}
	@Override
	public boolean checkFieldExistence(String fieldName) {
		boolean success = false;
		try {
			success =  doCheckFieldExistence(fieldName);
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	

	private boolean doVerifyFieldExistence(String fieldName, boolean expected) throws MetadataException {
		boolean result;
		boolean actual = false;
		Field field;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		if (!field.isLocatorParameterized()) {
			actual = isFieldPresent(fieldName);
		} else {
			throw new MetadataException("Error while verifying the existence or non-existence of the GUI element '" + fieldName + "' on the page '" + name + "': Fields whose locators specify a table cannot be checked for existence");
		}

		result = EISTestBase.assertFieldExistenceWithFlags(name, fieldName, actual, expected);

		return result;
	}
	
	private boolean doCheckFieldExistence(String fieldName) throws MetadataException {
		boolean actual = false;
		Field field;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		if (!field.isLocatorParameterized()) {
			actual = isFieldPresent(fieldName);
		} else {
			throw new MetadataException("Error while verifying the existence or non-existence of the GUI element '" + fieldName + "' on the page '" + name + "': Fields whose locators specify a table cannot be checked for existence");
		}

		return actual;
	}
	//*************************************************************************

	//***** verifyFieldVisible and verifyFieldNotVisible methods **************
	//We don't specify instance when calling verifyFieldVisible, verifyFieldNotVisible,
	//  or verifyFieldVisibility because they are meant to be called directly by the test
	//  method, in which case the instance is immaterial anyway:  the caller wants to
	//  know if the field is visible (or not) at that point in time
	//
	//If the verification data is set up like this:
	//	PageMyWonderfulPage.refineResultsPanel				=VERIFY_VISIBLE[]##1
	//	PageMyWonderfulPage.refineResultsPanel				=VERIFY_NOT_VISIBLE[]##2
	//then these two calls to verifyField (or a page-scope equivalent) will accomplish the user's intent:
	//  PageMyWonderfulPage.verifyField("refineResultsPanel", 1)
	//  PageMyWonderfulPage.verifyField("refineResultsPanel", 2)

	@Override
	public boolean verifyFieldVisible(String fieldName) {
		return verifyFieldVisibility(fieldName, true);
	}

	@Override
	public boolean verifyFieldNotVisible(String fieldName) {
		return verifyFieldVisibility(fieldName, false);
	}

	@Override
	public boolean verifyFieldVisibility(String fieldName, boolean expected) {
		boolean success = false;

		try {
			success = doVerifyFieldVisibility(fieldName, expected);
		} catch (MetadataException me) {
            EISTestBase.fail(me.getMessage());
		}

		return success;
	}

	private boolean doVerifyFieldVisibility(String fieldName, boolean expected) throws MetadataException {
		boolean result;
		boolean actual = false;
		Field field;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		if (!field.isLocatorParameterized()) {
			actual = isFieldVisible(fieldName);
		} else {
			throw new MetadataException("Error while verifying the visibility or non-visibility of the GUI element '" + fieldName + "' on the page '" + name + "': Fields whose locators specify a table cannot be checked for visibility");
		}

		result = EISTestBase.assertFieldVisibilityWithFlags(name, fieldName, actual, expected);

		return result;
	}
	//*************************************************************************
	
	//***** verifyFieldNull and verifyFieldNotNull methods ********************
	//We don't specify instance when calling verifyFieldNull or verifyFieldNotNull
	//  because they are meant to be called directly by the test method, in which
	//  case the instance is immaterial anyway:  the caller wants to know if the
	//  field is null (or not) at that point in time
	//
	//If the verification data is set up like this:
	//	PageMyWonderfulPage.refineResultsPanel				=VERIFY_NULL[]##1
	//	PageMyWonderfulPage.refineResultsPanel				=VERIFY_NOT_NULL[]##2
	//then these two calls to verifyField (or a page-scope equivalent) will accomplish the user's intent:
	//  PageMyWonderfulPage.verifyField("refineResultsPanel", 1)
	//  PageMyWonderfulPage.verifyField("refineResultsPanel", 2)

	@Override
	public boolean verifyFieldNull(String fieldName) {
		return verifyFieldNullness(fieldName, true);
	}

	@Override
	public boolean verifyFieldNotNull(String fieldName) {
		return verifyFieldNullness(fieldName, false);
	}

	@Override
	public boolean verifyFieldNullness(String fieldName, boolean expected) {
		boolean success = false;

		try {
			success = doVerifyFieldNullness(fieldName, expected);
		} catch (MetadataException me) {
            EISTestBase.fail(me.getMessage());
		}

		return success;
	}

	private boolean doVerifyFieldNullness(String fieldName, boolean expected) throws MetadataException {
		boolean result;
		boolean actual = false;
		String value;
		Field field;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		if ((field.getType() == EISConstants.FieldType.DATE) || (field.getType() == EISConstants.FieldType.TEXT) || (field.getType() == EISConstants.FieldType.READ_ONLY) || (field.getType() == EISConstants.FieldType.READ_ONLY_DATE)) {
			value = getActualValue(fieldName);
			actual = value.isEmpty() ? true : false;
		} else {
			throw new MetadataException("Error while verifying the null or non-null value of the GUI element '" + fieldName + "' on the page '" + name + "': Only Fields of the type EISConstants.FieldType.TEXT, EISConstants.FieldType.DATE, EISConstants.FieldType.READ_ONLY, or EISConstants.FieldType.READ_ONLY_DATE can be checked for 'nullness'");
		}

		result = EISTestBase.assertFieldNullnessWithFlags(name, fieldName, actual, expected);

		return result;
	}
	//*************************************************************************
	
	
	//***** verifyField methods ***********************************************
	@Override
	public boolean verifyFieldInstance(String fieldName, int instance) {
		boolean success = false;
		String expected = getVerificationDataInstanceValue(fieldName, instance);

		if (isExistenceTypeField(fieldName, instance)) {
			EISConstants.FieldExistenceCheckType fieldExistenceCheckType = EISConstants.FieldExistenceCheckType.valueOf(expected);
			switch (fieldExistenceCheckType) {
				case YES:	{
					success = verifyFieldExists(fieldName);
					break;
				}
				case NO:	{
					success = verifyFieldNotExists(fieldName);
					break;
				}
				default:	{
					EISTestBase.fail("Unhandled member of common.EISConstants.FieldExistenceCheckType enumerated type: " + fieldExistenceCheckType);
				}
			}
		} else if (isVisibilityTypeField(fieldName, instance)) {
			EISConstants.FieldVisibilityCheckType fieldVisibilityCheckType = EISConstants.FieldVisibilityCheckType.valueOf(expected);
			switch (fieldVisibilityCheckType) {
				case YES:	{
					success = verifyFieldVisible(fieldName);
					break;
				}
				case NO:	{
					success = verifyFieldNotVisible(fieldName);
					break;
				}
				default:	{
					EISTestBase.fail("Unhandled member of common.EISConstants.FieldVisibilityCheckType enumerated type: " + fieldVisibilityCheckType);
				}
			}
		} else if (isNullnessTypeField(fieldName, instance)) {
			EISConstants.FieldNullnessCheckType fieldNullnessCheckType = EISConstants.FieldNullnessCheckType.valueOf(expected);
			switch (fieldNullnessCheckType) {
				case YES:	{
					success = verifyFieldNull(fieldName);
					break;
				}
				case NO:	{
					success = verifyFieldNotNull(fieldName);
					break;
				}
				default:	{
					EISTestBase.fail("Unhandled member of common.EISConstants.FieldNullnessCheckType enumerated type: " + fieldNullnessCheckType);
				}
			}
		} else {
			FieldData_ fieldData = getVerificationDataRecord(fieldName, instance);

			EISConstants.ParameterizedVerificationDataValueType parameterizedVerificationDataValueType = fieldData.getParameterizedVerificationDataValueType();
			switch (parameterizedVerificationDataValueType) {
				case VERIFY:
				case VERIFY_VALUE:
				case VERIFY_CONSTANT:	{
					success = verifyField(fieldName, expected);
					break;
				}
				case VERIFY_VALUES:
				case VERIFY_VALUE_SET:	{
					success = verifyField(fieldName, fieldData.getValueSet());
					break;
				}
				default:	{
					EISTestBase.fail("Unhandled member of common.EISConstants.ParameterizedVerificationDataValueType enumerated type: " + parameterizedVerificationDataValueType);
				}
			}
		}
		
		return success;
	}

	@Override
	public boolean verifyFieldInstance(String fieldName, String instanceName) {
		return verifyFieldInstance(fieldName, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public boolean verifyField(String fieldName) {
		return verifyFieldInstance(fieldName, 1);
	}

	@Override
	public boolean verifyField(String fieldName, String expected) {
		boolean success = false;

		try {
			success = doVerifyField(fieldName, expected);
		} catch (MetadataException me) {
            EISTestBase.fail(me.getMessage());
		}

		return success;
	}

	@Override
	public boolean verifyField(String fieldName, String... expecteds) {
		boolean success = false;

		try {
			success = doVerifyField(fieldName, expecteds);
		} catch (MetadataException me) {
            EISTestBase.fail(me.getMessage());
		}

		return success;
	}

	@Override
	public boolean verifyField(String fieldName, List<String> expecteds) {
		return verifyField(fieldName, expecteds.toArray(new String[expecteds.size()]));
	}

	//NOTE that this is NOT a standard verify method.  It is not mapped to a member of
	//  EISConstants.ParameterizedVerificationDataValueType
	@Override
	public boolean verifyFieldStartsWith(String fieldName, String inExpected) {
		String expected = inExpected;
		String actual = "";
		boolean startsWith;
		boolean success;

		try {
			actual = getActualValue(fieldName);
		} catch (MetadataException me) {
            EISTestBase.fail(me.getMessage());
		}
		
		if (!EISTestBase.isVerifyCaseSensitive()) {
			actual = actual.toUpperCase();
			expected = expected.toUpperCase();
		}
		
		startsWith = actual.startsWith(expected);

		success = EISTestBase.assertTrueWithFlags(name, fieldName, "starts with '" + inExpected + "'", startsWith);		

		return success;
	}
	
	

	//NOTE that this is NOT a standard verify method.  It is not mapped to a member of
	//  EISConstants.ParameterizedVerificationDataValueType
	@Override
	public boolean verifyFieldContains(String fieldName, String inExpected) {
		boolean doesContain;
		boolean success;
		
		doesContain = isFieldContains(fieldName, inExpected);
		success = EISTestBase.assertTrueWithFlags(name, fieldName, "contains '" + inExpected + "'", doesContain);		

		return success;
	}
	
	public boolean isFieldContains(String fieldName, String inExpected) {
		String expected = inExpected;
		String actual = "";

		try {
			actual = getActualValue(fieldName);
		} catch (MetadataException me) {
            EISTestBase.fail(me.getMessage());
		}
		
		if (!EISTestBase.isVerifyCaseSensitive()) {
			actual = actual.toUpperCase();
			expected = expected.toUpperCase();
		}
		
		return actual.contains(expected);
	}
	
	
	private boolean isExistenceTypeField(String fieldName, int instance) {
		//Determine whether we are to verify the existence/non-existence of a field, or its contents
		boolean isVerifyingExistence = false;
		FieldData_ fieldData;

		fieldData = getVerificationDataRecord(fieldName, instance);
		if (fieldData != null) {
			switch (fieldData.getParameterizedVerificationDataValueType()) {
				case VERIFY_EXISTS:
				case VERIFY_NOT_EXISTS:	{
					isVerifyingExistence = true;
					break;
				}
				default:	{
					isVerifyingExistence = false;
					break;
				}
			}
		}

		return isVerifyingExistence;
	}

	private boolean isVisibilityTypeField(String fieldName, int instance) {
		//Determine whether we are to verify the visibility/non-visibility of a field, or its contents
		boolean isVerifyingVisibility = false;
		FieldData_ fieldData;

		fieldData = getVerificationDataRecord(fieldName, instance);
		if (fieldData != null) {
			switch (fieldData.getParameterizedVerificationDataValueType()) {
				case VERIFY_VISIBLE:
				case VERIFY_NOT_VISIBLE:	{
					isVerifyingVisibility = true;
					break;
				}
				default:	{
					isVerifyingVisibility = false;
					break;
				}
			}
		}

		return isVerifyingVisibility;
	}

	private boolean isNullnessTypeField(String fieldName, int instance) {
		//Determine whether we are to verify the nullness/non-nullness of a field, or its contents
		boolean isVerifyingNullness = false;
		FieldData_ fieldData;

		fieldData = getVerificationDataRecord(fieldName, instance);
		if (fieldData != null) {
			switch (fieldData.getParameterizedVerificationDataValueType()) {
				case VERIFY_NULL:
				case VERIFY_NOT_NULL:	{
					isVerifyingNullness = true;
					break;
				}
				default:	{
					isVerifyingNullness = false;
					break;
				}
			}
		}

		return isVerifyingNullness;
	}

	private boolean doVerifyField(String fieldName, String inExpected) throws MetadataException {
		//This method can handle fields with "normal" locators, as well as those with INFO_PANEL_LOOKUP locators, because
		//  accessing those GUI elements does not required a row number.  Because of that, the verifyInfoPanelCell
		//  methods call this method
		boolean result;
		String actual;
		String expected = inExpected;
		Field field;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while verifying the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		actual = getActualValue(fieldName);
		
		switch (field.getType()) {
			//In the case of radio buttons and check boxes, our GUI scraping method returns either "true" or "false" for
			//  actual, so we need to convert expected to one of those for our assert to work.  And we'll upper-case both
			//  actual and expected, just in case the verifyCaseSensitive flag has been set.
			//NOTE that if expected does not resolve to true, we set it to false - we can't know what the user intended
			//  beyond the values that are checked by Util.isCheckValue and Util.isUncheckValue so we must default to false
			case CHECKBOX:
			case RADIOBUTTON:		{
				if (Util.isCheckValue(expected)) {
					expected = Util.isCheckValue(expected) ? "TRUE" : "FALSE";
				}
	
				actual = actual.toUpperCase();
	
				break;
			}
			//If actual came from a DATE element or a "normal" READ_ONLY_DATE element, it was formatted in
			//  gui.getTextFromReadOnlyDateElement() or gui.getValueFromDateField().  However, if actual
			//  came from a READ_ONLY_DATE or DATE element within a INFO_PANEL_LOOKUP element, it was not
			//  formatted, so let's do that here. (In either case, expected needs to be formatted)
			case DATE:
			case READ_ONLY_DATE:	{
				actual = Util.formatDate(actual);
				expected = Util.formatDate(expected);
	
				break;
			}
			default:
		}

		result = EISTestBase.assertEqualsWithFlags(name, fieldName, actual, expected);

		return result;
	}

	private boolean doVerifyField(String fieldName, String... inExpecteds) throws MetadataException {
		//This method can handle fields with "normal" locators, as well as those with INFO_PANEL_LOOKUP locators, because
		//  accessing those GUI elements does not required a row number.  Because of that, the verifyInfoPanelCell
		//  methods call this method
		boolean result;
		String actual;
		String[] expecteds = inExpecteds;
		Field field;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while verifying the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}

		actual = getActualValue(fieldName);

		switch (field.getType()) {
			//In the case of radio buttons and check boxes, our GUI scraping method returns either "true" or "false" for
			//  actual, so we need to convert expected to one of those for our assert to work.  And we'll upper-case both
			//  actual and expected, just in case the verifyCaseSensitive flag has been set.
			//NOTE that if expected does not resolve to true, we set it to false - we can't know what the user intended
			//  beyond the values that are checked by Util.isCheckValue and Util.isUncheckValue so we must default to false
			case CHECKBOX:
			case RADIOBUTTON:	{
				actual = actual.toUpperCase();
				
				ListIterator<String> expectedItr = Arrays.asList(expecteds).listIterator();
				while (expectedItr.hasNext()) {
					String expected = expectedItr.next().trim();
					if (Util.isCheckValue(expected)) {
						expected = Util.isCheckValue(expected) ? "TRUE" : "FALSE";
					}
				}
	
				break;
			}
			//If actual came from a DATE element or a "normal" READ_ONLY_DATE element, it was formatted in
			//  gui.getTextFromReadOnlyDateElement() or gui.getValueFromDateField().  However, if actual
			//  came from a READ_ONLY_DATE or DATE element within a INFO_PANEL_LOOKUP element, it was not
			//  formatted, so let's do that here. (In either case, expected needs to be formatted)
			case DATE:
			case READ_ONLY_DATE:	{
				actual = Util.formatDate(actual);
				
				ListIterator<String> expectedItr = Arrays.asList(expecteds).listIterator();
				while (expectedItr.hasNext()) {
					String expected = expectedItr.next().trim();
					expected = Util.formatDate(expected);
				}
	
				break;
			}
			default:
		}

		result = EISTestBase.assertEqualsWithFlags(name, fieldName, actual, expecteds);

		return result;
	}

	private String getActualValue(String fieldName) throws MetadataException {
		//This method can handle fields with "normal" locators, as well as those with INFO_PANEL_LOOKUP locators, because
		//  accessing those GUI elements does not required a row number.  Because of that, the verifyInfoPanelCell
		//  methods call this method
		String actual;
		Field field;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		if (field.isLocatorParameterized()) {
			EISConstants.ParameterizedLocatorType parameterizedLocatorType = field.getParameterizedLocatorType();
			switch (parameterizedLocatorType) {
				case INFO_PANEL_LOOKUP:		{
					try {
						actual = doGetInfoPanelCell(field.getLocatorParmsSets());
					} catch (MetadataException me) {
						throw new MetadataException("Error while verifying the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
					}

					break;
				}
				case TABLE_CELL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + fieldName + "' on the page '" + name + "': The locator type is " + field.getParameterizedLocatorType().toString() + "; verify such fields by calling Page.verifyTableCell(fieldName, rowNumber) or Page.verifyTableCell(fieldName, rowNumber, expected)");
				}
				case RELATED_LIST_CELL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + fieldName + "' on the page '" + name + "': The locator type is " + field.getParameterizedLocatorType().toString() + "; verify such fields by calling Page.verifyRelatedListCell(fieldName, rowNumber) or Page.verifyRelatedListCell(fieldName, rowNumber, expected)");
				}
				default:	{
					throw new MetadataException("Unhandled member of common.EISConstants.ParameterizedLocatorType enumerated type: " + parameterizedLocatorType);
				}
			}
		} else {
			//It is a standard locator, so just scrape the GUI in the usual way
			actual = getValueFromGUI(fieldName);
		}

		return actual;
	}
	//*************************************************************************

	//***** verifyInfoPanelCell methods ***************************************
	//Note that these are convenience methods.  verifyField can be called instead but,
	//  given the existence of the verifyTableCell and verifyRelatedListCell methods,
	//  the user will assume the existence of methods with this name
	@Override
	public boolean verifyInfoPanelCellInstance(String fieldName, int instance) {
		return verifyField(fieldName, getVerificationDataInstanceValue(fieldName, instance));
	}

	@Override
	public boolean verifyInfoPanelCellInstance(String fieldName, String instanceName) {
		return verifyInfoPanelCellInstance(fieldName, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public boolean verifyInfoPanelCell(String fieldName) {
		return verifyInfoPanelCellInstance(fieldName, 1);
	}

	@Override
	public boolean verifyInfoPanelCell(String fieldName, String expected) {
		return verifyField(fieldName, expected);
	}

	@Override
	public boolean verifyInfoPanelCell(String fieldName, String... expecteds) {
		return verifyField(fieldName, expecteds);
	}

	@Override
	public boolean verifyInfoPanelCell(String fieldName, List<String> expecteds) {
		return verifyField(fieldName, expecteds.toArray(new String[expecteds.size()]));
	}
	//*************************************************************************

	/* TODO  Think about eliminating the distinction between related lists and regular tables as far
		as method names are concerned.  Perhaps the user does not care (or even know) whether the data
	  	is in a related list or regular table?

		Also, keep in mind that a lot of this stuff will change once I implement the enhanced (multi-row)
	  	table access and verification strategy!!! (see notes of 12/03/2010)
	 */	

	//***** verifyTableCell methods *******************************************
	@Override
	public boolean verifyTableCellInstance(String columnFieldName, int rowNumber, int instance) {
		boolean success = false;
		String expected = getVerificationDataInstanceValue(columnFieldName, instance);
	
		FieldData_ fieldData = getVerificationDataRecord(columnFieldName, instance);
		
		EISConstants.ParameterizedVerificationDataValueType parameterizedVerificationDataValueType = fieldData.getParameterizedVerificationDataValueType();
		switch (parameterizedVerificationDataValueType) {
			case VERIFY:
			case VERIFY_VALUE:
			case VERIFY_CONSTANT:	{
				success = verifyTableCell(columnFieldName, rowNumber, expected);
				break;
			}
			case VERIFY_VALUES:
			case VERIFY_VALUE_SET:	{
				success = verifyTableCell(columnFieldName, rowNumber, fieldData.getValueSet());
				break;
			}
			default:				{
				EISTestBase.fail("Unhandled member of common.EISConstants.ParameterizedVerificationDataValueType enumerated type: " + parameterizedVerificationDataValueType);
			}
		}

		return success;
	}

	@Override
	public boolean verifyTableCellInstance(String columnFieldName, int rowNumber, String instanceName) {
		return verifyTableCellInstance(columnFieldName, rowNumber, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public boolean verifyTableCell(String columnFieldName, int rowNumber) {
		return verifyTableCellInstance(columnFieldName, rowNumber, 1);
	}

	@Override
	public boolean verifyTableCell(String columnFieldName, int rowNumber, String expected) {
		boolean success = false;

		try {
			success = doVerifyTableCell(columnFieldName, rowNumber, expected);
		} catch (GUIException ge) {
            EISTestBase.fail(ge.getMessage());
		}

		return success;
	}

	@Override
	public boolean verifyTableCell(String columnFieldName, int rowNumber, String... expecteds) {
		boolean success = false;

		try {
			success = doVerifyTableCell(columnFieldName, rowNumber, expecteds);
		} catch (GUIException ge) {
            EISTestBase.fail(ge.getMessage());
		}

		return success;
	}

	@Override
	public boolean verifyTableCell(String columnFieldName, int rowNumber, List<String> expecteds) {
		return verifyTableCell(columnFieldName, rowNumber, expecteds.toArray(new String[expecteds.size()]));
	}

	private boolean doVerifyTableCell(String columnFieldName, int rowNumber, String inExpected) throws GUIException {
		boolean result;
		List<String> locatorParms;
		String actual;
		String expected = inExpected;
		Field field;
		
		try {
			field = getFieldMetadata(columnFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + columnFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		if (field.isLocatorParameterized()) {
			EISConstants.ParameterizedLocatorType parameterizedLocatorType = field.getParameterizedLocatorType();
			switch (parameterizedLocatorType) {
				case TABLE_CELL_LOOKUP:		{
					//Parms are the name of the Field object that contains locator(s) to the table, and column number
					locatorParms = field.getLocatorParmsSets().get(0);
					try {
						actual = doGetTableCell(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), rowNumber);
					} catch (GUIException ge) {
						throw new GUIException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': " + ge.getMessage());
					}

					break;
				}
				case INFO_PANEL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': The locator type is " + parameterizedLocatorType + "; verify such fields by calling Page.verifyInfoPanelCell(fieldName) or Page.verifyInfoPanelCell(fieldName, expected)");
				}
				case RELATED_LIST_CELL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': The locator type is " + parameterizedLocatorType + "; verify such fields by calling Page.verifyRelatedListCell(columnfieldName, rowNumber) or Page.verifyRelatedListCell(columnfieldName, rowNumber, expected)");
				}
				default:	{
					throw new MetadataException("Unhandled member of common.EISConstants.ParameterizedLocatorType enumerated type: " + parameterizedLocatorType);
				}
			}
		} else {
			throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': This is a standard GUI element - it does not live in a table; verify such fields by calling Page.verifyField(fieldName) or Page.verifyField(fieldName, expected)");
		}

		switch (field.getType()) {
			case DATE:
			case READ_ONLY_DATE:	{
				actual = Util.formatDate(actual);
				expected = Util.formatDate(expected);
	
				break;
			}
			default:
		}

		result = EISTestBase.assertEqualsWithFlags(name, columnFieldName, actual, expected);

		return result;
	}

	private boolean doVerifyTableCell(String columnFieldName, int rowNumber, String... inExpecteds) throws GUIException {
		boolean result;
		List<String> locatorParms;
		String actual;
		String[] expecteds = inExpecteds;
		Field field;
		
		try {
			field = getFieldMetadata(columnFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + columnFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		if (field.isLocatorParameterized()) {
			EISConstants.ParameterizedLocatorType parameterizedLocatorType = field.getParameterizedLocatorType();
			switch (parameterizedLocatorType) {
				case TABLE_CELL_LOOKUP:		{
					//Parms are the name of the Field object that contains locator(s) to the table, and column number
					locatorParms = field.getLocatorParmsSets().get(0);
					try {
						actual = doGetTableCell(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), rowNumber);
					} catch (GUIException ge) {
						throw new GUIException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': " + ge.getMessage());
					}

					break;
				}
				case INFO_PANEL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': The locator type is " + parameterizedLocatorType + "; verify such fields by calling Page.verifyInfoPanelCell(fieldName) or Page.verifyInfoPanelCell(fieldName, expected)");
				}
				case RELATED_LIST_CELL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': The locator type is " + parameterizedLocatorType + "; verify such fields by calling Page.verifyRelatedListCell(columnfieldName, rowNumber) or Page.verifyRelatedListCell(columnfieldName, rowNumber, expected)");
				}
				default:	{
					throw new MetadataException("Unhandled member of common.EISConstants.ParameterizedLocatorType enumerated type: " + parameterizedLocatorType);
				}
			}
		} else {
			throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': This is a standard GUI element - it does not live in a table; verify such fields by calling Page.verifyField(fieldName) or Page.verifyField(fieldName, expected)");
		}

		switch (field.getType()) {
			case DATE:
			case READ_ONLY_DATE:	{
				actual = Util.formatDate(actual);
				
				ListIterator<String> expectedItr = Arrays.asList(expecteds).listIterator();
				while (expectedItr.hasNext()) {
					String expected = expectedItr.next().trim();
					expected = Util.formatDate(expected);
				}
	
				break;
			}
			default:
		}

		result = EISTestBase.assertEqualsWithFlags(name, columnFieldName, actual, expecteds);

		return result;
	}
	//*************************************************************************

	//***** verifyRelatedListCell methods *************************************
	@Override
	public boolean verifyRelatedListCellInstance(String columnFieldName, int rowNumber, int instance) {
		boolean success = false;
		String expected = getVerificationDataInstanceValue(columnFieldName, instance);
	
		FieldData_ fieldData = getVerificationDataRecord(columnFieldName, instance);
		
		EISConstants.ParameterizedVerificationDataValueType parameterizedVerificationDataValueType = fieldData.getParameterizedVerificationDataValueType();
		switch (parameterizedVerificationDataValueType) {
			case VERIFY:
			case VERIFY_VALUE:
			case VERIFY_CONSTANT:	{
				success = verifyRelatedListCell(columnFieldName, rowNumber, expected);
				break;
			}
			case VERIFY_VALUES:
			case VERIFY_VALUE_SET:	{
				success = verifyRelatedListCell(columnFieldName, rowNumber, fieldData.getValueSet());
				break;
			}
			default:				{
				EISTestBase.fail("Unhandled member of common.EISConstants.ParameterizedVerificationDataValueType enumerated type: " + parameterizedVerificationDataValueType);
			}
		}

		return success;
	}

	@Override
	public boolean verifyRelatedListCellInstance(String columnFieldName, int rowNumber, String instanceName) {
		return verifyRelatedListCellInstance(columnFieldName, rowNumber, testProperties.getInstanceNumber(instanceName));
	}

	@Override
	public boolean verifyRelatedListCell(String columnFieldName, int rowNumber) {
		return verifyRelatedListCellInstance(columnFieldName, rowNumber, 1);
	}

	@Override
	public boolean verifyRelatedListCell(String columnFieldName, int rowNumber, String expected) {
		boolean success = false;

		try {
			success = doVerifyRelatedListCell(columnFieldName, rowNumber, expected);
		} catch (GUIException ge) {
            EISTestBase.fail(ge.getMessage());
		}

		return success;
	}

	@Override
	public boolean verifyRelatedListCell(String columnFieldName, int rowNumber, String... expecteds) {
		boolean success = false;

		try {
			success = doVerifyRelatedListCell(columnFieldName, rowNumber, expecteds);
		} catch (GUIException ge) {
            EISTestBase.fail(ge.getMessage());
		}

		return success;
	}

	@Override
	public boolean verifyRelatedListCell(String columnFieldName, int rowNumber, List<String> expecteds) {
		return verifyRelatedListCell(columnFieldName, rowNumber, expecteds.toArray(new String[expecteds.size()]));
	}

	private boolean doVerifyRelatedListCell(String columnFieldName, int rowNumber, String inExpected) throws GUIException {
		boolean result;
		String actual;
		String expected = inExpected;
		Field field;
		
		try {
			field = getFieldMetadata(columnFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + columnFieldName + "' on the page '" + name + "': " + me.getMessage());
		}

		if (field.isLocatorParameterized()) {
			EISConstants.ParameterizedLocatorType parameterizedLocatorType = field.getParameterizedLocatorType();
			switch (parameterizedLocatorType) {
				case RELATED_LIST_CELL_LOOKUP:	{
					try {
						actual = doGetRelatedListCell(field.getLocatorParmsSets(), rowNumber);
					} catch (GUIException ge) {
						throw new GUIException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': " + ge.getMessage());
					}

					break;
				}
				case INFO_PANEL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': The locator type is " + parameterizedLocatorType + "; verify such fields by calling Page.verifyInfoPanelCell(columnFieldName) or Page.verifyInfoPanelCell(columnFieldName, expected)");
				}
				case TABLE_CELL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': The locator type is " + parameterizedLocatorType + "; verify such fields by calling Page.verifyTableCell(columnFieldName, rowNumber) or Page.verifyTableCell(columnFieldName, rowNumber, expected)");
				}
				default:	{
					throw new MetadataException("Unhandled member of common.EISConstants.ParameterizedLocatorType enumerated type: " + parameterizedLocatorType);
				}
			}
		} else {
			throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': This is a standard GUI element - it does not live in a table; verify such fields by calling Page.verifyField(columnFieldName) or Page.verifyField(columnFieldName, expected)");
		}

		switch (field.getType()) {
			case DATE:
			case READ_ONLY_DATE:	{
				actual = Util.formatDate(actual);
				expected = Util.formatDate(expected);
		
				break;
			}
			default:
		}

		result = EISTestBase.assertEqualsWithFlags(name, columnFieldName, actual, expected);

		return result;
	}

	private boolean doVerifyRelatedListCell(String columnFieldName, int rowNumber, String... inExpecteds) throws GUIException {
		boolean result;
		String actual;
		String[] expecteds = inExpecteds;
		Field field;
		
		try {
			field = getFieldMetadata(columnFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + columnFieldName + "' on the page '" + name + "': " + me.getMessage());
		}

		if (field.isLocatorParameterized()) {
			EISConstants.ParameterizedLocatorType parameterizedLocatorType = field.getParameterizedLocatorType();
			switch (parameterizedLocatorType) {
				case RELATED_LIST_CELL_LOOKUP:	{
					try {
						actual = doGetRelatedListCell(field.getLocatorParmsSets(), rowNumber);
					} catch (GUIException ge) {
						throw new GUIException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': " + ge.getMessage());
					}

					break;
				}
				case INFO_PANEL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': The locator type is " + parameterizedLocatorType + "; verify such fields by calling Page.verifyInfoPanelCell(columnFieldName) or Page.verifyInfoPanelCell(columnFieldName, expected)");
				}
				case TABLE_CELL_LOOKUP:	{
					throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': The locator type is " + parameterizedLocatorType + "; verify such fields by calling Page.verifyTableCell(columnFieldName, rowNumber) or Page.verifyTableCell(columnFieldName, rowNumber, expected)");
				}
				default:	{
					throw new MetadataException("Unhandled member of common.EISConstants.ParameterizedLocatorType enumerated type: " + parameterizedLocatorType);
				}
			}
		} else {
			throw new MetadataException("Error while verifying the GUI element '" + columnFieldName + "' on the page '" + name + "': This is a standard GUI element - it does not live in a table; verify such fields by calling Page.verifyField(columnFieldName) or Page.verifyField(columnFieldName, expected)");
		}

		switch (field.getType()) {
			case DATE:
			case READ_ONLY_DATE:	{
				actual = Util.formatDate(actual);
				
				ListIterator<String> expectedItr = Arrays.asList(expecteds).listIterator();
				while (expectedItr.hasNext()) {
					String expected = expectedItr.next().trim();
					expected = Util.formatDate(expected);
				}
	
				break;
			}
			default:
		}

		result = EISTestBase.assertEqualsWithFlags(name, columnFieldName, actual, expecteds);

		return result;
	}
	//*************************************************************************

	@Override
	public String getInfoPanelCell(String fieldName) {
		String value = "";

		try {
			validateParameterizedLocatorType(fieldName, EISConstants.ParameterizedLocatorType.INFO_PANEL_LOOKUP);

			value = doGetInfoPanelCell(getFieldMetadata(fieldName).getLocatorParmsSets());
		} catch (MetadataException me) {
			EISTestBase.fail("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}

		return value;
	}
	
/*	private String doGetInfoPanelCell(List<List<String>> locatorParmsSets) throws MetadataException {
		//NOTE that there are serious restrictions on the xpaths used here (see the comments in EISConstants for more info)!!
		//  Even though we have taken pains to use the most reliable xpaths, it behooves us to take extra safeguards.  Hence
		//  the opening sleep and the retry of the getTableAsListOfList call.

		//When there is a help icon (a circle containing a question mark) next to a label in a table, getTableAsListOfList (before WebDriver
		//  migration) usually returned the name of a script (sfdcPage.setHelp) appended to the text of the label.  In order for our methods that
		//  scrape tables to work correctly, we need to strip that text out.
		//TODO  See if it still works that way with the new (post WebDriver migration) getTableAsListOfList method
		String appendedLabelText = EISConstants.APPENDED_LABEL_TEXT.trim().toUpperCase();

		List<String> locatorParms = new ArrayList<String>();
		List<List<String>> invalidParmsSets = new ArrayList<List<String>>();
		String locator;
		String value = "";
		String upperTargetLabel;
		String candidateTableLabel;
		String cacheKey = "";
		boolean isInfoPanelInCache = false;
		boolean success = false;

		//Changed after port to WebDriver
		//Util.sleep(1000);
		Util.sleep(300);

		ListIterator<List<String>> locatorParmItr = locatorParmsSets.listIterator();
		while (locatorParmItr.hasNext()) {
			locatorParms = locatorParmItr.next();

			locator = EISConstants.INFO_PANEL_HEADER_XPATH.replace(EISConstants.INFO_PANEL_HEADER_TOKEN, locatorParms.get(0).trim());
			if (gui.findSeleniumLocator(locator)) {
				List<List<String>> table = null;

				if (usingInfoPanelCache) {
					cacheKey = locatorParms.get(0).trim().toUpperCase();
					isInfoPanelInCache = infoPanelCache.containsKey(cacheKey);
				}
				
				locator += EISConstants.INFO_PANEL_XPATH;

				if (!(usingInfoPanelCache && isInfoPanelInCache)) {
					try {
						table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator));
					} catch (MetadataException me) {
						Util.printDebug("While searching for the 'info panel' at '" + locator + "' on the Page '" + name + "', the gui.getTableAsListOfList call threw an exception; will sleep and try again");
	
						Util.sleep(pageRedrawDelay);
	
						try {
							table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator));
						} catch (MetadataException me1) {
							throw new MetadataException("Error while searching for the 'info panel' at '" + locator + "' on the page '" + name + "': " + me1.getMessage());
						}
					}
					
					if (usingInfoPanelCache) {
						infoPanelCache.put(cacheKey, table);
					}
				} else {
					table = infoPanelCache.get(cacheKey);
				}

				List<String> cells = new ArrayList<String>();
				int index;

				//Note that we are doing straight equality comparisons (case-insensitive) rather than substring or regex
				//  comparisons.  That is to account for:
				//  1) the fact that some labels may contain regex characters
				//  2) we don't want to return the wrong label when there are multiple similar ones, e.g., if we want "Record",
				//     we don't want a "contains" comparison to "Record Type" to succeed
				upperTargetLabel = locatorParms.get(1).trim().toUpperCase();

				ListIterator<List<String>> rowItr = table.listIterator();
				while (rowItr.hasNext()) {
					cells = rowItr.next();

					ListIterator<String> colItr = cells.listIterator();
					while (colItr.hasNext()) {
						candidateTableLabel = colItr.next().trim().toUpperCase();

						index = candidateTableLabel.indexOf(appendedLabelText);
						if (index >= 0) {
							candidateTableLabel = candidateTableLabel.substring(0, index);
						}

						if (candidateTableLabel.equals(upperTargetLabel)) {
							if (colItr.hasNext()) {
								value = colItr.next().trim();
							} else {
								value = "";
							}

							success = true;

							//Break from iterating through columns in this row
							break;
						}

						if (colItr.hasNext()) {
							colItr.next();
						}
					}

					if (success) {
						//Break from iterating through rows in this table
						break;
					}
				}

				if (success) {
					//Break from iterating through locator parm sets
					break;
				}
			}

			//If the table was not found on this iteration, or if the table was found but the label
			//  was not, save the locator parm set for possible inclusion in an error message
			if (!success) {
				invalidParmsSets.add(locatorParms);
			}
		}

		if (!success) {
			throw new MetadataException("Error while searching for 'info panel' element on the page '" + name + "': the element could not be found; tried: " + invalidParmsSets.toString());
		}

		return value;
	}
*/
/*	private String doGetInfoPanelCell(List<List<String>> locatorParmsSets) throws MetadataException {
		//NOTE that there are serious restrictions on the xpaths used here (see the comments in EISConstants for more info)!!
		//  Even though we have taken pains to use the most reliable xpaths, it behooves us to take extra safeguards.  Hence
		//  the opening sleep and the retry of the getTableAsListOfList call.

		//When there is a help icon (a circle containing a question mark) next to a label in a table, getTableAsListOfList (before WebDriver
		//  migration) usually returned the name of a script (sfdcPage.setHelp) appended to the text of the label.  In order for our methods that
		//  scrape tables to work correctly, we need to strip that text out.
		//TODO  See if it still works that way with the new (post WebDriver migration) getTableAsListOfList method
		String appendedLabelText = EISConstants.APPENDED_LABEL_TEXT.trim().toUpperCase();

		List<String> locatorParms = new ArrayList<String>();
		List<List<String>> invalidParmsSets = new ArrayList<List<String>>();
		String locator;
		String value = "";
		String upperTargetLabel;
		String candidateTableLabel;
		String cacheKey = "";
		boolean isInfoPanelInCache = false;
		boolean success = false;

		//Changed after port to WebDriver
		//Util.sleep(1000);
		Util.sleep(300);

		ListIterator<List<String>> locatorParmItr = locatorParmsSets.listIterator();
		while (locatorParmItr.hasNext()) {
			locatorParms = locatorParmItr.next();

			locator = EISConstants.INFO_PANEL_HEADER_XPATH.replace(EISConstants.INFO_PANEL_HEADER_TOKEN, locatorParms.get(0).trim());
			if (gui.findSeleniumLocator(locator)) {
				List<List<String>> table = null;

				if (usingInfoPanelCache) {
					cacheKey = locatorParms.get(0).trim().toUpperCase();
					isInfoPanelInCache = infoPanelCache.containsKey(cacheKey);
				}
				
				locator += EISConstants.INFO_PANEL_XPATH;

				if (!(usingInfoPanelCache && isInfoPanelInCache)) {
					try {
						table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator));
					} catch (MetadataException me) {
						Util.printDebug("While searching for the 'info panel' at '" + locator + "' on the Page '" + name + "', the gui.getTableAsListOfList call threw an exception; will sleep and try again");
	
						Util.sleep(pageRedrawDelay);
	
						try {
							table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator));
						} catch (MetadataException me1) {
							throw new MetadataException("Error while searching for the 'info panel' at '" + locator + "' on the Page '" + name + "': " + me1.getMessage());
						}
					}
					
					if (usingInfoPanelCache) {
						infoPanelCache.put(cacheKey, table);
					}
				} else {
					table = infoPanelCache.get(cacheKey);
				}

				List<String> cells = new ArrayList<String>();
				int index;

				//Note that we are doing straight equality comparisons (case-insensitive) rather than substring or regex
				//  comparisons.  That is to account for:
				//  1) the fact that some labels may contain regex characters
				//  2) we don't want to return the wrong label when there are multiple similar ones, e.g., if we want "Record",
				//     we don't want a "contains" comparison to "Record Type" to succeed
				upperTargetLabel = locatorParms.get(1).trim().toUpperCase();

				ListIterator<List<String>> rowItr = table.listIterator();
				while (rowItr.hasNext()) {
					cells = rowItr.next();

					ListIterator<String> colItr = cells.listIterator();
					while (colItr.hasNext()) {
						candidateTableLabel = colItr.next().trim().toUpperCase();

						index = candidateTableLabel.indexOf(appendedLabelText);
						if (index >= 0) {
							candidateTableLabel = candidateTableLabel.substring(0, index);
						}

						if (candidateTableLabel.equals(upperTargetLabel)) {
							if (colItr.hasNext()) {
								value = colItr.next().trim();
							} else {
								value = "";
							}

							success = true;

							//Break from iterating through columns in this row
							break;
						}

						if (colItr.hasNext()) {
							colItr.next();
						}
					}

					if (success) {
						//Break from iterating through rows in this table
						break;
					}
				}

				if (success) {
					//Break from iterating through locator parm sets
					break;
				}
			}

			//If the table was not found on this iteration, or if the table was found but the label
			//  was not, save the locator parm set for possible inclusion in an error message
			if (!success) {
				invalidParmsSets.add(locatorParms);
			}
		}

		if (!success) {
			throw new MetadataException("Error while searching for 'info panel' element on the page '" + name + "': the element could not be found; tried: " + invalidParmsSets.toString());
		}

		return value;
	}
*/
/*	private String doGetInfoPanelCell(List<List<String>> locatorParmsSets) throws MetadataException {
		//NOTE that there are serious restrictions on the xpaths used here (see the comments in EISConstants for more info)!!
		//  Even though we have taken pains to use the most reliable xpaths, it behooves us to take extra safeguards.  Hence
		//  the retry of the getTableAsListOfList call.

		//When there is a help icon (a circle containing a question mark) next to a label in a table, getTableAsListOfList (before WebDriver
		//  migration) usually returned the name of a script (sfdcPage.setHelp) appended to the text of the label.  In order for our methods that
		//  scrape tables to work correctly, we need to strip that text out.
		//TODO  See if it still works that way with the new (post WebDriver migration) getTableAsListOfList method
		String appendedLabelText = EISConstants.APPENDED_LABEL_TEXT.trim().toUpperCase();

		List<String> locators = new ArrayList<String>();
		List<String> locatorParms = new ArrayList<String>();
		String value = "";
		String upperTargetLabel;
		String candidateTableLabel;
		String cacheKey = "";
		String fieldName;
		boolean isInfoPanelInCache = false;
		boolean success = false;

		//Changed after port to WebDriver
		//Util.sleep(1000);

		ListIterator<List<String>> locatorParmItr = locatorParmsSets.listIterator();
		while (locatorParmItr.hasNext()) {
			locatorParms = locatorParmItr.next();

			locators.add(EISConstants.INFO_PANEL_HEADER_XPATH.replace(EISConstants.INFO_PANEL_HEADER_TOKEN, locatorParms.get(0).trim()));
		}
		
		//Create a bogus Field object, and pass its name to waitForElementPresent
		fieldName = name + "InfoPanel";
		fieldMetadata.add(new Field(fieldName, EISConstants.FieldType.READ_ONLY, locators, EISConstants.RequirednessLevel.OPTIONAL, new ArrayList<String>()));
		
		if (waitForElementPresent(fieldName)) {
			List<List<String>> table = null;

			int locatorNum = gui.searchLocators(locators);
			
			if (usingInfoPanelCache) {
				//cacheKey = locatorParms.get(0).trim().toUpperCase();
				cacheKey = locatorParmsSets.get(locatorNum).get(0).trim().toUpperCase();
				isInfoPanelInCache = infoPanelCache.containsKey(cacheKey);
			}
			
			if (!(usingInfoPanelCache && isInfoPanelInCache)) {
				String locator = locators.get(locatorNum) + EISConstants.INFO_PANEL_XPATH;
				
				try {
					table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true));
				} catch (MetadataException me) {
					Util.printDebug("While searching for the 'info panel' at '" + locator + "' on the Page '" + name + "', the gui.getTableAsListOfList call threw an exception; will sleep and try again");

					Util.sleep(pageRedrawDelay);

					try {
						table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true));
					} catch (MetadataException me1) {
						throw new MetadataException("Error while searching for the 'info panel' at '" + locator + "' on the Page '" + name + "': " + me1.getMessage());
					}
				}
				
				if (usingInfoPanelCache) {
					infoPanelCache.put(cacheKey, table);
				}
			} else {
				table = infoPanelCache.get(cacheKey);
			}

			List<String> cells = new ArrayList<String>();
			int index;

			//Note that we are doing straight equality comparisons (case-insensitive) rather than substring or regex
			//  comparisons.  That is to account for:
			//  1) the fact that some labels may contain regex characters
			//  2) we don't want to return the wrong label when there are multiple similar ones, e.g., if we want "Record",
			//     we don't want a "contains" comparison to "Record Type" to succeed
			upperTargetLabel = locatorParms.get(1).trim().toUpperCase();

			ListIterator<List<String>> rowItr = table.listIterator();
			while (rowItr.hasNext()) {
				cells = rowItr.next();

				ListIterator<String> colItr = cells.listIterator();
				while (colItr.hasNext()) {
					candidateTableLabel = colItr.next().trim().toUpperCase();

					index = candidateTableLabel.indexOf(appendedLabelText);
					if (index >= 0) {
						candidateTableLabel = candidateTableLabel.substring(0, index);
					}

					if (candidateTableLabel.equals(upperTargetLabel)) {
						if (colItr.hasNext()) {
							value = colItr.next().trim();
						} else {
							value = "";
						}

						success = true;

						//Break from iterating through columns in this row
						break;
					}

					if (colItr.hasNext()) {
						colItr.next();
					}
				}

				if (success) {
					//Break from iterating through rows in this table
					break;
				}
			}
		}

		if (!success) {
			throw new MetadataException("Error while searching for 'info panel' element on the page '" + name + "': the element could not be found; tried: " + locatorParmsSets.toString());
		}

		return value;
	}
*/
	private String doGetInfoPanelCell(List<List<String>> locatorParmsSets) throws MetadataException {
		//NOTE that there are serious restrictions on the xpaths used here (see the comments in EISConstants for more info)!!
		//  Even though we have taken pains to use the most reliable xpaths, it behooves us to take extra safeguards.  Hence
		//  the retry of the getTableAsListOfList call.

		//When there is a help icon (a circle containing a question mark) next to a label in a table, getTableAsListOfList (before WebDriver
		//  migration) usually returned the name of a script (sfdcPage.setHelp) appended to the text of the label.  In order for our methods that
		//  scrape tables to work correctly, we need to strip that text out.
		//TODO  See if it still works that way with the new (post WebDriver migration) getTableAsListOfList method
		String appendedLabelText = EISConstants.APPENDED_LABEL_TEXT.trim().toUpperCase();

		List<String> locators = new ArrayList<String>();
		List<String> locatorParms = new ArrayList<String>();
		List<List<String>> table = null;
		String locator;
		int locatorNum;
		String value = "";
		String upperTargetLabel;
		String candidateTableLabel;
		String cacheKey = "";
		String fieldName;
		boolean isInfoPanelInCache = false;
		boolean success = false;

		//Changed after port to WebDriver
		//We had to put this back since we changed from calling waitForElementPresent to calling
		//  gui.isElementPresent
		//Util.sleep(2000);
		Util.sleep(2000);

		ListIterator<List<String>> locatorParmItr = locatorParmsSets.listIterator();
		while (locatorParmItr.hasNext()) {
			locatorParms = locatorParmItr.next();

			locators.add(EISConstants.INFO_PANEL_HEADER_XPATH.replace(EISConstants.INFO_PANEL_HEADER_TOKEN, locatorParms.get(0).trim()));
		}
		
		//Create a bogus Field object, and pass its name to waitForElementPresent
		fieldName = name + "InfoPanel";
		fieldMetadata.add(new Field(fieldName, EISConstants.FieldType.READ_ONLY, locators, EISConstants.RequirednessLevel.OPTIONAL, new ArrayList<String>()));
		
		ListIterator<String> locatorItr = locators.listIterator();
		while (locatorItr.hasNext()) {
			locator = locatorItr.next();
			
			if (gui.isElementPresent(locator)) {
				table = null;
	
				locatorNum = locatorItr.nextIndex() - 1;
				if (usingInfoPanelCache) {
					//cacheKey = locatorParms.get(0).trim().toUpperCase();
					cacheKey = locatorParmsSets.get(locatorNum).get(0).trim().toUpperCase();
					isInfoPanelInCache = infoPanelCache.containsKey(cacheKey);
				}
				
				if (!(usingInfoPanelCache && isInfoPanelInCache)) {
					locator += EISConstants.INFO_PANEL_XPATH;
					
					try {
						table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true));
					} catch (MetadataException me) {
						Util.printDebug("While searching for the 'info panel' at '" + locator + "' on the Page '" + name + "', the gui.getTableAsListOfList call threw an exception; will sleep and try again");
	
						Util.sleep(pageRedrawDelay);
	
						try {
							table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true));
						} catch (MetadataException me1) {
							throw new MetadataException("Error while searching for the 'info panel' at '" + locator + "' on the Page '" + name + "': " + me1.getMessage());
						}
					}
					
					if (usingInfoPanelCache) {
						infoPanelCache.put(cacheKey, table);
					}
				} else {
					table = infoPanelCache.get(cacheKey);
				}
	
				List<String> cells = new ArrayList<String>();
				int index;
	
				//Note that we are doing straight equality comparisons (case-insensitive) rather than substring or regex
				//  comparisons.  That is to account for:
				//  1) the fact that some labels may contain regex characters
				//  2) we don't want to return the wrong label when there are multiple similar ones, e.g., if we want "Record",
				//     we don't want a "contains" comparison to "Record Type" to succeed
				upperTargetLabel = locatorParms.get(1).trim().toUpperCase();
	
				ListIterator<List<String>> rowItr = table.listIterator();
				while (rowItr.hasNext()) {
					cells = rowItr.next();
	
					ListIterator<String> colItr = cells.listIterator();
					while (colItr.hasNext()) {
						candidateTableLabel = colItr.next().trim().toUpperCase();
	
						index = candidateTableLabel.indexOf(appendedLabelText);
						if (index >= 0) {
							candidateTableLabel = candidateTableLabel.substring(0, index);
						}
	
						if (candidateTableLabel.equals(upperTargetLabel)) {
							if (colItr.hasNext()) {
								value = colItr.next().trim();
							} else {
								value = "";
							}
	
							success = true;
	
							//Break from iterating through columns in this row
							break;
						}
	
						if (colItr.hasNext()) {
							colItr.next();
						}
					}
	
					if (success) {
						//Break from iterating through rows in this table
						break;
					}
				}
				
				if (success) {
					//Break from iterating through locators
					break;
				}
			}
		}

		if (!success) {
			throw new MetadataException("Error while searching for 'info panel' element on the page '" + name + "': the element could not be found; tried: " + locatorParmsSets.toString());
		}

		return value;
	}
	
	@Override
	public void clickLinkInInfoPanel(String fieldName, int linkNumberInCell) {
		try {
			validateParameterizedLocatorType(fieldName, EISConstants.ParameterizedLocatorType.INFO_PANEL_LOOKUP);

			//locator = getInfoPanelCellLocator(getFieldMetadata(fieldName).getLocatorParmsSets());
			doClickLinkInInfoPanel(fieldName, getFieldMetadata(fieldName).getLocatorParmsSets(), linkNumberInCell);
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + ge.getMessage());
		}
	}

	private void doClickLinkInInfoPanel(String fieldName, List<List<String>> locatorParmsSets, int linkNumberInCell) throws GUIException {
		String cellLocator;
		String linkLocator;

		try {
			cellLocator = getInfoPanelCellLocator(locatorParmsSets);
		} catch (GUIException ge) {
			throw new GUIException("Error while clicking the link associated with the GUI element '" + fieldName + "' on the page '" + name + "': " + ge.getMessage());
		}

		linkLocator = cellLocator + "//a[" + Integer.toString(linkNumberInCell) + "]";
		
		gui.click(linkLocator);
	}
	
	@Override
	public void clickLinkInInfoPanel(String fieldName) {
		clickLinkInInfoPanel(fieldName, 1);
	}
	
/*	private String getInfoPanelCellLocator(List<List<String>> locatorParmsSets) throws MetadataException {
		//NOTE that there are serious restrictions on the xpaths used here (see the comments in EISConstants for more info)!!
		//  Even though we have taken pains to use the most reliable xpaths, it behooves us to take extra safeguards.  Hence
		//  the retry of the getTableAsListOfList call.

		//When there is a help icon (a circle containing a question mark) next to a label in a table, getTableAsListOfList (before WebDriver
		//  migration) usually returned the name of a script (sfdcPage.setHelp) appended to the text of the label.  In order for our methods that
		//  scrape tables to work correctly, we need to strip that text out.
		//TODO  See if it still works that way with the new (post WebDriver migration) getTableAsListOfList method
		String appendedLabelText = EISConstants.APPENDED_LABEL_TEXT.trim().toUpperCase();

		List<String> locators = new ArrayList<String>();
		List<String> locatorParms = new ArrayList<String>();
		String locator = "";
		int rowNumber = -1;
		int columnNumber = -1;
		String upperTargetLabel;
		String candidateTableLabel;
		String cacheKey = "";
		String fieldName;
		boolean isInfoPanelInCache = false;
		boolean success = false;

		//Changed after port to WebDriver
		//Util.sleep(1000);

		ListIterator<List<String>> locatorParmItr = locatorParmsSets.listIterator();
		while (locatorParmItr.hasNext()) {
			locatorParms = locatorParmItr.next();

			locators.add(EISConstants.INFO_PANEL_HEADER_XPATH.replace(EISConstants.INFO_PANEL_HEADER_TOKEN, locatorParms.get(0).trim()));
		}
		
		//Create a bogus Field object, and pass its name to waitForElementPresent
		fieldName = name + "InfoPanel";
		fieldMetadata.add(new Field(fieldName, EISConstants.FieldType.READ_ONLY, locators, EISConstants.RequirednessLevel.OPTIONAL, new ArrayList<String>()));
		
		if (waitForElementPresent(fieldName)) {
			List<List<String>> table = null;

			int locatorNum = gui.searchLocators(locators);
			locator = locators.get(locatorNum) + EISConstants.INFO_PANEL_XPATH;
			
			if (usingInfoPanelCache) {
				//cacheKey = locatorParms.get(0).trim().toUpperCase();
				cacheKey = locatorParmsSets.get(locatorNum).get(0).trim().toUpperCase();
				isInfoPanelInCache = infoPanelCache.containsKey(cacheKey);
			}
			
			if (!(usingInfoPanelCache && isInfoPanelInCache)) {
				try {
					table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true));
				} catch (MetadataException me) {
					Util.printDebug("While searching for the 'info panel' at '" + locator + "' on the Page '" + name + "', the gui.getTableAsListOfList call threw an exception; will sleep and try again");

					Util.sleep(pageRedrawDelay);

					try {
						table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true));
					} catch (MetadataException me1) {
						throw new MetadataException("Error while searching for the 'info panel' at '" + locator + "' on the Page '" + name + "': " + me1.getMessage());
					}
				}
				
				if (usingInfoPanelCache) {
					infoPanelCache.put(cacheKey, table);
				}
			} else {
				table = infoPanelCache.get(cacheKey);
			}

			List<String> cells = new ArrayList<String>();
			int index;

			//Note that we are doing straight equality comparisons (case-insensitive) rather than substring or regex
			//  comparisons.  That is to account for:
			//  1) the fact that some labels may contain regex characters
			//  2) we don't want to return the wrong label when there are multiple similar ones, e.g., if we want "Record",
			//     we don't want a "contains" comparison to "Record Type" to succeed
			upperTargetLabel = locatorParms.get(1).trim().toUpperCase();

			ListIterator<List<String>> rowItr = table.listIterator();
			while (rowItr.hasNext()) {
				cells = rowItr.next();

				ListIterator<String> colItr = cells.listIterator();
				while (colItr.hasNext()) {
					candidateTableLabel = colItr.next().trim().toUpperCase();

					index = candidateTableLabel.indexOf(appendedLabelText);
					if (index >= 0) {
						candidateTableLabel = candidateTableLabel.substring(0, index);
					}

					if (candidateTableLabel.equals(upperTargetLabel)) {
						//Return the index to the next column, which contains the element the caller wants to access
						columnNumber = colItr.nextIndex();
						
						//Increment columnNumber to account for the fact that HTML column numbers are 1-based
						columnNumber ++;

						success = true;

						//Break from iterating through columns in this row
						break;
					}

					if (colItr.hasNext()) {
						colItr.next();
					}
				}

				if (success) {
					//Return the index to this row
					rowNumber = rowItr.nextIndex() - 1;
					
					//Increment rowNumber to account for the fact that HTML row numbers are 1-based
					rowNumber ++;

					//Break from iterating through rows in this table
					break;
				}
			}
		}

		if (!success) {
			throw new MetadataException("Error while searching for 'info panel' element on the page '" + name + "': the element could not be found; tried: " + locatorParmsSets.toString());
		} else {
			locator += ("/tr[" + Integer.toString(rowNumber) + "]/*[" + Integer.toString(columnNumber) + "]");
		}

		return locator;
	}
*/
	private String getInfoPanelCellLocator(List<List<String>> locatorParmsSets) throws MetadataException {
		//NOTE that there are serious restrictions on the xpaths used here (see the comments in EISConstants for more info)!!
		//  Even though we have taken pains to use the most reliable xpaths, it behooves us to take extra safeguards.  Hence
		//  the retry of the getTableAsListOfList call.

		//When there is a help icon (a circle containing a question mark) next to a label in a table, getTableAsListOfList (before WebDriver
		//  migration) usually returned the name of a script (sfdcPage.setHelp) appended to the text of the label.  In order for our methods that
		//  scrape tables to work correctly, we need to strip that text out.
		//TODO  See if it still works that way with the new (post WebDriver migration) getTableAsListOfList method
		String appendedLabelText = EISConstants.APPENDED_LABEL_TEXT.trim().toUpperCase();

		List<String> locators = new ArrayList<String>();
		List<String> locatorParms = new ArrayList<String>();
		List<List<String>> table = null;
		String locator = "";
		int locatorNum;
		int rowNumber = -1;
		int columnNumber = -1;
		String upperTargetLabel;
		String candidateTableLabel;
		String cacheKey = "";
		String fieldName;
		boolean isInfoPanelInCache = false;
		boolean success = false;

		//Changed after port to WebDriver
		//We had to put this back since we changed from calling waitForElementPresent to calling
		//  gui.isElementPresent
		//Util.sleep(1000);
		Util.sleep(2000);

		ListIterator<List<String>> locatorParmItr = locatorParmsSets.listIterator();
		while (locatorParmItr.hasNext()) {
			locatorParms = locatorParmItr.next();

			locators.add(EISConstants.INFO_PANEL_HEADER_XPATH.replace(EISConstants.INFO_PANEL_HEADER_TOKEN, locatorParms.get(0).trim()));
		}
		
		//Create a bogus Field object, and pass its name to waitForElementPresent
		fieldName = name + "InfoPanel";
		fieldMetadata.add(new Field(fieldName, EISConstants.FieldType.READ_ONLY, locators, EISConstants.RequirednessLevel.OPTIONAL, new ArrayList<String>()));
		
		ListIterator<String> locatorItr = locators.listIterator();
		while (locatorItr.hasNext()) {
			locator = locatorItr.next();
			
			if (gui.isElementPresent(locator)) {
				table = null;
	
				locatorNum = locatorItr.nextIndex() - 1;
				if (usingInfoPanelCache) {
					//cacheKey = locatorParms.get(0).trim().toUpperCase();
					cacheKey = locatorParmsSets.get(locatorNum).get(0).trim().toUpperCase();
					isInfoPanelInCache = infoPanelCache.containsKey(cacheKey);
				}
				
				if (!(usingInfoPanelCache && isInfoPanelInCache)) {
					locator += EISConstants.INFO_PANEL_XPATH;
					
					try {
						table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true));
					} catch (MetadataException me) {
						Util.printDebug("While searching for the 'info panel' at '" + locator + "' on the Page '" + name + "', the gui.getTableAsListOfList call threw an exception; will sleep and try again");
	
						Util.sleep(pageRedrawDelay);
	
						try {
							table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true));
						} catch (MetadataException me1) {
							throw new MetadataException("Error while searching for the 'info panel' at '" + locator + "' on the Page '" + name + "': " + me1.getMessage());
						}
					}
					
					if (usingInfoPanelCache) {
						infoPanelCache.put(cacheKey, table);
					}
				} else {
					table = infoPanelCache.get(cacheKey);
				}
	
				List<String> cells = new ArrayList<String>();
				int index;
	
				//Note that we are doing straight equality comparisons (case-insensitive) rather than substring or regex
				//  comparisons.  That is to account for:
				//  1) the fact that some labels may contain regex characters
				//  2) we don't want to return the wrong label when there are multiple similar ones, e.g., if we want "Record",
				//     we don't want a "contains" comparison to "Record Type" to succeed
				upperTargetLabel = locatorParms.get(1).trim().toUpperCase();
	
				ListIterator<List<String>> rowItr = table.listIterator();
				while (rowItr.hasNext()) {
					cells = rowItr.next();
	
					ListIterator<String> colItr = cells.listIterator();
					while (colItr.hasNext()) {
						candidateTableLabel = colItr.next().trim().toUpperCase();
	
						index = candidateTableLabel.indexOf(appendedLabelText);
						if (index >= 0) {
							candidateTableLabel = candidateTableLabel.substring(0, index);
						}
	
						if (candidateTableLabel.equals(upperTargetLabel)) {
							//Return the index to the next column, which contains the element the caller wants to access
							columnNumber = colItr.nextIndex();
							
							//Increment columnNumber to account for the fact that HTML column numbers are 1-based
							columnNumber ++;
	
							success = true;
	
							//Break from iterating through columns in this row
							break;
						}
	
						if (colItr.hasNext()) {
							colItr.next();
						}
					}
	
					if (success) {
						//Return the index to this row
						rowNumber = rowItr.nextIndex() - 1;
						
						//Increment rowNumber to account for the fact that HTML row numbers are 1-based
						rowNumber ++;
	
						//Break from iterating through rows in this table
						break;
					}
				}
				
				if (success) {
					//Break from iterating through locators
					break;
				}
			}
		}

		if (!success) {
			throw new MetadataException("Error while searching for 'info panel' element on the page '" + name + "': the element could not be found; tried: " + locatorParmsSets.toString());
		} else {
			locator += ("/tr[" + Integer.toString(rowNumber) + "]/*[" + Integer.toString(columnNumber) + "]");
		}

		return locator;
	}

	@Override
	public String getTableCell(String columnFieldName, int rowNumber) {
		String value = "";
		List<String> locatorParms;

		try {
			validateParameterizedLocatorType(columnFieldName, EISConstants.ParameterizedLocatorType.TABLE_CELL_LOOKUP);

			//locatorParms = name of the Field object that contains locator(s) to the table, and column number
			locatorParms = getFieldMetadata(columnFieldName).getLocatorParmsSets().get(0);

			//doGetTableCell(columnFieldName, columnNumber, rowNumber)
			value = doGetTableCell(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), rowNumber);
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnFieldName + "' on the page '" + name + "': " + ge.getMessage());
		}

		return value;
	}

/*	private String doGetTableCell(String fieldName, int columnNumber, int rowNumber) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		String value = "";
		List<String> locators;
		String locator = "";
		int locatorNum;
		Field field;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		cellAccessErrorMessage = "Error while accessing the table '" + fieldName + "' on the Page '" + name + "': ";
		
		//Proceed only if rowNumber refers to at least the first row of data, and columnNumber refers to
		//  at least the first column
		if ((rowNumber >= 0) && (columnNumber >= 0)) {
			locators = field.getLocators();
			locatorNum = gui.searchLocators(locators);

			if (locatorNum < 0) {
				throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
			}

			locator = locators.get(locatorNum);
			
			List<List<String>> table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator)); 

			//Bounds check rowNumber and columnNumber
			if (!(rowNumber < table.size())) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the table contains fewer than " + (rowNumber + 1) + " row(s)");
			}

			if (!(columnNumber < table.get(0).size())) {
				throw new TestDataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is out of bounds - the table contains fewer than " + (columnNumber + 1) + " column(s)");
			}
		} else {
			if (rowNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
			} else if (columnNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is less than zero");
			}
		}

		locator += "." + Integer.toString(rowNumber) + "." + Integer.toString(columnNumber);
		value = gui.getTable(locator).trim();

		return value;
	}
*/
	private String doGetTableCell(String tableFieldName, int columnNumber, int rowNumber) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		String value = "";
		List<String> locators;
		String locator = "";
		int locatorNum;
		Field field;

		try {
			field = getFieldMetadata(tableFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		cellAccessErrorMessage = "Error while accessing the table '" + tableFieldName + "' on the Page '" + name + "': ";
		
		//Proceed only if rowNumber refers to at least the first row of data, and columnNumber refers to
		//  at least the first column
		if ((rowNumber >= 0) && (columnNumber >= 0)) {
			locators = field.getLocators();
			locatorNum = gui.searchLocators(locators);

			if (locatorNum < 0) {
				throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
			}

			locator = locators.get(locatorNum);
			
			List<List<String>> table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator)); 

			//Bounds check rowNumber and columnNumber
			if (!(rowNumber < table.size())) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the table contains fewer than " + (rowNumber + 1) + " row(s)");
			}

			if (!(columnNumber < table.get(0).size())) {
				throw new TestDataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is out of bounds - the table contains fewer than " + (columnNumber + 1) + " column(s)");
			}
			
			value = table.get(rowNumber).get(columnNumber);
		} else {
			if (rowNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
			} else if (columnNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is less than zero");
			}
		}

		return value;
	}

	@Override
	public void clickLinkInTable(String columnFieldName, int rowNumber, int linkNumberInCell) {
		List<String> locatorParms;

		try {
			validateParameterizedLocatorType(columnFieldName, EISConstants.ParameterizedLocatorType.TABLE_CELL_LOOKUP);

			//locatorParms = name of the Field object that contains locator(s) to the table, and column number
			locatorParms = getFieldMetadata(columnFieldName).getLocatorParmsSets().get(0);

			//doClickLinkInTable(columnFieldName, columnNumber, rowNumber, linkNumberInCell)
			doClickLinkInTable(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), rowNumber, linkNumberInCell);
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnFieldName + "' on the page '" + name + "': " + ge.getMessage());
		}
	}

	@Override
	public void clickLinkInTable(String columnFieldName, int rowNumber) {
		clickLinkInTable(columnFieldName, rowNumber, 1);
	}

	@Override
	public void clickLinkInTable(String columnFieldNameToSearch, String value, String columnFieldNameToClick, int linkNumberInCell) {
		List<String> locatorParms;
		int rowNum = 0;

		try {
			validateParameterizedLocatorType(columnFieldNameToSearch, EISConstants.ParameterizedLocatorType.TABLE_CELL_LOOKUP);

			//locatorParms = name of the Field object that contains locator(s) to the table, and column number
			locatorParms = getFieldMetadata(columnFieldNameToSearch).getLocatorParmsSets().get(0);

			//rowNum = doGetTableRowNum(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), value, false);
			rowNum = doGetTableRowNum(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), value, true);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnFieldNameToSearch + "' on the page '" + name + "': " + me.getMessage());
		}
		
		if (rowNum >= 0) {
			clickLinkInTable(columnFieldNameToClick, rowNum, linkNumberInCell);
		} else {
			EISTestBase.fail("Error while accessing the GUI element '" + columnFieldNameToSearch + "' on the page '" + name + "': the value '" + value + "' was not found");
		}
	}

	@Override
	public void clickLinkInTable(String columnfieldNameToSearch, String value, String columnfieldNameToClick) {
		clickLinkInTable(columnfieldNameToSearch, value, columnfieldNameToClick, 1);
	}
	
	private void doClickLinkInTable(String tableFieldName, int columnNumber, int rowNumber, int linkNumberInCell) throws GUIException {
		String cellLocator;
		String linkLocator;

		try {
			cellLocator = getTableCellLocator(tableFieldName, columnNumber, rowNumber);
		} catch (GUIException ge) {
			throw new GUIException("Error while clicking the link '" + tableFieldName + "' on the Page '" + name + "': " + ge.getMessage());
		}

		//linkLocator = cellLocator + "/a[" + Integer.toString(linkNumberInCell) + "]";
		linkLocator = cellLocator + "//a[" + Integer.toString(linkNumberInCell) + "]";
		
		gui.click(linkLocator);
	}

/*	private String getTableCellLocator(String fieldName, int inColumnNumber, int inRowNumber) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		String cellLocator;
		List<String> locators;
		String locator = "";
		int locatorNum;
		int rowNumber = inRowNumber;
		int columnNumber = inColumnNumber;
		Field field;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		cellAccessErrorMessage = "Error while accessing the table '" + fieldName + "' on the Page '" + name + "': ";
		
		//Proceed only if rowNumber refers to at least the first row of data, and columnNumber refers to
		//  at least the first column
		if ((rowNumber >= 0) && (columnNumber >= 0)) {
			locators = field.getLocators();
			locatorNum = gui.searchLocators(locators);

			if (locatorNum < 0) {
				throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
			}

			locator = locators.get(locatorNum);
			
			//***** Changed this on 05/14/2012 to accommodate a possible corner case table.  Be on the lookout for side-effects!
			//  (The corner case is the Associated Partners table on the view oppty page.  The header row and the table
			//  proper are separate /table HTML objects, so I defined a locator for the table proper.  Therefore we have
			//  to tell gui.getTableAsListOfList to include the top row, which is data, not a header row!)
			//***** SEE A FEW LINES BELOW for a corresponding change
			//Nix this!  Decided to handle that corner case in a different way...
			List<List<String>> table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator)); 
			//List<List<String>> table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true)); 

			//Bounds check rowNumber and columnNumber
			if (!(rowNumber < table.size())) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the table contains fewer than " + (rowNumber + 1) + " row(s)");
			}

			if (!(columnNumber < table.get(0).size())) {
				throw new TestDataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is out of bounds - the table contains fewer than " + (columnNumber + 1) + " column(s)");
			}
		} else {
			if (rowNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
			} else if (columnNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is less than zero");
			}
		}

		//Increment columnNumber to account for the fact that HTML column numbers start at 1
		columnNumber++;

		//Increment rowNumber to account for the fact that HTML row numbers start at 1
		//***** Changed this on 05/14/2012 to accommodate a possible corner case table.  Be on the lookout for side-effects!
		//  (The corner case is the Associated Partners table on the view oppty page.  The header row and the table
		//  proper are separate /table HTML objects, so I defined a locator for the table proper.  Therefore we have
		//  to tell gui.getTableAsListOfList to include the top row, which is data, not a header row!)
		//***** SEE A FEW LINES ABOVE for a corresponding change
		//Nix this!  Decided to handle that corner case in a different way...
		rowNumber++;

		//TODO NOTE that some cells are th, not td (e.g., the cells in the Related To column of the Items to Approve related list)
		//  Using td here will not only cause those cells to not be found, but intervening th cells in a row of td cells will cause
		//  us to build incorrect locators for those as well!!!  So let's try using *
		//cellLocator = locator + "/tr[" + Integer.toString(rowNumber) + "]/td[" + Integer.toString(columnNumber) + "]";

		//And let's try //tr instead of /tr.  That way for the locator will work for tables with intervening elements between
		//  the table and tr tags, such as thead or tbody
		//cellLocator = locator + "/tr[" + Integer.toString(rowNumber) + "]/*[" + Integer.toString(columnNumber) + "]";
		cellLocator = locator + "//tr[" + Integer.toString(rowNumber) + "]/*[" + Integer.toString(columnNumber) + "]";

		return cellLocator;
	}
*/
	private String getTableCellLocator(String tableFieldName, int inColumnNumber, int inRowNumber) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		String cellLocator;
		List<String> locators;
		String locator = "";
		int locatorNum;
		int rowNumber = inRowNumber;
		int columnNumber = inColumnNumber;
		Field field;

		try {
			field = getFieldMetadata(tableFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		cellAccessErrorMessage = "Error while accessing the table '" + tableFieldName + "' on the Page '" + name + "': ";
		
		//Proceed only if rowNumber refers to at least the first row of data, and columnNumber refers to
		//  at least the first column
		if ((rowNumber >= 0) && (columnNumber >= 0)) {
			locators = field.getLocators();
			locatorNum = gui.searchLocators(locators);

			if (locatorNum < 0) {
				throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
			}

			locator = locators.get(locatorNum);
			
			//***** Changed this on 05/14/2012 to accommodate a possible corner case table.  Be on the lookout for side-effects!
			//  (The corner case is the Associated Partners table on the view oppty page.  The header row and the table
			//  proper are separate /table HTML objects, so I defined a locator for the table proper.  Therefore we have
			//  to tell gui.getTableAsListOfList to include the top row, which is data, not a header row!)
			//***** SEE A FEW LINES BELOW for a corresponding change
			//Nix this!  Decided to handle that corner case in a different way...
			//List<List<String>> table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator)); 
			//List<List<String>> table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, true)); 
			
			//Bounds check rowNumber and columnNumber
			int tableSize = gui.getTableSize(locator);
			if (!(rowNumber < tableSize)) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the table contains fewer than " + (rowNumber + 1) + " row(s)");
			}

			int tableWidth = gui.getTableWidth(locator);
			if (!(columnNumber < tableWidth)) {
				throw new TestDataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is out of bounds - the table contains fewer than " + (columnNumber + 1) + " column(s)");
			}
		} else {
			if (rowNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
			} else if (columnNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is less than zero");
			}
		}

		//Increment columnNumber to account for the fact that HTML column numbers start at 1
		columnNumber++;

		//Increment rowNumber to account for the fact that HTML row numbers start at 1
		//***** Changed this on 05/14/2012 to accommodate a possible corner case table.  Be on the lookout for side-effects!
		//  (The corner case is the Associated Partners table on the view oppty page.  The header row and the table
		//  proper are separate /table HTML objects, so I defined a locator for the table proper.  Therefore we have
		//  to tell gui.getTableAsListOfList to include the top row, which is data, not a header row!)
		//***** SEE A FEW LINES ABOVE for a corresponding change
		//Nix this!  Decided to handle that corner case in a different way...
		rowNumber++;

		//TODO NOTE that some cells are th, not td (e.g., the cells in the Related To column of the Items to Approve related list)
		//  Using td here will not only cause those cells to not be found, but intervening th cells in a row of td cells will cause
		//  us to build incorrect locators for those as well!!!  So let's try using *
		//cellLocator = locator + "/tr[" + Integer.toString(rowNumber) + "]/td[" + Integer.toString(columnNumber) + "]";

		//And let's try //tr instead of /tr.  That way for the locator will work for tables with intervening elements between
		//  the table and tr tags, such as thead or tbody
		//cellLocator = locator + "/tr[" + Integer.toString(rowNumber) + "]/*[" + Integer.toString(columnNumber) + "]";
		//cellLocator = locator + "//tr[" + Integer.toString(rowNumber) + "]/*[" + Integer.toString(columnNumber) + "]";
		
		//We need to account for the possibility that the header row contains links (for use in sorting).  If it does, even
		//  though all table methods don't return the first row (under the assumption that is is a header row), our xpath
		//  needs to exclude it so that we don't click it instead of the first data row  
		
		//saxenas - following code was causing no element to be selected 
		//cellLocator = locator + "//tr[" + Integer.toString(rowNumber) + "][not(contains(@class, 'header'))]/*[" + Integer.toString(columnNumber) + "]";
		
		cellLocator = locator + "//tr[not(contains(@class, 'header'))][" + Integer.toString(rowNumber) + "]/*[" + Integer.toString(columnNumber) + "]";

		return cellLocator;
	}
	
	@Override
	public String getRelatedListCell(String columnfieldName, int rowNumber) {
		String value = "";

		try {
			validateParameterizedLocatorType(columnfieldName, EISConstants.ParameterizedLocatorType.RELATED_LIST_CELL_LOOKUP);

			value = doGetRelatedListCell(getFieldMetadata(columnfieldName).getLocatorParmsSets(), rowNumber);
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnfieldName + "' on the page '" + name + "': " + ge.getMessage());
		}

		return value;
	}

	@Override
	public int getNumRowsInRelatedList(String relatedListFieldName) {
		int numRows = 0;

		try {
			numRows = doGetNumRowsInRelatedList(relatedListFieldName);
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		return numRows;
	}
	
	@Override
	public List<List<String>> getRelatedList(String relatedListFieldName) {
		List<List<String>> relatedListContents = null;

		try {
			validateParameterizedLocatorType(relatedListFieldName, EISConstants.ParameterizedLocatorType.RELATED_LIST_CELL_LOOKUP);

			relatedListContents = doGetRelatedList(getFieldMetadata(relatedListFieldName).getLocatorParmsSets());
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + relatedListFieldName + "' on the page '" + name + "': " + ge.getMessage());
		}

		return relatedListContents;
	}
	
/*	@Override
	public String[] getRelatedListRow(String relatedListFieldName, int rowNumber) {
		String[] rowContents = null;

		try {
			validateParameterizedLocatorType(relatedListFieldName, EISConstants.ParameterizedLocatorType.RELATED_LIST_CELL_LOOKUP);

			rowContents = doGetRelatedListRow(getFieldMetadata(relatedListFieldName).getLocatorParmsSets(), rowNumber);
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + relatedListFieldName + "' on the page '" + name + "': " + ge.getMessage());
		}

		return rowContents;
	}
*/
	@Override
	public List<String> getRelatedListRow(String relatedListFieldName, int rowNumber) {
		List<String> rowContents = null;

		try {
			validateParameterizedLocatorType(relatedListFieldName, EISConstants.ParameterizedLocatorType.RELATED_LIST_CELL_LOOKUP);

			rowContents = doGetRelatedListRow(getFieldMetadata(relatedListFieldName).getLocatorParmsSets(), rowNumber);
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + relatedListFieldName + "' on the page '" + name + "': " + ge.getMessage());
		}

		return rowContents;
	}
	
	@Override
	public List<String> getRelatedListRow(String columnfieldNameToSearch, String value, boolean isCaseInsensitive) {
		List<String> rowContents = null;

		try {
			validateParameterizedLocatorType(columnfieldNameToSearch, EISConstants.ParameterizedLocatorType.RELATED_LIST_CELL_LOOKUP);

			rowContents = doGetRelatedListRow(getFieldMetadata(columnfieldNameToSearch).getLocatorParmsSets(), value, isCaseInsensitive);
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnfieldNameToSearch + "' on the page '" + name + "': " + ge.getMessage());
		}

		return rowContents;
	}
		
	@Override
	public List<String> getRelatedListRow(String columnfieldNameToSearch, String value) {
		//return getRelatedListRow(fieldNameToSearch, value, false);
		return getRelatedListRow(columnfieldNameToSearch, value, true);
	}
		
	@Override
	public int getRelatedListRowNum(String columnfieldNameToSearch, String value, boolean isCaseInsensitive) {
		int rowNum = 0;

		try {
			validateParameterizedLocatorType(columnfieldNameToSearch, EISConstants.ParameterizedLocatorType.RELATED_LIST_CELL_LOOKUP);

			rowNum = doGetRelatedListRowNum(getFieldMetadata(columnfieldNameToSearch).getLocatorParmsSets(), value, isCaseInsensitive);
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnfieldNameToSearch + "' on the page '" + name + "': " + ge.getMessage());
		}

		return rowNum;
	}
		
	@Override
	public int getRelatedListRowNum(String columnfieldNameToSearch, String value) {
		//return getRelatedListRowNum(columnfieldNameToSearch, value, false);
		return getRelatedListRowNum(columnfieldNameToSearch, value, true);
	}
		
	private String doGetRelatedListCell(List<List<String>> locatorParmsSets, int rowNumber) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		List<String> locatorParms = new ArrayList<String>();
		List<List<String>> table = null; 
		String locator;
		String value = "";
		String tableHeader;
		int columnNumber;

		locatorParms = searchRelatedListLocatorParmSets(locatorParmsSets);
		tableHeader = locatorParms.get(0).trim();

		columnNumber = Integer.parseInt(locatorParms.get(1));

		cellAccessErrorMessage = "Error while accessing the related list '" + tableHeader + "' on the Page '" + name + "': ";

		//Proceed only if rowNumber refers to at least the first row of data (not the header row), and columnNumber refers to
		//  at least the first column
		if ((rowNumber >= 0) && (columnNumber >= 0)) {
			locator = EISConstants.RELATED_LIST_XPATH.replace(EISConstants.RELATED_LIST_HEADER_TOKEN, tableHeader.toUpperCase());

			expandRelatedList(locator);
			
			table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator)); 

			//Account for the header row when checking row number against table size
			//if (!(rowNumber < (table.size() - 1))) {
			//The new version of getTableAsListOfList does not return the header row by default
			if (!(rowNumber < table.size())) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the related list contains fewer than " + (rowNumber + 1) + " row(s)");
			}

			if (!(columnNumber < table.get(0).size())) {
				throw new MetadataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is out of bounds - the related list contains fewer than " + (columnNumber + 1) + " column(s)");
			}
		} else {
			if (rowNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
			} else if (columnNumber < 0) {
				throw new MetadataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is less than zero");
			}
		}

		//The new version of getTableAsListOfList does not return the header row by default
		//value = table.get(rowNumber + 1).get(columnNumber).trim();
		value = table.get(rowNumber).get(columnNumber).trim();

		return value;
	}
	
	@Override
	public int getRelatedListColumnNum(String columnfieldName) {
		int columnNum = -1;

		try {
			columnNum = Integer.parseInt(searchRelatedListLocatorParmSets(columnfieldName).get(1).trim());
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		return columnNum;
	}
	
	@Override
	public List<String> getRelatedListColumnLocatorParmSet(String columnfieldName) {
		List<String> locatorParms = new ArrayList<String>();

		try {
			locatorParms = searchRelatedListLocatorParmSets(columnfieldName);
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		return locatorParms;
	}
	
	private List<String> searchRelatedListLocatorParmSets(String fieldName) throws MetadataException {
		//TODO  Decide how to handle waiting for the related list to be present.  gui.isElementPresent(locator)
		//  does not wait - and we don't want it to!  SEE doGetInfoPanelCell for the solution.

		Field field;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the related list '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}

		return searchRelatedListLocatorParmSets(field.getLocatorParmsSets());
	}

	private List<String> searchRelatedListLocatorParmSets(List<List<String>> locatorParmsSets) throws MetadataException {
		//TODO  Decide how to handle waiting for the related list to be present.  gui.isElementPresent(locator)
		//  does not wait - and we don't want it to!  SEE doGetInfoPanelCell for the solution.

		final int NUM_TRIES = 3;

		List<String> locatorParms = new ArrayList<String>();
		List<String> invalidTableHeaders = new ArrayList<String>();
		String locator;
		String tableHeader;
		boolean foundTable = false;

		for (int tryNum = 1; tryNum <= NUM_TRIES; tryNum++) {
			for (int index = 0; index < locatorParmsSets.size(); index++) {
				locatorParms = locatorParmsSets.get(index);

				tableHeader = locatorParms.get(0).trim();

				locator = EISConstants.RELATED_LIST_XPATH.replace(EISConstants.RELATED_LIST_HEADER_TOKEN, tableHeader.toUpperCase());
				if (gui.isElementPresent(locator)) {
					foundTable = true;

					break;
				}

				//If we are on the final loop through locatorParmsSets, and if the table was not found using this instance of
				//  locatorParms, save the table header set for possible inclusion in an error message
				if (tryNum == NUM_TRIES) {
					if (!foundTable) {
						invalidTableHeaders.add(tableHeader);
					}
				}
			}

			if (foundTable) {
				break;
			}

			refresh();
		}

		if (!foundTable) {
			throw new MetadataException("Error while searching for a related list element on the page '" + name + "': the related list does not exist; tried these locators, " + NUM_TRIES + " times: " + invalidTableHeaders.toString());
		}

		return locatorParms;
	}

	private void expandRelatedList(String locator) {
		String showMoreLocator = locator + EISConstants.RELATED_LIST_SHOW_MORE_LINK_XPATH;

		while (gui.isElementPresent(showMoreLocator)) {
			//Sometimes the preceding isElementPresent call will return true, but this click call will fail.
			//  This can happen when the locator is still present after being clicked during the prior iteration
			//  of the loop. So we will sleep after every click call - whether successful or not
			try {
				gui.click(showMoreLocator);
			} catch (Exception e) {}

			//Sleep after each click, in an attempt to avoid the timing issues described above
			Util.sleep(1250);
		}
	}

	@Override
	public void clickLinkInRelatedList(String columnFieldName, int rowNumber, int linkNumberInCell) {
		try {
			doClickLinkInRelatedList(columnFieldName, rowNumber, linkNumberInCell);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}
	}

	@Override
	public void clickLinkInRelatedList(String columnFieldName, int rowNumber) {
		clickLinkInRelatedList(columnFieldName, rowNumber, 1);
	}
	
	@Override
	public void clickLinkInRelatedList(String columnFieldNameToSearch, String value, String columnFieldNameToClick, int linkNumberInCell) {
		int rowNum = 0;

		try {
			validateParameterizedLocatorType(columnFieldNameToSearch, EISConstants.ParameterizedLocatorType.RELATED_LIST_CELL_LOOKUP);

			//rowNum = doGetRelatedListRowNum(getFieldMetadata(columnFieldNameToSearch).getLocatorParmsSets(), value, false);
			rowNum = doGetRelatedListRowNum(getFieldMetadata(columnFieldNameToSearch).getLocatorParmsSets(), value, true);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnFieldNameToSearch + "' on the Page '" + name + "': " + me.getMessage());
		}
		
		if (rowNum >= 0) {
			clickLinkInRelatedList(columnFieldNameToClick, rowNum, linkNumberInCell);
		} else {
			EISTestBase.fail("Error while accessing the GUI element '" + columnFieldNameToSearch + "' on the page '" + name + "': the value '" + value + "' was not found");
		}
	}

	@Override
	public void clickLinkInRelatedList(String columnFieldNameToSearch, String value, String columnFieldNameToClick) {
		clickLinkInRelatedList(columnFieldNameToSearch, value, columnFieldNameToClick, 1);
	}
	
	private void doClickLinkInRelatedList(String columnFieldName, int rowNumber, int linkNumberInCell) throws GUIException {
		String cellLocator;
		String linkLocator;
		Field field;

		try {
			field = getFieldMetadata(columnFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while clicking the link '" + columnFieldName + "' on the Page '" + name + "': " + me.getMessage());
		}
		
		if (field.isLocatorParameterized()) {
			switch (field.getParameterizedLocatorType()) {
				case RELATED_LIST_CELL_LOOKUP:	{
					try {
						cellLocator = getRelatedListCellLocator(field.getLocatorParmsSets(), rowNumber);
						linkLocator = cellLocator + "/a[" + Integer.toString(linkNumberInCell) + "]";

						gui.click(linkLocator);
					} catch (GUIException ge) {
						throw new GUIException("Error while clicking the link '" + columnFieldName + "' on the page '" + name + "': " + ge.getMessage());
					}

					break;
				}
				default:	{
					throw new MetadataException("Error while clicking the link '" + columnFieldName + "' on the page '" + name + "': This link does not live in a related list");
				}
			}
		} else {
			throw new MetadataException("Error while clicking the link '" + columnFieldName + "' on the page '" + name + "': This link does not live in a related list");
		}
	}

/*	private String getRelatedListCellLocator(List<List<String>> locatorParmsSets, int inRowNumber) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		String cellLocator;
		List<String> locatorParms = new ArrayList<String>();
		List<List<String>> table = null; 
		String locator = "";
		String tableHeader = "";
		int rowNumber = inRowNumber;
		int columnNumber;

		locatorParms = searchRelatedListLocatorParmSets(locatorParmsSets);
		tableHeader = locatorParms.get(0).trim();

		columnNumber = Integer.parseInt(locatorParms.get(1));

		cellAccessErrorMessage = "Error while accessing the related list '" + tableHeader + "' on the Page '" + name + "': ";

		//Proceed only if rowNumber refers to at least the first row of data (not the header row), and columnNumber refers to
		//  at least the first column
		if ((rowNumber >= 0) && (columnNumber >= 0)) {
			locator = EISConstants.RELATED_LIST_XPATH.replace(EISConstants.RELATED_LIST_HEADER_TOKEN, tableHeader.toUpperCase());

			expandRelatedList(locator);
			
			table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator)); 

			//Account for the header row when checking row number against table size
			//if (!(rowNumber < (table.size() - 1))) {
			//The new version of getTableAsListOfList does not return the header row by default
			if (!(rowNumber < table.size())) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the related list contains fewer than " + (rowNumber + 1) + " row(s)");
			}

			if (!(columnNumber < table.get(0).size())) {
				throw new MetadataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is out of bounds - the related list contains fewer than " + (columnNumber + 1) + " column(s)");
			}
		} else {
			if (rowNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
			} else if (columnNumber < 0) {
				throw new MetadataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is less than zero");
			}
		}

		//Increment rowNumber to account for the fact that HTML row numbers start at 1
		rowNumber++;

		//Increment rowNumber (again) to account for the header row. (Even though the table contents returned
		//  by our call to gui.getTableAsListOfList (above) does not contain the header row, HTML does
		//  count it as a row in the table; we are accommodating that here.)
		rowNumber++;

		//Increment columnNumber to account for the fact that HTML column numbers start at 1
		columnNumber++;

		//TODO NOTE that some cells are th, not td (e.g., the cells in the Related To column of the Items to Approve related list)
		//  Using td here will not only cause those cells to not be found, but intervening th cells in a row of td cells will cause
		//  us to build incorrect locators for those as well!!!  So let's try using *
		//cellLocator = locator + "/tr[" + Integer.toString(rowNumber) + "]/td[" + Integer.toString(columnNumber) + "]";
		cellLocator = locator + "/tr[" + Integer.toString(rowNumber) + "]/*[" + Integer.toString(columnNumber) + "]";

		return cellLocator;
	}
*/
	private String getRelatedListCellLocator(List<List<String>> locatorParmsSets, int inRowNumber) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		String cellLocator;
		List<String> locatorParms = new ArrayList<String>();
		String locator = "";
		String tableHeader = "";
		int rowNumber = inRowNumber;
		int columnNumber;

		locatorParms = searchRelatedListLocatorParmSets(locatorParmsSets);
		tableHeader = locatorParms.get(0).trim();

		columnNumber = Integer.parseInt(locatorParms.get(1));

		cellAccessErrorMessage = "Error while accessing the related list '" + tableHeader + "' on the Page '" + name + "': ";

		//Proceed only if rowNumber refers to at least the first row of data (not the header row), and columnNumber refers to
		//  at least the first column
		if ((rowNumber >= 0) && (columnNumber >= 0)) {
			locator = EISConstants.RELATED_LIST_XPATH.replace(EISConstants.RELATED_LIST_HEADER_TOKEN, tableHeader.toUpperCase());

			expandRelatedList(locator);
			
			//Bounds check rowNumber and columnNumber
			int tableSize = gui.getTableSize(locator);
			if (!(rowNumber < tableSize)) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the related list contains fewer than " + (rowNumber + 1) + " row(s)");
			}

			int tableWidth = gui.getTableWidth(locator);
			if (!(columnNumber < tableWidth)) {
				throw new MetadataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is out of bounds - the related list contains fewer than " + (columnNumber + 1) + " column(s)");
			}
		} else {
			if (rowNumber < 0) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
			} else if (columnNumber < 0) {
				throw new MetadataException(cellAccessErrorMessage + "The column number (" + columnNumber + ") is less than zero");
			}
		}

		//Increment rowNumber to account for the fact that HTML row numbers start at 1
		rowNumber++;

		//Increment rowNumber (again) to account for the header row. (Even though the table contents returned
		//  by our call to gui.getTableAsListOfList (above) does not contain the header row, HTML does
		//  count it as a row in the table; we are accommodating that here.)
		rowNumber++;

		//Increment columnNumber to account for the fact that HTML column numbers start at 1
		columnNumber++;

		//TODO NOTE that some cells are th, not td (e.g., the cells in the Related To column of the Items to Approve related list)
		//  Using td here will not only cause those cells to not be found, but intervening th cells in a row of td cells will cause
		//  us to build incorrect locators for those as well!!!  So let's try using *
		//cellLocator = locator + "/tr[" + Integer.toString(rowNumber) + "]/td[" + Integer.toString(columnNumber) + "]";
		cellLocator = locator + "/tr[" + Integer.toString(rowNumber) + "]/*[" + Integer.toString(columnNumber) + "]";

		return cellLocator;
	}
	
	@Override
	public int getNumRowsInTable(String tableFieldName, boolean returnFirstRow) {
		int numRows = 0;

		try {
			numRows = doGetNumRowsInTable(tableFieldName, returnFirstRow);
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		return numRows;
	}

	@Override
	public int getNumRowsInTable(String tableFieldName) {
		return getNumRowsInTable(tableFieldName, false);
	}

	private int doGetNumRowsInTable(String tableFieldName, boolean returnFirstRow) throws MetadataException {
		List<String> locators;
		int locatorNum;
		String locator;
		Field field;
		
		try {
			field = getFieldMetadata(tableFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the table '" + tableFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		locators = field.getLocators();
		locatorNum = gui.searchLocators(locators);

		if (locatorNum < 0) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
		}

		locator = locators.get(locatorNum);

		//return gui.getTableAsListOfList(locator).size();
		return gui.getTableSize(locator, returnFirstRow);
	}

/*	@Override
	public String[] getTableRow(String tableFieldName, int rowNumber) {
		String[] rowContents = null;

		try {
			rowContents = doGetTableRow(tableFieldName, rowNumber);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return rowContents;
	}
*/
	@Override
	public List<String> getTableRow(String tableFieldName, int rowNumber, boolean returnFirstRow) {
		List<String> rowContents = null;

		try {
			rowContents = doGetTableRow(tableFieldName, rowNumber, returnFirstRow);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return rowContents;
	}

	@Override
	public List<String> getTableRow(String tableFieldName, int rowNumber) {
		return getTableRow(tableFieldName, rowNumber, false);
	}
		
/*	private String[] doGetTableRow(String tableFieldName, int rowNumber) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		String[] rowContents = null;
		List<List<String>> table = null;
		List<String> locators;
		int locatorNum;
		String locator;
		Field field;
		
		try {
			field = getFieldMetadata(tableFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		cellAccessErrorMessage = "Error while accessing the table '" + tableFieldName + "' on the Page '" + name + "': ";
		
		//Proceed only if rowNumber refers to at least the first row of data
		if (rowNumber >= 0) {
			locators = field.getLocators();
			locatorNum = gui.searchLocators(locators);
	
			if (locatorNum < 0) {
				throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
			}

			locator = locators.get(locatorNum);

			table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator));
			
			//Bounds check rowNumber
			if (!(rowNumber < table.size())) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the table contains fewer than " + (rowNumber + 1) + " row(s)");
			}
		} else {
			throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
		}

		rowContents = Util.listOfStringTrim(table.get(rowNumber)).toArray(new String[0]);
		
		return rowContents;
	}
*/
/*	private List<String> doGetTableRow(String tableFieldName, int rowNumber) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		List<List<String>> table = null;
		List<String> locators;
		int locatorNum;
		String locator;
		Field field;
		
		try {
			field = getFieldMetadata(tableFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		cellAccessErrorMessage = "Error while accessing the table '" + tableFieldName + "' on the Page '" + name + "': ";
		
		//Proceed only if rowNumber refers to at least the first row of data
		if (rowNumber >= 0) {
			locators = field.getLocators();
			locatorNum = gui.searchLocators(locators);
	
			if (locatorNum < 0) {
				throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
			}

			locator = locators.get(locatorNum);

			table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator));
			
			//Bounds check rowNumber
			if (!(rowNumber < table.size())) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the table contains fewer than " + (rowNumber + 1) + " row(s)");
			}
		} else {
			throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
		}

		return Util.listOfStringTrim(table.get(rowNumber));
	}
*/
	private List<String> doGetTableRow(String tableFieldName, int rowNumber, boolean returnFirstRow) throws MetadataException, TestDataException {
		String cellAccessErrorMessage;
		List<List<String>> table = null;
		List<String> locators;
		int locatorNum;
		String locator;
		Field field;
		
		try {
			field = getFieldMetadata(tableFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		cellAccessErrorMessage = "Error while accessing the table '" + tableFieldName + "' on the Page '" + name + "': ";
		
		//Proceed only if rowNumber refers to at least the first row of data
		if (rowNumber >= 0) {
			locators = field.getLocators();
			locatorNum = gui.searchLocators(locators);
	
			if (locatorNum < 0) {
				throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
			}

			locator = locators.get(locatorNum);

			table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, returnFirstRow));
			
			//Bounds check rowNumber
			if (!(rowNumber < table.size())) {
				throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the table contains fewer than " + (rowNumber + 1) + " row(s)");
			}
		} else {
			throw new TestDataException(cellAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
		}

		return Util.listOfStringTrim(table.get(rowNumber));
	}
		
	@Override
	public List<String> getTableRow(String columnFieldNameToSearch, String value, boolean isCaseInsensitive) {
		List<String> rowContents = null;
		List<String> locatorParms;

		try {
			validateParameterizedLocatorType(columnFieldNameToSearch, EISConstants.ParameterizedLocatorType.TABLE_CELL_LOOKUP);

			//locatorParms = name of the Field object that contains locator(s) to the table, and column number
			locatorParms = getFieldMetadata(columnFieldNameToSearch).getLocatorParmsSets().get(0);

			rowContents = doGetTableRow(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), value, isCaseInsensitive);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnFieldNameToSearch + "' on the page '" + name + "': " + me.getMessage());
		}

		return rowContents;
	}

	@Override
	public List<String> getTableRow(String columnFieldNameToSearch, String value) {
		//return getTableRow(columnFieldNameToSearch, value, false);
		return getTableRow(columnFieldNameToSearch, value, true);
	}
		
/*	private String[] doGetTableRow(String fieldName, int columnNumber, String value, boolean isCaseInsensitive) throws MetadataException {
		String[] rowContents = null;
		List<String> locators;
		String locator = "";
		int locatorNum;
		int rowNum;
		Field field;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}

		locators = field.getLocators();
		locatorNum = gui.searchLocators(locators);

		if (locatorNum < 0) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
		}

		locator = locators.get(locatorNum);
		
		List<List<String>> table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator));
		
		rowNum = doGetRowNum(table, columnNumber, value, isCaseInsensitive);
		if (rowNum >= 0) {
			rowContents = Util.listOfStringTrim(table.get(rowNum)).toArray(new String[0]);
		}

		return rowContents;
	}
*/
	private List<String> doGetTableRow(String tableFieldName, int columnNumber, String value, boolean isCaseInsensitive) throws MetadataException {
		List<String> rowContents = new ArrayList<String>();
		List<String> locators;
		String locator = "";
		int locatorNum;
		int rowNum;
		Field field;
		
		try {
			field = getFieldMetadata(tableFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': " + me.getMessage());
		}

		locators = field.getLocators();
		locatorNum = gui.searchLocators(locators);

		if (locatorNum < 0) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
		}

		locator = locators.get(locatorNum);
		
		List<List<String>> table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator));
		
		rowNum = doGetRowNum(table, columnNumber, value, isCaseInsensitive);
		if (rowNum >= 0) {
			rowContents = Util.listOfStringTrim(table.get(rowNum));
		}

		return rowContents;
	}


	@Override
/*	public String[] getTableColumn(String fieldName) {*/
	public List<String> getTableColumn(String columnFieldName, boolean returnFirstRow) {
		List<String> columnContents = null;
		List<String> locatorParms;

		try {
			validateParameterizedLocatorType(columnFieldName, EISConstants.ParameterizedLocatorType.TABLE_CELL_LOOKUP);

			//locatorParms = name of the Field object that contains locator(s) to the table, and column number
			locatorParms = getFieldMetadata(columnFieldName).getLocatorParmsSets().get(0);

			columnContents = doGetTableColumn(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), returnFirstRow);
		} catch (GUIException ge) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnFieldName + "' on the page '" + name + "': " + ge.getMessage());
		}

		return columnContents;
	}

	@Override
	public List<String> getTableColumn(String columnFieldName) {
		return getTableColumn(columnFieldName, false);
	}
	
/*	private String[] doGetTableColumn(String tableFieldName, int columnNumber) throws MetadataException, TestDataException {*/
	private List<String> doGetTableColumn(String tableFieldName, int columnNumber, boolean returnFirstRow) throws MetadataException, TestDataException {
		List<String> columnContents = new ArrayList<String>();
		List<List<String>> table = null;
		List<String> locators;
		int locatorNum;
		String locator;
		Field field;
		
		try {
			field = getFieldMetadata(tableFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		locators = field.getLocators();
		locatorNum = gui.searchLocators(locators);

		if (locatorNum < 0) {
			throw new MetadataException("Error while accessing the GUI element '" + tableFieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
		}

		locator = locators.get(locatorNum);

		table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator, returnFirstRow));
		
		for (List<String> row : table) {
			columnContents.add(row.get(columnNumber));
		}
		
		return Util.listOfStringTrim(columnContents);
	}
	
	@Override
	public int getTableRowNum(String columnFieldNameToSearch, String value, boolean isCaseInsensitive) {
		int rowNum = 0;
		List<String> locatorParms;

		try {
			validateParameterizedLocatorType(columnFieldNameToSearch, EISConstants.ParameterizedLocatorType.TABLE_CELL_LOOKUP);

			//locatorParms = name of the Field object that contains locator(s) to the table, and column number
			locatorParms = getFieldMetadata(columnFieldNameToSearch).getLocatorParmsSets().get(0);

			rowNum = doGetTableRowNum(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), value, isCaseInsensitive);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while accessing the GUI element '" + columnFieldNameToSearch + "' on the page '" + name + "': " + me.getMessage());
		}

		return rowNum;
	}

	@Override
	public int getTableRowNum(String columnFieldNameToSearch, String value) {
		//return getTableRowNum(columnFieldNameToSearch, value, false);
		return getTableRowNum(columnFieldNameToSearch, value, true);
	}
	
	private int doGetTableRowNum(String fieldName, int columnNumber, String value, boolean isCaseInsensitive) throws MetadataException {
		List<String> locators;
		String locator = "";
		int locatorNum;
		int rowNum;
		Field field;
		
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		locators = field.getLocators();
		locatorNum = gui.searchLocators(locators);

		if (locatorNum < 0) {
			throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': A valid Selenium locator was not found; tried: " + locators.toString());
		}

		locator = locators.get(locatorNum);
		
		List<List<String>> table = new ArrayList<List<String>>(gui.getTableAsListOfList(locator));
		
		rowNum = doGetRowNum(table, columnNumber, value, isCaseInsensitive);

		return rowNum;
	}

	private int doGetRowNum(List<List<String>> table, int columnNumber, String value, boolean isCaseInsensitive) {
		//Searches a List<List<String>> (typically populated by the caller with the contents of a table or related list) and returns the
		//  number of the "row" in which the value was found
		int rowNum = -1;
		List<String> cells = new ArrayList<String>();

		ListIterator<List<String>> rowItr = table.listIterator();
		while (rowItr.hasNext()) {
			cells = Util.listOfStringTrim(rowItr.next());

			if (Util.listOfStringFind(cells, value, isCaseInsensitive) == columnNumber) {
				rowNum = rowItr.nextIndex() - 1;
				
				break;
			}
		}

		return rowNum;
	}
	
	private String doGetRelatedListLocator (List<List<String>> locatorParmsSets) throws MetadataException{
		List<String> locatorParms = new ArrayList<String>();
		String locator;
		String tableHeader;

		locatorParms = searchRelatedListLocatorParmSets(locatorParmsSets);
		tableHeader = locatorParms.get(0).trim();

		locator = EISConstants.RELATED_LIST_XPATH.replace(EISConstants.RELATED_LIST_HEADER_TOKEN, tableHeader.toUpperCase());
		expandRelatedList(locator);
		
		return locator;
	}
	
	private int doGetNumRowsInRelatedList(String relatedListFieldName) throws MetadataException {
		int numRows = 0;
		Field field;
		
		try {
			field = getFieldMetadata(relatedListFieldName);
		} catch (MetadataException me) {
			throw new MetadataException("Error while accessing the related list '" + relatedListFieldName + "' on the page '" + name + "': " + me.getMessage());
		}

		if (field.isLocatorParameterized()) {
			switch (field.getParameterizedLocatorType()) {
				case RELATED_LIST_CELL_LOOKUP:	{
					try {
						numRows = doGetNumRowsInRelatedList(field.getLocatorParmsSets());
					} catch (MetadataException me) {
						throw new MetadataException("Error while accessing the related list '" + relatedListFieldName + "' on the page '" + name + "': " + me.getMessage());
					}

					break;
				}
				default:	{
					throw new MetadataException("Error while accessing the related list '" + relatedListFieldName + "' on the page '" + name + "': This GUI element is not a related list");
				}
			}
		} else {
			throw new MetadataException("Error while accessing the related list '" + relatedListFieldName + "' on the page '" + name + "': This GUI element is not a related list");
		}

		return numRows;
	}

	private int doGetNumRowsInRelatedList(List<List<String>> locatorParmsSets) throws MetadataException {
		String locator = doGetRelatedListLocator(locatorParmsSets);
		
		//getTableSize() does not return the header row by default
		return gui.getTableSize(locator);
	}

	private List<List<String>> doGetRelatedList(List<List<String>> locatorParmsSets) throws MetadataException {
		String locator = doGetRelatedListLocator(locatorParmsSets);
		
		return gui.getTableAsListOfList(locator);
	}
	
	private int doGetRelatedListRowNum(List<List<String>> locatorParmsSets, String value, boolean isCaseInsensitive) throws MetadataException {
		List<List<String>> table = null; 
		int rowNum;
		int columnNumber;
		
		columnNumber = Integer.parseInt(searchRelatedListLocatorParmSets(locatorParmsSets).get(1));
		table = doGetRelatedList(locatorParmsSets);
		rowNum = doGetRowNum(table, columnNumber, value, isCaseInsensitive);

		return rowNum;
	}
		
	private List<String> doGetRelatedListRow(List<List<String>> locatorParmsSets, int rowNumber) throws MetadataException, TestDataException {
		List<List<String>> table = null;  
		String tableHeader = searchRelatedListLocatorParmSets(locatorParmsSets).get(0);
		String rowAccessErrorMessage = "Error while accessing the related list '" + tableHeader + "' on the Page '" + name + "': ";

		//Proceed only if rowNumber refers to at least the first row of data (not the header row)
		if (rowNumber >= 0) {
			table = doGetRelatedList(locatorParmsSets);

			if (!(rowNumber < table.size())) {
				throw new TestDataException(rowAccessErrorMessage + "The row number (" + rowNumber + ") is out of bounds - the related list contains fewer than " + (rowNumber + 1) + " row(s)");
			}
		} else {
			throw new TestDataException(rowAccessErrorMessage + "The row number (" + rowNumber + ") is less than zero");
		}

		return Util.listOfStringTrim(table.get(rowNumber));
	}

	private List<String> doGetRelatedListRow(List<List<String>> locatorParmsSets, String value, boolean isCaseInsensitive) throws MetadataException, TestDataException {
		List<String> rowContents = new ArrayList<String>();
		List<List<String>> table = null; 
		int rowNum;
		
		rowNum = doGetRelatedListRowNum (locatorParmsSets, value, isCaseInsensitive);
		table = doGetRelatedList(locatorParmsSets);
		if (rowNum >= 0) {
			rowContents = Util.listOfStringTrim(table.get(rowNum));
		}

		return rowContents;
	}

	private void validateParameterizedLocatorType(String fieldName, EISConstants.ParameterizedLocatorType targetParameterizedLocatorType) throws MetadataException {
		Field field = null;
		
		field = getFieldMetadata(fieldName);
		
		if (field.isLocatorParameterized()) {
			if (field.getParameterizedLocatorType() != targetParameterizedLocatorType) {
				throw new MetadataException("The locator type is not " + targetParameterizedLocatorType);
			}
		} else {
			throw new MetadataException("The locator type is not " + targetParameterizedLocatorType);
		}
	}

	@Override
	public boolean click(String fieldName) {
		//This (and its 'AndWait' derivatives) is intended for use on LINK and BUTTON fields.
		//  It is a convenience method for the test writer; its name is more intuitive than
		//  populateField().  It calls the version of populateField that defines a value parameter.
		//Instance is irrelevant - the caller is specifying a value, so we don't need to look up the
		//  proper instance in the test data; in effect the caller is creating an instance.
		boolean populateField=false;
		try {
			/*return populateField(fieldName, "");*/
			populateField=populateField(fieldName, "");
			//intentionally left the exception block blank
		} catch (StaleElementReferenceException e) {}
		return populateField;
	}
	
	/**
	 * @Description This is not correct method for mouse Hover. Or the method name should  have been different. It is quite misleading with the body of the method.
	 * However to retain the method functionality of hovering
	 * Adding the hoverover to this.
	 * Also it is not requried to return the boolean value when you are doing an action on the element
	 */
	@Override
	public boolean hoverOver(String fieldName){		
//		 populateField(fieldName);		 
		if(isFieldVisible(fieldName)){
		 mouseHover();
		}
		 return true;		//always return true.  Retaining this way as this shouldnt break the existing tests
	}
	private void mouseHover() {
		try {			
			if (getCurrentWebElementList()!=null){
				hoverOnWebElement(getCurrentWebElement()); 
			}
		} catch (Exception e) {
			Util.printInfo(e.getMessage());
			Util.printError("Unable to hover on the metadata element.Pls check the stack trace");
		}
		
	}
	
	
	 // Enchancing this function to include Safari browser Hidden Element objects for Customer Portal Only. 
	// * Need to  test this function on all the projects as this might impact only projects implementing on Safari Browsers
	 
/*@Override 
public boolean clickAndWait(String fieldName, String fieldNameToWaitFor, int timeout) { 
boolean success = click(fieldName); 
 
if (success) { 
success = waitForFieldPresent(fieldNameToWaitFor, timeout); 
} 
 
return success; 
} */
	 
	
	

	@Override 
	// ************************Please read above comments***************************** 
	public boolean clickAndWait(String fieldName, String fieldNameToWaitFor, int timeout) { 
		//reducing the explicit wait from 1min to 20 sec
		timeout = 20000;
	Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities(); 
	String browserName = capabilities.getBrowserName(); 
	boolean success =false; 
	 
	if(!browserName.equalsIgnoreCase("safari")){ 
	 
	 success = click(fieldName); 
	} 
	 
	else{ 
	List<String> temp = CustomerPortalTestBase.homePage.getFieldLocators(""+fieldName+""); 
	for(int i=0;i<temp.size();i++){ 
	Util.sleep(2000); 
	String Xloc =temp.get(i).trim(); 
	System.out.println(Xloc); 
	 
	   //Execute javascript for Valid Xpath 
	try{   
		String GetXpath1 ="document.getElementByXPath = function(sValue){ var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0) { return a.snapshotItem(0); } };"; //document.getElementsByXPath = function(sValue){ var aResult = new Array();var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);for ( var i = 0 ; i < a.snapshotLength ; i++ ){aResult.push(a.snapshotItem(i));} return aResult;};    
	    String jsClick = "var click_ev = document.createEvent(\"MouseEvents\"); click_ev.initEvent(\"click\", true, true); document.getElementByXPath(\""+Xloc+ "\").dispatchEvent(click_ev);";
	
	JavascriptExecutor js = (JavascriptExecutor) driver;  
	System.out.println(jsClick); 
	js.executeScript(GetXpath1);  
	Util.sleep(2000);  
	js.executeScript(jsClick); 
	Util.sleep(2000);
	    success = true; 
	       break; 
	} catch (Exception e){  
	   e.printStackTrace();  
	   System.out.println("Xpath not found "+Xloc);  
	    
	   if(i==temp.size()-1){ 
	   EISTestBase.fail("Valid Xath Not Found for WebElement : \"" +fieldName+ " \"from "+temp); 
	   } 
	    
	   } 
	 
	} 
	} 
	 
	Util.sleep(2000); 
	if (success) { 
	success = waitForFieldPresent(fieldNameToWaitFor, timeout); 
	} 
	return success; 
	 
	} 
	//*********************************************************************************
	
	@Override 
	// ************************Please read above comments***************************** 
	public boolean clickWait(String fieldName, String fieldNameToWaitFor, int timeout) { 
		//reducing the explicit wait from 1min to 20 sec
		timeout = 5000;
	Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities(); 
	String browserName = capabilities.getBrowserName(); 
	boolean success =false; 
	
	List<String> temp = BornInCloudTestBase.homePage.getFieldLocators(""+fieldName+""); 
	for(int i=0;i<temp.size();i++){ 
	Util.sleep(2000); 
	String Xloc =temp.get(i).trim(); 
	System.out.println(Xloc); 
	 
	   //Execute javascript for Valid Xpath 
	try{   
		String GetXpath1 ="document.getElementByXPath = function(sValue){ var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0) { return a.snapshotItem(0); } };"; //document.getElementsByXPath = function(sValue){ var aResult = new Array();var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);for ( var i = 0 ; i < a.snapshotLength ; i++ ){aResult.push(a.snapshotItem(i));} return aResult;};    
	    String jsClick = "var click_ev = document.createEvent(\"MouseEvents\"); click_ev.initEvent(\"click\", true, true); document.getElementByXPath(\""+Xloc+ "\").dispatchEvent(click_ev);";
	
	JavascriptExecutor js = (JavascriptExecutor) driver;  
	System.out.println(jsClick); 
	js.executeScript(GetXpath1);  
	Util.sleep(2000);  
	js.executeScript(jsClick); 
	Util.sleep(2000);
	    success = true; 
	       break; 
	} catch (Exception e){  
	   e.printStackTrace();  
	   System.out.println("Xpath not found "+Xloc);  
	    
	   if(i==temp.size()-1){ 
	   EISTestBase.fail("Valid Xath Not Found for WebElement : \"" +fieldName+ " \"from "+temp); 
	   } 
	    
	   } 
	 
	} 
	 
	 
	Util.sleep(2000); 
	/*if (success) { 
	success = waitForFieldPresent(fieldNameToWaitFor, timeout); 
	} */
	return success; 
	 
	} 
	
	
	//***************************************************************************************
	
	

	@Override
	public boolean clickAndWait(String fieldName, String fieldNameToWaitFor) {
		return clickAndWait(fieldName, fieldNameToWaitFor, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean clickAndWaitForFieldPresent(String fieldName, String fieldNameToWaitFor, int timeout) {
		//boolean success = clickAndWait(fieldName);
		boolean success = click(fieldName);
		
		if (success) {
			success = waitForFieldPresent(fieldNameToWaitFor, timeout);
		}
		
		return success;
	}

	@Override
	public boolean clickAndWaitForFieldPresent(String fieldName, String fieldNameToWaitFor) {
		return clickAndWaitForFieldPresent(fieldName, fieldNameToWaitFor, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean clickAndWaitForFieldPresent(String fieldName, String fieldNameToWaitFor, String timeout) {
		return clickAndWaitForFieldPresent(fieldName, fieldNameToWaitFor, Integer.parseInt(timeout));
	}
	
	@Override
	public boolean clickAndWaitForFieldAbsent(String fieldName, String fieldNameToWaitFor, int timeout) {
		//boolean success = clickAndWait(fieldName);
		boolean success = click(fieldName);
		
		if (success) {
			success = waitForFieldAbsent(fieldNameToWaitFor, timeout);
		}
		
		return success;
	}

	@Override
	public boolean clickAndWaitForFieldAbsent(String fieldName, String fieldNameToWaitFor) {
		return clickAndWaitForFieldAbsent(fieldName, fieldNameToWaitFor, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean clickAndWaitForFieldAbsent(String fieldName, String fieldNameToWaitFor, String timeout) {
		return clickAndWaitForFieldAbsent(fieldName, fieldNameToWaitFor, Integer.parseInt(timeout));
	}
	
/*	@Override
	public boolean clickToSubmit(String fieldName, int waitTime) {
		boolean success;
		this.waitTime = waitTime;

		if (waitTime != EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT) {
			Util.printInfo("When submitting the '" + getPageName() + "' Page (by clicking the Field '" + fieldName + "'), the timeout value used was " + (waitTime / 1000) + " seconds instead of the default " + (EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT / 1000) + " seconds");
		}
		
		success = click(fieldName);
		
		//Check to see if the submit failed because of a page-level data error (but allow for the
		//  possibility that the pageMessageLocator is not defined)
		if (!errorCheckPageOnly(true)) {
			String message = "A Page-level error was found when the '" + getPageName() + "' Page was submitted (by clicking the Field '" + fieldName + "'):  '" + getErrorMessage(getPageName() + "'");
			
			if (EISTestBase.isFailOnSubmitError()) {
				EISTestBase.fail(message);
			} else {
				Util.printError(message);
			}
		}
		
		return success;
	}
*/
/*	@Override
	public boolean clickToSubmit(String fieldName, String fieldToWaitForName, int waitTime) {
		boolean success;

		if (waitTime != EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT) {
			Util.printInfo("When submitting the '" + getPageName() + "' Page (by clicking the Field '" + fieldName + "'), the timeout value used was " + (waitTime / 1000) + " seconds instead of the default " + (EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT / 1000) + " seconds");
		}
		
		success = click(fieldName);
		
		if (!fieldToWaitForName.isEmpty()) {
			//Give the thing we want to wait for a chance to go away first
			waitForElementAbsent(fieldToWaitForName, 1500);
			
			waitForElementVisible(fieldToWaitForName, waitTime);
		}
		
		//Sleep a little bit, just in case...
		Util.sleep(250);
		
		//Check to see if the submit failed because of a page-level data error (but allow for the
		//  possibility that the pageMessageLocator is not defined)
		if (!errorCheckPageOnly(true)) {
			String message = "A Page-level error was found when the '" + getPageName() + "' Page was submitted (by clicking the Field '" + fieldName + "'):  '" + getErrorMessage(getPageName() + "'");
			
			if (EISTestBase.isFailOnSubmitError()) {
				EISTestBase.fail(message);
			} else {
				Util.printError(message);
			}
		}
		
		return success;
	}
*/
/*	@Override
	public boolean clickToSubmit(String fieldName, String fieldToWaitForName, int waitTime) {
		boolean success;

		success = click(fieldName);
		
		//If fieldToWaitForName is an empty string, the caller does not want to wait on a Field to be present.  In such
		//  cases, he calls this method in order to get the call to errorCheckPageOnly, and will handle waiting for the
		//  page on his own
		if (!fieldToWaitForName.isEmpty()) {
			//See if the Field referenced by fieldToWaitForName for exists on this page.  If not, grab the
			//  EISConstants.DEFAULT_FIELD_TO_WAIT_FOR Field from EISTestBase.commonPage
			try {
				getFieldMetadata(fieldToWaitForName);
			} catch (MetadataException me1) {
				Util.printDebug("Did not find the Field '" + fieldToWaitForName + "' to wait for on the Page '" + name + "'; will use the 'commonPage." + EISConstants.DEFAULT_FIELD_TO_WAIT_FOR + "' Field instead");
	
				fieldToWaitForName = EISConstants.DEFAULT_FIELD_TO_WAIT_FOR;
				try {
					fieldMetadata.add(new Field(((Page) EISTestBase.commonPage).getFieldMetadata(fieldToWaitForName)));
				} catch (MetadataException me2) {}
			}
	
			if (waitTime != EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT) {
				Util.printInfo("When submitting the '" + name + "' Page (by clicking the Field '" + fieldName + "'), the timeout value used was " + waitTime + " milliseconds instead of the default " + EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT + " milliseconds");
			}
			
			//Give the thing we want to wait for a chance to go away first
			waitForElementAbsent(fieldToWaitForName, 1500);
			
			//Sleep a bit, just in case...
			Util.sleep(1000);
			
			waitForElementVisible(fieldToWaitForName, waitTime);
		}
		
		//Sleep a little bit, just in case...
		Util.sleep(750);
		
		//Check to see if the submit failed because of a page-level data error (but allow for the
		//  possibility that the pageMessageLocator is not defined)
		if (!errorCheckPageOnly(true)) {
			String message = "A Page-level error was found when the '" + name + "' Page was submitted (by clicking the Field '" + fieldName + "'):  '" + getErrorMessage(name) + "'";
			
			if (EISTestBase.isFailOnSubmitError()) {
				EISTestBase.fail(message);
			} else {
				Util.printError(message);
			}
		}
		
		return success;
	}
*/
	@Override
	public void clickToSubmit(String fieldName, String fieldToWaitForName, int waitTime) {
		//If fieldToWaitForName is an empty string, the caller does not want to wait on a Field to be present.  In such
		//  cases, he calls this method in order to get the call to errorCheckPageOnly, and will handle waiting for the
		//  page on his own

		click(fieldName);
		Util.sleep(10000);
//		waitAfterClickToSubmit(fieldName, fieldToWaitForName, waitTime);
	}
		
/*	@Override
	public boolean clickToSubmit(String fieldName) {
		return clickToSubmit(fieldName, EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT);
	}
*/
	@Override
	public void clickToSubmit(String fieldName, String fieldToWaitForName) {
		clickToSubmit(fieldName, fieldToWaitForName, EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT);
	}

/*	@Override
	public boolean clickToSubmit(String fieldName, String waitTime) {
		return clickToSubmit(fieldName, Integer.parseInt(waitTime));
	}
*/
	@Override
	public void clickToSubmit(String fieldName, int waitTime) {
		//Note that the field name (second parameter) being passed from here to clickToSubmit(String fieldName, String fieldToWaitForName, int waitTime)
		//  lives on commonPage.  Do not call this method if the Page that appears after clicking to submit is not commonPage!
		clickToSubmit(fieldName, EISConstants.DEFAULT_FIELD_TO_WAIT_FOR, waitTime);
	}

	@Override
	public void clickToSubmit(String fieldName) {
		//Note that the field name (second parameter) being passed from here to clickToSubmit(String fieldName, String fieldToWaitForName, int waitTime)
		//  lives on commonPage.  Do not call this method if the Page that appears after clicking to submit is not commonPage!
		clickToSubmit(fieldName, EISConstants.DEFAULT_FIELD_TO_WAIT_FOR, EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT);
	}
	
	private	void waitAfterClickToSubmit(String fieldName, String fieldToWaitForName, int waitTime) {
		//NOTE that it is not recommended to call this method if you need to wait for a field to go away before checking
		//  for it to reappear (even though there is code here to support that).  It is better to call click(String fieldName)
		//  and then waitForFieldToBeAbsent followed by waitForFieldToBePresent.  Better yet, try to find a field that DOES
		//  NOT EXIST before the click, and is guaranteed TO EXIST after the click!

		//If fieldToWaitForName is an empty string, the caller does not want to wait on a Field to be present.  In such
		//  cases, he calls this method in order to get the call to errorCheckPageOnly, and will handle waiting for the
		//  page on his own
		if (!fieldToWaitForName.isEmpty()) {
			//See if the Field referenced by fieldToWaitForName for exists on this page.  If not, grab the
			//  EISConstants.DEFAULT_FIELD_TO_WAIT_FOR Field from EISTestBase.commonPage
			try {
				getFieldMetadata(fieldToWaitForName);
			} catch (MetadataException me1) {
				Util.printDebug("Did not find the Field '" + fieldToWaitForName + "' to wait for on the Page '" + name + "'; will use the 'commonPage." + EISConstants.DEFAULT_FIELD_TO_WAIT_FOR + "' Field instead");
	
				fieldToWaitForName = EISConstants.DEFAULT_FIELD_TO_WAIT_FOR;
				try {
					fieldMetadata.add(new Field(((Page) EISTestBase.commonPage).getFieldMetadata(fieldToWaitForName)));
				} catch (MetadataException me2) {}
			}
	
			if (waitTime != EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT) {
				Util.printInfo("When submitting the '" + name + "' Page (by clicking the Field '" + fieldName + "'), the timeout value used was " + waitTime + " milliseconds instead of the default " + EISConstants.OBJECT_CREATE_WINDOW_WAIT_TIMEOUT + " milliseconds");
			}
			
			//Give the thing we want to wait for a chance to go away first
			waitForFieldAbsent(fieldToWaitForName, 1500);
			
			//Sleep a bit, just in case...
			Util.sleep(1000);
			
			waitForFieldVisible(fieldToWaitForName, waitTime);
		} else {
			//We still need to sleep a bit, in case the next line of code after we return from here is going
			//  to check for the "re-appearance" of a field on this page.  Normally, they would just provide
			//  the name of that field in the fieldToWaitForName argument, but it could be that what needs
			//  to be checked for is enabled-ness, not just presence.
			Util.sleep(2500);
		}
		
		//Sleep a little bit, just in case...
		Util.sleep(750);
		
		//Check to see if the submit failed because of a page-level data error (but allow for the
		//  possibility that the pageMessageLocator is not defined)
		if (!errorCheckPageOnly(true)) {
			String message = "A Page-level error was found when the '" + name + "' Page was submitted (by clicking the Field '" + fieldName + "'):  '" + getErrorMessage(name) + "'";
			
			if (EISTestBase.isFailOnSubmitError()) {
				EISTestBase.fail(message);
			} else {
				Util.printError(message);
			}
		}
	}
	
	@Override
	public boolean check(String fieldName) {
		//This (and its 'AndWait' derivatives) is intended for use on CHECKBOX fields.
		//  It is a convenience method for the test writer; its name is more intuitive than
		//  populateField().  It calls the version of populateField that defines a value parameter.
		//Instance is irrelevant - the caller is specifying a value, so we don't need to look up the
		//  proper instance in the test data; in effect the caller is creating an instance.
		return populateField(fieldName, "true");
	}
		
	@Override
	public boolean uncheck(String fieldName) {
		//This (and its 'AndWait' derivatives) is intended for use on CHECKBOX fields.
		//  It is a convenience method for the test writer; its name is more intuitive than
		//  populateField().  It calls the version of populateField that defines a value parameter.
		//Instance is irrelevant - the caller is specifying a value, so we don't need to look up the
		//  proper instance in the test data; in effect the caller is creating an instance.
		return populateField(fieldName, "false");
	}
	
	private String doGetValueFromGUI(String fieldName) throws GUIException {
		String value = "";
		String guiErrorMessage;
		Field field;

		guiErrorMessage = "Error while getting the value of the GUI element '" + fieldName + "' on the Page '" + name + "': ";

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException(guiErrorMessage + me.getMessage());
		}
		
		if (!field.isLocatorParameterized()) {
			EISConstants.FieldType fieldType = field.getType();
			try {
				switch (fieldType) {
					//90% of the time the field type will be READ_ONLY, because the user wants to grab a value for use
					//  in verification.  Typically that value will be found on a "view" page, not an input form
					case READ_ONLY:			{
						value = gui.getTextFromReadOnlyField(field);
						break;
					}
					case READ_ONLY_DATE:	{
						value = gui.getTextFromReadOnlyDateField(field);
						break;
					}
					case TEXT:				{
						value = gui.getValueFromTextField(field);
						break;
					}
					case DATE:				{
						value = gui.getValueFromDateField(field);
						break;
					}
					case PICKLIST:			{
						value = gui.getValueFromPicklist(field);
						break;
					}
					case MULTISELECT:		{
						value = gui.getValueFromMultiSelect(field);
						break;	//returns selections as a delimited string; for a List<String>, call Page.getValuesFromMultiSelect
					}
					case CHECKBOX:			{
						value = gui.getValueFromCheckbox(field);
						break;		//string value of either "true" or "false"
					}
					case RADIOBUTTON:		{
						value = gui.getValueFromRadioButton(field);
						break;	//string value of either "true" or "false"
					}
					case BUTTON:			{
						value = gui.getValueFromButton(field);
						break;
					}
					case LINK:				{
						value = gui.getTextFromLink(field); break;
					}
					case LOOKUP:			{
						throw new MetadataException(guiErrorMessage + "Fields of the type EISConstants.FieldType.LOOKUP cannot be read from the GUI; try the associated text field instead");
					}
					default:	{
						throw new TestDataException(guiErrorMessage + "Unhandled member of common.EISConstants.FieldType enumerated type: " + fieldType);
					}
				}
			} catch (MetadataException me) {
				throw new GUIException(guiErrorMessage + me.getMessage());
			}
		} else {
			switch (field.getParameterizedLocatorType()) {
				case INFO_PANEL_LOOKUP: 	{
					try {
						value = doGetInfoPanelCell(field.getLocatorParmsSets());
					} catch (MetadataException me) {
						throw new MetadataException(guiErrorMessage + me.getMessage());
					}
	
					break;
				}
				default:	{
					throw new MetadataException(guiErrorMessage + "This GUI element must be accessed via a method that reads from a table in the GUI, i.e., getTableCell or getRelatedListCell");
				}
			}
		}

		return value;
	}

	@Override
	public String getValueFromGUI(String fieldName) {
		//Note that in Selenium "value" refers to the value attribute of input fields.  So calling this
		//  method on, say, a link field, does not make sense.  However, in the interest of consistency,
		//  we will allow it; doGetValueFromGUI will determine which actual Selenium call to make.  (We
		//  do provide getTextFromLink (and getLinkText() for use when the caller knows what field type
		//  she is accessing, and knows Selenium's rules.  We also provide getValuesFromMultiSelect,
		//  because the user needs some way of retrieving a list of strings, as opposed to the single
		//  string returned by this method)
		String value = "";

		try {
			value = doGetValueFromGUI(fieldName);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return value;
	}

	@Override
	public boolean isChecked(String fieldName) {
		//Convenience method for getting the value of a check box or radio button
		//  (for controls of those types, getValueFromGUI returns a string value
		//  of either "true" or "false", which we convert to a Boolean)
		return Boolean.parseBoolean(getValueFromGUI(fieldName));
	}

	@Override
	public String getTextFromLink(String fieldName) {
		return getValueFromGUI(fieldName);
	}

	@Override
	public String getLinkText(String fieldName) {
		//Alias for getTextFromLink()
		return getTextFromLink(fieldName);
	}

	@Override
	public String getLinkTarget(String fieldName) {
		return getAttribute(fieldName, "href");
	}
	
/*	@Override
	public String[] getValuesFromMultiSelect(String fieldName) {
		return getValueFromGUI(fieldName).split(EISConstants.VALUE_DELIM);
	}
*/
	@Override
	public List<String> getValuesFromMultiSelect(String fieldName) {
		return Arrays.asList(getValueFromGUI(fieldName).split(EISConstants.VALUE_DELIM));
	}


	@Override
/*	public String[] getPickListContents(String fieldName) {*/
	public List<String> getPickListContents(String fieldName) {
		List<String> contents = null;
		
		try {
			contents =  getListContents(fieldName);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}
		
		return contents;
	}
	

	@Override
/*	public String[] getMultiSelectContents(String fieldName) {*/
	public List<String> getMultiSelectContents(String fieldName) {
		return getPickListContents(fieldName);
	}
	
/*	private String[] getListContents(String fieldName) throws GUIException {*/
	private List<String> getListContents(String fieldName) throws GUIException {
		List<String> contents;
		String guiErrorMessage;
		Field field;

		guiErrorMessage = "Error while getting the contents of the GUI element '" + fieldName + "' on the Page '" + name + "': ";

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			throw new MetadataException(guiErrorMessage + me.getMessage());
		}

		try {
			contents = gui.getListContents(field);
		} catch (MetadataException me) {
			throw new GUIException(guiErrorMessage + me.getMessage());
		}

		return contents;
	}
	
	@Override
	public int getNumPickListOptions(String fieldName) {
		List<String> contents = null;
		
		try {
			contents =  getListContents(fieldName);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}
		
		return contents.size();
	}
		
	@Override
	public int getNumMultiSelectOptions(String fieldName) {
		return getNumPickListOptions(fieldName);
	}
		
	@Override
	public boolean isValueInPickList(String fieldName, String value) {
		boolean found = false;
		
		try {
			found = isValueInList(fieldName, value);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return found;
	}

	@Override
	public boolean isValueInMultiSelect(String fieldName, String value) {
		boolean found = false;
		
		try {
			found = isValueInList(fieldName, value);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return found;
	}

	private boolean isValueInList(String fieldName, String value) throws GUIException {
		List<String> contents = null;
		int index = -1;
		boolean found;
		String guiErrorMessage;

		guiErrorMessage = "Error while searching the list of values of the GUI element '" + fieldName + "' on the Page '" + name + "': ";

		contents =  getListContents(fieldName);

		try {
			index = gui.findValueInListContents(contents, value);
		} catch (TestDataException te) {
			throw new GUIException(guiErrorMessage + te.getMessage());
		}

		found = (index >= 0) ? true : false;

		return found;
	}

	@Override
	public boolean areValuesInPickList(String fieldName, String... values) {
		boolean found = false;
		
		try {
			found = areValuesInList(fieldName, values);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return found;
	}

	@Override
	public boolean areValuesInMultiSelect(String fieldName, String... values) {
		boolean found = false;
		
		try {
			found = areValuesInList(fieldName, values);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return found;
	}

	private boolean areValuesInList(String fieldName, String... values) throws GUIException {
		List<String> contents = null;
		int index = -1;
		boolean found = true;
		String guiErrorMessage;

		guiErrorMessage = "Error while searching the list of values of the GUI element '" + fieldName + "' on the Page '" + name + "': ";

		contents =  getListContents(fieldName);
		
		for (String value : values) {
			try {
				index = gui.findValueInListContents(contents, value);
			} catch (TestDataException te) {
				throw new GUIException(guiErrorMessage + te.getMessage());
			}
	
			found = (index >= 0) ? true : false;
			
			if (!found) {
				break;
			}
		}

		return found;
	}

	@Override
	public String clickAndWaitForPopUpToOpen(String fieldName, Window_ windowToWaitFor) {
		//See comments at clickAndWaitForPopUpToOpen(String fieldName, int timeout)
		//  for caution on calling this method for Field objects of LOOKUP type!
		return clickAndWaitForPopUpToOpen(fieldName, windowToWaitFor.getLoadTimeout());
	}

	@Override
	public String clickAndWaitForPopUpToOpen(String fieldName, int timeout) {
		//DO NOT call this method for Field objects of LOOKUP type!  The click() call will
		//  eventually call doPopulate, which will call doPopulateLookupField, which will
		//  call gui.clickButtonOrLink and then waitForPopUpToOpen.  That's fine, but when
		//  control returns here we call waitForPopUpToOpen again!
		Set<String> existingWindows = getWindowHandles();

		click(fieldName);

		return waitForPopUpToOpen(existingWindows, timeout);
	}

	@Override
	public String clickAndWaitForPopUpToOpen(String fieldName, String timeout) {
		//See comments at clickAndWaitForPopUpToOpen(String fieldName, int timeout)
		//  for caution on calling this method for Field objects of LOOKUP type!
		return clickAndWaitForPopUpToOpen(fieldName, Integer.parseInt(timeout));
	}

	@Override
	public String clickAndWaitForPopUpToOpen(String fieldName) {
		//See comments at clickAndWaitForPopUpToOpen(String fieldName, int timeout)
		//  for caution on calling this method for Field objects of LOOKUP type!
		return clickAndWaitForPopUpToOpen(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}
	
	@Override
	public String waitForPopUpToOpen(Window_ windowToWaitFor, Set<String> existingWindows) {
		return waitForPopUpToOpen(existingWindows, windowToWaitFor.getLoadTimeout());
	}

/*	@Override
	public String waitForPopUpToOpen(GWindow windowToWaitFor, Set<String> preexistingWindows, int timeout) {
		String popUpLocator = "";
		String windowName = windowToWaitFor.getWindowName();
		
		//DEBUG  It may be that this block of code should be executed for ALL first-level pop-ups (not just those that
		//  are slow to open and settle) and the 'else' block should be executed for all child pop-ups of pop-ups
		//BUT if we do that, be sure to restrict the waitForElement call to only the slow ones
		if (windowName.equalsIgnoreCase("[some slow window]")) {

			//Some ideas (from initial round of testing on 09/30) regarding what to do after closing pop-ups:
			//  Call selectWindow (on the parent) followed by clearing the error flag
			//    Tried reading flag first, and then selectWindow:  throws exception on the getEval call, which jibes
			//      with what I saw when I did the same thing manually.  (Tried it in a catch block, but there is
			//      no way to clear the flag, because even the call to clear it throws an exception.)
			//    So, tried calling selectWindow first, and then clearing the flag. Found that after making the
			//      selectWindow call, was able to get and set the pageLoadError flag without triggering an exception.
			//    And it works!  I can get through the rest of the test, including logging out from the main
			//      window, without incident!!!  BUT, this solution does NOT work with pop-up children of other
			//      pop-ups
			//
			//The solution is implemented in Page.clearPageLoadErrorFlag()

			//The code here is pretty much what we used in the "ORIGINAL" code (above).
			Util.printDebug("Opening and waiting for the pop-up '" + windowName + "' using openAndSelectWindow and waitForElement instead of waitForPopUp");

			//  A click has been made by the caller.  The effect of seleniumTest.openAndSelectWindow was to
			//    wait for THIS Window object to open.  We will want to write a method (in GUI?) that takes the
			//    pre-existing window handles that were passed in and waits until another handle appears.
			//    That method will return "locator"
			//seleniumTest.openAndSelectWindow("", locator);
			gui.selectWindow(locator);

			try {
				//  BE SURE that the exceptions we are catching are appropriate to whatever replaces the kludge below 
				//  We will create our own waitForElement class, which will use findElement to wait.
				//  see http://groups.google.com/group/webdriver/browse_thread/thread/6e705242cc6d75ed
				
				//  The window has been detected to be open, and focus has been switched to it (see a few lines above)
				//  Now we need to wait for a element to appear (to ensure that it has settled.
				//  **** BETTER, call Page.waitForElementPresent
				seleniumTest.waitForElement("Window did not load within " + (timeout / 1000) + " seconds", EISConstants.[an xpath to wait for], true, timeout);
				Util.sleep(timeout); //A kludge, just to get it to compile
			} catch (SeleniumException se) {
				EISTestBase.fail("A SeleniumException occurred while waiting for the pop-up '" + windowName + "': " + se.getMessage());
			} catch (Exception e) {
				EISTestBase.fail("An exception occurred while waiting for the pop-up '" + windowName + "': " + e.getMessage());
			}

			try {
				//Experience has shown that if interaction with the GUI occurs too soon after a "slow to settle" window is opened, we may get errors like:
				//  "There is currently a technical problem with the configuration rules and the quote cannot be validated..."
				//  "This Quote cannot be converted, published, or submitted to Sales Ops until the Account has been linked with the Organization."
				//So let's wait a bit (NOTE that we may want to put this logic somewhere else...)
				Util.sleep(pageRedrawDelay);
			} catch (RuntimeException re) {
				EISTestBase.fail("A RuntimeException (" + re.getMessage() + ") occurred while pausing after the pop-up '" + windowName + "' loaded");
			}
		} else {
			try {
				//DEBUG *** We have determined that this works, at least as far as opening the contact pop-up is concerned.

				//seleniumTest.waitForPopUp(locator, Integer.toString(timeout));
				//seleniumTest.openAndSelectWindow("", locator);
				//  A click has been made by the caller.  The effect of seleniumTest.openAndSelectWindow was to
				//    wait for THIS Window object to open.  We will want to write a method (in GUI?) that takes the
				//    pre-existing window handles that were passed in and waits until another handle appears.
				//    That method will return "locator"
				gui.selectWindow(locator);
				
				//Note that I tried to create and use a WebDriverBackedSelenium object, but it does NOT provide
				//  waitForPopUp() and openAndSelectWindow(0 methods
				//selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
				//selenium.waitForPopUp(locator, Integer.toString(timeout));
				//selenium.openAndSelectWindow("", locator);
		    	//driver = ((WebDriverBackedSelenium) selenium).getWrappedDriver();
			} catch (SeleniumException se) {
				EISTestBase.fail("A SeleniumException occurred while waiting for the pop-up '" + windowName + "': " + "pop-up did not appear within " + timeout / 1000 + " seconds");
			}
		}
		
		return popUpLocator;
	}
*/
	@Override
	public String waitForPopUpToOpen(Set<String> existingWindows, int timeout) {
		//NOTE that in the original (Selenium 1.0) version, we also waited for the presence of an 
		//  element once we determined that a window had opened.  We did that only for some pop-ups,
		//  but it is something to keep in mind in case we run into problems.  (In that case, we
		//  should consider calling waitForElementPresent instead of waiting for the element here.)
		final int interval = 2000;

		String windowLocator = "";
		boolean opened = false;
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeout;

		while (System.currentTimeMillis() < endTime) {
			windowLocator = gui.getOpenedWindowLocator(existingWindows);
			if (!windowLocator.isEmpty()) {
				opened = true;
				break;
			}

			Util.sleep(interval);
		}

		if (opened) {
			//Note that sometimes this message will indicate that a pop-up opened in 0 seconds, when in fact it took longer.
			//  That's because in some cases after we initiate the open we don't regain control until after the pop-up has
			//  opened, i.e., the pop-up is already opened by the time we get into the while loop above.
			Util.printDebug("Pop-up with a locator of '" + windowLocator + "' opened after " + (System.currentTimeMillis() - startTime) + " milliseconds");

			//DO NOT select it - leave that up to the caller
			//selectWindow(windowLocator);
			
			//Sleep a bit more, "just in case"...
			Util.sleep(1000);
		}

		return windowLocator;
	}
	
	@Override
	public String waitForPopUpToOpen(Set<String> existingWindows, String timeout) {
		return waitForPopUpToOpen(existingWindows, Integer.parseInt(timeout));
	}

	@Override
	public String waitForPopUpToOpen(Set<String> existingWindows) {
		return waitForPopUpToOpen(existingWindows, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}
	
	@Override
	public String clickAndWaitForPopUpToClose(String fieldName) {
		return clickAndWaitForPopUpToClose(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public String clickAndWaitForPopUpToClose(String fieldName, int timeout) {
		Set<String> existingWindows = getWindowHandles();

		click(fieldName);

		return waitForPopUpToClose(existingWindows, timeout);
	}

	@Override
	public String clickAndWaitForPopUpToClose(String fieldName, String timeout) {
		return clickAndWaitForPopUpToClose(fieldName, Integer.parseInt(timeout));
	}

	@Override
	public String waitForPopUpToClose() {
		return waitForPopUpToClose(EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public String waitForPopUpToClose(int timeout) {
		Set<String> existingWindows = getWindowHandles();

		return waitForPopUpToClose(existingWindows, timeout);
	}

	@Override
	public String waitForPopUpToClose(String timeout) {
		return waitForPopUpToClose(Integer.parseInt(timeout));
	}

/*	@Override
	public String waitForPopUpToClose(Set<String> preexistingWindows, int timeout) {
		//TODO  Note that once we have implemented a better relationship between Page and Window objects
		//  (whereby a Page will be able to directly access its containing Window object), we will change
		//  the logic below to simply wait for the Window object's locator to not be present

		final int intervalInSeconds = 2;

		Set<String> existingWindows;
		Set<String> difference;
		String windowLocator = "";
		boolean closed = false;
		int seconds = 0;

		long endTime = System.currentTimeMillis() + timeout;

		while (System.currentTimeMillis() < endTime) {
			//See comment in getAllWindowNames() re the possibility of adding a call to clearPageLoadErrorFlag() there
			clearPageLoadErrorFlag();

			existingWindows = ImmutableSet.of(getAllWindowNames());
			difference = Sets.difference(preexistingWindows, existingWindows);

			if (difference.size() > 0) {
				windowLocator = difference.iterator().next();

				closed = true;
				break;
			}

			Util.sleep(intervalInSeconds * 1000);
			seconds += intervalInSeconds;
		}

		//A final call to clearPageLoadErrorFlag here, in case while we were in the loop the pop-up closed after we
		//  called it, but before we called getAllWindowNames()
		clearPageLoadErrorFlag();

		if (!closed) {
			EISTestBase.fail("Pop-up did not close within about " + seconds + " seconds");
		}

		//Note that sometimes this message will indicate that a pop-up closed in 0 seconds, when in fact it took longer.
		//  That's because in some cases after we initiate the close we don't regain control until after the pop-up has
		//  closed, i.e., the pop-up is already closed by the time we get into the while loop above.
		Util.printDebug("Pop-up with a locator of '" + windowLocator + "' closed after about " + seconds + " seconds");

		//Sleep a bit more, "just in case"...
		Util.sleep(2000);

		return windowLocator;
	}
*/
	@Override
	public String waitForPopUpToClose(Set<String> existingWindows, int timeout) {
		final int interval = 2000;

		String locator = "";
		boolean closed = false;
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeout;

		while (System.currentTimeMillis() < endTime) {
			locator = gui.getClosedWindowLocator(existingWindows);
			if (!locator.isEmpty()) {
				closed = true;
				break;
			}

			Util.sleep(interval);
		}

		if (closed) {
			//Note that sometimes this message will indicate that a pop-up closed in 0 seconds, when in fact it took longer.
			//  That's because in some cases after we initiate the close we don't regain control until after the pop-up has
			//  closed, i.e., the pop-up is already closed by the time we get into the while loop above.
			Util.printDebug("Pop-up with a locator of '" + locator + "' closed after " + (System.currentTimeMillis() - startTime) + " milliseconds");

			//Sleep a bit more, "just in case"...
			//Util.sleep(1000);
		}

		return locator;
	}

	@Override
	public boolean closeAllPopUps(String windowToLeaveOpenLocator) {
		return gui.closeAllWindowsBut(windowToLeaveOpenLocator);
	}
	
	@Override
	public boolean waitForPageToSettle(String referenceElement, boolean waitForPresent, int timeout) {
		//boolean success = true;

		//if (gui.findSeleniumLocator(referenceElement)) {
		//	success = gui.waitForElementAbsent(referenceElement, timeout);
		//}
		
		return gui.waitForElement(referenceElement, waitForPresent, timeout);
	}
	
	@Override
	public boolean waitForPageToSettle(String referenceElement, boolean waitForPresent) {
		return waitForPageToSettle(referenceElement, waitForPresent, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}
	
	@Override
	public boolean waitForPageToSettle() {
		return waitForFieldVisible(EISConstants.DEFAULT_FIELD_TO_WAIT_FOR);
	}
	
	
	/* TODO  Perhaps add similar wrappers for:
			waitForElementVisible
			waitForElementDisplayed
			waitForText
			waitForTextPresent
			waitForTextAbsent
			(others?)
	 */

	
	@Override
	public boolean waitForFieldPresent(String fieldName, int timeout) {
		return waitForField(fieldName, true, timeout);
	}

	@Override
	public boolean waitForFieldPresent(String fieldName) {
		return waitForFieldPresent(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean waitForFieldPresent(String fieldName, String timeout) {
		return waitForFieldPresent(fieldName, Integer.parseInt(timeout));
	}

	@Override
	public boolean waitForFieldAbsent(String fieldName, int timeout) {
		return waitForField(fieldName, false, timeout);
	}

	@Override
	public boolean waitForFieldAbsent(String fieldName) {
		return waitForFieldAbsent(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean waitForFieldAbsent(String fieldName, String timeout) {
		return waitForFieldAbsent(fieldName, Integer.parseInt(timeout));
	}

	@Override
	public boolean waitForField(String fieldName, boolean waitForPresent, int timeout) {
		String waitType = waitForPresent ? "present" : "absent";
		Field field = null;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while waiting for the GUI element '" + fieldName + "' on the page '" + name + "' to be " + waitType + ": " + me.getMessage());
		}

		return gui.waitForField(field, waitForPresent, timeout);
	}

	@Override
	public boolean waitForField(String fieldName, boolean waitForPresent) {
		return waitForField(fieldName, waitForPresent, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}
	
	@Override
	public boolean waitForField(String fieldName, boolean waitForPresent, String timeout) {
		return waitForField(fieldName, waitForPresent, Integer.parseInt(timeout));
	}

	@Override
	public boolean waitForFieldPresentWhileRefreshing(String fieldName, int timeout) {
		return waitForFieldWhileRefreshing(fieldName, true, timeout);
	}

	@Override
	public boolean waitForFieldPresentWhileRefreshing(String fieldName) {
		return waitForFieldPresentWhileRefreshing(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean waitForFieldPresentWhileRefreshing(String fieldName, String timeout) {
		return waitForFieldPresentWhileRefreshing(fieldName, Integer.parseInt(timeout));
	}

	@Override
	public boolean waitForFieldAbsentWhileRefreshing(String fieldName, int timeout) {
		return waitForFieldWhileRefreshing(fieldName, false, timeout);
	}

	@Override
	public boolean waitForFieldAbsentWhileRefreshing(String fieldName) {
		return waitForFieldAbsentWhileRefreshing(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean waitForFieldAbsentWhileRefreshing(String fieldName, String timeout) {
		return waitForFieldAbsentWhileRefreshing(fieldName, Integer.parseInt(timeout));
	}

	@Override
	public boolean waitForFieldWhileRefreshing(String fieldName, boolean waitForPresent, int timeout) {
		String waitType = waitForPresent ? "present" : "absent";
		Field field = null;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while waiting for the GUI element '" + fieldName + "' on the page '" + name + "' to be " + waitType + ": " + me.getMessage());
		}

		return gui.waitForFieldWhileRefreshing(field, waitForPresent, timeout);
	}

	@Override
	public boolean waitForFieldWhileRefreshing(String fieldName, boolean waitForPresent) {
		return waitForFieldWhileRefreshing(fieldName, waitForPresent, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean waitForFieldWhileRefreshing(String fieldName, boolean waitForPresent, String timeout) {
		return waitForFieldWhileRefreshing(fieldName, waitForPresent, Integer.parseInt(timeout));
	}

	@Override
	public boolean waitForFieldVisible(String fieldName, int timeout) {
		Field field = null;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while waiting for the GUI element '" + fieldName + "' on the page '" + name + "' to be present and visible: " + me.getMessage());
		}

		return gui.waitForFieldVisible(field, timeout);
	}

	@Override
	public boolean waitForFieldVisible(String fieldName) {
		return waitForFieldVisible(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean waitForFieldVisible(String fieldName, String timeout) {
		return waitForFieldVisible(fieldName, Integer.parseInt(timeout));
	}

	@Override
	public boolean waitForFieldNotVisible(String fieldName, int timeout) {
		Field field = null;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while waiting for the GUI element '" + fieldName + "' on the page '" + name + "' to be present but not visible: " + me.getMessage());
		}

		return gui.waitForFieldNotVisible(field, timeout);
	}

	@Override
	public boolean waitForFieldNotVisible(String fieldName) {
		return waitForFieldNotVisible(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean waitForFieldNotVisible(String fieldName, String timeout) {
		return waitForFieldNotVisible(fieldName, Integer.parseInt(timeout));
	}
	
	@Override
	public boolean waitForFieldEnabled(String fieldName, int timeout) {
		Field field = null;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while waiting for the GUI element '" + fieldName + "' on the page '" + name + "' to be present and visible: " + me.getMessage());
		}

		return gui.waitForFieldEnabled(field, timeout);
	}

	@Override
	public boolean waitForFieldEnabled(String fieldName) {
		return waitForFieldEnabled(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
	}

	@Override
	public boolean waitForFieldEnabled(String fieldName, String timeout) {
		return waitForFieldEnabled(fieldName, Integer.parseInt(timeout));
	}
	
	private boolean doErrorCheckFields(List<Field> fields) {
		boolean noErrors = true;
		String text;

		for (Field field : fields) {
			try {
				//text = gui.getText(field.getMessageLocators());
				text = Util.stripLineSeparators(gui.getText(field.getMessageLocators()));
				noErrors = false;

				errors.add(field.getName() + EISConstants.PROPERTY_TYPE_DELIM + text + EISConstants.PROPERTY_TYPE_DELIM + gui.getSeleniumLocator());
			} catch (GUIException ge) {}
		}

		return noErrors;
	}

	@Override
	public boolean errorCheck() {
		//Look for errors on all fields and at page level
		boolean noErrors = true;
		int numFields;
		List<Field> fieldsToErrorCheck = new ArrayList<Field>();
		List<String> errorsSaved = new ArrayList<String>();

		errors.clear();

		for (Field field : fieldMetadata) {
			if (!field.getMessageLocators().isEmpty()) {
				fieldsToErrorCheck.add(field);
			}
		}

		if (!fieldsToErrorCheck.isEmpty()) {
			noErrors = doErrorCheckFields(fieldsToErrorCheck);
			errorsSaved = getErrors();
		}

		//Since the user is not explicitly asking to check for page-level errors, we will
		//  not generate an error if messageLocators is empty (we will print a warning instead) 
		if (!errorCheckPageOnly(true)) {
			noErrors = false;
			errors.addAll(errorsSaved);
		} else {
			errors = errorsSaved;
		}

		numFields = fieldsToErrorCheck.size();
		
		if (getNumErrors() > 0) {
			Util.printWarning("Number of errors found (checked " + numFields + " Field" + (numFields != 1 ? "s" : "") + " and the Page-level error message Field on the '" + name + "' Page):  " + getNumErrors());
		}

		return noErrors;
	}

	@Override
	public boolean verifyErrorCheck(boolean areNoErrorsExpected) {
		boolean result;
		boolean noErrors = errorCheck();

		if (areErrors()) {
			Util.printDebug("Errors: \n" + errors.toString());
		}

		result = EISTestBase.assertErrorCheckWithFlags("Fields and the Page-level error message Field on the '" + name + "' Page", noErrors, areNoErrorsExpected);

		return result;
	}

	@Override
	public boolean verifyErrorCheck() {
		return verifyErrorCheck(true);
	}
	
	@Override
	public boolean errorCheckField(String fieldName) {
		//Look for errors on one field and at page level
		boolean noErrors;
		List<String> errorsSaved = new ArrayList<String>();

		noErrors = errorCheckFieldOnly(fieldName, true);
		errorsSaved = getErrors();

		//Since the user is not explicitly asking to check for page-level errors, we will
		//  not generate an error if messageLocators is empty (we will print a warning instead) 
		if (!errorCheckPageOnly(true)) {
			noErrors = false;
			errors.addAll(errorsSaved);
		} else {
			errors = errorsSaved;
		}

		if (getNumErrors() > 0) {
			Util.printWarning("Number of errors found (checked the '" + fieldName + "' Field and the Page-level error message Field on the '" + name + "' Page):  " + getNumErrors());
		}

		return noErrors;
	}

	@Override
	public boolean verifyErrorCheckField(String fieldName, boolean areNoErrorsExpected) {
		boolean result;
		boolean noErrors = errorCheckField(fieldName);

		if (areErrors()) {
			Util.printDebug("Errors: \n" + errors.toString());
		}

		result = EISTestBase.assertErrorCheckWithFlags("'" + fieldName + "' Field and the Page-level error message Field on the '" + name + "' Page", noErrors, areNoErrorsExpected);

		return result;
	}
	
	@Override
	public boolean verifyErrorCheckField(String fieldName) {
		return verifyErrorCheckField(fieldName, true);
	}
	
/*	@Override
	public boolean errorCheckFieldOnly(String fieldName) {
		//Look for errors on one field only (not at page level)
		boolean noErrors = true;
		Field field = null;

		errors.clear();

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for errors in the '" + fieldName + "' Field on the '" + name + "' Page: " + me.getMessage());
		}

		if (!field.getMessageLocators().isEmpty()) {
			List<Field> fieldsToErrorCheck = new ArrayList<Field>();

			fieldsToErrorCheck.add(field);
			if (!doErrorCheckFields(fieldsToErrorCheck)) {
				noErrors = false;
			}
		} else {
			errors.add(field.getName() + EISConstants.PROPERTY_TYPE_DELIM + "The 'messageLocators' property is empty" + EISConstants.PROPERTY_TYPE_DELIM + field.getMessageLocators());

			noErrors = false;
		}

		if (getNumErrors() > 0) {
			Util.printWarning("Number of errors found (checked the '" + fieldName + "' Field on the '" + name + "' Page): " + getNumErrors());
		}

		return noErrors;
	}
*/
	@Override
	public boolean errorCheckFieldOnly(String fieldName, boolean okayIfNoMessageLocator) {
		//Look for errors at field level only (by default, if messageLocators is empty we will fail the test)
		boolean noErrors = true;
		boolean checkedForErrors;
		Field field = null;

		errors.clear();

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for errors in the '" + fieldName + "' Field on the '" + name + "' Page: " + me.getMessage());
		}

		if (!field.getMessageLocators().isEmpty()) {
			checkedForErrors = true;
			
			List<Field> fieldsToErrorCheck = new ArrayList<Field>();

			fieldsToErrorCheck.add(field);
			if (!doErrorCheckFields(fieldsToErrorCheck)) {
				noErrors = false;
			}
		} else {
			checkedForErrors = false;
			
			if (!okayIfNoMessageLocator) {
				EISTestBase.fail("Error while checking for errors in the '" + fieldName + "' Field on the '" + name + "' Page:  The Field's 'messageLocators' property is empty");
			} else {
				Util.printWarning("Field-level error checking was not performed, because the 'messageLocators' property in the '" + fieldName + "' Field on the '" + name + "' Page is empty");
			}
		}

		if (checkedForErrors) {
			if (getNumErrors() > 0) {
				Util.printWarning("Number of errors found (checked the '" + fieldName + "' Field on the '" + name + "' Page): " + getNumErrors());
			}
		}

		return noErrors;
	}

	@Override
	public boolean errorCheckFieldOnly(String fieldName) {
		//Look for errors at field level only (by default, if messageLocators is empty we will fail the test)
		return errorCheckFieldOnly(fieldName, false);
	}
	
	@Override
	public boolean verifyErrorCheckFieldOnly(String fieldName, boolean isNoErrorExpected) {
		boolean result;
		boolean noErrors = errorCheckFieldOnly(fieldName);

		if (areErrors()) {
			Util.printDebug("Errors: \n" + errors.toString());
		}

		result = EISTestBase.assertErrorCheckWithFlags("'" + fieldName + "' Field on the '" + name + "' Page", noErrors, isNoErrorExpected);

		return result;
	}

	@Override
	public boolean verifyErrorCheckFieldOnly(String fieldName) {
		return verifyErrorCheckFieldOnly(fieldName, true);
	}
	
/*	@Override
	public boolean errorCheckPageOnly(boolean okayIfNoMessageLocator) {
		//Look for errors at page level only (by default, if messageLocators is empty,
		//  an error will be generated)
		boolean noErrors = true;
		boolean checkedForErrors;

		errors.clear();

		//Look for error on page (make a bogus Field object)
		if (!messageLocators.isEmpty()) {
			List<Field> fieldsToErrorCheck = new ArrayList<Field>();

			checkedForErrors = true;
			
			try {
				fieldsToErrorCheck.add(new Field(name, EISConstants.FieldType.TEXT, new ArrayList<String>(), EISConstants.RequirednessLevel.OPTIONAL, messageLocators));
			} catch (MetadataException me) {
				EISTestBase.fail(me.getMessage());
			}

			if (!doErrorCheckFields(fieldsToErrorCheck)) {
				noErrors = false;
			}
		} else {
			if (!okayIfNoMessageLocator) {
				checkedForErrors = true;
				
				errors.add(getPageName() + EISConstants.PROPERTY_TYPE_DELIM + "The 'messageLocators' property is empty" + EISConstants.PROPERTY_TYPE_DELIM + getMessageLocators());
	
				noErrors = false;
			} else {
				checkedForErrors = false;
				
				Util.printWarning("Page-level error checking was not performed, because there is no Page-level error message Field defined on the '" + getPageName() + "' Page");
			}
		}

		if (checkedForErrors) {
			if (getNumErrors() > 0) {
				Util.printWarning("Number of errors found (checked the Page-level error message Field on the '" + getPageName() + "' Page):  " + getNumErrors());
			}
		}

		return noErrors;
	}
*/
/*	@Override
	public boolean errorCheckPageOnly(boolean okayIfNoMessageLocator) {
		//Look for errors at page level only (by default, if messageLocators is empty an error will be generated)
		boolean noErrors = true;
		boolean checkedForErrors;

		errors.clear();

		//Look for error on page.  Do a quick check for the existence of the error message element before calling
		//  doErrorCheckFields.  The reason for that is to save time:  doErrorCheckFields calls gui.getText, which
		//  in turn calls gui.setSeleniumLocator, which can take up to EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT (10
		//  seconds) to determine whether the element is present.  That's a waste of a lot of time looking for an
		//  element we expect will not be present!  So we will call gui.isElementPresent first; it does a quick
		//  check for the presence of of an element that matches one of the members of messageLocators.  Only if
		//  the element is present will we call doErrorCheckFields
		//
		//TODO!!!!!  Add the same logic to errorCheck()
		//
		if (!messageLocators.isEmpty()) {
			checkedForErrors = true;
			
			if (gui.isElementPresent(messageLocators)) {
				List<Field> fieldsToErrorCheck = new ArrayList<Field>();

				try {
					//Create a bogus Field object to pass to doErrorCheckFields
					fieldsToErrorCheck.add(new Field(name, EISConstants.FieldType.TEXT, new ArrayList<String>(), EISConstants.RequirednessLevel.OPTIONAL, messageLocators));
				} catch (MetadataException me) {
					EISTestBase.fail(me.getMessage());
				}

				if (!doErrorCheckFields(fieldsToErrorCheck)) {
					noErrors = false;
				}
			}
		} else {
			if (!okayIfNoMessageLocator) {
				checkedForErrors = true;
				
				errors.add(name + EISConstants.PROPERTY_TYPE_DELIM + "The 'messageLocators' property is empty" + EISConstants.PROPERTY_TYPE_DELIM + getMessageLocators());
	
				noErrors = false;
			} else {
				checkedForErrors = false;
				
				Util.printWarning("Page-level error checking was not performed, because there is no Page-level error Field defined on the '" + name + "' Page");
			}
		}

		if (checkedForErrors) {
			if (getNumErrors() > 0) {
				Util.printWarning("Number of errors found (checked the Page-level error message Field on the '" + name + "' Page):  " + getNumErrors());
			}
		}

		return noErrors;
	}
*/
	@Override
	public boolean errorCheckPageOnly(boolean okayIfNoMessageLocator) {
		//Look for errors at page level only (by default, if messageLocators is empty we will fail the test)
		boolean noErrors = true;
		boolean checkedForErrors;

		errors.clear();

		//Look for error on page.  Do a quick check for the existence of the error message element before calling
		//  doErrorCheckFields.  The reason for that is to save time:  doErrorCheckFields calls gui.getText, which
		//  in turn calls gui.setSeleniumLocator, which can take up to EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT (10
		//  seconds) to determine whether the element is present.  That's a waste of a lot of time looking for an
		//  element we expect will not be present!  So we will call gui.isElementPresent first; it does a quick
		//  check for the presence of of an element that matches one of the members of messageLocators.  Only if
		//  the element is present will we call doErrorCheckFields
		//
		//TODO!!!!!  Add the same logic to errorCheck()
		//
		if (!messageLocators.isEmpty()) {
			checkedForErrors = true;
			
			if (gui.isElementPresent(messageLocators)) {
				List<Field> fieldsToErrorCheck = new ArrayList<Field>();

				try {
					//Create a bogus Field object to pass to doErrorCheckFields
					fieldsToErrorCheck.add(new Field(name, EISConstants.FieldType.TEXT, new ArrayList<String>(), EISConstants.RequirednessLevel.OPTIONAL, messageLocators));
				} catch (MetadataException me) {
					EISTestBase.fail(me.getMessage());
				}

				if (!doErrorCheckFields(fieldsToErrorCheck)) {
					noErrors = false;
				}
			}
		} else {
			checkedForErrors = false;
			
			if (!okayIfNoMessageLocator) {
				EISTestBase.fail("Error while checking for Page-level errors on the '" + name + "' Page:  The 'messageLocators' property is empty");
			} else {
				Util.printWarning("Page-level error checking was not performed, because the 'messageLocators' property on the '" + name + "' Page is empty");
			}
		}

		if (checkedForErrors) {
			if (getNumErrors() > 0) {
				Util.printWarning("Number of errors found (checked the Page-level error message Field on the '" + name + "' Page):  " + getNumErrors());
			}
		}

		return noErrors;
	}
	
	@Override
	public boolean errorCheckPageOnly() {
		//Look for errors at page level only (by default, if messageLocators is empty we will fail the test)
		return errorCheckPageOnly(false);
	}

	@Override
	public boolean verifyErrorCheckPageOnly(boolean isNoErrorExpected) {
		boolean result;
		boolean noErrors = errorCheckPageOnly();

		if (areErrors()) {
			Util.printDebug("Errors: \n" + errors.toString());
		}

		result = EISTestBase.assertErrorCheckWithFlags("Page-level error message Field on the '" + name + "' Page", noErrors, isNoErrorExpected);

		return result;
	}

	@Override
	public boolean verifyErrorCheckPageOnly() {
		return verifyErrorCheckPageOnly(true);
	}

	@Override
	public boolean errorCheck(Page_ anotherPage) {
		//Look for errors on all fields and at page level on this page.
		//  If a page-level error is not found on this page, the
		//  passed-in page will be error-checked for a page-level error.
		//  (the purpose of this method is to check for errors on a page
		//  that may be loaded as a consequence of an action on the
		//  current page)
		boolean noErrors;

		noErrors = errorCheck();

		if (getErrorMessage(name).isEmpty()) {
			//Since the user is not explicitly asking to check for page-level errors, we will
			//  not generate an exception if messageLocators is empty (we will print a warning instead) 
			if (!anotherPage.errorCheckPageOnly(true)) {
				noErrors = false;
				errors.addAll(anotherPage.getErrors());
			}
		}

		return noErrors;
	}

	@Override
	public boolean errorCheckField(String fieldName, Page_ anotherPage) {
		//Look for errors on a field and at page level on this page.
		//  If a page-level error is not found on this page, the
		//  passed-in page will be error-checked for a page-level error.
		//  (the purpose of this method is to check for errors on a page
		//  that may be loaded as a consequence of an action on the
		//  current page)
		boolean noErrors;

		noErrors = errorCheckField(fieldName);

		if (getErrorMessage(name).isEmpty()) {
			//Since the user is not explicitly asking to check for page-level errors, we will
			//  not generate an exception if messageLocators is empty (we will print a warning instead) 
			if (!anotherPage.errorCheckPageOnly(true)) {
				noErrors = false;
				errors.addAll(anotherPage.getErrors());
			}
		}

		return noErrors;
	}

	@Override
	public boolean errorCheckPageOnly(Page_ anotherPage) {
		//Look for errors at page level on this page.
		//  If a page-level error is not found on this page, the
		//  passed-in page will be error-checked for a page-level error
		//  (the purpose of this method is to check for errors on a page
		//  that may be loaded as a consequence of an action on the
		//  current page)
		boolean noErrors;

		noErrors = errorCheckPageOnly();

		//If we found an error on this page, don't look for
		//  one on the passed-in page
		if (noErrors) {
			if (!anotherPage.errorCheckPageOnly()) {
				noErrors = false;
				errors.addAll(anotherPage.getErrors());
			}
		}

		return noErrors;
	}

	@Override
	public boolean findErrorMessage(String errorMsg) {
		boolean found = false;

		if (areErrors()) {
			if (Util.listOfStringMatch(getErrorMessages(), ".*" + errorMsg + ".*") > -1) {
				found = true;
			}
		}

		return found;
	}

	@Override
	public boolean verifyErrorMessage(String fieldOrPageName, String errorMsgExpected) {
		boolean success = false;
		String errorMsgActual = getErrorMessage(fieldOrPageName);

		if (!errorMsgActual.isEmpty()) {
			if (errorMsgActual.matches("(?s).*" + errorMsgExpected + ".*")) {
				success = true;
			}
		}

		return success;
	}

	@Override
	public void selectWindow(String windowLocator) {
		gui.selectWindow(windowLocator);
	}

	@Override
	public void closeWindow(String windowLocator) {
		gui.closeWindow(windowLocator);
	}
	
	@Override
	public boolean isTextPresent(String text) {
		return gui.isTextPresent(text);
	}

	@Override
	public boolean isFieldPresent(String fieldName) {
		Field field = null;
		boolean isPresent = false;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for the presence of the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		if (field.isLocatorParameterized()) {
			switch (field.getParameterizedLocatorType()) {
				//doIsTablePresent() looks for the table itself (the column number/name part of the locator will be ignored) 
				case TABLE_CELL_LOOKUP:	{
					isPresent = doIsTablePresent(fieldName);
					break;
				}
				
				//doIsInfoPanelPresent() looks for the info panel itself (the column number/name part of the locator will be ignored) 
				case INFO_PANEL_LOOKUP:	{
					isPresent = doIsInfoPanelPresent(fieldName);
					break;
				}
				
				//doIsRelatedListPresent() looks for the related list itself (the column number/name part of the locator will be ignored) 
				case RELATED_LIST_CELL_LOOKUP:	{
					isPresent = doIsRelatedListPresent(fieldName);
					break;
				}
			}
		} else {
			//fieldName refers to a Field object that does not have a parameterized locator at all (this includes Fields that are
			//  REFERENCED by Fields with a parameterized locator type of TABLE_CELL_LOOKUP)
			isPresent = gui.isElementPresent(field.getLocators());
		}
		
		return isPresent;
	}

	@Override
	public boolean isTablePresent(String fieldName) {
		//It is expected that fieldName refers to a Field object with a parameterized locator type of TABLE_CELL_LOOKUP
		//  or a Field object that does not have a parameterized locator at all (such fields contain locators that
		//  address the table itself, not one of its columns) 
		return isFieldPresent(fieldName);
	}
	
	@Override
	public boolean isInfoPanelPresent(String fieldName) {
		//It is expected that fieldName refers to a Field object with parameterized locator type of INFO_PANEL_LOOKUP
		return isFieldPresent(fieldName);
	}	
	
	@Override
	public boolean isRelatedListPresent(String fieldName) {
		//It is expected that fieldName refers to a Field object with parameterized locator type of RELATED_LIST_CELL_LOOKUP
		//  (this could be the related list itself, or one of its constituent columns)
		return isFieldPresent(fieldName);
	}
	
	private boolean doIsTablePresent(String columnFieldName) {
		//TODO  Decide how to handle waiting for the table to be present.  gui.isElementPresent(locator)
		//  does not wait - and we don't want it to!  SEE doGetInfoPanelCell for the solution.

		//It is expected that fieldName refers to a Field object with parameterized locator type of TABLE_CELL_LOOKUP
		Field field = null;
		String tableFieldName = "";

		try {
			field = getFieldMetadata(columnFieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for the presence of the field '" + columnFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		//Field objects with a parameterized locator type of TABLE_CELL_LOOKUP can have only one locator, and therefore
		//  only one locator parm set.  The first element in that parm set is the name of the Field object that addresses
		//  the table itself
		tableFieldName = field.getLocatorParmsSets().get(0).get(0);

		try {
			field = getFieldMetadata(tableFieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for the presence of the field '" + tableFieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		return gui.isElementPresent(field.getLocators());
	}
	
	private boolean doIsInfoPanelPresent(String fieldName) {
		//TODO  Decide how to handle waiting for the info panel to be present.  gui.isElementPresent(locator)
		//  does not wait - and we don't want it to!  SEE doGetInfoPanelCell for the solution.

		//It is expected that fieldName refers to a Field object with parameterized locator type of INFO_PANEL_LOOKUP
		Field field = null;
		List<String> locatorParms = new ArrayList<String>();
		List<List<String>> locatorParmsSets = new ArrayList<List<String>>();
		String locator;
		boolean isPresent = false;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for the presence of the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		locatorParmsSets = field.getLocatorParmsSets();

		ListIterator<List<String>> locatorParmItr = locatorParmsSets.listIterator();
		while (locatorParmItr.hasNext()) {
			locatorParms = locatorParmItr.next();
			
			locator = EISConstants.INFO_PANEL_HEADER_XPATH.replace(EISConstants.INFO_PANEL_HEADER_TOKEN, locatorParms.get(0).trim());
			if (gui.isElementPresent(locator)) {
				isPresent = true;
				break;
			}
		}
		
		return isPresent;
	}

	private boolean doIsRelatedListPresent(String fieldName) {
		//It is expected that fieldName refers to a Field object with parameterized locator type of RELATED_LIST_CELL_LOOKUP
		//  (this could be the related list itself, or one of its constituent columns)
		Field field = null;
		boolean isPresent = true;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for the presence of the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		try {
			searchRelatedListLocatorParmSets(field.getLocatorParmsSets());
		} catch (MetadataException me) {
			isPresent = false;
		}
		
		return isPresent;
	}
	
	@Override
	public boolean isFieldVisible(String fieldName) {
		Field field = null;
/*
		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for the visibility of the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}
		
		return gui.isVisible(field.getLocators());*/
		 
		try {
			field = getFieldMetadata(fieldName);
			return gui.isVisible(field.getLocators());
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for the visibility of the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}catch (Exception e) {
			Util.printDebug("Something went wrong here. StaleElement Ref exception");
		}
		return false;
	}
	
	public boolean isFieldEnabled(String fieldName) {
		Field field = null;
		 
		try {
			field = getFieldMetadata(fieldName);
			for(String myLocator: field.getLocators()) {
				return gui.isFieldEnable(myLocator);
			}
			
		} catch (MetadataException me) {
			EISTestBase.fail("Error while checking for the state(enabled/disabled) of the field '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}catch (Exception e) {
			Util.printDebug("Something went wrong here. StaleElement Ref exception");
		}
		return false;
	}
		
	@Override
	public void refresh(int waitTime) {
		gui.refresh(waitTime);
	}

	@Override
	public void refresh() {
		refresh(EISConstants.DEFAULT_PAGE_REFRESH_INTERVAL);
	}

/*	public String getEval(String command) {
		String result = "";

		try {
			result = gui.getEval(command);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return result;
	}
*/
	@Override
	public Object getEval(String script) {
		Object result = null;

		try {
			result = gui.getEval(script);
		} catch (GUIException ge) {
			EISTestBase.fail(ge.getMessage());
		}

		return result;
	}

	@Override
	public Object executeJavascript(String script) {
		return getEval(script);
	}

	@Override
	public String getAttribute(String fieldName, String attributeName) {
		Field field = null;
		String attributeValue = "";		
		String guiErrorMessage = "Error while getting the value of the '" + attributeName + "' HTML attribute from the Field '" + fieldName + "' on the Page '" + name + "': ";

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail(guiErrorMessage + me.getMessage());
		}

		try {
			attributeValue = gui.getAttribute(field, attributeName);
		} catch (MetadataException me) {
			EISTestBase.fail(guiErrorMessage + me.getMessage());
		}
		
		return attributeValue;
	}
	/**
	 * @Description gets the Css property value of the HTML element
	 * @param fieldName
	 * @param cssPropertyName
	 * @return
	 */
	public String getCssPropertyValue(String fieldName, String cssPropertyName) throws MetadataException {
		Field field = null;
		String cssAttributeValue = "";		
		String guiErrorMessage = "Error while getting the CSS property value of the '" + cssPropertyName + "' HTML attribute from the Field '" + fieldName + "' on the Page '" + name + "': ";

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail(guiErrorMessage + me.getMessage());
		}

		try {
			cssAttributeValue = gui.getCssPropertyValue(field,cssPropertyName);
		} catch (MetadataException me) {
			EISTestBase.fail(guiErrorMessage + me.getMessage());
		}
		
		return cssAttributeValue;
		
	}
	@Override
	public void open(String url) {
		gui.open(url);
		Util.printInfo("Opened " + url);
	}
		
	@Override
	public String getLocation() {
		return gui.getLocation();
	}
		
	@Override
	public String getWindowHandle() {
		return gui.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return gui.getWindowHandles();
	}

	@Override
	public Set<String> getAllWindowHandles() {
		return getWindowHandles();
	}

	//Since migration to WebDriver, this method is no longer relevant
/*	@Override
	public String[] getAllWindowNames() {
		//TODO???  We may want to consider calling clearPageLoadErrorFlag() here so that we don't
		//  need to worry about calling it in code before calling this method.
		//HOWEVER!!! See the "CAUTION" comment in clearPageLoadErrorFlag()!
		//  AND it appears that calling selectWindow() (which clearPageLoadErrorFlag does)
		//  after a java.net.SocketTimeoutException exception can be very bad news!
		return gui.getAllWindowNames();
	}
*/
	
	//Since migration to WebDriver, this method is no longer relevant
/*	@Override
	public String getGeneratedWindowLocator(Set<String> preexistingWindows) {
		String windowLocator = "";

		//See comment in getAllWindowNames() re the possibility of adding a call to clearPageLoadErrorFlag() there
		Set<String> newWindows = getWindowHandles();
		Set<String> difference = Sets.difference(newWindows, preexistingWindows);

		if (difference.size() > 0) {
			windowLocator = (difference.iterator().next());

			Util.printDebug("The randomly generated locator of the pop-up is '" + windowLocator + "'");
		}

		return windowLocator;
	}
*/

	@Override
	public int dismissModalDialog(String command, int timeoutSecondsProcessArg, int timeoutMultiplierProcessWait, boolean okIfDoesNotAppear, String... args) {
		int exitCode;

		exitCode = EISTestBase.execProcess(command, timeoutSecondsProcessArg, timeoutMultiplierProcessWait, args);

		if ((exitCode != EISConstants.PROCESS_ERROR_EXIT_CODE) && (exitCode != EISConstants.PROCESS_EXIT_CODE_INVALID_ARGS)){
			if (okIfDoesNotAppear) {
				if ((exitCode == EISConstants.PROCESS_EXIT_CODE_SUCCESS) || (exitCode == EISConstants.PROCESS_EXIT_CODE_TIMEOUT_ON_OPEN)) {
					exitCode = EISConstants.PROCESS_EXIT_CODE_SUCCESS;
				}
			}
		}

		return exitCode;
	}

	@Override
	public int dismissModalDialog(String command, int timeoutSecondsProcessArg, boolean okIfDoesNotAppear, String... args) {
		return dismissModalDialog(command, timeoutSecondsProcessArg, EISConstants.DISMISS_MODAL_DIALOG_DEFAULT_PROCESS_WAIT_MULTIPLIER, okIfDoesNotAppear, args);
	}

	@Override
	public int dismissModalDialog(String command, int timeoutSecondsProcessArg, int timeoutMultiplierProcessWait, String... args) {
		return dismissModalDialog(command, timeoutSecondsProcessArg, timeoutMultiplierProcessWait, false, args);
	}

	@Override
	public int dismissModalDialog(String command, int timeoutSecondsProcessArg, String... args) {
		return dismissModalDialog(command, timeoutSecondsProcessArg, EISConstants.DISMISS_MODAL_DIALOG_DEFAULT_PROCESS_WAIT_MULTIPLIER, false, args);
	}

	@Override
	public int clickLinkInIE(String command, int timeoutSecondsProcessArg, String url, String linkText, boolean okIfNotFound) {
		int exitCode;

		//Note that the AutoIT process we call (ClickLinkInIE.exe) does not wait for anything, so the value of timeoutSecondsProcessArg
		//  is used in EISTestBase.execProcess only to validate the overall time taken by the process
		exitCode = EISTestBase.execProcess(command, timeoutSecondsProcessArg, 1, url, linkText);

		//For some reason, PROCESS_EXIT_CODE_LINK_NOT_FOUND is returned quite often, even though the link does get clicked
		if ((exitCode != EISConstants.PROCESS_ERROR_EXIT_CODE) && (exitCode != EISConstants.PROCESS_EXIT_CODE_INVALID_ARGS)){
			if (okIfNotFound) {
				exitCode = EISConstants.PROCESS_EXIT_CODE_SUCCESS;
			}
		}

		return exitCode;
	}
	
	
	@Override
	public int clickLinkInIE(String command, String url, String linkText, boolean okIfNotFound) {
		return clickLinkInIE(command, EISConstants.CLICK_LINK_IN_IE_DEFAULT_PROCESS_WAIT, url, linkText, okIfNotFound);
	}
	
	@Override
	public int clickLinkInIE(String command, int timeoutSecondsProcessArg, String url, String linkText) {
		return clickLinkInIE(command, timeoutSecondsProcessArg, url, linkText, true);
	}

	@Override
	public int clickLinkInIE(String command, String url, String linkText) {
		return clickLinkInIE(command, EISConstants.CLICK_LINK_IN_IE_DEFAULT_PROCESS_WAIT, url, linkText, true);
	}
	
	
	//WebDriver does not differentiate between alerts and confirmations
/*	@Override
	public void chooseCancelOnNextConfirmation() {
		gui.chooseCancelOnNextConfirmation();
	}
*/
	
	//WebDriver does not differentiate between alerts and confirmations
/*	@Override
	public boolean isConfirmationPresent() {
		return gui.isConfirmationPresent();
	}
*/
	
	//WebDriver does not differentiate between alerts and confirmations
/*	@Override
	public String getConfirmation() {
		return gui.getConfirmation();
	}
*/
	
	@Override
	public String handleAlert(EISConstants.AlertResponseType alertResponse) {
    	//NOTE that the caller needs to point the driver back to the main window
    	//  by calling mainWindow.select()!

		return gui.handleAlert(alertResponse);
	}

	@Override
	public boolean isAlertPresent() {
    	//NOTE that the caller needs to point the driver back to the main window
    	//  by calling mainWindow.select()!

		return gui.isAlertPresent();
	}

	@Override
	public String getAlert() {
    	//NOTE that the caller needs to point the driver back to the main window
    	//  by calling mainWindow.select()!

		return gui.getAlert();
	}
	
	public void verifyAlert(String alertMessage) {
		//NOTE that the caller needs to point the driver back to the main window
    	//  by calling mainWindow.select()!
		
		EISTestBase.assertTrue("Alert message \"" + alertMessage + "\" is found", getAlert().contains(alertMessage));
	}

	@Override
	public void respondToAlert(EISConstants.AlertResponseType alertResponse) {
    	//NOTE that the caller needs to point the driver back to the main window
    	//  by calling mainWindow.select()!

		gui.respondToAlert(alertResponse);
	}

	@Override
	public void acceptAlert() {
    	//NOTE that the caller needs to point the driver back to the main window
    	//  by calling mainWindow.select()!

		gui.respondToAlert(EISConstants.AlertResponseType.OK);
	}

	@Override
	public void dismissAlert() {
    	//NOTE that the caller needs to point the driver back to the main window
    	//  by calling mainWindow.select()!

		gui.respondToAlert(EISConstants.AlertResponseType.CANCEL);
	}

	//DEBUG this code is not yet ready for prime time.  It should be used as a model to create
	//  build a getMultipleValuesFromGUI method (a 'multiple' flavor of getValueFromGUI) 
	@Override
	public List<String> getMultipleTextValuesFromGUI(String fieldName) {
		Field field = null;

		try {
			field = getFieldMetadata(fieldName);
		} catch (MetadataException me) {
			EISTestBase.fail("Error while getting the values of the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
		}

		return gui.getMultipleTextValues(field);
	}

	@Override
	public boolean verifyFieldExistsInTable(String tableName, int row,
			String columnName,String fieldLocator)  {
		boolean flag = false;
		List<String> locatorParms = null;
		try {
			validateParameterizedLocatorType(columnName, EISConstants.ParameterizedLocatorType.TABLE_CELL_LOOKUP);
			locatorParms = getFieldMetadata(columnName).getLocatorParmsSets().get(0);
			flag = findElementExists(locatorParms.get(0), Integer.parseInt(locatorParms.get(1)), row, fieldLocator);
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		 catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GUIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	
	private boolean findElementExists(String tableFieldName, int columnNumber, int rowNumber,
			String fieldLocator) throws GUIException {
		String cellLocator;
		String elementLocator;

		try {
			Util.printInfo("tableFieldName : "+tableFieldName);
			Util.printInfo("columnNumber : "+columnNumber);
			Util.printInfo("rowNumber : "+rowNumber);
			cellLocator = getTableCellLocator(tableFieldName, columnNumber, rowNumber);
		} catch (GUIException ge) {
			throw new GUIException("Error while clicking the link '" + tableFieldName + "' on the Page '" + name + "': " + ge.getMessage());
		}
		
		elementLocator = cellLocator + fieldLocator;
		
		return gui.isElementPresent(elementLocator);
	}


	
	//**********************************************************************
	//  "...AndWait" methods that do not specify a field to wait for.  These
	//  are deprecated, and are NOT to be used!!!
	
	/*	@Override
		public void clickLinkInInfoPanelAndWait(String fieldName, int linkNumberInCell, String waitTime) {
			clickLinkInInfoPanelAndWait(fieldName, linkNumberInCell, Integer.parseInt(waitTime));
		}
	*/	
		
	/*	@Override
		public void clickLinkInInfoPanelAndWait(String fieldName, String waitTime) {
			clickLinkInInfoPanelAndWait(fieldName, 1, waitTime);
		}
	*/
		
	/*	@Override
		public void clickLinkInInfoPanelAndWait(String fieldName, int linkNumberInCell) {
			clickLinkInInfoPanelAndWait(fieldName, linkNumberInCell, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
		}
	*/
		
	/*	@Override
		public void clickLinkInInfoPanelAndWait(String fieldName, int linkNumberInCell, int waitTime) {
			clickLinkInInfoPanel(fieldName, linkNumberInCell);
	
			//We are not calling gui.waitForPageToLoad() (directly or from doPopulate()) anymore,
			//  so we may need to apply some "wait" logic here
			//try {
			//	gui.waitForPageToLoad(waitTime);
			//} catch (GUIException ge) {
			//	Util.printDebug(ge.getMessage() + " (while clicking the link '" + fieldName + "' on the Page '" + name + "'); will continue");
			//}
		}
	*/
		
	/*	@Override
		public void clickLinkInInfoPanelAndWait(String fieldName) {
			clickLinkInInfoPanelAndWait(fieldName, 1);
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldName, int rowNumber, int linkNumberInCell, String waitTime) {
			clickLinkInTableAndWait(fieldName, rowNumber, linkNumberInCell, Integer.parseInt(waitTime));
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldName, int rowNumber, String waitTime) {
			clickLinkInTableAndWait(fieldName, rowNumber, 1, waitTime);
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldName, int rowNumber, int linkNumberInCell) {
			clickLinkInTableAndWait(fieldName, rowNumber, linkNumberInCell, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldName, int rowNumber, int linkNumberInCell, int waitTime) {
			clickLinkInTable(fieldName, rowNumber, linkNumberInCell);
	
			//We are not calling gui.waitForPageToLoad() (directly or from doPopulate()) anymore,
			//  so we may need to apply some "wait" logic here
			//try {
			//	gui.waitForPageToLoad(waitTime);
			//} catch (GUIException ge) {
			//	Util.printDebug(ge.getMessage() + " (while clicking the link '" + fieldName + "' on the Page '" + name + "'); will continue");
			//}
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldName, int rowNumber) {
			clickLinkInTableAndWait(fieldName, rowNumber, 1);
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell, String waitTime) {
			clickLinkInTableAndWait(fieldNameToSearch, value, fieldNameToClick, linkNumberInCell, Integer.parseInt(waitTime));
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick, String waitTime) {
			clickLinkInTableAndWait(fieldNameToSearch, value, fieldNameToClick, 1, waitTime);
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell) {
			clickLinkInTableAndWait(fieldNameToSearch, value, fieldNameToClick, linkNumberInCell, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell, int waitTime) {
			clickLinkInTable(fieldNameToSearch, value, fieldNameToClick, linkNumberInCell);
	
			//We are not calling gui.waitForPageToLoad() (directly or from doPopulate()) anymore,
			//  so we may need to apply some "wait" logic here
			//try {
			//	gui.waitForPageToLoad(waitTime);
			//} catch (GUIException ge) {
			//	Util.printDebug(ge.getMessage() + " (while clicking the link '" + fieldNameToClick + "' on the Page '" + name + "'); will continue");
			//}
		}
	*/
		
	/*	@Override
		public void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick) {
			clickLinkInTableAndWait(fieldNameToSearch, value, fieldNameToClick, 1);
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldName, int rowNumber, int linkNumberInCell, String waitTime) {
			clickLinkInRelatedListAndWait(fieldName, rowNumber, linkNumberInCell, Integer.parseInt(waitTime));
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldName, int rowNumber, String waitTime) {
			clickLinkInRelatedListAndWait(fieldName, rowNumber, 1, waitTime);
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldName, int rowNumber, int linkNumberInCell) {
			clickLinkInRelatedListAndWait(fieldName, rowNumber, linkNumberInCell, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldName, int rowNumber, int linkNumberInCell, int waitTime) {
			clickLinkInRelatedList(fieldName, rowNumber, linkNumberInCell);
	
			//We are not calling gui.waitForPageToLoad() (directly or from doPopulate()) anymore,
			//  so we may need to apply some "wait" logic here
			//try {
			//	gui.waitForPageToLoad(waitTime);
			//} catch (GUIException ge) {
			//	Util.printDebug(ge.getMessage() + " (while clicking the link '" + fieldName + "' on the Page '" + name + "'); will continue");
			//}
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldName, int rowNumber) {
			clickLinkInRelatedListAndWait(fieldName, rowNumber, 1);
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell, String waitTime) {
			clickLinkInRelatedListAndWait(fieldNameToSearch, value, fieldNameToClick, linkNumberInCell, Integer.parseInt(waitTime));
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick, String waitTime) {
			clickLinkInRelatedListAndWait(fieldNameToSearch, value, fieldNameToClick, 1, waitTime);
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell) {
			clickLinkInRelatedListAndWait(fieldNameToSearch, value, fieldNameToClick, linkNumberInCell, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell, int waitTime) {
			clickLinkInRelatedList(fieldNameToSearch, value, fieldNameToClick, linkNumberInCell);
	
			//We are not calling gui.waitForPageToLoad() (directly or from doPopulate()) anymore,
			//  so we may need to apply some "wait" logic here
			//try {
				gui.waitForPageToLoad(waitTime);
			//} catch (GUIException ge) {
			//	Util.printDebug(ge.getMessage() + " (while clicking the link '" + fieldNameToClick + "' on the Page '" + name + "'); will continue");
			//}
		}
	*/
		
	/*	@Override
		public void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick) {
			clickLinkInRelatedListAndWait(fieldNameToSearch, value, fieldNameToClick, 1);
		}
	*/
		
	/*	@Override
		public boolean clickAndWait(String fieldName, int waitTime) {
			//NOTE that we never actually make a Selenium clickAndWait call.  See doPopulate for handling 
			this.waitTime = waitTime;
	
			return click(fieldName);
		}
	*/	
	
	/*	@Override
		public boolean clickAndWait(String fieldName) {
			return clickAndWait(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
		}
	*/
	
	/*	@Override
		public boolean checkAndWait(String fieldName, int waitTime) {
			//We are not calling gui.waitForPageToLoad() (directly or from doPopulate()) anymore,
			//  so we may need to apply some "wait" logic here
			//this.waitTime = waitTime;
			
			return check(fieldName);
		}
	*/
		
	/*	@Override
		public boolean checkAndWait(String fieldName) {
			return checkAndWait(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
		}
	*/
		
	/*	@Override
		public boolean checkAndWait(String fieldName, String waitTime) {
			return checkAndWait(fieldName, Integer.parseInt(waitTime));
		}
	*/
		
	/*	@Override
		public boolean uncheckAndWait(String fieldName, int waitTime) {
			//We are not calling gui.waitForPageToLoad() (directly or from doPopulate()) anymore,
			//  so we may need to apply some "wait" logic here
			//this.waitTime = waitTime;
			
			return uncheck(fieldName);
		}
	*/
		
	/*	@Override
		public boolean uncheckAndWait(String fieldName) {
			return uncheckAndWait(fieldName, EISConstants.DEFAULT_WINDOW_WAIT_TIMEOUT);
		}
	*/
		
	/*	@Override
		public boolean uncheckAndWait(String fieldName, String waitTime) {
			return uncheckAndWait(fieldName, Integer.parseInt(waitTime));
		}
	 */
	//**********************************************************************
	
	//saxenas -> fieldName to be checked should be the name of the input field. 
	//These methods should ideally prevent the need for creation of new fieldLocators is order to verify mandatory nature of field
	//Checked to be valid for for SFDC, Partner Center
	public boolean verifyFieldIsRequired (String fieldName) throws MetadataException {
		return verifyFieldIsRequired (fieldName, true);
	}
	
	public boolean verifyFieldIsRequired (String fieldName, boolean expected) throws MetadataException {
		return verifyFieldRequiredness (fieldName, expected);
	}
	
	private boolean verifyFieldRequiredness (String fieldName, boolean expected) throws MetadataException {
		
		boolean actual = false;
		Field field;
		List<String> finalFieldLocators = new ArrayList<>();
		String requiredBlockPath = "//ancestor::*[contains(@class,'required')]//div[@class='requiredBlock']";
		String errorMsgPath = "//ancestor::td[1]//div[@class='errorMsg']"; 
		
		//Click save to make sure all errors if not present
		if(!driver.findElement(By.id("errorDiv_ep")).isDisplayed()) {
			gui.click("//input[@name='save']");
		}
		
		//verify Invalid Data Error at top of page
		if(driver.findElement(By.id("errorDiv_ep")).isDisplayed())
		{
			//check fieldName is accessible 
			try {
				field = getFieldMetadata(fieldName);
			} 
			catch (MetadataException me) {
				throw new MetadataException("Error while accessing the GUI element '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
			}
			
			//case: field level info is present	
			if (!field.isLocatorParameterized()) {
				for (String eachLocator : field.getLocators()) {
					finalFieldLocators.add(eachLocator + requiredBlockPath);	//checks Required Bar is present by field
					finalFieldLocators.add(eachLocator + errorMsgPath); 		//checks Field Error Message exists by field
				}
				actual= gui.isElementPresent(finalFieldLocators);
			} 
			else {
				throw new MetadataException("Error while verifying the existence or non-existence of the GUI element 'Required Markers for " + fieldName + "' on the page '" + name + "': Fields whose locators specify a table cannot be checked for existence");
			}
			
			//case: invalid Data Error exists, yet field level info is not present
			if(!actual) {
				finalFieldLocators = null;
				String fieldLabelPath = "//ancestor::td[1]//preceding-sibling::td[1]//label";
				for (String eachLocator : field.getLocators()) {
					String labelLocator = eachLocator + fieldLabelPath;
					if (gui.isElementPresent(labelLocator)) { 
						try {
						actual = driver.findElement(By.id("errorDiv_ep")).getText().contains(gui.getText(labelLocator) + " is required");
						if(actual){break;}
						} catch (GUIException e) {
							Util.printInfo("Error while accessing text for gui Field" + fieldName);
							e.printStackTrace();
						}
					}
				}
				
			}
		}
		
		return EISTestBase.assertFieldExistenceWithFlags(name, fieldName+" Required Markers", actual, expected);
	}

//	private void doSwitchToFrame (List<String>)
//	private void switchToFrame (String fieldName) {
//		Field field = null;
//
//		try {
//			field = getFieldMetadata(fieldName);
//		} catch (MetadataException me) {
//			EISTestBase.fail("Error while checking for the presence of the frame '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
//		}
//		
//		try {
//			doSwitchToFrame(field.getLocators());
//		} catch (MetadataException me) {
//			EISTestBase.fail("Error while switching to frame '" + fieldName + "' on the page '" + name + "': " + me.getMessage());
//		}	
//	}		
	/**
     * @Description This is used to pull the textcontent from matching locators at once 
     * @return ArrayList
	 * @throws MetadataException 
     */	
	@Override
    public String[] getMultipleTextValuesfromField(String fieldName) throws MetadataException {
    /*	String[] elementData= null;
    	Field field=null;
    	field=getFieldMetadata(fieldName);    	 
		List<WebElement>elementList=gui.getMultipleTextValuesfromField(field);
		elementData=new String[elementList.size()];
		for (int i=0;i<elementData.length;i++){				
				elementData[i]=elementList.get(i).getText();	
		}
    	return elementData;*/
		return getMultipleTextValuesfromField(fieldName,false);
	}
	/**
	 * @Description: Same as above function but returns in list of elements
	 * @param fieldName
	 * @return
	 * @throws MetadataException
	 */
	public List<?> getListOfMultipleTextValuesfromField(String fieldName) throws MetadataException {
		List<?> lstFieldValues= new ArrayList<>(Arrays.asList(getMultipleTextValuesfromField(fieldName,false)));		
		return lstFieldValues;
	}
	/**
     * @Description This is used to pull the textcontent from matching locators at once 
     * @return ArrayList
	 * @throws MetadataException 
     */	
	@Override
    public String[] getMultipleTextValuesfromField(String fieldName,boolean ignoreDuplicates) throws MetadataException {
    	String[] elementData= null;
    	ArrayList<String> actualTextValues= new ArrayList<>();
    	Field field=null;
    	field=getFieldMetadata(fieldName);    	 
    	String[] newElementData =null;
    	List<WebElement>elementList=null;
		try {
			elementList=gui.getMultipleTextValuesfromField(field);
//		elementData=new String[elementList.size()];
			for (int i=0;i<elementList.size();i++){	
				String tempVal=elementList.get(i).getText().replaceAll(":", "").trim();
				if (ignoreDuplicates){				
					if (!actualTextValues.contains(tempVal)){
						actualTextValues.add(tempVal);
					}
				}else{
					actualTextValues.add(tempVal);
				}
					
			}	
		} catch (StaleElementReferenceException staleExp) {
			//Re do the same stuff
			elementList=gui.getMultipleTextValuesfromField(field);
//			elementData=new String[elementList.size()];
				for (int i=0;i<elementList.size();i++){	
					String tempVal=elementList.get(i).getText().replaceAll(":", "").trim();
					if (ignoreDuplicates){				
						if (!actualTextValues.contains(tempVal)){
							actualTextValues.add(tempVal);
						}
					}else{
						actualTextValues.add(tempVal);
					}
						
				}	
		}
		//Push the data from Array List to string[]
		elementData=new String[actualTextValues.size()];
		for(int i=0;i<actualTextValues.size();i++){
			elementData[i]=actualTextValues.get(i).trim();			
		}
		//Last try with Javascript if the elementData is still empty at the end
		if (elementData[elementData.length-1]==null||elementData[0].isEmpty()||elementData[elementData.length-1]==" "){
			String locator=field.getLocators().get(0);	//grab the first one . need to extend for tother as well
		String jScript=" var ele=\"\";if(document.evaluate){" +
				       "ele=document.evaluate(\""+locator+"\",document,null,XPathResult.ANY_TYPE,null);"+
				       "}else if (window.ActiveXObject){" +
				       "ele	=document.selectNodes(\""+locator+"\")" +
				       "}"+
						"if(ele){"+
						"var nodes=ele.iterateNext();"+
						"var expval=\"\";"+
						"while(nodes){"+
						" expval=expval+\",\"+nodes.textContent.trim();  "+
						" nodes=ele.iterateNext();"+
						"} }" +
						"return expval; ";		
		try {			
			Object obj=((JavascriptExecutor)(driver)).executeScript(jScript);
			//take out the first comma
			String tempContent=obj.toString().substring(1).replaceAll(":","").replaceAll("-", "").trim();
			ArrayList<String> lstValues= new ArrayList<>(Arrays.asList(tempContent.split(",")));	//requried to remove duplicates
			ArrayList<String> newList= new ArrayList<>();
			if (ignoreDuplicates){
				for(String myVal: lstValues){
					if (!newList.contains(myVal)){
						newList.add(myVal);
					}
				}
				//then push the elements from newList to newElementData array
				newElementData=new String[newList.size()];
				for(int i=0;i<newList.size();i++){
					newElementData[i]=newList.get(i);
				}
				
			}else{				
				//convert that into array of strings
				newElementData=tempContent.split(",");			
			}
			elementData=newElementData;
			
		} catch (Exception e) {
			Util.printError("Unable to execute the javascript to get the hidden meta data wrapper fields"+"\n"+e.getMessage());
		}
		
		}
    	return elementData;
	}
	/**
	 * @Description wait for the element to disappear
	 * @param fieldName
	 * @param iTimeout
	 * @return
	 * @throws MetadataException
	 */
	public boolean waitForElementToDisappear(String fieldName,int iTimeout) throws MetadataException {
		Field field=null;
		field=getFieldMetadata(fieldName);
		return gui.waitForElementToDisappear(field, iTimeout);
	}
	/**
	 * @Description verify if the element exists in the page
	 * @param fieldName
	 * @param iTimeout
	 * @return
	 * @throws MetadataException
	 */
	public boolean checkIfElementExistsInPage(String fieldName,int iTimeout) throws MetadataException {
		Field field=null;
		field=getFieldMetadata(fieldName);
		return gui.verifyIfElementIsPresentOnDOM(field, iTimeout);
	}

	@Override
	public void clickUsingLowLevelActions(String fieldLocator)	throws MetadataException {
		Field field=getFieldMetadata(fieldLocator);		
		gui.clickUsingLowLevelActions(field);
		
	}

	/**
	 * @Description This is used to get to know if all the checkboxes in a page are checked
	 */
	public boolean checkIfAllCheckboxesAreChecked(String fieldLocator) throws MetadataException {
		Field field= getFieldMetadata(fieldLocator);		
		List<WebElement> lstElements=gui.getMultipleTextValuesfromField(field);
		try {
				
			for(WebElement wEle: lstElements){
				//check if each webelement is checked
				if (!wEle.isSelected()) return false;
			}			
		} catch (Exception e) {
			Util.printDebug("Unable to get the elements checkbox state. Please check metadata element xpath");
			throw new MetadataException("Unable to get the elements checkbox state. Please check metadata element xpath");
		}		
		return true;
		
	}
	
	/**
	 * @Description: Used to uncheck/check all the checkboxes in a page
	 * @param fieldLocator
	 * @param checkOrUncheck
	 * @throws MetadataException
	 */
	public void checkAllCheckboxes(String fieldLocator,boolean checkOrUncheck) throws MetadataException {
		Field field= getFieldMetadata(fieldLocator);		
		List<WebElement> lstElements=gui.getMultipleTextValuesfromField(field);
		try {
			try {
				for(WebElement wEle: lstElements){
					//check if each webelement is checked
					//to check
					if (checkOrUncheck){					
						if (!wEle.isSelected()) {
							wEle.click();						
						}
					}else{
						//see if it is selected and then uncheck it 
						if (wEle.isSelected()) {
							wEle.click();						
						}
					}			
				}	
			}
			catch (Exception e) {
				Util.printDebug("Unable to uncheck/check the metadata elements check box.");
				throw new MetadataException("Unable to uncheck/check the metadata elements check box.");
			}
		} catch (Exception e) {
			Util.printDebug("Unable to uncheck/check the check box. Please check metadata element xpath");
		}		

	}
	@Override
	public List<WebElement> getMultipleWebElementsfromField(String fieldName)
			throws MetadataException {
    	
    	Field field=null;
    	field=getFieldMetadata(fieldName);
		List<WebElement>elementList=gui.getMultipleTextValuesfromField(field);
		return elementList;
	}
	public WebElement getCurrentWebElement() throws MetadataException{
		return gui.getWebElement();
	} 
	public List<WebElement> getCurrentWebElementList() throws MetadataException {
		return gui.getWebElementList();
	}
	
	
	public String getDOMAttributeOfWebElement(WebElement wEle,String attributeProperty) {
		String attrOfObject="";
		try {
			String jscriptTogetDOMAttribute="return arguments[0]."+attributeProperty+";";
			Object obj=((JavascriptExecutor)(driver)).executeScript(jscriptTogetDOMAttribute,gui.getWebElement());
			attrOfObject= obj.toString();		
			
		} catch (Exception e) {
			Util.printError("Unable to retrieve the DOM attribute requested for the Webelement,Please check the specific attribute:"+attributeProperty+" exists in DOM" );
		}
		if(attrOfObject.isEmpty() || attrOfObject==""){
			Util.printWarning("The DOM attribute is not found for the metadata element. Please check whether attribute exists"); 
		}
		return attrOfObject;
	}
	public void scrollIntoViewOfMetadataElement(WebElement metadataElement)  {
		try {
						
				((JavascriptExecutor)(driver)).executeScript("arguments[0].scrollIntoView(true);",metadataElement);
		
			
		} catch (Exception e) {
			Util.printWarning("Unable to scroll the webelement into view");
			
		}
	}
	/**
	 * @Description Scroll into list of metadata elements
	 * @param metadataElement
	 */
	public void scrollIntoViewOfAllMetadataElements(List<WebElement> metadataElements)  {
		try {			
				for(WebElement myElement:metadataElements){
					
					((JavascriptExecutor)(driver)).executeScript("arguments[0].scrollIntoView(true);",myElement);
				}	
		} catch (Exception e) {
			Util.printWarning("Unable to scroll the webelement into view");
			
		}
	}
	public void scrollIntoViewOfMetadataElementParentNode(WebElement metadataElement)  {
		try {
						
				((JavascriptExecutor)(driver)).executeScript("arguments[0].parentNode.scrollIntoView(true);",metadataElement);
		
			
		} catch (Exception e) {
			Util.printWarning("Unable to scroll the webelement's parent node into view");
			
		}
	}
	public void clickUsingInputDevicesMouseClick(WebElement metadataElement) throws Exception {			
			
			Locatable clickItem= (Locatable)metadataElement;
			Mouse mouse= ((HasInputDevices) driver).getMouse();
			mouse.click(clickItem.getCoordinates());		
			Util.sleep(1000);
	}
	
	/**
	 * @Description This method is used to hover on webelement using mouse Input device of Selenium
	 * @param metadataElement
	 * @throws Exception
	 */
	private void hoverOnWebElement(WebElement metadataElement) throws Exception{		
		Locatable clickItem= (Locatable)metadataElement;
		Mouse mouse= ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(clickItem.getCoordinates());
		Util.sleep(1500);	//pause for a sec after doing this action		
	}


	@Override
	public void clickUsingLowLevelActions(WebElement elementToClick)
			throws MetadataException {
		gui.clickUsingLowLevelActions(elementToClick);
		
	}
	
	
	
}
	

