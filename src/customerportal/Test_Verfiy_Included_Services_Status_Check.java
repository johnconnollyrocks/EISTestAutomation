package customerportal;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import common.EISTestBase;
import common.Util;

public class Test_Verfiy_Included_Services_Status_Check extends CustomerPortalTestBase{

	public Test_Verfiy_Included_Services_Status_Check() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_ProductUpdatesClearAllFilters() throws Exception {
		Point coordinates;
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		
		driver.findElement(By.xpath("//article[contains(@id,'BDSADV')]//button[(@class)='btn details-toggle arrow-btn']")).click();
		Util.sleep(1000);
		driver.findElement(By.xpath("//*[text()='Included Services']/following-sibling::ul//li[contains(text(),'Bridge Design for InfraWorks')]/following-sibling::button[contains(text(),'More')]")).click();
		
//		WebElement web =driver.findElement(By.xpath("//div[@class='scrollbar']//div[@class='thumb']"));
		Actions action = new Actions(driver);
//		Point coordinates = web.getLocation();
		Robot robot = new Robot();
//		robot.mouseMove(coordinates.getX(), (int) (coordinates.getY() + 92.4625));
		robot.mouseMove(1162, 150);
		robot.mousePress(InputEvent.BUTTON1_MASK);
	    robot.mouseRelease(InputEvent.BUTTON1_MASK);
		action.click();
		
//		robot.mouseMove((int) 92.4625, coordinates.getY() + 165);
			
//		homePage.click("//article[contains(@id,'BDSADV')]//button[(@class)='btn details-toggle arrow-btn']");
		List<WebElement> services=driver.findElements(By.xpath("//article[1]//ul[contains(@class,'service-list')]//li"));
		List<String> ServicesFromUIList= new ArrayList<>();
		for(WebElement service:services){
			ServicesFromUIList.add(service.getText());
			Util.printInfo("Service:"+service.getText());
		}
		homePage.click("users");
		Util.sleep(3000);
		
		String email=testProperties.getConstant("USER_NAME");
		String editAccess=homePage.createFieldWithParsedFieldLocatorsTokens("editAccessForCM",email);
		homePage.click(editAccess);
//		driver.findElement(By.xpath("editAccess")).getText();
		List<WebElement> servicesFromEditAccess=driver.findElements(By.xpath("//span[contains(text(),'Products & Services')]//ancestor::article[1]//following-sibling::div[1]//article[1]//ul//li"));
		
		List< String > services1 = new ArrayList<>();
		for(int i=1;i<=servicesFromEditAccess.size();i++){
//		String serviceFromUI=driver.findElement(By.xpath("//span[contains(text(),'Products & Services')]//ancestor::article[1]//following-sibling::div[1]//article[1]//ul//li["+i+"]//p")).getText();
		String serviceFromUI=driver.findElement(By.xpath("//span[contains(text(),'Products & Services')]//ancestor::article[1]//following-sibling::div[1]//article[1]//ul//li["+i+"]//p")).getAttribute("innerHTML").trim();
//		services1.add(serviceFromUI);
		String checkBoxXpath=homePage.createFieldWithParsedFieldLocatorsTokens("checkBoxStatus", serviceFromUI);
		String status=homePage.getAttribute(checkBoxXpath,"checked");
		String serviceStatus=serviceFromUI+"_"+status;
		Util.printInfo("Service_status: "+serviceStatus);
		services1.add(serviceStatus);
		}
//		Util.printInfo("Size1-"+services1.size());
//		Util.printInfo("Size2-"+services.size());
		homePage.click("saveButton");
		Util.sleep(5000);
		
		homePage.refresh();
		Util.sleep(50000);
		homePage.click("ProductsandServices");
		Util.sleep(15000);
		driver.findElement(By.xpath("//article[contains(@id,'BDSADV')]//button[(@class)='btn details-toggle arrow-btn']")).click();
		Util.sleep(5000);
		driver.findElement(By.xpath("//*[text()='Included Services']/following-sibling::ul//li[contains(text(),'Bridge Design for InfraWorks')]/following-sibling::button[contains(text(),'More')]")).click();
//		WebElement web =driver.findElement(By.xpath("//div[@class='scrollbar']//div[@class='thumb']"));
		action = new Actions(driver);
//		Point coordinates = web.getLocation();
		robot = new Robot();
//		robot.mouseMove(coordinates.getX(), (int) (coordinates.getY() + 92.4625));
		robot.mouseMove(1162, 150);
		robot.mousePress(InputEvent.BUTTON1_MASK);
	    robot.mouseRelease(InputEvent.BUTTON1_MASK);
		action.click();
		for(String serviceItem:services1){
			
			String[] serviceList=serviceItem.split("_");
			String service=serviceList[0].toString();
			String serviceStatus=serviceList[1].toString();
			Util.printInfo("Service:"+service);
			for(int i=0;i<ServicesFromUIList.size();i++){
			String serviceFromUI=ServicesFromUIList.get(i);
			Util.printInfo("Service From UI:"+serviceFromUI);
			
			if(serviceFromUI.contains(service)){
				Util.printInfo("Is a Service:"+service);
				String statusForServiceXpath=homePage.createFieldWithParsedFieldLocatorsTokens("serviceStatusCheck", serviceFromUI);
				String status=homePage.getAttribute(statusForServiceXpath,"class");
				if(serviceStatus.equalsIgnoreCase("true")){
					assertEquals(status, "enabled");
				}
				else{
					assertEquals(status, "locked");	
					String xpath="//li[contains(text(),'"+serviceFromUI+"')]";
					WebElement web=driver.findElement(By.xpath(xpath));
					coordinates = web.getLocation();
					robot.mouseMove(coordinates.getX(), coordinates.getY()+65);
					Util.sleep(4000);
					if(driver.findElement(By.xpath(xpath)).isDisplayed()){
						Util.printInfo("Verifying Tool tip for Locked Services is working");
						homePage.verifyFieldExists("toolTipValidationForLockedServices");
						}else{
							EISTestBase.fail("Tool tip for Locked Services is not working");
						}
					robot.mouseMove(1,1);
					Util.sleep(2000);
					
				}
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