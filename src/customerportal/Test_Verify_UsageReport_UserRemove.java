package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Raghavendra Yadav
 * @version 1.0.0
 */
public final class Test_Verify_UsageReport_UserRemove extends CustomerPortalTestBase {
	
	
	public Test_Verify_UsageReport_UserRemove() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {	
		launchMyAutodeskPortal(getBaseURL()); 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(5000);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
		String ReportingUser=null;
		String UsersUser=null;
		String UserStatus=null;
		int UsersList,j;
		boolean flag=true;
		Util.sleep(5000);

		//navigating to Reporting tab
		homePage.click("reporting");
		homePage.click("ByUsers");
		
		//Creating array list to store number of users from Reporting tab
		ArrayList<String> ArrayReport=new ArrayList<String>();
		int ReportUsersList=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr")).size();
		for(int i=1;i<=ReportUsersList;i++)
		{
			 UserStatus=driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/td[2]/div/div/span")).getText();		
			 System.out.println("UserStatus"+UserStatus);
			 if(!UserStatus.equalsIgnoreCase("Removed"))
				{	
				ReportingUser=driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/td/label")).getText();
				ArrayReport.add(ReportingUser);				
				}
		}
		if(ArrayReport.size()==0){
			Util.printInfo("User list is empty in Reporting tab");
		}
		
		//navigating to Users tab
		homePage.clickAndWait("users","selectAllInUP");
		if(ArrayReport.size()>0){
			
			//Array list to store number of users from Users tab
			ArrayList<String> ArrayUsers=new ArrayList<String>();
			UsersList=driver.findElements(By.xpath(".//*[@id='results']//li//section[1]")).size();
			int flag1=0;
			for(j=0;j<UsersList;j++)
			{			
			UsersUser=driver.findElement(By.xpath(".//*[@id='results']//li["+(j+1)+"]//section/div/following-sibling::div[@class='col first']/span")).getText();			
			String attribute=(driver.findElement(By.xpath(".//*[@id='results']/li["+(j+1)+"]/section/div[4]"))).getAttribute("class");
			ArrayUsers.add(UsersUser);		
			Collections.sort(ArrayReport);
			if(!attribute.equalsIgnoreCase("col last") && ArrayUsers.get(j).toString().equalsIgnoreCase(ArrayReport.get(j).toString())){
					driver.findElement(By.xpath(".//*[@id='results']/li["+(j+1)+"]/section/div/input")).click();					
					driver.findElement(By.xpath(".//*[@id='results']//li["+(j+1)+"]/section/div[4]")).click();
					UsersUser=driver.findElement(By.xpath(".//*[@id='results']//li["+(j+1)+"]//section/div/following-sibling::div[@class='col first']")).getText();					
					driver.findElement(By.xpath(".//*[@id='results']/li/section/div/div/div/div[@class='save']")).click();
					homePage.click("RemoveButton");
					flag1=1;
					break;
				}			
			}
			if(flag1==0)
			Util.printInfo("Seleted User is not pressent in Users Tab");
		
			if(flag1==1){
			homePage.click("reporting");
			homePage.click("ByUsers");
			homePage.refresh();			
			for(int k=1;k<ReportUsersList;k++)
			{	
			UserStatus=driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+k+"]/td[2]/div/div/span")).getText();
			ReportingUser=driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+k+"]/td/label")).getText();
			if(UserStatus.equalsIgnoreCase("Removed") && (UsersUser.equalsIgnoreCase(ReportingUser))){							
				Util.printInfo("User Removed"+ReportingUser);			
				flag=false;
				}
			 }
			
			if(flag)
				Util.printInfo("User" +UsersUser+ " is not pressent in Reporting tab and entire User row got deleted");		
			}
		  }	
else{					
Util.printInfo("No User to Delete");		
}	
		
String User=null;
homePage.clickAndWait("users","selectAllInUP");
int ListofUsers=driver.findElements(By.xpath(".//*[@id='results']//li//section[1]")).size();
int i;
for(i=0;i<ListofUsers;i++)
	User=driver.findElement(By.xpath(".//*[@id='results']//li["+(i+1)+"]//section/div/following-sibling::div[@class='col first']")).getText();
	String attribute=(driver.findElement(By.xpath(".//*[@id='results']/li["+(i+1)+"]/section/div[4]"))).getAttribute("class");
	if(!attribute.equalsIgnoreCase("col last")){
		driver.findElement(By.xpath(".//*[@id='results']/li["+i+"]/section/div/span[@class='edit_access']")).click();
		
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
