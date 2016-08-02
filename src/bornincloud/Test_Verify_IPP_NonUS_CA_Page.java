package bornincloud;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;
import bornincloud.BornInCloudConstants;


public class Test_Verify_IPP_NonUS_CA_Page extends BornInCloudTestBase{

	String UserID="";
 	String offerings="";
 	String country="";
 	String expectedHeader="";
 	String plan="";
 	String editionValue="";	
 	String expectedStringToDisplay="";
 	String expectedpageTitleHeading="";
	public Test_Verify_IPP_NonUS_CA_Page() throws IOException {
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
			expectedStringToDisplay=testProperties.getConstant("expectedTextDisplay_DEV");
			expectedpageTitleHeading=testProperties.getConstant("expectedpageTitleHeading_DEV");
		}else{
			UserID=testProperties.getConstant("userId_STG");
			offerings=testProperties.getConstant("offeringId_STG");
			country=testProperties.getConstant("country_STG");
			plan=testProperties.getConstant("plan_STG");
			editionValue=testProperties.getConstant("editionValue_STG");
			expectedHeader=testProperties.getConstant("expectedHeader_STG");
			expectedStringToDisplay=testProperties.getConstant("expectedTextDisplay_STG");
			expectedpageTitleHeading=testProperties.getConstant("expectedpageTitleHeading_STG");
			
			
		}
		String url=getBaseURL() + "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings ;
		launchIPP(url);		
		Util.sleep(4000);
		
	}
	
	
	@Test
	public void Test_VerifyIPPNonUSCA() throws Exception {
		
		Util.printMessage("validating the Header Text in IPP Home Page");
		String actualHeader=homePage.getValueFromGUI("headerINIPPHomePage");
		System.out.println(actualHeader);
		System.out.println(expectedHeader);
		assertTrueCatchException("Expected Header is displayed after launching the IPP.",actualHeader.contains(expectedHeader));
		
		Util.printMessage("validating the Page Title Heading in IPP Home Page for Non US/CA customers");	
		String actualpageTitleHeading=homePage.getValueFromGUI("pageTitleHeading");
		System.out.println(actualpageTitleHeading);
		System.out.println(expectedpageTitleHeading);
		assertTrueCatchException("Expected Page Title Heading is displayed after launching the IPP.Expected:"+ expectedpageTitleHeading+ "Actual: "+actualpageTitleHeading,actualpageTitleHeading.contains(expectedpageTitleHeading));
		
		Util.printMessage("validating the visit store text in IPP Home Page for Non US/CA customers");	
		String actualvisitStoreText =homePage.getValueFromGUI("visitStoreText");
		System.out.println(actualvisitStoreText);
		System.out.println(expectedStringToDisplay);
		assertTrueCatchException("Expected visit store text is displayed after launching the IPP.",actualvisitStoreText.contains(expectedStringToDisplay));
		
		Util.printMessage("validating the page title in IPP Home Page for Non US/CA customers");
		assertTrueCatchException("Expected Page Title is displayed after launching the IPP.",homePage.checkFieldExistence("pageTitle"));
		
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
