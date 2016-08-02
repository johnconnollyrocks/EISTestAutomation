package customerportal;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;
/**
 * @USER STORY: US 1578
 * @author t_marus
 *
 */

public class Test_Verify_AssignIndvUpd_Part1 extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;
	
	
	public Test_Verify_AssignIndvUpd_Part1() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
/**
 * Contains US 1578 TC:461, 472 test cases
 * @throws Exception
 */
	@Test
	public void testAssignIndividualUpdates() throws Exception {		
		System.out.println("Does not accept any Credentials from Jenkins for this test. As this is mostly driven based on User accounts given in test data");
		/*LoginAndGoProductUpdatesPage();*/
		USERNAME=getUserCredentials("TC461")[0];
		PASSWORD=getUserCredentials("TC461")[1];
		LoginAndGoProductUpdatesPage(USERNAME, PASSWORD);
		
		//*******TC461*******************
		//Verify if the Only Selected option is selected
		//Expand the access control for first the Update
		Util.printInfo("========================================TC461==========================================");
		Util.sleep(2000);		
		//get the first one
		Util.printInfo("Expand the product update by clicking on drawer button" );
		/*String articleUpdateText=productUpdatePage.getMultipleWebElementsfromField("newProductUpdatesList").get(0).getText();*/
		/*String productToggleDrawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("accessToggleDrawer", articleUpdateText);*/
		String productToggleDrawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("accessToggleDrawer",getListOfProductsInUpdatesPageViaJscript().get(0));	//This is used to pull the updates label via jscript as the regular gettext is trucated text in between
		productUpdatePage.click(productToggleDrawer);
		
		Util.printInfo("Verify that Access control button exists for that product update" );
		productUpdatePage.verifyFieldExists("accessControlButton");
		productUpdatePage.click("accessControlButton");
		
		String selectedDevices=productUpdatePage.getValueFromGUI("numberOfDevicesAddedUnderAccessControl");
		int iPos=selectedDevices.lastIndexOf("(");
		//parse the selected Devices
		selectedDevices=selectedDevices.substring(iPos+1,selectedDevices.length()).replace(")", "");
		Util.printInfo("Verify that Only Selected Option exists in Access control menu");
		assertTrueWithFlags("The Only selected Options exists for the User CM",productUpdatePage.isFieldVisible("onlySelectdDev"));
	
		Util.printInfo("Check if any devices are selected before, if not then number should be 0");
		//if the arrow doesnt exist then selected should show 0
		if (!productUpdatePage.isFieldVisible("myDevicesArrowBtnInAccessControl")){
			Util.printInfo("No devices have been selected hence the number should be 0");
			assertEquals(selectedDevices, "0");
		}	else{
			//remove all the devices
			productUpdatePage.click("myDevicesArrowBtnInAccessControl");
			//Although the token is given in xpath, that can be ignored here to pull the list of devices
			List<WebElement> removeDeviceList=productUpdatePage.getMultipleWebElementsfromField("removeDevice");
			for(WebElement myDeviceToRemove: removeDeviceList){
				myDeviceToRemove.click();
				Util.sleep(1000);
			}			
			//need static delay 
			Util.sleep(2000);	
			//check if the no arrow btn exists
			assertTrueWithFlags("The arrow button to side of Only selected shouldnt be visible when there are no devices",productUpdatePage.isFieldVisible("myDevicesArrowBtnInAccessControl"));
		}
		//click on add button and verify if the devices list pop up is shown		
		productUpdatePage.click("accessControlAddDev");
		Util.sleep(1000);
		//check if PII pop up appears
		if (productUpdatePage.isFieldVisible("acceptTheAgreement")){
			productUpdatePage.click("acceptTheAgreement");
		}
		
		assertTrueWithFlags("The Device table should be shown when the user clicks on Add button",(productUpdatePage.isFieldVisible("selectDevicesTable")
				&& productUpdatePage.isFieldVisible("selectDevicesHeaderMessage")
				&&  productUpdatePage.isFieldVisible("selectedDevicesCheckBoxCount")));
		
		//Click on Cancel btn and check if the selected devices still 0
		String selectBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "cancel");		
		Util.printInfo("Verifying that the Only selected still shows 0 when user clicked on Cancel btn in Select Devices table");
		productUpdatePage.click(selectBtnloc);
		Util.sleep(2000);
		if(!productUpdatePage.isFieldVisible("numberOfDevicesAddedUnderAccessControl")){
			productUpdatePage.click("accessControlButton");
			Util.sleep(2000);
		}
		 selectedDevices=productUpdatePage.getValueFromGUI("numberOfDevicesAddedUnderAccessControl");
		 iPos=selectedDevices.lastIndexOf("(");
		//parse the selected Devices
		selectedDevices=selectedDevices.substring(iPos+1,selectedDevices.length()).replace(")", "");
		assertEquals(selectedDevices, "0");
		logoutMyAutodeskPortal();
		
		
		//TC472 .. here hard coding the name of update as this only the one has no individual preferences set till now
		//need to login with different user acct
		Util.printInfo("======================================TC 472============================================");
		Util.printInfo("Verifying the test :TEST 472");
		String productUpdName=getSpecificTestDataBasedOnEnv("TC472_UPDATENAMEID");
		USERNAME=getUserCredentials("TC472")[0];
		PASSWORD=getUserCredentials("TC472")[1];
		
		LoginAndGoProductUpdatesPage(USERNAME, PASSWORD);
		Util.printInfo("Choosing a specific product update: "+productUpdName);
		String productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", productUpdName);
		if (productUpdatePage.isFieldVisible("acceptTheAgreement")){
			productUpdatePage.click("acceptTheAgreement");
		}
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("Verify that the access control has an option selected by default when logged in");		
		//click on access control and store the setting
		productUpdatePage.isFieldPresent("accessControlButton");
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.click("accessControlButton");//click on specific prod
		boolean bselected=isIndividualPrefSelectedForSpecificUpdate();
		assertTrueWithFlags("Any one of the Access control individual update preferences should be selected by default",bselected);
		
		// Change the Global setting flag here
		Util.printInfo("Changing the Global settting for Product updates to 'NONE'");
		productUpdatePage.isFieldPresent("deliverySettingsOnManageDevices");
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.click("deliverySettingsOnManageDevices");
		Util.sleep(4000); //wait for the pop up to appear
		
		String globalSettingIdValue=getSpecificTestDataBasedOnEnv("TC472_DEVICESELECTIONID");
		String radioBtninDeliverySetngLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("deliverySettingsRadioBtns", globalSettingIdValue);
		productUpdatePage.click(radioBtninDeliverySetngLoc);
		//click on Select
		Util.printInfo("Clicked on Select button");
		Util.sleep(2000);	//need static sleep here
		selectBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "select");		
		productUpdatePage.click(selectBtnloc);		
		Util.sleep(3000);
		productUpdatePage.click("accessControlButton");
		//NOW HERE ASSUMING THAT THIS INDV preference is not touched or done any updates in the backend. 
		productUpdatePage.isFieldPresent(productDrawerLoc);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		String indvSettingValue=getIndividualUpdatesPrefValueForSpecificUpdate();		
		Util.printInfo("The value of Individual Access control setting is :"+indvSettingValue+" which inherits from global setting value");
		String expIndvUpdPref=globalSettingIdValue.toLowerCase().replace("-", " ");
		assertTrueWithFlags("The Global setting :"+globalSettingIdValue+" should be seen for the individual update whose id is: "+productUpdName, indvSettingValue.equalsIgnoreCase(expIndvUpdPref));
		
		Util.printInfo("==================================================================================");
		//here logout and login check if the setting persists
		logoutMyAutodeskPortal();
		LoginAndGoProductUpdatesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that Access control settings persists when CM logs out and login back");
		 productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", productUpdName);
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("CLicking on Product access control button");
		Util.sleep(2000);
		productUpdatePage.click("accessControlButton");//click on specific prod
		Util.sleep(2000); //need to wait
		//click on access control and store the setting
		indvSettingValue=getIndividualUpdatesPrefValueForSpecificUpdate();		
		assertTrueWithFlags("The Individual update preference settings for the update id "+productUpdName+" should persist when user logout and login back" , indvSettingValue.equalsIgnoreCase(expIndvUpdPref));
		logoutMyAutodeskPortal();	
		
	}
	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
	
	
		
}