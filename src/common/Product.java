package common;

/**
 * Representation of an application-independent SFDC Product.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class Product extends SFDCObject {
	//State variables 
	//  Some (like name, id, and url) are defined in the superclass
	//  Define only object-specific ones here
	//These are set in Oppty.addProducts and Oppty.editProducts
	private String opptyName							= "";
	private String productLine							= "";
	private String productType							= "";
	private String licenseType							= "";
	private int estimatedSeats							= 0;
	private double salesPrice							= 0.0;

	//This is referred to as Total price in the Oppty, but Net Price in the DAR
	private double totalAndNetPrice						= 0.0;

	private String billingDate							= "";
	private String comments								= "";

	//These are set in DiscountApprovalRequest.editProducts
	private DiscountCategoryCode discountCategoryCode	= null;
	private String discountCategoryName					= "";
	private double srp									= 0.0;
	private double totalSrp								= 0.0;
	private double contractualDiscPct					= 0.0;
	private double contractualDiscAmt					= 0.0;
	private double additionalDiscPct					= 0.0;
	private double additionalDiscAmt					= 0.0;
	private double totalEndUserDiscPct					= 0.0;
	private double totalEndUserDiscAmt					= 0.0;
	private double unspokenDiscPct						= 0.0;
	private double unspokenDiscAmt						= 0.0;
	private double totalDiscPct							= 0.0;
	private double totalDiscAmt							= 0.0;
	private double netSrp								= 0.0;
	
	//Make all Page objects private, but provide getters so that test code can have read
	//  access to them.  That will allow test code to access test data, metadata, constants,
	//  etc., through this SFDCObject instance.  That's important, because we don't want
	//  test code authors to declare Page objects when they don't need to; instead we want
	//  test code to call utilities wherever possible.  Those utilities typically create an
	//  SFDCObject, instantiate the Page objects it needs, and pass back the SFDCObject
	//  instance.  It is through that instance that test code can get access to this
	//  object's Page data

	//NOTE that this constructor does not accept Page objects.  This is unlike the way other SFDCObject subclasses
	//  work, but that is because adding and editing products is done using a Page that is associated with Oppty
	//  (PageAddEditProducts).  This may change at some point.
	public Product(String opptyName, String productLine) {
		super(EISConstants.OBJECT_TYPE_PRODUCT);
		
		//mainWindow	= EISTestBase.mainWindow;
		//commonPage	= EISTestBase.commonPage;
		
		this.opptyName = opptyName;
		this.productLine = productLine;

		setName(productLine);
	}
	
	public String getOpptyName() {
		return opptyName;
	}

	public String getProductLine() {
		return productLine;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public int getEstimatedSeats() {
		return estimatedSeats;
	}

	public void setEstimatedSeats(int estimatedSeats) {
		this.estimatedSeats = estimatedSeats;
	}

	public double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}

	//We are using one variable (totalAndNetPrice) to store a value that the user sees
	//  under two names: Total Price in an Oppty, and Net Price in a DAR.  Therefore
	//  we are providing some additional accessors
	public double getTotalAndNetPrice() {
		return totalAndNetPrice;
	}

	public void setTotalAndNetPrice(double totalAndNetPrice) {
		this.totalAndNetPrice = totalAndNetPrice;
	}

	public void setTotalAndNetPrice() {
		totalAndNetPrice = estimatedSeats * salesPrice;
	}

	public double getTotalPrice() {
		return totalAndNetPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalAndNetPrice = totalPrice;
	}

	public void setTotalPrice() {
		totalAndNetPrice = estimatedSeats * salesPrice;
	}

	public double getNetPrice() {
		return totalAndNetPrice;
	}

	public void setNetPrice(double netPrice) {
		this.totalAndNetPrice = netPrice;
	}

	public void setNetPrice() {
		totalAndNetPrice = estimatedSeats * salesPrice;
	}
	
	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public DiscountCategoryCode getDiscountCategoryCode() {
		return discountCategoryCode;
	}

	public void setDiscountCategoryCode(DiscountCategoryCode discountCategoryCode) {
		this.discountCategoryCode = discountCategoryCode;
	}

	public String getDiscountCategoryName() {
		return discountCategoryName;
	}

	public void setDiscountCategoryName(String discountCategoryName) {
		this.discountCategoryName = discountCategoryName;
	}

	public double getSrp() {
		return srp;
	}

	public void setSrp(double srp) {
		this.srp = srp;
	}

	public double getTotalSrp() {
		return totalSrp;
	}

	public void setTotalSrp(double totalSrp) {
		this.totalSrp = totalSrp;
	}

	public double getContractualDiscPct() {
		return contractualDiscPct;
	}

	public void setContractualDiscPct(double contractualDiscPct) {
		this.contractualDiscPct = contractualDiscPct;
	}

	public double getContractualDiscAmt() {
		return contractualDiscAmt;
	}

	public void setContractualDiscAmt(double contractualDiscAmt) {
		this.contractualDiscAmt = contractualDiscAmt;
	}

	public double getAdditionalDiscPct() {
		return additionalDiscPct;
	}

	public void setAdditionalDiscPct(double additionalDiscPct) {
		this.additionalDiscPct = additionalDiscPct;
	}

	public double getAdditionalDiscAmt() {
		return additionalDiscAmt;
	}

	public void setAdditionalDiscAmt(double additionalDiscAmt) {
		this.additionalDiscAmt = additionalDiscAmt;
	}

	public double getTotalEndUserDiscPct() {
		return totalEndUserDiscPct;
	}

	public void setTotalEndUserDiscPct(double totalEndUserDiscPct) {
		this.totalEndUserDiscPct = totalEndUserDiscPct;
	}

	public double getTotalEndUserDiscAmt() {
		return totalEndUserDiscAmt;
	}

	public void setTotalEndUserDiscAmt(double totalEndUserDiscAmt) {
		this.totalEndUserDiscAmt = totalEndUserDiscAmt;
	}

	public double getUnspokenDiscPct() {
		return unspokenDiscPct;
	}

	public void setUnspokenDiscPct(double unspokenDiscPct) {
		this.unspokenDiscPct = unspokenDiscPct;
	}

	public double getUnspokenDiscAmt() {
		return unspokenDiscAmt;
	}

	public void setUnspokenDiscAmt(double unspokenDiscAmt) {
		this.unspokenDiscAmt = unspokenDiscAmt;
	}

	public double getTotalDiscPct() {
		return totalDiscPct;
	}

	public void setTotalDiscPct(double totalDiscPct) {
		this.totalDiscPct = totalDiscPct;
	}

	public double getTotalDiscAmt() {
		return totalDiscAmt;
	}

	public void setTotalDiscAmt(double totalDiscAmt) {
		this.totalDiscAmt = totalDiscAmt;
	}

	public double getNetSrp() {
		return netSrp;
	}

	public void setNetSrp(double netSrp) {
		this.netSrp = netSrp;
	}

	@Override
	public String toString() {
		return "Product [super=" + super.toString() +
				", opptyName=" + opptyName + 
				", productLine=" + productLine +
				", productType=" + productType +
				", licenseType=" + licenseType +
				", estimatedSeats=" + estimatedSeats +
				", salesPrice=" + salesPrice +
				", totalPrice=" + totalAndNetPrice +
				", billingDate=" + billingDate +
				", comments=" + comments +
				", discountCategoryCode=" + discountCategoryCode +
				", discountCategoryName=" + discountCategoryName +
				", srp=" + srp +
				", totalSrp=" + totalSrp +
				", contractualDiscPct=" + contractualDiscPct +
				", contractualDiscAmt=" + contractualDiscAmt +
				", additionalDiscPct=" + additionalDiscPct +
				", additionalDiscAmt=" + additionalDiscAmt +
				", totalEndUserDiscPct=" + totalEndUserDiscPct +
				", totalEndUserDiscAmt=" + totalEndUserDiscAmt +
				", unspokenDiscPct=" + unspokenDiscPct +
				", unspokenDiscAmt=" + unspokenDiscAmt +
				", totalDiscPct=" + totalDiscPct +
				", totalDiscAmt=" + totalDiscAmt +
				", netSrp=" + netSrp +
				", netPrice=" + totalAndNetPrice +
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
	
	//This will need to be modified to handle the many ways in which a product can be edited
	@Override
	public void openForEdit() {
		mainWindow.select();
		open();
		
		//commonPage.clickAndWait("editButton");
		commonPage.clickAndWait("editButton", "saveButton");
	}

	public void setEstimatedSeats(Double valueOf) {
		// TODO Auto-generated method stub
		
	}

	//For now, adding and editing products (en masse) is done on the PageAddEditProducts page, which is
	//  associated with the Oppty object.  If and when we need to edit a product in the normal way, we will
	//  add an edit() method
}
