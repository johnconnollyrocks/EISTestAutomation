package customerportal;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_Verify_ProductUpdatesSortByOptions extends CustomerPortalTestBase{
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	
	public Test_Verify_ProductUpdatesSortByOptions() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verify_ProductUpdatesSortByOption() throws Exception {
		//For every Sort by type need to Logout and login. Otherwise SOrt by selection is not working correctly
		LoginAndGoProductUpdatesPage();
		//Do this for all Sort by Options
		Util.sleep(2000);		
		Util.printInfo("******************************************************************");
		Util.printInfo("Verify Sort by functionality for 'Date'");
		checkProductUpdateSortByFeatures("Date", "DATELIST");
		logoutMyAutodeskPortal();
		LoginAndGoProductUpdatesPage();
		Util.printInfo("******************************************************************");
		Util.printInfo("Verify Sort by functionality for 'Type'");
		checkProductUpdateSortByFeatures("Type", "TYPELIST");
		logoutMyAutodeskPortal();
		Util.sleep(2000);
		LoginAndGoProductUpdatesPage();	
		Util.printInfo("******************************************************************");
		Util.printInfo("Verify Sort by functionality for 'Status'");
		checkProductUpdateSortByFeatures("Status", "STATUSLIST");
		logoutMyAutodeskPortal();
		Util.sleep(2000);
		LoginAndGoProductUpdatesPage();
		Util.printInfo("******************************************************************");
		Util.printInfo("Verify Sort by functionality for 'Product'");
		checkProductUpdateSortByFeatures("Product", "PRODUCTSLIST");
		logoutMyAutodeskPortal();
		Util.sleep(2000);
		LoginAndGoProductUpdatesPage();
		Util.printInfo("******************************************************************");
		Util.printInfo("Verify Sort by functionality for 'Severity'");
		checkProductUpdateSortByFeatures("Severity", "SEVERITYLIST");
		logoutMyAutodeskPortal();
	}
	@Override
	public void LoginAndGoProductUpdatesPage() throws Exception {
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
				PASSWORD = testProperties.getConstant("PASSWORD");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USERNAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");
			
			}
			loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);		
		}
		GoToProductUpdatesPage();


	}
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}