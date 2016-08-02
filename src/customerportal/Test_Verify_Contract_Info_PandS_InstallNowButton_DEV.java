package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.EISTestBase;
import common.Util;

/**
 * Test class - Test_VerifyInstallNowButton
 * Scenario::	P & S Page – View Included Products/Services, Storage, Install Now / Download / Browser Download Options
 * @author R.Puraj
 * @version 1.0.0
 */
public final class Test_Verify_Contract_Info_PandS_InstallNowButton_DEV extends CustomerPortalTestBase {
	public Test_Verify_Contract_Info_PandS_InstallNowButton_DEV() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyInstallNowButton() throws Exception {
		String getStatus;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb=new WebDriverWait(driver,30000);
		wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		Util.printMessage("\"All Products & Services\" page is displayed");
	
		
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		String ContractNumber=getMaxConVersion();
		IncludeProduct(ContractNumber);
		IncludeService(ContractNumber);
		InstallNow();
		DownloadNow();
		BrowserDownload();
		//Storage();  //Need to add assign and unassign functionalities
		
		
		logoutMyAutodeskPortal();   
	}
	
	

	public String getMaxConVersion(){

		
				String MaxContractNumber = null;
				
				//Get all the contract numbers
				List<WebElement> Products=driver.findElements(By.xpath(".//*[contains(@id,'11')]/div[2]/div[1]/div/div/div/span/span"));
				Util.printInfo("Number of Contracts displayed :"+Products.size());
				if(!(Products.size()==0)){
				//iterate thru individual contract numbers to find the Max version
				for(WebElement Version: Products){
					
					String ContractNumber=Version.getText().trim();
					MaxContractNumber = ContractNumber;
					try{
					driver.findElement(By.xpath("//article[contains(@id,'"+ContractNumber+"')]//button[contains(text(),'view product details')]"));//.click();
					}catch(Exception e){
						e.printStackTrace();
				
					}
					break;
					}			
				}else{
					EISTestBase.fail("/|\\ CM user does not have any Contracts to verify");
				}
				return MaxContractNumber;
					}

	
	public void IncludeProduct(String ContractNumber){
		driver.findElement(By.xpath("//article[contains(@id,'"+ContractNumber+"')]//button[contains(text(),'view product details')]")).click();
		Util.printInfo("Verify IncludeProducts is Displayed ");
		try{
		
		String ActualIncludeProducts =driver.findElement(By.xpath("//article[contains(@id,'"+ContractNumber+"')]//h4[contains(text(),'Product')]")).getText();
		String ExceptedIncludeProducts="Product Details";
		assertEquals(ActualIncludeProducts, ExceptedIncludeProducts);
		
		}catch(Exception e){
			e.printStackTrace();
			EISTestBase.fail("IncludeProducts is not Displayed for Contract # :"+ContractNumber);
		}
		String winHandleBefore = driver.getWindowHandle();
		
		String newURL = testProperties.getConstant("VIEWALLINSTALLEDPRODUCTSURL");
		Util.printInfo("Clicking on View all installed Products button");
		driver.findElement(By.xpath("//*[contains(@id,'"+ContractNumber+"')]//p/a")).click();
		for(String winHandle : driver.getWindowHandles()){
			 driver.switchTo().window(winHandle);
		}
		Util.printInfo("Verifying whether the View All installed products Link has opened a new window or not");
		String URL_New = driver.getCurrentUrl();
		String newURL1="http://www.autodesk.com/products/autocad-lt/overview";
		assertEquals(URL_New, newURL1);	
		driver.close();
		driver.switchTo().window(winHandleBefore);
		
		
	}
	
	public void IncludeService(String ContractNumber){
		//driver.findElement(By.xpath("//article[contains(@id,'"+ContractNumber+"')]//button[contains(text(),'view product details')]")).click();
		
		Util.printInfo("Verify IncludeServices is Displayed ");
		try{
		WebElement IncludeServices =driver.findElement(By.xpath("//article[contains(@id,'"+ContractNumber+"')]//h4[contains(text(),'Included Services')]"));
		IncludeServices.isDisplayed();
		}catch(Exception e){
			e.printStackTrace();
			EISTestBase.fail("Include Services is not Displayed for Contract # :"+ContractNumber);
		}
		String ActualProductName = driver.findElement(By.xpath("//*[contains(@id,'"+ContractNumber+"')]//section[3]/ul/li[1]")).getText();
		String ExpectedProductName="Autodesk® 360 Storage";
		
		assertEquals(ActualProductName, ExpectedProductName);
		//homePage.verify();
	
		
	}
	
	public void InstallNow(){
		//verify Install Now functionality
				Util.printInfo("verify Install Now functionality");
				homePage.clickAndWait("firstContractInstallNowButton", "languageSelectDropDown");
				homePage.verifyFieldExists("autocadLTInstallNow");
				homePage.clickAndWait("modelDialogClose", "firstContractSeats");
				Util.printInfo("verified Install Now functionality");
	}
	
	public void DownloadNow(){
		Util.printInfo("verify Download Now functionality");
		homePage.clickAndWait("firstContractDownloadNowButton", "languageSelectDropDown");
		homePage.verifyFieldExists("autocadLTDownloadNow");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Download Now functionality");
	}
	
	public void BrowserDownload(){
		Util.printInfo("verify Browser Download dropdown functionality");
		homePage.clickAndWait("firstContractBrowserDownloadButton", "languageSelectDropDown");
		homePage.verifyFieldExists("autocadLTBrowserDownload");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Browser Download functionality");
	}

	public void Storage(){
		
		Util.printInfo("verify Storage assign and unassigned functionality");
		
		homePage.clickAndWait("ProductsandServices", "ProductsandServices");
		String AvailableGB= null;
		driver.findElement(By.xpath(".//*[@id='mydesignstorage']/div[1]/button")).click();
		String UsedGB = driver.findElement(By.xpath(".//*[@id='mydesignstorage']/div[2]/div[2]/div/div/div[2]/span")).getText();
		
		getContractManager();
		
		//Assign all Products
		AssginAllProductandServices();
		AvailableGB= driver.findElement(By.xpath(".//*[@id='mydesignstorage']/div[2]/div[2]/div/div/div[3]/span")).getText();
		assertTrueCatchException("After Assigning All Products and Services  Storage is 5 GB : ",assertEquals(AvailableGB, "25"));
		
		getContractManager();
		//Un-assign all Products
		UnAssginAllProductandServices();
		AvailableGB= driver.findElement(By.xpath(".//*[@id='mydesignstorage']/div[2]/div[2]/div/div/div[3]/span")).getText();
		assertEquals(AvailableGB, "5");
		assertTrueCatchException("After unassigning All Products and Services Storage is 5 GB : ",assertEquals(AvailableGB, "5"));
	}
	
	public void assertTrueCatchException(String message , boolean expected){
		try{
		assertTrue(message,expected);
		}catch(Exception e){
		Util.printInfo(message+"--FAILED");
		}
		}
	
	public void getContractManager(){
		
WebDriverWait wb=new WebDriverWait(driver,30000);
Util.printInfo("Clicking on Users Page");
homePage.click("users");		
wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='results']/li")));


List <WebElement> UserCount = driver.findElements(By.xpath("//*[@id='results']/li//input"));

for (int i=0; i<UserCount.size(); i++){
//Find the ContractManager User
driver.findElement(By.xpath("//*[@id='results']/li["+i+"]/section[1]/div[2]")).click();
String ActualUser = driver.findElement(By.xpath(".//*[@id='results']/li[i]/section[2]/div/div[2]/div/div/p[1]")).getText();
String CMUser ="Contract Manager";

if(ActualUser.equalsIgnoreCase(CMUser)){
Util.printInfo("Contract Manager User is displayed");
driver.findElement(By.xpath(".//*[@id='results']/li["+i+"]/section[1]/div[5]/span")).click();
wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='edit-access']/footer/div[3]/a[1]")));

break;

}

if(i==UserCount.size()-1){
	EISTestBase.fail("Unable to Find the ContratManager User");
}
driver.findElement(By.xpath("//*[@id='results']/li["+i+"]/section[1]/div[2]")).click();
}
	}
	
	public void AssginAllProductandServices(){
		
		//Unable to get the Xpath due to Edit access issue
		
	}
	
	public void UnAssginAllProductandServices(){
		//Unable to get the Xpath due to Edit access issue
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

