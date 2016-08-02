package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_verify_StorageSpace extends CustomerPortalTestBase {
	public List<WebElement> numberDevicesList;
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;

	public Test_verify_StorageSpace() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_verify_StorageSpace_EndUser() throws Exception {
		//This is used to override the user name and password given in the test properties
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			if (getEnvironment().equalsIgnoreCase("dev")) {
				USERNAME = testProperties.getConstant("USERNAME");				
				PASSWORD = testProperties.getConstant("PASSWORD");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");
				
			}
			loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);
		}
			mainWindow.select();
			Util.sleep(2000);
			verifyStorageSpace();
			logoutMyAutodeskPortal();
					
	
	}

	private void verifyStorageSpace() throws Exception {
		
		Util.printInfo("Login as CM and edit Storage service");
		homePage.click("users");
		Util.printInfo("Clicked on Users");
		if(productUpdatePage.getValueFromGUI("endUserSeats").equals("0")){
			Util.printInfo("Storage is not assigned to this end user.Assign the storage space and check for the 25GB");
			Util.printInfo("Editing the end user storage space");
			productUpdatePage.click("endUserEditAccessLink");
			Util.sleep(1000);
			productUpdatePage.check("assignProducts");
			homePage.clickAndWait("saveButton", "endUserEditAccessLink");
			Util.printInfo("Saved the changes");
			logoutMyAutodeskPortal();
			loginAsMyAutodeskPortalUser(testProperties.getConstant("END_USER_NAME"),testProperties.getConstant("END_PASSWORD"));
			Util.printInfo("clicking on Reporting");
			homePage.click("reporting");
			Util.sleep(3000);
			String storageSpace = homePage.getValueFromGUI("myUsageStorageSpaceAvailable");
			Util.sleep(2000);
			String[] totalSpace = storageSpace.split("of");
			Util.printInfo("Storage space is " + totalSpace[1]);
			if(totalSpace[1].trim().equalsIgnoreCase("25 GB")){
			assertEquals(totalSpace[1].trim(), " 25 GB".trim());
			}else{
				EISTestBase.fail("Something is wrong pls check again");
			}
		}
		else{
			Util.printInfo("Storage is assigned to this end user.UnAssign the storage space and check for the 5GB");
			Util.printInfo("Editing the end user storage space");
			productUpdatePage.click("endUserEditAccessLink");
			Util.sleep(1000);
			productUpdatePage.uncheck("assignProducts");
			homePage.clickAndWait("saveButton", "endUserEditAccessLink");
			logoutMyAutodeskPortal();
			loginAsMyAutodeskPortalUser(testProperties.getConstant("END_USER_NAME"),testProperties.getConstant("END_PASSWORD"));
			Util.printInfo("clicking on Reporting");
			homePage.click("reporting");
			Util.sleep(3000);
			String storageSpace = homePage.getValueFromGUI("myUsageStorageSpaceAvailable");
			Util.sleep(2000);
			String[] totalSpace = storageSpace.split("of");
			Util.printInfo("Storage space is " + totalSpace[1]);
			if(totalSpace[1].trim().equalsIgnoreCase("5 GB")){
			assertEquals(totalSpace[1].trim(), " 5 GB".trim());
			}else{
				EISTestBase.fail("Something is wrong pls check again");
			}
		}
	}
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}