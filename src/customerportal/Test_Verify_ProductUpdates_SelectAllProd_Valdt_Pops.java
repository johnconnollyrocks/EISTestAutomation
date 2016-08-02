package customerportal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;


public class Test_Verify_ProductUpdates_SelectAllProd_Valdt_Pops extends CustomerPortalTestBase{
	
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String INSTANCE_NAME = null;
	public BufferedReader stdOutInput;
	public Process process;
	public int count;
	public String line;
	public List<WebElement> allChekboxsInProductUpdatePage;
	
	
	public Test_Verify_ProductUpdates_SelectAllProd_Valdt_Pops() throws IOException {
		super("Browser",getAppBrowser());						
	}
	
	@Before
	public void setUp() throws Exception {
		
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_VerifyProductUpdateMainPage() throws Exception {
		
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
				USERNAME = testProperties.getConstant("USERNAME_DEV");				
				PASSWORD = testProperties.getConstant("PASSWORD_DEV");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");				
			}
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);	
					
		}
		GoToProductUpdatesPage();
		Util.sleep(2000);
	//	productUpdatePage.click("selectAllUpdatesCheckbox");
		productUpdatePage.check("selectAllUpdatesCheckbox");
		
		allChekboxsInProductUpdatePage=productUpdatePage.getMultipleWebElementsfromField("articlesUpdatesCheckbox");
		
		//Get the number of checkboxes selected
		count=getCountOfCheckboxChecked();
		productUpdatePage.click("downloadSelectedBtn");
		/*if (productUpdatePage.isFieldVisible("productDownloadMultiple")){
			assertTrue("Expected pop message is displayed when user tried to download multiple products.",productUpdatePage.isFieldVisible("productDownloadMultiple"));
		}else{
			assertTrue("Expected pop message is not displayed when user tried to download multiple products.",false);
		}*/
		
		//While running from Jenkins the user.dir is <src path>\build so to avoid this
	     String buildPath=System.getProperty("user.dir");
	     String[] tempBuildpath=buildPath.split("\\\\");
	     if(tempBuildpath[tempBuildpath.length-1] ==tempBuildpath[tempBuildpath.length-2]){
	    	 Util.printInfo("Running from Jenkins, so truncate repeated build path");
	    	 buildPath=buildPath.substring(0,buildPath.lastIndexOf("\\"));
	    	 Util.printInfo("The Autoit file path is: "+buildPath);
	    	 
	     }
	     
	     if(!buildPath.contains("build"))
	     {
	    	 buildPath=buildPath+"\\build";
	     }
	     Util.printInfo("counting the browsers : ");
		//Execute AutoIT file to click on save button
	//	process=Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\PU_AutoITFile.exe "+getAppBrowser()+" "+"count"+" "+count);
	     process=Runtime.getRuntime().exec(buildPath+"\\PU_AutoITFile.exe "+getAppBrowser()+" "+"count"+" "+count);
		Util.sleep(2000); 
		stdOutInput = new BufferedReader( new InputStreamReader(process.getInputStream()));
		 
		 String lineData=null;
		while ((line=stdOutInput.readLine())!=null) {
			 
				Util.printInfo("Validate whether Download windows are equal to prodcut selected");
				System.out.println(line);
				lineData=line;
		}
		 if (assertEquals(lineData, "Download window found"+count)){
				assertTrue("Download windows are equal to prodcut selected",assertEquals(lineData, "Download window found"+count));
		 }
		 
		
	}
	
	public int getCountOfCheckboxChecked(){
		int count;
		count=0;
		for(int i=0;i<allChekboxsInProductUpdatePage.size();i++){
			if (allChekboxsInProductUpdatePage.get(i).isSelected()){
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
