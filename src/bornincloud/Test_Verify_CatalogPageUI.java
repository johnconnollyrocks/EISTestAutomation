package bornincloud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;


public class Test_Verify_CatalogPageUI extends BornInCloudTestBase {

	public Test_Verify_CatalogPageUI() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() throws Exception {
		/*String baseURL = getBaseURL() + "userId="
				+ testProperties.getConstant("USER_ID") + "&country="
				+ testProperties.getConstant("COUNTRY") + "&language="
				+ testProperties.getConstant("LANGUAGE");
		launchIPP(baseURL);*/
		String USERNAME = null;
		String COUNTRY = null;
		String LANGUAGE = null;
		String currencyType = null;	
		String url = null;
							
		if(getEnvironment().equalsIgnoreCase("DEV")){
			 USERNAME = testProperties.getConstant("USER_ID");
			 COUNTRY = testProperties.getConstant("COUNTRY");
			 LANGUAGE = testProperties.getConstant("LANGUAGE");
			 currencyType=testProperties.getConstant("currencyType");
			 url=getBaseURL()+"userId="+ USERNAME+"&country="+ testProperties.getConstant("COUNTRY")+"&lang="+ testProperties.getConstant("LANGUAGE");	
			}
		
			else if(getEnvironment().equalsIgnoreCase("STG")){
				 USERNAME = testProperties.getConstant("USER_ID_STG");			 
				 COUNTRY= testProperties.getConstant("COUNTRY_STG");
				 LANGUAGE = testProperties.getConstant("LANGUAGE_STG");
				 currencyType=testProperties.getConstant("currencyType_STG");
				 String hash = getclicHash(USERNAME, COUNTRY ,LANGUAGE);
				 url=getBaseURL()+"userId="+ USERNAME+"&country="+ testProperties.getConstant("COUNTRY")+"&lang="+ testProperties.getConstant("LANGUAGE") + hash ;				 
			}
		
		
		launchIPP(url);
		
	}

	@Test
	public void Test_Verify_CatalogPageUI_Validation() {
		// Verify Edition Button is Collapsed
		if (CatalogPage.isFieldVisible("EditionCollapse")) {
			CatalogPage.click("EditionCollapse");
			// Verify Autodesk Fusion 360 Subscription Header is Displayed
			assertTrueCatchException(
					"Autodesk Fusion 360 Subscription Header is Displayed",
					CatalogPage.checkFieldExistence("Autodesk360"));
			// Verify Choose Your Page Header is Displayed
			assertTrueCatchException("Choose Your Page Header is Displayed",
					CatalogPage.checkFieldExistence("ChooseYourPlan"));
			// Verify Basic Button is Displayed
			assertTrueCatchException("Basic Button is Displayed",
					CatalogPage.checkFieldExistence("Basic"));
			// Verify More Info Link is Displayed
			assertTrueCatchException("More Info Link is Displayed",
					CatalogPage.checkFieldExistence("MoreInfo"));

			// Verify Edition Button is Expanded
		} else if (CatalogPage.isFieldVisible("EditionExpand")) {
			// Verify Autodesk Fusion 360 Subscription Header is Displayed
			assertTrueCatchException(
					"Autodesk Fusion 360 Subscription Header is Displayed",
					CatalogPage.checkFieldExistence("Autodesk360"));
			// Verify Choose Your Page Header is Displayed
			assertTrueCatchException("Choose Your Page Header is Displayed",
					CatalogPage.checkFieldExistence("ChooseYourPlan"));
			// Verify Basic Button is Displayed
			assertTrueCatchException("Basic Button is Displayed",
					CatalogPage.checkFieldExistence("Basic"));
			// Verify More Info Link is Displayed
			assertTrueCatchException("More Info Link is Displayed",
					CatalogPage.checkFieldExistence("MoreInfo"));
		}
		if (CatalogPage.isFieldVisible("PlansCollapse")) {
			CatalogPage.click("PlansCollapse");
			Util.sleep(3000);
			/*assertTrueCatchException("Fusion 360 Annual Plan Displayed",
					CatalogPage.checkFieldExistence("AnnualPlan"));
			assertTrueCatchException("Fusion 360 Monthly Plan Displayed",
					CatalogPage.checkFieldExistence("MonthlyPlan"));*/
			/*assertEqualsCatchException("$100*",
					CatalogPage.getValueFromGUI("AnnualPrice"));
			assertEqualsCatchException("$35*",
					CatalogPage.getValueFromGUI("MonthlyPrice"));*/
			assertTrueCatchException(
					"Buy Button is Displayed Before Selecting Annual Plan",
					CatalogPage.checkFieldExistence("disableBuyItButton"));
			CatalogPage.click("AnnualPlanButton");
			assertTrueCatchException(
					"Buy Button is Enabled After Selecting Annual Plan",
					CatalogPage.checkFieldExistence("enableBuyItButton"));
			CatalogPage.click("enableBuyItButton");
			assertTrueCatchException(
					"Payment Page is Displayed After Selecting Annual Plan and Buy Button",
					CatalogPage.checkFieldExistence("Back"));
			CatalogPage.click("Back");
			Util.sleep(10000);
			assertTrueCatchException(
					"Buy Button is Displayed Before Selecting Monthly Plan",
					CatalogPage.checkFieldExistence("disableBuyItButton"));
			CatalogPage.click("MonthlyPlanButton");
			assertTrueCatchException(
					"Buy Button is Enabled After Selecting Monthly Plan",
					CatalogPage.checkFieldExistence("enableBuyItButton"));
			CatalogPage.click("enableBuyItButton");
			assertTrueCatchException(
					"Payment Page is Displayed After Selecting Monthly Plan and Buy Button",
					CatalogPage.checkFieldExistence("Back"));
			CatalogPage.click("Back");
			Util.sleep(10000);

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
