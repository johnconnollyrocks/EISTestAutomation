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

public class Test_Verify_Devices_Rename_Part2 extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String deviceName = null;
	public String deviceColLoc ="compName";
	private ArrayList<String> lstRenamedDeviceInfo=null;
	private String deviceDynamicLocators =null;
	
	public Test_Verify_Devices_Rename_Part2() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void testAbilityToRenameTheDevices_Part2() throws Exception {
		String CMUserName=null;
		deviceDynamicLocators=testProperties.getConstant("LOCATORSDYNAMICTOKENS");
		
			//THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FRdM JENKINS , as this test involved verification of the data with DIFFERENT USER ACCOUNTS 
		System.out.println("**********THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FROM JENKINS AS THIS TEST INVOLVES VERIFICATION OF DATA WITH DIFFERENT ACCOUNTS");
				
		Util.printInfo("=======================Test_Verify_Devices_Rename_Part2==========================================");
		//CASE 3: TC 343
		//TO DO In this case CM will do some changes and expect to see his own changes when SC changes device alias info for TC 371
		
		Util.printInfo("===========================TC343 and TC 371==============================================");	
		lstRenamedDeviceInfo=new ArrayList<>();		
		ArrayList<String> actDeviceInfo= new ArrayList<>();
		/*base state: let CM 1 do some changes on the device name* */
		Util.printInfo("This test also verifies the TC 371");
		
		lstRenamedDeviceInfo=new ArrayList<>();
		USERNAME=getUserCredentials("SC_WTIH_2CM")[0];			
		PASSWORD=getUserCredentials("SC_WTIH_2CM")[1];	
		Util.printInfo("SC under 2 CM is logging in");
		String CM1_2CMUserName=getUserCredentials("CM1_WTIH_2CM")[0];
		String CM2_2CMUserName=getUserCredentials("CM2_WTIH_2CM")[0];		
		Util.printInfo("Verify that the device information changed by SC :"+USERNAME+" under 2 CM on same device should be seen by all SC :"+USERNAME+" ,CM1 :"+CM1_2CMUserName+", CM2 : "+CM2_2CMUserName);		
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("SC under 2 CM is going to change the device alias info");
		changeTheDeviceInfo("SC_WTIH_2CM_DEVICEID");
		logoutMyAutodeskPortal();					
		
		Util.printInfo("=========================================================================");
		//Now the CM2  should see those changes done by SC
		Util.printInfo("CM2: "+CM2_2CMUserName+ " is logging in and expected to see the changes done by SC");
		USERNAME=getUserCredentials("CM2_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("CM2_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the CM2 :"+USERNAME+"is able to see Device information when SC has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_2CM_DEVICEID");
		assertTrueWithFlags("The logged in CM2 user: "+USERNAME+"  should see the alias device Name, Description and last user changes done by SC user ", lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		//Now CM1 should see those changes done by SC
		Util.printInfo("=========================================================================");
		Util.printInfo("CM1: "+CM1_2CMUserName+ " is logging in and expected not to see the changes done by SC");
		USERNAME=getUserCredentials("CM1_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("CM1_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the CM1 :"+USERNAME+"is should not see the Device information when SC has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_2CM_DEVICEID");
		if (!lstRenamedDeviceInfo.equals(actDeviceInfo)){
			Util.printWarning("For CM1 user there is already value exists in DB hence the changes done by SC not seen by CM1");
		}else{
			assertTrueWithFlags("The logged in CM1 user: "+USERNAME+"  should see the alias device Name, Description and last user changes done by SC user", lstRenamedDeviceInfo.equals(actDeviceInfo));			
		}		
		
		Util.printInfo("=========================================================================");
		
		//Verify tc371, let CM1 change the device alias info and followed by CM2. 
		Util.printInfo("=============================TC 371==========================================");
		lstRenamedDeviceInfo=new ArrayList<>();			
		Util.printInfo("CM1 under 2 CM is already logged in");
		Util.printInfo("CM1 :"+USERNAME+" under 2 CM on same device is going to change the device alias info");
		changeTheDeviceInfo("SC_WTIH_2CM_DEVICEID");
		//store the CM1 changes into a different list and verify that this info shld exist
		ArrayList<String> cm1DeviceInfo=(ArrayList<String>) lstRenamedDeviceInfo.clone();
		logoutMyAutodeskPortal();
		
		//Let the CM2 also change the Device alias info soon after changed by CM1.
		lstRenamedDeviceInfo=new ArrayList<>();
		USERNAME=getUserCredentials("CM2_WTIH_2CM")[0];			
		PASSWORD=getUserCredentials("CM2_WTIH_2CM")[1];	
		Util.printInfo("CM2 under 2 CM is logging in");			
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		/*Util.printInfo("Verify that the device information changed by SC :"+USERNAME+" under 2 CM on same device should be seen by all SC :"+USERNAME+" ,CM1 :"+CM1_2CMUserName+", CM2 : "+CM2_2CMUserName);*/		
		Util.printInfo("CM2 under 2 CM is going to change the device alias info");
		changeTheDeviceInfo("SC_WTIH_2CM_DEVICEID");
		logoutMyAutodeskPortal();			
		
		//Now the SC should see those changes done by CM2 not by CM1	
		//and the CM1  should see his/her own changes 
		USERNAME=getUserCredentials("SC_WTIH_2CM")[0];			
		PASSWORD=getUserCredentials("SC_WTIH_2CM")[1];
		Util.printInfo("SC: "+USERNAME+ " is logging in and expected to see the changes done by CM2:"+CM2_2CMUserName);
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the SC :"+USERNAME+"is able to see Device alias information done by CM2");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_2CM_DEVICEID");
		assertTrueWithFlags("The logged in SC user: "+USERNAME+"  should see the alias device Name, Description and last user changes done by CM2 user ", lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		Util.printInfo("======TC 371:=====VERIFY CM1 can see his own changes =======================================");
		Util.printInfo("CM1: "+CM1_2CMUserName+ " is logging in and expected see his/her own alias changes done by SC");
		USERNAME=getUserCredentials("CM1_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("CM1_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the CM1 :"+USERNAME+"is should see his/her own Device alias information when CM2 has changed the Device alias info");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_2CM_DEVICEID");
		assertTrueWithFlags("The logged in CM1 user: "+USERNAME+"  should see the alias device Name, Description and last user changes done by CM1 user", actDeviceInfo.equals(cm1DeviceInfo));
		
		logoutMyAutodeskPortal();
		Util.printInfo("========================END OF TC 371==========================================");
		
		
		
		
		Util.printInfo("=============================TC 344===========================================");
		Util.printInfo("Verify that CM1 and SC1 can see the changes of device name1 , CM2 and SC2 cannot see, when CM1 has changed Device alias info");
		USERNAME=getUserCredentials("TC344_CM1_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("TC344_CM1_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);		
		String sc1_2cm_UserName=getUserCredentials("TC344_SC1_WTIH_2CM")[0];
		String sc2_2cm_UserName=getUserCredentials("TC344_SC2_WTIH_2CM")[0];
		String cm2_2cm_UserName=getUserCredentials("TC344_CM2_WTIH_2CM")[0];
		lstRenamedDeviceInfo= new ArrayList<>();
		
		Util.printInfo("CM1: "+USERNAME+ " is logging in and SC1:"+sc1_2cm_UserName+"  expected to see the changes done by CM1 .But the SC2 :"+sc2_2cm_UserName+" and CM2: "+cm2_2cm_UserName+" should not see any changes");		
		Util.printInfo("Verify that the CM1 :"+USERNAME+"is going to change the Device alias names");
		changeTheDeviceInfo("TC344_SC2_WTIH_2CM_DEVICEID");			
		logoutMyAutodeskPortal();		
		//Now verify if SC1 can see those changes
		USERNAME=getUserCredentials("TC344_SC1_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("TC344_SC1_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);	
		Util.printInfo("Verify that the SC1 :"+USERNAME+" should see the Device information when CM1 has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("TC344_SC2_WTIH_2CM_DEVICEID");
		assertTrueWithFlags("The logged in SC1 user: "+USERNAME+"  should see modified alias device Name, Description and last user changes done by CM1 user",lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		//Now login with CM2 and check those changes are not seen .
		USERNAME=getUserCredentials("TC344_CM2_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("TC344_CM2_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);	
		Util.printInfo("Verify that the CM2 :"+USERNAME+" should not see the Device information when CM1 has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("TC344_SC2_WTIH_2CM_DEVICEID");
		assertFalseWithFlags("The logged in CM2 user: "+USERNAME+" should not see the modified alias device Name, Description and last user changes done by CM1 user",lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		//now login with SC2 and check the changes are not seen
		USERNAME=getUserCredentials("TC344_SC2_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("TC344_SC2_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);	
		Util.printInfo("Verify that the SC2 :"+USERNAME+" should not see the Device information when CM1 has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("TC344_SC2_WTIH_2CM_DEVICEID");
		assertFalseWithFlags("The logged in SC2 user: "+USERNAME+" should not see the modified alias device Name, Description and last user changes done by CM1 user",lstRenamedDeviceInfo.equals(actDeviceInfo));
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

