package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyEUProdEnhncmnt_Disable extends CustomerPortalTestBase {
	public Test_VerifyEUProdEnhncmnt_Disable() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	
	@Test
	public void Test_VerifyEUProdEnhncmnt_Disable() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.clickAndWait("users", "manageUsers");
		//homePage.click("manageUsers");
		/*Util.sleep(5000);
		String PrdEnhancementEmailId=testProperties.getConstant("ENDUSER_USER_NAME");
		
		String FirstName = "unique";
		String UserName = "uniquez"+" "+"name";
		
		String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",UserName);
		
		homePage.populateField("UserSearch", PrdEnhancementEmailId);*/
		
		/*Util.sleep(4000);
		
		if(homePage.isFieldVisible(Enduser1)){
			RemoveUser(UserName);
		}
		Util.sleep(20000);
		homePage.click("CloseSearch");
		Util.sleep(4000);
		
		Util.printInfo("Adding rendering end user ");
		
		addUser(PrdEnhancementEmailId, FirstName);*/
		
		/*String EditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("endUserEditAccessLink", testProperties.getConstant("ENDUSER_USER_NAME"));
		homePage.click(EditAccess);*/
		homePage.click("endUserEditAccessLink");
		Util.sleep(5000);
		
		unCheckChecKBox("productDowloadsCheckboxInput", "productDowloadsCheckboxLabel");
		
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		
		unCheckChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		
		unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		
		homePage.click("SaveUser");
		
		Util.sleep(4000);
		
 	    Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
	    String browserName = capabilities.getBrowserName();
	            if(!browserName.equalsIgnoreCase("safari")) {
	            	// unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
	    }
	    else{ 
	    	if(!homePage.isChecked("inputProductsAssignAll")){
				Util.sleep(5000);
				Util.printInfo("The check box is unchecked");
				Util.printInfo("Clicking on checkbox..");
				Util.sleep(2000);
				jsToolTip("labelProductsAssignAll");
				//	 		homePage.click("endUserAutoCADRevitLTSuitRenderingServiceCheckBox");
				Util.sleep(2000);
				Util.printInfo("The check box is checked now..");
			}
			else {
				Util.printInfo("The check box is checked");	
				Util.sleep(2000);
			}
	    }


	
		//homePage.click("saveButton");
		
		logoutMyAutodeskPortal();  
		Util.sleep(5000);
		
		/*loginAsMyAutodeskPortalUser(testProperties.getConstant("ENDUSER_USER_NAME") , testProperties.getConstant("ENDUSER_PASSWORD"));
		mainWindow.select();
		homePage.refresh();
		Util.sleep(45000);
		logoutMyAutodeskPortal(); 
		Util.sleep(5000);*/
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("ENDUSER_USER_NAME") , testProperties.getConstant("ENDUSER_PASSWORD"));
		//homePage.refresh();
		Util.sleep(40000);
		if(!homePage.isFieldVisible("productEnhancement")){
			assertFalseWithFlags("Product Enhancement link not displayed ", homePage.isFieldVisible("productEnhancement"));
		}else{
			EISTestBase.fail(" Product enhancement link exists ");
		}
		//homePage.verify();
		logoutMyAutodeskPortal();   
				
	}

	@After
	public void tearDown() throws Exception {
		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.manage().deleteAllCookies();
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}
}
