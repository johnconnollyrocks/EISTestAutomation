package customerportal;

import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;

public class Test_Verify_ProductUpdates_Prod_Metadata_CU_ID extends CustomerPortalTestBase {
	private String sortByOption="Product"; 
	private List<WebElement> numberProductUpdateList;
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	

	public Test_Verify_ProductUpdates_Prod_Metadata_CU_ID() throws IOException {
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
		GoToProductUpdatesPage();
		Util.sleep(2000);
		Util.printInfo("Select the Sort by Option as 'Product' ");		
		selectSortbyInProductUpdates(sortByOption);
		//first get the no.of updates are showing up for specific sort by option		
		//int iNoOfUpdates= getNoOfProductUpdatesWrapperGroup();
		Util.printInfo("Clicking  toggle drawer");
		Util.printInfo("Verifying for each products in the list");
		numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("newProductUpdatesList");
		 for(int i=0;i<numberProductUpdateList.size();i++){
			 String productToggleDrawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", numberProductUpdateList.get(i).getText());	
			 productUpdatePage.click(productToggleDrawer);
			Util.printInfo("Expanding the product details");
			
			Util.printInfo("Capturing the 'Update ID' from expanded Product details");
			String ProductUpdateId=productUpdatePage.getValueFromGUI("ProductUpdateCUId");
			Util.printInfo("ProductUpdateId------>" +String.valueOf(ProductUpdateId));
			
			Util.printInfo("Capturing the 'data id' from product article");
			String dataid=productUpdatePage.getAttribute("productUpdateFirstRow", "data-id");
			Util.printInfo("dataid------>" +String.valueOf(dataid));
			
			Util.printInfo("Verifying if the value of Update ID from the expanded product details is same as Prouct article data id");
			if(ProductUpdateId.contains(dataid)){		
				assertTrue(" meta datas are getting displayed properly", true);
			}
			else
			{
				EISTestBase.failTest("Type of the product is Service pack ");
			}
						
		}
			logoutMyAutodeskPortal();   
	}
		

	
	@After
	public void tearDown() throws Exception {		
		driver.quit();
		finish();
	}
	
}

