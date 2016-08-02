package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;

public class Test_Verify_ProductUpdats_Access_Control extends CustomerPortalTestBase{

	public String USERNAME = null;
	public String PASSWORD = null;
	public List<WebElement> numberProductUpdateList;

	public Test_Verify_ProductUpdats_Access_Control() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());		
	}
	@Test
	public void Test_Verify_ProductUpdats_Access_ControlButton() throws Exception {
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
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);	
					
		}
		Util.sleep(2000);
		numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("newProductUpdatesList");
		
		for(int i=0;i<numberProductUpdateList.size();i++){ 
		
			String productToggleDrawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("accessToggleDrawer", numberProductUpdateList.get(i).getText());	
			productUpdatePage.click(productToggleDrawer);
			String accessControl= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("accessControl", numberProductUpdateList.get(i).getText());
			productUpdatePage.verifyFieldExists(accessControl);
			productUpdatePage.click(accessControl);
			
		}
		
		logoutMyAutodeskPortal();

	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}

