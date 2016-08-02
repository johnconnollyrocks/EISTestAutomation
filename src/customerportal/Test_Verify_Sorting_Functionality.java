package customerportal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_Sorting_Functionality extends CustomerPortalTestBase {
	public Test_Verify_Sorting_Functionality() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_Verify_OnlineMap() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(20000);
		String EachUser=null;
		String UserName=null;
		String StrTemp=null;
		String StrTemp1=null;
		
		
		
		homePage.click("reporting");
		Util.sleep(10000);
		homePage.click("byUsers");
		Util.sleep(10000);
		List<WebElement> ListofUsers1=driver.findElements(By.xpath("//td/label"));
//		List<String> ListofUsers=homePage.getValuesFromMultiSelect("ByUsersPageSorting");
		ArrayList<String> MultipleUsers = new ArrayList<String>();
		ArrayList<String> MultipleUsersafterSort = new ArrayList<String>();
		ArrayList<Integer> IndividualSort = new ArrayList<Integer>();
		ArrayList<Integer> IndividualAfterSort = new ArrayList<Integer>();
		Util.printInfo("UserNames before sorting are :: ");
		for(WebElement EachUser1 : ListofUsers1){
			StrTemp=EachUser1.getText();
			EachUser=String.valueOf(StrTemp);
			String arr[]=EachUser.split(" ");
			String ActualUsername=arr[0]+arr[1];
			MultipleUsers.add(EachUser.toLowerCase());
		}
		Collections.sort(MultipleUsers);

		Util.printInfo(MultipleUsers.toString());
		driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/thead/tr/th[1]/div/span[2]")).click();
		Util.printInfo("UserNames after sorting are :: ");
		List<WebElement> ListofUsersAfterSort=driver.findElements(By.xpath("//td/label"));
		for(WebElement UserName1 : ListofUsersAfterSort){
			StrTemp1=UserName1.getText();
			UserName=String.valueOf(StrTemp1);
			MultipleUsersafterSort.add(UserName.toLowerCase());
		}
		Util.printInfo(MultipleUsersafterSort.toString());
		driver.findElement(By.xpath(".//*[@id='usage-by-users']/table/thead/tr/th[1]/div/span[2]")).click();
		Util.sleep(4000);
		if(MultipleUsers.equals(MultipleUsersafterSort)){
			assertEquals("UserNames are sorted in Alphabatical order",MultipleUsers.toString(), MultipleUsersafterSort.toString());
		}else{
			EISTestBase.fail("UserNames are not sorted in order :: ");
		}
		String CC=null;
		Util.printInfo("Validating Individual Sort :: ");
		List<WebElement> IndividualCC=driver.findElements(By.xpath(".//*[@id='usage-by-users']/table/tbody/tr/td/label/ancestor::td/following-sibling::td[contains(@class,'individual')]/div/div[@class='fraction']/span[contains(@class,'numerator')]")); 
		for(WebElement CloudCredits : IndividualCC){
			CC=CloudCredits.getText();
			IndividualSort.add(Integer.valueOf(CC));
		}
		Collections.sort(IndividualSort);
		Util.printInfo("Individual CC after sorting :: ");
		driver.findElement(By.xpath("//*[@id='usage-by-users']/table/thead/tr/th[2]//span[4]")).click();
		List<WebElement> IndividualCCAfterSort=driver.findElements(By.xpath("//*[@id='usage-by-users']/table/tbody/tr/td/label/ancestor::td/following-sibling::td[contains(@class,'individual')]/div/div[@class='fraction']/span[contains(@class,'numerator')]"));
		for(WebElement CloudCredits : IndividualCCAfterSort){
			CC=CloudCredits.getText();
			IndividualAfterSort.add(Integer.valueOf(CC));
		}
		driver.findElement(By.xpath("//*[@id='usage-by-users']/table/thead/tr/th[2]//span[4]")).click();
		if(IndividualSort.equals(IndividualAfterSort)){
			assertEquals("Individual sort is showing correctly ",IndividualSort.toString(), IndividualAfterSort.toString());
		}else{
			EISTestBase.fail("Individual sort is not working properly :: ");
		}
		
		Util.printInfo("Verifying more or less Link in Byusers Page :: ");
		String ByusersPageMoreOption=homePage.createFieldWithParsedFieldLocatorsTokens("MoreLinkInByusersPage", testProperties.getConstant("CM_NAME"));
		String ByusersPageLessOption=homePage.createFieldWithParsedFieldLocatorsTokens("LessLinkInByusersPage", testProperties.getConstant("CM_NAME"));
		if(homePage.isFieldVisible(ByusersPageMoreOption)){
			homePage.verifyFieldExists(ByusersPageMoreOption);
			Util.printInfo("More Link exists and clicking on more link in Byusers page :: ");
			homePage.click(ByusersPageMoreOption);
			if(homePage.isFieldVisible(ByusersPageLessOption)){
				Util.printInfo("Clicking on Less link in Byusers page :: ");
				homePage.verifyFieldExists(ByusersPageLessOption);
				homePage.click(ByusersPageLessOption);
			}else{
				EISTestBase.fail("Less Link does not exists in Byusers page :: ");
			}
		}else{
			EISTestBase.fail("More Link does not exist in Byusers Page :: ");
		}
		
		
		
		
		homePage.click("ProductsandServices");
		
		Util.sleep(10000);
		homePage.refresh();
		Util.sleep(50000);
		System.out.println("*****************************************129****************************************************************");
		ArrayList<String> arr=new ArrayList<String>();
		ArrayList<WebElement> arr1=new ArrayList<WebElement>();
		List<WebElement> listofContracts=driver.findElements(By.xpath("//span/span[contains(text(),'11')]"));
		for(WebElement Contract : listofContracts ){
		String EachContract=Contract.getText().trim();
		System.out.println(EachContract);
		arr.add(EachContract);
		try{
		driver.findElement(By.xpath("//article[contains(@id,'"+EachContract+"')]/div[1]/button")).click();
		}catch(Exception e){
			e.printStackTrace();
			EISTestBase.fail("Xpath is not able to click on the ToggleButton : " +EachContract);
		}
		
		int i=0;
		Util.printInfo("Verifying more or less Links in Products and services page :: ");
		List<WebElement> ListOfProducts =driver.findElements(By.xpath("//article[contains(@class,'product')]//button[contains(text(),' More')]"));
		
		for(WebElement EachProduct : ListOfProducts){
			System.out.println(EachProduct);
			arr1.add(EachProduct);
			Util.sleep(5000);
			
		//	arr1.get(i).click();
			
			String MoreOptioninProductandServicesPage=homePage.createFieldWithParsedFieldLocatorsTokens("MoreLinkinProductsandServicesPage", arr.get(i));
			//String LessOptionInProductandServicesPage=homePage.createFieldWithParsedFieldLocatorsTokens("LessLinkinProductsandServicesPage", arr.get(i));
			Util.sleep(10000);
			if(homePage.verifyFieldExists("xMore")){
				homePage.verifyFieldExists(MoreOptioninProductandServicesPage);
				Util.printInfo("Clicking on more link in products and services page :: ");
				Util.sleep(1000);
				//homePage.click(MoreOptioninProductandServicesPage);
				//arr1.get(i).click();
				
				driver.findElement(By.xpath("//article[contains(@id,'"+EachContract+"')]/div[3]/div[2]/div/div/section[3]/ul/button[1]")).click();
				if(homePage.verifyFieldExists("xLess")){
					Util.printInfo("Clicking on Less link in Products and services page :: ");
					Util.sleep(1000);
					//homePage.click(LessOptionInProductandServicesPage);
					driver.findElement(By.xpath("//article[contains(@id,'"+EachContract+"')]/div[3]/div[2]/div/div/section[3]/ul/button[2]")).click();
					
				}
				
	
				break;
			}else{
				EISTestBase.fail("More option doesnot exists in products and services page :: ");
			}
		}
	
		}
		
		
		
		
		
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
