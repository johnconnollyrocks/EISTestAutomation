package customerportal;

import java.io.IOException;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.DOMXmlParser;
import common.SoapUIExampleTest;
import common.Util;
import customerportal.UserDetailsDTO;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_Contracts_ByUser_Page extends CustomerPortalTestBase {
	
	
	public Test_Verify_Contracts_ByUser_Page() throws IOException {
//		super("Browser",getAppBrowser());
		super("chrome");
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_Consumed_Users_method() throws Exception {
		//setDebugMode(true);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		if(getEnvironment().equalsIgnoreCase("DEV")){
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			Util.printInfo("Logging into CM's Account");
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		}
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(20000);
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		
		boolean flag = false;
		List<WebElement> usersTable = driver.findElements(By.xpath(".//*[@id='results']/li"));
		String emailIdGiven = homePage.getConstant("NEWUSER_EMAIL");
		Util.printInfo("emailIdGiven : "+emailIdGiven);
		int k =1;
		for(k=1;k<=usersTable.size();k++){
			String emailId = driver.findElement(By.xpath(".//*[@id='results']/li["+k+"]//section[2]//a[@class='emailAddress']")).getAttribute("title");
			Util.printInfo("emailIdGiven : "+emailIdGiven);
			Util.printInfo("emailId : "+emailId);
			if(emailIdGiven.trim().equalsIgnoreCase(emailId.trim())){
				flag = true;
				break;
			}
			
		}
		Util.printInfo("Adding a User");
		
		String Email=homePage.getConstant("EMAIL");
		String ActualEmail=Email+getUniqueId()+"@ssttest.net";
		String FirstName=RandomStringUtils.randomAlphabetic(5);
		String User=FirstName+" "+"User";
		
		addUser(ActualEmail, FirstName);
		Util.printInfo("user added");
//		WebElement saveContbutton = driver.findElement(By.cssSelector("#save-cancel span.and-continue"));
		
//		if(!driver.findElement(By.xpath(".//*[@id='access']/div")).getAttribute("class").contains("checked")){
//			driver.findElement(By.xpath(".//*[@id='access']/div")).click();
//		}
		
//		saveContbutton.click();
		
//		addNewUser(homePage.getConstant("NEWUSER_EMAIL"));
		
		Util.sleep(10000);
		Util.printInfo("saving the user");
		Util.sleep(25000);
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
		homePage.click("SaveUser");
//		changesNotSavedPageOnCancel(false);
//
//		Util.sleep(2000);
//		Util.printInfo("update button present : "+flag);
//		if(flag){
//			driver.findElement(By.xpath(".//*[@id='add-wrapper']/div[10]/div/div[1]")).click();
//		}
//		Util.sleep(4000);
//		
//		homePage.click("editAccessCancelBtn");
		
		List<UserDetailsDTO> userDetailsList  = getUserDetails();
		Util.printInfo("userDetailsList : "+userDetailsList);
		Util.sleep(1000);
		homePage.click("reporting");
		Util.sleep(1000);
		homePage.click("byUsers");
		
		Util.sleep(20000);
		
		int rows = driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();
		
		for(UserDetailsDTO userdetail : userDetailsList){
			if(userdetail!=null){
				
			 for(int i =1;i<=rows;i++){			
				
				String userName = userdetail.getUserName();
				
				String pUserName = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]//*[@class='name']")).getText();
				
				if(userName.equalsIgnoreCase(pUserName.trim())){		
					
					
					assertEquals(userName, pUserName);
					
					if(userdetail.getContractWithType()!=null){
						
					int pnumberOfcontracts = driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]//td[@class='number']//span")).size();
					assertEquals(pnumberOfcontracts, userdetail.getContractWithType().size());
					
					for(int j=1;j<=pnumberOfcontracts;j++){
						if(!( driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]//a[@class='more']")).size()<=0)){
							driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]//a[@class='more']")).click();
						}
							
						String pContract = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table//tr["+i+"]//td[@class='pooled-usage']//tr["+j+"]//td[@class='number']//span")).getText();
						Util.printInfo("pContract before index pound: "+pContract);
						int indexofpound = pContract.indexOf("#");
						if(indexofpound !=-1){
							pContract = pContract.substring(indexofpound+1);
							Util.printInfo("pContract : "+pContract);
						
						}
						assertTrue("contract ("+pContract+") should be present in  :("+userdetail.getContractWithType()+") ", userdetail.getContractWithType().containsKey(pContract));
						if(!( driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]//a[@class='less']")).size()<=0)){
						driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]//a[@class='less']")).click();
					} }
					}
					else{
						String credits = driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]//*[@class='pooled-usage']")).getText().trim();
						assertEquals(credits, "No Contract Assigned");
						if(pUserName.equalsIgnoreCase(testProperties.getConstant("NEWUSER_USERNAME")));
						assertTrue("for new user without any contract : should have 'No Contract Assigned' ", credits.equalsIgnoreCase("No Contract Assigned"));
					}
					
					assertTrue("individual usage gauge", "gauge".equals(driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]//td[@class='individual-usage']/div")).getAttribute("class")));
				}
			}
		  }
		}
		
		
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		Util.printInfo("Deleting Test User");
		RemoveUserBySearch(ActualEmail, User);
		
		Util.printInfo("User Removed successfully :: ");
		
		/*homePage.click("xxxyyyRemoveLink");
		homePage.click("xxxyyyConfirmRemoveButton");*/
		
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
