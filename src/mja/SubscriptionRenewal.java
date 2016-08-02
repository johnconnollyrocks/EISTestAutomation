package mja;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.openqa.selenium.By;
import common.EISConstants;
import common.EISTestBase;
import common.Page_;
import common.SFDCObject;
import common.Util;

/**
 * Representation of a Major Accounts SFDC subscription renewal.
 * 
 * @author Ravi Shankar
 * @version 1.0.0
 */
final class SubscriptionRenewal extends SFDCObject {

	/**
	 * Indicator of case name to be used
	 */
	public static enum CaseName {
		OPTIONS_CHECK, SUB_RENEWAL, SEAT_COUNT_CHECK, SAME_OPPTY_MULTIPLE_AGREEMENTS, SAME_OPPTY__NUM_MULTIPLE_AGREEMENTS, SAME_OPPTY__15_AGREEMENTS, SAME_OPPTY_MULTIPLE_AGREEMENTS_VERIFY_EXPIRY_DATE, REGISTERED_LINE_ITEM_MULTIPLE_AGREEMENTS, CLOSE_OPPTY_MULTIPLE_AGREEMENTS_VERIFY_GREEN_CHECK_MARK
	}

	// State variables
	// Some (like name, id, and url) are defined in the superclass
	// Define only object-specific ones here

	// Perhaps the "object" we are creating is actually Service Contract, not a
	// Subscription Renewal.
	private String CaseName = null;
	private String subRegion = "";
	private String expiryFromDate = "";
	private String expiryToDate = "";
	private String country = "";
	private String usageType = "";
	private String status = "";
	private String agreementsList = "";
	private String fulfillmentCategoryList = "";
	private String registeredLineItemList = "";
	private String serviceContractNumber = "";
	private String contractUrl = "";
	private String masterAgreementURL = "";
	private String childAgreementURL = "";
	private String subConsoleWindow = "";
	
	private String expectedOpptyName = "";
	private String stringOfContractNumbers = "";
	private String totalActiveSeatCount;
	private String lastModifiedTimestamp = "";
	private String masterAgreement = "";
	private String childAgreement = "";
	private String agreementNumberInMasterAgreement = "";
	private String additionalAgreementNumbersInMasterAgreement = "";
	private String agreementNumberInChildAgreement = "";
	private String additionalAgreementNumbersInChildAgreement = "";
	private String firstAgreementExpiryDt = "";
	private String secondAgreementExpiryDt = "";
	private String expiryDate = "";
	private String opptyNameStatusOrEndDatePart = "";
	private String actualExpiryDate = "";
	private String optyName = "";
	private String accountName = "";
	private List<String> firstAssetSerialNumList = null;
	private List<String> secondAssetSerialNumList = null;
	private List<String> commentList = null;
	String opportunityLocatorFirstRow = null;
	String opportunityLocatorSecondRow = null;
	String productNumber = null;

	// NOTE TO OFFSHORE - I moved the getters and setters you had here down
	// below the constructor.
	// A generally accepted coding standard is class members first (the private
	// variables we are
	// declaring here), followed by the constructor(s), followed by getters and
	// setters, and then
	// all other methods.

	// //NOTE TO OFFSHORE - I moved most of these into the create() method,
	// because that is where
	// they are used. It is important to limit the scope of variables as much as
	// possible, to
	// avoid unintended complications that can arise from multiple methods
	// sharing the same
	// variables. (I moved opportunityName to the list above, because it is a
	// class member.)
	// private String arrayOfExpiryDates = "";
	// private int numRowsInOpportunitiesRelatedList;
	// //private String expiryDate = "";
	// private String expiryDate = "";
	// private String lineItemStatus = "";
	// //private String [] arrayOfExpiryDates;
	// private String [] expiryDateParts;
	// private String expiryDateArray = "";
	// private String opportunityName = "";
	// private String expectedOpportunityName = "";

	// Make all Page objects private, but provide getters so that test code can
	// have read
	// access to them. That will allow test code to access test data, metadata,
	// constants,
	// etc., through this SFDCObject instance. That's important, because we
	// don't want
	// test code authors to declare Page objects when they don't need to;
	// instead we want
	// test code to call utilities wherever possible. Those utilities typically
	// create an
	// SFDCObject, instantiate the Page objects it needs, and pass back the
	// SFDCObject
	// instance. It is through that instance that test code can get access to
	// this
	// object's Page data

	public List<String> getFirstAssetSerialNumList() {
		return firstAssetSerialNumList;
	}

	public void setFirstAssetSerialNumList(List<String> firstAssetSerialNumList) {
		this.firstAssetSerialNumList = firstAssetSerialNumList;
	}

	public List<String> getSecondAssetSerialNumList() {
		return secondAssetSerialNumList;
	}

	public void setSecondAssetSerialNumList(
			List<String> secondAssetSerialNumList) {
		this.secondAssetSerialNumList = secondAssetSerialNumList;
	}

	public List<String> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<String> commentList) {
		this.commentList = commentList;
	}

	public String getTotalActiveSeatCount() {
		return totalActiveSeatCount;
	}

	public void setTotalActiveSeatCount(String totalActiveSeatCount) {
		this.totalActiveSeatCount = totalActiveSeatCount;
	}

	private Page_ createSubscriptionRenewalPage;
	private Page_ viewServiceContractPage;

	// NOTES TO OFFSHORE - I added this (and also in
	// MJATestBase.utilCreateSubscriptionRenewalObject
	private Page_ viewOpptyPage;

	/**
	 * Class constructor specifying the Page objects necessary for interacting
	 * with an SFDC Lead.
	 * 
	 * @param createLeadRTPage
	 *            the Page object used for specifying the record type when
	 *            creating the SFDC Lead
	 * @param createLeadPage
	 *            the Page object used for creating the SFDC Lead
	 * @param viewLeadPage
	 *            the Page object used for viewing the SFDC Lead
	 */
	public SubscriptionRenewal(Page_ createSubscriptionRenewalPage,
			Page_ viewSubscriptionRenewalPage, Page_ viewOpptyPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_LEAD,
				EISConstants.OBJECT_TYPE_LEAD);

		// mainWindow = EISTestBase.mainWindow;
		// commonPage = EISTestBase.commonPage;

		this.createSubscriptionRenewalPage = createSubscriptionRenewalPage;
		this.viewServiceContractPage = viewSubscriptionRenewalPage;
		this.viewOpptyPage = viewOpptyPage;
	}

	/**
	 * @return the opportunityName
	 */
	/*
	 * public String getOpportunityName() { return opportunityName; }
	 */

	/**
	 * @param opportunityName
	 *            the opportunityName to set
	 */
	/*
	 * public void setOpportunityName(String opportunityName) {
	 * this.opportunityName = opportunityName; }
	 */

	/**
	 * @return the contractUrl
	 */
	public String getContractUrl() {
		return contractUrl;
	}

	public String getExpectedOpptyName() {
		return expectedOpptyName;
	}

	/**
	 * @param contractUrl
	 *            the contractUrl to set
	 */
	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}

	/**
	 * @return the serviceContractNumber
	 */
	public String getServiceContractNumber() {
		return serviceContractNumber;
	}

	/**
	 * @param serviceContractNumber
	 *            the serviceContractNumber to set
	 */
	public void setServiceContractNumber(String serviceContractNumber) {
		this.serviceContractNumber = serviceContractNumber;
	}

	/**
	 * @return the subRegion
	 */
	public String getSubRegion() {
		return subRegion;
	}

	/**
	 * @return the expiryFromDate
	 */
	public String getExpiryFromDate() {
		return expiryFromDate;
	}

	/**
	 * @return the expiryToDate
	 */
	public String getExpiryToDate() {
		return expiryToDate;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the usageType
	 */
	public String getUsageType() {
		return usageType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the agreementsList
	 */
	public String getAgreementsList() {
		return agreementsList;
	}

	/**
	 * @return the fulfillmentCategoryList
	 */
	public String getFulfillmentCategoryList() {
		return fulfillmentCategoryList;
	}

	/**
	 * @return the registeredLineItemList
	 */
	public String getRegisteredLineItemList() {
		return registeredLineItemList;
	}

	/**
	 * @return the createSubscriptionRenewalPage
	 */
	Page_ getCreateSubscriptionRenewalPage() {
		return createSubscriptionRenewalPage;
	}

	/**
	 * @return the viewServiceContractPage
	 */
	Page_ getViewServiceContractPage() {
		return viewServiceContractPage;
	}

	/**
	 * @return the viewOpptyPage
	 */
	Page_ getViewOpptyPage() {
		return viewOpptyPage;
	}

	/**
	 * @return the stringOfContractNumbers
	 */
	public String getStringOfContractNumbers() {
		return stringOfContractNumbers;
	}

	/**
	 * @param stringOfContractNumbers
	 *            the stringOfContractNumbers to set
	 */
	public void setStringOfContractNumbers(String stringOfContractNumbers) {
		this.stringOfContractNumbers = stringOfContractNumbers;
	}

	/**
	 * @return the CaseName
	 */
	public String getCaseName() {
		return CaseName;
	}
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	@Override
	public String toString() {
		return "SubscriptionRenewal [super=" + super.toString()
				+ ", subRegion=" + subRegion + ", expiryFromDate="
				+ expiryFromDate + ", expiryToDate=" + expiryToDate
				+ ", country=" + country + ", usagetype=" + usageType
				+ ", status=" + status + ", agreementsList=" + agreementsList
				+ ", fulfillmentCategoryList=" + fulfillmentCategoryList
				+ ", registeredLineItemList=" + registeredLineItemList
				+ ", serviceContractNumber=" + serviceContractNumber
				+ ", contractUrl=" + contractUrl 
				+ ", masterAgreementURL=" + masterAgreementURL 
				+ ", childAgreementURL=" + childAgreementURL 
				+ ", subConsoleWindow=" + subConsoleWindow
				// ", opportunityName=" + opportunityName +
				+", stringOfContractNumbers=" + stringOfContractNumbers + "]";
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

	/**
	 * Creates an SFDC Subscription Renewal Opportunity.
	 * 
	 * @return The name of the lead
	 */
	/*
	 * protected String create(TransferLineItem transferLineItem) {
	 * 
	 * this.transferLineItem = transferLineItem;
	 * 
	 * Util.printInfo("Creating Subscription Renewal...");
	 * 
	 * mainWindow.select();
	 * 
	 * //commonPage.clickAndWait("tabLeads");
	 * commonPage.clickAndWait("tabSubscriptionsBeta","optionsButton");
	 * createSubscriptionRenewalPage.populateField("accountsFilter");
	 * createSubscriptionRenewalPage.populateField("subRegion");
	 * createSubscriptionRenewalPage.populateField("expiryFromDate");
	 * createSubscriptionRenewalPage.populateField("expiryToDate");
	 * createSubscriptionRenewalPage.populateField("country");
	 * createSubscriptionRenewalPage.populateField("usageType");
	 * createSubscriptionRenewalPage.populateField("status");
	 * 
	 * 
	 * commonPage.clickToSubmit("optionsButton");
	 * 
	 * createSubscriptionRenewalPage.populateField("agreementsList");
	 * createSubscriptionRenewalPage.populateField("fulfillmentCategoryList");
	 * createSubscriptionRenewalPage.populateField("registeredLineItemList");
	 * 
	 * commonPage.clickToSubmit("saveButton");
	 * 
	 * createSubscriptionRenewalPage.clickToSubmit("searchButton",
	 * "optionsButton" , 30000000);
	 * 
	 * contractNumber = createSubscriptionRenewalPage.getValueFromGUI(
	 * "contractTableAgreementNumberFirstRow");
	 * setContractNumber(contractNumber);
	 * 
	 * createSubscriptionRenewalPage.checkAndWait("contractTableCheckBoxFirstRow"
	 * );
	 * 
	 * commonPage.clickToSubmit("createOpportunity", "optionsButton" ,
	 * 100000000);
	 * 
	 * viewOpportunityURL = createSubscriptionRenewalPage.getAttribute(
	 * "contractTableAgreementNumberFirstRow", "href");
	 * 
	 * Util.printInfo(
	 * "Created Subscription Renewal Opportunity for the Agreement Number : - '"
	 * + getContractNumber() + "' (" + getUrl() + ")");
	 * 
	 * createSubscriptionRenewalPage.open(viewOpportunityURL);
	 * 
	 * if(transferLineItem.toString() =="YES"){
	 * 
	 * viewSubscriptionRenewalPage.click("contractLineItemsLink");
	 * 
	 * intNumberOfRows = viewSubscriptionRenewalPage.getNumRowsInRelatedList(
	 * "contractLineItemsRelatedList");
	 * 
	 * for(int index=0; index < intNumberOfRows; index++){
	 * 
	 * List<String> statusinRelatedList =
	 * viewSubscriptionRenewalPage.getRelatedListRow
	 * ("statusInContractLineItemsRelatedList", index); String[] arrayOfStrings
	 * = statusinRelatedList.toArray(new String[statusinRelatedList.size()]);
	 * 
	 * if (arrayOfStrings[3].equalsIgnoreCase("active") &&
	 * arrayOfStrings[7].equalsIgnoreCase("registered")){
	 * 
	 * expiryDate = arrayOfStrings[4].toString(); expiryDateParts =
	 * expiryDate.split("/"); expiryDateArray = expiryDateParts[0] + "/" +
	 * expiryDateParts[2] ; expiryDateArray = " - Renewal " + expiryDateArray;
	 * 
	 * if(stringOfContractNumbers == ""){ stringOfContractNumbers
	 * =arrayOfStrings[8].toString(); arrayOfExpiryDates
	 * =expiryDateArray.toString(); } else{ stringOfContractNumbers
	 * =stringOfContractNumbers.concat(";" +arrayOfStrings[8].toString());
	 * arrayOfExpiryDates =expiryDateArray.toString(); }
	 * 
	 * 
	 * } }
	 * 
	 * Util.printInfo(
	 * "The contract Numbers for which the Status is Active and Asset Status is Registered are : - "
	 * + stringOfContractNumbers);
	 * 
	 * 
	 * numRowsInOpportunitiesRelatedList =
	 * viewSubscriptionRenewalPage.getNumRowsInRelatedList
	 * ("opportunitiesRelatedList");
	 * viewSubscriptionRenewalPage.clickLinkInRelatedList
	 * ("opportunityNameinOpportunitiesRelatedList",
	 * numRowsInOpportunitiesRelatedList-1);
	 * 
	 * opportunityName =
	 * viewSubscriptionRenewalPage.getValueFromGUI("opportunityName");
	 * setName(opportunityName);
	 * 
	 * expectedOpportunityName =
	 * contractNumber.concat(arrayOfExpiryDates.split(";"
	 * )[arrayOfExpiryDates.split(";").length-1]);
	 * 
	 * if(expectedOpportunityName.equalsIgnoreCase(opportunityName)) {
	 * Util.printInfo(
	 * "Created Subscription Renewal Opportunity and line item transfer. Actual Opportunity Name : - '"
	 * + getOpportunityName() + "' for the Agreement Number : - '" +
	 * getContractNumber() + "' Where the expected opportunity name was : - '" +
	 * expectedOpportunityName + "' (" + getUrl() + ")"); } else { fail(
	 * "Created Subscription Renewal Opportunity and line item transfer. Actual Opportunity Name : - '"
	 * + getOpportunityName() + "' for the Agreement Number : - '" +
	 * getContractNumber() + "' Where the expected opportunity name was : - '" +
	 * expectedOpportunityName + "' (" + getUrl() + ")"); }
	 * 
	 * viewSubscriptionRenewalPage.verifyField("fulfillmentCategory");
	 * viewSubscriptionRenewalPage.verifyField("projectedCloseDate");
	 * 
	 * viewSubscriptionRenewalPage.click("adskProductsList"); return
	 * getArrayOfContractNumbers(); } setUrl(); setId();
	 * 
	 * return getContractNumber(); }
	 */

	/*
	 * protected String create() { String returnValue = ""; int numberOfRows =
	 * 0;
	 * 
	 * //Util.printInfo("Creating Subscription Renewal...");
	 * Util.printInfo("Creating Service Contract '" + serviceContractNumber +
	 * "'...");
	 * 
	 * mainWindow.select();
	 * 
	 * //commonPage.clickAndWait("tabLeads");
	 * 
	 * commonPage.click("tabSubscriptionsBeta");
	 * createSubscriptionRenewalPage.waitForElementPresent("optionsButton");
	 * 
	 * createSubscriptionRenewalPage.populate();
	 * 
	 * //commonPage.clickToSubmit("optionsButton");
	 * createSubscriptionRenewalPage.clickAndWait("optionsButton",
	 * "saveOptionsButton");
	 * 
	 * createSubscriptionRenewalPage.populateInstance("OPTIONS_OVERLAY");
	 * 
	 * //When dealing with dismissing/submitting overlays, we cannot call
	 * Page_.clickToSubmit(String fieldName), because // the default field it
	 * waits for (Page_Common.privacyStatement) is present throughout the
	 * process (see the logic // in the method for details.) But we can't use
	 * the Page_.clickToSubmit(String fieldName, String fieldToWaitForName) //
	 * either, because "optionsButton" is always present. //So we do this in
	 * multiple steps because optionsButton is always present, but
	 * "in the background" (behind the overlay).
	 * //commonPage.clickToSubmit("saveButton");
	 * createSubscriptionRenewalPage.click("saveOptionsButton");
	 * createSubscriptionRenewalPage.waitForElementAbsent("saveOptionsButton");
	 * 
	 * //This won't work because commonPage.privacyStatement never goes away
	 * //createSubscriptionRenewalPage.clickToSubmit("searchButton",
	 * MJAConstants.DEFAULT_SUB_RENEWAL_SEARCH_TIMEOUT);
	 * createSubscriptionRenewalPage.clickToSubmit("searchButton",
	 * "contractTableAgreementNumberFirstRow",
	 * MJAConstants.DEFAULT_SUB_RENEWAL_SEARCH_TIMEOUT);
	 * 
	 * serviceContractNumber = createSubscriptionRenewalPage.getValueFromGUI(
	 * "contractTableAgreementNumberFirstRow");
	 * 
	 * //***** Originally we were waiting to do this until after for the create
	 * oppty process finishes (see code below). // However, the href attribute
	 * points to the contract, not the oppty. Therefore, we don't need to wait
	 * for // the create oppty process to finish before interrogating the href
	 * attribute! So we moved the code here // from its original location. By
	 * doing that, we limit our risk of failure there in the event that control
	 * // returns too soon from the create oppty process. contractUrl =
	 * createSubscriptionRenewalPage
	 * .getAttribute("contractTableAgreementNumberFirstRow", "href");
	 * 
	 * //NOTES TO OFFSHORE - calling getters and setters from within declaring
	 * class - shouldn't do it (for performance reasons) // unless the getter or
	 * setter is calculating or changing the variable that you are getting or
	 * setting //setContractNumber(contractNumber);
	 * 
	 * //Avoid ...AndWait methods that don't specify a field to wait for // (in
	 * this particular case, there is no need to wait anyway)
	 * //createSubscriptionRenewalPage
	 * .checkAndWait("contractTableCheckBoxFirstRow");
	 * createSubscriptionRenewalPage.check("contractTableCheckBoxFirstRow");
	 * 
	 * //Trouble handling this wait, because everything is present throughout
	 * the process // and control does not pass to another page (if it did, we
	 * could wait for something to appear // there). //HOWEVER, it appears that
	 * we don't need to worry about it, because control does not return // until
	 * the process is finished. The only problem with that is we cannot check
	 * for a timeout //ACTUALLY, it turns out that that is NOT TRUE - control
	 * DOES sometimes return before the process is finished!!! // Therefore we
	 * do need to deal with determining when the process is finished
	 * //commonPage.clickToSubmit("createOpportunity", "optionsButton" ,
	 * 100000000); //commonPage.clickToSubmit("createOpportunity",
	 * "optionsButton" ,
	 * MJAConstants.DEFAULT_CREATE_OPPTY_FROM_SUB_RENEWAL_SEARCH_TIMEOUT);
	 * //createSubscriptionRenewalPage.clickToSubmit("createOpportunityButton",
	 * "optionsButton" ,
	 * MJAConstants.DEFAULT_CREATE_OPPTY_FROM_SUB_RENEWAL_SEARCH_TIMEOUT);
	 * //createSubscriptionRenewalPage.clickToSubmit("createOpportunityButton");
	 * //createSubscriptionRenewalPage.clickToSubmit("createOpportunityButton",
	 * "contractTableAgreementNumberFirstRow");
	 * createSubscriptionRenewalPage.click("createOpportunityButton");
	 * 
	 * //!!!ALL checks for presence, visibility, and enabled-ness return too
	 * soon! I tried contractTableCheckBoxFirstRow, //
	 * contractTableAgreementNumberFirstRow, createOpportunityButton, and
	 * searchButton. (And yes, I checked to be sure // that the reason for the
	 * early return is not because we might have been checking even before the
	 * process started.) //So the only solution is to check for visibility or
	 * enabled-ness of a field (let's do both), and then sleep //Note that the
	 * more we access a GUI element while some process is going on, the more
	 * chance // we have of triggering a "stale element" exception!!!
	 * 
	 * //NOTE that we may need to revisit this code...
	 * createSubscriptionRenewalPage
	 * .waitForElementVisible("contractTableCheckBoxFirstRow");
	 * createSubscriptionRenewalPage
	 * .waitForElementEnabled("contractTableCheckBoxFirstRow");
	 * 
	 * //Observation tells me that this is more than enough time to sleep,
	 * especially because now that we have moved // the
	 * createSubscriptionRenewalPage
	 * .getAttribute("contractTableAgreementNumberFirstRow", "href") call //
	 * (see comments at the commented out call, below) we are no longer
	 * interrogating the GUI, and therefore // are less exposed to a failure
	 * here. Util.sleep(10000);
	 * 
	 * //***** This is the only thing we need to do after waiting for the create
	 * oppty process to finish. However, // the href attribute points to the
	 * contract, not the oppty. Therefore, we don't need to wait for // the
	 * create oppty process to finish before interrogating the href attribute!
	 * So let's move it up // to the point where we grab the contract number
	 * itself. By doing that, we limit our risk of failure // here in the event
	 * that control returns too soon from the create oppty process.
	 * //contractUrl = createSubscriptionRenewalPage.getAttribute(
	 * "contractTableAgreementNumberFirstRow", "href");
	 * 
	 * //Util.printInfo(
	 * "Created Subscription Renewal Opportunity for the Agreement Number '" +
	 * contractNumber + "'"); Util.printInfo("Created Service Contract '" +
	 * serviceContractNumber + "'");
	 * 
	 * 
	 * //NOTES TO OFFSHORE - //I am also a little concerned about the
	 * concatenating of an array into a string. I see that the code below
	 * returns // either a single contract number in a string, or a concatenated
	 * string of multiple contract numbers. But the return // value of the
	 * method is never used by the caller. Instead, the test method calls
	 * getArrayOfContractNumbers, but never // getServiceContractNumber. Which
	 * means that the single contract number is unnecessary. The new method you
	 * create for // most of the code below should build a real array (not a
	 * concatenated string) and stringOfContractNumbers should be turned into //
	 * an array. getArrayOfContractNumbers should return that array.
	 * 
	 * createSubscriptionRenewalPage.open(contractUrl);
	 * 
	 * //NOTES TO OFFSHORE - should be viewServiceContractPage
	 * viewServiceContractPage.click("contractLineItemsLink");
	 * 
	 * numberOfRows = viewServiceContractPage.getNumRowsInRelatedList(
	 * "contractLineItemsRelatedList");
	 * 
	 * for(int index=0; index < numberOfRows; index++){
	 * 
	 * //List<String> statusinRelatedList =
	 * viewSubscriptionRenewalPage.getRelatedListRow
	 * ("statusInContractLineItemsRelatedList", index); List<String>
	 * statusinRelatedList =
	 * viewServiceContractPage.getRelatedListRow("contractLineItemsRelatedList",
	 * index); String[] arrayOfStrings = statusinRelatedList.toArray(new
	 * String[statusinRelatedList.size()]);
	 * 
	 * //if (arrayOfStrings[3].equalsIgnoreCase("active") &&
	 * arrayOfStrings[7].equalsIgnoreCase("registered")){ if
	 * (arrayOfStrings[3].equalsIgnoreCase
	 * (createSubscriptionRenewalPage.getConstant("STATUS")) &&
	 * arrayOfStrings[7].equalsIgnoreCase("registered")){
	 * 
	 * expiryDate = arrayOfStrings[4].toString(); lineItemStatus =
	 * arrayOfStrings[3].toString();
	 * 
	 * 
	 * expiryDateParts = expiryDate.split("/"); if
	 * (lineItemStatus.equals("Expired")) { expiryDateArray = lineItemStatus ; }
	 * else { expiryDateArray = expiryDateParts[0] + "/" + expiryDateParts[2] ;
	 * }
	 * 
	 * 
	 * expiryDateArray = " - Renewal " + expiryDateArray;
	 * 
	 * if(stringOfContractNumbers == ""){ stringOfContractNumbers
	 * =arrayOfStrings[8].toString(); arrayOfExpiryDates
	 * =expiryDateArray.toString(); } else{ stringOfContractNumbers
	 * =stringOfContractNumbers.concat(";" +arrayOfStrings[8].toString());
	 * arrayOfExpiryDates =expiryDateArray.toString(); }
	 * Util.printInfo("The contract Numbers for which the Status is :" +
	 * createSubscriptionRenewalPage.getConstant("STATUS") +
	 * "and Asset Status is Registered are : - " + stringOfContractNumbers); } }
	 * 
	 * numRowsInOpportunitiesRelatedList =
	 * viewServiceContractPage.getNumRowsInRelatedList
	 * ("opportunitiesRelatedList"); if (numRowsInOpportunitiesRelatedList > 0)
	 * { viewServiceContractPage.clickLinkInRelatedList(
	 * "opportunityNameinOpportunitiesRelatedList",
	 * numRowsInOpportunitiesRelatedList-1); } else {
	 * fail("No opportunity table found"); } opportunityName =
	 * viewServiceContractPage.getValueFromGUI("opportunityName");
	 * setName(opportunityName);
	 * 
	 * expectedOpportunityName =
	 * serviceContractNumber.concat(arrayOfExpiryDates.
	 * split(";")[arrayOfExpiryDates.split(";").length-1]);
	 * 
	 * if(expectedOpportunityName.equalsIgnoreCase(opportunityName)) {
	 * Util.printInfo(
	 * "Created Subscription Renewal Opportunity and line item transfer. Actual Opportunity Name : - '"
	 * + getOpportunityName() + "' for the Agreement Number : - '" +
	 * serviceContractNumber + "' Where the expected opportunity name was : - '"
	 * + expectedOpportunityName + "' (" + getUrl() + ")"); } else { fail(
	 * "Created Subscription Renewal Opportunity and line item transfer. Actual Opportunity Name : - '"
	 * + getOpportunityName() + "' for the Agreement Number : - '" +
	 * serviceContractNumber + "' Where the expected opportunity name was : - '"
	 * + expectedOpportunityName + "' (" + getUrl() + ")"); }
	 * 
	 * //NOTES TO OFFSHORE - verifications should be done in the test method,
	 * not the "base" class or the SFDCObject subclass
	 * 
	 * //NOTES TO OFFSHORE - calling getters and setters from within declaring
	 * class - shouldn't do it (for performance reasons) // unless the getter or
	 * setter is calculating or changing the variable that you are getting or
	 * setting //return getArrayOfContractNumbers(); returnValue =
	 * stringOfContractNumbers;
	 * 
	 * setUrl(); setId();
	 * 
	 * return returnValue; }
	 */

	// NOTE TO OFFSHORE - This method should return the name of the renewal
	// (which is actually a service contract). In fact, this class
	// should be named something like ServiceContract (or just Contract?). The
	// caller would then extract the contract number(s) by calling
	// getArrayOfContractNumbers (see my comments on that below)
	protected String create() {
		// Use constants wherever possible, especially if the value will be
		// referenced in more than one place
		final String TARGET_ASSET_STATUS = "Registered";

		// NOTE TO OFFSHORE - I changed a number of these variable names for
		// clarity. I was especially confused by the
		// "arrayOf..." variable names and the use of the name
		// "statusinRelatedList" to refer to an entire row from
		// the contract line related list
		List<List<String>> contractLines = null;
		List<String> contractLine = null;
		MJAConstants.ContractLineStatusType contractLineStatusType = null;
		String stringOfExpiryDates = "";
		// int numRowsInOpportunitiesRelatedList;
		String expiryDate;
		String lineItemStatus;
		String[] expiryDateParts;
		String opptyNameStatusOrEndDatePart;
		String expectedStatus = createSubscriptionRenewalPage
				.getConstant("STATUS");

		// Util.printInfo("Creating Subscription Renewal...");

		Util.printInfo("Creating Service Contract '" + serviceContractNumber
				+ "'...");

		queryToGetAgreementsList();

		serviceContractNumber = createSubscriptionRenewalPage
				.getValueFromGUI("contractTableAgreementNumberFirstRow");

		// ***** Originally we were waiting to do this until after for the
		// create oppty process finishes (see code below).
		// However, the href attribute points to the contract, not the oppty.
		// Therefore, we don't need to wait for
		// the create oppty process to finish before interrogating the href
		// attribute! So we moved the code here
		// from its original location. By doing that, we limit our risk of
		// failure there in the event that control
		// returns too soon from the create oppty process.
		masterAgreementURL = createSubscriptionRenewalPage.getAttribute(
				"contractTableAgreementNumberFirstRow", "href");

		// NOTES TO OFFSHORE - calling getters and setters from within declaring
		// class - shouldn't do it (for performance reasons)
		// unless the getter or setter is calculating or changing the variable
		// that you are getting or setting
		// setContractNumber(contractNumber);

		// Avoid ...AndWait methods that don't specify a field to wait for
		// (in this particular case, there is no need to wait anyway)
		// createSubscriptionRenewalPage.checkAndWait("contractTableCheckBoxFirstRow");
		// createSubscriptionRenewalPage.check("contractTableCheckBoxFirstRow");
		createSubscriptionRenewalPage
				.populateInstance("CHECK_FIRST_AGREEMENT_NUM");

		if (createSubscriptionRenewalPage
				.isFieldPresent("masterSelectionRadioButton")) {
			Util.printInfo("selecting the ratio button Master Aggreement");
			createSubscriptionRenewalPage.populateInstance("MASTER_AGREEMENT");
		}

		// Trouble handling this wait, because everything is present throughout
		// the process
		// and control does not pass to another page (if it did, we could wait
		// for something to appear
		// there).
		// HOWEVER, it appears that we don't need to worry about it, because
		// control does not return
		// until the process is finished. The only problem with that is we
		// cannot check for a timeout
		// ACTUALLY, it turns out that that is NOT TRUE - control DOES sometimes
		// return before the process is finished!!!
		// Therefore we do need to deal with determining when the process is
		// finished
		// commonPage.clickToSubmit("createOpportunity", "optionsButton" ,
		// 100000000);
		// commonPage.clickToSubmit("createOpportunity", "optionsButton" ,
		// MJAConstants.DEFAULT_CREATE_OPPTY_FROM_SUB_RENEWAL_SEARCH_TIMEOUT);
		// createSubscriptionRenewalPage.clickToSubmit("createOpportunityButton",
		// "optionsButton" ,
		// MJAConstants.DEFAULT_CREATE_OPPTY_FROM_SUB_RENEWAL_SEARCH_TIMEOUT);
		// createSubscriptionRenewalPage.clickToSubmit("createOpportunityButton");
		// createSubscriptionRenewalPage.clickToSubmit("createOpportunityButton",
		// "contractTableAgreementNumberFirstRow");
		//createSubscriptionRenewalPage.click("createOpportunityButton");
		EISTestBase.driver.findElement(By.xpath("//input[contains(@id,'createopp')]")).click();
		createSubscriptionRenewalPage.acceptAlert();
		Util.sleep(10000);
		// !!!ALL checks for presence, visibility, and enabled-ness return too
		// soon! I tried contractTableCheckBoxFirstRow,
		// contractTableAgreementNumberFirstRow, createOpportunityButton, and
		// searchButton. (And yes, I checked to be sure
		// that the reason for the early return is not because we might have
		// been checking even before the process started.)
		// So the only solution is to check for visibility or enabled-ness of a
		// field (let's do both), and then sleep
		// Note that the more we access a GUI element while some process is
		// going on, the more chance
		// we have of triggering a "stale element" exception!!!

		// NOTE that we may need to revisit this code, because it STILL is not
		// reliable
		createSubscriptionRenewalPage
				.waitForFieldVisible("contractTableCheckBoxFirstRow");
		
		

		// Observation tells me that this is more than enough time to sleep,
		// especially because now that we have moved
		// the
		// createSubscriptionRenewalPage.getAttribute("contractTableAgreementNumberFirstRow",
		// "href") call
		// (see comments at the commented out call, below) all we are doing
		// after this point is opening the contract
		Util.sleep(10000);

		// ***** This is the only thing we need to do after waiting for the
		// create oppty process to finish. However,
		// the href attribute points to the contract, not the oppty. Therefore,
		// we don't need to wait for
		// the create oppty process to finish before interrogating the href
		// attribute! So let's move it up
		// to the point where we grab the contract number itself. By doing that,
		// we limit our risk of failure
		// here in the event that control returns too soon from the create oppty
		// process.
		// contractUrl =
		// createSubscriptionRenewalPage.getAttribute("contractTableAgreementNumberFirstRow",
		// "href");

		// Util.printInfo("Created Subscription Renewal Opportunity for the Agreement Number '"
		// + contractNumber + "'");
		Util.printInfo("Created Service Contract '" + serviceContractNumber
				+ "'");

		// NOTES TO OFFSHORE -
		// I am also a little concerned about the concatenating of an array into
		// a string. I see that the code below returns
		// either a single contract number in a string, or a concatenated string
		// of multiple contract numbers. But the return
		// value of the method is never used by the caller. Instead, the test
		// method calls getArrayOfContractNumbers, but never
		// getServiceContractNumber. Which means that the single contract number
		// is unnecessary. The new method you create for
		// most of the code below should build a real array (not a concatenated
		// string) and stringOfContractNumbers should be turned into
		// an array. getArrayOfContractNumbers should return that array.

		createSubscriptionRenewalPage.open(masterAgreementURL);

		// NOTES TO OFFSHORE - should be viewServiceContractPage
		// NOTES TO OFFSHORE - this is unnecessary, because we don't need to
		// bring the related list into view in order to access it
		// viewServiceContractPage.click("contractLineItemsLink");

		// NOTES TO OFFSHORE - I added a new method, Page_.getRelatedList. It is
		// much more efficient to call this once than to call
		// getRelatedListRow numberOfRows times
		// numberOfRows =
		// viewServiceContractPage.getNumRowsInRelatedList("contractLineItemsRelatedList");
		contractLines = viewServiceContractPage
				.getRelatedList("contractLineItemsRelatedList");

		// NOTES TO OFFSHORE - this is a way to insulate the code against
		// changes to the UI. Should the order of the columns in a related list
		// change, that change need only be made in the page properties file,
		// not in the code
		// This is IMPORTANT
		int statusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("statusInContractLineItemsRelatedList");
		int endDateColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("endDateInContractLineItemsRelatedList");
		int assetStatusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetStatusInContractLineItemsRelatedList");
		int assetSerialNumberColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetSerialNumberInContractLineItemsRelatedList");

		// NOTES TO OFFSHORE - I added a new method, Page_.getRelatedList. It is
		// much more efficient to call this once than to call
		// getRelatedListRow numberOfRows times
		// for(int index=0; index < numberOfRows; index++){
		ListIterator<List<String>> itr = contractLines.listIterator();
		while (itr.hasNext()) {
			contractLine = itr.next();

			// List<String> statusinRelatedList =
			// viewSubscriptionRenewalPage.getRelatedListRow("statusInContractLineItemsRelatedList",
			// index);
			// List<String> statusinRelatedList =
			// viewServiceContractPage.getRelatedListRow("contractLineItemsRelatedList",
			// index);
			// String[] arrayOfStrings = statusinRelatedList.toArray(new
			// String[statusinRelatedList.size()]);

			// if (arrayOfStrings[3].equalsIgnoreCase("active") &&
			// arrayOfStrings[7].equalsIgnoreCase("registered")){
			// if
			// (contractLine.get(statusColumnNum).equalsIgnoreCase(createSubscriptionRenewalPage.getConstant("STATUS"))
			// &&
			// contractLine.get(assetStatusColumnNum).equalsIgnoreCase("registered"))
			// {
			if (contractLine.get(statusColumnNum).equalsIgnoreCase(
					expectedStatus)
					&& contractLine.get(assetStatusColumnNum).equalsIgnoreCase(
							TARGET_ASSET_STATUS)) {

				// NOTES TO OFFSHORE - toString() call on a String object is
				// redundant
				// expiryDate = arrayOfStrings[4].toString();
				// lineItemStatus = arrayOfStrings[3].toString();
				expiryDate = contractLine.get(endDateColumnNum);
				lineItemStatus = contractLine.get(statusColumnNum);

				// This doesn't need to be done unless status is not Expired
				// expiryDateParts = expiryDate.split("/");

				// Better to use enumerated types, especially when comparing
				// test data to GUI content. It helps us
				// track when the test data needs to be changed to accommodate
				// changes to the GUI
				// if (lineItemStatus.equals("Expired")) {
				// expiryDateArray = lineItemStatus ;
				// } else {
				// expiryDateArray = expiryDateParts[0] + "/" +
				// newExpiryDatsArray[2] ;
				// }
				try {
					contractLineStatusType = MJAConstants.ContractLineStatusType
							.valueOf(lineItemStatus.trim().toUpperCase());
				} catch (IllegalArgumentException e) {
					fail("The contract line status '"
							+ lineItemStatus
							+ "' is not a member of the MJAConstants.ContractLineStatusType enumerated type; valid values are: "
							+ Util.valuesOfEnum(MJAConstants.ContractLineStatusType.class));
				}

				switch (contractLineStatusType) {
				case EXPIRED:
					opptyNameStatusOrEndDatePart = lineItemStatus;
					break;
				default:
					// TODO (for Jeffrey) This code is not
					// localization-friendly. Call a Java Date or DateFormat
					// method instead
					expiryDateParts = expiryDate.split("/");
					opptyNameStatusOrEndDatePart = expiryDateParts[0] + "/"
							+ expiryDateParts[2];
				}

				opptyNameStatusOrEndDatePart = " - Renewal "
						+ opptyNameStatusOrEndDatePart;

				// NOTES TO OFFSHORE - toString() call on a String object is
				// redundant
				// arrayOfExpiryDates =expiryDateArray.toString();
				// NOTES TO OFFSHORE - moved this line up from the conditional
				// blocks below, since it is done in both cases
				// (this is one of the fields whose name I changed - it was
				// expiryDateArray)
				stringOfExpiryDates = opptyNameStatusOrEndDatePart;

				if (stringOfContractNumbers == "") {
					// stringOfContractNumbers =arrayOfStrings[8].toString();
					stringOfContractNumbers = contractLine
							.get(assetSerialNumberColumnNum);

					// arrayOfExpiryDates =expiryDateArray.toString();
				} else {
					// stringOfContractNumbers
					// =stringOfContractNumbers.concat(";"
					// +arrayOfStrings[8].toString());
					stringOfContractNumbers = stringOfContractNumbers
							.concat(";"
									+ contractLine
											.get(assetSerialNumberColumnNum));

					// arrayOfExpiryDates =expiryDateArray.toString();
				}

				// Util.printInfo("The contract Numbers for which the Status is: "
				// + createSubscriptionRenewalPage.getConstant("STATUS") +
				// " and Asset Status is Registered are : - " +
				// stringOfContractNumbers);
				Util.printInfo("The contract Numbers for which the Status is: "
						+ expectedStatus + " and Asset Status is "
						+ TARGET_ASSET_STATUS + " are : - "
						+ stringOfContractNumbers);
			}
		}

		// NOTES TO OFFSHORE - at this point we have created the service
		// contract, so we should save its URL and leave. The rest of this
		// code should be in the test method

		// numRowsInOpportunitiesRelatedList =
		// viewServiceContractPage.getNumRowsInRelatedList("opportunitiesRelatedList");
		// if (numRowsInOpportunitiesRelatedList > 0) {
		// NOTES TO OFFSHORE - we will do this in the test method
		// viewServiceContractPage.clickLinkInRelatedList("opportunityNameinOpportunitiesRelatedList",
		// numRowsInOpportunitiesRelatedList - 1);
		// }
		// else {
		// fail("The Service Contract '" + serviceContractNumber +
		// "' does not have an associated opportunity");
		// }

		// NOTES TO OFFSHORE - at this point, we are no longer viewing a service
		// contract!
		// opptyName =
		// viewServiceContractPage.getValueFromGUI("opportunityName");
		// opptyName = viewOpptyPage.getValueFromGUI("opptyName");
		// setName(viewOpptyPage.getValueFromGUI("opptyName"));

		// NOTES TO OFFSHORE - generate an expected oppty name, and save it in
		// verification data - but do not verify it here
		expectedOpptyName = serviceContractNumber.concat(stringOfExpiryDates
				.split(";")[stringOfExpiryDates.split(";").length - 1]);
		// if (expectedOpportunityName.equalsIgnoreCase(opptyName)) {
		// Util.printInfo("Created Subscription Renewal Opportunity and line item transfer. Actual Opportunity Name : - '"
		// + opptyName + "' for the Agreement Number : - '" +
		// serviceContractNumber +
		// "' Where the expected opportunity name was : - '" +
		// expectedOpportunityName + "' (" + getUrl() + ")");
		// }
		// else {
		// fail("Created Subscription Renewal Opportunity and line item transfer. Actual Opportunity Name : - '"
		// + opptyName + "' for the Agreement Number : - '" +
		// serviceContractNumber +
		// "' Where the expected opportunity name was : - '" +
		// expectedOpportunityName + "' (" + getUrl() + ")");
		// }

		// NOTES TO OFFSHORE - verifications should be done in the test method,
		// not the "base" class or the SFDCObject subclass
		// THANKS FOR TAKING CARE Of THIS! (FROM JEFFREY)

		// NOTES TO OFFSHORE - calling getters and setters from within declaring
		// class - shouldn't do it (for performance reasons)
		// unless the getter or setter is calculating or changing the variable
		// that you are getting or setting
		// return getArrayOfContractNumbers();
		// No need to have this here at all
		// returnValue = stringOfContractNumbers;

		setUrl();
		setId();

		// return returnValue;
		return stringOfContractNumbers;
	}

	public void verifyOptions() {
		beforeOptionsPopulate();
		createSubscriptionRenewalPage.populate();

		// When dealing with dismissing/submitting overlays, we cannot call
		// Page_.clickToSubmit(String fieldName), because
		// the default field it waits for (Page_Common.privacyStatement) is
		// present throughout the process (see the logic
		// in the method for details.) But we can't use the
		// Page_.clickToSubmit(String fieldName, String fieldToWaitForName)
		// either, because "optionsButton" is always present.
		// So we do this in multiple steps because optionsButton is always
		// present, but "in the background" (behind the overlay).
		// commonPage.clickToSubmit("saveButton");
		createSubscriptionRenewalPage.click("saveOptionsButton");
		createSubscriptionRenewalPage.waitForFieldAbsent("saveOptionsButton");
	}

	public void beforeOptionsPopulate() {
		mainWindow.select();
		commonPage.click("tabSubscriptionsBeta");
		createSubscriptionRenewalPage.waitForFieldPresent("optionsButton");

		createSubscriptionRenewalPage.clickAndWait("optionsButton",
				"saveOptionsButton");
	}

	public void queryToGetAgreementsList() {
		mainWindow.select();

		commonPage.click("tabSubscriptionsBeta");
		createSubscriptionRenewalPage.waitForFieldPresent("optionsButton");
		createSubscriptionRenewalPage.populate();
		createSubscriptionRenewalPage.clickAndWait("optionsButton",
				"saveOptionsButton");
		createSubscriptionRenewalPage.populateInstance("OPTIONS_OVERLAY");

		// When dealing with dismissing/submitting overlays, we cannot call
		// Page_.clickToSubmit(String fieldName), because
		// the default field it waits for (Page_Common.privacyStatement) is
		// present throughout the process (see the logic
		// in the method for details.) But we can't use the
		// Page_.clickToSubmit(String fieldName, String fieldToWaitForName)
		// either, because "optionsButton" is always present.
		// So we do this in multiple steps because optionsButton is always
		// present, but "in the background" (behind the overlay).
		// commonPage.clickToSubmit("saveButton");
		createSubscriptionRenewalPage.click("saveOptionsButton");
		createSubscriptionRenewalPage.waitForFieldAbsent("saveOptionsButton");

		// This won't work because commonPage.privacyStatement never goes away
		// createSubscriptionRenewalPage.clickToSubmit("searchButton",
		// MJAConstants.DEFAULT_SUB_RENEWAL_SEARCH_TIMEOUT);

		// Control returns before the page has completely loaded - it partially
		// loads, then kind of refreshes itself. The upshot
		// is that this call alone does not successfully tell us that we can
		// proceed. However, it seems that contractTableCheckBoxFirstRow
		// is not enabled until the page is completely refreshed, so we will try
		// waiting for it. But a sleep is still required
		// before checking, to give the field a chance to go away
		// createSubscriptionRenewalPage.clickToSubmit("searchButton",
		// "contractTableAgreementNumberFirstRow",
		// MJAConstants.DEFAULT_SUB_RENEWAL_SEARCH_TIMEOUT);

		// Calling clickToSubmit with an empty fieldToWaitForName argument will
		// cause it to click the button but not wait for a field
		// to appear. However, it will check for a page-level error
		// createSubscriptionRenewalPage.clickToSubmit("searchButton", "",
		// MJAConstants.DEFAULT_SUB_RENEWAL_SEARCH_TIMEOUT);
		//createSubscriptionRenewalPage.clickToSubmit("searchButton", "");
		createSubscriptionRenewalPage.clickToSubmit("searchButton");
//		EISTestBase.driver.findElement(By.xpath("//input[contains(@id,'goSearch')]")).click();
		Util.sleep(2000);
//		createSubscriptionRenewalPage.acceptAlert();
		Util.sleep(5000);
		// createSubscriptionRenewalPage.waitForElementEnabled("contractTableCheckBoxFirstRow");
		createSubscriptionRenewalPage
				.waitForFieldEnabled("contractTableAgreementNumberFirstRow");
		subConsoleWindow=EISTestBase.driver.getCurrentUrl();
	}

	// Verify the active and expired seat counts
	public void verifySeatCount() {

		queryToGetAgreementsList();

	}

	public void verifyMultipleAgreements() {

		// common query to search for the agreements
		queryToGetAgreementsList();
		Util.sleep(5000);
		mainWindow.setLocator(EISTestBase.driver.getWindowHandle());
		createSubscriptionRenewalPage
				.populateInstance("CLICKAGREEMENT_OVERLAY");
		Util.printInfo("Creating Opportunity...");
		EISTestBase.driver.findElement(By.xpath("//input[contains(@id,'createopp')]")).click();
		createSubscriptionRenewalPage.acceptAlert();
		Util.sleep(10000);
		Util.printInfo("Opportunity Created");
//		String locator = createSubscriptionRenewalPage
//				.clickAndWaitForPopUpToOpen("contractTableAgreementNumberFirstRow");
		masterAgreementURL = createSubscriptionRenewalPage.getAttribute(
				"contractTableAgreementNumberFirstRow", "href");
		
		childAgreementURL = createSubscriptionRenewalPage.getAttribute(
				"childAgreement", "href");
		masterAgreement = createSubscriptionRenewalPage
				.getLinkText("masterAgreement");
		Util.printInfo("Master Agreement number is " + masterAgreement);
		childAgreement = createSubscriptionRenewalPage
				.getLinkText("childAgreement");
		Util.printInfo("Child Agreement number is " + childAgreement);		
		Util.printInfo("First Agreement URL is " + masterAgreementURL);
		firstAgreementExpiryDt = createSubscriptionRenewalPage
				.getValueFromGUI("firstAgreementExpiryDate");
		secondAgreementExpiryDt = createSubscriptionRenewalPage
				.getValueFromGUI("secondAgreementExpiryDate");
		
		EISTestBase.open(masterAgreementURL);
			
		/*
		 * viewServiceContractPage.selectWindow(locator); lastModifiedTimestamp
		 * = viewServiceContractPage
		 * .getValueFromGUI("lastModifiedDateInSystemInformation");
		 */
//		createSubscriptionRenewalPage.selectWindow(locator);
		
		int numRowsInOpportunitiesRelatedList = viewServiceContractPage
				.getNumRowsInRelatedList("opportunitiesRelatedList");
		if (numRowsInOpportunitiesRelatedList > 0) {
			Util.printInfo("Navigating to opportunity page from master agreement page");
			viewServiceContractPage.clickLinkInRelatedList(
					"opportunityNameinOpportunitiesRelatedList",
					numRowsInOpportunitiesRelatedList - 1);
			viewOpptyPage.waitForFieldPresent("discountApprovalRequestButton");
			setUrl();
			Util.printInfo("Oppty page URL  from master agreement is "+getUrl());
			lastModifiedTimestamp = viewServiceContractPage
					.getValueFromGUI("lastModifiedByInSystemInformation");
			String[] lastModifiedTimeList = lastModifiedTimestamp
					.split(",");
			lastModifiedTimestamp = lastModifiedTimeList[1];
		}
//		String mainWindowLocator = mainWindow.getLocator();
//		viewServiceContractPage.closeAllPopUps(mainWindowLocator);
		mainWindow.select();
		
	}

	public void verifyNumMultipleAgreements() {
	// common query to search for the agreements
	queryToGetAgreementsList();
	Util.sleep(5000);
	mainWindow.setLocator(EISTestBase.driver.getWindowHandle());
	createSubscriptionRenewalPage
			.populateInstance("CLICKAGREEMENT_OVERLAY");
	Util.printInfo("Creating Opportunity...");
	EISTestBase.driver.findElement(By.xpath("//input[contains(@id,'createopp')]")).click();
	Util.sleep(2000);
	createSubscriptionRenewalPage.acceptAlert();
	Util.sleep(10000);
	Util.printInfo("Opportunity Created");


	// Verify if the tick mark is present on the opportunity created on
	// multiple agreements
	createSubscriptionRenewalPage.verify();
	
//	String locator = createSubscriptionRenewalPage
//			.clickAndWaitForPopUpToOpen("contractTableAgreementNumberFirstRow");
	masterAgreementURL = createSubscriptionRenewalPage.getAttribute(
			"contractTableAgreementNumberFirstRow", "href");
	
	childAgreementURL = createSubscriptionRenewalPage.getAttribute(
			"childAgreement", "href");
	masterAgreement = createSubscriptionRenewalPage
			.getLinkText("masterAgreement");
	Util.printInfo("Master Agreement number is " + masterAgreement);
	childAgreement = createSubscriptionRenewalPage
			.getLinkText("childAgreement");
	Util.printInfo("Child Agreement number is " + childAgreement);
	Util.printInfo("First Agreement URL is " + masterAgreementURL);
	Util.printInfo("Child Agreement URL is " + childAgreementURL);
	firstAgreementExpiryDt = createSubscriptionRenewalPage
			.getValueFromGUI("firstAgreementExpiryDate");
	secondAgreementExpiryDt = createSubscriptionRenewalPage
			.getValueFromGUI("secondAgreementExpiryDate");
	EISTestBase.open(masterAgreementURL);
	

	
	/*
	 * viewServiceContractPage.selectWindow(locator); lastModifiedTimestamp
	 * = viewServiceContractPage
	 * .getValueFromGUI("lastModifiedDateInSystemInformation");
	 */
//	createSubscriptionRenewalPage.selectWindow(locator);
	
	int numRowsInOpportunitiesRelatedList = viewServiceContractPage
			.getNumRowsInRelatedList("opportunitiesRelatedList");
	if (numRowsInOpportunitiesRelatedList > 0) {
		Util.printInfo("Navigating to opportunity page from master agreement page");
		viewServiceContractPage.clickLinkInRelatedList(
				"opportunityNameinOpportunitiesRelatedList",
				numRowsInOpportunitiesRelatedList - 1);
		viewOpptyPage.waitForFieldPresent("discountApprovalRequestButton");
		setUrl();
		Util.printInfo("Oppty page URL  from master agreement is "+getUrl());
		lastModifiedTimestamp = viewServiceContractPage
				.getValueFromGUI("lastModifiedByInSystemInformation");
		String[] lastModifiedTimeList = lastModifiedTimestamp
				.split(",");
		lastModifiedTimestamp = lastModifiedTimeList[1];
	}
	}
	


	public String getLastModifiedTimestamp() {
		return lastModifiedTimestamp;
	}

	public void setLastModifiedTimestamp(String lastModifiedTimestamp) {
		this.lastModifiedTimestamp = lastModifiedTimestamp;
	}

	public List<String> verifyOpptyNumMultipleAgreements() {

		final String TARGET_ASSET_STATUS = "Registered";
		final String STATUS = "Active";
		String masterOpportunityNumber = null;
		String childOpportunityNumber = null;
		List<List<String>> contractLinesFirstAgreement = null;
		List<List<String>> contractLinesSecondAgreement = null;
		List<String> contractLineFirstAgr = null;
		List<String> contractLineSecondAgr = null;
		int statusColumnNum = 0;
		int assetSerialNumColumnNum = 0;
		int assetStatusColumnNum = 0;
//
//		EISTestBase.open(subConsoleWindow);
//		
//		masterAgreement = createSubscriptionRenewalPage
//				.getLinkText("masterAgreement");
//		Util.printInfo("Master Agreement number is " + masterAgreement);
//		childAgreement = createSubscriptionRenewalPage
//				.getLinkText("childAgreement");
//		Util.printInfo("Child Agreement number is " + childAgreement);
//		firstAgreementExpiryDt = createSubscriptionRenewalPage
//				.getValueFromGUI("firstAgreementExpiryDate");
//		secondAgreementExpiryDt = createSubscriptionRenewalPage
//				.getValueFromGUI("secondAgreementExpiryDate");
////		String opportunityLocatorFirstRow = createSubscriptionRenewalPage
////				.clickAndWaitForPopUpToOpen("masterAgreement");
//		masterAgreementURL = createSubscriptionRenewalPage.getAttribute(
//				"masterAgreement", "href");
//		childAgreementURL = createSubscriptionRenewalPage.getAttribute(
//				"childAgreement", "href");
//		Util.printInfo("Child Agreement URL is " + childAgreementURL);
//		Util.printInfo("Master Agreement URL is " + masterAgreementURL);
//		createSubscriptionRenewalPage.selectWindow(opportunityLocatorFirstRow);
		EISTestBase.open(masterAgreementURL);
		// Code to get the serial numbers from the master agreement
		Util.printInfo("Fetching Contract Line Item Related List from master agreement page");
		contractLinesFirstAgreement = viewServiceContractPage
				.getRelatedList("contractLineItemsRelatedList");
		statusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("statusInContractLineItemsRelatedList");
		accountName = viewServiceContractPage.getValueFromGUI("accountName");
		assetStatusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetStatusInContractLineItemsRelatedList");
		assetSerialNumColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetSerialNumberInContractLineItemsRelatedList");
		ListIterator<List<String>> itr = contractLinesFirstAgreement
				.listIterator();
		firstAssetSerialNumList = new ArrayList<String>();
		while (itr.hasNext()) {
			contractLineFirstAgr = itr.next();

			if (contractLineFirstAgr.get(statusColumnNum).equalsIgnoreCase(
					STATUS)
					&& contractLineFirstAgr.get(assetStatusColumnNum)
							.equalsIgnoreCase(TARGET_ASSET_STATUS)) {
				String assetSerialNum1 = contractLineFirstAgr
						.get(assetSerialNumColumnNum);
				// List that contains the master agreement serial numbers
				firstAssetSerialNumList.add(assetSerialNum1);
			}
		}

		int numRowsInOpportunitiesRelatedList = viewServiceContractPage
				.getNumRowsInRelatedList("opportunitiesRelatedList");
		if (numRowsInOpportunitiesRelatedList > 0) {
			Util.printInfo("Navigating to opportunity page from master agreement page");
			viewServiceContractPage.clickLinkInRelatedList(
					"opportunityNameinOpportunitiesRelatedList",
					numRowsInOpportunitiesRelatedList - 1);
			viewOpptyPage.waitForFieldPresent("discountApprovalRequestButton");
			setUrl();
			Util.printInfo("Oppty page URL  from master agreement is "+getUrl());
		} else {
			fail("The Service Contract '" + masterAgreement
					+ "' does not have an associated opportunity");
		}

		masterOpportunityNumber = viewServiceContractPage
				.getValueFromGUI("opportunityNumber");
		expiryDate = viewServiceContractPage
				.getValueFromGUI("projectedCloseDate");
		optyName = viewServiceContractPage.getValueFromGUI("opportunityName");
		agreementNumberInMasterAgreement = viewServiceContractPage
				.getValueFromGUI("agreementNumber");
		additionalAgreementNumbersInMasterAgreement = viewServiceContractPage
				.getValueFromGUI("additionalAgreementNumber");

		 productNumber = viewServiceContractPage
				.getLinkText("productNumList");
		 if (productNumber.equalsIgnoreCase("[0]")) {
			Util.printInfo("No Product available for the Opportunity hence cannot validate the ProductsRelatedList");
		} else {
			// Code to get the serial numbers from the opportunity created
			List<List<String>> products = viewServiceContractPage
					.getRelatedList("productsRelatedList");
			List<String> product = null;
			int commentsColumn = viewServiceContractPage
					.getRelatedListColumnNum("commentsinProductsRelatedList");

			ListIterator<List<String>> itrPrd = products.listIterator();
			commentList = new ArrayList<String>();

			while (itrPrd.hasNext()) {
				product = itrPrd.next();
				String prdComment = product.get(commentsColumn);
				String[] productComment = prdComment.split(":");
				String serialNumber = productComment[1];
				String[] actualSerialNumber = serialNumber.split(" ");
				String actualSerialNum = actualSerialNumber[0];
				commentList.add(actualSerialNum.trim());
			}
		}
//		String mainWindowLocator = mainWindow.getLocator();
//		viewServiceContractPage.closeAllPopUps(mainWindowLocator);
//		mainWindow.select();
//		String opportunityLocatorSecondRow = createSubscriptionRenewalPage
//				.clickAndWaitForPopUpToOpen("childAgreement");
		
//		createSubscriptionRenewalPage.selectWindow(opportunityLocatorSecondRow);
		 EISTestBase.open(childAgreementURL);
		// Code to get the serial numbers from the child agreement
		Util.printInfo("Fetching Contract Line Item Related List from Child agreement page");
		contractLinesSecondAgreement = viewServiceContractPage
				.getRelatedList("contractLineItemsRelatedList");
		statusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("statusInContractLineItemsRelatedList");

		assetStatusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetStatusInContractLineItemsRelatedList");
		assetSerialNumColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetSerialNumberInContractLineItemsRelatedList");
		ListIterator<List<String>> itrSec = contractLinesSecondAgreement
				.listIterator();
		secondAssetSerialNumList = new ArrayList<String>();
		while (itrSec.hasNext()) {
			contractLineSecondAgr = itrSec.next();
			if (contractLineSecondAgr.get(statusColumnNum).equalsIgnoreCase(
					STATUS)
					&& contractLineSecondAgr.get(assetStatusColumnNum)
							.equalsIgnoreCase(TARGET_ASSET_STATUS)) {
				String assetSerialNum2 = contractLineSecondAgr
						.get(assetSerialNumColumnNum);
				// List that contains the child agreement serial numbers
				secondAssetSerialNumList.add(assetSerialNum2);
			}
		}

		numRowsInOpportunitiesRelatedList = viewServiceContractPage
				.getNumRowsInRelatedList("opportunitiesRelatedList");
		if (numRowsInOpportunitiesRelatedList > 0) {
			Util.printInfo("Navigating to opportunity page from child agreement page");
			viewServiceContractPage.clickLinkInRelatedList(
					"opportunityNameinOpportunitiesRelatedList",
					numRowsInOpportunitiesRelatedList - 1);
			viewOpptyPage.waitForFieldPresent("discountApprovalRequestButton");
			setUrl();
			Util.printInfo("Oppty page URL  from child agreement is "+getUrl());
		} else {
			fail("The Service Contract '" + childAgreement
					+ "' does not have an associated opportunity");
		}
		childOpportunityNumber = viewServiceContractPage
				.getValueFromGUI("opportunityNumber");

		agreementNumberInChildAgreement = viewServiceContractPage
				.getValueFromGUI("agreementNumber");
		additionalAgreementNumbersInChildAgreement = viewServiceContractPage
				.getValueFromGUI("additionalAgreementNumber");

		ArrayList<String> opportunityNumberArray = new ArrayList<String>(
				Arrays.asList(masterOpportunityNumber, childOpportunityNumber));

		return opportunityNumberArray;
	}

	public String getMasterAgreement() {
		return masterAgreement;
	}

	public void setMasterAgreement(String masterAgreement) {
		this.masterAgreement = masterAgreement;
	}

	public String getChildAgreement() {
		return childAgreement;
	}

	public void setChildAgreement(String childAgreement) {
		this.childAgreement = childAgreement;
	}

	public String getAgreementNumberInMasterAgreement() {
		return agreementNumberInMasterAgreement;
	}

	public void setAgreementNumberInMasterAgreement(
			String agreementNumberInMasterAgreement) {
		this.agreementNumberInMasterAgreement = agreementNumberInMasterAgreement;
	}

	public String getAdditionalAgreementNumbersInMasterAgreement() {
		return additionalAgreementNumbersInMasterAgreement;
	}

	public void setAdditionalAgreementNumbersInMasterAgreement(
			String additionalAgreementNumbersInMasterAgreement) {
		this.additionalAgreementNumbersInMasterAgreement = additionalAgreementNumbersInMasterAgreement;
	}

	public String getAgreementNumberInChildAgreement() {
		return agreementNumberInChildAgreement;
	}

	public void setAgreementNumberInChildAgreement(
			String agreementNumberInChildAgreement) {
		this.agreementNumberInChildAgreement = agreementNumberInChildAgreement;
	}

	public String getAdditionalAgreementNumbersInChildAgreement() {
		return additionalAgreementNumbersInChildAgreement;
	}

	public void setAdditionalAgreementNumbersInChildAgreement(
			String additionalAgreementNumbersInChildAgreement) {
		this.additionalAgreementNumbersInChildAgreement = additionalAgreementNumbersInChildAgreement;
	}

	public String getMasterAgreementURL() {
		return masterAgreementURL;
	}

	public void setMasterAgreementURL(String masterAgreementURL) {
		this.masterAgreementURL = masterAgreementURL;
	}

	public String getChildAgreementURL() {
		return childAgreementURL;
	}

	public void setChildAgreementURL(String childAgreementURL) {
		this.childAgreementURL = childAgreementURL;
	}
	public String getSubConsoleWindow() {
		return subConsoleWindow;
	}

	public void setSubConsoleWindow(String subConsoleWindow) {
		this.subConsoleWindow = subConsoleWindow;
	}


	public void createOptyForFifteenAgreements() {
		String contractTableAgreementCheckBox = null;
		queryToGetAgreementsList();
		// createSubscriptionRenewalPage
		// .populateInstance("CLICKAGREEMENT_OVERLAY");
		masterAgreement = createSubscriptionRenewalPage
				.getLinkText("masterAgreement");
		Util.printInfo("Master Agreement number is " + masterAgreement);
		createSubscriptionRenewalPage.check("masterSelectionRadioButton");
		String contractTableAgreementCheck = "contractTableCheckBox";
		for (int i = 2; i <= 25; i++) {
			contractTableAgreementCheckBox = contractTableAgreementCheck + i;
			createSubscriptionRenewalPage.check(contractTableAgreementCheckBox);
		}
//		createSubscriptionRenewalPage.clickToSubmit("createOpportunityButton",
//				"");
		EISTestBase.driver.findElement(By.xpath("//input[contains(@id,'createopp')]")).click();
		createSubscriptionRenewalPage.acceptAlert();
		Util.sleep(20000);
		String msg = createSubscriptionRenewalPage
				.getTextFromLink("errorMessageForOptyCreation");
		if (msg.isEmpty()) {
			Util.printInfo("No error message caught. Opportunity is created.");
			failTest("Opportunity should not get created. Error message should be displayed.");

		} else {
			Util.printError("Caught an error message. Opportunity cannot be created and the message is "
					+ "\"" + msg + "\" ");
		}
		//createSubscriptionRenewalPage.clickToSubmit("searchButton", "");
		EISTestBase.driver.findElement(By.xpath("//input[contains(@id,'goSearch')]")).click();
		createSubscriptionRenewalPage.acceptAlert();
		
		createSubscriptionRenewalPage
				.waitForFieldEnabled("contractTableAgreementNumberFirstRow");
		 Util.sleep(20000);
		createSubscriptionRenewalPage.check("masterSelectionRadioButton");
		contractTableAgreementCheck = "contractTableCheckBox";
		for (int i = 2; i <= 15; i++) {
			contractTableAgreementCheckBox = contractTableAgreementCheck + i;
			createSubscriptionRenewalPage.check(contractTableAgreementCheckBox);
		}
//		createSubscriptionRenewalPage.clickToSubmit("createOpportunityButton",
//				"");
		EISTestBase.driver.findElement(By.xpath("//input[contains(@id,'createopp')]")).click();
		createSubscriptionRenewalPage.acceptAlert();
		
		msg=createSubscriptionRenewalPage.getTextFromLink("errorMessageForOptyCreation");
		if(msg.isEmpty()){
			Util.printInfo("No error message caught. Opportunity is created.");
		}
		else{
		failTest("Caught an error message. Opportunity cannot be created and the message is "
				+ "\"" + msg + "\" ");
		}
//		String opportunityLocatorFirstRow = createSubscriptionRenewalPage
//				.clickAndWaitForPopUpToOpen("masterAgreement");
//		contractUrl = createSubscriptionRenewalPage.getAttribute(
//				"masterAgreement", "href");
		masterAgreementURL = createSubscriptionRenewalPage.getAttribute(
				"masterAgreement", "href");
		Util.printInfo("Master Agreement URL is " + contractUrl);
		EISTestBase.open(masterAgreementURL);
//		createSubscriptionRenewalPage.selectWindow(opportunityLocatorFirstRow);
		int numRowsInOpportunitiesRelatedList = viewServiceContractPage
				.getNumRowsInRelatedList("opportunitiesRelatedList");
		if (numRowsInOpportunitiesRelatedList > 0) {
			Util.printInfo("Navigating to opportunity page from master agreement page");
			viewServiceContractPage.clickLinkInRelatedList(
					"opportunityNameinOpportunitiesRelatedList",
					numRowsInOpportunitiesRelatedList - 1);
			viewOpptyPage.waitForFieldPresent("discountApprovalRequestButton");
			setUrl();
			Util.printInfo("Oppty page URL is "+getUrl());
			lastModifiedTimestamp = viewServiceContractPage
					.getValueFromGUI("lastModifiedByInSystemInformation");
			String[] lastModifiedTimeList = lastModifiedTimestamp
					.split(",");
			lastModifiedTimestamp = lastModifiedTimeList[1];
		} else {
			fail("The Service Contract '" + masterAgreement
					+ "' does not have an associated opportunity");
		}

		String masterOpportunityNumber = viewServiceContractPage
				.getValueFromGUI("opportunityNumber");

		Util.printInfo("Opportynity number is " + masterOpportunityNumber);
		mainWindow.select();
	}

	/**
	 * @return the firstAgreementExpiryDate
	 */
	public String getFirstAgreementExpiryDt() {
		return firstAgreementExpiryDt;
	}

	/**
	 * @param firstAgreementExpiryDate
	 *            the firstAgreementExpiryDate to set
	 */
	public void setFirstAgreementExpiryDt(String firstAgreementExpiryDt) {
		this.firstAgreementExpiryDt = firstAgreementExpiryDt;
	}

	/**
	 * @return the secondAgreementExpiryDate
	 */
	public String getSecondAgreementExpiryDt() {
		return secondAgreementExpiryDt;
	}

	/**
	 * @param secondAgreementExpiryDate
	 *            the secondAgreementExpiryDate to set
	 */
	public void setSecondAgreementExpiryDt(String secondAgreementExpiryDt) {
		this.secondAgreementExpiryDt = secondAgreementExpiryDt;
	}

	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the opptyNameStatusOrEndDatePart
	 */
	public String getOpptyNameStatusOrEndDatePart() {
		return opptyNameStatusOrEndDatePart;
	}

	/**
	 * @param opptyNameStatusOrEndDatePart
	 *            the opptyNameStatusOrEndDatePart to set
	 */
	public void setOpptyNameStatusOrEndDatePart(
			String opptyNameStatusOrEndDatePart) {
		this.opptyNameStatusOrEndDatePart = opptyNameStatusOrEndDatePart;
	}

	/**
	 * @return the actualExpiryDate
	 */
	public String getActualExpiryDate() {
		return actualExpiryDate;
	}

	/**
	 * @param actualExpiryDate
	 *            the actualExpiryDate to set
	 */
	public void setActualExpiryDate(String actualExpiryDate) {
		this.actualExpiryDate = actualExpiryDate;
	}

	/**
	 * @return the optyName
	 */
	public String getOptyName() {
		return optyName;
	}

	/**
	 * @param optyName
	 *            the optyName to set
	 */
	public void setOptyName(String optyName) {
		this.optyName = optyName;
	}

	public void verifyOptyCreateForMultiAgreementsAndCheckExpiryDate()
			throws Exception {
		String[] expiryDateParts;
		String myFormatString = null;
		SimpleDateFormat df;
		Date AgreementExpiryDate1;
		Date AgreementExpiryDate2;
		Calendar cal;
		DateFormat dateFormat;

		myFormatString = "MM/dd/yyyy";
		df = new SimpleDateFormat(myFormatString);
		AgreementExpiryDate1 = df.parse(firstAgreementExpiryDt);
		AgreementExpiryDate2 = df.parse(secondAgreementExpiryDt);

		if (AgreementExpiryDate1.before(AgreementExpiryDate2)) {
			actualExpiryDate = firstAgreementExpiryDt;
		} else {
			actualExpiryDate = secondAgreementExpiryDt;
		}
		expiryDateParts = actualExpiryDate.split("/");
		opptyNameStatusOrEndDatePart = expiryDateParts[0] + "/"
				+ expiryDateParts[2];
	}

	public void closeOptyForMultiAgreementsAndVerifyGreenCheckMark() {
		queryToGetAgreementsList();
		masterAgreement = createSubscriptionRenewalPage
				.getLinkText("masterAgreement");

		Util.printInfo("Master Agreement number is " + masterAgreement);
		masterAgreementURL = createSubscriptionRenewalPage.getAttribute(
				"masterAgreement", "href");
		Util.printInfo("First Agreement URL is " + masterAgreementURL);
		childAgreement = createSubscriptionRenewalPage
				.getLinkText("childAgreement");

		Util.printInfo("Child Agreement number is " + childAgreement);
		childAgreementURL = createSubscriptionRenewalPage.getAttribute(
				"childAgreement", "href");
		Util.printInfo("Second Agreement URL is " + childAgreementURL);
		firstAgreementExpiryDt = createSubscriptionRenewalPage
				.getValueFromGUI("firstAgreementExpiryDate");
		secondAgreementExpiryDt = createSubscriptionRenewalPage
				.getValueFromGUI("secondAgreementExpiryDate");
//		opportunityLocatorFirstRow = createSubscriptionRenewalPage
//				.clickAndWaitForPopUpToOpen("masterAgreement");
				
		/*String mainWindowLocator = mainWindow.getLocator();
		mainWindow.select();
		opportunityLocatorSecondRow = createSubscriptionRenewalPage
				.clickAndWaitForPopUpToOpen("childAgreement");
		mainWindow.select(); */
		createSubscriptionRenewalPage
				.populateInstance("CLICKAGREEMENT_OVERLAY");
//		createSubscriptionRenewalPage.clickToSubmit("createOpportunityButton",
//				"");
		EISTestBase.driver.findElement(By.xpath("//input[contains(@id,'createopp')]")).click();
		createSubscriptionRenewalPage.acceptAlert();
//		viewServiceContractPage.selectWindow(opportunityLocatorFirstRow);
		EISTestBase.open(masterAgreementURL);
		viewServiceContractPage.refresh(5000);
		lastModifiedTimestamp = viewServiceContractPage
				.getValueFromGUI("lastModifiedDateInSystemInformation");
	 accountName = viewServiceContractPage.getValueFromGUI("accountName");
//		mainWindow.select();
	}

	public List<String> verifyOpptAndRelatedList() {

		final String TARGET_ASSET_STATUS = "Registered";
		final String STATUS = "Active";
		String masterOpportunityNumber = null;
		String childOpportunityNumber = null;
		List<List<String>> contractLinesFirstAgreement = null;
		List<List<String>> contractLinesSecondAgreement = null;
		List<String> contractLineFirstAgr = null;
		List<String> contractLineSecondAgr = null;
		int statusColumnNum = 0;
		int assetSerialNumColumnNum = 0;
		int assetStatusColumnNum = 0;

//		createSubscriptionRenewalPage.selectWindow(opportunityLocatorFirstRow);
		EISTestBase.open(masterAgreementURL);

		// Code to get the serial numbers from the master agreement
		Util.printInfo("Fetching Contract Line Item Related List from Master agreement page");
		contractLinesFirstAgreement = viewServiceContractPage
				.getRelatedList("contractLineItemsRelatedList");
		statusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("statusInContractLineItemsRelatedList");

		assetStatusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetStatusInContractLineItemsRelatedList");
		accountName = viewServiceContractPage.getValueFromGUI("accountName");
		assetSerialNumColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetSerialNumberInContractLineItemsRelatedList");
		ListIterator<List<String>> itr = contractLinesFirstAgreement
				.listIterator();
		firstAssetSerialNumList = new ArrayList<String>();
		while (itr.hasNext()) {
			contractLineFirstAgr = itr.next();

			if (contractLineFirstAgr.get(statusColumnNum).equalsIgnoreCase(
					STATUS)
					&& contractLineFirstAgr.get(assetStatusColumnNum)
							.equalsIgnoreCase(TARGET_ASSET_STATUS)) {
				String assetSerialNum1 = contractLineFirstAgr
						.get(assetSerialNumColumnNum);
				// List that contains the master agreement serial numbers
				firstAssetSerialNumList.add(assetSerialNum1);
			}
		}

		int numRowsInOpportunitiesRelatedList = viewServiceContractPage
				.getNumRowsInRelatedList("opportunitiesRelatedList");
		if (numRowsInOpportunitiesRelatedList > 0) {
			Util.printInfo("Navigating to opportunity page from master agreement page");
			viewServiceContractPage.clickLinkInRelatedList(
					"opportunityNameinOpportunitiesRelatedList",
					numRowsInOpportunitiesRelatedList - 1);
			viewOpptyPage.waitForFieldPresent("discountApprovalRequestButton");
			setUrl();
			Util.printInfo("Oppty page URL from master agreement  is "+getUrl());
		} else {
			fail("The Service Contract '" + masterAgreement
					+ "' does not have an associated opportunity");
		}

		masterOpportunityNumber = viewServiceContractPage
				.getValueFromGUI("opportunityNumber");
		expiryDate = viewServiceContractPage
				.getValueFromGUI("projectedCloseDate");
		optyName = viewServiceContractPage.getValueFromGUI("opportunityName");
		agreementNumberInMasterAgreement = viewServiceContractPage
				.getValueFromGUI("agreementNumber");
		additionalAgreementNumbersInMasterAgreement = viewServiceContractPage
				.getValueFromGUI("additionalAgreementNumber");

		productNumber = viewServiceContractPage
				.getLinkText("productNumList");
		if (productNumber.equalsIgnoreCase("[0]")) {
			Util.printInfo("No Product available for the Opportunity hence cannot validate the ProductsRelatedList");
		} else {
			// Code to get the serial numbers from the opportunity created
			List<List<String>> products = viewServiceContractPage
					.getRelatedList("productsRelatedList");
			List<String> product = null;
			int commentsColumn = viewServiceContractPage
					.getRelatedListColumnNum("commentsinProductsRelatedList");

			ListIterator<List<String>> itrPrd = products.listIterator();
			commentList = new ArrayList<String>();

			while (itrPrd.hasNext()) {
				product = itrPrd.next();
				String prdComment = product.get(commentsColumn);
				String[] productComment = prdComment.split(":");
				String serialNumber = productComment[1];
				String[] actualSerialNumber = serialNumber.split(" ");
				String actualSerialNum = actualSerialNumber[0];
				commentList.add(actualSerialNum.trim());
			}
		}
//		String mainWindowLocator = mainWindow.getLocator();
//		mainWindow.select();	
		EISTestBase.open(childAgreementURL);
//		viewServiceContractPage.selectWindow(opportunityLocatorSecondRow);
		viewServiceContractPage.refresh(5000);
		// Code to get the serial numbers from the child agreement
		Util.printInfo("Fetching Contract Line Item Related List from Child agreement page");
		contractLinesSecondAgreement = viewServiceContractPage
				.getRelatedList("contractLineItemsRelatedList");
		statusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("statusInContractLineItemsRelatedList");

		assetStatusColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetStatusInContractLineItemsRelatedList");
		assetSerialNumColumnNum = viewServiceContractPage
				.getRelatedListColumnNum("assetSerialNumberInContractLineItemsRelatedList");
		ListIterator<List<String>> itrSec = contractLinesSecondAgreement
				.listIterator();
		secondAssetSerialNumList = new ArrayList<String>();
		while (itrSec.hasNext()) {
			contractLineSecondAgr = itrSec.next();
			if (contractLineSecondAgr.get(statusColumnNum).equalsIgnoreCase(
					STATUS)
					&& contractLineSecondAgr.get(assetStatusColumnNum)
							.equalsIgnoreCase(TARGET_ASSET_STATUS)) {
				String assetSerialNum2 = contractLineSecondAgr
						.get(assetSerialNumColumnNum);
				// List that contains the child agreement serial numbers
				secondAssetSerialNumList.add(assetSerialNum2);
			}
		}

		numRowsInOpportunitiesRelatedList = viewServiceContractPage
				.getNumRowsInRelatedList("opportunitiesRelatedList");
		if (numRowsInOpportunitiesRelatedList > 0) {
			Util.printInfo("Navigating to opportunity page from child agreement page");
			viewServiceContractPage.clickLinkInRelatedList(
					"opportunityNameinOpportunitiesRelatedList",
					numRowsInOpportunitiesRelatedList - 1);
			viewOpptyPage.waitForFieldPresent("discountApprovalRequestButton");
			setUrl();
			Util.printInfo("Oppty page URL from child agreement is "+ getUrl() );
		} else {
			fail("The Service Contract '" + childAgreement
					+ "' does not have an associated opportunity");
		}
		childOpportunityNumber = viewServiceContractPage
				.getValueFromGUI("opportunityNumber");

		agreementNumberInChildAgreement = viewServiceContractPage
				.getValueFromGUI("agreementNumber");
		additionalAgreementNumbersInChildAgreement = viewServiceContractPage
				.getValueFromGUI("additionalAgreementNumber");

		ArrayList<String> opportunityNumberArray = new ArrayList<String>(
				Arrays.asList(masterOpportunityNumber, childOpportunityNumber));
		Util.printInfo("Closing the Opty...");
		commonPage.click("editButton");
		createSubscriptionRenewalPage.populateInstance("EDIT_OPTY_OVERLAY");
		commonPage.clickToSubmit("saveButton", "discountApprovalRequestButton");
		String accName = viewOpptyPage.getValueFromGUI("accountName");
		String optyCloseDate = viewOpptyPage
				.getValueFromGUI("projectedCloseDate");
		mainWindow.select();
		commonPage.click("tabSubscriptionsBeta");
		createSubscriptionRenewalPage.waitForFieldPresent("optionsButton");
		createSubscriptionRenewalPage.populateField("accountsFilter",
				"All Accounts");
		createSubscriptionRenewalPage.populateField("accountName", accName);
		createSubscriptionRenewalPage.populateField("expiryFromDate", "");
		createSubscriptionRenewalPage.populateField("expiryToDate",
				optyCloseDate);
//		createSubscriptionRenewalPage.clickToSubmit("searchButton", "");
		EISTestBase.driver.findElement(By.xpath("//input[contains(@id,'goSearch')]")).click();
		createSubscriptionRenewalPage.acceptAlert();
		Util.sleep(5000);
		createSubscriptionRenewalPage
				.waitForFieldEnabled("contractTableAgreementNumberFirstRow");
		return opportunityNumberArray;

	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	
}
