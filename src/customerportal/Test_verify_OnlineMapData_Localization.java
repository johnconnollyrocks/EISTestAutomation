package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.EISTestBase;
import common.Util;

public class Test_verify_OnlineMapData_Localization extends CustomerPortalTestBase {
	public Test_verify_OnlineMapData_Localization() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_verify_LanguageDescription() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.printInfo("Clicking on Profile");
		homePage.click("Profile");
        Util.sleep(5000);
        Util.printInfo("Editing the language");
        homePage.click("EditAccount");
        Util.sleep(5000);
        WebElement wb=driver.findElement(By.xpath(".//*[@id='Language']"));
        Select sel=new Select(wb);
        List<String>  list1 = new ArrayList<String>();
        int size = sel.getOptions().size();
        System.out.println("Total languages pressent ="+size);
        for (WebElement we : sel.getOptions()) {
          // String language=we.getText();
        	if(!we.getText().equals("English")){
        	   list1.add(we.getText());
        	}
        }
        Util.sleep(4000);
        int j=list1.size();
        for(int k = 0; k<j; k++)
    	{
    	System.out.println("Selected language :: "+list1.get(k));
    	WebElement wb1=driver.findElement(By.xpath(".//*[@id='Language']"));
    	Util.sleep(2000);
    	Select sel1=new Select(wb1);
    	Util.sleep(4000);
    	sel1.selectByVisibleText(list1.get(k));
    	homePage.clickAndWait("EditLanguageSave","EditLanguageSave");
    	Util.sleep(4000);
    	homePage.clickAndWait("AccountTab","AccountTab");
    	WebDriverWait wb11=new WebDriverWait(driver,40000);
//		wb11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
        homePage.click("onlineMapToggleDrawer");
		String ExpectedDescreption1 = testProperties.getConstant("ONLINE_MAP_DESCRIPTION");
		String ActualDescription1 = homePage.getValueFromGUI("onlineMapDescriptionLocalization");
		if(!ActualDescription1.trim().equalsIgnoreCase(ExpectedDescreption1)){
		assertTrue("onlineMapDescription", !ActualDescription1.trim().equalsIgnoreCase(ExpectedDescreption1));
		}else{
			homePage.clickAndWait("Profile","Profile");
	        Util.sleep(4000);
	        homePage.clickAndWait("EditAccount","EditAccount");
	        WebElement wb2=driver.findElement(By.xpath(".//*[@id='Language']"));
	    	Select sel2=new Select(wb2);
	        sel2.selectByIndex(2);
	        homePage.clickAndWait("EditLanguageSave","EditLanguageSave");
	        EISTestBase.fail("Description for onlineMapData is showing incorrect language, Actual Language is ::"+list1.get(k));
		}
		Util.sleep(4000);
		/*String characterGeneratorAccessLink=homePage.getValueFromGUI("characterGeneratorAccessLink");
		if(!characterGeneratorAccessLink.equalsIgnoreCase("Access Now")){
		assertTrue("Access Now Button language for onlineMapData :: "+list1.get(k),!characterGeneratorAccessLink.equalsIgnoreCase("Access Now"));
		}else{
			EISTestBase.fail("Access Now Button language of onlineMapData is shwoing incorrect language, Actual lnguage is "+list1.get(k));
		}*/
        Util.printInfo("Clicking on Profile");
        homePage.clickAndWait("Profile","Profile");
        Util.sleep(4000);
        Util.printInfo("Editing the language");
        homePage.clickAndWait("EditAccount","EditAccount");
    	}
        WebElement wb1=driver.findElement(By.xpath(".//*[@id='Language']"));
    	Select sel1=new Select(wb1);
        Util.sleep(4000);
        sel1.selectByVisibleText("English");
        homePage.clickAndWait("EditLanguageSave","EditLanguageSave");
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
