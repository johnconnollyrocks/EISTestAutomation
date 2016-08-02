package customerportal;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import common.Util;

public class Test_Verify_AssignIndvUpd_Part3 extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;
	String indvPreferenceSelected=null;


	public Test_Verify_AssignIndvUpd_Part3() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}


	@Test
	public void testAssignIndividualUpdates_Part3() throws Exception {	
		System.out.println("Does not accept any Credentials from Jenkins for this test. As this is mostly driven based on User accounts given in test data");
		
		Util.printInfo("================================TC477 & TC476=============================================");
		USERNAME=getUserCredentials("TC476")[0];
		String CMUserName=USERNAME;
		PASSWORD=getUserCredentials("TC476")[1];
		LoginAndGoProductUpdatesPage(USERNAME,PASSWORD);

		String productUpdName=getSpecificTestDataBasedOnEnv("TC_476_UPDATENAMEID");		
		Util.printInfo("Choosing a specific product update: "+productUpdName);
		String productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", productUpdName);
		
		//delete all the devices which exists before
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("Clicking on Product access control button");
		Util.sleep(2000);
		productUpdatePage.click("accessControlButton");
		//NOW HERE ASSUMING THAT THIS INDV preference is not touched or done any updates in the backend. 
		productUpdatePage.isFieldPresent(productDrawerLoc);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());

		Util.printInfo("Check if any devices are selected before if so then delete them");
		//if the arrow doesnt exist then selected should show 0
		if (!productUpdatePage.isFieldVisible("myDevicesArrowBtnInAccessControl")){
			Util.printInfo("No devices have been selected hence the number should be 0");			
		}	else{
			//remove all the devices
			productUpdatePage.click("myDevicesArrowBtnInAccessControl");
			//Although the token is given in xpath, that can be ignored here to pull the list of devices
		/*	List<WebElement> myDeviceList=productUpdatePage.getMultipleWebElementsfromField("removeDevice");
			List<WebElement> deviceListElements = productUpdatePage.getMultipleWebElementsfromField("removeDeviceInAccessControlMenuElements");
 			List<WebElement> deleteIconInDeviceList=productUpdatePage.getMultipleWebElementsfromField("deleteIconAllDevicesUnderAccessControl");
 		*/	
			/*for(int i=0;i<myDeviceList.size();i++){
				mouseHover(myDeviceList.get(i));
				productUpdatePage.clickUsingInputDevicesMouseClick(deviceListElements.get(i));
				Util.sleep(500);
				productUpdatePage.clickUsingLowLevelActions(deleteIconInDeviceList.get(i));
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", deleteIconInDeviceList.get(i));
				deleteIconInDeviceList.get(i).click();				
				Util.sleep(1000);				
			}*/
 			deleteDevicesUnderAccessControlMenu();
			Util.sleep(1000);
		}
		
		Util.printInfo("Verify uncheck any existing device ,add couple of devices and check the behaviour of individual preferences");
		//NOW add some more devices by unchecking the checkbox device id1 and select device id2 , deviceid3		
		productUpdatePage.click("accessControlAddDev");		
		Util.sleep(1000);
		//select any device		
		String newDeviceID2=getSpecificTestDataBasedOnEnv("TC_476_DEVICEID2");
		Util.printInfo("Checking the Device whose is id: "+newDeviceID2);
		String selectDeviceCheckboxloc2=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectADeviceCheckbox", newDeviceID2);
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc2);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.check(selectDeviceCheckboxloc2);	//select 2nd  device
		String newDeviceID3=getSpecificTestDataBasedOnEnv("TC_476_DEVICEID3");
		Util.printInfo("Checking the Device whose is id: "+newDeviceID3);		
		String selectDeviceCheckboxloc3=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectADeviceCheckbox", newDeviceID3);
		productUpdatePage.check(selectDeviceCheckboxloc3);	//select 3rd  device

		//PULL the device names which are added in select Devices dialog
		//NOTE:NO checked attribute is found when the checkboxes are checked in device modal dialog
		/*String[] checkedDevicesUnderSelectModalDialog= productUpdatePage.getMultipleTextValuesfromField("getcheckedDevicesInSelectDeviceModalDialog");*/
		String[] checkedDevicesUnderSelectModalDialog= getDevicesNamesCheckedUnderSelectModalDialog();
		//apply the changes
		String selectBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "select");		
		productUpdatePage.click(selectBtnloc);
		Util.printInfo("Click on Select buttton Select modal dialog");		
		Util.sleep(1000);	//need to wait here
		productUpdatePage.click("clickTheArrow");

		//Pull list of devices see that only the newly added ones should be found
		//always pull from first update
		String[] devicesUnderAccessControl=productUpdatePage.getMultipleTextValuesfromField("devicelst");		
		assertTrueWithFlags("The list of devices checked in Select Device modal dialog should only be seen in Only selected device list of the update", Arrays.equals(checkedDevicesUnderSelectModalDialog, devicesUnderAccessControl));
		
		//Now uncheck tehm by clicking on add button and check that the devices are gone
		productUpdatePage.click("accessControlAddDev");		
		Util.sleep(1000);		
		Util.printInfo("Unchecking the Device2, Device3");
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc2);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.uncheck(selectDeviceCheckboxloc2);	//select 2nd  device
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc3);
		productUpdatePage.uncheck(selectDeviceCheckboxloc3);	//select 2nd  device
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.click(selectBtnloc);
		Util.sleep(1000);	//need to wait here		
		//Now verify that there are no devices in access control :Only selected
		indvPreferenceSelected=getIndividualUpdatesPrefValueForSpecificUpdate();
		String selectedDevices=productUpdatePage.getValueFromGUI("numberOfDevicesAddedUnderAccessControl");
		int iPos=selectedDevices.lastIndexOf("(");
		//parse the selected Devices
		selectedDevices=selectedDevices.substring(iPos+1,selectedDevices.length()).replace(")", "");
		Util.printInfo("No devices have been selected hence the number should be 0");
		Util.printInfo("Verify that Only Selected is still selected under Access control menu, but the no of devices is 0");
		assertEquals(selectedDevices, "0");
		assertTrueWithFlags("The Individual device preferences:Only selected should still selected when removed all the devices",indvPreferenceSelected.equalsIgnoreCase("Only Selected"));		
		Util.sleep(1000);	//need to wait here
		
		Util.printInfo("Verifying that when unchecked few devices and select a device in Select devices dialog, the unchecked devices should be removed and checked device should be seen under Access control menu content");
		productUpdatePage.click("accessControlAddDev");		
		Util.sleep(1000);
		//HERE ADD SOME DEVICES AND AGAIN Open device modal dialog, and uncheck few devices and select only one device
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc2);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.check(selectDeviceCheckboxloc2);	//select 2nd  device
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc3);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.check(selectDeviceCheckboxloc3);	//select 2nd  device
		productUpdatePage.click(selectBtnloc);
		Util.sleep(1000);	//need to wait here
		productUpdatePage.click("accessControlAddDev");		
		Util.sleep(1000);
		
		Util.printInfo("Unchecking the Device2, Device3");
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc2);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.uncheck(selectDeviceCheckboxloc2);	//select 2nd  device
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc3);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.uncheck(selectDeviceCheckboxloc3);	//select 2nd  device
		
		//Also add another device here
		String newDeviceID1=getSpecificTestDataBasedOnEnv("TC_476_DEVICEID1");
		String selectDeviceCheckboxloc1=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectADeviceCheckbox", newDeviceID1);
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc1);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		productUpdatePage.check(selectDeviceCheckboxloc1);	//select 1st  device		
		checkedDevicesUnderSelectModalDialog= getDevicesNamesCheckedUnderSelectModalDialog();
		Util.printInfo("Click on Select button Select modal dialog with two unchecked and one checked");
		productUpdatePage.click(selectBtnloc);
		Util.sleep(1000);	//need to wait here

		//NOW add some more devices by unchecking the checkbox device id1 and select device id2 , deviceid3
		devicesUnderAccessControl=productUpdatePage.getMultipleTextValuesfromField("devicelst");
		assertTrueWithFlags("The list of multiple individual device preferences should be same as selected in Select devices dialog", Arrays.equals(checkedDevicesUnderSelectModalDialog, devicesUnderAccessControl));		
		/*NOTE: TC478 cannot be AUTOMATED as this test requires the Device to be changed at customer side*/
		

	}
		
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}

}
