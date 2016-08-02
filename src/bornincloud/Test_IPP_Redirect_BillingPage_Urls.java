package bornincloud;
//package bornincloud;
//
//import java.io.IOException;
//
//import org.apache.http.client.ClientProtocolException;
//import org.json.JSONException;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
///**
// * Test class - Test_IPP_Redirect_BillingPage
// * @author Brijesh Chavda
// * @version 1.0.0
// */
//public final class CopyOfTest_IPP_Redirect_BillingPage_Urls extends BornInCloudTestBase {
//	
//	public CopyOfTest_IPP_Redirect_BillingPage_Urls() throws IOException {
//		super("Browser",getAppBrowser());
//		// TODO Auto-generated constructor stub
//	}
//	
//	@Before
//	public void setUp() throws Exception {
//		launchIPP("https://depot-dev.autodesk.com/service/ipp/v1/ippredirect?contextId=99999&userId=11111&lang=enUS&country=USA&email=test@test.com");
//	}
//
//	@Test
//	public void Test_REST_GetEntitlement_UserId_Method() throws ClientProtocolException, IOException, JSONException, InterruptedException {		
//		Boolean isPresent = driver.findElements(By.xpath("//*[@id=\"bic-template\"]/div/div[2]/div[2]/div/div[1]/div[1]")).size() > 0;
//		String URL = driver.getCurrentUrl();
//		if (isPresent){
//			System.out.println("\n" + URL +"\n");
//			System.out.println("Choose Your Plan window exists : IPP Redirect works");
//		}
//		driver.findElement(By.xpath("//*[@id=\"bic-template\"]/div/div[2]/div[2]/div/div[1]/div[1]")).click();
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
//		driver.findElement(By.cssSelector("#auto-renew-checkbox")).click();
//		
//		//WebDriverWait wait = new WebDriverWait(driver, 20000);	
//		Thread.sleep(20000);
//		
//		driver.findElement(By.cssSelector("#terms > div.button-next-container > div.button-next.billing-page")).click();
//		
//		Boolean issubmitorderPresent = driver.findElements(By.cssSelector("#order_summary > div.button-next-container.submitOrderBtn > div.button-next.order-summary-page > span")).size() > 0;
//		if (issubmitorderPresent){
//			System.out.println("\n" + URL +"\n");
//			System.out.println("Successfully able to navigate to Submit Order Page");
//		}
//	}
//	@After
//	public void tearDown() throws Exception {
//		// Close the browser. Call stop on the WebDriverBackedSelenium instance
//		// instead of calling driver.quit(). Otherwise, the JVM will continue
//		// running after the browser has been closed.
//		driver.quit();
//
//		// TODO Figure out how to determine if the test code has failed in a
//		// manner other than by EISTestBase.fail() being called. Otherwise,
//		// finish() will always print the default passed message to the console.
//		
//		//finish();
//	}
//}
//
