package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_AddUserAndAssignBenifitsProductsInNAMUApplication extends CustomerPortalTestBase {
	public Test_AddUserAndAssignBenifitsProductsInNAMUApplication() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_AddUserAndAssignBenifitsProductsInNAMUApplication() throws Exception {
		//setDebugMode(true);
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb=new WebDriverWait(driver,30000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.click("users");
		Util.sleep(20000);
		Util.printInfo("Adding a new User ");
		
		String ActualEmail=homePage.getConstant("EMAIL");
		//String FirstName=RandomStringUtils.randomAlphabetic(5);
		
		String User1="Unique"+" "+"Name";
				
		String Useremail=testProperties.getConstant("EMAIL");
		
		String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",User1);
		homePage.populateField("UserSearch", Useremail);
		
		Util.sleep(4000);
		
		if(homePage.isFieldVisible(Enduser1)){
			RemoveUser(User1);
		}
		Util.sleep(40000);
		homePage.click("CloseSearch");
		addUser(ActualEmail, "Unique");
		Util.sleep(5000);
		/*boolean flag = false;
		List<WebElement> usersTable = driver.findElements(By.xpath(".//*[@id='results']/li"));
		String emailIdGiven = homePage.getConstant("EMAIL");
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
		Util.printInfo("Adding a User");
		homePage.clickAndWait("addUser", "email");
		homePage.populate();
		WebElement saveContbutton = driver.findElement(By.xpath("//div[contains(@id,'save-cancel')]//a[contains(@class,'save-user')]"));
		
//		if(!driver.findElement(By.xpath(".//*[@id='access-toggle']")).getAttribute("checked").contains("checked")){
//			driver.findElement(By.xpath(".//*[@id='access-toggle']")).click();
//		}
		
		saveContbutton.click();
		Util.sleep(2000);
		Util.printInfo("update button present : "+flag);
		if(flag){
			driver.findElement(By.xpath("//div[@id='add-wrapper']//div[@data-i18n='MSG_USERS_UPDATE']")).click();
		}
		Util.sleep(4000);*/
	
//		homePage.click("closeAddUserWindow");
//		homePage.click("newUserEditAccessLink");
		
//		String benefitsSeatsAvailableForContract1=homePage.getValueFromGUI("benefitsSeatsAvailableForContract1");
//		Util.printInfo("Benefits Seats available for Contract 1 are "+benefitsSeatsAvailableForContract1);
//				
//		String productSeatsAvailableForContract1=homePage.getValueFromGUI("productsServicesFirstProductSeatsAvailable");
//		Util.printInfo("Products Seats available for Contract 1 are "+productSeatsAvailableForContract1);
		
//		homePage.verifyInstance("BENEFITS_PRODUCTS_SEAT_COUNT_CHECK");
		Util.printInfo("Assigning Benefits & Products to the user");
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		homePage.check("assignAllBenefits");
	//	assignAllBenefits();
		//assignAllProducts();
//		String changedBenefitsSeatsAvailableForContract=homePage.getValueFromGUI("benefitsSeatsAvailableForContract1");
//		Util.printInfo("Changed benefits Seats available for the Contract are "+changedBenefitsSeatsAvailableForContract);
//				
//		String changedProductSeatsAvailableForContract1=homePage.getValueFromGUI("productsServicesFirstProductSeatsAvailable");
//		Util.printInfo("Changed Products Seats available for the Contract are "+changedProductSeatsAvailableForContract1);
			
		homePage.click("saveButton");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(5000);
		Util.printInfo("Verifying new user is added or not");
		Util.sleep(10000);
		
		String NewlyAddedEndUser=homePage.createFieldWithParsedFieldLocatorsTokens("testUserToggleDrawerUserPage", Useremail);
		homePage.verifyFieldExists(NewlyAddedEndUser);
		
		homePage.click(NewlyAddedEndUser);
		
		Util.sleep(3000);
		
		/*String NewlyAddedUserEmailAdress=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserEmailAddress", Useremail);
		homePage.verifyFieldExists(NewlyAddedUserEmailAdress);*/
		
		Util.printInfo("Verifying Agreements, Benefits and Products assigned");
		
		String NewlyAddedUserProductExtensions=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserProductExtensions", User1);
		String NewlyAddedUserWebSupport=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserWebSupport", User1);
		String NewlyAddedUserProductDownloads=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserProductDownloads", User1);

		if(homePage.isFieldVisible(NewlyAddedUserProductExtensions)){
			homePage.verifyFieldExists(NewlyAddedUserProductExtensions);
		}else{
			EISTestBase.fail("No product extensions benefit is displayed for newly added end user "+User1);
		}
		
		if(homePage.isFieldVisible(NewlyAddedUserWebSupport)){
			homePage.verifyFieldExists(NewlyAddedUserWebSupport);
		}else{
			EISTestBase.fail("No Web Support benefit is displayed for newly added end user "+User1);
		}
		
		if(homePage.isFieldVisible(NewlyAddedUserProductDownloads)){
			homePage.verifyFieldExists(NewlyAddedUserProductDownloads);
		}else{
			EISTestBase.fail("No Web Support benefit is displayed for newly added end user "+User1);
		}
		
		ArrayList<String> ListOfProducts = new ArrayList<String>();
		ArrayList<String> ListOfEndUserProducts = new ArrayList<String>();
		String ProductsList=homePage.createFieldWithParsedFieldLocatorsTokens("EnduserProductsList", User1);
		List<WebElement> EnduserProductsList=homePage.getMultipleWebElementsfromField(ProductsList);
		if(EnduserProductsList.size()!=0){
		for(WebElement EachProduct : EnduserProductsList){
			Util.printInfo("The total number of products assigned to the end user is / are :: "+EnduserProductsList.size());
			Util.printInfo("The Assigned product for end user "+User1+" is  :: "+EachProduct.getText());
			
			if(EachProduct.getText().contains("Autodesk")){
				String[] arr=EachProduct.getText().split("Autodesk");
				ListOfProducts.add(arr[1]);
			}else{
				ListOfProducts.add(EachProduct.getText());
			}
		 }
		}else{
			EISTestBase.fail("No products assigned for the newly added end user or products are not getting displayed, please recheck again ");
		}
		
		
		
		
		//homePage.verify();
		
	//	homePage.hoverOver("servicesforProductHover");
		Util.sleep(2000);
    //	Util.printInfo("Verifying Services");
	//	homePage.verifyInstance("SERVICES_TOOL_TIP_CONTENT_CHECK");
		Util.printInfo("Deleting Test User");
		RemoveUser(User1);
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
