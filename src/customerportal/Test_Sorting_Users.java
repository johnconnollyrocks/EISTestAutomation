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

public class Test_Sorting_Users extends CustomerPortalTestBase {
	public Test_Sorting_Users() throws IOException {
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
		
		ArrayList<String> CPFirstName=new ArrayList<String>();
		ArrayList<String> FirstNameSort=new ArrayList<String>();
		ArrayList<String> CPFirstNameReverse=new ArrayList<String>();
		ArrayList<String> FirstNameSortReverse=new ArrayList<String>();
		ArrayList<String> CPLastNameSort=new ArrayList<String>();
		ArrayList<String> LastNameSort=new ArrayList<String>();
		ArrayList<String> CPLastNameReverseSort=new ArrayList<String>();
		ArrayList<String> LastNameReverseSort=new ArrayList<String>();
		
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		
		Util.printInfo("*********** verifying Sorting Functionality for FirstName(A-Z) and  FirstName(Z-A) **************");
		
		Util.printInfo(" verifying sorting functionality of FirstName (A-Z) ");
		
		List<WebElement> UserNames=homePage.getMultipleWebElementsfromField("UsersSortingList");
		
		for(WebElement EachUser : UserNames){
			String User=EachUser.getText();
			String[] arr=User.split(" ");
			CPFirstName.add(arr[0].toLowerCase());
			FirstNameSort.add(arr[0].toLowerCase());
		}
		
		Collections.sort(CPFirstName);
		
		Util.printInfo("The alphabetically sorted users are :: "+CPFirstName);
		
		if(CPFirstName.equals(FirstNameSort)){
			assertTrue("The FirstName (A-Z) sorted in alphabetical order ", CPFirstName.equals(FirstNameSort));
		}else{
			EISTestBase.fail("The FirstName (A-Z) not sorted in alphabetical order " +CPFirstName);
		}
		
		Util.printInfo(" verifying sorting functionality of FirstName (Z-A) ");
		
		homePage.click("FirstNameSort");
		
		homePage.click("FirstNameLabel");
		
		Util.sleep(4000);
		
		List<WebElement> UserNames1=homePage.getMultipleWebElementsfromField("UsersSortingList");
		
		for(WebElement EachUser : UserNames1){
			String User=EachUser.getText();
			String[] arr=User.split(" ");
			CPFirstNameReverse.add(arr[0].toLowerCase());
			FirstNameSortReverse.add(arr[0].toLowerCase());
		}
		
		Collections.sort(CPFirstNameReverse);
		Collections.reverse(CPFirstNameReverse);
		
		
		Util.printInfo("The reverse sorted users are :: "+CPFirstNameReverse);
		
		if(CPFirstNameReverse.equals(FirstNameSortReverse)){
			assertTrue("The FirstName (Z-A) sorted in alphabetical order ", CPFirstNameReverse.equals(FirstNameSortReverse));
		}else{
			EISTestBase.fail("The FirstName (Z-A) not sorted in alphabetical order " +CPFirstNameReverse);
		}
		
		
		Util.printInfo("*********** verifying Sorting Functionality for LastName(A-Z) and  LastName(Z-A) **************");
		
		Util.printInfo(" verifying sorting functionality of LastName (A-Z) ");
		
		
		homePage.click("FirstNameSort");
		
		homePage.click("LastNameSort");
		
		Util.sleep(4000);

		List<WebElement> UserNames11=homePage.getMultipleWebElementsfromField("UsersSortingList");
		
		for(WebElement EachUser : UserNames11){
			String User=EachUser.getText();
			String[] arr=User.split(" ");
			CPLastNameSort.add(arr[1].toLowerCase());
			LastNameSort.add(arr[1].toLowerCase());
		}
		
		Collections.sort(CPLastNameSort);
		
		Util.printInfo("The alphabetically sorted Last Name users are :: "+CPLastNameSort);
		
		if(CPLastNameSort.equals(LastNameSort)){
			assertTrue("The LastName (A-Z) sorted in alphabetical order ", CPLastNameSort.equals(LastNameSort));
		}else{
			EISTestBase.fail("The LastName (A-Z) not sorted in alphabetical order " +CPLastNameSort);
		}
		
		Util.printInfo(" verifying sorting functionality of LastName (Z-A) ");
		
		
		homePage.click("FirstNameSort");
		
		homePage.click("LastNameSort");
		
		Util.sleep(4000);

		List<WebElement> UserNames111=homePage.getMultipleWebElementsfromField("UsersSortingList");
		
		for(WebElement EachUser : UserNames111){
			String User=EachUser.getText();
			String[] arr=User.split(" ");
			CPLastNameReverseSort.add(arr[1].toLowerCase());
			LastNameReverseSort.add(arr[1].toLowerCase());
		}
		
		Collections.sort(CPLastNameReverseSort);
		Collections.reverse(CPLastNameReverseSort);
		
		Util.printInfo("The alphabetically sorted Last Name users are :: "+CPLastNameReverseSort);
		
		if(CPLastNameReverseSort.equals(LastNameReverseSort)){
			assertTrue("The LastName (Z-A) sorted in alphabetical order ", CPLastNameReverseSort.equals(LastNameReverseSort));
		}else{
			EISTestBase.fail("The LastName (Z-A) not sorted in alphabetical order " +CPLastNameReverseSort);
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
