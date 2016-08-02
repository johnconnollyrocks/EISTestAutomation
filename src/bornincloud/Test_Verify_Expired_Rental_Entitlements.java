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

public class Test_Verify_Expired_Rental_Entitlements extends BornInCloudTestBase {
	
	String contractType="";
	final String statusImageNameForAutoRenew="autorenew";
	final String statusImageNameForExpires="expires";
	final String statusImageNameForExpired="expired";
	
	public Test_Verify_Expired_Rental_Entitlements() {
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
//			validateEditRenewal("fusion");
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
		String expectedContractNumber="";
		String expectedLink1="";
		String editRenewalLink="";
		this.contractType=contractType;
		if(contractType.equalsIgnoreCase("Fusion")){
			productName=testProperties.getConstant("activeFusionProductName");
			expectedContractStatus=testProperties.getConstant("expectedContractStatusForFusion");
			expectedDate=testProperties.getConstant("expectedDateForFusion");
			expectedContractNumber=testProperties.getConstant("expectedContractNumberForFusion");
			expectedLink1=testProperties.getConstant("expectedLink1ForFusion");
			editRenewalLink=testProperties.getConstant("notExpectedLink1ForFusion");
		}else if(contractType.equalsIgnoreCase("Maya")){
			productName=testProperties.getConstant("activeMayaProductName");
			expectedContractStatus=testProperties.getConstant("expectedContractStatusForMaya");
			expectedDate=testProperties.getConstant("expectedDateForMaya");
			expectedContractNumber=testProperties.getConstant("expectedContractNumberForMaya");
			expectedLink1=testProperties.getConstant("expectedLink1ForMaya");
			editRenewalLink=testProperties.getConstant("notExpectedLink1ForMaya");
			
		}
		
		String viewDetailsXpath=homePage.createFieldWithParsedFieldLocatorsTokens("viewProductDetailsButton", expectedContractNumber);
		String contractStautXpath=homePage.createFieldWithParsedFieldLocatorsTokens("validateContract", expectedContractNumber,expectedContractStatus);
		String dateXpath=homePage.createFieldWithParsedFieldLocatorsTokens("validateContract", expectedContractNumber,expectedDate);
		String productNameXpath=homePage.createFieldWithParsedFieldLocatorsTokens("validateContract", expectedContractNumber,productName);
		String statusImageXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractSatusImage", expectedContractNumber,statusImageNameForExpired);
		String link1Xpath=homePage.createFieldWithParsedFieldLocatorsTokens("linkForRental", expectedContractNumber,expectedLink1);
		String editRenewalLinkXpath=homePage.createFieldWithParsedFieldLocatorsTokens("linkForRental", expectedContractNumber,editRenewalLink);
		
		assertTrue("Expected renetal product with expired contract number "+expectedContractNumber+" found", homePage.checkIfElementExistsInPage(viewDetailsXpath, 20));
		homePage.click(viewDetailsXpath);
		validateServices(expectedContractNumber);
		assertTrueCatchException("Exepected status '"+expectedContractStatus+"' for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(contractStautXpath, 20));
		assertTrueCatchException("Exepected date '"+expectedDate+"' for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(dateXpath, 20));
		assertTrueCatchException("Exepected product Name '"+productName+"' for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(productNameXpath, 20));
		assertTrueCatchException("Expired Image Status should be displayed for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(statusImageXpath, 20));
		assertTrueCatchException("Expected link '"+expectedLink1+"' should be displayed for the contract '"+expectedContractNumber+"'", homePage.checkIfElementExistsInPage(link1Xpath, 20));
		if(!homePage.checkIfElementExistsInPage(editRenewalLinkXpath,20)){
			assertTrueCatchException("Edit Renewal link is not be displayed for the expired contract "+expectedContractNumber,true);
		}else{
			assertTrueCatchException("Edit Renewal link is displayed for the expired contract "+expectedContractNumber,false);
		}
		
		homePage.click(viewDetailsXpath);
	}
	
	public void validateServices(String ContractType) throws MetadataException{
		String servicesForContractXpath=homePage.createFieldWithParsedFieldLocatorsTokens("servicesByUserType", ContractType);
		if(!homePage.checkIfElementExistsInPage(servicesForContractXpath,20)){
			assertTrueCatchException("Services are not display for expired contract "+ContractType,true);
		}else{
			assertTrueCatchException("Services are display for expired contract "+ContractType,false);
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




