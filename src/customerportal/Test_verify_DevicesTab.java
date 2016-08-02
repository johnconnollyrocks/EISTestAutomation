package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_verify_DevicesTab extends CustomerPortalTestBase{

	public String USERNAME = null;
	public String PASSWORD = null;
	

	public Test_verify_DevicesTab() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());		
	}
	@Test
	public void Test_verify_DevicesTab_ON_PAND_SPage() throws Exception {
		LoginAndGoToManageDevicesPage();		
		mainWindow.select();
		Util.sleep(10000);
		Util.printInfo("Verifying for the" +" Devices"+" Tab");
		Util.sleep(5000);
		productUpdatePage.verifyFieldExists("manageDevicesTab");
		Util.printInfo("Clicking on the " +" Devices"+" Tab");
		productUpdatePage.click("manageDevicesTab");
		Util.printInfo("Verifying for the filter dropdown, ExportAll On Devices Page");
		productUpdatePage.verify();
		logoutMyAutodeskPortal();
		

	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}
