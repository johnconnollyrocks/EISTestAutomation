package bornincloud;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_IPP_Cart_Persistence extends BornInCloudTestBase{

	String UserID="";
 	String offerings="";
 	String country="";
 	String expectedHeader="";
 	String monthlyPlan="";
 	String annualPlan="";
 	String baseEdition="";	
 	String quantity="";
 	String advancedEdition="";
	public Test_Verify_IPP_Cart_Persistence() throws IOException {
		super("browser");						
	}
	
	@Before
	public void setUp() throws Exception {
		if(getEnvironment().equalsIgnoreCase("dev")){
			UserID=testProperties.getConstant("userId_DEV");
			offerings=testProperties.getConstant("offeringId_DEV");
			country=testProperties.getConstant("country_DEV");
			monthlyPlan=testProperties.getConstant("monthlyPlan_DEV");
			annualPlan=testProperties.getConstant("annualPlan_DEV");
			baseEdition=testProperties.getConstant("baseEdition_DEV");
			advancedEdition=testProperties.getConstant("advancedEdition_DEV");
			expectedHeader=testProperties.getConstant("expectedHeader_DEV");
			quantity=testProperties.getConstant("Quantity_DEV");
			
		}else{
			UserID=testProperties.getConstant("userId_STG");
			offerings=testProperties.getConstant("offeringId_STG");
			country=testProperties.getConstant("country_STG");
			monthlyPlan=testProperties.getConstant("monthlyPlan_STG");
			annualPlan=testProperties.getConstant("annualPlan_STG");
			baseEdition=testProperties.getConstant("baseEdition_STG");
			advancedEdition=testProperties.getConstant("advancedEdition_STG");
			expectedHeader=testProperties.getConstant("expectedHeader_STG");
			quantity=testProperties.getConstant("Quantity_STG");
		}
		String url=getBaseURL() + "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings ;
		launchIPP(url);		
		Util.sleep(4000);
	}
	
	
	@Test
	public void Test_VerifyIPPFrameWork() throws Exception {
		
		SelectEdtion(baseEdition);
		ClickOnContinuButtonOnEditionPage();
		selectPlan(monthlyPlan);
		ClickOnEditionLink();
		validateEidtionPage(advancedEdition);
		selectPlan(annualPlan);
		ClickOnContinueOnPlanPage();
		ClickOnEditionLink();
		validateEidtionPage(baseEdition);
		selectPlan(monthlyPlan);
		ClickOnContinueOnPlanPage();
		validateEditionAndPlan(baseEdition,monthlyPlan);
		Util.printInfo("Going to click on Plan link in Add Cloud Credits page");
		homePage.click("planLink");
		String selectPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectPlan", monthlyPlan);
		assertTrue("Select Plan page should display after clicking on Plan link", homePage.checkIfElementExistsInPage(selectPlanXpath, 20));
		selectPlan(annualPlan);
		ClickOnContinueOnPlanPage();
		validateEditionAndPlan(baseEdition,annualPlan);
		homePage.click("continueButtonOnCloudCreditPage");
		Util.sleep(2000);
		ClickOnEditionLink();
		validateEidtionPage(advancedEdition);
		selectPlan(monthlyPlan);
		ClickOnContinueOnPlanPage();
		validateEditionAndPlan(advancedEdition,monthlyPlan);
		homePage.click("continueButtonOnCloudCreditPage");
		Util.sleep(2000);
		homePage.click("planLink");
		selectPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectPlan", annualPlan);
		assertTrue("Select Plan page should display after clicking on Plan link", homePage.checkIfElementExistsInPage(selectPlanXpath, 20));
		selectPlan(annualPlan);
		ClickOnContinueOnPlanPage();
		validateEditionAndPlan(advancedEdition,annualPlan);
		homePage.click("continueButtonOnCloudCreditPage");
		Util.sleep(2000);
		validateEditionAndPlan(advancedEdition,annualPlan);
		homePage.click("cloudCreditLinkOnCloudCreditPage");
		assertTrue("Add cloud credit page should display after clicking on Cloud Credit link", homePage.checkIfElementExistsInPage("quantityField", 20));
		
		
	}
	
	public void validateEidtionPage(String editionToSelect) throws MetadataException{
		String selectEditionXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectBasicOrAdvanceOption", editionToSelect);
		assertTrue("Select your Edition page is displayed after clicking on Edition Link" ,homePage.checkIfElementExistsInPage(selectEditionXpath, 20));
		homePage.click(selectEditionXpath);
		Util.printInfo("Click on Continue button on Select Edition Page");
		homePage.click("enabledContinueButton");
		homePage.waitForFieldVisible("editionValue", 10);
		assertTrueCatchException("Edition value is displayed as expected in 'Select your plan' page. Expected: "+editionToSelect ,homePage.getValueFromGUI("editionValue").equalsIgnoreCase(editionToSelect));
		assertTrueCatchException("Plan value is displayed as expected in 'Select your plan' page before selecting plan. Expected: null" ,homePage.getValueFromGUI("diablePlanLinkINPlanPage").equalsIgnoreCase(""));
	}
	public void validateEditionAndPlan(String editionToCheck, String planToCheck) throws MetadataException{
		
		assertTrueCatchException("Edition value is displayed as expected in 'Select your plan' page. Expected: "+editionToCheck ,homePage.getValueFromGUI("editionValue").equalsIgnoreCase(editionToCheck));
		assertTrueCatchException("Plan value is displayed as expected in 'Select your plan' page before selecting plan. Expected: "+planToCheck ,homePage.getValueFromGUI("planTitleDisplayed").equalsIgnoreCase(planToCheck));
	}
	
	
	public void selectPlan(String planToSelect){
		String selectPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectPlan", planToSelect);
		Util.printInfo("selecting plan "+planToSelect);
		homePage.click(selectPlanXpath);
	}
	
	public void SelectEdtion(String editionToSelect){
		String selectEditionXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectBasicOrAdvanceOption", editionToSelect);
		Util.printInfo("Select Edition as "+editionToSelect );
		homePage.click(selectEditionXpath);
	}
	
	public void ClickOnContinuButtonOnEditionPage(){
		Util.printInfo("Click on Continue button");
		homePage.click("enabledContinueButton");
		homePage.waitForFieldVisible("editionValue", 10);
	}
	public void ClickOnEditionLink(){
		Util.printInfo("Going to click on Edition link in Select plan page");
		homePage.click("editionLink");
		Util.sleep(3000);
	}
	public void ClickOnContinueOnPlanPage(){
		Util.printInfo("Click on Continue button on 'select plan' page");
		homePage.click("enabledContinueButtonOnSelectPlanPage");
		homePage.waitForFieldVisible("quantityField", 10);
		Util.sleep(2000);
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
