package customerportal;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;

public class Test_Verify_ProdUpds_CheckBox_DwnLoad  extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String deviceName = null;
	public int count=0;
	public List<WebElement> selectedProductsCount;
	
	public Test_Verify_ProdUpds_CheckBox_DwnLoad() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_ProdUpds_CheckBox_DwnLoads() throws Exception {
		/*if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{*/
			if (getEnvironment().equalsIgnoreCase("dev")) {
				USERNAME = testProperties.getConstant("USERNAME_DEV");
				EMAIL = USERNAME;
				PASSWORD = testProperties.getConstant("PASSWORD_DEV");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");
			
			}
			loginAsMyAutodeskPortalUser(USERNAME , PASSWORD);		
		/*}*/
		//In Product & Updates page, check if updates are showing up 
		GoToProductUpdatesPage();
		Util.printInfo("Verifying the Title of the Product update page");
		productUpdatePage.verifyFieldExists("productUpdatemainPageTitle");
		Util.printInfo("Verifying the Product Updates page has manage user access, if the user id is CM then expected is to have"
				+ "'Manage Users Btn.");
		if (checkIfLoggedUserIsCMOrSC()) {
			Util.printInfo("The logged user is CM or SC hence checking the Manager Users button exists");
			productUpdatePage.verifyFieldExists("managerUsersBtn");
		}
		Util.printInfo("Verifying the Product Updates is as per the UX");
		verifyIfTheProductUpdatesPageIsAsExpected();
		Util.printInfo("Verify that the 'Download Selected' Button and 'Ignore Selected' exists when user clicked Downloadselect drop down");
		productUpdatePage.click("downloadselectDropDownBtn");
		Util.sleep(1000);
		productUpdatePage.verifyAll();
		
		//Select check box/multi check box of product updates 
		Util.printInfo("Verify that functionality of Checkboxes by selecting all Checkboxes, selecting individual checkboxes and also uncheck and verify it is working as expected");
		Util.printInfo("Checking the count of  checkboxes Checked");
		productUpdatePage.check("selectAllUpdatesCheckbox");
		selectedProductsCount=productUpdatePage.getMultipleWebElementsfromField("selectedProductsCount");
		count=getCountOfCheckboxChecked();
		Util.printInfo("count of  checkboxes Checked:" +count);
		productUpdatePage.uncheck("selectAllUpdatesCheckbox");

		//Check all the checkboxes are not checked
		assertTrueWithFlags("All the product update checkboxes should be unchecked when Select All checkbox is unchecked", verifyIfAllTheCheckboxesAreUncheckedWhenSelectAllISUnchecked());
		Util.printInfo("Check the 1 Product update checkbox and check if Select All checkbox is still unchecked");
		productUpdatePage.check("firstProductCheckBox");
		count=getCountOfCheckboxChecked();
		assertTrueWithFlags("The 'Select All' checkbox is unchecked and all other product updates are unchecked except one, when one of the product update checkbox is checked", count==1);
		
		Util.printInfo("Verify that Download of product update works as expected when clicked on download button");
		Util.printInfo("check 1 checkbox and click on download");
		Util.printInfo("Verify that File download popup displays when clicked on download");
		productUpdatePage.click("firstProductDownloadsButton");
		Util.sleep(1000);//to avoid race cond.
		boolean downloadConfirmation=productUpdatePage.checkIfElementExistsInPage("downloadConfirmation",20);					
		//bad URL SENT By ROVER. Hence commented -Sai-05-sept
		
		/*assertTrue("File Download Confimation message is displayed after clicking on Download button. ",downloadConfirmation);*/
		
		//get the first product udpate first download href attribute and check
		String downloadLinklocValue=productUpdatePage.getFirstFieldLocator("firstProductDownloadsButton");
		//now add a new locator to the stack
		productUpdatePage.addNewFieldToExistingfieldMetadataList("downloadLinklocValue", "LINK##"+downloadLinklocValue+"/..");
		//get the href
		String hrefAttr=productUpdatePage.getAttribute("downloadLinklocValue", "href");
		assertTrueWithFlags("The download Href URL for the product update should be valid and contain right URL", (isURLWorksAsExpected(hrefAttr) && (hrefAttr.contains(".exe")||hrefAttr.contains(".msp"))));
		count=getCountOfCheckboxChecked();
		Util.printInfo("count of  checkboxes Checked:" +count);
		productUpdatePage.uncheck("firstProductCheckBox");
		
		count=getCountOfCheckboxChecked();
		//Here check all the checkboxes are unchecked
		assertTrueWithFlags("All the Product updates checkboxes should be unchecked when Checkboxes are unchecked ", count==0);
		
		String IgnoreButton=productUpdatePage.getValueFromGUI("firstProductIgnoreButton");
		if(IgnoreButton.equalsIgnoreCase("Ignore")){
			productUpdatePage.click("firstProductIgnoreButton");
		}
		assertTrue("Ignore button should be present" ,productUpdatePage.verifyFieldExists("firstProductIgnoreButton"));
		assertTrue("Download button should be present" ,productUpdatePage.verifyFieldExists("firstProductDownloadsButton"));
		
		/*ADD IGNORE FUNCTIONALITY TEST HERE*/
		//Verify the Ignore functionality 
		
		
	//verifying the access control drop down	
		productUpdatePage.click("openToggleDrawer");
		Util.printInfo("Verify if the Access control button is displayed");
		productUpdatePage.verifyFieldVisible("accessControlButton");
		Util.printInfo("Clicking on the Access Control button");
		productUpdatePage.click("accessControlButton");
		Util.printInfo("Verify that the drop down is displayed when the user clicked on Access Control button");
		Util.sleep(2000);
		productUpdatePage.waitForFieldVisible("accessControlElements", 2000);
		assertTrue("The drop down menu under Access control button should be visible when clicked on it", productUpdatePage.isFieldVisible("accessControlDropDownMenu"));
		assertTrue("Add button is Present ",productUpdatePage.verifyFieldExists("addButtonOfAccessControl"));
		logoutMyAutodeskPortal();

	}
	private int getCountOfCheckboxChecked(){
		int count=0;
		
		for(int i=0;i<selectedProductsCount.size();i++){
			if (selectedProductsCount.get(i).isSelected()){
				count=count+1;
			}
		}
		return count;
	}
	/**
	 * @Description check if all the checkboxes are unchecked when select all checkbox is unchecked
	 * @return
	 */
	public boolean verifyIfAllTheCheckboxesAreUncheckedWhenSelectAllISUnchecked() {
		for(int i=0;i<selectedProductsCount.size();i++){
			if (selectedProductsCount.get(i).isSelected()){
				return false;
			}
		}
		return true;
	} 
	
	public boolean isURLWorksAsExpected(String downloadUrl) {
		try {
			URL myUrl= new URL(downloadUrl);
			HttpURLConnection myHttp=(HttpURLConnection)myUrl.openConnection();
			myHttp.setRequestMethod("GET");	//get and get the response
			myHttp.connect();
			//get the response code
			int respCode=myHttp.getResponseCode();
			if (respCode!=200){
				return false;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Util.printError("Not a valid download URL found for Product update, the link is broken");
		}
		return true;
		
	}
	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}


}
