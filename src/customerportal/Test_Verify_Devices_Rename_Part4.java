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

public class Test_Verify_Devices_Rename_Part4 extends CustomerPortalTestBase {

	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String deviceName = null;
	public String deviceColLoc ="compName";
	private ArrayList<String> lstRenamedDeviceInfo=null;
	private String deviceDynamicLocators =null;
	
	public Test_Verify_Devices_Rename_Part4() throws Exception {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void testAbilityToRenameTheDevices_Part4() throws Exception {
		String CMUserName=null;
		
			//THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FRdM JENKINS , as this test involved verification of the data with DIFFERENT USER ACCOUNTS 
		System.out.println("**********THIS TEST DOES NOT ACCEPT ANY ARGUMENTS FROM JENKINS AS THIS TEST INVOLVES VERIFICATION OF DATA WITH DIFFERENT ACCOUNTS");
		Util.printInfo("=======================Test_Verify_Devices_Rename_Part4==========================================");
		ArrayList<String> actDeviceInfo= new ArrayList<>();
		
		//TC 347 .
		lstRenamedDeviceInfo=new ArrayList<>();
		Util.printInfo("====================================TC 347=====================================");
		//Now the SC1 is going to change the device , SC1 and Cm1 shld see
		USERNAME=getUserCredentials("CM_WTIH_CM")[0];
		PASSWORD=getUserCredentials("CM_WTIH_CM")[1];
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
		Util.sleep(3000);
		Util.printInfo("Verify that the SC1 :"+USERNAME+"  should see the modified Device information when CM has changed the Device alias names");
		actDeviceInfo= getEditedDevicesInfo("SC_WTIH_CM1_CM2_DEVICEID");
		String cm_sc1_sc2_Username=getUserCredentials("CM_WTIH_CM")[0];
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
		 deviceDynamicLocators=testProperties.getConstant("LOCATORSDYNAMICTOKENS");
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

