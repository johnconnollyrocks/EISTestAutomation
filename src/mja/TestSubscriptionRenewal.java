package mja;

import java.util.List;
import java.util.ListIterator;

import mja.SubscriptionRenewal.CaseName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import common.EISTestBase;
import common.Page_;

/**
 * Test class - TestSubscriptionRenewal
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestSubscriptionRenewal extends MJATestBase {
	String dbConfigFileName;
	public TestSubscriptionRenewal() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_SubscriptionRenewal() throws Exception {
		CaseName caseName	= getCaseName(MJAConstants.MJA_CASE_NAME_ENUM_CONSTANT_NAME);
		//Can include Selenium and WebDriver commands - but please don't!
		loginAsAutoUser();	
		//Code to fetch Jenkins Job Name from testdata properties file	
	//	String jobName=testProperties.getConstant("JENKINS_JOB_NAME");
	//	EISTestBase.setJenkinsJobName(jobName);	
	//	dbConfigFileName = "DbConfiguration_STG.properties";
	//	DBConnect();
		//NOTES TO OFFSHORE - verifications should be done in the test method, not the "base" class or the SFDCObject subclass
		SubscriptionRenewal subRenewal =  utilCreateSubscriptionRenewal(caseName);
		Page_ viewOpptyPage = subRenewal.getViewOpptyPage();
		Page_ viewServiceContractPage = subRenewal.getViewServiceContractPage();
	
		
		//NOTES TO OFFSHORE - having returned from utilCreateSubscriptionRenewal, we cannot depend on the state of the app.
		//  The first thing we generally do after creating an object is open it (we can't depend on the "create" method
		//  having left it open!)
		
		//This is how we open it.
//		open(subRenewal.getContractUrl());
		String masterAgreementURL=subRenewal.getMasterAgreementURL();
		EISTestBase.open(masterAgreementURL);
		
		String accName=viewServiceContractPage.getValueFromGUI("accountName");
		
		//This code was in the sub renewal create method.  It verifies that an oppty was created, and also opens the oppty
		int numRowsInOpportunitiesRelatedList = viewServiceContractPage.getNumRowsInRelatedList("opportunitiesRelatedList");
		if (numRowsInOpportunitiesRelatedList > 0) {
			viewServiceContractPage.clickLinkInRelatedList("opportunityNameinOpportunitiesRelatedList", numRowsInOpportunitiesRelatedList - 1);
			viewOpptyPage.waitForFieldPresent("discountApprovalRequestButton");
		}
		else {
			fail("The Service Contract '" + subRenewal.getServiceContractNumber() + "' does not have an associated opportunity");
		}
		
		//NOTES TO OFFSHORE - I see that you are doing a lot of verifying using if statements, and then calling fail.  We should not do that.
		//  Instead, if the thing you want to verify cannot be handled with a verify method call (either page or field level), please call
		//  one of the "EIS.assert.....WithFlags" methods.  If you don't call those, we can't take advantage of the "flags" (booleans) they
		//  use, and we can't format the assert status information for the console not count the number of asserts we do.  Do a search on calls
		//  to the assertEqualsWithFlags and assertTrueWithFlags methods for examples of how they are used.
		String expectedOpptyName=subRenewal.getExpectedOpptyName();
		String expectedOpportunityName=accName+" - "+expectedOpptyName;
		
	//	viewOpptyPage.setVerificationDataValue("opptyName", subRenewal.getExpectedOpptyName());
//		viewOpptyPage.setVerificationDataValue("opptyName",expectedOpportunityName);
		//NOTES TO OFFSHORE - Now in viewOpptyPage.  Plus, the expected value for PageViewOppty.opptyName was set in
		//  utilCreateSubscriptionRenewal so all expected values for viewOpptyPage are all set.  Therefore we can call
		//  verify() to verify them all
		//viewServiceContractPage.verifyField("fulfillmentCategory");
		//viewServiceContractPage.verifyField("projectedCloseDate");
		viewOpptyPage.verify();
		
		//Unnecessary
		//viewServiceContractPage.click("adskProductsList");
		//viewOpptyPage.click("adskProductsList");
		
		//NOTES TO OFFSHORE - at this point you are looking at the oppty page, not the service contract page
		//NOTES TO OFFSHORE - this is not the related list, but just a link to it!
		//List<List<String>> products = viewServiceContractPage.getRelatedList("adskProductsList");
		List<List<String>> products = viewOpptyPage.getRelatedList("productsRelatedList");
		List<String> product = null;
		
		//int productTypeColumn 		= viewServiceContractPage.getRelatedListColumnNum("productTypeinProductsRelatedList");
		//int licenseTypeColumn 		= viewServiceContractPage.getRelatedListColumnNum("licenseTypeinProductsRelatedList");
		//int commentsColumn 			= viewServiceContractPage.getRelatedListColumnNum("commentsinProductsRelatedList");
		int productTypeColumn 		= viewOpptyPage.getRelatedListColumnNum("productTypeinProductsRelatedList");
		int licenseTypeColumn 		= viewOpptyPage.getRelatedListColumnNum("licenseTypeinProductsRelatedList");
		int commentsColumn 			= viewOpptyPage.getRelatedListColumnNum("commentsinProductsRelatedList");
		
		//String stringContractNumbers = subRenewal.getStringOfContractNumbers();
		//String [] arrayOfContractNumbers = stringContractNumbers.split(";"); 
		String [] arrayOfContractNumbers = subRenewal.getStringOfContractNumbers().split(";"); 
		
		boolean found2;
		
		ListIterator<List<String>> itr = products.listIterator();
		while (itr.hasNext()) {
			product = itr.next();
			
			assertEqualsWithFlags(product.get(productTypeColumn) , "Maintenance Subscription");
			assertEqualsWithFlags(product.get(licenseTypeColumn) , "Renewal");
			
			//String stringContractNumbers = subRenewal.getStringOfContractNumbers();
			//String [] arrayOfContractNumbers = stringContractNumbers.split(";"); 
			
			//NOTES TO OFFSHORE - this needs to be set to false for every product
			found2 = false;
			for (int arrayIndex = 0; arrayIndex < arrayOfContractNumbers.length; arrayIndex++) {
				if (product.get(commentsColumn).contains(arrayOfContractNumbers[arrayIndex])) {
					found2 = true;
					break;
				}
			}
		
			assertTrueWithFlags("The Comment contains one of the contract numbers " +subRenewal.getStringOfContractNumbers() , found2);
		}
		
		
//		int intNoOfRows = viewServiceContractPage.getNumRowsInRelatedList("productsRelatedList");
//		for(int rowIndex=0; rowIndex < intNoOfRows; rowIndex++){
//
//			//NOTES TO OFFSHORE - toString() call on a String object is redundant. I have removed it in a few places here
//
//			//NOTES TO OFFSHORE - there are some bad variable names here.  Calling a variable that contains one row "rowsinRelatedList"
//			//  is misleading!
//			List<String> rowinRelatedList = viewServiceContractPage.getRelatedListRow("productTypeinProductsRelatedList", rowIndex);
//			String[] arrayOfValues = rowinRelatedList.toArray(new String[rowinRelatedList.size()]);
//			
//			//NOTES TO OFFSHORE - see my email of July 20 for an example of how to do this by calling EISTestBase.assertEqualsWithFlags
//			
//			assertEqualsWithFlags(arrayOfValues[2], "Subscription");
//			assertEqualsWithFlags(arrayOfValues[3], "Renewal");
//			
//			String stringContractNumbers = subRenewal.getStringOfContractNumbers();
//			String [] arrayOfContractNumbers = stringContractNumbers.split(";"); 
//
//			//NOTES TO OFFSHORE - this loop has a logic issue: it should break out of the loop the first time a serial number is found.  In addition,
//			//  the comparison you are doing should be done in an assert or verify call (see my email of July 20).  Below this commented code are a
//			// 	couple of alternatives.  PLEASE NOTE THAT those alternatives are using some of the existing code here that needs to be changed,
//			//  especially the use of hard-coded array subscripts instead of column numbers derived from page metadata (see SubscriptionRenewal,
//			//  around line 684)
//			//for(int arrayIndex = 0; arrayIndex < arrayOfContractNumbers.length; arrayIndex++){
//			//	if(arrayOfValues[8].toString().contains(arrayOfContractNumbers[arrayIndex])){
//			//		Util.printInfo("The Comments contain one of the contract numbers. The comment displayed is : - " +arrayOfValues[8] + " The comment contains serial number : - " + arrayOfContractNumbers[arrayIndex]);
//			//	}
//			//	else{
//			//		if(arrayIndex == arrayOfContractNumbers.length){
//			//			fail("The Comments doesn't contain one of the contract numbers. The comment displayed is : - " +arrayOfValues[8] + " The comment doesn't contain any of the serial numbers : - " + stringContractNumbers);	
//			//		}
//			//	}
//			//}
//
//
//			
//			
//			
//			//***** ALTERNATIVE 1 (better)
//			//This alternative breaks out of the loop whens it finds the value, then fails the test if the value was not found.
//			//  However, it does not call one of the "assert with flags" methods
////			boolean found = false;
////			for(int arrayIndex = 0; arrayIndex < arrayOfContractNumbers.length; arrayIndex++) {
////				if (arrayOfValues[8].contains(arrayOfContractNumbers[arrayIndex])) {
////					found = true;
////					break;
////				}
////			}
////			
////			if (!found) {
////				fail("The Comment '" + arrayOfValues[8] + "' does not contain one of the contract numbers " + arrayOfContractNumbers.toString());
////				//or
////				//fail("The Comment '" + arrayOfValues[8] + "' does not contain one of the contract numbers " + stringContractNumbers);
////			}
////			//***** ALTERNATIVE 1 (end)
//			
//
//			
//			
//			
//			//***** ALTERNATIVE 2 (best - use this one)
//			//This alternative breaks out of the loop when it finds the value, then fails the test if the value was not found,
//			//  but it does that by calling one of the "assert with flags" methods
//			boolean found2 = false;
//			for(int arrayIndex = 0; arrayIndex < arrayOfContractNumbers.length; arrayIndex++) {
//				if (arrayOfValues[8].contains(arrayOfContractNumbers[arrayIndex])) {
//					found2 = true;
//					break;
//				}
//			}
//			
//			assertTrueWithFlags("The Comment contains one of the contract numbers " + arrayOfContractNumbers.toString(), found2);
//			//or
//			//assertTrueWithFlags("The Comment contains one of the contract numbers " + stringContractNumbers, found2);
//			//or one of the above but also passing page name and field name
//
//			//What will be printed to the console:
//			//  ASSERTING
//			//    The test 'The Comment contains one of the contract numbers [1647837, 8699305, 4783902]' should be true"
//			//  ASSERT PASSED
//			//or
//			//  ASSERTING
//			//    The test 'The Comment contains one of the contract numbers [1647837, 8699305, 4783902]' should be true"
//			//  ASSERT FAILED
//			//
//			//  TEST FAILED! Reason:
//			//    *** ASSERTION FAILURE: The test 'The Comment contains one of the contract numbers [1647837, 8699305, 4783902]' should be true, but is false"
//
//			//***** ALTERNATIVE 2 (end)
//		}
	}	
//
//	private void DBConnect() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//		Connection conn;
//		Util.printInfo("Connecting to DB");
//		String host1 = "USPETQDTSTAUT01";
//		String db1= "Automation";
//		Statement stmt=null;
//		 String driverName = "com.mysql.jdbc.Driver";
//		//Rdbms rdbms = Rdbms.getInstance(getResourceDir() + dbConfigFileName);
//		
//		try {
//			
//			Class.forName(driverName).newInstance();
//
//			conn = DriverManager.getConnection("jdbc:mysql://USPETQDTSTAUT01:3306/automation", "root", "505tulsa");
//			System.out.println("ODS Database is connected successfully.");
////			SELECT COUNT (*) FROM [GDW].[dbo].[T_DIM_OPPORTUNITY_LINE_ITEM]
//			stmt = conn.createStatement();
//
//			String query1= "SELECT COUNT(*) from test_master where AUTOMATION_TOOL ='Selenium'";
//			 System.out.println("query1---"+query1);
//			ResultSet rs=stmt.executeQuery(query1);                  
//	        //Extact result from ResultSet rs
//	        while(rs.next()){
//	            System.out.println("COUNT(*)="+rs.getInt("COUNT(*)"));                              
//	          }
//	        // close ResultSet rs
//	        rs.close();
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
        
        
        
//		Statement s = conn.createStatement ();
//		   s.executeQuery ("SELECT id, name, category FROM animal");
//		   ResultSet rs = s.getResultSet ();
//		System.out.println(query1);
				
		
		
//	}

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
}
