package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

/**
 * Test class - Test_VerifyStorageSpaceAvailability
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_Accounts_Assigned_Services extends CustomerPortalTestBase {
	public Test_Verify_Accounts_Assigned_Services() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyStorageSpaceAvailability() throws Exception {
		
		//Login as CM and edit access by giving Storage service
		Util.printInfo("Login as CM and check storage access is present");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("CM_USER_NAME") , testProperties.getConstant("CM_PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		homePage.clickAndWait("users","editAccessPage");
		Util.sleep(2000);
		
		homePage.click("editAccessPage");
		Util.sleep(5000);
		Util.printInfo("Clicking on toggle drawer");
	//	driver.findElement(By.xpath("//article[contains(@id,'product_')]/div[1]/button")).click();
	//	driver.findElement(By.xpath("//article[1]/div[1]/button")).click();
									 
		Util.sleep(2000);
		Util.printInfo("Check Assign button whether its checked");
		
		//assignAllProducts();
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		
		checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
//		checkChecKBox("checkProduct1Input", "checkProdcut1Label");
		
/*		Boolean b = homePage.isChecked("checkProduct1"); 
		
		Util.printInfo("Status of Assign checkbox : "+b);
		
		if(b){
			Util.printInfo("Checkbox is checked");
			driver.findElement(By.xpath(".//article[contains(@id,'product_')]/div[2]/div[3]/div/form/label")).click();
			Util.sleep(200);
			driver.findElement(By.xpath(".//article[contains(@id,'product_')]/div[2]/div[3]/div/form/label")).click();
		}
		
		else{
			
			Util.printInfo("The check box is unchecked");
	 		Util.printInfo("Clicking on check box to make it checked");
	 			
			driver.findElement(By.xpath(".//article[contains(@id,'product_')]/div[2]/div[3]/div/form/label")).click();
			
			Util.sleep(200);
			driver.findElement(By.xpath(".//article[contains(@id,'product_')]/div[2]/div[3]/div/form/label")).click();
			Util.sleep(200);
			driver.findElement(By.xpath(".//article[contains(@id,'product_')]/div[2]/div[3]/div/form/label")).click();
		}
		
*/		
		/*
		
		homePage.clickAndWait("editAccess", "checkProduct1");
		Util.sleep(2000);
		String getStatus = homePage.getAttribute("checkProduct1", "class");
		
		
		
	 	if(getStatus.equalsIgnoreCase("toggle-switch enabled-false")){
	 		Util.printInfo("The check box is not checked");
	 		homePage.click("checkProduct1");
	 		driver.findElement(By.xpath(".//article[contains(@id,'product-')]/div[2]/div[3]/div/form/label")).click();
	 		
	 		Util.sleep(2000);
	 		Util.printInfo("The check box is checked now");	
	 		
	 	}
 	 	else if(getStatus.equalsIgnoreCase("toggle-switch enabled-true")){
 	 		Util.printInfo("The check box is checked");
 	 		driver.findElement(By.xpath(".//article[contains(@id,'product-')]/div[3]/ul/li[2]/form/label")).click();
 			Util.sleep(200);
 	 		driver.findElement(By.xpath(".//article[contains(@id,'product-')]/div[3]/ul/li[2]/form/label")).click();
 	 		
 	 	}
		*/
		Util.sleep(2000);
		homePage.clickAndWait("saveButton","editAccessPage");
		//Added Storage service
		Util.printInfo("Added Storage service");
		
		
		homePage.clickAndWait("productsAndServices","storageServiceType");
		Util.printInfo("Refreshing the page....");
		homePage.refresh();
		Util.sleep(40000);
		homePage.refresh();
		Util.printInfo("Page Refreshed");
		Util.sleep(40000);
		
		homePage.verify();
		/*Util.printInfo("Verifying Storage service of 25.0 GB");
		Util.sleep(5000);
		String storageSpace=homePage.getValueFromGUI("storageServiceSpaceAvailable");
		Util.sleep(5000);
		String[] totalSpace=((String)storageSpace).split("of");
		Util.printInfo("Storage space is "+totalSpace[1]);
		assertEquals(totalSpace[1].trim(),"25 GB");
		
		Util.printInfo("Verifying in Reporting Tab-My Usage");
		Util.printInfo("clicking on Reporting");
		homePage.clickAndWait("reporting","myUsageButton");
		homePage.clickAndWait("myUsageButton","myUsageStorageSpaceAvailable");
		Util.sleep(2000);
		storageSpace=homePage.getValueFromGUI("myUsageStorageSpaceAvailable");
		Util.sleep(2000);
		totalSpace=((String)storageSpace).split("of");
		Util.printInfo("Storage space is "+totalSpace[1]);
		assertEquals(totalSpace[1].trim(),"25 GB");*/
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

