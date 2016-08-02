package customerportal;

import java.io.IOException;
import java.util.ArrayList;
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

public class Test_AssignBenefits_ProductsFor_EndUser extends CustomerPortalTestBase {
	public Test_AssignBenefits_ProductsFor_EndUser() throws IOException {
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
		String UserRole=null;
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		
		Util.printInfo("*********** Validating Assign or unassign of benefits/products to EndUser In DEV Environment ***************");
		
		List<WebElement> ListOfUsers=homePage.getMultipleWebElementsfromField("DEV_UsersList");
		
		//for(WebElement User : ListOfUsers ){
			//String UserName=User.getText();
			//User.click();
			/*count=count+1;
			String AdminRole=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(count));
			UserRole=homePage.getValueFromGUI(AdminRole);*/
			
			String ActualEmail=testProperties.getConstant("ENDUSERNAME");
			String FirstName=RandomStringUtils.randomAlphabetic(5);
			//String User1=FirstName+" "+"User";
			
			String User1=testProperties.getConstant("EndUser");
			
			String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",User1);
			
			homePage.populateField("UserSearch", User1);
			
			Util.sleep(4000);
			
			if(homePage.isFieldVisible(Enduser1)){
				RemoveUser(User1);
			}
			
			homePage.click("CloseSearch");
			Util.sleep(4000);
			
			addUser(ActualEmail, FirstName);
			
			checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
			
			checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
			
			homePage.click("SaveUser");
			
			Util.sleep(4000);
			
			/*if(UserName.contains("EndUser")){
				String EditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("EditAccess", String.valueOf(count));
				homePage.click(EditAccess);
				
				checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
				
				checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
				
				homePage.click("SaveUser");
				homePage.refresh();
				Util.sleep(4000);
				User1.click();				
				Util.sleep(4000);*/
			
				homePage.refresh();
				Util.sleep(20000);
				
				String Enduser=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",User1);
				homePage.click(Enduser);
				
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
				
				/*if(homePage.isFieldVisible(NewlyAddedUserProductDownloads)){
					homePage.verifyFieldExists(NewlyAddedUserProductDownloads);
				}else{
					EISTestBase.fail("No ProductDownloads benefit is displayed for newly added end user "+User1);
				}*/
				
				ArrayList<String> ListOfProducts = new ArrayList<String>();
				ArrayList<String> ListOfEndUserProducts = new ArrayList<String>();
				String ProductsList=homePage.createFieldWithParsedFieldLocatorsTokens("EnduserProductsList", User1);
				List<WebElement> EnduserProductsList=homePage.getMultipleWebElementsfromField(ProductsList);
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
				Util.printInfo(" Logging out from Customer portal ");
				
				homePage.click("Logout");
				
				Util.printInfo("Logged out Successfully from customer portal as Contract Manager ");
				
				Util.sleep(4000);
				
				Util.printInfo(" Logging in as End User ");
				
				loginAsMyAutodeskPortalUser(testProperties.getConstant("ENDUSERNAME") , testProperties.getConstant("ENDUSERPASSWORD"));
				
				if(homePage.isFieldVisible("NewPassword")){
					EISTestBase.fail("End User " +testProperties.getConstant("ENDUSERNAME")+ " looking for password change, so please reset the password and login ");
				}
				
				homePage.click("EnduserProductPage");
				
				WebDriverWait wait=new WebDriverWait(driver, 60000);
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'Products')]")));
				
				if(!homePage.isFieldVisible("DownloadProducts")){
				
				List<WebElement> EndUserContractNumbers=homePage.getMultipleWebElementsfromField("EndUserContractNumbers");
				
				for(WebElement EachContract : EndUserContractNumbers){
					String EndUserProduct=homePage.createFieldWithParsedFieldLocatorsTokens("EndUserProduct", EachContract.getText());
					String Product=homePage.getValueFromGUI(EndUserProduct);
					ListOfEndUserProducts.add(Product);
				 }
				}else{
					EISTestBase.fail("The assigned products/services to an end user are not reflected in end user login products page ");
				}
				
				if(ListOfProducts.equals(ListOfEndUserProducts)){
					assertTrue("The assigned products/services to an end user are reflected in end user login products page ", ListOfProducts.equals(ListOfEndUserProducts));
				}else{
					EISTestBase.fail("The assigned products/services to an end user are not reflected in end user login products page ");
				}
				
			
			homePage.click("Logout");
			
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
			
			homePage.waitForField("users", true);
			homePage.clickAndWait("users","selectAllInUP");
			
			//List<WebElement> ListOfUsers1=homePage.getMultipleWebElementsfromField("DEV_UsersList");
			
			/*for(WebElement User11 : ListOfUsers ){
				String UserName1=User11.getText();
				User11.click();
				count=count+1;
				String AdminRole1=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(count));
				UserRole=homePage.getValueFromGUI(AdminRole);
				
				if(UserName.contains("EndUser")){
					String EditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("EditAccess", String.valueOf(count));
					homePage.click(EditAccess);
					
					unCheckChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
					
					unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
					homePage.click("SaveUser");
					homePage.refresh();
					Util.sleep(40000);
					User11.click();				
					Util.sleep(40000);
					String NewlyAddedUserProductExtensions=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserProductExtensions", UserName);
					String NewlyAddedUserWebSupport=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserWebSupport", UserName);
					String NewlyAddedUserProductDownloads=homePage.createFieldWithParsedFieldLocatorsTokens("NewlyAddedUserProductDownloads", UserName);
				}
			}*/
			
			/*String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser", User1);
			homePage.click(Enduser1);
			
			checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
			
			unCheckChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
			
			checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
			
			unCheckChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
			
			homePage.click("SaveUser");
			
			homePage.refresh();
			
			Util.sleep(4000);*/
			
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
