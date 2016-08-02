package customerportal;

import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_AddUsers_Benefits_Services_Products extends CustomerPortalTestBase {
	public Test_AddUsers_Benefits_Services_Products() throws IOException {
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
		homePage.click("users");
		String UserOne=testProperties.getConstant("EndUSer1");
		String UserTwo=testProperties.getConstant("EndUSer2");
		String UserThree=testProperties.getConstant("EndUSer3");
		String UserFour=testProperties.getConstant("EndUSer4");
		
		String UserEmailOne=testProperties.getConstant("EndUSerEmail1");
		String UserEmailTwo=testProperties.getConstant("EndUSerEmail2");
		String UserEmailThree=testProperties.getConstant("EndUSerEmail3");
		String UserEmailFour=testProperties.getConstant("EndUSerEmail4");
		
		RemoveUserBySearch(UserEmailOne,UserOne);
		RemoveUserBySearch(UserEmailTwo,UserTwo);
		RemoveUserBySearch(UserEmailThree,UserThree);
		RemoveUserBySearch(UserEmailFour,UserFour);
		
		Util.printInfo("********* Adding single User **********");
		
		Util.printInfo("Inviting a new user ");
		String Email=homePage.getConstant("EMAIL");
		
		String ActualEmail="unique.user@ssttest.net";
		
		//String FirstName=RandomStringUtils.randomAlphabetic(5);
		String User="Unique"+" "+"Name";
		
		//String User1=testProperties.getConstant("EndUser");
		
		String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",User);
		
		homePage.populateField("UserSearch", ActualEmail);
				
		Util.sleep(4000);
		
		if(homePage.isFieldVisible(Enduser1)){
			RemoveUser(User);
		 }
		
		homePage.click("CloseSearch");
		
		Util.sleep(4000);
		
		addUser(ActualEmail, "Unique");
		
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		
		Util.sleep(60000);
		homePage.click("SaveUser");
		Util.sleep(4000);
		homePage.refresh();
		Util.sleep(40000);
		String NewlyAddedUser=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUser", User);
		homePage.populateField("UserSearch", User);
		Util.sleep(40000);
		if(homePage.isFieldVisible(NewlyAddedUser)){
			 homePage.verifyFieldExists(NewlyAddedUser);
		}else{
			EISTestBase.fail("Newly Added User "+User+" doesnot exits ");
		}
		
		homePage.click(NewlyAddedUser);
		Util.sleep(4000);
		
		String NewlyAddedUserProductExtensions=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserProductExtensions", User);
		
		String NewlyAddedUserProductDownloads=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserProductDownloads", User);
		String NewlyAddedUserWebSupport=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserWebSupport", User);
		
			if(homePage.isFieldVisible(NewlyAddedUserProductExtensions)){
			assertTrue("Product Extension Benefit for newly added user is displayed ", homePage.isFieldVisible(NewlyAddedUserProductExtensions));
			}else{
			 EISTestBase.fail("No product extensions benefit is displayed for newly added user ");
		}
		
		if(homePage.isFieldVisible(NewlyAddedUserProductDownloads)){
			assertTrue("Product Download Benefit for newly added user is displayed ", homePage.isFieldVisible(NewlyAddedUserProductDownloads));
		}else{
			EISTestBase.fail("No Product Download benefit is displayed for newly added user ");
		}
		
		if(homePage.isFieldVisible(NewlyAddedUserWebSupport)){
			assertTrue("web Support Benefit for newly added user is displayed ", homePage.isFieldVisible(NewlyAddedUserWebSupport));
		}else{
			EISTestBase.fail("No web Support benefit is displayed for newly added user ");
		}
		
		Util.sleep(6000);
		
		RemoveUser(User);
		
		homePage.click("CloseSearch");
		
		/*Util.printInfo("************************************* Adding Bulk Users and Assigning Benefits/Products /Services ****************************************");
		
		homePage.click("addUser");
		homePage.click("bulkAdd");
		Util.sleep(40000);
		String BulkUsers=testProperties.getConstant("BulkData");
		homePage.populateField("BulkUserTextArea", BulkUsers);
		Util.printInfo("Bulk users");
		homePage.click("BulkUsersSave");
		Util.sleep(40000);
		if(homePage.isFieldVisible("EditAccessPage")){
			assertTrue("Edit Access page opened successfully", homePage.isFieldVisible("EditAccessPage"));
		}else{
			EISTestBase.fail("Edit access page not opened please try after someitme");
		}
		Util.sleep(40000);
		
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		
		Util.sleep(40000);
		homePage.click("SaveUser");
		
		if(homePage.isFieldVisible("AllUsers")){
			assertTrue("All added bulk users saved successfully ",homePage.isFieldVisible("AllUsers"));
		}else{
			EISTestBase.fail("Added bulk users are not saved, please recheck again ");
		}*/
		
		/*String UserOne=testProperties.getConstant("EndUSer1");
		String UserTwo=testProperties.getConstant("EndUSer2");
		String UserThree=testProperties.getConstant("EndUSer3");
		String UserFour=testProperties.getConstant("EndUSer4");
		
		String BulkUser[]=testProperties.getConstant("NewEndUser").split(";");
		for(String EachUser : BulkUser){
		String NewlyAddedBulkUser=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUser", EachUser.trim());
		if(homePage.isFieldVisible(NewlyAddedBulkUser)){
			 homePage.verifyFieldExists(NewlyAddedBulkUser);
			 Util.printInfo("Newly added bulk ## "+EachUser+" ## user exists so removing the user ");
			 RemoveUser(EachUser.trim());
		}else{
			EISTestBase.fail("Newly Added Bulk User "+EachUser+" doesnot exits in user list page");
		 }
		}*/
		
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
