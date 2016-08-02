package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_VerifyAssignBenifitsProductsWhenSeatCountIsZeroNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyAssignBenifitsProductsWhenSeatCountIsZeroNAMUApplication extends CustomerPortalTestBase {
	public Test_VerifyAssignBenifitsProductsWhenSeatCountIsZeroNAMUApplication() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyAssignBenifitsProductsWhenSeatCountIsZeroNAMUApplication() throws Exception {
		//setDebugMode(true);
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.click("users");
		
		String User1=testProperties.getConstant("ENDUSEREMAIL");
		
		String arr[]=User1.split(";");
		
		for(int i=0;i<arr.length;i++){
		
		String User2=testProperties.getConstant("ENDUSERNAME");
		String arr1[]=User2.split(";");
		String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",arr1[i]);
		
		homePage.populateField("UserSearch", arr[i]);
		
		Util.sleep(4000);
		
		if(homePage.isFieldVisible(Enduser1)){
			RemoveUser(arr[i]);
		}
		homePage.click("CloseSearch");		
		}
		Util.sleep(5000);
		boolean flag = false;
		List<WebElement> usersTable = driver.findElements(By.xpath(".//*[@id='results']/li"));
		String emailIdGiven = "aaa.bbb@ssttest.net";
		Util.printInfo("emailIdGiven : "+emailIdGiven);
		for(WebElement user : usersTable){
			String emailId = user.findElement(By.xpath("//section[2]//a[@class='emailAddress']")).getAttribute("title");
			Util.printInfo("emailIdGiven : "+emailIdGiven);
			Util.printInfo("emailId : "+emailId);
			if(emailIdGiven.trim().equalsIgnoreCase(emailId.trim())){
				flag = true;
				break;
			}
		}
		Util.printInfo("Adding first set of Bulk users ");
		homePage.clickAndWait("addUser", "email");
		homePage.clickAndWait("bulkAdd","bulkInput");
		homePage.populate();
		WebElement saveContbutton = driver.findElement(By.xpath("//div[contains(@id,'save-cancel')]//a[contains(@class,'save-user')]"));
//		if(!driver.findElement(By.xpath(".//*[@id='access-toggle']")).getAttribute("checked").contains("checked")){
//			driver.findElement(By.xpath(".//*[@id='access-toggle']")).click();
//		}
		saveContbutton.click();
		Util.sleep(10000);
	
		Util.printInfo("update button present : "+flag);
		if(flag){
			driver.findElement(By.xpath("//div[@id='add-wrapper']//div[@data-i18n='MSG_USERS_UPDATE']")).click();
		}
		
//		String benefitsSeatsAvailableForBulkUsers=homePage.getValueFromGUI("benefitsSeatsAvailableForBulkUsers");
//		Util.printInfo("Benefits Seats available for Bulk Contracts are "+benefitsSeatsAvailableForBulkUsers);
				
		Util.sleep(10000);
		String productsServicesForBulkUsersSeatsAvailable=homePage.getValueFromGUI("productsServicesForBulkUsersSeatsAvailable");
		Util.printInfo("Products Seats available for Bulk Contracts are "+productsServicesForBulkUsersSeatsAvailable);
		
		Util.printInfo("Assigning Products to Bulk users");
		
//		homePage.check("assignAllBenefits");
		homePage.check("assignAllProducts");
//		if(driver.findElements(By.xpath(".//*[@id='products']/div[1]/article")).size()>1){
//			driver.findElement(By.xpath("//input[@id='products-assign-all']/following-sibling::label")).click();
//		}
//		else{
//			
//			driver.findElement(By.xpath(".//article[contains(@id,'benefit-')]/div[1]/button")).click();
//			driver.findElement(By.xpath(".//*[@id='product_110000716110']/div[3]/ul/li[1]/form/label")).click();
//			driver.findElement(By.xpath(".//*[@id='product_110000716110']/div[3]/ul/li[2]/form/label")).click();
//			/*List<WebElement> benefits = driver.findElements(By.xpath(".//*[contains(@id,'benefit-')]/div[3]/ul/li"));
//			for(WebElement benefit :benefits){
//				benefit.findElement(By.xpath("//form[@class='checkbox']/label")).click();
//			}*/
//		}
//		driver.findElement(By.xpath(".//*[contains(@id,'product_')]/div[2]/div[3]/div/form/label")).click();
		
//		String changedBenefitsSeatsAvailableForBulkUsers=homePage.getValueFromGUI("benefitsSeatsAvailableForBulkUsers");
//		Util.printInfo("Changed benefits Seats available for Bulk Contracts are "+changedBenefitsSeatsAvailableForBulkUsers);
				
		String changedProductsServicesForBulkUsersSeatsAvailable=homePage.getValueFromGUI("productsServicesForBulkUsersSeatsAvailable");
		Util.printInfo("Changed Products Seats available for Bulk Contracts are "+changedProductsServicesForBulkUsersSeatsAvailable);
			
		homePage.click("saveButton");
		Util.sleep(2000);
		homePage.refresh();
		Util.sleep(5000);
		// Add second set of Users
		Util.printInfo("Adding second set of Bulk users ");
		homePage.clickAndWait("addUser", "email");
		homePage.clickAndWait("bulkAdd","bulkInput");
		homePage.populateInstance("SECOND_SET_USERS");
		saveContbutton = driver.findElement(By.cssSelector("#save-cancel span.and-continue"));
		
//		if(!driver.findElement(By.xpath(".//*[@id='access-toggle']")).getAttribute("checked").contains("checked")){
//			driver.findElement(By.xpath(".//*[@id='access-toggle']")).click();
//		}
		saveContbutton.click();
		Util.sleep(4000);
	
//		 benefitsSeatsAvailableForBulkUsers=homePage.getValueFromGUI("benefitsSeatsAvailableForBulkUsers");
//		Util.printInfo("Benefits Seats available for Bulk Contracts are "+benefitsSeatsAvailableForBulkUsers);
				
		productsServicesForBulkUsersSeatsAvailable=homePage.getValueFromGUI("productsServicesForBulkUsersSeatsAvailable");
		Util.printInfo("Products Seats available for Bulk Contracts are "+productsServicesForBulkUsersSeatsAvailable);
		
//		Util.printInfo("Assigning Products to Bulk users");
//		
//		homePage.check("assignAllBenefits");
//		homePage.check("assignAllProducts");
//		
//		changedBenefitsSeatsAvailableForBulkUsers=homePage.getValueFromGUI("benefitsSeatsAvailableForBulkUsers");
//		Util.printInfo("Changed benefits Seats available for Bulk Contracts are "+changedBenefitsSeatsAvailableForBulkUsers);
//				
//		
//		changedProductsServicesForBulkUsersSeatsAvailable=homePage.getValueFromGUI("productsServicesForBulkUsersSeatsAvailable");
//		Util.printInfo("Changed Products Seats available for Bulk Contracts are "+changedProductsServicesForBulkUsersSeatsAvailable);
		
		Util.printInfo("Checking whether the seats count is zero or not" );
		homePage.isFieldPresent("seatcountwhenallseatsareassigned");
			if (homePage.isFieldPresent("seatcountwhenallseatsareassigned")) {
				Util.printInfo("Checking whether the assign checkbox is disabled when the seat count is zero  or not" );
				String checkboxState = homePage.getAttribute("checboxDisabledstateSeatsZero" , "class");
				EISTestBase.assertEquals(checkboxState.substring(12, 20).trim(), "ignore-");
				Util.printInfo("No Products added as Seat Count is ZERO");
			}
			
		homePage.click("saveButton");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);
		/*Util.printInfo("Deleting Users");
		homePage.click("testUserRemoveLink");
		homePage.click("testUserConfirmRemoveButton");
		Util.sleep(5000);*/
		homePage.refresh();
		Util.sleep(5000);
		/*homePage.click("chooseUserRemoveLink");
		homePage.click("chooseUserConfirmRemoveButton");*/
		RemoveUser(testProperties.getConstant("User1"));
		Util.sleep(5000);
		//homePage.refresh();
		Util.sleep(5000);
		/*homePage.click("testUsercRemoveLink");
		homePage.click("testUsercConfirmRemoveButton");
		Util.sleep(5000);
		homePage.refresh();*/
		RemoveUser(testProperties.getConstant("User2"));
		/*Util.sleep(5000);
		homePage.click("testUserdRemoveLink");
		homePage.click("testUserdConfirmRemoveButton");
		Util.sleep(5000);
		homePage.refresh();*/
		RemoveUser(testProperties.getConstant("User3"));
		Util.sleep(5000);
		/*homePage.click("testUsereRemoveLink");
		homePage.click("testUsereConfirmRemoveButton");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);*/
		RemoveUser(testProperties.getConstant("User4"));
//		homePage.click("testUserfRemoveLink");
//		homePage.click("testUserfConfirmRemoveButton");
//		Util.sleep(5000);
		/*homePage.click("testUsergRemoveLink");
		homePage.click("testUsergConfirmRemoveButton");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);*/
		RemoveUser(testProperties.getConstant("User5"));
		
		/*homePage.click("testUserhRemoveLink");
		homePage.click("testUserhConfirmRemoveButton");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);
		homePage.click("testUseriRemoveLink");
		homePage.click("testUseriConfirmRemoveButton");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);*/
		
		RemoveUser(testProperties.getConstant("User6"));
		
		Util.printInfo("Successfully deleted all users ");
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
