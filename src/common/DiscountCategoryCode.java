package common;

/**
 * Discount Approval Request (DAR) discount category codes.
 * 
 * NOTE that the ordering of these codes is irrelevant.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public enum DiscountCategoryCode {
	/**
	 * License Products
	 */
	LP,
	/**
	 * CPM Services
	 */
	CPMS,
	/**
	 * Creative Finishing SW
	 */
	CFSW,
	/**
	 * Creative Finishing HW
	 */
	CFHW,
	/**
	 * Creative Finishing Support Svcs
	 */
	CFSS,
	/**
	 * Subscription and Cloud Services
	 */
	SCS,
	/**
	 * Subscription and Cloud Services Late Fees
	 */
	SCSLF,
	/**
	 * Discount not applicable
	 */
	DNA
}
