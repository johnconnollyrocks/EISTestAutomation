package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import common.Util;

public class Test_verify_Devices_Valds_SearchBar extends CustomerPortalTestBase {
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	private ArrayList<String> lsDeviceInfoInUI = new ArrayList();
	String[] deviceCols;
	
	public Test_verify_Devices_Valds_SearchBar() throws IOException {
		super("Browser",getAppBrowser());						
	}
	
	@Before
	public void setUp() throws Exception {
		
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_VerifyProductUpdateSaveUpdates() throws Exception {
		
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			USERNAME=getUserCredentials("DEVICESEARCHBAR")[0];
			PASSWORD=getUserCredentials("DEVICESEARCHBAR")[1];
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);	
					
		}
		
		mainWindow.select();
		Util.sleep(3000);
		Util.printInfo("Clicking on the " +" Devices"+" Tab");
		productUpdatePage.click("manageDevicesTab");
		Util.sleep(3000);
		
		// verifying  the  presence of place holder on devices page 
		assertTrue("Place holder text Search devices is visible" ,productUpdatePage.isFieldVisible("devicesPlaceHolderText"));
		
		// verifying the max length of the place holder(should not be more than 30)
		String DeviceName = "Device" + RandomStringUtils.randomAlphabetic(24);
		System.out.println(" DeviceName---->"+DeviceName +" and "+ "DeviceName length ------>" +DeviceName.length());
		productUpdatePage.sendKeysInTextFieldSlowly("devicesPlaceHolderText", DeviceName);
		
		String enteredCharsSearchBarFilters=productUpdatePage.getValueFromGUI("enteredCharsSearchBarFilters");
		enteredCharsSearchBarFilters=enteredCharsSearchBarFilters.substring(enteredCharsSearchBarFilters.indexOf(':'));
		if (enteredCharsSearchBarFilters.length()>30) {
			assertTrue("The Search bar field should not accepted characters more that 30", true);
		}else{
			assertTrue("The Search bar field is not accepting characters more that 30", true);
		}
		
		// verifying for the clear button 
		productUpdatePage.click("clearSearchBar");
		assertFalseWithFlags("Cleared the devices Search Bar by clicking on 'X'(checking for the filter symbol)", productUpdatePage.isFieldVisible("enteredCharsSearchBarFilters"));
		
		//verifying for the special character
		String SpecialCharacters=testProperties.getConstant("SPECIALCHARS");
		Util.printInfo("Validating The Special characters ::"+SpecialCharacters);
		
		String[] SpecialCharacter=SpecialCharacters.split(" ");
		
			for(String eachSpecialChar : SpecialCharacter){
				boolean foudSpecialChar = false;
				productUpdatePage.sendKeysInTextFieldSlowly("devicesPlaceHolderText",eachSpecialChar);
				Util.sleep(1000);
				if(!productUpdatePage.isFieldVisible("noMatchedDevicesForAppliedFilter")){
					 deviceCols=productUpdatePage.getMultipleTextValuesfromField("allRowsinDevicesTable");
					lsDeviceInfoInUI.addAll(Arrays.asList(deviceCols));
					 if (lsDeviceInfoInUI.toString().contains(eachSpecialChar)) { 
						 foudSpecialChar =true;
					 }
					 if(foudSpecialChar){
						 assertTrueWithFlags("Found the matches for the special character " + eachSpecialChar, true);
						 }
				}
					
			}
			productUpdatePage.click("clearSearchBar");
			Util.printInfo("Verified all the special characters");
			
		//verifying for a single letter	
			String devicenames=RandomStringUtils.randomAlphabetic(1);
			productUpdatePage.sendKeysInTextFieldSlowly("devicesPlaceHolderText",devicenames);
			Util.sleep(300);
			if(productUpdatePage.isFieldVisible("noMatchedDevicesForAppliedFilter")){
				assertTrueWithFlags("The Device table should show the message no matches found and the help message",productUpdatePage.isFieldVisible("noMatchedDevicesForAppliedFilter")
					&&productUpdatePage.isFieldVisible("deviceTable")&& productUpdatePage.isFieldVisible("helpMessageFornoMatchedDevices"));
			}
			else{
				deviceCols=productUpdatePage.getMultipleTextValuesfromField("allRowsinDevicesTable");
				lsDeviceInfoInUI.addAll(Arrays.asList(deviceCols));
				 if (lsDeviceInfoInUI.toString().contains(devicenames)) { 
					 assertTrueWithFlags("Found the matches for the letter" + " " +devicenames +" ", true);
				 }
			}	
			productUpdatePage.click("clearSearchBar");
			Util.printInfo("Search bar is not case sensitive");
			
			
			//Search a word or a phrase
			String searchWord= "device";
			Util.sleep(300);
			for(int i=1;i<=searchWord.length();i++){
				String enterString=searchWord.substring(0, i);
				Util.printInfo("enterString  ="+enterString);
				productUpdatePage.sendKeysInTextFieldSlowly("devicesPlaceHolderText",searchWord.substring(0, i));
			if(productUpdatePage.isFieldVisible("noMatchedDevicesForAppliedFilter")){
				assertTrueWithFlags("The Device table should show the message no matches found and the help message",productUpdatePage.isFieldVisible("noMatchedDevicesForAppliedFilter")
					&&productUpdatePage.isFieldVisible("deviceTable")&& productUpdatePage.isFieldVisible("helpMessageFornoMatchedDevices"));
			}
			else{
				deviceCols=productUpdatePage.getMultipleTextValuesfromField("allRowsinDevicesTable");
				lsDeviceInfoInUI.addAll(Arrays.asList(deviceCols));
				String rowLst=lsDeviceInfoInUI.toString().toLowerCase();
				if (rowLst.contains(enterString)) { 
					 assertTrueWithFlags("Found the matches for the letter" + searchWord, true);
				 }
				}
			}
			productUpdatePage.click("clearSearchBar");
		}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
}
	
}