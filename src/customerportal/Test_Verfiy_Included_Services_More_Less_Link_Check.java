package customerportal;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.EISTestBase;
import common.Util;

public class Test_Verfiy_Included_Services_More_Less_Link_Check extends CustomerPortalTestBase{

	public Test_Verfiy_Included_Services_More_Less_Link_Check() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_ProductUpdatesClearAllFilters() throws Exception {
		Point coordinates;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		
		//Util.sleep(15000);
		mainWindow.select();
		Util.sleep(15000);
		//driver.findElement(By.xpath("//article[contains(@id,'BDSADV')]//button[(@class)='btn details-toggle arrow-btn']")).click();
		try{
			homePage.clickAndWait("xToggleButton", "ManageUsers");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		homePage.verifyFieldExists("includedSrevicesMoreLink");
		Util.printInfo("More Link Exists.Clicking on it");
		homePage.click("includedSrevicesMoreLink");
		
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
		String browserName=capabilities.getBrowserName();
		
		if(!browserName.equalsIgnoreCase("safari")){
			
//			WebElement web =driver.findElement(By.xpath("//div[@class='scrollbar']//div[@class='thumb']"));
			Actions action = new Actions(driver);
//			Point coordinates = web.getLocation();
			Robot robot = new Robot();
//			robot.mouseMove(coordinates.getX(), (int) (coordinates.getY() + 92.4625));
			robot.mouseMove(1162, 150);
			robot.mousePress(InputEvent.BUTTON1_MASK);
		    robot.mouseRelease(InputEvent.BUTTON1_MASK);
			action.click();
		}else {
			try{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String jScroll="document.getElementsByClassName('thumb')[0].style.top='123.521px';document.getElementsByClassName('overview')[0].style.top='-192px';";
			jse.executeScript(jScroll);
			Util.sleep(5000);
			
		}catch(Exception e ){
			e.printStackTrace();
			EISTestBase.fail("Javascript failed to scroll down the scrollbar");
		}
			}

		
		
		
		homePage.verifyFieldExists("includedSrevicesLessLink");
		Util.printInfo("Less Link Exists.Clicking on it");
		homePage.click("includedSrevicesLessLink");
		
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