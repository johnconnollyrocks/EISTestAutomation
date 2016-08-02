package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;
/*import common.objDetailedReport;*/

public class Test_Verify_ProductUpdatesmainPage extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String INSTANCE_NAME = null;
	public String SCEmailId = null;
	public String contractNumbr = "";
	public String Contracts = null;

	public Test_Verify_ProductUpdatesmainPage() throws IOException {
		super("Browser", getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {

		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyProductUpdateMainPage() throws Exception {
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			if (getEnvironment().equalsIgnoreCase("dev")) {
				USERNAME = testProperties.getConstant("USERNAME");
				EMAIL = USERNAME;
				PASSWORD = testProperties.getConstant("PASSWORD");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");
			
			}
			loginAsMyAutodeskPortalUser(USERNAME , PASSWORD);		
		}
		
		/*assertEqualsWithFlags("Prod Updates", "login", "Test is found", "Test is found");*/
		/*objDetailedReport.closeDetailedReport();*/
		GoToProductUpdatesPage();
		Util.printInfo("Verifying the Title of the Product update page");
		productUpdatePage.verifyFieldExists("productUpdatemainPageTitle");
		Util.printInfo("Verifying the Product Updates page has manage user access, if the user id is CM then expected is to have"
				+ "'Manage Users Btn.");
		if (checkIfLoggedUserIsCMOrSC()) {
			Util.printInfo("The logged user is CM or SC hence checking the Manager Users button exists");
			productUpdatePage.verifyFieldExists("managerUsersBtn");
		}
		Util.printInfo("Verifying the Product Updates is as per the UX( Mentioned in the user story:US23950");

		verifyIfTheProductUpdatesPageIsAsExpected();
		/* verify if the default value of Sort by is 'Date' */
		productUpdatePage.click("downloadselectDropDownBtn");
		Util.sleep(1000);
		productUpdatePage.verifyAll();
		Util.printInfo("Verify that when user clicks on Manage Users button the page navigates to User management page");		
		homePage.click("ManageUsers");
		Util.sleep(2000);
		boolean foundUsermgmt= driver.getCurrentUrl().contains("user-management");
		homePage.checkIfElementExistsInPage("genericLoadingIcon", 20);
		//also check All Users text is found
		assertTrue("The user is navigated to User management page when clicked on manager users button",(homePage.isFieldPresent("AllUsersInUserManagementPage")));
		
		logoutMyAutodeskPortal();

	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}