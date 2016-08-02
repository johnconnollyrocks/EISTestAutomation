package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
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
public final class Test_AddBulkUsersAndAssignBenifitsProductsInNAMUApplication extends CustomerPortalTestBase {
	public Test_AddBulkUsersAndAssignBenifitsProductsInNAMUApplication() throws IOException {
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
		Util.printInfo("Adding bulk Users");
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
			RemoveUser(arr1[i]);
			Util.sleep(4000);
		}
		homePage.click("CloseSearch");		
		}
		
		boolean flag = false;
		List<WebElement> usersTable = driver.findElements(By.xpath(".//*[@id='results']/li"));
		Util.printInfo("Users : "+usersTable.size());
		String emailIdGiven1 = homePage.getConstant("BULKINPUT");
		String emailIdGiven = emailIdGiven1.split(";")[0];
		emailIdGiven = emailIdGiven.substring(emailIdGiven.indexOf("<")+1,emailIdGiven.indexOf(">"));
		
		String emailIdGiven2 = emailIdGiven1.split(";")[1];
		emailIdGiven2 = emailIdGiven2.substring(emailIdGiven2.indexOf("<")+1,emailIdGiven2.indexOf(">"));
		
		
		Util.printInfo("emailIdGiven : "+emailIdGiven);
		Util.printInfo("emailIdGiven2 : "+emailIdGiven2);
		for(WebElement user : usersTable){
			String emailId = user.findElement(By.xpath("//section[2]//a[@class='emailAddress']")).getAttribute("title");
			Util.printInfo("emailId : "+emailId);
			if((emailId.trim().contains(emailIdGiven.trim())) || (emailId.trim().contains(emailIdGiven2.trim()))){
				flag = true;
				break;
			}
		}
		
		
		Util.sleep(5000);
		homePage.click("addUser");
		Util.sleep(5000);
		homePage.click("bulkAdd");
		Util.sleep(1000);
		homePage.populate();
		
//		if(!driver.findElement(By.xpath(".//*[@id='access-toggle']")).getAttribute("checked").contains("checked")){
//			driver.findElement(By.xpath(".//*[@id='access-toggle']")).click();
//		}
		WebElement saveContbutton = driver.findElement(By.xpath("//div[contains(@id,'save-cancel')]//a[contains(@class,'save-user')]"));
		saveContbutton.click();		
		Util.printInfo("update button present : "+flag);
		if(flag){
			driver.findElement(By.xpath(".//div[@id='add-wrapper']//div[contains(@class,'dont-update')]")).click();
		}
		Util.sleep(40000);
		if(homePage.isFieldVisible("BulkUsersEditAccess")){
			Util.printInfo("Edit Access page loaded successfully ");
		}else{
			EISTestBase.fail("Edit access page not loaded successfully, so please change CM user or else please recheck again ");
		}
		Util.sleep(4000);
		driver.findElement(By.xpath(".//*[contains(@id,'benefit-product-downloads')]/div[1]/span")).click();
		Util.sleep(2000);
		driver.findElement(By.xpath(".//*[contains(@id,'benefit-product-downloads')]/div[2]//a[contains(text(),'Assign All')]")).click();
		Util.sleep(2000);
		checkChecKBox("AssignAllTheServices","AssignAllTheServices");
		Util.sleep(8000);
		Capabilities capabilities = ((RemoteWebDriver)driver).getCapabilities();
		String browserName=capabilities.getBrowserName();
		if(browserName.equalsIgnoreCase("safari")){
		
		try{
			String GetXpath1 ="document.getElementByXPath = function(sValue){ var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0) { return a.snapshotItem(0); } };"; //document.getElementsByXPath = function(sValue){ var aResult = new Array();var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);for ( var i = 0 ; i < a.snapshotLength ; i++ ){aResult.push(a.snapshotItem(i));} return aResult;};
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String jScroll="document.getElementsByClassName('overview')[2].style.top='-1862px';document.getElementsByClassName('thumb')[2].style.top='123px';";
			js.executeScript(GetXpath1);
			Util.sleep(5000);
			js.executeScript(jScroll);
			Util.sleep(5000);
			
		}catch(Exception e ){
			e.printStackTrace();
		
			//EISTestBase.fail("Javascript failed to scroll down the scrollbar");
		}
		}
		Util.sleep(5000);
		
		checkChecKBox("AssignAllTheProducts","AssignAllTheProducts");
		Util.sleep(2000);
//		((JavascriptExecutor) driver).executeScript("return arguments[0].click()", driver.findElement(By.xpath("//input[@id='contracts-assign-all' AND @class='edit-action' AND @type='checkbox']")));
//		driver.findElement(By.xpath("//input[@id='contracts-assign-all' AND @class='edit-action' AND @type='checkbox']/following-sibling::label[@for='contracts-assign-all']")).sendKeys(Keys.SPACE);
//		driver.findElement(By.xpath(".//*[contains(@id,'services-')]/div[2]//a/span[contains(text(),'Assign All')]")).click();
		
//		driver.findElement(By.xpath(".//*[@id='products-top']/div[1]/div[2]/div/form/label")).click();
//		((JavascriptExecutor) driver).executeScript("return arguments[0].click()", driver.findElement(By.xpath("//input[@id='products-assign-all' AND @class='edit-action' AND @type='checkbox']/following-sibling::label[@for='products-assign-all']")));
//		driver.findElement(By.xpath(".//*[contains(@id,'benefits_')]/div[2]//a/span[contains(text(),'Assign All')]")).click();

		
//		homePage.click("closeAddUserWindow");
//		homePage.click("newUserEditAccessLink");
//		String benefitsSeatsAvailableForBulkUsers=homePage.getValueFromGUI("benefitsSeatsAvailableForBulkUsers");
//		Util.printInfo("Benefits Seats available for Bulk Contracts are "+benefitsSeatsAvailableForBulkUsers);
//				
//		String productsServicesForBulkUsersSeatsAvailable=homePage.getValueFromGUI("productsServicesForBulkUsersSeatsAvailable");
//		Util.printInfo("Products Seats available for Bulk Contracts are "+productsServicesForBulkUsersSeatsAvailable);
		
		Util.printInfo("Assiged Benefits & Products to Bulk users");
		
//		homePage.check("assignAllBenefits");
//		homePage.check("assignAllProducts");
		
		Util.sleep(6000);
		
//		String changedBenefitsSeatsAvailableForBulkUsers=homePage.getValueFromGUI("benefitsSeatsAvailableForBulkUsers");
//		Util.printInfo("Changed benefits Seats available for Bulk Contracts are "+changedBenefitsSeatsAvailableForBulkUsers);
//				
//		String changedProductsServicesForBulkUsersSeatsAvailable=homePage.getValueFromGUI("productsServicesForBulkUsersSeatsAvailable");
//		Util.printInfo("Changed Products Seats available for Bulk Contracts are "+changedProductsServicesForBulkUsersSeatsAvailable);
			
		homePage.click("saveButton");
		Util.sleep(40000);
		homePage.refresh();
		Util.sleep(40000);
		Util.printInfo("Verifying new user is added or not");
		
		String EndUser1=testProperties.getConstant("ENDUSEREMAIL");
		
		String UserArr[]=User1.split(";");
		
		int i=0;
		
		String User2=testProperties.getConstant("ENDUSERNAME");
		String UserArr1[]=User2.split(";");
		String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",UserArr1[i]);
		
		homePage.populateField("UserSearch", UserArr[i]);
		
		Util.sleep(4000);
		
		homePage.verifyFieldExists("testUserToggleDrawerUserPage1");
		homePage.click("testUserToggleDrawerUserPage1");
		homePage.click("CloseSearch");		
		Util.sleep(4000);
		Util.printInfo("Verifying Agreements, Benefits and Products assigned");
		homePage.populateField("UserSearch", UserArr[i+1]);
		Util.sleep(4000);
		homePage.verifyFieldExists("chooseUserToggleDrawerUserPage1");
		Util.sleep(4000);
		homePage.click("chooseUserToggleDrawerUserPage1");
		homePage.verifyFieldExists("testUserEmailAddress1");
		Util.sleep(4000);
		homePage.verify();
		homePage.click("CloseSearch");
//		homePage.hoverOver("servicesforProductHover");
		Util.sleep(2000);
		homePage.refresh();
		Util.sleep(50000);
    //	Util.printInfo("Verifying Services");
	//	homePage.verifyInstance("SERVICES_TOOL_TIP_CONTENT_CHECK");
		
		Util.printInfo("Deleting Test User");
		homePage.populateField("UserSearch", UserArr[i]);
		Util.sleep(4000);
		homePage.click("testUserRemoveLink1");
		Util.sleep(5000);
		homePage.click("testUserConfirmRemoveButton1");
		Util.sleep(5000);
		homePage.click("CloseSearch");
		Util.sleep(5000);
		Util.printInfo("Deleting Choose User");
		homePage.populateField("UserSearch", UserArr[i+1]);
		Util.sleep(5000);
		homePage.click("chooseUserRemoveLink1");
		Util.sleep(5000);
		homePage.click("chooseUserConfirmRemoveButton1");
		Util.sleep(5000);
		homePage.click("CloseSearch");
		Util.sleep(5000);
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
