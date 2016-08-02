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

import common.EISTestBase;
import common.Util;

public class Test_Verify_ClearAll_link_After_Selecting_Filters extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;
	public String deviceName=null;
	public String description=null;
	public String lastUser=null;
	

	public Test_Verify_ClearAll_link_After_Selecting_Filters() throws Exception {
		super("Browser", getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_AliasNames_IN_Devices_Page() throws Exception {
		
		loginToPortalAndnavigateToToDevices();
		Util.printInfo("******************************************************DEF60*********************************************************");
		Util.printInfo("Verifying Clear all link is displayed and it is not truncated after selecting the more that 67 filter options");
		Util.printInfo("Clicking on filter button in devices page");
		productUpdatePage.click("fillterButtonOnDevicePage");
		String[] allCheckBoxes=productUpdatePage.getMultipleTextValuesfromField("allcheckBoxInFilterOptions");
		if (allCheckBoxes.length<6){
			EISTestBase.fail("Filter should have more that 6 options (i.e checkboxs) to test whether 'Clear All' link is truncated or not");
		}
		Util.printInfo("Select all the check Boxes in the filter options");
		productUpdatePage.checkAllCheckboxes("allcheckBoxInFilterOptions", true);
		productUpdatePage.click("fillterButtonOnDevicePage");
		assertTrue("'Clear All' link is displayed after selecting more that 6 filter options", productUpdatePage.isFieldVisible("clearAllLink"));
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
		
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}

}
