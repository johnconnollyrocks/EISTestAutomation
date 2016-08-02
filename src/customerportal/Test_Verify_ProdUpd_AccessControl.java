package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;

public class Test_Verify_ProdUpd_AccessControl extends CustomerPortalTestBase {
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	

	public Test_Verify_ProdUpd_AccessControl() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	
	@Test
	public void testProductUpdatesAccessControlFeatures() throws Exception{		
		LoginAndGoProductUpdatesPage();
		verifyAccessControlSettingsInProductUpdates();
		logoutMyAutodeskPortal();
	}
	
	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}

}
