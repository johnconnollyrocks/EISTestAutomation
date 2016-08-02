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

public class Test_Verify_LazyLoading extends CustomerPortalTestBase {
	public Test_Verify_LazyLoading() throws IOException {
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
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		boolean flag=true;
//		for(int i=0;i<7;i++){
		List<WebElement> UsersList = driver.findElements(By.xpath(".//*[@id='results']/li/section[1]/div[2]"));
		Util.printInfo("Validating Select Option Should not exist :: ");
//		homePage.verifyFieldNotExists("SelectAllCheckBox");
		
		if(UsersList.size()==50 ){
			flag=false;
			Util.printInfo("50 Users got loaded");
			assertTrue("Fifty End Users got loaded Successfully before scroll down ", UsersList.size()==50);
		}	else{
			EISTestBase.fail("Fifty Users not loaded successfully please re check :: ");
		}
		
		/*Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();*/
		
		LazyScroll();
		
		Util.sleep(10000);
		List<WebElement> UsersListAfterScroll = driver.findElements(By.xpath(".//*[@id='results']/li/section[1]/div[2]"));
		
		if(UsersListAfterScroll.size()==100){
			Util.printInfo("100 Users got loaded");
			assertTrue("Hundred End users got loaded Successfully after scroll down ", UsersListAfterScroll.size()==100);
		}else{
			EISTestBase.fail("Hundred Users not loaded successfully please re check :: ");
		}
		
		/*Actions actions1 = new Actions(driver);
		actions1.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();*/

		LazyScroll();
		Util.sleep(10000);
		List<WebElement> UsersListAfterScroll1 = driver.findElements(By.xpath(".//*[@id='results']/li/section[1]/div[2]"));
		
		if(UsersListAfterScroll1.size()==150){
			Util.printInfo("150 Users got loaded");
			assertTrue("150 End users got loaded Successfully after scroll down ", UsersListAfterScroll1.size()==150);
		}else{
			EISTestBase.fail("150 Users not loaded successfully please re check :: ");
		}
		
		/*Actions actions11 = new Actions(driver);
		actions11.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();*/
		LazyScroll();
		Util.sleep(10000);
		List<WebElement> UsersListAfterScroll11 = driver.findElements(By.xpath(".//*[@id='results']/li/section[1]/div[2]"));
		
		if(UsersListAfterScroll11.size()==200){
			Util.printInfo("200 Users got loaded");
			assertTrue("200 End users got loaded Successfully after scroll down ", UsersListAfterScroll11.size()==200);
		}else{
			EISTestBase.fail("200 Users not loaded successfully please re check :: ");
		}
//	  }	
		
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
