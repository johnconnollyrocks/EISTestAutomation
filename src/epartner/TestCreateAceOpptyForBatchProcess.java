package epartner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import common.DBConnection;
import common.EISTestBase;
import common.Oppty;
import common.Page_;
import common.Rdbms;
import common.Util;

/**
 * Test class - TestOpptySharingBetweenPartnerAndDistributor
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateAceOpptyForBatchProcess extends EpartnerTestBase {
	public TestCreateAceOpptyForBatchProcess() {
		super("chrome");
	}
	
	@Before
	public void setUp() throws Exception {
		//launchSalesforce(EpartnerConstants.BASE_URL);
		launchSalesforce(getBasePartnerURL());
	}

	@Test
	public void Test_ApproveAceOpptyInSFDC() throws Exception {
		DBConnection dbConnect=new DBConnection();
		dbConnect.getDatabaseConnectionSTG();
		Oppty oppty = utilCreatePartnerOppty();
		Page_ viewPartnerOpportunityPage = oppty.getViewOpptyInPortalPage();
		String opportunityName;
		String opportunityNumber;
		String editedOpptyName;
		StringBuffer outputOpptyList=new StringBuffer("opptyList=");
		String outputOppties="opptyList=";
		String shipToCSN;
		String soldToParty;
		String query;
		List<String> opptyNames= new ArrayList<String>();
		List<String> opptyNumbers= new ArrayList<String>();
		Page_ portalLandingPage = oppty.getPortalLandingPage();
		Page_ viewOpptyPage	=oppty.getViewOpptyPage();
		loginAsEpartnerUser(testProperties.getConstant("PARTNER_USER_NAME"), testProperties.getConstant("PARTNER_PASSWORD"), false);
		oppty.setUrl();
		
		portalLandingPage.clickAndWait("dealRegACELink", "partnerCenterHome");			
//		for(int j=0;j<2;j++){
//		viewPartnerOpportunityPage.click("opportunity");
		opportunityName=oppty.createPartnerOpportunity();
		
		
		
//		viewPartnerOpportunityPage1.verifyField("opportunityName");
		opportunityNumber=viewPartnerOpportunityPage.getValueFromGUI("opportunityNumber");
		Util.printInfo("Opportunity Number is: "+opportunityNumber);
		oppty.addContactsToOpportunity();
		
//		EISTestBase.switchDriverToFrame(4);
//		commonPage.click("saveButton");
		viewPartnerOpportunityPage.verifyInstance("AFTER_ADD_CONTACTS");
		mainWindow.select();
		
		oppty.addDistributorsToOpportunity();
		
		
		EISTestBase.switchDriverToFrame(5);
		viewPartnerOpportunityPage.verifyInstance("AFTER_ADD_DISTS");
		mainWindow.select();
		
		oppty.addRequiredProductsToOpportunity();
		
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("productInproductsRelatedList", 0, "AFTER_ADD_PRODS");
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("estimatedSeatsInproductsRelatedList", 0, "AFTER_ADD_PRODS");
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("productTypeInproductsRelatedList", 0, "AFTER_ADD_PRODS");
//		viewPartnerOpportunityPage1.verifyRelatedListCellInstance("licenseTypeInproductsRelatedList", 0, "AFTER_ADD_PRODS");		
//		viewPartnerOpportunityPage1.verifyInstance("AFTER_ADD_PRODS");
		
	
		
		editedOpptyName=oppty.editOppty(opportunityName.concat("_1"));
		oppty.registerDealAndConfirm();
		opportunityNumber=viewPartnerOpportunityPage.getValueFromGUI("opportunityNumber");
		
		opptyNames.add(editedOpptyName);
		for(int i=2;i<=10;i++){
		commonPage.click("cloneButton");
		editedOpptyName=oppty.editOppty(opportunityName.concat("_"+i));
		opptyNames.add(editedOpptyName);
		oppty.registerDealAndConfirm();
		}
		for(int i=0;i<opptyNames.size();i++){
			Util.printInfo("Edited Oppty Name:"+opptyNames.get(i));	
		}
			
		
//		launchSalesforce(getAppServerBaseURL());
//		}
		viewPartnerOpportunityPage.click("partnerCenterHome");
		
		logoutAsEpartnerUser();	
		open("https://test.salesforce.com");
		loginAsAutoUser(false);
		for(int i=0;i<opptyNames.size();i++){
			
			oppty.searchOppty(opptyNames.get(i));

			commonPage.clickLinkInRelatedList("opptyNameInopportunitiesRelatedList", 0);
			
			Util.printInfo("Changing the deal status of the oppty : " + opptyNames.get(i) + " to Approved" );
			commonPage.clickToSubmit("editButton", "dealStatusList");
			commonPage.populateField("dealStatusList", "Approved");
			
			//Handling alert
			driver.findElement(By.xpath("//td[@id='topButtonRow']/input[normalize-space(@class)='btn' and normalize-space(@value)='Save']")).click();
			if(commonPage.isAlertPresent()){
				commonPage.acceptAlert();
			}
		//	commonPage.click("saveButton");
			
			Util.printInfo("Changed the deal status of the oppty : " + opptyNames.get(i) + " to Approved" );
			
			Util.printInfo("Changing the deal status of the oppty : " + opptyNames.get(i) + " to Ordered" );
			commonPage.clickToSubmit("editButton", "dealStatusList");
			commonPage.populateField("dealStatusList", "Ordered");
			commonPage.clickToSubmit("saveButton", "editButton");
			Util.printInfo("Changed the deal status of the oppty : " + opptyNames.get(i) + " to Ordered" );
			
			opportunityNumber=viewOpptyPage.getValueFromGUI("opptyNumber");
			Util.printInfo("Opportunity Number is: "+opportunityNumber);
//			opptyNumbers.add(opportunityNumber);
//			outputOpptyList.append(opportunityNumber);
//			outputOpptyList.append(";");
//			Util.printInfo("outputOpptyList ="+outputOpptyList);
			shipToCSN = commonPage.getValueFromGUI("CSN");
			soldToParty= testProperties.getConstant("SOLDTO");
			query="INSERT INTO `automation`.`aceoppties` (aceoppty,soldto,shipto,ordered) values ('"+opportunityNumber+"','"+soldToParty+"','"+shipToCSN+"','Yes')";
			dbConnect.ExecuteQuery(query);
		}
			
		
//		outputOpptyList.append(":");
//		outputOpptyList.append(shipToCSN);
//		outputOpptyList.append(":");
//		outputOpptyList.append(soldToParty);
		
//		String[] cmd={"cscript", "//ecs-9844/DoNotDelete/JenkinsData/DynamicPropertiesUpdatetoP4.vbs", outputOpptyList.toString(),"OpptyList.properties"};
//		String[] cmd={"wscript", System.getProperty("user.dir")+"//build"+"DynamicPropertiesUpdatetoP4.vbs", "Hi Hello","OpptyList.properties"};
//		Process p=Runtime.getRuntime().exec(cmd);  
		
//		dbConnect.closeStatement();
		dbConnect.closeConnection();
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
}
