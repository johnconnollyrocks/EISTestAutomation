package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;

public class Test_verify_SelectAll_ManageAccess extends CustomerPortalTestBase {
	
	public List<WebElement> allChekboxsInManageAccessWindow;
	public List<WebElement> numberOfProductList;
	public int count;
	public int versionCount;
	
	public List<WebElement> versionsCount;
	public Test_verify_SelectAll_ManageAccess() throws IOException {
		super("Browser",getAppBrowser());
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_verify_SelectAll_ManageAccess_Window() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
			Util.sleep(3000);
			mainWindow.select();
			Util.sleep(3000);
			homePage.verifyFieldExists("InstallNowButton");
			Util.printInfo("Clicking on the dropdown");
			homePage.click("productDropDown");
			homePage.click("manageAccess");
			Util.sleep(2000);
			homePage.check("selectAllManageAccessCheckBox");
			allChekboxsInManageAccessWindow=homePage.getMultipleWebElementsfromField("selectAllcheckboxes");
			
			count=getCountOfCheckboxChecked();
			Util.printInfo(" Total products selected =" +count);
			Util.sleep(2000);
			homePage.click("applyVersions");
			homePage.check("clickOnAll");
			assertTrue("allversions",true);
			homePage.check("version1");
			assertTrue("version1Status",true);
			homePage.uncheck("version1");
			homePage.check("version2");
			assertTrue("version2Status",true);
			homePage.uncheck("version2");
			homePage.click("clickAnyWhereOnPopUp");
			homePage.verify();
			
			/*versionsCount=homePage.getMultipleWebElementsfromField("versionsCount");
			versionCount=getCountOfCheckboxChecked();
			Util.printInfo(" Total Versions Selected : =" +count);*/
			
			homePage.click("manageAccessCloseButton");
				
		logoutMyAutodeskPortal();
			
}
	

	public int getCountOfCheckboxChecked(){
		int count;
		count=0;
		for(int i=0;i<allChekboxsInManageAccessWindow.size();i++){
			if (allChekboxsInManageAccessWindow.get(i).isSelected()){
				count=count+1;
			}
		}
		return count;
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