package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_MarketingNames_PandS extends CustomerPortalTestBase {
	public Test_Verify_MarketingNames_PandS() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyDwnldOtherProductsWindowURL() throws Exception {
		String getStatus;
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG"), testProperties.getConstant("PASSWORD_STG"));
		
		Util.printInfo("Loggin into CM's Account >>>>"+testProperties.getConstant("CM_USER_NAME"));
		Util.sleep(40000);
		List<WebElement> Services=driver.findElements(By.xpath(".//*[contains(@class,'service not')]/div[2]/div[1]//div/span[1]"));
		
		Util.printInfo("Services Pressent under CM "+testProperties.getConstant("USER_NAME_STG")+ " is/are ::" );
		
		Util.printInfo("verifying for Autodesk® Flow Design, Storage ,Autodesk® Remote ,Autodesk® ReCap 360™ , Autodesk® Character Generator ,Nesting for Autodesk® Fabrication CAMduct™ are present are not");
		homePage.verify();
		int flag=0;
				
		for(WebElement EachService:Services)
		{
			
			String ServiceName=EachService.getText();
			
			Util.printInfo(ServiceName);
			/*if(ServiceName.contains("Desktop Access")){
				
				flag=1;
			}*/	
			if(ServiceName.trim().contains("svc"))
			{
				EISTestBase.fail("The service " +ServiceName+ " has Marketing Name start with SVC..");
			}else{
				assertTrue("The service " +ServiceName+ " has proper Marketting Name", !ServiceName.trim().contains("svc"));
			}
			
			}
		/*if (flag==0){
			EISTestBase.fail("Desktop Accessservice not pressent");
		}*/
			
				
		Util.printInfo("Total Services pressent :: "+Services.size());
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
