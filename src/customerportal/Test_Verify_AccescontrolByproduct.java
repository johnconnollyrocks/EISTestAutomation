package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.Util;

public class Test_Verify_AccescontrolByproduct extends CustomerPortalTestBase {
	public String USERNAME = null;
	public String PASSWORD = null;
	private String sortByOption="Type"; 
	private ArrayList<String> lsactgrpFacetOptions= null;
	private ArrayList<String> lsexpGrpFacetoptions=null;
	private List<WebElement> productversionOfilterCheckboxLst=null;
	
	public Test_Verify_AccescontrolByproduct() throws IOException {
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
			USERNAME=getUserCredentials("ACCESSCONTROLBYPRODUCT")[0];
			PASSWORD=getUserCredentials("ACCESSCONTROLBYPRODUCT")[1];
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);	
					
		}
		
		mainWindow.select();
		Util.sleep(3000);
		GoToProductUpdatesPage();
		Util.sleep(2000);
		productUpdatePage.click("sortByDropdownBtn");
		Util.sleep(200);
		String FilterByProduct=productUpdatePage.getValueFromGUI("filterByProduct");
		String SortbyProduct=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectSortbyValueInDropdown", FilterByProduct);
		Util.printInfo("Verifying that Sortby changes upon selection");
		productUpdatePage.hoverOver(SortbyProduct);
		productUpdatePage.clickUsingLowLevelActions(SortbyProduct);
		Util.sleep(300);
		assertTrueWithFlags("Access control buttton is present after filtering by Proudct", productUpdatePage.isFieldVisible("filterproductAccessControl"));
		productUpdatePage.click("filterField");
		assertTrue("The Filter Panel is displayed when clicked on Filter button",productUpdatePage.verifyFieldExists("filterPanelHeader"));
		scrollFilterScrollBar("110px");
		assertTrueWithFlags("product version Ofilter is present after filtering by Proudct", productUpdatePage.isFieldVisible("productversionOfilter"));
		
		productversionOfilterCheckboxLst=productUpdatePage.getMultipleWebElementsfromField("productversionOfilterCheckboxLst");
		for(int i=0;i<productversionOfilterCheckboxLst.size();i++){
			String filterCheckbxName=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productversionOfilterCheckbox", productversionOfilterCheckboxLst.get(i).getText());
			productUpdatePage.check(filterCheckbxName);
			Util.sleep(300);
			assertTrueWithFlags("appliedFilters Label is present after filtering by Proudct +version", productUpdatePage.isFieldVisible("appliedFiltersLabel"));
			String productVersionAppliedFilterPill=productUpdatePage.getValueFromGUI("productVersionAppliedFilterPill");
			String filterCheckbxName1=productversionOfilterCheckboxLst.get(i).getText();
			Util.printInfo("filterCheckbxName1"+filterCheckbxName1);
			assertEquals(String.valueOf(filterCheckbxName1), productVersionAppliedFilterPill);
			productUpdatePage.uncheck(filterCheckbxName);
			Util.sleep(300);// to avoid race condition
			assertFalseWithFlags("appliedFilters ahould not be present when we uncheck the checkbox", productUpdatePage.isFieldVisible("appliedFiltersLabel"));
			productUpdatePage.check(filterCheckbxName);
			Util.sleep(300);
			productUpdatePage.click("deleteTheAppliedFilterPills");
			Util.sleep(300);
			assertFalseWithFlags("appliedFilters pill should not be present once deleted", productUpdatePage.isFieldVisible("productVersionAppliedFilterPill"));
			productUpdatePage.check(filterCheckbxName);
			Util.sleep(300);
			assertTrueWithFlags("Product version checkbox is selected ", productUpdatePage.isChecked(filterCheckbxName));
			productUpdatePage.click("filterPanelClearAllBtn");
			Util.sleep(300);
			assertFalseWithFlags("appliedFilters are all unchecked afteronce clicked on clear all ", productUpdatePage.isChecked(filterCheckbxName));
			productUpdatePage.click("collapseProductVersion");
			Util.sleep(300);
			assertTrueWithFlags("version check box shud be collapsed when clciked on the arrow", productUpdatePage.isFieldVisible("productversionCollapsed"));
			productUpdatePage.click("collapseProductVersion");
		}
		productUpdatePage.click("filterField");
		assertTrue("The Filter Panel is displayed when clicked on Filter button",productUpdatePage.verifyFieldExists("filterPanelHeader"));
		productUpdatePage.check("checkTheProductVersionCheckBox");
		selectSortbyInProductUpdates(sortByOption);
		String filterCheckbxFacetoptNames=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filtercheckboxfacetOptionNames", sortByOption);
		lsactgrpFacetOptions=new ArrayList<>(Arrays.asList(productUpdatePage.getMultipleTextValuesfromField(filterCheckbxFacetoptNames)));

		//get the expected DATEFILTERGROUPFACETOPTIONNAMES
		lsexpGrpFacetoptions=new ArrayList<>(Arrays.asList(testProperties.getConstant(sortByOption.toUpperCase()+"FILTERGROUPFACETOPTIONNAMES").split(",")));
		assertEqualsWithFlags(lsactgrpFacetOptions,lsexpGrpFacetoptions,true);
}
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
}
}

