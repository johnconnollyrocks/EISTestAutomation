package customerportal;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyRenewBtnInCMContractRpt extends CustomerPortalTestBase {
	public Test_VerifyRenewBtnInCMContractRpt() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	
	
	@Test
	public void Test_VerifyRenewBtnInCMContractRpt_method() throws Exception {
		
		
//		Rdbms rdbms = Rdbms.getInstance("/EISTestAutomation/src/gdw/resource/DbConfiguration_STG.properties");
//		
//		System.out.println("GDW connection : "+rdbms.CheckGDWconnection());
//		
//		System.out.println("GDW connection : "+rdbms.getdbvalue("select * from sys.tables", "cepstgdb"));
		
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		
		Util.printInfo("click on reporting");
		homePage.clickAndWait("reporting", "contractReport");
		
		Util.printInfo("click on contract Report");
//		homePage.click("contractReport");
		
		Util.printInfo("url : "+testProperties.getConstant("NAVIGATEURL"));
		driver.navigate().to(testProperties.getConstant("NAVIGATEURL"));
		homePage.waitForField("headerContract", true);
		Util.sleep(5000);
		
		int tablesize = driver.findElements(By.xpath(".//*[@id='contract-report']/table/tbody/tr")).size();//homePage.getNumRowsInTable("contractReportTable");
		Util.printInfo("tablesize : "+tablesize);
		for(int i =1;i<=tablesize;i++){
			String endDate = driver.findElement(By.xpath("//*[@id='contract-report']/table/tbody/tr[1]/td[@class='endDate asterisk']")).getText().trim();
			String contractNumber = driver.findElement(By.xpath("//*[@id='contract-report']/table/tbody/tr["+i+"]/td[1]")).getText().trim();
//			String endDate = homePage.getTableCell("endDateColumn", i).trim();
			Util.printInfo("endDate : "+endDate);
			Util.printInfo("contractNumber : "+contractNumber);
//			Util.printInfo("gui element : "+homePage.getValueFromGUI(endDate));
			 Date actualDate =  DateUtils.parseDate(endDate, new String[]{"MMM d, yyyy"});
			 Util.printInfo("actualDate : "+actualDate);
			 Date dateBefore45 =  DateUtils.addDays(actualDate, -45);
			 Util.printInfo("cal 45 days before : "+dateBefore45);
			 Date dateAfter30 = DateUtils.addDays(actualDate, 30);
			 Util.printInfo("cal 30 days after : "+dateAfter30);
			 
			 Date currDate = new Date();
			 
			 Boolean renewButtonExists =(currDate.after(dateBefore45) && currDate.before(dateAfter30));
			 if(renewButtonExists==false){
				 Date dateAfter31 = DateUtils.addDays(actualDate, 31);
				 renewButtonExists =(currDate.after(dateBefore45) && currDate.before(dateAfter31));
			 }
			 Util.printInfo("renewButtonExists : "+renewButtonExists);
			 
			 
			 if(renewButtonExists){
//				 Util.printInfo("Renew ex : "+driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[8]/a[contains(text(),'Renew')]")).isDisplayed());
				 assertTrue("renew button should exist for the contract number " + contractNumber,driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[8]/a")).getAttribute("class").equalsIgnoreCase("locale renew-button"));//homePage.verifyFieldExistsInTable("contractReportTable",i,"endDateColumn",testProperties.getConstant("FIELD_LOCATOR_RB_EXISTS")));
			 }
			 else{
//				 assertTrue("renew button should not exist",homePage.verifyFieldExistsInTable("contractReportTable",i,"endDateColumn",testProperties.getConstant("FIELD_LOCATOR_RB_NOT_EXISTS")));
				 assertTrue("renew button should not exist for the contract number " + contractNumber,!driver.findElement(By.xpath(".//*[@id='contract-report']/table/tbody/tr["+i+"]/td[8]/a")).isDisplayed());
			 }
			  
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
