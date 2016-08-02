package customerportal;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.EISTestBase;
import common.Util;

public class Test_verify_UserManagement_ContractReportPages extends CustomerPortalTestBase {
	public Test_verify_UserManagement_ContractReportPages() throws IOException {
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
		Util.sleep(2000);
		String UsersEmailId=null;
		String UniqueEmailId=null;
		String UserNameAfterSearch=null;
		Actions action=new Actions(driver);
		Util.printInfo("Clicking on Usermanagement page");
		
		homePage.waitForField("users", true);
		homePage.click("users");
		
		Util.printInfo("***************** Validating UserManagement Search Functionality In DEV Environment ******************");
		
		List<WebElement> ListOfUsers=homePage.getMultipleWebElementsfromField("DEV_UsersList");
		
		for(WebElement User : ListOfUsers ){
			String UserName=User.getText();
			boolean flag=true;
			String[] arr=UserName.split(" ");
			Util.printInfo("Searching for user ********" +arr[0]+ "******* through search field in Usermanagement page");
			Util.sleep(6000);
			User.click();
			Util.sleep(6000);
			UsersEmailId=homePage.createFieldWithParsedFieldLocatorsTokens("UsersEmailId", UserName);
			UniqueEmailId=homePage.getValueFromGUI(UsersEmailId);
			Util.sleep(6000);
			User.click();
			Util.sleep(6000);
			Util.printInfo("Placing the first name ****** " +arr[0]+ " ****** in user search field");
			homePage.populateField("UserSearch", arr[0]);
			action.sendKeys(Keys.ENTER).build().perform();
			Util.sleep(8000);
			UserNameAfterSearch=homePage.createFieldWithParsedFieldLocatorsTokens("UserNameAfterSearch", UserName);
			if(homePage.isFieldVisible(UserNameAfterSearch)){
				Util.printInfo(" Verifying the search results after searching with first name in search field ");
				String SearchResultsAfterSearchingWithFirstName=homePage.createFieldWithParsedFieldLocatorsTokens("SearchResultsAfterSearchingWithFirstName", UserName);
				if(homePage.isFieldVisible(SearchResultsAfterSearchingWithFirstName)){
					assertTrue("Search results after searching with first name ", homePage.isFieldVisible(SearchResultsAfterSearchingWithFirstName));
					List<WebElement> ListOfUsers1=homePage.getMultipleWebElementsfromField("DEV_UsersList");
					Util.printInfo("Search results found, so the number of users displayed after search is / are ::  " +ListOfUsers1.size());					
					if(ListOfUsers1.size() < 50){
					if(homePage.isFieldVisible("selectAllInUP")){
						Util.printInfo("Number of users pressent after search is less than 50, So verifying whether select All check box is pressent or not ");
						assertTrue("Select All check box is displayed for users lessthan 50..", homePage.isFieldVisible("selectAllInUP"));
					}else{
						EISTestBase.fail("****** Select All check box is not displayed for users lessthan 50...******");				
					 }
					}
				}else{
					EISTestBase.fail("********* There are no results found after searching in search field in usermanagement page *********");				
				}
			}
			homePage.click("CloseSearch");
			Util.sleep(40000);
			
			Util.printInfo(" Verifying user search field by giving email Id " +UniqueEmailId+ " of User");
			homePage.populateField("UserSearch", UniqueEmailId);
			action.sendKeys(Keys.ENTER).build().perform();
			Util.sleep(4000);
			
			if(homePage.isFieldVisible(UserNameAfterSearch)){
				Util.printInfo(" Verifying the search results after searching with email id in user search field ");
				String SearchResultsAfterSearchingWithEmailId=homePage.createFieldWithParsedFieldLocatorsTokens("SearchResultsAfterSearchingWithFirstName", UserName);
				if(homePage.isFieldVisible(SearchResultsAfterSearchingWithEmailId)){
					assertTrue("Search results after searching with first name ", homePage.isFieldVisible(SearchResultsAfterSearchingWithEmailId));
					List<WebElement> ListOfUsers1=homePage.getMultipleWebElementsfromField("DEV_UsersList");
					Util.printInfo("Search results found, so the number of users displayed after search is / are ::  " +ListOfUsers1.size());					
					if(ListOfUsers1.size() < 50){
					if(homePage.isFieldVisible("selectAllInUP")){
						Util.printInfo("Number of users pressent after search is less than 50, So verifying whether select All check box is pressent or not ");
						assertTrue("Select All check box is displayed for users lessthan 50..", homePage.isFieldVisible("selectAllInUP"));
					}else{
						EISTestBase.fail("****** Select All check box is not displayed for users lessthan 50...******");				
					 }
					}else{
						assertTrue("Number of users pressent after search is greater than 50,so verifying the Select All checkbox should not pressent",(homePage.verifyFieldNotExists("selectAllInUP")));
					}
				}else{
					EISTestBase.fail("********* There are no results found after searching in user search field in usermanagement page *********");				
				}
			}			
			homePage.click("CloseSearch");
			Util.sleep(4000);
			Util.printInfo("Verifying error result by giving non existing user in user search field ");
			homePage.populateField("UserSearch", "xxxyyy");
			Util.sleep(8000);
			action.sendKeys(Keys.ENTER).build().perform();
			Util.sleep(8000);
			if(homePage.isFieldVisible("ZeroSearchResultsforUser") && homePage.isFieldVisible("HelpMessage")){
				homePage.verifyFieldExists("ZeroSearchResultsforUser");
				homePage.verifyFieldExists("HelpMessage");
				homePage.click("CloseSearch");
				Util.sleep(8000);
			}else{
				EISTestBase.fail("There is no error message dispalyed for non existing user ");
			}
			
			Util.printInfo("******** Validating lazy loading of user search field by giving last name of user ********");
			homePage.populateField("UserSearch", arr[1]);
			action.sendKeys(Keys.ENTER).build().perform();
			Util.sleep(8000);
			int HundredUsers=LazyLoading(100);
			Util.sleep(8000);
			int HundredandFiftyUsers=LazyLoading(150);
			Util.sleep(8000);
			int TwoHundredUsers=LazyLoading(200);
			Util.sleep(8000);
			
			if(HundredUsers==100){
				assertTrue("Hundred Users loaded after scrolling scroll bar ",HundredUsers==100);
			}else{
				EISTestBase.fail("****** Lazy loading got failed because the number of users loaded after scrolling scroll bar in usermanagement page is "+HundredUsers+" ******");
			}
			
			if(HundredUsers==150){
				assertTrue("Hundred and fifty Users loaded after scrolling scroll bar ",HundredandFiftyUsers==150);
			}else{
				EISTestBase.fail("****** Lazy loading got failed because the number of users loaded after scrolling scroll bar in usermanagement page is "+HundredandFiftyUsers+" ******");
			}
			
			break;
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
