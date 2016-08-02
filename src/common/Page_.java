package common;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.WebElement;

import common.exception.MetadataException;

/**
 * Defines required public methods for classes that define Page objects.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public interface Page_ {

	public abstract String getName();

	public abstract String getContainerWindowName();

	public abstract List<String> getMessageLocators();

	public abstract List<String> getFieldLocators(String fieldName);

	public abstract String getFirstFieldLocator(String fieldName);

	public abstract Map<String, List<String>> getAllFieldLocators();
	
	public abstract void setFieldLocators(String fieldName, List<String> locators);
	public abstract void setFieldLocators(String fieldName,String actualText,String replaceDynamicText) ;

	public abstract void setFieldLocators(Map<String, List<String>> fieldLocators);

	public abstract List<String> getFieldMessageLocators(String fieldName);

	public abstract List<String> getFieldDependencies();

	public abstract String getPropertiesFilename();

	public abstract int getPageRedrawDelay();

	public abstract void setPageRedrawDelay(int pageRedrawDelay);

	public abstract Properties getPageProperties();

	//wait was used in doPopulate to simulate Selenium clickAndWait calls.  It has no use now, because "...AndWait" methods are deprecated
	//public abstract int getWaitTime();

	//wait was used in doPopulate to simulate Selenium clickAndWait calls.  It has no use now, because "...AndWait" methods are deprecated
	//public abstract void setWaitTime(int waitTime);

	public abstract boolean isCheckForAlerts();

	public abstract void setCheckForAlertsDefault(boolean checkForAlertsDefault);

	public abstract boolean isCheckForAlertsDefault();

	public abstract void setCheckForAlerts(boolean checkForAlerts);

	public abstract void setCheckForAlerts();

	//WebDriver does not differentiate between alerts and confirmations
	//public abstract boolean isConfirmationMessagePresent();

	//WebDriver does not differentiate between alerts and confirmations
	//public abstract String getConfirmationMessage();

	public abstract boolean isAlertMessagePresent();

	public abstract String getAlertMessage();

	public abstract int getNumTestDataRecords();

	public abstract int getNumVerificationDataRecords();

	public abstract int getNumConstants();

	public abstract List<String> getUnverifiedFieldNames();

	public abstract int getNumUnverifiedFieldNames();

	public abstract boolean areErrors();

	public abstract int getNumErrors();

	public abstract String getError(String fieldOrPageName);

	public abstract List<String> getErrors();

	public abstract void printErrors();

	public abstract String getErrorMessage(String fieldOrPageName);

	public abstract List<String> getErrorMessages();

	public abstract void printErrorMessages();

	public abstract Properties getConstants();

	public abstract String getConstant(String constantName);

	public abstract boolean constantExists(String constantName);

	public abstract String getMetadata();
	
	public abstract List<FieldData_> getInstanceFieldData(int instance);

	public abstract List<FieldData_> getInstanceFieldData(String instanceName);

	public abstract boolean setTestDataInstanceValue(String fieldName, String value, int instance, boolean setOnlyIfBlank);

	public abstract boolean setTestDataInstanceValue(String fieldName, String value, String instanceName, boolean setOnlyIfBlank);

	public abstract boolean setTestDataValue(String fieldName, String value, boolean setOnlyIfBlank);

	public abstract boolean setTestDataInstanceValue(String fieldName, String value, int instance);

	public abstract boolean setTestDataInstanceValue(String fieldName, String value, String instanceName);

	public abstract boolean setTestDataValue(String fieldName, String value);

	public abstract String getTestDataInstanceValue(String fieldName, int instance);

	public abstract String getTestDataInstanceValue(String fieldName, String instanceName);

	public abstract String getTestDataValue(String fieldName);

	public abstract FieldData_ getTestDataInstanceRecord(String fieldName, int instance);

	public abstract FieldData_ getTestDataInstanceRecord(String fieldName, String instanceName);

	public abstract FieldData_ getTestDataRecord(String fieldName);

	public abstract boolean deleteTestDataInstanceRecord(String fieldName, int instance);

	public abstract boolean deleteTestDataInstanceRecord(String fieldName, String instanceName);

	public abstract boolean deleteTestDataRecord(String fieldName);

	public abstract boolean setVerificationDataInstanceValue(String fieldName, String value, int instance, boolean setOnlyIfBlank);

	public abstract boolean setVerificationDataInstanceValue(String fieldName, String value, String instanceName, boolean setOnlyIfBlank);

	public abstract boolean setVerificationDataValue(String fieldName, String value, boolean setOnlyIfBlank);

	public abstract boolean setVerificationDataInstanceValue(String fieldName, String value, int instance);

	public abstract boolean setVerificationDataInstanceValue(String fieldName, String value, String instanceName);

	public abstract boolean setVerificationDataValue(String fieldName, String value);

	public abstract String getVerificationDataInstanceValue(String fieldName, int instance);

	public abstract String getVerificationDataInstanceValue(String fieldName, String instanceName);

	public abstract String getVerificationDataValue(String fieldName);

	public abstract FieldData_ getVerificationDataInstanceRecord(String fieldName, int instance);

	public abstract FieldData_ getVerificationDataInstanceRecord(String fieldName, String instanceName);

	public abstract FieldData_ getVerificationDataRecord(String fieldName);

	public abstract boolean deleteVerificationDataInstanceRecord(String fieldName, int instance);

	public abstract boolean deleteVerificationDataInstanceRecord(String fieldName, String instanceName);

	public abstract boolean deleteVerificationDataRecord(String fieldName);

	public abstract String createFieldWithParsedFieldLocatorsTokens(String fieldName, String value, boolean parseFirstTokenOnly);
	
	public abstract String createFieldWithParsedFieldLocatorsTokens(String fieldName, String value);
	
	public abstract String createFieldWithParsedFieldLocatorsTokens(String fieldName, String... values);
	
	public abstract void parseFieldLocatorsTokens(String fieldName, String value, boolean parseFirstTokenOnly);

	public abstract void parseFieldLocatorsTokens(String fieldName, String value);

	public abstract void parseFieldLocatorsTokens(String fieldName, String... values);

	public abstract void parseAllFieldLocatorsTokens(String value, boolean parseFirstTokenOnly);

	public abstract void parseAllFieldLocatorsTokens(String value);

	public abstract void parseAllFieldLocatorsTokens(String... values);

	public abstract void parseL10NFieldLocatorsTokens(String fieldName, String value, boolean parseFirstTokenOnly);

	public abstract void parseL10NFieldLocatorsTokens(String fieldName, String value);

	public abstract int populateInstance(int instance);

	public abstract int populateInstance(String instanceName);

	public abstract int populate();

	public abstract int populateAllInstance(int instance);

	public abstract int populateAllInstance(String instanceName);

	public abstract int populateAll();

	public abstract int populatePageInstance(int instance);

	public abstract int populatePageInstance(String instanceName);

	public abstract int populatePage();

	public abstract boolean populateFieldInstance(String fieldName, int instance);

	public abstract boolean populateFieldInstance(String fieldName, String instanceName);

	public abstract boolean populateField(String fieldName);

	public abstract boolean populateField(String fieldName, String value);

	//***** page-scope verify methods *****************************************
	public abstract int verifyInstance(int instance);

	public abstract int verifyInstance(String instanceName);

	public abstract int verify();

	public abstract int verifyAllInstance(int instance);

	public abstract int verifyAllInstance(String instanceName);

	public abstract int verifyAll();

	public abstract int verifyPageInstance(int instance);

	public abstract int verifyPageInstance(String instanceName);

	public abstract int verifyPage();

	public abstract int verifyAllValuesInstance(int instance);

	public abstract int verifyAllValuesInstance(String instanceName);

	public abstract int verifyAllValues();

	public abstract int verifyAllExistenceInstance(int instance);

	public abstract int verifyAllExistenceInstance(String instanceName);

	public abstract int verifyAllExistence();

	public abstract int verifyAllExistsInstance(int instance);

	public abstract int verifyAllExistsInstance(String instanceName);

	public abstract int verifyAllExists();

	public abstract int verifyAllNotExistsInstance(int instance);

	public abstract int verifyAllNotExistsInstance(String instanceName);

	public abstract int verifyAllNotExists();

	public abstract int verifyAllVisibilityInstance(int instance);

	public abstract int verifyAllVisibilityInstance(String instanceName);

	public abstract int verifyAllVisibility();

	public abstract int verifyAllVisibleInstance(int instance);

	public abstract int verifyAllVisibleInstance(String instanceName);

	public abstract int verifyAllVisibleInstance();

	public abstract int verifyAllNotVisibleInstance(int instance);

	public abstract int verifyAllNotVisibleInstance(String instanceName);

	public abstract int verifyAllNotVisible();

	public abstract int verifyAllNullnessInstance(int instance);

	public abstract int verifyAllNullnessInstance(String instanceName);

	public abstract int verifyAllNullness();

	public abstract int verifyAllNullInstance(int instance);

	public abstract int verifyAllNullInstance(String instanceName);

	public abstract int verifyAllNullInstance();

	public abstract int verifyAllNotNullInstance(int instance);

	public abstract int verifyAllNotNullInstance(String instanceName);

	public abstract int verifyAllNotNull();

	//***** field-scope verify methods ****************************************
	public abstract boolean verifyFieldExists(String fieldName);

	public abstract boolean verifyFieldNotExists(String fieldName);

	public abstract boolean verifyFieldExistence(String fieldName, boolean expected);

	public abstract boolean verifyFieldVisible(String fieldName);

	public abstract boolean verifyFieldNotVisible(String fieldName);

	public abstract boolean verifyFieldVisibility(String fieldName, boolean expected);

	public abstract boolean verifyFieldNull(String fieldName);

	public abstract boolean verifyFieldNotNull(String fieldName);

	public abstract boolean verifyFieldNullness(String fieldName, boolean expected);

	public abstract boolean verifyFieldInstance(String fieldName, int instance);

	public abstract boolean verifyFieldInstance(String fieldName, String instanceName);

	public abstract boolean verifyField(String fieldName);

	public abstract boolean verifyField(String fieldName, String expected);

	public abstract boolean verifyField(String fieldName, String... expecteds);

	public abstract boolean verifyField(String fieldName, List<String> expecteds);

	//NOTE that this is NOT a standard verify method.  It is not mapped to a member of
	//  EISConstants.ParameterizedVerificationDataValueType
	public abstract boolean verifyFieldStartsWith(String fieldName, String expected);

	//NOTE that this is NOT a standard verify method.  It is not mapped to a member of
	//  EISConstants.ParameterizedVerificationDataValueType
	public abstract boolean verifyFieldContains(String fieldName, String expected);
	
	public abstract boolean isFieldContains(String fieldName, String expected);

	//***** INFO_PANEL_LOOKUP verify methods **********************************
	//Note that these are convenience methods.  verifyField can be called instead but,
	//  given the existence of the verifyTableCell and verifyRelatedListCell methods,
	//  the user will assume the existence of methods with this name
	public abstract boolean verifyInfoPanelCellInstance(String fieldName, int instance);

	public abstract boolean verifyInfoPanelCellInstance(String fieldName, String instanceName);

	public abstract boolean verifyInfoPanelCell(String fieldName);

	public abstract boolean verifyInfoPanelCell(String fieldName, String expected);

	public abstract boolean verifyInfoPanelCell(String fieldName, String... expecteds);

	public abstract boolean verifyInfoPanelCell(String fieldName, List<String> expecteds);

	//***** INFO_PANEL_LOOKUP access methods **********************************
	//This method allows the user to retrieve the value and then perform her own verification
	public abstract String getInfoPanelCell(String fieldName);

	public abstract void clickLinkInInfoPanel(String fieldName, int linkNumberInCell);

	public abstract void clickLinkInInfoPanel(String fieldName);

	//***** TABLE_CELL_LOOKUP verify methods **********************************
	public abstract boolean verifyTableCellInstance(String columnFieldName, int rowNumber, int instance);

	public abstract boolean verifyTableCellInstance(String columnFieldName, int rowNumber, String instanceName);

	public abstract boolean verifyTableCell(String columnFieldName, int rowNumber);

	public abstract boolean verifyTableCell(String columnFieldName, int rowNumber, String expected);

	public abstract boolean verifyTableCell(String columnFieldName, int rowNumber, String... expecteds);

	public abstract boolean verifyTableCell(String columnFieldName, int rowNumber, List<String> expecteds);

	//***** TABLE_CELL_LOOKUP access methods **********************************
	//This method allows the user to retrieve the value and then perform her own verification
	public abstract String getTableCell(String columnFieldName, int rowNumber);

	public abstract int getNumRowsInTable(String tableFieldName, boolean returnFirstRow);

	public abstract int getNumRowsInTable(String tableFieldName);

	public abstract List<String> getTableRow(String tableFieldName, int rowNumber, boolean returnFirstRow);

	public abstract List<String> getTableRow(String tableFieldName, int rowNumber);

	public abstract List<String> getTableRow(String columnFieldNameToSearch, String value, boolean isCaseInsensitive);

	public abstract List<String> getTableRow(String columnFieldNameToSearch, String value);

	public abstract int getTableRowNum(String columnFieldNameToSearch, String value, boolean isCaseInsensitive);

	public abstract int getTableRowNum(String columnFieldNameToSearch, String value);

	public abstract List<String> getTableColumn(String columnFieldName, boolean returnFirstRow);

	public abstract List<String> getTableColumn(String columnFieldName);

	public abstract void clickLinkInTable(String columnFieldName, int rowNumber, int linkNumberInCell);

	public abstract void clickLinkInTable(String columnFieldName, int rowNumber);

	public abstract void clickLinkInTable(String columnFieldNameToSearch, String value, String columnFieldNameToClick, int linkNumberInCell);

	public abstract void clickLinkInTable(String columnFieldNameToSearch, String value, String columnFieldNameToClick);

	//***** RELATED_LIST_CELL_LOOKUP verify methods ***************************
	public abstract boolean verifyRelatedListCellInstance(String columnFieldName, int rowNumber, int instance);

	public abstract boolean verifyRelatedListCellInstance(String columnFieldName, int rowNumber, String instanceName);

	public abstract boolean verifyRelatedListCell(String columnFieldName, int rowNumber);

	public abstract boolean verifyRelatedListCell(String columnFieldName, int rowNumber, String expected);

	public abstract boolean verifyRelatedListCell(String columnFieldName, int rowNumber, String... expecteds);

	public abstract boolean verifyRelatedListCell(String columnFieldName, int rowNumber, List<String> expecteds);

	//***** RELATED_LIST_CELL_LOOKUP access methods ***************************
	//This method allows the user to retrieve the value and then perform her own verification
	public abstract String getRelatedListCell(String columnFieldName, int rowNumber);

	public abstract int getNumRowsInRelatedList(String relatedListFieldName);
	
	public abstract List<List<String>> getRelatedList(String relatedListFieldName);

	public abstract List<String> getRelatedListRow(String relatedListFieldName, int rowNumber);

	public abstract List<String> getRelatedListRow(String columnFieldNameToSearch, String value, boolean isCaseInsensitive);

	public abstract List<String> getRelatedListRow(String columnFieldNameToSearch, String value);

	public abstract int getRelatedListRowNum(String columnFieldNameToSearch, String value, boolean isCaseInsensitive);

	public abstract int getRelatedListRowNum(String columnFieldNameToSearch, String value);

	public abstract void clickLinkInRelatedList(String columnFieldName, int rowNumber, int linkNumberInCell);

	public abstract void clickLinkInRelatedList(String columnFieldName, int rowNumber);

	public abstract void clickLinkInRelatedList(String columnFieldNameToSearch, String value, String columnFieldNameToClick, int linkNumberInCell);

	public abstract void clickLinkInRelatedList(String columnFieldNameToSearch, String value, String columnFieldNameToClick);

	public abstract  int getRelatedListColumnNum(String columnFieldName);

	public abstract List<String> getRelatedListColumnLocatorParmSet(String columnFieldName);
	
	//click (intended for use on LINK and BUTTON fields) is a convenience method for the test writer;
	//  its name is more intuitive than populateField().  It calls the version of populateField
	//  that defines a value parameter.
	public abstract boolean click(String fieldName);

	public abstract boolean clickAndWait(String fieldName, String fieldNameToWaitFor, int waitTime);

	public abstract boolean clickAndWait(String fieldName, String fieldNameToWaitFor);

	public abstract boolean clickAndWaitForFieldPresent(String fieldName, String fieldNameToWaitFor, String waitTime);

	public abstract boolean clickAndWaitForFieldPresent(String fieldName, String fieldNameToWaitFor);

	public abstract boolean clickAndWaitForFieldPresent(String fieldName, String fieldNameToWaitFor, int waitTime);

	public abstract boolean clickAndWaitForFieldAbsent(String fieldName, String fieldNameToWaitFor, String waitTime);

	public abstract boolean clickAndWaitForFieldAbsent(String fieldName, String fieldNameToWaitFor);

	public abstract boolean clickAndWaitForFieldAbsent(String fieldName, String fieldNameToWaitFor, int waitTime);

	public abstract void clickToSubmit(String fieldName, String fieldToWaitForName, int waitTime);

	public abstract void clickToSubmit(String fieldName, String fieldToWaitForName);

	public abstract void clickToSubmit(String fieldName, int waitTime);

	public abstract void clickToSubmit(String fieldName);

	//check and uncheck (intended for use on CHECKBOX fields) are convenience methods for the test writer;
	//  their names are more intuitive than populateField().  They call the version of populateField
	//  that defines a value parameter.
	//Instance is irrelevant - the caller is specifying a value, so we don't need to look up the proper
	//  instance in the test data; in effect the caller is creating an instance.
	public abstract boolean check(String fieldName);

	public abstract boolean uncheck(String fieldName);

	//Note that in Selenium "value" refers to the value attribute of input fields.  So calling this
	//  method on, say, a link field, does not make sense.  However, in the interest of consistency,
	//  we will allow it; doGetValueFromGUI will determine which actual Selenium call to make.  (We
	//  do provide getTextFromLink for use when the caller knows what field type she is accessing,
	//  and knows Selenium's rules.  We also provide getValuesFromMultiSelect, because the user needs
	//  some way of retrieving a list of strings, as opposed to the single string returned by this
	//  method)
	public abstract String getValueFromGUI(String fieldName);

	//Convenience method for getting the value of a check box or radio button
	//  (for controls of those types, getValueFromGUI returns a string value
	//  of either "true" or "false", which we convert to a Boolean)
	public abstract boolean isChecked(String fieldName);

	//(see comments at getValueFromGUIField)
	public abstract String getTextFromLink(String fieldName);

	//Alias for getTextFromLink()
	public abstract String getLinkText(String fieldName);

	public abstract String getLinkTarget(String fieldName);

	//(see comments at getValueFromGUI)
	//public abstract String[] getValuesFromMultiSelect(String fieldName);
	public abstract List<String> getValuesFromMultiSelect(String fieldName);

	//public abstract String[] getPickListContents(String fieldName);
	public abstract List<String> getPickListContents(String fieldName);

	//public abstract String[] getMultiSelectContents(String fieldName);
	public abstract List<String> getMultiSelectContents(String fieldName);

	public abstract int getNumPickListOptions(String fieldName);

	public abstract int getNumMultiSelectOptions(String fieldName);

	public abstract boolean isValueInPickList(String fieldName, String value);

	public abstract boolean isValueInMultiSelect(String fieldName, String value);

	public abstract boolean areValuesInPickList(String fieldName, String... values);

	public abstract boolean areValuesInMultiSelect(String fieldName, String... values);

	public abstract String clickAndWaitForPopUpToOpen(String fieldName, Window_ windowToWaitFor);

	public abstract String clickAndWaitForPopUpToOpen(String fieldName, int timeout);

	public abstract String clickAndWaitForPopUpToOpen(String fieldName, String timeout);
	  
	public abstract String clickAndWaitForPopUpToOpen(String fieldName);
	  
	public abstract String waitForPopUpToOpen(Window_ windowToWaitFor, Set<String> existingWindows);

	public abstract String waitForPopUpToOpen(Set<String> existingWindows, int timeout);

	public abstract String waitForPopUpToOpen(Set<String> existingWindows, String timeout);

	public abstract String waitForPopUpToOpen(Set<String> existingWindows);

	public abstract String clickAndWaitForPopUpToClose(String fieldName);

	public abstract String clickAndWaitForPopUpToClose(String fieldName, int timeout);

	public abstract String clickAndWaitForPopUpToClose(String fieldName, String timeout);

	public abstract String waitForPopUpToClose();

	public abstract String waitForPopUpToClose(int timeout);

	public abstract String waitForPopUpToClose(String timeout);

	public abstract String waitForPopUpToClose(Set<String> existingWindows, int timeout);
	
	public abstract boolean closeAllPopUps(String windowToLeaveOpenLocator);

	public abstract boolean waitForPageToSettle(String referenceElement, boolean waitForPresent, int timeout);

	public abstract boolean waitForPageToSettle(String referenceElement, boolean waitForPresent);

	public abstract boolean waitForPageToSettle();

	public abstract boolean waitForFieldPresent(String fieldName, int timeout);

	public abstract boolean waitForFieldPresent(String fieldName);

	public abstract boolean waitForFieldPresent(String fieldName, String timeout);

	public abstract boolean waitForFieldAbsent(String fieldName, int timeout);

	public abstract boolean waitForFieldAbsent(String fieldName);

	public abstract boolean waitForFieldAbsent(String fieldName, String timeout);

	public abstract boolean waitForField(String fieldName, boolean waitForPresent, int timeout);

	public abstract boolean waitForField(String fieldName, boolean waitForPresent);

	public abstract boolean waitForField(String fieldName, boolean waitForPresent, String timeout);

	public abstract boolean waitForFieldPresentWhileRefreshing(String fieldName, int timeout);

	public abstract boolean waitForFieldPresentWhileRefreshing(String fieldName);

	public abstract boolean waitForFieldPresentWhileRefreshing(String fieldName, String timeout);

	public abstract boolean waitForFieldAbsentWhileRefreshing(String fieldName, int timeout);

	public abstract boolean waitForFieldAbsentWhileRefreshing(String fieldName);

	public abstract boolean waitForFieldAbsentWhileRefreshing(String fieldName, String timeout);

	public abstract boolean waitForFieldWhileRefreshing(String fieldName, boolean waitForPresent, int timeout);

	public abstract boolean waitForFieldWhileRefreshing(String fieldName, boolean waitForPresent);

	public abstract boolean waitForFieldWhileRefreshing(String fieldName, boolean waitForPresent, String timeout);

	public abstract boolean waitForFieldVisible(String fieldName, int timeout);

	public abstract boolean waitForFieldVisible(String fieldName);

	public abstract boolean waitForFieldVisible(String fieldName, String timeout);

	public abstract boolean waitForFieldNotVisible(String fieldName, int timeout);

	public abstract boolean waitForFieldNotVisible(String fieldName);

	public abstract boolean waitForFieldNotVisible(String fieldName, String timeout);

	public abstract boolean waitForFieldEnabled(String fieldName, int timeout);

	public abstract boolean waitForFieldEnabled(String fieldName);

	public abstract boolean waitForFieldEnabled(String fieldName, String timeout);

	//Look for errors on all fields and at page level
	public abstract boolean errorCheck();

	//Look for errors on all fields and at page level, and assert results
	public abstract boolean verifyErrorCheck(boolean areNoErrorsExpected);

	public abstract boolean verifyErrorCheck();

	//Look for errors on one field and at page level
	public abstract boolean errorCheckField(String fieldName);

	//Look for errors on one field and at page level, and assert results
	public abstract boolean verifyErrorCheckField(String fieldName, boolean areNoErrorsExpected);

	public abstract boolean verifyErrorCheckField(String fieldName);

	//Look for errors at field level only (by default, if messageLocators is empty we will fail the test)
	public abstract boolean errorCheckFieldOnly(String fieldName, boolean okayIfNoMessageLocator);

	//Look for errors at field level only (by default, if messageLocators is empty we will fail the test)
	public abstract boolean errorCheckFieldOnly(String fieldName);

	//Look for errors on one field only (not at page level), and assert results
	public abstract boolean verifyErrorCheckFieldOnly(String fieldName, boolean isNoErrorExpected);

	public abstract boolean verifyErrorCheckFieldOnly(String fieldName);

	//Look for errors at page level only (by default, if messageLocators is empty we will fail the test)
	public abstract boolean errorCheckPageOnly(boolean okayIfNoMessageLocator);

	//Look for errors at page level only (by default, if messageLocators is empty we will fail the test)
	public abstract boolean errorCheckPageOnly();

	//Look for errors at page level only, and assert results
	public abstract boolean verifyErrorCheckPageOnly(boolean isNoErrorExpected);

	public abstract boolean verifyErrorCheckPageOnly();

	//Look for errors on all fields and at page level on this page.
	//  If a page-level error is not found on this page, the
	//  passed-in page will be error-checked for a page-level error.
	//(the purpose of this method is to check for errors on a page
	//  that may be loaded as a consequence of an action on the
	//  current page)
	public abstract boolean errorCheck(Page_ anotherPage);

	//Look for errors on a field and at page level on this page.
	//  If a page-level error is not found on this page, the
	//  passed-in page will be error-checked for a page-level error.
	//(the purpose of this method is to check for errors on a page
	//  that may be loaded as a consequence of an action on the
	//  current page)
	public abstract boolean errorCheckField(String fieldName, Page_ anotherPage);

	//Look for errors at page level on this page.
	//  If a page-level error is not found on this page, the
	//  passed-in page will be error-checked for a page-level error
	//(the purpose of this method is to check for errors on a page
	//  that may be loaded as a consequence of an action on the
	//  current page)
	public abstract boolean errorCheckPageOnly(Page_ anotherPage);

	public abstract boolean findErrorMessage(String errorMsg);

	public abstract boolean verifyErrorMessage(String fieldOrPageName, String errorMsgExpected);

	//This method should be used sparingly!  It's purpose is to allow the selection of temporary windows,
	//  those that don't merit the creation of an associated Window instance.
	public abstract void selectWindow(String windowLocator);

	public abstract boolean isTextPresent(String text);

	public abstract boolean isFieldPresent(String fieldName);

	public abstract boolean isTablePresent(String fieldName);

	public abstract boolean isInfoPanelPresent(String fieldName);

	public abstract boolean isRelatedListPresent(String fieldName);

	public abstract boolean isFieldVisible(String fieldName);

	//public abstract void clearPageLoadErrorFlag();

	public abstract void refresh(int waitTime);

	public abstract void refresh();

	//public abstract String getEval(String command);
	public abstract Object getEval(String script);

	public abstract Object executeJavascript(String script);

	public abstract String getAttribute(String fieldName, String attributeName);

	public abstract void open(String url);
	
	public abstract String getLocation();

	public abstract String getWindowHandle();

	public abstract Set<String> getWindowHandles();

	public abstract Set<String> getAllWindowHandles();

	//Since migration to WebDriver, this method is no longer relevant
	//public abstract String[] getAllWindowNames();

	//Since migration to WebDriver, this method is no longer relevant
	//public abstract String getGeneratedWindowLocator(Set<String> preexistingWindows);

	public abstract int dismissModalDialog(String command, int timeoutSecondsProcessArg, int timeoutMultiplierProcessWait, boolean okIfDoesNotAppear, String... args);

	public abstract int dismissModalDialog(String command, int timeoutSecondsProcessArg, boolean okIfDoesNotAppear, String... args);

	public abstract int dismissModalDialog(String command, int timeoutSecondsProcessArg, int timeoutMultiplierProcessWait, String... args);

	public abstract int dismissModalDialog(String command, int timeoutSecondsProcessArg, String... args);

	public abstract int clickLinkInIE(String command, int timeoutSecondsProcessArg, String url, String linkText, boolean okIfNotFound);

	public abstract int clickLinkInIE(String command, String url, String linkText, boolean okIfNotFound);

	public abstract int clickLinkInIE(String command, int timeoutSecondsProcessArg, String url, String linkText);

	public abstract int clickLinkInIE(String command, String url, String linkText);

	//WebDriver does not differentiate between alerts and confirmations
	//public abstract void chooseCancelOnNextConfirmation();

	//WebDriver does not differentiate between alerts and confirmations
	//public abstract boolean isConfirmationPresent();

	//WebDriver does not differentiate between alerts and confirmations
	//public String getConfirmation();

	public abstract String handleAlert(EISConstants.AlertResponseType alertResponse);

	public abstract boolean isAlertPresent();

	public abstract String getAlert();

	public abstract void respondToAlert(EISConstants.AlertResponseType alertResponse);

	public abstract void acceptAlert();
	
	public abstract void verifyAlert(String alertMessage);

	public abstract void dismissAlert();

	//This code is not yet ready for prime time
	public abstract List<String> getMultipleTextValuesFromGUI(String fieldName);

	boolean hoverOver(String fieldName);

	public abstract boolean verifyFieldExistsInTable(String tableName, int row,
			String columnName,String fieldLocator);

	
	//**********************************************************************
	//  "...AndWait" methods that do not specify a field to wait for.  These
	//  are deprecated, and are NOT to be used!!!

	//public abstract void clickLinkInInfoPanelAndWait(String fieldName, int linkNumberInCell, String waitTime);

	//public abstract void clickLinkInInfoPanelAndWait(String fieldName, String waitTime);

	//public abstract void clickLinkInInfoPanelAndWait(String fieldName, int linkNumberInCell);

	//public abstract void clickLinkInInfoPanelAndWait(String fieldName, int linkNumberInCell, int waitTime);

	//public abstract void clickLinkInInfoPanelAndWait(String fieldName);

	//public abstract void clickLinkInTableAndWait(String fieldName, int rowNumber, int linkNumberInCell, String waitTime);

	//public abstract void clickLinkInTableAndWait(String fieldName, int rowNumber, String waitTime);

	//public abstract void clickLinkInTableAndWait(String fieldName, int rowNumber, int linkNumberInCell);

	//public abstract void clickLinkInTableAndWait(String fieldName, int rowNumber, int linkNumberInCell, int waitTime);

	//public abstract void clickLinkInTableAndWait(String fieldName, int rowNumber);

	//public abstract void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell, String waitTime);

	//public abstract void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick, String waitTime);

	//public abstract void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell);

	//public abstract void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell, int waitTime);

	//public abstract void clickLinkInTableAndWait(String fieldNameToSearch, String value, String fieldNameToClick);

	//public abstract void clickLinkInRelatedListAndWait(String fieldName, int rowNumber, int linkNumberInCell, String waitTime);

	//public abstract void clickLinkInRelatedListAndWait(String fieldName, int rowNumber, String waitTime);

	//public abstract void clickLinkInRelatedListAndWait(String fieldName, int rowNumber, int linkNumberInCell);

	//public abstract void clickLinkInRelatedListAndWait(String fieldName, int rowNumber, int linkNumberInCell, int waitTime);

	//public abstract void clickLinkInRelatedListAndWait(String fieldName, int rowNumber);

	//public abstract void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell, String waitTime);

	//public abstract void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick, String waitTime);

	//public abstract void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell);

	//public abstract void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick, int linkNumberInCell, int waitTime);

	//public abstract void clickLinkInRelatedListAndWait(String fieldNameToSearch, String value, String fieldNameToClick);

	//public abstract boolean clickAndWait(String fieldName, int waitTime);

	//public abstract boolean clickAndWait(String fieldName);

	//public abstract boolean checkAndWait(String fieldName, int waitTime);

	//public abstract boolean checkAndWait(String fieldName);

	//public abstract boolean checkAndWait(String fieldName, String waitTime);

	//public abstract boolean uncheckAndWait(String fieldName, int waitTime);

	//public abstract boolean uncheckAndWait(String fieldName);

	//public abstract boolean uncheckAndWait(String fieldName, String waitTime);
	//**********************************************************************
	
	public boolean verifyFieldIsRequired (String fieldName) throws MetadataException;
	
	public boolean verifyFieldIsRequired (String fieldName, boolean expected) throws MetadataException;

	void closeWindow(String windowLocator);

	public boolean checkFieldExistence(String fieldName);
	
	public abstract String[] getMultipleTextValuesfromField(String fieldName)throws MetadataException;
	public List<?> getListOfMultipleTextValuesfromField(String fieldName) throws MetadataException;
	public String[] getMultipleTextValuesfromField(String fieldName,boolean ignoreDuplicates) throws MetadataException;
	public List<WebElement> getMultipleWebElementsfromField(String fieldName) throws MetadataException;
	public abstract boolean waitForElementToDisappear(String fieldName,int iTimeout) throws MetadataException ;
	public abstract boolean checkIfElementExistsInPage(String fieldName,int iTimeout) throws MetadataException ;
	public void clickUsingLowLevelActions(String fieldLocator)throws MetadataException  ;
	public void clickUsingInputDevicesMouseClick(WebElement metadataElement) throws Exception;
	public abstract boolean checkIfAllCheckboxesAreChecked(String fieldLocator) throws MetadataException;
	public void checkAllCheckboxes(String fieldLocator,boolean checkOrUncheck) throws MetadataException;
	public String getCssPropertyValue(String fieldName, String cssPropertyName)  throws MetadataException;
	/*To use this getCurrentWebElement, always call methods like isFieldVisible or IsFieldPresent, with this way, the setSeleniumLocators
	 * method will be called and resets the webelement (Element) to the desired metadata element in gui.java.  */
	public WebElement getCurrentWebElement() throws MetadataException;
	/*This is used to get any attribute of webelement , the user can pull any DOM attribute which you see in Firebug-> DOM, like clientHeight,clientWidth, many more properties which cannot be
	 * retrieved via webdriver */
	public String getDOMAttributeOfWebElement(WebElement wEle,String attributeProperty);
	public void addNewFieldToExistingfieldMetadataList(String fieldName, String fieldLocatorvalue) throws MetadataException;
	public void scrollIntoViewOfMetadataElement(WebElement metadataElement);
	public void scrollIntoViewOfAllMetadataElements(List<WebElement> metadataElements);
	public List<WebElement> getCurrentWebElementList() throws MetadataException ;
	
	public  List<String> getFieldLocators(String fieldName,boolean ignoreCase);
	public void scrollIntoViewOfMetadataElementParentNode(WebElement metadataElement);	
	public void sendKeysInTextFieldSlowly(String fieldName, String value) throws Exception;
	public void removeFieldToExistingfieldMetadataList(String fieldName) throws Exception;
	public void clickUsingLowLevelActions(WebElement elementToClick)throws MetadataException  ;
	public boolean isFieldEnabled(String fieldName);

	boolean clickWait(String fieldName, String fieldNameToWaitFor, int timeout);
	
}
	
