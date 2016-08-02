package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ProductUpdates_IndPref_Setting_N_CM_SC_Sett extends CustomerPortalTestBase{
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String[] productsName=null;
	public String selectedOption=null;
	public String alreadyselectedOption=null;
	public String changedProductName=null;
	public String checkForProductXpath=null;
	
	public Test_Verify_ProductUpdates_IndPref_Setting_N_CM_SC_Sett() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	/**
	 * ContainsTC:547, 548 test cases
	 */
	@Test
	public void Test_Verify_ProductUpdates_CM_SC_Settings() throws Exception {
		
		//TC created for DEV suite which will check individual settings are not overwrite by global settings and SC can see changes done by CM and vice versa
		Util.printInfo("This test does not accept any user accounts from Jenkins");
		Util.printInfo("========================================TC548==========================================");
		//Login with CM user and get the products from Product Update Page.
		Util.printInfo("Loging to Portal as CM and to validate Individual Settings are not effected by global settings");
		LoginAndGoProductUpdatesPage("CM");
		Util.sleep(2000);		
		Util.printInfo("Getting all the Products in ProductUpdate Page");
		productsName=productUpdatePage.getMultipleTextValuesfromField("productUpdateListNames");
		validateIndividualSettingLogic();
		Util.printInfo("LogingOff from Portal as CM and Loging back as SC");
		Util.printInfo("=======================================================================================");
		// Login as SC and change the settings
		
		Util.printInfo("========================================TC547==========================================");
		logoutMyAutodeskPortal();
		LoginAndGoProductUpdatesPage("SC");
		Util.printInfo("Finding for the product which was available in CM");
		//Get matching product name in SC
		getMatchingProductName();
				
//		System.out.println(productUpdatePage.getFieldLocators(checkForProductXpath));
		Util.printInfo("Click on product "+changedProductName);
		productUpdatePage.click(checkForProductXpath);
		Util.sleep(3000);	//need static sleep to avoid race cond
		//get already selected option for SC
		alreadyselectedOption=getSelectedSettings();
		Util.printInfo("Already Selected option for Product "+ changedProductName +" is " + alreadyselectedOption);
		//change the settings if already selected option is ALL Devices then select No Devices, if it is No Devices then select All Devices
		String chagneTo=null;
		if (alreadyselectedOption.contains("No Devices")){
			chagneTo="All Devices";
		}else if(alreadyselectedOption.contains("All Devices")){
			chagneTo="No Devices";
					
		}else if(alreadyselectedOption.contains("Only Selected")){
			chagneTo="No Devices";
		}
		String newOption=changeSettings(chagneTo);
		
		Util.printInfo("LogingOff from Portal as SC and Loging back as CM");
		
		//Login as CM and check if the setting done by SC are displayed. 
		logoutMyAutodeskPortal();
		LoginAndGoProductUpdatesPage("CM");
		String checkForProductXpathInCM=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("machingProductInSC", changedProductName);
		productUpdatePage.click(checkForProductXpathInCM);
		Util.sleep(3000);	//need static sleep to avoid race cond
		String displayedOptionInCM=getSelectedSettings();
		if (displayedOptionInCM.contains(newOption)){
			assertTrue("settings done by SC to the product "+changedProductName+" are displayed for CM. Option displayed: "+displayedOptionInCM, true);
		}else {
			EISTestBase.fail("settings done by SC are not displayed to the product"+changedProductName+" for CM");
		}
		
		if (newOption.contains("No Devices")){
			chagneTo="All Devices";
		}else if(newOption.contains("All Devices")){
			chagneTo="No Devices";
					
		}else if(newOption.contains("Only Selected")){
			chagneTo="No Devices";
		}
		//Change the settings to validate whether settings done by CM will be displayed when we login with SC
		newOption=changeSettings(chagneTo);
		
		
		Util.printInfo("LogingOff from Portal as CM and Loging back as SC");
		logoutMyAutodeskPortal();
		LoginAndGoProductUpdatesPage("SC");
		String checkForProductXpathInSC=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("machingProductInSC", changedProductName);
		productUpdatePage.click(checkForProductXpathInSC);
		String displayedOptionInSC=getSelectedSettings();
		if (displayedOptionInCM.contains(newOption)){
			assertTrue("settings done by CM to the product "+changedProductName+" are displayed for SC. Option displayed: "+displayedOptionInSC, true);
		}else {
			EISTestBase.fail("settings done by Cm are not displayed to the product"+changedProductName+" for SC");
		}
		Util.printInfo("=======================================================================================");
		
	}
	
	public String getSelectedSettings() throws Exception{
		
//		String matchingAccessControlButtonXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("matchingProductAccessControlButton", changedProductName);
		Util.printInfo("Clicking on Access Control ");
		//System.out.println(productUpdatePage.getFieldLocators(matchingAccessControlButtonXpath)); 
//		productUpdatePage.click(matchingAccessControlButtonXpath);
		productUpdatePage.click("accessControlButton");
//		String selectedOptionXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectedOptionInSC",changedProductName);
//		System.out.println(productUpdatePage.getFieldLocators(selectedOptionXpath)); 
//		String selectedOption=productUpdatePage.getValueFromGUI(selectedOptionXpath);
		String selectedOption=getIndividualUpdatesPrefValueForSpecificUpdate();
		return selectedOption;
	}
	
	public String changeSettings(String option) throws Exception{
		if (option.contains("No Devices")){
			String allDevicesXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("allDevices", changedProductName);
			//System.out.println(productUpdatePage.getFieldLocators(allDevicesXpath));
			String allDevicesLable=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("allDevicesLabel", changedProductName);
			/*selectedOption=productUpdatePage.getValueFromGUI(allDevicesXpath);*/
			//System.out.println(selectedOption);
			String deviceRadioBtnloc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("accessControlRadioBtns","no-devices");
			productUpdatePage.click(deviceRadioBtnloc);
			/*productUpdatePage.click(allDevicesLable);*/
			Util.printInfo("changed the option from No Devices to All Devices for the Product "+changedProductName);
		}else if(option.contains("All Devices")){
			String noDevicesXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("noDevices", changedProductName);
			//System.out.println(productUpdatePage.getFieldLocators(noDevicesXpath));
			String noDevicesLable=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("noDevicesLabel", changedProductName);
			/*selectedOption=productUpdatePage.getValueFromGUI(noDevicesXpath);*/
			//System.out.println(selectedOption);
			/*productUpdatePage.click(noDevicesLable);*/
			String nodeviceRadioBtnloc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("accessControlRadioBtns","all-devices");
			productUpdatePage.click(nodeviceRadioBtnloc);
			Util.printInfo("changed the option from ALL Devices to No Devices for the Product "+changedProductName);
					
		}else if(option.contains("Only Selected")){
			String noDevicesXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("noDevices", changedProductName);
			//System.out.println(productUpdatePage.getFieldLocators(noDevicesXpath));
			String noDevicesLable=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("noDevicesLabel", changedProductName);
			/*selectedOption=productUpdatePage.getValueFromGUI(noDevicesXpath);*/
			//System.out.println(selectedOption);
			/*productUpdatePage.click(noDevicesLable);*/
			String deviceRadioBtnloc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("accessControlRadioBtns","selected-devices");
			productUpdatePage.click(deviceRadioBtnloc);
			
			Util.printInfo("changed the option from Only Selected to No Devices for the Product "+changedProductName);
					
		}
		/*return selectedOption;*/
		return getSelectedSettings();
	}
	public void getMatchingProductName(){
		for(int i=0;i<productsName.length;i++){
			checkForProductXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("machingProductInSC", productsName[i]);
			if (productUpdatePage.checkFieldExistence(checkForProductXpath)){
				changedProductName=productsName[i];
				break;
			}
		}
		
	}
	
	public void validateIndividualSettingLogic() throws Exception{
		Util.printInfo("Validating Individual Settings not effected by global settings.");
		getMatchingProductName();
		Util.printInfo("Click on product "+changedProductName);
		productUpdatePage.click(checkForProductXpath);
		productUpdatePage.click("accessControlButton");
		//when you will send the input as All devices it will change the setting to No devices and vice versa
		/*changeSettings("All Devices");//Change the setting to No Devices
*/		changeSettings("No Devices");//Change the setting to No Devices
		productUpdatePage.click(checkForProductXpath);
		Util.printInfo("Click on global setting button and select All Devices");
		productUpdatePage.click("deliverySettingsPUPage");
//		productUpdatePage.waitForFieldVisible("allDevicesRadioButton");
		Util.sleep(10000);
		productUpdatePage.click("allDevicesRadioButton");
		Util.printInfo("Selected All Devices option");
		productUpdatePage.click("selectButtonOnGlbSett");
		Util.printInfo("Clicked on select button. Sucessfully selected the All devices in global settings");
		Util.printInfo("Validate still the product "+changedProductName+" shows its own settings i.e. No devices.");
		Util.printInfo("Click on product "+changedProductName);
		productUpdatePage.click(checkForProductXpath);
		String displayedSettings=getSelectedSettings();
		if (displayedSettings.contains("No Devices")){
			assertTrue("Individaual setting not effected by global settings. Still the product "+changedProductName+" shows No devices even after global settings are selected as All Devices", true);
		}else {
			EISTestBase.fail("Individual setting effected by global settings");
		}
		
	}
	
	public void LoginAndGoProductUpdatesPage(String userType) throws Exception {
		
			if (userType.equalsIgnoreCase("CM")){
				/*if (getEnvironment().equalsIgnoreCase("dev")) {
					USERNAME = testProperties.getConstant("CM_USERNAME_DEV");				
					PASSWORD = testProperties.getConstant("CM_PASSWORD_DEV");
				} else if (getEnvironment().equalsIgnoreCase("stg")) {
					USERNAME = testProperties.getConstant("CM_USERNAME_STG");
					PASSWORD = testProperties.getConstant("CM_PASSWORD_STG");
				
				}*/
				USERNAME=getUserCredentials("CM")[0];
				PASSWORD=getUserCredentials("CM")[1];
			}else if(userType.equalsIgnoreCase("SC")){
				/*if (getEnvironment().equalsIgnoreCase("dev")) {
					USERNAME = testProperties.getConstant("SC_USERNAME_DEV");				
					PASSWORD = testProperties.getConstant("SC_PASSWORD_DEV");
				} else if (getEnvironment().equalsIgnoreCase("stg")) {
					USERNAME = testProperties.getConstant("SC_USERNAME_STG");
					PASSWORD = testProperties.getConstant("SC_PASSWORD_STG");
				
				}*/
				USERNAME=getUserCredentials("SC")[0];
				PASSWORD=getUserCredentials("SC")[1];
			}		
		loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);
		GoToProductUpdatesPage();

	}
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}