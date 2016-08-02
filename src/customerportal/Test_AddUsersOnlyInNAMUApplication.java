package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_AddUsersOnlyInNAMUApplication extends CustomerPortalTestBase {
	public Test_AddUsersOnlyInNAMUApplication() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_AddUsersOnlyInNAMUApplication() throws Exception {
		//setDebugMode(true);
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb=new WebDriverWait(driver,30000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.printInfo("Adding a User");
		homePage.click("users");
		
		Util.sleep(2000);
		
		//String ActualEmail=homePage.getConstant("EMAIL");
		
		String User1="Unique"+" "+"Name";
				
		String Useremail=testProperties.getConstant("EMAIL");
		
		String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",User1);
		homePage.populateField("UserSearch", Useremail);
		
		Util.sleep(4000);
		
		if(homePage.isFieldVisible(Enduser1)){
			RemoveUser(User1);
		}
		Util.sleep(20000);
		homePage.click("CloseSearch");
		Util.sleep(5000);
		addUser(Useremail, "Unique");
		Util.sleep(5000);
		
        //WebElement saveContbutton = driver.findElement(By.xpath("//div[contains(@id,'save-cancel')]//a[contains(@class,'save-user')]"));
		Util.printInfo("Assigning Benefits & Products to the user");
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		
		homePage.click("saveButton");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);
		Util.printInfo("Verifying new user is added or not");
		Util.sleep(10000);
		
		//homePage.clickAndWait("saveContinueButton","benefitsSeatsAvailableForContract1");	
		
		homePage.populateField("UserSearch", Useremail);
		Util.sleep(10000);
		String NewlyAddedEndUser=homePage.createFieldWithParsedFieldLocatorsTokens("testUserToggleDrawerUserPage", Useremail);
		homePage.verifyFieldExists(NewlyAddedEndUser);
		
		Util.sleep(1000);
		homePage.click(NewlyAddedEndUser);
		
		String NewlyAddedUserProductExtensions=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserProductExtensions", User1);
		String NewlyAddedUserWebSupport=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserWebSupport", User1);
		String NewlyAddedUserProductDownloads=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserProductDownloads", User1);

		if(homePage.isFieldVisible(NewlyAddedUserProductExtensions)){
			homePage.verifyFieldExists(NewlyAddedUserProductExtensions);
		}else{
			EISTestBase.fail("No product extensions benefit is displayed for newly added end user "+User1);
		}
		
		if(homePage.isFieldVisible(NewlyAddedUserWebSupport)){
			homePage.verifyFieldExists(NewlyAddedUserWebSupport);
		}else{
			EISTestBase.fail("No Web Support benefit is displayed for newly added end user "+User1);
		}
		
		if(homePage.isFieldVisible(NewlyAddedUserProductDownloads)){
			homePage.verifyFieldExists(NewlyAddedUserProductDownloads);
		}else{
			EISTestBase.fail("No Web Support benefit is displayed for newly added end user "+User1);
		}
		
		Util.printInfo("Deleting newly added User" +User1);
		RemoveUserBySearch(Useremail, User1);
		Util.sleep(4000);
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
