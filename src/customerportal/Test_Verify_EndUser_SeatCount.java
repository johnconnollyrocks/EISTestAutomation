package customerportal;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.Util;

public class Test_Verify_EndUser_SeatCount  extends CustomerPortalTestBase {
	public Test_Verify_EndUser_SeatCount () throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
 		
	}

	@Test
	public void Test_Verify_EndUser_SeatCounts() throws Exception {
		if(getEnvironment().equalsIgnoreCase("DEV")){
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_DEV") , testProperties.getConstant("PASSWORD_DEV"));
			}
			else if(getEnvironment().equalsIgnoreCase("STG")){
				loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME_STG") , testProperties.getConstant("PASSWORD_STG"));
			}
			Util.sleep(5000);
			CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
			String UserInfo=null;
			String TotalSeats=null;
			String EndUser=null;
			int TotalSeatsBeforeAssignments =0;
			int TotalSeatsAfterAssignments =0;
			int seats=0;
			loginPage = customerPortal.getLoginPage();
			homePage = customerPortal.getHomePage();
			mainWindow.select();
			Util.sleep(5000);
			
			homePage.click("users");
			Util.sleep(5000);
			int UsersList=driver.findElements(By.xpath(".//*[@id='results']/li")).size();
			
			for(int rownum=1;rownum<=UsersList;rownum++){
			
			String EachUser=homePage.createFieldWithParsedFieldLocatorsTokens("EachUser", String.valueOf(rownum));
			homePage.click(EachUser);
				
			String EndUserList=homePage.createFieldWithParsedFieldLocatorsTokens("UserRole", String.valueOf(rownum));
			UserInfo=homePage.getValueFromGUI(EndUserList);
			
			if(UserInfo.contains("End User")){
				Util.sleep(2000);
				EndUser=homePage.createFieldWithParsedFieldLocatorsTokens("EndUserEditAccess", String.valueOf(rownum));
				homePage.click(EndUser);
				Util.sleep(4000);
				List<WebElement>  contracts=driver.findElements(By.xpath(".//*[contains(@id,'product-list-link')]/a/span[2]"));
                for(WebElement contract:contracts){
                     String Contract1=contract.getText();
                     String ArrayOfContracts[]=Contract1.split("#");
                     String ContractSeats=homePage.createFieldWithParsedFieldLocatorsTokens("UserContracts", String.valueOf(ArrayOfContracts[1]));
         			 UserInfo=homePage.getValueFromGUI(ContractSeats);
         			int seatcount=Integer.valueOf(UserInfo);
         			TotalSeatsBeforeAssignments=TotalSeatsBeforeAssignments+seatcount;
         			
                }
             homePage.click("AssignAllBenifits");
             //inputBenefitsAssignAll
   			 homePage.clickAndWait("saveButton","editAccess");
   			 
   			 Util.sleep(2000);
   			 
   			 homePage.refresh();
   			 
   		//	 homePage.click("editAccess");
   			Util.sleep(2000);
			//String EndUser1=homePage.createFieldWithParsedFieldLocatorsTokens("EndUserEditAccess", String.valueOf(rownum));
			homePage.click(EndUser);
			
   			 
			 Util.sleep(2000);
			 
			 List<WebElement>  contractsAfterSave=driver.findElements(By.xpath(".//*[contains(@id,'product-list-link')]/a/span[2]"));
             for(WebElement contract:contractsAfterSave){

                  String Contract1=contract.getText();
                  String ArrayOfContracts[]=Contract1.split("#");
                  String ContractSeats=homePage.createFieldWithParsedFieldLocatorsTokens("UserContracts", String.valueOf(ArrayOfContracts[1]));
      			 UserInfo=homePage.getValueFromGUI(ContractSeats);
      			int seatcount=Integer.valueOf(UserInfo);
      			TotalSeatsAfterAssignments=TotalSeatsAfterAssignments+seatcount;
             }
             homePage.click("AssignAllBenifits");
             Util.sleep(2000);
             homePage.click("AssignAllBenifits");
             homePage.clickAndWait("saveButton","editAccess");
			 break;
			 }
			
		}
			Util.printInfo("TotalSeatsAfterAssignments"+String.valueOf(TotalSeatsAfterAssignments) );
			Util.printInfo("TotalSeatsBeforeAssignments"+String.valueOf(TotalSeatsBeforeAssignments) );
			
		//	assertTrue("",String.valueOf(TotalSeatsAfterAssignments).equalsIgnoreCase(String.valueOf(TotalSeatsBeforeAssignments)));
			assertFalse("Seat count after assignment of services or products should decrease",String.valueOf(TotalSeatsAfterAssignments).equalsIgnoreCase(String.valueOf(TotalSeatsBeforeAssignments)));
			
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