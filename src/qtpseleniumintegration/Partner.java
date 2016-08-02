package qtpseleniumintegration;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import common.EISTestBase;
import common.Page_;
import common.EISConstants;
import common.SFDCObject;
import common.Util;

/**
 * Representation of an ePartner SFDC Partner.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class Partner extends SFDCObject {
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
	private Page_ portalLandingPage;
	private Page_ createPartnerOpportunityPage;
	private Page_ viewPartnerOpportunityPage;
	private Page_ viewAccountDetailsPage;
	private Page_ createAccountFromAccountsLink;

	

	private String opptyName=null;
	private String accountName=null;
	private String partnerAccountName=null;
	
	private String distributorAccountName 		=null;			
	private String distributorAccountCity 		=null;
	private String distributorAccountCountry 	=null;
	private String distributorAccountPartnerType =null;							

	private String projectedCloseDate=null;
	private String stage=null;
	private String contactRole=null;
	private String searchText=null;
	private String contactSelectionRadioButton=null;
	private String distributorSelectionRadioButton=null;
	private String distributorAccountCSN=null;
	//private String firstProductLineCheckbox=null;
	//private String firstProductSeatsText=null;
	private String opportunityNumber =null;
	private String opportunityName	=null;
	private String accountCSN=null;
	private String accountType=null;
	/**
	 * Class constructor specifying the Page objects necessary for interacting with an SFDC Partner.
	 * @param portalLandingPage the Page object that defines GUI elements on the portal landing page
	 */
	Partner(Page_ portalLandingPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_PARTNER, EISConstants.OBJECT_TYPE_PARTNER);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.portalLandingPage	= portalLandingPage;
	}

/*	Partner(Page_ createPartnerOpportunityPage,Page_ viewPartnerOpportunityPage, Page_ viewAccountDetailsPage, Page_ createAccountFromAccountsLink) {
		super(EISConstants.OBJECT_NAME_PREFIX_PARTNER, EISConstants.OBJECT_TYPE_PARTNER);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.createPartnerOpportunityPage	= createPartnerOpportunityPage;
		this.viewPartnerOpportunityPage	= viewPartnerOpportunityPage;
		this.viewAccountDetailsPage		= viewAccountDetailsPage;
		this.createAccountFromAccountsLink = createAccountFromAccountsLink;
	} */
	Partner(Page_ createPartnerOpportunityPage,Page_ viewPartnerOpportunityPage, Page_ viewAccountDetailsPage) {
		super(EISConstants.OBJECT_NAME_PREFIX_PARTNER, EISConstants.OBJECT_TYPE_PARTNER);
		
		//mainWindow				= EISTestBase.mainWindow;
		//commonPage				= EISTestBase.commonPage;

		this.createPartnerOpportunityPage	= createPartnerOpportunityPage;
		this.viewPartnerOpportunityPage	= viewPartnerOpportunityPage;
		this.viewAccountDetailsPage		= viewAccountDetailsPage;
	//	this.createAccountFromAccountsLink = createAccountFromAccountsLink;
	}
	/**
	 * @return the distributorAccountName
	 */
	public String getDistributorAccountName() {
		return distributorAccountName;
	}

	/**
	 * @return the distributorAccountCity
	 */
	public String getDistributorAccountCity() {
		return distributorAccountCity;
	}

	/**
	 * @return the distributorAccountCountry
	 */
	public String getDistributorAccountCountry() {
		return distributorAccountCountry;
	}

	/**
	 * @return the distributorAccountPartnerType
	 */
	public String getDistributorAccountPartnerType() {
		return distributorAccountPartnerType;
	}
	
	/**
	 * @return the distributorAccountCSN
	 */
	public String getDistributorAccountCSN() {
		return distributorAccountCSN;
	}

	/**
	 * @return the partnerAccountName
	 */
	public String getPartnerAccountName() {
		return partnerAccountName;
	}
	
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @return the projectedCloseDate
	 */
	public String getProjectedCloseDate() {
		return projectedCloseDate;
	}

	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @return the contactRole
	 */
	public String getContactRole() {
		return contactRole;
	}

	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @return the contactSelectionRadioButton
	 */
	public String getContactSelectionRadioButton() {
		return contactSelectionRadioButton;
	}

	/**
	 * @return the distributorSelectionRadioButton
	 */
	public String getDistributorSelectionRadioButton() {
		return distributorSelectionRadioButton;
	}

	/**
	 * @return the firstProductLineCheckbox
	 */
/*	public String getFirstProductLineCheckbox() {
		return firstProductLineCheckbox;
	}
*/
	
	/**
	 * @return the firstProductSeatsText
	 */
/*	public String getFirstProductSeatsText() {
		return firstProductSeatsText;
	}
*/

	/**
	 * @return the opportunityNumber
	 */
	public String getOpportunityNumber() {
		return opportunityNumber;
	}

	/**
	 * @return the opportunityName
	 */
	public String getOpportunityName() {
		return opportunityName;
	}

	/**
	 * @return the createPartnerOpportunityPage
	 */
	public Page_ getCreatePartnerOpportunityPage() {
		return createPartnerOpportunityPage;
	}

	/**
	 * @return the viewPartnerOpportunityPage
	 */
	public Page_ getViewPartnerOpportunityPage() {
		return viewPartnerOpportunityPage;
	}
	
	/**
	 * @return the viewAccountDetailsPage
	 */
	public Page_ getViewAccountDetailsPage() {
		return viewAccountDetailsPage;
	}
	/**
	 * Gets the Page object that defines GUI elements on the portal landing page
	 * @return The Page object that defines GUI elements on the portal landing page
	 */
	Page_ getPortalLandingPage() {
		return portalLandingPage;
	}
		
	protected void setCreateOpportunityStateVariables() {
		partnerAccountName = createPartnerOpportunityPage.getValueFromGUI("partnerAccountName");			
	}

	public String getAccountCSN() {
		return accountCSN;
	}

	public void setAccountCSN(String accountCSN) {
		this.accountCSN = accountCSN;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}	
	/**
	 * @return the CreateAccountFromAccountsLink
	 */
	public Page_ getCreateAccountFromAccountsLink() {
		return createAccountFromAccountsLink;
	}

	
	@Override
	public String toString() {
		return "Partner [super=" + super.toString() +
				", opptyName=" 		+ opptyName +
				", accountName="	+ accountName +
				", projectedCloseDate =" + projectedCloseDate +
				", stage=" 				+ stage +
				", contactRole=" + contactRole +
				", searchText=" + searchText +
				", contactSelectionRadioButton=" + contactSelectionRadioButton +
				", distributorSelectionRadioButton=" + distributorSelectionRadioButton +
				//", firstProductLineCheckbox=" +  firstProductLineCheckbox +
				//", firstProductSeatsText=" +  firstProductSeatsText +
				", opportunityNumber =" + opportunityNumber +
				", opportunityName	=" + opportunityName +
				", partnerAccountName=" + partnerAccountName +		

				", distributorAccountCSN=" + distributorAccountCSN +
				", distributorAccountName=" +			distributorAccountName +
				", distributorAccountCity=" +			distributorAccountCity +
				", distributorAccountCountry=" +		distributorAccountCountry +
				", distributorAccountPartnerType=" +	distributorAccountPartnerType +		
				", accountCSN="+	accountCSN+
				", accountType="+	accountType+
				
				"]";
	}

	/**
	 * @return the opptyName
	 */
	public String getOpptyName() {
		return opptyName;
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
	
	protected String createOpportunity() {
		Util.printInfo("Creating Opportunity for Partner...");
		
		commonPage.click("newButton");
		mainWindow.select();
		
		createPartnerOpportunityPage.populate(); 
		setCreateOpportunityStateVariables();
		
		commonPage.clickToSubmit("saveButton", "editButton");
		
		//NOTES TO OFFSHORE - the URL you are saving here is for an opportunity, not a partner.  Speaking
		//  of which, please see the notes in the test method for my comments on the Partner class
     	setUrl();
     	setId();

     	opportunityNumber = viewPartnerOpportunityPage.getValueFromGUI("opportunityNumber");
     	setName(opportunityNumber);
     	
     	Util.printInfo("Created opportunity '" + opportunityNumber + "' (" + getUrl() + ")");

     	return opportunityNumber;
	}

	protected String createOpportunityWithNewAccount(String accName) throws WriteException, BiffException, IOException {
		Util.sleep(2000);
        EISTestBase.switchDriverToFrame(1);
        HashMap<String, String> map= readFromExcel();
      	createPartnerOpportunityPage.setTestDataValue("opptyName",map.get("opptyName"));
    	createPartnerOpportunityPage.setTestDataValue("stage",map.get("stage"));
    	createPartnerOpportunityPage.setTestDataValue("contactRole",map.get("contactRole"));
    	createPartnerOpportunityPage.setTestDataValue("productLineSearchBox",map.get("productLineSearchBox"));
    	createPartnerOpportunityPage.setTestDataValue("firstProductSeatsText",map.get("firstProductSeatsText"));
    	   	
        Util.printInfo("Creating New Account for Partner...");
        createPartnerOpportunityPage.click("opportunity");
        commonPage.click("newButton");
        mainWindow.select();
        createPartnerOpportunityPage.setTestDataValue("accountNameText",accName);
       	createPartnerOpportunityPage.populate(); 
		setCreateOpportunityStateVariables();
		commonPage.clickToSubmit("saveButton", "editButton");
		viewPartnerOpportunityPage.setVerificationDataValue("type", map.get("type"));
		viewPartnerOpportunityPage.setVerificationDataValue("opportunityName", map.get("opptyName"));
		viewPartnerOpportunityPage.setVerificationDataValue("contactRoleInOpportunityContactRoles", map.get("contactRoleInOpportunityContactRoles"));
		viewPartnerOpportunityPage.setVerificationDataValue("contactEmailInOpportunityContactRolesForDistributor", map.get("contactEmailInOpportunityContactRolesForDistributor"));
		viewPartnerOpportunityPage.setVerificationDataValue("partnerNameInAssociatedPartners", map.get("partnerNameInAssociatedPartners"));
		viewPartnerOpportunityPage.setVerificationDataValue("partnerCSNInAssociatedPartners", map.get("partnerCSNInAssociatedPartners"));
		viewPartnerOpportunityPage.setVerificationDataValue("partnerCityInAssociatedPartners", map.get("partnerCityInAssociatedPartners"));
		viewPartnerOpportunityPage.setVerificationDataValue("partnerCountryInAssociatedPartners", map.get("partnerCountryInAssociatedPartners"));
		viewPartnerOpportunityPage.setVerificationDataValue("partnerTypeInAssociatedPartners", map.get("partnerTypeInAssociatedPartners"));
		viewPartnerOpportunityPage.setVerificationDataValue("productInproductsRelatedList", map.get("productInproductsRelatedList"));
		viewPartnerOpportunityPage.setVerificationDataValue("productTypeInproductsRelatedList", map.get("productTypeInproductsRelatedList"));
		viewPartnerOpportunityPage.setVerificationDataValue("licenseTypeInproductsRelatedList", map.get("licenseTypeInproductsRelatedList"));
		
		
		//NOTES TO OFFSHORE - the URL you are saving here is for an opportunity, not a partner.  Speaking
		//  of which, please see the notes in the test method for my comments on the Partner class
     	setUrl();
     	setId();

     	opportunityNumber = viewPartnerOpportunityPage.getValueFromGUI("opportunityNumber");
     	setName(opportunityNumber);
     	
     	Util.printInfo("Created opportunity '" + opportunityNumber + "' (" + getUrl() + ")");

     	return opportunityNumber;
	}

	protected void verifyAddressForOpportunity() {
		//String returnValue = "";
		
	//	Util.printInfo("Verifying Address of  the opportunity '" + opportunityNumber + "'...");
				
		mainWindow.select();

		createPartnerOpportunityPage.waitForFieldPresent("editButton");
		Util.sleep(2000);
		EISTestBase.switchDriverToFrame(2);
		createPartnerOpportunityPage.click("compareAddress");
		
		//This button is in the portal, not the common page.  And we have defined searchText on
		//  createPartnerOpportunityPage, so perhaps the button belongs there
		//commonPage.click("goButton");
		createPartnerOpportunityPage.click("useAsEntered");
		Util.printInfo("Verified Account for  the opportunity '" + opportunityNumber + "'");
		mainWindow.select();
		
	}
	//NOTES TO OFFSHORE - don't return something if it cannot be used by the caller
	//protected String addContactsToOpportunity() {
	protected void addContactsToOpportunity() {
		//String returnValue = "";
		
	//	Util.printInfo("Adding Contacts to the opportunity '" + opportunityNumber + "'...");
		Util.sleep(2000);
		EISTestBase.switchDriverToFrame(3);
		viewPartnerOpportunityPage.click("addContactButton");
		mainWindow.select();

		//NOTES TO OFFSHORE - It turns out we need to click the New button after clicking the Add button
		viewPartnerOpportunityPage.click("newContactButton");
		createPartnerOpportunityPage.waitForFieldPresent("contactRole");
		
		createPartnerOpportunityPage.populateInstance("ADD_CONTACTS");
		
		//This button is in the portal, not the common page.  And we have defined searchText on
		//  createPartnerOpportunityPage, so perhaps the button belongs there
		//commonPage.click("goButton");
		createPartnerOpportunityPage.click("searchButton");
		
		//NOTES TO OFFSHORE - Replaced sleep with call to waitForElementPresent
		//Util.sleep(15000);
		createPartnerOpportunityPage.waitForFieldPresent("contactSelectionRadioButton");

		createPartnerOpportunityPage.populateInstance("ADD_CONTACTS_1");
		
		//Since we had to click the New button, the controls are different
		//commonPage.clickToSubmit("saveButton", "editButton");
		//NOTES TO OFFSHORE - The empty "field name to wait for" will cause error checking to be performed, but
		//  no waiting for a field to appear. 
		createPartnerOpportunityPage.clickToSubmit("selectButton", "");
		commonPage.waitForFieldPresent("editButton");
		
		//NOTES TO OFFSHORE - you are again saving the URL of an oppty 
     	//setUrl();
     	//setId();
     	
		//NOTES TO OFFSHORE - don't return something if it cannot be used by the caller
     	//return returnValue;
		
		Util.printInfo("Added Contacts to the opportunity '" + opportunityNumber + "'");
	}
	
	
	
	//NOTES TO OFFSHORE - 
	//Please see the changes I made in addContactsToOpportunity, and apply them to:
	//  addDistributorsToOpportunity
	//  addProductsToOpportunity
	//  registerDealAndConfirm
	
	//location of controls, waitForElement instead of sleeping, etc.
	
	
	
	//protected String addDistributorsToOpportunity() {
	protected void addDistributorsToOpportunity() {
//		String returnValue = "";
//		Util.printInfo("Adding Distributors to the opportunity '" + opportunityNumber + "'...");
		
		mainWindow.select();
		Util.sleep(2000);
		EISTestBase.switchDriverToFrame(4);
		viewPartnerOpportunityPage.click("addDistributorButton");				
		createPartnerOpportunityPage.waitForFieldPresent("searchText");
		mainWindow.select();

		createPartnerOpportunityPage.populateInstance("ADD_DISTS");
		
		createPartnerOpportunityPage.clickToSubmit("goButton" , "");
		
		distributorAccountCSN = createPartnerOpportunityPage.getConstant("DIST_ACCOUNT_CSN");	
		
		createPartnerOpportunityPage.populateInstance("ADD_DISTS_1");
		
		createPartnerOpportunityPage.clickToSubmit("saveButton", "editButton");
		
		//NOTES TO OFFSHORE - perhaps control returned too soon, or an error (uncaught) caused us to never get to
		//  the view oppty page
		Util.sleep(2000);
		EISTestBase.switchDriverToFrame(4);
			
		mainWindow.select();

		Util.printInfo("Added Distributors to the opportunity '" + opportunityNumber + "'");
	}

	//protected String addProductsToOpportunity() {
	protected void addProductsToOpportunity() {
	//	Util.printInfo("Adding Products to the opportunity '" + opportunityNumber + "'...");

		mainWindow.select();
		viewPartnerOpportunityPage.click("addProductButton");
		createPartnerOpportunityPage.waitForFieldPresent("productLineSearchBox");
		
		createPartnerOpportunityPage.populateInstance("ADD_PRODUCTS");
		createPartnerOpportunityPage.clickToSubmit("saveButton", "editButton");

		//NOTES TO OFFSHORE - you are again saving the URL of an oppty 
     	//setUrl();
     	//setId();
		
		//NOTES TO OFFSHORE - why return the accountName?
		//return accountName;		

		Util.printInfo("Added Products to the opportunity '" + opportunityNumber + "'");
	}

	protected void addRequiredProductsToOpportunity() {


		mainWindow.select();
		viewPartnerOpportunityPage.click("addProductButton");
		createPartnerOpportunityPage.waitForFieldPresent("productLineSearchBox");
		
		createPartnerOpportunityPage.populateInstance("ADD_PRODUCTS_SEARCH");
		createPartnerOpportunityPage.clickToSubmit("searchButton", "searchButton");
		
		createPartnerOpportunityPage.populateInstance("ADD_PRODUCTS");
		createPartnerOpportunityPage.clickToSubmit("saveButton", "editButton");

		//NOTES TO OFFSHORE - you are again saving the URL of an oppty 
     	//setUrl();
     	//setId();
		
		//NOTES TO OFFSHORE - why return the accountName?
		//return accountName;		

		Util.printInfo("Added Products to the opportunity '" + opportunityNumber + "'");
	}

	//protected String registerDealAndConfirm() {
	protected void registerDealAndConfirm() {
	//	Util.printInfo("Registering the deal for opportunity '" + opportunityNumber + "'...");
		
		mainWindow.select();
		viewPartnerOpportunityPage.clickToSubmit("registerDealButton", "confirmButton");

		//Note that we are passing an override timeout value
		viewPartnerOpportunityPage.clickToSubmit("confirmButton", "addProductButton", EpartnerConstants.DEFAULT_OPPTY_SHARE_TIMEOUT);		

		//NOTES TO OFFSHORE - why return the oppty name?
		//return getName();		

		Util.printInfo("Registered the deal for opportunity '" + opportunityNumber + "'");
	}
	
	protected void waitForOpptyShare() {
		final int interval = 2000;
		
		String partnerOpportunityNumber = getOpportunityNumber();
		
		int timeout = EpartnerConstants.DEFAULT_OPPTY_SHARE_TIMEOUT;
		boolean opened = false;
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeout;
		
		Util.printInfo("Waiting for opportunity to be shared...");

		mainWindow.select();
		
		//TODO  Most (if not all of these elements should not be defined in Page_Common.  They should be in
		//  Page_CommonPortal instead!

		//Do a search on nothing, just to get the searchAgainBox box to appear
		commonPage.click("goButtoninSidebar");	
		
		//We only need to do this once, because the search box stays populated after every search (I BELIEVE!!!)
		commonPage.populateField("searchBox", partnerOpportunityNumber);
		
		while (System.currentTimeMillis() < endTime) {
			//We could call the clickAndWait(String fieldName, String fieldNameToWaitFor) version, and then add a
			//  Util.sleep(interval) call outside of the if block, but this is more succinct
			if (commonPage. clickAndWait("searchButton", "editButton", interval)) {
				opened = true;
				Util.printInfo("Opportunity number " + partnerOpportunityNumber + " is visible to distributor");
				break;
			}
		}
			
		if (!opened) {
			fail("Opportunity number " + partnerOpportunityNumber + " did not become visible to distributor after waiting " + (timeout / 1000) + " seconds");
		}		
		
		Util.printInfo("Opportunity was successfully shared");
	}
	protected void waitForAccountCSN() {
		final int interval = 2000;		
		accountCSN = viewAccountDetailsPage.getValueFromGUI("accountCSN");
		
		int timeout = EpartnerConstants.DEFAULT_OPPTY_SHARE_TIMEOUT;
		boolean opened = false;
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeout;
		
		Util.printInfo("Waiting for Account CSN to be displayed...");

		
		while (System.currentTimeMillis() < endTime) {
			viewAccountDetailsPage.refresh(interval);
			accountCSN = viewAccountDetailsPage.getValueFromGUI("accountCSN");
			if (!accountCSN.isEmpty()) {
				opened = true;
				Util.printInfo("Account CSN " + accountCSN + " is displayed");
				break;
			}
		}
			
		if (!opened) {
			fail("Account CSN " + accountCSN + "  did not become visible after waiting " + (timeout / 1000) + " seconds");
		}		
		
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
	public static  HashMap<String, String> readFromExcel() throws IOException, WriteException, BiffException {
		  // TODO Auto-generated method stub
				List<String> keyList = new ArrayList<String>(); 
				List<String> list = new ArrayList<String>(); 
				HashMap<String, String> map = new HashMap<String, String>();   
				String str = null;
			
				try
		            {
		                File f1=new File("//ecs-9844/DoNotDelete/JenkinsData/input.xls");
		               //the excel sheet which contains data
		                WorkbookSettings ws=new WorkbookSettings();
		                ws.setLocale(new Locale("er","ER"));
		                Workbook workbook=Workbook.getWorkbook(f1,ws);
		                Sheet readsheet=workbook.getSheet(0);
		                //Loop to read the KEYS from Excel i.e, 1st column of the Excel
		                for(int i=0;i<readsheet.getColumns();i++) {
		              	str=readsheet.getCell(i,0).getContents().toString();
		              	list.add(str);
		                }
		              	keyList.addAll(list);
		              	// Hardcoding the first map (key, value) values           
		               	map.put(keyList.get(0), readsheet.getCell(0, 1).getContents().toString());
		               	// Loop to read TEST DATA from the Excel
		                for(int i=1;i<readsheet.getRows();i++) {
		                for(int j=1;j<readsheet.getColumns();j++) {	
		                str=readsheet.getCell(j,i).getContents().toString();
		                list.add(str);
		                System.out.println(str);
		                map.put(keyList.get(j),str); 
		                	}
		               }
		             //Print the map(key, value)   
		               System.out.println("Print map");
		               System.out.println(map);
		             
		              }
		            catch(IOException e)
		            {
		                e.printStackTrace();
		            }

		            catch(BiffException e)
		            {
		                e.printStackTrace();
		            } catch (Exception e) {
		                e.printStackTrace(); 
		            }
					return map;
	}
	
	

}
