package ddlogin;

import common.EISConstants;
import common.Page_;
import common.SFDCObject;
import common.Window_;

/**
 * Representation of a Digital Delivery object.
 * 
 * @author Nithya Somasundaram
 * @version 1.0.0
 */
final class DDLogin extends SFDCObject {
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

	private Page_ myDocumentsPage;
	private Page_ viewContactPage;

	private Window_ myDocumentsPopUp;

	/**
	 * Class constructor specifying the Page objects necessary for interacting with a Digital Delivery object.
	 * @param somePage the Page object used for ...
	 */
	public DDLogin(Window_ myDocumentsPopUp, Page_ myDocumentsPage, Page_ viewContactPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_DD, EISConstants.OBJECT_TYPE_DD);
		
		//mainWindow	= EISTestBase.mainWindow;
		//commonPage	= EISTestBase.commonPage;

		this.myDocumentsPage	= myDocumentsPage;
		this.viewContactPage	= viewContactPage;

		this.myDocumentsPopUp	= myDocumentsPopUp;
	}
	
	/**
	 * Gets the Page object used for managing documents.
	 * @return The Page object that defines objects	used when managing documents
	 */
	public Page_ getMyDocumentsPage() {
		return myDocumentsPage;
	}

	/**
	 * Gets the Page object used for viewing an SFDC Contact.
	 * @return The Page object that defines objects	used when viewing an SFDC Contact
	 */
	protected Page_ getViewContactPage() {
		return viewContactPage;
	}
	
	@Override
	public String toString() {
		return "Digital Delivery object [super=" + super.toString() +
				"]";
	}

	protected void openMyDocuments(String fieldName) {
		String locator;
		
		locator = viewContactPage.clickAndWaitForPopUpToOpen(fieldName);

		myDocumentsPopUp.setLocator(locator);	
		myDocumentsPopUp.select();
		
		myDocumentsPage.waitForFieldPresent("signOut");
	}
	
	protected void openMyDocuments() {
		openMyDocuments("viewContactsProductDownloadsLink");
	}
	
	protected void closeMyDocuments() {
		myDocumentsPopUp.select();
		
		myDocumentsPage.click("signOut");
		myDocumentsPopUp.close();
		
		mainWindow.select();
	}

//	protected List<String> getProductLines(String constantName) {
//		String productLineConstantsString;
//		List<String> productLineConstants = new ArrayList<String>();
//		List<String> productLines = new ArrayList<String>();
//
//		productLineConstantsString = myDocumentsPage.getConstant(constantName);
//		if (productLineConstantsString.isEmpty()) {
//			fail("No product line constants were supplied in a CONSTANT_SET named '" + constantName + "' in test properties");
//		}
//		
//		productLineConstants = Util.listOfStringTrim(Arrays.asList(productLineConstantsString.split(EISConstants.PARAMETER_DELIM)));
//		
//		ListIterator<String> itr = productLineConstants.listIterator();
//		while (itr.hasNext()) {
//			String productLineConstant = itr.next();
//			
//			productLines.add(myDocumentsPage.getConstant(productLineConstant));
//		}
//		
//		return productLines;
//	}
//	
//	protected List<String> getProductLines() {
//		return getProductLines(DDLoginConstants.DD_PRODUCT_LINE_CONSTANT_SET_NAME);
//	}
//		
//	protected List<String> getProductLinePanelFieldNames(List<String> productLines) {
//		List<String> productLinePanelFieldNames = new ArrayList<String>();
//		
//		ListIterator<String> itr = productLines.listIterator();
//		while (itr.hasNext()) {
//			String productLine = itr.next();
//			
//			productLinePanelFieldNames.add(myDocumentsPage.createFieldWithParsedFieldLocatorsTokens("productLinePanel", productLine, true));
//		}
//		
//		return productLinePanelFieldNames;
//	}
//
//	protected List<String> getProductLinePanelFieldNames(String constantName) {
//		return getProductLinePanelFieldNames(getProductLines(constantName));
//	}
//
///*	protected List<String> getProductLinePanelFieldNames() {
//	//NOTE that product lines have been moved from DDConstants.productLines to testdata.Data_Product_Lines.properties
//	//  but leave this here until the design is settled
//		return getProductLinePanelFieldNames(DDConstants.productLines);
//	}
//*/
//	
//	protected List<String> getProductLinePanelFieldNames() {
//		return getProductLinePanelFieldNames(DDLoginConstants.DD_PRODUCT_LINE_CONSTANT_SET_NAME);
//	}
//	
//	protected List<String> getProductLineYearVersionFieldNames(List<String> productLineYearVersionStrings) {
//		List<String> productLineYearVersionFieldNames = new ArrayList<String>();
//		List<String> productLineYearVersions = new ArrayList<String>();
//		
//		ListIterator<String> stringItr = productLineYearVersionStrings.listIterator();
//		while (stringItr.hasNext()) {
//			String productLineYearVersionString = stringItr.next();
//			
//			productLineYearVersions = Util.listOfStringTrim(Arrays.asList(productLineYearVersionString.split(EISConstants.PARAMETER_DELIM)));
//			
//			String productLineConstant = productLineYearVersions.remove(0);
//			String productLine = myDocumentsPage.getConstant(productLineConstant);
//
//			ListIterator<String> yearVersionItr = productLineYearVersions.listIterator();
//			while (yearVersionItr.hasNext()) {
//				String productLineYearVersion = yearVersionItr.next();
//				
//				productLineYearVersionFieldNames.add(myDocumentsPage.createFieldWithParsedFieldLocatorsTokens("yearButton", productLine, productLineYearVersion));				
//			}			
//		}
//		
//		return productLineYearVersionFieldNames;
//	}
//
//	protected List<String> getProductLineYearVersionFieldNamesExpected(String constantName) {
//		String productLineYearVersionStringConstantsString;
//		List<String> productLineYearVersionStringConstants = new ArrayList<String>();
//		List<String> productLineYearVersionStrings = new ArrayList<String>();
//	
//		productLineYearVersionStringConstantsString = myDocumentsPage.getConstant(constantName);
//		if (productLineYearVersionStringConstantsString.isEmpty()) {
//			fail("No product line year version constants were supplied in a CONSTANT_SET named '" + constantName + "' in test properties");
//		}
//		
//		productLineYearVersionStringConstants = Util.listOfStringTrim(Arrays.asList(productLineYearVersionStringConstantsString.split(EISConstants.PARAMETER_DELIM)));
//		
//		ListIterator<String> itr = productLineYearVersionStringConstants.listIterator();
//		while (itr.hasNext()) {
//			String productLineYearVersionStringConstant = itr.next();
//			
//			productLineYearVersionStrings.add(myDocumentsPage.getConstant(productLineYearVersionStringConstant));
//		}
//		
//		return getProductLineYearVersionFieldNames(productLineYearVersionStrings);
//	}
//
//	protected List<String> getProductLineYearVersionFieldNamesExpected() {
//		return getProductLineYearVersionFieldNamesExpected(DDLoginConstants.DD_PRODUCT_LINE_YEAR_VERSIONS_CONSTANT_SET_NAME);
//	}
//	
//	protected List<String> getProductLineYearVersionFieldNamesSuperset(List<String> productLines) {
//		List<String> productLineYearVersionFieldNames = new ArrayList<String>();
//		List<String> years = new ArrayList<String>();
//		
//		for (int year = DDLoginConstants.PRODUCT_VERSION_MIN_YEAR; year <= DDLoginConstants.PRODUCT_VERSION_MAX_YEAR; year++) {
//			years.add(Integer.toString(year));
//		}
//		
//		ListIterator<String> itr = productLines.listIterator();
//		while (itr.hasNext()) {
//			String productLine = itr.next();
//			
//			ListIterator<String> yearItr = years.listIterator();
//			while (yearItr.hasNext()) {
//				String year = yearItr.next();
//				
//				productLineYearVersionFieldNames.add(myDocumentsPage.createFieldWithParsedFieldLocatorsTokens("yearButton", productLine, year));				
//			}			
//		}
//		
//		return productLineYearVersionFieldNames;
//	}
//	
//	protected List<String> getFileNamesExpected(String constantNamePrefix, String yearVersion) {
//		String fileNamesString;
//		String constantName;
//		List<String> fileNames = new ArrayList<String>();
//	
//		constantName = constantNamePrefix + yearVersion;
//		
//		fileNamesString = myDocumentsPage.getConstant(constantName);
//		if (fileNamesString.isEmpty()) {
//			fail("No file names were supplied in a CONSTANT_SET named '" + constantName + "' in test properties");
//		}
//		
//		fileNames = Util.listOfStringTrim(Arrays.asList(fileNamesString.split(EISConstants.PARAMETER_DELIM)));
//				
//		return fileNames;
//	}
//
//	protected List<String> getFileNamesExpected(String yearVersion) {
//		return getFileNamesExpected(DDLoginConstants.DD_FILE_NAMES_YEAR_VERSION_CONSTANT_SET_NAME_PREFIX, yearVersion);
//	}
//	
//	protected List<String> getTabFieldNames(List<String> productLines) {
//		return getProductLinePanelContentFieldNames(productLines, DDLoginConstants.TAB_FIELD_NAMES);
//	}
	
/*	protected List<String> getDetailsFieldNames(List<String> productLines) {
		return getProductLinePanelContentFieldNames(productLines, DDConstants.DETAILS_FIELD_NAMES);
	}
*/
	
//	protected List<String> getFileDetailsButtonFieldNames(List<String> productLines) {
//		return getProductLinePanelContentFieldNames(productLines, DDLoginConstants.FILE_DETAILS_BUTTON_FIELD_NAMES);
//	}
//	
//	protected List<String> getFileDetailsDropDownValuesFieldNames(List<String> productLines) {
//		return getProductLinePanelContentFieldNames(productLines, DDLoginConstants.FILE_DETAILS_DROP_DOWN_VALUES_FIELD_NAMES);
//	}
//	
//	private List<String> getProductLinePanelContentFieldNames(List<String> productLines, List<String> fieldNames) {
//		List<String> contentFieldNames = new ArrayList<String>();
//		
//		ListIterator<String> itr = productLines.listIterator();
//		while (itr.hasNext()) {
//			String productLine = itr.next();
//			
//			ListIterator<String> fieldNameItr = fieldNames.listIterator();
//			while (fieldNameItr.hasNext()) {
//				String fieldName = fieldNameItr.next();
//				
//				contentFieldNames.add(myDocumentsPage.createFieldWithParsedFieldLocatorsTokens(fieldName, productLine, true));				
//			}			
//		}
//		
//		return contentFieldNames;
//	}
//	
//	protected String getYearVersionFieldName(List<String> productLineYearVersionFieldNames, String productLine, String yearVersion) {
//		String fieldName = "";
//		String productLineSubstring;
//		String yearVersionSubstring;
//		
//		productLineSubstring = "_" + productLine + "_";
//		productLineSubstring = productLineSubstring.replace(" ", "");
//		
//		yearVersionSubstring = "_" + yearVersion + "_";
//
//		ListIterator<String> productLineYearVersionFieldNamesItr = productLineYearVersionFieldNames.listIterator();
//		while (productLineYearVersionFieldNamesItr.hasNext()) {
//			String productLineYearVersionFieldName = productLineYearVersionFieldNamesItr.next();
//			
//			if (productLineYearVersionFieldName.toUpperCase().contains(productLineSubstring.toUpperCase()) && productLineYearVersionFieldName.contains(yearVersionSubstring)) {
//				fieldName = productLineYearVersionFieldName;
//				
//				break;
//			}
//		}
//		
//		return fieldName;
//	}
//	
//	protected String getContentFieldName(List<String> contentFieldNames, String baseFieldName, String productLine) {
//		String fieldName = "";
//		String productLineSubstring;
//		
//		productLineSubstring = "_" + productLine + "_";
//		productLineSubstring = productLineSubstring.replace(" ", "");
//		
//		ListIterator<String> contentFieldNamesItr = contentFieldNames.listIterator();
//		while (contentFieldNamesItr.hasNext()) {
//			String contentFieldName = contentFieldNamesItr.next();
//			
//			if (contentFieldName.toUpperCase().contains(baseFieldName.toUpperCase()) && contentFieldName.toUpperCase().contains(productLineSubstring.toUpperCase())) {
//				fieldName = contentFieldName;
//				
//				break;
//			}
//		}
//		
//		return fieldName;
//	}
}
