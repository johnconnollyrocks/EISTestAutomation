package bornincloud;

//@@ author: Santosh

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import common.Util;
import common.exception.MetadataException;


public class Test_Verify_CP_For_StudentID_After_CreatingOrderThroughIPP extends BornInCloudTestBase{
	
	public String sessionID;	
	public String  userID;
	public String  url;
	
	public Test_Verify_CP_For_StudentID_After_CreatingOrderThroughIPP() throws IOException {
		super("browser","firefox");						
	}
	@Rule
	public TestName tname = new TestName();
	
	@Before
	public void setUp() throws Exception {
		
	}
	@Test
	public void Test_Verify_CP_For_StudentID_Method() throws Exception {
		
		Map<String, String> userDetails= new TreeMap<String, String>();
		userDetails=createCustomerPortalUserID();
		
		
		sessionID=userDetails.get("sessionID");
		userID=userDetails.get("userID");
		switchDriverToOtherBrowser("chrome");
		if (getEnvironment().equalsIgnoreCase("STG")){
			String hash = getclicHash(sessionID, "US","en");
			url=getBaseURL()+"userId="+ sessionID+"&country=US&lang=en" + hash ;
		}else if(getEnvironment().equalsIgnoreCase("DEV")){
			url=getBaseURL()+"userId="+ sessionID+"&country=US&lang=en";
		}
		launchIPP(url);
		IPPOrderCreate(userID,"student");
		Util.sleep(5000);
		launchIPP(getPortalURL());
		Util.sleep(2000);
		portalValidationsForUser(userID, "Password1","Student");
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
