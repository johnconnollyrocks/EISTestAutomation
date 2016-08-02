package customerportal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import common.Util;
/**
 * @USER STORY: US 1411
 * @author t_marus
 * NOTE: TC594 and TC616 cannot be automated, as one involves localization and later ipad
 */

public class Test_Verify_DeviceDisplay_SC extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;
	private String prodUpdateName   =  null;
	private String newDeviceID=null;
	public Test_Verify_DeviceDisplay_SC() throws Exception {		
		super("Browser",getAppBrowser());

	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
		prodUpdateName=getSpecificTestDataBasedOnEnv("DEF88_PRODUPDNAME");

	}
	@Test
	public void testSearchDevicesInAddDevicesOptionWindow() throws Exception {
		/*	if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{*/
		Util.printInfo("This test doesnt accept any user accounts from test");
		USERNAME=getUserCredentials("DEF88CM")[0];
		PASSWORD=getUserCredentials("DEF88CM")[1];
		loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);	

		/*}*/
		newDeviceID=getSpecificTestDataBasedOnEnv("DEF88SC_DEVICEID");
		mainWindow.select();
		Util.sleep(5000);
		GoToProductUpdatesPage();
		String SCUserName=getUserCredentials("DEF88CM")[0];

		Util.printInfo("Verify that SC: "+SCUserName+" should be able to see only the device under the Access control menu for which he/she is assigned to, not all the devices" +
				"but the total no of devices in Only selected should be number of devices for which is individual updates preferences is set");
		Util.printInfo("CM :"+USERNAME+"  is going to login and set the individual devices preferences for which SC is associated");
		Util.printInfo("Search for the Product update "+prodUpdateName+" in the update list");

		String productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", prodUpdateName);		

		//add the devices for which SC is associated 
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("Clicking on Product access control button");
		Util.sleep(2000);
		productUpdatePage.click("accessControlButton");//click on specific prod

		Util.printInfo("Clicking on Add button");
		productUpdatePage.click("accessControlAddDev");
		Util.sleep(1000);

		newDeviceID=getSpecificTestDataBasedOnEnv("DEF88SC_DEVICEID");
		String selectDeviceCheckboxloc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectADeviceCheckbox", newDeviceID);
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		Util.sleep(600);
		boolean alreadyChecked=true;
		if (!productUpdatePage.isChecked(selectDeviceCheckboxloc)) {		
			productUpdatePage.check(selectDeviceCheckboxloc);	//select the device	
			alreadyChecked=false;
		}
		//select all checkboxes i.e all devices.		
		int selectedDevCB=checkAllDevicesInSelectDevices(true);
		//if already checked then 
		String selectBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "select");
		// if select btn is enabled then click on it
		String classAttr=productUpdatePage.getAttribute(selectBtnloc, "class");
		if (!classAttr.contains("disabled")) {	
			productUpdatePage.click(selectBtnloc);
			Util.sleep(1000);	//to avoid race condition
		}else {	
			// some of the devices would already have been checked so click on cancel
			selectBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "cancel");
			productUpdatePage.click(selectBtnloc);	
			Util.sleep(1000); //to avoid race condition
		}	 
		//jsut click the arrow so that devices get updates correctly or events will be triggered
		if (!productUpdatePage.isFieldVisible("listOfAllDevicesUnderAccessControl")){					
			productUpdatePage.click("myDevicesArrowBtnInAccessControl");
			Util.sleep(1000); //to avoid race condition
		}
		Util.printInfo("The Device found to be selected before");


		//now log out and login with the SC account see that no of devices are correctly showing up.
		logoutMyAutodeskPortal();
		Util.printInfo("CM is Logging out"); 
		USERNAME=getUserCredentials("DEF88SC")[0];
		PASSWORD=getUserCredentials("DEF88SC")[1];
		Util.printInfo("Logging with the SC account: "+USERNAME+ " check that correct no. of devices are showing up for SC and should see SC assigned device");		
		loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);		
		goToManageDevicePage();

		//run through the devices and pull the device value
		String[] devicesInDevicesTab=productUpdatePage.getMultipleTextValuesfromField("onlyAllDevicesInManageDevices");
		ArrayList<String> devicesListInManageDevices=new ArrayList<>(Arrays.asList(devicesInDevicesTab));

		GoToProductUpdatesPage();
		productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", prodUpdateName);		

		//add the devices for which SC is associated 
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("Clicking on Product access control button");
		Util.sleep(2000);
		productUpdatePage.click("accessControlButton");//click on specific prod
		Util.sleep(1000);	
		// now check no of devices for product update ind. device preferneces
		String selectedDevices=productUpdatePage.getValueFromGUI("numberOfDevicesAddedUnderAccessControl");
		String iPos=null;
		//parse the selected Devices
		selectedDevices=selectedDevices.substring(selectedDevices.indexOf('(')+1 ,selectedDevices.lastIndexOf(')'));
	//	selectedDevices=selectedDevices.split("\\n")[0];
		int actDevices=Integer.valueOf(selectedDevices);
		Util.printInfo("Verify that the total no of devices under Only Selected Option in Access control menu matches with no of devices assinged for the prod update: "+prodUpdateName);
		assertTrueWithFlags("The total no of devices showing up for the SC should be total no of devices assigned for the prod update",selectedDevCB==actDevices );
		//now expand and see it should show only device which is assigned to SC
		if(productUpdatePage.isFieldVisible("myDevicesArrowBtnInAccessControl")){				
			//expand only if the devices are not showing up
			if (!productUpdatePage.isFieldVisible("listOfAllDevicesUnderAccessControl")){					
				productUpdatePage.click("myDevicesArrowBtnInAccessControl");

				//when you expand the arrow then no of devices used to show as 0. this was observed as defect.
				selectedDevices=productUpdatePage.getValueFromGUI("numberOfDevicesAddedUnderAccessControl");
				iPos=selectedDevices;
				//parse the selected Devices
				selectedDevices=selectedDevices.substring(selectedDevices.indexOf('(')+1 ,selectedDevices.lastIndexOf(')'));
				selectedDevices=selectedDevices.split("\\n")[0];
				int noOFDeviceAfterexpandingarrow=Integer.valueOf(selectedDevices);
				assertTrueWithFlags("The no of devices under Only selected lable is showing right no of devices when user clicked on arrow", noOFDeviceAfterexpandingarrow==actDevices);

			}
		}
		//get the list of devices showing under SC user account for that prod update

		String[] lsactualDevicesForSC=productUpdatePage.getMultipleTextValuesfromField("devicesLabelsUnderAccessControlMenu");
		ArrayList<String> devicesShownForSC=new ArrayList<>(Arrays.asList(lsactualDevicesForSC));
		assertEquals(devicesShownForSC, devicesListInManageDevices);		

		//log out and login with CM and remove the devices which is assigned for SC
		logoutMyAutodeskPortal();

		Util.printInfo("CM is logging in"); 
		USERNAME=getUserCredentials("DEF88CM")[0];
		PASSWORD=getUserCredentials("DEF88CM")[1];
		Util.printInfo("Logging with the CM account: "+USERNAME+ " remove the device which is assinged for SC and so that SC should not see his assgined device but should see total no of devies for indv device preferences");		
		loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);		
		GoToProductUpdatesPage();
		Util.printInfo("Search for the Product update "+prodUpdateName+" in the update list");

		productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", prodUpdateName);		

		//add the devices for which SC is associated 
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("Clicking on Product access control button");
		Util.sleep(2000);
		productUpdatePage.click("accessControlButton");//click on specific prod

		Util.printInfo("Clicking on Add button");
		productUpdatePage.click("accessControlAddDev");
		Util.sleep(1000);		
		uncheckDevice();
		int noOfDevicesChecked=checkAllDevicesInSelectDevices(false);
		selectBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "select");
		// if select btn is enabled then click on it
		productUpdatePage.waitForFieldEnabled(selectBtnloc, 3);
		productUpdatePage.click(selectBtnloc);
		Util.sleep(2000);	//to avoid race condition
		//logout and login with SC and check that total no of devices are showing up
		logoutMyAutodeskPortal();
		Util.printInfo("CM is Logging out");
		Util.printInfo("SC is logging in"); 
		USERNAME=getUserCredentials("DEF88SC")[0];
		PASSWORD=getUserCredentials("DEF88SC")[1];
		Util.printInfo("Logging with the SC account: "+USERNAME+ " check that correct no. of devices are showing up for SC when SC assigned device is removed");		
		loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);		
		GoToProductUpdatesPage();
		productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", prodUpdateName);		

		//add the devices for which SC is associated 
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("Clicking on Product access control button");
		Util.sleep(2000);
		productUpdatePage.click("accessControlButton");//click on specific prod
		Util.sleep(1000);	
		// now check no of devices for product update ind. device preferneces
		selectedDevices=productUpdatePage.getValueFromGUI("numberOfDevicesAddedUnderAccessControl");
				//parse the selected Devices
		selectedDevices=selectedDevices.substring(selectedDevices.indexOf('(')+1 ,selectedDevices.lastIndexOf(')'));
		selectedDevices=selectedDevices.split("\\n")[0];
		actDevices=Integer.valueOf(selectedDevices);
		Util.printInfo("Verify that the total no of devices under Only Selected Option in Access control menu matches with no of devices assinged for the prod update: "+prodUpdateName);
		assertTrueWithFlags("The total no of devices showing up for the SC account should be total no of devices assigned for the prod update though when SC assigned device is removed",noOfDevicesChecked==actDevices );
		//Also verify that arrow btn shldnt exist and 
		assertFalseWithFlags("The Arrow btn for individual devices preferences should not be seen when SC assigned device is removed",productUpdatePage.isFieldVisible("myDevicesArrowBtnInAccessControl"));
		logoutMyAutodeskPortal();

	}

	public int checkAllDevicesInSelectDevices(boolean checkDevice) throws Exception {
		//check all the devices if not checked
		int retDevices=0;
		productUpdatePage.isFieldVisible("selectDeviceAllCheckboxes");
		String checkAllDevices=null;
		if (checkDevice) {
			
			checkAllDevices="var i = 0; $('input[type=\"checkbox\"][name=\"device\"]').each(function(){"+		 
					"if (!this.checked){"+
					"this.click();" +
					"}" +
					"i++;}); return i;";
			
		}else {
			checkAllDevices="var i = 0; $('input[type=\"checkbox\"][name=\"device\"]').each(function(){"+		 
					"if ($(this).length && this.checked){"+					
					"i++; }}); return i;";
			
		}
		Object objRetVal=((JavascriptExecutor)(driver)).executeScript(checkAllDevices);
		retDevices=Integer.valueOf(objRetVal.toString());
		Util.sleep(2000);
		/*productUpdatePage.clickUsingInputDevicesMouseClick(productUpdatePage.getCurrentWebElement());*/
		return retDevices;
	}
	public void uncheckDevice() throws Exception {
		String uncheckDeviceJscript="$('input[type=\"checkbox\"][name=\"device\"]').each(function(){\r\n" + 
				"   if ($(this).attr('value')==\""+newDeviceID+"\"){ \r\n" + 
				"       $(this).click();\r\n" + 
				"        }\r\n" + 
				"});";
		((JavascriptExecutor)(driver)).executeScript(uncheckDeviceJscript);
	}
	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}



}
