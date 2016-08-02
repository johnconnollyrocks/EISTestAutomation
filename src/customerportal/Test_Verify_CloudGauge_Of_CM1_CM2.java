package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.Util;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_CloudGauge_Of_CM1_CM2 extends CustomerPortalTestBase {
	
	
	public Test_Verify_CloudGauge_Of_CM1_CM2 () throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		
		
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_UsageReport_AddUser_method() throws Exception {
		if(getEnvironment().equalsIgnoreCase("DEV")){
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			Util.printInfo("Logging into customer portal");
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
		}
		Util.sleep(5000);
//		String ContractNumbers=null;
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		ArrayList<String> ContractNumbers=new ArrayList<String>();
		String TotalContractsCM1="";
		String TotalContractsCM2="";
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		String UniqueString=RandomStringUtils.randomAlphabetic(5);;
		//String UniqueEmailid=homePage.getConstant("EMAIL")+getUniqueId()+"@ssttest.net";
		mainWindow.select();
		Util.sleep(5000);
		String Contracts=null;
		String ContractAssignedUser="";
		String ContractAssignedUserCM2="";
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
		
		String RenderingEmailId=testProperties.getConstant("EMAILID");
		
		String FirstName = "unique";
		String UserName = "unique"+" "+"name";
		
		String Enduser1=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",UserName);
		
		homePage.populateField("UserSearch", RenderingEmailId);
		
		Util.sleep(4000);
		
		if(homePage.isFieldVisible(Enduser1)){
			RemoveUserBySearch(RenderingEmailId, UserName);
		}
		
		homePage.click("CloseSearch");
		Util.sleep(4000);
		
		addUser(RenderingEmailId, FirstName);
		
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
	
		
		
		//*****************************************************************************************************
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = capabilities.getBrowserName();
		
		if(!browserName.equalsIgnoreCase("safari")) {
			
			checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
			
			}
			else{
				
				
				if(!homePage.isChecked("inputProductsAssignAll")){
					Util.sleep(5000);
					Util.printInfo("The check box is unchecked");
					Util.printInfo("Clicking on checkbox..");
					Util.sleep(2000);
					jsToolTip("labelProductsAssignAll");
					//	 		homePage.click("endUserAutoCADRevitLTSuitRenderingServiceCheckBox");
					Util.sleep(2000);
					Util.printInfo("The check box is checked now..");
				}
				else {
					Util.printInfo("The check box is checked");	
					Util.sleep(2000);
				}
			}
		
		
		
		//***********************************************************************************************************
		
		
		
		List<WebElement> CM1Contracts=driver.findElements(By.xpath(".//*[contains(@id,'product-list')]/a/span[2]"));
		for(WebElement CMcontracts:CM1Contracts){
			String ContractsUnderCM1=CMcontracts.getText();
			TotalContractsCM1=ContractsUnderCM1+""+TotalContractsCM1;
		}
		
		homePage.click("SaveUser");
		
		homePage.refresh();
		Util.sleep(50000);
		
		List<WebElement> usersTable = driver.findElements(By.xpath(".//*[@id='results']/li"));
		
		for(int i=1;i<=usersTable.size();i++)
		{	
			boolean flag=false;
			Util.sleep(5000);
			String parsedUsers=homePage.createFieldWithParsedFieldLocatorsTokens("UserList",String.valueOf(i));
//			homePage.verifyFieldExists(parsedUsers);			
//			String Users=homePage.getFirstFieldLocator(parsedUsers);
			homePage.populateField("UserSearch", RenderingEmailId);
			String Users1=homePage.getAttribute(parsedUsers, "title");			
			if(Users1.trim().equalsIgnoreCase(RenderingEmailId)){
			String UsersContract=homePage.createFieldWithParsedFieldLocatorsTokens("UsersContractSTG",String.valueOf(i));		
			Util.sleep(5000);		
			homePage.click(UsersContract);
			Util.sleep(5000);		
			List<WebElement> ContractNumberSize=driver.findElements(By.xpath(".//*[@id='results']/li["+i+"]/section/div/div/div/div/p/following-sibling::ul/li"));
			
			for(WebElement Contract : ContractNumberSize )
			{
				
				Contracts=Contract.getText();
				ContractAssignedUser=ContractAssignedUser+Contracts;
			}
			
			Util.sleep(5000);
			homePage.refresh();
			Util.sleep(4000);
			flag=true;
			break;
			}			
		}	
		
		Util.printInfo("Contracts assigned to new user "+UserName+" in CM1 is/are :: "+ContractAssignedUser);
		
		homePage.click("UsageReport");
		Util.sleep(2000);
		homePage.click("byUsers");
		String ContractsCM1=null;
		homePage.refresh();
		Util.sleep(5000);
		List<WebElement> ContractsSize=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+UserName+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));
		
		for(WebElement Contract: ContractsSize){
		
			ContractsCM1=Contract.getText();
			
		String parsedContractValue=null;
		
		parsedContractValue =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+UserName+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+ContractsCM1+"')]/ancestor::td[@class='number']/following-sibling::td")).getText();
//		String parsedContractValue =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+UserName+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+ContractsCM1+"')]/ancestor::tr[@class='contract-usage no-usage']")).getText();
		
		/*String Array[]=parsedContractValue.split(ContractsCM1);
		
		System.out.println("Gauge Value1:"+Array[0]);
		System.out.println("Gauge Value2:"+Array[1]);*/
		if(!parsedContractValue.trim().contains("No Cloud Credits Purchased")){
		assertTrue("The Pooled Gauge "+parsedContractValue+"",parsedContractValue.trim().contains("0"));	
			}
		}
		
		
		if(ContractsSize.size()==0){
			
			/*String NoContractsAssigned=homePage.createFieldWithParsedFieldLocatorsTokens("NoContractsAssigned", UserName);
			String ContractsAssigned=homePage.getValueFromGUI(NoContractsAssigned);
			assertTrue("Under User :", ContractsAssigned.trim().equalsIgnoreCase("No Contract Assigned"));*/
			Util.printError("There are no Contracts under Pooled coloumn in CM1 for End User :: "+UserName);
			
		}
		
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		
		RemoveUserBySearch(RenderingEmailId, UserName);
		
		Util.printInfo("User in CM1 removed successfully :: ");
		homePage.refresh();
		Util.sleep(60000);
		
//		homePage.click("Logout");
		logoutMyAutodeskPortal(); 
		
		Util.printInfo("Logging into CM2's Account :: ");
		
		LoginCustomerPortal();
		Util.sleep(6000);
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		
		Util.printInfo("Adding a Same User in CM2");
		
		String RenderingEmailId1=testProperties.getConstant("EMAILID");
		
		String FirstName1 = "unique";
		String UserName1 = "unique"+" "+"email";
		
		String Enduser11=homePage.createFieldWithParsedFieldLocatorsTokens("ClickUser",UserName1);
		
		homePage.populateField("UserSearch", RenderingEmailId1);
		
		Util.sleep(4000);
		
		if(homePage.isFieldVisible(Enduser11)){
			RemoveUserBySearch(RenderingEmailId, UserName);
		}
		
		homePage.click("CloseSearch");
		Util.sleep(4000);
		
		addUser(RenderingEmailId1, FirstName1);
		
		checkChecKBox("inputBenefitsAssignAll", "labelBenefitsAssignAll");
		
		//**********************************************************************************
		
		
		if(!browserName.equalsIgnoreCase("safari")) {
			
			checkChecKBox("inputProductsAssignAll", "labelProductsAssignAll");
			
			}
			else{
				
				
				if(!homePage.isChecked("inputProductsAssignAll")){
					Util.sleep(5000);
					Util.printInfo("The check box is unchecked");
					Util.printInfo("Clicking on checkbox..");
					Util.sleep(2000);
					jsToolTip("labelProductsAssignAll");
					//	 		homePage.click("endUserAutoCADRevitLTSuitRenderingServiceCheckBox");
					Util.sleep(2000);
					Util.printInfo("The check box is checked now..");
				}
				else {
					Util.printInfo("The check box is checked");	
					Util.sleep(5000);
				}
			}
		
		
		
		//*******************************************************************************
		
		List<WebElement> CM2Contracts=driver.findElements(By.xpath(".//*[contains(@id,'product-list')]/a/span[2]"));
		for(WebElement CMcontracts:CM2Contracts){
			String ContractsUnderCM2=CMcontracts.getText();
			TotalContractsCM2=ContractsUnderCM2+""+TotalContractsCM2;
		}
		
		homePage.click("SaveUser");
		Util.sleep(5000);
		homePage.refresh();
		Util.sleep(60000);
		
		List<WebElement> usersTable1 = driver.findElements(By.xpath(".//*[@id='results']/li"));
		
		for(int i=1;i<=usersTable1.size();i++)
		{	
			boolean flag=false;
			Util.sleep(5000);
			String parsedUsers=homePage.createFieldWithParsedFieldLocatorsTokens("UserList",String.valueOf(i));
//			homePage.verifyFieldExists(parsedUsers);			
//			String Users=homePage.getFirstFieldLocator(parsedUsers);
			homePage.populateField("UserSearch", RenderingEmailId1);
			String Users1=homePage.getAttribute(parsedUsers, "title");			
			if(Users1.trim().equalsIgnoreCase(RenderingEmailId1)){
			String UsersContract=homePage.createFieldWithParsedFieldLocatorsTokens("UsersContractSTG",String.valueOf(i));		
			Util.sleep(5000);		
			homePage.click(UsersContract);
			Util.sleep(5000);		
			List<WebElement> ContractNumberSize=driver.findElements(By.xpath(".//*[@id='results']/li["+i+"]/section/div/div/div/div/p/following-sibling::ul/li"));
			
			for(WebElement Contract : ContractNumberSize )
			{
				
				Contracts=Contract.getText();
				ContractAssignedUserCM2=ContractAssignedUserCM2+Contracts;
			}
			
			Util.sleep(5000);
			homePage.refresh();
			Util.sleep(50000);
			flag=true;
			break;
			}			
		}
		
		Util.printInfo("Contracts assigned to new user "+UserName1+" in CM2 is/are :: "+ContractAssignedUserCM2);
		
		homePage.click("UsageReport");
		Util.sleep(15000);
		homePage.click("byUsers");
		String ContractsCM2=null;
		
		List<WebElement> ContractsSizeCM2=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+UserName1+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'#')]"));
		
		for(WebElement Contract: ContractsSizeCM2){
		
		ContractsCM2=Contract.getText();
		Util.sleep(5000);
		String parsedContractValue =driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody//*[contains(text(),'"+UserName1.trim()+"')]/ancestor::tr//td[@class='pooled-usage']//*[contains(text(),'"+ContractsCM2+"')]/ancestor::td[@class='number']/following-sibling::td")).getText();
		
		if(!parsedContractValue.trim().contains("No Cloud Credits Purchased")){
			assertTrue("The Pooled Gauge for :"+ContractsCM1+" is "+parsedContractValue+"",parsedContractValue.trim().contains("0"));		
			}
		}
		
		if(ContractsSizeCM2.size()==0){
			
			/*String NoContractsAssigned=homePage.createFieldWithParsedFieldLocatorsTokens("NoContractsAssigned", UserName);
			String ContractsAssigned=homePage.getValueFromGUI(NoContractsAssigned);
			assertTrue("Under User :", ContractsAssigned.trim().equalsIgnoreCase("No Contract Assigned"));*/
			Util.printError("There are no Contracts under Pooled coloumn in CM2 for End User :: "+UserName);
			
		}
		
		homePage.waitForField("users", true);
		homePage.clickAndWait("users","selectAllInUP");
		RemoveUserBySearch(RenderingEmailId, UserName);
		
		homePage.refresh();
		Util.sleep(50000);
		
		//if(!ContractsCM1.equalsIgnoreCase("null") && !ContractsCM2.equalsIgnoreCase("null")){
//		Assert.assertFalse("Contracts of CM1 and CM2 are not Equal",ContractsCM2.trim().contains(ContractsCM1));
		Util.printInfo("validating CM1 and CM2 contracts");
				
		Util.printInfo("contracts of CM1 :: "+TotalContractsCM1);
		
		Util.printInfo("contracts of CM2 :: "+TotalContractsCM2);
		
		assertEquals(TotalContractsCM1.trim().contains(TotalContractsCM2),false);
		
		Util.printInfo("CM1 and CM2 contracts are distinct :: ");
		
		/*assertTrue("CM1 and CM2 Contracts are not same",TotalContractsCM2.contains(TotalContractsCM1));
//		homePage.click("AssignContracts");
		homePage.click("SaveUser");
		Util.sleep(5000);
		homePage.refresh();				
		homePage.click("UsageReport");
		homePage.click("byUsers");
		int i;
		List<WebElement> ReportingUsers=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr/td/label"));
		for(i=1;i<=ReportingUsers.size();i++){
		String CM2User=testProperties.getConstant("FIRSTNAME")+" "+testProperties.getConstant("LASTNAME");
		String Users=homePage.createFieldWithParsedFieldLocatorsTokens("ReportingUser", String.valueOf(i));
		String NewUser=homePage.getValueFromGUI(Users);
		if(NewUser.trim().equalsIgnoreCase(CM2User)){
//			String CM2Contracts=homePage.createFieldWithParsedFieldLocatorsTokens("NewUser", String.valueOf(i));			
			ArrayList<String> NewUserContract=new ArrayList<String>();
			List<WebElement>  NewUserContractSize=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/td//table/tbody/tr"));		
			for(int j=1;j<=NewUserContractSize.size();j++){
				String CM2UserContract=driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr["+i+"]/td//table/tbody/tr["+j+"]")).getText();
				NewUserContract.add(CM2UserContract);
//			assertTrue("User Under CM2 has the different Contracts of CM1", NewUserContract.contains(ContractNumbers));
//			if(ContractNumbers.contains(NewUserContract))			
			}
			assertFalse("CM1 User and CM2 User has different Contracts",ContractNumbers.contains(NewUserContract));
		}
	}
		homePage.clickAndWait("users","selectAllInUP");
		Util.sleep(5000);
//		RemoveUser(i);
		homePage.refresh();*/
		
		logoutMyAutodeskPortal();
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
