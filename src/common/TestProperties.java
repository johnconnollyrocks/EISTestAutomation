package common;

import java.io.IOException;
import java.util.*;

import com.eviware.soapui.support.StringUtils;

import common.exception.MetadataException;
import common.exception.TestDataException;

/**
 * Manages all test data, verification data, and metadata in test properties files.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestProperties {
	//Used to find test-level metadata entries in the test properties object
	private static final String TEST_NAME			= "name";
	private static final String DESCRIPTION			= "description";
	private static final String RESULT_TYPE			= "resultType";
	private static final String AUTHOR				= "author";
	private static final String CREATE_DATE			= "createDate";
	private static final String LAST_MODIFIED_DATE	= "lastModifiedDate";
	
	//Test-level metadata, from the test properties file
	//   (be sure to initialize optional properties where appropriate, in case they are not specified in the properties file)
	private String name;																	//required (no default)
	private String description						= "";									//optional (defaults to "")
	private EISConstants.TestResultType resultType	= EISConstants.TestResultType.POSITIVE;	//optional (defaults to POSITIVE)
	private String author 							= "";									//optional (defaults to "")
	private String createDate 						= "";									//optional (defaults to "")
	private String lastModifiedDate 				= "";									//optional (defaults to "")
	
	private String testPropertiesFilename;
	
	private List<String> includedTestPropertiesFilenames = new ArrayList<String>();

	//Staging area for test properties (test and verification data, and metadata)
	//  (we cannot use a Properties object because duplicates are allowed)
	private List<String[]> testProperties = new ArrayList<String[]>();

	//All (that is, for all pages) test data (not test metadata) lives here
	//  The Page class constructor will call getPageTestData to retrieve its test data
	private List<TestData> testData = new ArrayList<TestData>();
	
	//All (that is, for all pages) verification data (not test metadata) lives here
	//  The Page class constructor will call getPageVerificationData to retrieve its verification data
	private List<TestData> verificationData = new ArrayList<TestData>();
	
	//All page-scope constants live here
	//  The Page class constructor will call getPageConstants to retrieve its constants,
	//    which will be returned in a Properties object  
	private List<TestData> pageConstants = new ArrayList<TestData>();
	
	//All test-scope constants live here
	private Properties testConstants = new Properties();
	
	//Used for mapping constant names to instance values (integers)
	private Hashtable<String, Integer> instanceMap = new Hashtable<String, Integer>();
	
	private int numTestDataRecords = 0;
	private int numVerificationDataRecords = 0;
	private int numPageConstants = 0;
	private int numTestConstants = 0;

	private String testDataDir			= "";
	private Properties testManifest 	= new Properties();
	
	public TestProperties(String testPropertiesFilename, String testDataDir, Properties testManifest) {
		//TODO  ***** JAB  Add error-checking here
		this.testPropertiesFilename = testPropertiesFilename;
		this.testDataDir = testDataDir;
		this.testManifest = testManifest;
		
		//Because this class is instantiated directly from the test code, and we want to shield the user from having
		//  to write too much Java just to catch errors that require failing the test, we are not propagating the
		//  exception handling up the call chain
		//
		//Note that we are calling EISTestBase.fail(message) instead of org.junit.Assert.Rfail(message),
      	//  as we may (but shouldn't!) be doing elsewhere.

		try {
			testProperties = loadTestProperties(testPropertiesFilename);
		} catch (TestDataException te) {
			EISTestBase.fail(te.getMessage());
		}

		try {
			loadIncludedTestProperties();
		} catch (TestDataException te) {
			EISTestBase.fail(te.getMessage());
		}

		try {
			setTestMetadata();
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		try {
			setConstants();
		} catch (TestDataException te) {
			EISTestBase.fail(te.getMessage());
		}

		try {
			setData();
		} catch (TestDataException te) {
			EISTestBase.fail(te.getMessage());
		}
		
		Util.printInfo("");
		Util.printInfo("Read " + testProperties.size() + " test properties from '" + testPropertiesFilename + "'");
		
		numTestDataRecords = testData.size();
		Util.printInfo("Number of test data records:         " + numTestDataRecords);

		numVerificationDataRecords = verificationData.size();
		Util.printInfo("Number of verification data records: " + numVerificationDataRecords + "\n");

		numPageConstants = pageConstants.size();
		Util.printInfo("Number of page-scope constants:      " + numPageConstants);

		numTestConstants = testConstants.size();
		Util.printInfo("Number of test-scope constants:      " + numTestConstants + "\n");
	}
	
	/**
	 * @Description dont need test manifest file. it not required
	 * @param testPropertiesFilename
	 * @param testDataDir
	 */
	public TestProperties(String testPropertiesFilename, String testDataDir ) {
		this.testPropertiesFilename = testPropertiesFilename;
		this.testDataDir = testDataDir;		
		
		try {
			testProperties = loadTestProperties(testPropertiesFilename);
		} catch (TestDataException te) {
			EISTestBase.fail(te.getMessage());
		}

		try {
			loadIncludedTestProperties();
		} catch (TestDataException te) {
			EISTestBase.fail(te.getMessage());
		}

		try {
			setTestMetadata();
		} catch (MetadataException me) {
			EISTestBase.fail(me.getMessage());
		}

		try {
			setConstants();
		} catch (TestDataException te) {
			EISTestBase.fail(te.getMessage());
		}

		try {
			setData();
		} catch (TestDataException te) {
			EISTestBase.fail(te.getMessage());
		}
		
		Util.printInfo("");
		Util.printInfo("Read " + testProperties.size() + " test properties from '" + testPropertiesFilename + "'");
		
		numTestDataRecords = testData.size();
		Util.printInfo("Number of test data records:         " + numTestDataRecords);

		numVerificationDataRecords = verificationData.size();
		Util.printInfo("Number of verification data records: " + numVerificationDataRecords + "\n");

		numPageConstants = pageConstants.size();
		Util.printInfo("Number of page-scope constants:      " + numPageConstants);

		numTestConstants = testConstants.size();
		Util.printInfo("Number of test-scope constants:      " + numTestConstants + "\n");
	}
	
	//We store global test and verification data in this format (instead of in a
	//  list of FieldDataInstance) because when we need to access the contents of
	//  the fieldData member (e.g., in resolveTestDataTokens) we don't want to use
	//  the FieldDataInstance.getFieldData method, because it returns a deep copy,
	//  which prevents us from editing these local test data elements
	private class TestData {
		String pageName;
		FieldData_ fieldData;
		int instance;
		
		private TestData(String pageName, FieldData_ fieldData, int instance) {
			this.pageName = pageName;
			this.fieldData = fieldData;
			this.instance = instance;
		}
		
		private TestData(String pageName, FieldData_ fieldData) {
			this(pageName, fieldData, 1);
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public EISConstants.TestResultType getResultType() {
		return resultType;
	}

	public String getAuthor() {
		return author;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public String getTestPropertiesFilename() {
		return testPropertiesFilename;
	}

	public List<String> getIncludedTestPropertiesFilenames() {
		return new ArrayList<String>(includedTestPropertiesFilenames);
	}

	public int getNumTestDataRecords() {
		return numTestDataRecords;
	}
	
	public int getNumVerificationDataRecords() {
		return numVerificationDataRecords;
	}
	
	public int getNumPageConstants() {
		return numPageConstants;
	}

	public int getNumTestConstants() {
		return numTestConstants;
	}

	public Properties getConstants() {
		//This deep copy is necessary (Properties objects are mutable)
		Properties newTestConstantsProperties = new Properties();
	    Enumeration<?> keys;
	    String key;

	    keys = testConstants.keys();
	    while (keys.hasMoreElements()) {
	    	key = (String) keys.nextElement();
	    	newTestConstantsProperties.setProperty(key, testConstants.getProperty(key));
	    }

		return newTestConstantsProperties;
	}
	
	public String getConstant(String constantName) {
		String constantNameUpper = constantName.toUpperCase();
		String value = testConstants.getProperty(constantNameUpper, "");

		if (value.isEmpty()) {
			Util.printDebug("The constant '" + constantNameUpper + "' was not found in the test-scope constant store; returning empty string");
		}
		if (StringUtils.isNullOrEmpty(value)) {
			value="";	//send empty val instead of null just to avoid null pointer exception
		}
		return value;
	}

	public boolean constantExists(String constantName) {
		return testConstants.containsKey(constantName.toUpperCase());
	}
	
	public String getTestMetadata() {
		return	"Test or utility name:          " + name + "\n" +
				"Description:                   " + description + "\n" +
				"Result type:                   " + resultType + "\n" +
				"Author:                        " + author + "\n" +
				"Created:                       " + createDate + "\n" +
				"Last modified:                 " + lastModifiedDate;
	}
	
	

	@Override
	public String toString() {
		return	getTestMetadata() + "\n" +
		      	"Properties file:               " + testPropertiesFilename + "\n" +
		      	"Num test data records:         " + numTestDataRecords + "\n" +
				"Num verification data records: " + numVerificationDataRecords + "\n" +
				"Num page constants:            " + numPageConstants + "\n" +
				"Num test constants:            " + numTestConstants;
	}

	private List<String[]> loadTestProperties(String propertiesFilename) throws TestDataException {
		List<String[]> testProperties;
		try {
			testProperties = Util.readKeyValuePairs(propertiesFilename, EISConstants.PROPERTY_PAIR_DELIM);
		} catch (IOException ioe) {
			throw new TestDataException(ioe.getMessage());
		}
		
		return testProperties;
	}

	//IMPORTANT COMMENTS ABOUT THE PURPOSE AND LIMITATIONS OF THIS METHOD
	//  This method loads test data from properties files that are referenced in the test properties
	//  file.  Note that this functionality IS NOT suitable for handling test data defaults, because
	//  it does not check for overrides or duplicates!  If the primary test data file includes test
	//  data records that refer to fields that are also referred to by records in an included file,
	//  RESULTS ARE UNDEFINED!!
	private void loadIncludedTestProperties() throws TestDataException {
		List<String[]> testPropertiesRaw = new ArrayList<String[]>();
		String[] pair = new String[2];
		String filename=null;
		String testPropertiesFileKey;
    	String[] metadataTags = {
			TEST_NAME,
			DESCRIPTION,
			RESULT_TYPE,
			AUTHOR,
			CREATE_DATE,
			LAST_MODIFIED_DATE
		};


		//Get a list of the property files we need to read
		Iterator<String[]> itr = testProperties.iterator();
		while (itr.hasNext()) {
			pair = itr.next();
			
			if (pair[0].compareToIgnoreCase(EISConstants.NESTED_TEST_DATA_FILE_TOKEN) == 0) {
				testPropertiesFileKey = pair[1].toUpperCase();
				
				if (testManifest!=null) {
					filename = testDataDir + testManifest.getProperty(testPropertiesFileKey, "");					
				}
				else {
					//TO DO
				}
		    	
		    	if (filename.isEmpty()) {
					throw new TestDataException("Error while opening properties file: the value returned from the manifest using the file name key '" + testPropertiesFileKey + "' does not reference a valid file name");
		    	}

				includedTestPropertiesFilenames.add(filename);

				itr.remove();
			}
		}
		
		//Loop through the list of property files to include:  remove the metadata, and then add the
		//  properties (which now contain only test and verification data) to the testProperties
		//  instance variable.
		//  NOTE that we are not checking for duplicate test data here!
		for (String propertiesFilename : includedTestPropertiesFilenames) {
			testPropertiesRaw.clear();
			
			testPropertiesRaw = loadTestProperties(propertiesFilename);
			
			Iterator<String[]> itrRaw = testPropertiesRaw.iterator();
			while (itrRaw.hasNext()) {
				pair = itrRaw.next();
				
				if (Util.arrayOfStringFind(metadataTags, pair[0], true) >= 0) {
					itrRaw.remove();
				}
			}
			
			testProperties.addAll(testPropertiesRaw);
		}
	}

	private void setTestMetadata() throws MetadataException {
		String[] pair = new String[2];
		boolean foundName = false;

		//NOTE  It is important to remove the metadata from testProperties, because in setTestData() we loop
		//  through all records.  If metadata records are there, they will be interpreted as test data

		Iterator<String[]> itr = testProperties.iterator();
		while (itr.hasNext()) {
			pair = itr.next();
			
			if (pair[0].compareToIgnoreCase(TEST_NAME) == 0) {
				name = pair[1];

				foundName = true;

				itr.remove();

				continue;
			}
			
			if (pair[0].compareToIgnoreCase(DESCRIPTION) == 0) {
				description = pair[1];

				itr.remove();

				continue;
			}
			
			if (pair[0].compareToIgnoreCase(RESULT_TYPE) == 0) {
				try {
					resultType = EISConstants.TestResultType.valueOf(pair[1].toUpperCase());
				} catch (IllegalArgumentException e) {
					throw new MetadataException("The value supplied for the test property 'resultType' is not a member of the EISConstants.TestResultType enumerated type; valid values are: " + Util.valuesOfEnum(EISConstants.TestResultType.class), e);
				}
				
				itr.remove();

				continue;
			}
			
			if (pair[0].compareToIgnoreCase(AUTHOR) == 0) {
				author = pair[1];

				itr.remove();

				continue;
			}
			
			if (pair[0].compareToIgnoreCase(CREATE_DATE) == 0) {
				createDate = pair[1];

				itr.remove();
				
				continue;
			}
			
			if (pair[0].compareToIgnoreCase(LAST_MODIFIED_DATE) == 0) {
				lastModifiedDate = pair[1];

				itr.remove();
			}
		}
		if (!foundName) {
			throw new MetadataException("The '" + TEST_NAME + "' property was not found in the test properties file '" + testPropertiesFilename + "'");
		}
	}
	
/*	private void setConstants() throws TestDataException {
		String regexTerm = ".*" + EISConstants.PARAMETERS_REGEX_TERM + ".*";
		String constantName;
		String pageName;
		String value;
		String temp;
		boolean isPageConstant;
		String[] keyValuePair = new String[2];
		String[] pageAndConstant = new String[2];
		String previousValue;
		
		Iterator<String[]> itr = testProperties.iterator();
		while (itr.hasNext()) {
			keyValuePair = itr.next();
			
			//See if the value property matches the format of a constant's value, and then check for the CONSTANT token
    		if (keyValuePair[1].matches(regexTerm)) {
    			//We now have text that matches the regex term, but that text is not necessarily a parm 
    			//  set containing the value of a constant; it could be part of a "real value".  We'll
    			//  grab the text to the left of the parms start delimiter, and see if it == EISConstants.CONSTANT_TOKEN

				temp = Util.left(keyValuePair[1], keyValuePair[1].indexOf(EISConstants.PARAMETERS_START_DELIM)).trim();

				if (temp.toUpperCase().equals(EISConstants.CONSTANT_TOKEN.toUpperCase())) {
					//We know we have a constant; determine whether it's Page-scope or a test-scope
					//pageAndConstant = keyValuePair[0].split("\\.", 2);
					pageAndConstant = keyValuePair[0].split("\\.");
					if (pageAndConstant.length == 2) {
						pageName = pageAndConstant[0];
						constantName = pageAndConstant[1].toUpperCase();
						
						isPageConstant = true;
					} else {
						pageName = "";
						constantName = pageAndConstant[0].toUpperCase();
						
						isPageConstant = false;
					}
				
	    			value = Util.getField(keyValuePair[1], EISConstants.PARAMETERS_START_DELIM, EISConstants.PARAMETERS_END_DELIM);

	    			//Resolve possible global object or date math token
	    			value = Util.resolveConstantToken(value);

	    			if (isPageConstant) {
	    				//Don't check for duplicates here if it is a Page-scope constant; it is easier to do that
	    				//  in getPageConstants, where we convert them to Properties objects (see comments there)
	    				pageConstants.add(this.new TestData(pageName, (new FieldData(constantName, value))));
	    			} else {
	    				//Check for duplicates
	    				previousValue = testConstants.getProperty(constantName, null);

	    				if (previousValue != null) {
	    					Util.printWarning("The test-scope constant '" + constantName + "' was defined more than once; all but the first instance will be discarded");
	    				} else {
	    					testConstants.setProperty(constantName, value);
	    				}
	    			}
	    			
    				itr.remove();
    			}
     		}
		}
	}
*/	
	
	private void setConstants() throws TestDataException {
		String regexTerm = ".*" + EISConstants.PARAMETERS_REGEX_TERM + ".*";
		String overrideToken = EISConstants.OVERRIDE_TOKEN.toUpperCase();
		String constantName;
		String pageName;
		String value;
		String temp;
		boolean isPageConstant;
		boolean isOverride;
		String[] keyValuePair = new String[2];
		String[] pageAndConstant = new String[2];
		String previousValue;
		Properties testConstantsOverrides = new Properties();
		
		Iterator<String[]> itr = testProperties.iterator();
		while (itr.hasNext()) {
			keyValuePair = itr.next();
			
			//See if the value property matches the format of a constant's value, and then check for the CONSTANT token
    		if (keyValuePair[1].matches(regexTerm)) {
    			//We now have text that matches the regex term, but that text is not necessarily a parm 
    			//  set containing the value of a constant; it could be part of a "real value".  We'll
    			//  grab the text to the left of the parms start delimiter, and see if it == EISConstants.CONSTANT_TOKEN or EISConstants.CONSTANT_SET_TOKEN

				temp = Util.left(keyValuePair[1], keyValuePair[1].indexOf(EISConstants.PARAMETERS_START_DELIM)).trim();

				//if (temp.toUpperCase().equals(EISConstants.CONSTANT_TOKEN.toUpperCase())) {
				if (temp.toUpperCase().equals(EISConstants.CONSTANT_TOKEN.trim().toUpperCase()) || temp.toUpperCase().equals(EISConstants.CONSTANT_SET_TOKEN.trim().toUpperCase())) {
					//We know we have a constant; determine whether it's Page-scope or test-scope
					//pageAndConstant = keyValuePair[0].split("\\.", 2);
					pageAndConstant = keyValuePair[0].split("\\.");
					if (pageAndConstant.length == 2) {
						pageName = pageAndConstant[0].trim();
						constantName = pageAndConstant[1];
						
						isPageConstant = true;
					} else {
						pageName = "";
						constantName = pageAndConstant[0];
						
						isPageConstant = false;
					}
					
					constantName = constantName.trim().toUpperCase();

					//see if it is an override constant, and remove the token if necessary
					isOverride = constantName.indexOf(overrideToken) > -1;
					if (isOverride) {
						constantName = constantName.replaceAll(overrideToken, "").trim();
					}

	    			value = Util.getField(keyValuePair[1], EISConstants.PARAMETERS_START_DELIM, EISConstants.PARAMETERS_END_DELIM);

	    			//resolve possible global object or date math token
	    			value = Util.resolveConstantToken(value);

					if (!isPageConstant) {
						if (!isOverride) {
		    				//check for duplicates in collection of constants
		    				previousValue = testConstants.getProperty(constantName, null);

		    				if (previousValue != null) {
		    					Util.printWarning("The test-scope constant '" + constantName + "' was defined more than once; all but the first instance will be discarded");
		    				} else {
		    					testConstants.setProperty(constantName, value);
		    				}
						} else {
		    				//check for duplicates in collection of overrides
		    				previousValue = testConstantsOverrides.getProperty(constantName, null);

		    				if (previousValue != null) {
		    					Util.printWarning("The test-scope constant override '" + constantName + "' was defined more than once; all but the first instance will be discarded");
		    				} else {
		    					testConstantsOverrides.setProperty(constantName, value);
		    				}
						}
					} else {
	    				//don't check for duplicates and overrides here if it is a Page-scope constant; it is easier to
	    				//  do that in getPageConstants, where we convert them to Properties objects (see comments there)
	    				pageConstants.add(this.new TestData(pageName, (new FieldData(constantName, value))));
					}
				
    				itr.remove();
    			}
     		}
		}
		
	    Enumeration<?> keys;
	    String key;

	    keys = testConstantsOverrides.keys();
	    while (keys.hasMoreElements()) {
	    	key = (String) keys.nextElement();
	    	
	    	testConstants.setProperty(key, testConstantsOverrides.getProperty(key));
	    }
	}

	
	private void setData() throws TestDataException {
		String regexTerm = ".*" + EISConstants.PARAMETERS_REGEX_TERM;
		String msgPrefix;
		String fieldName;
		String pageName;
		String value;
		String rawValue;
		String parsedValue;
		String instanceString;
		String constantValue;
		int instance;
		String tempType;
		String[] keyValuePair = new String[2];
		String[] pageAndField = new String[2];
		String[] valueAndInstance = new String[2];
		boolean isTestData;
		boolean isPureData;
		
		//This method does NOT validate the test data (e.g., ensuring that values for fields of type CHECKBOX can be
		//  resolved to check or uncheck).  We don't do that because we cannot assume that every test data record
		//  will be used during the test.
		
		//Test data and verification data are put into separate lists, and the following parsing of the value
		//  property is applied:
		//
		//  The value property for verification data IS NOT parsed for constants (as the parameter of VERIFY_CONSTANT),
		//    references to other test data, or any other kinds of parameterized values that could be added to
		//    EISConstants.ParameterizedVerificationDataValueType in the future.  It is up to the field verification
		//    methods in the Page class to resolve the value property on a "just in time" basis.  (Note that we
		//    COULD parse for constants here, but it is cleaner for the Page verification methods that parse for
		//    other parameterized values to parse for all such values.)
		//  The value property for test data IS NOT parsed for constants (as the parameter of VALUE_CONSTANT).  It
		//    is up to the Page object to parse them after retrieving test data from the TestProperties object.
		//  The value property for both test and verification data is parsed for global object and date math
		//    tokens, by calls to Util.resolveGlobalConstantToken
		
		//Should probably change this to use for-each, but the type String[] makes that non-trivial
		Iterator<String[]> itr = testProperties.iterator();
		while (itr.hasNext()) {
			keyValuePair = itr.next();
			
			//pageAndField = keyValuePair[0].split("\\.", 2);
			pageAndField = keyValuePair[0].split("\\.");
			if (pageAndField.length != 2) {
				throw new TestDataException("The data element name '" + keyValuePair[0] + "' is not in the format '[pageName].[fieldName]'");
			}

			pageName = pageAndField[0];
			fieldName = pageAndField[1];
			
			//valueAndInstance = keyValuePair[1].split(EISConstants.PROPERTY_TYPE_DELIM, 2);
			valueAndInstance = keyValuePair[1].split(EISConstants.PROPERTY_TYPE_DELIM);
			value = valueAndInstance[0];

			tempType = "";
			
			//Determine whether this is test data, or verification data
    		if (value.matches(regexTerm)) {
    			//We now have text that matches the regex term, but that text is not necessarily a parm type
    			//  and a delimited value for verification or data entry; it could be part of a "pure value"
    			//  that just happens to match the regex.  We'll grab the text to the left of the parms start
    			//  delimiter, and see if it is of the type EISConstants.ParameterizedVerificationDataValueType
				tempType = Util.left(value, value.indexOf(EISConstants.PARAMETERS_START_DELIM)).trim().toUpperCase();
				
    			try {
    				EISConstants.ParameterizedVerificationDataValueType.valueOf(tempType);
    				
    				//It's verification data, not test data, and we don't care whether it's pure
    				isTestData = false;
    				isPureData = false;
    			} catch (IllegalArgumentException e) {
    				//It's test data, not verification data.  But we still don't know if it's pure
    				//  test data - the value matches the regex term, but it could still be a
    				//  pure value.  We'll determine that below
    				isTestData = true;
    				isPureData = false;
    			}
    		} else {
    			//It's test data, and it's pure
    			isTestData = true;
    			isPureData = true;
    		}
			
    		//If an instance property was not specified, use the default of 1
			if (valueAndInstance.length != 2) {
				instance = 1;
			} else {
				//The instance property was specified; grab it 
				instanceString = valueAndInstance[1];
				
				//Used when throwing exceptions
				msgPrefix  = "The 'instance' property of the data element '" + pageName + "." + fieldName + "' ('" + instanceString + "') ";
				
				//See if the instance property resolves to an integer
				try {
					//The instance property does resolve to an integer
					instance = Integer.parseInt(instanceString);
					
					if (instance < 1) {
						throw new TestDataException(msgPrefix + "was specified as an integer, but the value of the integer (" + instance + ") is less than 1");
						//DEBUG
						//Util.printDebug(msgPrefix + "was specified as an integer, but the value of the integer (" + instance + ") is less than 1");
					}

					//We won't add it to instanceMap, because it isn't a string
				} catch (NumberFormatException nfe1) {
					//The instance property is a string; use it to find a constant in the test-scope store
					instanceString = instanceString.toUpperCase();
					constantValue = getConstant(instanceString);
					
					if (!constantValue.isEmpty()) {
						//The constant was found; see if its value resolves to an integer
						try {
							//The constant's value does resolve to an integer
							instance = Integer.parseInt(constantValue);
							
							if (instance < 1) {
								throw new TestDataException(msgPrefix + "references a constant in the test-scope constant store, but the integer value of that constant (" + instance + ") is less than 1");
								//DEBUG
								//Util.printDebug(msgPrefix + "references a constant in the test-scope constant store, but the integer value of that constant (" + instance + ") is less than 1");
							}

							//Its key in instanceMap will be the name of the constant
							instanceMap.put(instanceString, instance);
						} catch (NumberFormatException nfe2) {
							//The constant's value does not resolve to an integer; throw an exception
							throw new TestDataException(msgPrefix + "references a constant in the test-scope constant store, but the value of that constant ('" + constantValue + "') is not an integer");
							//DEBUG
							//Util.printDebug(msgPrefix + "references a constant in the test-scope constant store, but the value of that constant ('" + constantValue + "') is not an integer");
						}
					} else {
						//The constant was not found; throw an exception
						throw new TestDataException(msgPrefix + "was specified as a string ('" + instanceString + "') that does not reference a constant in the test-scope constant store");
						//DEBUG
						//Util.printDebug(msgPrefix + "was specified as a string ('" + instanceString + "') that does not reference a constant in the test-scope constant store");
					}
				}
			}
			
			if (isTestData) {
				//It's test data, but we don't yet know whether it is pure; let's find out
				if (!isPureData) {
	    			try {
	    				EISConstants.ParameterizedTestDataValueType.valueOf(tempType);
	    				//It's not pure test data
	    			} catch (IllegalArgumentException e) {
	    				//It's pure test data	    				
	    				isPureData = true;
	    			}
				}
				
				//If it's not pure, check for the existence of the EISConstants.ParameterizedTestDataValueType
				//  element VALUE, which is provided in case the test data writer forgets that a pure value
				//  can be specified as is (see comments at EISConstants.ParameterizedTestDataValueType)
				if (!isPureData) {
    				if (EISConstants.ParameterizedTestDataValueType.VALUE.toString().equalsIgnoreCase(tempType)) {
    					//Replace the value property with just the value inside the delims
    					value = Util.getField(value, EISConstants.PARAMETERS_START_DELIM, EISConstants.PARAMETERS_END_DELIM);

    					//Now it's pure test data
		    			isPureData = true;
    				}
				}
				
				//Resolve possible global object or date math token
				if (isPureData) {
					value = Util.resolveConstantToken(value);
				} else {
					rawValue = Util.getField(value, EISConstants.PARAMETERS_START_DELIM, EISConstants.PARAMETERS_END_DELIM);
					parsedValue = Util.resolveConstantToken(rawValue);
					value = value.replace(rawValue, parsedValue);
				}

    			testData.add(this.new TestData(pageName, (new FieldData(fieldName, value)), instance));
			} else {
				//Resolve possible global object or date math token
				rawValue = Util.getField(value, EISConstants.PARAMETERS_START_DELIM, EISConstants.PARAMETERS_END_DELIM);
				parsedValue = Util.resolveConstantToken(rawValue);
				value = value.replace(rawValue, parsedValue);

    			verificationData.add(this.new TestData(pageName, (new FieldData(fieldName, value)), instance));
			}
		}
	}

	public int getInstanceNumber(String instanceName) {
		String instanceNameUpper = instanceName.toUpperCase();
		int instanceNumber = 0;
		
		if (instanceName.equals("1")) {
			instanceNumber = 1;
		} else {
			if (instanceMap.containsKey(instanceNameUpper)) {
				instanceNumber = instanceMap.get(instanceNameUpper);
			} else {
				Util.printWarning("The instance name '" + instanceNameUpper + "' was not found in the list of instance names; returning 0");
			}
		}
		
		return instanceNumber;
	}

	protected List<FieldDataInstance_> getPageTestData(String pageName) {
		return doGetFieldDataInstanceList(pageName, testData);
	}
	
	public List<FieldDataInstance_> getPageVerificationData(String pageName) {
		return doGetFieldDataInstanceList(pageName, verificationData);
	}

	private List<FieldDataInstance_> doGetFieldDataInstanceList(String pageName, List<TestData> testDataList) {
		List<FieldDataInstance_> newFieldDataInstanceList = new ArrayList<FieldDataInstance_>();

		for (TestData td : testDataList) {
			if (td.pageName.compareToIgnoreCase(pageName) == 0) {
				//pageTestData.add(new FieldDataInstance((FieldData) td.fieldData.clone(), td.instance));
				//Using the FieldData copy constructor instead of FieldData.clone
				newFieldDataInstanceList.add(new FieldDataInstance(new FieldData(td.fieldData), td.instance));
			}
		}

		return newFieldDataInstanceList;
	}
	
	protected Properties getPageConstants(String pageName) {
		Properties pageConstantsProperties = new Properties();
		String previousValue;
		String key;

		for (TestData td : pageConstants) {
			if (td.pageName.compareToIgnoreCase(pageName) == 0) {
				key = td.fieldData.getFieldName();
				
				//We are checking for duplicates here, because we originally loaded constants from the test
				//  properties file into a List<TestData>, which includes page names along with the field
				//  data.  Because a constant name can be used in more than one page, it would be a hassle
				//  to check for duplicates there.
				previousValue = pageConstantsProperties.getProperty(key, null);

				if (previousValue != null) {
					Util.printWarning("The constant '" + key + "' was defined more than once for the Page '" + pageName + "'; all but the first instance will be discarded");
				} else {
					pageConstantsProperties.setProperty(key, td.fieldData.getValue());
				}
			}
		}

		return pageConstantsProperties;
	}
}
