package customerportal;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.EISTestBase;
import common.Util;

public class Test_Verify_Filters_On_User_Management_Tab extends CustomerPortalTestBase {
	public Test_Verify_Filters_On_User_Management_Tab() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyFilters_On_User_Management_Tab() throws Exception {
		String xpath;
		String xpath1;
		String totalCount;
		String actualCount;
		boolean moreIsdisplayed;
		String filterCount;
		
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		Util.sleep(20000);
		homePage.click("users");
		homePage.click("filters");
		homePage.click("clearFilters");
		Util.sleep(5000);
		
		//Filter Check when clicked on text instead of checkbox
		xpath="//div[contains(@class,'viewport')]//ul[@role='presentation']/li[1][@class='up-facet']//ul/li[1]/span";
		findElementByXpath(xpath);
//		homePage.click("firstFilterCheckBox");
		Util.sleep(5000);
		filterCount=homePage.getValueFromGUI("appliedFiltersCount");
		assertEquals(filterCount, "1");
		//Verify Applied Filter visibility outside
		String appliedFilter=driver.findElement(By.xpath("//span[contains(text(),'Applied Filters:')]//following-sibling::ul//li[1]")).getAttribute("data-facet-option");
		String filterOnFilterList=driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li[1][@class='up-facet']//ul/li[1]/input")).getAttribute("data-facet-option");
		assertEquals(filterOnFilterList, appliedFilter);
		homePage.click("clearAllOnFilters");
		Util.sleep(5000);
		homePage.verifyFieldNotVisible("appliedFiltersCount");
		
				
		//Verify filter count, total user count, filter user count next on right side, total results count in filter panel
		xpath="//div[contains(@class,'viewport')]//ul[@role='presentation']/li[1][@class='up-facet']//ul/li[1]/span";
		findElementByXpath(xpath);
//		homePage.click("firstFilterCheckBox");
		//Count Results in Page
		Util.sleep(5000);
		List<WebElement> results=driver.findElements(By.xpath("//ul[@id='results']/li"));
		Util.sleep(5000);
		int resultsFetched=results.size();
		//Get Data from Results Count. Eg:3 results
		/*actualCount=homePage.getValueFromGUI("resultsCount");*/
		actualCount=getFilterResultsCount();
		//Get Data from Filter Results. Eg:Filters (3 results)
		filterCount=homePage.getValueFromGUI("filterResultsCountOnFiltersTab");
		String filterResultsCount=getFilterResultsCount(filterCount);
		//Get Data from Filter Row. Eg:Autodesk® 360 Storage (3)
		String countOnItem=driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li[1][@class='up-facet']//ul/li[1]/input")).getAttribute("data-facet-option");
		totalCount=getCount(countOnItem);
		//Get Data from Actions Tab
		homePage.click("actionsOnUserManagementTab");
		Util.sleep(5000);
		String countOnExportLink=homePage.getValueFromGUI("exportCSVDataValue");
		Util.sleep(5000);
		assertEquals(countOnExportLink.trim(), Integer.toString(resultsFetched));
		assertEquals(actualCount,Integer.toString(resultsFetched));
		assertEquals(filterResultsCount,Integer.toString(resultsFetched));
		assertEquals(totalCount,Integer.toString(resultsFetched));
				
				
		//Verify Filter Check for all the filters
		homePage.click("clearAllOnFilters");
		Util.sleep(5000);
		List<WebElement> facetCount=driver.findElements(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li[@class='up-facet']"));
		Util.printInfo("Facet Count="+ facetCount.size());
		int i=1;
		for(WebElement facet:facetCount){
		String att=driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//div")).getAttribute("class");
		Util.printInfo("att="+ att);
		if(att.equalsIgnoreCase("up-facet-header collapsed")){
			driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//div//span[1]")).click();
			
			
		}
		List<WebElement> facets=driver.findElements(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//ul/li"));
		Util.printInfo("Facets ="+ facets.size());
		int j=1;
		for(WebElement eachFacet:facets){
			xpath="//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//ul/li["+j+"]/input";
			findElementByXpath(xpath);
			Util.sleep(2000);
			String count=driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//ul/li["+j+"]/input")).getAttribute("data-facet-option");
			//pull the only number from string			
//			String count=homePage.getAttribute("xpath", "data-facet-option");
			totalCount=getCount(count);
			/*actualCount=homePage.getValueFromGUI("resultsCount");*/
			actualCount=getFilterResultsCount();
			Util.printInfo("Actual Count ="+ actualCount);
			if(Integer.parseInt(actualCount)>49){
				homePage.verifyFieldNotVisible("selectAll");
				LazyScroll();
				Util.sleep(10000);
				//Count Results in Page
				results=driver.findElements(By.xpath("//ul[@id='results']/li"));
				resultsFetched=results.size();
				assertEquals(actualCount, Integer.toString(resultsFetched));
			}
			assertEquals(actualCount, totalCount);
			findElementByXpath(xpath);
			Util.sleep(2000);
			j++;	
			if(j>=5){
				try{
				 moreIsdisplayed=driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//ul//button[contains(text(),'More')]")).isDisplayed();
				}catch(Exception e){
					break;
				}
				if(moreIsdisplayed){
				assertTrue("More link is displayed-", moreIsdisplayed);
				driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//ul//button[contains(text(),'More')]")).click();
				try{
                    JavascriptExecutor jse = (JavascriptExecutor) driver;
                    String ScrollBar = "var iheight=document.documentElement.clientHeight; var yscroll=iheight * 5; window.scrollBy(0,500);";

                    JavascriptExecutor js = (JavascriptExecutor) driver;

                    js.executeScript(ScrollBar); 


                    String jScroll="document.getElementsByClassName('thumb')[0].style.top='396.872px';document.getElementsByClassName('overview')[0].style.top='-1580px';";
                    
                    jse.executeScript(jScroll);
                    Util.sleep(5000);
             }catch(Exception e ){
                   e.printStackTrace();
                    EISTestBase.fail("Javascript failed to scroll down the scrollbar");
            }
				driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//ul//button[contains(text(),'Less')]")).click();
				}
				else{
					break;
				}
				break;
				}
		}
		homePage.scrollIntoViewOfMetadataElement(driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//div//span[1]")));
		driver.findElement(By.xpath("//div[contains(@class,'viewport')]//ul[@role='presentation']/li["+i+"][@class='up-facet']//div//span[1]")).click();
				i+=2;
				
		}
		logoutMyAutodeskPortal();
	}

		private String getFilterResultsCount(String filterCount) {
			String[] count1=filterCount.split(" ");
			String[] count2=count1[0].split("\\(");
			return count2[1];
	}

		private String getCount(String count) {
			String countNo=count.replaceAll("\\D+", "");	//replace all chars with digits
			String[] count1=count.split("\\(");
			String[] count2=count1[1].split("\\)");
			return count2[0].trim();
			/*return countNo;*/
		
	}
		public String getFilterResultsCount() {
			String jqueryScript="return $('div[class=\"results-count\"]>span').text();";			
			Object objVal=((JavascriptExecutor)driver).executeScript(jqueryScript);
			String textVal=	objVal.toString();
			return textVal;

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
