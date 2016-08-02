package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.DiscountCategory.DiscountCategoryDetails;
import common.ApprovalChain.Chain;
import common.EISConstants.AlertResponseType;

/**
 * Representation of an MJA Discount Approval Request.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class DiscountApprovalRequest extends SFDCObject {
	//State variables 
	//  Some (like name, id, and url) are defined in the superclass
	//  Define only object-specific ones here
	private double totalSrpOppty = 0;
	private double totalDiscAmtOppty = 0;
	private double totalDiscPctOppty = 0;
	private Map<DiscountCategoryCode, List<Product>> prodsByDiscCategory = new HashMap<DiscountCategoryCode, List<Product>>();
	WebDriver driver=EISTestBase.driver;
	//TODO  Add necessary fields.  What are they?  Things like  totals and chains associated with
	//  discount categories, total and chain associated with the entire oppty
	//  (and be sure to add getters for them and reference them in toString())
	//(!!!!!  DO NOT add products - nothing that happens in a DAR affects the products associated with the oppty!)

	//Make all Page objects private, but provide getters so that test code can have read
	//  access to them.  That will allow test code to access test data, metadata, constants,
	//  etc., through this SFDCObject instance.  That's important, because we don't want
	//  test code authors to declare Page objects when they don't need to; instead we want
	//  test code to call utilities wherever possible.  Those utilities typically create an
	//  SFDCObject, instantiate the Page objects it needs, and pass back the SFDCObject
	//  instance.  It is through that instance that test code can get access to this
	//  object's Page data

	private Page_ editDiscountApprovalRequestPage;
	private Page_ viewDiscountApprovalRequestPage;

	public DiscountApprovalRequest(Page_ editDiscountApprovalRequestPage, Page_ viewDiscountApprovalRequestPage) {
		super(EISConstants.OBJECT_TYPE_DAR);
		
		//mainWindow	= EISTestBase.mainWindow;
		//commonPage	= EISTestBase.commonPage;

		this.editDiscountApprovalRequestPage	= editDiscountApprovalRequestPage;
		this.viewDiscountApprovalRequestPage	= viewDiscountApprovalRequestPage;
	}
	
	public Page_ getEditDiscountApprovalRequestPage() {
		return editDiscountApprovalRequestPage;
	}

	public Page_ getViewDiscountApprovalRequestPage() {
		return viewDiscountApprovalRequestPage;
	}

	public Map<DiscountCategoryCode, List<Product>> getProdsByDiscCategoryCollection() {
		return prodsByDiscCategory;
	}
	
	public List<Product> getProdsByDiscCategory(DiscountCategoryCode discCategoryCode) {
		return prodsByDiscCategory.get(discCategoryCode);
	}
	
	public double getTotalSrpOppty() {
		return totalSrpOppty;
	}

	public double getTotalDiscAmtOppty() {
		return totalDiscAmtOppty;
	}

	public double getTotalDiscPctOppty() {
		return totalDiscPctOppty;
	}

	@Override
	public String toString() {
		return "Product [super=" + super.toString() +
				", totalSrpOppty=" + totalSrpOppty +
				", totalDiscAmtOppty=" + totalDiscAmtOppty +
				", totalDiscPctOppty=" + totalDiscPctOppty +
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
	
	@Override
	public void openForEdit() {
		mainWindow.select();
		open();
		
		//commonPage.clickAndWait("editButton");
		commonPage.clickAndWait("editButton", "saveButton");
	}

	//Editing products in the DAR is done en masse
	//TODO  Allow passing an optional constant set name, so that this routine can be called more than
	//  once in a test
	protected void editProducts(Map<String, Product> products, boolean openIt) {
		Product product;
		String instanceNamesString;
		String productLine;
		String value;
		DiscountCategoryCode discCategoryCode;
		String discCategoryName;
		int numProducts;
		String messageChunk;
		List<String> instanceSet = new ArrayList<String>();
		List<Product> prodsInCategory = new ArrayList<Product>();
		Map<String, List<String>> savedFieldLocators = new HashMap<String, List<String>>();
		
		if (openIt) {
			openForEdit();
		}
		
		instanceNamesString = editDiscountApprovalRequestPage.getConstant(EISConstants.DAR_EDIT_PRODUCTS_CONSTANT_SET_NAME);
		if (!instanceNamesString.isEmpty()) {
			instanceSet = Util.listOfStringTrim(Arrays.asList(instanceNamesString.split(EISConstants.PARAMETER_DELIM)));
		} else {
			instanceSet.add("1");
		}
		
		numProducts = instanceSet.size();
		messageChunk = " product" + (numProducts == 1 ? "" : "s");
		
       	//DARs don't have names, so let's use the oppty name
     	Util.printInfo("Editing " + numProducts + messageChunk + " in the Discount Approval Request associated with oppty '" + getName() + "'...");
     	
		//We have to save the locators of all Fields on the page, not just those referenced in instanceFieldData, because we
		//  will be scraping the GUI.  We can do this outside of the loop, since we are manipulating all Field objects, not
		//  just those referenced in instanceFieldData
		savedFieldLocators = editDiscountApprovalRequestPage.getAllFieldLocators();

		for (String instance : instanceSet) {
			//Get the value of the addEditProductsPage.prodLine FieldData record, which we are using to store the product line
			productLine = editDiscountApprovalRequestPage.getTestDataInstanceValue(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);

			//Save the default (with tokens) locators, so that we have tokens to replace when dealing with subsequent instances
			//savedFieldLocators = EISTestBase.getReferencedFieldLocators(editDiscountApprovalRequestPage, instanceFieldData);

			//Replace the tokens in the locators of the Field objects associated with the product with the value of productLine
			//EISTestBase.parseReferencedFieldLocators(editDiscountApprovalRequestPage, instanceFieldData, productLine);
			//We have to parse the locators of all Fields on the page, not just those referenced in instanceFieldData, because we
			//  will be scraping the GUI
			editDiscountApprovalRequestPage.parseAllFieldLocatorsTokens(productLine);
			
			//Delete the prodLine FieldData record, because it is of type READ_ONLY
			editDiscountApprovalRequestPage.deleteTestDataInstanceRecord(EISConstants.PRODUCT_NAME_FIELD_NAME, instance);
			
			//Populate the product fields in the GUI
			editDiscountApprovalRequestPage.populateInstance(instance);
			
			product = products.get(productLine);
			
			//At the end of this method we need to reload the discCategoryProdNames map with the list of products
			//  as they stand.  Since that map is keyed on DiscountCategoryCode, we need to calculate it here.
			//  Getting discCategory from GUI because the selected value in a Field of type PICKLIST will often
			//  not match the value provided in test properties
			discCategoryName = editDiscountApprovalRequestPage.getValueFromGUI("discCategory");
			product.setDiscountCategoryName(discCategoryName);
			
			discCategoryCode = DiscountCategory.getDiscountCategoryCode(discCategoryName);
			product.setDiscountCategoryCode(discCategoryCode);
			
			//Since none of the edit-able fields are required to be edited, we cannot count on finding their values in
			//  test properties.  However, we can check there first, and scrape the GUI (slow!) only when necessary
			value = editDiscountApprovalRequestPage.getTestDataInstanceValue("SRP", instance);
			if (value.isEmpty()) {
				value = editDiscountApprovalRequestPage.getValueFromGUI("SRP");
			}
			product.setSrp(Double.valueOf(value));
			
			value = editDiscountApprovalRequestPage.getTestDataInstanceValue("contractualDiscPct", instance);
			if (value.isEmpty()) {
				value = editDiscountApprovalRequestPage.getValueFromGUI("contractualDiscPct");
			}
			product.setContractualDiscPct(Double.valueOf(value));
			
			value = editDiscountApprovalRequestPage.getTestDataInstanceValue("additionalDiscPct", instance);
			if (value.isEmpty()) {
				value = editDiscountApprovalRequestPage.getValueFromGUI("additionalDiscPct");
			}
			product.setAdditionalDiscPct(Double.valueOf(value));
			
			value = editDiscountApprovalRequestPage.getTestDataInstanceValue("aceDiscPct", instance);
			if (value.isEmpty()) {
				value = editDiscountApprovalRequestPage.getValueFromGUI("aceDiscPct");
			}
			product.setAdditionalDiscPct(Double.valueOf(value));
			
			value = editDiscountApprovalRequestPage.getTestDataInstanceValue("baseDiscPct", instance);
			if (value.isEmpty()) {
				value = editDiscountApprovalRequestPage.getValueFromGUI("baseDiscPct");
			}
			product.setAdditionalDiscPct(Double.valueOf(value));
			
			value = editDiscountApprovalRequestPage.getTestDataInstanceValue("comments", instance);
			if (value.isEmpty()) {
				value = editDiscountApprovalRequestPage.getValueFromGUI("comments");
			}
			product.setComments(value);
			
			//Set all the other Product fields that may have changed based on the edits that were made
			double totalSrp = product.getEstimatedSeats() * product.getSrp();
			product.setTotalSrp(totalSrp);
			
			product.setContractualDiscAmt(product.getContractualDiscPct() * (totalSrp / 100));
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
			
			//Replace the modified locators with the default locators, so that we have tokens to replace when
			//  dealing with the next instance
			editDiscountApprovalRequestPage.setFieldLocators(savedFieldLocators);
		}
		
		prodsByDiscCategory.clear();
	    for (Entry<String, Product> entry : products.entrySet()) {
	    	product = entry.getValue();
	    	discCategoryCode = product.getDiscountCategoryCode();
	    	
    		prodsInCategory = new ArrayList<Product>();
	    	if (prodsByDiscCategory.containsKey(discCategoryCode)) {
	    		prodsInCategory = prodsByDiscCategory.get(discCategoryCode);
	    	}
	    	
	    	prodsInCategory.add(product);
	    	prodsByDiscCategory.put(discCategoryCode, prodsInCategory);
	    }
	    
	    editDiscountApprovalRequestPage.populateField("discJustification");

	    editDiscountApprovalRequestPage.clickToSubmit("validateAndSaveButton");

     	Util.printInfo("Edited " + numProducts + messageChunk + " in the Discount Approval Request associated with oppty '" + getName() + "'");
	}

	protected void editProducts(Map<String, Product> products) {
		editProducts(products, true);
	}
	
	protected void verifyApprovalChain(Map<String, Product> products) {
		List<ApprovalRoleCode> approvalRoleCodes;
		List<String> approverNamesActual;
		List<String> approverNamesExpected = new ArrayList<String>();
		
		//Must do this before calling generateApprovalChain
		generateTotalOpptyValues(products);
		
		//Get a list of expected approver names by first getting a list of the role codes, then getting the
		//  names associated with those roles
		approvalRoleCodes = generateApprovalChain(products);

		for (ApprovalRoleCode approvalRoleCode : approvalRoleCodes) {
			approverNamesExpected.add(Approver.getApprovalDetails(approvalRoleCode).getName().trim());
		}
		
		//Get a list of actual approver names by scraping them from the GUI
		displayApprovalChain();
		
		approverNamesActual = viewDiscountApprovalRequestPage.getTableColumn("approverNameInApprovalChainTable", true);
		
		//Compare the lists (case-insensitive match)
		boolean previousSetting = EISTestBase.setVerifyCaseSensitive(false);

		//Note that as of 04/04/2012 there is an outstanding issue with the SD role not appearing in the generated
		//  chain number 7.  Syed is investigating
		//UPDATE:  As of late April, there are a number issues, which are being investigated by Gurdev
		EISTestBase.assertEqualsWithFlags(viewDiscountApprovalRequestPage.getName(), "approverNameInApprovalChainTable", approverNamesActual, approverNamesExpected);

		//Set the verifyCaseSensitive flag back to its previous setting
		EISTestBase.setVerifyCaseSensitive(previousSetting);
	}

	protected List<ApprovalRoleCode> generateApprovalChain(Map<String, Product> products) {
		Chain approvalChain;
		List<Chain> discCategoryChains = new ArrayList<Chain>();
		Set<ApprovalRoleCode> approvalRoleCodes = new TreeSet<ApprovalRoleCode>();
		
		//Get the chains for all discount categories
	    for (Entry<DiscountCategoryCode, List<Product>> entry : prodsByDiscCategory.entrySet()) {
	    	approvalChain = generateDiscCategoryChain(entry.getKey(), entry.getValue());
	    	discCategoryChains.add(approvalChain);
	    	
	    	//Util.printInfo("For discount category " + entry.getKey() + ", generated chain number " + approvalChain.getChainNum() + " (" + approvalChain.getApprovalRoleCodes().toString() + ")");
	    }

	    //Merge the ApprovalRoleCodes from all the category chains into a TreeSet collection,
	    //  which rejects duplicates and maintains order
	    for (Chain chain : discCategoryChains) {
	    	approvalRoleCodes.addAll(chain.getApprovalRoleCodes());
	    }
	    
	    return new ArrayList<ApprovalRoleCode>(approvalRoleCodes);
	}
	
	private void generateTotalOpptyValues(Map<String, Product> products) {
		Product product;
		DiscountCategoryCode discountCategoryCode;
		
		//Exclude all of the exception categories.  According to Syed (04/06/2012) we should
		//  exclude those categories from totalSrpOppty as well as from totalDiscAmtOppty.
		//  Also, he said to treat DNA the same way.
		for (Entry<String, Product> entry : products.entrySet()) {
	    	product = entry.getValue();
	    	
	    	discountCategoryCode = product.getDiscountCategoryCode();
	    	if ((discountCategoryCode != DiscountCategoryCode.SCS) && (discountCategoryCode != DiscountCategoryCode.SCSLF) && (discountCategoryCode != DiscountCategoryCode.DNA)) {
		    	totalSrpOppty += product.getTotalSrp();
		    	totalDiscAmtOppty += product.getTotalDiscAmt();
	    	}
	    }
		
		totalDiscPctOppty = (totalDiscAmtOppty / totalSrpOppty) * 100;
	}

	private Chain generateDiscCategoryChain(DiscountCategoryCode discCategoryCode, List<Product> products) {
		Chain approvalChain;
		DiscountCategoryDetails discCategoryDetails;
		String chainNum;
		double totalSrp = 0;
		double addlDiscPct = 0;
		double addlDiscAmt = 0;
		String message = "For discount category " + discCategoryCode + ", generated chain number ";

		discCategoryDetails = DiscountCategory.getDiscountCategoryDetails(discCategoryCode);
		
		//Attempt to get a chain number based on the total oppty discount pct and amt
		chainNum = discCategoryDetails.getTotalDiscChainNum(totalDiscPctOppty, totalDiscAmtOppty);
		
		//If we did not get a chain number based on total oppty discount pct and amt, get it using addl discount pct and amt
		if (chainNum.isEmpty()) {
			for (Product product : products) {
				totalSrp += product.getTotalSrp();
				addlDiscAmt += product.getAdditionalDiscAmt();
		    }

			addlDiscPct = (addlDiscAmt / totalSrp) * 100;
			
			chainNum = discCategoryDetails.getAddlDiscChainNum(addlDiscPct, addlDiscAmt);

	    	message += chainNum + " based on Additional Discount (pct = " + EISTestBase.formatNumber(addlDiscPct) + ", amt = " + EISTestBase.formatNumber(addlDiscAmt) + "); roles are ";
		} else {
			message += chainNum + " based on Total Opportunity Discount (pct = " + EISTestBase.formatNumber(totalDiscPctOppty) + ", amt = " + EISTestBase.formatNumber(totalDiscAmtOppty) + "); roles are ";
		}
		
		approvalChain = ApprovalChain.getApprovalChain(chainNum);

		message += approvalChain.getApprovalRoleCodes().toString();
 
		Util.printInfo(message);

		return approvalChain;
	}

	public void calculateDiscount() {
		openForEdit();
		
		editDiscountApprovalRequestPage.clickToSubmit("calculateDiscountButton");
	}

	public void displayApprovalChain() {
		//open();
		
		viewDiscountApprovalRequestPage.clickToSubmit("displayApprovalChainButton");
	}

	public void submitForApproval() {
		open();
		viewDiscountApprovalRequestPage.clickToSubmit("submitForApprovalButton");
		viewDiscountApprovalRequestPage.clickToSubmit("iAgreeButton");
		
	}

	public void addAdditionalApprovers() {
		//open();
//		if(editDiscountApprovalRequestPage.isFieldVisible("validateAndSaveButton")){
//		editDiscountApprovalRequestPage.click("validateAndSaveButton");
//		editDiscountApprovalRequestPage.click("okButton");
//		}
		viewDiscountApprovalRequestPage.verifyFieldExists("addAdditionalApproversButton");
		viewDiscountApprovalRequestPage.click("addAdditionalApproversButton");
		viewDiscountApprovalRequestPage.waitForFieldVisible("updateButton");
		
		viewDiscountApprovalRequestPage.populate();
		
		//When dealing with dismissing/submitting overlays, we cannot call Page_.clickToSubmit(String fieldName), because
		//  the default field it waits for (Page_Common.privacyStatement) is present throughout the process (see the logic
		//  in the method for details.)  But we can't use the Page_.clickToSubmit(String fieldName, String fieldToWaitForName)
		//  either, because all objects in both the overlay and the underlying page are always present.
		//So we do this in multiple steps because addAdditionalApproversButton is always present (except for a very brief
		//  period while updateButton is not visible), but "in the background" (behind the overlay).
		//NOTE that updateButton is ALWAYS present - it's just not always visible
		//viewDiscountApprovalRequestPage.clickAndWait("updateButton", "addAdditionalApproversButton");
		viewDiscountApprovalRequestPage.click("updateButton");
		viewDiscountApprovalRequestPage.waitForFieldNotVisible("updateButton");
	}

	public void editPercentages() {
		//Verify Step 1-Price List URL
	    String expectedPriceListURL=EISTestBase.testProperties.getConstant("PRICE_LIST_URL");
		Util.printDebug("Expected Price List URL is :"+ expectedPriceListURL); 
//		
//		editDiscountApprovalRequestPage.click("priceList");
//		mainWindow.setLocator(EISTestBase.driver.getWindowHandle());
//		for(String winHandle : ((WebDriver) driver).getWindowHandles()){
//			 ((WebDriver) driver).switchTo().window(winHandle);    
//		}	
//		setUrl();
//		String actualPriceListURL=getUrl();
//		Util.printInfo("Actual Price List URL is :"+ actualPriceListURL);
		String actualPriceListURL=editDiscountApprovalRequestPage.getAttribute("priceList", "href");
		EISTestBase.assertEquals(actualPriceListURL, expectedPriceListURL);	
		mainWindow.select();
		
			editDiscountApprovalRequestPage.click("applyDiscount");
			editDiscountApprovalRequestPage.populateInstance("STEP2A");
			editDiscountApprovalRequestPage.click("applyButtonA");
			editDiscountApprovalRequestPage.populateInstance("STEP2B");
			editDiscountApprovalRequestPage.click("applyButtonB");
			
		if(EISTestBase.testProperties.getConstant("OPPTY_TYPE").equalsIgnoreCase("Partner Opportunity")){
			editDiscountApprovalRequestPage.populateInstance("STEP2C");
			editDiscountApprovalRequestPage.click("aceBaseDiscApply");
		}
		  editDiscountApprovalRequestPage.populate();
		  editDiscountApprovalRequestPage.click("validateAndSaveButton");
		  editDiscountApprovalRequestPage.click("okButton");
//		  driver.findElement(By.xpath("//table[@id='button']//input[normalize-space(@class)='btn' and normalize-space(@value)='Validate and Save']")).click();
//		  editDiscountApprovalRequestPage.acceptAlert();
//		  editDiscountApprovalRequestPage.clickToSubmit("submitForApproval");
//		  editDiscountApprovalRequestPage.click("iAgreeButton");
	}

	public void submitDARForApproval() {
		 editDiscountApprovalRequestPage.clickToSubmit("submitForApproval");
		 editDiscountApprovalRequestPage.click("iAgreeButton");
		
	}
}
