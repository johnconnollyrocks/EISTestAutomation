package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ManageAccessOperations extends CustomerPortalTestBase {
	public Test_Verify_ManageAccessOperations() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_OnlineMap() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		VerifyManageAccessAddUserLink();
		logoutMyAutodeskPortal();
}
	
	public void VerifyManageAccessAddUserLink(){
		//clicking on manage access versions
		
		//Getting contract numbers from products and services page
		List<WebElement> ListOfContracts = null;
		ArrayList<String> alist=new ArrayList<String>();
		try{
		ListOfContracts = homePage.getMultipleWebElementsfromField("ListOfContracts");
		}catch(Exception e){
			System.out.println(e);
		}

		for(WebElement EachContract : ListOfContracts){
		WebElement Element=ReturnWebElementToClick(EachContract.getText());
		
		//checking manage access button and clicking on that
		
		if(Element.isDisplayed()){
		Element.click();
		Util.sleep(4000);
		List<WebElement> ManageAccessVersions=null;
		//verifying manage access window exists or not after clicking on manage access window
		
		homePage.verifyFieldExists("ManageAccessWindow");
		Util.sleep(4000);
		Util.printInfo(" Verifying the add user tooltip on manage access window ");
		Actions DoAction=new Actions(driver);
		Util.sleep(4000);
		DoAction.moveToElement(ReturnWebelement());
		Util.sleep(4000);
		
		if(homePage.isFieldVisible("ManageAccessAdduser")){
			Util.printInfo("Tool tip exists for add user on manage access window ");
			Util.printInfo("The text on add user Tool tip is " +homePage.getValueFromGUI("ManageAccessAdduser"));
			homePage.verifyFieldExists("ManageAccessAdduser");
			Util.printInfo("Add user tool tip exits on manage access window ");
			Util.printInfo("Clicking on adduser link ");
			Util.printInfo("verifying navigation of Add users link in manage access widnow ");
			homePage.click("ManageAccessAddUserClick");
			Util.sleep(4000);
			if(driver.getCurrentUrl().contains("user-management") || homePage.isFieldVisible("UserManageMentPageAddUSers")){
				assertTrue("Navigated to user management page, after clicking on addusers link on manage access window ", driver.getCurrentUrl().contains("user-management"));
				homePage.verifyFieldExists("UserManageMentPageAddUSers");
			}else{
				EISTestBase.fail("It is not navigated to Users management page ");
			}
		}else{
			EISTestBase.fail("Tool Tip doesnot exists for add users on manage access window ");
		}
		homePage.click("ManageAccessSave");
	   }
		break;
      }
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
