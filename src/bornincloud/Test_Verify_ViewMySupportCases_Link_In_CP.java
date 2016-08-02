package bornincloud;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebElement;

import com.sun.java.swing.plaf.windows.resources.windows;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ViewMySupportCases_Link_In_CP extends
		BornInCloudTestBase {

	public String href;
	public String winHandleBefore;
	public String expectedURL;

	public Test_Verify_ViewMySupportCases_Link_In_CP() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Verify_ViewMySupport_Link() throws Exception {
		String userID = "";
		String password = "";
		String[] userIDs = null;
		String[] passwords = null;
		String[] supports = null;
		String[] typeOfUsers = null;
		String userType="";
		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();

		String env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			userIDs = testProperties.getConstant("USER_ID_DEV").split(";");
			passwords = testProperties.getConstant("PASSWORD_DEV").split(";");
			supports = testProperties.getConstant("supportDetails").split(";");
			typeOfUsers = testProperties.getConstant("typeOfUsers").split(";");

		} else if (env.equalsIgnoreCase("STG")) {
			userIDs = testProperties.getConstant("USER_ID_STG").split(";");
			passwords = testProperties.getConstant("PASSWORD_STG").split(";");
			supports = testProperties.getConstant("supportDetails").split(";");
			typeOfUsers = testProperties.getConstant("typeOfUsers").split(";");
		}
		for (int i = 0; i < userIDs.length; i++) {
			userID = userIDs[i];
			password = passwords[i];
			String[] supportDetails=supports[i].split(":");
			userType=typeOfUsers[i];
			
			
			bic.login(userID, password);

			homePage.waitForElementToDisappear("pageLoadImg", 100000);
			homePage.waitForFieldVisible("supportTab", 300000);

			EISTestBase.assertTrue("Customer Portal Home Page is Displayed, Support Tab Found.",homePage.checkIfElementExistsInPage("supportTab",20));
			for(int j=0;j<supportDetails.length;j++){
				String supportLink=homePage.createFieldWithParsedFieldLocatorsTokens("supportForBICUsers", supportDetails[j]);
				assertTrue(supportDetails[j]+" link found in support tab for user "+userID+" with contract type "+userType,homePage.checkIfElementExistsInPage(supportLink,20));
			}
		
			homePage.click("arrow");

			homePage.click("signout");
			Util.sleep(5000);
		}
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
