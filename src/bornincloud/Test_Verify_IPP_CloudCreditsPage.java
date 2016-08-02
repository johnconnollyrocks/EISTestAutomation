package bornincloud;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_IPP_CloudCreditsPage extends BornInCloudTestBase{

	String UserID="";
 	String offerings="";
 	String country="";
 	String expectedHeader="";
 	String plan="";
 	String editionValue="";	
 	String quantity="";
	public Test_Verify_IPP_CloudCreditsPage() throws IOException {
		super("browser");						
	}
	
	@Before
	public void setUp() throws Exception {
		if(getEnvironment().equalsIgnoreCase("dev")){
			UserID=testProperties.getConstant("userId_DEV");
			offerings=testProperties.getConstant("offeringId_DEV");
			country=testProperties.getConstant("country_DEV");
			plan=testProperties.getConstant("plan_DEV");
			editionValue=testProperties.getConstant("editionValue_DEV");
			expectedHeader=testProperties.getConstant("expectedHeader_DEV");
			quantity=testProperties.getConstant("Quantity_DEV");
			
		}else{
			UserID=testProperties.getConstant("userId_STG");
			offerings=testProperties.getConstant("offeringId_STG");
			country=testProperties.getConstant("country_STG");
			plan=testProperties.getConstant("plan_STG");
			editionValue=testProperties.getConstant("editionValue_STG");
			expectedHeader=testProperties.getConstant("expectedHeader_STG");
			quantity=testProperties.getConstant("Quantity_STG");
		}
		String url=getBaseURL() + "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings ;
		launchIPP(url);		
		Util.sleep(4000);
	}
	
	
	@Test
	public void Test_VerifyIPPFrameWork() throws Exception {
		
		String selectEditionXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectBasicOrAdvanceOption", editionValue);
		Util.printInfo("Select Edition as "+editionValue );
		homePage.click(selectEditionXpath);
		Util.printInfo("Click on Continue button");
		homePage.click("enabledContinueButton");
		homePage.waitForFieldVisible("editionValue", 10);
		String selectPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectPlan", plan);
		Util.printInfo("selecting plan "+plan);
		assertTrueCatchException("continue button is enabled after selecting the plan",homePage.checkIfElementExistsInPage("enabledContinueButtonOnSelectPlanPage", 10));
		homePage.click(selectPlanXpath);
		assertTrueCatchException("continue button is enabled after selecting the plan",homePage.checkIfElementExistsInPage("enabledContinueButtonOnSelectPlanPage", 10));
		Util.printInfo("Click on Continue button on 'select plan' page");
		homePage.click("enabledContinueButtonOnSelectPlanPage");
		homePage.waitForFieldVisible("quantityField", 10);
		assertTrueCatchException("Edition value is displayed as expected in 'Add Cloud Credits' page. Expected: "+editionValue ,homePage.getValueFromGUI("editionValue").equalsIgnoreCase(editionValue));
		Util.sleep(2000);
		assertTrueCatchException("Plan value is displayed as expected in 'Add Cloud Credits' page after. Expeted: "+plan ,homePage.getValueFromGUI("planTitleDisplayed").equalsIgnoreCase(plan));
//		String currencyPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("planCurrency", plan);
		String expectedCurrencyValue=homePage.getValueFromGUI("planPriceDisplayed");
		assertTrueCatchException("Plan Currency value is displayed as expected in 'Add Cloud Credits'. Expected: "+expectedCurrencyValue ,homePage.getValueFromGUI("planPriceDisplayed").contains(expectedCurrencyValue));
		String expectedDefaultCloudCreditValue=homePage.getValueFromGUI("defaultCloudCreditsDisplayed");
		assertTrueCatchException("Cloud Credits value is displayed as expected in 'Add Cloud Credits' before entering quantity. Expected: "+expectedDefaultCloudCreditValue ,homePage.checkIfElementExistsInPage("defaultCloudCreditsDisplayed", 10));
		assertTrueCatchException("Quantity field is empty before use enters something" ,homePage.getValueFromGUI("quantityField").equalsIgnoreCase(""));
		//assertTrueCatchException("Cloud Credits currency Value is displayed as expected before user enters quantity" ,homePage.checkIfElementExistsInPage("defaultPriceForCloudCredits", 10));
		homePage.populateField("quantityField",quantity);
		Util.sleep(2000);
		String actualCloudCreditPrice=homePage.getValueFromGUI("quantityField");
		System.out.println(actualCloudCreditPrice);
		
		String expectedValueAsString=String.valueOf(quantity);
		//assertTrueCatchException("Cloud Credits currency Value is displayed as expected after user enters quantity. Expected: "+ expectedQuantity,homePage.getValueFromGUI("quantityField").contains(expectedValueAsString));
		assertTrueCatchException("Cloud Credits currency Value is displayed as expected after user enters quantity. Expected: "+ expectedValueAsString,actualCloudCreditPrice.contains(expectedValueAsString));
	}
	
	public void validateHover(){
		Util.printInfo("Need to find way to validate Hover. Work in Progress");
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
