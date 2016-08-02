package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ProductUpdates_FiltersBasedOnDate extends CustomerPortalTestBase {
	private ArrayList<String> lsactgrpFacetOptions= null;
	private ArrayList<String> lsexpGrpFacetoptions=null;
	private ArrayList<String> lsActGrpHeaders= null;
	private ArrayList<String> lsactProdUpdFacetOptNames=null;
	private ArrayList<String> lstFilterPillBtns=null;
	private String sortByOption="Date";
	private String appliedFilterslabel=testProperties.getConstant("APPLIEDFILTERSTEXT");
	private String filterSelectedGrpName="Date";
	
	
	public Test_Verify_ProductUpdates_FiltersBasedOnDate() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_ProductUpdates_FiltersBasedOnTheDate() throws Exception {
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
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));		
		}
		GoToProductUpdatesPage();
		//select Sort by field as Date
		Util.printInfo("Select the Sort by Option as 'Date' ");		
		selectSortbyInProductUpdates(sortByOption);
		//first get the no.of updates are showing up for specific sort by option		
		int iNoOfUpdates= getNoOfProductUpdatesWrapperGroup();
		Util.printInfo("Verifying that clicking on Filters button the filter panel shows up");
		productUpdatePage.click("filterField");
		assertTrue("The Filter Panel is displayed when clicked on Filter button",productUpdatePage.verifyFieldExists("filterPanelHeader"));
		//get the no of updates and check if the no of checkboxes matches under the specific group
		Util.printInfo("Verifying that the total no. of checkboxes displayed under Filter group: "+filterSelectedGrpName+" in Filter Panel matches with Product updates shown for Sort by option: "+sortByOption);
		String newFilterGrouploc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterGrpCheckboxes", sortByOption);
		List<WebElement> noOfCheckboxesInFilterPanel=productUpdatePage.getMultipleWebElementsfromField(newFilterGrouploc);
		assertTrue("The number of checkboxes displayed under the Filter group: "+filterSelectedGrpName+" in Filter Panel matches with product updates shown for Sort by option: "+sortByOption, (noOfCheckboxesInFilterPanel.size()==iNoOfUpdates));
		//get the facet -option name in the filter panel matches with Product updates groups
		Util.printInfo("Verify that the Filter facet option names for specific filter Group: "+ filterSelectedGrpName+" should be as expected");
		String filterCheckbxFacetoptNames=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filtercheckboxfacetOptionNames", filterSelectedGrpName);
		lsactgrpFacetOptions=new ArrayList<>(Arrays.asList(productUpdatePage.getMultipleTextValuesfromField(filterCheckbxFacetoptNames)));
		//get the expected DATEFILTERGROUPFACETOPTIONNAMES
		lsexpGrpFacetoptions=new ArrayList<>(Arrays.asList(testProperties.getConstant("DATEFILTERGROUPFACETOPTIONNAMES").split(",")));
		assertEqualsWithFlags(lsactgrpFacetOptions,lsexpGrpFacetoptions,true);
		
		Util.printInfo("Verify that the list of Product updates specific to Sort by option :"+sortByOption+" should match with available filters for Filter group: "+filterSelectedGrpName);
		String[] lsGroupHeaders=productUpdatePage.getMultipleTextValuesfromField("filterGroupHeading");
		ArrayList<String> lstactualProductUpdategrpHeadings= new ArrayList<>(Arrays.asList(lsGroupHeaders));
		EISTestBase.setVerifyCaseSensitive(false);
		assertEqualsWithFlags(lsactgrpFacetOptions,lstactualProductUpdategrpHeadings);
		//now verify when checked the results should be filtered
		ArrayList<String> filterFaceOptionsNames=new ArrayList<>();		
		boolean filterApplied=false;
		String facetOptionName="";
		for(int i=0;i<lsactgrpFacetOptions.size();i++){
		filterApplied=false;
		String facetOptName=lsactgrpFacetOptions.get(i);;
		if(i>0){
			facetOptionName=facetOptionName+","+facetOptName;
		}else{			
			facetOptionName=facetOptName;
		}
		 filterFaceOptionsNames.add(facetOptName);	//put into the list
		 
		 String newFilterChckbox= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterChckboxWithFaceName", filterSelectedGrpName,facetOptName);
		 //check only filtered results are only showing up
		 //check the checkbox here
		 productUpdatePage.check(newFilterChckbox);
		 
		 Util.printInfo("Verify that only filtered Product updates are displayed for specific filtered check box:"+facetOptionName);
		 int iFilteredNoOfUpdates = getNoOfProductUpdatesWrapperGroup();
		 String[] prodUpdateGrpName=productUpdatePage.getMultipleTextValuesfromField("filterGroupHeading");
		 
		 ArrayList<String> lsProdUpdateGrpheadings= new ArrayList<>(Arrays.asList(prodUpdateGrpName));
		 
		 assertTrueWithFlags("Product Updates", facetOptionName+": Filter Checkbox", "fitered Product updates should be displayed correctly based on the selected Filter options",
				 (prodUpdateGrpName.length==iFilteredNoOfUpdates)
				   && 				  
				   checkIfExpListContainsTheActualItems(lsProdUpdateGrpheadings,filterFaceOptionsNames));
				   
		
		 //check if the applied filters no of results should match with Filter panel and product updates group number
		 Util.printInfo("Verify that the no of filter results in Filter panel matches with the number of product updates displayed"); 
		 int igetNoOfFilterResults=getNoOfFilterResultsDisplayedInFilterPanel();
		 int iactNoOfProdUpdates=getNoOfProductUpdatesDisplayedInPage();
		 assertEquals(iactNoOfProdUpdates, igetNoOfFilterResults);
		 
		 //check Applied Filters label is showing up
		 Util.printInfo("Verify that Applied filters pill header displayed for the selected Filter group: "+filterSelectedGrpName);
		 String appFiltersLabel=productUpdatePage.getTextFromLink("appliedFiltersLabel");
		 appFiltersLabel=appFiltersLabel.substring(0,appFiltersLabel.length()-1); //remove ":"
		 assertTrue("The Applied filter pill header is displayed for Filter group: "+filterSelectedGrpName,appFiltersLabel.equalsIgnoreCase(appliedFilterslabel));
		 
		 //now verify if the applied filters pill is showing up
		 Util.printInfo("Verify that, for the Filter group: "+filterSelectedGrpName + ", applied filter pill item for the checked filter checkbox is displayed on Applited filters row in Product updates page");
		 String[] actprodUpdateFacetoptionsNames=productUpdatePage.getMultipleTextValuesfromField("appliedFiltersFacetOptNames");
		 lsactProdUpdFacetOptNames=new ArrayList<>(Arrays.asList(actprodUpdateFacetoptionsNames));
		 assertEqualsWithFlags(lstactualProductUpdategrpHeadings, filterFaceOptionsNames,true);
		 filterApplied=true;
		}
		//start unchecking all the pills i.e applied filters and see the gui update according to that.'
		if(filterApplied){
			facetOptionName="";			
			for(int i=0;i<filterFaceOptionsNames.size();i++){
			 	String facetOptName=filterFaceOptionsNames.get(i);
			 	if(i>0){
					facetOptionName=facetOptionName+","+facetOptName;
				}else{					
					facetOptionName=facetOptName;
				}
				Util.printInfo("Verify that The check box :"+facetOptionName+" in the Filter Panel should be unchecked when the user clicks on "+facetOptionName+" pill close button in Applied filters row");
				String filterPillLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("allAppFiltersFacetOptsCloseBtn", facetOptName);			
				productUpdatePage.click(filterPillLoc);//click on close btn of the pill on applied filter row			
				String newFilterChckbox= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterChckboxWithFaceName", filterSelectedGrpName,facetOptName);
				assertFalse("The check box(s) :"+facetOptionName+ " in the Filter Panel should be unchecked when the user clicks on "+facetOptionName+" pill close button(s) in Applied filters row",
							productUpdatePage.isChecked(newFilterChckbox));

				//check the results gets updated accordingly.
				Util.printInfo("Verify that when unchecked the filters in the filter panel, the no of Filter results in Filter panel matches with the number of product updates displayed");
				 int igetNoOfFilterResults=getNoOfFilterResultsDisplayedInFilterPanel();
				 int iactNoOfProdUpdates=getNoOfProductUpdatesDisplayedInPage();
				 assertEquals(iactNoOfProdUpdates, igetNoOfFilterResults);
			}
			//now verify if clearAll works as expected
			 facetOptionName="";
			for(int i=0;i<filterFaceOptionsNames.size();i++){			 	
				String facetOptName=filterFaceOptionsNames.get(i);
				if(i>0){
					facetOptionName=facetOptionName+","+facetOptName;
				}else{					
					facetOptionName=facetOptName;
				}
				String newFilterChckbox= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterChckboxWithFaceName", filterSelectedGrpName,facetOptName);
				//Check all the check boxes now under the group and check if clear all works as expected
				productUpdatePage.check(newFilterChckbox);
			}
			//This clears all the selections done before and verify none of the pills shld display and checkboxes shld be unchecked
			//****************CLEAR ALL functionality************************************************
			productUpdatePage.click("filterPanelClearAllBtn");	
			Util.printInfo("Verify that the applied filter pill items are not showing up on Applited filters row in Product updates page when clicked on Clear All link for Filter group: "+filterSelectedGrpName);
		    assertFalse("The applied filter pills should not displayed",productUpdatePage.isFieldPresent("appliedFiltersFacetOptNames"));
		    
		    Util.printInfo("Verify that the Filter panel results are not displayed when clicked on Clear All link for Filter group: "+filterSelectedGrpName);
		    assertFalse("The Filter Panel results should not displayed",productUpdatePage.isFieldPresent("filterProdUpdatesResults"));
		    
		    //iterate again through the loop and check if filter checkboxes are unchecked when clear all is clicked
		    facetOptionName="";
		    for(int i=0;i<filterFaceOptionsNames.size();i++){
		    	String facetOptName=filterFaceOptionsNames.get(i);;
		    	if(i>0){
					facetOptionName=facetOptionName+","+facetOptName;
				}else{					
					facetOptionName=filterFaceOptionsNames.get(i);			
				}
				
				Util.printInfo("Verify that The check box :"+facetOptionName+" in the Filter Panel should be unchecked when the user clicks on 'Clear All' link for the filter Group :"+filterSelectedGrpName);
				String newFilterChckbox= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterChckboxWithFaceName", filterSelectedGrpName,facetOptName);
				assertFalse("The check box(s) :"+facetOptionName+ " in the Filter Panel should be unchecked when the user clicks on 'Clear All' link for the filter Group :"+filterSelectedGrpName,
							productUpdatePage.isChecked(newFilterChckbox));
		    }
		    
		}
		else{
			Util.printError("Something went wrong while applying the Filters for Filter group: " +filterSelectedGrpName);
		}
		Util.printInfo("Closing the filter panel by clicking on 'X' btn");
		productUpdatePage.click("filterPanelCloseBtn");
		
		logoutMyAutodeskPortal();
		
	}

	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
	
}
