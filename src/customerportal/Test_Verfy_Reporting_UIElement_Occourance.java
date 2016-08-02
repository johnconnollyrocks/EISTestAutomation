package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.Util;

public class Test_Verfy_Reporting_UIElement_Occourance extends CustomerPortalTestBase{

	public Test_Verfy_Reporting_UIElement_Occourance() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_VerfyReportingUIElementOccourance() throws Exception {
		List<WebElement> noOfOccurances;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.click("reporting");
		Util.sleep(2000);
		//Validating on ALL USAGE Tab
		Util.printInfo("Validating in All Usage Tab");
		noOfOccurances=driver.findElements(By.xpath("//*[normalize-space(text())='Individual Cloud Credits']"));
		Util.printInfo("Number of occurances of 'Individual Cloud Credits' header is:"+noOfOccurances.size());
		if(noOfOccurances.size()>1){
			fail("More than one occurance of 'Individual Cloud Credits' header");
		}
		noOfOccurances=driver.findElements(By.xpath("//*[normalize-space(text())='Individual Usage']"));
		Util.printInfo("Number of occurances of 'Individual Usage' text is:"+noOfOccurances.size());
		if(noOfOccurances.size()>1){
			fail("More than one occurance of 'Individual Usage' text");
		}
		noOfOccurances=driver.findElements(By.xpath("//*[normalize-space(text())='Shared Cloud Credits']"));
		Util.printInfo("Number of occurances of 'Shared Usage' text is:"+noOfOccurances.size());
		if(noOfOccurances.size()>1){
			fail("More than one occurance of 'Shared Usage' text");
		}
		//Validating on MY USAGE Tab
		Util.printInfo("Validating in My Usage Tab");
		homePage.click("myUsageButton");
		Util.sleep(2000);
		noOfOccurances=driver.findElements(By.xpath("//*[@id='myusage-view']//*[normalize-space(text())='Individual Cloud Credits']"));
		Util.printInfo("Number of occurances of 'Individual Cloud Credits' header is:"+noOfOccurances.size());
		if(noOfOccurances.size()>1){
			fail("More than one occurance of 'Individual Cloud Credits' header");
		}
		noOfOccurances=driver.findElements(By.xpath("//*[@id='myusage-view']//*[normalize-space(text())='Individual Usage']"));
		Util.printInfo("Number of occurances of 'Individual Usage' text is:"+noOfOccurances.size());
		if(noOfOccurances.size()>1){
			fail("More than one occurance of 'Individual Usage' text");
		}
		noOfOccurances=driver.findElements(By.xpath("//*[@id='myusage-view']//*[normalize-space(text())='Shared Cloud Credits']"));
		Util.printInfo("Number of occurances of 'Shared Usage' text is:"+noOfOccurances.size());
		if(noOfOccurances.size()>1){
			fail("More than one occurance of 'Shared Usage' text");
		}
		noOfOccurances=driver.findElements(By.xpath("//*[@id='myusage-view']//*[normalize-space(text())='Autodesk 360']"));
		Util.printInfo("Number of occurances of 'Autodesk 360' text is:"+noOfOccurances.size());
		if(noOfOccurances.size()>1){
			fail("More than one occurance of 'Autodesk 360' text");
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