package bornincloud;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;
import bornincloud.BornInCloudConstants;


public class Test_Verify_IPP_NonCommercial_Registration_Complete_Process extends BornInCloudTestBase{
	String UserID="";
 	String offerings="";
 	String country="";
 	String expectedHeader="";
 	String plan="";
 	String editionValue="";	
 	String expectedNonCommercialLink="";
 	String expectedapptitleHeading="";
 	String expectednonCommercialTitleHeading="";
 	String expectedsectiontext="";
 	String sessionID="";
 	String userID="";
 	
	 	
	public Test_Verify_IPP_NonCommercial_Registration_Complete_Process() throws IOException {
		super("browser");						
	}
	
	@Before
	public void setUp() throws Exception {
		Map<String, String> userDetails= new TreeMap<String, String>();
		userDetails=createCustomerPortalUserID();
		
		
		sessionID=userDetails.get("sessionID");
		userID=userDetails.get("userID");
		
		if(getEnvironment().equalsIgnoreCase("dev")){
			UserID=sessionID;
			offerings=testProperties.getConstant("offeringId_DEV");
			country=testProperties.getConstant("country_DEV");
			expectedNonCommercialLink=testProperties.getConstant("expectedNonCommercialLink_DEV");
			expectedapptitleHeading=testProperties.getConstant("expectedapptitleHeading_DEV");
			expectednonCommercialTitleHeading=testProperties.getConstant("expectednonCommercialTitleHeading_DEV");
			expectedsectiontext=testProperties.getConstant("expectedsectiontext_DEV");
			
		}else{
			UserID=sessionID;
			offerings=testProperties.getConstant("offeringId_STG");
			country=testProperties.getConstant("country_STG");
			expectedNonCommercialLink=testProperties.getConstant("expectedNonCommercialLink_STG");
			expectedapptitleHeading=testProperties.getConstant("expectedapptitleHeading_STG");
			expectednonCommercialTitleHeading=testProperties.getConstant("expectednonCommercialTitleHeading_STG");
			expectedsectiontext=testProperties.getConstant("expectedsectiontext_STG");
		}
		String url=getBaseURL() + "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings ;
		launchIPP(url);		
		Util.sleep(4000);
		
	}
	
	
	@Test
	public void Test_VerifyIPPNonCommercialRegistrationComplete() throws Exception {
		
		Util.printMessage("validating the Non Commercial Use Register Link for Fusion360");
		
		String actualHeader=homePage.getValueFromGUI("apptitleHeading");
		System.out.println(actualHeader);
		System.out.println(expectedapptitleHeading);
		assertTrueCatchException("Expected Header is displayed after launching the IPP using Fusion360 trial.",actualHeader.contains(expectedapptitleHeading));	
		
		assertTrueCatchException("Non Commercial Use Register Link exists for Fusion360 Trial",homePage.checkFieldExistence("registerNonUserLink"));
		String actualNonCommercialLink=homePage.getValueFromGUI("registerNonUserLink");
		System.out.println(actualNonCommercialLink);
		System.out.println(expectedNonCommercialLink);
		assertTrueCatchException("expected register for Non-Commercial Use text displayed in the link",actualNonCommercialLink.contains(expectedNonCommercialLink));
		
		
				
		Util.printMessage("selecting the student option, accept the conditions and submitting");	
		homePage.click("registerNonUserLink");
		Util.sleep(2000);
		homePage.click("fusion360studentoption");
		homePage.checkIfElementExistsInPage("iAcceptCheckBox", 1);
		homePage.click("enabledSubmitButton");
		Util.sleep(2000);
		
		Util.printMessage("validating the registration confirmation text");
		
		String actualnonCommercialTitleHeading =homePage.getValueFromGUI("nonCommercialTitleHeading");
		System.out.println(actualnonCommercialTitleHeading);
		System.out.println(expectednonCommercialTitleHeading);
		assertTrueCatchException("Non commercial registration complete text is displayed",actualnonCommercialTitleHeading.contains(expectednonCommercialTitleHeading));	
//		String actualsectiontext =homePage.getValueFromGUI("sectiontext");
//		System.out.println(actualsectiontext);
//		System.out.println(expectedsectiontext);
//		assertTrueCatchException("Thank you text is displayed",actualsectiontext.contains(expectedsectiontext));	
		
		
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
