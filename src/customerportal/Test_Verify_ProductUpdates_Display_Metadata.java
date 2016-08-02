package customerportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ProductUpdates_Display_Metadata extends CustomerPortalTestBase {
	private ArrayList<String> lsactgrpFacetOptions= null;
	private ArrayList<String> lsexpGrpFacetoptions=null;
	private ArrayList<String> lsActGrpHeaders= null;
	private ArrayList<String> lsactProdUpdFacetOptNames=null;
	private ArrayList<String> lstFilterPillBtns=null;
	private String sortByOption="Product"; 
	
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	
	
	public Test_Verify_ProductUpdates_Display_Metadata() throws IOException {
		super("Browser",getAppBrowser());	
	}

	@Before
	public void setUp() throws Exception {
		launchMyAutodeskPortal(getBaseURL());
	}
	@Test
	public void Test_ProductUpdates_DisplayMetadata() throws Exception {
		//Get the Login credentials from Jenkins if provided else go with one given in properties file
		if (System.getProperty("UserName_jenkins")!=null && System.getProperty("Password_jenkins")!=null){			
			System.out.println("Found creds from jenkins");
			System.out.println("************************************************************************************");
			System.out.println("Dllata From Jenkins" );
			System.out.println("UserName:"+System.getProperty("UserName_jenkins"));
			System.out.println("Password:"+System.getProperty("Password_jenkins") );
			System.out.println("***=*********************************************************************************");
			loginAsMyAutodeskPortalUser(System.getProperty("UserName_jenkins"),System.getProperty("Password_jenkins"));
		}else{
			loginAsMyAutodeskPortalUser(testProperties.getConstant("USERNAME") , testProperties.getConstant("PASSWORD"));		
		}
		
		GoToProductUpdatesPage();
		Util.printInfo("Select the Sort by Option as 'Product' ");		
		selectSortbyInProductUpdates(sortByOption);
		//first get the no.of updates are showing up for specific sort by option		
		//int iNoOfUpdates= getNoOfProductUpdatesWrapperGroup();
		Util.printInfo("Clicking  toggle drawer");
		List<WebElement> ProductsLst=driver.findElements(By.xpath("//div[@class='update-desc']/span[@class='name']"));
		for(WebElement eachProduct:ProductsLst){
			productUpdatePage.click("articlesUpdatesDrawerBtn");
			
			Util.sleep(2000);
			String[] metadaOptions=testProperties.getConstant("METADATAOPTIONS").split(",");
			
			List<WebElement> productmetadata=productUpdatePage.getMultipleWebElementsfromField("ProductMetaDataLabel");
			//ProductMetaDataLabel
			
			int noofmetadata =productmetadata.size();
			
			if (noofmetadata==metadaOptions.length){
				
				Util.printInfo("Verifying the product metadata displayed with the available options");
				
				for (int i=0;i<(metadaOptions.length-1);i++){
					
					if (productmetadata.get(i).getText().contains(metadaOptions[i])){
						
						assertTrue(productmetadata.get(i+1).getText(),true);						
					}
					else
					{
						EISTestBase.failTest("Metadata is not displaying properly");
					}					
					
				}break;
				
			}else
			{
				EISTestBase.failTest("All Metadata are not present");
			}
					
		}	
			logoutMyAutodeskPortal();   
	}
		
		//String[] filteredGrpNames=testProperties.getConstant("FILTEROPTIONS").split(",");
		//verifyClearFiltersFunctionalityForAllFilterGroups(filteredGrpNames);
		//logoutMyAutodeskPortal();
		

	
	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
	
}

