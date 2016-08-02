package common;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.teamdev.jxbrowser.webkit.cocoa.mactypes.UniChar;

import common.exception.MetadataException;

/**
 * Representation of an SFDC Case.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class Case extends SFDCObject {
	/**
	 * Indicator of whence a case is being created 
	 */
	WebDriver driver=EISTestBase.driver; 
	public static enum CreateFrom {
		SFDC_CONTACT, SFDC_CASES_TAB,			//These initiate case creation through the SFDC interface
		PC_PORTAL, SC_PORTAL,
		QUOTES_PAGE,SSP,WEBFORM1,				
		WEBFORM2, PROD_SUPPORT
	}

	/**
	 * Indicator of the type of case being created 
	 */
	public static enum CaseType {
		//The comments specify which interfaces support each case type.  Please keep them maintained!
		//		(SFDC = standard SFDC interface, SC = Sub Center portal, PC = Partner Center portal)
		//  Note that a case type may not always be available in the given interface, depending on
		//  user.  For example, Marc Durand (CSN 19070960) can create an API_SUPPORT case in SC
		//  but not a TECH_SUPPORT.  For other SC users, it is vice versa.
		PROD_LIC_REG_AND_ACT,				//		SC	PC
		SW_DOWNLOAD,						//		SC
		CHANGE_SW_COORD_OR_CONTRACT_MGR,	//		SC	
		MY_CONTRACT_INFO,					//SFDC	SC
		SUB_PROGRAM_INFO,					//		SC
		API_SUPPORT,						//SFDC	SC
		SUBMIT_ORDER,						//			PC
		SUBMIT_RETURNS_CREDITS,				//			PC
		STATUS_ORDERS_RETURNS_CREDITS,		//			PC
		SUB_CONTRACT_INQUIRY,				//			PC
		TECH_SUPPORT,						//SFDC	SC	PC
		CRM_SUPPORT,						//SFDC		PC
		BUSINESS_SVC_REQUEST,
		PROD_SUPPORT,						//SFDC	SC
		INSTAL_CONFIG_ISSUE_CASE,			// 		SC
		IDEAS_SUGGESTION_CASE,				//		SC
		OTHER,							//		SC	PC
		DEV
	}	

	//State variables 
	//  Some (like name, id, and url) are defined in the superclass
	//  Define only object-specific ones here
	private CreateFrom createFrom				= null;
	private CaseType caseType					= null;
	private String number						= "";
	private String type							= "";
	private String accountName					= "";
	private String contactName					= "";
	private String customerContactName			= "";
	private String customerContactEmailAddress	= "";
	private String supportedAssetSerialNum		= "";
	private String supportedAsset				= "";
	private String supportedProduct				= "";
	private String productLanguage				= "";
	private String reason						= "";
	private String severity						= "";
	private String operatingSystem				= "";
	private String slaHours						= "";
	private String api							= "";
	private String serialNumber					= "";
	private String subject						= "";
	private String description					= "";
	private String origin						= "";
	private String status						= "";
	private String poNumber						= "";
	private String productTotal					= "";
	private String subscriptionTotal			= "";
	private String caseCurrency					= "";
	private String applicationType				= "";
	private String functionalArea				= "";
	private String businessArea					= "";
	private String caseArea						= "";
	private String industry						= "";
	private String releaseVersion				= "";
	private String numberOfSeats				= "";
	private String versionRequested				= "";
	private String industryType					= "";
	private String group						= "";
	private String caseNumber					= "";

	//Make all Page objects private, but provide getters so that test code can have read
	//  access to them.  That will allow test code to access test data, metadata, constants,
	//  etc., through this SFDCObject instance.  That's important, because we don't want
	//  test code authors to declare Page objects when they don't need to; instead we want
	//  test code to call utilities wherever possible.  Those utilities typically create an
	//  SFDCObject, instantiate the Page objects it needs, and pass back the SFDCObject
	//  instance.  It is through that instance that test code can get access to this
	//  object's Page data

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	private Page_ createCaseRTPage;
	private Page_ createCasePage;
	private Page_ viewCasePage;
	private Page_ viewContactPage;
	private Page_ commonPortalPage;
	private Page_ closeCasePage;
	private Page_ viewCaseInPortalPage;
	private Page_ productPopUpPage;
	
	/**
	 * Class constructor specifying the Page objects necessary for interacting with an SFDC Case,
	 * including creating it from a contact page, and through either the SFDC or a portal interface.
	 * @param createCaseRTPage the Page object used for specifying the record type when creating the SFDC Case
	 * through the SFDC interface
	 * @param createCasePage the Page object used for creating the SFDC Case
	 * @param viewCasePage the Page object used for viewing the SFDC Case in the SFDC interface
	 * @param viewContactPage the Page object used as a starting point when creating the SFDC Case
	 * @param commonPortalPage the Page object used for specifying the topic when creating the SFDC Case
	 * through a portal interface
	 * @param viewCaseInPortalPage the Page object used for viewing the SFDC Case in a portal interface
	 */
	public Case(Page_ createCaseRTPage, Page_ createCasePage, Page_ viewCasePage, Page_ viewContactPage, Page_ commonPortalPage, Page_ viewCaseInPortalPage, Page_ productPopUpPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_CASE, EISConstants.OBJECT_TYPE_CASE);
		
		//mainWindow					= EISTestBase.mainWindow;
		//commonPage					= EISTestBase.commonPage;

		this.createCaseRTPage		= createCaseRTPage;
		this.createCasePage			= createCasePage;
		this.viewCasePage			= viewCasePage;
		this.viewContactPage		= viewContactPage;
		this.commonPortalPage		= commonPortalPage;
		this.viewCaseInPortalPage	= viewCaseInPortalPage;
		this.productPopUpPage		= productPopUpPage;
		
		//The case name is not entered when the case is created.  We will set the name when we create it
	}

	public Case(Page_ createCaseRTPage, Page_ createCasePage, Page_ viewCasePage, Page_ viewContactPage, Page_ commonPortalPage, Page_ viewCaseInPortalPage, Page_ productPopUpPage, Page_ closeCasePage) {
		this.createCaseRTPage		= createCaseRTPage;
		this.createCasePage			= createCasePage;
		this.viewCasePage			= viewCasePage;
		this.viewContactPage		= viewContactPage;
		this.commonPortalPage		= commonPortalPage;
		this.viewCaseInPortalPage	= viewCaseInPortalPage;
		this.closeCasePage			= closeCasePage;
		this.productPopUpPage		= productPopUpPage;
		
	}
	
	/**
	 * Class constructor specifying the Page objects necessary for interacting with an SFDC Case,
	 * including creating it from a contact page.
	 * @param createCaseRTPage the Page object used for specifying the record type when creating the SFDC Case
	 * through the SFDC interface
	 * @param createCasePage the Page object used for creating the SFDC Case
	 * @param viewCasePage the Page object used for viewing the SFDC Case in the SFDC interface
	 * @param viewContactPage the Page object used as a starting point when creating the SFDC Case
	 */
	public Case(Page_ createCaseRTPage, Page_ createCasePage, Page_ viewCasePage, Page_ viewContactPage) {
		this(createCaseRTPage, createCasePage, viewCasePage, viewContactPage, null, null, null);
	}

	/**
	 * Gets the number.
	 * @return The case number
	 */
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Gets the type.
	 * @return The type of the case
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the interface from which the Case is created.
	 * @return An enumerated value that specifies the source of the precipitating action
	 * @see SSConstants.CreateFrom
	 */
	public CreateFrom getCreateFrom() {
		return createFrom;
	}

	/**
	 * Gets the type of Case.
	 * @return An enumerated value that specifies the type of the case
	 * @see SSConstants.CaseType
	 */
	public CaseType getCaseType() {
		return caseType;
	}

	/**
	 * Gets the Account name.
	 * @return The name of the Account object associated with the case
	 */
	public String getAccountName() {
		return accountName;
	}
	
	/**
	 * Gets the Contact name.
	 * @return The name of the Contact object associated with the case
	 */
	public String getContactName() {
		return contactName;
	}

	public String getCustomerContactName() {
		return customerContactName;
	}

	public String getCustomerContactEmailAddress() {
		return customerContactEmailAddress;
	}

	public String getSupportedAssetSerialNum() {
		return supportedAssetSerialNum;
	}

	public String getSupportedAsset() {
		return supportedAsset;
	}

	public String getSupportedProduct() {
		return supportedProduct;
	}

	public String getProductLanguage() {
		return productLanguage;
	}

	public String getReason() {
		return reason;
	}

	public String getSeverity() {
		return severity;
	}

	public String getSlaHours() {
		return slaHours;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public String getApi() {
		return api;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getSubject() {
		return subject;
	}

	public String getDescription() {
		return description;
	}

	public String getOrigin() {
		return origin;
	}
	
	public String getStatus() {
		return status;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public String getProductTotal() {
		return productTotal;
	}

	public String getSubscriptionTotal() {
		return subscriptionTotal;
	}

	public String getCaseCurrency() {
		return caseCurrency;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public String getFunctionalArea() {
		return functionalArea;
	}

	public String getBusinessArea() {
		return businessArea;
	}

	public String getCaseArea() {
		return caseArea;
	}

	public String getIndustry() {
		return industry;
	}

	public String getReleaseVersion() {
		return releaseVersion;
	}

	public String getNumberOfSeats() {
		return numberOfSeats;
	}

	public String getVersionRequested() {
		return versionRequested;
	}

	public String getIndustryType() {
		return industryType;
	}

	public String getGroup() {
		return group;
	}

	/**
	 * Gets the Page object used for specifying the record type when creating the SFDC Case
	 * through the SFDC interface
	 * @return The Page object that defines objects	used when specifying the record type
	 * when creating the SFDC Case through the SFDC interface
	 */
	Page_ getCreateCaseRTPage() {
		return createCaseRTPage;
	}
	
	/**
	 * Gets the Page object used for creating the SFDC Case
	 * @return The Page object that defines objects	used when creating the SFDC Case
	 */
	public Page_ getCreateCasePage() {
		return createCasePage;
	}
	
	/**
	 * Gets the Page object used for viewing the SFDC Case in the SFDC interface
	 * @return The Page object that defines objects	used when viewing the SFDC Case in the
	 * SFDC interface
	 */
	public Page_ getViewCasePage() {
		return viewCasePage;
	}

	/**
	 * Gets the Page object used for viewing the SFDC Contact associated with the SFDC Case
	 * @return The Page object that defines objects	used when viewing an SFDC Contact
	 */
	Page_ getViewContactPage() {
		return viewContactPage;
	}

	/**
	 * Gets the Page object used for specifying the topic when creating the SFDC Case
	 * through a portal interface
	 * @return The Page object that defines objects	used when specifying the topic
	 * when creating the SFDC Case through a portal interface
	 */
	public Page_ getCommonPortalPage() {
		return commonPortalPage;
	}
	
	/**
	 * @return the closeCasePage
	 */
	public Page_ getCloseCasePage() {
		return closeCasePage;
	}


	/**
	 * Gets the Page object used for viewing the SFDC Case in a portal interface
	 * @return The Page object that defines objects	used when viewing the SFDC Case in a
	 * portal interface
	 */
	public Page_ getViewCaseInPortalPage() {
		return viewCaseInPortalPage;
	}

	/**
	 * Gets the Page object used for selecting a product when creating the SFDC Case in
	 * the Partner Center portal interface
	 * @return The Page object used for selecting a product when creating the SFDC Case in
	 * the Partner Center portal interface
	 */
	public Page_ getProductPopUpPage() {
		return productPopUpPage;
	}
			
	@Override
	public String toString() {
		return "Case [super=" + super.toString() +
				", createFrom=" + createFrom +
				", caseType=" + caseType +
				", number=" + number +
				", accountName=" + accountName +
				", contactName=" + contactName +
				", customerContactName=" + customerContactName +
				", customerContactEmailAddress=" + customerContactEmailAddress +
				", supportedAssetSerialNum=" + supportedAssetSerialNum +
				", supportedAsset=" + supportedAsset +
				", supportedProduct=" + supportedProduct +
				", productLanguage=" + productLanguage +
				", reason=" + reason +
				", severity=" + severity +
				", slaHours=" + slaHours +
				", operatingSystem=" + operatingSystem +
				", api=" + api +
				", serialNumber=" + serialNumber +
				", subject=" + subject +
				", description=" + description +
				", origin=" + origin +
				", status=" + status +
				", poNumber=" + poNumber +
				", productTotal=" + productTotal +
				", subscriptionTotal=" + subscriptionTotal +
				", caseCurrency=" + caseCurrency +
				", applicationType=" + applicationType +
				", functionalArea=" + functionalArea +
				", businessArea=" + businessArea +
				", caseArea=" + caseArea +
				", industry=" + industry +
				", releaseVersion=" + releaseVersion +
				", numberOfSeats=" + numberOfSeats +
				", versionRequested=" + versionRequested +
				", industryType=" + industryType +
				", group=" + group +
				"]";
	}

	/**
	 * Opens the case in the SFDC interface, if it is not already open.
	 * @return An indication of whether the case was actually opened
	 */
	@Override
	public boolean open() {
		boolean pageLoaded;
		
		mainWindow.select();
		
		pageLoaded = super.open();
		if (pageLoaded) {
			waitForPageToSettle();
		}
		
		return pageLoaded;
	}
	
	/**
	 * Opens the case in the portal, if it is not already open.
	 * @return An indication of whether the case was actually opened
	 */
	@Override
	public boolean openInPortal() {
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
	 * Creates an SFDC Case
	 * @param createFrom an enumerated value that specifies the source of the precipitating action
	 * @param caseType an enumerated value that specifies the type of the case
	 * @return The name of the case
	 * @see SSConstants.CreateFrom
	 * @see SSConstants.CaseType
	 */
	public String create(CreateFrom createFrom, CaseType caseType) {
		String caseName = "";
		
		this.createFrom = createFrom;
		this.caseType = caseType;
		
		switch (createFrom) {
			case PC_PORTAL:	{
				openCreateCaseFormInPortal();
				caseName = createInPCPortal();
				break;
			}
			case SC_PORTAL:	{
				openCreateCaseFormInPortal();
				caseName = createInSCPortal();
				break;
			}
			case SFDC_CONTACT:	{
				if(caseType.toString().equalsIgnoreCase("API_SUPPORT")){
				caseName = createAPICaseFromContact();
				}
				else if(caseType.toString().equalsIgnoreCase("DEV")){
					caseName = createCaseInDEVFromContact();
					}
				else{
				caseName = createFromContact();
				}
				break;
			}
			case SFDC_CASES_TAB:	{
				caseName = createFromCasesTab();
				break;
			}	
			case QUOTES_PAGE: 		{
				caseName = createFromQuotes();
				break;
			}
			case SSP:	{
				caseName = createInSSP();
				break;
			}
			case WEBFORM1:	{
				caseName = createFromWebForm();
				break;
			}
			case WEBFORM2:	{
				caseName = createFromWebForm2();
				break;
			}
			case PROD_SUPPORT:	{
				caseName = createProdSuppCaseInSC(caseType);
				break;
			}
			default:	{
				EISTestBase.fail("Unhandled member of ss.SSConstants.CreateFrom enumerated type: " + createFrom);
			}
		}
		
		return caseName;
	
	}
	
	
	private String createFromQuotes(){
		Util.printInfo("Creating case from the Quote...");
		mainWindow.select();
		createInSFDCInterface();
		Util.printInfo("Created case '" + getName() + "' (" + getUrl() + ") from the View Quote Page");
		return getName();
	}
	private String createInSSP(){
		String caseNo;
		String[] caseNum;
		Util.printInfo("Creating case from SSP...");
		mainWindow.select();
		createCasePage.click("logACase");
		createCasePage.populate();
		commonPortalPage.clickToSubmit("submitButton","addCommentButton");
		caseNo=commonPortalPage.getValueFromGUI("caseNumber");
		caseNum = caseNo.split(" ");
		caseNumber=caseNum[1];
		Util.printInfo("Created case with case number =" +caseNumber);
		commonPortalPage.click("addCommentButton");
		commonPortalPage.populateInstance("ADD_COMMENT");
		commonPortalPage.click("submitButton");
		setUrl();
		setName(caseNumber);
		
		Util.printInfo("Created case '" + getName() + "' (" + getUrl() + ") from the SSP Page");
		return getName();
	}
	
	private String createFromWebForm(){
		Util.printInfo("Creating case from Web Form...");
		mainWindow.select();
		createCasePage.populateInstance("PRE_SELECT");
		createCasePage.click("yourSerialNumber");
		createCasePage.populate();
		createCasePage.populateInstance("CHECK_THE_BOX");
		createCasePage.clickAndWait("nextButton","attachFileText");
		boolean caseStatus=createCasePage.isTextPresent("Attachments");
		if(caseStatus){
		Util.printInfo("Created Case Successfully");
		}
		return getName();
	}
	
	private String createFromWebForm2(){
		Util.printInfo("Creating case from Web Form...");
		mainWindow.select();
		createCasePage.populate();
		createCasePage.populateInstance("CHECK_THE_BOX");
		createCasePage.clickAndWait("submitCase","thankYouText");
		boolean caseStatus=createCasePage.isFieldPresent("thankYouText");
		if(caseStatus){
		Util.printInfo("Created Case Successfully");
		}
		return getName();
	}


	/**
	 * Creates an SFDC Case from the Cases tab.
	 * @return The name of the case
	 */
	private String createFromCasesTab() {
     	Util.printInfo("Creating case from the Cases tab...");

		mainWindow.select();
		
		//commonPage.clickAndWait("tabCases");
		commonPage.clickAndWait("tabCases", "newButton");

		//Ensure that we wait for the page to load, so that createFromSFDCInterface can make a valid check for the presence
		//  of the recordType field
		commonPage.clickToSubmit("newButton");

		createInSFDCInterface();
     	
     	Util.printInfo("Created case '" + getName() + "' (" + getUrl() + ") from the Cases tab");
     	
     	return getName();
	}

	/**
	 * Creates an SFDC Case from a contact page.
	 * @return The name of the case
	 */
	
	private String createFromContact() {
		//It is assumed that the contact page is open
		
     	Util.printInfo("Creating case from contact page...");

		mainWindow.select();
		
		//Ensure that we wait for the page to load, so that createFromSFDCInterface can make a valid check for the presence
		//  of the recordType field
		viewContactPage.clickToSubmit("newCaseButton");

		createInSFDCInterface();
     	
     	Util.printInfo("Created case '" + getName() + "' (" + getUrl() + ") from contact page");
     	
     	return getName();
	}
	private String createCaseInDEVFromContact() {
		//It is assumed that the contact page is open
		
     	Util.printInfo("Creating case from contact page...");

		mainWindow.select();
		
		//Ensure that we wait for the page to load, so that createFromSFDCInterface can make a valid check for the presence
		//  of the recordType field
		String userTab=commonPage.createFieldWithParsedFieldLocatorsTokens("userNameTab",EISTestBase.testProperties.getConstant("CONTACT_NAME"));
		commonPage.click(userTab);
		EISTestBase.swithToIframeWhereMyFieldExist(viewContactPage,"newCaseButton");
		viewContactPage.clickToSubmit("newCaseButton");
		EISTestBase.driver.switchTo().defaultContent();
		EISTestBase.swithToIframeWhereMyFieldExist(createCasePage,"caseType");
		
		createCaseInSFDCAndValidate();
		
		     	
     	return getName();
	}
	public Map<String, String> createCaseInSFDCAndValidate(){
		Map<String, String> caseDetails=new TreeMap<String, String>();
		
//		createCasePage.populate();
		createCasePage.click("caseSubmitButton");
		Util.sleep(5000);
		
		Alert alert = EISTestBase.driver.switchTo().alert();
		//Text displayed on Alert using getText() method of Alert class
		String AlertText = alert.getText();
//		EISTestBase.swithToIframeWhereMyFieldExist(createCasePage, "alertMsg");
//		String alertMsg=createCasePage.getAlertMessage();
		Util.printInfo(AlertText);
		EISTestBase.assertTrue("Alert Message is displayed-"+AlertText,true);
		alert.accept();
//		createCasePage.verifyFieldVisible("alertMsg");
		//Validations 
//		EISTestBase.swithToIframeWhereMyFieldExist(createCasePage,"caseType");
		EISTestBase.assertTrue("By default orgin is selected as Phone",viewCasePage.checkFieldExistence("IsCaseOrginSelectedAsPhone"));
		 		
 		String contactNameXpath=viewCasePage.createFieldWithParsedFieldLocatorsTokens("IsContactNameDisplayed", EISTestBase.testProperties.getConstant("CONTACT_NAME"));
 		System.out.println(viewCasePage.getFieldLocators(contactNameXpath));
 		EISTestBase.assertTrue("Contract Name is prepopulated",viewCasePage.checkFieldExistence(contactNameXpath));
 		
 		String accountNameXpath=viewCasePage.createFieldWithParsedFieldLocatorsTokens("IsAccountNameDisplayed", EISTestBase.testProperties.getConstant("ACCOUNT_NAME"));
 		System.out.println(viewCasePage.getFieldLocators(accountNameXpath));
 		EISTestBase.assertTrue("Account Name is prepopulated",viewCasePage.checkFieldExistence(accountNameXpath));
 		
 			
 		//Eneter data and create Case
		String caseSubject="AutomationCase"+getUniqueString(5);
		createCasePage.setTestDataValue("caseSubject", caseSubject);
//		createCasePage.populateField("caseType", EISTestBase.testProperties.getConstant("CASETYPE"));
//     	createCasePage.populateField("caseTopic", EISTestBase.testProperties.getConstant("CASETOPIC"));
//     	createCasePage.populateField("caseSubject",caseSubject );
		createCasePage.populate();
 		createCasePage.click("caseSubmitButton");
 		Util.sleep(5000);
 		viewCasePage.waitForField("newCaseNumber", true);
 		String newCaseNumber=viewCasePage.getValueFromGUI("newCaseNumber");
 		if (!newCaseNumber.equalsIgnoreCase("")){
 			EISTestBase.assertTrue("Case Createdf Sucessfully. Case Number: "+newCaseNumber , true);
 			caseDetails.put("CaseNumber", newCaseNumber);
 			caseDetails.put("CaseSubject", caseSubject);
 		}else{
 			EISTestBase.failTest("Case creation Failed");
 		}
 		
 		// validate title
 		String titleXpath=viewCasePage.createFieldWithParsedFieldLocatorsTokens("IsTitlewithSubjectName", caseSubject);
 		System.out.println(viewCasePage.getFieldLocators(titleXpath));
 		EISTestBase.assertTrue("Title is Displayed with Subject name which is given while case creation. Expected: "+caseSubject,viewCasePage.checkFieldExistence(titleXpath));
 		
 		//validate status
 		String status=EISTestBase.testProperties.getConstant("STATUS");
 		String statusXpath=viewCasePage.createFieldWithParsedFieldLocatorsTokens("IsStatusDisplayed", status);
 		EISTestBase.assertTrue("Status is displayed as expected. Expected: ",viewCasePage.checkFieldExistence(statusXpath));
 		
 		//validate Severity
 		String severity=EISTestBase.testProperties.getConstant("SEVERITY");
 		String severityXpath=viewCasePage.createFieldWithParsedFieldLocatorsTokens("IsSeverityDisplayed", severity);
 		System.out.println(viewCasePage.getFieldLocators(severityXpath));
 		EISTestBase.assertTrue("Severity is displayed as expected. Expected: "+severity,viewCasePage.checkFieldExistence(severityXpath));
 		
 		//Validate Case Owner 
 		String[] userName=EISTestBase.getCurrentUserName().split("@");
 		String caseOwnerName=userName[0];
 		String caseOwnerXpath=viewCasePage.createFieldWithParsedFieldLocatorsTokens("IsCaseOwnerDisplayed", caseOwnerName);
 		System.out.println(viewCasePage.getFieldLocators(caseOwnerXpath));
// 		EISTestBase.assertTrue("Title is Displayed with Subject name which is given while case creation",viewCasePage.checkFieldExistence(severityXpath));
 		
		return caseDetails;
	}	
	public String getUniqueString(int lenght) {
		return RandomStringUtils.random(lenght, true, true);
	}
	/*public void swithToIframeWhereMyFieldExist(Page_ page, String field ){
		
		List<WebElement> framesList=null;
		try {
			framesList = commonPage.getMultipleWebElementsfromField("iframesList");
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<framesList.size();i++){
			String frameID=framesList.get(i).getAttribute("id");
			EISTestBase.driver.switchTo().frame(frameID);
			if (page.checkFieldExistence(field)){
				break;
			}else{
				EISTestBase.driver.switchTo().defaultContent();
			}
			
		}
		
	}*/
	
	private String createAPICaseFromContact() {
		//It is assumed that the contact page is open
		
     	Util.printInfo("Creating case from contact page...");

		mainWindow.select();
		
		//Ensure that we wait for the page to load, so that createFromSFDCInterface can make a valid check for the presence
		//  of the recordType field
		viewContactPage.clickToSubmit("newCaseButton");

		createAPISupportInSFDCInterface();
     	
     	Util.printInfo("Created case '" + getName() + "' (" + getUrl() + ") from contact page");
     	
     	return getName();
	}
	/**
	 * Creates an SFDC Case in the Sub Center portal.
	 * @return The name of the case
	 */
	private String createInSCPortal() {
		//It is assumed that the create case form is open in the Sub Center portal 
		
     	Util.printInfo("Creating case in Sub Center portal...");

		mainWindow.select();
		
     	if (caseType == CaseType.TECH_SUPPORT) {
	     	//Special handling for Product pop-up (it may apply to other case types as well...)
			String locator = createCasePage.clickAndWaitForPopUpToOpen("product");
			productPopUpPage.selectWindow(locator);
			//productPopUpPage.parseFieldLocatorsTokens("productLineLink", productPopUpPage.getConstant("PRODUCT"));
			//productPopUpPage.click("productLineLink");
			String fieldName = productPopUpPage.createFieldWithParsedFieldLocatorsTokens("productLineLink",productPopUpPage.getConstant("PRODUCT"));
			productPopUpPage.click(fieldName);

			mainWindow.select();
     	}
     	if (caseType == CaseType.PROD_SUPPORT) {
     		EISTestBase.open("https://autodesk--adsksfdev.cs12.my.salesforce.com/apex/Case_CustomerPortalLandingPage#");
     		createCasePage.click("productSupportCase");
     		
     	}
		
		createCasePage.populate();
		
		setStateVariables();
     	
		//commonPortalPage.click("saveOrSubmitButton");
     	//if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.API_SUPPORT)) {
     	//	commonPortalPage.waitForElementVisible("addCommentButton");
     	//} else {
     	//	//PROD_LIC_REG_AND_ACT - haven't tried others
    	//	commonPortalPage.waitForElementVisible("OKButton");
    	//	
    	//	commonPortalPage.click("OKButton");
    	//	commonPortalPage.waitForElementVisible("requestCloseButton");
     	//}
     	
     	if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.API_SUPPORT)) {
     		commonPortalPage.clickToSubmit("saveOrSubmitButton", "addCommentButton");
     	} else {
     		//PROD_LIC_REG_AND_ACT - haven't tried others
     		commonPortalPage.clickToSubmit("saveOrSubmitButton", "OKButton");
     		commonPortalPage.clickToSubmit("OKButton", "requestCloseButton");
     	}
     	
     	setPortalUrl();
     	
     	if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.API_SUPPORT)) {
         	setUrl(getPortalUrl().replace("apex/p1234_overridecaseviewpagecustomer?id=", "").replace("&mode=view", ""));
     	} else {
     		//PROD_LIC_REG_AND_ACT - haven't tried others
         	setUrl(getPortalUrl().replace("apex/OverrideCaseView?id=", "").replace("&sfdc.override=1", ""));
     	}
     	
     	setId();
     	
     	//TECH_SUPPORT, API_SUPPORT, and PROD_LIC_REG_AND_ACT - haven't tried others
     	number = viewCaseInPortalPage.getValueFromGUI("caseNumber");
     	
     	//There is no case name per se, so use the number
     	setName(number);
     	
     	//If the case is TECH_SUPPORT or API_SUPPORT, the owner name is available on viewCaseInPortalPage;
     	//  it is not for PROD_LIC_REG_AND_ACT - haven't tried others
     	if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.API_SUPPORT)) {
     		setOwner(viewCaseInPortalPage.getValueFromGUI("ownerName"));
     	}
     	Util.sleep(5000);
     	Util.printInfo("Created case '" + getName() + "' (" + getPortalUrl() + ") in Sub Center portal");
     	Util.sleep(5000);
     	return getName();
	}
	/**
	 * Creates an SFDC Case in the Partner Center portal.
	 * @return The name of the case
	 */
	private String createInPCPortal() {
		//It is assumed that the create case form is open in the Partner Center portal
		String supportedAssetFieldName = "";
		
     	Util.printInfo("Creating case in Partner Center portal...");

		mainWindow.select();
		
		createCasePage.populate();
		
		//For whatever reason, in the Test_CreateTechSupportCaseInPC test, populating this field (as is
		//  done in the createCasePage.populate() call above) usually does not cause supportedProductText
		//  to be automatically populated.  I have tried many workarounds, but this seems to be the only
		//  fix.  One thing I haven't tried is interrogating the link we click in the lookup results
		//  table to see if we are handling the Javascript correctly - see GUI.doClick().  But the
		//  problem is that those links are very different from the ones we usually deal with!!!
		//  (See emails to and from S&S team on 08/08/2012 for a better description of the problem)
		if (createCasePage.isFieldPresent("supportedAssetSerialNum")) {
			supportedAssetFieldName = "supportedAssetSerialNum";
		} else if (createCasePage.isFieldPresent("supportedAsset")) {
			supportedAssetFieldName = "supportedAsset";
		}
		
		if (!supportedAssetFieldName.isEmpty()) {
			if (createCasePage.getValueFromGUI("supportedProductText").isEmpty()) {
				createCasePage.populateField(supportedAssetFieldName);
			}
		}

/*		//Just entering a value into supportedProductText causes an error when we save the form
		//  ("Product is required to create a case")
		//String productValue = createCasePage.getValueFromGUI("supportedProductText");
		//if (productValue.isEmpty()) {
		//	String assetValue = createCasePage.getValueFromGUI("supportedAssetText");
		//	createCasePage.populateField("supportedProductText", assetValue);
		//}
*/		

/*		String productValue = createCasePage.getValueFromGUI("supportedProductText");
		if (productValue.isEmpty()) {
			String assetValue = createCasePage.getValueFromGUI("supportedAssetText");
			createCasePage.populateField("supportedProduct", assetValue);
		}
*/
		
		//commonPortalPage.click("saveOrSubmitButton");
     	//if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.CRM_SUPPORT)) {
     	//	commonPortalPage.waitForElementVisible("addCommentButton");
     	//} else {
     	//	//SUBMIT_ORDER - haven't tried others
    	//	commonPortalPage.waitForElementVisible("OKButton");
    	//	
    	//	commonPortalPage.click("OKButton");
    	//	commonPortalPage.waitForElementVisible("requestCloseButton");
     	//}
     	
 		setStateVariables();
 		
     	if ((caseType == CaseType.TECH_SUPPORT) || (caseType == CaseType.CRM_SUPPORT)) {
     		commonPortalPage.clickToSubmit("saveOrSubmitButton", "addCommentButton");
     	} else {
     		//SUBMIT_ORDER - haven't tried others
     		commonPortalPage.clickToSubmit("saveOrSubmitButton", "OKButton");
     		commonPortalPage.clickToSubmit("OKButton", "requestCloseButton");
     	}
     	
     	setPortalUrl();
     	
     	//TECH_SUPPORT, SUBMIT_ORDER, and CRM_SUPPORT - haven't tried others
        setUrl(getPortalUrl().replace("apex/OverrideCaseView?id=", "").replace("&sfdc.override=1", ""));

     	setId();
     	
     	//TECH_SUPPORT, SUBMIT_ORDER, and CRM_SUPPORT - haven't tried others
     	number = viewCaseInPortalPage.getValueFromGUI("caseNumber");
     	
     	//There is no case name per se, so use the number
     	setName(number);
     	
     	Util.printInfo("Created case '" + getName() + "' (" + getPortalUrl() + ") in Partner Center portal");
     	
     	return getName();
	}

	public void openCreateCaseFormInPortal() {
		//It is assumed that the Sub Center or Partner Center portal is open

		mainWindow.select();
		
		EISTestBase.switchDriverToFrame(2);
		commonPortalPage.click("casesLink");
		mainWindow.select();
		
		//commonPortalPage.click("createNewCaseButton");
		//commonPortalPage.waitForElementVisible("selectButton");
		//commonPortalPage.clickToSubmit("createNewCaseButton", "selectButton");
		commonPortalPage.clickAndWaitForFieldPresent("createNewCaseButton", "selectButton");
		
		//Choose the case type (note that we are assuming there will be no test data for commonPortalPage
		//  other than one of the topic radio buttons!)
		commonPortalPage.populate();
		
		//commonPortalPage.click("selectButton");
		//commonPortalPage.waitForElementVisible("saveOrSubmitButton");
		commonPortalPage.clickToSubmit("selectButton", "saveOrSubmitButton");
		
		//Page redraws can be MUCH slower in the portal because the screen does a time-consuming
		//  refresh after the howCanWeHelpYou pick list is populated
		//createCasePage.setPageRedrawDelay(createCasePage.getPageRedrawDelay() * 2);
		createCasePage.setPageRedrawDelay(EISConstants.DEFAULT_PAGE_REDRAW_LONG_DELAY * 2);
	}
	
	/**
	 * Creates an SFDC Case in the SFDC interface.
	 * @return The name of the case
	 */
	private  void createInSFDCInterface() {
     	if (createCaseRTPage.isFieldPresent("recordType")) {
     		createCaseRTPage.populate();
     		
     		//Cannot use the value we entered as a record type as the case type,
     		//  because the two values are not always an exact match 
     		//setType(createCaseRTPage.getValueFromGUI("recordType"));
     		
     		//createCaseRTPage.clickAndWait("continueButton");
     		createCaseRTPage.click("continueButton");
     		
     		//The problem with this is that it waits for privacyStatement, which never goes away
     		//commonPage.waitForPageToSettle();

     		commonPage.waitForFieldVisible("saveButton");		//triggers a VM crash
     		//commonPage.isFieldPresent("saveButton");			//does not trigger a VM crash
     	}

 		createCasePage.populate();
     	

 		setStateVariables();

     	//NOTE that we had a timing issue here for a bit (page was seen to be loaded too early);
     	//  keep an eye on it...
     	//commonPage.clickToSubmit("saveButton");
//     	commonPage.clickToSubmit("saveButton", "editButton");

     	setUrl();
     	setId();
     	
     	//Because of the very odd way that case number is rendered in the GUI, the value returned
     	//  by getValueFromGUI contains superfluous text; let's get rid of it
     	commonPage.click("caseDetails");
     	number = viewCasePage.getValueFromGUI("caseNumber");
     	number = Util.left(number, " ");
     	
     	//There is no case name per se, so use the number
     	setName(number);
     	
     	setOwner(EISTestBase.getCurrentUserName());
	}
	private  void createAPISupportInSFDCInterface() {
     	if (createCaseRTPage.isFieldPresent("recordType")) {
     		createCaseRTPage.populate();
     		
     		//Cannot use the value we entered as a record type as the case type,
     		//  because the two values are not always an exact match 
     		//setType(createCaseRTPage.getValueFromGUI("recordType"));
     		
     		//createCaseRTPage.clickAndWait("continueButton");
     		createCaseRTPage.click("continueButton");
     		
     		//The problem with this is that it waits for privacyStatement, which never goes away
     		//commonPage.waitForPageToSettle();

     		commonPage.waitForFieldVisible("saveButton");		//triggers a VM crash
     		//commonPage.isFieldPresent("saveButton");			//does not trigger a VM crash
     	}

 		createCasePage.populate();

 		setStateVariables();

     	//NOTE that we had a timing issue here for a bit (page was seen to be loaded too early);
     	//  keep an eye on it...
     	//commonPage.clickToSubmit("saveButton");
     	commonPage.clickToSubmit("saveButton", "editButton");

     	setUrl();
     	setId();
     	
     	//Because of the very odd way that case number is rendered in the GUI, the value returned
     	//  by getValueFromGUI contains superfluous text; let's get rid of it
     	number = viewCasePage.getValueFromGUI("caseNumber");
     	number = Util.left(number, " ");
     	
     	//There is no case name per se, so use the number
     	setName(number);
     	
     	setOwner(EISTestBase.getCurrentUserName());
	}
	public String createProdSuppCaseInSC(CaseType caseType){
 	String contactName;
 	String welcomeTxt;
 	String clause;
 	clause=EISTestBase.testProperties.getConstant("DEFAULT_CHECK");
 	contactName=EISTestBase.testProperties.getConstant("CONTACT_NAME");
 	welcomeTxt="Welcome ".concat(contactName);
 	createCasePage.verifyFieldContains("welcomeMsg",welcomeTxt);
 	if(caseType==CaseType.PROD_SUPPORT){
 	Util.printInfo("Creating Product Support case in Sub Center portal...");
 	createCasePage.click("productSupportCase");

//	 if(clause.equalsIgnoreCase("Yes")){
//	 String getStatus = createCasePage.getAttribute("makeDefault1", "checked");
//	if(getStatus==null){
//	 		Util.printInfo("The make default check box is unchecked");
//	 		String xpath1 = "//*[@id='product_info_container']/ul/li/label/span";
//	 		EISTestBase.findElementByXpathAndClick(xpath1);
//	 	}
//	 	else if(getStatus.equalsIgnoreCase("true")){
//	 		Util.printInfo("The make default check box is checked");	
//	 	}
//	 }
 	}
 	else if(caseType==CaseType.API_SUPPORT){
 	 Util.printInfo("Creating API Support case in Sub Center portal...");
 	 createCasePage.click("apiSupportCase");
 	}
 	else if(caseType==CaseType.INSTAL_CONFIG_ISSUE_CASE){
 	 Util.printInfo("Creating Installation & Configuration case in Sub Center portal...");
 	 createCasePage.click("installConfigIssueCase");
 	 }
 	else if(caseType==CaseType.SW_DOWNLOAD){
 	 	 Util.printInfo("Creating Software Support case in Sub Center portal...");
 	 	 createCasePage.click("softwareDownloadsCase");
 	 	 createCasePage.populateInstance("CATEGORY");
 	 	 }
 	else if(caseType==CaseType.PROD_LIC_REG_AND_ACT){
	 	 Util.printInfo("Creating Registration & Activation case in Sub Center portal...");
	 	 createCasePage.click("regAndActivationCase");
	 	 createCasePage.populateInstance("CATEGORY");
	 	
	 	 }
 	else if(caseType==CaseType.IDEAS_SUGGESTION_CASE){
	 	 Util.printInfo("Creating Ideas & Suggestion case in Sub Center portal...");
	 	 createCasePage.click("ideasSuggestionCase");
	  	 }
 	else if(caseType==CaseType.CHANGE_SW_COORD_OR_CONTRACT_MGR){
	 	 Util.printInfo("Creating My Subscription case in Sub Center portal...");
	 	 createCasePage.click("mySubscriptionCase");
	 	 createCasePage.populateInstance("CATEGORY");
	 	 }
 	else if(caseType==CaseType.OTHER){
	 	 Util.printInfo("Creating Other case in Sub Center portal...");
	 	 createCasePage.click("otherCase");
	 	 }
 	Util.sleep(20000);
 	 createCasePage.populate();
 	if((caseType==CaseType.PROD_SUPPORT||caseType==CaseType.API_SUPPORT) && clause.equalsIgnoreCase("Yes")){
 	 	 String getStatus = createCasePage.getAttribute("makeDefault1", "checked");
 	 	if(getStatus==null){
 		 		Util.printInfo("The make default check box is unchecked");
 		 		String xpath1 = "//*[@id='product_info_container']/ul/li/label/span";
 		 		EISTestBase.findElementByXpathAndClick(xpath1);
 		 	}
 	 	 	else if(getStatus.equalsIgnoreCase("true")){
 	 	 		Util.printInfo("The make default check box is checked");	
 	 	 	}
 	 	 }
 	 clause=EISTestBase.testProperties.getConstant("ADD_DATA");
 	 if(clause.equalsIgnoreCase("Yes")){
 		createCasePage.click("addRequestCode");
 		createCasePage.populateField("requestCode2", "Request2");
 		createCasePage.click("addRequestCode");
 		createCasePage.click("removeRequestCode3");
 	 
 	 }
	createCasePage.click("submit");
	viewCaseInPortalPage.waitForFieldPresent("caseNo");
	setUrl();
 	setId();
 	Util.sleep(10000);
 	caseNumber=viewCaseInPortalPage.getValueFromGUI("caseNo");
 	if(caseNumber.isEmpty()){
 	failTest("Case is not created");
 	}
 	else{
 	setName(caseNumber);
  	Util.printInfo("Created case '" + getName() +  "' in Subscription Center portal");
  	Util.sleep(10000);
 	}
 	return getName();
 	
	}
 	
	protected void setStateVariables() {
		switch (caseType) {
			case TECH_SUPPORT:	{
		     	reason = createCasePage.getValueFromGUI("howCanWeHelpYou");
		     	severity = createCasePage.getValueFromGUI("severity");
		     	operatingSystem = createCasePage.getValueFromGUI("operatingSystem");
		     	subject = createCasePage.getValueFromGUI("subject");
		     	description = createCasePage.getValueFromGUI("description");
		     	slaHours = viewCasePage.getConstant("SLA_HOURS");
		     	
				switch (createFrom) {
					case PC_PORTAL:		{
						//As of August, 2012, these fields are no longer present
						//customerContactName = createCasePage.getValueFromGUI("customerContactName");
						//customerContactEmailAddress = createCasePage.getValueFromGUI("customerContactEmailAddress");

						//Note that the offshore team had commented this line.  My un-commenting it may
						//  result in unintended side-effects
					//	supportedAsset = createCasePage.getValueFromGUI("supportedAssetText");

						contactName = createCasePage.getValueFromGUI("contactNameText");
				     //	supportedAssetSerialNum = createCasePage.getTestDataValue("supportedAssetSerialNum");
				     	supportedProduct = createCasePage.getValueFromGUI("supportedProductText");
				     	break;
					}
					case SC_PORTAL:		{
				     	contactName = createCasePage.getValueFromGUI("contactNameInfo");
				     	supportedProduct = createCasePage.getValueFromGUI("productText");
				     	break;
					}
					case SFDC_CONTACT:{
						contactName = createCasePage.getValueFromGUI("contactNameText");
				     	supportedAssetSerialNum = createCasePage.getTestDataValue("supportedAssetSerialNum");
				     	supportedAsset = createCasePage.getValueFromGUI("supportedAssetText");
				     	supportedProduct = createCasePage.getValueFromGUI("supportedProductText");
				     	origin = createCasePage.getValueFromGUI("caseOrigin");
						break;
					}
					case SFDC_CASES_TAB:{
				     	contactName = createCasePage.getValueFromGUI("contactNameText");
				     	supportedAssetSerialNum = createCasePage.getTestDataValue("supportedAssetSerialNum");
				     	supportedAsset = createCasePage.getValueFromGUI("supportedAssetText");
				     	supportedProduct = createCasePage.getValueFromGUI("supportedProductText");
				     	origin = createCasePage.getValueFromGUI("caseOrigin");
				     	break;
					}
				}
				break;
			}
			case PROD_LIC_REG_AND_ACT:	{
				switch (createFrom) {
					case PC_PORTAL:		{
				     	numberOfSeats = createCasePage.getValueFromGUI("numberOfSeats");
				     	versionRequested = createCasePage.getValueFromGUI("versionRequested");
				     	//No break here - we want to fall through to handle fields that are common to both PC and SC
					}
					case SC_PORTAL:		{
				     	contactName = createCasePage.getValueFromGUI("contactNameInfo2");
				     	status = createCasePage.getValueFromGUI("status");
				     	severity = createCasePage.getValueFromGUI("severity");
				     	serialNumber = createCasePage.getValueFromGUI("serialNumber");
				     	subject = createCasePage.getValueFromGUI("subject");
				     	description = createCasePage.getValueFromGUI("description");
				     	slaHours = viewCasePage.getConstant("SLA_HOURS");
				     	break;
					}
					case SFDC_CONTACT:
					case SFDC_CASES_TAB:{
						fail("PROD_LIC_REG_AND_ACT cases are not supported in the SFDC interface");
				     	break;
					}
				}
				break;
			}
			case API_SUPPORT:	{
				switch (createFrom) {
					case PC_PORTAL:		{
						fail("API_SUPPORT cases are not supported in Partner Center");
						break;
					}
					case SC_PORTAL:		{
				     	contactName = createCasePage.getValueFromGUI("contactNameInfo");
				     	supportedProduct = createCasePage.getValueFromGUI("supportedProductList");
				     	releaseVersion = createCasePage.getValueFromGUI("releaseVersionList");
				     	productLanguage = createCasePage.getValueFromGUI("productLanguageList");
				     	operatingSystem = createCasePage.getValueFromGUI("operatingSystem");
				     	api = createCasePage.getValueFromGUI("apiList");
				     	subject = createCasePage.getValueFromGUI("subject");
				     	description = createCasePage.getValueFromGUI("description");
				     	break;
					}
					case SFDC_CONTACT:
					case SFDC_CASES_TAB:{
						//API_SUPPORT cases are supported through the SFDC interface, but we haven't defined any test
						//  properties or test cases yet
				     	break;
					}
				}
				break;
			}
			case SUBMIT_ORDER:	{
				switch (createFrom) {
					case PC_PORTAL:		{
				     	contactName = createCasePage.getValueFromGUI("contactNameInfo");
				     	type = createCasePage.getValueFromGUI("type");
				     	status = createCasePage.getValueFromGUI("status");
				     	severity = createCasePage.getValueFromGUI("severity");
				     	group = createCasePage.getValueFromGUI("group");
				     	poNumber = createCasePage.getValueFromGUI("poNumber");
				     	productTotal = createCasePage.getValueFromGUI("productTotal");
				     	subscriptionTotal = createCasePage.getValueFromGUI("subscriptionTotal");
				     	caseCurrency = createCasePage.getValueFromGUI("caseCurrency");
				     	subject = createCasePage.getValueFromGUI("subject");
				     	description = createCasePage.getValueFromGUI("description");
						break;
					}
					case SC_PORTAL:		{
						fail("SUBMIT_ORDER cases are not supported in Sub Center");
				     	break;
					}
					case SFDC_CONTACT:
					case SFDC_CASES_TAB:{
						fail("SUBMIT_ORDER cases are not supported in the SFDC interface");
				     	break;
					}
				}
				break;
			}
			case CRM_SUPPORT:	{
				switch (createFrom) {
					case PC_PORTAL:		{
				     	contactName = createCasePage.getValueFromGUI("contactNameInfo");
				     	status = createCasePage.getValueFromGUI("status");
				     	severity = createCasePage.getValueFromGUI("severity");
				     	applicationType = createCasePage.getValueFromGUI("applicationType");
				     	
				     	//As of 08/14/2012, this element is no longer part of a CRM case
				     	//functionalArea = createCasePage.getValueFromGUI("functionalArea");
				     	
				     	businessArea = createCasePage.getValueFromGUI("businessArea");
				     	caseArea = createCasePage.getValueFromGUI("caseArea");
				     	
						//As of 06/29/2012, this element is no longer part of a CRM case
				     	//industryType = createCasePage.getValueFromGUI("industryType");
				     	
				     	subject = createCasePage.getValueFromGUI("subject");
				     	description = createCasePage.getValueFromGUI("description");
				     	
						//As of August, 2012, this element is no longer part of a CRM case
				     	//slaHours = viewCasePage.getConstant("SLA_HOURS");
				     	break;
					}
					case SC_PORTAL:		{
						fail("CRM_SUPPORT cases are not supported in Sub Center");
						break;
					}
					case SFDC_CONTACT:
					case SFDC_CASES_TAB:{
						//CRM_SUPPORT cases are supported through the SFDC interface, but we haven't defined any test
						//  properties or test cases yet
				     	break;
					}
				}
				break;
			}
				
			//We haven't defined any test properties or test cases for these yet
			case SUBMIT_RETURNS_CREDITS:
			case STATUS_ORDERS_RETURNS_CREDITS:
			case SUB_CONTRACT_INQUIRY:
			case BUSINESS_SVC_REQUEST:	
			case SW_DOWNLOAD:	{
				severity = createCasePage.getValueFromGUI("severity");
				status = createCasePage.getValueFromGUI("status");
		     	subject = createCasePage.getValueFromGUI("subject");
		     	description = createCasePage.getValueFromGUI("description");
			}
			case CHANGE_SW_COORD_OR_CONTRACT_MGR:	
			case MY_CONTRACT_INFO:
			case SUB_PROGRAM_INFO:
			case OTHER:
				break;
			default:		{
				fail("Unhandled member of ss.SSConstants.CaseType enumerated type: " + caseType);
			}
		}		
	}

	protected void attachFile(String filename) {
		//It is assumed that a case is open in view mode in the portal
		
     	Util.printInfo("Attaching file '" + filename + "' to case '" + getName() + "'...");

		commonPortalPage.click("attachFileButton");
		
		//As of June 13, 2012, this field is disabled.  Until it is fixed, we cannot populate it
		//commonPortalPage.populateField("selectFile", filename);
		//commonPortalPage.click("attachFileButton");
		commonPortalPage.clickToSubmit("doneButton", "attachFileButton");
		
     	Util.printInfo("Attached file '" + filename + "' to case '" + getName() + "'");
	}

	public void addComment(String comment) {
		//It is assumed that a case is open in view mode in the portal
		
     	Util.printInfo("Adding comment '" + comment + "' to case '" + getName() + "'...");
		
		commonPortalPage.click("addCommentButton");
		
		commonPortalPage.populateField("comment", comment);
		commonPortalPage.clickToSubmit("saveButton", "addCommentButton");
		
     	Util.printInfo("Added comment '" + comment + "' to case '" + getName() + "'");
	}

	public void requestClose(String comment) {
		//It is assumed that a case is open in view mode in the portal
		
     	Util.printInfo("Requesting close of case '" + getName() + "'...");
		
		commonPortalPage.click("requestCloseButton");

		commonPortalPage.populateField("caseComment", comment);
		commonPortalPage.clickToSubmit("submitButton", "requestCloseButton");
		
     	Util.printInfo("Requested close of case '" + getName() + "'");
	}

	protected int getTimeRemaining(int rowNumber) {
		//Assumes the case is open in view mode in the SFDC interface
		
		return Integer.parseInt(Util.left(viewCasePage.getRelatedListCell("timeRemainingInCaseMilestonesRelatedList", rowNumber), ":"));
	}
	
	protected int getTimeRemaining() {
		return getTimeRemaining(0);
	}
	
	public boolean checkTimeRemaining(int rowNumber, int threshold) {
		int actual = getTimeRemaining(rowNumber);
		int expectedMax = ((int) Double.parseDouble(slaHours)) * 60;
		int expectedMin = expectedMax - threshold;
		
		return (actual >= expectedMin && actual <= expectedMax);
	}

	public void sendEmail() {
		viewCasePage.click("email");
//		viewCasePage.click("selectATemplate");
//		viewCasePage.populateField("folderSelect",EISTestBase.testProperties.getConstant("EMAIL_TEMPLATE_FOLDER"));
//		viewCasePage.populateField("folderSelect",EISTestBase.testProperties.getConstant("EMAIL_TEMPLATE_FOLDER"));
//		viewCasePage.click("template");
//		Util.sleep(10000);
		viewCasePage.populateField("emailSubject","Test Mail");
		viewCasePage.click("sendEmail");
		
	}
	public void call() {
		viewCasePage.click("call");
		viewCasePage.populateField("commentsInCall","Test Call");
		viewCasePage.click("logACall");
	}

	public void comment() {
		viewCasePage.click("more");
		viewCasePage.click("comment");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Post_Case_Comment']")));
//		EISTestBase.driver.switchTo().defaultContent();
//		WebElement frame =  EISTestBase.driver.findElement(By.id("sc-evf-b911b821-cd64-47b9-bbd0-2b516df362d5-09DV000000002zL_066V00000004hJ"));
//		EISTestBase.driver.switchTo().frame(frame);
//		EISTestBase.driver.switchTo().frame("sc-evf-b911b821-cd64-47b9-bbd0-2b516df362d5-09DV000000002zL_066V00000004hJf");
//		EISTestBase.driver.switchTo().defaultContent();
//		EISTestBase.swithToIframeWhereMyFieldExist(viewCasePage, "commentText");
		viewCasePage.populateField("commentText","Test Comment");
		viewCasePage.click("postCaseComments");	

	}

	public void changeOwner(String actionType) {
		Util.sleep(5000);
		viewCasePage.clickToSubmit("editOwner");
		Util.sleep(10000);
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Caseowner_Transfer']")));
		Util.sleep(5000);
		viewCasePage.populateField("actionType",actionType);
		
		viewCasePage.populateField("commentsInTransferCase","Changing Owner");
		
		viewCasePage.click("submit");
		Util.sleep(5000);
		if(viewCasePage.isAlertPresent()){
			
		viewCasePage.acceptAlert();
		}
		Util.sleep(5000);
		
	}

/*	protected boolean checkTimeRemaining() {
		return checkTimeRemaining(0, SSConstants.DEFAULT_CASE_TIME_REMAINING_THRESHOLD);
	}
*/
}

