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

public class Test_Verify_Devices_Rename_Part3 extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String deviceName = null;
	public String deviceColLoc ="compName";
	private ArrayList<String> lstRenamedDeviceInfo=null;
	private String deviceDynamicLocators =null;
	
	public Test_Verify_Devices_Rename_Part3() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void testAbilityToRenameTheDevices_Part3() throws Exception {
		String CMUserName=null;
		deviceDynamicLocators=testProperties.getConstant("LOCATORSDYNAMICTOKENS");
		
			//THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FRdM JENKINS , as this test involved verification of the data with DIFFERENT USER ACCOUNTS 
		System.out.println("**********THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FROM JENKINS AS THIS TEST INVOLVES VERIFICATION OF DATA WITH DIFFERENT ACCOUNTS");
		Util.printInfo("=======================Test_Verify_Devices_Rename_Part3==========================================");
		ArrayList<String> actDeviceInfo= new ArrayList<>();
		Util.printInfo("=============================TC 345============================================");
		Util.printInfo("Verify that, when SC1 changes the device alias info, CM1 and SC1 should see changes but not CM2 and SC2" );
		
		USERNAME=getUserCredentials("TC345_SC1_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("TC345_SC1_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);		
		String cm1_2cm_UserName=getUserCredentials("TC345_CM1_WTIH_2CM")[0];
		String sc2_2cm_UserName=getUserCredentials("TC345_SC2_WTIH_2CM")[0];
		String cm2_2cm_UserName=getUserCredentials("TC345_CM2_WTIH_2CM")[0];
		lstRenamedDeviceInfo= new ArrayList<>();
		
		Util.printInfo("SC1: "+USERNAME+ " is logging in and CM1:"+cm1_2cm_UserName+"  expected to see the changes done by SC1 .But the SC2 :"+sc2_2cm_UserName+" and CM2: "+cm2_2cm_UserName+" should not see any changes");		
		Util.printInfo("Verify that the SC1 :"+USERNAME+"is going to change the Device alias names");
		changeTheDeviceInfo("TC345_SC2_WTIH_2CM_DEVICEID");			
		logoutMyAutodeskPortal();		
		//Now verify if SC1 can see those changes
		USERNAME=getUserCredentials("TC345_SC1_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("TC345_SC1_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);	
		Util.printInfo("Verify that the CM1 :"+USERNAME+" should see the Device information when SC1 has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("TC345_SC2_WTIH_2CM_DEVICEID");
		assertTrueWithFlags("The logged in CM1 user: "+USERNAME+"  should see modified alias device Name, Description and last user changes done by SC1 user",lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		//Now login with CM2 and check those changes are not seen .
		USERNAME=getUserCredentials("TC345_CM2_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("TC345_CM2_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);	
		Util.printInfo("Verify that the CM2 :"+USERNAME+" should not see the Device information when SC1 has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("TC345_SC2_WTIH_2CM_DEVICEID");
		assertFalseWithFlags("The logged in CM2 user: "+USERNAME+" should not see the modified alias device Name, Description and last user changes done by CM1 user",lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		//now login with SC2 and check the changes are not seen
		USERNAME=getUserCredentials("TC345_SC2_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("TC345_SC2_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);	
		Util.printInfo("Verify that the SC2 :"+USERNAME+" should not see the Device information when SC1 has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("TC345_SC2_WTIH_2CM_DEVICEID");
		assertFalseWithFlags("The logged in SC2 user: "+USERNAME+" should not see the modified alias device Name, Description and last user changes done by SC1 user",lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		//************************************
		
		
		
		Util.printInfo("=============================TC 346============================================");
		//verifying the TC 346
		Util.printInfo("=========================================================================");
		//Now the SC1 is going to change the device , SC1 and Cm1 shld see
		USERNAME=getUserCredentials("SC_WTIH_CM1_CM2")[0];
		PASSWORD=getUserCredentials("SC_WTIH_CM1_CM2")[1];
		String cm1_cm1_UserName=getUserCredentials("CM1_WTIH_CM1")[0];
		String cm2_cm2_UserName=getUserCredentials("CM2_WTIH_CM2")[0];
		lstRenamedDeviceInfo=new ArrayList<>();
		Util.printInfo("SC1: "+USERNAME+ " is logging in and CM1:"+cm1_cm1_UserName+"  expected to see the changes done by SC.But the CM2 :"+cm2_cm2_UserName+" should not see any changes");		
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);		
		Util.printInfo("Verify that the SC_CM1 :"+USERNAME+"is going to change the Device alias names");
		changeTheDeviceInfo("SC_WTIH_CM1_DEVICEID");	//applicable for Device1 and SC1		
		logoutMyAutodeskPortal();		
		
		Util.printInfo("CM1: "+cm1_cm1_UserName+ " is logging in and expected to see the changes done by SC");
		USERNAME=getUserCredentials("CM1_WTIH_CM1")[0];
		PASSWORD=getUserCredentials("CM1_WTIH_CM1")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the CM1 :"+USERNAME+"is should see the Device information when SC has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_CM1_DEVICEID");
		assertTrueWithFlags("The logged in CM1 user: "+USERNAME+"  should modified alias device Name, Description and last user changes done by SC user",lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		Util.printInfo("=========================================================================");
		USERNAME=getUserCredentials("CM2_WTIH_CM2")[0];
		PASSWORD=getUserCredentials("CM2_WTIH_CM2")[1];
		String sc1_CM1_userName=getUserCredentials("SC_WTIH_CM1_CM2")[0];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("CM2:"+USERNAME+" is going to login and expected not to see those changes done by SC :"+sc1_CM1_userName);
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_CM1_DEVICEID");
		if(actDeviceInfo!=null)
		{
		assertTrueWithFlags("The logged in CM2 user: "+USERNAME+"  is seeing the alias device Name, Description and last user changes done by SC user :"+sc1_CM1_userName+", which is not expected",false);
		}
		else{
			assertTrueWithFlags("The logged in CM2 user: "+USERNAME+"  is not able to see the alias device Name, Description and last user changes done by SC user :"+sc1_CM1_userName,true);
		}
		logoutMyAutodeskPortal();
		
		
		
		
	}
	/** 
	 * @throws Exception
	 */
	private void changeTheDeviceInfo(String deviceNameIdrow)  throws Exception{
				
		String[] deviceDynamicLocs=deviceDynamicLocators.split(",");
	  String liLocatorForJscript=null;
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
				liLocatorForJscript="Enter new name";
			}else if(deviceDynamicLocs[i].equalsIgnoreCase("compDescription")){
				optionName="Description";
				liLocatorForJscript="Enter new description";
			}else{
				optionName="adsUser";
				liLocatorForJscript="Enter new name";
			}			
			//generate a randno and attach to this
			Random rand= new Random();
			int randomNum = rand.nextInt((100 - 2) + 1) + 1;			
			String renameInfo=optionName+randomNum+tempDeviceName;
			tempDeviceName=optionName+tempDeviceName;
			int j=getDeviceIdRowInDeviceTable(deviceDynamicLocs[i],deviceIDName)-1;			
			int indexToClick=j;
			
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
				  Util.sleep(30000);
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
	public boolean verifyTheDeviceInfoSeenByCMOrSC(String roleType) throws Exception {
		String optionName=null;
		boolean verifyData=false;
		String[] deviceDynamicLocs=deviceDynamicLocators.split(",");
		//do this for all Devices, last user and Description and verify that the device info is same 
		for(int i=0;i<deviceDynamicLocs.length;i++){
			 verifyData=false;
			String deviceCompnameLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesColumnsData",deviceDynamicLocs[i] );			
			List<WebElement> lstDevices=productUpdatePage.getMultipleWebElementsfromField(deviceCompnameLoc);
			if (deviceDynamicLocs[i].equalsIgnoreCase("compName")){
				optionName="Devices";
			}else if(deviceDynamicLocs[i].equalsIgnoreCase("compDescription")){
				optionName="Description";
			}else{
				optionName="Last User";
			}				
			//Verify only for the First Device row
			for(int j=0;j<lstDevices.size();){
				//do this only once				
				String verifyDeviceInfolabel=lstDevices.get(i).getText();
				Util.printInfo("Verify that the Device information for the field :"+optionName+"is as expected when User: "+roleType+" logs in");	
				verifyData=lstRenamedDeviceInfo.contains(verifyDeviceInfolabel);
				assertTrue("The Device info field: "+optionName+" should be visible when the user : "+roleType+" logs in",verifyData );				
				break;	//do this for first row				
			}
		}
		return verifyData;
	}
	/**
	 * @Description : Gets the modified device information of the entire row from a specific device id
	 * @param deviceidName
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList<String> getEditedDevicesInfo(String deviceidName) throws Exception {
		String[] deviceDynamicLocs=deviceDynamicLocators.split(",");
		String theDeviceIdValue= getSpecificTestDataBasedOnEnv(deviceidName);
		ArrayList<String> lsActDeviceInfo	=  new ArrayList<>();
		try{
		for(int j=0;j<deviceDynamicLocs.length;j++){			
			String getDeviceRowsInfo= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("deviceNameIdRow", theDeviceIdValue,deviceDynamicLocs[j]);
			String deviceInfoActual=productUpdatePage.getValueFromGUI(getDeviceRowsInfo);
			lsActDeviceInfo.add(deviceInfoActual);
		}
		}catch (Exception ex) {
			Util.printWarning("The deviceId :"+deviceidName+" does not exist in the Device table for logged in user: "+USERNAME);
			return null;
		}
		return lsActDeviceInfo;		
	}

	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
	public String[] getUserCredentials(String userAcctType)  throws Exception{
		String[] userCredentials = new String[2]; // always two username and passwd
		if(getEnvironment().equalsIgnoreCase("DEV")){						
			userCredentials[0]=testProperties.getConstant(userAcctType+"_USERNAME_DEV");
			userCredentials[1]=testProperties.getConstant(userAcctType+"_PASSWORD_DEV");		
		}else{
			userCredentials[0]=testProperties.getConstant(userAcctType+"_USERNAME_STG");
			userCredentials[1]=testProperties.getConstant(userAcctType+"_PASSWORD_STG");
		}		
		return userCredentials;
	}
}

