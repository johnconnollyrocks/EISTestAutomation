package customerportal;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Test_Verify_ProductUpdate_FiltersContainer extends CustomerPortalTestBase{
	
	private ArrayList<String> lsUserCreds= null;
	
	
	
	public Test_Verify_ProductUpdate_FiltersContainer() throws IOException {
		super("Browser",getAppBrowser());		
	}
	
	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_Verify_ProductUpdates_Filters() throws Exception {
		//This is used to override the user name and password given in the test properties
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			//default login with CM user and it got updates
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));			
			
		}
		GoToProductUpdatesPage();
		
	}


	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
}

}
