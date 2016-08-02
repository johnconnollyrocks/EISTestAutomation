package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_IndividualCredits_LargeAccounts extends CustomerPortalTestBase {
	public Test_Verify_IndividualCredits_LargeAccounts() throws IOException {
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
		Util.sleep(30000);
		int count=0;
		Util.printInfo("Verifying individual cloud credits for larger accounts :: ");
		
		homePage.click("reporting");
		Util.sleep(4000);
		homePage.click("byUsers");
		Util.sleep(4000);
		List<WebElement> IndividualCloudCredits=driver.findElements(By.xpath("//div/div/span[@class='denomenator']"));
		List<WebElement> AllUsers=driver.findElements(By.xpath("//td[@class='name']/label"));
		Util.printInfo("Number of users pressent :: "+AllUsers.size());
		Util.printInfo("chekcing individaul cloud credits for each user :: ");
		for(WebElement EachCloudCredit : IndividualCloudCredits){
			count=count+1;
			String EachCCDenomenator=EachCloudCredit.getText();
			if(EachCCDenomenator.contains("100")){
				assertTrue("Individual Credits shown correctly", EachCCDenomenator.contains("100"));
			}else{
				EISTestBase.fail("Individual Cloud Credits showing incorrect at row number "+count);
			}
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
