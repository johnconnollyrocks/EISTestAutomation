package bornincloud;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_IPP_Payment_Field_Validations extends BornInCloudTestBase{

	String UserID="";
 	String offerings="";
 	String country="";
 	String expectedAppHeader="";
 	String expectedPageHeader="";
 	String plan="";
 	String editionValue="";	
 	String baseURL="";
 	String cardValue="";
	public Test_Verify_IPP_Payment_Field_Validations() throws IOException {
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
			cardValue=testProperties.getConstant("cardValue_DEV");
			expectedAppHeader=testProperties.getConstant("expectedAppHeader_DEV");
			expectedPageHeader=testProperties.getConstant("expectedPageHeader_DEV");	
			baseURL=BornInCloudConstants.BASE_URL_DEV;
		}else{
			UserID=testProperties.getConstant("userId_STG");
			offerings=testProperties.getConstant("offeringId_STG");
			country=testProperties.getConstant("country_STG");
			plan=testProperties.getConstant("plan_STG");
			editionValue=testProperties.getConstant("editionValue_STG");
			cardValue=testProperties.getConstant("cardValue_STG");
			expectedAppHeader=testProperties.getConstant("expectedAppHeader_STG");
			expectedPageHeader=testProperties.getConstant("expectedPageHeader_STG");
			baseURL=BornInCloudConstants.BASE_URL_STG;
		}
		String url=baseURL + "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings ;
		launchIPP(url);		
		Util.sleep(4000);
	}
	
	
	@Test
	public void Test_VerifyIPPFrameWork() throws Exception {
		
		Util.printInfo("Navigating to the Payment Info page");
		homePage.click("selectBasicOption");
		homePage.click("enabledContinueButton");
		String plan =homePage.createFieldWithParsedFieldLocatorsTokens("selectPlan", "Annual");
		homePage.waitForFieldVisible(plan,20);
		homePage.click(plan);
		homePage.click("enabledContinueButtonOnSelectPlanPage");
		homePage.waitForFieldVisible("quantityField",20);
		homePage.populateField("quantityField", "1");
		homePage.click("enabledContinueButtonOnSelectPlanPage");
		Util.printInfo("Validating the Payment Info page");
		
		Util.printInfo("Validating the Product Type header");
		String actualAppHeader=homePage.getValueFromGUI("headerINIPPHomePage");
		System.out.println(actualAppHeader);
		assertTrueCatchException("Expected Product Type Header is displayed after launching the IPP. Expected: "+expectedAppHeader+" Actual: "+actualAppHeader,actualAppHeader.contains(expectedAppHeader));
		
//		Util.printInfo("Validating the Page Type header");
//		String actualPageHeader=homePage.getValueFromGUI("headerINIPPHomePage");
//		System.out.println(actualPageHeader);
//		assertTrueCatchException("Expected Page Type Header is displayed after launching the IPP. Expected: "+expectedPageHeader+" Actual: "+actualAppHeader,actualPageHeader.contains(expectedPageHeader));
		
		Util.printInfo("Validating the First Name field");		
		assertTrueCatchException("Validating the First Name field Label",homePage.checkIfElementExistsInPage("firstnameLabel", 2));
		assertTrueCatchException("Validating the First Name field Textbox",homePage.checkIfElementExistsInPage("firstnameTextbox", 2));
		
		Util.printInfo("Validating the Last Name field");		
		assertTrueCatchException("Validating the Last Name field Label",homePage.checkIfElementExistsInPage("lastnameLabel", 2));
		assertTrueCatchException("Validating the Last Name field Textbox",homePage.checkIfElementExistsInPage("lastnameTextbox", 2));
		
		Util.printInfo("Validating the Card Number field");	
		assertTrueCatchException("Validating the CardNumber field Label",homePage.checkIfElementExistsInPage("cardnumberLabel", 2));
		assertTrueCatchException("Validating the CardNumber field textbox",homePage.checkIfElementExistsInPage("cardnumberTextbox", 2));
		
		Util.printInfo("Validating the Card Type Image before entering the card number");
		assertTrueCatchException("Validating the Card Type Image before entering the card number",homePage.checkIfElementExistsInPage("creditcardnotSelectedImg", 2));
		
		Util.printInfo("Validating the Card Type Image after entering the card number");
		homePage.populateField("cardnumberTextbox",cardValue);
		homePage.populateField("lastnameTextbox","");
		Util.sleep(2000);		
		assertTrueCatchException("Validating the Card Type Image after entering the card number",homePage.checkIfElementExistsInPage("creditcardSelectedImg", 2));
				
		Util.printInfo("Validating the Exp Month Dropdown");
		assertTrueCatchException("Validating the existance Exp Month Dropdown",homePage.checkIfElementExistsInPage("expMonth", 2));
		
		List<String> ActualMonth = homePage.getPickListContents("expMonth");
		List<String> ExpectedMonth = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		
		for (int i=1;i<=12;i++){
			
			assertTrueCatchException("Validating the month values in Expired Month dropdown. Expected: "+ExpectedMonth.get(i-1)+" Actual: "+ActualMonth.get(i),ActualMonth.get(i).contains(ExpectedMonth.get(i-1)));
			
		}	
		
		Util.printInfo("Validating the existance of Exp Year Dropdown");
		assertTrueCatchException("Validating the existance Exp Month Dropdown",homePage.checkIfElementExistsInPage("expYear", 2));
		
		Util.printInfo("Validating the contents of Exp Year Dropdown");
		String startYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		System.out.println(startYear);
		String actualStartYear=homePage.getPickListContents("expYear").get(1); 
		System.out.println(actualStartYear);
		assertTrueCatchException("Validating the Start Year displayed in Expired Month dropdown. Expected: "+startYear+" Actual: "+actualStartYear,startYear.contains(actualStartYear));		
		
		String endYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)+14);
		System.out.println(endYear);
		String actualEndYear=homePage.getPickListContents("expYear").get(15); 
		System.out.println(actualEndYear);
		assertTrueCatchException("Validating the End Year displayed in Expired Month dropdown. Expected: "+endYear+" Actual: "+actualEndYear,endYear.contains(actualEndYear));
		
		Util.printInfo("Validating the Security Code field");	
		assertTrueCatchException("Validating the Security Code field level",homePage.checkIfElementExistsInPage("secCodeLabel", 2));
		assertTrueCatchException("Validating the Security Code field tooltip",homePage.checkIfElementExistsInPage("secCodeTooptip", 2));		
		WebElement secCodeToolTip=homePage.getMultipleWebElementsfromField("secCodeTooptip").get(0);
		mouseHover(secCodeToolTip);
		Util.sleep(3000);
		assertTrueCatchException("Validating the Security Code field tooltip Text",homePage.checkIfElementExistsInPage("secCodeTooltipText", 2));
		assertTrueCatchException("Validating the Security Code field tooltip Textbox",homePage.checkIfElementExistsInPage("secCodeTooltipTextBox", 2));
		
		Util.printInfo("Validating the Billing Address field");		
		assertTrueCatchException("Validating the Billing Address field Label",homePage.checkIfElementExistsInPage("billingAddressLabel", 2));
		assertTrueCatchException("Validating the Billing Address field Textbox",homePage.checkIfElementExistsInPage("billingAddressTextbox", 2));
		
		Util.printInfo("Validating the City field");		
		assertTrueCatchException("Validating the City field Label",homePage.checkIfElementExistsInPage("cityLabel", 2));
		assertTrueCatchException("Validating the City field Textbox",homePage.checkIfElementExistsInPage("cityTextbox", 2));
		
		Util.printInfo("Validating the existance of State Dropdown Label field");
		assertTrueCatchException("Validating the existance of State Dropdown field",homePage.checkIfElementExistsInPage("stateLabel", 2));
		
		Util.printInfo("Validating the existance of State Dropdown field");
		assertTrueCatchException("Validating the existance of State Dropdown field",homePage.checkIfElementExistsInPage("stateDropdown", 2));
		
		Util.printInfo("Validating the contents of State Dropdown field");
		
//		List<String> ActualState = homePage.getPickListContents("stateDropdown");
//		List<String> ExpectedState = Arrays.asList("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina","North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming");
//		
//		for (int i=1;i<=ActualState.size();i++){
//			
//			assertTrueCatchException("Validating the month values in Expired Month dropdown. Expected:"+ExpectedState.get(i-1)+" Actual: "+ActualState.get(i),ActualState.get(i).contains(ExpectedState.get(i-1)));
//			
//		}
		
		Util.printInfo("Validating the Zip/Postal field level");
		assertTrueCatchException("Validating the Zip/Postal field level",homePage.checkIfElementExistsInPage("ziplabel", 2));
		
		Util.printInfo("Validating the Zip/Postal field textbox");
		assertTrueCatchException("Validating the Zip/Postal field textbox",homePage.checkIfElementExistsInPage("zipTextbox", 2));
		
		Util.printInfo("Validating the Country Dropdown field level");
		assertTrueCatchException("Validating the Country Dropdown field level",homePage.checkIfElementExistsInPage("countryLabel", 2));
		
		Util.printInfo("Validating the existance of Country Dropdown field");
		assertTrueCatchException("Validating the existance of Country Dropdown field",homePage.checkIfElementExistsInPage("countryDropdown", 2));
		
		Util.printInfo("Validating the contents of Country Dropdown field");
		assertTrueCatchException("Validating the contents of Country Dropdown field",homePage.getPickListContents("countryDropdown").get(0).contains("United States"));
		
		Util.printInfo("Validating the Email field");
		assertTrueCatchException("Validating the Email field Label",homePage.checkIfElementExistsInPage("emailLabel", 2));
		assertTrueCatchException("Validating the Email Tooltip",homePage.checkIfElementExistsInPage("emailTooltip", 2));
		WebElement emailToolTip=homePage.getMultipleWebElementsfromField("emailTooltip").get(0);
		mouseHover(emailToolTip);
		Util.sleep(3000);
		assertTrueCatchException("Validating the Email Tooltip Text",homePage.checkIfElementExistsInPage("emailTooltipText", 2));
		assertTrueCatchException("Validating the Email Hardcoded value",homePage.checkIfElementExistsInPage("emailValue", 2));
				
		Util.printInfo("Validating the Footer Link");
		assertTrueCatchException("Validating the Footer Link",homePage.checkIfElementExistsInPage("footerLink", 2));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		


		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*String actualAppHeader=homePage.getValueFromGUI("headerINIPPHomePage");
		System.out.println(actualAppHeader);
		assertTrueCatchException("Expected Header is displayed after launching the IPP.",actualHeader.equals(expectedHeader));
		assertTrueCatchException("Continue button is disabled after launching the IPP",homePage.checkIfElementExistsInPage("disabledContinueButton", 10));
		Util.printInfo("Clicking on 'Register for Non-Commercial Use' link.");
		homePage.click("registerNonUserLink");
		Util.printInfo("Validating whether submit button is disabled after navigating to 'Register For Non-Commercial' Page");
		assertTrueCatchException("Submit button is disabled after launching the IPP",homePage.checkIfElementExistsInPage("disabledSubmitButton", 10));
		if(!homePage.isChecked("iAcceptCheckBox")){
			assertTrueCatchException("Check box is unchecked after navigating to 'Register For Non-Commercial' Page",true);
		}else{
			assertTrueCatchException("Check box is unchecked after navigating to 'Register For Non-Commercial' Page",false);
		}
		String actualUserOptionSelected=homePage.getValueFromGUI("selectedUserByDefault");
		System.out.println(actualUserOptionSelected);
		if(actualUserOptionSelected.contains("Student")){
			assertTrueCatchException("Student is selected as default after navigating to 'Register For Non-Commercial' Page",true);
		}else{
			assertTrueCatchException("Student is selected as default after navigating to 'Register For Non-Commercial' Page",false);
		}
		Util.printInfo("Select accept terms and conditions. i.e click on I accept check box and validate whether continue button is enabled");
		homePage.check("iAcceptCheckBox");
		assertTrueCatchException("Continue button should be enabled after use accepts terms and conditions",homePage.checkIfElementExistsInPage("enabledSubmitButton", 10));
		Util.printInfo("Clicking on 'Register For Non-Commercial' link to go back to home page");
		homePage.click("registerNonUserLink");
		validateHover();
		String selectEditionXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectBasicOrAdvanceOption", editionValue);
		Util.printInfo("Select Edition as "+editionValue );
		homePage.click(selectEditionXpath);
		Util.printInfo("Validating whether continue button is enabled after selecting Edition: "+editionValue);
		assertTrueCatchException("Continue button should be enabled after use accepts terms and conditions",homePage.checkIfElementExistsInPage("enabledContinueButton", 10));
		Util.printInfo("Click on Continue button");
		homePage.click("enabledContinueButton");
		homePage.waitForFieldVisible("editionValue", 10);
		assertTrueCatchException("Edition value is displayed as expected in 'Select your plan' page. Expected: "+editionValue ,homePage.getValueFromGUI("editionValue").equalsIgnoreCase(editionValue));
		assertTrueCatchException("Plan value is displayed as expected in 'Select your plan' page before selecting plan. Expected: null" ,homePage.getValueFromGUI("planTitleDisplayed").equalsIgnoreCase(""));
		assertTrueCatchException("continue button is disabled before selecting the plan",homePage.checkIfElementExistsInPage("disabledContinueButtonOnSelectPlanPage", 10));
		String selectPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectPlan", plan);
		Util.printInfo("selecting plan "+plan);
		homePage.click(selectPlanXpath);
		assertTrueCatchException("Plan value is displayed as expected in 'Select your plan' page after selecting plan. Expected: "+plan ,homePage.getValueFromGUI("planTitleDisplayed").equalsIgnoreCase(plan));
		String currencyPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("planCurrency", plan);
		String expectedCurrencyValue=homePage.getValueFromGUI(currencyPlanXpath);
		assertTrueCatchException("Plan Currency value is displayed as expected in 'Select your plan' page after selecting plan. Expected: "+expectedCurrencyValue ,homePage.getValueFromGUI("planPriceDisplayed").contains(expectedCurrencyValue));
		assertTrueCatchException("continue button is enabled after selecting the plan",homePage.checkIfElementExistsInPage("enabledContinueButtonOnSelectPlanPage", 10));*/
		
	
	}
	
		






	
	//********************************************************************
	
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
