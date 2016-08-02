package bornincloud;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;
import bornincloud.BornInCloudConstants;


public class Test_Verify_IPP_NonCommercial_Registration_Complete_Page extends BornInCloudTestBase{
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
	 	
	public Test_Verify_IPP_NonCommercial_Registration_Complete_Page() throws IOException {
		super("browser");						
	}
	
	@Before
	public void setUp() throws Exception {

		if(getEnvironment().equalsIgnoreCase("dev")){
			UserID=testProperties.getConstant("userId_DEV");
			offerings=testProperties.getConstant("offeringId_DEV");
			country=testProperties.getConstant("country_DEV");
			expectednonCommercialTitleHeading=testProperties.getConstant("expectednonCommercialTitleHeading_DEV");
			expectedsectiontext=testProperties.getConstant("expectedsectiontext_DEV");
			
		}else{
			UserID=testProperties.getConstant("userId_STG");
			offerings=testProperties.getConstant("offeringId_STG");
			country=testProperties.getConstant("country_STG");
			expectednonCommercialTitleHeading=testProperties.getConstant("expectednonCommercialTitleHeading_STG");
			expectedsectiontext=testProperties.getConstant("expectedsectiontext_STG");
		}
		String url=getBaseURL() + "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings ;
		launchIPP(url);
		Util.sleep(4000);
		
	}
	
	
	@Test
	public void Test_VerifyIPPNonCommercialRegistrationComplete() throws Exception {
		
						
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
		String actualsectiontext =homePage.getValueFromGUI("actualsectiontext");
		System.out.println(actualsectiontext);
		System.out.println(expectedsectiontext);
		assertTrueCatchException("Thank you text is displayed",actualsectiontext.contains(expectedsectiontext));	
		
		
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
