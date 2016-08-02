package bornincloud;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;
import bornincloud.BornInCloudConstants;


public class Test_Verify_IPP_Redirect extends BornInCloudTestBase{

	 	String UserID="";
	 	String offerings="";
	 	String country="";
	 	String expectedHeader="";
	 	String plan="";
	 	String editionValue="";
	 	String url="";
	public Test_Verify_IPP_Redirect() throws IOException {
		super("browser");						
	}
	
	@Before
	public void setUp() throws Exception {
		if(getEnvironment().equalsIgnoreCase("dev")){
			UserID=testProperties.getConstant("userId_DEV");
			offerings=testProperties.getConstant("offeringId_DEV");
			country=testProperties.getConstant("country_DEV");
			plan=testProperties.getConstant("plan_DEV");
			editionValue=testProperties.getConstant("editionValue_DEV");
			expectedHeader=testProperties.getConstant("expectedHeader_DEV");
		}else{
			UserID=testProperties.getConstant("userId_STG");
			offerings=testProperties.getConstant("offeringId_STG");
			country=testProperties.getConstant("country_STG");
			plan=testProperties.getConstant("plan_STG");
			editionValue=testProperties.getConstant("editionValue_STG");
			expectedHeader=testProperties.getConstant("expectedHeader_STG");
		}
		url=getBaseURL()+ "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings ;
		launchIPP(url);		
		Util.sleep(4000);
		
	}
	
	
	@Test
	public void Test_VerifyIPPRedirect() throws Exception {
		String actualHeader=homePage.getValueFromGUI("headerINIPPHomePage");
		assertTrueCatchException("Expected Header is displayed after launching the IPP.",actualHeader.contains(expectedHeader));
		validateNegativeRedirect();
	}
	
	public void validateNegativeRedirect() throws Exception{
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.	
		
		
		//validate for invalid offering 
		url=getBaseURL()+ "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + "FUSSION-TRI" ;
		launchIPP(url);		
		homePage.waitForFieldVisible("errorINIPPForWrongDetails",10);
		assertTrueCatchException("Negative scenarios-Ipp redirect should fail for invalid offering.",homePage.checkIfElementExistsInPage("errorINIPPForWrongDetails", 20));
		
//		//validate for invalid signature 
//		String todaydate = dateFormat.format(cvtToGmt(c.getTime()));
//		url=getBaseURL()+ "/r?signature=dfjdlfjdfd2f4d56fdfdfjsdfjdsewe&grantToken=ABCD"+"&timestamp="+todaydate+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings;
//		launchIPP(url);		
//		homePage.waitForFieldVisible("unauthorizedRequest",10);
//		System.out.println("Testing: "+homePage.getValueFromGUI("unauthorizedRequest"));
//		assertTrueCatchException("Negative scenarios-Ipp redirect should fail for invalid Signature.",homePage.checkIfElementExistsInPage("unauthorizedRequest", 20));
//		
//		//validate for invalid User 
//		url=getBaseURL()+ "/r?signature=" + getSignature(UserID)+ "&userId=REGHTUYDLSE&country=" + country + "&offeringId=" + offerings ;
//		launchIPP(url);		
//		homePage.waitForFieldVisible("unauthorizedRequest",10);
//		assertTrueCatchException("Negative scenarios-Ipp redirect should fail for invalid User.",homePage.checkIfElementExistsInPage("unauthorizedRequest", 20));
		
		
		
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
