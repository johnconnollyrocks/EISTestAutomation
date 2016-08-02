package common;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Constants and enumerated types common to all EIS applications.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class EISConstants {
	/**
	 * References to EIS applications, typically used for performing application-specific actions in
	 * application-independent levels of the framework.
	 */
	public static enum App {
		//SALES,
		LEAD_MANAGEMENT,	//LM
		ADSK_SALES,			//MJA, ePartner
		ADSK_CALL_CENTER,	//SS
		UCM_CONSOLE			//SS
	}

	/**
	 * Field types for use in declaring Field objects.
	 * @see Field
	 */
	protected static enum FieldType {
		//The READ_ONLY type is for fields that are used for verification only, not data entry.
		//  Fields of that type will be skipped by populate methods

		//Selenium provides direct support for radio buttons, but not radio lists
		//  (let's wait to implement radio lists until we have to)
		TEXT, PICKLIST, MULTISELECT, BUTTON, LINK, CHECKBOX, DATE, RADIOBUTTON, LOOKUP, READ_ONLY, READ_ONLY_DATE, HIDDENTEXT,HIDDENTEXT_2, HOVER
	}

	/**
	 * Test result types for use in test properties file metadata.
	 * @see TestProperties
	 */
	protected static enum TestResultType {
		/**
		 * The default test type - a failed assertion fails the test.
		 */
		POSITIVE,
		/**
		 * Allows running a simple test (one that makes only one assertion) as a negative test without changing the code.
		 */
		NEGATIVE
	}

	/**
	 * 'Requiredness' level types for use in declaring Field objects.
	 * @see Field
	 */
	protected static enum RequirednessLevel {
		/**
		 * [not implemented]
		 */
		REQUIRED,
		/**
		 * [not implemented]
		 */
		OPTIONAL,
		/**
		 * Specifies that a Field may not be present on a form, but that if present it is a required field
		 */
		VARIABLE
	}
	
	//Used as tokens in the locator property of the Field class to specify that instead of a
	//  standard Selenium locator, a locator tailored to one of several different types of
	//  tables will be used.  For example:
	//    1) A locator like this:
	//         INFO_PANEL_LOOKUP[Contract Information&&Contract Start Date]
	//       is used in Page.getInfoPanelCell to access panels that display info
	//       in a columnar format on many SFDC pages
	//    2) A locator like this:
	//         TABLE_CELL_LOOKUP[subscriptionProductsTable&&8]
	//       is used in Page.getTableCell to access tables other than info panels and related lists
	//  (see the comments at Page.getInfoPanelCell and Page.getTableCell for more info)
	/**
	 * Special locator types for use in declaring some Field objects (tables, related lists, and info panels).
	 * @see Field
	 */
	protected static enum ParameterizedLocatorType {
		/**
		 * For use with regular tables.
		 */
		TABLE_CELL_LOOKUP,
		/**
		 * For use with related lists.
		 */
		RELATED_LIST_CELL_LOOKUP,
		/**
		 * For use with 'info tables' - alternating columns of labels and read only information.
		 */
		INFO_PANEL_LOOKUP  
	}
	
	//See ResultsVerification.doc for usage
	/**
	 * Verification types for use in declaring FieldData objects.
	 * @see FieldData
	 */
	protected static enum ParameterizedVerificationDataValueType {
		//FieldData (in test data properties files) is identified as being verification data (as opposed to test
		//  data) by the presence of one of these elements in the value property.  When one of these elements
		//  is specified in the value property, it is usually followed by a delimited parameter.  (The delimiters
		//  are specified in the PARAMETERS_START_DELIM and PARAMETERS_END_DELIM constants in this class.)  That
		//  parameter (either a literal value or the name of a page- or test-scope constant) is resolved on a "just
		//  in time" basis in the verification methods in the Page class.  Allowable parameters are:

		/**
		 * For verifying a field against an expected value.
		 */
		VERIFY,								//parameter: a value, or nothing (in the latter case, it is expected that the call to the
											//  verification method will pass the expected value)

		/**
		 * For verifying a field against an expected value.
		 */
		VERIFY_VALUE,						//parameter: a value, or nothing (in the latter case, it is expected that the call to the
											//  verification method will pass the expected value)
		
		/**
		 * For verifying a field against a set of expected values.
		 */
		VERIFY_VALUES,						//parameter: a delimited (using PARAMETER_DELIM) set of values

		/**
		 * For verifying a field against a set of expected values.
		 */
		VERIFY_VALUE_SET,					//parameter: a delimited (using PARAMETER_DELIM) set of values 

		/**
		 * For verifying a field against the value of a constant in the test properties file.
		 */
		VERIFY_CONSTANT,					//parameter: the name of a page- or test-scope constant
		
		/**
		 * For verifying that a field is null (empty) (applies to fields of TEXT and DATE FieldType only).
		 */
		VERIFY_NULL,					//parameter: none

		/**
		 * For verifying that a field is not null (not empty) (applies to fields of TEXT and DATE FieldType only).
		 */
		VERIFY_NOT_NULL,					//parameter: none
		
		/**
		 * For verifying that a field exists.
		 */
		VERIFY_EXISTS,						//parameter: none

		/**
		 * For verifying that a field does not exist.
		 */
		VERIFY_NOT_EXISTS,					//parameter: none
				
		/**
		 * For verifying that a field is visible.
		 */
		VERIFY_VISIBLE,						//parameter: none

		/**
		 * For verifying that a field is not visible.
		 */
		VERIFY_NOT_VISIBLE					//parameter: none

		//VERIFY_TEST_DATA					//not implemented
	}

	/**
	 * Verification categories for use by page-scope verification methods that verify FieldData
	 * objects using closely related verification types.
	 * @see ParameterizedVerificationDataValueType
	 * @see Page#verifyAllExistenceInstance(int instance)
	 */
	protected static enum PageVerificationCategoryType {
		ALL,

		/**
		 * For verifying all fields for which an expected value (or set of values) is provided or implied.
		 * @see ParameterizedVerificationDataValueType#VERIFY
		 * @see ParameterizedVerificationDataValueType#VERIFY_VALUE
		 * @see ParameterizedVerificationDataValueType#VERIFY_VALUES
		 * @see ParameterizedVerificationDataValueType#VERIFY_VALUE_SET
		 * @see ParameterizedVerificationDataValueType#VERIFY_CONSTANT
		 */
		VALUES_ONLY,

		/**
		 * For verifying existence - all fields that are expected to exist AND all fields that are expected not to exist.
		 * @see ParameterizedVerificationDataValueType#VERIFY_EXISTS
		 * @see ParameterizedVerificationDataValueType#VERIFY_NOT_EXISTS
		 */
		EXISTENCE_ONLY,

		/**
		 * For verifying all fields that are expected to exist.
		 * @see ParameterizedVerificationDataValueType#VERIFY_EXISTS
		 */
		EXISTS_ONLY,

		/**
		 * For verifying all fields that are expected not to exist.
		 * @see ParameterizedVerificationDataValueType#VERIFY_NOT_EXISTS
		 */
		NOT_EXISTS_ONLY,

		/**
		 * For verifying visibility - all fields that are expected to be visible AND all fields that are expected not to be visible.
		 * @see ParameterizedVerificationDataValueType#VERIFY_VISIBLE
		 * @see ParameterizedVerificationDataValueType#VERIFY_NOT_VISIBLE
		 */
		VISIBILITY_ONLY,

		/**
		 * For verifying all fields that are expected to be visible.
		 * @see ParameterizedVerificationDataValueType#VERIFY_VISIBLE
		 */
		VISIBLE_ONLY,

		/**
		 * For verifying all fields that are expected not to be visible.
		 * @see ParameterizedVerificationDataValueType#VERIFY_NOT_VISIBLE
		 */
		NOT_VISIBLE_ONLY,

		/**
		 * For verifying 'nullness' - all fields that are expected to be null AND all fields that are expected not to be null.
		 * @see ParameterizedVerificationDataValueType#VERIFY_NULL
		 * @see ParameterizedVerificationDataValueType#VERIFY_NOT_NULL
		 */
		NULLNESS_ONLY,

		/**
		 * For verifying all fields that are expected to be null.
		 * @see ParameterizedVerificationDataValueType#VERIFY_NULL
		 */
		NULL_ONLY,

		/**
		 * For verifying all fields that are expected not to be null.
		 * @see ParameterizedVerificationDataValueType#VERIFY_NOT_NULL
		 */
		NOT_NULL_ONLY
	}

	//When VERIFY_EXISTS or VERIFY_NOT_EXISTS is specified in the value property
	//  in a verification data record, the method that parses that property (Page.getVerificationDataValue) will return
	//  a string representation of one of these to its caller
	/**
	 * Values returned by methods that verify the existence or non-existence of a field
	 */
	protected static enum FieldExistenceCheckType {
		YES, NO
	}
	
	//When VERIFY_VISIBLE or VERIFY_NOT_VISIBLE is specified in the value property
	//  in a verification data record, the method that parses that property (Page.getVerificationDataValue) will return
	//  a string representation of one of these to its caller
	/**
	 * Values returned by methods that verify the visibility or non-visibility of a field
	 */
	protected static enum FieldVisibilityCheckType {
		YES, NO
	}
	
	//When VERIFY_NULL or VERIFY_NOT_NULL is specified in the value property
	//  in a verification data record, the method that parses that property (Page.getVerificationDataValue) will return
	//  a string representation of one of these to its caller
	/**
	 * Values returned by methods that verify the nullness or non-nullness of a field
	 */
	protected static enum FieldNullnessCheckType {
		YES, NO
	}
		
	/**
	 * Test data types for use in declaring FieldData objects.&nbsp;They expect a delimited parameter representing the test data value.
	 * @see FieldData
	 */
	protected static enum ParameterizedTestDataValueType {
		//Test data may contain one of these elements in the value property.  When one of these elements
		//  is specified in the value property, it is followed by a delimited parameter.  That parameter (either
		//  a literal value or the name of a constant) is resolved immediately after the test data is read from the
		//  properties file inTestProperties (in the case of VALUE), or immediately after the constants are read
		//  into the Page object (in the case of VALUE_CONSTANT)
		// 
		//VALUE is provided in case the test data writer forgets that a pure value can be specified
		//  as is.  The following test data specifications are equivalent:
		//    myPage.myField	=Gooey mess
		//    myPage.myField	=VALUE[Gooey mess]
		
		/**
		 * Specifies that the parameter (if provided) is a literal value or a 'decorated' string.&nbsp;If no
		 * parameter is provided in the test properties file, it is expected that the test code will provide one.
		 */
		VALUE,
		
		/**
		 * Specifies that the parameter is the name of a constant in the test properties file.
		 */
		VALUE_CONSTANT
	}

	/**
	 * Tokens specified in the value property when declaring FieldData objects.&nbsp;They allow specifying a
	 * positive or negative offset to a current date.
	 */
	protected static enum DateOffsetType {
		//TODO  Add weeks?
		D, DAY, DAYS,
		M, MONTH, MONTHS,
		Y, YEAR, YEARS
	}
	
	/**
	 * Tokens for use in dismissing alerts.
	 */
	protected static enum AlertResponseType {
		OK, CANCEL, IAgree
	}
	
	//TODO Make this an enum (DEV, STG, PROD)
	protected static final String DEFAULT_ENVIRONMENT						= "STG";

	//SFDC object constants; the OBJECT_NAME_PREFIX_xxx and OBJECT_NAME_FIELD_NAME_xxx constants
    //  are typically used for building object names that need to be generated at run time.  The
	//  OBJECT_TYPE_xxx constants are used primarily in messages
	public static final String OBJECT_TYPE_OPPTY							= "oppty";
	public static final String OBJECT_NAME_PREFIX_OPPTY						= "AutoOppty_";
	public static final String OBJECT_NAME_FIELD_NAME_OPPTY					= "opptyName";

	public static final String OBJECT_TYPE_CASE								= "case";
	public static final String OBJECT_NAME_PREFIX_CASE						= "AutoCase_";
	public static final String OBJECT_NAME_FIELD_NAME_CASE					= "caseName";

	public static final String OBJECT_TYPE_ACCT								= "account";
	public static final String OBJECT_NAME_PREFIX_ACCT						= "AutoAcct_";
	public static final String OBJECT_NAME_FIELD_NAME_ACCT					= "accountName";

	public static final String OBJECT_TYPE_LEAD								= "lead";
	public static final String OBJECT_NAME_PREFIX_LEAD						= "AutoLead_";
	public static final String OBJECT_NAME_FIELD_NAME_LEAD					= "leadName";

	public static final String OBJECT_TYPE_PARTNER							= "partner";
	public static final String OBJECT_NAME_PREFIX_PARTNER					= "AutoPartner_";
	public static final String OBJECT_NAME_FIELD_NAME_PARTNER				= "partnerName";

	public static final String OBJECT_TYPE_DD								= "digital delivery object";
	public static final String OBJECT_NAME_PREFIX_DD						= "AutoDD_";

	public static final String OBJECT_TYPE_ORG								= "org";
	public static final String OBJECT_NAME_PREFIX_ORG						= "AutoOrg_";
	public static final String OBJECT_NAME_PREFIX_ORG_USER					= "AutoUser_";

	public static final String OBJECT_TYPE_CONTACT							= "contact";
	public static final String OBJECT_TYPE_QUOTE							= "quote";
	public static final String OBJECT_TYPE_ORDER							= "order";
	public static final String OBJECT_TYPE_CONTRACT							= "contract";
	public static final String OBJECT_TYPE_PRODUCT							= "product";
	public static final String OBJECT_TYPE_DAR								= "discount approval request";

	//Used as a token in the value property of test data to specify that the
	//  test data is actually a constant (the parameter is the actual value
	//  of the constant).  For example:
	//    PageCreateLead.PRODUCT = CONSTANT[Envision]
	//  means that we are creating a constant named PRODUCT in the
	//    PageCreateLead Page object, and the constant has the value "Envision"
	//  it DOES NOT mean that during data entry for a field named PRODUCT
	//    on the PageCreateLead Page object we will look for a constant named Envision
	//    and use its value
	//See ResultsVerification.doc for usage
	public static final String CONSTANT_TOKEN								= "CONSTANT";
	public static final String CONSTANT_SET_TOKEN							= "CONSTANT_SET";
	
	//Used as a token in the left-hand side of a test data, verification data, or constant element in
	//  test properties to indicate that its value should override any matching elements.  This
	//  token is intended to be used as an alternative to nesting test properties files.  It is used
	//  as follows:
	//    PageCreateLead.product			= Envision
	//    PageCreateLead.product@override	= Hello Kitty Starter Kit
	//When a test properties file containing both of those elements is loaded, the override value
	//  will be stored, and the other discarded.  (Note that the token can appear anywhere between
	//  the field name and the = sign, and is case-insensitive.)
	public static final String OVERRIDE_TOKEN								= "@OVERRIDE";
	
	//The following two values are used in Util.getTestRootDir() to determine TEST_ROOT_PATH
	//The name of the JVM property (typically set in an Ant script) that holds the location of the EISTestAutomation "home" directory
	public static final String TEST_HOME_JVM_PROPERTY_NAME					= "EISTestAutoHome";
	//The name of the system environment variable that holds the location of the EISTestAutomation "home" directory
	public static final String TEST_HOME_ENV_VAR_NAME						= "EIS_TEST_AUTO_HOME";

	//Classes that need to build paths should build them off of TEST_ROOT_PATH
	//DO NOT remove the separators from the ends of these paths and directory names!
	public static final String TEST_RELATIVE_PATH 							= "src" + File.separator;

	//public static final String TEST_ROOT_PATH 							= System.getProperties().getProperty(JVM_TEST_HOME_PROPERTY_NAME) + File.separator + TEST_RELATIVE_PATH;
	public static final String TEST_ROOT_PATH 								= Util.getTestRootDir() + File.separator + TEST_RELATIVE_PATH ;

	public static final String TEST_DATA_DIR 								= "testdata" + File.separator;
	public static final String RESOURCE_DIR 								= "resource" + File.separator;

	//The path to the 'base' directory of the framework
	public static final String TEST_BASE_PATH 								= TEST_ROOT_PATH + "common" + File.separator;

	public static final String PAGE_MANIFEST_PROPERTIES_FILE				= "PageManifest.properties";
	public static final String WINDOW_MANIFEST_PROPERTIES_FILE				= "WindowManifest.properties";
	public static final String TEST_MANIFEST_PROPERTIES_FILE				= "TestManifest.properties";
	
	//Date and time constants
	//TODO  Resolve which locale we are going to use
	public static final String DEFAULT_DATE_FORMAT							= "M/d/yyyy";
	public static final String DEFAULT_DATE_FORMAT_APAC						= "dd/MM/yyyy";
	public static final String DEFAULT_DATE_FORMAT_EMEA						= "d.M.yyyy";

	public static final String DEFAULT_TIMESTAMP_FORMAT						= "yyyy_MM_dd_HH_mm_ss";

	//Used for formatting currency scraped from the GUI for verification.  Can be overridden in test
	//  properties if the appropriate constant is defined
	public static final int DEFAULT_NUM_CURRENCY_DECIMAL_DIGITS				= 2;

	//The AutoIT process we call to find a link by its text and click it (see EISTestBase.clickLinkInIE)
	public static final String CLICK_LINK_IN_IE_PROCESS						= "ClickLinkInIE.exe";
	
	public static final String INVOKE_IE_DRIVER								= "IEDriverServer.exe";
	
	//public static final String ACCOUNT_LOOKUP_POPUP_URL						= "https://autodesk.adsksfstg.cs12.force.com/partner/NGCRM_AccountSearchPage?isOpp=true&lkfm=editPage&lknm=opp4&lktp=001"; 
	
	//Text of some of the links we look for in calls to EISTestBase.clickLinkInIE
	//  NOTE that these strings CANNOT be defined as regular expressions
	//This is used to work around the web site certificate issue in ePartner
	static final String CONTINUE_TO_WEBSITE_LINK_TEXT						= "\"Continue to this website (not recommended).\"";

	//Note that the AutoIT process we call (ClickLinkInIE.exe) does not wait for the text to appear, so this value
	//  is used in EISTestBase.execProcess only to validate the overall time taken by the process (please make it
	//  at least 20 seconds)
	static final int CLICK_LINK_IN_IE_DEFAULT_PROCESS_WAIT					= 20;

	//The AutoIT process we call to dismiss modal dialogs (see EISTestBase.dismissModalDialog)
	public static final String DISMISS_MODAL_DIALOG_PROCESS					= "DismissModalDialog.exe";
	
	//The number of seconds DismissModalDialog.exe will wait for the dialog to appear, and to disappear after a button is clicked
	static final int DISMISS_MODAL_DIALOG_DEFAULT_PROCESS_WAIT				= 2;
	
	//A multiplier used in determining the total amount of time EISTestBase.execProcess will wait for the process to return when called
	//  by Page.dismissModalDialog.  The total wait time is calculated as:
	//    DISMISS_MODAL_DIALOG_DEFAULT_PROCESS_WAIT * DISMISS_MODAL_DIALOG_DEFAULT_PROCESS_WAIT_MULTIPLIER
	//  Be sure that this value is at least 2, because DismissModalDialog.exe will wait up to DISMISS_MODAL_DIALOG_DEFAULT_PROCESS_WAIT
	//  seconds both for the dialog to appear, and to disappear after a button is clicked.  Therefore this multiplier is less than 2,
	//  we risk having EISTestBase.execProcess terminate DismissModalDialog.exe before it is finished
	static final int DISMISS_MODAL_DIALOG_DEFAULT_PROCESS_WAIT_MULTIPLIER	= 2;

	//static final String[] DISMISS_MODAL_DIALOG_ARGS_OK					= {"\"The page at.* says\"", "OK"};
	//static final String[] DISMISS_MODAL_DIALOG_ARGS_CANCEL				= {"\"The page at.* says\"", "Cancel"};
	
	//NOTE that we may need to fiddle with this if we encounter different window titles.
	//NOTE that window title text can be specified here as regular expressions!!!
	static final String[] DISMISS_MODAL_DIALOG_ARGS_OK						= {"\"Message from webpage\"", "OK"};
	static final String[] DISMISS_MODAL_DIALOG_ARGS_CANCEL					= {"\"Message from webpage\"", "Cancel"};
	//********************************************************************************************************************************
	//**** Constants used for referencing test properties elements *******************************************************************
	//Following are constants that reference test properties constant names.  (See LM.LMConstants.java for usage)
	
	//The name of the field in common.resource.Page_AddEditProducts that we are referencing in test properties
	//  files to store the name of a product to add or edit.  See common.Oppty.addProducts and editProducts for
	//  usage.  Also used in ommon.resource.PageEditDiscountApprovalRequest
	public static final String PRODUCT_NAME_FIELD_NAME 								= "prodLine";

	//When a constant with the name specified by this variable is defined in test properties, it is used instead of the value defined
	//  in DEFAULT_NUM_CURRENCY_DECIMAL_DIGITS
	public static final String NUM_CURRENCY_DECIMAL_DIGITS_OVERRIDE_CONSTANT_NAME	= "NUM_CURRENCY_DECIMAL_DIGITS_OVERRIDE";
	
	//When a constant with the name specified by this variable is defined in test properties, its value is used as the default key by
	//  EISTestBase.decrypt
	public static final String DECRYPTION_KEY_CONSTANT_NAME							= "DECRYPTION_KEY";
	
	//When a CONSTANT_SET with the name specified by this variable is defined in test properties, its value is used as the default set
	//  of instances in Oppty.addProducts
	public static final String OPPTY_ADD_PRODUCTS_CONSTANT_SET_NAME					= "ADD_PRODUCTS";
	
	//When a CONSTANT_SET with the name specified by this variable is defined in test properties, its value is used as the default set
	//  of instances in Oppty.editProducts
	public static final String OPPTY_EDIT_PRODUCTS_CONSTANT_SET_NAME				= "EDIT_PRODUCTS";
	
	//When a CONSTANT_SET with the name specified by this variable is defined in test properties, its value is used as the default set
	//  of instances in DiscountApprovalRequest.editProducts
	public static final String DAR_EDIT_PRODUCTS_CONSTANT_SET_NAME					= "EDIT_DAR_PRODUCTS";
    
	//When a CONSTANT with the name specified by this variable is defined in test properties, it is expected to contain a 
    //  language code (see the LanguageCodeType enumeration below for values)
    public static final String LANGUAGE_CODE_ENUM_CONSTANT_NAME 					= "LANGUAGE_CODE_ENUM";
    
	//When a CONSTANT with the name specified by this variable is defined in test properties, it is expected to contain a 
    //  country code (see the CountryCodeType enumeration below for values)
    public static final String COUNTRY_CODE_ENUM_CONSTANT_NAME 						= "COUNTRY_CODE_ENUM";

    public static final String OPPTY_EDIT_QUOTE_PRODUCTS_CONSTANT_SET_NAME			= "VERIFY_QUOTE_PRODUCTS";
    
    public static final String QUOTE_EDIT_PRODUCTS_CONSTANT_SET_NAME				= "EDIT_QUOTE_PRODUCTS";
	//********************************************************************************************************************************
	//********************************************************************************************************************************

	//Used by methods that parse test data for input (such as Util.formatDate) as a return value to specify that the data is invalid
	public static final String INVALID_INPUT								= "INVALID INPUT";
	
	//Used for formatting numbers other than currency scraped from the GUI for verification
	public static final int DEFAULT_NUM_NUMBER_DECIMAL_DIGITS				= 2;
	public static final String DEFAULT_NUMBER_FORMAT						= "%,." + Integer.toString(DEFAULT_NUM_NUMBER_DECIMAL_DIGITS) + "f";
	
	//Can be referenced in test data
	public static final String TODAY 										= Util.getCurrentDate(DEFAULT_DATE_FORMAT);
	public static final String YESTERDAY 									= Util.getYesterday(DEFAULT_DATE_FORMAT);
	public static final String TOMORROW 									= Util.getTomorrow(DEFAULT_DATE_FORMAT);
	public static final String MONTH_TERM_FROM_TODAY 						= Util.getDateMonthTermFromToday(DEFAULT_DATE_FORMAT);
	public static final String YEAR_TERM_FROM_TODAY							= Util.getDateYearTermFromToday(DEFAULT_DATE_FORMAT);
	public static final String FIRST_DAY_THIS_MONTH 						= Util.getDateFirstDayThisMonth(DEFAULT_DATE_FORMAT);
	public static final String LAST_DAY_THIS_MONTH 							= Util.getDateLastDayThisMonth(DEFAULT_DATE_FORMAT);
	public static final String FIRST_DAY_NEXT_MONTH 						= Util.getDateFirstDayNextMonth(DEFAULT_DATE_FORMAT);
	public static final String FIRST_DAY_THIS_YEAR 							= Util.getDateFirstDayThisYear(DEFAULT_DATE_FORMAT);
	public static final String LAST_DAY_THIS_YEAR							= Util.getDateLastDayThisYear(DEFAULT_DATE_FORMAT);
	public static final String FIRST_DAY_NEXT_YEAR							= Util.getDateFirstDayNextYear(DEFAULT_DATE_FORMAT);

	public static final String TODAY1 										= Util.getCurrentDate(DEFAULT_DATE_FORMAT_APAC);
	public static final String YESTERDAY1 									= Util.getYesterday(DEFAULT_DATE_FORMAT_APAC);
	public static final String TOMORROW1 									= Util.getTomorrow(DEFAULT_DATE_FORMAT_APAC);
	public static final String MONTH_TERM_FROM_TODAY1						= Util.getDateMonthTermFromToday(DEFAULT_DATE_FORMAT_APAC);
	public static final String YEAR_TERM_FROM_TODAY1						= Util.getDateYearTermFromToday(DEFAULT_DATE_FORMAT_APAC);
	public static final String FIRST_DAY_THIS_MONTH1 						= Util.getDateFirstDayThisMonth(DEFAULT_DATE_FORMAT_APAC);
	public static final String LAST_DAY_THIS_MONTH1 						= Util.getDateLastDayThisMonth(DEFAULT_DATE_FORMAT_APAC);
	public static final String FIRST_DAY_NEXT_MONTH1 						= Util.getDateFirstDayNextMonth(DEFAULT_DATE_FORMAT_APAC);
	public static final String FIRST_DAY_THIS_YEAR1 							= Util.getDateFirstDayThisYear(DEFAULT_DATE_FORMAT_APAC);
	public static final String LAST_DAY_THIS_YEAR1							= Util.getDateLastDayThisYear(DEFAULT_DATE_FORMAT_APAC);
	public static final String FIRST_DAY_NEXT_YEAR1							= Util.getDateFirstDayNextYear(DEFAULT_DATE_FORMAT_APAC);



	public static final String TODAY2 										= Util.getCurrentDate(DEFAULT_DATE_FORMAT_EMEA);
	public static final String YESTERDAY2 									= Util.getYesterday(DEFAULT_DATE_FORMAT_EMEA);
	public static final String TOMORROW2 									= Util.getTomorrow(DEFAULT_DATE_FORMAT_EMEA);
	public static final String MONTH_TERM_FROM_TODAY2						= Util.getDateMonthTermFromToday(DEFAULT_DATE_FORMAT_EMEA);
	public static final String YEAR_TERM_FROM_TODAY2						= Util.getDateYearTermFromToday(DEFAULT_DATE_FORMAT_EMEA);
	public static final String FIRST_DAY_THIS_MONTH2 						= Util.getDateFirstDayThisMonth(DEFAULT_DATE_FORMAT_EMEA);
	public static final String LAST_DAY_THIS_MONTH2 						= Util.getDateLastDayThisMonth(DEFAULT_DATE_FORMAT_EMEA);
	public static final String FIRST_DAY_NEXT_MONTH2 						= Util.getDateFirstDayNextMonth(DEFAULT_DATE_FORMAT_EMEA);
	public static final String FIRST_DAY_THIS_YEAR2 						= Util.getDateFirstDayThisYear(DEFAULT_DATE_FORMAT_EMEA);
	public static final String LAST_DAY_THIS_YEAR2							= Util.getDateLastDayThisYear(DEFAULT_DATE_FORMAT_EMEA);
	public static final String FIRST_DAY_NEXT_YEAR2							= Util.getDateFirstDayNextYear(DEFAULT_DATE_FORMAT_EMEA);

	public static final String TIMESTAMP 									= Util.getTimestamp();

	//Used for nesting test (and utility) data files
	public static final String NESTED_TEST_DATA_FILE_TOKEN					= "include";

	
	//IMPORTANT NOTES ON DELIMITERS!!!
	//  1) NEVER use :: as ANY delimiter!  It is part of the xpath grammar, and we read xpath locators from properties files! 
	//
	//  2) AVOID using a regex meta-character as a delimiter whenever possible!  In the cases where it makes sense to do so,
	//     such as when you want to delimit an entire list of objects, be extremely careful with which delimiter you use,
	//     and provide explanatory comments at the declaration of the delimiter
	//
	//  3) BE VERY CAREFUL about using the same value - or part of the same value - for multiple delimiters!  Be especially
	//     careful when the string to be parsed contains a delimited list of values, each of which could be further delimited.
	//     For example, test and verification data are specified in the test properties file as (brackets added for clarity):
	//       [pageName.fieldName]  [propertyPairDelim]  [valueTerm]  [propertyDelimiter]  [instance]
	//     Some samples:
	//       PageCreateLead.product				= Envision
	//       PageCreateLead.product				= AutoCAD##2
	//       PageCreateAccount.firstActiveDate	= !+1!##2
	//     The first two samples are the norm, but things get interesting in the third sample.  The valueTerm is a relative
	//       date value used in date math, and as such needs to be delimited (or "flagged") to differentiate it from a literal
	//       value.  If # instead of ! had been used to delimit it, that delimiter would conflict with the ## being used as
	//       propertyDelimiter, thereby causing the parser to return the wrong results.  Furthermore, valueTerm itself can
	//       contain a delimited list of values, and each of those values can be delimited!  So be careful!
	//     These cautions apply to field and page metadata as well, especially in respect to Selenium object locators
	// 
	//  4) IT IS OKAY to re-use delimiters in non-conflicting contexts - and is sometimes encouraged.  If a delimiter
	//     is chosen to delimit one type of list, it can be used to delimit others as well - as long as the relevant
	//     contexts are not in conflict, i.e., using the same list delimiter for both levels in a "list of lists"
	//     construct would yield disappointing results!  But is in encouraged to re-use delimiters in contexts where
	//     the user might reasonably expect them to be the same.  HOWEVER, please do define the delimiter objects
	//     separately, so that changing the delimiter for one context does not change it for all contexts.  Normally
	//     that would be a good idea, but we can not be 100% sure that such a change might result in unintended
	//     consequences in one or more of the contexts
	//
	//  5) Some delimiters are actually flags, as they do not separate elements of a list, but instead mark a text element
	//     as "special."  Examples are OBJECT_REFERENCE_DELIM and RELATIVE_DATE_DELIM.  Both of these are used to flag test
	//     data, but have different meanings when parsed.  So be sure to use separate delimiters for them, and for other
	//     "special" text elements that may be defined in the future.
	//
	//  6) Most - but not quite all - of these issues will disappear once we convert all properties files over to XML

	
	//Used while parsing info in properties files (this is obviously a universal delimiter for name/value property pairs,
	//  and should not be changed)
	public static final String PROPERTY_PAIR_DELIM 							= "=";

	//BE SURE not to use the value of this constant (currently ##) to delimit anything else in test data or
	//  metadata!  It is used to delimit properties ONLY; any other use will result in the string being
	//  parsed improperly!!!!
	public static final String PROPERTY_TYPE_DELIM							= "##";
	
	//BE SURE not to use the value of this constant (currently @@) to delimit anything else in test data or
	//  metadata!  It is used to delimit multiple instances of a property ONLY; any other use will result
	//  in the string being parsed improperly!!!!
	public static final String PROPERTY_INSTANCE_DELIM 						= "@@";

	public static final String FIELD_DEPENDENCY_DELIM 						= "&&";
	public static final String FIELD_DEPENDENCY_GLOBAL_FLAG 				= "*";
	
	//Timeouts
	//Time to wait for a window to open or close
	//public static final int DEFAULT_WINDOW_WAIT_TIMEOUT					= 10000;
	//public static final int DEFAULT_WINDOW_WAIT_TIMEOUT					= 15000;
	public static final int DEFAULT_WINDOW_WAIT_TIMEOUT						= 60000;
//	public static final int DEFAULT_WINDOW_WAIT_TIMEOUT						= 80000;
	
	//Time to tell WebDriver to wait for pages to load.
	public static final int DEFAULT_PAGE_WAIT_TIMEOUT						= 20000;
	
	//Time to wait for a search.  Note that there are no methods that are specifically meant for
	//  performing searches.  This constant is for use in "...AndWait" method calls,
	//  as an override of the default timeout, which is typically DEFAULT_PAGE_WAIT_TIMEOUT)
	public static final int DEFAULT_SEARCH_TIMEOUT							= 45000;
	
	//Time to wait for an SFDC object to be created (JAB: we may consider putting this in SFDCObject class...)
	public static final int OBJECT_CREATE_WINDOW_WAIT_TIMEOUT				= 150000;
	
	//Interval to use when in a "refresh loop" (refreshing page until some condition is met) 
	public static final int DEFAULT_PAGE_REFRESH_INTERVAL					= 5000;
	
	//NOTE:  Predefined locators were deprecated when the framework was migrated to WebDriver;
	//  it generates all window handles dynamically
	//Used as a way of designating the main window of the application
	//public static final String DEFAULT_MAIN_WINDOW_LOCATOR					= "MAIN";

	//Delimiter used in test data to wrap a reference to a global object (i.e., an object name in this class),
	//  and the associated regex term used when parsing the test data
	public static final String OBJECT_REFERENCE_DELIM 						= "%";
	public static final String OBJECT_REFERENCE_REGEX_TERM					= OBJECT_REFERENCE_DELIM + ".*" + OBJECT_REFERENCE_DELIM;

	//Delimiter used in test data to wrap a reference to a "date math" construct, and the associated regex term
	//  used when parsing the test data
	public static final String DATE_OFFSET_DELIM 							= "!";
	public static final String DATE_OFFSET_REGEX_TERM						= DATE_OFFSET_DELIM + ".*" + DATE_OFFSET_DELIM;
	
	public static final String TOKEN_REGEX_TERM								= "(?i)TOKEN[0-9]{1,}";

	//Used for parsing Field locators for L10N (localization) tests
	public static final String L10N_TOKEN_REGEX_TERM						= "(?i)L10N_TOKEN[0-9]{1,}";

	//These are used to bracket the set of parameters associated with:
	//  ParameterizedLocatorType				(found in the 'locators' property in metadata)
	//  ParameterizedVerificationDataValueType	(found in the 'value' property in test or verification data)
	//  ParameterizedTestDataValueType			(found in the 'value' property in test or verification data)
	public static final String PARAMETERS_START_DELIM						= "[";
	public static final String PARAMETERS_END_DELIM							= "]";
	
	//If PARAMETERS_START_DELIM or PARAMETERS_END_DELIM is a regex meta-character, it must be escaped here
	public static final String PARAMETERS_REGEX_TERM						= "\\" + PARAMETERS_START_DELIM + ".*\\" + PARAMETERS_END_DELIM;
	//Used to delimit the actual parameters
	public static final String PARAMETER_DELIM								= "&&";
	
	//Used for delimiting values that have been concatenated into a string (e.g., the string returned by gui.getValueFromMultiSelect)
	public static final String VALUE_DELIM									= "&&";

	//Test data constants
	//These values can be specified in test data or verification data as values for check boxes and radio buttons
	//Be sure not to remove "true" from this list
	public static final String FIELD_CHECK_VALUES							= "check" + PROPERTY_INSTANCE_DELIM +
																			  "on" + PROPERTY_INSTANCE_DELIM +
																			  "1" + PROPERTY_INSTANCE_DELIM +
																			  "true" + PROPERTY_INSTANCE_DELIM +
																			  "yes" + PROPERTY_INSTANCE_DELIM +
																			  "set" + PROPERTY_INSTANCE_DELIM +
																			  "select";
	//Be sure not to remove "false" from this list
	public static final String FIELD_UNCHECK_VALUES							= "uncheck" + PROPERTY_INSTANCE_DELIM +
																			  "off" + PROPERTY_INSTANCE_DELIM +
																			  "0" + PROPERTY_INSTANCE_DELIM +
																			  "false" + PROPERTY_INSTANCE_DELIM +
																			  "no" + PROPERTY_INSTANCE_DELIM +
																			  "unset" + PROPERTY_INSTANCE_DELIM +
																			  "unselect" + PROPERTY_INSTANCE_DELIM +
																			  "deselect" + PROPERTY_INSTANCE_DELIM +
																			  "clear";

	//For commenting lines in test data and page metadata files (works only for commenting whole lines!
	public static final String COMMENT_TOKEN								= "#";

	//Email client (Hotmail) info
	//public static final String EMAIL_CLIENT_URL							= "http://login.live.com";
	public static final String EMAIL_CLIENT_URL								= "https://login.live.com/login.srf";
	public static final String EMAIL_CLIENT_USERNAME						= "sfdcDocusign@hotmail.com";
	public static final String EMAIL_CLIENT_PASSWORD						= "at123456";
	public static final String EMAIL_CLIENT_INBOX_URL						= "http://by153w.bay153.mail.live.com/?rru=inbox";
	public static final String EMAIL_CLIENT_EMAIL_ADDRESS					= EMAIL_CLIENT_USERNAME;
	
	//The default xpath to related lists. The token must be replaced with an upper-cased string representing the list header.  (NOTE that
	//  the string must be converted to upper-case in order for the comparison to be case-insensitive!)
	//NOTE the caution at the declaration of INFO_PANEL_HEADER_XPATH (below) against using xpath's translate function!!!
	public static final String RELATED_LIST_HEADER_TOKEN					= "HEADER_TOKEN";

	//Modified this to account for the fact that:
	//  1. Sometimes the node that contains the title text is not the direct descendant of the td with @class == pbTitle
	//     (I changed "td[normalize-space(@class)='pbTitle']/node()" to "td[normalize-space(@class)='pbTitle']//node()")
	//  2. Sometimes there is a record count in the title , e.g., "Contacts (25+)"
	//     (I changed it to normalize the text of the title, and then check that it begins with the token, instead of just
	//     that it contains the token)
	//public static final String RELATED_LIST_XPATH	= "//div[normalize-space(@class)='bRelatedList']/descendant::div[normalize-space(@class)='pbHeader']/descendant::td[normalize-space(@class)='pbTitle']/node()[translate(normalize-space(text()), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + RELATED_LIST_HEADER_TOKEN + "']/ancestor::div[normalize-space(@class)='pbHeader']/following-sibling::div[normalize-space(@class)='pbBody']/table[normalize-space(@class)='list']/tbody";
	public static final String RELATED_LIST_XPATH	= "//div[normalize-space(@class)='bRelatedList']/descendant::div[normalize-space(@class)='pbHeader']/descendant::td[normalize-space(@class)='pbTitle']//node()[starts-with(translate(normalize-space(text()), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + RELATED_LIST_HEADER_TOKEN + "')]/ancestor::div[normalize-space(@class)='pbHeader']/following-sibling::div[normalize-space(@class)='pbBody']/table[normalize-space(@class)='list']/tbody";

	//The default xpath to the "show more" link in related lists (will be appended to RELATED_LIST_XPATH when expanding related lists)
	//Modified this to:
	//  1. Target the first ancestor div above the table that contains the list
	//     (I changed "/ancestor::div/" to "/ancestor::div[1]/")
	//  2. Account for the fact that sometimes the div with @class == pShowMore is not a direct descendant of that ancestor div
	//     (I changed "/ancestor::div/div" to "/ancestor::div[1]//div")
	//  3. Account for the fact that the final div's class attribute may be pShowMore or pSearchShowMore
	//     (I changed "div[normalize-space(@class)='pShowMore']" to "div[contains(@class,'ShowMore')]")
	//  4. Account for the fact that the text of the link can vary.  In some places it is something like "Show 4 more" and
	//     in other places it is "Show More"
	//     (I changed "div[normalize-space(@class)='pShowMore']" to "div[contains(@class,'ShowMore')]")
	//public static final String RELATED_LIST_SHOW_MORE_LINK_XPATH	= "/ancestor::div/div[normalize-space(@class)='pShowMore']//a[contains(text(), 'Show') and contains(text(), 'more')]";
	public static final String RELATED_LIST_SHOW_MORE_LINK_XPATH	= "/ancestor::div[1]//div[contains(@class,'ShowMore')]//a[contains(text(), 'Show') and (contains(text(), 'more') or contains(text(), 'More'))]";

	//The default xpaths to info panel headers and their associated tables.  The token must be replaced with an upper-cased string representing
	//  the table header.  (NOTE that the string must be converted to upper-case in order for the comparison to be case-insensitive!)
	//  Page.getInfoPanelCell uses these xpaths as follows:
	//    A check is made that a GUI element with the INFO_PANEL_HEADER_XPATH (after token substitution) exists.  If so, INFO_PANEL_XPATH is appended
	//    to INFO_PANEL_HEADER_XPATH and a call is made to getTableAsListOfList
	//NOTE that it is crucial that the locator that is passed to getTableAsListOfList refer to only one node in the document!!!
	//  If not, it will throw an exception.  After MUCH time and testing, the following versions of INFO_PANEL_HEADER_XPATH and INFO_PANEL_XPATH have
	//  been deemed acceptable in that regard.  The commented versions that follow, in any combination with each other, DO NOT WORK in certain conditions.
	//  While it appears that they should work - and in normal circumstances they do - they fail on occasion.  I suspect that the reason is (of course!)
	//  timing related: the page load is in an unstable enough state that the xpath gets resolved incorrectly.  That said, there are additional
	//  safeguards in Page.getInfoPanelCell
	//See Page.getInfoPanelCell for more information
	public static final String INFO_PANEL_HEADER_TOKEN				= "HEADER_TOKEN";
	//An attempt at case-insensitive comparison of the table header.  This is not a robust implementation, because it is locale-dependent - using
	//  the xpath upper-case function would be better, but it requires a version of xpath later than 1.0
	//TODO  Try the upper-case function now, since the migration to WebDriver - I believe it supports xpath 2.0
	//THE FOLLOWING COMMENT WAS MADE PRIOR TO MIGRATION TO WEBDRIVER, AND THEREFORE REFERS TO A PRE-MIGRATION
	//  VERSION OF getTableAsListOfList.  IT MAY NO LONGER APPLY:
	//    NOTE that soon after adding the translate function to this xpath, getTableAsListOfList started failing, reporting that the
	//    xpath returns more than one node (it doesn't!).  I have disabled translate until further notice! (see Page.getInfoPanelCell for details)
	//    NOTE that since we are seeing the failure in getTableAsListOfList when accessing the info panels, we should be wary of
	//    the same failures when accessing related lists!

	//DEBUG JAB (08/08/2011) DO NOT remove the commented versions of these constants!  I am experimenting, and need to be able to easily revert
	//  to the original
	//public static final String INFO_PANEL_HEADER_XPATH				= "//div[contains(@class, 'pbSubheader') and node()[translate(normalize-space(text()), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + INFO_PANEL_HEADER_TOKEN + "']][1]";
	//public static final String INFO_PANEL_HEADER_XPATH				= "//div[contains(@class, 'pbSubheader') and node()[normalize-space(text())='" + INFO_PANEL_HEADER_TOKEN + "']][1]";
	//public static final String INFO_PANEL_XPATH						= "/following-sibling::div[normalize-space(@class)='pbSubsection'][1]/table[normalize-space(@class)='detailList'][1]/tbody";
	public static final String INFO_PANEL_HEADER_XPATH					= "//div[(contains(@class, 'pbSubheader') or contains(@class, 'pbHeader')) and descendant::node()[normalize-space(text())='" + INFO_PANEL_HEADER_TOKEN + "']][1]";
	public static final String INFO_PANEL_XPATH							= "/following-sibling::div[normalize-space(@class)='pbSubsection' or normalize-space(@class)='pbBody'][1]//table[normalize-space(@class)='detailList'][1]/tbody";

	//These, in any combination with each other, DO NOT WORK in certain conditions (see the comment above)
	//public static final String INFO_PANEL_HEADER_XPATH				= "//div[contains(@class, 'pbSubheader') and node()[normalize-space(text())='" + INFO_PANEL_HEADER_TOKEN + "']]";
	//public static final String INFO_PANEL_XPATH						= "/following-sibling::div[normalize-space(@class)='pbSubsection']//table[normalize-space(@class)='detailList']/tbody";
	//public static final String INFO_PANEL_XPATH						= "/following-sibling::div[normalize-space(@class)='pbSubsection']/table[normalize-space(@class)='detailList']/tbody";

	//When there is a help icon (a circle containing a question mark) next to a label in a table, getTableAsListOfList (before WebDriver
	//  migration) usually returned the name of a script (sfdcPage.setHelp) appended to the text of the label.  In order for our methods that
	//  scrape tables to work correctly, we need to strip that text out.
	//TODO  See if it still works that way with the new (post WebDriver migration) getTableAsListOfList method
	//NOTE that we may later find other objects in the GUI that cause getTableAsListOfList to see extraneous text in a table cell; if so,
	//  we may want to turn this into a list
	public static final String APPENDED_LABEL_TEXT							= "sfdcPage.setHelp";
	
	//The name of the default Field (on commonPage) for which to wait in Page.clickToSubmit and Page.waitForPageToSettle
	public static final String DEFAULT_FIELD_TO_WAIT_FOR					="privacyStatement";
	
	//This delay is used in Page.doPopulate() when we make extra attempts to populate a field
	public static final int POPULATE_RETRY_DELAY							= 1500;
	
	//The xpath of the GUI element that we use in the main window when waiting for SFDC pages to 'settle.'
	//  When we navigate to a new page, sometimes the page is in an indeterminate state (as far as Selenium
	//  is concerned).  It is not seen as having finished loading, and is therefore inaccessible to subsequent
	//  commands.  This occurs quite often when the page contains a lot of related list items.  The page does
	//  indeed finish loading, but Ajax-like elements like spinners and the instances of the text "Loading..."
	//  are present, making all other GUI elements inaccessible.This xpath references those elements, and
	//  plays a key role in the logic we use to determine that the window has settled.
	//(see the comments in SFDCObject.open for more info)
	public static final String WAIT_FOR_WINDOW_ID_TOKEN						= "ID_TOKEN";
	public static final String WAIT_FOR_WINDOW_ELEMENT_XPATH				= "//div[contains(@id, '" + WAIT_FOR_WINDOW_ID_TOKEN + "') and normalize-space(@class)='pbBody']/div[normalize-space(@class)='loading']/img";

	//  We will invoke this delay value when we open certain windows (those that open or settle slowly).
	//  A problem can also arise wherein the speed with which the fields get populated and the speed of the window "redraws" (not real refreshes,
	//  but not Ajax either) get out of synch, causing some fields which had already been populated to get cleared.  We will use this delay to
	//  handle that problem, as well, by invoking this delay after each field populate operation.
	//The Window pageRedrawDelay defaults to this if a value is not provided in window metadata.  It is expected that a larger value will be provided
	//  in the metadata for windows that open or settle slowly, but if no value is provided for such windows, EISTestBase
	//  CAN use DEFAULT_PAGE_REDRAW_LONG_DELAY as a default (note that such functionality will NOT be built into the interface, because it is
	//  app-dependent!)
	//public static final int DEFAULT_PAGE_REDRAW_DELAY						= 750;
	//public static final int DEFAULT_PAGE_REDRAW_LONG_DELAY				= 1500;
	//public static final int DEFAULT_PAGE_REDRAW_DELAY						= 100;
	public static final int DEFAULT_PAGE_REDRAW_DELAY						= 500;
	public static final int DEFAULT_PAGE_REDRAW_LONG_DELAY					= 1000;

	//Implementing classes should set the SFDCObject.sfdcObjectType member to a value like "account" or "oppty".  This constant is the default
	public static final String OBJECT_TYPE_SFDC_OBJECT						= "SFDC object";
	
	//"Success" exit code returned by EISTextBase.execProcess
	public static final int PROCESS_EXIT_CODE_SUCCESS						= 0;

	//We return this code from methods that call external processes (using Java's Runtime or ProcessBuilder classes) when we
	//  encounter a problem with the Process object itself (not the executable tied to the process).  (See EISTestBase.execProcess)
	public static final int PROCESS_ERROR_EXIT_CODE							= -1;

	//Exit code returned by any AutoIT executable
	public static final int PROCESS_EXIT_CODE_INVALID_ARGS					= 999;
	
	//Exit codes returned by the AutoIT executable (see EISConstants.DISMISS_MODAL_DIALOG_PROCESS) we use for handling modal dialogs that
	//  Selenium cannot seem to handle. (We are not using 1, 2, or 3 because AutoIT reserves some of them (at least '2').)
	public static final int PROCESS_EXIT_CODE_TIMEOUT_ON_OPEN				= 101;
	public static final int PROCESS_EXIT_CODE_COULD_NOT_ACTIVATE			= 111;
	public static final int PROCESS_EXIT_CODE_TIMEOUT_ON_CLOSE				= 121;

	//Exit codes returned by the AutoIT executable (see EISConstants.CLICK_LINK_IN_IE_PROCESS) we use for finding links by
	//  text and clicking them.  (We are not using 1, 2, or 3 because AutoIT reserves some of them (at least '2').)
	public static final int PROCESS_EXIT_CODE_LINK_NOT_FOUND				= 107;
	
	//NOTE:  Predefined locators were deprecated when the framework was migrated to WebDriver;
	//  it generates ALL window handles dynamically
	//public static final String GENERATED_WINDOW_LOCATOR_TOKEN				= "GENERATED";
	
	//Test result strings.  At the end of your test, print a message to the console that contains the text in TEST_PASSED_MESSAGE_TEXT.  (Assuming,
	//  of course, that the text indeed passed!)  Typically when a test fails, it will be as a result of an assertion failure or other exception,
	//  so you will never get to the last line of the test method.  But if you manage failures in such a way that you do execute to the end of the
	//  test method even when the test fails, be sure to print a message to the console that contains the text in TEST_FAILED_MESSAGE_TEXT.
	public static final String TEST_PASSED_MESSAGE_TEXT						= "TEST PASSED";
	public static final String TEST_FAILED_MESSAGE_TEXT						= "TEST FAILED";

	//Gets printed to console along with a message indicating the assert that is about to be done
	public static final String ASSERTING_MESSAGE_TEXT						= "ASSERTING";
	
	//Gets printed to console after an assert 
	public static final String ASSERTION_FAILED_TEXT						= "ASSERT FAILED";
	public static final String ASSERTION_PASSED_TEXT						= "ASSERT PASSED";

	//The default prefix that is used in the message that is printed to the console when a test fails (see Util.failTest()).
	//  The format of that message (on two lines only!) is:
	//    [prefix]:
	//    	[reason]
	//  The "reason" field is the text passed to the method, typically an error message.
	//NOTE that Jenkins parses console output for the prefix in order to include it in emails, so if you
	//  change this, be sure to change it in Jenkins as well (Jenkins admin permission required)
	public static final String TEST_FAILED_MESSAGE_PREFIX					= TEST_FAILED_MESSAGE_TEXT + "! Reason:  ";

	//The default prefix that is used in the message that is printed to the console to display the number of asserts that were performed.
	//NOTE that Jenkins parses console output for the prefix in order to include it in emails, so if you
	//  change this, be sure to change it in Jenkins as well (Jenkins admin permission required)
	public static final String NUM_ASSERTS_PREFIX							= "Total Number of asserts performed:  ";

	//Used for formatting assertion failure messages that fail the test
	public static final String ASSERTION_FAILURE_PREFIX						= "*** ASSERTION FAILURE: ";
	
	//Used in the subject line of outgoing emails (perhaps not all of them, but at least the one that is sent out when publishing a quote)
	public static final String EMAIL_SUBJECT_SUFFIX							= "[TBD]";
    
    static final List<LanguageCodeType> LANGUAGE_CODES_REQUIRING_COUNTRY_CODE	= new ArrayList<LanguageCodeType>(Arrays.asList(LanguageCodeType.zh));
	//In test properties files used for L10N testing, there must be a CONSTANT with the name specified
    
    /*While developing the test you dont want to close the firefox browsers opened by u mostly used while developing tests*/
    public static final boolean GLOBAL_BROWSERS_CLOSE=true;
    
    public static boolean TEST_STATUS_GLOBAL=false;
    public static String ASSERTION_MESSAGE_DATA						= "";
    public static boolean REPORT_TEST_STATUS_IS_FAIL						= false;
    
    /**
     * Global flag , whether it it is required for every test. Do not capture the video for every test. The size of video will be huge and
     * might eat up of lot of disk space.
     */
    public static boolean SCREEN_RECORDING				= false;
    
    //  in LANGUAGE_CODE_ENUM_CONSTANT_NAME.  The value of that CONSTANT must be one of the following
	public enum LanguageCodeType {
		cs, 	//Czech
		da, 	//Danish
		de, 	//German
		es, 	//Spanish
		fi, 	//Finnish
		fr, 	//French
		hu, 	//Hungarian
		it, 	//Italian
		ja, 	//Japanese
		ko, 	//Korean	
		nl, 	//Dutch
		no, 	//Norwegian
		pl, 	//Polish
		pt, 	//Portuguese
		ru, 	//Russian
		sv, 	//Swedish
		vi,		//Vietnamese		
		zh		//Chinese
	}
	
	//In test properties files used for L10N testing, there may be a CONSTANT with the name specified
	//  in COUNTRY_CODE_ENUM_CONSTANT_NAME.  The value of that CONSTANT must be one of the following
	public enum CountryCodeType {
		CN, 	//China		(when combined with the LanguageCodes element zh, yields the locale for Simplified Chinese)
		TW 		//Taiwan	(when combined with the LanguageCodes element zh, yields the locale for Traditional Chinese)
	}
}