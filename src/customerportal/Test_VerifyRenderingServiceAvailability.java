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

/**
 * Test class - Test_VerifyInstallNowButton
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyRenderingServiceAvailability extends CustomerPortalTestBase {
	public Test_VerifyRenderingServiceAvailability() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyInstallNowButton() throws Exception {
		
		//Login as CM and edit access by giving Rendering service
		Util.printInfo("Login as CM and edit access by giving Rendering service");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME") , testProperties.getConstant("CM_PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.click("users");
		int i=0;
		//homePage.clickAndWait("endUserEditAccessLink", "productsServicesFirstProductButtonToggleDrawer");
		/*List<WebElement> ReportUsersList=homePage.getMultipleWebElementsfromField("EndUserName");
		
		for(WebElement UserName : ReportUsersList)
		{
			i=i+1;
			UserName.click();
			String EndUser=driver.findElement(By.xpath("//div[2]/section/div[2]/section[4]/ul/li["+i+"]/section[2]/div/div[2]/div/div")).getText();
			UserName.click();			
			if(EndUser.contains("End User")){
				Util.sleep(4000);
				String EndUserEditAccesss=homePage.createFieldWithParsedFieldLocatorsTokens("endUserEditAccessLink", UserName.getText());
				homePage.clickAndWait(EndUserEditAccesss, "productsServicesFirstProductButtonToggleDrawer");
				break;
			}
		}*/
		//homePage.clickAndWait("endUserProductToggleDrawer","endUserRevitLTSuitRenderingService");
		
		String RenderingEmailId=testProperties.getConstant("USER_NAME");
		
		String FirstName = "Email";
		String UserName = "Email"+" "+"Fdjke";
		
		String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",UserName);
		
		homePage.populateField("UserSearch", RenderingEmailId);
		
		Util.sleep(4000);
		
		if(homePage.isFieldVisible(Enduser1)){
			RemoveUser(UserName);
		}
		Util.sleep(6000);
		homePage.click("CloseSearch");
		Util.sleep(4000);
		
		Util.printInfo("Adding rendering end user ");
		
		addUser(RenderingEmailId, FirstName);
		
		Util.sleep(40000);
		if(homePage.isFieldVisible("BulkUsersEditAccess")){
			Util.printInfo("Edit Access page loaded successfully ");
		}else{
			EISTestBase.fail("Edit access page not loaded successfully, so please change CM user or else please recheck again ");
		}
		
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		
		Util.sleep(4000);
		homePage.click("SaveUser");
		
		
		
		Util.sleep(4000);
		
		//homePage.clickAndWait("saveButton","endUserEditAccessLink");
		//Added rendering service
		Util.printInfo("Added rendering service to the user " +RenderingEmailId);
		logoutMyAutodeskPortal();   
		Util.printInfo("Login as End User to check Rendering service existence");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		homePage.click("services");
		//Check whether rendering service is present or not
		Util.printInfo("Check whether rendering service is present or not");
		homePage.verifyFieldExists("renderingService");
		logoutMyAutodeskPortal();
		//Login as CM and edit access by removing Rendering service
		Util.printInfo("Login as CM and edit access by removing Rendering service");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME") , testProperties.getConstant("CM_PASSWORD"));
		homePage.click("users");
		Util.sleep(4000);
		//homePage.clickAndWait("endUserEditAccessLink", "productsServicesFirstProductButtonToggleDrawer");
		RemoveUser(UserName);
		//Removed rendering service
		Util.printInfo("Removed rendering service");
		logoutMyAutodeskPortal();   
		Util.sleep(5000);
		Util.printInfo("Login as End User to check Rendering service does not exist");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(10000);
		Util.printInfo("Before clicking on Services");
		homePage.click("services");
		Util.sleep(5000);
		//Check whether rendering service is present or not
		Util.printInfo("Check whether rendering service is present or not");
		homePage.verifyFieldNotExists("renderingService");
		logoutMyAutodeskPortal();
	}

	
		

	@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}
}
