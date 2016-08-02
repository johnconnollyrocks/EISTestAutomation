package bornincloud;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Test class - Test_IPP_Redirect_BillingPage
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public final class Test_IPP_Redirect_BillingPage extends BornInCloudTestBase {
	
	public Test_IPP_Redirect_BillingPage() throws IOException {
		super("Frontend","Browser",getAppBrowser());
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void setUp() throws Exception {
		//launchIPP("https://depot-dev.autodesk.com/service/ipp/v1/ippredirect?userId=UGR8LDV4QW3P&country=US&language=en");
		String url=getBaseURL()+"userId="+ testProperties.getConstant("USER_ID")+"&country="+ testProperties.getConstant("COUNTRY")+"&language="+ testProperties.getConstant("LANGUAGE");
		launchIPP(url);
	}

	@Test
	public void Test_REST_GetEntitlement_UserId_Method() throws ClientProtocolException, IOException, JSONException, InterruptedException {		
		Boolean isPresent = driver.findElements(By.cssSelector("#\\34 619256 > div.title")).size() > 0;
		String URL = driver.getCurrentUrl();
		if (isPresent){
			System.out.println("\n" + URL +"\n");
			System.out.println("Choose Your Plan window exists : IPP Redirect works");
			driver.findElement(By.cssSelector("#\\34 619256 > div.title")).click();
		}
		//driver.findElement(By.xpath("//*[@id=\\\"bic-template\\\"]/div/div[2]/div[3]/div/div[1]/div[1]")).click();
		driver.findElement(By.cssSelector("#choosePlanAccordian > div.buyIt.active > div")).click();	
		String newURL = driver.getCurrentUrl();
		String updatedURL = newURL+"&DOTEST=1";
		Thread.sleep(15000);
		open(updatedURL);
//		launchIPP(URL+"&DOTEST=1");
		
		String customerName = driver.findElement(By.cssSelector("#nameoncard")).getAttribute("value");
		
		compareStrings("John Test", customerName);
		
		String cardNumber = driver.findElement(By.cssSelector("#tiCNumber")).getAttribute("value");
		compareStrings("4111111111111111", cardNumber);
		
		String expiDateMonth = driver.findElement(By.cssSelector("#cbExpMounth")).getAttribute("value");
		compareStrings("04", expiDateMonth);
		
		String expYear = driver.findElement(By.cssSelector("#cbExpYear")).getAttribute("value");
		compareStrings("2015", expYear);
		
		String securityCode = driver.findElement(By.cssSelector("#tiCVV")).getAttribute("value");
		compareStrings("123", securityCode);
		
		String billingAdress = driver.findElement(By.cssSelector("#address")).getAttribute("value");
		compareStrings("any address", billingAdress);
		
		String city = driver.findElement(By.cssSelector("#city")).getAttribute("value");
		compareStrings("any city", city);
		
		String state = driver.findElement(By.cssSelector("#state")).getAttribute("value");
		compareStrings("California", state);
		
		String zip = driver.findElement(By.cssSelector("#zipcode")).getAttribute("value");
		compareStrings("94444", zip);
		
//		String country = driver.findElement(By.cssSelector("#nameoncard")).getText();
//		compareStrings("John Test", customerName);
		
//		driver.findElement(By.cssSelector("#nameoncard")).sendKeys("any name");
//		driver.findElement(By.cssSelector("#tiCNumber")).sendKeys("4111111111111111");
//			
//		Select selectmonth = new Select(driver.findElement(By.cssSelector("#cbExpMounth")));
//		selectmonth.selectByVisibleText("01 (January)");
//		
//		Select selectyear = new Select(driver.findElement(By.cssSelector("#cbExpYear")));
//		selectyear.selectByVisibleText("2015");
//
//		driver.findElement(By.cssSelector("#tiCVV")).sendKeys("111");
//		driver.findElement(By.cssSelector("#address")).sendKeys("any adress");
//		driver.findElement(By.cssSelector("#city")).sendKeys("any city");
//		
//		Select selectstate = new Select(driver.findElement(By.cssSelector("#state")));
//		selectstate.selectByVisibleText("California");
//		
//		driver.findElement(By.cssSelector("#zipcode")).sendKeys("11111");		
		driver.findElement(By.cssSelector("#auto-renew-checkbox")).click();
		String emailadress = driver.findElement(By.cssSelector("#bill_email > td.order__checkout__form__input > div")).getText();
		compareStrings("test@test.com", emailadress);
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#pagecode > div.button-next-container.billing-page > button.button-next.billing-page.enabled")).click();
		
						
		//WebDriverWait wait = new WebDriverWait(driver, 20000);	
		Thread.sleep(5000);	
		
		 
		Boolean issubmitorderPresent = driver.findElements(By.cssSelector("#order_summary > div.button-next-container.order-summary-page.enabled.disabled > button.button-next.enabled.order-summary-page")).size() > 0;
		
		if (issubmitorderPresent){
			System.out.println("\n" + URL +"\n");
			System.out.println("Successfully able to navigate to Submit Order Page");
			driver.findElement(By.cssSelector("#order_summary > div.button-next-container.order-summary-page.enabled.disabled > button.button-next.enabled.order-summary-page")).click();
		}
		
		Boolean iscloseorderPresent = driver.findElements(By.cssSelector("#orderCompleteCloseBtn")).size() > 0;
		
		Thread.sleep(5000);	
		
		if (iscloseorderPresent){
			System.out.println("\n" + URL +"\n");
			System.out.println("Successfully able to submit the order");
			driver.findElement(By.cssSelector("#orderCompleteCloseBtn")).click();
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
		
		//finish();
	}
}

