package customerportal;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Util;

public class Test_Verify_Rename_Device_DEV extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;
	public String deviceName=null;
	public String description=null;
	public String lastUser=null;
	

	public Test_Verify_Rename_Device_DEV() throws Exception {
		super("Browser", getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_Rename_Devices_DEV() throws Exception {
		if (getEnvironment().equalsIgnoreCase("dev")) {
			USERNAME = testProperties.getConstant("USERNAME_DEV");				
			PASSWORD = testProperties.getConstant("PASSWORD_DEV");
		} else if (getEnvironment().equalsIgnoreCase("stg")) {
			USERNAME = testProperties.getConstant("USERNAME_STG");
			PASSWORD = testProperties.getConstant("PASSWORD_STG");

		}
		//for iterations the login page might not appear and it redirects directly prod updates page
		loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);
		mainWindow.select();
			Util.sleep(5000);
			// verify the delivery settings pop up on Devices page & Verify the
			// devices on manage devices page
			Util.printInfo("Verifying for the" + " Devices" + " Tab");
			productUpdatePage.click("manageDevicesTab");
			Util.sleep(3000);
			// Rename the device and verify
			if (productUpdatePage.isFieldVisible("acceptTermsInDevicePage")) {
				productUpdatePage.click("acceptTermsInDevicePage");
			}
			Util.sleep(3000);
			if(productUpdatePage.isFieldVisible("nOProdcutsOnDevicesPage")){
				EISTestBase.fail("No  DEVICES ARE SHOWN IN THE MANAGE DEVICES PAGE. HENCE FAILING THE TEST");
				
			}
			String DeviceNameb4Edit = productUpdatePage.getValueFromGUI("firtDeviceName");
			String DeviceDescriptionb4Edit = productUpdatePage.getValueFromGUI("firtDeviceName");
			String DeviceLastUserNameb4Edit = productUpdatePage.getValueFromGUI("firtDeviceName");
			
			//Editing the device name
			productUpdatePage.click("editFirstDevice");
			String DeviceName = "Device" + RandomStringUtils.randomNumeric(2);
			enterText(DeviceName);
			Util.printInfo("Edited the DeviceName to : " + DeviceName);
			
			String DeviceNameAfterEdit = productUpdatePage
					.getValueFromGUI("firtDeviceName");
			if (!(DeviceNameb4Edit.equalsIgnoreCase(DeviceNameAfterEdit))) {
				assertTrue("The device name has been renamed", true);
			}

			//Editing the Description
			productUpdatePage.click("editFirstDescription");
			String Description = "This is automation"
					+ RandomStringUtils.randomAlphabetic(2);
			enterText(Description);
			Util.printInfo("Edited the Description to : " + DeviceName);
			String DeviceDescriptionAfterEdit = productUpdatePage
					.getValueFromGUI("firtDeviceDescription");
			if (!(DeviceDescriptionb4Edit.equalsIgnoreCase(DeviceDescriptionAfterEdit))) {
				assertTrue("The Description  has been edited", true);
			}
			
			//Editing the LastUserName
			productUpdatePage.click("editFirstLastUser");
			String LastUser = "ADS" + "//" + RandomStringUtils.randomAlphabetic(2);
			enterText(LastUser);
			String DeviceLastUserNameAfterEdit = productUpdatePage
					.getValueFromGUI("firtDeviceLastUserName");
			Util.printInfo("Edited the LastUser to : " + DeviceName);
			if (!(DeviceLastUserNameb4Edit.equalsIgnoreCase(DeviceLastUserNameAfterEdit))){
				assertTrue("The LastUserName has been Renamed", true);
			}
		
			logoutMyAutodeskPortal();
			
			loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);
			mainWindow.select();Util.sleep(2000);
			goToManageDevicePage();
				Util.sleep(5000);
			deviceName=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesName", DeviceName);
			description=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("description", Description);
			lastUser=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("lastName", LastUser);
			assertTrueWithFlags("The edited name is present when the user logsout and login ",productUpdatePage.verifyFieldExists(deviceName));
			assertTrueWithFlags("The edited Description is present  when the user logsout and login ",productUpdatePage.verifyFieldExists(description));
			assertTrueWithFlags("The edited LastName is present  when the user logsout and loginx",productUpdatePage.verifyFieldExists(lastUser));
			
	}
	
	
		
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}

}
