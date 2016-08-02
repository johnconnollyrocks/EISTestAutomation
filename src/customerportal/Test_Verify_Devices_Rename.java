package customerportal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.eviware.soapui.support.StringUtils;

import common.Util;

/*IMPORTANT NOTE PLEASE READ BEFORE YOU MODIFY. IF YOU ARE NOT GOING TO MODIFY THEN IGNORE THIS COMMENT*/
/*
 NOTE: All the tests are tagged with no of SC/CM required. Also a device id is mandatory for this . Hence when you are modifying this test, please step
  into this with caution.Any unknown changes can goof up the script

 * The user accounts are following a specific pattern here, for ex: for tc 345 to run on STG ,the user act is TC345_SC1_WTIH_2CM_USERNAME_STG
 * so in the provide only TC345_SC_WITH_2CM and other portion of the string is concatenated in the function itself. Why this is done? to make the code look neat
 * and ability to work on DEV/STG env as well.
 */

public class Test_Verify_Devices_Rename extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String deviceName = null;
	public String deviceColLoc ="compName";
	private ArrayList<String> lstRenamedDeviceInfo=null;
	private String deviceDynamicLocators =null;
	
	public Test_Verify_Devices_Rename() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void testAbilityToRenameTheDevices() throws Exception {
		String CMUserName=null;
		
			//THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FRdM JENKINS , as this test involved verification of the data with DIFFERENT USER ACCOUNTS 
		System.out.println("**********THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FROM JENKINS AS THIS TEST INVOLVES VERIFICATION OF DATA WITH DIFFERENT ACCOUNTS");
		USERNAME=getUserCredentials("CM")[0];			
		PASSWORD=getUserCredentials("CM")[1];		
		CMUserName=USERNAME;
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);	
		deviceDynamicLocators=testProperties.getConstant("LOCATORSDYNAMICTOKENS");
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
		
		//CASE 3: TC 343
		//TO DO In this case CM will do some changes and expect to see his own changes when SC changes device alias info for TC 371
		
		Util.printInfo("===========================TC343 and TC 371==============================================");	
		lstRenamedDeviceInfo=new ArrayList<>();
		actDeviceInfo= new ArrayList<>();
		/*base state: let CM 1 do some changes on the device name* */
		Util.printInfo("Let CM1 do some changes in Device alias name, so that only CM1 and SC1 can see but not CM2. Ideally 343 can be done when there are no entries " +
				"in the DB for CM1 and CM2. The same test is also veriyfing the TC 371. " +
				"When the CM2 changes the device alias only the assoicated SC to both CMs can see and the CM1 can see device alias which CM1 has done");
		
		USERNAME=getUserCredentials("CM1_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("CM1_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Capture the CM1 device alias info");	
		//change the devices 
		changeTheDeviceInfo("SC_WTIH_2CM_DEVICEID");
		/*actDeviceInfo=getEditedDevicesInfo("SC_WTIH_2CM_DEVICEID");*/		
		//store the CM1 changes into a different lsit and verify that this info shld exist
		ArrayList<String> cm1DeviceInfo=(ArrayList<String>) lstRenamedDeviceInfo.clone(); 
		logoutMyAutodeskPortal();					
		/*end of Base state*/
		
		lstRenamedDeviceInfo=new ArrayList<>();
		USERNAME=getUserCredentials("SC_WTIH_2CM")[0];			
		PASSWORD=getUserCredentials("SC_WTIH_2CM")[1];
		String CM1_2CMUserName=getUserCredentials("CM1_WTIH_2CM")[0];
		String CM2_2CMUserName=getUserCredentials("CM2_WTIH_2CM")[0];		
		Util.printInfo("Verify that the device information changed by SC :"+USERNAME+" under 2 CM on same device should be seen by all : SC :"+USERNAME+" ,CM1 :"+CM1_2CMUserName+", CM2 : "+CM2_2CMUserName);		
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("SC under 2 CM is going to change the device alias info");
		changeTheDeviceInfo("SC_WTIH_2CM_DEVICEID");
		logoutMyAutodeskPortal();					
		
		Util.printInfo("=========================================================================");
		//Now the CM2  should see those changes not Cm1
		Util.printInfo("CM2: "+CM2_2CMUserName+ " is logging in and expected to see the changes done by SC");
		USERNAME=getUserCredentials("CM2_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("CM2_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the CM :"+USERNAME+"is able to see Device information when SC has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_2CM_DEVICEID");
		assertTrueWithFlags("The logged in CM2 user: "+USERNAME+"  should see the alias device Name, Description and last user changes done by SC user ", lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		Util.printInfo("=========================================================================");
		Util.printInfo("CM1: "+CM1_2CMUserName+ " is logging in and expected not to see the changes done by SC");
		USERNAME=getUserCredentials("CM1_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("CM1_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the CM1 :"+USERNAME+"is should not see the Device information when SC has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_2CM_DEVICEID");
		assertFalseWithFlags("The logged in CM1 user: "+USERNAME+"  should not see the alias device Name, Description and last user changes done by SC user", lstRenamedDeviceInfo.equals(actDeviceInfo));
		assertTrueWithFlags("The logged in CM1 user: "+USERNAME+"  should see self modified alias device Name, Description and last user changes done by CM1 user", actDeviceInfo.equals(cm1DeviceInfo));
		logoutMyAutodeskPortal();
		Util.printInfo("=========================================================================");
		

		/*TO DO************************	
			
		//*********************************/
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
		
		Util.printInfo("=============================TC 345============================================");
		Util.printInfo("Verify that, when SC1 changes the device alias info, CM1 and SC1 should see changes but not CM2 and SC2" );
		
		USERNAME=getUserCredentials("TC345_SC1_WTIH_2CM")[0];
		PASSWORD=getUserCredentials("TC345_SC1_WTIH_2CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);		
		String cm1_2cm_UserName=getUserCredentials("TC345_CM1_WTIH_2CM")[0];
		sc2_2cm_UserName=getUserCredentials("TC345_SC2_WTIH_2CM")[0];
		cm2_2cm_UserName=getUserCredentials("TC345_CM2_WTIH_2CM")[0];
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
		
		//TC 347 .
		lstRenamedDeviceInfo=new ArrayList<>();
		Util.printInfo("====================================TC 347=====================================");
		//Now the SC1 is going to change the device , SC1 and Cm1 shld see
		USERNAME=getUserCredentials("CM_WTIH_CM_")[0];
		PASSWORD=getUserCredentials("CM_WTIH_CM_")[1];
		String sc1_cm_UserName=getUserCredentials("SC1_WTIH_CM")[0];
		String sc2_cm_UserName=getUserCredentials("SC2_WTIH_CM")[0];
		Util.printInfo("Verify that the SC1: "+sc1_cm_UserName+", SC2: "+sc2_cm_UserName+" is going to see the device alias info done by CM:" +USERNAME);
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);		
		Util.printInfo(" CM :"+USERNAME+"is going to change the Device alias names");
		changeTheDeviceInfo("SC_WTIH_CM1_CM2_DEVICEID");	//applicable for Device1 and SC1		
		logoutMyAutodeskPortal();		
		
		Util.printInfo("SC1: "+sc1_cm_UserName+ " is logging in and expected to see the changes done by CM");
		USERNAME=getUserCredentials("SC1_WTIH_CM")[0];
		PASSWORD=getUserCredentials("SC1_WTIH_CM")[1];
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the SC1 :"+USERNAME+"  should see the modified Device information when CM has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_CM1_CM2_DEVICEID");
		String cm_sc1_sc2_Username=getUserCredentials("CM_WTIH_CM_")[0];
		assertTrueWithFlags("The logged in SC1 user: "+USERNAME+"  should see modified alias device Name, Description and last user changes done by CM user :"+cm_sc1_sc2_Username,lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		Util.printInfo("=========================================================================");
		Util.printInfo("Now check the same info can be seen by SC2 tagged to same CM");
		USERNAME=getUserCredentials("SC2_WTIH_CM")[0];
		PASSWORD=getUserCredentials("SC2_WTIH_CM")[1];		
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("SC2:"+USERNAME+" is going to login and expected to see those changes done by CM1:"+cm_sc1_sc2_Username);
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_CM1_CM2_DEVICEID");
		assertTrueWithFlags("The logged in SC2 user: "+USERNAME+"  is seeing the alias device Name, Description and last user changes done by CM user :"+cm_sc1_sc2_Username,lstRenamedDeviceInfo.equals(actDeviceInfo));
		
		logoutMyAutodeskPortal();
		
		Util.printInfo("=========================================================================");
		//TC348 
		lstRenamedDeviceInfo=new ArrayList<>();
		Util.printInfo("====================================TC 348 =====================================");
		//Now the SC1 is going to change the device , SC1 and Cm1 shld see
		USERNAME=getUserCredentials("SC1_WTIH_CM")[0];
		PASSWORD=getUserCredentials("SC1_WTIH_CM")[1];
		String sc2_with_CMUsername=getUserCredentials("SC2_WTIH_CM")[0];
		String cm_with_2SCUsername=getUserCredentials("CM_WTIH_CM")[0];
		Util.printInfo("Verify that, when SC1 user: "+USERNAME+" done changes the alias device info, the CM :"+cm_with_2SCUsername+" and SC2 :"+sc2_with_CMUsername+" should see the expected changes done by SC1");
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo(" SC :"+USERNAME+"is going to change the Device alias names");
		changeTheDeviceInfo("SC_WTIH_CM1_CM2_DEVICEID");	//applicable for Device1 and SC1		
		logoutMyAutodeskPortal();	
		
		//Now login with CM and check if the CM can see the device alias names done by SC
		
		USERNAME=getUserCredentials("CM_WTIH_CM")[0];
		PASSWORD=getUserCredentials("CM_WTIH_CM")[1];
		String sc1_with_CMusername=getUserCredentials("SC1_WTIH_CM")[0];
		Util.printInfo("CM: "+USERNAME+ " is logging in and expected to see the changes done by SC");
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the CM :"+USERNAME+"  should see the modified Device alias information when SC has changed it");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_CM1_CM2_DEVICEID");		
		assertTrueWithFlags("The logged in CM user: "+USERNAME+"  should see modified alias device Name, Description and last user changes done by SC user :"+sc1_with_CMusername,lstRenamedDeviceInfo.equals(actDeviceInfo));
		logoutMyAutodeskPortal();
		
		//now login with SC2 and check if SC2 can see the changes done by SC1
		USERNAME=getUserCredentials("SC2_WTIH_CM")[0];
		PASSWORD=getUserCredentials("SC2_WTIH_CM")[1];		
		Util.printInfo("SC2: "+USERNAME+ " is logging in and expected to see the changes done by SC1");
		LoginAndGoToManageDevicesPage(USERNAME, PASSWORD);
		Util.printInfo("Verify that the SC2 :"+USERNAME+"  should see the modified Device alias information when SC1 has changed it");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_CM1_CM2_DEVICEID");		
		assertTrueWithFlags("The logged in SC2 user: "+USERNAME+"  should see modified alias device Name, Description and last user changes done by SC1 user :"+sc1_with_CMusername,lstRenamedDeviceInfo.equals(actDeviceInfo));
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
			String jqueryScript="$('td[class=\"editable\"][data-name=\""+deviceDynamicLocs[i]+"\"]').each(function(index){ if(index=="+indexToClick+"){ $(this).click();}});\r" + 
						" $('input[placeholder=\""+liLocatorForJscript+"\"]').val(\""+renameInfo+"\");  $('input[placeholder=\""+liLocatorForJscript+"\"]').trigger(\"change\"); ";
			Util.printInfo("Changing the Device information for the field :"+optionName);
			lstDevices=productUpdatePage.getMultipleWebElementsfromField(deviceCompnameLoc);	
			Util.sleep(1000);
			lstDevices.get(j).click();
			Util.sleep(900);
			//collapse it again.
			lstDevices.get(j).click();
			Util.sleep(1000);
			//check that the new name pop up is displayed				
			Util.printInfo("Entering the new name as "+renameInfo+" for the Device for "+optionName);
			((JavascriptExecutor)(driver)).executeScript(jqueryScript);	//do this only for first row hence index is set 0;				
			Util.sleep(900);
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
		String theDeviceIdValue= testProperties.getConstant(deviceidName);
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

