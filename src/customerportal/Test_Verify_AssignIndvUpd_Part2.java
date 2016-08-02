package customerportal;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

 
public class Test_Verify_AssignIndvUpd_Part2 extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;
	
	
	public Test_Verify_AssignIndvUpd_Part2() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	@Test
	public void testAssignIndividualUpdates_Part2() throws Exception {
		System.out.println("Does not accept any Credentials from Jenkins for this test. As this is mostly driven based on User accounts given in test data");
		
		//TC 474
		Util.printInfo("================================TC474=============================================");
		
		USERNAME=getUserCredentials("TC474")[0];
		String CMUserName=USERNAME;
		PASSWORD=getUserCredentials("TC474")[1];
		LoginAndGoProductUpdatesPage(USERNAME,PASSWORD);
		
		String productUpdName=getSpecificTestDataBasedOnEnv("TC_474_UPDATENAMEID");		
		Util.printInfo("Choosing a specific product update: "+productUpdName);
		String productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", productUpdName);		
		
		Util.printInfo("Verify that one update can be set to specific Device");
		//delete all the devices which exists before
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("Clicking on Product access control button");
		Util.sleep(2000);
		productUpdatePage.click("accessControlButton");//click on specific prod
		Util.sleep(4000); //need to wait
		//click on Add button and check if any device
		//check if only selected device option is selected		
		Util.printInfo("Verify if any devices exists before, if so, delete them");
		boolean selectedDeviceExists=getIndividualUpdatesPrefValueForSpecificUpdate().toLowerCase().contains("selected");
		if(selectedDeviceExists){
			//ie. there are already devices selected delete all of them then add new ones4
			if(productUpdatePage.isFieldVisible("myDevicesArrowBtnInAccessControl")){				
				//expand only if the devices are not showing up
				if (!productUpdatePage.isFieldVisible("listOfAllDevicesUnderAccessControl")){					
					productUpdatePage.click("myDevicesArrowBtnInAccessControl");				
				}
				//Although the token is given in xpath, that can be ignored here to pull the list of devices
				//do mouse over				
				List<WebElement> myDeviceList=productUpdatePage.getMultipleWebElementsfromField("listOfAllDevicesUnderAccessControl");				
				List<WebElement> deleteIconInDeviceList=productUpdatePage.getMultipleWebElementsfromField("deleteIconAllDevicesUnderAccessControl");
				for(int i=0;i<myDeviceList.size();i++){
					mouseHover(myDeviceList.get(i));
					deleteIconInDeviceList.get(i).click();					
					Util.sleep(1000);
				}			
				//need static delay 
				Util.sleep(800);
			}
		}
		//now click on add device
		productUpdatePage.click("accessControlAddDev");
		Util.sleep(1000);
		//select any device
		String newDeviceID=getSpecificTestDataBasedOnEnv("TC_474_DEVICEID1");
		String selectDeviceCheckboxloc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectADeviceCheckbox", newDeviceID);
		productUpdatePage.isFieldPresent(selectDeviceCheckboxloc);
		productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
		Util.sleep(600);
		boolean alreayChecked=true;
		if (!productUpdatePage.isChecked(selectDeviceCheckboxloc)) {		
			productUpdatePage.check(selectDeviceCheckboxloc);	//select the device	
			 alreayChecked=false;
		}
		Util.printInfo("The Device found to be selected before");
		String deviceNameInSelectModalLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("deviceLabelInSelectDeviceModal", newDeviceID);
		String selectedDeviceLabelinSelectModal=productUpdatePage.getValueFromGUI(deviceNameInSelectModalLoc);
		if (alreayChecked) {
			
			String cancelBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "cancel");
			productUpdatePage.click(cancelBtnloc);
		}else {
			String selectBtnloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("buttonInSelectDeviceDialog", "select");
			productUpdatePage.click(selectBtnloc);
			
		}
		//get the list of selected device name
		Util.sleep(1000);	//need to wait here
		
		String onlySelectedDevLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("getDeviceNameUnderOnlySelected", newDeviceID);
		String deviceLabelUnderAccessControl=productUpdatePage.getValueFromGUI(onlySelectedDevLoc);
		//ie. you got empty val then expand the arrow
		//check here if the only selected arrow is expanded
		
		if (deviceLabelUnderAccessControl.isEmpty()) {
			productUpdatePage.click("clickTheArrow");	//expand it
			Util.sleep(8000);
			 deviceLabelUnderAccessControl=productUpdatePage.getValueFromGUI(onlySelectedDevLoc);
		}
		/*productUpdatePage.click(fieldName)*/	
		
		assertTrueWithFlags("The selected device should be visible under Only Selected device list", deviceLabelUnderAccessControl.equalsIgnoreCase(selectedDeviceLabelinSelectModal));		 
		logoutMyAutodeskPortal();		
		
		//Log out and login check if the prod updates exists
		Util.printInfo("Verify that Access control settings persists when CM: " +USERNAME+" logs out and login back");
		
		LoginAndGoProductUpdatesPage(USERNAME,PASSWORD);
		productDrawerLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", productUpdName);
		productUpdatePage.click(productDrawerLoc);//click on specific prod
		Util.printInfo("Clicking on Product access control button");
		Util.sleep(2000);
		productUpdatePage.click("accessControlButton");//click on specific prod
		Util.sleep(4000); //need to wait
		selectedDeviceExists=getIndividualUpdatesPrefValueForSpecificUpdate().toLowerCase().contains("selected");
		if(selectedDeviceExists){
			//ie. there are already devices selected delete all of them then add new ones4
			if(productUpdatePage.isFieldVisible("myDevicesArrowBtnInAccessControl")){				
				//expand only if the devices are not showing up
				if (!productUpdatePage.isFieldVisible("listOfAllDevicesUnderAccessControl")){					
					productUpdatePage.click("myDevicesArrowBtnInAccessControl");		//expand it and check if exists				
				}				
				onlySelectedDevLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("getDeviceNameUnderOnlySelected", newDeviceID);
				deviceLabelUnderAccessControl=productUpdatePage.getValueFromGUI(onlySelectedDevLoc);
				assertTrueWithFlags("The selected device should be visible under Only Selected device list when the user logout and log in back", deviceLabelUnderAccessControl.equalsIgnoreCase(selectedDeviceLabelinSelectModal));				
			}
		}
		else{
			EISTestBase.fail("The selected device individual preferences are not found when the user logged out and log in");
		}
		
	}
		
	@After
	public void tearDown() throws Exception {

		driver.quit();

		finish();
}
	
}
