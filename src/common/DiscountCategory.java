package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Defines Discount Approval Request (DAR) discount categories.
 * 
 * Defines all discount categories, each of which is comprised of a DiscountCategoryCode value, name,
 * Total Discount percentage and dollar amount thresholds, and Additional Discount routing table
 * @see DiscountCategoryCode
 * @see ApprovalChain
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class DiscountCategory {
	private static Map<DiscountCategoryCode, DiscountCategoryDetails> discountCategories = new HashMap<DiscountCategoryCode, DiscountCategoryDetails>();
	
	private DiscountCategory(DiscountCategoryCode discountCategoryCode, String name, boolean hasTotalOpptyMap, double totalDiscPctMin, double totalDiscAmtMin, String totalDiscChainNum) {
		discountCategories.put(discountCategoryCode, new DiscountCategoryDetails(discountCategoryCode, name, hasTotalOpptyMap, totalDiscPctMin, totalDiscAmtMin, totalDiscChainNum));
	}

	static {
		new DiscountCategory(DiscountCategoryCode.LP,		"License Products",								true,	65, 50000,	 "4");
		new DiscountCategory(DiscountCategoryCode.CPMS,		"SaaS incl. Cloud Services and CPM",			true,	65, 50000,	 "8");
		new DiscountCategory(DiscountCategoryCode.CFSW,		"Creative Finishing SW",						true,	65, 50000,	"13");
		new DiscountCategory(DiscountCategoryCode.CFHW,		"Creative Finishing HW",						true,	65, 50000,	"15");
		new DiscountCategory(DiscountCategoryCode.CFSS,		"Creative Finishing Support Svcs",				true,	65, 50000,	"18");
		new DiscountCategory(DiscountCategoryCode.SCS,		"Subscriptions and Cloud Services",				false,	-1,    -1,	  "");
		new DiscountCategory(DiscountCategoryCode.SCSLF,	"Subscriptions and Cloud Services Late Fees",	false,	-1,    -1,	  "");
		new DiscountCategory(DiscountCategoryCode.DNA, 		"Discount not applicable",						false,	-1,    -1,	  "");
	}

	public static Map<DiscountCategoryCode, DiscountCategoryDetails> getDiscountCategoryDetailsCollection() {
		return discountCategories;
	}

	public static DiscountCategoryDetails getDiscountCategoryDetails(DiscountCategoryCode discountCategoryCode) {
		return discountCategories.get(discountCategoryCode);
	}
	
	//TODO  This is inherently dangerous, as we are using a text string to look up an entry in
	//  discountCategories.  We are doing it so that we can get the code, but perhaps it would
	//  be safer to have it specified in test properties
	public static DiscountCategoryCode getDiscountCategoryCode(String discountCategoryName) {
		DiscountCategoryCode discountCategoryCode = null;
		
	    for (Entry<DiscountCategoryCode, DiscountCategoryDetails> entry : discountCategories.entrySet()) {
	    	if (entry.getValue().getName().equalsIgnoreCase(discountCategoryName)) {
	    		discountCategoryCode = entry.getKey();
	    		
	    		break;
	    	}
	    }
	    
	    if (discountCategoryCode == null) {
	    	EISTestBase.fail("Error while lookinp up the discount category code: '" + discountCategoryName + "' is not a valid discount category name");
	    }
	    
		return discountCategoryCode;
	}
	
	public class DiscountCategoryDetails {
		private String name;
		boolean hasTotalDiscMap;
		private double totalDiscPctMin;
		private double totalDiscAmtMin;
		private String totalDiscChainNum;
		private RoutingCriteriaTable routingCriteriaTable;

		DiscountCategoryDetails(DiscountCategoryCode discountCategoryCode, String name, boolean hasTotalOpptyMap, double totalDiscPctMin, double totalDiscAmtMin, String totalDiscChainNum) {
			this.name				= name;
			this.hasTotalDiscMap	= hasTotalOpptyMap;
			this.totalDiscPctMin 	= totalDiscPctMin;
			this.totalDiscAmtMin	= totalDiscAmtMin;
			this.totalDiscChainNum	= totalDiscChainNum;
			routingCriteriaTable	= new RoutingCriteriaTable(discountCategoryCode);
		}

		public String getName() {
			return name;
		}

		public boolean hasTotalDiscMap() {
			return hasTotalDiscMap;
		}

		public double getTotalDiscPctMin() {
			return totalDiscPctMin;
		}

		public double getTotalDiscAmtMin() {
			return totalDiscAmtMin;
		}

		public String getTotalDiscChainNum() {
			return totalDiscChainNum;
		}

		public String getTotalDiscChainNum(double totalDiscPct, double totalDiscAmt) {
			String chainNum = "";

			if (hasTotalDiscMap) {
				if ((totalDiscPct >= totalDiscPctMin) && (totalDiscAmt >= totalDiscAmtMin)) {
					chainNum = totalDiscChainNum;
				}
			}

			return chainNum;
		}

		public String getAddlDiscChainNum(double addlDiscPct, double addlDiscAmt) {
			String chainNum = "";
			List<RoutingCriteriaLine> routingCriteriaLines = routingCriteriaTable.getRoutingCriteriaLines();

			for (RoutingCriteriaLine line : routingCriteriaLines) {
				if ((addlDiscPct >= line.addlDiscPctMin) && (addlDiscPct < line.addlDiscPctMax)) {
					if ((addlDiscAmt >= line.addlDiscAmtMin) && (addlDiscAmt < line.addlDiscAmtMax)) {
						chainNum = line.chainNum;
						break;
					}
				}
			}

			return chainNum;
		}

		public RoutingCriteriaTable getRoutingCriteriaTable() {
			return routingCriteriaTable;
		}

		public RoutingCriteriaLine getRoutingCriteriaLine(String chainNum) {
			return routingCriteriaTable.getRoutingCriteriaLine(chainNum);
		}
	}
	
	public class RoutingCriteriaTable {
		private List<RoutingCriteriaLine> routingCriteriaLines = new ArrayList<RoutingCriteriaLine>();

		public RoutingCriteriaTable(DiscountCategoryCode discountCategoryCode) {
			routingCriteriaLines.clear();
			
			switch (discountCategoryCode) {
				//case LP:	{
				//	routingCriteriaLines.add(new RoutingCriteriaLine(1,		 0,		 20,	    0,	 10000));
				//	routingCriteriaLines.add(new RoutingCriteriaLine(2,		 0,	 	 20,	10000,	 20000));
				//	routingCriteriaLines.add(new RoutingCriteriaLine(2,		20,  	100,	    0,	 10000));
				//	routingCriteriaLines.add(new RoutingCriteriaLine(2,		20,  	100,	10000,	 20000));
				//	routingCriteriaLines.add(new RoutingCriteriaLine(3,		 0,		 20,	20000,	Double.MAX_VALUE));
				//	routingCriteriaLines.add(new RoutingCriteriaLine(3,		20,  	100,	20000,	Double.MAX_VALUE));
				//	routingCriteriaLines.add(new RoutingCriteriaLine(4,		65,  	100,	50000,	Double.MAX_VALUE));
				//	break;
				//}
				case LP:	{
					routingCriteriaLines.add(new RoutingCriteriaLine("1",	 0,		 20,	    0,	 		   10000));
					routingCriteriaLines.add(new RoutingCriteriaLine("2",	 0,	 	 65,	10000,	 		   25000));	//changed
					routingCriteriaLines.add(new RoutingCriteriaLine("2",	20,  	 65,	    0,	 		   10000));	//changed
					routingCriteriaLines.add(new RoutingCriteriaLine("2A",	65,  	100,		0,	 		   25000));	//changed (was 2c)
					routingCriteriaLines.add(new RoutingCriteriaLine("3",	 0,		 65,	25000,	Double.MAX_VALUE));	//changed
					routingCriteriaLines.add(new RoutingCriteriaLine("3A",	65,  	100,	25000,	 		   50000));	//changed (was 3b)
					routingCriteriaLines.add(new RoutingCriteriaLine("4",	65,  	100,	50000,	Double.MAX_VALUE));
					break;
				}
				case CPMS:	{
					routingCriteriaLines.add(new RoutingCriteriaLine("5",	 0,   	 10,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("6",	10,		 20,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("7",	20,  	100,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("8",	65,  	100,	50000,	Double.MAX_VALUE));
					break;
				}
				case CFSW:	{
					routingCriteriaLines.add(new RoutingCriteriaLine("9",	0,   	 10,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("10",	10,   	 15,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("11",	15,		 20,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("12",	20,  	100,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("13",	65,   	100,	50000,	Double.MAX_VALUE));
					break;
				}
				case CFHW:	{
					routingCriteriaLines.add(new RoutingCriteriaLine("9",	0,   	 10,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("12",	15,  	100,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("14",	10,   	 15,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("15",	65,  	100,	50000,	Double.MAX_VALUE));
				}
				case CFSS:	{
					routingCriteriaLines.add(new RoutingCriteriaLine("16",	 0,   	 10,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("17",	10,  	100,	    0,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("18",	65,  	100,	50000,	Double.MAX_VALUE));
					break;
				}
				case SCS:	{
					routingCriteriaLines.add(new RoutingCriteriaLine("5",	 0,   	 10,	    0,	  			1000));
					routingCriteriaLines.add(new RoutingCriteriaLine("19",	 0,   	 10,	 1000,	Double.MAX_VALUE));
					routingCriteriaLines.add(new RoutingCriteriaLine("20",	10,  	100,	    0,	Double.MAX_VALUE));
					break;
				}
				case SCSLF:	{
					routingCriteriaLines.add(new RoutingCriteriaLine("9",	 0,   	100,	    0,	  			1000));
					routingCriteriaLines.add(new RoutingCriteriaLine("21",	 0,   	100,	 1000,   		   20000));
					routingCriteriaLines.add(new RoutingCriteriaLine("22",	 0,   	100,	20000,	Double.MAX_VALUE));
					break;
				}
				case DNA:	{
					routingCriteriaLines.add(new RoutingCriteriaLine("",	-1,   	 -1,	   -1,	    		  -1));
					break;
				}
				default:		{
					EISTestBase.fail("Unhandled member of common.DiscountCategoryCode enumerated type: " + discountCategoryCode);
				}
			}
			
		}
		
		public List<RoutingCriteriaLine> getRoutingCriteriaLines() {
			return routingCriteriaLines;
		}

		//NOTE That there MAY (?) be duplicate lines for a chain number!  Perhaps we should get rid of this method
		public RoutingCriteriaLine getRoutingCriteriaLine(String chainNum) {
			RoutingCriteriaLine routingCriteriaLine = null;
			RoutingCriteriaLine tempRoutingCriteriaLine;
			
			Iterator<RoutingCriteriaLine> routingCriteriaLineItr = routingCriteriaLines.iterator();
			while (routingCriteriaLineItr.hasNext()) {
				tempRoutingCriteriaLine = routingCriteriaLineItr.next();
				
				if (tempRoutingCriteriaLine.chainNum == chainNum) {
					routingCriteriaLine = tempRoutingCriteriaLine;
					
					break;
				}
			}
			
			return routingCriteriaLine;
		}
	}
	
	public class RoutingCriteriaLine {
		private String	chainNum;	
		private double	addlDiscPctMin;	// >= condition
		private double	addlDiscPctMax;	// < condition
		private double	addlDiscAmtMin;	// >= condition
		private double	addlDiscAmtMax;	// < condition
		
		RoutingCriteriaLine(String chainNum, double addlDiscPctMin, double addlDiscPctMax, double addlDiscAmtMin, double addlDiscAmtMax) {
			this.chainNum		= chainNum;	
			this.addlDiscPctMin	= addlDiscPctMin;
			this.addlDiscPctMax	= addlDiscPctMax;
			this.addlDiscAmtMin	= addlDiscAmtMin;
			this.addlDiscAmtMax	= addlDiscAmtMax;
		}

		public String getChainNum() {
			return chainNum;
		}

		public double getAddlDiscPctMin() {
			return addlDiscPctMin;
		}

		public double getAddlDiscPctMax() {
			return addlDiscPctMax;
		}

		public double getAddlDiscAmtMin() {
			return addlDiscAmtMin;
		}

		public double getAddlDiscAmtMax() {
			return addlDiscAmtMax;
		}
	}
}
