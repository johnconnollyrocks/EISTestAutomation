package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_UsageReport_VerifyCredits_AddUser extends CustomerPortalTestBase {
	
	
	public Test_Verify_UsageReport_VerifyCredits_AddUser() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		if(getEnvironment().equalsIgnoreCase("DEV")){
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		}
		Util.sleep(5000);
//		String ContractNumbers=null;
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		ArrayList<String> ContractNumbers=new ArrayList<String>();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		
		Util.printInfo("Adding a new User ");
		
		addNewUser(homePage.getConstant("EMAIL"));
		homePage.click("AssignContracts");
		homePage.click("SaveUser");
		homePage.refresh();
		List<WebElement> usersTable = driver.findElements(By.xpath(".//*[@id='results']/li"));
		for(int i=1;i<=usersTable.size();i++)
		{	
			boolean flag=false;
			Util.sleep(5000);
			String parsedUsers=homePage.createFieldWithParsedFieldLocatorsTokens("UserList",String.valueOf(i));
//			homePage.verifyFieldExists(parsedUsers);			
//			String Users=homePage.getFirstFieldLocator(parsedUsers);
			String Users1=homePage.getAttribute(parsedUsers, "title");			
			if(Users1.trim().equalsIgnoreCase(homePage.getConstant("EMAIL"))){
			String UsersContract=homePage.createFieldWithParsedFieldLocatorsTokens("UsersContractSTG",String.valueOf(i));		
			Util.sleep(5000);
			homePage.click(UsersContract);
//			for(int j=1;j<=contracts.size();j++){			
			
			String Contract=homePage.createFieldWithParsedFieldLocatorsTokens("Contracts", String.valueOf(i));
//			List<WebElement> ContractNumber=homePage.getValueFromGUI(Contract);
			Util.sleep(5000);		
			List<WebElement> ContractNumberSize=driver.findElements(By.xpath(".//*[@id='results']/li["+i+"]/section/div/div/div/div/p/following-sibling::ul/li"));
			for(int j=1;j<=ContractNumberSize.size();j++){
				String ContractNumber=driver.findElement(By.xpath(".//*[@id='results']/li["+i+"]/section/div/div/div/div/p/following-sibling::ul/li["+j+"]")).getText();
				ContractNumbers.add(ContractNumber);
			}
			Util.sleep(5000);
//			RemoveUser(i);
			homePage.refresh();
			flag=true;
			break;
			}			
		}	
		
		homePage.click("Logout");
//		LoginCustomerPortal();
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		Util.printInfo("Adding a new User in CM2");
		addNewUser(homePage.getConstant("EMAIL"));
//		homePage.click("AssignContracts");
		homePage.click("SaveUser");	
		Util.sleep(5000);
		homePage.refresh();				
		homePage.click("UsageReport");
		homePage.click("byUsers");
		int i;
		List<WebElement> ReportingUsers=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr/td/label"));
		for(i=1;i<=ReportingUsers.size();i++){
		String CM2User=testProperties.getConstant("FIRSTNAME")+" "+testProperties.getConstant("LASTNAME");
		String Users=homePage.createFieldWithParsedFieldLocatorsTokens("ReportingUser", String.valueOf(i));
		String NewUser=homePage.getValueFromGUI(Users);
		if(NewUser.trim().equalsIgnoreCase(CM2User)){
//			String CM2Contracts=homePage.createFieldWithParsedFieldLocatorsTokens("NewUser", String.valueOf(i));			
			ArrayList<String> NewUserContract=new ArrayList<String>();
			List<WebElement>  NewUserContractSize=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/td//table/tbody/tr"));		
			for(int j=1;j<=NewUserContractSize.size();j++){
				String CM2UserContract=driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/td//table/tbody/tr["+j+"]")).getText();
				NewUserContract.add(CM2UserContract);
//			assertTrue("User Under CM2 has the different Contracts of CM1", NewUserContract.contains(ContractNumbers));
//			if(ContractNumbers.contains(NewUserContract))
			assertFalse("User Under CM2 has different Contracts compare to CM1",ContractNumbers.contains(NewUserContract));
			
			}
		}
	}
		homePage.clickAndWait("users","selectAllInUP");
//		RemoveUser(i);
		homePage.refresh();
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
