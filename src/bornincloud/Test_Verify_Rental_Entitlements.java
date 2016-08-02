
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

public class Test_Verify_Rental_Entitlements extends BornInCloudTestBase {
	
	String contractType="";
	final String statusImageNameForAutoRenew="autorenew";
	final String statusImageNameForExpires="expires";
	final String statusImageNameForExpired="expired";
	
	public Test_Verify_Rental_Entitlements() {
		//FirefoxProfile profile = getWebDriverPreferencesForFirefoxProfile();				
		super("Browser",getAppBrowser(),false,true);	
		
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_Rental_Entitl() throws Exception {

		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;


		env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("USER_ID_DEV"),
					testProperties.getConstant("PASSWORD_DEV"));
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
		}
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
				
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);

		// Select Product Link
		bic.select("productlink");
				
		
		homePage.waitForFieldVisible("productslist", 30000);
		if (homePage.checkFieldExistence("productslist")) {
			validateEditRenewal("fusion");
			validateEditRenewal("maya");
		}
		// Logout
		homePage.click("arrow");

		homePage.click("signout");
	}
	public void validateEditRenewal(String contractType) throws MetadataException{
		String productName="";
		String expectedServices="";
		String expectedContractStatus="";
		String expectedDate="";
		String expectedTerm="";
		String expectedContractNumber="";
		String expectedCardType="";
		String expectedCardNumber="";
		String expectedLink1="";
		String expectedLink2="";
		String expectedNextBillingPrice="";
		String expectedNextBillingPeriod="";
		
		this.contractType=contractType;
		if(contractType.equalsIgnoreCase("Fusion")){
			productName=testProperties.getConstant("activeFusionProductName");
			expectedServices=testProperties.getConstant("expectedFusionServices");
			expectedContractStatus=testProperties.getConstant("expectedContractStatusForFusion");
			expectedDate=testProperties.getConstant("expectedDateForFusion");
			expectedTerm=testProperties.getConstant("expectedTermForFusion");
			expectedCardType=testProperties.getConstant("expectedCardTypeForFusion");
			expectedCardNumber=testProperties.getConstant("expectedCardNumberForFusion");
			expectedContractNumber=testProperties.getConstant("expectedContractNumberForFusion");
			expectedLink1=testProperties.getConstant("expectedLink1ForFusion");
			expectedLink2=testProperties.getConstant("expectedLink2ForFusion");
			expectedNextBillingPrice=testProperties.getConstant("expectedNextBillingPriceForFussion");
			expectedNextBillingPeriod=testProperties.getConstant("expectedNextBillingPeriodForFussion");
			
		}else if(contractType.equalsIgnoreCase("Maya")){
			productName=testProperties.getConstant("activeMayaProductName");
			expectedServices=testProperties.getConstant("expectedMayaServices");
			expectedContractStatus=testProperties.getConstant("expectedContractStatusForMaya");
			expectedDate=testProperties.getConstant("expectedDateForMaya");
			expectedTerm=testProperties.getConstant("expectedTermForMaya");
			expectedCardType=testProperties.getConstant("expectedCardTypeForMaya");
			expectedCardNumber=testProperties.getConstant("expectedCardNumberForMaya");
			expectedContractNumber=testProperties.getConstant("expectedContractNumberForMaya");
			expectedLink1=testProperties.getConstant("expectedLink1ForMaya");
			expectedLink2=testProperties.getConstant("expectedLink2ForMaya");
			expectedNextBillingPrice=testProperties.getConstant("expectedNextBillingPriceForMaya");
			expectedNextBillingPeriod=testProperties.getConstant("expectedNextBillingPeriodForMaya");
			
		}
		
		String viewDetailsXpath=homePage.createFieldWithParsedFieldLocatorsTokens("viewProductDetailsButton", expectedContractNumber);
		String contractStautXpath=homePage.createFieldWithParsedFieldLocatorsTokens("validateContract", expectedContractNumber,expectedContractStatus);
		String dateXpath=homePage.createFieldWithParsedFieldLocatorsTokens("validateContract", expectedContractNumber,expectedDate);
		String termXpath=homePage.createFieldWithParsedFieldLocatorsTokens("validateContract", expectedContractNumber,expectedTerm);
		String cardDetailsXpath=homePage.createFieldWithParsedFieldLocatorsTokens("cardDetailsForRental", expectedContractNumber,expectedCardType,expectedCardNumber);
		String productNameXpath=homePage.createFieldWithParsedFieldLocatorsTokens("validateContract", expectedContractNumber,productName);
		String statusImageXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractSatusImage", expectedContractNumber,statusImageNameForAutoRenew);
		String link1Xpath=homePage.createFieldWithParsedFieldLocatorsTokens("linkForRental", expectedContractNumber,expectedLink1);
		String link2Xpath=homePage.createFieldWithParsedFieldLocatorsTokens("linkForRental", expectedContractNumber,expectedLink2);
		String nextBillingPriceXpath=homePage.createFieldWithParsedFieldLocatorsTokens("nextBillingPriceINPSPage", expectedContractNumber,expectedNextBillingPrice,expectedNextBillingPeriod);
		
		assertTrue("Expected renetal product with contract number "+expectedContractNumber+" found", homePage.checkIfElementExistsInPage(viewDetailsXpath, 20));
		homePage.click(viewDetailsXpath);
		validateServices(expectedContractNumber, expectedServices);
		assertTrueCatchException("Exepected status '"+expectedContractStatus+"' for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(contractStautXpath, 20));
		assertTrueCatchException("Exepected date '"+expectedDate+"' for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(dateXpath, 20));
		assertTrueCatchException("Exepected Term '"+expectedTerm+"' for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(termXpath, 20));
		assertTrueCatchException("Exepected credit card number '"+expectedCardNumber+"' for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(cardDetailsXpath, 20));
		assertTrueCatchException("Exepected product Name '"+productName+"' for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(productNameXpath, 20));
		assertTrueCatchException("Active Image Status should be displayed for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(statusImageXpath, 20));
		assertTrueCatchException("Expected link '"+expectedLink1+"' should be displayed for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(link1Xpath, 20));
		assertTrueCatchException("Expected link '"+expectedLink2+"' should be displayed for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(link2Xpath, 20));
		assertTrueCatchException("Expected next Billing price '"+expectedNextBillingPrice+"' should be displayed for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(nextBillingPriceXpath, 20));
		
		homePage.click(viewDetailsXpath);
	}
	
	public void validateServices(String ContractType, String expectedServicesForContract) throws MetadataException{
		String[] expectedServices=expectedServicesForContract.split(";");
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
		assertTrue("All expected services are displayed for Fusion 360 Pro",(foundServices==actualServices.length));
			
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






