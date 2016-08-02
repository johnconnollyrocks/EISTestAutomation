package customerportal;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_HomeUse extends CustomerPortalTestBase {
	public Test_HomeUse() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_HomeUse() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(2000);
		List<WebElement> HomeUseList=homePage.getMultipleWebElementsfromField("HomeUseList");
		
		for(WebElement EachHomeUser : HomeUseList){
			if(EachHomeUser.isDisplayed()){
				assertTrue("Home Use link exists for the product / service ", EachHomeUser.isDisplayed());
			}else{
				EISTestBase.fail("HomeUse Link doesnot exists for the product/serivce ");
			}
			Util.printInfo("clicking on Home Use button ");
			EachHomeUser.click();
			Util.sleep(4000);
			homePage.verifyFieldExists("HomeUseWindow");
			Util.sleep(4000);
			homePage.verifyFieldExists("GetLicensedForHomeUse");
			Util.sleep(4000);
			homePage.verifyFieldExists("UseThisFormForHomeUseOfNetworkLicensesOnAMaintenanceSubscription");
			Util.sleep(4000);
			homePage.verifyFieldExists("SpecifyTheProduct");
			Util.sleep(4000);
			homePage.verifyFieldExists("ViewEligibleAutodeskProductsPDF");
			Util.sleep(4000);
			homePage.verifyFieldExists("ProductNameTextBox");
			Util.sleep(4000);
			homePage.verifyFieldExists("HomeUseVersion");
			Util.sleep(4000);
			homePage.verifyFieldExists("VersionTextBox");
			Util.sleep(4000);
			homePage.verifyFieldExists("HomeUseQuantity");
			Util.sleep(4000);
			homePage.verifyFieldExists("QuantityTextBox");
			Util.sleep(4000);
			homePage.verifyFieldExists("HomeUseSubs");
			Util.sleep(4000);
			homePage.verifyFieldExists("SubscriptionTextBox");
			Util.sleep(4000);
			homePage.verifyFieldExists("HowShouldWeContactYou");
			Util.sleep(4000);
			homePage.verifyFieldExists("YourRequestMayTakeFivedays");
			Util.sleep(4000);
			homePage.verifyFieldExists("EmailRequired");
			Util.sleep(4000);
			homePage.verifyFieldExists("EmailRequiredTextBox");
			Util.sleep(4000);
			homePage.verifyFieldExists("PhoneNumberTextBox");
			Util.sleep(4000);
			homePage.verifyFieldExists("PhoneNumber");
			Util.sleep(4000);
			homePage.verifyFieldExists("SendRequest");
			Util.sleep(4000);
			homePage.verifyFieldExists("HomeUseCancel");
			Util.sleep(4000);
			Util.printInfo("Closing Home Use Window by clicking on cancel button ");
			//homePage.click("HomeUseCancel");
			driver.findElement(By.xpath(".//*[@id='home-use-modal']/footer//div/button[contains(text(),'Cancel')]")).click();
			Util.sleep(4000);
			if(!(homePage.isFieldVisible("HomeUseWindow"))){
				Util.printInfo("Home Use Window closed successfully after clicking on cancel button");
			}else{
				EISTestBase.fail("Home use Window didnot closed even after clicking on cancel button ");
			}
			EachHomeUser.click();
			Util.sleep(4000);
			homePage.verifyFieldExists("CloseIcon");
			/*Util.sleep(4000);
			Util.printInfo("Closing Home Use Window by clicking on close Icon ");*/
			//homePage.click("CloseIcon");
			//driver.findElement(By.xpath(".//*[@id='home-use-modal']/header/button[contains(@class,'close')]")).click();
			Util.sleep(4000);
			/*if(!(homePage.isFieldVisible("HomeUseWindow"))){
				Util.printInfo("Home Use Window closed successfully after clicking on close Icon ");
			}else{
				EISTestBase.fail("Home use Window didnot closed even after clicking on Close Icon on top corner side of House Use window  ");
			}*/
			break;
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
