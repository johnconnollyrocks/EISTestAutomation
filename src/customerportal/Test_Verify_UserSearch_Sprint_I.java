package customerportal;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.EISTestBase;
import common.Util;

public class Test_Verify_UserSearch_Sprint_I extends CustomerPortalTestBase {
	Actions action=new Actions(driver);
	Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
	String browserName = capabilities.getBrowserName();
	public Test_Verify_UserSearch_Sprint_I() throws IOException {
		
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_UserSearch_Sprint_I() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
//		Actions action=new Actions(driver);
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(10000);
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		String[] arr=null;
		String UsersEmailId="";
		String UniqueEmailId="";
		String UserNameAfterSearch="";
		
		List<WebElement> ListOfUsers=homePage.getMultipleWebElementsfromField("ListOfUsersUsersSearch");
		Util.printInfo("Validating user search field by giving first name of user ");
		for(WebElement User : ListOfUsers){
			String UserName=User.getText();
			arr=UserName.split(" ");
			Util.sleep(6000);
			User.click();
			Util.sleep(6000);
			UsersEmailId=homePage.createFieldWithParsedFieldLocatorsTokens("UsersEmailId", UserName);
			UniqueEmailId=homePage.getValueFromGUI(UsersEmailId);
			Util.sleep(6000);
			User.click();
			Util.sleep(6000);
			homePage.populateField("UserSearch", arr[0]);
			Util.sleep(8000);
			
			
			if(!browserName.equalsIgnoreCase("safari")) {
				
			action.sendKeys(Keys.ENTER).build().perform();
				
			}
			Util.sleep(6000);
			UserNameAfterSearch=homePage.createFieldWithParsedFieldLocatorsTokens("UserNameAfterSearch", UserName);
			if(homePage.isFieldVisible(UserNameAfterSearch)){
				Util.printInfo("Users are less than fifty, So verifying select all check box is displayed or not ");
				if(homePage.isFieldVisible("selectAllInUP")){
					assertTrue("Select All check box displayed for users lessthan 50..", homePage.isFieldVisible("selectAllInUP"));
				}else{
					EISTestBase.fail("Select All check box is not displayed for users lessthan 50.. ");				
				}
				homePage.verifyFieldExists(UserNameAfterSearch);
				homePage.click("CloseSearch");
				Util.printInfo("Validating User Search - Edit Access or changing the permissions ");
				String UserEditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("CPUserEditAccess", UserName);
				String FirstUser=homePage.createFieldWithParsedFieldLocatorsTokens("FirstUser",UserName);
				Util.sleep(8000);
				homePage.click(UserEditAccess);
				Util.sleep(6000);
				checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
				Util.sleep(6000);
				checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
				homePage.click("SaveUser");
				Util.sleep(8000);
				homePage.click(FirstUser);
				homePage.verifyFieldExists("ProductExtensions");
				Util.sleep(6000);
				homePage.verifyFieldExists("WebSupport");
				Util.sleep(6000);
				homePage.verifyFieldExists("ProductDownloads");
				Util.sleep(6000);
				homePage.click(FirstUser);
				homePage.click(UserEditAccess);
				Util.sleep(8000);
				unCheckChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
				Util.sleep(6000);
				unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
				Util.sleep(6000);
				homePage.click("SaveUser");
				Util.sleep(8000);
			}else{
				EISTestBase.fail("Not displayed any User after searching with firstname ");
			}
			
			Util.printInfo("Validating user search field by giving email id of user ");
			homePage.populateField("UserSearch", UniqueEmailId);
			if(!browserName.equalsIgnoreCase("safari")) {
				
				action.sendKeys(Keys.ENTER).build().perform();
					
				}
			Util.sleep(6000);
			if(homePage.isFieldVisible(UserNameAfterSearch)){
				homePage.verifyFieldExists(UserNameAfterSearch);
				homePage.click("CloseSearch");
				break;
			}else{
				EISTestBase.fail("Not displayed any User after searching wit email id ");
			}
		}
	
		Util.printInfo("Verifying error result by giving non existing user in search field ");
		homePage.populateField("UserSearch", "xxxyyy");
		Util.sleep(8000);
		if(!browserName.equalsIgnoreCase("safari")) {
			
			action.sendKeys(Keys.ENTER).build().perform();
				
			}
		Util.sleep(8000);
		if(homePage.isFieldVisible("ZeroSearchResultsforUser") && homePage.isFieldVisible("HelpMessage")){
			homePage.verifyFieldExists("ZeroSearchResultsforUser");
			homePage.verifyFieldExists("HelpMessage");
			homePage.click("CloseSearch");
			Util.sleep(8000);
		}else{
			EISTestBase.fail("There is no error message dispalyed for non existing user ");
		}
		
		Util.printInfo("Validating lazy loading of user search field by giving last name of user ");
		homePage.populateField("UserSearch", arr[1]);
		Util.sleep(8000);
		if(!browserName.equalsIgnoreCase("safari")) {
			
			action.sendKeys(Keys.ENTER).build().perform();
				
			}
		Util.sleep(8000);
		int HundredUsers=LazyLoading(100);
		Util.sleep(8000);
		int HundredandFiftyUsers=LazyLoading(150);
		Util.sleep(8000);
		int TwoHundredUsers=LazyLoading(200);
		Util.sleep(8000);
		
		/*if(UsersSize==50){
			assertTrue("Fifty Users loaded after searching with last name ",UsersSize==50);
		}else{
			EISTestBase.fail("Not displayed any User after searching with Lastname, So lazy loading got failed ");
		}*/
		
		if(HundredUsers==100){
			assertTrue("Hundred Users loaded after searching with last name ",HundredUsers==100);
		}else{
			EISTestBase.fail("Not displayed any User after searching with Lastname, So lazy loading got failed ");
		}
		
		if(HundredandFiftyUsers==150){
			assertTrue("HundredandFifty Users loaded after searching with last name ",HundredandFiftyUsers==150);
		}else{
			EISTestBase.fail("Not displayed any User after searching with Lastname, So lazy loading got failed ");
		}
		
		if(TwoHundredUsers==200){
			assertTrue("Two Hundred Users loaded after searching with last name ",TwoHundredUsers==200);
		}else{
			EISTestBase.fail("Not displayed any User after searching with Lastname, So lazy loading got failed ");
		}
		
		Util.printInfo("Validating page navigation after searching for the user in user search field ");
		
		homePage.populateField("UserSearch", arr[0]);
		if(!browserName.equalsIgnoreCase("safari")) {
			
			action.sendKeys(Keys.ENTER).build().perform();
				
			}
		
		Util.sleep(6000);
		
		Util.printInfo("Navigating from Users Tab to Products and Services Tab...");
		
		homePage.click("ProductsandServices");
		
		Util.sleep(6000);
		
		Util.printInfo("Navigating back to Users Tab...");
		
		homePage.clickAndWait("users","selectAllInUP");
		
		List<WebElement> ListOfUsersAfterNavigation=homePage.getMultipleWebElementsfromField("ListOfUsersUsersSearch");
		
		if(!(ListOfUsersAfterNavigation.size()>50)){
			homePage.verifyFieldExists("UniqueUserAfterSearch");
			assertTrue("The Users search criteria still exists even after navigating to Products and services ", !(ListOfUsersAfterNavigation.size()>50));
		}else{
			EISTestBase.fail("The Users search criteria does not exists after navigating to Products and services ");
		}
		
		Util.printInfo("Navigating to Reporing Page ");
		homePage.clickAndWait("reporting","byUsers");
		Util.printInfo("Navigating back to Users page ");
		homePage.clickAndWait("users","selectAllInUP");
		
		List<WebElement> ListOfUsersAfterNavigation1=homePage.getMultipleWebElementsfromField("ListOfUsersUsersSearch");
		
		if(!(ListOfUsersAfterNavigation1.size()>50)){
			homePage.verifyFieldExists("UniqueUserAfterSearch");
			assertTrue("The Users search criteria still exists even after navigating to Reporting page ", !(ListOfUsersAfterNavigation1.size()>50));
		}else{
			EISTestBase.fail("The Users search criteria does not exists after navigating to Reporting page ");
		}
		
		Util.printInfo("Navigating to profile page ");
		homePage.click("Profile");
		Util.sleep(6000);
		Util.printInfo("Navigating back to Account page --> Users tab ");
		homePage.click("AccountTab");
		Util.sleep(40000);
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(6000);
		
		List<WebElement> ListOfUsersAfterNavigation11=homePage.getMultipleWebElementsfromField("ListOfUsersUsersSearch");
		
		if((ListOfUsersAfterNavigation11.size()>=50)){
			assertTrue("The Users search criteria does not exists after navigating to Profile page ", (ListOfUsersAfterNavigation11.size()>=50));
		}else{
			EISTestBase.fail("The Users search criteria still exists after navigating to Profile page ");
		}
		
		Util.printInfo("Refreshing the page to validate search criteria exists or not ");
		Util.sleep(8000);
		
		List<WebElement> ListOfUsersAfterRefresh=homePage.getMultipleWebElementsfromField("ListOfUsersUsersSearch");
		
		if((ListOfUsersAfterRefresh.size()>=50)){
			assertTrue("The Users search criteria does not exists after Refreshing the page ", (ListOfUsersAfterRefresh.size()>=50));
		}else{
			EISTestBase.fail("The Users search criteria still exists after Refreshing the page ");
		}
		
		
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
