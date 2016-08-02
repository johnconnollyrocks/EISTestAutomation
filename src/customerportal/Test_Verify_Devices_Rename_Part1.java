package customerportal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import common.Util;

/*IMPORTANT NOTE PLEASE READ BEFORE YOU MODIFY. IF YOU ARE NOT GOING TO MODIFY THEN IGNORE THIS COMMENT*/
/*
 NOTE: All the tests are tagged with no of SC/CM required. Also a device id is mandatory for this . Hence when you are modifying this test, please step
  into this with caution.Any unknown changes can goof up the script

 * The user accounts are following a specific pattern here, for ex: for tc 345 to run on STG ,the user act is TC345_SC1_WTIH_2CM_USERNAME_STG
 * so in the provide only TC345_SC_WITH_2CM and other portion of the string is concatenated in the function itself. Why this is done? to make the code look neat
 * and ability to work on DEV/STG env as well.
 */

public class Test_Verify_Devices_Rename_Part1 extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String deviceName = null;
	public String deviceColLoc ="compName";
	private ArrayList<String> lstRenamedDeviceInfo=null;
	private String deviceDynamicLocators =null;
	
	public Test_Verify_Devices_Rename_Part1() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void testAbilityToRenameTheDevices_Part1() throws Exception {
		String CMUserName=null;
		
		
		Util.printInfo("=======================Test_Verify_Devices_Rename_Part1==========================================");
		
		Util.printInfo("=========================TC341================================================");
		
			//THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FRdM JENKINS , as this test involved verification of the data with DIFFERENT USER ACCOUNTS 
		System.out.println("**********THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FROM JENKINS AS THIS TEST INVOLVES VERIFICATION OF DATA WITH DIFFERENT ACCOUNTS");
		USERNAME=getUserCredentials("CM")[0];			
		PASSWORD=getUserCredentials("CM")[1];		
		CMUserName=USERNAME;
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);	
		deviceDynamicLocators=testProperties.getConstant("LOCATORSDYNAMICTOKENS");
		Util.sleep(3000);
		verifyTheBasicRenameDeviceOptionsWorksAsExpected();	
			
		
		//Case 1 : TC341: CM and SC on same	 contract the CM has changed the Device name so SC shld be able to see the name		
		Util.printInfo("Verify that the CM has changed the Device information and SC should be able to see");		
		lstRenamedDeviceInfo=new ArrayList<>();
		Util.printInfo("=========================TC341================================================");
		Util.printInfo("CM has changed the Device information");		
		changeTheDeviceInfo("SC_WITH_CM_ON_SAMECONTRACT_DEVICEID");
		logoutMyAutodeskPortal();		
		//now login with SC with same contract
		Util.printInfo("SC is logging in to check if he can see the Device information updated by CM");
		USERNAME=getUserCredentials("SC_WITH_CM_ON_SAMECONTRACT")[0];
		PASSWORD=getUserCredentials("SC_WITH_CM_ON_SAMECONTRACT")[1];		
		String SC1_CM1_username=USERNAME;
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the SC can see the devices information");
		Util.printInfo("=========================================================================");
		ArrayList<String> actDeviceInfo= getEditedDevicesInfo("SC_WITH_CM_ON_SAMECONTRACT_DEVICEID");
		assertTrueWithFlags("The logged in SC user: "+USERNAME+"  should see the alias device Name, Description and last user changes done by CM user :"+CMUserName, lstRenamedDeviceInfo.equals(actDeviceInfo));
		
		/*************************************************************************************************/
		//CASE 2:SC and CM on same contract. When the SC has changed the information the CM should be able to see TC342
		lstRenamedDeviceInfo=new ArrayList<>();
		actDeviceInfo= new ArrayList<>();
		Util.printInfo("======================TC 342===================================================");
		changeTheDeviceInfo("SC_WITH_CM_ON_SAMECONTRACT_DEVICEID");
		logoutMyAutodeskPortal();		
		USERNAME=getUserCredentials("CM")[0];			
		PASSWORD=getUserCredentials("CM")[1];
		
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the CM :"+USERNAME+"is able to see Device information when SC :"+SC1_CM1_username+" has changed the Device alias names");	
		Util.printInfo("Verify that the CM can see the devices information changed by SC on same contract");
		Util.printInfo("=========================================================================");			
		actDeviceInfo= getEditedDevicesInfo("SC_WITH_CM_ON_SAMECONTRACT_DEVICEID");
		assertTrueWithFlags("The logged in CM user: "+USERNAME+"  should see the alias device Name, Description and last user changes done by SC user :"+SC1_CM1_username, lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		

		
		
	}
	/** 
	 * @throws Exception
	 */
	private void changeTheDeviceInfo(String deviceNameIdrow)  throws Exception{
				
		String[] deviceDynamicLocs=deviceDynamicLocators.split(",");
	  /*String liLocatorForJscript=null;*/
	  String deviceIDName= getSpecificTestDataBasedOnEnv(deviceNameIdrow);
		
		//do this for all Devices, last user and Description
		for(int i=0;i<deviceDynamicLocs.length;i++){
			String optionName=null;
			String tempDeviceName="AutoTest";
			String deviceCompnameLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesColumnsRow",deviceDynamicLocs[i] );			
			//not changing to getCurrentwebelement it is safe to consider getmultipleweblements still
			List<WebElement> lstDevices=productUpdatePage.getMultipleWebElementsfromField(deviceCompnameLoc);	//always get 1 element
			if (deviceDynamicLocs[i].equalsIgnoreCase("compName")){	
				optionName="Devices";
				/*liLocatorForJscript="Enter new name";*/
			}else if(deviceDynamicLocs[i].equalsIgnoreCase("compDescription")){
				optionName="Description";
				/*liLocatorForJscript="Enter new description";*/
			}else{
				optionName="adsUser";
				/*liLocatorForJscript="Enter new name";*/
			}			
			//generate a randno and attach to this
			Random rand= new Random();
			int randomNum = rand.nextInt((100 - 2) + 1) + 1;			
			String renameInfo=optionName+randomNum+tempDeviceName;
			tempDeviceName=optionName+tempDeviceName;
			int j=getDeviceIdRowInDeviceTable(deviceDynamicLocs[i],deviceIDName)-1;			
			/*int indexToClick=j;*/
			
			/*String jqueryScript="$('td[class=\"editable\"][data-name=\""+deviceDynamicLocs[i]+"\"]').each(function(index){ if(index=="+indexToClick+"){ $(this).click();}});\r" + 
						" $('input[placeholder=\""+liLocatorForJscript+"\"]').val(\""+renameInfo+"\");  $('input[placeholder=\""+liLocatorForJscript+"\"]').trigger(\"change\"); ";*/
						
			Util.printInfo("Changing the Device information for the field :"+optionName);
			lstDevices=productUpdatePage.getMultipleWebElementsfromField(deviceCompnameLoc);	
			Util.sleep(1000);
			lstDevices.get(j).click();
			Util.sleep(900);
			//collapse it again.
			/*lstDevices.get(j).click();*/
			//check that the new name pop up is displayed				
			Util.printInfo("Entering the new name as "+renameInfo+" for the Device for "+optionName);
			productUpdatePage.isFieldVisible("renameDeviceDialogtextPlaceholder");	// click inside  and enter text
			((JavascriptExecutor)(driver)).executeScript("arguments[0].focus();",productUpdatePage.getCurrentWebElement());
			productUpdatePage.sendKeysInTextFieldSlowly("renameDeviceDialogtextPlaceholder",renameInfo);
			try{
				productUpdatePage.getCurrentWebElement().sendKeys(Keys.RETURN);
			}
			catch(Exception e){
			//  e.printStackTrace();e.getCause(); e.getMessage();
			}
			/*((JavascriptExecutor)(driver)).executeScript(jqueryScript);	//do this only for first row hence index is set 0;				
*/			Util.sleep(900);
			lstDevices=productUpdatePage.getMultipleWebElementsfromField(deviceCompnameLoc);
			//now check if the name is displayed
			//click on the device name column
			int rowNum=getDeviceIdRowInDeviceTable(deviceDynamicLocs[i], deviceIDName)-1;
			lstDevices.get(rowNum).click();	//collapse the rename dialog
			lstDevices=productUpdatePage.getMultipleWebElementsfromField(deviceCompnameLoc);			
			String modifiedName=lstDevices.get(rowNum).getText();
			assertTrueWithFlags("The new Name should match for "+optionName,modifiedName.equalsIgnoreCase(renameInfo));						
			
			lstRenamedDeviceInfo.add(renameInfo);			
		}	

	}
	


	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
	
}

