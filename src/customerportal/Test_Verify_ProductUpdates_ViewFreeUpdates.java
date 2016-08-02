package customerportal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.EISTestBase;

public class Test_Verify_ProductUpdates_ViewFreeUpdates extends CustomerPortalTestBase{
		
		private ArrayList<String> lsUserCreds= null;
		private String userName=null;
		private String passWord=null;
		public String USERNAME = null;
		public String PASSWORD = null;
		
		public Test_Verify_ProductUpdates_ViewFreeUpdates() throws IOException {
			super("Browser",getAppBrowser());		
		}
		
		@Before
		public void setUp() throws Exception {
			launchMyAutodeskPortal(getBaseURL());
		}
		
		@Test
		public void Test_Verify_ProductUpdates_ViewFreeUpdates_EndUserAndAdmin() throws Exception {
			//This is used to override the user name and password given in the test properties
			String allUserCreds=null;
			if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
				System.out.println("Found creds from jenkins");
				System.out.println("************************************************************************************");
				System.out.println("Data From Jenkins" );
				System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
				System.out.println("Password:"+System.getProperty("Password_jenkins") );
				System.out.println("************************************************************************************");
				userName=System.getProperty("UserName_jenkins");
				passWord=System.getProperty("Password_jenkins");
				loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
			}else{
				//default login with CM user and it got updates
				if (getEnvironment().equalsIgnoreCase("dev")) {
					USERNAME = testProperties.getConstant("CM_USER_NAME");				
					PASSWORD = testProperties.getConstant("CM_PASSWORD");
				} else if (getEnvironment().equalsIgnoreCase("stg")) {
					USERNAME = testProperties.getConstant("USER_NAME_STG");
					PASSWORD = testProperties.getConstant("PASSWORD_STG");				
				}
				loginAsMyAutodeskPortalUser(USERNAME , PASSWORD);			
				userName=USERNAME;
				passWord=PASSWORD;
			}
			GoToProductUpdatesPage();
			//get the list of User ID and iterate for all of them
			//Initially check with Contract mgr and then followed by other users
			isFreeUpdatesAvailableInProductUpdates(userName,"Contract Manager");
			logoutMyAutodeskPortal();			
			//for dev & stg do condtional logic
			if (getEnvironment().equalsIgnoreCase("DEV")){
				allUserCreds =testProperties.getConstant("LOGINCREDENTIALS_DEV");
			}else{
				allUserCreds= testProperties.getConstant("LOGINCREDENTIALS_STG");
			}
			
			lsUserCreds= new ArrayList<>(Arrays.asList(allUserCreds.split(",")));
			for(int i=0;i<lsUserCreds.size()-1;i+=3){
				String lUserName=lsUserCreds.get(i+1);
				String lPassword=lsUserCreds.get(i+2);
				loginAsMyAutodeskPortalUser(lUserName,lPassword);
				GoToProductUpdatesPage();
				isFreeUpdatesAvailableInProductUpdates(lsUserCreds.get(i+1),lsUserCreds.get(i));
				logoutMyAutodeskPortal();
			}
		}

	
		@After
		public void tearDown() throws Exception {
			driver.quit();
			finish();
	}

}

