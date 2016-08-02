package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ManageDevice_FiltersPanel extends CustomerPortalTestBase {
	private ArrayList<String> lsactgrpFacetOptions= null;
	private ArrayList<String> lsexpGrpFacetoptions=null;
	private ArrayList<String> lsActGrpHeaders= null;
	private ArrayList<String> lsactProdUpdFacetOptNames=null;
	private ArrayList<String> lstFilterPillBtns=null;


	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;


	public Test_Verify_ManageDevice_FiltersPanel() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_ManageDevices_FiltersPanelForAllTypes() throws Exception {
		//Get the Login credentials from Jenkins if provided else go with one given in properties file
		LoginAndGoToManageDevicesPage();
		
		productUpdatePage.click("filterDropDown");

		Util.printInfo("Verifying that clicking on Filters button  in the Manage Devices Page the filter panel shows up");		
		assertTrueWithFlags("The Filters Panel shows up when clicked on Filter Button in Manage Devices tab", productUpdatePage.isFieldPresent("filterPanelHeader"));

		//Note: It verifies whether the Filter group headings and checkboxes default behaviour
		Util.printInfo("**************************************************************************************************");
		Util.printInfo("Verify that the Devices Filter Panel group headings and checkboxes default behaviour");
		verifyFilterGroupHeadersAndItsAssociatedCheckboxesFeaturesInFilterPanel();
		Util.printInfo("**************************************************************************************************");
		//Refresh the page as the earlier would have made scrollIntoView calls which makes the test distorted hence refresh
		driver.navigate().refresh();
		waitForThePageToLoad();
		Util.sleep(2000);
		if(productUpdatePage.isFieldVisible("acceptTermsInDevicePage")){			
			productUpdatePage.click("acceptTermsInDevicePage");
		}
		String filterSelectedGrpName="";
		String appliedFilterslabel=testProperties.getConstant("FILTEROPTIONS");
		String filtersLabel	= testProperties.getConstant("APPLIEDFILTERSTEXT");
		String[] filterOptions= appliedFilterslabel.split(",");
		ArrayList<String> lstExpGrpHeaders=new ArrayList<>(Arrays.asList(filterOptions));

		boolean filterScrollBarExists=false;
		String lastuserParseLabel="last user";

		//identify if the scroll bar exists
		productUpdatePage.click("filterDropDown");
		productUpdatePage.isFieldVisible("deviceFilterPanelOverview");
		int filterScrollHeight=Integer.valueOf(productUpdatePage.getDOMAttributeOfWebElement(productUpdatePage.getCurrentWebElement(), "clientHeight"));
		int newScrollhght=10;//an offset

		Util.printInfo("Verify that if the filter Scroll bar exists in Devices filter Panel");

		if (filterScrollHeight>200){			
			Util.printInfo("The Height of the filter panel is less than 200px so no filter scroll bar should  be seen in Devices filter Panel");
			assertTrue("Filter scroll bar should be visible", productUpdatePage.isFieldVisible("filterScrollBar"));
			filterScrollBarExists=true;
		}else{
			Util.printInfo("The Height of the filter panel is less than 200px so no filter scroll bar should not be seen in Devices filter Panel");
			//In case of IE browser ignore this test step
			if (!EISTestBase.getAppBrowser().equalsIgnoreCase("ie")){
				assertFalse("Filter scroll bar should not be visible", productUpdatePage.isFieldVisible("filterScrollBar"));
			}
		}

		//get the no of all check boxes in the Device filters panel
		List<WebElement> allFilterCheckboxes=productUpdatePage.getMultipleWebElementsfromField("allFilterCheckboxes");
		int noofChecboxes=allFilterCheckboxes.size();
		
		//If there are many no of checkbox better do collapse all of them and then pull the elements
		//*************************
		for(int grp=0;grp<filterOptions.length;grp++){
			String newGrpBtnFieldLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterGroupheaderBtns", String.valueOf(grp+1));
			productUpdatePage.click(newGrpBtnFieldLoc);	//collapse all of them and pull the details
		}		
		///*******************
		
		//get the no. of filter group headings in the Filter panel
		productUpdatePage.isFieldVisible("filterGroupHeaders");
		productUpdatePage.scrollIntoViewOfAllMetadataElements(productUpdatePage.getCurrentWebElementList());		
		productUpdatePage.scrollIntoViewOfMetadataElementParentNode(productUpdatePage.getCurrentWebElement());
		String[] actFilterGrpheaders =productUpdatePage.getMultipleTextValuesfromField("filterGroupHeaders");
		ArrayList<String> lstActgrpheaders= new ArrayList<>(Arrays.asList(actFilterGrpheaders));
		Util.printInfo("Verify that the Device filter group names matches with expected list");
		assertEqualsWithFlags(lstActgrpheaders, lstExpGrpHeaders);
		List<WebElement> lsElements=productUpdatePage.getCurrentWebElementList();

		//expand them again
		//*************************
		for(int grp=0;grp<filterOptions.length;grp++){
			String newGrpBtnFieldLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterGroupheaderBtns", String.valueOf(grp+1));
			productUpdatePage.click(newGrpBtnFieldLoc);	//collapse all of them and pull the details
		}
		///*******************
		String[] dynamicLocatorsTokens = testProperties.getConstant("LOCATORSDYNAMICTOKENS").split(",");
		for(int i=0;i<filterOptions.length;i++){
			if (i>0){
				//click on filter Drop down do this only  when i >0
//				productUpdatePage.click("filterDropDown");
				productUpdatePage.getMultipleTextValuesfromField("filterGroupHeaders");
			   lsElements=productUpdatePage.getCurrentWebElementList();
			}
			/*String grpName=lsElements.get(i).getText();	*/ //Filter grp name
			String grpName=filterOptions[i];
			String parseFieldName=dynamicLocatorsTokens[i];		//This is used for Dynamic parse field name
			filterSelectedGrpName=grpName;
			//Now pull the information inside each column and check no of checkboxes under specific group matches
			String deviceColumnData=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesColumnsData", parseFieldName);
			List<WebElement> deviceEles=productUpdatePage.getMultipleWebElementsfromField(deviceColumnData);


			if(filterScrollBarExists){
				String scrollbarHeight=productUpdatePage.getCssPropertyValue("filterScrollBar", "height");
				scrollbarHeight=scrollbarHeight.replace("px", "").trim();				
				int heightofScrollbar=new Double(scrollbarHeight).intValue();

				//get the thumb scroll bar height if the newScrollhght is greater than thumb scroll bar height then scroll down
				newScrollhght+=(filterScrollHeight/noofChecboxes);	

				if (newScrollhght>heightofScrollbar){	
					//move the filter based on the diff between
					int moveScrollheight=newScrollhght-heightofScrollbar;
					scrollFilterScrollBar(String.valueOf(moveScrollheight));	//if the client height of filter scroll bar is 300 and no of elements is 5 then scroll part by part like 300/5						
				}
			}

			//get the no of check boxes under specific group either device or Status or Last User 
			//NOTE: Invalid step, you can have same device name for two users but the Device id is different which will not be shown on table
	/*		String newGrpCheckboxLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterGrpCheckboxes", grpName);
			List<WebElement> grpCheckboxes=productUpdatePage.getMultipleWebElementsfromField(newGrpCheckboxLoc);
			//get the actual no of devices exists in the Devices table and see if it matches
			Util.printInfo("Verify the Items shown under specific group :"+ grpName+" in the filter panel should match with the available items in Device table");
			assertTrueWithFlags("The no of Items under the group :"+grpName+" should match  with the available items in Device table" ,grpCheckboxes.size()==deviceEles.size());*/

			//Also verify the labels of the checkboxes with device names in Device table
			String newGrpCheckboxFieldNamesLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filtercheckboxfacetOptionNames", grpName);
			
			//scroll into view here explicitly do it here instead of breaking other tests
			productUpdatePage.isFieldPresent(newGrpCheckboxFieldNamesLoc);
			productUpdatePage.scrollIntoViewOfAllMetadataElements(productUpdatePage.getCurrentWebElementList());
			
			String[] devicesLabelsInFilterPanel=productUpdatePage.getMultipleTextValuesfromField(newGrpCheckboxFieldNamesLoc);				  
			ArrayList<String> lstActDevicelabelInFilterPanel = new ArrayList<>(Arrays.asList(devicesLabelsInFilterPanel));
			
			//scroll back to parent node
			productUpdatePage.scrollIntoViewOfMetadataElementParentNode(productUpdatePage.getCurrentWebElement());
			//get the actual no of devices exists in the Devices table and see if it matches
			String deviceLabelloc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesColumnsData", parseFieldName);			
			String[] devicesLabelsInDeviceTable=productUpdatePage.getMultipleTextValuesfromField(deviceLabelloc);
			
			ArrayList<String> lstActDevicelabelInDeviceTable = new ArrayList<>(Arrays.asList(devicesLabelsInDeviceTable));

			Util.printInfo("Verify the checkbox label names shown under specific group :"+ grpName+" in the filter panel should match with the available Labels in Device table");

			
			assertEqualsWithFlags(lstActDevicelabelInFilterPanel,lstActDevicelabelInDeviceTable,true);

			//execute jquery to expand the more or less btn links			
			if (productUpdatePage.isFieldVisible("moreBtnLinkInFilterPanelOverView")){				
				List<WebElement> moreBtnsInFiltepanellst=productUpdatePage.getMultipleWebElementsfromField("moreBtnLinkInFilterPanelOverView");
				if (moreBtnsInFiltepanellst!=null){
					String jqueryScript="$('div[class=\"overview\"] button[class=\"btn btn-link more more\"]').each(function(){ $(this).click();});";
					((JavascriptExecutor)(driver)).executeScript(jqueryScript);					
				}			
			}
			ArrayList<String> filterFaceOptionsNames=new ArrayList<>();		
			boolean filterApplied=false;
			int lastUserrow=0;
			
			//Now verify if the filter can be applied
			int countRows=0;
			filterFaceOptionsNames=new ArrayList<>();	
			String facetOptionName="";
			for(int j=0;j<devicesLabelsInFilterPanel.length;j++){
				String facetOptName=devicesLabelsInFilterPanel[j];
				if(j>0){
					facetOptionName=facetOptionName+","+facetOptName;
				}else{			
					facetOptionName=facetOptName;
				}
				filterFaceOptionsNames.add(facetOptName);	//put into the list
				
//				 facetOptName=devicesLabelsInDeviceTable[j];
//				countRows++;
				
				String newFilterChckbox= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterChckboxWithFaceName", grpName,facetOptName);
				if (grpName.equalsIgnoreCase(lastuserParseLabel)){				
					lastUserrow++;
					//In case of last user filters the user name contains domain name hence add escape sequence.
					String newOptName= facetOptName;
					if(facetOptName.contains("\\")){
						facetOptName=facetOptName.replace("\\", "");
					}
					
					//GET THE FIELD LOCATOR and add it to explicitly to fielddata list due to special char in case of last user
					String newobjLoc=productUpdatePage.getFieldLocators(newFilterChckbox).get(0);
					//NOTE: There are cases where string contains \\\\ i.e \\ in that case
					if (newobjLoc.contains("\\")){
						//then replace '\' with empty val
						newobjLoc=newobjLoc.replace("\\", "");
					}
					String newFaceOptName=newobjLoc.replace(facetOptName, newOptName);// replace regex chars again with special chars "\"
//					newobjLoc=newobjLoc.replace(facetOptName, newFaceOptName);
					productUpdatePage.addNewFieldToExistingfieldMetadataList("newFilterCheckbox_luser"+lastUserrow, "CHECKBOX##"+newFaceOptName);
					productUpdatePage.check("newFilterCheckbox_luser"+lastUserrow);	//check the filter panel check box

				}else{
					productUpdatePage.check(newFilterChckbox);	//check the filter panel check box

				}
				
	
				Util.printInfo("Verify that only filtered Devices are displayed for specific filtered check box: "+facetOptName);
				int iFilteredNoOfDevices = getNoOfVisibleRowsInDeviceTable();
								
				//Verify if the right device information  is displayed when Filter is applied(Again extract this information)
				deviceLabelloc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("devicesColumnsData", parseFieldName);
				String[] devicesLabelsInDeviceTable_filterd=productUpdatePage.getMultipleTextValuesfromField(deviceLabelloc);
				ArrayList<String> lstActDevicelabelInDeviceTable_Filtered = new ArrayList<>(Arrays.asList(devicesLabelsInDeviceTable_filterd));				
				assertTrueWithFlags("The list of devices displayed should match with filters applied on Devices", checkIfExpListContainsTheActualItems(lstActDevicelabelInFilterPanel, lstActDevicelabelInDeviceTable_Filtered));
				
				//check if the applied filters no of results should match with Filter panel and Devices
				Util.printInfo("Verify that the no of filter results in Filter panel matches with the number of Devices displayed"); 
				int igetNoOfFilterResults=getNoOfFilterResultsDisplayedInDeviceFilterPanel();
				assertEquals(iFilteredNoOfDevices, igetNoOfFilterResults);
	
				//check Applied Filters laobel is showing up
				Util.printInfo("Verify that Applied filters pill header displayed for the selected Filter group: "+filterSelectedGrpName);
				productUpdatePage.isFieldPresent("appliedFiltersLabel");
				productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
				String appFiltersLabel=productUpdatePage.getTextFromLink("appliedFiltersLabel");
				appFiltersLabel=appFiltersLabel.substring(0,appFiltersLabel.length()-1); //remove ":"
				assertTrue("The Applied filter pill header is displayed for Filter group: "+filterSelectedGrpName,appFiltersLabel.equalsIgnoreCase(filtersLabel));
	
				//now verify if the applied filters pill is showing up
				Util.printInfo("Verify that, for the Filter group: "+filterSelectedGrpName + ", applied filter pill item for the checked filter checkbox is displayed on Applited filters row in Manage Devices page");
				String[] actprodUpdateFacetoptionsNames=productUpdatePage.getMultipleTextValuesfromField("appliedFiltersFacetOptNames");
				lsactProdUpdFacetOptNames=new ArrayList<>(Arrays.asList(actprodUpdateFacetoptionsNames));
				
				//get the Device names from the Device table and verify 
				String[] newdevicesLabelsInDeviceTable=productUpdatePage.getMultipleTextValuesfromField(deviceLabelloc);
				ArrayList<String> lstnewActDevicelabelInDeviceTable = new ArrayList<>(Arrays.asList(newdevicesLabelsInDeviceTable));
				assertEqualsWithFlags(lsactProdUpdFacetOptNames, lstnewActDevicelabelInDeviceTable,true);
				filterApplied=true;
				
			}
			if(filterApplied){
				lastUserrow++;	//just keep adding a number
				facetOptionName="";		
				String facetOptName="";
				String newFilteOptName="";
				for(int j=0;j<filterFaceOptionsNames.size();j++){
					newFilteOptName=filterFaceOptionsNames.get(j);
					 facetOptName=filterFaceOptionsNames.get(j);
					if(j>0){
						facetOptionName=facetOptionName+","+facetOptName;
					}else{					
						facetOptionName=facetOptName;
					}
					Util.printInfo("Verify that The check box :"+facetOptionName+" in the Filter Panel should be unchecked when the user clicks on "+facetOptionName+" pill close button in Applied filters row");
					
					String filterPillLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("allAppFiltersFacetOptsCloseBtn", facetOptName);
					//For last user a special char is added '\' to support the domain name and this will be trucated while creating dynamic locator
					/// Hence need to re add to filedmetadata list
					if (grpName.equalsIgnoreCase(lastuserParseLabel)){							
						//In case of last user filters the user name contains domain name hence add escape sequence.
						String newOptName= facetOptName;
						if(facetOptName.contains("\\")){
							facetOptName=facetOptName.replace("\\", "");
						}				
						//GET THE FIELD LOCATOR and add it to explicitly to fielddata list due to special char in case of last user
						//here fo9r Chrome browser get the next locator
						String newobjLoc=null;
						if (EISTestBase.getAppBrowser().equalsIgnoreCase("chrome")){								
							 newobjLoc=productUpdatePage.getFieldLocators(filterPillLoc).get(1);
						}else{
							
							 newobjLoc=productUpdatePage.getFieldLocators(filterPillLoc).get(0);
						}
						//NOTE: There are cases where string contains \\\\ i.e \\ in that case
						if (newobjLoc.contains("\\")){
							//then replace '\' with empty val
							newobjLoc=newobjLoc.replace("\\", "");
						}
						String newFaceOptName=newobjLoc.replace(facetOptName, newFilteOptName);// replace regex chars again with special chars "\"
//						newobjLoc=newobjLoc.replace(facetOptName, newFaceOptName);
						productUpdatePage.addNewFieldToExistingfieldMetadataList("newFilterPillloc_luser"+lastUserrow, "BUTTON##"+newFaceOptName);
						assertTrueWithFlags("Verifying that the Applied filter pill is showing up on applied filter row",productUpdatePage.isFieldVisible("newFilterPillloc_luser"+lastUserrow));	//check before you click
						productUpdatePage.click("newFilterPillloc_luser"+lastUserrow);	//check the filter panel check box
					}else{
						assertTrueWithFlags("Verifying that the Applied filter pill is showing up on applied filter row",productUpdatePage.isFieldVisible(filterPillLoc));	//check before you click
						productUpdatePage.click(filterPillLoc);//click on close btn of the pill on applied filter row			
					}
					
					String newFilterChckbox= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterChckboxWithFaceName", filterSelectedGrpName,facetOptName);
					if (grpName.equalsIgnoreCase(lastuserParseLabel)){				
						lastUserrow+=lastUserrow+1;
						//In case of last user filters the user name contains domain name hence add escape sequence.
						facetOptName=newFilteOptName;
						if(facetOptName.contains("\\")){
							facetOptName=facetOptName.replace("\\", "");
						}				
						//GET THE FIELD LOCATOR and add it to explicitly to fielddata list due to special char in case of last user
						String newobjLoc=productUpdatePage.getFieldLocators(newFilterChckbox,false).get(0);
						String newFaceOptName=newobjLoc.replace(facetOptName, newFilteOptName);// replace regex chars again with special chars "\"
//						newobjLoc=newobjLoc.replace(facetOptName, newFaceOptName);
						productUpdatePage.addNewFieldToExistingfieldMetadataList("newFilterCheckboxWithFaceName_luser"+lastUserrow, "CHECKBOX##"+newFaceOptName);
						assertFalse("The check box(s) :"+facetOptionName+ " in the Filter Panel should be unchecked when the user clicks on "+facetOptionName+" pill close button(s) in Applied filters row",
								productUpdatePage.isChecked("newFilterCheckboxWithFaceName_luser"+lastUserrow));
					}else{
						assertFalse("The check box(s) :"+facetOptionName+ " in the Filter Panel should be unchecked when the user clicks on "+facetOptionName+" pill close button(s) in Applied filters row",
								productUpdatePage.isChecked(newFilterChckbox));
					}
					

					//check the results gets updated accordingly.
					Util.printInfo("Verify that when unchecked the filters in the filter panel, the no of Filter results in Filter panel matches with the number of Devices displayed");
					//when unchecked all of them no results will be shown
					int igetNoOfFilterResults=getNoOfFilterResultsDisplayedInDeviceFilterPanel();
					if (igetNoOfFilterResults>0){					
						int iactNoOfDevices=getNoOfVisibleRowsInDeviceTable();
						assertEquals(iactNoOfDevices, igetNoOfFilterResults);
					}
				}
				//now verify if clearAll works as expected
				facetOptionName="";
				String facetOptName1="";
				newFilteOptName="";
				for(int k=0;k<filterFaceOptionsNames.size();k++){			 	
					newFilteOptName=filterFaceOptionsNames.get(k);
					facetOptName=filterFaceOptionsNames.get(k);
					if(k>0){
						facetOptionName=facetOptionName+","+facetOptName;
					}else{					
						facetOptionName=facetOptName;
					}
					String newFilterChckbox= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterChckboxWithFaceName", filterSelectedGrpName,facetOptName);
					if (grpName.equalsIgnoreCase(lastuserParseLabel)){				
						lastUserrow+=lastUserrow++;
						//In case of last user filters the user name contains domain name hence add escape sequence.
						String newOptName= facetOptName;
						if(facetOptName.contains("\\")){
							facetOptName=facetOptName.replace("\\", "");
						}				
						//GET THE FIELD LOCATOR and add it to explicitly to fielddata list due to special char in case of last user
						String newobjLoc=productUpdatePage.getFieldLocators(newFilterChckbox,false).get(0);
						//NOTE: There are cases where string contains \\\\ i.e \\ in that case
						if (newobjLoc.contains("\\")){
							//then replace '\' with empty val
							newobjLoc=newobjLoc.replace("\\", "");
						}
						String newFaceOptName=newobjLoc.replace(facetOptName, newFilteOptName);// replace regex chars again with special chars "\"
//						newobjLoc=newobjLoc.replace(facetOptName, newFaceOptName);
						productUpdatePage.addNewFieldToExistingfieldMetadataList("newFilterCheckbox_luser"+lastUserrow, "CHECKBOX##"+newFaceOptName);
						productUpdatePage.check("newFilterCheckbox_luser"+lastUserrow);	//check the filter panel check box
						
					}else{
					
					//Check all the check boxes now under the group and check if clear all works as expected
					productUpdatePage.check(newFilterChckbox);
					}
						
				}
				//This clears all the selections done before and verify none of the pills shld display and checkboxes shld be unchecked
				
				//****************CLEAR ALL functionality************************************************
				productUpdatePage.click("filterPanelClearAllBtn");	
				Util.printInfo("Verify that the applied filter pill items are not showing up on Applited filters row in Manage Devices page when clicked on Clear All link for Filter group: "+filterSelectedGrpName);
				assertFalse("The applied filter pills should not displayed",productUpdatePage.isFieldVisible("appliedFiltersFacetOptNames"));

				Util.printInfo("Verify that the Filter panel results are not displayed when clicked on Clear All link for Filter group: "+filterSelectedGrpName);
				int igetNoOfFilterResults=getNoOfFilterResultsDisplayedInFilterPanel();
				assertFalse("The Filter Panel results should not displayed",(igetNoOfFilterResults>0)?true:false);

				//iterate again through the loop and check if filter checkboxes are unchecked when clear all is clicked
				facetOptionName="";
				 facetOptName="";
				 newFilteOptName="";
				for(int m=0;m<filterFaceOptionsNames.size();m++){
					newFilteOptName=filterFaceOptionsNames.get(m);
					facetOptName=filterFaceOptionsNames.get(m);
					facetOptionName=filterFaceOptionsNames.get(m);	

					Util.printInfo("Verify that The check box :"+facetOptionName+" in the Filter Panel should be unchecked when the user clicks on 'Clear All' link for the filter Group :"+filterSelectedGrpName);
					String newFilterChckbox= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("filterChckboxWithFaceName", filterSelectedGrpName,facetOptName);
					if (grpName.equalsIgnoreCase(lastuserParseLabel)){				
						lastUserrow+=lastUserrow++;
						//In case of last user filters the user name contains domain name hence add escape sequence.
						String newOptName= facetOptName;
						if(facetOptName.contains("\\")){
							facetOptName=facetOptName.replace("\\", "");
						}				
						//GET THE FIELD LOCATOR and add it to explicitly to fielddata list due to special char in case of last user
						String newobjLoc=productUpdatePage.getFieldLocators(newFilterChckbox,false).get(0);
						if (newobjLoc.contains("\\")){
							//then replace '\' with empty val
							newobjLoc=newobjLoc.replace("\\", "");
						}
						String newFaceOptName=newobjLoc.replace(facetOptName, newFilteOptName);// replace regex chars again with special chars "\"
//						newobjLoc=newobjLoc.replace(facetOptName, newFaceOptName);
						productUpdatePage.addNewFieldToExistingfieldMetadataList("newFilterCheckboxWithFaceName_luser"+lastUserrow, "CHECKBOX##"+newFaceOptName);
						assertFalse("The check box(s) :"+facetOptionName+ " in the Filter Panel should be unchecked when the user clicks on "+facetOptionName+" pill close button(s) in Applied filters row",
								productUpdatePage.isChecked("newFilterCheckboxWithFaceName_luser"+lastUserrow));
					}else{
						
						assertFalse("The check box(s) :"+facetOptionName+ " in the Filter Panel should be unchecked when the user clicks on 'Clear All' link for the filter Group :"+filterSelectedGrpName,
								productUpdatePage.isChecked(newFilterChckbox));
					}
				}
				
				
				//Verify the More or Less functionality in Filters Panel  do this for the last one
				if (i==filterOptions.length-1){
					//go top
					productUpdatePage.isFieldVisible("filterDropDown");
					productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
					//drag the filter scroll bar up to the beginning
					scrollFilterScrollBar("-110px");
					//NOTE: do this only for the last item in filterOptions list			
					//IF the Client Width is more than 422px then the row expands and the other added to the next row
					if (!productUpdatePage.isFieldVisible("deviceFilterPanelOverview")){						
						productUpdatePage.click("filterDropDown");
						Util.sleep(800);
					}
					productUpdatePage.isFieldVisible("allFilterCheckboxes");
					productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
					List<WebElement> myCheckboxes=productUpdatePage.getMultipleWebElementsfromField("allFilterCheckboxes");				
							
					for(int cb=0;cb<myCheckboxes.size();cb++){
						myCheckboxes=productUpdatePage.getMultipleWebElementsfromField("allFilterCheckboxes");
						//scroll each element and check it
						productUpdatePage.scrollIntoViewOfMetadataElement(myCheckboxes.get(cb));
						myCheckboxes.get(cb).click();	//keep checking check boxes
						//move to parent
						productUpdatePage.scrollIntoViewOfMetadataElementParentNode(myCheckboxes.get(cb));
					}
					productUpdatePage.click("filterDropDown");
					Util.sleep(1000);				
					productUpdatePage.isFieldPresent("appliedFiltersPresentationArea");
					productUpdatePage.scrollIntoViewOfMetadataElement(productUpdatePage.getCurrentWebElement());
										
					String btnMoreLinkLoc= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("btnMoreLessLinkOnFiltersRow","More");					
					//As the filter pill width would varies hence check if the more link is displayed when all checkboxes are applied
					if (productUpdatePage.isFieldVisible(btnMoreLinkLoc)){
						Util.printInfo("Found the More or Less button links");
						assertTrueWithFlags("The button More link should be seen on Applied Filters row", productUpdatePage.isFieldPresent(btnMoreLinkLoc));

						//now find out how many filters are showing up in the first row of Applied filters
						int visibleElements=productUpdatePage.getMultipleWebElementsfromField("visibleAppFilters").size();
						//So the no of elements which are not seen in the first row is visibleElements-checkboxes.size
						Util.printInfo("Verify No of Filters resulting number to the side of More link button");

						int expInvisibleElements=myCheckboxes.size()-visibleElements;
						String moreBtnLinkText=productUpdatePage.getValueFromGUI(btnMoreLinkLoc);
						int actInvisibleEle=Integer.valueOf(moreBtnLinkText.substring(0,1));	//the very first char is the number
						assertTrue("The No of Filters resulting number on More link button ", actInvisibleEle==expInvisibleElements);					

						//Also check the remaining filters are not visible
						//the filters which are hidden the style attribute is 'list-item'
						Util.printInfo("Verify that the hidden filters on Applied filters row are not seen");
						String hiddenLoc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("hiddenAppliedFilters","list-item");
						assertFalseWithFlags("The hidden Filters should not be seen on Applied Filters row",productUpdatePage.isFieldVisible(hiddenLoc));

						Util.printInfo("Clicking on button More link");
						//Now click on more btn link see that less btn link is found
						productUpdatePage.click(btnMoreLinkLoc);
						Util.printInfo("Verify that the Less button link is displayed");
						String btnLessLinkloc=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("btnMoreLessLinkOnFiltersRow","Less");
						assertTrueWithFlags("The button Less link should be seen on Applied Filters row", productUpdatePage.isFieldPresent(btnLessLinkloc));

						//the hidden applied filters shld be seen now
						Util.printInfo("Verify that the hidden filters on Applied filters row should be visible");
						assertTrueWithFlags("The hidden Filters should be seen on Applied Filters row",productUpdatePage.isFieldVisible(hiddenLoc));

						Util.printInfo("Clicking on Clear all button link on Applied filters row");
						//Click on Clear all link see that all the filters gone
						productUpdatePage.click("appliedFilterClearAllLink");	
						Util.printInfo("Verify that the Applied filters row should not visible");
						assertFalseWithFlags("The Applied Filters row should not be seen",productUpdatePage.isFieldVisible("appliedFiltersLabel"));

					}else{
						Util.printInfo("The Applied Filters presentation area does not have More and Less button links . So it should not be visible");
						assertFalseWithFlags("The button More link should not be seen on Applied Filters row", productUpdatePage.isFieldVisible(btnMoreLinkLoc));
					}

				}
			}	
/*			//not required for the last one
			if (i==filterOptions.length-1){
				logoutMyAutodeskPortal();
				LoginAndGoToManageDevicesPage();
			}
			
*/		}
		
	}

	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}

}