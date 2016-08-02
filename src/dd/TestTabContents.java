package dd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Page_;
import common.Util;

import dd.DDConstants.TabNameType;

/**
 * Test class - TestTabContents
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestTabContents extends DDTestBase {
	public TestTabContents() {
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(DDConstants.BASE_URL);
		launchSalesforce();
	}

	@Test
	public void TEST_TabContents() throws Exception {
		Page_ myDocumentsPage;

		TabNameType tabName = null;
		String tabFieldName;
		String tabDisplayName = null;
		int numActual = 0;
		int sizeActual = 0;
		int numExpected;
		int sizeExpected = 0;
		String productLine;
		String yearVersion;
		String fileDetailsFileNamesFieldName;
		String fileDetailsFileNamesFieldSize;
		String value;
		String tabNameString;
						
		ArrayList<String> languageList = new ArrayList<String>(Arrays.asList(
				"British English", "Czech",
				"Dutch", "English",
				"European English",
				"French",
				"German", "Hungarian",
				"Italian", "Japanese",
				"Korean", "Nordic",
				"Polish",
				"Portuguese",
				"Russian", "Simplified Chinese",
				"Spanish", "Traditional Chinese",
				"Vietnamese"
				));
		
		
		//Some of these lists (esp. productLines) should live in DD (see TestPreviousVersionFunctionality.java
		//  for more notes on refactoring)
		
		
		
		tabNameString = testProperties.getConstant(DDConstants.DD_TAB_NAME_ENUM_CONSTANT_NAME).trim().toUpperCase();
		try {
			tabName = TabNameType.valueOf(tabNameString);
			tabDisplayName = tabName.getDisplayName();
		} catch (IllegalArgumentException e) {
			fail("The value supplied in the constant '" + DDConstants.DD_TAB_NAME_ENUM_CONSTANT_NAME + "' (" + tabNameString + ") is not a member of the DDConstants.TabNameType enumerated type; valid values are: " + Util.valuesOfEnum(DDConstants.TabNameType.class));
		}
		
		
		
		
		List<String> productLines = new ArrayList<String>();
		List<String> productLineYearVersionFieldNames = new ArrayList<String>();
		List<String> tabFieldNames = new ArrayList<String>();
		List<String> buttonFieldNames = new ArrayList<String>();
		List<String> dropDownValueFieldNames = new ArrayList<String>();
		List<String> fileNamesActual = new ArrayList<String>();
		List<String> fileSizeActual = new ArrayList<String>();
		
		List<String> fileNamesExpected = new ArrayList<String>();
		List<String> fileNamesExpectedEnglish = new ArrayList<String>();
		List<String> fileSizeExpected = new ArrayList<String>();
		List<String> serialNumbersExpected = new ArrayList<String>();
		
		loginAsAutoUser();
		loginAsDDUser();
		
		DD dd = utilCreateDDObject();
		myDocumentsPage = dd.getMyDocumentsPage();
		
		//Even though this is not an "L10N test" we need to call localizeFieldLocators(myDocumentsPage)
		//  because many locators on that page are tokenized for L10N
		localizeFieldLocators(myDocumentsPage);

		//Be sure call localizeFieldLocators BEFORE calling the following methods!  Field objects whose
		//  locators contain non-L10N tokens are "cloned" into new Field objects with parsed non-L10N
		//  tokens.  We need to be sure we are doing that AFTER calling localizeFieldLocators to parse
		//  the L10N tokens!
		productLines = dd.getProductLines();
		productLineYearVersionFieldNames = dd.getProductLineYearVersionFieldNamesExpected();
		tabFieldNames = dd.getTabFieldNames(productLines);
		buttonFieldNames = dd.getFileDetailsButtonFieldNames(productLines);
		dropDownValueFieldNames = dd.getFileDetailsDropDownValuesFieldNames(productLines);
		
		dd.openMyDocuments();
		myDocumentsPopUp.select();
				
		productLine = productLines.get(0);
		
		Util.printInfo("");
		Util.printInfo("Verifying the " + tabDisplayName + " tab for the '" + productLine + "' product line");

		tabFieldName = dd.getContentFieldName(tabFieldNames, tabName.getFieldName(), productLine);
		
		fileDetailsFileNamesFieldName = myDocumentsPage.createFieldWithParsedFieldLocatorsTokens(DDConstants.FILE_DETAILS_FILE_NAMES_FIELD_NAME, productLine, true);
		fileDetailsFileNamesFieldSize = myDocumentsPage.createFieldWithParsedFieldLocatorsTokens(DDConstants.FILE_DETAILS_FILE_NAMES_FIELD_SIZE, productLine, true);
		
		ListIterator<String> productLineYearVersionFieldNamesItr = productLineYearVersionFieldNames.listIterator();
		
		while (productLineYearVersionFieldNamesItr.hasNext()) {
			String productLineYearVersionFieldName = productLineYearVersionFieldNamesItr.next();
			
			myDocumentsPage.click(productLineYearVersionFieldName);
			yearVersion = myDocumentsPage.getAttribute(productLineYearVersionFieldName, "title");
			
			Util.printInfo("");
			Util.printInfo("Verifying the " + yearVersion + " year version");

			myDocumentsPage.click(tabFieldName);
			
			myDocumentsPage.populateField("platform", "All");
			if(!tabDisplayName.equalsIgnoreCase("Software"))
				languageList = new ArrayList<String>(Arrays.asList(
						"English"));
			for(int language=0; language<languageList.size(); language++){
				
				List<String> fileNameExpectedLanguageWise = new ArrayList<String>();
				List<String> fileSizeExpectedLanguageWise = new ArrayList<String>();
				List<String> fileSizeActualArray = new ArrayList<String>();
				
				myDocumentsPage.populateField("language", languageList.get(language));
				
				Util.printInfo("Verifying language " + languageList.get(language));
				value = myDocumentsPage.getValueFromGUI(tabFieldName);
				
				numExpected = Integer.parseInt(Util.getField(value, "(", ")"));
		
				boolean serialNumAndDownloadLogsFlag = false;
				if(tabDisplayName.equalsIgnoreCase("Serial Numbers") || tabDisplayName.equalsIgnoreCase("Download Logs"))
				{
					serialNumAndDownloadLogsFlag = true;
				}
				
				if (numExpected != 0) {
					
					if(!serialNumAndDownloadLogsFlag){
					fileNamesActual = myDocumentsPage.getMultipleTextValuesFromGUI(fileDetailsFileNamesFieldName);
					numActual = fileNamesActual.size();
					assertTrueWithFlags("The number of files on the " + tabDisplayName + " tab (" + numActual + ") equals the number in the tab header (" + numExpected + ")", numActual == numExpected);
						
						if(tabDisplayName.equalsIgnoreCase("Software") || tabDisplayName.equalsIgnoreCase("Documentation") || tabDisplayName.equalsIgnoreCase("Language Packs") || tabDisplayName.equalsIgnoreCase("Extras")){
							fileSizeActual = myDocumentsPage.getMultipleTextValuesFromGUI(fileDetailsFileNamesFieldSize);
							String[] splitFileSize = null;
							
							for (int z=0; z<fileSizeActual.size(); z++){
								splitFileSize = fileSizeActual.get(z).split(":");
								fileSizeActualArray.add(splitFileSize[1].trim());
							}
							
							sizeActual = fileSizeActualArray.size();
							assertTrueWithFlags("The file sizes described on the " + tabDisplayName + " tab (" + sizeActual + ") equals the number in the tab header (" + numExpected + ")", sizeActual == numExpected);
						}
							
					}else{
						serialNumbersExpected = dd.getFileNamesExpected(yearVersion);
						int serialNoRowWithHeader = myDocumentsPage.getNumRowsInTable("serialNumbersTable", true);
						numActual = serialNoRowWithHeader-1;
						assertTrueWithFlags("The number of files on the " + tabDisplayName + " tab (" + numActual + ") equals the number in the tab header (" + numExpected + ")", numActual == numExpected);
	
						for (int i=0;i<numActual; i++){
							String serialNumberActual = myDocumentsPage.getTableCell("serialNoInSerialNumbersTable", i);
					
							assertTrueWithFlags("The serial number '" + serialNumberActual + "' was found in the list of serial numbers on the " + tabDisplayName + " tab", serialNumbersExpected.contains(serialNumberActual));
						}
	
					}
					
				}else {
						if(tabDisplayName.equalsIgnoreCase("Serial Numbers") && numExpected == 0){
							myDocumentsPage.verifyFieldExists("youDoNotHaveSerialNumbersMessage");
							assertTrueWithFlags("The number of files on the " + tabDisplayName + " tab (" + numExpected + ") can never be 0", numExpected > 0);
							}
						else if(tabDisplayName.equalsIgnoreCase("Download Logs") && numExpected == 0){
							myDocumentsPage.verifyFieldExists("nothingDownloadedToDateMessage");
							numActual = 0;
							}
						else if(numExpected == 0){
							myDocumentsPage.verifyFieldExists("noDownloadsMatchMessage");
							numActual = 0;
							}
						}
		
				if (numActual > 0 && (!serialNumAndDownloadLogsFlag)) {
					fileNamesExpected = dd.getFileNamesExpected(yearVersion);
					
					if(tabDisplayName.equalsIgnoreCase("Software") || tabDisplayName.equalsIgnoreCase("Documentation") || tabDisplayName.equalsIgnoreCase("Language Packs") || tabDisplayName.equalsIgnoreCase("Extras")){
					
						if(languageList.get(language).equalsIgnoreCase("English"))
						{
							for(int j=0; j<fileNamesExpected.size(); j++)
							{
								if((fileNamesExpected.get(j).contains("British English")) ||(fileNamesExpected.get(j).contains("European English")))
									fileNamesExpectedEnglish.add(fileNamesExpected.get(j));
							}
							
							fileNamesExpected.removeAll(fileNamesExpectedEnglish);
						}
						
						for(int y=0; y<fileNamesExpected.size(); y++)
						{
							String[] splitFileNameAndSize = null;
																			
							if(fileNamesExpected.get(y).contains(languageList.get(language))){
								splitFileNameAndSize = fileNamesExpected.get(y).split("#");
								fileNameExpectedLanguageWise.add(splitFileNameAndSize[0]);
								fileSizeExpectedLanguageWise.add(splitFileNameAndSize[1]);
							}
						}
						
						numExpected = fileNameExpectedLanguageWise.size();
						sizeExpected = fileSizeExpectedLanguageWise.size();
						
						fileNamesExpected = fileNameExpectedLanguageWise;
						fileSizeExpected = fileSizeExpectedLanguageWise;
						
						assertTrueWithFlags("The file sizes described on the " + tabDisplayName + " tab (" + sizeActual + ") equals the expected number (" + sizeExpected + ")", sizeActual == sizeExpected);
					}
					else
					
					numExpected = fileNamesExpected.size();
		
					assertTrueWithFlags("The number of files on the " + tabDisplayName + " tab (" + numActual + ") equals the expected number (" + numExpected + ")", numActual == numExpected);
					
		
					//Since we know the lists are the same size, we don't have to do "contains" checks in both directions for the following
					//  verifications - if all expected files are present, we know that no unexpected files are present
					ListIterator<String> fileNamesExpectedItr = fileNamesExpected.listIterator();
					while (fileNamesExpectedItr.hasNext()) {
						String fileNameExpected = fileNamesExpectedItr.next();
						
						assertTrueWithFlags("The file '" + fileNameExpected + "' was found in the list of files on the " + tabDisplayName + " tab", fileNamesActual.contains(fileNameExpected));
					}

					//File size assertions for SOFTWARE, LANGUAGE PACKS AND EXTRAS tab
					if(tabDisplayName.equalsIgnoreCase("Software") || tabDisplayName.equalsIgnoreCase("Documentation") || tabDisplayName.equalsIgnoreCase("Language Packs") || tabDisplayName.equalsIgnoreCase("Extras")){
					ListIterator<String> fileSizeExpectedItr = fileSizeExpected.listIterator();
						while (fileSizeExpectedItr.hasNext()) {
							String fileSizesExpected = fileSizeExpectedItr.next();
							
							assertTrueWithFlags("The file '" + fileSizesExpected + "' was found in the list of files on the " + tabDisplayName + " tab", fileSizeActualArray.contains(fileSizesExpected.trim()));
						}
					}
					ListIterator<String> fileNamesActualItr = fileNamesActual.listIterator();
					while (fileNamesActualItr.hasNext()) {
						String fileNameActual = fileNamesActualItr.next();
						
						ListIterator<String> buttonFieldNamesItr = DDConstants.FILE_DETAILS_BUTTON_FIELD_NAMES.listIterator();
						while (buttonFieldNamesItr.hasNext()) {
							String buttonFieldName = buttonFieldNamesItr.next();
							
							String tokenizedFileDetailsButtonFieldName = dd.getContentFieldName(buttonFieldNames, buttonFieldName, productLine);
							String fileDetailsButtonFieldName = myDocumentsPage.createFieldWithParsedFieldLocatorsTokens(tokenizedFileDetailsButtonFieldName, fileNameActual, true);				
											
							//TODO:  use an enumeration
							switch (buttonFieldName) {
								case "fileDetailsGetSoftwareButton":	{
									myDocumentsPage.verifyFieldExists(fileDetailsButtonFieldName);
									break;
								}
								case "fileDetailsGetSoftwareButtonDropdown":	{
									if (myDocumentsPage.isFieldPresent(fileDetailsButtonFieldName)) {
										int numOptionsFound = 0;
										
										ListIterator<String> dropDownValuesFieldNamesItr = DDConstants.FILE_DETAILS_DROP_DOWN_VALUES_FIELD_NAMES.listIterator();
										while (dropDownValuesFieldNamesItr.hasNext()) {
											String dropDownValueFieldName = dropDownValuesFieldNamesItr.next();
											String tokenizedFileDetailsDropDownValueFieldName = dd.getContentFieldName(dropDownValueFieldNames, dropDownValueFieldName, productLine);
											String fileDetailsDropDownValueFieldName = myDocumentsPage.createFieldWithParsedFieldLocatorsTokens(tokenizedFileDetailsDropDownValueFieldName, fileNameActual, true);
											myDocumentsPage.getMultipleTextValuesFromGUI(fileDetailsFileNamesFieldSize);
											if (myDocumentsPage.isFieldPresent(fileDetailsDropDownValueFieldName)) {
												numOptionsFound++;
												Util.printInfo("Split button " + dropDownValueFieldName + " present for file name" + fileNameActual);
											}
										}
										boolean present = false;
										if(tabDisplayName.equalsIgnoreCase("Software")){
											 present  = myDocumentsPage.isFieldPresent("fileDetailsGetSoftwareButtonDropdownInstallNow");
											 if(present)
												 assertTrueWithFlags("The number of options in the drop down '" + fileDetailsButtonFieldName + "' (" + numOptionsFound + ") should be greater than 3", numOptionsFound > 3);
											 else 
												 assertTrueWithFlags("The number of options in the drop down '" + fileDetailsButtonFieldName + "' (" + numOptionsFound + ") is equal to 3", numOptionsFound == 3);
										}
										else
										assertTrueWithFlags("The number of options in the drop down '" + fileDetailsButtonFieldName + "' (" + numOptionsFound + ") is greater than 0", numOptionsFound > 0);
									}
									
									break;
								}
							}
						}
					}
				}
			}
		}
		
		dd.closeMyDocuments();
		
		logout();
	}

	@After
	public void tearDown() throws Exception {
		//Close the browser. Call stop on the WebDriverBackedSelenium instance
		//  instead of calling driver.quit(). Otherwise, the JVM will continue
		//  running after the browser has been closed.
		driver.quit();
		
		//TODO  Figure out how to determine if the test code has failed in a
		//  manner other than by EISTestBase.fail() being called.  Otherwise,
		//  finish() will always print the default passed message to the console.
		finish();
	}
}