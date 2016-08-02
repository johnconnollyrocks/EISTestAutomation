package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_validate_behavior_of_Version_of_Install_Now_modal extends CustomerPortalTestBase {
	public Test_validate_behavior_of_Version_of_Install_Now_modal() throws IOException {
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
		//ArrayList to store Tool Tip versions
		ArrayList<String> ToolTipVersions=null;
		ArrayList<String> InstallNowWindowVersions=null;
		
		//Getting download permissions for user	TestCase - TC56485	
		ToolTipVersions=getToolTipProductVersions();
		
		Util.printInfo("The download permissions for user is / are :: " +ToolTipVersions);
		
		//Validating same permissions exists for user on Install now window
		InstallNowWindowVersions=getVersionsOfInstallNow();
		
		Util.printInfo("The versions on Install now window is / are :: " +InstallNowWindowVersions);
		
		//comparing permissions of InstallNow window and manageAccess permissions
		
		if(ToolTipVersions.equals(InstallNowWindowVersions)){
			assertTrue("The behavior of Version of Install Now modal is working fine ", ToolTipVersions.equals(InstallNowWindowVersions));
		}else{
			EISTestBase.fail("The behavior of Version of Install Now modal is not working as expected ");
		}
		
	}
	
	//Function to get tool tip versions
	public ArrayList<String> getToolTipProductVersions(){
		String ProductVersionsinToolTip=null;
		String[] VersionSplit=null;
		String ProductName=null;
		ArrayList<String> versions=new ArrayList<String>();
		try {
			
			//Getting contract numbers from products and services page
			List<WebElement> ListOfContracts=homePage.getMultipleWebElementsfromField("ListOfContracts");
			
			//Getting Each Contract
			for(WebElement EachContract : ListOfContracts){
				WebElement ToolTipVersions=ReturnWebelement(EachContract.getText());
				
				//check if two versions ( or ) tool tip exists then do mouse hover
				if(ToolTipVersions!=null){
				if(ToolTipVersions.isDisplayed()){
				//ProductName=homePage.getValueFromGUI("ProductName");
				Actions DoAction=new Actions(driver);
				Util.sleep(4000);
				DoAction.moveToElement(ReturnWebelement(EachContract.getText())).build().perform();
				Util.sleep(4000);
				ProductVersionsinToolTip=homePage.getValueFromGUI("ToolTipInner");
			 }else{
				 Util.printError("There is no more than one version is pressent for this CM ");
			  }
		       }else{
		    	   Util.printError("There is no more than one version is pressent for this CM ");
		       }
			}
		} catch (MetadataException e) {
			e.printStackTrace();
		}
		VersionSplit=ProductVersionsinToolTip.split(",");
		for(String Version : VersionSplit){
			versions.add(Version.trim());
		}
		//versions.add(ProductVersionsinToolTip.trim());
		return versions;
	}
	
	//Getting Versions on Install now Window
	public ArrayList<String> getVersionsOfInstallNow(){
		int count=0;
		//Getting contract numbers from products and services page
		List<WebElement> ListOfContracts = null;
		List<WebElement> ListOfVersions=null;
		ArrayList<String> alist=new ArrayList<String>();
		try{
		ListOfContracts = homePage.getMultipleWebElementsfromField("ListOfContracts");
		}catch(Exception e){
			System.out.println(e);
		}

		for(WebElement EachContract : ListOfContracts){
		String InstallNow= homePage.createFieldWithParsedFieldLocatorsTokens("InstallNowWindowVersions", EachContract.getText());
		if(homePage.isFieldVisible(InstallNow)){
		count=count+1;
		homePage.click(InstallNow);
		String CPInstallNow=homePage.createFieldWithParsedFieldLocatorsTokens("CPInstallNow", EachContract.getText());
		homePage.click(CPInstallNow);
		homePage.click("VersionOnInstallNowWindow");
		
		try {
			ListOfVersions = homePage.getMultipleWebElementsfromField("ListOfVersions");
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(WebElement EachVersion : ListOfVersions){
			alist.add(EachVersion.getText());
		}
		homePage.click("CloseInstallNowWindow");
		}
	 }
		return alist;
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
