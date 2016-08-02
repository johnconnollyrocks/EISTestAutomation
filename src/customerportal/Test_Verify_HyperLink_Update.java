package customerportal;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

import javax.xml.bind.DatatypeConverter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;
/**
 * @USER STORY: US 1836
 * @author t_marus
 *
 */

public class Test_Verify_HyperLink_Update extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String PASSWORD = null;
	private String hrefViewReleaseNotes=null;
	private String expReleaseNotesWindTitle=null;	
	private String userName="****";	//need a user account for this
	private String password="****";
	//set the following pref to avoid auth pop ups
	private static String[] prefValues={"network.automatic-ntlm-auth.trusted-uris","eng,ussclpdesweb001.autodesk.com","signon.autologin.proxy","true"};
	
			
	public Test_Verify_HyperLink_Update() throws Exception {		
		super("Browser",getAppBrowser(),true, false, prefValues);
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void testHyperLinkReleaseNotes() throws Exception {
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			USERNAME=getUserCredentials("HYPERLINK")[0];
			PASSWORD=getUserCredentials("HYPERLINK")[1];
			loginAsMyAutodeskPortalUser(USERNAME ,PASSWORD);	
					
		}
		expReleaseNotesWindTitle=testProperties.getConstant("HYPERLINK_URL_TITLE");
		mainWindow.select();
		Util.sleep(5000);
		GoToProductUpdatesPage();
		//expand any of the product update and check if the hyperlink exists and navigates to right page
		Util.printInfo("Verify that 'View Release notes' link exists under Product update");
		Util.printInfo("Expand the product update by clicking on drawer button" );
		String articleUpdateText=productUpdatePage.getMultipleWebElementsfromField("newProductUpdatesList").get(0).getText();
		String productToggleDrawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("accessToggleDrawer", articleUpdateText);	
		productUpdatePage.click(productToggleDrawer);
		Util.printInfo("Clicking on 'View Release notes' link" );
		Util.sleep(1000);
		hrefViewReleaseNotes=productUpdatePage.getAttribute("viewReleaseNotes", "href");
		
		//the response code should be 200		
		/*assertTrueWithFlags(" View Release notes link should be working", checkIfReleaseNoteslinkIsWorking(hrefViewReleaseNotes)==200);*/
		productUpdatePage.click("viewReleaseNotes");	//click on view release notes link
				
		//Switch the focus to newly opened window
		HashSet<String> lstWindows=(HashSet<String>) driver.getWindowHandles();
		String[] windowHand= new String[lstWindows.size()];
		lstWindows.toArray(windowHand);
		Util.sleep(2000);	
		if (productUpdatePage.isAlertPresent())	{
			productUpdatePage.dismissAlert();///dismiss unwanted alerts
		}
		
		//get the last one
		driver.switchTo().window(windowHand[windowHand.length-1]);
		
		//String authURL1="http://ads\\t_marus:Ikon_321@ussclpdesweb001.autodesk.com";		
		//get the title of the release notes window and close it
		String releaseNoteWndTitle=driver.getTitle();
		if (releaseNoteWndTitle.contains("Unauthorized: Access")) {
			Util.printWarning("Unable to login to Release engg notes. Please add ADSK login credentials to check this");
		}else {
			assertTrueWithFlags("The View release notes for the product update :"+articleUpdateText+" should be working as expected", releaseNoteWndTitle.contains(expReleaseNotesWindTitle));			
		}
		
	}
	public int checkIfReleaseNoteslinkIsWorking(String urlToConnect) throws Exception{
		int responseCode=0;
		
		String authString=userName+":"+password;
		try {
			
			URL url= new URL(urlToConnect);			
			HttpURLConnection httpUrl= (HttpURLConnection) url.openConnection();
			String encodedAuthString = DatatypeConverter.printBase64Binary(authString.getBytes("UTF-8"));
			httpUrl.setRequestProperty("Authorization",encodedAuthString);
			httpUrl.setRequestMethod("GET");			
			httpUrl.connect();
			InputStream istream=httpUrl.getInputStream();	//try to get the input stream			
			responseCode= httpUrl.getResponseCode();			
			
		} catch (Exception e) {			
			Util.printInfo(e.getMessage());
			Util.printInfo("Retry connecting to redirected URL");
			//if you get exception then there is another layer of site you need to get in hence add the authentication tehre as well
			String nextUrl=e.getMessage().split("URL:")[1];
			responseCode=checkIfReleaseNoteslinkIsWorking(nextUrl);
			
		}
		finally{
			//try finally after two layers of redirection
			if (responseCode!=200)
			{
				Util.printError("The View Release notes link is not working, please check");
			}
		}
		return responseCode;
	}
	public void typeInAuthPopUp() {
		//ugly way but it works there would be again maintainance when it comes for other browsers so going this way
		driver.switchTo().alert().sendKeys("ads\\t_marus");
		driver.switchTo().alert().sendKeys("{TAB}");		
		Util.sleep(500);
		driver.switchTo().alert().sendKeys("Ikon_532");
		driver.switchTo().alert().accept();
	}

	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
	
	
		
}