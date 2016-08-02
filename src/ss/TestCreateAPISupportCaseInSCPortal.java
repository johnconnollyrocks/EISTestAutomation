package ss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Case;
import common.EISTestBase;
import common.Page_;
import common.Util;

import common.Case.CreateFrom;
import common.Case.CaseType;

/**
 * Test class - TestCreateCaseInPortal
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class TestCreateAPISupportCaseInSCPortal extends SSTestBase {
	public TestCreateAPISupportCaseInSCPortal() {
		super("firefox");
	}
	
	@Before
	public void setUp() throws Exception {
		launchSalesforce();
	}

	@Test
	public void TEST_CreateCaseInPortal() throws Exception {
		String CustUrl ="https://autodesk--adsksfdev.cs12.my.salesforce.com/apex/Case_CustomerPortalLandingPage#";
		CreateFrom createFrom 	= getInterfaceType(SSConstants.SS_CREATE_FROM_ENUM_CONSTANT_NAME);
		CaseType caseType		= getCaseType(SSConstants.SS_CASE_TYPE_ENUM_CONSTANT_NAME);
	
		loginAsAutoUser();
		loginAsPortalUser();
		
		Case supportCase = utilCreateCaseObject(createFrom);
	
		Page_ createCasePage = supportCase.getCreateCasePage();

		supportCase = utilCreateCase(createFrom, caseType);
		
		String caseNumber= supportCase.getName();
		loginAsAutoUser(true);
		Util.sleep(50000);
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.printInfo("opened case verify on view case page");
        Page_ viewCasePage = supportCase.getViewCasePage();
        viewCasePage.verify();
		loginAsPortalUser();
		mainWindow.select();	
	 	EISTestBase.open(CustUrl);
	 	
	 	
	 	if(caseType==CaseType.API_SUPPORT){
	 	Util.printInfo("Creating API Support case in Sub Center portal...");
	 	createCasePage.click("apiSupportCase");
	 	createCasePage.verifyInstance("DEFAULT_CHECK1");
	 	String getStatus = createCasePage.getAttribute("makeDefault1", "checked");
	 	if(getStatus==null){
	 		Util.printInfo("The make default check box is unchecked");
	 	}
 	 	else if(getStatus.equalsIgnoreCase("true")){
 	 		Util.printInfo("The make default check box is checked");	
 	 	}
		createCasePage.populateField("platformOrOS", "Citrix");
		getStatus = createCasePage.getAttribute("makeDefault1", "checked");
		if(getStatus==null){
	 		Util.printInfo("The make default check box is unchecked");
	 	}
 	 	else if(getStatus.equalsIgnoreCase("true")){
 	 		Util.printInfo("The make default check box is checked");	
 	 	}
		createCasePage.populateInstance("SUB_DESC_POPULATE");
		createCasePage.click("submit");
		Util.sleep(10000);
		caseNumber=createCasePage.getValueFromGUI("caseNo");
	 	Util.printInfo("Created case '" + caseNumber  +"' in Subscription Center portal");
	 	loginAsAutoUser(true);
		Util.sleep(50000);
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.printInfo("opened case verify on view case page");
        viewCasePage.verifyInstance("VIEW_DETAILS1");
        
                
        loginAsPortalUser();
		mainWindow.select();	
	 	EISTestBase.open(CustUrl);
	 
	 	Util.printInfo("Creating API Support case in Sub Center portal...");
	 	createCasePage.click("apiSupportCase");
	 	createCasePage.verifyInstance("DEFAULT_CHECK1");
	 	getStatus = createCasePage.getAttribute("makeDefault1", "checked");
	 	if(getStatus==null){
	 		Util.printInfo("The make default check box is unchecked");
	 	}
 	 	else if(getStatus.equalsIgnoreCase("true")){
 	 		Util.printInfo("The make default check box is checked");	
 	 	}
		createCasePage.populateField("productName", "Fire");
		createCasePage.populateField("api", "Other");
		String xpath1 = "//*[@id='product_info_container']/ul/li/label/span";
	 	EISTestBase.findElementByXpathAndClick(xpath1);
		createCasePage.populateInstance("SUB_DESC_POPULATE");
		createCasePage.click("submit");
		Util.sleep(10000);
		caseNumber=createCasePage.getValueFromGUI("caseNo");
	 	Util.printInfo("Created case '" + caseNumber  +"' in Subscription Center portal");
	 	loginAsAutoUser(true);
		Util.sleep(50000);
		search(caseNumber);
		commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
		commonPage.click("caseDetails");
		Util.printInfo("opened case verify on view case page");
        viewCasePage.verifyInstance("VIEW_DETAILS2");
        loginAsPortalUser();
		mainWindow.select();	
	 	EISTestBase.open(CustUrl);
	 	Util.printInfo("Creating API Support case in Sub Center portal...");
	 	createCasePage.click("apiSupportCase");
	 	createCasePage.verifyInstance("DEFAULT_CHECK2");
	 	createCasePage.populateInstance("SET_DEFAULT_VALUES");
	 	getStatus = createCasePage.getAttribute("makeDefault1", "checked");
	  	if(getStatus==null){
	 	 		Util.printInfo("The make default check box is unchecked");
	 	 		xpath1 = "//*[@id='product_info_container']/ul/li/label/span";
	 	 		EISTestBase.findElementByXpathAndClick(xpath1);
	 	 	}
	  	 	else if(getStatus.equalsIgnoreCase("true")){
	  	 		Util.printInfo("The make default check box is checked");	
	  	 	}
	  	createCasePage.populateInstance("SUB_DESC_POPULATE");
	  	createCasePage.click("submit");
	  	Util.sleep(10000);
	 	}
	 	else if(caseType==CaseType.PROD_SUPPORT){
	 		Util.printInfo("Creating Technical Product Support case in Sub Center portal...");
		 	createCasePage.click("productSupportCase");
		 	createCasePage.verifyInstance("DEFAULT_CHECK1");
		 	String getStatus = createCasePage.getAttribute("makeDefault1", "checked");
		 	if(getStatus==null){
		 		Util.printInfo("The make default check box is unchecked");
		 	}
	 	 	else if(getStatus.equalsIgnoreCase("true")){
	 	 		Util.printInfo("The make default check box is checked");	
	 	 	}
			createCasePage.populateField("platformOrOS", "Citrix");
			getStatus = createCasePage.getAttribute("makeDefault1", "checked");
			if(getStatus==null){
		 		Util.printInfo("The make default check box is unchecked");
		 	}
	 	 	else if(getStatus.equalsIgnoreCase("true")){
	 	 		Util.printInfo("The make default check box is checked");	
	 	 	}
			createCasePage.populateInstance("SUB_DESC_POPULATE");
			createCasePage.click("submit");
			Util.sleep(10000);
			caseNumber=createCasePage.getValueFromGUI("caseNo");
		 	Util.printInfo("Created case '" + caseNumber  +"' in Subscription Center portal");
		 	loginAsAutoUser(true);
			Util.sleep(50000);
			search(caseNumber);
			commonPage.clickLinkInRelatedList("caseStatusInCaseRelatedList",testProperties.getConstant("STATUS"), "caseNumberInCasesRelatedList");
			commonPage.click("caseDetails");
			Util.printInfo("opened case verify on view case page");
	        viewCasePage.verifyInstance("VIEW_DETAILS1");
	        
	                
	        loginAsPortalUser();
			mainWindow.select();	
		 	EISTestBase.open(CustUrl);
		 
		 	Util.printInfo("Creating Technical Product Support case in Sub Center portal...");
		 	createCasePage.click("productSupportCase");
		 	createCasePage.verifyInstance("DEFAULT_CHECK1");
		 	getStatus = createCasePage.getAttribute("makeDefault1", "checked");
		 	if(getStatus==null){
		 		Util.printInfo("The make default check box is unchecked");
		 	}
	 	 	else if(getStatus.equalsIgnoreCase("true")){
	 	 		Util.printInfo("The make default check box is checked");	
	 	 	}
		}
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
