package getlicense;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.SoapProject;
import common.Util;


/**
 * Test class - Test_PartnerLicenseFields
 * 
 * @author Shivani Saxena
 * @version 1.0.0
 */
public final class Test_PartnerLicenseFields extends GetLicenseTestBase 
{	
	
	public Test_PartnerLicenseFields() throws IOException 
	{
		super("chrome");
	}
	
	
	@Test
	public void Test_LicenseFieldsForPartnerTypes() throws Exception 
	{
		//SETUP PROJECT
		SoapProject soap = new SoapProject(testProperties.getConstant("SOAP_PROJECT"), "AutodeskInternal_DEVQA_exp2016.p12", "Adsk2016");						//for Jenkins
		//SoapProject soap = new SoapProject(testProperties.getConstant("SOAP_PROJECT"), true);		//for running on local machine

		displayItem ("Request "+testProperties.getConstant("REQUEST"), soap.selectRequest(testProperties.getConstant("REQUEST")));
		String xmlResponse = soap.getResponse();
		
		//DISPLAY RESPONSE
		displayItem("Response", xmlResponse);
		
		//GET LICENSES
		List<String> allLicenses = getLicenseNodes (xmlResponse);
		
		displayItem("Verifications", "");
		//VERIFY NUMBER OF LICENSES IS MULTIPLE IF REQUIRED
		if (testProperties.getConstant("LIC_NUMS").equalsIgnoreCase("MULTIPLE"))
		{
			assertTrue("Number of licenses in response (" +allLicenses.size()+ ") are multiple as expected", allLicenses.size() > 1);
		}

		// GET AND VERIFY LICENSES
		for (int count = 0; count < allLicenses.size(); count++)
		{
		
			System.out.println("\n***\n");
			String thisLicense = allLicenses.get(count);
			Util.printInfo("Verifying License #"+ (count+1) + " for SN: " + soap.getNodeValue("0:SerialNumber", thisLicense));
			System.out.println();
			
			//verify compulsory fields and VALIDITY OF SN
			assertTrue("Validity of SN is as expected", verifyCompulsoryLicenseFields(thisLicense, Boolean.valueOf(testProperties.getConstant("IS_SN_VALID"))));
			if(!Boolean.valueOf(testProperties.getConstant("IS_SN_VALID"))) {continue;}
			
			//verify atleast 1 old common fields exist
			assertTrue("Atleast 1 Common License Field is present", verifyCommonLicenseFields(thisLicense));
			
			//verify active status fields
			assertTrue("Active Subscription License Fields are as expected", verifyActiveSubLicenseFields(thisLicense, Boolean.valueOf(testProperties.getConstant("DO_CHECK_ACTIVE"))));
			
			//verify new common field as expected
			assertTrue("Existence of new common fields is as expected", verifyNewLicenseFields(thisLicense, Boolean.valueOf(testProperties.getConstant("IS_GL_NEW"))));
			if(!Boolean.valueOf(testProperties.getConstant("IS_GL_NEW"))) {continue;}
			
			//verify special fields present or absent depending on type
			if(!testProperties.getConstant("PARTNER_TYPE").isEmpty()){
				assertTrue("Existence of new extra fields is as expected", verifySpecialLicenseFields(thisLicense, testProperties.getConstant("PARTNER_TYPE")));
			}
			
			//verify Relationship Fields
			assertTrue("Relationship Fields are as expected", verifyRelationshipFields(thisLicense));
			
			//node value verifications if required
			if(!testProperties.getConstant("NODE_NAME").isEmpty()){
				String strMessage = "Value of node ns" + testProperties.getConstant("NODE_NAME");
				String valueInRequest = soap.getNodeValue(testProperties.getConstant("NODE_NAME"), soap.getRequestContent());
				String valueInResponse = soap.getNodeValue(testProperties.getConstant("NODE_NAME"), thisLicense);
				strMessage += " in request ('" + valueInRequest + "') should be equal to value of node in response ('" + valueInResponse + "')";
				assertTrue(strMessage, valueInRequest.equalsIgnoreCase(valueInResponse));
			}
		}		
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
