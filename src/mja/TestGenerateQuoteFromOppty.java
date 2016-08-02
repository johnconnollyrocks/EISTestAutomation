package mja;

import org.junit.After; 
import org.junit.Before;
import org.junit.Test;
import common.Case;
import common.Oppty;
import common.Page_;
import common.Case.CaseType;
import common.Case.CreateFrom;
import common.Util;

/**
 * Test class - TestGenerateApprovalChain
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestGenerateQuoteFromOppty extends MJATestBase {
	public TestGenerateQuoteFromOppty() {
		super("firefox");
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce(getAppServerBaseURL());
	}

	@Test
	public void TEST_GenerateQuoteFromOppty() throws Exception {
		//Can include Selenium and WebDriver commands - but please don't!
		
		loginAsAutoUser();
		
		Oppty oppty = utilCreateOppty();
		Page_ viewOpptyPage = oppty.getViewOpptyPage();
		
		String opptyName = viewOpptyPage.getValueFromGUI("opptyName");
		String opptyNumber = viewOpptyPage.getValueFromGUI("opptyNumber");
		
		oppty.addProducts();	
		
		Oppty quote = utilCreateQuoteFromOppty();		
		Page_ viewQuotePage = quote.getViewQuotePage();
		
		viewQuotePage.setVerificationDataValue("quoteName", quote.getQuoteName());
		viewQuotePage.setVerificationDataValue("opptyName", opptyName);		

		assertEqualsWithFlags(quote.getQuoteName(), opptyNumber + " - Quote");
		
		viewQuotePage.verify();	

//		viewQuotePage.verifyRelatedListCell("productNameInQuoteLinesRelatedList", 0, testProperties.getConstant("PRODUCT1"));
//		viewQuotePage.verifyRelatedListCell("productNameInQuoteLinesRelatedList", 1, testProperties.getConstant("PRODUCT2"));
//		viewQuotePage.verifyRelatedListCell("productTypeInQuoteLinesRelatedList", 0, "License");
//		viewQuotePage.verifyRelatedListCell("productTypeInQuoteLinesRelatedList", 1, "License");
//		viewQuotePage.verifyRelatedListCell("licenseTypeInQuoteLinesRelatedList", 0, "New");
//		viewQuotePage.verifyRelatedListCell("licenseTypeInQuoteLinesRelatedList", 1, "New");

		//Add Discounts to the quote products
		quote.editProductsInQuote();	
		
		//Verify the discounted amounts		
//		quote.verifyProductsInQuote();
		
		//Add notes to quote
		if(testProperties.getConstant("ADD_NOTES_TO_QUOTE").equalsIgnoreCase("Yes")){
		quote.addNotestoQuote();
		}	

		//create and save pdf 
		if(testProperties.getConstant("CREATE_AND_VERIFY_PDF").equalsIgnoreCase("Yes")){
		quote.createAndSavePDF();		
		quote.verifyQuotePDFRelatedList();
		}	

		//edit quote
		if(testProperties.getConstant("EDIT_CHANGE_AND_VERIFY_QUOTE_DETAILS").equalsIgnoreCase("Yes")){
		quote.editQuote();
		viewQuotePage.verifyInstance("EDIT_QUOTE");
		}
		
		//Edit and change Autodesk Entity
		if(testProperties.getConstant("EDIT_CHANGE_AND_VERIFY_AUTODESK_ENTITY_DETAILS").equalsIgnoreCase("Yes")){
		quote.changeAutodeskEntityForQuoteAndVerify();
		}
		
//		Create Case From Quote
		if(testProperties.getConstant("CREATE_CASE_FROM_QUOTE").equalsIgnoreCase("Yes")){
		createCaseFromQuote(quote);
		}
		
	}
	
	
	public void createCaseFromQuote(Oppty quote) {
		//Create Business service request inquiry Case out of a quote
		Page_ viewQuotePage = quote.getViewQuotePage();
		viewQuotePage.click("newCaseButton");
		Util.sleep(5000);
		if(viewQuotePage.isAlertPresent()){
		viewQuotePage.acceptAlert();
		}
		CreateFrom createFrom 	= getInterfaceType(MJAConstants.MJA_CREATE_FROM_ENUM_CONSTANT_NAME);
		CaseType caseType		= getCaseType(MJAConstants.MJA_CASE_TYPE_ENUM_CONSTANT_NAME);
		
		Case supportCase = utilCreateCase(createFrom, caseType);
	//	supportCase.open();
		
	//	Page_ viewCasePage = supportCase.getViewCasePage();
	}
	@After
	public void tearDown() throws Exception {
		//Close the browser. Call stop on the WebDriverBackedSelenium instance
		//  instead of calling driver.quit(). Otherwise, the JVM will continue
		//  running after the browser has been closed.
		driver.quit();
		
		//TODO  Figure out how to determine if the test code has failed in a
		//  manner other than by EISTestBase.fail() being called.  Otherwise,
		//  finish() will always print the default passed message to the console.
		finish();
	}
}
