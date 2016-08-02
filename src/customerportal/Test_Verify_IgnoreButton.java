package customerportal;

import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_IgnoreButton extends CustomerPortalTestBase {
	public Test_Verify_IgnoreButton() throws IOException {
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
//		Util.sleep(20000);
		homePage.click("productUpdates");
		int count=0;
		ArrayList<WebElement> arr=new ArrayList<WebElement>();
		ArrayList<WebElement> arr1=new ArrayList<WebElement>();
		List<WebElement> NumberOfProductUpdates=driver.findElements(By.xpath("//article[contains(@id,'update')]"));
		List<WebElement> IgnoreButtons=driver.findElements(By.xpath(".//*[contains(@id,'update')]/div[3]/div/div/a[2]"));
		for(WebElement IgnoreBtn : IgnoreButtons){
			arr.add(IgnoreBtn);
			}
		for(WebElement EachWebElement :  NumberOfProductUpdates){
		arr1.add(EachWebElement);
		}
		for(int i=0;i<arr1.size();i++){
		count=count+1;
		Point coordinates = arr1.get(i).getLocation();
		Robot robot = new Robot();
		robot.mouseMove(coordinates.getX(), coordinates.getY() + 65);
		Util.sleep(4000);
		if(arr.get(i).isDisplayed()){
			assertTrue("Ignore Button is pressent for product "+count,arr.get(i).isDisplayed());
		}else{
			EISTestBase.fail("Ignore Button is not pressent for product :: "+count);
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
