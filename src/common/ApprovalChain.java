package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines Discount Approval Request (DAR) approval chains.
 * 
 * Defines all approval chains, each of which is comprised of a chain number, followed by
 * some number of ApprovalRoleCode values
 * @see ApprovalRoleCode
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class ApprovalChain {
	private static Map<String, Chain> approvalChains = new HashMap<String, Chain>();
	
	private ApprovalChain(String chainNum, ApprovalRoleCode... approvalRoleCodes) {
		approvalChains.put(chainNum, new Chain(chainNum, approvalRoleCodes));
	}

	static {
		//Based on a conversation with Syed on 04/06/2012, we will ignore the Sales Industry or Emerging VP
		//  (SIEVP) role in testing.  This was decided because the rules as to when it will be included in a
		//  chain instead of or in addition to the Sales VP (SVP) role, and when those roles will be filled
		//  by the same or different users are complex and still unsettled.  If we run into problems, we'll
		//  revisit the issue.
		
		//Note that in many cases we are passing the codes in unsorted order, but the constructor sorts them
		new ApprovalChain("1", 	ApprovalRoleCode.SM,	ApprovalRoleCode.SFP);
		
		new ApprovalChain("2", 	ApprovalRoleCode.SM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.SVP);
		
		//new ApprovalChain(3, 	ApprovalRoleCode.SM, 	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,
		//						ApprovalRoleCode.SFD,	ApprovalRoleCode.SVP);

		new ApprovalChain("3", 	ApprovalRoleCode.SM, 	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.SFD,
								ApprovalRoleCode.SVP);

		new ApprovalChain("4", 	ApprovalRoleCode.SM, 	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.SVP,
								ApprovalRoleCode.SFD,	ApprovalRoleCode.GRM, 	ApprovalRoleCode.DRB);

		new ApprovalChain("5", 	ApprovalRoleCode.SM, 	ApprovalRoleCode.GRM,	ApprovalRoleCode.SFP);

		//new ApprovalChain(6, 	ApprovalRoleCode.SM, 	ApprovalRoleCode.GRM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,
		//						ApprovalRoleCode.SVP);
		new ApprovalChain("6", 	ApprovalRoleCode.SM, 	ApprovalRoleCode.GRM,	ApprovalRoleCode.SFP, ApprovalRoleCode.SVP);

		new ApprovalChain("7", 	ApprovalRoleCode.SM, 	ApprovalRoleCode.GRM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,
								ApprovalRoleCode.SVP,	ApprovalRoleCode.SFD);

		new ApprovalChain("8", 	ApprovalRoleCode.SM, 	ApprovalRoleCode.GRM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,
								ApprovalRoleCode.SVP,	ApprovalRoleCode.SFD,	ApprovalRoleCode.DRB);

		new ApprovalChain("9", 	ApprovalRoleCode.SM);
		
		new ApprovalChain("10", ApprovalRoleCode.SM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD);
		
		new ApprovalChain("11", ApprovalRoleCode.SM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.SFD);
		
		new ApprovalChain("12", ApprovalRoleCode.SM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.SFD,
								ApprovalRoleCode.MEVP);

		//new ApprovalChain(13, ApprovalRoleCode.SM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.SFD,
		//						ApprovalRoleCode.MEVP,	ApprovalRoleCode.DRB);
		new ApprovalChain("13", ApprovalRoleCode.SM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,
								ApprovalRoleCode.SFD,	ApprovalRoleCode.SVP,	ApprovalRoleCode.DRB);

		new ApprovalChain("14", ApprovalRoleCode.SM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.SFD);
		
		//new ApprovalChain(15, ApprovalRoleCode.SM, 	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.SFD,
		//						ApprovalRoleCode.MEVP,	ApprovalRoleCode.GRM,	ApprovalRoleCode.DRB);
		new ApprovalChain("15",	ApprovalRoleCode.SM, 	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,
								ApprovalRoleCode.SFD,	ApprovalRoleCode.SVP,	ApprovalRoleCode.DRB);

		new ApprovalChain("16", ApprovalRoleCode.SM, 	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,	ApprovalRoleCode.SFP);

		new ApprovalChain("17", ApprovalRoleCode.SM, 	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,	ApprovalRoleCode.SFP,
								ApprovalRoleCode.MEVP);

		new ApprovalChain("18", ApprovalRoleCode.SM, 	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,	ApprovalRoleCode.SFP,
								ApprovalRoleCode.MEVP,	ApprovalRoleCode.DRB);

		new ApprovalChain("19", ApprovalRoleCode.SM, 	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,	ApprovalRoleCode.SFP);

		//new ApprovalChain(20, ApprovalRoleCode.SM, 	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,
		//						ApprovalRoleCode.SFD);
		new ApprovalChain("20", ApprovalRoleCode.SM, 	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,
								ApprovalRoleCode.SFD,	ApprovalRoleCode.SVP);

		new ApprovalChain("21", ApprovalRoleCode.SM, 	ApprovalRoleCode.SD);

		//new ApprovalChain(22,	ApprovalRoleCode.SD,	ApprovalRoleCode.SM);
		new ApprovalChain("22",	ApprovalRoleCode.SM,	ApprovalRoleCode.SD,	ApprovalRoleCode.SVP);

		new ApprovalChain("2A", ApprovalRoleCode.SM,	ApprovalRoleCode.SFP,	ApprovalRoleCode.GRM,	ApprovalRoleCode.SD,
								ApprovalRoleCode.SVP);

		new ApprovalChain("3A", ApprovalRoleCode.SM, 	ApprovalRoleCode.SFP,	ApprovalRoleCode.SD,	ApprovalRoleCode.GRM,
								ApprovalRoleCode.SFD,	ApprovalRoleCode.SVP);
	}

	public static Map<String, Chain> getApprovalChainsCollection() {
		return approvalChains;
	}
	
	public static Chain getApprovalChain(String chainNum) {
		return approvalChains.get(chainNum);
	}
	
	public class Chain {
		private String chainNum;
		private List<ApprovalRoleCode> approvalRoleCodes = new ArrayList<ApprovalRoleCode>();

		Chain(String chainNum, ApprovalRoleCode... approvalRoleCodes) {
			this.chainNum = chainNum;
			this.approvalRoleCodes.addAll(Arrays.asList(approvalRoleCodes));
			
			//It is assumed that the elements are declared in the desired rank order in the ApprovalRoleCode enum class,
			//  as this sort call will sort the collection on the enum ordinal property
			Collections.sort(this.approvalRoleCodes);
		}
		
		public String getChainNum() {
			return chainNum;
		}
		
		public List<ApprovalRoleCode> getApprovalRoleCodes() {
			return approvalRoleCodes;
		}
	}
}
