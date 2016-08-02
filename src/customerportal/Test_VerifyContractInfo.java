package customerportal;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Util;

/**
 * Test class - Test_VerifyDwnldOtherProductsWindowURL
 * 
 * @author Vithyusha Revuri
 * @version 1.0.0
 */
public final class Test_VerifyContractInfo extends CustomerPortalTestBase {
	public Test_VerifyContractInfo() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyDwnldOtherProductsWindowURL() throws Exception {
		driver.manage().deleteAllCookies();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		WebDriverWait wb1=new WebDriverWait(driver,30000);
		wb1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[contains(text(),'All Products')]")));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
//		Util.printInfo("Get the number of users from users page and compare with Products and services page");
//		boolean compare = compareNoOfUsers(driver,driver.findElement(By.xpath(".//*[@id='383-94427757']//label[contains(text(),'Users Assigned')]/following-sibling::span")).getText());
//		Util.printInfo("number of users Same : "+compare);
//		assertTrue("No of Users should match", compare);
		
		
		Util.printInfo("Clicking on the first contract toggle drawer button");
		homePage.clickAndWait("firstButtonToggleDrawer" , "firstViewAllInstalledProductsLink");
		Util.printInfo("Validating whether 'Seats,Users,Version,Language,Serial-Number,Product Key,Contract,Contract type' values are populated or not");
		Util.sleep(5000);
		homePage.verify();
		homePage.clickAndWait("secondButtonToggleDrawer" , "secondProductImage");
		homePage.clickAndWait("thirdButtonToggleDrawer" , "storageDescription");
		homePage.clickAndWait("fourthButtonToggleDrawer" , "autocad360Description");
		Util.printInfo("Validating products images,services description are present or not");
		homePage.verifyInstance("AFTER_TOGGLE_BUTTON");
		mainWindow.select();
		String winHandleBefore = driver.getWindowHandle();
	   	String newURL = testProperties.getConstant("AUTOCAD_360_URL");
		
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = capabilities.getBrowserName();
      
		
		//************************To handle Safari browser scrollBar issue******************************************
		
		if(!browserName.equalsIgnoreCase("safari")) {
			
			Util.printInfo("*******Browser is not SAFARI******");
			
			}
			else{
				try { 
					 homePage.refresh();
		        	 Util.sleep(30000);
		        	 homePage.clickAndWait("firstButtonToggleDrawer" , "firstViewAllInstalledProductsLink");
		        	 Util.printInfo("Validating whether 'Seats,Users,Version,Language,Serial-Number,Product Key,Contract,Contract type' values are populated or not");
		        	 Util.sleep(5000); 
		
		         	} catch (Exception e){
		        	 e.printStackTrace();		
		         	}
				}
		
		
		homePage.click("firstViewAllInstalledProductsLink");
		
		//************************************************************************************************************	
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
		
		String URL_New = driver.getCurrentUrl();
		Util.printInfo("Validating the view product link navigation");
		assertEquals(URL_New, newURL);		
		mainWindow.select();
		
		//verify Install Now,Download Now, Browser Download dropdown functionality
		Util.printInfo("verify Install Now,Download Now, Browser Download dropdown functionality");
		
		//*********************************InstallButtonHandler for Safari**********************************************
		
		if(!browserName.equalsIgnoreCase("safari")) {
			
			homePage.clickAndWait("firstContractInstallNowButton", "languageSelectDropDown");
			
			}
			else{	
		
		         try { String GetXpath ="document.getElementByXPath = function(sValue){ var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0) { return a.snapshotItem(0); } };document.getElementsByXPath = function(sValue){ var aResult = new Array();var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);for ( var i = 0 ; i < a.snapshotLength ; i++ ){aResult.push(a.snapshotItem(i));} return aResult;};";
		        	 
		        	 String InstallNowButton = "var click_ev = document.createEvent(\"MouseEvents\"); click_ev.initEvent(\"click\", true, true); document.getElementByXPath(\"//ul[@class='dropdown-menu']//a[contains(text(),'Install Now')]\").dispatchEvent(click_ev);";
		         JavascriptExecutor js = (JavascriptExecutor) driver;
		         js.executeScript(GetXpath); 
		         js.executeScript(InstallNowButton); 
		         Util.sleep(5000);
		
		         } catch (Exception e){
		        	 e.printStackTrace();
		
		         }
		}
		
		homePage.verifyFieldExists("autodeskRevitLTInstallNow");
		homePage.verifyFieldExists("autodeskAutoCADLTInstallNow");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Install Now functionality");
		
		
		//*************************************************************************	
		
		if(!browserName.equalsIgnoreCase("safari")) {
			
			homePage.clickAndWait("firstContractDownloadNowButton", "languageSelectDropDown");
			
			}
			else{	
		
		
		       try { String GetXpath ="document.getElementByXPath = function(sValue){ var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0) { return a.snapshotItem(0); } };document.getElementsByXPath = function(sValue){ var aResult = new Array();var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);for ( var i = 0 ; i < a.snapshotLength ; i++ ){aResult.push(a.snapshotItem(i));} return aResult;};"; 
		    	     String DownloadButton = "var click_ev = document.createEvent(\"MouseEvents\"); click_ev.initEvent(\"click\", true, true); document.getElementByXPath(\"//ul[@class='dropdown-menu']//a[contains(text(),'Download Now')]\").dispatchEvent(click_ev);";
		       			JavascriptExecutor js = (JavascriptExecutor) driver;
		       		 js.executeScript(GetXpath); 
		       		 js.executeScript(DownloadButton); 		
		       			Util.sleep(5000);
		
		       } catch (Exception e){
		    	   e.printStackTrace();
		    	   }
		
			}
		
		
		homePage.verifyFieldExists("autodeskRevitLTDownloadNow");
		homePage.verifyFieldExists("autodeskAutoCADLTDownloadNow");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Download Now functionality");
		
		//*************************************************************************
		
		if(!browserName.equalsIgnoreCase("safari")) {
			
			homePage.clickAndWait("firstContractBrowserDownloadButton", "languageSelectDropDown");
					
					}
					else{	
				
				
				try { String GetXpath ="document.getElementByXPath = function(sValue){ var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0) { return a.snapshotItem(0); } };document.getElementsByXPath = function(sValue){ var aResult = new Array();var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);for ( var i = 0 ; i < a.snapshotLength ; i++ ){aResult.push(a.snapshotItem(i));} return aResult;};";
					String DownloadButton = "var click_ev = document.createEvent(\"MouseEvents\"); click_ev.initEvent(\"click\", true, true); document.getElementByXPath(\"//article[1]//div[@class='right-col']//a[contains(text(),'Browser Download')]\").dispatchEvent(click_ev);";
					JavascriptExecutor js = (JavascriptExecutor) driver;
					 js.executeScript(GetXpath);
				    js.executeScript(DownloadButton); 
				    Util.sleep(5000);
				
				} catch (Exception e){
					e.printStackTrace();
				
				
				}
				
					}
		
		//*************************************************************************	
		homePage.verifyFieldExists("autodeskRevitLTBrowserDownload");
		homePage.verifyFieldExists("autodeskAutoCADLTBrowserDownload");
		homePage.clickAndWait("modelDialogClose", "firstContractSeats");
		Util.printInfo("verified Browser Download functionality");
		logoutMyAutodeskPortal(); 
		Util.sleep(2000);
			
		
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
