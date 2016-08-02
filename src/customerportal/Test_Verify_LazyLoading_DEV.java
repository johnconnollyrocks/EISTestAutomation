package customerportal;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.EISTestBase;
import common.Util;

public class Test_Verify_LazyLoading_DEV extends CustomerPortalTestBase {
	public Test_Verify_LazyLoading_DEV() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_LazyLoading_DEV() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.waitForFieldPresent("AllProductsandServices");
		
		Util.printInfo("Clicking on UserManagement page and verifying all the fields in Users page :: ");
		
		homePage.click("users");
		homePage.waitForFieldPresent("AllUsersInUserManagementPage");

		boolean flag=true;

		List<WebElement> UsersList = driver.findElements(By.xpath(".//*[@id='results']/li/section[1]/div[2]"));
		Util.printInfo("Validating Select Option Should not exist :: ");

		
		if(UsersList.size()==50 ){
			flag=false;
			Util.printInfo("50 Users got loaded");
			assertTrue("Fifty End Users got loaded Successfully before scroll down ", UsersList.size()==50);
		}	else{
			EISTestBase.fail("Fifty Users not loaded successfully please re check :: ");
		}
		
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		Util.sleep(4000);
		List<WebElement> UsersListAfterScroll = driver.findElements(By.xpath(".//*[@id='results']/li/section[1]/div[2]"));
		
		if(UsersListAfterScroll.size()==100){
			Util.printInfo("100 Users got loaded");
			assertTrue("Hundred End users got loaded Successfully after scroll down ", UsersListAfterScroll.size()==100);
		}else{
			EISTestBase.fail("Hundred Users not loaded successfully please re check :: ");
		}
		
		Actions actions1 = new Actions(driver);
		actions1.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		Util.sleep(4000);
		List<WebElement> UsersListAfterScroll1 = driver.findElements(By.xpath(".//*[@id='results']/li/section[1]/div[2]"));
		
		if(UsersListAfterScroll1.size()==150){
			Util.printInfo("150 Users got loaded");
			assertTrue("150 End users got loaded Successfully after scroll down ", UsersListAfterScroll1.size()==150);
		}else{
			EISTestBase.fail("150 Users not loaded successfully please re check :: ");
		}
		
		Actions actions11 = new Actions(driver);
		actions11.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		Util.sleep(4000);
		List<WebElement> UsersListAfterScroll11 = driver.findElements(By.xpath(".//*[@id='results']/li/section[1]/div[2]"));
		
		if(UsersListAfterScroll11.size()==200){
			Util.printInfo("200 Users got loaded");
			assertTrue("200 End users got loaded Successfully after scroll down ", UsersListAfterScroll11.size()==200);
		}else{
			EISTestBase.fail("200 Users not loaded successfully please re check :: ");
		}
		
		String ActualEmail="User"+getUniqueId()+"@ssttest.net";
		String FirstNameUp=RandomStringUtils.randomAlphabetic(5);
		String FirstName=toTitleCase(FirstNameUp);
		Util.printInfo("Adding single User :: ");
		Util.printInfo("New User " +FirstName);
		addUser(ActualEmail, FirstName);
		homePage.click("SaveUser");
		homePage.waitForFieldPresent("AllUsersInUserManagementPage");
		homePage.refresh();
		homePage.waitForFieldPresent("AllUsersInUserManagementPage");
		
		for(int i=0;i<=11;i++){
			/*Actions action = new Actions(driver);
			Util.sleep(6000);
			action.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();*/
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,6000)");
			Util.sleep(6000);
		}
		
		Util.printInfo("Verifying CM's edit access page..");
		String CMEditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("CmGlEditAccess", testProperties.getConstant("CMUser"));
		String CmOnEditAccessPage=homePage.createFieldWithParsedFieldLocatorsTokens("CmOnEditAccessPage", testProperties.getConstant("CMUser"));
		homePage.click(CMEditAccess);
		Util.sleep(6000);
		homePage.verifyFieldExists(CmOnEditAccessPage);
		homePage.click("SaveUser");
		Util.sleep(6000);
		for(int i=0;i<=11;i++){
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,6000)");
			Util.sleep(6000);
		}
		
		String UserName=FirstName;
		Util.printInfo("New user created successfully " +UserName);
		
		Util.printInfo("Searching for the user with first name for deleting");
		
		homePage.populateField("searchTheUser", UserName);
		Util.sleep(8000);
		
		Actions usersearch = new Actions(driver);
		usersearch.sendKeys(Keys.ENTER).build().perform();
		Util.sleep(6000);
		
		String NewlyAddedUserInUserManagementPage=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserInUserManagementPage", UserName);
		if(homePage.isFieldVisible(NewlyAddedUserInUserManagementPage)){
			homePage.verifyFieldExists(NewlyAddedUserInUserManagementPage);
		}else{
			EISTestBase.fail("Newly added user is not displayed after searching with the first name in User management page ");
		}
				
		Util.printInfo("Removing the newly added user ");		
		RemoveUser(UserName);		
		homePage.verifyFieldNotExists(NewlyAddedUserInUserManagementPage);		
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
