package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.EISTestBase;
import common.Util;


public class Test_Verify_ProductUpdates_Download_button extends CustomerPortalTestBase{
	
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String INSTANCE_NAME = null;
	public String SCEmailId=null;
	public String contractNumbr="";
	public String Contracts=null;
	public List<WebElement> numberProductUpdateList;
	public List<WebElement> numberDownloadButtons;
	public int count=0;
	 
	
	
	public Test_Verify_ProductUpdates_Download_button() throws IOException {
		super("Browser",getAppBrowser());						
	}
	
	@Before
	public void setUp() throws Exception {
		
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_VerifyProductUpdateDownloadButton() throws Exception {
		
		//Get the Login credentials from Jenkins if provided else go with one given in properties file
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
			loginAsMyAutodeskPortalUser(USERNAME , PASSWORD);	
					
		}
		GoToProductUpdatesPage();	
		
	//	numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("productUpdateList");
		numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("newProductUpdatesList");
//		numberDownloadButtons=productUpdatePage.getMultipleWebElementsfromField("prouctUpdateDownloadButtonList");
		
		ValidateDowloadButton();
		
		logoutMyAutodeskPortal();
		
	}
	
	public void ValidateDowloadButton() throws Exception {
		
		for(int i=0;i<numberProductUpdateList.size();i++){
			count=count+1;
			Actions actions = new Actions(driver);
			actions.moveToElement(numberProductUpdateList.get(i));
			actions.perform();
			Util.sleep(4000);
			String newDownloadBtnFieldloc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("prouctUpdateDownloadButtonList", numberProductUpdateList.get(i).getText());
			if( productUpdatePage.isFieldVisible(newDownloadBtnFieldloc)){
//			if(numberDownloadButtons.get(i).isDisplayed()){
//				assertTrue("Download Button is pressent for product "+count,numberDownloadButtons.get(i).isDisplayed());
				assertTrue("Download Button is pressent for product "+count,true);
			}else{
				EISTestBase.fail("Download Button is not pressent for product :: "+count);
			}
			
		}
	}
	@After
	public void tearDown() throws Exception {

		driver.quit();

		finish();
}
}
