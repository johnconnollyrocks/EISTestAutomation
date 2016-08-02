package customerportal;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import common.DOMXmlParser;
import common.SoapUIExampleTest;
import common.Util;
import customerportal.UserDetailsDTO;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_Usage_By_User extends CustomerPortalTestBase {
	
	
	public Test_Verify_Usage_By_User() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_Usage_By_User_method() throws Exception {
		//setDebugMode(true);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		List<UserDetailsDTO> userDetailsList  = getUserDetails();
		Util.printInfo("userDetailsList : "+userDetailsList);
		homePage.click("reporting");
		Util.sleep(2000);
		homePage.click("byUsers");
		Util.sleep(2000);
		
		int rows = driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();
		
		for(int i =1;i<=rows;i++){
			
			String name = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/*[@class='name']")).getText().trim();
			String email = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/*[@class='email']")).getText().trim();
			String contract = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/*[@class='contract-number']")).getText().trim();
			String type = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/*[@class='contract-type']")).getText().trim();
			
			for(UserDetailsDTO userDetail : userDetailsList){
				if(userDetail.getUserName().equalsIgnoreCase(name)){
					
					Map<String, String> contraMap = userDetail.getContractWithType();
					assertEquals(name, userDetail.getUserName());
					assertEquals(email, userDetail.getEmailAddrs());
					assertTrue("should contain contract from usage report",contraMap.containsKey(contract));
					assertEquals(type, contraMap.get(contract));
					
				}
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
