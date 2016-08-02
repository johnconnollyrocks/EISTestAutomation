package eurp;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verify_Portal_EURP_ByDay extends EURPTestBase{

	public Test_Verify_Portal_EURP_ByDay() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verfy_ContractNumbers() throws Exception {
		Util.printInfo("Logging into customer portal ");
		ArrayList<String> UserIdList=new ArrayList<String>();
		ArrayList<String> UnSortedUserIdList=new ArrayList<String>();
		ArrayList<String> EmailIdList=new ArrayList<String>();
		ArrayList<String> UnSortedEmailList=new ArrayList<String>();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		if(homePage.isFieldVisible("ManageUsers")){
			assertTrue("Successfully logged into EURP ", homePage.isFieldVisible("ManageUsers"));
		}else{
			EISTestBase.fail("Error occured while logging in ");
		}
		
		Util.printInfo("Clicking on reporting page");

		driver.get("https://customer-stg.autodesk.com/cep/#reporting/product-usage/by-day");
		
		Util.sleep(40000);
		
		Util.printInfo("Verifying By Day tab");
		
		if(homePage.isFieldVisible("ByDay")){
			Util.printInfo("By Day tab pressent in reporting page, so clicking on By Day tab ");
			homePage.click("ByDay");
			homePage.verifyFieldExists("SuccessfullyNavigatedToByDayPage");
			Util.printInfo(" Navigated to ByDay page successfully ");
			Util.printInfo(" Validating sorting in ByDay ");
			
			List<WebElement> Frames=driver.findElements(By.tagName("iframe"));
			
			 for (WebElement iframe : Frames) {
			        driver.switchTo().frame(iframe);
					if(homePage.isFieldVisible("UsageFrom"))
						break;
					driver.switchTo().defaultContent();
			    }
			 if(homePage.isFieldVisible("UsageFrom")){
				 homePage.verifyFieldExists("UsageFrom");
				 }else{
					 homePage.refresh();
					 Util.sleep(40000);
					 if(homePage.isFieldVisible("UsageFrom")){
						 homePage.verifyFieldExists("UsageFrom");
						 }else{
					 EISTestBase.fail("There is a problem in loading By Day page, please check again ");
						 }
				 }
			homePage.click("ByDaySortBy");
			List<WebElement> UserIdsBeforeSort=homePage.getMultipleWebElementsfromField("UserIDs");
			for(WebElement UserID : UserIdsBeforeSort){
				UnSortedUserIdList.add(UserID.getText());
			}
			Collections.sort(UnSortedUserIdList);
			//Collections.reverse(UnSortedUserIdList);
			homePage.click("UserID");
			Util.sleep(4000);
			List<WebElement> UserIdsAfterSort=homePage.getMultipleWebElementsfromField("UserIDs");
			for(WebElement UserID : UserIdsAfterSort){
				UserIdList.add(UserID.getText());
			}
			Collections.sort(UserIdList);
			
			//assertTrue("User ID's sorted successfully ", UnSortedUserIdList.equals(UserIdList));
			
			homePage.click("ByDaySortBy");
			
			List<WebElement> EmailIdsBeforeSort=homePage.getMultipleWebElementsfromField("UserIDs");
			for(WebElement BeforeSortEmail : EmailIdsBeforeSort){
				UnSortedEmailList.add(BeforeSortEmail.getText());
			}
			Collections.sort(UnSortedEmailList);
			
			homePage.click("SortByEmail");
			
			Util.sleep(4000);
			List<WebElement> EmailIdsAfterSort=homePage.getMultipleWebElementsfromField("UserIDs");
			for(WebElement EmailIDs : EmailIdsAfterSort){
				EmailIdList.add(EmailIDs.getText());
			}
			Collections.sort(EmailIdList);
			
		}else{
			EISTestBase.fail("Dashboard tab doesnot pressent in reporting page, so unable to click on dashboard");
		}
		//assertTrue("Email ID's sorted successfully ", UnSortedEmailList.equals(EmailIdList));
		
		Util.printInfo("Validating Usage from Dates in By Day page ");
		
		String UsageFromDate=homePage.getValueFromGUI("UsageFromDate");
		String[] UIDate=UsageFromDate.split("Usage From ");
		
		String[] StartAndEndDate=UIDate[1].split(" to ");
		
		Util.printInfo("Usage from start Date is :: " +StartAndEndDate[0].trim());
		Util.printInfo("Usage from End Date is :: " +StartAndEndDate[1].trim());
		
		String EndDate=getCurrentDate();
		String StartDate=getDateBefore90();
		
		assertTrue("Usage from start date ", StartAndEndDate[0].trim().equals(StartDate));
		
		assertTrue("Usage from End date ", StartAndEndDate[1].trim().equals(EndDate));
		
	}
	
	public String getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String date1=dateFormat.format(date);
	    return date1;
	}
	
	
	public String getDateBefore90(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();	    
		return dateFormat.format(DateUtils.addDays( cal.getTime(),-90));
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