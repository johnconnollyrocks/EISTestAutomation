package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import common.Util;

public class Test_Verify_Disabled_checkBox_OfCM extends CustomerPortalTestBase {

	public Test_Verify_Disabled_checkBox_OfCM() throws IOException {
		super("Browser",getAppBrowser());
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verfy_ContractNumbers() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		Util.sleep(5000);
		String USERNAME = null;
		String EMAIL = null;
		String USER_NAME = null;
		String PASSWORD = null;
		String INSTANCE_NAME = null;
		
		if(getEnvironment().equalsIgnoreCase("DEV")){
			 USERNAME = testProperties.getConstant("USERNAME_DEV");
			 EMAIL = testProperties.getConstant("EMAIL_DEV");
			 USER_NAME = testProperties.getConstant("USER_NAME_DEV");
			 PASSWORD = testProperties.getConstant("PASSWORD_DEV");
			 INSTANCE_NAME = "NEW_USER_DEV";
			
			}
			else if(getEnvironment().equalsIgnoreCase("STG")){
				 USERNAME = testProperties.getConstant("USER_NAME_STG");
//				 EMAIL = testProperties.getConstant("EMAIL_STG");
				 EMAIL = "automation_cm_769593@ssttest.net";
				 USER_NAME = testProperties.getConstant("c");
				 PASSWORD = testProperties.getConstant("PASSWORD_STG");
				 INSTANCE_NAME = "NEW_USER_STG";
			}
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		String UserInfo=null;
		mainWindow.select();
		Util.sleep(5000);
		
		homePage.click("users");
		Util.sleep(4000);
		String CM1 =testProperties.getConstant("USER_NAME_STG");
		String CM2=testProperties.getConstant("CM2");
		Util.sleep(5000);
		int UsersList=driver.findElements(By.xpath(".//*[@id='results']/li")).size();
		
		for(int rownum=1;rownum<=UsersList;rownum++){
		
		String EachUser=homePage.createFieldWithParsedFieldLocatorsTokens("EachUser", String.valueOf(rownum));
		homePage.click(EachUser);
			
		String  CMlst=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(rownum));
		UserInfo=homePage.getValueFromGUI(CMlst);
		
		if(UserInfo.contains("Contract Manager")){
			Util.sleep(2000);
				if(CM1!=CM2){
					String AnotherCM=homePage.createFieldWithParsedFieldLocatorsTokens("EndUserEditAccess", String.valueOf(rownum));
					homePage.click(AnotherCM);
			//		verifyDisAbledCheckbox("AssignAllBenifitsForCM", "class");
					//homePage.clickAndWait("saveButton","editAccess");
					
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