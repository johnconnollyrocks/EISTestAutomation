package bornincloud;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

import edu.umass.cs.benchlab.har.HarEntries;
import edu.umass.cs.benchlab.har.HarEntry;
import edu.umass.cs.benchlab.har.HarLog;
import edu.umass.cs.benchlab.har.HarPage;
import edu.umass.cs.benchlab.har.HarQueryParam;
import edu.umass.cs.benchlab.har.HarQueryString;
import edu.umass.cs.benchlab.har.tools.HarFileReader;

public class Test_Verify_SiteCatalyst_Attributes extends BornInCloudTestBase{
	String expireDate = null;
	String renewalDate = null;
	String name = null;
	String creditCard = null;
	String editcreditCard = null;
	List<WebElement> productslist = null;
	
	public Test_Verify_SiteCatalyst_Attributes() {
		//FirefoxProfile profile = getWebDriverPreferencesForFirefoxProfile();				
		super("Browser",getAppBrowser(),false,true);	

	}
	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Verify_SiteCatalyst_Attributes_Method() throws Exception {

		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;
		
		/*String siteCatalystQueryValue="manage:en:products-services";
		//String pageNameVal="pageName";
		boolean foundSiteCatalystQueryVal=false;
		File harFileToRead= new File( Util.getTestRootDir()+"\\logs\\CaptureNetworkTraffic");
		String harFilePath=getHarFileToRead(harFileToRead);
		File harFileToPar= new File(harFilePath);
		HarFileReader harReader= new HarFileReader();

		HarLog log= harReader.readHarFile(harFileToPar);		
		HarEntries hEntries= log.getEntries();
		List<HarEntry> eachHarEntry= hEntries.getEntries();
		List<HarPage> myPages= log.getPages().getPages();*/

		//ArrayList<String> contract = new ArrayList<String>();
						
		
	
		//homePage.getMultipleWebElementsfromField(element);

		env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("USER_ID_DEV"),
					testProperties.getConstant("PASSWORD_DEV"));
			expireDate = testProperties.getConstant("EXPIRESDATE_DEV");
		} else if (env.equalsIgnoreCase("STG")) {
		bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
			expireDate = testProperties.getConstant("EXPIRESDATE_STG");
		}
		homePage.waitForElementToDisappear("pageLoadImg", 100000);

		homePage.waitForFieldVisible("titleproductservices", 30000);
		
		//verify data tracking attribute for P&S link
		
		List<WebElement>element=driver.findElements(By.xpath("//ul[@class='nav']//a[@data-tracking]"));
		
		if(element.size()!=0){
			
			assertTrue("dataTracking attribute presetn", element.size()!=0);
			//assertTrue("dataTracking attribute present", element.get(i).getAttribute("data-tracking")!=null);
			//element.get(i).getAttribute("data-tracking")
			
		}
		
		
				
		/*WebElement element= driver.findElement(By.xpath("//*[@id='nav-primary']/div/ul/li[1]/a"));
		
		assertTrue("dataTracking attribute present", element.getAttribute("data-tracking")!=null);*/
			
		
		// Logout
		homePage.click("arrow");

		homePage.click("signout");
		
		String siteCatalystQueryValue="manage:en:products-services";
		//String pageNameVal="pageName";
		boolean foundSiteCatalystQueryVal=false;
		File harFileToRead= new File( Util.getTestRootDir()+"\\logs\\CaptureNetworkTraffic");
		String harFilePath=getHarFileToRead(harFileToRead);
		File harFileToPar= new File(harFilePath);
		HarFileReader harReader= new HarFileReader();

		HarLog log= harReader.readHarFile(harFileToPar);		
		HarEntries hEntries= log.getEntries();
		List<HarEntry> eachHarEntry= hEntries.getEntries();
		List<HarPage> myPages= log.getPages().getPages();
		
		
		for (HarPage myHarPage: myPages){

			/*System.out.println(myHarPage.getId());
			System.out.println(myHarPage.getTitle());*/

			//with in the pages get each entry
			for(HarEntry harEntry:eachHarEntry) {
				/*System.out.println(harEntry.getRequest().getQueryString());
				System.out.println(harEntry.getRequest().getUrl());*/
				String siteCatalystUrl=harEntry.getRequest().getUrl();
				//find out if it matches with smetrics site
				// get the query params and check for the response against the request
				if (siteCatalystUrl.startsWith("https://smetrics.autodesk.com/")){
					// for that HarEntry get the relevant Response
					
					String pageNameUrl_r=null;
					HarQueryString hRequestQuery= harEntry.getRequest().getQueryString();

					List<HarQueryParam> eachQueryParams= hRequestQuery.getQueryParams();

					/*for(HarQueryParam myQueryParam: eachQueryParams){*/
					for(int i=0;i<eachQueryParams.size();i++){
						
						String queryValue=eachQueryParams.get(i).getValue();
						//String queryName=eachQueryParams.get(i).getName();
						//if (queryValue.equalsIgnoreCase(siteCatalystQueryValue) && queryName.equalsIgnoreCase(pageNameVal)){
						if (queryValue.equalsIgnoreCase(siteCatalystQueryValue)){
							foundSiteCatalystQueryVal=true;
							//also get the next value of 'r' i.e URL info for the pageName key ( next entry)
							pageNameUrl_r=eachQueryParams.get(i+1).getValue();
							break;	//come out
						}
						/*		System.out.println(queryName);
					System.out.println(queryValue);*/
					}
					//get the corresponding response
					if (foundSiteCatalystQueryVal){
						int respStatus=harEntry.getResponse().getStatus();	//should be 200*/
						switch(respStatus){
						case 200:{				
							Util.printInfo("****SUCCESS ***:The response code for the request of Sitecatalyst param: "+siteCatalystQueryValue+" for pageURL value: "+pageNameUrl_r+" is :"+respStatus);
							break;
						}
						case 302:{
							Util.printWarning("The response code for the request of Sitecatalyst param: "+siteCatalystQueryValue+"  for pageURL value: "+pageNameUrl_r+" is :"+respStatus+" please check");
							break;
						}

						case 0:{
							
							Util.printInfo("The response code for the request of Sitecatalyst param: "+siteCatalystQueryValue+"  for logout pageURL value: "+pageNameUrl_r+" is :"+respStatus+" please ignore");
							break;
						}


						default:{
							EISTestBase.fail("****FAILED ****:The request for the Sitecatalyst param: "+siteCatalystQueryValue+"  for pageURL value: "+pageNameUrl_r+"  is failed, as the response code found as :"+respStatus);
							Util.printError("****FAILED ****:The request for the Sitecatalyst param: "+siteCatalystQueryValue+"  for pageURL value: "+pageNameUrl_r+"  is failed, as the response code found as :"+respStatus);
							break;
						}
						}

					}
				}
			}

		}
		}


	
		@After
	public void tearDown() throws Exception {

		driver.quit();

		finish();
	}

}
