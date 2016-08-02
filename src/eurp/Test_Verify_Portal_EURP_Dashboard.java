package eurp;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_Portal_EURP_Dashboard extends EURPTestBase{

	public Test_Verify_Portal_EURP_Dashboard() throws IOException {
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
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		if(homePage.isFieldVisible("ManageUsers")){
			assertTrue("Successfully logged into EURP ", homePage.isFieldVisible("ManageUsers"));
		}else{
			EISTestBase.fail("Error occured while logging in ");
		}
		
		Util.printInfo("Clicking on reporting page");
		homePage.click("reporting");
		
		Util.printInfo("Verifying Dashboard tab");
		
		if(homePage.isFieldVisible("ReportingPageDashboard")){
			Util.printInfo("Dashboard tab pressent in reporting page, so clicking on dashboard tab ");
			homePage.click("ReportingPageDashboard");
			Util.sleep(40000);
			homePage.verifyFieldExists("SuccessfullyNavigatedToDashBoard");
			Util.printInfo(" Navigated to Dashboard successfully ");
			
			List<WebElement> Frames=driver.findElements(By.xpath("//iframe[contains(@class,'iframeReport')]"));
			
			 for (WebElement iframe : Frames) {
			        driver.switchTo().frame(iframe);
					if(homePage.isFieldVisible("SummaryOfCurrentContractYear"))
						break;
			    }
			
			 if(homePage.isFieldVisible("SummaryOfCurrentContractYear")){
			 homePage.verifyFieldExists("SummaryOfCurrentContractYear");
			 }else{
				 EISTestBase.fail("There is a problem in loading Reporting page, please check again ");
			 }
			//driver.switchTo().defaultContent();
			String CurrentContractYear=GetCurrentYear();
			String SummaryOfCurrentContractYearDate=homePage.createFieldWithParsedFieldLocatorsTokens("SummaryOfCurrentContractYearDate", CurrentContractYear);
			
			/*List<WebElement> IFrames=driver.findElements(By.tagName("iframe"));
			 for (WebElement iframe : IFrames) {
			        driver.switchTo().frame(iframe);
					if(homePage.isFieldVisible(SummaryOfCurrentContractYearDate))
						break;
			    }*/
			
			//Util.sleep(4000);
			//homePage.verifyFieldExists(SummaryOfCurrentContractYearDate);
			//Util.sleep(4000);
			
			Util.printInfo("Summary of current contract year Date is " +CurrentContractYear);
			homePage.verifyFieldExists("AnnualTokenUsage");
			Util.sleep(4000);
			homePage.verifyFieldExists("AnnualTokenUsageValue");
			Util.sleep(4000);
			Util.printInfo("Annual Token Usage value is :: "+homePage.getValueFromGUI("AnnualTokenUsageValue"));
			homePage.verifyFieldExists("DashBoardStartDate");
			Util.sleep(4000);
			homePage.verifyFieldExists("StartDateValue");
			Util.sleep(4000);
			Util.printInfo("Start Date value is :: "+homePage.getValueFromGUI("StartDateValue"));
			homePage.verifyFieldExists("DashBoardEndDate");
			Util.sleep(4000);
			homePage.verifyFieldExists("EndDateValue");
			Util.sleep(4000);
			Util.printInfo("End Date value is :: "+homePage.getValueFromGUI("EndDateValue"));
			homePage.verifyFieldExists("DashboardTerm");
			Util.sleep(4000);
			homePage.verifyFieldExists("TermValue");
			Util.sleep(4000);
			Util.printInfo("Term value is :: "+homePage.getValueFromGUI("TermValue"));
			
			Util.printMessage("verifying annual tokens table columns ");
			
			homePage.verifyFieldExists("TotalColumn");
			Util.sleep(4000);
			homePage.verifyFieldExists("UsedColumn");
			Util.sleep(4000);
			homePage.verifyFieldExists("RemainingColumn");
			Util.sleep(4000);
			
			Util.printInfo("verifying Multi - Year Tokens table ");
			
			homePage.verifyFieldExists("MultiYearTotalColumn");
			Util.sleep(4000);
			homePage.verifyFieldExists("MultiYearUsedColumn");
			Util.sleep(4000);
			homePage.verifyFieldExists("MultiYearRemainingColumn");
			Util.sleep(4000);
			homePage.verifyFieldExists("AnnualAttainment");
			Util.sleep(4000);
			homePage.verifyFieldExists("AnnualAttainmentValue");
			
		}else{
			EISTestBase.fail("Dashboard tab doesnot pressent in reporting page, so unable to click on dashboard");
		}
		
		driver.switchTo().defaultContent();
		logoutMyAutodeskPortal();
		Util.printInfo("Successfully logged out ");
	}
	
	public String GetCurrentYear(){
		Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
	     
        Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR);
	    Calendar calendarStart=Calendar.getInstance();
	    calendarStart.set(Calendar.YEAR,year);
	    calendarStart.set(Calendar.MONTH,0);
	    calendarStart.set(Calendar.DAY_OF_MONTH,1);
	    // returning the first date
	    Date startDate=calendarStart.getTime();
	    String[] StartDate=String.valueOf(startDate).split(" PST ");
	    String[] arr=StartDate[0].split("Wed ");
	    String[] arr1=arr[1].split(String.valueOf(calendarStart.get(Calendar.MONTH) + 01));
	    String ActualStartDate=arr1[0]+String.valueOf((calendarStart.get(Calendar.MONTH) + 1))+", "+now.get(Calendar.YEAR);
	    String CurrentYearStartDate=null;
	    if(ActualStartDate.contains("Jan")){
	    	CurrentYearStartDate=ActualStartDate.replace("Jan", "January");
	    }
	    Calendar calendarEnd=Calendar.getInstance();
	    calendarEnd.set(Calendar.YEAR,year);
	    calendarEnd.set(Calendar.MONTH,11);
	    calendarEnd.set(Calendar.DAY_OF_MONTH,31);
	    // returning the last date
	    Date endDate=calendarEnd.getTime();
	    
	    String[] EndtDate=String.valueOf(endDate).split(" PST ");
	    String[] Temp=EndtDate[0].split("Wed ");
	    String[] Temp1=Temp[1].split(String.valueOf(calendarStart.get(Calendar.MONTH) + 01));
	    String ActualEndDate=Temp1[0]+String.valueOf((calendarStart.get(Calendar.MONTH) + 1))+", "+now.get(Calendar.YEAR);
	    String CurrentYearEndDate=null;
	    if(ActualEndDate.contains("Dec")){
	    	CurrentYearEndDate=ActualEndDate.replace("Dec", "December");
	    }
	    
		return (CurrentYearStartDate+" to "+CurrentYearEndDate);
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