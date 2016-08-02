package customerportal;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_GloblSetting_ON_ProdUpds_MngDevics extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String deviceName = null;
	public Test_Verify_GloblSetting_ON_ProdUpds_MngDevics() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void TestGloblSetting_ON_ProdUpds_MngDevics() throws Exception {
	/*	if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{*/
		//shouldnt accept any user creds from Jenkins
			if (getEnvironment().equalsIgnoreCase("dev")) {
				USERNAME = testProperties.getConstant("USERNAME_DEV");
				EMAIL = USERNAME;
				PASSWORD = testProperties.getConstant("PASSWORD_DEV");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USERNAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");
			
			}
			loginAsMyAutodeskPortalUser(USERNAME , PASSWORD);		
		/*}*/
		
		homePage.checkIfElementExistsInPage("genericLoadingIcon", 20);
		if (!productUpdatePage.isFieldPresent("manageDevicesTab")){
			EISTestBase.fail("No Devices tab is found, Hence exiting the test");
		}		
		productUpdatePage.click("manageDevicesTab");
		Util.sleep(4000);		
		if(productUpdatePage.isFieldVisible("acceptTermsInDevicePage")){			
			productUpdatePage.click("acceptTermsInDevicePage");
		}
		// verify the delivery settings pop up on Devices page & Verify the devices on manage devices page
		Util.printInfo("Verifying for the" +" Devices"+" Tab");
		assertTrue("Devices Tab is present",productUpdatePage.verifyFieldExists("manageDevicesTab"));
		Util.printInfo("Clicking on the " +" Devices"+" Tab");
		productUpdatePage.click("manageDevicesTab");
		assertTrue("Page title Manage Devices is present",productUpdatePage.verifyFieldExists("manageDevicesTab"));
		Util.printInfo("Verifying for the elements on Devices Page");
		productUpdatePage.verify();
		
		//Check PII persistent banner
		String remndrloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("reminderMore", "More");
		productUpdatePage.verify();
		assertTrueWithFlags("The More button should be displayed and text should be wrapped inside in the Remainder block" , productUpdatePage.isFieldVisible(remndrloc));		
		productUpdatePage.click(remndrloc);
		remndrloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("reminderMore", "Less");
		assertTrueWithFlags("The More button should be displayed and text should be wrapped inside in the Remainder block" , productUpdatePage.isFieldVisible(remndrloc));
		assertTrue("Page title Manage Devices is present",productUpdatePage.verifyFieldExists("reminderMessage"));
		
		
		assertTrue("Page title Manage Devices is present",productUpdatePage.verifyFieldExists("manageDevicesPageTitle"));
		assertTrue("Delivery settings on the manage devices page is present",productUpdatePage.verifyFieldExists("deliverySettingsOnManageDevices"));
		if (productUpdatePage.isFieldVisible("acceptTermsInDevicePage")){
			productUpdatePage.click("acceptTermsInDevicePage");
		}

		productUpdatePage.click("deliverySettingsOnManageDevices");
		Util.sleep(4000);
		productUpdatePage.waitForFieldPresent("AcceptedDateAndTime");
		assertTrue("Accepted Date and time getting displayed ",productUpdatePage.verifyFieldExists("AcceptedDateAndTime"));
		Util.printInfo(productUpdatePage.getValueFromGUI("AcceptedDateAndTime"));
		assertTrue("Delivery settings popup is showing up ",productUpdatePage.verifyFieldExists("DeliverySettingsPopUp"));
		assertTrue("Download Acces Control text is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("downloadAccessControl"));
		assertTrue("Privacy Information text is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("privacyInformation"));
		assertTrue("All the radio buttons are present on the delivery settings popup ",productUpdatePage.verifyFieldExists("deliverySettingsRadioButtons"));
		assertTrue("Add button is Present ",productUpdatePage.verifyFieldExists("addDevices"));
		productUpdatePage.click("closeDeliverySettingWindowOfProdUpds");
		Util.sleep(1000);
		productUpdatePage.refresh();
		// verify the delivery settings pop up on product Updates page
		
		GoToProductUpdatesPage();
		String newURL=driver.getCurrentUrl();
		assertTrue("Delivery settings on the ProductUpdates page is present",productUpdatePage.verifyFieldExists("deliverySettingsOnManageDevices"));
		productUpdatePage.click("deliverySettingsOnManageDevices");
		Util.sleep(2000);
		assertTrue("Delivery settings popup is showing up ",productUpdatePage.verifyFieldExists("DeliverySettingsPopUp"));
		assertTrue("Download Acces Control text is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("downloadAccessControl"));
		assertTrue("Privacy Information text is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("privacyInformation"));
		assertTrue("All the radio buttons are present on the delivery settings popup ",productUpdatePage.verifyFieldExists("deliverySettingsRadioButtons"));

	    //1.	Go to global settings, click on I don’t accept and check close to prior page works. Then again verify accept terms.	
		productUpdatePage.click("personalIdentificationAgreementLink");
		productUpdatePage.click("declineTerms");
		String ExpectedDesc=productUpdatePage.getValueFromGUI("declineDescription");
		assertEquals(ExpectedDesc, testProperties.getConstant("DECLINE_AGREEMENT"));
		productUpdatePage.click("closeAndGoToProfile");
		Util.sleep(2000);	//to avoid race condition
		productUpdatePage.click("deliverySettingsOnManageDevices");
		Util.sleep(2000);	//wait for sometime till the pop up comes up
		productUpdatePage.click("personalIdentificationAgreementLink");
		assertTrue("Agreement Icon is getting displayed ",productUpdatePage.verifyFieldExists("agreementIcon"));
		String ActAgreementDesc=productUpdatePage.getValueFromGUI("agreementMessage");
		assertEquals(ActAgreementDesc, testProperties.getConstant("Agreement_MESSAGE"));
		productUpdatePage.click("declineTerms");
		assertEquals(ExpectedDesc, testProperties.getConstant("DECLINE_AGREEMENT"));
		driver.getWindowHandle();
		productUpdatePage.click("closeAndGoToProfile");
		Util.printInfo("Validating the URL navigation from agreement tab to the previous page(product updates Page)");
		Util.sleep(2000);
		String URL_New = driver.getCurrentUrl();
		assertEquals(URL_New, newURL);
		
		logoutMyAutodeskPortal();
	}
	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}


}

