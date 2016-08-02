package bornincloud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;

public class Test_Verify_Links_Under_Support_Tab_In_CP extends BornInCloudTestBase {

	public String href;
	public String winHandleBefore;
	public String expectedURL;
	BornInCloud bic ;
	public Test_Verify_Links_Under_Support_Tab_In_CP() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Verify_Links_In_Support_Tab() throws Exception {
		
		bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		
		loginToPortalAsBicUser();
		
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
		homePage.waitForFieldVisible("supportTab", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed, Support Tab Found.", homePage.checkIfElementExistsInPage("supportTab", 20));


		//TC498: Display Support at the Top of Portal for BIC Customers - "Browse support & learning"
			assertTrue("Browser Support & Learning option found for the user in Support.",homePage.checkIfElementExistsInPage("browserSupportAndLearning", 20));
			href=homePage.getAttribute("browserSupportAndLearning", "href");
			expectedURL="http://www.autodesk.com/accounts-support";
			if(href.equalsIgnoreCase(expectedURL)){
				assertTrueCatchException("href attribute for Browser Support & Learning link is pointed to correct URL.URL: "+href, true);
			}else{
				EISTestBase.failTest("href attribute for Broswer Support & Learning link is pointing to wrong URL Expected: "+expectedURL +"Actual: "+href);
			}
			homePage.click("browserSupportAndLearning");
			String winHandleBefore = homePage.getWindowHandle();
	        for(String winHandle : driver.getWindowHandles()){
	            driver.switchTo().window(winHandle);
	        }
	        if(getCurrentURL().equalsIgnoreCase("http://knowledge.autodesk.com/support")){
				assertTrueCatchException("After clicking on Browser Support& Learning link it navigated to expected page. Expected URL:"+getCurrentURL(), true);
			}
	        driver.close();
	        driver.switchTo().window(winHandleBefore);
        //////////////////////////////////////////////////////////
	        
        //TC500: Display Support at the Top of Portal for BIC Customers (5/30) - Contact us
	        assertTrue("Contact us option found for the user in Support.",homePage.checkIfElementExistsInPage("contactUs", 20));
			href=homePage.getAttribute("contactUs", "href");
			expectedURL="http://www.autodesk.com/accounts-contact-us";
			if(href.equalsIgnoreCase(expectedURL)){
				assertTrueCatchException("href attribute for Contact us link is pointed to correct URL.", true);
			}else{
				EISTestBase.failTest("href attribute for Contact us link is pointing to wrong URL Expected: "+expectedURL +"Actual: "+href);
			}
			homePage.click("contactUs");
			winHandleBefore = homePage.getWindowHandle();
	        for(String winHandle : driver.getWindowHandles()){
	            driver.switchTo().window(winHandle);
	        }
	        if(getCurrentURL().equalsIgnoreCase("http://knowledge.autodesk.com/contactus")){
				assertTrueCatchException("After clicking on Contact us link it navigated to expected page. Expected URL:"+getCurrentURL(), true);
			}
	        driver.close();
	        driver.switchTo().window(winHandleBefore);
	    //////////////////////////////////////////////////////////
	        
	    //TC501: Display Support at the Top of Portal for BIC Customers - Contact us for Fusion 360
	        assertTrue("Contact us for Fusion 360 option found for the user in Support.",homePage.checkIfElementExistsInPage("contactUsFoprFusion360", 20));
			href=homePage.getAttribute("contactUsFoprFusion360", "href");
			expectedURL="http://www.autodesk.com/accounts-bic-fusion";
			if(href.equalsIgnoreCase(expectedURL)){
				assertTrueCatchException("href attribute for Contact us for Fusion 360 link is pointed to correct URL.", true);
			}else{
				EISTestBase.failTest("href attribute for Contact us for Fusion 360 link is pointing to wrong URL Expected: "+expectedURL +"Actual: "+href);
			}
			homePage.click("contactUsFoprFusion360");
			winHandleBefore = homePage.getWindowHandle();
	        for(String winHandle : driver.getWindowHandles()){
	            driver.switchTo().window(winHandle);
	        }
	        if(getCurrentURL().equalsIgnoreCase("http://knowledge.autodesk.com/contactus")){
				assertTrueCatchException("After clicking on Contact us for Fusion 360 Lins it navigated to expected page. Expected URL:"+getCurrentURL(), true);
			}
	        driver.close();
	        driver.switchTo().window(winHandleBefore);
	    //////////////////////////////////////////////////////////
	
	    assertFalse("View My Support Cases option is not shown for enduser.",homePage.checkIfElementExistsInPage("viewMySupportCases", 20));
		
	    logOutFromPortal();
		launchIPP(getPortalURL());
		loginToPortalAsNonBicUser();
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
		homePage.waitForFieldVisible("supportTab", 30000);
		
		assertFalse("Contact us for Fusion 360 option is not displayed for the user, who is not having fusion 360 product",homePage.checkIfElementExistsInPage("contactUsFoprFusion360", 20));
		
		
	}
	public void loginToPortalAsBicUser() throws Exception{

		String env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("USER_ID_DEV"),
					testProperties.getConstant("PASSWORD_DEV"));
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
		}
	}
	public void loginToPortalAsNonBicUser() throws Exception{
		
		String env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("NON_BIC_USER_ID_DEV"),
					testProperties.getConstant("NON_BIC_PASSWORD_DEV"));
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("NON_BIC_USER_ID_STG"),
					testProperties.getConstant("NON_BIC_PASSWORD_STG"));
		}
	}
	public void logOutFromPortal(){
		homePage.click("arrow");

		homePage.click("signout");
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
