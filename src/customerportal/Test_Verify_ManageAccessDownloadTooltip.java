package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_ManageAccessDownloadTooltip extends CustomerPortalTestBase {
	public Test_Verify_ManageAccessDownloadTooltip() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_OnlineMap() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		//Verifying Download desktop Tool Tip

		//clicking on manage access versions
		
		//Getting contract numbers from products and services page
		List<WebElement> ListOfContracts = null;
		ArrayList<String> alist=new ArrayList<String>();
		try{
		ListOfContracts = homePage.getMultipleWebElementsfromField("ListOfContracts");
		}catch(Exception e){
			System.out.println(e);
		}

		for(WebElement EachContract : ListOfContracts){
		WebElement Element=driver.findElement(By.xpath(".//*[contains(@id,'"+EachContract.getText()+"')]/div[2]/div[2]/div/div/div[1]/a[2]/div"));
		
		//checking manage access button and clicking on that
		
		if(Element.isDisplayed()){
		Element.click();
		Util.sleep(4000);
		List<WebElement> ManageAccessVersions=null;
		//verifying manage access window exists or not after clicking on manage access window
		
		homePage.verifyFieldExists("ManageAccessWindow");
		Util.sleep(4000);
		Actions DoActions=new Actions(driver);
		Util.sleep(4000);
		DoActions.moveToElement(ReturnWebelement()).build().perform();
		Util.sleep(4000);
		
		if(homePage.isFieldVisible("DownloadDeskTop")){
			homePage.verifyFieldExists("DownloadDeskTop");
			homePage.verifyFieldExists("DownloadDeskTopText");
			homePage.verifyFieldExists("DownloadDesktopEditAccess");
			homePage.clickAndWait("DownloadDesktopEditAccess","manageUsers");
			Util.sleep(5000);
			homePage.verifyFieldExists("manageUsers");
			toolTipWindowCloseValidations();
			
		}else{
			EISTestBase.fail("Download and Desktop access tool tip does not exists ");
		}
		
		//homePage.click("ManageAccessSave");
	   }
		break;
      }
	
	
		
		logoutMyAutodeskPortal();
}
	private void toolTipWindowCloseValidations() {
		homePage.clickAndWait("productsAndServices", "installNow");
		homePage.click("manageAccessProductIcon");
		//Mouse Hover outside Tooltip window
		Util.sleep(4000);
		Actions DoActions=new Actions(driver);
		Util.sleep(4000);
		DoActions.moveToElement(ReturnWebelement()).build().perform();
		Util.sleep(4000);
		DoActions=new Actions(driver);
		Util.sleep(4000);
		DoActions.moveToElement(driver.findElement(By.xpath("//div[@class='user-list']//p[contains(@class,'name')]"))).build().perform();
		Util.sleep(4000);
		homePage.verifyFieldNotVisible("DownloadDeskTop");
		//Click outside Tooltip window
		DoActions=new Actions(driver);
		Util.sleep(4000);
		DoActions.moveToElement(ReturnWebelement()).build().perform();
		Util.sleep(4000);
		DoActions=new Actions(driver);
		Util.sleep(4000);
		driver.findElement(By.xpath("//div[@class='user-list']//p[contains(@class,'name')]")).click();
		Util.sleep(4000);
		homePage.verifyFieldNotVisible("DownloadDeskTop");
		
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
