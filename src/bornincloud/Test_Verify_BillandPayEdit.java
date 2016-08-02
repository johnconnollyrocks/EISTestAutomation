package bornincloud;



import java.util.List;
import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_BillandPayEdit extends BornInCloudTestBase{

	public String emailInPaymentPage;
	public String countryInPaymentPage;
	
	public Test_Verify_BillandPayEdit() throws IOException {
		super("browser");						
	}
	
	@Before
	public void setUp() throws Exception {
	//URL Modification of "language" to "lang" as per Ranjitha's Comments
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
	
	//********************Merging two test cases **************************
	
	@Test
	public void Test_VerifyIPPFrameWork() throws Exception {
	 
	 
	 
	 
	assertTrueCatchException("Buy IT button is disabled on launch ",homePage.checkIfElementExistsInPage("disableBuyItButton", 10));
	homePage.click("AnnualPlanButton");
	 
	assertTrueCatchException("Buy IT button is enabled after selcting the plan ",homePage.checkIfElementExistsInPage("enableBuyItButton", 10));
	homePage.click("enableBuyItButton");
	 
	homePage.waitForField("NameText", true, 10);
	 
	if (homePage.checkIfElementExistsInPage("NameText", 10)){
	assertTrueCatchException("Sucessfully navigated to payment information page after clicking on Buy it. ",homePage.checkIfElementExistsInPage("NameText", 10));
	}else{
	EISTestBase.fail(" payment information page is not found after clicking on Buy it button");
	 
	}
	 
	Util.sleep(5000);
	 
	//Enter valid data and place the order
	 
	 
	 
	BillInfoPopulate();
	 
	 
	 
	//***********BillEdit in Order summary Page******************@@//div[@id='order_summary']//div[contains(text(),Edit)]
	 
	Util.sleep(15000);
	if(homePage.checkIfElementExistsInPage("editLnkBillingInfo",30) ==true){
	 
	 
	Util.printInfo("Sucessfully Clicked on submit order button in Order summary page");
	Util.printInfo("Validate whether billing information is same as expected in ReEnter or Change Payment info page");
	validateBillingInformation();
	Util.sleep(5000);
	homePage.click("editLnkBillingInfo");
	Util.sleep(5000);
	BillInfoVerify();
	 
	}
	 
	else{
	Util.sleep(15000);
	if(homePage.checkIfElementExistsInPage("editLnkBillingInfo",30) ==false){
	EISTestBase.fail("Fatal error: Submit Order Button is not displayed in Order Summary Page");
	}
	 
	else{
	 
	Util.printInfo("Sucessfully Clicked on submit order button in Order summary page");
	Util.printInfo("Validate whether billing information is same as expected in ReEnter or Change Payment info page");
	validateBillingInformation();
	Util.sleep(5000);
	homePage.click("editLnkBillingInfo");
	Util.sleep(5000);
	BillInfoVerify();
	 
	 
	}
	 
	}
	 
	 
	 
	}
	 
	@Test
	public void Test_VerifyIPPFrameWork2() throws Exception {
	 
	assertTrueCatchException("Buy IT button is disabled on launch ",homePage.checkIfElementExistsInPage("disableBuyItButton", 10));
	homePage.click("AnnualPlanButton");
	 
	assertTrueCatchException("Buy IT button is enabled after selcting the plan ",homePage.checkIfElementExistsInPage("enableBuyItButton", 10));
	homePage.click("enableBuyItButton");
	 
	homePage.waitForField("NameText", true, 10);
	 
	if (homePage.checkIfElementExistsInPage("NameText", 10)){
	assertTrueCatchException("Sucessfully navigated to payment information page after clicking on Buy it. ",homePage.checkIfElementExistsInPage("NameText", 10));
	}else{
	EISTestBase.fail(" payment information page is not found after clicking on Buy it button");
	 
	}
	 
	Util.sleep(5000);
	 
	//Enter valid data and place the order
	 
	 
	BillInfoPopulate();
	 
	//****************PayEdit in Order summary page***************
	 
	 
	 
	Util.sleep(5000);
	 
	 
	if(homePage.checkIfElementExistsInPage("editLnkPymntInfo",30) ==true){
	 
	assertTrueCatchException("VisaNumber validation in Order Summary Page",homePage.isFieldPresent("VisaNum"));
	 
	homePage.click("editLnkPymntInfo");
	Util.sleep(5000);
	Util.printInfo("Sucessfully Clicked on PayEdit");
	BillInfoVerify();
	 
	}
	 
	else{
	Util.sleep(15000);
	if(homePage.checkIfElementExistsInPage("editLnkPymntInfo",30) ==false){
	EISTestBase.fail("Fatal error: Submit Order Button is not displayed in Order Summary Page");
	}
	 
	else{
	assertTrueCatchException("VisaNumber validation in Order Summary Page",homePage.isFieldPresent("VisaNum"));
	homePage.click("editLnkPymntInfo");
	Util.printInfo("Sucessfully Clicked on PayEdit");
	Util.sleep(5000);
	BillInfoVerify();
	 
	 
	}
	 
	}
	 
	 
	 
	 
	 
	 
	}
	 
	public void validateBillingInformation(){
	 
	try {
	List<WebElement>address=homePage.getMultipleWebElementsfromField("orderSummaryBillInfo");
	 
	 
	String name=address.get(0).getText();
	System.out.println(name);
	String billingAddress=address.get(1).getText().trim();
	System.out.println(billingAddress);
	String cityNState=address.get(2).getText().trim();
	System.out.println(cityNState);
	String zipCode=address.get(3).getText().trim();
	System.out.println(zipCode);
	String country=address.get(4).getText().trim();
	System.out.println(country);
	String email=address.get(5).getText().trim();
	System.out.println(email);
	 
	String[] temp=cityNState.split(",");
	assertTrueCatchException("Name validation passed both the page",assertEquals(name.trim(), testProperties.getConstant("NameText")));
	System.out.println(testProperties.getConstant("NameText")+"gui value"+name);
	assertTrueCatchException("Billing address the validation passed both the page",assertEquals(billingAddress, testProperties.getConstant("BillingAddressText")));
	System.out.println(testProperties.getConstant("BillingAddressText")+"gui value"+billingAddress);
	assertTrueCatchException("Billing address the validation passed both the page",assertEquals(temp[0].trim(), testProperties.getConstant("cityText")));
	System.out.println(testProperties.getConstant("cityText")+"gui value"+temp[0]);
	assertTrueCatchException("Billing address the validation passed both the page",assertEquals(temp[1].trim(), testProperties.getConstant("SelectProvince")));
	System.out.println(testProperties.getConstant("SelectProvince")+"gui value"+temp[1]);
	 
	System.out.println(emailInPaymentPage+" gui value "+email);
	assertTrueCatchException("Billing address the validation passed both the page",assertEquals(emailInPaymentPage,email));
	System.out.println(countryInPaymentPage+" gui value "+country);
	assertTrueCatchException("Billing address the validation passed both the page",assertEquals(countryInPaymentPage,country));

	 
	} catch (MetadataException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	 
	}
	 
	 
	 
	public void assertTrueCatchException(String message , boolean expected){
	try{
	assertTrue(message,expected);
	}catch(Exception e){
	Util.printInfo(message+"--FAILED");
	}
	}
	 
	 
	 
	public void BillInfoPopulate(){
	 
	homePage.populateField("NameText","");
	homePage.populateField("NameText",testProperties.getConstant("NameText"));
	 
	homePage.populateField("CardNumberText","");
	homePage.populateField("CardNumberText",testProperties.getConstant("CardNumberText"));
	 
	homePage.populateField("SelectExpMonth","");
	homePage.populateField("SelectExpMonth",testProperties.getConstant("SelectExpMonth"));
	 
	homePage.populateField("SelectExpYear"," ");
	homePage.populateField("SelectExpYear",testProperties.getConstant("SelectExpYear"));
	 
	homePage.populateField("SecurityCodeText","");
	homePage.populateField("SecurityCodeText",testProperties.getConstant("SecurityCodeText"));
	 
	homePage.populateField("BillingAddressText","");
	homePage.populateField("BillingAddressText",testProperties.getConstant("BillingAddressText"));
	 
	homePage.populateField("cityText","");
	homePage.populateField("cityText",testProperties.getConstant("cityText"));
	 
	homePage.populateField("SelectProvince"," ");
	homePage.populateField("SelectProvince",testProperties.getConstant("SelectProvince"));
	 
	homePage.populateField("ZipCodeText","");
	homePage.populateField("ZipCodeText",testProperties.getConstant("ZipCodeText"));
	 
	emailInPaymentPage=homePage.getValueFromGUI("emailInPayMentPage");
	countryInPaymentPage=homePage.getValueFromGUI("countryInPayMentPage");
	 
	 
	System.out.println(emailInPaymentPage);
	System.out.println(countryInPaymentPage);
	 
	homePage.check("autoRenewal");
	Util.sleep(5000);
	 
	homePage.click("ContinueEnable"); 
	 
	}
	 
	public void BillInfoVerify() throws MetadataException{
	 
	//Instruction to insert data in the fields ??
	 
	//homePage.getAttribute(fieldName, attributeName)
	 
	String customerName = homePage.getAttribute("NameText", "value");
	 
	//Instruction to Evaluate to values ??
	 
	compareStrings("John Mars", customerName);
	//***********************************************************************
	 
	String cardNumber = homePage.getAttribute("CardNumberText", "value");
	 
	compareStrings("4444333322221111", cardNumber);
	//***********************************************************************
	String expiDateMonth = homePage.getAttribute("SelectExpMonth", "value");
	 
	compareStrings("04", expiDateMonth);
	//***********************************************************************
	String expYear =  homePage.getAttribute("SelectExpYear", "value");
	 
	compareStrings("2015", expYear);
	//***********************************************************************
	String securityCode = homePage.getAttribute("SecurityCodeText", "value");
	 
	compareStrings("123", securityCode);
	//***********************************************************************
	String billingAdress = homePage.getAttribute("BillingAddressText", "value");
	 
	compareStrings("Invalid #43,Cross,7th 1sr-phase.", billingAdress);
	//***********************************************************************
	String city = homePage.getAttribute("cityText", "value");
	 
	compareStrings("QueBeC", city);
	//***********************************************************************
	String state = homePage.getAttribute("SelectProvince", "value");
	 
	compareStrings("California", state);
	//***********************************************************************
	String zip = homePage.getAttribute("ZipCodeText", "value");
	 
	compareStrings("94444", zip);
	//***********************************************************************
	 
	homePage.check("autoRenewalCheckBox");
	 
	Util.sleep(5000);
	 
	 
	 
	 

	//***********Validate Continue button on Billing Information Page********** 
	 
	Util.printInfo("Validation whether continue button is Enabled");
	 
	if(homePage.checkIfElementExistsInPage("ContinueEnable",30) == true){
	 
	homePage.click("ContinueEnable");  
	Util.sleep(10000);
	}
	else{
	Util.sleep(15000);
	if(homePage.checkIfElementExistsInPage("ContinueEnable",30) == false){
	   
	    EISTestBase.fail("Fatal Error: Continue Button is not Enabled in the Billing Page");
	 
	}
	else{
	System.out.println("Checking Else Block when condition is True");
	homePage.click("ContinueEnable");  
	Util.sleep(15000);
	}
	 
	}
	 
	 
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