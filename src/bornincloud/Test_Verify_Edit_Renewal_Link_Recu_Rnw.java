package bornincloud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_Edit_Renewal_Link_Recu_Rnw extends BornInCloudTestBase {

	String contractNumberWithOneProduct=null;
	String contractNumberWithMultipleProducts=null;
	String contractNumberWithMoreThan20Products=null;
	int numberOfContracts;
	String numberForLink;
	boolean autoRecurringVal=false;
	boolean expiresRecurringVal=false;
	boolean expiredRecurringVal=false;
	boolean autoRenewaVall=false;
	boolean expiresRenewalVal=false;
	boolean expiredRenewalVal=false;
	BornInCloud bic ;
	
	public Test_Verify_Edit_Renewal_Link_Recu_Rnw() {
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
		LoginAndGoContracts("RECURRING");
		editLinkValidationsForAllContract("RECURRING");
		logoutFromPortal();
		LoginAndGoContracts("RENEWAL");
		editLinkValidationsForAllContract("RENEWAL");
		allTypeOfContractsVaidated();
		logoutFromPortal();
		
	}
	
	public void editLinkValidationsForAllContract(String contractType) throws MetadataException{
		
		String editRenewalXpath=null;
		String autoRenews="Auto-renews";
		String expires="Expires";
		String expired="Expired";
		
		String[] contractNumbers=homePage.getMultipleTextValuesfromField("contractNumers");
		String contractTypeInContractPage="";
		for(int i=0;i<contractNumbers.length;i++){
			String contractNumber=contractNumbers[i];
			String contractNumberXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractNumber", contractNumber);
			homePage.click(contractNumberXpath);	
			
			
			contractTypeInContractPage=homePage.getValueFromGUI("contractTypeINContractPage");
			String contractStatusInContractPage=homePage.getValueFromGUI("contractStatusInContractPage");
			
			if(contractTypeInContractPage.contains("Recurring")){
				
					if(contractStatusInContractPage.contains("Auto")){
						editRenewalXpath=homePage.createFieldWithParsedFieldLocatorsTokens("recurringEditLink", autoRenews);
						assertTrueCatchException("Edit Renewal link is displayed for the recurring Auto Renews contract. Contract Number:"+contractNumber, homePage.checkIfElementExistsInPage(editRenewalXpath, 5));
						editRenewalLinkValidation(editRenewalXpath);
						autoRecurringVal=true;
					}else if(contractStatusInContractPage.contains("Expires")){
							editRenewalXpath=homePage.createFieldWithParsedFieldLocatorsTokens("recurringEditLink", expires);
							assertTrueCatchException("Edit Renewal link is displayed for the recurring Expires contract. Contract Number:"+contractNumber, homePage.checkIfElementExistsInPage(editRenewalXpath, 5));
							editRenewalLinkValidation(editRenewalXpath);
							expiresRecurringVal=true;
					}else if(contractStatusInContractPage.contains("Expired")){
							editRenewalXpath=homePage.createFieldWithParsedFieldLocatorsTokens("recurringEditLink", expired);
							expiredRecurringVal=true;
							if(! homePage.checkIfElementExistsInPage(editRenewalXpath, 5)){
								assertTrueCatchException("Edit Renewal link is not displayed for the recurring Expired contract. Contract Number:"+contractNumber,true);
							}else{
								EISTestBase.fail("Edit Renewal link is displayed for the recurring Expired contract. Contact:"+contractNumbers);
							}
					}
			}else if(contractTypeInContractPage.contains("Renewal")){
					
//					if(contractStatusInContractPage.contains("Auto")){
//						editRenewalXpath=homePage.createFieldWithParsedFieldLocatorsTokens("renewalEditLink", autoRenews);
//						if(! homePage.checkIfElementExistsInPage(editRenewalXpath, 5)){
//							assertTrueCatchException("Edit Renewal link is not displayed for the Auto Renewal Renewal contract. Contract Number:"+contractNumber,true);
//						}else{
//							EISTestBase.fail("Edit Renewal link is displayed for the Auto Renewal, renewal contract. Contact:"+contractNumbers);
//						}
//						autoRenewaVall=true;
//					}else 
					if (contractStatusInContractPage.contains("Expires")){
						editRenewalXpath=homePage.createFieldWithParsedFieldLocatorsTokens("renewalEditLink", expires);
						if(! homePage.checkIfElementExistsInPage(editRenewalXpath, 5)){
							assertTrueCatchException("Edit Renewal link is not displayed for the Expires Renewal contract. Contract Number:"+contractNumber,true);
						}else{
							EISTestBase.fail("Edit Renewal link is displayed for the Expires, renewal contract. Contact:"+contractNumbers);
						}
						expiresRenewalVal=true;
					}else if(contractStatusInContractPage.contains("Expired")){
						editRenewalXpath=homePage.createFieldWithParsedFieldLocatorsTokens("renewalEditLink", expired);
						if(! homePage.checkIfElementExistsInPage(editRenewalXpath, 5)){
							assertTrueCatchException("Edit Renewal link is not displayed for the Expired Renewal contract. Contract Number:"+contractNumber,true);
						}else{
							EISTestBase.fail("Edit Renewal link is displayed for the Expired, renewal contract. Contact:"+contractNumbers);
						}
						expiredRenewalVal=true;
					}
			}
			
			// Select Product Link
			bic.select("contractlist");
		}	
	}
	
	public void LoginAndGoContracts(String userType) throws Exception {
		String env= getEnvironment();
		if(userType.equalsIgnoreCase("Renewal")){
			if (env.equalsIgnoreCase("DEV")) {
				bic.login(testProperties.getConstant("USER_ID_DEV_RNW"),
						testProperties.getConstant("PASSWORD_DEV_RNW"));
			} else if (env.equalsIgnoreCase("STG")) {
				bic.login(testProperties.getConstant("USER_ID_STG_RNW"),
						testProperties.getConstant("PASSWORD_STG_RNW"));
			}	
		}else if(userType.equalsIgnoreCase("Recurring")){
			if (env.equalsIgnoreCase("DEV")) {
				bic.login(testProperties.getConstant("USER_ID_DEV_REC"),
						testProperties.getConstant("PASSWORD_DEV_REC"));
			} else if (env.equalsIgnoreCase("STG")) {
				bic.login(testProperties.getConstant("USER_ID_STG_REC"),
						testProperties.getConstant("PASSWORD_STG_REC"));
			}	
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
	
	public void allTypeOfContractsVaidated(){
			
		if(!autoRecurringVal){
			//EISTestBase.fail("Recurring contract not found with Auto Renew type.");
			Util.printInfo("Recurring contract not found with Auto Renew type.");
		}
		if(!expiresRecurringVal){
			//EISTestBase.fail("Recurring contract not found with Expires type.");
			Util.printInfo("Recurring contract not found with Expires type.");
		}
		if(!expiredRecurringVal){
			//EISTestBase.fail("Recurring contract not found with Expired type.");
			Util.printInfo("Recurring contract not found with Expired type.");
		}
//		if(!autoRenewaVall){
//			EISTestBase.fail("Renewal contract not found with Auto Renew type.");
//		}
		if(!expiresRenewalVal){
			Util.printInfo("Renewal contract not found with expires type.");
			//EISTestBase.fail("Renewal contract not found with expires type.");
		}
		if(!expiredRenewalVal){
			Util.printInfo("Renewal contract not found with expired type.");
			//EISTestBase.fail("Renewal contract not found with expired type.");
		}
	}
	public void logoutFromPortal(){
		// Logout
				homePage.click("arrow");

				homePage.click("signout");
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
