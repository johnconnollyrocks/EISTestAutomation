package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;

public class Test_Verify_AccessControl_AddDevice extends CustomerPortalTestBase {
	public List<WebElement> numberDevicesList;
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public int count;
	public List<WebElement> allChekboxsInManageAccessWindow;
	

	public Test_Verify_AccessControl_AddDevice() throws IOException {
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
		
		productUpdatePage.click("openToggleDrawer");
		Util.printInfo("Verify if the Access control button is displayed");
		productUpdatePage.verifyFieldVisible("accessControlButton");
		Util.printInfo("Clicking on the Access Control button");
		productUpdatePage.click("accessControlButton");
		//check if the access control has menu
		Util.printInfo("Verify that the drop down is displayed when the user clicked on Access Control button");
		Util.sleep(2000);
		productUpdatePage.waitForFieldVisible("accessControlElements", 2000);
		assertTrue("The drop down menu under Access control button should be visible when clicked on it", productUpdatePage.isFieldVisible("accessControlDropDownMenu"));
		assertTrue("Add button is Present ",productUpdatePage.verifyFieldExists("addButtonOfAccessControl"));
		String devicesAdded=productUpdatePage.getValueFromGUI("numberOfDevicesAddedUnderAccessControl");
		
		
	//verifying the Accept agreement message when the user logs in and PII banner should open if the user has not accepted it before	
		// Add the devices if they are not added
		addDeviceUnderAccessControl();
		//Decline the agreement
		declineTheAgreement();
		Util.sleep(2000);
		productUpdatePage.click("accessControlButton");
		productUpdatePage.click("clickTheArrow");
		assertTrue("Agreement Icon is getting displayed ",productUpdatePage.verifyFieldExists("agreementIcon"));
		String ActAgreementDesc=productUpdatePage.getValueFromGUI("agreementMessage");
		assertEquals(ActAgreementDesc, testProperties.getConstant("Agreement_MESSAGE"));
		productUpdatePage.click("acceptTermsInDevicePage");
		
	// verifying the total number of devices showing under the accessControl dropdown against the number checkboxes checked in the devices table 	
		productUpdatePage.click("accessControlButton");
		productUpdatePage.click("addButtonOfAccessControl");
		allChekboxsInManageAccessWindow=productUpdatePage.getMultipleWebElementsfromField("selectedDevicesCheckBoxCount");
		count=getCountOfCheckboxChecked();
		Util.printInfo(" Total Devices selected =" +count);
		
		devicesAdded=devicesAdded.substring(devicesAdded.indexOf('(')+1 ,devicesAdded.lastIndexOf(')'));
		Util.printInfo("Total number of Devices added is "+devicesAdded);
		assertEquals(String.valueOf(count),devicesAdded);
		logoutMyAutodeskPortal();
	}
	
	public int getCountOfCheckboxChecked(){
		int count;
		count=0;
		for(int i=0;i<allChekboxsInManageAccessWindow.size();i++){
			if (allChekboxsInManageAccessWindow.get(i).isSelected()){
				count=count+1;
			}
		}
		return count;
	}

	
	
	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
}