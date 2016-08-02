package customerportal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_SelectAllOption extends CustomerPortalTestBase {
	public Test_Verify_SelectAllOption() throws IOException {
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
		int count=0;
		List<WebElement> UsersList=driver.findElements(By.xpath(".//*[@id='results']/li/section[1]/div[2]"));
		
		if(UsersList.size()>=50){
			EISTestBase.fail("Please give the account which has less than 50 end users ");
		}else{
			Util.printInfo("The Number of Users pressent :: "+UsersList.size());
			Util.printInfo("Validating Select All Check box is pressent or not...");
			if(homePage.isFieldVisible("SelectAllCheckBox")){
			homePage.verifyFieldExists("SelectAllCheckBox");
			Util.printInfo("Clicking on Select All CheckBox");
			homePage.check("UserManagementPageSelectAllCheckBox");
			Util.printInfo("clicking on Actions Edit Access Link");
			homePage.click("ActionDropDownList");
			homePage.click("AllEditAccess");
			if(homePage.isFieldVisible("ActionsEditAccessWindow")){
				homePage.verifyFieldExists("ActionsEditAccessWindow");
				homePage.click("CloseEditAccess");
			}else{
				EISTestBase.fail("Edit Access Window is not displayed :: ");
			}
			Util.printInfo("Clicking on Actions --> Export All as CSV Link ");
			homePage.click("ActionDropDownList");
			homePage.click("ExportAllasCSV");
			Util.sleep(6000);
			String RootDir=System.getProperty("user.dir");
			String AutoItPath=RootDir+"\\ExportAllasCSV.exe";
			Process p1=Runtime.getRuntime().exec(AutoItPath);
			 BufferedReader in = new BufferedReader( new InputStreamReader(p1.getInputStream()));
			 String line;
			 boolean flag=false;
				while ((line=in.readLine())!=null) {
					flag=true;
					System.out.println(line);
				}
			if(flag){
				assertTrue("Export as CSV popup is pressent :: ", true);
			}else{
				EISTestBase.fail("Export as CSV popup is not pressent :: " );
			}
			}else{
				EISTestBase.fail("Select All checkbox is not pressent in User Management page for the account which has less than 50 users ");
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
