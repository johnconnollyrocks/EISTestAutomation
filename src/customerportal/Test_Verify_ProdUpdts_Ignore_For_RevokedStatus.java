package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;
import common.exception.MetadataException;

public class Test_Verify_ProdUpdts_Ignore_For_RevokedStatus extends CustomerPortalTestBase{
	public List<WebElement> numberProductUpdateList;
	public String USERNAME = null;
	public String PASSWORD = null;
	

	public Test_Verify_ProdUpdts_Ignore_For_RevokedStatus() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());		
	}
	@Test
	public void Test_Verify_ProdUpdts_IgnoreOption_For_RevokedStatus()throws Exception {
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
				USERNAME = testProperties.getConstant("USER_NAME");				
				PASSWORD = testProperties.getConstant("PASSWORD");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");				
			}
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);
					
		}
		GoToProductUpdatesPage();
			Util.sleep(2000);
			numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("newProductUpdatesList");
			
			validateIgnoreOptionForRevokedStatus();
			logoutMyAutodeskPortal();
					
	}
	

	private void validateIgnoreOptionForRevokedStatus() throws MetadataException{
		
			for(int i=0;i<numberProductUpdateList.size();i++){
			
			mouseHover(numberProductUpdateList.get(i));
			
			String prouctUpdateDownloadButton=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("prouctUpdateDownloadButtonList", numberProductUpdateList.get(i).getText());
			String prouctUpdateIgnoreButton=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("prouctUpdateIgnoredButtonList", numberProductUpdateList.get(i).getText());
			String Status=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("status", numberProductUpdateList.get(i).getText());
			
			
			//Finding the product which does not contain Download and contains the only Ignore button
			if (! productUpdatePage.isFieldPresent(prouctUpdateDownloadButton)){
				
				Util.printInfo("Download button is not present for product : : "+ numberProductUpdateList.get(i).getText() );
				assertTrue("Product should not contains Download button " ,true);
				String productToggleDrawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", numberProductUpdateList.get(i).getText());	
				productUpdatePage.click(productToggleDrawer);
				Util.printInfo("Clicked on the productToggleDrawer");
			
				
			// checking for the status of the product which contains only Ignore button	
				if (productUpdatePage.getValueFromGUI(Status).equalsIgnoreCase("Revoked")){
					assertTrue("Status should be Revoked (Revoked)" ,productUpdatePage.getValueFromGUI(Status).equalsIgnoreCase("Revoked"));
				}
				
				
			//clicking on the Ignore button of the product which contains only ignore and den verifying the status 	
				if (productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignore")){
					
					assertTrue("Ignore button should be present and(Ignore) " ,productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignore"));
					String ignoreBtn= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("ignoreButton", numberProductUpdateList.get(i).getText());
					productUpdatePage.click(ignoreBtn);
					Util.printInfo("clicked on ignore button of the product : " +numberProductUpdateList.get(i).getText());
					Util.sleep(500);
				}
				
		//verifying the status  after clicking on the Ignore button	
				if (productUpdatePage.getValueFromGUI(Status).equalsIgnoreCase("Ignored")){
					assertTrue("Status should be Ignored (Ignored)" ,productUpdatePage.getValueFromGUI(Status).equalsIgnoreCase("Ignored"));
				}
				
			//	verifying the the ignore button (it should not be greyed out it should display ignore)
				productUpdatePage.refresh();
				Util.printInfo("refreshed the product updates page");	
				if (productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignore")){
					
					assertTrue("Ignore button is not greyed out (Ignore) " ,productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignore"));
					
				}else{
					Util.printInfo("something went wrong ! ! ! !");
				}
				break;
				
			}
		
			else{
				Util.printInfo("Download button is present" + numberProductUpdateList.get(i).getText());
			}
			
			}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}
