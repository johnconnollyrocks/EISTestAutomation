package customerportal;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.EISTestBase;
import common.Util;

public class Test_Verfy_ContractReport_SortResults extends CustomerPortalTestBase{

	public Test_Verfy_ContractReport_SortResults() throws IOException {
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
		String EachUser=null;
		String UserName=null;
		String StrTemp=null;
		String StrTemp1=null;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(5000);
	//	homePage.click("reporting");
	//	Util.sleep(2000);
		homePage.click("contractReport");
		Util.sleep(5000);
		driver.findElement(By.xpath("//th[contains(@class,'end tablesorter')]//span[@class='arrows']")).click();
		List<WebElement> ListofUsers1=driver.findElements(By.xpath("//td[@class='number']//a"));
//		List<String> ListofUsers=homePage.getValuesFromMultiSelect("ByUsersPageSorting");
		ArrayList<String> MultipleUsers = new ArrayList<String>();
		ArrayList<String> MultipleUsersafterSort = new ArrayList<String>();
		Util.printInfo("Contract Numbers before sorting are :: ");
		for(WebElement EachUser1 : ListofUsers1){
			StrTemp=EachUser1.getText();
			EachUser=String.valueOf(StrTemp);
			MultipleUsers.add(EachUser.toLowerCase());
		}
		Collections.sort(MultipleUsers,Collections.reverseOrder());

		Util.printInfo(MultipleUsers.toString());
		driver.findElement(By.xpath("//th[contains(@class,'contract')]//span[@data-i18n='MSG_USAGE_BY_USERS_CONTRACT']")).click();
		Util.printInfo("Contract Numbers after sorting are :: ");
		List<WebElement> ListofUsersAfterSort=driver.findElements(By.xpath("//td[@class='number']//a"));
		for(WebElement UserName1 : ListofUsersAfterSort){
			StrTemp1=UserName1.getText();
			UserName=String.valueOf(StrTemp1);
			MultipleUsersafterSort.add(UserName.toLowerCase());
		}
		Util.printInfo(MultipleUsersafterSort.toString());
		driver.findElement(By.xpath("//th[contains(@class,'contract')]//span[@data-i18n='MSG_USAGE_BY_USERS_CONTRACT']")).click();
		Util.sleep(4000);
		if(MultipleUsers.equals(MultipleUsersafterSort)){
			assertEquals("Contract Numbers are sorted in Alphabatical order",MultipleUsers.toString(), MultipleUsersafterSort.toString());
		}else{
			EISTestBase.fail("Contract Numbers are not sorted in order :: ");
		}
//		homePage.refresh(5000);
//		homePage.click("termSort");
		driver.findElement(By.xpath("//th[contains(@class,'term table')]//span[contains(@data-i18n,'REPORT_TERM')]//following-sibling::span")).click();
		homePage.verifyInstance("TERM_SORT");
		homePage.refresh();
		/*List<WebElement> startDate=driver.findElements(By.xpath("//td[@class='startDate']"));
//		List<String> ListofUsers=homePage.getValuesFromMultiSelect("ByUsersPageSorting");
		ArrayList<String> startDateFromUI = new ArrayList<String>();
		ArrayList<String> startDateafterSort = new ArrayList<String>();
		Util.printInfo("Contract Numbers before sorting are :: ");
		for(WebElement EachUser1 : startDate){
			StrTemp=EachUser1.getText();
			EachUser=String.valueOf(StrTemp);
			startDateFromUI.add(EachUser);
		}
		Collections.sort(startDateFromUI, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				DateFormat df=new SimpleDateFormat("MMM d,yyyy");
				try{
				return df.parse(o1).compareTo(df.parse(o2));
				}
				catch (ParseException e){
					throw new IllegalArgumentException(e);
				}
			}
			
		});
		
		Util.printInfo(startDateFromUI.toString());
//		homePage.click("startDateSort");
		driver.findElement(By.xpath("//th[contains(@class,'start table')]//span[2]")).click();
		Util.printInfo("Contract Numbers after sorting are :: ");
		List<WebElement> startDateFromUIAfterSort=driver.findElements(By.xpath("//td[@class='startDate']"));
		for(WebElement UserName1 : startDateFromUIAfterSort){
			StrTemp1=UserName1.getText();
			UserName=String.valueOf(StrTemp1);
			startDateafterSort.add(UserName);
		}
		Util.printInfo(startDateafterSort.toString());
//		driver.findElement(By.xpath("//th[contains(@class,'contract')]//span[@data-i18n='MSG_USAGE_BY_USERS_CONTRACT']")).click();
		Util.sleep(4000);
		if(startDateFromUI.equals(startDateafterSort)){
			assertEquals("Start Dates are sorted",startDateFromUI.toString(), startDateafterSort.toString());
		}else{
			EISTestBase.fail("Start Dates are not sorted");
		}			
		*/
		
		List<WebElement> endDate=driver.findElements(By.xpath("//td[contains(@class,'endDate')]"));
//		List<String> ListofUsers=homePage.getValuesFromMultiSelect("ByUsersPageSorting");
		ArrayList<String> endDateFromUI = new ArrayList<String>();
		ArrayList<String> endDateafterSort = new ArrayList<String>();
		Util.printInfo("Contract Numbers before sorting are :: ");
		for(WebElement EachUser1 : endDate){
			StrTemp=EachUser1.getText();
			EachUser=String.valueOf(StrTemp);
			/*String arr[]=EachUser.split(" ");
			String ActualUsername=arr[0]+arr[1]; */
			endDateFromUI.add(EachUser);
		}
		Collections.sort(endDateFromUI, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				DateFormat df=new SimpleDateFormat("MMM d,yyyy");
				try{
				return df.parse(o1).compareTo(df.parse(o2));
				}
				catch (ParseException e){
					throw new IllegalArgumentException(e);
				}
			}
			
		});
		
		Util.printInfo(endDateFromUI.toString());
//		driver.findElement(By.xpath("//th[contains(@class,'end table')]//span[contains(@data-i18n,'END_DATE')]//following-sibling::span")).click();
		driver.findElement(By.xpath("//th[contains(@class,'end table')]//span[2]")).click();
		Util.printInfo("Contract Numbers after sorting are :: ");
		List<WebElement> endDateFromUIAfterSort=driver.findElements(By.xpath("//td[contains(@class,'endDate')]"));
		for(WebElement UserName1 : endDateFromUIAfterSort){
			StrTemp1=UserName1.getText();
			UserName=String.valueOf(StrTemp1);
			endDateafterSort.add(UserName);
		}
		Util.printInfo(endDateafterSort.toString());
		
		Util.sleep(4000);
		if(endDateFromUI.equals(endDateafterSort)){
			assertEquals("End Dates are sorted",endDateFromUI.toString(),endDateafterSort.toString());
		}else{
			EISTestBase.fail("End Dates are not sorted");
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