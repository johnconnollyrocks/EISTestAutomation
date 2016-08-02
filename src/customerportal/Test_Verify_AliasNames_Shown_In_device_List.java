package customerportal;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.Util;

public class Test_Verify_AliasNames_Shown_In_device_List extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;
	public String deviceName=null;
	public String description=null;
	public String lastUser=null;
	

	public Test_Verify_AliasNames_Shown_In_device_List() throws Exception {
		super("Browser", getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_AliasNames_IN_Devices_Page() throws Exception {
		loginToPortalAndnavigateToToDevices();
			// verify the delivery settings pop up on Devices page & Verify the
			// devices on manage devices page
			Util.printInfo("Verifying for the" + " Devices" + " Tab");

			// Rename the device and verify
			if (productUpdatePage.isFieldVisible("acceptTermsInDevicePage")) {
				productUpdatePage.click("acceptTermsInDevicePage");
			}
			String DeviceNameb4Edit = productUpdatePage.getValueFromGUI("firtDeviceName");
			String DeviceDescriptionb4Edit = productUpdatePage.getValueFromGUI("firtDeviceName");
			String DeviceLastUserNameb4Edit = productUpdatePage.getValueFromGUI("firtDeviceName");
			
//			Validate the field limit
			productUpdatePage.click("editFirstDevice");
			ValidateFieldLimit("Devices");
//			Editing the device name
			productUpdatePage.click("editFirstDevice");
			String DeviceName = "AutomationDevice" + RandomStringUtils.randomNumeric(4);
			enterText(DeviceName);
			Util.printInfo("Edited the DeviceName to : " + DeviceName);
			
			String DeviceNameAfterEdit = productUpdatePage.getValueFromGUI("firtDeviceName");
			if (!(DeviceNameb4Edit.equalsIgnoreCase(DeviceNameAfterEdit))) {
				assertTrue("The device name has been renamed", true);
			}
			
//			Validate the field limit
			productUpdatePage.click("editFirstDescription");
			ValidateFieldLimit("Description");
			//Editing the Description
			productUpdatePage.click("editFirstDescription");
			String Description = "This is automation"+ RandomStringUtils.randomAlphabetic(4);
			enterText(Description);
			Util.printInfo("Edited the Description to : " + DeviceName);
			String DeviceDescriptionAfterEdit = productUpdatePage
					.getValueFromGUI("firtDeviceDescription");
			if (!(DeviceDescriptionb4Edit.equalsIgnoreCase(DeviceDescriptionAfterEdit))) {
				assertTrue("The Description  has been edited", true);
			}
			
//			Validate the field limit
			productUpdatePage.click("editFirstLastUser");
			ValidateFieldLimit("LastUser");
			//Editing the LastUserName
			productUpdatePage.click("editFirstLastUser");
			String LastUser = "AutomatinoADS" + "//" + RandomStringUtils.randomAlphabetic(4);
			enterText(LastUser);
			String DeviceLastUserNameAfterEdit = productUpdatePage.getValueFromGUI("firtDeviceLastUserName");
			Util.printInfo("Edited the LastUser to : " + DeviceName);
			if (!(DeviceLastUserNameb4Edit.equalsIgnoreCase(DeviceLastUserNameAfterEdit))){
				assertTrue("The LastUserName has been Renamed", true);
			}
			
			logoutMyAutodeskPortal();
			loginToPortalAndnavigateToToDevices();
			/*LoginAndGoToManageDevicesPage();*/			
			deviceName=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesName", DeviceName);
			description=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("description", Description);
			lastUser=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("lastName", LastUser);
			assertTrueWithFlags("The edited name is present when the user logsout and login ",productUpdatePage.verifyFieldExists(deviceName));
			assertTrueWithFlags("The edited Description is present  when the user logsout and login ",productUpdatePage.verifyFieldExists(description));
			assertTrueWithFlags("The edited LastName is present  when the user logsout and loginx",productUpdatePage.verifyFieldExists(lastUser));
			
			Util.printInfo("Verifying whether the alias names are  seen in the device list when the user accepts PII after declining it.");
			Util.printInfo("Clicking on setting button on Devices page.");
			productUpdatePage.click("devicesSettingButton");
			productUpdatePage.waitForFieldVisible("personalIdentifiableLink", 10);
			Util.printInfo("Clicking on 'Personally Identifiable Information Agreement' link on Delivery setting popup page");
			productUpdatePage.click("personalIdentifiableLink");
			productUpdatePage.waitForFieldVisible("iDontAcceptLinkInPIIPopUP", 10);
			Util.printInfo("Clicking on 'I don't accept' link on Personally Identifiable Information popup page");
			productUpdatePage.click("iDontAcceptLinkInPIIPopUP");
			productUpdatePage.waitForFieldVisible("closeAndGoToPriorPageLink", 10);
			Util.printInfo("Clicking on 'Close, Go to Prior Page' link on Personally Identifiable Information popup page");
			productUpdatePage.click("closeAndGoToPriorPageLink");
			productUpdatePage.waitForFieldVisible("productAndServicesPageHeader", 20);
			assertTrueWithFlags("Sucessfully navigated to 'All Products and Services' page after declining the PII",productUpdatePage.checkFieldExistence("productAndServicesPageHeader"));
			productUpdatePage.click("manageDevicesTab");
			productUpdatePage.waitForFieldVisible("iAcceptLinkInPIIPopUP", 20);
			assertTrueWithFlags("Personally Identifiable Information popup displayed after navigating to Devices page",productUpdatePage.checkIfElementExistsInPage("iAcceptLinkInPIIPopUP", 10));
			Util.printInfo("Clicking 'I accept on' Personally Identifiable Information popup page ");
			Util.sleep(1000);
			productUpdatePage.click("iAcceptLinkInPIIPopUP");
			Util.sleep(1000);
			Util.printInfo("Verifying whether the alias names are  seen in the device list when the user accepts PII after declining it.");
			deviceName=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesName", DeviceName);
			description=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("description", Description);
			lastUser=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("lastName", LastUser);
			System.out.println(productUpdatePage.getFieldLocators(deviceName));
			System.out.println(productUpdatePage.getFieldLocators(description));
			System.out.println(productUpdatePage.getFieldLocators(lastUser));
			assertTrueWithFlags("The alias name is present when the user accepts PII after declining it ",productUpdatePage.verifyFieldExists(deviceName));
			assertTrueWithFlags("The alias Description is present when the user accepts PII after declining it",productUpdatePage.verifyFieldExists(description));
			assertTrueWithFlags("The alias LastName is present when the user accepts PII after declining it",productUpdatePage.verifyFieldExists(lastUser));
	}
	
	public void loginToPortalAndnavigateToToDevices() throws Exception{
		if (getEnvironment().equalsIgnoreCase("dev")) {
			USERNAME = testProperties.getConstant("USERNAME_DEV");				
			PASSWORD = testProperties.getConstant("PASSWORD_DEV");
		} else if (getEnvironment().equalsIgnoreCase("stg")) {
			USERNAME = testProperties.getConstant("USERNAME_STG");
			PASSWORD = testProperties.getConstant("PASSWORD_STG");

		}
		//for iterations the login page might not appear and it redirects directly prod updates page
		loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);	
		/*LoginAndGoToManageDevicesPage();*/
		goToManageDevicePage();
			Util.sleep(5000);
	
	}
	public void ValidateFieldLimit(String fieldNameToValidate){
		String textMoreThan45Char="ajshuiedjrnskeodensolkwnstksnakedirjsnskodnsisnsis";
		switch(fieldNameToValidate.toUpperCase()){
		
			case "DEVICES":{
				Util.printInfo("Validating Devices field Limt is limited to 45");
				Util.printInfo("sending keys more that 45 charactes to Device field");
				enterText(textMoreThan45Char);
				String DeviceNameAfterEdit = productUpdatePage.getValueFromGUI("firtDeviceName");
				if (DeviceNameAfterEdit.length()>45) {
					assertTrue("The device field should not accepted characters more that 45", false);
				}else{
					assertTrue("The device field is not accepting characters more that 45", true);
				}
				break;
			}
			case "DESCRIPTION":{
				Util.printInfo("Validating Description field Limt is limited to 45");
				Util.printInfo("sending keys more that 45 charactes to Description field");
				enterText(textMoreThan45Char);
				String DeviceNameAfterEdit = productUpdatePage.getValueFromGUI("firtDeviceDescription");
				if (DeviceNameAfterEdit.length()>45) {
					assertTrue("The Description field should not accepted characters more that 45", false);
				}else{
					assertTrue("The Description field is not accepting characters more that 45", true);
				}
				break;
			}
			case "LASTUSER":{
				Util.printInfo("Validating LastUser field Limt is limited to 45");
				Util.printInfo("sending keys more that 45 charactes to LastUser field");
				enterText(textMoreThan45Char);
				String DeviceNameAfterEdit = productUpdatePage.getValueFromGUI("firtDeviceLastUserName");
				if (DeviceNameAfterEdit.length()>45) {
					assertTrue("The LastUser field should not accepted characters more that 45", false);
				}else{
					assertTrue("The LastUser field is not accepting characters more that 45", true);
				}
				break;
			}
			
		}
		
	}	
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}

}
