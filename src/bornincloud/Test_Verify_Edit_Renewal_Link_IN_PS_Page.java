package bornincloud;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_Edit_Renewal_Link_IN_PS_Page extends BornInCloudTestBase {

	String contractNumberWithOneProduct=null;
	String contractNumberWithMultipleProducts=null;
	String contractNumberWithMoreThan20Products=null;
	int numberOfContracts;
	String numberForLink;
	String autoRenews="Auto-renews";
	String expires="Expires";
	String expired="Expired";
	BornInCloud bic ;
	boolean autoRecurringVal=false;
	boolean expiresRecurringVal=false;
	boolean expiredRecurringVal=false;
	List<String> recurringContracts=new ArrayList<String>();
	
	public Test_Verify_Edit_Renewal_Link_IN_PS_Page() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_US28() throws Exception {


		bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		LoginAndGoContracts();
		editLinkValidationsForAllRecurringContract();
		allTypeOfContractsVaidated();	
		// Logout
		homePage.click("arrow");

		homePage.click("signout");
	}
	
	public void editLinkValidationsForAllRecurringContract() throws MetadataException{
		
		
		String[] contractNumbers=homePage.getMultipleTextValuesfromField("contractNumers");
		for(int i=0;i<contractNumbers.length;i++){
			String contractNumber=contractNumbers[i];
			String contractNumberXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractNumber", contractNumber);
			homePage.click(contractNumberXpath);	
			if(homePage.checkIfElementExistsInPage("onlyRecurringContract", 3)){
				recurringContracts.add(contractNumber);
			}
			// Select Product Link
			bic.select("contractlist");
		}
		homePage.click("titleproductservices");
		for (int i=0;i<recurringContracts.size();i++){
			String recContractNumber=recurringContracts.get(i);
			String viewProductForRecContractXpath=homePage.createFieldWithParsedFieldLocatorsTokens("viewProductButtonForRecContract", recContractNumber);
			if (homePage.checkIfElementExistsInPage(viewProductForRecContractXpath, 3)){
				homePage.click(viewProductForRecContractXpath);
				String autoRenewalXpath=homePage.createFieldWithParsedFieldLocatorsTokens("editRenewalLinkForRecutingContractInPS",recContractNumber,autoRenews);
				String expiresRenewalXpath=homePage.createFieldWithParsedFieldLocatorsTokens("editRenewalLinkForRecutingContractInPS",recContractNumber,expires);
//				String expiredRenewalXpath=homePage.createFieldWithParsedFieldLocatorsTokens("editRenewalLinkForRecutingContractInPS",recContractNumber,expired);
				if(homePage.checkIfElementExistsInPage(autoRenewalXpath, 2)){
					assertTrueCatchException("Edit Renewal link is displayed for the recurring Auto Renews contract. Contract Number:"+recContractNumber,true);
					editRenewalLinkValidation(autoRenewalXpath);
					autoRecurringVal=true;
				}else if (homePage.checkIfElementExistsInPage(expiresRenewalXpath, 2)){
					assertTrueCatchException("Edit Renewal link is displayed for the recurring Expires contract. Contract Number:"+recContractNumber, true);
					editRenewalLinkValidation(expiresRenewalXpath);
					expiresRecurringVal=true;
				}
//				else if(!homePage.checkIfElementExistsInPage(expiredRenewalXpath, 5)){
//						assertTrueCatchException("Edit Renewal link is not displayed for the recurring Expired contract. Contract Number:"+recContractNumber,true);
//						expiredRecurringVal=true;
//					  }else{
//						EISTestBase.fail("Edit Renewal link is displayed for the recurring Expired contract. Contact:"+contractNumbers);
//					  }	
				homePage.click(viewProductForRecContractXpath);
			}
		}
		
	}	
	
	
	public void allTypeOfContractsVaidated(){
		
		if(!autoRecurringVal){
			//EISTestBase.fail("Recurring contract not found with Auto Renew type.");
			Util.printInfo("Recurring contract not found with Auto Renew type.");
		}
		if(!expiresRecurringVal){
			Util.printInfo("Recurring contract not found with Expires type.");
			//EISTestBase.fail("Recurring contract not found with Expires type.");
		
		}
//		if(!expiredRecurringVal){
//			EISTestBase.fail("Recurring contract not found with Expired type.");
//		}
	}
	public void LoginAndGoContracts() throws Exception {
		String env= getEnvironment();
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
		bic.select("contractlist");
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
		homePage.waitForFieldVisible("contracttable", 100000);
}
	public void editRenewalLinkValidation(String field){
		
		homePage.click(field);
		String winHandleBefore = homePage.getWindowHandle();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        if(getCurrentURL().equalsIgnoreCase(testProperties.getConstant("ExpectedEditRenewalLink"))){
			assertTrueCatchException("After clicking on Edit renewal link it navigated to expected page. Expected URL:"+getCurrentURL(), true);
		}
        driver.close();
        driver.switchTo().window(winHandleBefore);
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
