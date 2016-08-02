package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;
import common.exception.MetadataException;

public class Test_verify_Devices_GlobalSettings extends CustomerPortalTestBase {
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public int count;
	public List<WebElement> allChekboxsInManageAccessWindow;
	private ArrayList<String> lstdeviceNames= null;
	

	public Test_verify_Devices_GlobalSettings() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_verify_Devices_GlobalSettings_UnderArrow() throws Exception {
		//This is used to override the user name and password given in the test properties
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			if (getEnvironment().equalsIgnoreCase("dev")) {
				USERNAME = testProperties.getConstant("USERNAME");				
				PASSWORD = testProperties.getConstant("PASSWORD");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");
				
			}
			loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);	
					
		}
		mainWindow.select();
		Util.sleep(5000);
		GoToProductUpdatesPage();
		verifyDevicesUnderArrow();
		logoutMyAutodeskPortal();
	}
	
	private void verifyDevicesUnderArrow() throws MetadataException {
		assertTrue("Delivery settings on the ProductUpdates page is present",productUpdatePage.verifyFieldExists("deliverySettingsOnManageDevices"));
		productUpdatePage.click("deliverySettingsOnManageDevices");
		Util.sleep(3000);
		assertTrue("Delivery settings popup is showing up ",productUpdatePage.verifyFieldExists("DeliverySettingsPopUp"));
		String devicesAdded=productUpdatePage.getValueFromGUI("numberOfDevicesAdded");
		devicesAdded=devicesAdded.substring(devicesAdded.indexOf('(')+1 ,devicesAdded.lastIndexOf(')'));
		if(!devicesAdded.equals("0")){
			Util.printInfo("Total number of Devices added is "+devicesAdded);
			assertTrue("Device Update arrow is present",productUpdatePage.verifyFieldExists("deviceUpdateArrow"));
			productUpdatePage.click("deviceUpdateArrow");
			Util.sleep(3000);
			//if the arrow is not open then click on it
			if (productUpdatePage.isFieldVisible("arrowUnderDeliverySetttings")) {
				productUpdatePage.click("clickArrowUnderDeliverySetttings");
			}

			assertTrue("Number of devices added",productUpdatePage.verifyFieldExists("devicesAdded"));
			productUpdatePage.click("deviceUpdateArrow");
			assertFalseWithFlags("Devices are not getting displayed.", productUpdatePage.isFieldVisible("devicesNotGettingDisplayed"));
		}
		else{
			Util.sleep(2000);
			productUpdatePage.click("addDevices");
			String deviceName=productUpdatePage.getValueFromGUI("deviceName");
			//table[@class='table-component tablesorter tablesorter-default']//tbody/tr[1]//input
			productUpdatePage.check("selectDevices");
			Util.sleep(2000);
			productUpdatePage.click("saveAddedDevices");
			//if the arrow is not open then click on it
			if (productUpdatePage.isFieldVisible("arrowUnderDeliverySetttings")) {
				productUpdatePage.click("clickArrowUnderDeliverySetttings");
			}
			String deviceNameUnderArrow=productUpdatePage.getValueFromGUI("deviceNameUnderArrow");
			assertEquals(deviceName, deviceNameUnderArrow);
			Util.printInfo("Clicking on the remove buttonof the product");
			productUpdatePage.click("removeAddedDevice");
			assertFalseWithFlags("Added deviced has been removed from the list.", productUpdatePage.isFieldVisible("deviceNameUnderArrow"));
			
		}

}

	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
}