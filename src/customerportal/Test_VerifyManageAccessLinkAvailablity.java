package customerportal;

import java.awt.Robot;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyManageAccessLinkAvailablity extends CustomerPortalTestBase {
	public Test_VerifyManageAccessLinkAvailablity() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_AddUserAndAssignBenifitsProductsInNAMUApplication() throws Exception {
		//setDebugMode(true);
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb=new WebDriverWait(driver,30000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));

		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.verify();
		
		Actions action = new Actions(driver);
		WebElement web = driver.findElement(By.xpath("//div[@class='manageUsers']"));
		Point coordinates = web.getLocation();
		Robot robot = new Robot();
		robot.mouseMove(coordinates.getX()+10, coordinates.getY()+75);
		Util.sleep(4000);
		homePage.verifyFieldExists("manageAccessHover");
		logoutMyAutodeskPortal();	
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("END_USER_NAME") , testProperties.getConstant("PASSWORD"));
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		
		homePage.verifyFieldNotExists("manageUsers");
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
