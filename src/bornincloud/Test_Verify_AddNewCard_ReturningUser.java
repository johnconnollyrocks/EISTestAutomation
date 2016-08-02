package bornincloud;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.EISTestBase;
import common.Page;
import common.TestProperties;
import common.Util;
import common.exception.MetadataException;
import customerportal.CustomerPortalTestBase;

public class Test_Verify_AddNewCard_ReturningUser extends BornInCloudTestBase {
	String AName = null;
	String Aaddress = null;
	String Acity =  null;
	String Azipcode = null; 
	String AEmail = null;
	String UserID="";
 	String offerings="";
 	String country="";
 	WebDriverWait wait=new WebDriverWait(driver,60);
 	String expectedHeader="";
 	String monthlyPlan="";
 	String annualPlan="";
 	String baseEdition="";	
 	String quantity="";
 	String advancedEdition="";
 	String Ename="";
 	String Eaddress="";
 	String Ecity="";
 	String Ezipcode="";
 	String Eemail="";
 	String Uaddress="";
 	String ETaxPrice="";
 	String ETotalPrice="";
 	String SingleQTY="";
 	String EMonthlyPrice="";
 	String P2FooterMSG="";
 	String ECloudPrice,Subscript   ="";
 	String AddCardFName           ="";
 	String AddCardLName           ="";
 	String AddCardNumber          ="";
 	String AddCardCode            ="";
 	String AddCardEaddress        ="";
 	String AddCardEcity	           ="";
 	String AddCardEzipcode,AddCardEemail	       ="";

	public Test_Verify_AddNewCard_ReturningUser() {
		super("browser");
		// TODO Auto-generated constructor stub
	}


		
		@Before
		public void setUp() throws Exception {
			

			
			//PelicanExpireSubscription(login_Pelican_Admin());
			Subscript =testProperties.getConstant("Subscript");
			P2FooterMSG=testProperties.getConstant("P2FooterMSG");
			AddCardFName=testProperties.getConstant("AddCardFName");
			AddCardLName=testProperties.getConstant("AddCardLName");
			AddCardNumber=testProperties.getConstant("AddCardNumber");
			AddCardCode=testProperties.getConstant("AddCardCode");
			AddCardEaddress=testProperties.getConstant("AddCardEaddress");
			AddCardEcity=testProperties.getConstant("AddCardEcity");
			AddCardEzipcode=testProperties.getConstant("AddCardEzipcode");
			AddCardEemail=testProperties.getConstant("AddCardEemail");
			
			
			if(getEnvironment().equalsIgnoreCase("dev")){
				UserID=testProperties.getConstant("userId_DEV");
				offerings=testProperties.getConstant("offeringId_DEV");
				country=testProperties.getConstant("country_DEV");
				monthlyPlan=testProperties.getConstant("monthlyPlan_DEV");
				annualPlan=testProperties.getConstant("annualPlan_DEV");
				baseEdition=testProperties.getConstant("baseEdition_DEV");
				advancedEdition=testProperties.getConstant("advancedEdition_DEV");
				Ename=testProperties.getConstant("Ename_DEV");
				Eaddress=testProperties.getConstant("Eaddress_DEV");
				Ecity=testProperties.getConstant("Ecity_DEV");
				Ezipcode=testProperties.getConstant("Ezipcode_DEV");
				Eemail=testProperties.getConstant("Eemail_DEV");
				Uaddress=testProperties.getConstant("Uaddress_DEV");
				ETaxPrice=testProperties.getConstant("ETaxPrice_DEV");
				ETotalPrice=testProperties.getConstant("ETotalPrice_DEV");
				SingleQTY=testProperties.getConstant("SingleQTY_DEV");
				EMonthlyPrice=testProperties.getConstant("EMonthlyPrice_DEV");				
				ECloudPrice=testProperties.getConstant("ECloudPrice_DEV");
				
				
			}else{
				UserID=testProperties.getConstant("userId_STG");
				offerings=testProperties.getConstant("offeringId_STG");
				country=testProperties.getConstant("country_STG");
				monthlyPlan=testProperties.getConstant("monthlyPlan_STG");
				annualPlan=testProperties.getConstant("annualPlan_STG");
				baseEdition=testProperties.getConstant("baseEdition_STG");
				advancedEdition=testProperties.getConstant("advancedEdition_STG");
				Ename=testProperties.getConstant("Ename_STG");
				Eaddress=testProperties.getConstant("Eaddress_STG");
				Ecity=testProperties.getConstant("Ecity_STG");
				Ezipcode=testProperties.getConstant("EzipcodeSTG");
				Eemail=testProperties.getConstant("Eemail_STG");
				Uaddress=testProperties.getConstant("Uaddress_STG");
				ETaxPrice=testProperties.getConstant("ETaxPrice_STG");
				ETotalPrice=testProperties.getConstant("ETotalPrice_STG");
				SingleQTY=testProperties.getConstant("SingleQTY_STG");
				EMonthlyPrice=testProperties.getConstant("EMonthlyPrice_STG");				
				ECloudPrice=testProperties.getConstant("ECloudPrice_STG");
				
			}
			String url=getBaseURL() + "/r?signature=" + getSignature(UserID)+ "&userId=" + UserID + "&country=" + country + "&offeringId=" + offerings ;
			System.out.println(url);
			launchIPP(url);		
			Util.sleep(4000);
		}
		

	

	@Test
	public void Test_Validate_Pelican_Subscription() throws Exception {

		PageRedirectWaitFor("disabledContinueButton");
		Util.printInfo("IPP home page is loaded Successfully");		
		assertTrue("Base Edition is Displayed",homePage.isFieldVisible("IPPBaseEdition"));
		SelectEdtion(baseEdition);
		verifyButton("enabledContinueButton");
		homePage.click( "enabledContinueButton");
	
		
		
		PageRedirectWaitFor("PlanBackButton");		
		Util.printInfo("Choose Order_Plan page is loaded Successfully");
		//verifyButton("disabledContinueButton");
		selectPlan(monthlyPlan);
		assertTrue("Edition is displayed ", homePage.isFieldVisible("ViewEditionValue"));
		assertTrue("Plan is displayed ", homePage.isFieldVisible("ViewSelectPlan"));
		assertTrue("Plan Price is displayed ", homePage.isFieldVisible("ViewSelectPlanPrice"));
		ValidateGuiElements("ViewEditionValue",baseEdition);
		ValidateGuiElements("ViewSelectPlan",monthlyPlan);
		ValidateGuiElements("ViewSelectPlanPrice",EMonthlyPrice);
		ValidateGuiElements("ViewFooterText",P2FooterMSG);
		ClickOnContinueOnPlanPage();
		
//****************************************************************************		
		PageRedirectWaitFor("CCheader");	
		Util.printInfo("Cloud_Credits page is loaded Successfully");	
		assertTrue("Edition is displayed ", homePage.isFieldVisible("ViewEditionValue"));
		
		homePage.populateField("QtyText", "1");
			
		ValidateGuiElements("ViewEditionValue",baseEdition);
		ValidateGuiElements("ViewP3Plan",monthlyPlan);
		ValidateGuiElements("ViewP3PlanPrice",EMonthlyPrice);
		
		Util.printInfo("Validating Cloud Credits ");	
		ValidateGuiElements("cloudCreditsQTY",SingleQTY);
		ValidateGuiElements("cloudCreditsPrice",ECloudPrice);
		assertTrue("Click here link is displayed in Cloud Credits Page",homePage.isFieldVisible("ClickHereCA"));
		homePage.click("P3Continue");
		
		
		
		
		
		
		PageRedirectWaitFor("BillPayHeader");		
		Util.printInfo("Billing Detials page is loaded Successfully");
		verifyButton("p4DisContinue");		
		assertTrue("Edition is displayed in Payment Information page ", homePage.isFieldVisible("ViewEditionValue"));				
		ValidateGuiElements("ViewEditionValue",baseEdition);
		ValidateGuiElements("ViewP3Plan",monthlyPlan);
		ValidateGuiElements("ViewP3PlanPrice",EMonthlyPrice);		
		Util.printInfo("Validating Cloud Credits in Payment Page");	
		ValidateGuiElements("cloudCreditsQTY",SingleQTY);
		ValidateGuiElements("p4CCPrice",ECloudPrice);
		
		ValidateGuiElements("SubscriptText",Subscript);
		
		//Skipping validation of Privacy link as the Business flow is not yet complete. 
		/*homePage.clickWait("PrivacyLink","PrivacyLink",10);
		PageRedirectWaitFor("p4PrivacyHeader");
		driver.navigate().back();*/
		
		
		
	
		Verifycard();
		
		
		
		
		
		
		//Verify Billing Informations Before Updating 
		/*
		homePage.clickWait("PayEdit","PayEdit",10);
		homePage.populateField("PayAddressText",Eaddress);
		driver.findElement(By.xpath(".//*[@id='postalCode']")).click();
		verifyButton("p4DisContinue");
		homePage.clickWait("PayAgreeCB", "PayAgreeCB", 10);		
		homePage.click("P3Continue");*/
		
		
		PageRedirectWaitFor("OrderSummaryHeader");
		
		//Verify Billing Summary Page
		BillSummary("Eaddress");
		PayoptionRead();
		
		assertTrue("Edition is displayed in Payment Information page ", homePage.isFieldVisible("ViewEditionValue"));				
		ValidateGuiElements("ViewEditionValue",baseEdition);
		ValidateGuiElements("ViewP3Plan",monthlyPlan);
		ValidateGuiElements("ViewP3PlanPrice",EMonthlyPrice);		
		Util.printInfo("Validating Cloud Credits in Payment Page");	
		ValidateGuiElements("cloudCreditsQTY",SingleQTY);
		ValidateGuiElements("p4CCPrice",ECloudPrice);
		
				
		assertTrue("Tax Price is displayed on Billing Summary Page",homePage.isFieldVisible("TaxPrice"));
		assertTrue("Total Order in USD is diplayed in Billing Summary Pgae", homePage.isFieldVisible("TotalOrderUSD"));
		assertTrue("Total Order Price for 1qty  is diplayed in Billing Summary Pgae", homePage.isFieldVisible("TotalOrderPrice"));
		
		String ATaxPrice = driver.findElement(By.xpath(".//*[@id='root']/div/div/div[2]/div[2]/div/div[4]/div/div[2]/div[2]/div")).getText();
		String ATotalPrice = driver.findElement(By.xpath(".//*[@id='root']/div/div/div[2]/div[2]/div/div[5]/div/div[2]/div[2]/div")).getText();
		
		assertEquals(ATaxPrice, ETaxPrice);
		assertEquals(ATotalPrice, ETotalPrice);
		Util.sleep(2000);
		homePage.click("OrderSummarySubmit");

		
		//Navigating to Order Completion  Page
		
		PageRedirectWaitFor("OrderCheader");
		Util.printInfo("Order Completion Page is loaded Successfully");
		assertTrue("Order Completion Header is displayed", homePage.isFieldVisible("OrderCheader"));
		assertTrue("Order Number is displayed", homePage.isFieldVisible("OrderNumText"));
		String OrderNum = driver.findElement(By.xpath(".//*[@id='root']/div/div/div[1]/div/div[1]/div[1]/div[1]/div/span[2]")).getText();
		Util.printInfo("Order was successfull placed and the Order Number ::" +OrderNum);
		
		String OTotalPrice=driver.findElement(By.xpath(".//*[@id='root']/div/div/div[1]/div/div[1]/div[1]/div[2]/div[3]/span")).getText();
		compareStrings(ETotalPrice, OTotalPrice);
		assertEquals(ETotalPrice, OTotalPrice);
		
		
		//Validate Date
		String Odate =driver.findElement(By.xpath(".//*[@id='root']/div/div/div[1]/div/div[1]/div[1]/div[2]/div[1]/span[2]")).getText();
		  Odate=   Odate.replace(" ","/");
		  Odate = Odate.replace(",", "").trim();	
		  Date date = new Date();
		    String DATE_FORMAT = "MM/dd/yyyy";
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		    String Cdate =sdf.format(date);  Cdate=Cdate.substring(2);
		    Formatter fmt = new Formatter();
		      Calendar cal = Calendar.getInstance();  fmt = new Formatter(); String fDate=fmt.format(" %tb", cal).toString(); fDate=fDate.concat(Cdate).trim();
		   
		      Util.sleep(10000);
		    assertEquals(Odate, fDate);
		    
		//Validate QTY
		    String OrderQTY =driver.findElement(By.xpath(".//*[@id='root']/div/div/div[1]/div/div[1]/div[1]/div[4]/div[3]/span")).getText();
		   
		    OrderQTY= OrderQTY.replace("Qty:", " ").trim();;
	
		    assertEquals(OrderQTY, testProperties.getConstant("SingleQTY"));
	//Validating Thankyou Message text on Order Confirmation page
		    Util.PrintInfo("Validating Thankyou Message text on Order Confirmation page");
		ValidateGuiElements("OrderSummaryTYmsg", testProperties.getConstant("OrderSummaryTYmsg"));
		ValidateGuiElements("OderSummaryTYemail", testProperties.getConstant("OderSummaryTYemail")+" "+testProperties.getConstant("Eemail"));
	}

	
	public String PaymentVerify(String Address){
		
		
		if(homePage.isFieldVisible("PayOptions")){
			getGUIBilldetails();
			
			if(getEnvironment().equalsIgnoreCase("dev")){
				Address=Address+"_"+"DEV";
			}else{
				Address=Address+"_"+"STG";
			}
			assertEquals(AName, Ename);
			assertEquals(Aaddress, testProperties.getConstant(""+Address+""));
			assertEquals(Acity, Ecity);
			assertEquals(Azipcode, Ezipcode);
			assertEquals(AEmail, Eemail);
		}else {
			EISTestBase.fail("FATAL ERROR: Billing Options is not displayed");
		}
		
		return Util.PrintInfo("Billing information Verifications is Completed");
	}
	
	public  void getGUIBilldetails(){
		

		 AName = driver.findElement(By.xpath(".//*[@id='billingForm']/div/div[2]/div/div[2]/div")).getText().trim();
		 Aaddress = driver.findElement(By.xpath(".//*[@id='billingForm']/div/div[2]/div/div[3]/div")).getText().trim();
		 Acity = driver.findElement(By.xpath(".//*[@id='billingForm']/div/div[2]/div/div[4]/div")).getText().trim();
		 Azipcode = driver.findElement(By.xpath(".//*[@id='billingForm']/div/div[2]/div/div[5]/div")).getText().trim();
		 AEmail = driver.findElement(By.xpath(".//*[@id='billingForm']/div/div[2]/div/div[6]/div")).getText().trim();
		
	}
	
	public void BillSummary(String Address){
		
		if(getEnvironment().equalsIgnoreCase("dev")){
			Address=Address+"_"+"DEV";
		}else{
			Address=Address+"_"+"STG";
		}
		try {
			List<WebElement>address=homePage.getMultipleWebElementsfromField("SummaryBillDetails");
			
				
				String name=address.get(0).getText();
				System.out.println(name);
				String billingAddress=address.get(1).getText().trim();
				System.out.println(billingAddress);
				String cityNState=address.get(2).getText().trim();
				System.out.println(cityNState);
				String zipCode=address.get(3).getText().trim();
				System.out.println(zipCode);
				String email=address.get(4).getText().trim();
				System.out.println(email);
				
				assertTrueCatchException("Name validation passed both the page",assertEquals(name.trim(), Ename));
				assertTrueCatchException("Billing address the validation passed both the page",assertEquals(billingAddress, testProperties.getConstant(Address)));
				assertTrueCatchException("Billing address the validation passed both the page",assertEquals(cityNState.trim(), Ecity));
				assertTrueCatchException("Billing address the validation passed both the page",assertEquals(zipCode.trim(), Ezipcode));
				assertTrueCatchException("Billing address the validation passed both the page",assertEquals(email.trim(), Eemail));
				
				

			
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

public void SelectMastercard(){
	WebElement option = driver.findElement(By.xpath(".//*[@id='billingForm']/div/div[1]/div/div[2]/select"));
	Select select = new Select(option);
	select.selectByValue("1");
	Util.printInfo("MasterCard is selected");
}
	

public void Verifycard(){
	String TestUserRan=null;
	WebElement Cardoption = driver.findElement(By.xpath(".//*[@id='billingForm']/div/div[1]/div/div[2]/select"));
	Select select = new Select(Cardoption);
	select.selectByValue("");
	assertTrue("After selecting Add card user is taken to enter Card Details Page",homePage.isFieldVisible("p4SecurityText"));
	
	for(int i=0;i<5;i++){
		try {
			 TestUserRan=generateRandomString(10,Mode.ALPHA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	homePage.populateField("AddUserFN", AddCardFName+TestUserRan);
	homePage.populateField("AddUserLN", AddCardLName+TestUserRan);
	homePage.populateField("AddUserCardNum", AddCardNumber);
	homePage.populateField("AddUserCode", AddCardCode);
	homePage.populateField("AddUserBilling", AddCardEaddress);
	homePage.populateField("AddUserCity", AddCardEcity);
	homePage.populateField("AddUserZip", AddCardEzipcode);
	homePage.populateField("AddCardMonth", testProperties.getConstant("SelectExpMonth"));
	homePage.populateField("AddCardYYYY", testProperties.getConstant("SelectExpYear"));
	homePage.populateField("AddCardState", testProperties.getConstant("SecurityCodeText"));
	
	
	String FirstName=AddCardFName+TestUserRan;
	String LastName=AddCardLName+TestUserRan;
	
	verifyButton("p4DisContinue");
	homePage.clickWait("PayAgreeCB", "PayAgreeCB", 10);		
	homePage.click("P3Continue");
	

	
	try {
		List<WebElement>address=homePage.getMultipleWebElementsfromField("SummaryBillDetails");
		
			
			String name=address.get(0).getText();
			System.out.println(name);
			String billingAddress=address.get(1).getText().trim();
			System.out.println(billingAddress);
			String cityNState=address.get(2).getText().trim();
			System.out.println(cityNState);
			String zipCode=address.get(3).getText().trim();
			System.out.println(zipCode);
			String email=address.get(4).getText().trim();
			System.out.println(email);
			
			assertTrueCatchException("Name validation passed both the page",assertEquals(name.trim(), FirstName+" "+LastName));
			assertTrueCatchException("Billing address the validation passed both the page",assertEquals(billingAddress, testProperties.getConstant(LastName)));
			assertTrueCatchException("Billing address the validation passed both the page",assertEquals(cityNState.trim(), Ecity));
			assertTrueCatchException("Billing address the validation passed both the page",assertEquals(zipCode.trim(), Ezipcode));
			assertTrueCatchException("Billing address the validation passed both the page",assertEquals(email.trim(), AddCardEemail));
			
			address.clear();

		
	} catch (MetadataException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

		

	
}


public static enum Mode {
    ALPHA, ALPHANUMERIC, NUMERIC 
}


public static String generateRandomString(int length, Mode mode) throws Exception {

	StringBuffer buffer = new StringBuffer();
	String characters = "";

	switch(mode){
	
	case ALPHA:
		characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		break;
	
	case ALPHANUMERIC:
		characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		break;

	case NUMERIC:
		characters = "1234567890";
	    break;
	}
	
	int charactersLength = characters.length();

	for (int i = 0; i < length; i++) {
		double index = Math.random() * charactersLength;
		buffer.append(characters.charAt((int) index));
	}
	return buffer.toString();
}


public String PayoptionRead(){
	String Status = null;
	try{
		SelectMastercard();
		EISTestBase.fail("ERR:: Card option is Editable in Order Summary Page");
	}catch(Exception e){
		 Status ="Card Option is ReadOnly in Order Summary Page";
	}
	
	
	return Util.PrintInfo(Status);
}

public void validateEidtionPage(String editionToSelect) throws MetadataException{
	String selectEditionXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectBasicOrAdvanceOption", editionToSelect);
	assertTrue("Select your Edition page is displayed after clicking on Edition Link" ,homePage.checkIfElementExistsInPage(selectEditionXpath, 20));
	homePage.click(selectEditionXpath);
	Util.printInfo("Click on Continue button on Select Edition Page");
	homePage.click("enabledContinueButton");
	homePage.waitForFieldVisible("editionValue", 10);
	assertTrueCatchException("Edition value is displayed as expected in 'Select your plan' page. Expected: "+editionToSelect ,homePage.getValueFromGUI("editionValue").equalsIgnoreCase(editionToSelect));
	assertTrueCatchException("Plan value is displayed as expected in 'Select your plan' page before selecting plan. Expected: null" ,homePage.getValueFromGUI("diablePlanLinkINPlanPage").equalsIgnoreCase(""));
}
public void validateEditionAndPlan(String editionToCheck, String planToCheck) throws MetadataException{
	
	assertTrueCatchException("Edition value is displayed as expected in 'Select your plan' page. Expected: "+editionToCheck ,homePage.getValueFromGUI("editionValue").equalsIgnoreCase(editionToCheck));
	assertTrueCatchException("Plan value is displayed as expected in 'Select your plan' page before selecting plan. Expected: "+planToCheck ,homePage.getValueFromGUI("planTitleDisplayed").equalsIgnoreCase(planToCheck));
}


public void selectPlan(String planToSelect){
	String selectPlanXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectPlan", planToSelect);
	Util.printInfo("selecting plan "+planToSelect);
	homePage.click(selectPlanXpath);
}

public boolean  SelectEdtion(String editionToSelect){
	boolean status = false;

	String selectEditionXpath=homePage.createFieldWithParsedFieldLocatorsTokens("selectBasicOrAdvanceOption", editionToSelect);
	Util.printInfo("Select Edition as "+editionToSelect );
	homePage.click(selectEditionXpath);
	return status=true;
}

public void ClickOnContinuButtonOnEditionPage(){
	Util.printInfo("Click on Continue button");
	homePage.click("enabledContinueButton");
	homePage.waitForFieldVisible("editionValue", 10);
}
public void ClickOnEditionLink(){
	Util.printInfo("Going to click on Edition link in Select plan page");
	homePage.click("editionLink");
	Util.sleep(3000);
}
public void ClickOnContinueOnPlanPage(){
	Util.printInfo("Click on Continue button on 'select plan' page");
	homePage.click("enabledContinueButtonOnSelectPlanPage");
	homePage.waitForFieldVisible("quantityField", 10);
	Util.sleep(2000);
}

public String PageRedirectWaitFor(String fieldName){
	 String Status = "";

		List<String> temp = BornInCloudTestBase.homePage.getFieldLocators(""+fieldName+""); 
		for(int i=0;i<temp.size();i++){ 
		Util.sleep(2000); 
		String Xloc =temp.get(i).trim(); 
		 
		try{   
			
			WebDriverWait wait = new WebDriverWait(driver,60);
			Status=""+fieldName+ " : Displayed in GUI";
		       break; 
		} catch (Exception e){  
			
		   e.printStackTrace();  
		  Status ="Xpath not found "+Xloc+" for "+fieldName+"field is NOT displayed in GUI";  
		    
		   if(i==temp.size()-1){ 
		   EISTestBase.fail("Valid Xath Not Found for WebElement : \"" +fieldName+ " \"from "+temp+"is NOT displayed in GUI"); 
		   } 
		    
		   } 
		 
		} 
		
		
	return Util.PrintInfo(Status);
		
}

public void ValidateGuiElements(String fieldName, String ExpectedValue){
	 String Status = "";
	 String ActualText="";
		List<String> temp = BornInCloudTestBase.homePage.getFieldLocators(""+fieldName+""); 
		for(int i=0;i<temp.size();i++){ 
		Util.sleep(2000); 
		String Xloc =temp.get(i).trim(); 
		 
		try{   
			
			ActualText= driver.findElement(By.xpath(""+Xloc+"")).getText().trim();
			Status=""+fieldName+ " : Displayed in GUI";
		       break; 
		} catch (Exception e){  
			
		   e.printStackTrace();  
		  Status ="Xpath not found "+Xloc+" for "+fieldName+"field is NOT displayed in GUI";  
		    
		   if(i==temp.size()-1){ 
		   EISTestBase.fail("Valid Xath Not Found for WebElement : \"" +fieldName+ " \"from "+temp+"is NOT displayed in GUI"); 
		   } 
		    
		   } 
		 
		} 
		
		assertEquals(ActualText, ExpectedValue.trim());
		
		
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
		//finish();
	}
}
