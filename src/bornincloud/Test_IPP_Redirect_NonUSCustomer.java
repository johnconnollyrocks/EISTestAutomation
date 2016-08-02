package bornincloud;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

/**
 * Test class - Test_IPP_Redirect_BillingPage
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_IPP_Redirect_NonUSCustomer extends BornInCloudTestBase {
	
	public Test_IPP_Redirect_NonUSCustomer() throws IOException {
		//super("Frontend","Browser",getAppBrowser());
		super("browser");
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void setUp() throws Exception {
		String USERNAME = null;
		String COUNTRY = null;
		String LANGUAGE = null;
		String currencyType = null;	
		String url = null;
							
		if(getEnvironment().equalsIgnoreCase("DEV")){
			 USERNAME = testProperties.getConstant("USER_ID");
			 COUNTRY = testProperties.getConstant("COUNTRY");
			 LANGUAGE = testProperties.getConstant("LANGUAGE");
			 currencyType=testProperties.getConstant("currencyType");
			 url=getBaseURL()+"userId="+ USERNAME+"&country="+ testProperties.getConstant("COUNTRY")+"&lang="+ testProperties.getConstant("LANGUAGE");	
			}
		
			else if(getEnvironment().equalsIgnoreCase("STG")){
				 USERNAME = testProperties.getConstant("USER_ID_STG");			 
				 COUNTRY= testProperties.getConstant("COUNTRY_STG");
				 LANGUAGE = testProperties.getConstant("LANGUAGE_STG");
				 currencyType=testProperties.getConstant("currencyType_STG");
				 String hash = getclicHash(USERNAME, COUNTRY ,LANGUAGE);
				 url=getBaseURL()+"userId="+ USERNAME+"&country="+ testProperties.getConstant("COUNTRY")+"&lang="+ testProperties.getConstant("LANGUAGE") + hash ;				 
			}
				
		launchIPP(url);
		//launchIPP("https://depot-dev.autodesk.com/service/ipp/v1/ippredirect?contextId=&userId=96GSM8PFT98N&lang=en&country=RU");
	}

	@Test
	public void Test_IPP_Redirect_NonUSCustomer_Method() throws ClientProtocolException, IOException, JSONException, InterruptedException, MetadataException {		
		
		
		//verify visit the store button existence
		
		if(homePage.checkFieldExistence("visitTheStoreButton")) {
			String URL = driver.getCurrentUrl();
			System.out.println("\n" + URL +"\n");
			System.out.println("Non-BIC catalog page opens : IPP Redirect works");
			Util.sleep(2000);
		}
		
		
				
		Boolean isPresent = driver.findElements(By.cssSelector("#nonCommercialTxt")).size()!=0;
				
		if(isPresent){
			driver.findElement(By.cssSelector("#nonCommercialTxt")).click();
			Util.sleep(1000);
		}
				
		/*if(homePage.checkFieldExistence("nonCommercialTxt")) {
			homePage.click("nonCommercialTxt");
			Util.sleep(2000);
		}*/
		
		//verify country selected
		  
		 Select countryDrpdown = new Select(driver.findElement(By.id("countryList")));
		 String country = countryDrpdown.getFirstSelectedOption().getText();
		 		 		 
		 assertTrueCatchException("Country Selected validation passed both the page",assertEquals(country.trim(), testProperties.getConstant("selectedCountry")));
		 
		
		 //verify noncommerical options
		 
		 if (homePage.checkIfElementExistsInPage("studentOption", 10)){
				assertTrueCatchException("StudentOption available in NonCommercial page", homePage.checkIfElementExistsInPage("studentOption", 10));
			}else{
				EISTestBase.fail("StudentOption is not found upon clicking NonCommercialUse Link");
				}
				 
		 if (homePage.checkIfElementExistsInPage("enthusiastOption", 10)){
				assertTrueCatchException("enthusiastOption available in NonCommercial page", homePage.checkIfElementExistsInPage("enthusiastOption", 10));
			}else{
				EISTestBase.fail("enthusiastOption is not found upon clicking NonCommercialUse Link");
				}
		 
		 	
		 
		 driver.findElement(By.cssSelector("#nonCommercialTxt")).click();
		 Thread.sleep(3000);
		 
		
		//verify visitTheStore button functionality	
			homePage.click("visitTheStoreButton");
			Util.sleep(5000);
			
			/*homePage.getAllWindowHandles();
			
			Set<String> windows = driver.getWindowHandles();
			Iterator<String> iter = windows.iterator();
			
			String mainWindow = iter.next();
			String newWindow = iter.next();
			
			driver.switchTo().window(newWindow);
						
			
			String newURL = driver.getCurrentUrl();
			assertTrueCatchException("Redirect works on clicking visitTheStoreButton", assertEquals(newURL,homePage.getConstant("expURL")));*/
			
	}
	
			public void assertTrueCatchException(String message , boolean expected){
				try{
					assertTrue(message,expected);
				}catch(Exception e){
					Util.printInfo(message+"--FAILED");
				}
				
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



