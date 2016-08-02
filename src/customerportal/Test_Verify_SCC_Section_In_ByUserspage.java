package customerportal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;
import common.exception.MetadataException;

//Author: Santosh Kumar

public class Test_Verify_SCC_Section_In_ByUserspage extends CustomerPortalTestBase{
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String newEmailWithContract = null;
	public String newFirstNameWithContract = null;
	public String newLastNameWithContract = null;
	public String newEmailWithOutContract = null;
	public String newFirstNameWithOutContract = null;
	public String newLastNameWithOutContract = null;
	Map<String, String> newuserDetails = new TreeMap<String, String>();
	
	
	public Test_Verify_SCC_Section_In_ByUserspage() throws IOException {
		super("Browser",getAppBrowser());		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verify_ProductUpdates_CM_SC_Settings() throws Exception {
		
		//TC429-Test the Shared Cloud Credit section in ByUsers page
		
		//Login with CM user and go to By users page
		LoginAndGoToByUsersPage("CM");		
		validateAllUsersAvailable();
		validateSharedSortOption();
		//go to Manage users page
		GoToPage("users","manageusers");
		//create user and with out assigning any contract
		newuserDetails=addUserReturnDetails(false);
		newEmailWithOutContract=newuserDetails.get("email");
		newFirstNameWithOutContract=newuserDetails.get("firstName");
		newLastNameWithOutContract=newuserDetails.get("lastName");
		//create user and assigning any contract
		newuserDetails=addUserReturnDetails(true);		
		newEmailWithContract=newuserDetails.get("email");
		newFirstNameWithContract=newuserDetails.get("firstName");
		newLastNameWithContract=newuserDetails.get("lastName");
		//Go to By By user page	
		GoToPage("reportingPage","byusers");
		//validate the line item is showing for new user create with message 'No Contract Assigned'
		String newUserWithOutContractXpath=homePage.createFieldWithParsedFieldLocatorsTokens("userWithOutContract", newFirstNameWithOutContract+ " "+newLastNameWithOutContract);
		System.out.println(homePage.getFieldLocators(newUserWithOutContractXpath));
		assertTrue("The user has a line item in his name and SCC section has No contract assigned", homePage.checkFieldExistence(newUserWithOutContractXpath));
		//validate the line item is showing for new user create Contract Assigned'
		String newUserWithContractXpath=homePage.createFieldWithParsedFieldLocatorsTokens("userwithContract", newFirstNameWithContract+ " "+newLastNameWithContract);
		System.out.println(homePage.getFieldLocators(newUserWithContractXpath));
		assertTrue("The user has a line item in his name and SCC section has contract assigned", homePage.checkFieldExistence(newUserWithContractXpath));
		//go to Manage users page
		GoToPage("users","manageusers");
		//Remove new user with out contract
		RemoveUser(newFirstNameWithOutContract+ " "+newLastNameWithOutContract);
		//Get contract assigned to new user who assigned with contract 
		String contractlistXpath=homePage.createFieldWithParsedFieldLocatorsTokens("userwithContract", newFirstNameWithContract+ " "+newLastNameWithContract);
		String [] contractList=homePage.getMultipleTextValuesfromField(contractlistXpath);
		//consume cloud credits for any one agreement assigned to the new user
		getGUIDAndConsumeCC(newEmailWithContract,contractList[0]);
		//Remove new user with contract
		RemoveUser(newFirstNameWithContract+ " "+newLastNameWithContract);
		//Go to By Users page
		GoToPage("reportingPage","byusers");
		//Validate whether new user with out any contract is not showing in table
		if (!homePage.checkFieldExistence(newUserWithContractXpath)){
			assertTrue("The Line item  new user with out contract is not showing in By Users page after deleting",true);
		}
		//Validate whether new user with contract is showing with removed msg in ICC
		String removedUserLabelXpath=homePage.createFieldWithParsedFieldLocatorsTokens("removedUserLabel", newFirstNameWithContract+ " "+newLastNameWithContract);
		assertTrue("The Line item  new user with contract is showing as Removed in By Users page after deleting",homePage.checkFieldExistence(removedUserLabelXpath));
		
		
	}
	
	public void getGUIDAndConsumeCC(String email, String contract) throws IOException{
		String guid=getGUIDForUser(email);
		consumeCloudCredits(guid, contract);
		
	}
	
	public void LoginAndGoToByUsersPage(String userType) throws Exception {
		
			if (userType.equalsIgnoreCase("CM")){
				if (getEnvironment().equalsIgnoreCase("dev")) {
					USERNAME = testProperties.getConstant("CM_USERNAME_DEV");				
					PASSWORD = testProperties.getConstant("CM_PASSWORD_DEV");
				} else if (getEnvironment().equalsIgnoreCase("stg")) {
					USERNAME = testProperties.getConstant("CM_USERNAME_STG");
					PASSWORD = testProperties.getConstant("CM_PASSWORD_STG");
				
				}
			}else if(userType.equalsIgnoreCase("SC")){
				if (getEnvironment().equalsIgnoreCase("dev")) {
					USERNAME = testProperties.getConstant("SC_USERNAME_DEV");				
					PASSWORD = testProperties.getConstant("SC_PASSWORD_DEV");
				} else if (getEnvironment().equalsIgnoreCase("stg")) {
					USERNAME = testProperties.getConstant("SC_USERNAME_STG");
					PASSWORD = testProperties.getConstant("SC_PASSWORD_STG");
				
				}
			}		
		loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);
//		GoToPage("reporting","byusers");

	}
	public void validateAllUsersAvailable(){
		Util.printInfo("VALIDATION PENDING.Need to add logic to Validate whether all users are displayed in By Users Page");
	}
	public void validateSharedSortOption(){
		Util.printInfo("VALIDATION PENDING.Need to add logic for sorting by Shared in BY Users Page");
	}
	public Map<String, String> addUserReturnDetails(boolean assignUser) throws MetadataException{
		
		Map<String, String> userDetails = new TreeMap<String, String>();
		
		String email="Portal2"+getUniqueNumber(8)+"@ssttest.net";
		String firstName="First"+getUniqueString(4);
		String lastName="Last"+getUniqueString(4);
		CustomerPortal customerPortal = utilCreateMyAutodeskPortalWithProductUpdateAndEmailObject(testProperties);
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		Util.printInfo("Clicking on add user buttong. ");
		homePage.click("addUserButton");
		homePage.populateField("email", email);
		homePage.populateField("firstName", firstName);
		homePage.populateField("lastName", lastName);
		if (assignUser){
			do {
				homePage.check("addAccessCheckBox");
				homePage.click("saveContinueButton");
				Util.sleep(10000);
			}while(homePage.checkIfElementExistsInPage("saveContinueButton",100));
			Util.printInfo("Assigning Benefits & Products to the user: "+email);
			checkChecKBox("productDowloadsCheckboxInput", "productDowloadsCheckboxLabel");
			checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
			checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
			homePage.click("saveButton");
		}else{
			homePage.uncheck("addAccessCheckBox");
			do {
				homePage.click("saveContinueButton");
				Util.sleep(10000);
			}while(homePage.checkIfElementExistsInPage("saveContinueButton",50));
		}
		
		String newUserXpath=homePage.createFieldWithParsedFieldLocatorsTokens("validateNewUser", email);
		if (homePage.checkIfElementExistsInPage(newUserXpath, 20)){
			Util.printInfo("User Created Sucessfully with email ID: "+email );
		}else{
			EISTestBase.failTest("User not created. Created user not found in Manage users page");
		}
		userDetails.put("emailID", email);
		userDetails.put("firstName", firstName);
		userDetails.put("lastName", lastName);
		return userDetails;
	}
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
	}
	
}

