package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verfy_Reporting_SortResults extends CustomerPortalTestBase{

	public Test_Verfy_Reporting_SortResults() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_VerfyProductUpdatesSortByDate() throws Exception {
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
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.click("reporting");
		Util.sleep(15000);
		//Sort by Contract Type
		Util.printInfo("Sorting by Contract type");
		homePage.clickAndWait("contractTypeSort","ToolTipValidationIndividual");
		currentlySorted=homePage.getValueFromGUI("sortBy");
		assertEquals(currentlySorted, "Contract Type");
		homePage.verify();
		
		//Sort by Highest Consumed
		Util.printInfo("Sorting by Highest Usage");
		homePage.clickAndWait("highestUsageSort","ToolTipValidationIndividual");
		currentlySorted=homePage.getValueFromGUI("sortBy");
		assertEquals(currentlySorted, "Highest Usage");
		List<WebElement> ListofCC=driver.findElements(By.xpath("//article[contains(@id,'contract')]//div[@class='inner']//div[@class='gauge']//div[@class='consumed']//span[1]"));
		Util.printInfo("Contract Credits before sorting are :: ");
		for(WebElement EachUser1 : ListofCC){
			StrTemp=EachUser1.getText();
			EachUser=String.valueOf(StrTemp);
			MultipleUsers.add(EachUser.toLowerCase());
		}
		

		Util.printInfo(MultipleUsers.toString());
		Util.printInfo("Contract Credits after sorting are :: ");
		List<WebElement> ListofUsersAfterSort=driver.findElements(By.xpath("//article[contains(@id,'contract')]//div[@class='inner']//div[@class='gauge']//div[@class='consumed']//span[1]"));
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
		homePage.clickAndWait("lowestUsageSort","ToolTipValidationIndividual");
		currentlySorted=homePage.getValueFromGUI("sortBy");
		assertEquals(currentlySorted, "Lowest Usage");
		List<WebElement> ListofCC1=driver.findElements(By.xpath("//article[contains(@id,'contract')]//div[@class='inner']//div[@class='gauge']//div[@class='consumed']//span[1]"));
		Util.printInfo("Contract Credits before sorting are :: ");
		for(WebElement EachUser1 : ListofCC1){
			StrTemp=EachUser1.getText();
			EachUser=String.valueOf(StrTemp);
			MultipleUsers1.add(EachUser.toLowerCase());
		}
//		Collections.sort(MultipleUsers1,Collections.reverseOrder());

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
		homePage.clickAndWait("endDateSort","ToolTipValidationIndividual");
		currentlySorted=homePage.getValueFromGUI("sortBy");
		assertEquals(currentlySorted, "Auto renewal / End Date");
		homePage.verifyInstance("END_DATE_SORT");
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