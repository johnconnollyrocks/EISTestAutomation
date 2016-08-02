package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.webdriven.commands.Uncheck;

import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_VerifyInstallNowButton
 * Scenario2: P & S Page – Manage Access Modal – Available Versions, Assign/Un-Assign New Versions, Select All Versions and Save, Modify Permissions and Don’t Save and Modify Permissions and Save
 * @author R.Puraj
 * @version 1.0.0
 */


public final class Test_Verify_Search_FName_LName_Email_DEV extends CustomerPortalTestBase {
	WebDriverWait wb=new WebDriverWait(driver,120);
	public Test_Verify_Search_FName_LName_Email_DEV() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyInstallNowButton() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		Util.printMessage("\"All Products & Services\" page is displayed");
		
		String UsersEmailId=null;
		String UniqueEmailId=null;
		String UserNameAfterSearch=null;
		
		
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
			
			Util.sleep(8000);
			
			UserEditAccess(UserName);
			
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
			Util.sleep(8000);
			
			//**********************************************************************
			// Search for Last name
			Util.printInfo("Placing the Last name ****** " +arr[1]+ " ****** in user search field");
			homePage.populateField("UserSearch", arr[1]);
			
			Util.sleep(8000);
			//UserEditAccess(UserName);
			
			
			UserNameAfterSearch=homePage.createFieldWithParsedFieldLocatorsTokens("UserNameAfterSearch", UserName);
			if(homePage.isFieldVisible(UserNameAfterSearch)){
				Util.printInfo(" Verifying the search results after searching with last name in search field ");
				String SearchResultsAfterSearchingWithLastName=homePage.createFieldWithParsedFieldLocatorsTokens("SearchResultsAfterSearchingWithFirstName", UserName);
				if(homePage.isFieldVisible(SearchResultsAfterSearchingWithLastName)){
					assertTrue("Search results after searching with Last name ", homePage.isFieldVisible(SearchResultsAfterSearchingWithLastName));
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
			Util.sleep(4000);
			
			//*********************************************************************
					
			
			Util.printInfo(" Verifying user search field by giving email Id " +UniqueEmailId+ " of User");
			homePage.populateField("UserSearch", UniqueEmailId);
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='results']/li")));
			Util.sleep(2000);
			UserEditAccess(UserName);
			
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
	
			homePage.refresh();
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='search-users']")));
			Util.printInfo("Verifying error result by giving non existing user in user search field ");
			homePage.populateField("UserSearch", "qwqewxxxyyasdfay");
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='messages']/p/span")));
			VerifyZeroSearchRes();
			homePage.click("CloseSearch");
			
			homePage.refresh();
			homePage.refresh();
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='nav-primary']/div/ul/li[1]/a")));
			Util.sleep(20000);
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='nav-primary']/div/ul/li[1]/a")));
			Util.printInfo("******** Validating lazy loading of user search field by giving last name of user ********");
			homePage.populateField("UserSearch", "t");
			
			Util.sleep(4000);
			int HundredUsers=LazyLoading(100);
			Util.sleep(15000);
					
			
			if(HundredUsers > 50 || HundredUsers<=100){
				assertTrue("Hundred Users loaded after scrolling scroll bar ",HundredUsers>100 || HundredUsers<=100);
			}else{
				EISTestBase.fail("****** Lazy loading got failed because the number of users loaded after scrolling scroll bar in usermanagement page is "+HundredUsers+" ******");
			}
			
			System.out.println("*************** Deleted User Name **********");
			homePage.refresh();
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='results']/li")));
			Util.sleep(8000);
			RemoveUser(UserName);
			
			homePage.refresh();
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='results']/li")));
			Util.sleep(8000);		
			System.out.println("***************Search with Deleted User Name **********");
			homePage.populateField("UserSearch",UserName);
		
			Util.sleep(8000);				
			VerifyZeroSearchRes();

			
			System.out.println("***************Search with Deleted Email Address **********");
				
			homePage.populateField("UserSearch", UniqueEmailId);
			Util.sleep(8000);				
			VerifyZeroSearchRes();
			Util.sleep(4000);
			
			System.out.println("***************Add Deleted User **********");
			addUser(UniqueEmailId, arr[0], arr[1]);
			System.out.println("***************Successfully added User **********");
			break;
		}
		
		
		logoutMyAutodeskPortal();
		
		
	}
	
	public void RemoveUser(String UserName){
		homePage.populateField("UserSearch", UserName);
		String UserRemove=homePage.createFieldWithParsedFieldLocatorsTokens("RemoveUser",UserName );
		//if(homePage.verifyFieldExists(UserRemove)){
		homePage.click(UserRemove);
		String RemoveLink=homePage.createFieldWithParsedFieldLocatorsTokens("RemoveLink", UserName);
		homePage.click(RemoveLink);
		homePage.waitForFieldAbsent("RemoveLink");
		Util.printInfo("User Removed Successfully. User: "+ UserName);
		/*}else{
			Util.printInfo("Unable to  Removed User: "+ UserName);
			//EISTestBase.fail("Contract Managers cannot be removed from Users Page");
		}*/
	}
	
	public void UserEditAccess(String UserName){
		Util.printInfo("Validating User Search - Edit Access or changing the permissions ");
		String UserEditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("CPUserEditAccess", UserName);
		
		Util.sleep(8000);
		homePage.click(UserEditAccess);
		Util.sleep(6000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='edit-access']/footer/div[3]/a[1]")));
		Util.printMessage("Edit Access page is Loaded");
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		Util.sleep(6000);
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		homePage.click("SaveUser");
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='results']/li")));
		Util.sleep(8000);

		 
		driver.findElement(By.xpath("//*[@id='results']//div[2]")).click();
		
	
		
		homePage.verifyFieldExists("ProductExtensions");	
		Util.sleep(6000);
		homePage.verifyFieldExists("WebSupport");
		Util.sleep(6000);
		homePage.verifyFieldExists("ProductDownloads");
		Util.sleep(6000);
		driver.findElement(By.xpath("//*[@id='results']//div[2]")).click();
		homePage.click(UserEditAccess);
		Util.sleep(8000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='edit-access']/footer/div[3]/a[1]")));
		unCheckChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		Util.sleep(6000);
		unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		Util.sleep(6000);
		homePage.click("SaveUser");
		Util.printInfo(" Successfully Validated Search - Edit Access or changing the permissions : "+UserName);
	}
	
	public void addUser(String emailIdGiven,String FirstName, String LastName) {
		boolean flag = false;
				
		homePage.clickAndWait("addUser", "email");
		Util.sleep(6000);
		driver.findElement(By.xpath(".//*[@id='email']")).sendKeys(emailIdGiven);
		Util.sleep(4000);
		driver.findElement(By.xpath(".//*[@id='first-name']")).sendKeys(FirstName);
		Util.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='last-name']")).sendKeys(LastName);
		((JavascriptExecutor)driver).executeScript("window.scrollBy(0,6000)");
		//		homePage.click("addUserSaveAndContinueButton");
		driver.findElement(By.xpath("//*[contains(@class,'and-continue')]")).click();
		//		saveContbutton.click();
		Util.sleep(8000);
		Util.printInfo("update button present : "+flag);
		if(flag){
			//			homePage.click("dontUpdateAddUser");
			driver.findElement(By.xpath(".//div[@id='add-wrapper']//div[contains(@class,'dont-update')]")).click();
		}
		Util.sleep(4000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='edit-access']/footer/div[3]/a[1]")));
		Util.printMessage("Edit Access page is Loaded");
		
		
		/*checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		Util.sleep(3000);
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		Util.sleep(4000);
		unCheckChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		Util.sleep(3000);
		unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");*/
		
		homePage.click("SaveUser");
		homePage.refresh();
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='results']/li")));
		Util.sleep(2000);

	}
	
	public void VerifyZeroSearchRes(){
		if(homePage.isFieldVisible("ZeroSearchResultsforUser") && homePage.isFieldVisible("HelpMessage")){
			homePage.verifyFieldExists("ZeroSearchResultsforUser");
			homePage.verifyFieldExists("HelpMessage");
			homePage.click("CloseSearch");
			Util.sleep(8000);
		}else{
			EISTestBase.fail("There is no error message dispalyed for non existing user ");
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
