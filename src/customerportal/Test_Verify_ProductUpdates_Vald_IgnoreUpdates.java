package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_ProductUpdates_Vald_IgnoreUpdates extends CustomerPortalTestBase{
	
	public List<WebElement> numberProductUpdateList;
	public List<WebElement> numberIgnoreButtons;
	private ArrayList<String> lsUserCreds= null;
	public boolean atleastOneIgnorebtnIsValidated=false;

	public Test_Verify_ProductUpdates_Vald_IgnoreUpdates() throws IOException {
		super("Browser",getAppBrowser());		
	}
	
	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void Test_Verify_ProductUpdatesValidateIgnoreUpdates() throws Exception {
		
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){
			System.out.println("This test cannot accept the user account information from Jenkins.Since this test needs to verify" +
					"the Ignore functionality for set of user accounts. Please add the additional user accounts in the test properties file");
		}else{
			System.out.println("No Login Credentials found from Jenkins");
		}
		String allUserCreds= testProperties.getConstant("LOGINCREDENTIALS");
		if (getEnvironment().equalsIgnoreCase("dev")) {
			 allUserCreds= testProperties.getConstant("LOGINCREDENTIALS_DEV");	
		} else if (getEnvironment().equalsIgnoreCase("stg")) {
			 allUserCreds= testProperties.getConstant("LOGINCREDENTIALS_STG");	
		}
		
		lsUserCreds= new ArrayList<>(Arrays.asList(allUserCreds.split(",")));
		for(int i=0;i<lsUserCreds.size()-1;i+=3){
			String userType=lsUserCreds.get(i);
			Util.printInfo("********************************************************************************");
			Util.printInfo("Verify that Ignore button is not greyed out for user Account: "+userType );			
			String lUserName=lsUserCreds.get(i+1);
			String lPassword=lsUserCreds.get(i+2);
			loginAsMyAutodeskPortalUser(lUserName,lPassword);
			GoToProductUpdatesPage();
			/*numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("productUpdateList");*/
			numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("newProductUpdatesList");
			validateIgnoreButton();	
			logoutMyAutodeskPortal();
			loginAsMyAutodeskPortalUser(lUserName,lPassword);
			GoToProductUpdatesPage();
			Util.printInfo("********************************************************************************");
			Util.printInfo("Verify that Ignore button is greyed out for user Account: "+userType );
			/*numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("productUpdateList");*/
			numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("newProductUpdatesList");
			validateIgnoredButton();
			logoutMyAutodeskPortal();
		}
			
	}
	private void validateIgnoreButton() throws MetadataException{
		for(int i=0;i<numberProductUpdateList.size();i++){
			
			mouseHover(numberProductUpdateList.get(i));
			Util.printInfo("Products "+numberProductUpdateList.get(i).getText() );
			String prouctUpdateIgnoreButton=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("prouctUpdateIgnoredButtonList", numberProductUpdateList.get(i).getText());
			
			if (productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignore")){
				
				assertTrue("Ignore button should be present and(Ignore) " ,productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignore"));
				String ignoreBtn= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("ignoreButton", numberProductUpdateList.get(i).getText());
//				productUpdatePage.click("ignoreButton");
				productUpdatePage.click(ignoreBtn);
				Util.printInfo("clicked on ignore button of the product : " +numberProductUpdateList.get(i).getText());
				
			}
			 if(productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignored")) {
				
				Util.printInfo(" Ignore button is greyed out (Ignored)" + numberProductUpdateList.get(i).getText());
			}
			 else if (!productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignore")&&productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignored")){
				 EISTestBase.fail("There are no products to perform the actions please choose another user :");
				 
			 }
		}
	}
	private void validateIgnoredButton(){
		for(int i=0;i<numberProductUpdateList.size();i++){			
//			mouseHover(numberProductUpdateList.get(i));
			Util.printInfo("Products "+numberProductUpdateList.get(i).getText() );
			String prouctUpdateIgnoreButton=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("prouctUpdateIgnoredButtonList", numberProductUpdateList.get(i).getText());
			
			if (productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignored")){
				
				assertTrue("Ignore button is greyed out (Ignored) " ,productUpdatePage.getValueFromGUI(prouctUpdateIgnoreButton).equalsIgnoreCase("Ignored"));
				
			}else{
				Util.printInfo("Ignore button is not greyed out");
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}

}
