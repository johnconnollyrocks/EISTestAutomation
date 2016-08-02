package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ManageAccessMenuItem extends CustomerPortalTestBase {
	
	public List<WebElement> allChekboxsInManageAccessWindow;
	public List<WebElement> numberOfProductList;
	public int count;
	public int versionCount;
	
	public List<WebElement> versionsCount;
	public Test_Verify_ManageAccessMenuItem() throws IOException {
		super("Browser",getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_ManageAccessMenuItems() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(10000);
		Util.printInfo("*******************************************************************************");
		if(homePage.verifyFieldExists("InstallNowButton")){
			assertTrue("Products are present for this user",homePage.verifyFieldExists("InstallNowButton"));
			assertTrue("Manage Access Icon is present in the last",homePage.verifyFieldExists("manageAccess"));
			Util.printInfo("Clicking on the dropdown");
			homePage.click("productDropDown");
			assertTrue("Manage Access separator is present",homePage.verifyFieldExists("manageAccessSeparator"));
			homePage.click("manageAccess");
			assertTrue("The manage access close button X is present",homePage.verifyFieldExists("manageAccessCloseButton"));
			String productNameOnPNSPage =homePage.getValueFromGUI("productNameOnPNSPage");
			String productNameOnManageAccessPage=homePage.getValueFromGUI("productNameOnManageAccessPage");
			assertEquals(productNameOnPNSPage, productNameOnManageAccessPage);
			
		}
		else{
			EISTestBase.fail(" OOpps !!!no products for this CM choose an another");
		}
		
		logoutMyAutodeskPortal();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("END_USER_NAME") , testProperties.getConstant("PASSWORD"));
		if(homePage.isFieldVisible("InstallNowButton")){
			assertTrue("Products are present for this user",homePage.verifyFieldExists("InstallNowButton"));
			assertTrue("Manage Access Icon is not present",homePage.verifyFieldNotExists("manageAccess"));
		}
		else{
			EISTestBase.fail(" OOpps !!!no products for this End User, choose another");
		}
		logoutMyAutodeskPortal();
	}
		
		@After
		public void tearDown() throws Exception {
			driver.quit();

			finish();
		}
		}


