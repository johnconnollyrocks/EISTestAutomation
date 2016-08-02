package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_VersionPermissionsOf_EndUser extends CustomerPortalTestBase {
	public Test_Verify_VersionPermissionsOf_EndUser() throws IOException {
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
		//Setting permissions to the EndUser
		givingVersionPermissionsToUser();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("ENDUSER_NAME") , testProperties.getConstant("ENDUSER_PASSWORD"));
		homePage.refresh();
		Util.sleep(4000);
		homePage.verifyFieldNotExists("EndUserVersions");
		
		homePage.click("Logout");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		assignPermissionstoEndUser();
		
		logoutMyAutodeskPortal();
}
	
	public void givingVersionPermissionsToUser(){
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
		Util.printInfo("clicking on Individual Users versions");
		homePage.click("ManageAccessCheckBoxVersions");
		List<WebElement> Web=null;
		try {
			Web = homePage.getMultipleWebElementsfromField("ManageAccessVersionsElements");
		} catch (MetadataException e) {
			e.printStackTrace();
		}
		for(WebElement EachElement : Web ){
		//EachElement.click();
		Util.sleep(6000);
		unCheckChecKBox("InputIndividualCheckBoxOfUser","LabelIndividualCheckBoxOfUser");
		EachElement.click();
		Util.sleep(6000);
		Util.printInfo(" Verifying cancel button and close button functionality ");
		homePage.click("CloseButtonManageAccess");
		if(homePage.isFieldVisible("ManageAccessCancelDailogPopup")){
			homePage.verifyFieldExists("ManageAccessCancelDailogPopup");
			}else{
				EISTestBase.fail("There is no cancel popup exists after changing permissions, clicking on Close button on manage access window ");
			}
			homePage.click("ManageAccessSave");
			homePage.click("Logout");
			break;
		 }
	   }
		break;
      }
	}
	
	
	
	public void assignPermissionstoEndUser(){
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
		Util.printInfo("clicking on Individual Users versions");
		homePage.click("ManageAccessCheckBoxVersions");
		List<WebElement> Web=null;
		try {
			Web = homePage.getMultipleWebElementsfromField("ManageAccessVersionsElements");
		} catch (MetadataException e) {
			e.printStackTrace();
		}
		for(WebElement EachElement : Web ){
		//EachElement.click();
		Util.sleep(6000);
		checkChecKBox("InputIndividualCheckBoxOfUser","LabelIndividualCheckBoxOfUser");
		EachElement.click();
		Util.sleep(6000);
		homePage.click("ManageAccessSave");
		break;
		}
	   }
		break;
	  }
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
