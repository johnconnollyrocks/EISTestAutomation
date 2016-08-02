package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.EISTestBase;
import common.Util;

public class Test_VerfyCharacterGenerator extends CustomerPortalTestBase {
	public Test_VerfyCharacterGenerator() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyDwnldOtherProductsWindowURL() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(80000);
		System.out.println("******************33*************************");
		homePage.click("characterGeneratorToggleDrawer");
		Util.printInfo("checking for the  proper Description");
		String ExpectedDescreption=testProperties.getConstant("CHARACTER_GEN_DESCRIPTION");
		String ActualDescription =homePage.getValueFromGUI("CharacterGeneratorDescription");
		
		assertEquals(ActualDescription, ExpectedDescreption);
		Util.printInfo("verifying the product name");
//		assertEquals(homePage.getValueFromGUI("characterGeneratorProductName"), "Autodeskï¿½ Character Generator");
		assertEquals(homePage.getValueFromGUI("characterGeneratorProductName"), "Autodesk® Character Generator");
		Util.printInfo("checking weather the image is present or not");
//		homePage.verifyFieldExists("characterGeneratorImage");
		Util.printInfo("verifying the characterGenerator service Image");
		
		String OnlineMapImage=homePage.getAttribute("characterGeneratorImage", "src");
		String[] arr=OnlineMapImage.split("badges/");
		Util.printInfo(arr[0]);
		Util.printInfo(arr[1]);
//		homePage.verifyFieldExists("onlineMapImage");
		String ImageName=testProperties.getConstant("characterGeneratorImage");
		assertEquals(ImageName, arr[1]);
		String newURL = testProperties.getConstant("AUTOCAD_CharacterGenerator");
		Util.printInfo("Clicking on access now button");
		
		if(homePage.isFieldVisible("characterGeneratorAccessLink")){
		homePage.click("characterGeneratorAccessLink");
		System.out.println("******************59*************************");
		Capabilities capabilities = ((RemoteWebDriver)driver).getCapabilities();
		String browserName=capabilities.getBrowserName();
		if(!browserName.equalsIgnoreCase("safari")){
			System.out.println("After clicking on characterGeneratorAccessLink ");
		}else{
			try{
				System.out.println("******************66*************************");	
					SafariCertificate();
					}
			catch(Exception e){
					e.printStackTrace();
					System.out.println("******************69*************************");	
					}
				}
		Util.sleep(25000);
		}else{
			EISTestBase.fail("Access now button is not pressent :: ");
		}
		String winHandleBefore = driver.getWindowHandle();
System.out.println("******************77*************************");	
		Util.sleep(15000);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);    
		}	
		Util.sleep(35000);
		String URL_New = driver.getCurrentUrl();
		Util.printInfo("Validating the accessNow link navigation");
		assertEquals(URL_New, newURL);
		mainWindow.select();

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
