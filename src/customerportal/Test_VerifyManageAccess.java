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

public class Test_VerifyManageAccess extends CustomerPortalTestBase {
	public Test_VerifyManageAccess() throws IOException {
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
		String[] VersionSplit=null;
		ArrayList<String> ToolTipList=new ArrayList<String>();
		ArrayList<String> ManageAccesslTipList=new ArrayList<String>();
		
		//Getting the total product versions that tool tip displays - TC56480
		Util.printInfo("The number of product versions in tool tip pressent is / are " +getToolTipProductVersions());
		
		//Splitting versions of tool tips
		ArrayList<String> ToolTipVersions=getToolTipProductVersions();
		
		ManageAccesslTipList=getManageAccessVersions();
		//Getting the total manage access versions - TC56480
		Util.printInfo("The number of product versions in manage access window is / are " +ManageAccesslTipList);
		
		//comparing both versions of tool tips and manage access versions
		
		if(ManageAccesslTipList.equals(ToolTipVersions)){
			assertTrue("The Tool Tip versions and manage access versions are same ", ManageAccesslTipList.equals(ToolTipVersions));
		}else{
			EISTestBase.fail("The versions from Tool Tip and versions from manage access are not equal, please check ");
		}
		
		logoutMyAutodeskPortal();
		
}
	//Function to return number of versions displayed in tool tip
	public ArrayList<String> getToolTipProductVersions(){
		String ProductVersionsinToolTip=null;
		String[] VersionSplit=null;
		ArrayList<String> versions=new ArrayList<String>();
		try {
			
			//Getting contract numbers from products and services page
			List<WebElement> ListOfContracts=homePage.getMultipleWebElementsfromField("ListOfContracts");
			
			//Getting Each Contract
			for(WebElement EachContract : ListOfContracts){
				WebElement ToolTipVersions=ReturnWebelement(EachContract.getText());
				
				//check if two versions ( or ) tool tip exists then do mouse hover
				if(ToolTipVersions.isDisplayed()){
				Actions DoAction=new Actions(driver);
				Util.sleep(4000);
				DoAction.moveToElement(ReturnWebelement(EachContract.getText())).build().perform();
				Util.sleep(4000);
				ProductVersionsinToolTip=homePage.getValueFromGUI("ToolTipInner");
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
	
	public ArrayList<String> getManageAccessVersions(){
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
		WebElement Element=ReturnWebElementToClick(EachContract.getText());
		
		//checking manage access button and clicking on that
		
		if(Element.isDisplayed()){
		Element.click();
		Util.sleep(4000);
		List<WebElement> ManageAccessVersions=null;
		//verifying manage access window exists or not after clicking on manage access window
		
		homePage.verifyFieldExists("ManageAccessWindow");
		homePage.click("ManageAccessWindowVersions");
		Util.sleep(4000);
		try {
			ManageAccessVersions=homePage.getMultipleWebElementsfromField("ManageAccessWindowVersions1");
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		for(WebElement EachManageAccessVersion : ManageAccessVersions){
			alist.add(EachManageAccessVersion.getText());
		}
		homePage.click("ManageAccessSave");
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
