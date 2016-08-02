package customerportal;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import common.EISTestBase;
import common.Util;
/**
 * @USER STORY: US 1411
 * @author t_marus
 * NOTE: TC594 and TC616 cannot be automated, as one involves localization and later ipad
 */

public class Test_Verify_SearchDev_AddOptWind extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;
	private String hrefViewReleaseNotes=null;
	private String expReleaseNotesWindTitle=null;
	private String expPlaceholderText="Search devices";
	private String searchString=null;
	private ArrayList<String> lsDeviceInfoInUI= new ArrayList<>();
	private String dynamicLoc=testProperties.getConstant("LOCATORSDYNAMICTOKENS");
	private String[] listDynamicLoc= dynamicLoc.split(",");
	private String jScriptToGetValueInSearchDevPlaceholder="$('div[id=\"select-devices-modal\"] input[name=\"search\"]').val()";
	private String germanDeviceAlias="k�nigstra�e";
	private String specialCharString="\\";
	private String sortColumnheader="Last User";
			
	public Test_Verify_SearchDev_AddOptWind() throws Exception {		
		super("Browser",getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
		
	}
	@Test
	public void testSearchDevicesInAddDevicesOptionWindow() throws Exception {
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			USERNAME=getUserCredentials("SEARCHDEVICES")[0];
			PASSWORD=getUserCredentials("SEARCHDEVICES")[1];
			loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);	
					
		}
		
		mainWindow.select();
		Util.sleep(5000);
		GoToProductUpdatesPage();
		launchAddDeviceDialogViaProductUpdates_AccessControl_Add();
		assertTrueWithFlags("The Search bar in Select Device modal dialog should be visible", productUpdatePage.isFieldVisible("deviceSearchBarInSelectDevModalDialog"));
		getDevicesDataInDeviceTable();
		String actplaceHolderText=productUpdatePage.getAttribute("deviceSearchBarInSelectDevModalDialog", "placeholder");
		assertEquals(expPlaceholderText,actplaceHolderText);
		searchString=lsDeviceInfoInUI.get(2);	//get the Last user of the first device and search for it
		int rowsNumBeforeSearch=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;
		productUpdatePage.sendKeysInTextFieldSlowly("deviceSearchBarInSelectDevModalDialog",searchString);
		//get the row count before search and see that row count should be less than or equal to rows found after search results
		int rowsNumafterSearch=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;
		//if search string contains "\" then replace "\" with "\\"
		if (searchString.contains("\\"))
			searchString.replace("\\", "\\\\");
		//now get whether the grid has search Data
		Util.printInfo("Verify that the search in Select modal dialog works as expected with the search string:"+searchString);
		lsDeviceInfoInUI.clear();
		getDevicesDataInDeviceTable();	//get the data and dump into lsDeviceInfoUi 
		assertTrueWithFlags("The search string: "+searchString+" is found in the Device table grid after search", (lsDeviceInfoInUI.contains(searchString)
				&& rowsNumafterSearch <=rowsNumBeforeSearch));
		
		//Ability to clear the search bar 
		//click on  'X' to clear the search and check that search bar is cleared
		Util.printInfo("Verify that clicking on 'X' close button the search criteria clears and revert all the rows in the grid");
		
		productUpdatePage.click("clearSearchTextInSelectDevModalDialog");	//clear the search and check if no text is found in search bar after clearing
		// also check if the table gets the data back
		Util.sleep(1000);
		rowsNumafterSearch=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;
		// the rows inthe table should be same as before
		assertTrue("The value in Search Device placeholder text should be reset to 'Search device' when search text is cleared", (getValueInSearchDeviceSelectModalDialog().isEmpty()
				&& rowsNumafterSearch==rowsNumBeforeSearch));
		
		//Check Search string is not case sensitive
		Util.printInfo("Verify that search string is not case sensitive and should yield the correct results");
		rowsNumBeforeSearch=0;
		rowsNumafterSearch=0;
		rowsNumBeforeSearch=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;
		productUpdatePage.sendKeysInTextFieldSlowly("deviceSearchBarInSelectDevModalDialog",searchString.toUpperCase());
		Util.sleep(1000);
		rowsNumafterSearch=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;
		lsDeviceInfoInUI.clear();
		getDevicesDataInDeviceTable();	//get the data and dump into lsDeviceInfoUi 
		assertTrueWithFlags("The search string  should be case insensitive and should display correct results "+searchString.toUpperCase()+" in the Device table when search ", lsDeviceInfoInUI.contains(searchString)
				&& rowsNumafterSearch <=rowsNumBeforeSearch);
		
		//Check that no error message is found when searched with some unknown string
		Util.printInfo("Verify that no error message is found when searched with some unknown string and should no devices found as expected message");
		String unknownString="zxxaszafs";		
		//noDevicesFilterinSelectDevModalDialog
		productUpdatePage.sendKeysInTextFieldSlowly("deviceSearchBarInSelectDevModalDialog",unknownString);
		Util.sleep(1000);
		//check no devices filter is displayed and search bar contains same text
		assertTrueWithFlags("The search string with unknown chars "+unknownString+" should not yield any results  in the Device table when searched ", (productUpdatePage.isFieldVisible("noDevicesFilterinSelectDevModalDialog")&&
				getValueInSearchDeviceSelectModalDialog().equalsIgnoreCase(unknownString)));
		
		
		//click on  'X' to clear the search and check that search bar is cleared
		productUpdatePage.click("clearSearchTextInSelectDevModalDialog"); //check that 		
		
		//Now check if the sorting order is maintained while doing the search
		//Sort the Last User column and search for Devices in Search bar . check the sort order is still on Description column		
		String jqueryScripttoClickOnDeviceTabelColumnheader="$('table[role=\"grid\"]').trigger(\"sorton\",[[[2,1]]]);";	//sorting on last user column in descending order
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript(jqueryScripttoClickOnDeviceTabelColumnheader);
		//click the column header using low level action just to let the sort order remain in tact otherwise via automation the order jumps whihc doesnt happen manually
		productUpdatePage.isFieldPresent("checkSortappliedonDevColHeaderinSelectDevModalDialog");
		productUpdatePage.clickUsingInputDevicesMouseClick(productUpdatePage.getCurrentWebElement());
		Util.sleep(500);// check if the sorting remains same
		productUpdatePage.sendKeysInTextFieldSlowly("deviceSearchBarInSelectDevModalDialog",searchString);
		Util.sleep(1000);
		assertTrueWithFlags("The sorting order should be same while searching for the Device",productUpdatePage.getValueFromGUI("checkSortappliedonDevColHeaderinSelectDevModalDialog").trim().equalsIgnoreCase(sortColumnheader));		

		//NOTE: TC594 localization testing is not supported
		Util.printInfo("Localization test is not supported; Hence skipping this test : TC594");
		
		//Now check if maximimum length of the search pattern string
		String longString="Device99ads\\supriya's2longdescriptioncomputerADS//lO";
		Util.printInfo("Verify that maximum length of the search phrase should not exceed 30");
		productUpdatePage.sendKeysInTextFieldSlowly("deviceSearchBarInSelectDevModalDialog",longString);
		// now get the string  which is entered in searchbar and see that length does not exceed 30
		String enteredSearchText=getValueInSearchDeviceSelectModalDialog();
		//now get the length of it
		assertTrueWithFlags("The Lenght of the search phrase should not exceed 30 chars", (enteredSearchText.length()==30 &&
				productUpdatePage.getAttribute("deviceSearchBarInSelectDevModalDialog","maxlength").equalsIgnoreCase("30")));
		
		String cancelBtninSelectDev=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectDevModalBtns", "cancel");
		productUpdatePage.click(cancelBtninSelectDev);
		Util.sleep(1000);
		//click on cancel in Delivery settings
		String cancelBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "cancel");
		/*if (productUpdatePage.isFieldVisible("cancelBtnloc")) {
			productUpdatePage.click(cancelBtnloc);			
		}*/
		
		//check when the user in the portal is in English and user search for different lang Device name then search results should
		// show the devices name
		//To do this test step, you need to change the device alias name to german name
		//select any device 
		//go to manage devicespage
		Util.printInfo("Clicking on Devices Tab");
		homePage.checkIfElementExistsInPage("genericLoadingIcon", 40);
		if (!productUpdatePage.isFieldPresent("manageDevicesTab")){
			EISTestBase.fail("No Devices tab is found, Hence exiting the test");
		}		
		productUpdatePage.waitForFieldVisible("manageDevicesTab","50");
		productUpdatePage.click("manageDevicesTab");
		Util.sleep(2000);		
		if(productUpdatePage.isFieldVisible("acceptTermsInDevicePage")){			
			productUpdatePage.click("acceptTermsInDevicePage");
		}
		homePage.checkIfElementExistsInPage("genericLoadingIcon", 30);
		productUpdatePage.click("editFirstDevice");	//always the first device and change it to german  
		productUpdatePage.isFieldVisible("renameDeviceDialogtextPlaceholder");	// click inside  and enter text
		((JavascriptExecutor)(driver)).executeScript("arguments[0].focus();",productUpdatePage.getCurrentWebElement());
		enterAliasDeviceinfo(germanDeviceAlias);
		Util.sleep(1000);		//now click on delivery settings and go to add select dev window		
		productUpdatePage.click("deliverySettingsOnManageDevices");	//
		Util.sleep(800);
		productUpdatePage.click("addDevices");
		//Now search for the german alias name
		Util.printInfo("Verify that the system has ablity to search words in other languages, when the user is set in English language and also verifies that Device alias name step");
		rowsNumBeforeSearch=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;		
		productUpdatePage.sendKeysInTextFieldSlowly("deviceSearchBarInSelectDevModalDialog",germanDeviceAlias);
		Util.sleep(1000); //to avoid race condition
		//verify that the search results should show matching to search pattern
		rowsNumafterSearch=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;
		
		assertTrue("The different language search phrase should display search results though the user profile is set in English", (rowsNumafterSearch<rowsNumBeforeSearch));
		
		productUpdatePage.click("clearSearchTextInSelectDevModalDialog");	//clear the search and check if no text is found in search bar after clearing
		// also check if the table gets the data back
		Util.sleep(1000);
		//Search special chars in Select Device modal dialog
		rowsNumBeforeSearch=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;
		productUpdatePage.sendKeysInTextFieldSlowly("deviceSearchBarInSelectDevModalDialog",specialCharString);
		rowsNumafterSearch=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;
		Util.sleep(1000);
		assertTrue("The search phrase with special character should work as expected", (rowsNumafterSearch<=rowsNumBeforeSearch));
		cancelBtninSelectDev=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectDevModalBtns", "cancel");
		productUpdatePage.click(cancelBtninSelectDev);
		Util.sleep(2000);
		//click on cancel in Delivery settings
		cancelBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "cancel");
		productUpdatePage.click(cancelBtnloc);
		
		
		Util.printInfo("Verify that the Search bar exists when there are no devices exists for the User account ");
		logoutMyAutodeskPortal();
		// Then now login with the user which doesnt have any devices  and search bar should exist
		String noDev_Username=getUserCredentials("NODEVICESUSER")[0];
		String noDev_Passwd=getUserCredentials("NODEVICESUSER")[1];
		loginAsMyAutodeskPortalUser(noDev_Username ,noDev_Passwd);
		
		//Now here go to Manage devices page and click on Global settings icon and try to check
		goToManageDevicePage();
		Util.printInfo("Click on Delivery settings icon in ManageDevices page");	//click on delivery settings
		productUpdatePage.click("deliverySettingsOnManageDevices");	//
		Util.printInfo("Click on 'Add' button in Delviert settings window");
		productUpdatePage.click("addDevices");
		//wait for the add devces window 
		assertTrueWithFlags("The Search bar in Select Device modal dialog should be visible when there are no devices", productUpdatePage.isFieldVisible("deviceSearchBarInSelectDevModalDialog"));
		//click on cancel
		cancelBtninSelectDev=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectDevModalBtns", "cancel");
		productUpdatePage.click(cancelBtninSelectDev);
		Util.sleep(2000);
		//click on cancel in Delivery settings
		cancelBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "cancel");
		productUpdatePage.click(cancelBtnloc);
		
	}
	
	private void enterAliasDeviceinfo(String aliasInfo) {
		try{
			productUpdatePage.getCurrentWebElement().sendKeys(aliasInfo);			
			Util.sleep(900);
			productUpdatePage.getCurrentWebElement().sendKeys(Keys.RETURN);
		}
		catch(Exception e){	}
		
	}
	public String getValueInSearchDeviceSelectModalDialog() {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		Object objVal=js.executeScript("return "+jScriptToGetValueInSearchDevPlaceholder);
		String valinSearchDeviceHolder=objVal.toString();
		return valinSearchDeviceHolder;
	}
	public void getDevicesDataInDeviceTable() throws Exception{
		//go to manage devices page and grab the devices info
		// if not found then go to manage devices tab
		int rowsNum=productUpdatePage.getMultipleTextValuesfromField("allRowsinSelectDevicesDialogmodal").length;
		int j=0;
		while(j<rowsNum){
		
			for(int i=0;i<listDynamicLoc.length;i++){
				String dynamicLocator=listDynamicLoc[i];		
			
				//get each row info of specific td
				String getRowDevicesInfo=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("deviceSpecificRowColinSelectDevModalDialog", String.valueOf(j+1 ),dynamicLocator);
				if (productUpdatePage.isFieldVisible(getRowDevicesInfo)){
					String devicerowInfo=productUpdatePage.getValueFromGUI(getRowDevicesInfo);	//only row info
					lsDeviceInfoInUI.add(devicerowInfo);		
				}
			}
			j++;
		 }
		
	}
	
	public void launchAddDeviceDialogViaProductUpdates_AccessControl_Add() throws Exception {
		String articleUpdateText=getListOfProductsInUpdatesPageViaJscript().get(0);
		String productToggleDrawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("accessToggleDrawer", articleUpdateText);	
		productUpdatePage.click(productToggleDrawer);
		Util.sleep(2000);
		Util.printInfo("Verify that Access control button exists for that product update" );
		productUpdatePage.verifyFieldExists("accessControlButton");
		productUpdatePage.click("accessControlButton");
		//click on add button and verify if the devices list pop up is shown		
		productUpdatePage.click("accessControlAddDev");
		Util.sleep(2000);
		//check if PII pop up appears
		if (productUpdatePage.isFieldVisible("acceptTheAgreement")){
			productUpdatePage.click("acceptTheAgreement");
		}
		
		assertTrueWithFlags("The Device table should be shown when the user clicks on Add button",(productUpdatePage.isFieldVisible("selectDevicesTable")
				&& productUpdatePage.isFieldVisible("selectDevicesHeaderMessage")
				&&  productUpdatePage.isFieldVisible("selectedDevicesCheckBoxCount")));
		
	}

	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
	
	
		
}
