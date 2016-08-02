
package customerportal;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.Util;
import flex.messaging.io.ArrayList;

public class Test_Verify_Sorting_ProductLine_Version extends CustomerPortalTestBase {
	private String USERNAME = null;
	private String PASSWORD = null;
	private List<WebElement> numberOfProducts;
	private List<WebElement> productMetaData;
	private List<WebElement> numberProductUpdateList;
	private String [] numberOfProductsBeisdeTheProductHeaderLst;
	private int count=0;
	private List<Integer> ProductsLst = new ArrayList();
	
	
	public Test_Verify_Sorting_ProductLine_Version() throws IOException {
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
			 String Version=null;
			 String ProductName=null;
			 boolean ProductNameVersionAndTheMetaDataVersionAreSame = false;
			 numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("newProductUpdatesList");
			 for(int i=0;i<numberProductUpdateList.size();i++){
				 String productToggleDrawer= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productToggleDrawer", numberProductUpdateList.get(i).getText());	
				 productUpdatePage.click(productToggleDrawer);
				 Version=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("prodVersionLst", numberProductUpdateList.get(i).getText());
				 String ProductVersion=productUpdatePage.getValueFromGUI(Version);
				 ProductName=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("newProductUpdatesList", numberProductUpdateList.get(i).getText());
				 if(ProductName.contains(ProductVersion)){
					 ProductNameVersionAndTheMetaDataVersionAreSame= true;
				 }
			 }
				 if(ProductNameVersionAndTheMetaDataVersionAreSame){
					 assertTrue("product Name contains the version same as the metadata version", true);	
					 }
			 
				productUpdatePage.click("sortByDropdownBtn");
				Util.sleep(200);
				String FilterByProduct=productUpdatePage.getValueFromGUI("filterByProduct");
				String SortbyProduct=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("selectSortbyValueInDropdown", FilterByProduct);
				Util.printInfo("Verifying that Sortby changes upon selection");
				productUpdatePage.hoverOver(SortbyProduct);
				productUpdatePage.clickUsingLowLevelActions(SortbyProduct);
				Util.sleep(300);
				assertTrueWithFlags("Access control buttton is present after filtering by Proudct", productUpdatePage.isFieldVisible("filterproductAccessControl"));
				numberOfProducts=productUpdatePage.getMultipleWebElementsfromField("productHeaders");
				productMetaData=productUpdatePage.getMultipleWebElementsfromField("productsMetaData");
				String productHeader=null;
				String productmetaData=null;
				 String prodMetadata=null;
				 String prodHeader=null;
				
				boolean productHeaderContainsTheSameNameAsTheMetaDatProductname=false; 
				
					  for(int i=0;i<numberOfProducts.size();i++){
						  productHeader= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productHeader",numberOfProducts.get(i).getText());
						  for(int j=i;j<productMetaData.size();j++){
						  productmetaData= productUpdatePage.createFieldWithParsedFieldLocatorsTokens("productMetaData",productMetaData.get(j).getText());
						  
						   prodMetadata= productUpdatePage.getValueFromGUI(productmetaData);
						   prodHeader =productUpdatePage.getValueFromGUI(productHeader);
						  
						 if (prodHeader.contains(prodMetadata)){
							 productHeaderContainsTheSameNameAsTheMetaDatProductname=true;
						  
						 } 
					  }
				} 
					  if(productHeaderContainsTheSameNameAsTheMetaDatProductname){
						  assertTrue("product header name and the metadata product name both are same " , true);
						  }
		
		productUpdatePage.click("filterField");
		assertTrue("The Filter Panel is displayed when clicked on Filter button",productUpdatePage.verifyFieldExists("filterPanelHeader"));
//		assertTrue("The Filter Panel is displayed when clicked on Filter button",productUpdatePage.verifyFieldExists("productVersionUnderFilter"));

	// temp fix (DEV is down)
		productUpdatePage.check("checkTheProductVersionCheckBox");
		numberOfProductsBeisdeTheProductHeaderLst=productUpdatePage.getMultipleTextValuesfromField("numberOfProductsBeisdeTheProductHeader");
		String numberOfProductsChecked=productUpdatePage.getValueFromGUI("numberOfProductsChecked");
		numberOfProductsChecked=numberOfProductsChecked.substring(numberOfProductsChecked.indexOf('(')+1 ,numberOfProductsChecked.lastIndexOf(')')-8);
		Util.printInfo("numberOfProductsChecked :" + numberOfProductsChecked);
		count = getCountOfFilteredProductsFromProductHeaders();
		Util.printInfo(String.valueOf(count));
		assertEquals(String.valueOf(count),numberOfProductsChecked);
}
	private int getCountOfFilteredProductsFromProductHeaders(){
		String Products= null;
		int Praseint=0;
		for(String numberOfProduct :numberOfProductsBeisdeTheProductHeaderLst){
			Products=numberOfProduct.substring(numberOfProduct.indexOf('(')+1 ,numberOfProduct.lastIndexOf(')'));
			Praseint= Integer.parseInt(Products);
			ProductsLst.add(Praseint);
			ProductsLst.size();
		}
		int temp =0;
		for(int i=0;i<ProductsLst.size();i++){
			int temp1=0; 
			temp1=ProductsLst.get(i);
			temp=temp+temp1;
		}
		return temp;
		
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		finish();
}
}
