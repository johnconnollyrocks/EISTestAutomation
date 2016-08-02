package customerportal;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.selenium.condition.Condition;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;
import customerportal.UserDetailsDTO;

/**
 * Test class - Test_AddUserAndAssignBenifitsProductsInNAMUApplication
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_Verify_EditUser_SCAT extends CustomerPortalTestBase {
	
	
	public Test_Verify_EditUser_SCAT() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_Consumed_Users_method() throws Exception {
		//setDebugMode(true);
		SoapUIExampleTest suet = new SoapUIExampleTest();
		DOMXmlParser domParser = new DOMXmlParser();
		if(getEnvironment().equalsIgnoreCase("DEV")){
			LaunchSCAT(testProperties.getConstant("USERNAME_SCAT"),testProperties.getConstant("PASSWORD_SCAT"));
		}
		else if(getEnvironment().equalsIgnoreCase("STG")){
			Util.printInfo("Logging into SCAT :: ");
			LaunchSCAT(testProperties.getConstant("USERNAME_SCAT"),testProperties.getConstant("PASSWORD_SCAT"));
		}
		Util.sleep(5000);
		Util.printInfo("Logged into SCAT App :: ");
		homePage.click("SCATEditUsers");
//		driver.findElement(By.xpath("//span/a[contains(@class,'textSmall') And contains(text(),'Edit Users')]")).click();
		homePage.populateField("SCATEMailField", testProperties.getConstant("EMAIL_SCAT"));
		Util.sleep(4000);
		homePage.click("SCATUserID");
		Util.sleep(4000);
		homePage.click("SCATSubmit");
		String ChangeEmailId1=RandomStringUtils.randomAlphabetic(5)+"123@ssttest.net";
		String ChangeEmailId=ChangeEmailId1.toLowerCase();
		homePage.populateField("SCATEditEmail", ChangeEmailId);
		Util.sleep(4000);
		String ChangeFirstName=RandomStringUtils.randomAlphabetic(5);
		String StartingLetterUPCase=toTitleCase(ChangeFirstName);
		homePage.populateField("SCATEditFirstName",StartingLetterUPCase);
		String ChangeLastName=RandomStringUtils.randomAlphabetic(5);
		String StartingLetterLowCase=toTitleCase(ChangeLastName);
		homePage.populateField("SCATEditLastName",StartingLetterLowCase);
		homePage.populateField("SCATPhoneNumber","1234567890");
		homePage.click("SCATSubmitProfile");
		Util.sleep(4000);
		if(homePage.isFieldVisible("SCATEditUserErrorMsg")){
			EISTestBase.fail("SCAT Edit User Failed ");
		}else{
			homePage.verifyFieldNotExists("SCATEditUserErrorMsg");
			Util.printInfo("User successfully Updated in SCAT :: ");
		}
		
		Util.printInfo("Assigning Phone Support to the User :: "+StartingLetterUPCase+""+StartingLetterLowCase);
		homePage.click("SCATPhoneSupportUser");
		homePage.populateField("SCATEMailField", testProperties.getConstant("USER_NAME"));
		Util.sleep(4000);
		homePage.click("SCATUserID");
		Util.sleep(4000);
		homePage.click("SCATSubmit");
		Util.sleep(2000);
		homePage.refresh();
		Util.sleep(4000);
		homePage.refresh();
		Util.sleep(4000);
		homePage.refresh();
		Util.sleep(4000);
		homePage.refresh();
		Util.sleep(4000);
		homePage.refresh();
		Util.sleep(4000);
		String SCATPhoneSUpportUSerList=homePage.createFieldWithParsedFieldLocatorsTokens("SCATPhoneSUpportUSerList", StartingLetterUPCase+""+StartingLetterLowCase);
		WebElement web=driver.findElement(By.xpath("//td/b[contains(text(),'Available users')]/following-sibling::div/select"));
		Util.sleep(4000);
		Select sel=new Select(web);
		String temp=(StartingLetterUPCase+""+StartingLetterLowCase).trim();
		sel.selectByVisibleText(temp);
//		homePage.click(SCATPhoneSUpportUSerList);
		if(homePage.isFieldVisible("SCATAddPhoneUser")){
		homePage.click("SCATAddPhoneUser");
		}
		Util.sleep(2000);
		homePage.click("SCATUpdate");
		Util.sleep(8000);
		if(homePage.isFieldVisible("SCATUpdatePhoneSupportUpdatedSuccessfully")){
			homePage.verifyFieldExists("SCATUpdatePhoneSupportUpdatedSuccessfully");
		}else{
			EISTestBase.fail("Phone Support user not updated successfully :: ");
		}
		
		homePage.click("SCATLogout");
		Util.printInfo("Logout from SCAT :: ");
		
		Util.printInfo("Logging into customer portal :: ");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		Util.sleep(10000);
		homePage.clickAndWait("users","selectAllInUP");
		String CPEndUserAfterChangingFirstandLastName=homePage.createFieldWithParsedFieldLocatorsTokens("CPEndUser", temp);
		homePage.refresh();
		Util.sleep(10000);
		if(homePage.isFieldPresent(CPEndUserAfterChangingFirstandLastName)){
			homePage.verifyFieldExists(CPEndUserAfterChangingFirstandLastName);
		}
		
		else{
			homePage.refresh();
			Util.sleep(10000);
			homePage.click("Logout");
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
			Util.sleep(10000);
			homePage.refresh();
			Util.sleep(10000);
			homePage.clickAndWait("users","selectAllInUP");
			if(homePage.isFieldPresent(CPEndUserAfterChangingFirstandLastName)){
				homePage.verifyFieldExists(CPEndUserAfterChangingFirstandLastName);
			}else{
				homePage.refresh();
				homePage.click("Logout");
				Util.sleep(60000);
				loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
				Util.sleep(10000);
				homePage.refresh();
				Util.sleep(60000);
				homePage.clickAndWait("users","selectAllInUP");
			}if(homePage.isFieldPresent(CPEndUserAfterChangingFirstandLastName)){
				homePage.verifyFieldExists(CPEndUserAfterChangingFirstandLastName);
			}else{
				EISTestBase.fail("Changes not reflected in Customer Portal After Changing First and Last Name in SCAT :: ");
			}
		}
		homePage.click(CPEndUserAfterChangingFirstandLastName);
		Util.sleep(2000);
		String CPEndUserAfterChangingEmailId=homePage.createFieldWithParsedFieldLocatorsTokens("CPEmailEdit", ChangeEmailId);
		homePage.refresh();
		Util.sleep(4000);
		
		homePage.click(CPEndUserAfterChangingFirstandLastName);
		if(homePage.isFieldVisible(CPEndUserAfterChangingEmailId)){
			homePage.verifyFieldExists(CPEndUserAfterChangingEmailId);
		}else{
			homePage.refresh();
			homePage.click("Logout");
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
			Util.sleep(10000);
			homePage.click(CPEndUserAfterChangingFirstandLastName);
			if(homePage.isFieldPresent(CPEndUserAfterChangingEmailId)){
				homePage.verifyFieldExists(CPEndUserAfterChangingEmailId);
			}else{
				EISTestBase.fail("Changes not reflected in Customer Portal After Changing Email ID in SCAT :: ");
			}
		}
		
		String CPUserEditAccess=homePage.createFieldWithParsedFieldLocatorsTokens("CPUserEditAccess", temp);
		homePage.click(CPUserEditAccess);
		Util.printInfo("Checking Phone support user update in Customer portal :: ");
		
		String CPPhoneSupport=null;
		boolean flag=false;
		List<WebElement> CM1Contracts=driver.findElements(By.xpath(".//*[contains(@id,'product-list')]/a/span[2]"));
		for(WebElement CMcontracts:CM1Contracts){
			String ContractsUnderCM1=CMcontracts.getText();
			String ContractsUnderCM[]=ContractsUnderCM1.split("#");
			Util.sleep(2000);
			driver.findElement(By.xpath(".//*[@id='product-list-link_"+ContractsUnderCM[1]+"']/a/span[2]")).click();
			Util.sleep(4000);
			if(homePage.isFieldVisible("CPPhoneSupport")){
			flag=true;
			CPPhoneSupport=homePage.getValueFromGUI("CPPhoneSupport");
			}
			if(flag){
			if(!CPPhoneSupport.trim().contains("Unassigned")){
				Util.sleep(4000);
				assertTrue("Phone Support for the user "+ChangeFirstName+" "+ChangeLastName+" Assigned Successfully ", CPPhoneSupport.trim().contains("Assigned"));
				break;
				}else{
					EISTestBase.fail("No Phone Support assigned for the user :: "+temp);
				}
			}
			driver.findElement(By.xpath(".//*[@id='product-list-link_"+ContractsUnderCM[1]+"']/a/span[2]")).click();
		}
		
		homePage.click("CPEditSave");
		
		LaunchSCAT(testProperties.getConstant("USERNAME_SCAT"),testProperties.getConstant("PASSWORD_SCAT"));
		homePage.click("SCATPhoneSupportUser");
		homePage.populateField("SCATEMailField", testProperties.getConstant("USER_NAME"));
		Util.sleep(4000);
		homePage.click("SCATUserID");
		Util.sleep(4000);
		homePage.click("SCATSubmit");
		Util.sleep(4000);
		WebElement web1=driver.findElement(By.xpath("//tr/td[@class='user_box_right']/div/select"));
		Select sel1=new Select(web1);
		sel1.selectByVisibleText(temp);
		homePage.click("SCATPhoneRemoveUser");
		Util.sleep(2000);
		homePage.click("SCATUpdate");
		Util.sleep(6000);
		Util.sleep(2000);
		if(homePage.isFieldVisible("SCATUpdatePhoneSupportUpdatedSuccessfully")){
			homePage.verifyFieldExists("SCATUpdatePhoneSupportUpdatedSuccessfully");
		}else{
			EISTestBase.fail("Phone Support user not updated successfully :: ");
		}
		
		homePage.click("SCATLogout");
		
		Util.printInfo("Logout from SCAT :: ");
		
		/*Util.printInfo("Logging into customer portal :: ");
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));

		homePage.clickAndWait("users","selectAllInUP");
		String CPUserEditAccess1=homePage.createFieldWithParsedFieldLocatorsTokens("CPUserEditAccess", ChangeFirstName+" "+ChangeLastName);
		homePage.click(CPUserEditAccess1);
		Util.printInfo("Checking Un assignment of Phone support user update in Customer portal :: ");
		
		String CPPhoneSupport1=null;
		boolean flag1=false;
		List<WebElement> CM1Contracts1=driver.findElements(By.xpath(".//*[contains(@id,'product-list')]/a/span[2]"));

		for(WebElement CMcontracts:CM1Contracts1){
			String ContractsUnderCM1=CMcontracts.getText();
			String ContractsUnderCM[]=ContractsUnderCM1.split("#");
			driver.findElement(By.xpath(".//*[@id='product-list-link_"+ContractsUnderCM[1]+"']/a/span[2]")).click();
			if(homePage.isFieldVisible("CPPhoneSupport")){
			flag1=true;
			CPPhoneSupport1=homePage.getValueFromGUI("CPPhoneSupport");
			}
			if(flag1){
			if(CPPhoneSupport1.trim().contains("Unassigned")){
				assertTrue("Phone Support for the user "+ChangeFirstName+" "+ChangeLastName+" Unassigned Successfully ", CPPhoneSupport1.trim().equalsIgnoreCase("Unassigned"));
				}else{
					EISTestBase.fail("No Phone Support Unassigned for the user :: "+ChangeFirstName+" "+ChangeLastName);
				}
			}
		}*/
		
//		homePage.click("CPEditSave");
		
		/*CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		launchSCAT(testProperties.getConstant("USERNAME_SCAT"),testProperties.getConstant("PASSWORD_SCAT"));*/
		
		
		
//		logoutMyAutodeskPortal();
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
