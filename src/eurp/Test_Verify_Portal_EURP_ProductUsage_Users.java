package eurp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DOMXmlParser;
import common.EISTestBase;
import common.SoapUIExampleTest;
import common.Util;

public class Test_Verify_Portal_EURP_ProductUsage_Users extends EURPTestBase{

	public Test_Verify_Portal_EURP_ProductUsage_Users() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_Verfy_ContractNumbers() throws Exception {
		Util.printInfo("Logging into customer portal ");
		ArrayList<String> SortedUserIdList=new ArrayList<String>();
		ArrayList<String> UnSortedUserIdList=new ArrayList<String>();
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		if(homePage.isFieldVisible("ManageUsers")){
			assertTrue("Successfully logged into EURP ", homePage.isFieldVisible("ManageUsers"));
		}else{
			EISTestBase.fail("Error occured while logging in ");
		}
		
		Util.printInfo("Clicking on reporting page");
		homePage.click("reporting");
		
		Util.printInfo("Verifying ProductUsage tab");
		
		if(homePage.isFieldVisible("ProductUsage")){
			Util.printInfo("ProductUsage tab pressent in reporting page, so clicking on ProductUsage tab ");
			homePage.click("ProductUsage");
			Util.sleep(40000);
			homePage.verifyFieldExists("SccussfullyNavigatedToProductUsage");
		}else{
			EISTestBase.fail("ProductUsage tab doesnot pressent in reporting page, so unable to click on ProductUsage");
		}
		
		Util.printInfo("Verifying ProductUsage Users sort");
		
		List<WebElement> Frames=driver.findElements(By.tagName("iframe"));
		
		 for (WebElement iframe : Frames) {
		        driver.switchTo().frame(iframe);
				if(homePage.isFieldVisible("TotalTokensUsed"))
					break;
				driver.switchTo().defaultContent();
		    }
		
		homePage.verifyFieldExists("TotalTokensUsed");
		Util.sleep(4000);
		homePage.verifyFieldExists("TotalTokensUsedValue");
		Util.sleep(4000);
		Util.printInfo("The total tokens used is / are :: "+homePage.getValueFromGUI("TotalTokensUsedValue"));
		homePage.click("ProductUsageSortBy");
		Util.sleep(4000);
		homePage.click("TokensUsedLowest");
		Util.sleep(4000);
		List<WebElement> NumberOfTokens = homePage.getMultipleWebElementsfromField("TokensUsed");
		
		for(WebElement EachProduct : NumberOfTokens){
			UnSortedUserIdList.add(EachProduct.getText());
		}
		
		String HighestSortedTokens=UnSortedUserIdList.toString().replace(", "," ");
		ArrayList <Integer> Arr = new ArrayList<Integer>();
		String[] NewList=HighestSortedTokens.trim().split(" ");
		
		for( String List : NewList ){
			int Temp=0;
			if(!(List.equals("[") && List.equals("]"))){
			Temp=Integer.valueOf(List);
			}
			Arr.add(Temp);
		}
		Collections.sort(Arr);
		
		System.out.println(Arr);
		
		List<WebElement> NumberOfTokensSort = homePage.getMultipleWebElementsfromField("TokensUsed");
		
		for(WebElement Product : NumberOfTokensSort){
			SortedUserIdList.add(Product.getText());
		}
		
		Collections.sort(SortedUserIdList);
		String LowestSortedTokens=SortedUserIdList.toString().replace(", ,","");
		System.out.println(LowestSortedTokens);
		
		Util.printInfo(" Verifying sorted list of Tokens Used ");
		
		assertTrue("Tokens Used are sorted successfully ", UnSortedUserIdList.equals(SortedUserIdList));
		
		
		logoutMyAutodeskPortal();
		Util.printInfo("Successfully logged out ");
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