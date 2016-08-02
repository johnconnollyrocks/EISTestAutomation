package common;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines Discount Approval Request (DAR) approvers.
 * 
 * Defines all AMER approvers, each of which is comprised of an ApprovalRoleCode value, name, email address,
 * email client password, SFDC user name, and SFDC password
 * @see ApprovalRoleCode
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class Approver {
	private static Map<ApprovalRoleCode, ApproverDetails> approvers = new HashMap<ApprovalRoleCode, ApproverDetails>();
	
	private Approver(ApprovalRoleCode approvalRoleCode, String name, String email, String emailPassword, String sfdcUserName, String sfdcPassword) {
		approvers.put(approvalRoleCode, new ApproverDetails(name, email, emailPassword, sfdcUserName, sfdcPassword));
	}

	//TODO  NOTE that this class currently addresses AMER approvers only!  We need to modify it to handle other regions.
	static {
		new Approver(ApprovalRoleCode.SR,		"Spencer Adams", 	"mja.rep@gmail.com", 			"sfdc1234", "spencer.adams@autodesk.com.adsksfstg",		"sfstg1234");
		new Approver(ApprovalRoleCode.SM,		"Thomas Cooper", 	"mja.salesmanager@gmail.com", 	"sfdc1234", "tom.cooper@autodesk.com.prd.adsksfstg",	"sfstg1234");
		new Approver(ApprovalRoleCode.SFP,		"Paul Serekis", 	"mja.fbp@gmail.com", 			"sfdc1234", "?", "?");
		new Approver(ApprovalRoleCode.SD,		"Neal Nicholas", 	"mja.director@gmail.com", 		"sfdc1234", "?", "?");
		new Approver(ApprovalRoleCode.GRM,		"Mike Pak", 		"mja.geomanager@gmail.com", 	"sfdc1234", "?", "?");
		new Approver(ApprovalRoleCode.SFD,		"Jamie Westcott", 		"mja.adsk@gmail.com", 			"sfdc1234", "?", "?");
		new Approver(ApprovalRoleCode.MEVP, 	"Marc Petit",		"?",							"sfdc1234", "?", "?");
		new Approver(ApprovalRoleCode.SVP,		"Thomas Cameron",	"mja.salesvp@gmail.com", 		"sfdc1234", "?", "?");
		new Approver(ApprovalRoleCode.SIEVP,	"?", 				"?", 							"?", 		"?", "?");
		new Approver(ApprovalRoleCode.DRB,		"Kim Harrison", 	"mja.adsk@gmail.com", 			"sfdc1234", "?", "?");
	}

	public static Map<ApprovalRoleCode, ApproverDetails> getApprovalDetailsCollection() {
		return approvers;
	}
	
	public static ApproverDetails getApprovalDetails(ApprovalRoleCode approvalRoleCode) {
		return approvers.get(approvalRoleCode);
	}
	
	public class ApproverDetails {
		private String name;
		private String email;
		private String emailPassword;
		private String sfdcUserName;
		private String sfdcPassword;

		ApproverDetails(String name, String email, String emailPassword, String sfdcUserName, String sfdcPassword) {
			this.name = name;
			this.email = email;
			this.emailPassword = emailPassword;
			this.sfdcUserName = sfdcUserName;
			this.sfdcPassword = sfdcPassword;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}

		public String getEmailPassword() {
			return emailPassword;
		}

		public String getSfdcUserName() {
			return sfdcUserName;
		}

		public String getSfdcPassword() {
			return sfdcPassword;
		}
	}
}
