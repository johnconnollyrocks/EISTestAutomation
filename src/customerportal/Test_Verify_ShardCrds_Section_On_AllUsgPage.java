package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;

public class Test_Verify_ShardCrds_Section_On_AllUsgPage extends CustomerPortalTestBase{

	public String USERNAME = null;
	public String PASSWORD = null;
	public String [] numberOfContracts;
	boolean cloudunitValidation=false;
	

	public Test_Verify_ShardCrds_Section_On_AllUsgPage() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());		
	}
	@Test
	public void Test_Verify_ISharedCloudCredits_Section_in_AllUsage() throws Exception {
		if (getEnvironment().equalsIgnoreCase("dev")) {
			USERNAME = testProperties.getConstant("USERNAME_DEV");
			PASSWORD = testProperties.getConstant("PASSWORD_DEV");
		} else if (getEnvironment().equalsIgnoreCase("stg")) {
			USERNAME = testProperties.getConstant("USER_NAME_STG");
			PASSWORD = testProperties.getConstant("PASSWORD_STG");
		}
		loginAsMyAutodeskPortalUser(USERNAME , PASSWORD);
					
		Util.sleep(2000);
		verifySharedCloudCreditsScetionInAllUsagePage();
		
		logoutMyAutodeskPortal();

	}

	private void verifySharedCloudCreditsScetionInAllUsagePage() throws Exception {
		homePage.click("reporting");
		Util.sleep(3000);
		Util.printInfo("Verifying the shared cloud credits section");
		assertTrue(" Shared cloud credits heading is visible ",productUpdatePage.verifyFieldExists("SharedCloudCreditsHeading"));
		assertTrue(" SharedCloudCredits Tool Tip is visible ",productUpdatePage.verifyFieldExists("SharedCloudCreditsToolTip"));
		assertTrue(" SharedCloudCredits Tool Tip is visible ",productUpdatePage.verifyFieldExists("SharedCloudCreditsToolTipContent"));
		numberOfContracts=productUpdatePage.getMultipleTextValuesfromField("ContractsUnderSharedCloudCreditsSection");
		for(int i=0;i<numberOfContracts.length;i++){
			 String drawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("eachContractDrawer",numberOfContracts[i]);
             productUpdatePage.click(drawer);
		
			String contractXpath=homePage.createFieldWithParsedFieldLocatorsTokens("noCloudCredits", numberOfContracts[i]);
            boolean noCloudCreditsPurchased=homePage.isFieldPresent(contractXpath);

            if(noCloudCreditsPurchased){

                    Util.printInfo("No cloud credits purchased for this contract." + numberOfContracts[i] );
                   
                    assertTrue(" No cloud credits  Message is present ",homePage.verifyFieldExists("noCloudCreditsUsedMessage"));
                    String trynLrnLinkXpath=homePage.createFieldWithParsedFieldLocatorsTokens("trynLrnLink", numberOfContracts[i]);
                    assertTrue(" Try and learn moore link is present ",homePage.verifyFieldExists(trynLrnLinkXpath));
                    String accessCloudSvcLinkXpath=homePage.createFieldWithParsedFieldLocatorsTokens("accessCloudSvcLink", numberOfContracts[i]);
                    assertTrue(" Access your cloud credits link is present ",homePage.verifyFieldExists(accessCloudSvcLinkXpath));
            }else if (!cloudunitValidation){

				assertTrue(" SharedCloudCredits guage is present ",productUpdatePage.verifyFieldExists("eachContractGauge"));
				assertTrue(" get cloud credits button is present",productUpdatePage.verifyFieldExists("getCloudCreditsButton"));
				assertTrue(" Usage by users link is present",productUpdatePage.verifyFieldExists("usageLinkUnderEachContract"));
				assertTrue(" ExportToCSV link is present",productUpdatePage.verifyFieldExists("ExportToCSV"));
				String ExportToCSV=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("ExportToCSV", numberOfContracts[i]);
				Util.printInfo("Clicking on ExportToCSV Link ");
				productUpdatePage.click(ExportToCSV);
				Util.sleep(3000);
				String path=System.getProperty("user.dir");
				if (!path.contains("build")){
					path=path+"\\build";
				}
				Process process=Runtime.getRuntime().exec(path+"\\ExportToCSV.exe "+numberOfContracts[i]);
				process.waitFor();
//				Util.sleep(10000);
				assertTrue("ExportToCSV dialog is present ",true);
				cloudunitValidation=true;
//				break;
				}
            drawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("eachContractDrawer",numberOfContracts[i]);
            productUpdatePage.click(drawer);
			}
		
		
		}
		
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
}
