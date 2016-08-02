package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ManageAccessUserList extends CustomerPortalTestBase {
	public Test_Verify_ManageAccessUserList() throws IOException {
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
		int count=0;
		ArrayList<String> Array=new ArrayList<String>();
		ArrayList<String> Array1=new ArrayList<String>();
		
		Util.printInfo("Validating the test case TC55775 under user story US28128 ");
		
		Util.printInfo("Logged customer portal as CM ");
		Util.printInfo("Clicking on manage access modal ");
		homePage.click("manageAccess");
		Util.printInfo("Verifying list of users with User icon per user ");
		
		List<WebElement> EachUser=homePage.getMultipleWebElementsfromField("ManageAccessUserList");
		Util.printInfo("The total number of users pressent in manage access modal is / are "+EachUser.size());
		for(WebElement Element : EachUser){
			count=count+1;
			if(Element.isDisplayed()){
				assertTrue("The User Icon is displayed for user "+count, Element.isDisplayed());
			}else{
				EISTestBase.fail("The User Icon is not displayed for user "+count);
			}
		}
		
		Util.printInfo("Validating the test case TC55776 and TC55779 under user story US28128 ");
		
		Util.printInfo("getting manage access users list...");
		
		List<WebElement> UsersList=homePage.getMultipleWebElementsfromField("ManageAccessUsers");
		
		for(WebElement Element : UsersList){
			String UserName=Element.getText();
			Util.printInfo("Actual User Name is :: "+UserName);
			String ManageAccessLabel=homePage.createFieldWithParsedFieldLocatorsTokens("ManageAccessUserAccessLabel", UserName);
			String DownloadIcon=homePage.createFieldWithParsedFieldLocatorsTokens("DownloadIcon", UserName);
			String AccessIcon=homePage.createFieldWithParsedFieldLocatorsTokens("AccessIcon", UserName);
			String arr[]=UserName.split(" ");
			
			if(Element.isDisplayed() && homePage.isFieldVisible(ManageAccessLabel) && homePage.isFieldVisible(DownloadIcon)){
				homePage.verifyFieldExists(ManageAccessLabel);
				homePage.verifyFieldExists(DownloadIcon);
				homePage.verifyFieldExists(AccessIcon);
			}else{
				EISTestBase.fail("No Access label or No download Icon or Access Icon exists for user "+UserName);
			}
			
			Util.printInfo("The first name of the user on manage access modal is :: "+arr[0]);
			Array.add(arr[0]);
			Array1.add(arr[0]);
			
			Util.printInfo("The last name of the user on manage access modal is :: "+arr[1]);
			
			if(!(arr[0].isEmpty())){
			assertTrue("The User's first and last name exists on manage access modal for user "+UserName, !(arr[0].isEmpty()));
			}else{
				EISTestBase.fail("The first name or last name doesnot exists on manage access modal for user "+UserName);
			 }
		}
		
		Util.printInfo("Validating user list with default sort as first name in alphabetic order ");
		Collections.sort(Array);
		
		assertTrue("The First Name on Manage access modal are sorted in an alphabetical order ", Array.equals(Array1));
		
		Util.printInfo("Validating the test case TC55777 under user story US28128 ");
		
		List<WebElement> UsersEmailIds=homePage.getMultipleWebElementsfromField("ManageAccessEmailAddress");
		int count1=0;
		for(WebElement Element : UsersEmailIds){
			count1=count1+1;
			String UserEmailId=Element.getText();
			Util.printInfo("The user "+count1+" email id is :: "+UserEmailId);
			assertTrue("The User "+count1+" email id on manage access modal ", Element.isDisplayed());
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
