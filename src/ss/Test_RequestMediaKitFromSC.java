package ss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import common.Case;
import common.EISConstants;
import common.EISTestBase;
import common.Page_;
import common.Util;

import common.Case.CreateFrom;
import common.Case.CaseType;

/**
 * Test class - TestCreateCaseInPortal
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class Test_RequestMediaKitFromSC extends SSTestBase {
	public Test_RequestMediaKitFromSC() {
		super("firefox");
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce("https://subscription-stg.autodesk.com/sp");
	}

	@Test
	public void TEST_CreateCaseInPortal() throws Exception {
		CreateFrom createFrom 	= getInterfaceType(SSConstants.SS_CREATE_FROM_ENUM_CONSTANT_NAME);
		custPortallogin(testProperties.getConstant("EIDM_ID"),testProperties.getConstant("PASSWORD"));
		Case supportCase = utilCreateCaseObject(createFrom);
		Page_ commonPortalPage = supportCase.getCommonPortalPage();
	//	supportCase.openCreateCaseFormInPortal();
//		commonPage.click("getYourUpgrade");
		driver.findElement(By.xpath("//embed[contains(@flashvars,'Get Your Upgrade')]")).click();
//		driver.findElement(By.xpath("//a[contains(text(),'Related information')]//ancestor::p//embed")).click();
		if(!commonPortalPage.isFieldVisible("requestMediaFor")){
			driver.findElement(By.xpath("//embed[contains(@flashvars,'Get Your Upgrade')]")).click();	
		}
		commonPortalPage.waitForFieldVisible("requestMediaFor");
		commonPortalPage.click("requestMediaFor");
		String winHandleBefore = driver.getWindowHandle();
		
		Util.sleep(5000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(10000);
		driver.switchTo().frame(driver.findElement(By.xpath("//frame[contains(@name,'_sweclient')]")));
		driver.switchTo().frame(driver.findElement(By.xpath("//frame[contains(@name,'_sweview')]")));
		commonPortalPage.populate();
//		commonPortalPage.populateField("selectLanguageForProduct1","English");
//		commonPortalPage.populateField("selectLanguageForProduct1","English");
		commonPortalPage.click("saveChanges1");
		commonPortalPage.populateInstance("PRODUCT_2");
		commonPortalPage.click("saveChanges2");
		commonPortalPage.clickAndWait("continue","reason");
		Util.sleep(5000);
//		driver.switchTo().frame(driver.findElement(By.xpath("//frame[contains(@name,'_sweclient')]")));
//		driver.switchTo().frame(driver.findElement(By.xpath("//frame[contains(@name,'_sweview')]")));
		commonPortalPage.populateField("reason","Download failed");
		commonPortalPage.click("saveReason");
		commonPortalPage.clickAndWait("saveChanges","continue2");
		commonPortalPage.clickAndWait("continue2","submitRequest");
		commonPortalPage.clickAndWait("submitRequest","thankYou");
		commonPortalPage.verifyFieldExists("thankYou");
	}
		

	@After
	public void tearDown() throws Exception {
		//Close the browser. Call stop on the WebDriverBackedSelenium instance
		//  instead of calling driver.quit(). Otherwise, the JVM will continue
		//  running after the browser has been closed.
		driver.quit();
		
		//TODO  Figure out how to determine if the test code has failed in a
		//  manner other than by EISTestBase.fail() being called.  Otherwise,
		//  finish() will always print the default passed message to the console.
		finish();
	}
}
