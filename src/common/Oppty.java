
package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.openqa.selenium.By;

/**
 * Representation of an application-independent SFDC Opportunity.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class Oppty extends SFDCObject {
	//State variables 
	//  Some (like name, id, and url) are defined in the superclass
	//  Define only object-specific ones here
	//private List<Product> products = new ArrayList<Product>();
	private Map<String, Product> products = new HashMap<String, Product>();
	private DiscountApprovalRequest dar;

	//NOTE At Autodesk we don't create an account in the test, so we have no Account object to pass
	//  to Oppty.create().  The only account related info we have is the account name, which is
	//  supplied in test properties.  This may change later, however, as we discover new work flows
	//private Account account = null;
	private String accountName = "";
	private String partnerName	="";
	private String quoteName = "";
	private int totalPriceAfterDiscount=0;
	private int totalDiscountPercentage=0;
	private String opportunityName;
	private String distributorAccountCSN=null;
	private String opportunityNumber;


	//Make all Page objects private, but provide getters so that test code can have read
	//  access to them.  That will allow test code to access test data, metadata, constants,
	//  etc., through this SFDCObject instance.  That's important, because we don't want
	//  test code authors to declare Page objects when they don't need to; instead we want
	//  test code to call utilities wherever possible.  Those utilities typically create an
	//  SFDCObject, instantiate the Page objects it needs, and pass back the SFDCObject
	//  instance.  It is through that instance that test code can get access to this
	//  object's Page data



	public String getOpportunityNumber() {
		return opportunityNumber;
	}

	public String getDistributorAccountCSN() {
		return distributorAccountCSN;
	}

	public String getOpportunityName() {
		return opportunityName;
	}

	public void setOpportunityName(String opportunityName) {
		this.opportunityName = opportunityName;
	}

	protected Page_ createOpptyRTPage;
	protected Page_ createOpptyPage;
	protected Page_ viewOpptyPage;
	protected Page_ viewOpptyInPortalPage;
	protected Page_ addEditProductsPage;
	protected Page_ addPartnerPage;
	protected Page_ addCompetitorPage;
	protected Page_ addSalesTeamPage;
	protected Page_ createQuotePage;
	protected Page_ viewQuotePage; 
	protected Page_ editQuotePage; 
	protected Page_ addNoteToQuotePage;
	protected Page_ editQuoteProductsPage;
	protected Page_ createPDFToQuotePage;
	protected Page_ changeAutodeskEntityForQuotePage;
	protected Page_ createPartnerOpptyPage;
	protected Page_ portalLandingPage;
	
	
	

	public Oppty(Page_ createOpptyRTPage, Page_ createOpptyPage, Page_ viewOpptyPage, Page_ viewOpptyInPortalPage, Page_ addEditProductsPage, Page_ addPartnersPage, Page_ addCompetitorsPage, Page_ addSalesTeamPage,Page_ createPartnerOpptyPage,Page_ portalLandingPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_OPPTY, EISConstants.OBJECT_TYPE_OPPTY);
		
		///mainWindow					= EISTestBase.mainWindow;
		//commonPage					= EISTestBase.commonPage;

		this.createOpptyRTPage		= createOpptyRTPage;
		this.createOpptyPage		= createOpptyPage;
		this.viewOpptyPage			= viewOpptyPage;
		this.viewOpptyInPortalPage	= viewOpptyInPortalPage;
		this.addEditProductsPage	= addEditProductsPage;
		this.addPartnerPage			= addPartnersPage;
		this.addCompetitorPage		= addCompetitorsPage;
		this.addSalesTeamPage		= addSalesTeamPage;
		this.createPartnerOpptyPage	=createPartnerOpptyPage;
		this.portalLandingPage	= portalLandingPage;
		//The name may be overwritten, depending on how the oppty is created, e.g., lead conversion 
		setName(generateObjectName(this.createOpptyPage, EISConstants.OBJECT_NAME_FIELD_NAME_OPPTY));
		setName(generateObjectName(this.createPartnerOpptyPage, EISConstants.OBJECT_NAME_FIELD_NAME_OPPTY));
	}
	
	public Oppty(Page_ createOpptyRTPage, Page_ createOpptyPage, Page_ viewOpptyPage, Page_ viewOpptyInPortalPage, Page_ addEditProductsPage, Page_ addPartnersPage, Page_ addCompetitorsPage, Page_ addSalesTeamPage, Page_ createQuotePage, Page_ viewQuotePage, Page_ editQuotePage, Page_ editQuoteProductsPage, Page_ addNoteToQuotePage, Page_ createPDFToQuotePage, Page_ changeAutodeskEntityForQuotePage) {
		super(EISConstants.OBJECT_NAME_PREFIX_OPPTY, EISConstants.OBJECT_TYPE_OPPTY);
		
		///mainWindow					= EISTestBase.mainWindow;
		//commonPage					= EISTestBase.commonPage;

		this.createOpptyRTPage		= createOpptyRTPage;
		this.createOpptyPage		= createOpptyPage;
		this.viewOpptyPage			= viewOpptyPage;
		this.viewOpptyInPortalPage	= viewOpptyInPortalPage;
		this.addEditProductsPage	= addEditProductsPage;
		this.addPartnerPage			= addPartnersPage;
		this.addCompetitorPage		= addCompetitorsPage;
		this.addSalesTeamPage		= addSalesTeamPage;
		this.createQuotePage		= createQuotePage;
		this.viewQuotePage			= viewQuotePage;
		this.editQuotePage			= editQuotePage;
		this.editQuoteProductsPage	= editQuoteProductsPage;
		this.addNoteToQuotePage		= addNoteToQuotePage;
		this.createPDFToQuotePage	= createPDFToQuotePage;
		this.changeAutodeskEntityForQuotePage = changeAutodeskEntityForQuotePage;
		//The name may be overwritten, depending on how the oppty is created, e.g., lead conversion 
		setName(generateObjectName(this.createOpptyPage, EISConstants.OBJECT_NAME_FIELD_NAME_OPPTY));
	}

	
	/*	public Account getAccount() {
		return account;
	}
*/
	
	public Map<String, Product> getProducts() {
		return products;
	}

	public int getNumProducts() {
		return products.size();
	}

	public Product getProduct(String productLine) {
		return products.get(productLine);
	}

	public DiscountApprovalRequest getDiscountApprovalRequest() {
		return dar;
	}
	
	public Page_ getViewDiscountApprovalRequestPage() {
		return dar.getViewDiscountApprovalRequestPage();
	}
	
	public String getAccountName() {
		return getAccountName();
	}

	public Page_ getCreateOpptyRTPage() {
		return createOpptyRTPage;
	}

	public Page_ getCreateOpptyPage() {
		return createOpptyPage;
	}

	public Page_ getViewOpptyPage() {
		return viewOpptyPage;
	}
	
	public Page_ getViewOpptyInPortalPage() {
		return viewOpptyInPortalPage;
	}
	
	public Page_ getAddEditProductsPage() {
		return addEditProductsPage;
	}

	public Page_ getAddPartnersPage() {
		return addPartnerPage;
	}

	public Page_ getAddCompetitorsPage() {
		return addCompetitorPage;
	}

	public Page_ getAddSalesTeamPage() {
		return addSalesTeamPage;
	}
	
	public Page_ getCreateQuotePage() {
		return createQuotePage;
	}
	
	public int getTotalDiscountPercentage() {
		return totalDiscountPercentage;
	}
	
	public int getTotalPriceAfterDiscount() {
		return totalPriceAfterDiscount;
	}


	public Page_ getViewQuotePage() {
		return viewQuotePage;
	}

	public Page_ getEditQuotePage() {
		return editQuotePage;
	}

	public Page_ getEditQuoteProductsPage() {
		return editQuoteProductsPage;
	}

	public Page_ getAddNoteToQuotePage(){
		return addNoteToQuotePage;
	}
	
	public Page_ getCreatePDFToQuotePage(){
		return createPDFToQuotePage;
	}
	
	public Page_ getChangeAutodeskEntityForQuotePage(){
		return changeAutodeskEntityForQuotePage;
	}
	public String getPartnerName() {
		return partnerName;
	}
	
	public String getQuoteName(){
		return quoteName;
	}
	
	public Page_ getPortalLandingPage() {
		return portalLandingPage;
	}

	@Override
	public String toString() {
		return "Oppty [super=" + super.toString() +
				", accountName=" + accountName +
				", quoteName=" + quoteName +
				", totalDiscountPercentage=" + totalDiscountPercentage +
				", totalPriceAfterDiscount=" + totalPriceAfterDiscount +
				", number of products=" + products.size() +
				", distributorAccountCSN=" + distributorAccountCSN +
				", opportunityNumber =" + opportunityNumber +
				"]";
	}

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
	 * Opens the oppty in the portal, if it is not already open.
	 * @return An indication of whether the oppty was actually opened
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
	
	@Override
	public void openForEdit() {
		mainWindow.select();
		open();
		
		//commonPage.clickAndWait("editButton");
		commonPage.clickAndWait("editButton", "saveButton");
	}
	
	public String create() {
     	Util.printInfo("Creating oppty...");
     	
		mainWindow.select();
		
		//commonPage.clickAndWait("tabOpptys");
		commonPage.clickAndWait("tabOpptys", "newButton");

		//Ensure that we wait for the page to load, so that we can make a valid check for the presence
		//  of the recordType field
		//commonPage.clickAndWait("newButton");
		commonPage.clickToSubmit("newButton");

     	if (createOpptyRTPage.isFieldPresent("recordType")) {
     		createOpptyRTPage.populate();
     		
     		//createOpptyRTPage.clickAndWait("continueButton");
     		createOpptyRTPage.click("continueButton");
     		
     		//The problem with this is that it waits for privacyStatement, which never goes away
     		//commonPage.waitForPageToSettle();
     		commonPage.waitForFieldVisible("saveButton");
     	}

     	createOpptyPage.populate();
     	
     	//commonPage.clickToSubmit("saveButton");
     	commonPage.clickToSubmit("saveButton", "editButton");

     	setUrl();
     	setId();
     	
     	accountName = viewOpptyPage.getValueFromGUI("accountName");
     	
     	setOwner(EISTestBase.getCurrentUserName());

     	Util.printInfo("Created oppty '" + getName() + "' (" + getUrl() + ")");
     	
     	return getName();
	}
	public String createPartnerOpportunity() {
		Util.printInfo("Creating Opportunity for Partner...");
		
		commonPage.click("newButton");
		mainWindow.select();
		
		createPartnerOpptyPage.populate(); 
		
		commonPage.clickToSubmit("saveButton", "editButton");
		
		//NOTES TO OFFSHORE - the URL you are saving here is for an opportunity, not a partner.  Speaking
		//  of which, please see the notes in the test method for my comments on the Partner class
     	setUrl();
     	setId();

     	opportunityName = viewOpptyInPortalPage.getValueFromGUI("opptyName");
     	setName(opportunityName);
     	
     	Util.printInfo("Created opportunity '" + opportunityName + "' (" + getUrl() + ")");

     	return opportunityName;
	}
	
	//For now, adding and editing products (en masse) is done on the PageAddEditProducts page, which is
	//  associated with the Oppty object.  If and when we need to edit a product in the normal way, we will
	//  add an edit() method to Product and call that
	//TODO  Allow passing an optional constant set name, so that this routine can be called more than
	//  once in a test
	public void addProducts() {
		Product product;
		String instanceNamesString;
		String productLine;
		int numProducts;
		String messageChunk;
		int estimatedSeats;
		double salesPrice;
		List<String> instanceSet = new ArrayList<String>();
		Map<String, List<String>> savedFieldLocators = new HashMap<String, List<String>>();
		List<FieldData_> instanceFieldData = new ArrayList<FieldData_>();
		
//		open();
		
		//viewOpptyPage.clickAndWait("addProductButton");
		viewOpptyPage.click("addProductButton");
		commonPage.waitForFieldPresent("saveButton");
		
		instanceNamesString = addEditProductsPage.getConstant(EISConstants.OPPTY_ADD_PRODUCTS_CONSTANT_SET_NAME);
		if (!instanceNamesString.isEmpty()) {
			instanceSet = Util.listOfStringTrim(Arrays.asList(instanceNamesString.split(EISConstants.PARAMETER_DELIM)));
		} else {
			instanceSet.add("1");
		}
		
		numProducts = instanceSet.size();
		messageChunk = " product" + (numProducts == 1 ? "" : "s");
		
     	Util.printInfo("Adding " + numProducts + messageChunk + " to oppty '" + getName() + "' (" + getUrl() + ")...");
     	
		for (String instance : instanceSet) {
			instanceFieldData = addEditProductsPage.getInstanceFieldData(instance);

			//Get the value of the addEditProductsPage.prodLine FieldData record, which we are using to store the product line
			productLine = addEditProductsPage.getTestDataInstanceValue(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);

			//Save the default (with tokens) locators, so that we have tokens to replace when dealing with subsequent instances
			savedFieldLocators = EISTestBase.getReferencedFieldLocators(addEditProductsPage, instanceFieldData);

			//Replace the tokens in the locators of the Field objects associated with the product with the value of productLine
			EISTestBase.parseReferencedFieldLocators(addEditProductsPage, instanceFieldData, productLine);
			
			//Delete the prodLine FieldData record, because it is of type READ_ONLY
			addEditProductsPage.deleteTestDataInstanceRecord(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);
			
			//Populate the product fields in the GUI
			addEditProductsPage.populateInstance(instance);
			
			product = new Product(getName(), productLine);
			
			//We are getting the value of Property fields from the GUI where necessary, otherwise from test
			//  properties.  We can count on all required fields being present in test properties.  In the
			//  case of optional fields, when a value is not found in test properties, the Property field will
			//  be set to empty string.
			//CAUTION:  before changing this code to get optional values from the GUI, keep in mind that the
			//  locators of Field objects on the page will not have had their tokens parsed if an associated
			//  record was not present in test properties!
			
			//Getting these from GUI because the selected value in a Field of type PICKLIST will often not match
			//  the value provided in test properties
			product.setProductType(addEditProductsPage.getValueFromGUI("prodType"));
			product.setLicenseType(addEditProductsPage.getValueFromGUI("licenseType"));

			estimatedSeats = Integer.parseInt(addEditProductsPage.getTestDataInstanceValue("estSeats", instance));
			product.setEstimatedSeats(estimatedSeats);
			
			salesPrice = Double.valueOf(addEditProductsPage.getTestDataInstanceValue("salesPrice", instance));
			product.setSalesPrice(salesPrice);
			
			product.setTotalPrice(estimatedSeats * salesPrice);
			
			//These are optional fields
			product.setBillingDate(addEditProductsPage.getTestDataInstanceValue("billingDate", instance));
			product.setComments(addEditProductsPage.getTestDataInstanceValue("comments", instance));
			
			products.put(productLine, product);
			
			//Replace the modified locators with the default locators, so that we have tokens to replace when
			//  dealing with the next instance
			addEditProductsPage.setFieldLocators(savedFieldLocators);
		}
		
		//commonPage.clickToSubmit("saveButton");
		commonPage.clickToSubmit("saveButton", "editButton");

     	Util.printInfo("Added " + numProducts + messageChunk + " to oppty '" + getName() + "' (" + getUrl() + ")");
	}
	
	public void addRequiredProductsToOpportunity() {


		mainWindow.select();
		viewOpptyInPortalPage.clickToSubmit("addProductButton" , "");
		Util.sleep(5000);
		if(createPartnerOpptyPage.isAlertPresent())
		{
			createPartnerOpptyPage.dismissAlert();		
		}
		
		createPartnerOpptyPage.waitForFieldPresent("productLineSearchBox");
		
		createPartnerOpptyPage.populateInstance("ADD_PRODUCTS_SEARCH_1");
		createPartnerOpptyPage.clickToSubmit("searchButton", "searchButton");
		
		createPartnerOpptyPage.populateInstance("ADD_PRODUCTS_1");
		createPartnerOpptyPage.clickToSubmit("saveButton", "editButton");
		
		if(EISTestBase.testProperties.getConstant("NUM_PRODUCTS").equalsIgnoreCase("TWO")){
		viewOpptyInPortalPage.clickToSubmit("addProductButton" , "");
		Util.sleep(5000);
		if(createPartnerOpptyPage.isAlertPresent())
		{
			createPartnerOpptyPage.dismissAlert();		
		}
			
		createPartnerOpptyPage.waitForFieldPresent("productLineSearchBox");	
		createPartnerOpptyPage.populateInstance("ADD_PRODUCTS_SEARCH_2");
		createPartnerOpptyPage.clickToSubmit("searchButton", "searchButton");
			
		createPartnerOpptyPage.populateInstance("ADD_PRODUCTS_2");
		createPartnerOpptyPage.clickToSubmit("saveButton", "editButton");
		
		}
		
		//NOTES TO OFFSHORE - you are again saving the URL of an oppty 
     	//setUrl();
     	//setId();
		
		//NOTES TO OFFSHORE - why return the accountName?
		//return accountName;		

		Util.printInfo("Added Products to the opportunity");
	}
	
	public void addContactsToOpportunity() {
		//String returnValue = "";
		
	//	Util.printInfo("Adding Contacts to the opportunity '" + opportunityNumber + "'...");
		Util.sleep(2000);
		EISTestBase.switchDriverToFrame(4);
		viewOpptyInPortalPage.click("addContactButton");
		mainWindow.select();

		//NOTES TO OFFSHORE - It turns out we need to click the New button after clicking the Add button
		viewOpptyInPortalPage.click("newContactButton");
		createPartnerOpptyPage.waitForFieldPresent("contactRole");
		
		createPartnerOpptyPage.populateInstance("ADD_CONTACTS");
		
		//This button is in the portal, not the common page.  And we have defined searchText on
		//  createPartnerOpptyPage, so perhaps the button belongs there
		//commonPage.click("goButton");
		createPartnerOpptyPage.click("searchButton");
		
		//NOTES TO OFFSHORE - Replaced sleep with call to waitForElementPresent
		//Util.sleep(15000);
		createPartnerOpptyPage.waitForFieldPresent("contactSelectionRadioButton");

		createPartnerOpptyPage.populateInstance("ADD_CONTACTS_1");
		
		//Since we had to click the New button, the controls are different
		//commonPage.clickToSubmit("saveButton", "editButton");
		//NOTES TO OFFSHORE - The empty "field name to wait for" will cause error checking to be performed, but
		//  no waiting for a field to appear. 
		createPartnerOpptyPage.clickToSubmit("selectButton", "");
		commonPage.waitForFieldPresent("editButton");
		EISTestBase.switchDriverToFrame(4);
		commonPage.click("saveButton");
		Util.sleep(5000);
		//NOTES TO OFFSHORE - you are again saving the URL of an oppty 
     	//setUrl();
     	//setId();
     	
		//NOTES TO OFFSHORE - don't return something if it cannot be used by the caller
     	//return returnValue;
		
		Util.printInfo("Added Contacts to the opportunity");
	}
	//protected String addDistributorsToOpportunity() {
		public void addDistributorsToOpportunity() {
//			String returnValue = "";
//			Util.printInfo("Adding Distributors to the opportunity '" + opportunityNumber + "'...");
			
			mainWindow.select();
			Util.sleep(2000);
			EISTestBase.switchDriverToFrame(5);
			viewOpptyInPortalPage.click("addDistributorButton");				
			createPartnerOpptyPage.waitForFieldPresent("searchText");
			mainWindow.select();

			createPartnerOpptyPage.populateInstance("ADD_DISTS");
			
			createPartnerOpptyPage.clickToSubmit("goButton" , "");
			
			distributorAccountCSN = createPartnerOpptyPage.getConstant("DIST_ACCOUNT_CSN");	
			
			createPartnerOpptyPage.populateInstance("ADD_DISTS_1");
			
			createPartnerOpptyPage.clickToSubmit("saveButton", "editButton");
			
			//NOTES TO OFFSHORE - perhaps control returned too soon, or an error (uncaught) caused us to never get to
			//  the view oppty page
			Util.sleep(2000);
			EISTestBase.switchDriverToFrame(5);
				
			mainWindow.select();

			Util.printInfo("Added Distributors to the opportunity");
		}
	
	public void registerDealAndConfirm() {
	//	Util.printInfo("Registering the deal for opportunity '" + opportunityNumber + "'...");
			
	mainWindow.select();
	//viewOpptyInPortalPage.clickToSubmit("registerDealButton","confirmButton");
	EISTestBase.driver.findElement(By.xpath("//td[contains(@id,'topButtonRow')]/input[contains(@value,'Register Deal')]")).click();
	if(viewOpptyInPortalPage.isAlertPresent()){
		viewOpptyInPortalPage.acceptAlert();
	}
	if(viewOpptyInPortalPage.isFieldPresent("contractedAccountWarningMsg")){
		Util.printInfo("Deal cannot be Registered for opportunity as opportunity belongs to an Autodesk Contracted Account that is NOT eligible for ACE");
	}
	else{
	
	viewOpptyInPortalPage.waitForFieldPresent("confirmButton");
	//Note that we are passing an override timeout value
	viewOpptyInPortalPage.clickToSubmit("confirmButton", "addProductButton",1200000);		

	//NOTES TO OFFSHORE - why return the oppty name?
	//return getName();		

	Util.printInfo("Registered the deal for opportunity");
	}
	}
	
	public void changeOpptyStatusToApproved(){
		mainWindow.select();
		Util.printInfo("Changing the deal status of the oppty to Approved" );
		commonPage.clickToSubmit("editButton", "dealStatusList");
		commonPage.populateField("dealStatusList", "Approved");
		commonPage.populateField("referenceSRP", "End User Discounted SRP");
		commonPage.populateField("partnerDiscount", "Base");
		//Handling alert
		EISTestBase.driver.findElement(By.xpath("//td[@id='topButtonRow']/input[normalize-space(@class)='btn' and normalize-space(@value)='Save']")).click();
		if(commonPage.isAlertPresent()){
			commonPage.acceptAlert();
		}
	//	commonPage.click("saveButton");
		
		
		
		Util.printInfo("Changed the deal status of the oppty to Approved" );
		
	}
	//For now, adding and editing products (en masse) is done on the PageAddEditProducts page, which is
	//  associated with the Oppty object.  If and when we need to edit a product in the normal way, we will
	//  add an edit() method to Product and call that
	//TODO  Allow passing an optional constant set name, so that this routine can be called more than
	//  once in a test
	public void editProducts() {
		Product product;
		String instanceNamesString;
		String productLine;
		String fieldName;
		int numProducts;
		String messageChunk;
		List<String> instanceSet = new ArrayList<String>();
		Map<String, List<String>> savedFieldLocators = new HashMap<String, List<String>>();
		List<FieldData_> instanceFieldData = new ArrayList<FieldData_>();
		
		open();
		
		//viewOpptyPage.clickAndWait("editAllButton");
		viewOpptyPage.click("editAllButton");
		commonPage.waitForFieldPresent("saveButton");
		
		instanceNamesString = addEditProductsPage.getConstant(EISConstants.OPPTY_EDIT_PRODUCTS_CONSTANT_SET_NAME);
		if (!instanceNamesString.isEmpty()) {
			instanceSet = Util.listOfStringTrim(Arrays.asList(instanceNamesString.split(EISConstants.PARAMETER_DELIM)));
		} else {
			instanceSet.add("1");
		}
		
		numProducts = instanceSet.size();
		messageChunk = " product" + (numProducts == 1 ? "" : "s");
		
     	Util.printInfo("Editing " + numProducts + messageChunk + " in oppty '" + getName() + "' (" + getUrl() + ")...");
     	
		for (String instance : instanceSet) {
			instanceFieldData = addEditProductsPage.getInstanceFieldData(instance);

			//Get the value of the addEditProductsPage.prodLine FieldData record, which we are using to store the product line
			productLine = addEditProductsPage.getTestDataInstanceValue(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);

			//Save the default (with tokens) locators, so that we have tokens to replace when dealing with subsequent instances
			savedFieldLocators = EISTestBase.getReferencedFieldLocators(addEditProductsPage, instanceFieldData);

			//Replace the tokens in the locators of the Field objects associated with the product with the value of productLine
			EISTestBase.parseReferencedFieldLocators(addEditProductsPage, instanceFieldData, productLine);
			
			//Delete the prodLine FieldData record, because it is of type READ_ONLY
			addEditProductsPage.deleteTestDataInstanceRecord(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);
			
			//Populate the product fields in the GUI
			addEditProductsPage.populateInstance(instance);
			
			product = products.get(productLine);
			
			ListIterator<FieldData_> itr = instanceFieldData.listIterator();
			while (itr.hasNext()) {
				fieldName = itr.next().getFieldName().toUpperCase();
				
				//We are getting the value of only those Product fields that were edited
				switch (fieldName) {
					//Getting these from GUI because the selected value in a Field of type PICKLIST will often not match
					//  the value provided in test properties
					case "PRODTYPE":	{
						product.setProductType(addEditProductsPage.getValueFromGUI(fieldName));
						break;
					}
					case "LICENSETYPE":	{
						product.setLicenseType(addEditProductsPage.getValueFromGUI(fieldName));
						break;
					}
					case "ESTSEATS":	{
						product.setEstimatedSeats(Integer.parseInt(addEditProductsPage.getTestDataInstanceValue(fieldName, instance)));
						break;
					}
					case "SALESPRICE":	{
						product.setSalesPrice(Double.valueOf(addEditProductsPage.getTestDataInstanceValue(fieldName, instance)));
						break;
					}
					case "BILLINGDATE":	{
						product.setBillingDate(addEditProductsPage.getTestDataInstanceValue(fieldName, instance));
						break;
					}
					case "COMMENTS":	{
						product.setComments(addEditProductsPage.getTestDataInstanceValue(fieldName, instance));
						break;
					}
				}
			}
			
			//In case product.estimatedSeats or product.salesPrice was modified
			product.setTotalPrice();
			
			products.put(productLine, product);
			
			//Replace the modified locators with the default locators, so that we have tokens to replace when
			//  dealing with the next instance
			addEditProductsPage.setFieldLocators(savedFieldLocators);
		}
		
		//commonPage.clickToSubmit("saveButton");
		commonPage.clickToSubmit("saveButton", "editButton");

     	Util.printInfo("Edited " + numProducts + messageChunk + " in oppty '" + getName() + "' (" + getUrl() + ")");
	}
	
	public String editOppty(String opptyName2) {
		// TODO Auto-generated method stub
		commonPage.click("editButton");
		createPartnerOpptyPage.populateField("opptyName",opptyName2);
		commonPage.clickToSubmit("saveButton", "editButton");
		opportunityName = viewOpptyInPortalPage.getValueFromGUI("opptyName");
     	setName(opportunityName);
     	Util.printInfo("Edited opportunity '" + opportunityName + "' (" + getUrl() + ")");

     	return opportunityName;
	}
	public boolean searchOppty(String opptyNumber){
    	mainWindow.select();
    	
    	commonPage.populateField("searchBox", opptyNumber);
    	//commonPage.clickToSubmit("searchButton");
    	commonPage.clickToSubmit("searchButton", "searchAgainButton");
    	if(commonPage.isFieldPresent("searchAllButton")){
    	commonPage.clickToSubmit("searchAllButton", "searchAgainButton");
		}
    	return(commonPage.isFieldPresent("noMatchesFoundMsg"));

	}

	protected void createDiscountApprovalRequest(DiscountApprovalRequest dar) {
       	//DARs don't have names, so let's use the oppty name
		String opptyName = getName();
			
     	Util.printInfo("Creating Discount Approval Request for oppty '" + opptyName + "'...");
     	
     	this.dar = dar;
     	
       	dar.setName(opptyName);
     	
		mainWindow.select();
		//open();
		
       	viewOpptyPage.click("discountApprovalRequestButton");
       	
       	//TODO  Need to deal with confirmation dialog

       	mainWindow.select();

       	//A DiscountApprovalRequest object is not a stand-alone object - it has a unique relationship with its
       	//  Oppty.  Because both "creating" it and editing it are done in the editDiscountApprovalRequest method,
       	//  we need to pass a flag to the method to tell it whether or not to open the DAR
       //	editDiscountApprovalRequest(false);
       	editDiscApprovalRequestNew();

		dar.setUrl();
       	dar.setId();
       	
     	Util.printInfo("Created Discount Approval Request for oppty '" + opptyName + "' (" + dar.getUrl() + ")");
	}
	
	public void editDiscountApprovalRequest(boolean openIt) {
		dar.editProducts(products, openIt);
	}
	public void editDiscApprovalRequestNew(){
	dar.editPercentages();
	
	}

	public void editDiscountApprovalRequest() {
		editDiscountApprovalRequest(true);
	}

	public void calculateDiscountApprovalRequestDiscount() {
		dar.calculateDiscount();
	}

	public void displayDiscountApprovalRequestApprovalChain() {
		dar.displayApprovalChain();
	}

	public void verifyDiscountApprovalRequestApprovalChain() {
		dar.verifyApprovalChain(products);
	}
	


	public void submitDiscountApprovalRequestForApproval() {
		dar.submitForApproval();
	}
	
	public void addAdditionalApprovers() {
		dar.addAdditionalApprovers();
	}

	public void addPartner(String instanceName) {
			open(); 		
		
     		Util.printInfo("Adding partner to oppty '" + getName() + "' (" + getUrl() + ")...");
	     	
     		//We need to do this because the newAssociatedPartnerButton button lives in a frame
     		EISTestBase.switchDriverToFrame(4);	//The field lived in frame #3 until mid-May 2012, when the Chat widget was added

     		//viewOpptyPage.clickAndWait("newAssociatedPartnerButton");
     		viewOpptyPage.click("newAssociatedPartnerButton");
	     	addPartnerPage.waitForFieldPresent("goButton");
			
			mainWindow.select();
		
			//search for the partner 
	     	//addPartnerPage.populateFieldInstance("searchPartnerText", instanceName);
	     	//addPartnerPage.populateFieldInstance("isPrimary", instanceName);
	     	addPartnerPage.populateInstance(instanceName);

	     	//addPartnerPage.clickAndWait("goButton");
	     	addPartnerPage.clickAndWait("goButton", "firstFoundPartnerAccount");
	     	
	     	//TODO  Add code to handle no search results (similar to what we do with lookups)
			addPartnerPage.check("firstFoundPartnerAccount");
	     	//partnerName = addPartnerPage.getValueFromGUI("partnerName");

			//Sometimes the radio button gets clicked (I can see it), but the partner does not get added.
			//  I suspect that clicking the Save button somehow interrupts the app's processing of the
			//  radio button click.  Let's see if sleeping a bit takes care of the problem
			//!!!ACTUALLY what is happening is that the radio button is successfully getting lit, but
			//  when the Save button is clicked (even after waiting a long time) the radio button
			//  clears and the page refreshes!  (Not reproducible by hand.)  BUT, I do not see this
			//  behavior with some data, for example 5109154689 for STG.  The upshot is to be very careful
			//  about what test data to use!  (And the following sleep is probably not necessary.)
			Util.sleep(600);
			
			//addPartnerPage.click("saveButton");
			commonPage.clickToSubmit("saveButton");
	   
	     	Util.printInfo("Added partner to oppty '" + getName() + "' (" + getUrl() + ")");
		}

	public void addPartner() {
		addPartner("1");
	}
	
	public void addSalesTeam(String instanceName) {
		open(); 		
		
     	Util.printInfo("Adding sales team to oppty '" + getName() + "' (" + getUrl() + ")...");
		
		//viewOpptyPage.clickAndWait("addSalesTeamButton");
		viewOpptyPage.click("addSalesTeamButton");
		commonPage.waitForFieldPresent("saveButton");
		
		//Necessary?  We aren't switching from a frame
		//mainWindow.select();
		
		addSalesTeamPage.populateInstance(instanceName);

		//addSalesTeamPage.click("save");
		//addSalesTeamPage.click("saveButton");
		//commonPage.clickToSubmit("saveButton");
		commonPage.clickToSubmit("saveButton", "editButton");

     	Util.printInfo("Added sales team to oppty '" + getName() + "' (" + getUrl() + ")");
	}

	public void addSalesTeam() {
		addSalesTeam("1");
	}
	
	public void addCompetitor(String instanceName) {
     	//Ensure that we are in selected oppty page, so that we can add Competitor to that oppty 
		open(); 		
		
     	Util.printInfo("Adding competitor to oppty '" + getName() + "' (" + getUrl() + ")...");
     	
		//viewOpptyPage.clickAndWait("newCompetitorButton");
		viewOpptyPage.click("newCompetitorButton");
		commonPage.waitForFieldPresent("saveButton");
		
		//Necessary?  We aren't switching from a frame
		//mainWindow.select();
	
		addCompetitorPage.populateInstance(instanceName);
		//addCompetitorPage.populateField("competitorName");
		//addCompetitorPage.populateField("strengths");
		//addCompetitorPage.populateField("weaknesses");
		
		//addCompetitorPage.click("saveButton");
		//commonPage.clickToSubmit("saveButton");
		commonPage.clickToSubmit("saveButton", "editButton");
   
     	Util.printInfo("Added competitor to oppty '" + getName() + "' (" + getUrl() + ")");
	}

	public void addCompetitor() {
		addCompetitor("1");
	}

	public void delete() {
		open();
		
     	Util.printInfo("Deleting oppty '" + getName() + "' (" + getUrl() + ")...");
		
		//We have gotten rid of clickAndWait(String fieldName) calls, so we have to specify
		//  something for which to wait - but what?
     	//commonPage.clickAndWait("deleteButton");
     	commonPage.click("deleteButton");
		
     	Util.printInfo("Deleted oppty '" + getName() + "' (" + getUrl() + ")");
	}
	
	public void createQuoteFromOppty(){		
		
		Util.printInfo("Creating Quote From oppty '" + getName() + "' (" + getUrl() + ")...");
		
		viewOpptyPage.click("newQuoteButton");
		commonPage.waitForFieldPresent("saveButton");
		
		quoteName = createQuotePage.getValueFromGUI("quoteName");		
		
		createQuotePage.populate();
		commonPage.clickToSubmit("saveButton", "editButton");
		Util.printInfo("Created Quote '" + getQuoteName() + " from oppty '" + getName() + "' (" + getUrl() + ")");
		
	}
	
	public void editProductsInQuote() {
		Product product;
		String instanceNamesString;
		String productLine;
		String fieldName;
		int numProducts;
		String messageChunk;
		List<String> instanceSet = new ArrayList<String>();
		Map<String, List<String>> savedFieldLocators = new HashMap<String, List<String>>();
		List<FieldData_> instanceFieldData = new ArrayList<FieldData_>();
		
		//viewOpptyPage.clickAndWait("editAllButton");
		viewQuotePage.click("editAllProductsButton");
		commonPage.waitForFieldPresent("saveButton");
		
		instanceNamesString = editQuoteProductsPage.getConstant(EISConstants.QUOTE_EDIT_PRODUCTS_CONSTANT_SET_NAME);
		if (!instanceNamesString.isEmpty()) {
			instanceSet = Util.listOfStringTrim(Arrays.asList(instanceNamesString.split(EISConstants.PARAMETER_DELIM)));
		} else {
			instanceSet.add("1");
		}
		
		numProducts = instanceSet.size();
		messageChunk = " product" + (numProducts == 1 ? "" : "s");
		
     	Util.printInfo("Editing " + numProducts + messageChunk + " in quote '" + getQuoteName() + "'...");
     	
		for (String instance : instanceSet) {
			instanceFieldData = editQuoteProductsPage.getInstanceFieldData(instance);

			//Get the value of the editQuoteProductsPage.prodLine FieldData record, which we are using to store the product line
			productLine = editQuoteProductsPage.getTestDataInstanceValue(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);

			//Save the default (with tokens) locators, so that we have tokens to replace when dealing with subsequent instances
			savedFieldLocators = EISTestBase.getReferencedFieldLocators(editQuoteProductsPage, instanceFieldData);

			//Replace the tokens in the locators of the Field objects associated with the product with the value of productLine
			EISTestBase.parseReferencedFieldLocators(editQuoteProductsPage, instanceFieldData, productLine);
			
			//Delete the prodLine FieldData record, because it is of type READ_ONLY
			editQuoteProductsPage.deleteTestDataInstanceRecord(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);
			
			//Populate the product fields in the GUI
			editQuoteProductsPage.populateInstance(instance);
			
			product = products.get(productLine);
			
			ListIterator<FieldData_> itr = instanceFieldData.listIterator();
			while (itr.hasNext()) {
				fieldName = itr.next().getFieldName().toUpperCase();
				
				//We are getting the value of only those Product fields that were edited
				switch (fieldName) {
					//Getting these from GUI because the selected value in a Field of type PICKLIST will often not match
					//  the value provided in test properties
					case "PRODTYPE":	{
						product.setProductType(editQuoteProductsPage.getValueFromGUI(fieldName));
						break;
					}
					case "LICENSETYPE":	{
						product.setLicenseType(editQuoteProductsPage.getValueFromGUI(fieldName));
						break;
					}
					case "ESTSEATS":	{
						product.setEstimatedSeats(Integer.parseInt(editQuoteProductsPage.getTestDataInstanceValue(fieldName, instance)));
						break;
					}
					case "SALESPRICE":	{
						product.setSalesPrice(Double.valueOf(editQuoteProductsPage.getTestDataInstanceValue(fieldName, instance)));
						break;
					}
					case "BILLINGDATE":	{
						product.setBillingDate(editQuoteProductsPage.getTestDataInstanceValue(fieldName, instance));
						break;
					}
					case "COMMENTS":	{
						product.setComments(editQuoteProductsPage.getTestDataInstanceValue(fieldName, instance));
						break;
					}
				}
			}
			
			//In case product.estimatedSeats or product.salesPrice was modified
//			product.setTotalPrice();
			
			products.put(productLine, product);
			
			//Replace the modified locators with the default locators, so that we have tokens to replace when
			//  dealing with the next instance
			editQuoteProductsPage.setFieldLocators(savedFieldLocators);
		}
		
		//commonPage.clickToSubmit("saveButton");
		commonPage.clickToSubmit("saveButton", "editButton");

		Util.printInfo("Edited " + numProducts + messageChunk + " in quote and gave the discount'" + getQuoteName() + "'...");
	}

	public void verifyProductsInQuote() {
		Product product;
		String instanceNamesString;
		String productLine;
		String fieldName;
		String value;	
		int numProducts;
		Double newvalue = null;
		String messageChunk;
		List<String> instanceSet = new ArrayList<String>();
		Map<String, List<String>> savedFieldLocators = new HashMap<String, List<String>>();
		List<FieldData_> instanceFieldData = new ArrayList<FieldData_>();	
		
		instanceNamesString = editQuoteProductsPage.getConstant(EISConstants.OPPTY_EDIT_QUOTE_PRODUCTS_CONSTANT_SET_NAME);
		if (!instanceNamesString.isEmpty()) {
			instanceSet = Util.listOfStringTrim(Arrays.asList(instanceNamesString.split(EISConstants.PARAMETER_DELIM)));
		} else {
			instanceSet.add("1");
		}
		
		numProducts = instanceSet.size();
		messageChunk = " product" + (numProducts == 1 ? "" : "s");
		
     	Util.printInfo("Verifying " + numProducts + messageChunk + " in quote " + getQuoteName() +"...");
     	int numOfRowsInQuoteRelatedList =  viewQuotePage.getNumRowsInRelatedList("quoteLinesRelatedList");
     	int rowNumber = 0;
		for (String instance : instanceSet) {
			instanceFieldData = editQuoteProductsPage.getInstanceFieldData(instance);

			//Get the value of the editQuoteProductsPage.prodLine FieldData record, which we are using to store the product line
			productLine = editQuoteProductsPage.getTestDataInstanceValue(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);

			//Save the default (with tokens) locators, so that we have tokens to replace when dealing with subsequent instances
			savedFieldLocators = EISTestBase.getReferencedFieldLocators(editQuoteProductsPage, instanceFieldData);

			//Replace the tokens in the locators of the Field objects associated with the product with the value of productLine
			EISTestBase.parseReferencedFieldLocators(editQuoteProductsPage, instanceFieldData, productLine);
			
			//Delete the prodLine FieldData record, because it is of type READ_ONLY
			editQuoteProductsPage.deleteTestDataInstanceRecord(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);
			
//			//Populate the product fields in the GUI
//			editQuoteProductsPage.populateInstance(instance);
			product = new Product(getName(), productLine);
//			product = products.get(productLine);
//			products.put(productLine, product);
			
			
			value = editQuoteProductsPage.getTestDataInstanceValue("quoteSalesPrice", instance);
			int rows;
			if (value.isEmpty()) {
				for(rows = 0 ; rows < numOfRowsInQuoteRelatedList ; rows ++){
				String prod = viewQuotePage.getRelatedListCell("productNameInQuoteLinesRelatedList", rowNumber);
				if(prod.equalsIgnoreCase(productLine)){
				value = viewQuotePage.getRelatedListCell("salesPriceInQuoteLinesRelatedList", rowNumber);
				break;
				}
				else{
					if(rows==numOfRowsInQuoteRelatedList-1){
						fail("The product that we added " + productLine + "while creating opportunity is not available in the quote product line items list");
					}
				}
				}
				value = value.split("USD ")[1]; //.split("\\.")[0];
				newvalue = Double.valueOf(value);
			}
				product.setSrp(newvalue);
				

			value = editQuoteProductsPage.getTestDataInstanceValue("quotediscount", instance);
			if (value.isEmpty()) {
				for(rows = 0 ; rows < numOfRowsInQuoteRelatedList ; rows ++){
					String prod = viewQuotePage.getRelatedListCell("productNameInQuoteLinesRelatedList", rowNumber);
					if(prod.equalsIgnoreCase(productLine)){
					value = viewQuotePage.getRelatedListCell("discountPctInQuoteLinesRelatedList", rowNumber);
					break;
				}
				else{
					if(rows==numOfRowsInQuoteRelatedList-1){
						fail("The product that we added " + productLine + "while creating opportunity is not available in the quote product line items list");
					}
				}
				}

			}
			product.setAdditionalDiscPct(Double.valueOf(value));			
			
			value = editQuoteProductsPage.getTestDataInstanceValue("quantity", instance);		
			
			if (value.isEmpty()) {
				for(rows = 0 ; rows < numOfRowsInQuoteRelatedList ; rows ++){
					String prod = viewQuotePage.getRelatedListCell("productNameInQuoteLinesRelatedList", rowNumber);
					if(prod.equalsIgnoreCase(productLine)){
					value = viewQuotePage.getRelatedListCell("qtyInQuoteLinesRelatedList", rowNumber);
					value = value.split("\\.")[0];
					break;
				}
				else{
					if(rows==numOfRowsInQuoteRelatedList-1){
						fail("The product that we added " + productLine + "while creating opportunity is not available in the quote product line items list");
					}
				}
				}
			}
			product.setEstimatedSeats(Integer.parseInt(value));	
			
			
			//Set all the other Product fields that may have changed based on the edits that were made
			double totalSrp = product.getEstimatedSeats() * product.getSrp();
			product.setTotalSrp(totalSrp);			

			product.setAdditionalDiscAmt(product.getAdditionalDiscPct() * (totalSrp / 100));
			
			double totalEndUserDiscAmt = product.getContractualDiscAmt() + product.getAdditionalDiscAmt();
			product.setTotalEndUserDiscAmt(totalEndUserDiscAmt);
			product.setTotalEndUserDiscPct(product.getContractualDiscPct() + product.getAdditionalDiscPct());

			double unspokenDiscAmt = totalSrp - totalEndUserDiscAmt - product.getNetPrice();
			product.setUnspokenDiscAmt(unspokenDiscAmt);
			product.setUnspokenDiscPct((unspokenDiscAmt / totalSrp) * 100);
			
			double totalDiscAmt = totalEndUserDiscAmt + unspokenDiscAmt;
			product.setTotalDiscAmt(totalDiscAmt);
			product.setTotalDiscPct((totalDiscAmt / totalSrp) * 100);
			
			product.setNetSrp(totalSrp - totalEndUserDiscAmt);
			
			products.put(productLine, product);
			
			String totalDiscountedPrice = viewQuotePage.getRelatedListCell("totalPriceInQuoteLinesRelatedList", rowNumber);
			totalDiscountedPrice = totalDiscountedPrice.split("USD ")[1].split("\\.")[0].replace(",","");
			totalPriceAfterDiscount = totalPriceAfterDiscount + Integer.parseInt(totalDiscountedPrice);
			
			String totalDiscountedPct = viewQuotePage.getRelatedListCell("discountPctInQuoteLinesRelatedList", rowNumber);
			totalDiscountedPct = totalDiscountedPct.split("\\.")[0];
			totalDiscountPercentage = totalDiscountPercentage+Integer.parseInt(totalDiscountedPct);

			String quoteSalesPrice = viewQuotePage.getRelatedListCell("salesPriceInQuoteLinesRelatedList", rowNumber);
			quoteSalesPrice = quoteSalesPrice.split("USD ")[1].split("\\.")[0];
			
			Util.printInfo("Verifying Quote Sales Price in the Quotes related list for the product line : " +productLine);
			EISTestBase.assertEqualsWithFlags(quoteSalesPrice , Double.toString(product.getSrp()).split("\\.")[0]);
			Util.printInfo("Verifying Quote Discount Percentage in the Quotes related list for the product line : " +productLine);
			EISTestBase.assertEqualsWithFlags(totalDiscountedPct , Double.toString(product.getAdditionalDiscPct()).split("\\.")[0]);
			Util.printInfo("Verifying total Quote price after discount in the Quotes related list for the product line : " +productLine);
			EISTestBase.assertEqualsWithFlags(totalDiscountedPrice , Double.toString(product.getNetSrp()).split("\\.")[0]);	
			
			
			
			//Replace the modified locators with the default locators, so that we have tokens to replace when
			//  dealing with the next instance
			editQuoteProductsPage.setFieldLocators(savedFieldLocators);
			
			
			ListIterator<FieldData_> itr = instanceFieldData.listIterator();
			while (itr.hasNext()) {
				fieldName = itr.next().getFieldName().toUpperCase();
				
				//We are getting the value of only those Product fields that were edited
				switch (fieldName) {
					//Getting these from GUI because the selected value in a Field of type PICKLIST will often not match
					//  the value provided in test properties
					case "PRODTYPE":	{
						product.setProductType(editQuoteProductsPage.getValueFromGUI(fieldName));
						break;
					}
					case "LICENSETYPE":	{
						product.setLicenseType(editQuoteProductsPage.getValueFromGUI(fieldName));
						break;
					}
					case "ESTSEATS":	{
						product.setEstimatedSeats(Integer.parseInt(editQuoteProductsPage.getTestDataInstanceValue(fieldName, instance)));
						break;
					}
					case "SALESPRICE":	{
						product.setSalesPrice(Double.valueOf(editQuoteProductsPage.getTestDataInstanceValue(fieldName, instance)));
						break;
					}
					case "BILLINGDATE":	{
						product.setBillingDate(editQuoteProductsPage.getTestDataInstanceValue(fieldName, instance));
						break;
					}
					case "COMMENTS":	{
						product.setComments(editQuoteProductsPage.getTestDataInstanceValue(fieldName, instance));
						break;
					}
				}
			}
			
			//In case product.estimatedSeats or product.salesPrice was modified
//			product.setTotalPrice();
			
			products.put(productLine, product);
			
			//Replace the modified locators with the default locators, so that we have tokens to replace when
			//  dealing with the next instance
			editQuoteProductsPage.setFieldLocators(savedFieldLocators);
			
			if(rowNumber < numOfRowsInQuoteRelatedList-1){
			rowNumber = rowNumber+1;
			}
			else{
				break;			
			}
		}
		totalDiscountPercentage = totalDiscountPercentage/numProducts;

     	Util.printInfo("Verified " + numProducts + messageChunk + " in oppty '" + getName() + "' (" + getUrl() + ")");
	}

	public void addNotestoQuote(){
		Util.printInfo("Adding Note to the Quote : " + getQuoteName());
		viewQuotePage.click("newNoteButton");
		commonPage.waitForFieldPresent("saveButton");
		
		addNoteToQuotePage.populateInstance("ADD_NOTE");		
		
		commonPage.clickToSubmit("saveButton", "editButton");
		
		Util.printInfo("Added a new Note to the Quote : " + getQuoteName() + "Note Title : " + "" + "Note Body : " + "");		
	}
	
	public void createAndSavePDF(){
		Util.printInfo("Creating PDF to the Quote : " + getQuoteName());
		viewQuotePage.click("createPDFButton");
		createPDFToQuotePage.waitForFieldPresent("previewQuotePDFButton");		
		
		createPDFToQuotePage.populateInstance("CREATE_PDF");
		createPDFToQuotePage.clickToSubmit("previewQuotePDFButton", "saveQuotePDFButton");
		
		createPDFToQuotePage.click("saveQuotePDFButton");
		commonPage.waitForFieldPresent("editButton");
		
		Util.printInfo("Created a PDF to the Quote : " + getQuoteName());		
	}
	
	public void verifyQuotePDFRelatedList(){
		Util.printInfo("Verifying PDF related list on ViewQuotePage: " + getQuoteName());
		List<List<String>> pdfs = viewQuotePage.getRelatedList("quotePDFsRelatedList");
		List<String> pdf = null;		
	
		int quoteDiscountColumn 		= viewQuotePage.getRelatedListColumnNum("totalQuotediscountinQuotePDFsRelatedList");
		int totalQuoteAmountColumn 		= viewQuotePage.getRelatedListColumnNum("totalQuoteAmount");
		
		ListIterator<List<String>> itr = pdfs.listIterator();
		while (itr.hasNext()) {
			pdf = itr.next();			
			EISTestBase.assertEqualsWithFlags(pdf.get(quoteDiscountColumn).split("\\.")[0] , String.valueOf(getTotalDiscountPercentage()));
			EISTestBase.assertEqualsWithFlags(pdf.get(totalQuoteAmountColumn).split("USD ")[1].split("\\.")[0].replace(",","") , String.valueOf(getTotalPriceAfterDiscount()));
		}	
		Util.printInfo("Verified PDF related list on ViewQuotePage: " + getQuoteName());
	}
	
	public void editQuote(){
		Util.printInfo("Editing the quote to change the values : " + getQuoteName());
		commonPage.clickToSubmit("editButton" , "saveButton");
		
		editQuotePage.populateInstance("EDIT_QUOTE");
		
		commonPage.clickToSubmit("saveButton" , "editButton");
		
		Util.printInfo("Edited the quote and given new values : " + getQuoteName());
	}
	
	public void changeAutodeskEntityForQuoteAndVerify(){

	String currentAutodeskEntity = viewQuotePage.getValueFromGUI("autodeskEntity");
	Util.printInfo("Changing the Autodesk Entity. Current Autodesk Entity : " + currentAutodeskEntity + "in Quote : " + getQuoteName());
	
	viewQuotePage.click("changeAutodeskEntity"); 
	changeAutodeskEntityForQuotePage.waitForFieldPresent("entityCountry");
	changeAutodeskEntityForQuotePage.populateField("entityCountry", "France");
	String newEntityName = changeAutodeskEntityForQuotePage.getValueFromGUI("newEntity");
	changeAutodeskEntityForQuotePage.click("updateQuoteAutodeskEntityButton");
	viewQuotePage.waitForFieldPresent("changeAutodeskEntity");
	
	String changedEntityName = viewQuotePage.getValueFromGUI("autodeskEntity");		
	EISTestBase.assertEqualsWithFlags(newEntityName, changedEntityName);

	Util.printInfo("Changed the Autodesk Entity. New Autodesk Entity : " + changedEntityName + "in Quote : " + getQuoteName());
	
	}

	public void submitForApproval() {
		dar.submitDARForApproval();
				
	}
}
