package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_VerifyInstallNowButton
 * Scenario2: P & S Page – Manage Access Modal – Available Versions, Assign/Un-Assign New Versions, Select All Versions and Save, Modify Permissions and Don’t Save and Modify Permissions and Save
 * @author R.Puraj
 * @version 1.0.0
 */

public final class Test_Verify_Manage_Access_Modal_DEV extends CustomerPortalTestBase {
	WebDriverWait wb=new WebDriverWait(driver,40000);
	public Test_Verify_Manage_Access_Modal_DEV() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyInstallNowButton() throws Exception {
		
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
				
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		Util.printMessage("\"All Products & Services\" page is displayed");	
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();		
		
	// Get the contract Number with version
		
		String ContractNumber = getMaxConVersion();
		
		
	//Assign all contracts  and UnAssign Permission only for CM
		Util.printInfo("Unassign all the version for "+ContractNumber);
		setVersionBlank(ContractNumber);
		System.out.println("*****************************1*************************************");
		Util.printInfo("Assign 2011 version for "+ContractNumber);
		assignVersion(ContractNumber,"2011");
		System.out.println("*****************************2*************************************");
		Util.printInfo("Assign 2012 version for "+ContractNumber);
		assignVersion(ContractNumber,"2012");
		System.out.println("*****************************3*************************************");
		Util.printInfo("Assign 2013 version for "+ContractNumber);
		assignVersion(ContractNumber,"2013");
		System.out.println("*****************************4*************************************");
		Util.printInfo("Assign 2014 version for "+ContractNumber);
		assignVersion(ContractNumber,"2014");
		System.out.println("*****************************5*************************************");
		Util.printInfo("Assign ONLINE version for "+ContractNumber);
		assignVersion(ContractNumber,"ONLINE");
		System.out.println("*****************************6*************************************");
		Util.printInfo("Assign All versions for "+ContractNumber);
		setAllVersion(ContractNumber);
		System.out.println("*****************************7*************************************");
	
		logoutMyAutodeskPortal();
		
	}
	
	
	
	

	public String getMaxConVersion(){
	
	String MaxContractNumber = null;
				
	//Get all the contract numbers
		List<WebElement> Products=driver.findElements(By.xpath(".//*[contains(@id,'11')]/div[2]/div[1]/div/div/div/span/span"));
		Util.printInfo("Number of Contracts displayed :"+Products.size());
		 MaxContractNumber = Products.get(0).getText();
			if(!(Products.size()==0)){
	//iterate thru individual contract numbers to find the Max version
			for(WebElement Version: Products){
						
		String ContractNumber=Version.getText();
		
		List <String> unsortedList = new ArrayList<String>();
	//Get list of all Products with more than one Version and Find the Max Version
		List<WebElement> PSVersions =driver.findElements(By.xpath("//span[contains(text(),'Versions')]"));
		
		if(!(PSVersions.size()==0)){								
		
		for(WebElement vers : PSVersions){
			if(org.apache.commons.lang.StringUtils.isNotBlank(vers.getText())){
				unsortedList.add(vers.getText());
			}
		}					
				Collections.sort(unsortedList);
				Collections.reverse(unsortedList);
				String MaxVersion = unsortedList.get(0);
				String ContractVers=driver.findElement(By.xpath("//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[1]/div/div/div/div[1]/p/span[1]")).getText();
						
						
	//Find the Maxversion with Contract number
			if(MaxVersion.equals(ContractVers)){
			    MaxContractNumber =ContractNumber;
				Util.printInfo("Contract has more than one version : "+ContractNumber);							
					}
			}
			}	
			}else{
				Util.printInfo("Versions are not Assigned to any Contract in the \"Products and Services\" page");
				}
		return MaxContractNumber;
					
}
	
	public void setVersionBlank(String ContractNumber){
	//Click on Edit Manage Access link				
		
		driver.findElement(By.xpath("//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[2]/div/div/div/a[2]/div")).click();
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Manage Access')]")));

	//Check if Contracts are assigned if true then Unassign all the contracts
			List <WebElement> EmailWB= driver.findElements(By.xpath(".//*[@id='user-permissions-list']//div[3]/p[2]"));						
	
		if(!(EmailWB.size()==0)){
	//Get list of all the email address for which Contracts are assigned
			
			List <String> EmailList = new ArrayList <String>();
			for (WebElement  emailWB : EmailWB){
					String EmailAddress = emailWB.getText();
					EmailList.add(EmailAddress);
				}
			
		//Close Manage Access window after fetching all the email address
			System.out.println("****************setVersionBlock1********************************");
				Util.printInfo("Closing Manage Access Window");
				homePage.clickAndWait("CloseButtonManageAccess","users");
				
		//Using EmailID unassign all the contracts	
				ManageAccessclearPermission(EmailList);
				
		/*//Go to Product & Services Page		
				homePage.click("ProductsandServices");
				wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='products-services-page']/header/div/div[1]/a")));
				driver.findElement(By.xpath("//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[2]/div/div/div/a[2]/div")).click();
				wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Manage Access')]")));*/
			}else{
				System.out.println("****************setVersionBlock2********************************");
				Util.printInfo("Closing Manage Access Window");
				homePage.clickAndWait("CloseButtonManageAccess","users");
			}
				//Verify with out selecting any version
		System.out.println("****************setVersionBlock3********************************");    
			//Assign all Benefits and Services to CM
			CMAssignPandS(ContractNumber);
			
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='results']/li")));
			homePage.click("ProductsandServices");	
			//Wait for Manage Users Button to be displayed
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='products-services-page']/header//a")));		
			driver.findElement(By.xpath("//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[2]/div/div/div/a[2]/div")).click();
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Manage Access')]")));
				
			driver.findElement(By.xpath("//*[@id='user-permissions-list']//a/span[2]")).click();
		
				clearAllCheckBox();
				
				homePage.clickAndWait("ManageAccessSave", "ManageAccessSave");
				
				if(homePage.isFieldVisible("WarningWindow")){
					homePage.verifyFieldExists("versionSelectorErrorMsg");
					Util.printInfo("Pop up message is displayed to User: Select Version");
					}else{
						EISTestBase.fail("Alert Message is not Displayed, When User try to save without selecting any Version.");
					}
				
				homePage.click("closeWarning");
				
				driver.findElement(By.xpath(".//*[@id='permissions-modal']/header/div")).click();
				
				Util.sleep(3000);
				homePage.refresh();
				wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
			
				System.out.println("****************setVersion End********************************");
		
	}
	
	public void clearAllCheckBox(){
		wb.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='1959754_ALL']")));
		driver.findElement(By.xpath(".//*[@id='1959754_ALL']")).click();
		Util.sleep(2000);
		wb.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='1959754_ALL']")));
		driver.findElement(By.xpath(".//*[@id='1959754_ALL']")).click();
	}
	
	public void setAllVersion(String ContractNumber){
		  //Click on Edit Manage Access link
						
		driver.findElement(By.xpath("//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[2]/div/div/div/a[2]/div")).click();
		try{
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Manage Access')]")));
			driver.findElement(By.xpath("//*[@id='user-permissions-list']//a/span[2]")).click();
		}catch(Exception e) {
			e.printStackTrace();
			EISTestBase.fail("Manage Access Modal is not Displayed correctly");
		}
	
		//Verify with out selecting any version
	
		driver.findElement(By.xpath("//*[@id='1959754_ALL']")).click();
		homePage.clickAndWait("ManageAccessSave", "ManageAccessSave");
		Util.sleep(3000);
		homePage.refresh();
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
        String VersionText = driver.findElement(By.xpath("//*[contains(@id,'110000464434')]/div[2]/div[1]/div/div/div/div/p")).getText();
		System.out.println(VersionText);
		if((VersionText.contains("5 Versions"))){
			Util.printInfo("Versions saved are successfully displayed in the Product and Services Page for Contract : "+ContractNumber);
		}else{
			EISTestBase.fail("Version  saved is not  reflected in the Products and Service page for Contract : "+ContractNumber);
		}
	

	}
	
	public void UnassignVersion(String ContractNumber){
		  //Click on Edit Manage Access link
		
		
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[2]/div/div/div/a[2]/div")));
			
		
		try{
			driver.findElement(By.xpath("//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[2]/div/div/div/a[2]/div")).click();
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Manage Access')]")));
			driver.findElement(By.xpath("//*[@id='user-permissions-list']//a/span[2]")).click();
			Util.sleep(2000);
		}catch(Exception e) {
			e.printStackTrace();
			EISTestBase.fail("Manage Access Modal is not Displayed correctly");
		}
	
		//Verify with out selecting any version
			clearAllCheckBox();
			

	}
	
	public void assignVersion(String ContractNumber , String Year ){
		
		UnassignVersion(ContractNumber);
		Util.sleep(2000);
		Util.printInfo("Selecting the CheckBox for version : " +Year);
		
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'1959754_"+Year+"')]")));
		try{
        driver.findElement(By.xpath("//*[contains(@id,'1959754_"+Year+"')]")).click();
		}catch(Exception e){
			System.out.println("Unable to select checkbox for " +Year);
			e.printStackTrace();
		}
        Util.printInfo("Successfully Checked version : " +Year);
   		Util.sleep(3000);
		homePage.click("ManageAccessSave");
		Util.sleep(8000);
		logoutMyAutodeskPortal();
		Util.sleep(8000);
		try {
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error while Logging into the application");
		}
		
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		homePage.refresh();
		Util.sleep(5000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		Util.printInfo("Assert : Version of the Contract Tooltip ");
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'110000464434')]/div[2]/div[1]/div/div/div/div/p")));
		String VersionText = driver.findElement(By.xpath("//*[contains(@id,'110000464434')]/div[2]/div[1]/div/div/div/div/p")).getText();
		assertTrue("Version "+Year+" saved is successfully displayed in the Product and Services Page", VersionText.contains(Year));
		
		
	}
	

	
	public void ManageAccessclearPermission(List<String> EmailList){
	//get all the email ids from manage access window
		System.out.println("****************ManageAccessclearPermission Start********************************");		
		for(String email : EmailList){
			String EmailAddress = email.trim();
			System.out.println("Email address for permissions are to be removed : "+EmailAddress);
	//Go to users page and unassign all the Products and services
						
			Util.printInfo("Clicking on Users page");
			homePage.clickAndWait("users", "UniqueUserAfterSearch");
			
     //Click on search field and populate email address
			Util.printInfo("Search for the User using Email Address");
			homePage.populateField("UserSearch", EmailAddress);
						
     //verify edit access link is displayed
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='results']/li/section[1]/div[5]/span")));
			
	//Click on edit access link
			driver.findElement(By.xpath("//*[@id='results']/li/section[1]/div[5]/span")).click();
			
	//check all the products & services
			wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='edit-access']/footer/div[3]/a[1]")));
			checkChecKBox("AssignAllBenifits", "AssignAllBenifits"); //Assign benefits 
			checkChecKBox("ContractAllAssignCB", "ContractAllAssignCB");
						
	//Uncheck all the  products  & services
			Util.sleep(6000);
			homePage.click("AssignAllBenifits");
			Util.sleep(6000);
			homePage.click("ContractAllAssignCB");
						
	//Save after unassigning all Products and Services
			homePage.clickAndWait("CPEditSave", "UniqueUserAfterSearch");
			System.out.println("****************ManageAccessclearPermission END********************************");	
	//Clear text 
			driver.findElement(By.xpath("//*[@id='search-users']/input")).clear();
			
		}
		
	
	}
	
	
	public void CMAssignPandS(String ContractNumber){
		
		homePage.click("ProductsandServices");
		homePage.refresh();
		System.out.println(ContractNumber);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[2]/div/div/div/a[2]/div")));
		
		System.out.println("*******************CMAssignPandS Start******************************************");
		driver.findElement(By.xpath("//*[contains(@id,'"+ContractNumber+"')]/div[2]/div[2]/div/div/div/a[2]/div")).click();
		System.out.println("****************CMAssignPandS Click ManageAccess********************************");
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Manage Access')]")));

	//Check if Contracts are assigned if true then Unassign all the contracts
			List <WebElement> EmailWB= driver.findElements(By.xpath(".//*[@id='user-permissions-list']//div[3]/p[2]"));						
	
		if(!(EmailWB.size()==0)){
			
			System.out.println("***************************CMAssignPandS 1***********************************");
	//Get list of all the email address for which Contracts are assigned
			setVersionBlank(ContractNumber);
			
				}else{
					System.out.println("**************************CMAssignPandS 2********************************");
					Util.printInfo("Closing Manage Access Window");
					homePage.clickAndWait("CloseButtonManageAccess","users");
					
				}
				
		String CMemailAddress= testProperties.getConstant("USER_NAME");
		Util.printInfo("Clicking on Users page");
		homePage.clickAndWait("users", "UniqueUserAfterSearch");
		
	//Click on search field and populate email address
		Util.printInfo("Search for the User using Email Address");
		homePage.populateField("UserSearch", CMemailAddress);
		//verify edit access link is displayed
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='results']/li/section[1]/div[5]/span")));
		
	//Click on edit access link
		driver.findElement(By.xpath("//*[@id='results']/li/section[1]/div[5]/span")).click();
		
	//check all the products & services
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='edit-access']/footer/div[3]/a[1]")));
		checkChecKBox("AssignAllBenifits", "AssignAllBenifits"); //Assign benefits 
		checkChecKBox("ContractAllAssignCB", "ContractAllAssignCB");
		
	//Save after unassigning all Products and Services
		homePage.clickAndWait("CPEditSave", "UniqueUserAfterSearch");	
		
		System.out.println("****************CMAssignPandS End********************************");
		Util.sleep(2000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='search-users']/input")));
		driver.findElement(By.xpath("//*[@id='search-users']/input")).clear();
		
		Util.sleep(5000);
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
