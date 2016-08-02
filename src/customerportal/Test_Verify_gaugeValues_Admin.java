package customerportal;

import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Raghavendra Yadav
 * @version 1.0.0
 */
public final class Test_Verify_gaugeValues_Admin extends CustomerPortalTestBase {
	
	
	public Test_Verify_gaugeValues_Admin() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {	
		launchMyAutodeskPortal(getBaseURL()); 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		Util.printInfo("Logging into CM's Account ");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		
		Util.printInfo("Navigating to Usage-by-Users page");
		
		homePage.click("reporting");
		homePage.click("ByUsers");
		String UserStatus=null;
		String ByUser=null;
		String AdminIndividualgauge=null;
		String Admin=null;		
		String AdminPooledGauge=null;
		String UserCredits=null;
		String Contracts=null;
		int ReportUsersList=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();

		Admin=homePage.getValueFromGUI("AdminName");
		List<WebElement> usersTable = driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr"));
				for(int k = 1;k<=usersTable.size();k++)
				{ 
					String NewUser=homePage.createFieldWithParsedFieldLocatorsTokens("GetUser", String.valueOf(k));
					String UserID=homePage.getValueFromGUI(NewUser);
				 if(Admin.trim().equalsIgnoreCase(UserID.trim())){
					Util.sleep(5000);
					
					Util.printInfo("verifying admin individual gauge ::");
					
					AdminIndividualgauge=homePage.createFieldWithParsedFieldLocatorsTokens("UserCredits", String.valueOf(k));
					homePage.verifyFieldExists(AdminIndividualgauge);
					UserCredits=homePage.getValueFromGUI(AdminIndividualgauge);
					Util.printInfo("Individual credits for new user ::" +Admin+ "is" +UserCredits);
					break;
				  }
				}
				List<WebElement> ContractsSize=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+Admin+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));
				
				for(WebElement Contract: ContractsSize){
				
				Contracts=Contract.getText();
				System.out.println("Contracts under pooled column :"+Contracts);
				String parsedContractValue =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+Admin+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+Contracts+"')]/ancestor::td[@class='number']/following-sibling::td")).getText();
				Util.printInfo("verifying pooled credits row ::");
				
				if (!parsedContractValue.trim().contains(
						"No Cloud Credits Purchased")) {
					Util.printInfo("pooled credits before consuming...");
					assertTrue("The Pooled Gauge is pressent :: " +parsedContractValue+" ",parsedContractValue.trim().contains("0"));
				 }else{
					 assertTrue("The Pooled Gauge is pressent :: ",parsedContractValue.trim().contains("No Cloud Credits Purchased"));
				 }
				}
				
				if(ContractsSize.size()==0){
					assertTrue("The admin has a row with pooled cloud credit gauge", ContractsSize.size()==0);
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
