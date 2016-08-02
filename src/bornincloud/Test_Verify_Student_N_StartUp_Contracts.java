package bornincloud;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_Student_N_StartUp_Contracts extends BornInCloudTestBase {
	
	String contractType="";
	String ViewsubscriptionText="";
	String editRenewalText="";
	String expectedServicesForStudent="";
	String expectedServicesForStartup="";
	String expectedContractStatusForStudent="";
	String expectedContractStatusForStartup="";
	String expectedStatus="";
	
	public Test_Verify_Student_N_StartUp_Contracts() {
		//FirefoxProfile profile = getWebDriverPreferencesForFirefoxProfile();				
		super("Browser",getAppBrowser(),false,true);	
		
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_Student_N_StartUp() throws Exception {

		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;


		env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			ViewsubscriptionText=testProperties.getConstant("ViewsubscriptionText_DEV");
			editRenewalText=testProperties.getConstant("editRenewalText_DEV");
			expectedServicesForStudent=testProperties.getConstant("expectedServicesForStudent_DEV");
			expectedServicesForStartup=testProperties.getConstant("expectedServicesForStartup_DEV");
			expectedContractStatusForStudent=testProperties.getConstant("expectedContractStatusForStudent_DEV");
			expectedContractStatusForStartup=testProperties.getConstant("expectedContractStatusForStartup_DEV");
			
			bic.login(testProperties.getConstant("USER_ID_DEV"),testProperties.getConstant("PASSWORD_DEV"));
		} else if (env.equalsIgnoreCase("STG")) {
			
			ViewsubscriptionText=testProperties.getConstant("ViewsubscriptionText_DEV");
			editRenewalText=testProperties.getConstant("editRenewalText_STG");
			expectedServicesForStudent=testProperties.getConstant("expectedServicesForStudent_STG");
			expectedServicesForStartup=testProperties.getConstant("expectedServicesForStartup_STG");
			expectedContractStatusForStudent=testProperties.getConstant("expectedContractStatusForStudent_STG");
			expectedContractStatusForStartup=testProperties.getConstant("expectedContractStatusForStartup_STG");
			bic.login(testProperties.getConstant("USER_ID_STG"),testProperties.getConstant("PASSWORD_STG"));
		}
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
				
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);

		// Select Product Link
		bic.select("productlink");
				
		
		homePage.waitForFieldVisible("productslist", 30000);
		if (homePage.checkFieldExistence("productslist")) {
			validateEditRenewal("Student");
			validateEditRenewal("Startup");
		}
		// Logout
		homePage.click("arrow");

		homePage.click("signout");
	}
	public void validateEditRenewal(String contractType) throws MetadataException{
		this.contractType=contractType;
		String studentContractXpath=homePage.createFieldWithParsedFieldLocatorsTokens("viewProductDetailsButton", contractType);
		List<WebElement> viewDetailButtonXpath=homePage.getMultipleWebElementsfromField(studentContractXpath);
		for(int i=0;i<viewDetailButtonXpath.size();i++){
			viewDetailButtonXpath.get(i).click();
			String editRenewalForstudentXpath=homePage.createFieldWithParsedFieldLocatorsTokens("editRenewalLinkForNonCommerial", contractType,editRenewalText);
			assertFalse(contractType+" contract should not show Edit renewal link",homePage.checkIfElementExistsInPage(editRenewalForstudentXpath, 10));
			String viewSubscriptionStudentXpath=homePage.createFieldWithParsedFieldLocatorsTokens("editRenewalLinkForNonCommerial",contractType,ViewsubscriptionText);
			assertFalse(contractType+" contract should not show View subscription and order history link",homePage.checkIfElementExistsInPage(viewSubscriptionStudentXpath, 10));
			String dateFieldXpath=homePage.createFieldWithParsedFieldLocatorsTokens("dateField",contractType);
			String [] date=homePage.getValueFromGUI(dateFieldXpath).split(" ");
			String actualDate=getMonthNumber(date[0])+"/"+date[1].replace(",", "/")+date[2];
			System.out.println("Actual Date: "+actualDate);
			assertTrueCatchException(contractType+"Date should display for "+contractType,validateDateFormat(actualDate));
			String contractStatusXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractStatus",contractType);
			expectedStatus=testProperties.getConstant("expectedContractStatusFor"+contractType+"_"+getEnvironment().toUpperCase());
			assertTrueCatchException("Expected status '"+expectedStatus+"' is displayed for "+contractType,homePage.getValueFromGUI(contractStatusXpath).equalsIgnoreCase(expectedStatus));
			validaetServices(contractType);
			viewDetailButtonXpath.get(i).click();
			break;
		}
	}
	public String getMonthNumber(String monthName){
		String returnMonthNumber="";
		switch(monthName.toLowerCase()){
			case "jan":{
				returnMonthNumber="01";
			}
			case "feb":{
				returnMonthNumber="02";
			}
			case "mar":{
				returnMonthNumber="03";
			}
			case "apr":{
				returnMonthNumber="04";
			}
			case "may":{
				returnMonthNumber="05";
			}
			case "jun":{
				returnMonthNumber="06";
			}
			case "jul":{
				returnMonthNumber="07";
			}
			case "aug":{
				returnMonthNumber="08";
			}
			case "sep":{
				returnMonthNumber="09";
			}
			case "oct":{
				returnMonthNumber="10";
			}
			case "nov":{
				returnMonthNumber="11";
			}
			case "dec":{
				returnMonthNumber="12";
			}
		}
		return returnMonthNumber;
	}
	public void validaetServices(String ContractType) throws MetadataException{
		String[] expectedServices=testProperties.getConstant("expectedServicesFor"+ContractType+"_"+getEnvironment().toUpperCase()).split(";");
		String servicesForContractXpath=homePage.createFieldWithParsedFieldLocatorsTokens("servicesByUserType", ContractType);
		String[] actualServices=homePage.getMultipleTextValuesfromField(servicesForContractXpath);
		if(expectedServices.length==actualServices.length){
			assertTrue("Expected number of services should match with actual number of services", true);
		}
		int foundServices=0;
		for(int i=0;i<actualServices.length;i++){
			for(int j=0;j<expectedServices.length;j++){
				if(actualServices[i].contains(expectedServices[j])){
					assertTrueCatchException("Expected services found. Expected: "+expectedServices[j],true);
					foundServices=foundServices+1;
					break;
				}
			}
		}
		if (foundServices==actualServices.length){
			assertTrue("All expected services are displayed for Fusion 360 Pro",true);
		}
	}
	public boolean validateDateFormat(String dateToValidate){
		if(dateToValidate == null){
			return false;
		}
		SimpleDateFormat sampleDateFormat = new SimpleDateFormat("mm/dd/yyyy");
		sampleDateFormat.setLenient(false);
		try {
			//if not valid, it will throw ParseException
			Date date = sampleDateFormat.parse(dateToValidate);
			System.out.println(date);
 
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
 
		return true;
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
