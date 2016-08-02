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

public class Test_Verify_BridgeDesign_for_Infraworks_Launching extends CustomerPortalTestBase {
	public Test_Verify_BridgeDesign_for_Infraworks_Launching() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_BridgeDesign_Infraworks_Launching() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(4000);
//		String Append="™";
//		Util.printInfo("verifying the product name");
//		homePage.verifyFieldExists("autodeskBridgeDesignProductName");
		Util.printInfo("verifying the autodeskBridgeDesignProductName service image");
		
		String autodeskBridgeDesignServiceName=homePage.getAttribute("autodeskBridgeDesignServiceName", "src");
		String[] arr=autodeskBridgeDesignServiceName.split("badges/");
		Util.printInfo(arr[0]);
		Util.printInfo(arr[1]);
		
		String ImageName=testProperties.getConstant("ServiceImage");
		assertEquals(ImageName, arr[1]);
		
		int flag=0;
		List<WebElement> Services=driver.findElements(By.xpath(".//*[contains(@class,'service not')]/div[2]/div[1]//div/span[1]"));
		
		Util.printInfo("Services Pressent under CM "+testProperties.getConstant("USER_NAME_STG")+ " is/are ::" );
		String ServiceDescription="Autodesk® Bridge Design for InfraWorks 360 software helps engineers more effectively explore design options by modeling and visualizing more realistic civil structures in the context of the overall transportation project.";
		for(WebElement EachService:Services)
		{
			
			String ServiceName=EachService.getText();
			
			Util.printInfo(ServiceName);
			
			if(ServiceName.trim().contains("Autodesk® Bridge Design for InfraWorks 360"))
			{	
				Util.printInfo("Validating 'Access Now' button is displayed at the correct location for Autodesk® Bridge Design for InfraWorks 360 :: ");
				
				String winHandleBefore = driver.getWindowHandle();
//				Util.printInfo("Verifying 'Autodesk® Bridge Design for InfraWorks 360' service access now button :: ");
				
				/*if(homePage.isFieldVisible("AccessNowButton")){
				homePage.verifyFieldExists("AccessNowButton");
				
				}else{
					EISTestBase.fail("AccessNowButton is not pressent for 'Autodesk® Bridge Design for InfraWorks 360' :: ");
				}*/
				
				Util.sleep(5000);
				for(String winHandle : driver.getWindowHandles()){
				    driver.switchTo().window(winHandle); 
				}	
				Util.sleep(5000);
				flag=1;
				Util.printInfo("Clicking on service :: ");
				driver.findElement(By.xpath(".//*[@id='svc0000024']/div[1]/button")).click();
				Util.sleep(4000);
				String Description=homePage.getValueFromGUI("Description").split("Autodesk® Bridge Design for InfraWorks 360")[1].split("software")[1].trim();
				String actualDescription = "Autodesk® Bridge Design for InfraWorks 360 software " + Description;  
				Util.sleep(4000);
				assertEquals(actualDescription,ServiceDescription);
			}
		}
		
		if(flag==0){
			
			EISTestBase.fail("No such service exist under this account please use other account ::");
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
	