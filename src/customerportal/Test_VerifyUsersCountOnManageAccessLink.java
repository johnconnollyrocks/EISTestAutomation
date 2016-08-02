package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
public final class Test_VerifyUsersCountOnManageAccessLink extends CustomerPortalTestBase {
	public Test_VerifyUsersCountOnManageAccessLink() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_AddUserAndAssignBenifitsProductsInNAMUApplication() throws Exception {
		String Name="";
		String Names = "";
		String UserName2="";
		String namesFromUserPage="";
		String namesFromManageUsersPage="";
		String count;
		Actions DoAction=new Actions(driver);
		//setDebugMode(true);
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb=new WebDriverWait(driver,30000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		String contractNo=homePage.getValueFromGUI("contractNumberOfFirstProduct");
		WebDriver driver=EISTestBase.driver;
		homePage.click("users");
		Util.sleep(5000);
		List<WebElement> UserNames=driver.findElements(By.xpath("//li[contains(text(),'110000856532')]//ancestor::section[1]//preceding::section[1]//span[@class='title']"));
		String xxx = " ";
		for(int i=0;i<UserNames.size();i++){
			Name=UserNames.get(i).getText();
			Util.printInfo("User Names are :"+Name);
			Names+=Name;
		}		
		
		homePage.click("ProductsandServices");
		Util.sleep(5000);
		homePage.click("manageAccess");
		List<WebElement> UserNamesOnManageAccess=driver.findElements(By.xpath("//div[@id='permissions-modal']//p[contains(@class,'name')]"));
		
		for(int i=0;i<UserNamesOnManageAccess.size();i++){
			String UserName=UserNamesOnManageAccess.get(i).getText();
			Util.printInfo("User Names are :"+UserName);
			UserName2+=UserName;
		}	
		assertEquals(UserName2,Names);
		count=homePage.getValueFromGUI("userCount");
		String[] userCount=count.split("\\(");
		String[] userCount2=userCount[1].split("\\)");
		assertEquals(userCount2[0],Integer.toString(UserNames.size()) );
		
//		for(WebElement User : UserNamesOnManageAccess){
		for(int i=0;i<UserNamesOnManageAccess.size();i++){			
			//check if two versions ( or ) tool tip exists then do mouse hover
			String UserName=UserNamesOnManageAccess.get(i).getText();
			WebElement User=UserNamesOnManageAccess.get(i);
			Util.printInfo("User Name is :"+UserName);
			int length=UserName.length();
			if(length>25){
			
			Util.sleep(4000);
//			DoAction.moveToElement(ReturnWebelement(User.getText()))
			DoAction.moveToElement(User).build().perform();
			Util.sleep(4000);
			homePage.verifyFieldExists("userNameHover");
			Util.sleep(4000);
			WebElement UserCount=driver.findElement(By.xpath("//div[@class='user-count']"));
			Util.sleep(4000);
			DoAction.moveToElement(UserCount).build().perform();
			Util.sleep(4000);
			}
			else{
				homePage.verifyFieldNotExists("userNameHover");
				}
		}
			/*List<WebElement> UserEmailsOnManageAccess=driver.findElements(By.xpath("//div[@id='permissions-modal']//p[contains(@class,'email')]"));
			//String emailLength=driver.findElement(By.xpath("//section[@id='user-permissions-list']//li[1]//p[contains(@class,'email')]")).getText();
			for(int i=0;i<UserEmailsOnManageAccess.size();i++){			
				//check if two versions ( or ) tool tip exists then do mouse hover
				String email=UserEmailsOnManageAccess.get(i).getText();
				WebElement UserEmail=UserEmailsOnManageAccess.get(i);
				Util.printInfo("User Email is :"+email);
				int length=email.length();
				if(length>25){
				
				Util.sleep(4000);
//				DoAction.moveToElement(ReturnWebelement(User.getText()))
				DoAction.moveToElement(UserEmail).build().perform();
				Util.sleep(4000);
				homePage.verifyFieldExists("userEmailHover");
				Util.sleep(4000);
				WebElement UserCount=driver.findElement(By.xpath("//div[@class='user-count']"));
				Util.sleep(4000);
				DoAction.moveToElement(UserCount).build().perform();
				Util.sleep(4000);
				}
				else{
					homePage.verifyFieldNotExists("userEmailHover");
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
