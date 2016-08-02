package customerportal;

import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_AddBulkUsers_SCAT extends CustomerPortalTestBase {
	
	
	public Test_Verify_AddBulkUsers_SCAT() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_Consumed_Users_method() throws Exception {
		//setDebugMode(true);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		if(getEnvironment().equalsIgnoreCase("DEV")){
			LaunchSCAT(testProperties.getConstant("USERNAME_SCAT"),testProperties.getConstant("PASSWORD_SCAT"));
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			Util.printInfo("Logging into SCAT :: ");
			LaunchSCAT(testProperties.getConstant("USERNAME_SCAT"),testProperties.getConstant("PASSWORD_SCAT"));
		}
		Util.sleep(5000);
		Util.printInfo("Logged into SCAT App :: ");
		homePage.click("SCATAddBulkUser");
		homePage.populateField("ContractID","110000856531");
		homePage.click("ContractSubmit");
		homePage.click("ContractBrowse");
		Util.sleep(20000);
		String RootDir=System.getProperty("user.dir");
		String AutoItPath=RootDir+"\\UploadExcel.exe";
		String RootDir1=System.getProperty("user.dir");
		String AutoItPath1=RootDir1+"\\User_Template.xls";
		Process process = new ProcessBuilder(AutoItPath,
				AutoItPath1, "Open").start();
		Util.sleep(20000);
		homePage.click("ScatSubmitExcel");
		if(homePage.isFieldVisible("BulkUsersUploadedSuccessfully")){
			homePage.verifyFieldExists("BulkUsersUploadedSuccessfully");
		}
		Util.printInfo("Verifying Bulk users status :: ");
		homePage.click("BulkUserStatus");
		String ErrorValue=homePage.getValueFromGUI("ErrorValue");
		if(Integer.valueOf(ErrorValue)>1){
			Util.printError("Errors exists for number of uers while uploading :: "+homePage.getValueFromGUI("ErrorValue"));
		}
		
		homePage.click("SCATLogout");
		Util.printInfo("Logout from SCAT :: ");
		Util.printInfo("Number of Users Added in SCAT :: 6");
		Util.printInfo("Logging into customer portal :: ");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		homePage.clickAndWait("users","selectAllInUP");
		String UserName=null;
		int count=0;
		homePage.refresh();
		Util.sleep(80000);
		homePage.refresh();
		Util.sleep(80000);
		List<WebElement> CPValidatingUsers=driver.findElements(By.xpath("//div/span[contains(text(),'End User')]"));
		
		for(WebElement EachUser : CPValidatingUsers){
			UserName=EachUser.getText();
			String CPUsers=homePage.createFieldWithParsedFieldLocatorsTokens("CPValidatingUsers", UserName);
			Util.sleep(60000);
			if(homePage.isFieldVisible(CPUsers)){
				count=count+1;
				homePage.verifyFieldExists(CPUsers);
				Util.printInfo("Removing Each User in Customer Portal :: ");
				RemoveUser(UserName);
			}else{
			homePage.click("Logout");
			Util.sleep(80000);
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
			homePage.refresh();
			Util.sleep(40000);
			homePage.refresh();
			Util.sleep(40000);
			
			if(homePage.isFieldVisible(CPUsers)){
				count=count+1;
				homePage.verifyFieldExists(CPUsers);
				Util.printInfo("Removing Each User in Customer Portal :: ");
				RemoveUser(UserName);
			}else{
				EISTestBase.fail("Users are not updated in Customer Portal ");
			}
		}
	 }		
		
		if(count<6){
			EISTestBase.fail("Users in Customer Portal not Updated Successfully");
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
