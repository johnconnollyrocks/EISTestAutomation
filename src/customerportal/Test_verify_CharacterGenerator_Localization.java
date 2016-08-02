package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import common.EISTestBase;
import common.Util;

public class Test_verify_CharacterGenerator_Localization extends CustomerPortalTestBase {
	public Test_verify_CharacterGenerator_Localization() throws IOException {
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
		Util.printInfo("Logged in Successfully");
		homePage.click("Profile");
		Util.printInfo("Clicking on Profile");
        Util.sleep(40000);
        Util.printInfo("Editing the language");
        homePage.click("EditAccount");
        Util.sleep(8000);
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
        
        int j=list1.size();
        for(int k = 0; k<j; k++)
    	{
    	System.out.println("Selected language :: "+list1.get(k));
    	WebElement wb1=driver.findElement(By.xpath(".//*[@id='Language']"));
    	Select sel1=new Select(wb1);
    	sel1.selectByVisibleText(list1.get(k));
    	homePage.clickAndWait("EditLanguageSave","EditLanguageSave");
    	Util.sleep(5000);
    	homePage.clickAndWait("AccountTab","ManageUsers");
    	Util.sleep(10000);
        homePage.clickAndWait("characterGeneratorToggleDrawer","characterGeneratorToggleDrawer");
		String ExpectedDescreption1 = testProperties.getConstant("CHARACTER_GEN_DESCRIPTION");
		String ActualDescription1 = homePage.getValueFromGUI("CharacterGeneratorDescription");
		if(!ActualDescription1.trim().equalsIgnoreCase(ExpectedDescreption1)){
		assertTrue("CharacterGeneratorDescription", !ActualDescription1.trim().equalsIgnoreCase(ExpectedDescreption1));
		}else{
			homePage.clickAndWait("Profile","Profile");
//	        Util.sleep(4000);
	        homePage.clickAndWait("EditAccount","EditAccount");
	        WebElement wb2=driver.findElement(By.xpath(".//*[@id='Language']"));
	    	Select sel2=new Select(wb2);
	        sel2.selectByIndex(2);
	        homePage.clickAndWait("EditLanguageSave","EditLanguageSave");
			EISTestBase.fail("Description for CharacterGenerator is showing incorrect Actual Language is ::"+list1.get(k));
		}
		Util.sleep(4000);
		if(homePage.isFieldVisible("characterGeneratorAccessLink")){
		String characterGeneratorAccessLink=homePage.getValueFromGUI("characterGeneratorAccessLink");
		if(!characterGeneratorAccessLink.equalsIgnoreCase("Access Now")){
		assertTrue("Access Now Button language :: "+list1.get(k),!characterGeneratorAccessLink.equalsIgnoreCase("Access Now"));
		}else{
			homePage.clickAndWait("Profile","Profile");
//	        Util.sleep(4000);
	        homePage.clickAndWait("EditAccount","EditAccount");
	        WebElement wb2=driver.findElement(By.xpath(".//*[@id='Language']"));
	    	Select sel2=new Select(wb2);
	        sel2.selectByIndex(2);
	        homePage.clickAndWait("EditLanguageSave","EditLanguageSave");
			EISTestBase.fail("Access Now Button language is shwoing incorrect :: Actual lnguage is "+list1.get(k));
		}
	  }else{
		  	homePage.clickAndWait("Profile","Profile");
//	        Util.sleep(4000);
	        homePage.clickAndWait("EditAccount","EditAccount");
	        WebElement wb2=driver.findElement(By.xpath(".//*[@id='Language']"));
	    	Select sel2=new Select(wb2);
	        sel2.selectByIndex(2);
	        homePage.clickAndWait("EditLanguageSave","EditLanguageSave");
	        EISTestBase.fail("Access Now Button is not pressent ::");
	  }
        Util.printInfo("Clicking on Profile");
        homePage.clickAndWait("Profile","Profile");
//        Util.sleep(4000);
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
