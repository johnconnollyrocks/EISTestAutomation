package customerportal;

import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import common.Util;

public class Test_Add_MoreUsers extends CustomerPortalTestBase {
	public Test_Add_MoreUsers() throws IOException {
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
		
		for(int i=1;i<=500;i++){
		
		String Email=homePage.getConstant("EMAIL");
		
		String ActualEmail=Email+getUniqueId()+"@ssttest.net";
		String FirstName=RandomStringUtils.randomAlphabetic(5);
		
		addUser(ActualEmail, FirstName);
		Util.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='benefits_110000860119_0']")).click();
		Util.sleep(1000);
//		homePage.populateField("AssignContract");
		homePage.click("AssignContract1");
//		homePage.populateField("AssignWebSupport");
		homePage.click("AssignWebSupport");
//		homePage.populateField("AssignPrd");
		driver.findElement(By.xpath(".//*[@id='benefits_110000860119_0']")).click();
		homePage.click("AssignPrd");
		homePage.click("SaveUser");
		System.out.println("Number of users added :: "+i);
		Util.sleep(2000);
		homePage.refresh();
		Util.sleep(10000);
		
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
