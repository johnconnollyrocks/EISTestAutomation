package customerportal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

public class Test_Verfy_ProductUpdatesSortByDate extends CustomerPortalTestBase{

	public Test_Verfy_ProductUpdatesSortByDate() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_VerfyProductUpdatesSortByDate() throws Exception {
		String myFormatString = null;
		SimpleDateFormat df;
		Date Date1;
		Date Date2;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.click("productUpdates");
		Util.sleep(2000);
		String currentlySorted=homePage.getValueFromGUI("sortBy");
		assertEquals(currentlySorted, "Date");
		String DateFromUI1=homePage.getValueFromGUI("prod1ReleaseDate");
		String DateFromUI2=homePage.getValueFromGUI("prod2ReleaseDate");
		myFormatString = "yyyy-MM-dd";
		df = new SimpleDateFormat(myFormatString);
		Date1 = df.parse(DateFromUI1);
		Date2 = df.parse(DateFromUI2);
		if (Date1.before(Date2)) {
			failTest(Date1+" should be greater than "+Date2);
		}
		String DateFromUI3=homePage.getValueFromGUI("prod3ReleaseDate");
		String DateFromUI4=homePage.getValueFromGUI("prod4ReleaseDate");
		String DateFromUI5=homePage.getValueFromGUI("prod5ReleaseDate");
		myFormatString = "yyyy-MM-dd";
		df = new SimpleDateFormat(myFormatString);
		Date Date3 = df.parse(DateFromUI3);
		Date Date4 = df.parse(DateFromUI4);
		Date Date5 = df.parse(DateFromUI5);
		if (Date3.before(Date4)) {
			failTest(Date3+" should be greater than "+Date4);
		}
		else if(Date4.before(Date5)){
			failTest(Date4+" should be greater than "+Date5);
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