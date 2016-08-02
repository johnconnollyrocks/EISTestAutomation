package bornincloud;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.EISTestBase;
import common.Util;
import common.exception.MetadataException;

public class Test_Verify_US1977_TC804 extends BornInCloudTestBase {
	String expireDate = null;
	String renewalDate = null;
	String name = null;
	String creditCard = null;
	String editcreditCard = null;
	List<String> productslist = null;
	List<String> contractDeatils = new ArrayList<String>();
	String ContractNum=null;

	int j =0;

	public Test_Verify_US1977_TC804() {
		super("browser");
		// TODO Auto-generated constructor stub
	}

	@Before
	public void launchBrowser() {

		launchIPP(getPortalURL());
	}

	@Test
	public void Test_Validate_US1333_TC519() throws Exception {

		boolean product = false;
	
		

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
		PageRedirectWaitFor("productsAndServicesLink");

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
				
				//Condition to check only BIC products
				System.out.println(productslist.get(j).length());
				
				if( (productslist.get(0).length()<5)){
					//Store the Contract Number to construct the Xpath
					if((j==0)){
						 ContractNum = productslist.get(j).trim();
						
					}					
					

	//loop through the elements in Row of the Table
					
					
					if(productslist.get(j).contains("Auto-renews")){
						product = true;
						//Construct Xpath of Renew/EndDate Icon
						String AutoIconXpath=homePage.createFieldWithParsedFieldLocatorsTokens("COTAUTOICON",ContractNum);
						Util.printInfo("Verifying Icon is displayed for Auto-renew");
						assertTrue("Expected Auto-renews ICOn for  contract number "+ContractNum+" found", homePage.checkIfElementExistsInPage(AutoIconXpath, 2000));
						Util.printInfo("Verify Details of Subscription page for Auto-renew Contract");
						AutorenewValidation(j,i);
						break;
					}
					
					
					else if(productslist.get(j).contains("Expired")){
						product = true;
						String ExpiredIconXpath=homePage.createFieldWithParsedFieldLocatorsTokens("COTIcon",ContractNum);
						Util.printInfo("Verifying Icon is displayed for Expired contract");
	                	assertTrue("Expired Iconn for  contract number "+ContractNum+" found", homePage.checkIfElementExistsInPage(ExpiredIconXpath, 2000));
	                	Util.printInfo("Verify Expired Contract is not greater than 30days");
	                	String ActDate1=homePage.getValueFromGUI(ExpiredIconXpath);
	                	String[] Temp = ActDate1.split("Expired");	
	                    ActDate1=Temp[1].toString().trim();
	                   
	                 	try{
	            			
	            			//Add 30 days to current system date
	            			DateFormat dateFormat= new SimpleDateFormat("MMM-dd-yyyy");
	            			Calendar c = Calendar.getInstance();    
	            			c.setTime(new Date());
	            			c.add(Calendar.DATE, 30);
	            			String d2=dateFormat.format(c.getTime());
	            			
	            			// gui READ DATE  MM-DD-YYYY
	            			//String Odate =driver.findElement(By.xpath(".//*[@id='root']/div/div/div[1]/div/div[1]/div[1]/div[2]/div[1]/span[2]")).getText();
	            			 String d1=  ActDate1;
	            			 d1=  d1.replace(" ","-");
	            			 d1 = d1.replace(",", "").trim();
	            				
	            					
	                		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
//	                		String d1="Nov-07-2014";
//	                		String d2 ="Oct-08-2014";
	                    	Date date1 = sdf.parse(d1);
	                    	Date date2 = sdf.parse(d2);
	             
	                    	System.out.println("Date1 :: "+sdf.format(date1));
	                    	System.out.println("Date2 :: "+sdf.format(date2));
	             
	                    	if(date1.compareTo(date2)>0){
	                    		System.out.println("Date1 is after Date2");
	                    		EISTestBase.fail("Expired Contract greater 30 days is displayed in 'Contract & Orders' page for contract :: " +ContractNum );
	                    	}else if(date1.compareTo(date2)<0){
	                    		System.out.println("Date1 is before Date2");
	                    	}else if(date1.compareTo(date2)==0){
	                    		System.out.println("Date1 is equal to Date2");
	                    	}else{
	                    		System.out.println("How to get here?");
	                    	}
	             
	                	}catch(ParseException ex){
	                		ex.printStackTrace();
	                	}
	            		
	                   	Util.printInfo("Verify Details of Subscription page for Expired Contract");
	                	ExpiredValidation(j,i);
						
						break;
					}
					
					else if(productslist.get(j).contains("Expires")){
						
						product = true;
						//Xpath for Expires is not created due test data issue, 
						String ExpiresIconXpath=homePage.createFieldWithParsedFieldLocatorsTokens("COTExpiresICON",ContractNum);
						assertTrue("Expected Auto-renews ICOn for  contract number "+ContractNum+" found", homePage.checkIfElementExistsInPage(ExpiresIconXpath, 2000));
						Util.printInfo("Verify Details of Subscription page for Expires Contract");
						ExpiresValidation(j,i);
						
						break;
					}
				}else{
					//Exclude all the contracts having length greater than 4 digits
					break;
				}
				
				
				//********************************************************************
				
			
				
				
			}
			
		}	
		
		if(!product){EISTestBase.failTest("Product Not Displayed, Change Test Data");}
		
		
		
		
		
		

		homePage.click("signout");
	}
	
	
	public void AutorenewValidation(int j, int i ) {
		
		// All the Auto Renewal Logic for validation in "Contract & Orders" page and "Subscription Details" page
		//Check  for Edit renewal link is displayed in the Table for Auto-Renew 

		Add_List("AutorenewValidation", j);
		contractDeatils.add(productslist.get(j+2));    //4. Edit renewal
		List<WebElement> contract;
		try {
			contract = homePage.getMultipleWebElementsfromField("Contract");
			contract.get(i).click();
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		PageRedirectWaitFor("DOSWaitFor");
		assertTrue("Edit Renewal link is displayed on DOS page",homePage.isFieldVisible("DOSEditRenewal"));
		
		String Actual = homePage.getValueFromGUI("DOSContNum");
		String Expected = contractDeatils.get(0);
		System.out.println( "GUI value : "+ Actual +  "List Value : " +Expected);
		assertTrue("Verify Contract Number is displayed correctly on DOS page",homePage.getValueFromGUI("DOSContNum").contains(contractDeatils.get(0)));
		
		Actual = homePage.getValueFromGUI("DOSType");
		Expected = contractDeatils.get(1);
		System.out.println( "GUI value : "+ Actual +  "   List Value : " +Expected);
		assertTrue("Verify Type is displayed correctly on DOS page",homePage.getValueFromGUI("DOSType").contains(contractDeatils.get(1)));
				
		Actual = homePage.getValueFromGUI("DOSExpDate");
		Expected = contractDeatils.get(2).trim();
		System.out.println( "GUI value : "+ Actual +  "  List Value : " +Expected);
		System.out.println("*************************************tst1********************");
		assertTrue(" Auto-renew Date is displayed correctly on DOS page",homePage.getValueFromGUI("DOSExpDate").contains(contractDeatils.get(2)));	
		assertTrue("Icon for  Auto-renew  is displayed ",homePage.isFieldVisible("DOSAutoRenIcon"));
		
		System.out.println("*************************************tst2********************");
		assertTrue("Icon for  Auto-renew  is displayed ",homePage.isFieldVisible("DOSEditRenewal"));
		/*homePage.click("DOSEditRenewal");
		PageRedirectWaitFor("DOSEditHeader");
		assertTrue(" Auto Renew button is displayed",homePage.isFieldVisible("DOSAutoRenew"));
		assertTrue(" Cancel button is displayed in EditRenewal modal",homePage.isFieldVisible("DOSAUTOCancel"));
		assertTrue(" Icon for Auto-renew is Displayed",homePage.isFieldVisible("DOSAutoRenIcon"));
		
		String Ccard = homePage.getValueFromGUI("DOSCardDetails");
		homePage.click("DOSAUTOClose");
		Util.sleep(3000);
		*/
		
		homePage.clickWait("DOSClose", "DOSClose", 10);
		contractDeatils.clear();
	}
	
	
	public void ExpiredValidation(int j, int i){
		// All the  Logic for  validation of Expired Orders in "Contract & Orders" page and "Subscription Details" page
		//Check  for Edit renewal link NOT  displayed in the Table for Expired Orders
		
		
		Add_List("ExpiredValidation", j);
		
		Util.printMessage("Products is displayed");
		try{
		contractDeatils.add(productslist.get(j+2));
		Util.printError("Edit Renewal buttton is displayed ");
		EISTestBase.fail("Edit Renewal link is displayed for Expired products");
		}catch(Exception e){
			Util.printMessage("Edit Renewal link is NOT displayed for Expired Contract");
		}
		System.out.println(contractDeatils);
		
		List<WebElement> contract;
		try {
			contract = homePage.getMultipleWebElementsfromField("Contract");
		
		contract.get(i).click();
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageRedirectWaitFor("DOSWaitFor");
		
		System.out.println(homePage.getValueFromGUI("DOSContNum"));
		String Actual = homePage.getValueFromGUI("DOSContNum");
		String Expected = contractDeatils.get(0);
		System.out.println( "GUI value : "+ Actual +  "List Value : " +Expected);
		assertTrue("Verify Contract Number is displayed correctly on DOS page",homePage.getValueFromGUI("DOSContNum").contains(contractDeatils.get(0)));
		
		Actual = homePage.getValueFromGUI("DOSType");
		Expected = contractDeatils.get(1);
		System.out.println( "GUI value : "+ Actual +  "List Value : " +Expected);
		assertTrue("Verify Type is displayed correctly on DOS page",homePage.getValueFromGUI("DOSType").contains(contractDeatils.get(1)));
		
		
		Actual = homePage.getValueFromGUI("DOSExpDate");
		Expected = contractDeatils.get(2);
		System.out.println( "GUI value : "+ Actual +  "List Value : " +Expected);
		assertTrue("Verify Expired Date is displayed correctly on DOS page",homePage.getValueFromGUI("DOSExpDate").contains(contractDeatils.get(2)));																		assertTrue("Icon for  Expired  is displayed ",homePage.isFieldVisible("DOSExpIcon"));
		
		
		homePage.clickWait("DOSClose", "DOSClose", 10);
		contractDeatils.clear();
		
	}

	
	public void ExpiresValidation(int j,int i){
		
		// All the  Logic for  validation of Order as Expires  in "Contract & Orders" page and "Subscription Details" page
	    //Check  for Edit renewal link is  displayed in the Table for Order as Expires
	
		Add_List("Expires Validation", j);
		contractDeatils.add(productslist.get(j+2));    //4. Edit renewal				
		List<WebElement> contract;
		try {
			contract = homePage.getMultipleWebElementsfromField("Contract");
			contract.get(i).click();
		} catch (MetadataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PageRedirectWaitFor("DOSWaitFor");
		assertTrue("Edit Renewal link is displayed on DOS page",homePage.isFieldVisible("DOSEditRenewal"));
		

		String Actual = homePage.getValueFromGUI("DOSContNum");
		String Expected = contractDeatils.get(0);
		System.out.println( "GUI value : "+ Actual +  "List Value : " +Expected);
		assertTrue("Verify Contract Number is displayed correctly on DOS page",homePage.getValueFromGUI("DOSContNum").contains(contractDeatils.get(0)));
		
		Actual = homePage.getValueFromGUI("DOSType");
		Expected = contractDeatils.get(1);
		System.out.println( "GUI value : "+ Actual +  "List Value : " +Expected);
		assertTrue("Verify Type is displayed correctly on DOS page",homePage.getValueFromGUI("DOSType").contains(contractDeatils.get(1)));
		
		
		Actual = homePage.getValueFromGUI("DOSEXpiresDate");
		Expected = contractDeatils.get(2);
		System.out.println( "GUI value : "+ Actual +  "List Value : " +Expected);
		assertTrue("Verify Expired Date is displayed correctly on DOS page",homePage.getValueFromGUI("DOSEXpiresDate").contains(contractDeatils.get(2)));																		assertTrue("Icon for  Expired  is displayed ",homePage.isFieldVisible("DOSExpIcon"));
		
		
		homePage.click("DOSEditRenewal");
		PageRedirectWaitFor("DOSEditHeader");
		assertTrue("Verify Auto Renew button is displayed",homePage.isFieldVisible("DOSAutoRenew"));
		assertTrue("Verify Cancel button is displayed in EditRenewal modal",homePage.isFieldVisible("DOSAUTOCancel"));
		
		//************************************
		try {
			assertTrue("Verify  Icon for Expires is Displayed",homePage.isFieldPresent("DOSExpiresIcon"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertTrue("Verify  Icon for Expires is Displayed",homePage.checkFieldExistence("DOSExpiresIcon"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			assertTrue("Verify  Icon for Expires is Displayed",homePage.constantExists("DOSExpiresIcon"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertTrue("Verify  Icon for Expires is Displayed",homePage.isFieldVisible("DOSExpiresIcon"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		String Ccard = homePage.getValueFromGUI("DOSCardDetails");
		homePage.click("DOSAUTOClose");
		Util.sleep(3000);
		homePage.clickWait("DOSClose", "DOSClose", 10);
		contractDeatils.clear();
		
	}
	
	
	public String  Add_List(String Caller, int j){
		String Status = null;
		contractDeatils.clear();
		try{
		contractDeatils.add(productslist.get(j-j));    //0.ID
		contractDeatils.add(productslist.get(j-1));    //1. Term
		contractDeatils.add(productslist.get(j));      //2. Date Expiry date
		contractDeatils.add(productslist.get(j+1));    //3. Product
		
		Status= "Successfully   Added "+contractDeatils+"Subs_ID, Term, Renew/Expired Date, Product ID to  "+ Caller;
		}catch(Exception e){
						e.printStackTrace();
			Status= "Failed to   Add Subs_ID, Term, Renew/Expired Date, Product ID to  "+ Caller;
			EISTestBase.fail("Error while Adding Subs_ID, Term, Renew/Expired Date, Product ID to the List " + Caller);
		}
		return Util.PrintInfo(Status);
	}
	
	public void VerifyIconAuto(String ContractNum){
		
	}
	
public void VerifyIconExpired(String Contract ){
		
	}

public void VerifyIconExpires(){
	
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
