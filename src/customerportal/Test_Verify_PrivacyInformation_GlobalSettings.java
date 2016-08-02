package customerportal;

import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;
import common.exception.MetadataException;

public class Test_Verify_PrivacyInformation_GlobalSettings extends CustomerPortalTestBase {
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public int count;
	public List<WebElement> allChekboxsInManageAccessWindow;
	

	public Test_Verify_PrivacyInformation_GlobalSettings() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_Verify_PrivacyInformation_GlobalSetting() throws Exception {
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
		verifyDeliverysettings();
		logoutMyAutodeskPortal();
	}
	
	private void verifyDeliverysettings() throws Exception {
		String newURL=driver.getCurrentUrl();
		assertTrue("Delivery settings on the ProductUpdates page is present",productUpdatePage.verifyFieldExists("deliverySettingsOnManageDevices"));
		productUpdatePage.click("deliverySettingsOnManageDevices");
		Util.sleep(3000);
		assertTrue("Delivery settings popup is showing up ",productUpdatePage.verifyFieldExists("DeliverySettingsPopUp"));
		assertTrue("Privacy Information text is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("privacyInformation"));
		assertTrue("Personally Identifiable Information Agreement tooltip Icon is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("personalInformationAgreementToolTip"));
		assertTrue("Personally Identifiable Information Agreement Link is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("personalIdentificationAgreementLink"));
		productUpdatePage.click("personalIdentificationAgreementLink");
//		assertTrue("Personally Identifiable Information Agreement Link is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("personalIdentificationAgreementLink"));
		assertTrue("agreement Header is present on the delivery settings popup ",productUpdatePage.verifyFieldExists("agreementHeader"));
		String ActualDesc=productUpdatePage.getValueFromGUI("agreementDescription");
		ActualDesc=ActualDesc.replace("\\r", "").trim();
		ActualDesc=ActualDesc.replace("\u201C", "").replace("\u201D", "");
		assertEqualsWithFlags(ActualDesc, testProperties.getConstant("PERSONAL_INFO_AGREEMENT"));
		productUpdatePage.click("acceptTheAgreement");
		assertTrue("Accepted Date and time getting displayed ",productUpdatePage.verifyFieldExists("AcceptedDateAndTime"));
		productUpdatePage.click("personalIdentificationAgreementLink");
		productUpdatePage.click("declineTerms");
		String ExpectedDesc=productUpdatePage.getValueFromGUI("declineDescription");
		assertEquals(ExpectedDesc, testProperties.getConstant("DECLINE_AGREEMENT"));
		productUpdatePage.click("closeAndGoToProfile");
		Util.sleep(3000);	//to avoid race condition
		productUpdatePage.click("deliverySettingsOnManageDevices");
		Util.sleep(2000);	//wait for sometime till the pop up comes up
		productUpdatePage.click("personalIdentificationAgreementLink");
		assertTrue("Agreement Icon is getting displayed ",productUpdatePage.verifyFieldExists("agreementIcon"));
		String ActAgreementDesc=productUpdatePage.getValueFromGUI("agreementMessage");
		assertEquals(ActAgreementDesc, testProperties.getConstant("Agreement_MESSAGE"));
		productUpdatePage.click("declineTerms");
		assertEquals(ExpectedDesc, testProperties.getConstant("DECLINE_AGREEMENT"));
		assertTrue("Close, Go to profile should be displayed" ,productUpdatePage.verifyFieldExists("closeAndGoToProfile"));
		driver.getWindowHandle();
		/*Sai: Dont hardcode the url in test props file, your test may not run properly on other environment, so contruct the url here and check*/		
		/*String newURL = testProperties.getConstant("GOTO_PROFILE");*/		
		productUpdatePage.click("closeAndGoToProfile");
		/*No need to do switching windows, as this with in same window*/
		/*for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}*/
		Util.printInfo("Validating the URL navigation from agreement tab to the previous page(product updates Page)");
		Util.sleep(2000);
		String URL_New = driver.getCurrentUrl();
		assertEquals(URL_New, newURL);		
		mainWindow.select();
		
		
		
		
		
       
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

