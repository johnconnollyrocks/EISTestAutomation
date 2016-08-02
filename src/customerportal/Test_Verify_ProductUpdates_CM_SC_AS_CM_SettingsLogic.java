package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ProductUpdates_CM_SC_AS_CM_SettingsLogic extends CustomerPortalTestBase{
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String[] productsName=null;
	public String selectedOption=null;
	public String alreadyselectedOption=null;
	public String changedProductName=null;
	public String checkForProductXpath=null;
	
	public Test_Verify_ProductUpdates_CM_SC_AS_CM_SettingsLogic() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verify_ProductUpdates_CM_SC_AS_CM_Settings() throws Exception {
		
		//TC546-Test CM/registered end user get their own settings
		
		//Login with CM user and get the products from Product Update Page.
		LoginAndGoProductUpdatesPage("CM");
		Util.sleep(2000);		
		Util.printInfo("Getting all the Products in ProductUpdate Page");
		productsName=productUpdatePage.getMultipleTextValuesfromField("productUpdateListNames");
		Util.printInfo("LogingOff from Portal as CM and Loging back as SC");
		// Login as SC and change the settings 
		logoutMyAutodeskPortal();
		LoginAndGoProductUpdatesPage("SC");
		Util.printInfo("Finding for the product which was available in CM.Initially  make sure that All Devices is selected to one of the product");
		
		//Get matching product name in SC
		getMatchingProductName();
				
//		System.out.println(productUpdatePage.getFieldLocators(checkForProductXpath));
		Util.printInfo("Click on product "+changedProductName);
		productUpdatePage.click(checkForProductXpath);
	
		Util.printInfo("Initially select All Devices option for Product "+ changedProductName);
		//Initially select all devices option for a product.
		getSelectedSettings();
		changeSettingToAllDevices();
		Util.printInfo("Selected All Devices option for Product "+ changedProductName);
		
		Util.printInfo("LogingOff from Portal as SC and Loging back as CM");
		
		//Login as CM and Chagne the setting to No Devices for the product which we mad Add Devices using SC. 
		logoutMyAutodeskPortal();
		LoginAndGoProductUpdatesPage("CM");
		String checkForProductXpathInCM=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("machingProductInSC", changedProductName);
		productUpdatePage.click(checkForProductXpathInCM);
		getSelectedSettings();
		String newOption=changeSettingToNoDevices();
		
		//Login as CM and check if the setting done by SC are displayed. 
		logoutMyAutodeskPortal();
		LoginAndGoProductUpdatesPage("SC");
		checkForProductXpathInCM=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("machingProductInSC", changedProductName);
		productUpdatePage.click(checkForProductXpathInCM);
		String displayedOptionInCM=getSelectedSettings();
		if (!displayedOptionInCM.contains(newOption)){
			assertTrue("settings done by CM to the product "+changedProductName+" are not displayed for SC as he is also a CM. Option displayed: "+displayedOptionInCM, true);
		}else {
			EISTestBase.fail("settings done by CM are  displayed to the product"+changedProductName+" for SC who is also a CM. Expected SC whoe is also a CM should have his wown Settings");
		}
		
		//Chagne the settings to validate whether settings done by CM will be displayed when we login with SC
		
		newOption=changeSettingToOnlySelected();
		
		Util.printInfo("LogingOff from Portal as CM and Loging back as SC");
		logoutMyAutodeskPortal();
		LoginAndGoProductUpdatesPage("CM");
		String checkForProductXpathInSC=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("machingProductInSC", changedProductName);
		productUpdatePage.click(checkForProductXpathInSC);
		String displayedOptionInSC=getSelectedSettings();
		if (!displayedOptionInCM.contains(newOption)){
			assertTrue("settings done by SC to the product "+changedProductName+" are not displayed for CM. Option displayed: "+displayedOptionInSC, true);
		}else {
			EISTestBase.fail("settings done by SC are displayed to the product"+changedProductName+" for CM. Expected CM should have his won Settings");
		}	
		
	}
	
	public String getSelectedSettings(){
		
		String matchingAccessControlButtonXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("matchingProductAccessControlButton", changedProductName);
		Util.printInfo("Clicking on Access Control ");
		//System.out.println(productUpdatePage.getFieldLocators(matchingAccessControlButtonXpath)); 
		System.out.println(productUpdatePage
				.getFieldLocators(matchingAccessControlButtonXpath));
		productUpdatePage.click(matchingAccessControlButtonXpath);
		String selectedOptionXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectedOptionInSC",changedProductName);
		//System.out.println(productUpdatePage.getFieldLocators(selectedOptionXpath)); 
		String selectedOption=productUpdatePage.getValueFromGUI(selectedOptionXpath);
		return selectedOption;
	}
	
	
	public String changeSettingToAllDevices(){
		String allDevicesXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("allDevices", changedProductName);
		//System.out.println(productUpdatePage.getFieldLocators(allDevicesXpath));
		String allDevicesLable=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("allDevicesLabel", changedProductName);
		selectedOption=productUpdatePage.getValueFromGUI(allDevicesXpath);
		//System.out.println(selectedOption);
		productUpdatePage.click(allDevicesLable);
		return selectedOption;
	}
	
	public String changeSettingToNoDevices(){
		String noDevicesXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("noDevices", changedProductName);
		//System.out.println(productUpdatePage.getFieldLocators(noDevicesXpath));
		String noDevicesLable=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("noDevicesLabel", changedProductName);
		selectedOption=productUpdatePage.getValueFromGUI(noDevicesXpath);
		//System.out.println(selectedOption);
		productUpdatePage.click(noDevicesLable);
		return selectedOption;
	}
	public String changeSettingToOnlySelected(){
		String onlySelectedDevicesXpath=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("onlySelectedDevices", changedProductName);
		//System.out.println(productUpdatePage.getFieldLocators(noDevicesXpath));
		String onlySelectedDevicesLabel=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("onlySelectedDevicesLabel", changedProductName);
		selectedOption=productUpdatePage.getValueFromGUI(onlySelectedDevicesXpath);
		//System.out.println(selectedOption);
		productUpdatePage.click(onlySelectedDevicesLabel);
		return selectedOption;
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
	
	public void LoginAndGoProductUpdatesPage(String userType) throws Exception {
		
			if (userType.equalsIgnoreCase("CM")){
				if (getEnvironment().equalsIgnoreCase("dev")) {
					USERNAME = testProperties.getConstant("CM_USERNAME_DEV");				
					PASSWORD = testProperties.getConstant("CM_PASSWORD_DEV");
				} else if (getEnvironment().equalsIgnoreCase("stg")) {
					USERNAME = testProperties.getConstant("CM_USERNAME_STG");
					PASSWORD = testProperties.getConstant("CM_PASSWORD_STG");
				
				}
			}else if(userType.equalsIgnoreCase("SC")){
				if (getEnvironment().equalsIgnoreCase("dev")) {
					USERNAME = testProperties.getConstant("SC_USERNAME_DEV");				
					PASSWORD = testProperties.getConstant("SC_PASSWORD_DEV");
				} else if (getEnvironment().equalsIgnoreCase("stg")) {
					USERNAME = testProperties.getConstant("SC_USERNAME_STG");
					PASSWORD = testProperties.getConstant("SC_PASSWORD_STG");
				
				}
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