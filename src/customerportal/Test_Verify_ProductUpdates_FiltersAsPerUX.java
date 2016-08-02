package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Util;

public class Test_Verify_ProductUpdates_FiltersAsPerUX extends CustomerPortalTestBase{
	
	private ArrayList<String> lsExpGrpHeaders= null;
	private ArrayList<String> lsActGrpHeaders= null;
	private boolean scrollBarExist=false;
	public String USERNAME = null;
	public String PASSWORD = null;
	
	public Test_Verify_ProductUpdates_FiltersAsPerUX() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_ProductUpdates_FiltersAsPerUX() throws Exception {
		//Get the Login credentials from Jenkins if provided else go with one given in properties file
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Data From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("************************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			if (getEnvironment().equalsIgnoreCase("dev")) {
				USERNAME = testProperties.getConstant("USER_NAME");				
				PASSWORD = testProperties.getConstant("PASSWORD");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");				
			}
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);
					
		}
		GoToProductUpdatesPage();
		Util.printInfo("Verifying that clicking on Filters button the filter panel shows up");
		productUpdatePage.click("filterField");
		Util.sleep(1000);
		assertTrue("The Filter Panel is displayed when clicked on FIlter button",productUpdatePage.verifyFieldExists("filterPanelHeader"));

		//Verify that Filter panel is as expected according to UX.	
		Util.printInfo("************************************************************************************************************");
		Util.printInfo("Verify that Filter panel is as per the UX spec");
		Util.printInfo("Verifying that the Filter Panel contains Clear all link");
		productUpdatePage.verifyFieldVisible("filterPanelClearAllBtn");
		Util.printInfo("Verifying that the Filter Panel contains close button");
		productUpdatePage.verifyFieldVisible("filterPanelCloseBtn");
		//Verify that scroll bar is displayed for the items if max-height of filter panel view port is more than 200
		Util.printInfo("Verify that scroll bar exists in Filter Panel if the filter panel overview contains more filter items");
		productUpdatePage.isFieldPresent("filterPanelOverView");
		if (Integer.valueOf(productUpdatePage.getDOMAttributeOfWebElement(productUpdatePage.getCurrentWebElement(), "clientHeight"))>200){
			//then scroll bar exists
			Util.printInfo("Verify that scroll bar exists in Filter Panel as there are many no.of filter panel items");
			productUpdatePage.verifyFieldVisible("filterScrollBar");
			scrollBarExist=true;
		}else{
			Util.printInfo("Verify that scroll bar should not exists in Filter Panel as there are quite less no.of filter panel items");
			productUpdatePage.verifyFieldNotVisible("filterScrollBar");	
		}
		Util.printInfo("Verifying that the Filter Panel contains Filter group headers as expected");
		String newFilterGrplist= testProperties.getConstant("FILTERGROUPHEADERS");
		lsExpGrpHeaders= new ArrayList<>(Arrays.asList(newFilterGrplist.split(",")));
		if(scrollBarExist){
		//fire java script to scroll down the bar, otherwise all the filter group headers will not extracted. 
			scrollFilterScrollBar("110px");
		}
		String[] arrGroupHeaders= productUpdatePage.getMultipleTextValuesfromField("filterGroupHeaders");
		lsActGrpHeaders= new ArrayList<>(Arrays.asList(arrGroupHeaders));
		assertEqualsWithFlags(lsActGrpHeaders, lsExpGrpHeaders, true);
		
		Util.printInfo("Verifying that the Filter Panel group headers are in bold ");
		assertTrue("The Filter panel Group headers font text is 'Bold'", isFilterPanelGroupHeadersFontTextisBold());
		//now try to collapse and expand the of the group header filter : Dates and see that the check boxes are hidden
		//move the scroll bar back to top if scrollbarexist 
		if (scrollBarExist){ 
			scrollFilterScrollBar("-100px");
		}
		verifyFilterGroupHeadersAndItsAssociatedCheckboxesFeaturesInFilterPanel();
		//verify if the filterpanel closes when cliced on close btn and filter icon
		Util.printInfo("Verify that the Filter panel closes when clicked on 'X' button in filter Panel");		
		productUpdatePage.click("filterPanelCloseBtn");
		assertFalse("The Filter panel is not displayed when clicked on 'X' close btn", productUpdatePage.isFieldVisible("filterPanelOverView"));
		
		//relaunch the filter panel
		productUpdatePage.click("filterField");		
		Util.printInfo("Verify that the Filter panel closes when clicked on Filter button in filter Panel");
		productUpdatePage.click("filterField");
		assertFalse("The Filter panel is not displayed when clicked on Filter btn", productUpdatePage.isFieldVisible("filterPanelOverView"));
		logoutMyAutodeskPortal();
		
	}

	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
}
