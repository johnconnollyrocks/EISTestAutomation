package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Product;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_Deliverysettings extends CustomerPortalTestBase {
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public int count;
	public List<WebElement> allChekboxsInManageAccessWindow;
	private ArrayList<String> lstdeviceNames= null;
	

	public Test_Verify_Deliverysettings() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_Verify_Deliverysetting() throws Exception {
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
		verifyDeliverysettings();
		logoutMyAutodeskPortal();
	}
	
	private void verifyDeliverysettings() throws MetadataException {
		Util.printInfo("Verifying for the" +" Devices"+" Tab");
		assertTrue("Devices Tab is present",productUpdatePage.verifyFieldExists("manageDevicesTab"));
		Util.printInfo("Clicking on the " +" Devices"+" Tab");
		productUpdatePage.click("manageDevicesTab");
		assertTrue("Page title Manage Devices is present",productUpdatePage.verifyFieldExists("manageDevicesTab"));
		String remndrloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("reminderMore", "More");
		
		/*Verify the US US1594 Ability to show persistant banner about "PII"*/		
		productUpdatePage.verify();
		//click on more link btn		
		assertTrueWithFlags("The More button should be displayed and text should be wrapped inside in the Remainder block" , productUpdatePage.isFieldVisible(remndrloc));		
		productUpdatePage.click(remndrloc);
		//now check less btn is displayed and also the text is not wrapped
		remndrloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("reminderMore", "Less");
		assertTrueWithFlags("The More button should be displayed and text should be wrapped inside in the Remainder block" , productUpdatePage.isFieldVisible(remndrloc));
		
		assertTrue("Page title Manage Devices is present",productUpdatePage.verifyFieldExists("reminderMessage"));
		assertTrue("Page title Manage Devices is present",productUpdatePage.verifyFieldExists("manageDevicesPageTitle"));
		assertTrue("Delivery settings on the manage devices page is present",productUpdatePage.verifyFieldExists("deliverySettingsOnManageDevices"));
		productUpdatePage.click("deliverySettingsOnManageDevices");
		Util.sleep(3000);
		// if you see personally identifiable info then accept it and move on
		if (productUpdatePage.isFieldVisible("acceptTermsInDevicePage")){
			productUpdatePage.click("acceptTermsInDevicePage");
		}
		assertTrue("Delivery settings popup is showing up ",productUpdatePage.verifyFieldExists("DeliverySettingsPopUp"));
		assertTrue("Download Acces Control text is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("downloadAccessControl"));
		assertTrue("Privacy Information text is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("privacyInformation"));
		assertTrue("All the radio buttons are present on the delivery settings popup ",productUpdatePage.verifyFieldExists("deliverySettingsRadioButtons"));
		assertTrue("default access for updates tooltip Icon is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("defaultAccessToolTipIcon"));
	//	assertTrue("All the devices radio button is checked ",productUpdatePage.isChecked("allDevicesRadioButton"));
		assertTrue("Add button is Present ",productUpdatePage.verifyFieldExists("addDevices"));
		String devicesAdded=productUpdatePage.getValueFromGUI("numberOfDevicesAdded");
		productUpdatePage.click("addDevices");
		assertTrue("Selected Devices text is Present ",productUpdatePage.verifyFieldExists("selectedDevices"));
		assertTrue("Selected Devices Table is Present ",productUpdatePage.verifyFieldExists("selectDevicesTable"));
		allChekboxsInManageAccessWindow=productUpdatePage.getMultipleWebElementsfromField("selectedDevicesCheckBoxCount");
		count=getCountOfCheckboxChecked();
		Util.printInfo(" Total Devices selected =" +count);
		 devicesAdded=devicesAdded.substring(devicesAdded.indexOf('(')+1 ,devicesAdded.lastIndexOf(')'));
		Util.printInfo("Total number of Devices added is "+devicesAdded);
		assertEquals(String.valueOf(count),devicesAdded);
		
		/*if(productUpdatePage.verifyFieldExists("deviceName")){
			String devicesNameLst=productUpdatePage.getValueFromGUI("deviceName");
			List<WebElement> totalSerialNumberandProductKey
			for(WebElement eachDevice:devicesNameLst){
				
			}
			
		}

		String selectedDevicesCheckBoxCount=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectedDevicesCheckBoxCount");
		for(int i=0;i<=selectedDevicesCheckBoxCount.length();i++){
			if(productUpdatePage.isChecked("selectedDevicesCheckBox")){
				
			}
		}*/
		
			
		
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

