package bornincloud;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.EISTestBase;
import common.Util;

public class Test_Verify_US1333_TC521 extends BornInCloudTestBase {
	String expireDate = null;
	String renewalDate = null;
	String name = null;
	String creditCard = null;
	String editcreditCard = null;
	List<String> productslist = null;
	int j =0;

	public Test_Verify_US1333_TC521() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_US1333_TC521() throws Exception {

		boolean product = false;
		
		List<String> contractDeatils = new ArrayList<String>();

		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;

		env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("USER_ID_DEV"),
					testProperties.getConstant("PASSWORD_DEV"));
			expireDate = testProperties.getConstant("EXPIRESDATE_DEV");
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
			expireDate = testProperties.getConstant("EXPIRESDATE_STG");
		}
		// assertEquals(homePage.getValueFromGUI("titleproductservices"),
		// "Products & Services");
		homePage.waitForElementToDisappear("pageLoadImg", 10000);

		
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");

		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);

		// Select Product Link
		bic.select("contractlist");
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
		homePage.waitForFieldVisible("contracttable", 100000);
		int rowCount = homePage.getNumRowsInTable("contracttable");
	
		for (int i=0;i<rowCount;i++){
			productslist = homePage.getTableRow("contracttable",i);
			for(j=0;j<productslist.size();j++){
				String temp = productslist.get(j);
				if(temp.contains("110000742065")){
					product = true;
					contractDeatils.add(productslist.get(j));
					contractDeatils.add(productslist.get(j+1));
					contractDeatils.add(productslist.get(j+2));
					contractDeatils.add(productslist.get(j+3));
					List<WebElement> contract = homePage.getMultipleWebElementsfromField("Contract");
					contract.get(i).click();
					break;
				}
				
			}
			if(product){break;}
		}	
		
		if(!product){EISTestBase.failTest("Product Not Displayed, Change Test Data");}
		
		
		assertEqualsCatchException(productslist.get(j+3),"1");
		assertEqualsCatchException(productslist.get(j+1),"Quarterly");
		
		if (homePage.waitForField("type1", true)){
			assertEqualsCatchException(testProperties.getConstant("type"),homePage.getValueFromGUI("type1"));
			assertEqualsCatchException(testProperties.getConstant("startDate"),homePage.getValueFromGUI("startDate1"));
			//assertEqualsCatchException(testProperties.getConstant("endDate"),homePage.getValueFromGUI("endDate"));
			
		}else{
			EISTestBase.failTest("Details of Subscription Not Displayed");
		}
		
		
		
		//homePage.clickLinkInRelatedList("Contract", 6, "winson-051601");
		//homePage.clickLinkInRelatedList("Contract1",5,0);
//		homePage.clickLinkInRelatedList("Contract", "winson-051601", "Contract");
//		homePage.clickLinkInTable("contracttablecolumn1", 6);
//		int teamp = homePage.getTableRowNum("contracttablecolumn1", "winson-051601");
		
		// Logout
		homePage.click("arrow");

		homePage.click("signout");
	}

	@After
	public void tearDown() throws Exception {

		// Close the browser. Call stop on the WebDriverBackedSelenium instance
		// instead of calling driver.quit(). Otherwise, the JVM will continue
		// running after the browser has been closed.
		driver.quit();

		// TODO Figure out how to determine if the test code has failed in a
		// manner other than by EISTestBase.fail() being called. Otherwise,
		// finish() will always print the default passed message to the console.
		finish();
	}
}
