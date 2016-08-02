package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_Sorting_Of_Contracts_OnReporting extends CustomerPortalTestBase{
	public String USERNAME = null;
	public String PASSWORD = null;
	String currentlySorted;
	String CC1;
	String EachUser=null;
	String UserName=null;
	String StrTemp=null;
	String StrTemp1=null;
	ArrayList<String> MultipleUsers = new ArrayList<String>();
	ArrayList<String> MultipleCCsafterSort = new ArrayList<String>();
	ArrayList<String> MultipleUsers1 = new ArrayList<String>();
	ArrayList<String> MultipleCCsafterSort1 = new ArrayList<String>();
	

	public Test_Verify_Sorting_Of_Contracts_OnReporting() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());		
	}
	@Test
	public void Test_Verify_Sorting_Of_Contracts_OnReportingPage()throws Exception {
		//Get the Login credentials from Jenkins if provided else go with one given in properties file
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			if (getEnvironment().equalsIgnoreCase("dev")) {
				USERNAME = testProperties.getConstant("USER_NAME");				
				PASSWORD = testProperties.getConstant("PASSWORD");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");				
			}
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);
					
		}
		Util.printInfo("navigating to reporting page");
		homePage.click("reporting");
		Util.sleep(4000);
		boolean noCloudCreditsPurchased=homePage.isFieldPresent("noCloudCredits");
		if(noCloudCreditsPurchased){
			EISTestBase.fail("No cloud credits purchased for this contract please chose an another user");
		}
		else{
			//Sort by Contract Type
			Util.printInfo("Sorting by Contract type");
			homePage.click("contractTypeSort");
			currentlySorted=homePage.getValueFromGUI("sortBy");
			assertEquals(currentlySorted, "Contract Type");
			homePage.verify();
			
			//Sort by Highest Consumed
			Util.printInfo("Sorting by Highest Usage");
			homePage.click("highestUsageSort");
			currentlySorted=homePage.getValueFromGUI("sortBy");
			assertEquals(currentlySorted, "Highest Usage");
			List<WebElement> ListofCC=driver.findElements(By.xpath("//article[contains(@id,'contract')]/div[@class='contract-summary']//div[@class='inner']//div[@class='gauge']//div[@class='consumed']//span[1]"));
			Util.printInfo("Contract Credits before sorting are :: ");
			for(WebElement EachUser1 : ListofCC){
				StrTemp=EachUser1.getText();
				EachUser=String.valueOf(StrTemp);
				MultipleUsers.add(EachUser.toLowerCase());
			}
			

			Util.printInfo(MultipleUsers.toString());
			Util.printInfo("Contract Credits after sorting are :: ");
			List<WebElement> ListofUsersAfterSort=driver.findElements(By.xpath("//article[contains(@id,'contract')]/div[@class='contract-summary']//div[@class='inner']//div[@class='gauge']//div[@class='consumed']//span[1]"));
			for(WebElement UserName1 : ListofUsersAfterSort){
				StrTemp1=UserName1.getText();
				UserName=String.valueOf(StrTemp1);
				MultipleCCsafterSort.add(UserName.toLowerCase());
			}
			Util.printInfo(MultipleCCsafterSort.toString());
			Util.sleep(4000);
			if(MultipleUsers.equals(MultipleCCsafterSort)){
				assertEquals("Contract Credits are sorted in  order",MultipleUsers.toString(), MultipleCCsafterSort.toString());
			}else{
				EISTestBase.fail("Contract Credits are not sorted in order");
			}

			
			//Sort by Lowest Consumed
			Util.printInfo("Sorting by Lowest Usage");
			homePage.click("lowestUsageSort");
			currentlySorted=homePage.getValueFromGUI("sortBy");
			assertEquals(currentlySorted, "Lowest Usage");
			List<WebElement> ListofCC1=driver.findElements(By.xpath("//article[contains(@id,'contract')]//div[@class='inner']//div[@class='gauge']//div[@class='consumed']//span[1]"));
			Util.printInfo("Contract Credits before sorting are :: ");
			for(WebElement EachUser1 : ListofCC1){
				StrTemp=EachUser1.getText();
				EachUser=String.valueOf(StrTemp);
				MultipleUsers1.add(EachUser.toLowerCase());
			}
//			Collections.sort(MultipleUsers1,Collections.reverseOrder());

			Util.printInfo(MultipleUsers1.toString());
			Util.printInfo("Contract Credits after sorting are :: ");
			List<WebElement> ListofUsersAfterSort1=driver.findElements(By.xpath("//article[contains(@id,'contract')]//div[@class='inner']//div[@class='gauge']//div[@class='consumed']//span[1]"));
			for(WebElement UserName1 : ListofUsersAfterSort1){
				StrTemp1=UserName1.getText();
				UserName=String.valueOf(StrTemp1);
				MultipleCCsafterSort1.add(UserName.toLowerCase());
			}
			Util.printInfo(MultipleCCsafterSort1.toString());
			Util.sleep(4000);
			if(MultipleUsers1.equals(MultipleCCsafterSort1)){
				assertEquals("Contract Credits are sorted in order",MultipleUsers1.toString(), MultipleCCsafterSort1.toString());
			}else{
				EISTestBase.fail("Contract Credits are not sorted in order ");
			}

			
			//Sort by Auto Auto renewal / End Date
			Util.printInfo("Sorting by Auto renewal / End Date");
			homePage.click("endDateSort");
			currentlySorted=homePage.getValueFromGUI("sortBy");
			assertEquals(currentlySorted, "Auto renewal / End Date");
			homePage.verifyInstance("END_DATE_SORT");
			
			logoutMyAutodeskPortal();
		}
		
	}
		@After
		public void tearDown() throws Exception {
			driver.quit();
			finish();
		}
}
