package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import common.Util;


public class Test_Verify_ProductUpdates_DownloadSelected extends CustomerPortalTestBase{
	private ArrayList<String> lstSortOptions= null;
	public String USERNAME = null;
	
	public String PASSWORD = null;
	
	
	
	public Test_Verify_ProductUpdates_DownloadSelected() throws IOException {
		super("Browser",getAppBrowser());		
	}
	
	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_Verify_ProductUpdatesDownloadSingle_MultipleSelectedUpdates() throws Exception {
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
			if (getEnvironment().equalsIgnoreCase("dev")) {
				USERNAME = testProperties.getConstant("USERNAME");				
				PASSWORD = testProperties.getConstant("PASSWORD");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");
				
			}
			loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);	
					
		}
		
		GoToProductUpdatesPage();	
		
		// TC276 of US 1364
		
		homePage.checkIfElementExistsInPage("genericLoad" +
				"ingIcon", 20);
		Util.printInfo("Verify that select All checkbox enabled when clicked on it in the Product updates");
		productUpdatePage.check("selectAllUpdatesCheckbox");		
		assertEqualsWithFlags("Product Updates Page","selectAllUpdatesCheckbox", (productUpdatePage.isChecked("selectAllUpdatesCheckbox")), true);
		Util.printInfo("Verify that the individual check boxes are selected when select all Checkbox is selected");		
		assertEqualsWithFlags("Product Updates Page","individualProdUpdatesCheckbox", (productUpdatePage.checkIfAllCheckboxesAreChecked("individualProdUpdatesCheckbox")), true);	
		
		//Now check if any one of the check box is unchecked then select All checkboxes should be unchecked
		Util.printInfo("Verify that the Select All checkbox is unchecked when any of the individual product update checkbox is unchecked");		
		productUpdatePage.uncheck("individualProdUpdatesCheckbox");
		assertEqualsWithFlags("Product Updates Page","selectAllUpdatesCheckbox", (productUpdatePage.isChecked("selectAllUpdatesCheckbox")), false);
		
		//now select all the individual checkboxes and see that SelectAll checkbox is checked.
		Util.printInfo("Verify that the Select All checkbox is checked when all the individual product checkboxes are checked");		
		productUpdatePage.checkAllCheckboxes("individualProdUpdatesCheckbox", true);
		assertEqualsWithFlags("Product Updates Page","selectAllUpdatesCheckbox", (productUpdatePage.isChecked("selectAllUpdatesCheckbox")), true);
		
		// TC276 of US 1364
		//verify if the downloads are done Locally do this wr to each sort by options
		String sortByOptions= testProperties.getConstant("SORTBYOPTIONS");
		lstSortOptions= new ArrayList<>(Arrays.asList(sortByOptions.split(",")));
		//Needs to verify for all the sort by options 
		for(String sortOption: lstSortOptions){			
		productUpdatePage.click("sortByDropdownBtn");
		Util.sleep(2000);
		productUpdatePage.parseFieldLocatorsTokens("selectSortbyValueInDropdown", sortOption);
		Util.printInfo("Changing the Sortby option and verify that Download work as expected");
		productUpdatePage.hoverOver("selectSortbyValueInDropdown");
		productUpdatePage.clickUsingLowLevelActions("selectSortbyValueInDropdown");
		Util.sleep(2000);
		
		
		}
		
		
		
		
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
}

}
