package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import common.DOMXmlParser;
import common.SoapUIExampleTest;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Raghavendra Yadav
 * @version 1.0.0
 */
public final class Test_Verify_Individualgauge_UsageReport_CM extends CustomerPortalTestBase {
	
	
	public Test_Verify_Individualgauge_UsageReport_CM() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {	
		launchMyAutodeskPortal(getBaseURL()); 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(5000);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.click("reporting");
		homePage.click("byUsers");
		
		String ByUservalue=null;
		String Admingauge=null;
		String Admin=null;	
		
		String CMIndividualGauge=null;
		int j;
		int ReportUsersList=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();
		for(int i=1;i<=ReportUsersList;i++)
		{
//			 UserStatus=driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+(i+1)+"]/td[2]/div/div/span")).getText();
			 Admin=driver.findElement(By.xpath(".//*[@id='username']")).getText();
			 ByUservalue=homePage.createFieldWithParsedFieldLocatorsTokens("ByUsersList", String.valueOf(i));
			 String CMUser=homePage.getValueFromGUI(ByUservalue);
		 if(CMUser.equalsIgnoreCase(Admin))
		 	{
			 Util.printInfo("Cheking Individual Cloud Credit gauge for CM	" +Admin);
			 Admingauge=homePage.createFieldWithParsedFieldLocatorsTokens("AdminGauge", String.valueOf(i));
			 homePage.verifyFieldExists(Admingauge);
			 CMIndividualGauge=homePage.getValueFromGUI(Admingauge);
			 Util.printInfo("Individual Cloud Credit gauge value for CM "+Admin+ "is "+CMIndividualGauge);
			}
		}
		
		homePage.click("usageReport");
		
		String CMUsageReportIndividualGauge=homePage.getValueFromGUI("CMIndividualGauge");
		
		assertEquals(CMUsageReportIndividualGauge.trim(), CMIndividualGauge.trim());
		
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
