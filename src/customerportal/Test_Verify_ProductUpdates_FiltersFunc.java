package customerportal;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test_Verify_ProductUpdates_FiltersFunc extends CustomerPortalTestBase {
	private ArrayList<String> lsactgrpFacetOptions= null;
	private ArrayList<String> lsexpGrpFacetoptions=null;
	private ArrayList<String> lsActGrpHeaders= null;
	private ArrayList<String> lsactProdUpdFacetOptNames=null;
	private ArrayList<String> lstFilterPillBtns=null;
	private String sortByOption="Date"; 
	
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	
	
	public Test_Verify_ProductUpdates_FiltersFunc() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_ProductUpdates_FiltersBasedOnTheDate() throws Exception {
		//Get the Login credentials from Jenkins if provided else go with one given in properties file
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Dllata From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("***=*********************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USERNAME") , testProperties.getConstant("PASSWORD"));		
		}
		GoToProductUpdatesPage();
		String[] filteredGrpNames=testProperties.getConstant("FILTEROPTIONS").split(",");
		verifyClearFiltersFunctionalityForAllFilterGroups(filteredGrpNames);
		logoutMyAutodeskPortal();
		
		
	}
	
	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
	
}

