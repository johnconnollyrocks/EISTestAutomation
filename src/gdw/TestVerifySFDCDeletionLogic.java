package gdw;

import org.junit.After; 
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Oppty;
import common.Rdbms;
import common.Util;

/**
 * Test class - TestVerifySFDCDeletionLogic
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestVerifySFDCDeletionLogic extends GDWTestBase {
	String envToken;
	String dbConfigFileName;
	GDWConstants.TestPart testPart = null;
	
	public TestVerifySFDCDeletionLogic() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		envToken = testProperties.getConstant("ENVIRONMENT").trim().toUpperCase();
		
     	Util.printInfo("***** TEST WILL BE RUN IN THE '" + envToken + "' ENVIRONMENT *****");

     	//This is a repeat of the code in GDWTestBase.setEnvironmentVariables()
     	switch (envToken) {
			case "STG":	{
				//GDWConstants.AUTO_USERNAME = GDWConstants.AUTO_USERNAME_STG;
				//GDWConstants.AUTO_PASSWORD = GDWConstants.AUTO_PASSWORD_STG;
				setAutoUserName(GDWConstants.AUTO_USERNAME_STG);
				setAutoPassword(GDWConstants.AUTO_PASSWORD_STG);
				
				dbConfigFileName = "DbConfiguration_STG.properties";
				
				break;
				}
			case "DEV":
			default:	{
				//GDWConstants.AUTO_USERNAME = GDWConstants.AUTO_USERNAME_DEV;
				//GDWConstants.AUTO_PASSWORD = GDWConstants.AUTO_PASSWORD_DEV;
				setAutoUserName(GDWConstants.AUTO_USERNAME_DEV);
				setAutoPassword(GDWConstants.AUTO_PASSWORD_DEV);
				
				dbConfigFileName = "DbConfiguration_DEV.properties";
				
				break;
				}
		}

     	String testPartString = sysProps.getProperty("testPart").trim().toUpperCase();
     	if (testPartString.isEmpty()) {
     		testPart = GDWConstants.TestPart.ALL;
     	} else {
			try {
				testPart = GDWConstants.TestPart.valueOf(testPartString);
			} catch (IllegalArgumentException e) {
				fail("The value '" + testPartString + "' supplied for the property 'testPart' is not a member of the GDWConstants.TestPart enumerated type; valid values are: " + Util.valuesOfEnum(GDWConstants.TestPart.class));
			}
			
			if (testPart == GDWConstants.TestPart.ALL) {
		     	Util.printInfo("***** ALL PARTS OF THE TEST WILL BE RUN *****");
			} else {
		     	Util.printInfo("***** ONLY PART " + testPart + " OF THE TEST WILL BE RUN *****");
			}
     	}
		launchSalesforce();
	}

	@Test
	public void TEST_VerifySFDCDeletionLogic() throws Exception {
		Oppty oppty1 = null;
		Oppty oppty2 = null;
		
		if ((testPart == GDWConstants.TestPart.ONE) || (testPart == GDWConstants.TestPart.TWO) || (testPart == GDWConstants.TestPart.THREE) || (testPart == GDWConstants.TestPart.ALL)) {
			loginAsAutoUser();
		}

		if ((testPart == GDWConstants.TestPart.ONE) || (testPart == GDWConstants.TestPart.ALL)) {
			//PART 1 BEGIN *************************************
			Util.printInfo("***** STARTING PART ONE... *****");
	
			//Step 1 begin ************************
			//Step 1a
			oppty1 = utilCreateOppty();
	
			//Step 1b
			oppty1.addProducts();
			
			//Step 1c
			oppty1.addPartner("PARTNER_1");

			//Step 1d
			oppty1.addSalesTeam("SALES_TEAM_1");
			oppty1.addSalesTeam("SALES_TEAM_2");
			
			//Step 1e
			oppty1.addCompetitor("COMPETITOR_1");
			oppty1.addCompetitor("COMPETITOR_2");
			//Step 1 end **************************
		
	
			//Step 3 begin ************************
			//Step 3a
			oppty2 = utilCreateOppty();
			
			//Step 3b
			oppty2.addProducts();
			
			//Step 3c
			oppty2.open();
			
			//NOTE that the framework does not support searching related lists for now, so we will just delete the first one in the list

			//NOTE that clickLinkInRelatedListAndWait does not exist anymore,
			//  so we may need to apply some "wait" logic here
			//oppty2.getViewOpptyPage().clickLinkInRelatedListAndWait("actionInProductsRelatedList", 0, 2);
			oppty2.getViewOpptyPage().clickLinkInRelatedList("actionInProductsRelatedList", 0, 2);

			Util.printInfo("Deleted product from oppty '" + oppty2.getName() + "' (" + oppty2.getUrl() + ")");
			//Step 3 end **************************
	
			
			//Step 6 begin ************************
			//Step 6a
			Oppty oppty3 = utilCreateOppty();
	
			//Step 6b
			oppty3.addProducts();
			
			//Step 6c
			oppty3.addPartner("PARTNER_1");
			
			//Step 6d
			oppty3.delete();
			
			//Step 6e
			emptyRecycleBin();
			//Step 6 end **************************
			
			//TODO (maybe)  Figure out a way to serialize/deserialize this stuff
			//Util.serialize(oppty1, getResourceDir(), "oppty1.ser");
			//Util.serialize(oppty2, getResourceDir(), "oppty2.ser");
			
			//Required for doVerifySFDCDeletionLogic_part_1()
			System.setProperty("oppty_ID_2", oppty2.getId());
			System.setProperty("oppty_ID_3", oppty3.getId());
			
			Util.printInfo("*************************************");
			Util.printInfo("*************************************");
			Util.printInfo("oppty_ID_1: " + oppty1.getId());
			Util.printInfo("oppty_ID_2: " + oppty2.getId());
			Util.printInfo("oppty_ID_3: " + oppty3.getId());
			Util.printInfo("*************************************");
			Util.printInfo("*************************************");

			Util.printInfo("***** FINISHED PART ONE *****");
		//PART 1 END ***************************************
		}

		
		if ((testPart == GDWConstants.TestPart.ONE_DB) || (testPart == GDWConstants.TestPart.ALL)) {
			//PART 1_DB BEGIN **********************************
			Util.printInfo("***** STARTING PART ONE_DB... *****");
			
			doVerifySFDCDeletionLogic_part_1();
			
			Util.printInfo("***** FINISHED PART ONE_DB *****");
			//PART 1_DB END ************************************
		}

		
		//Cannot be run separately, because it presumes the existence of oppty1 and oppty2
		//if ((testPart == GDWConstants.TestPart.TWO) || (testPart == GDWConstants.TestPart.ALL)) {
		if (testPart == GDWConstants.TestPart.ALL) {
			//PART 2 BEGIN *************************************

			//TODO (maybe)  Figure out a way to serialize/deserialize this stuff
			//oppty1 = (Oppty) Util.deserialize(getResourceDir(), "oppty1.ser");
			//oppty2 = (Oppty) Util.deserialize(getResourceDir(), "oppty2.ser");
			
			Util.printInfo("***** STARTING PART TWO... *****");
			
			//Step 2 begin ************************
			//Step 2a
			oppty1.open();
			
			//NOTE that the framework does not support searching related lists for now, so we will just delete the first one in the list

			//NOTE that clickLinkInRelatedListAndWait does not exist anymore,
			//  so we may need to apply some "wait" logic here
			//oppty1.getViewOpptyPage().clickLinkInRelatedListAndWait("actionInProductsRelatedList", 0, 2);
			oppty1.getViewOpptyPage().clickLinkInRelatedList("actionInProductsRelatedList", 0, 2);

			Util.printInfo("Deleted product from oppty '" + oppty1.getName() + "' (" + oppty1.getUrl() + ")");
			
			//Step 2b
			oppty1.open();
			
			//!!!!! NOTE that I have never been able to get this working!  When this test is run as a Jenkins job (with JB's
			//  laptop as the test node), the field associatedPartnersTableFirstRowDel cannot be found.  I have tried frame 3
			//  and frame 4; I have tried accessing the actionInAssociatedPartnersTable object; I have tried sleeping; etc.
			//  I cannot get it to work.
			//!!!!! IT DOES WORK WHEN RUN AS A LOCAL JOB (USING ECLIPSE) ON THE SAME MACHINE, JB'S LAPTOP
			//  It is very odd that it fails, because the newAssociatedPartnerButton field, which also lives in a frame, can
			//  be seen when this test is run as a Jenkins job (see Oppty.addPartner)
     		EISTestBase.switchDriverToFrame(4);	//The field lived in frame #3 until mid-May 2012, when the Chat widget was added
			//We abandoned the effort to use framework table access methods for the Associated Partners table
     		//  because of the extremely weird way it is rendered
			//oppty1.getViewOpptyPage().clickLinkInTable("actionInAssociatedPartnersTable", 0);
			oppty1.getViewOpptyPage().click("associatedPartnersTableFirstRowDel");
	     	Util.printInfo("Deleted partner from oppty '" + oppty1.getName() + "' (" + oppty1.getUrl() + ")");
			mainWindow.select();
	     	
			//Step 2c
			oppty1.addPartner("PARTNER_2");
			
			//Step 2d
			oppty1.open();
			
			//NOTE that the framework does not support searching related lists for now, so we will just delete the first one in the list

			//NOTE that clickLinkInRelatedListAndWait does not exist anymore,
			//  so we may need to apply some "wait" logic here
			//oppty1.getViewOpptyPage().clickLinkInRelatedListAndWait("actionInSalesTeamRelatedList", 0, 2);
			oppty1.getViewOpptyPage().clickLinkInRelatedList("actionInSalesTeamRelatedList", 0, 2);

			Util.printInfo("Deleted sales team from oppty '" + oppty1.getName() + "' (" + oppty1.getUrl() + ")");
			
			//Step 2e
			oppty1.open();
			
			//NOTE that the framework does not support searching related lists for now, so we will just delete the first one in the list

			//NOTE that clickLinkInRelatedListAndWait does not exist anymore,
			//  so we may need to apply some "wait" logic here
			//oppty1.getViewOpptyPage().clickLinkInRelatedListAndWait("actionInCompetitorsRelatedList", 0, 2);
			oppty1.getViewOpptyPage().clickLinkInRelatedList("actionInCompetitorsRelatedList", 0, 2);

			Util.printInfo("Deleted competitor from oppty '" + oppty1.getName() + "' (" + oppty1.getUrl() + ")");
			//Step 2 end **************************

			
			//Step 4 begin ************************
			//Step 4a
			oppty2.delete();
			//Step 4 end **************************
			
			//Required for doVerifySFDCDeletionLogic_part_2()
			System.setProperty("oppty_ID_1", oppty1.getId());
			System.setProperty("oppty_ID_2", oppty2.getId());
			
			Util.printInfo("***** FINISHED PART TWO *****");
			//PART 2 END ***************************************
		}

		
		if ((testPart == GDWConstants.TestPart.TWO_DB) || (testPart == GDWConstants.TestPart.ALL)) {
			//PART 2_DB BEGIN **********************************
			Util.printInfo("***** STARTING PART TWO_DB... *****");
			
			doVerifySFDCDeletionLogic_part_2();
			
			Util.printInfo("***** FINISHED PART TWO_DB *****");
			//PART 2_DB END ************************************
		}

		
		//Cannot be run separately, because it presumes the existence of oppty2
		//if ((testPart == GDWConstants.TestPart.THREE) || (testPart == GDWConstants.TestPart.ALL)) {
		if (testPart == GDWConstants.TestPart.ALL) {
			//PART 3 BEGIN *************************************
			Util.printInfo("***** STARTING PART THREE... *****");

			//Step 5 begin ************************
			//Step 5a
			emptyRecycleBin();
			//Step 5 end **************************

			//Required for doVerifySFDCDeletionLogic_part_3()
			System.setProperty("oppty_ID_2", oppty2.getId());
			
			Util.printInfo("***** FINISHED PART THREE *****");
			//PART 3 END ***************************************
		}

		
		if ((testPart == GDWConstants.TestPart.THREE_DB) || (testPart == GDWConstants.TestPart.ALL)) {
			//PART 3_DB BEGIN **********************************
			Util.printInfo("***** STARTING PART THREE_DB... *****");
			
			doVerifySFDCDeletionLogic_part_3();
			
			Util.printInfo("***** FINISHED PART THREE_DB *****");
			//PART 3_DB END ************************************
		}
	}

	@After
	public void tearDown() throws Exception {
		//Close the browser. Call stop on the WebDriverBackedSelenium instance
		//  instead of calling driver.quit(). Otherwise, the JVM will continue
		//  running after the browser has been closed.
		driver.quit();
		
		//TODO  Figure out how to determine if the test code has failed in a
		//  manner other than by EISTestBase.fail() being called.  Otherwise,
		//  finish() will always print the default passed message to the console.
		finish();
	}
	
	private void doVerifySFDCDeletionLogic_part_1() throws Exception {		
		
		//Verifying ODS part 1 
		
		Rdbms rdbms = Rdbms.getInstance(getResourceDir() + dbConfigFileName);
		
		//String oppty_ID_2 = System.getenv("oppty_ID_2").trim();	//006V0000002X5Jz	(STG)
		//String oppty_ID_3 = System.getenv("oppty_ID_3").trim();	//006V0000002X5CP	(STG)

		//Need to handle the possibility that the properties we need can be either:
		// 1. environment variables (if the part (ONE) of the script that
		//    creates the opptys WAS NOT executed)
		// 2. system properties (if the part (ONE) of the script that
		//    creates the opptys WAS executed)
		String oppty_ID_2 = sysProps.getProperty("oppty_ID_2", "");
		if (oppty_ID_2.isEmpty()) {
			oppty_ID_2 = System.getenv("oppty_ID_2");
		}
		oppty_ID_2 = oppty_ID_2.trim();
		
		String oppty_ID_3 = sysProps.getProperty("oppty_ID_3", "");
		if (oppty_ID_3.isEmpty()) {
			oppty_ID_3 = System.getenv("oppty_ID_3");
		}
		oppty_ID_3 = oppty_ID_3.trim();
		
		// checking Query1 results...........................
		System.out.println("ODS Deletion Verification Started\n");
		System.out.println("Verifying Oppty = " + oppty_ID_2 + "\nChecking if only one LineItem exists..... \nExpected Value: 1");	
		String query1= "SELECT count(*) FROM [SFDC].[dbo].[T_OPPORTUNITYLINEITEM] b, [SFDC].[dbo].[T_OPPORTUNITY] a where OPPORTUNITYID = '" + oppty_ID_2 + "' and a.ID = b.OPPORTUNITYID";
		rdbms.comparedbvalues(query1, "1", "ODS");			
		
		// checking Query2 results...........................		
		System.out.println("Verifying Oppty = " + oppty_ID_2 + "\nChecking if isDelete flag = 0 for existing LineItem..... \nExpected Value: 0");	
		String query2= "SELECT b.ISDELETED FROM [SFDC].[dbo].[T_OPPORTUNITYLINEITEM] b, [SFDC].[dbo].[T_OPPORTUNITY] a where OPPORTUNITYID = '" + oppty_ID_2 + "' and LICENSE_TYPE__C = 'Renewal' and a.ID = b.OPPORTUNITYID";
		rdbms.comparedbvalues(query2, "0", "ODS");
		
		// checking Query3 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_3 + "\nChecking if the isdelete flag = 1 in T_OPPORTUNITY..... \nExpected Value: 1");	
		String query3= "SELECT [ISDELETED] FROM [SFDC].[dbo].[T_OPPORTUNITY] where ID like '%" + oppty_ID_3 + "%'";
		rdbms.comparedbvalues(query3, "1", "ODS");
		
		
		//Verifying GDW part 1
		
		// checking Query4 results...........................
		System.out.println("GDW Deletion Verification Started\n");
		System.out.println("Verifying Oppty = " + oppty_ID_2 + "\nChecking if only one LineItem exists..... \nExpected Value: 1");
		String query4= "SELECT COUNT (*) FROM [GDW].[dbo].[T_DIM_OPPORTUNITY_LINE_ITEM] where OPPORTUNITY_ID = '" + oppty_ID_2 + "'";
		rdbms.comparedbvalues(query4, "1", "GDW");
		
		// checking Query5 results...........................		
		System.out.println("Verifying Oppty = " + oppty_ID_2 + "\nChecking if src_delete_flg = 0 for existing LineItem..... \nExpected Value: N");	
		String query5= "SELECT b.SRC_DELETE_FLG FROM [GDW].[dbo].[T_FACT_OPPORTUNITY_LINE_ITEM] a, [GDW].[dbo].[T_DIM_OPPORTUNITY_LINE_ITEM] b where a.OPPORTUNITY_LINE_ITEM_KEY = b.OPPORTUNITY_LINE_ITEM_KEY  and LICENSE_TYPE_NM = 'Renewal' and OPPORTUNITY_ID = '" + oppty_ID_2 + "'";
		rdbms.comparedbvalues(query5, "N", "GDW");
		
		// getting line item key from Query6.................
		System.out.println("Retrieving Line Item Key to query View..... \n");		
		String query6= "SELECT b.OPPORTUNITY_LINE_ITEM_KEY FROM [GDW].[dbo].[T_FACT_OPPORTUNITY_LINE_ITEM] a, [GDW].[dbo].[T_DIM_OPPORTUNITY_LINE_ITEM] b where a.OPPORTUNITY_LINE_ITEM_KEY = b.OPPORTUNITY_LINE_ITEM_KEY and LICENSE_TYPE_NM = 'Renewal' and OPPORTUNITY_ID = '" + oppty_ID_2 + "'";
		String lineitemkey = rdbms.getdbvalue(query6, "GDW");
		System.out.println("Line Item Key retrieved: " + lineitemkey );	
		
		// checking Query7 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_2 + "\nChecking if only one LineItem exists in View..... \nExpected Value: 1");	
		String query7= "select COUNT(*) from [GDW].[dbo].[VW_FACT_OPPORTUNITY_LINE_ITEM] where OPPORTUNITY_LINE_ITEM_KEY = '" + lineitemkey + "'";
		rdbms.comparedbvalues(query7, "1", "GDW");
		
		// checking Query8 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_3 + "\nChecking if thesrc_delete_flg = 'Y' in T_DIM_OPPORTUNITY..... \nExpected Value: Y");	
		String query8= "SELECT [ISDELETED] FROM [SFDC].[dbo].[T_OPPORTUNITY] where ID like '%" + oppty_ID_2 + "%'SELECT SRC_DELETE_FLG FROM [GDW].[dbo].[T_DIM_OPPORTUNITY] where SRC_ID like '%" + oppty_ID_3 + "%'";
		rdbms.comparedbvalues(query8, "1", "GDW");
	}

	private void doVerifySFDCDeletionLogic_part_2() throws Exception {
		
		//Verifying ODS part 1 
		
		Rdbms rdbms = Rdbms.getInstance(getResourceDir() + dbConfigFileName);		

		//String oppty_ID_1 = System.getenv("oppty_ID_1").trim();	//006V0000002X5Dr	(STG)
		//String oppty_ID_2 = System.getenv("oppty_ID_2").trim();	//006V0000002X5Dw	(STG)

		//Need to handle the possibility that the properties we need can be either:
		// 1. environment variables (if the part (ONE) of the script that
		//    creates the opptys WAS NOT executed)
		// 2. system properties (if the part (ONE) of the script that
		//    creates the opptys WAS executed)
		String oppty_ID_1 = sysProps.getProperty("oppty_ID_1", "");
		if (oppty_ID_1.isEmpty()) {
			oppty_ID_1 = System.getenv("oppty_ID_1");
		}
		oppty_ID_1 = oppty_ID_1.trim();
		
		String oppty_ID_2 = sysProps.getProperty("oppty_ID_2", "");
		if (oppty_ID_2.isEmpty()) {
			oppty_ID_2 = System.getenv("oppty_ID_2");
		}
		oppty_ID_2 = oppty_ID_2.trim();
		
		// checking Query1 results...........................
		System.out.println("ODS Deletion Verification Started\n");
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if two LineItem exists..... \nExpected Value: 2");			
		String query1= "SELECT count(*) FROM [SFDC].[dbo].[T_OPPORTUNITYLINEITEM] b, [SFDC].[dbo].[T_OPPORTUNITY] a where OPPORTUNITYID = '" + oppty_ID_1 + "' and a.ID = b.OPPORTUNITYID";
		rdbms.comparedbvalues(query1, "2", "ODS");			
		
		// ###################  Based on Licence Type  #############################
		// checking Query2 results...........................		
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if isDelete flag = 1 for deleted LineItem..... \nExpected Value: 1");			
		String query2= "SELECT b.ISDELETED FROM [SFDC].[dbo].[T_OPPORTUNITYLINEITEM] b, [SFDC].[dbo].[T_OPPORTUNITY] a where OPPORTUNITYID = '" + oppty_ID_1 + "' and LICENSE_TYPE__C = 'New' and a.ID = b.OPPORTUNITYID";
		rdbms.comparedbvalues(query2, "1", "ODS");
		
		// checking Query3 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if two records for primary partner exist..... \nExpected Value: 2");		
		String query3= "SELECT count(*) FROM [SFDC].[dbo].[T_OPPORTUNITYLINEITEM] b, [SFDC].[dbo].[T_OPPORTUNITY] a where OPPORTUNITYID = '" + oppty_ID_1 + "' and a.ID = b.OPPORTUNITYID";
		rdbms.comparedbvalues(query3, "2", "ODS");
		
		// ###################  Based on CSN  #############################
		// checking Query4 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if isDelete flag = 1 for deleted PrimaryPartner..... \nExpected Value: 1");				
		String query4= "SELECT a.isdeleted FROM [SFDC].[dbo].[T_OPPORTUNITYPARTNER] a, [SFDC].[dbo].[T_ACCOUNT] b where OPPORTUNITYID = '" + oppty_ID_1 + "' and a.accounttoid = b.ID and a.ISPRIMARY = '0' and b.ACCOUNT_CSN__C = '5109154689'";
		rdbms.comparedbvalues(query4, "1", "ODS");
		
		// ###################  Based on CSN  #############################
		// checking Query5 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if isDelete flag = 0 for newly added PrimaryPartner..... \nExpected Value: 0");				
		String query5= "SELECT a.isdeleted FROM [SFDC].[dbo].[T_OPPORTUNITYPARTNER] a, [SFDC].[dbo].[T_ACCOUNT] b where OPPORTUNITYID = '" + oppty_ID_1 + "' and a.accounttoid = b.ID and a.ISPRIMARY = '1' and b.ACCOUNT_CSN__C = '5109161852'";
		rdbms.comparedbvalues(query5, "0", "ODS");
		
		// checking Query6 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if two SalesTeamMembers exists..... \nExpected Value: 2");				
		String query6= "SELECT COUNT(*) FROM [SFDC].[dbo].[T_OPPORTUNITYTEAMMEMBER] where OPPORTUNITYID = '" + oppty_ID_1 + "'";
		rdbms.comparedbvalues(query6, "2", "ODS");
		
		// ###################  Based on TeamMemberRole  #############################
		// checking Query7 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if isDelete flag = 1 for deleted SalesTeamMember..... \nExpected Value: 1");				
		String query7= "SELECT ISDELETED FROM [SFDC].[dbo].[T_OPPORTUNITYTEAMMEMBER] where OPPORTUNITYID = '" + oppty_ID_1 + "' and TEAMMEMBERROLE = 'ACER'";
		rdbms.comparedbvalues(query7, "1", "ODS");
		
		// checking Query8 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if two Competitors exists..... \nExpected Value: 2");				
		String query8= "SELECT COUNT(*) FROM [SFDC].[dbo].[T_OPPORTUNITYCOMPETITOR] where OPPORTUNITYID = '" + oppty_ID_1 + "'";
		rdbms.comparedbvalues(query8, "2", "ODS");
		
		// ###################  Based on CompetitorName  #############################
		// checking Query9 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if isDelete flag = 1 for deleted Competitor..... \nExpected Value: 1");				
		String query9= "SELECT ISDELETED FROM [SFDC].[dbo].[T_OPPORTUNITYCOMPETITOR] where OPPORTUNITYID = '" + oppty_ID_1 + "' and COMPETITORNAME = 'Apple'";
		rdbms.comparedbvalues(query9, "1", "ODS");
		
		// checking Query10 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_2 + "\nChecking if isdelete flag = 1 for deleted oppty2..... \nExpected Value: 1");				
		String query10= "SELECT ISDELETED FROM [SFDC].[dbo].[T_OPPORTUNITY] where ID like '%" + oppty_ID_2 + "%'";
		rdbms.comparedbvalues(query10, "1", "ODS");
		
		
		// GDW Verification
		// checking Query11 results...........................
		System.out.println("GDW Deletion Verification Started\n");
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if two LineItems exist..... \nExpected Value: 2");				
		String query11= "SELECT COUNT (*) FROM [GDW].[dbo].[T_DIM_OPPORTUNITY_LINE_ITEM] where OPPORTUNITY_ID = '" + oppty_ID_1 + "'";
		rdbms.comparedbvalues(query11, "2", "GDW");
		
		// ###################  Based on Licence Type  #############################
		// checking Query12 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if src_delete_flg = Y for deleted LineItem..... \nExpected Value: Y");				
		String query12= "SELECT b.SRC_DELETE_FLG FROM [GDW].[dbo].[T_FACT_OPPORTUNITY_LINE_ITEM] a, [GDW].[dbo].[T_DIM_OPPORTUNITY_LINE_ITEM] b where a.OPPORTUNITY_LINE_ITEM_KEY = b.OPPORTUNITY_LINE_ITEM_KEY  and LICENSE_TYPE_NM = 'New' and OPPORTUNITY_ID = '" + oppty_ID_1 + "'";
		rdbms.comparedbvalues(query12, "Y", "GDW");
		
		// Retrieving Line Item Key to ...........................
		System.out.println("Retreiving Line Item Key to query View..... \n");	
		String query13= "SELECT b.OPPORTUNITY_LINE_ITEM_KEY FROM [GDW].[dbo].[T_FACT_OPPORTUNITY_LINE_ITEM] a, [GDW].[dbo].[T_DIM_OPPORTUNITY_LINE_ITEM] b where a.OPPORTUNITY_LINE_ITEM_KEY = b.OPPORTUNITY_LINE_ITEM_KEY and LICENSE_TYPE_NM = 'New' and OPPORTUNITY_ID = '" + oppty_ID_1 + "'";
		String lineitemkey = rdbms.getdbvalue(query13, "GDW");
		System.out.println("Line Item Key retrieved: " + lineitemkey );			
		
		// checking Query14 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_1 + "\nChecking if only one LineItem exists in View..... \nExpected Value: 0");				
		String query14= "select COUNT(*) from [GDW].[dbo].[VW_FACT_OPPORTUNITY_LINE_ITEM] where OPPORTUNITY_LINE_ITEM_KEY = '" + lineitemkey + "'";
		rdbms.comparedbvalues(query14, "0", "GDW");
		
		// checking Query15 results...........................
		System.out.println("Verifying Oppty = " + oppty_ID_2 + "\nChecking if src_delete_flg = Y for deleted oppty2..... \nExpected Value: Y");				
		String query15= "SELECT SRC_DELETE_FLG FROM [GDW].[dbo].[T_DIM_OPPORTUNITY] where SRC_ID like '%" + oppty_ID_2 + "%'";
		rdbms.comparedbvalues(query15, "Y", "GDW");		
	}

	private void doVerifySFDCDeletionLogic_part_3() throws Exception {
		
		//Verifying ODS part 1 
		
		Rdbms rdbms = Rdbms.getInstance(getResourceDir() + dbConfigFileName);		

		//String oppty_ID_2 = System.getenv("oppty_ID_2").trim();	//006V0000002X5Dw	(STG)

		//Need to handle the possibility that the properties we need can be either:
		// 1. environment variables (if the part (ONE) of the script that
		//    creates the opptys WAS NOT executed)
		// 2. system properties (if the part (ONE) of the script that
		//    creates the opptys WAS executed)
		String oppty_ID_2 = sysProps.getProperty("oppty_ID_2", "");
		if (oppty_ID_2.isEmpty()) {
			oppty_ID_2 = System.getenv("oppty_ID_2");
		}
		oppty_ID_2 = oppty_ID_2.trim();
		
		// checking Query1 results...........................
		System.out.println("ODS Deletion Verification Started\n");
		System.out.println("Verifying Oppty = " + oppty_ID_2 + "\nChecking if isdelete flag = 1 for deleted oppty2..... \nExpected Value: 1");				
		String query1= "SELECT [ISDELETED] FROM [SFDC].[dbo].[T_OPPORTUNITY] where ID like '%" + oppty_ID_2 + "%'";
		rdbms.comparedbvalues(query1, "1", "ODS");
		
		
		//Verifying GDW 
		
		// checking Query2 results...........................
		System.out.println("GDW Deletion Verification Started\n");
		System.out.println("Verifying Oppty = " + oppty_ID_2 + "\nChecking if src_delete_flg = Y for deleted oppty2..... \nExpected Value: Y");				
		String query2= "SELECT SRC_DELETE_FLG FROM [GDW].[dbo].[T_DIM_OPPORTUNITY] where SRC_ID like '%" + oppty_ID_2 + "%'";
		rdbms.comparedbvalues(query2, "Y", "GDW");	
		
	}
} 
