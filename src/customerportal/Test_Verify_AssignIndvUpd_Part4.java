package customerportal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_Verify_AssignIndvUpd_Part4 extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;


	public Test_Verify_AssignIndvUpd_Part4() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}


	@Test
	public void testAssignIndividualUpdates_Part4() throws Exception {	
		System.out.println("Does not accept any Credentials from Jenkins for this test. As this is mostly driven based on User accounts given in test data");

		/*NOTE: TC478 cannot be AUTOMATED as this test requires the Device to be changed at customer side*/
		Util.printInfo("======================================TC491==================================================================");
		Util.printInfo("Verify that the devices set for an update by the registered user like SC who is also End User or the admin is correctly displayed to them for that update");
		
		USERNAME=getUserCredentials("TC491")[0];
		PASSWORD=getUserCredentials("TC491")[1];
		LoginAndGoProductUpdatesPage(USERNAME, PASSWORD);
		String productUpdName=getSpecificTestDataBasedOnEnv("TC491_UPDATENAMEID");
		Util.printInfo("Choosing a specific product update: "+productUpdName);
		String productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", productUpdName);		
		Util.printInfo("Expand the product update by clicking on drawer button");			
		productUpdatePage.click(productDrawerLoc);
		
		Util.printInfo("Verify that Access control button exists for that product update" );
		productUpdatePage.verifyFieldExists("accessControlButton");
		Util.printInfo("Clicking on Product access control button");
		productUpdatePage.click("accessControlButton");		
		Util.sleep(2000);
		
		//Select all Devices and then log out and log in , check if the setting persists
		Util.printInfo("Setting All devices as individual preference for the Product update: "+productUpdName);
		productUpdatePage.click("allDeviceRadioButton");		 
		Util.sleep(2000);	//wait for a sec to trigger the fetch device pref call
		logoutMyAutodeskPortal();	
		
		Util.printInfo("Verify that for registered user i.e non subscription user individual device preferences persists when the user logout and logs in again");		
		LoginAndGoProductUpdatesPage(USERNAME, PASSWORD);		
		productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", productUpdName);
		productUpdatePage.click(productDrawerLoc);//click on specific product
		Util.printInfo("Clicking on Product access control button");		
		Util.sleep(2000);
		Util.printInfo("Clicking on Product access control button");
		productUpdatePage.click("accessControlButton");		
		Util.sleep(3000);		
		String indvSettingValue=getIndividualUpdatesPrefValueForSpecificUpdate();
		assertTrueWithFlags("The Individual Device preferences should persists when logout and logged in, for a registered user who is actually a End user and SC", indvSettingValue.equalsIgnoreCase("all devices"));


		/* *********************************************************************** */
		//NOTE:TC 492 is SAME AS TC 539
		/* *********************************************************************** */

		//START OF TC539 .. instead of logging out change the individual setting preference is to None here, this guy is a SC 

		Util.printInfo("======================================TC539=================================================================");
		String CMUserName=getSpecificTestDataBasedOnEnv("TC539_CM_USERNAME");
		Util.printInfo("Verify that settings done by SC :"+USERNAME+" and can be seen by CM:" +CMUserName+" and vice-versa");
		Util.printInfo("SC Going to change the Individual preferences now and check if CM can see it");
		
		Util.printInfo("Setting NO devices as individual preference for the Product update: "+productUpdName);
		productUpdatePage.click("noDevicesRadioButton");		 
		Util.sleep(2000);	//wait for a sec to trigger the fetch device pref call
		//now log out and login with CM check if can see device settings.
		Util.printInfo("SC is logging out and after changing the device preferences");
		logoutMyAutodeskPortal();	
		
		USERNAME= getUserCredentials("TC539_CM")[0];
		PASSWORD= getUserCredentials("TC539_CM")[1];
		LoginAndGoProductUpdatesPage(USERNAME, PASSWORD);
		
		Util.printInfo("CM : "+USERNAME+" logged in");
		productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", productUpdName);
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("Clicking on Product access control button");
		Util.sleep(2000);
		productUpdName=testProperties.getConstant("TC491_UPDATENAMEID");
		productUpdatePage.click("accessControlButton");//click on specific prod
		Util.sleep(3000);
		indvSettingValue=getIndividualUpdatesPrefValueForSpecificUpdate();
		assertTrueWithFlags("The Individual Device preferences should be seen by CM :"+USERNAME, indvSettingValue.equalsIgnoreCase("no devices"));
		logoutMyAutodeskPortal();	
		Util.printInfo("END OF US 1578");
	}


	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}

}
