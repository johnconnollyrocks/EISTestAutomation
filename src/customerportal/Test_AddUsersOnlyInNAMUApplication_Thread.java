package customerportal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.Parallelized;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
@RunWith(Parallelized.class)
public final class Test_AddUsersOnlyInNAMUApplication_Thread extends CustomerPortalTestBase {
	public Test_AddUsersOnlyInNAMUApplication_Thread(String browser) throws IOException {
		super("Browser",browser,"Para");
//		super();
		
	}
	@Parameters
	public static Collection<String[]>  browserData(){
		String[][] browsers = new String[][]{{"chrome"},{"ie"},{"firefox"}};
		return Arrays.asList(browsers);
		
	}
	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_AddUsersOnlyInNAMUApplication() throws Exception {
		//setDebugMode(true);
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.printInfo("Adding a User");
		homePage.click("users");
		
		Util.sleep(2000);
		
		boolean flag = false;
		List<WebElement> usersTable = driver.findElements(By.xpath(".//*[@id='results']/li"));
		String emailIdGiven = homePage.getConstant("EMAIL");
		Util.printInfo("emailIdGiven : "+emailIdGiven);
		for(WebElement user : usersTable){
			String emailId = user.findElement(By.xpath("//section[2]//a[@class='emailAddress']")).getAttribute("title");
			Util.printInfo("emailIdGiven : "+emailIdGiven);
			Util.printInfo("emailId : "+emailId);
			if(emailIdGiven.trim().equalsIgnoreCase(emailId.trim())){
				flag = true;
				break;
			}
		}
		homePage.clickAndWait("addUser", "email");
		homePage.populate();	
				WebElement saveContbutton = driver.findElement(By.xpath("//div[contains(@id,'save-cancel')]//a[contains(@class,'save-user')]"));
		
		if(!driver.findElement(By.xpath(".//*[@id='access-toggle']")).getAttribute("checked").contains("checked")){
			driver.findElement(By.xpath(".//*[@id='access-toggle']")).click();
		}
		
		saveContbutton.click();
		Util.sleep(4000);
		//homePage.clickAndWait("saveContinueButton","benefitsSeatsAvailableForContract1");	
		
		Util.printInfo("update button present : "+flag);
		if(flag){
			driver.findElement(By.xpath("//div[@id='add-wrapper']//div[@data-i18n='MSG_USERS_UPDATE']")).click();
		}
		
		homePage.click("saveButton");
		Util.sleep(1000);
		
		Util.printInfo("Verifying new user is added or not");
		homePage.verifyFieldExists("testUserToggleDrawerUserPage");
		homePage.verify();
		Util.printInfo("Deleting Test User");
		homePage.click("testUserRemoveLink");
		homePage.click("testUserConfirmRemoveButton");
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
