package customerportal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
 
import common.EISTestBase;
import common.Util;

public class Test_Verify_Download_Popups extends CustomerPortalTestBase {

	
	public Test_Verify_Download_Popups () throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_AccessSavedButton_method() throws Exception {
		if(getEnvironment().equalsIgnoreCase("DEV")){
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
			}
			else if(getEnvironment().equalsIgnoreCase("STG")){
				loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
			}
			Util.sleep(5000);
			CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
			loginPage = customerPortal.getLoginPage();
			homePage = customerPortal.getHomePage();
			mainWindow.select();
			Util.sleep(5000);
			homePage.click("productsUpdate");
			homePage.populateField("ProductUpdateSelectAll");
			homePage.click("DownloadBtn");
			Util.sleep(2000);
			
			 String RootDir=System.getProperty("user.dir");
			 String AutoItPath=RootDir+"\\Download.exe";
			 Process p1=Runtime.getRuntime().exec(AutoItPath);
			 BufferedReader in = new BufferedReader( new InputStreamReader(p1.getInputStream()));
			 String line;
			 boolean flag=false;
				while ((line=in.readLine())!=null) {
					flag=true;
					System.out.println(line);
				}
			if(flag){
				assertTrue("Two Download popup's are pressent :: ", true);
			}else{
				EISTestBase.fail("Download popup's are not pressent :: " );
			}
			
			/*Iterator<String> it=PopUps.iterator();
			while(it.hasNext()){
				String PopupHandle=it.next();
				driver.switchTo().window(PopupHandle);
				System.out.println(driver.getTitle());
				Util.sleep(2000);
				driver.close();
			}*/
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
