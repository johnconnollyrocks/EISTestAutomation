package bornincloud;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.syntax.jedit.InputHandler.home;

import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_ProductList_Format extends BornInCloudTestBase {

	String contractNumberWithOneProduct="";
	String contractNumberWithMultipleProducts="";
	String contractNumberWithMoreThan20Products="";
	int numberOfContracts;
	String numberForLink;
	int count;
	
	public Test_Verify_ProductList_Format() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_US2133() throws Exception {


		BornInCloud bic = utilCreateMyAutodeskPortal();
		loginPage = bic.getLoginPage();
		homePage = bic.getHomePage();
		String env;

		env = getEnvironment();
		if (env.equalsIgnoreCase("DEV")) {
			bic.login(testProperties.getConstant("USER_ID_DEV"),
					testProperties.getConstant("PASSWORD_DEV"));
		} else if (env.equalsIgnoreCase("STG")) {
			bic.login(testProperties.getConstant("USER_ID_STG"),
					testProperties.getConstant("PASSWORD_STG"));
		}
		// assertEquals(homePage.getValueFromGUI("titleproductservices"),
		// "Products & Services");
		homePage.waitForElementToDisappear("pageLoadImg", 100000);

		
		homePage.waitForFieldVisible("titleproductservices", 30000);

		boolean text = homePage.getValueFromGUI("titleproductservices").contains("PRODUCTS & SERVICES");


		EISTestBase.assertTrue("Customer Portal Home Page is Displayed", text);

		// Select Product Link
		bic.select("contractlist");
		homePage.waitForElementToDisappear("pageLoadImg", 100000);
		homePage.waitForFieldVisible("contracttable", 100000);
		boolean contractNumWitnOnepro=false;
		boolean contractNumWithlessThan20=false;
		boolean contractNumWithMorethan20=false;
		
		String[] contractNumbers=homePage.getMultipleTextValuesfromField("contractNumers");
		
		for(int i=0;i<contractNumbers.length;i++){
//			homePage.scrollIntoViewOfMetadataElement(contractNumbers.get(i));
			String contractNumberDisplayed=contractNumbers[i];
			String contractLink=homePage.createFieldWithParsedFieldLocatorsTokens("contractLinkINContractPage", contractNumbers[i]);
			System.out.println(homePage.getFieldLocators(contractLink));
			homePage.click(contractLink);
			Util.sleep(10000);
			//Util.sleep(10000);
			/*homePage.waitForFieldVisible("countOfProducts", 100000);*/
			homePage.checkIfElementExistsInPage("countOfProducts",120);
//			homePage.isFieldVisible("countOfProducts");
//			homePage.scrollIntoViewOfMetadataElement(homePage.getCurrentWebElement());
			String countOfProducts=getValueOfProducts();			
			int k=0;
			while(k<=30){
				countOfProducts=getValueOfProducts();
				Util.sleep(2000);
				if (!countOfProducts.contains("-")){
					break;
				}
				k++;
			}
			count=Integer.valueOf(countOfProducts);
			homePage.click("backButtonContractDetails");
			if(count==1) {
				
				contractNumberWithOneProduct=contractNumberDisplayed;
				if (!contractNumWitnOnepro && !contractNumberWithOneProduct.isEmpty()){
					String contractNumberWithOneProductXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractwithOneProduct", contractNumberWithOneProduct);
					assertTrueCatchException("contract with one Product displayed product Names in contract list. Contract Number: "+contractNumberWithOneProduct, homePage.checkIfElementExistsInPage(contractNumberWithOneProductXpath, 4));
					contractNumWitnOnepro=true;
				}
			}else if(count>20){
				numberOfContracts=count-20;
				numberForLink=String.valueOf(numberOfContracts);
				contractNumberWithMoreThan20Products=contractNumberDisplayed;
				if (!contractNumWithMorethan20 && !contractNumberWithMoreThan20Products.isEmpty()){
					String contractWithMoreThan20ProductsXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractWithMultipeProducts", contractNumberWithMoreThan20Products);
					List<WebElement> moreLinkElement=homePage.getMultipleWebElementsfromField(contractWithMoreThan20ProductsXpath);
					/*mouseHover(moreLinkElement.get(0));*/
					JavascriptExecutor js = (JavascriptExecutor) driver;  
					String jScript="$('td[colspan=\"2\"] > a[data-contract-id=\""+contractNumberWithMoreThan20Products+"\"]').trigger(\"mouseover\")";
					js.executeScript(jScript);
					Util.sleep(3000);
					String moreLink=homePage.createFieldWithParsedFieldLocatorsTokens("moreButtonInProductList", numberForLink);
//					homePage.isFieldPresent(moreLink);
//					homePage.scrollIntoViewOfMetadataElement(homePage.getCurrentWebElement());
					if(homePage.checkIfElementExistsInPage(moreLink, 10)){
						assertTrue("more hyperlink is displayed for the contact which has more that 20 products ", homePage.checkIfElementExistsInPage(moreLink, 10));
						homePage.click(moreLink);
//						homePage.isFieldPresent("lessButtonInProductList");
//						homePage.scrollIntoViewOfMetadataElement(homePage.getCurrentWebElement());
						assertTrue("Less hyperlink is displayed after clicking on more hyperlink ", homePage.checkIfElementExistsInPage("lessButtonInProductList", 10));
//						homePage.isFieldPresent("contractsPageTitle");
//						homePage.scrollIntoViewOfMetadataElement(homePage.getCurrentWebElement());
					}else{
						EISTestBase.fail("More hyperlink not isplayed for the contract with more that 20 products. Contract#: "+contractNumberWithMoreThan20Products);
					}
					contractNumWithMorethan20=true;
				}
			}
			else if(count>1&&count<20){				
				contractNumberWithMultipleProducts=contractNumberDisplayed;
				if (!contractNumWithlessThan20 && !contractNumberWithMultipleProducts.isEmpty()){
					String contractWithMultipeProductsXpath=homePage.createFieldWithParsedFieldLocatorsTokens("contractWithMultipeProducts", contractNumberWithMultipleProducts);
					assertTrue("contract with more that one Product displayed hyperlink with number. Contract Number: "+contractNumberWithMultipleProducts, homePage.checkIfElementExistsInPage(contractWithMultipeProductsXpath, 10));
					contractNumWithlessThan20=true;
				}
				
			}
		 
		Util.sleep(2000);
		//Later test the following
		//if (contractNumWitnOnepro=true)
		/*else{
			//assertTrueCatchException("contract not found with one Product",false);
			Util.printInfo("contract not found with one Product");
		}*/
		/*else{
			//assertTrueCatchException("contract not found with more than one Product",false);
			Util.printInfo("contract not found with more than one Product");
			contractNumWithlessThan20=true;
		}*/
		/*else{
			//assertTrueCatchException("contract not found with more than 20 Product to validate the more and less buttons",false);
			Util.printInfo("contract not found with more than 20 Product to validate the more and less buttons");
		}*/
		// Logout
//		homePage.click("arrow");
		}
		homePage.click("signout");
	}
	
	public void mouseHover(WebElement element) throws MetadataException{

		/*Actions actions = new Actions(driver);		
		actions.moveToElement(element).perform();			
		actions.perform();
		Util.sleep(2000);*/
		Locatable hoverTheItem= (Locatable)element;
		Mouse mouse= ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverTheItem.getCoordinates());		
		Util.sleep(2000);
	}

	public String getValueOfProducts() {
		String countOfProducts=null;
		try {
			countOfProducts=homePage.getValueFromGUI("countOfProducts");	
		} catch (Exception e) {}
			//intentionally left blank
		return countOfProducts;
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
