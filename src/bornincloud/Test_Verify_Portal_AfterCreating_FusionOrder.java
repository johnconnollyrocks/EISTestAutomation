package bornincloud;

//@@ author: Santosh

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.jniwrapper.linux.x11.events.XClientMessageEvent.Data;

import common.EISTestBase;
import common.Page_;
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_Portal_AfterCreating_FusionOrder extends BornInCloudTestBase{
	
	public String sessionID;	
	public String  userID;
	
	public Test_Verify_Portal_AfterCreating_FusionOrder() throws IOException {
		super("browser","firefox");						
	}
	@Rule
	public TestName tname = new TestName();
	
	@Before
	public void setUp() throws Exception {
		
	}
	@Test
	public void Test_Verify_CP_For_Enthusiast_User_Method() throws Exception {
		
		Map<String, String> userDetails= new TreeMap<String, String>();
		userDetails=createCustomerPortalUserID();
		
		
		sessionID=userDetails.get("sessionID");
		userID=userDetails.get("userID");
		logoutMyAutodeskPortal();
		Util.sleep(10000);
		switchDriverToOtherBrowser("chrome");
		createOrderInPelcan();
		portalValidationsForBICUser(userID, "Password1","fusion");
	
	}
	
	public void createOrderInPelcan() throws Exception{
		Page_ pelicanPage = createPage(testProperties, "PAGE_PORTALHOME_PROPERTIES_FILE");
		launchPelican(getPelicanURL());
		pelicanPage.click("pelicanAccessKeyButton");
		pelicanPage.waitForFieldVisible("pelicanPartnerID", 10);
		pelicanPage.populateField("pelicanPartnerID", testProperties.getConstant("pelicanPartnerID"));
		pelicanPage.populateField("pelicanAccessKey", testProperties.getConstant("pelicanAccessKey"));
		pelicanPage.populateField("pelicanFamilyID", testProperties.getConstant("pelicanFamilyID"));
		pelicanPage.populateField("userExternalKey", sessionID);
		pelicanPage.populateField("subscriptionOfferExternalKey", testProperties.getConstant("subscriptionOfferExternalKey"));
		pelicanPage.populateField("currencyName", testProperties.getConstant("currencyName"));
		pelicanPage.click("submitSubscription");
		pelicanPage.waitForFieldVisible("isResponceStatusOK", 20);
		assertTrue("Found 200 OK status.Subscription Created Sucessfully", pelicanPage.checkIfElementExistsInPage("isResponceStatusOK", 20));
			
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
