package common;

/**
 * Discount Approval Request (DAR) approval role codes.
 * 
 * NOTE that the order of these codes matters!  A role's position in this declaration
 * determines its approval rank!
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public enum ApprovalRoleCode {
	/**
	 * Sales Representative
	 */
	SR,
	/**
	 * Sales Manager
	 */
	SM,
	/**
	 * Sales Finance Partner
	 */
	SFP,
	/**
	 * Sales Director
	 */
	SD,
	/**
	 * GEO Revenue Manager
	 */
	GRM,
	/**
	 * Sales Finance Director
	 */
	SFD,
	/**
	 * M&E VP
	 */
	MEVP,
	/**
	 * Sales VP
	 */
	SVP,
	/**
	 * Sales Industry or Emerging VP
	 */
	SIEVP,
	/**
	 * DRB
	 */
	DRB
}
