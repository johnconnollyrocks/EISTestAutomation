package customerportal;

import java.io.IOException;
//import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.DatabaseHandlerSTG;
import common.EISTestBase;
import common.Util;

public class Test_Verify_Platform_Language_Version extends CustomerPortalTestBase {
	public Test_Verify_Platform_Language_Version() throws IOException {
		super("Browser",getAppBrowser());
//		super();
		
	}

	@Before
	public void setUp() throws Exception {
 		launchMyAutodeskPortal(getBaseURL());
	}

	@Test
	public void Test_VerifyDwnldOtherProductsWindowURL() throws Exception {
		loginAsMyAutodeskPortalUser(testProperties.getConstant("USER_NAME") , testProperties.getConstant("PASSWORD"));
		CustomerPortal customerPortal = utilCreateMyAutodeskPortal();
		DatabaseHandlerSTG db=new DatabaseHandlerSTG();
		loginPage = customerPortal.getLoginPage();
		homePage = customerPortal.getHomePage();
		mainWindow.select();
		ArrayList<String> arr=new ArrayList<String>();
		ArrayList<String> Dbarr=new ArrayList<String>();
		ArrayList<String> PlatFormList=new ArrayList<String>();
		ArrayList<String> Child=new ArrayList<String>();
		ArrayList<String> Child1=new ArrayList<String>();
		ArrayList<String> LangList=new ArrayList<String>();
		ArrayList<String> LangListDB=new ArrayList<String>();
		ArrayList<String> LangListDB1=new ArrayList<String>();
		ArrayList<ArrayList<String>> items =new ArrayList<ArrayList<String>>();
		int count=1;
		ResultSet rs=null;
		ResultSet rs1=null;
		String ProductName="3DSMAX";
		homePage.click("InstallNowButton");
		db.getSiebleDBConnection();
		db.getSiebleDBConnection1();
		rs=db.ExecuteSiebelDBQuery(testProperties.getConstant("QUERY"));
		rs1=db.ExecuteSiebelDBQuery1(testProperties.getConstant("MYSQLQUERY"));

//		List<String> DataList=homePage.getMultipleTextValuesFromGUI("VersionPlatformLanguage");
		List<WebElement> DataList=driver.findElements(By.xpath("//span/span[contains(text(),'3DS Max')]/ancestor::span[@class='product-name']/following-sibling::form//div[contains(@class,'btn-group')]/button[contains(@class,'btn-default')]/div[contains(@class,'filter-option')]"));
		for(WebElement Element : DataList){
		Element.click();
		if(count==1){
		List<WebElement> Version=driver.findElements(By.xpath(".//*[@id='install-scroll-area']/div[2]/section/div/div[2]/form/div[1]/div/div/div[2]/div/ul/li/a"));
		for(WebElement EachVersion : Version ){
			Util.printInfo("The Version for the Product from Customer portal UI is : "+EachVersion.getText());
			String ProductVersion=EachVersion.getText();
			arr.add(ProductVersion);
		 }
		while(rs.next()){
			if(rs.getString(1).equalsIgnoreCase("3DSMAX")){
				Util.printInfo("The Version for the product from DataBase is "+ProductName+" is : "+rs.getString(2));
				Dbarr.add(rs.getString(2));
			}
		  }
		if(arr.equals(Dbarr)){
			assertTrue("The Versions from Customer Portal Install now window and Versins from DataBase are same ", arr.equals(Dbarr));
		}else{
			EISTestBase.fail("The Versions from Customer Portal Install now window and Versins from DataBase are not saamee ");
		 }
	  }		
		
		if(count==2){
		List<WebElement> PlatformList=driver.findElements(By.xpath(".//*[@id='install-scroll-area']/div[2]/section/div/div[2]/form/div[2]/div/div/div[2]/div/ul/li/a/span"));
		if(PlatformList.size()!=0){
			for(WebElement EachPlatform : PlatformList){
				PlatFormList.add(EachPlatform.getText());
		  }
			Util.printInfo("The Platform on install now window is / are :: " +PlatFormList);
		 }
		while(rs1.next())
		{
			Child.add(rs1.getString(42));
		}
				HashSet<String> uniqueValues = new HashSet<>(Child);
				for (String value : uniqueValues) {
					   Child1.add(value);
					}
				Util.printInfo("The Platform values from CEP Stage DataBase is /  are :: " +Child1);	
	   if(PlatformList.equals(Child1)){
		   assertTrue("The Platform values from Customer Portal Install Now window "+PlatFormList+" and CEP stage DataBase "+Child1+" are same :: ", PlatformList.equals(Child1));
	   }else{
		   EISTestBase.fail("The Platform values from Customer Portal Install Now window "+PlatFormList+" and CEP stage DataBase "+Child1+" are not same ");
	   }
		}
		if(count==3){
			List<WebElement> Language=driver.findElements(By.xpath(".//*[@id='install-scroll-area']/div[2]/section/div/div[2]/form/div[3]/div/div/div[2]/div/ul/li/a/span"));
			if(Language.size()!=0){
				for(WebElement EachLanguage : Language ){
					LangList.add(EachLanguage.getText());
				}
			}
			Util.printInfo("The Language on install now window is / are :: " +LangList);
			rs1=db.ExecuteSiebelDBQuery1(testProperties.getConstant("MYSQLQUERY"));
			ArrayList<String> LanguageArr=new ArrayList<String>();
			while(rs1.next())
			{
				LangListDB.add(rs1.getString(39));
				LanguageArr.add(LangListDB.get(0));
			}
			HashSet<String> uniqueValues1 = new HashSet<>(LanguageArr);
			for(String EachValue : uniqueValues1){
				LangListDB1.add(EachValue);
			}
			Util.printInfo("The Language values from CEP Stage DataBase is /  are :: " +LangListDB1);
		}
		count=count+1;
	}
		DatabaseHandlerSTG.closeConnection();
  }
}
