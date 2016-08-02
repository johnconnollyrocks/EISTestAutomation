package customerportal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_Verify_ProductUpdatesSortByDate extends CustomerPortalTestBase{

	private ArrayList<String> expDateFilters = null;
	public String USERNAME = null;
	public String PASSWORD = null;
	

	private String[] actFilterHeadings=null;
	SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	public Test_Verify_ProductUpdatesSortByDate() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());		
	}
	@Test
	public void Test_VerfyProductUpdatesSortByDate() throws Exception {
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
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);	
					
		}
		GoToProductUpdatesPage();	  

		Util.sleep(2000);	
		Util.printInfo("Verifying the default value of Sort by is 'Date'");
		homePage.verify();
		/*Get the article release dates at one go and do sort logic here */
		String[] productDates=productUpdatePage.getMultipleTextValuesfromField("articleUpdatesDate");
		Util.printInfo("Verifying the dates of the Product updates, expected to be in descending order from the product 1 to next one.. and so on");		
		assertEquals(checkIfDatesAreInOrder(productDates, "MM/dd/yyyy"),true);
		actFilterHeadings=productUpdatePage.getMultipleTextValuesfromField("filterGroupHeading");
		Util.printInfo("Verifying the Date filter headings is as per the expected list 'Recent'/'Last Weeek'/'Last month'/'Older'");
		String expDateFilterTypes=testProperties.getConstant("DATEFILTERTYPES");
		//dump them into an array
		String[] arrExpDatetypes=expDateFilterTypes.split(",");
		expDateFilters=new ArrayList<>(Arrays.asList(arrExpDatetypes));
		//check one of the values exists
		assertEqualsWithFlags(new ArrayList<>(Arrays.asList(actFilterHeadings)), expDateFilters, true);
		
		logoutMyAutodeskPortal();
	}
	

	@After
	public void tearDown() throws Exception {

		driver.quit();
		finish();
}
}