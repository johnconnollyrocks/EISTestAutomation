package customerportal;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import common.EISTestBase;
import common.Util;


public class Test_Verify_ProductUpdates_Save_UpdateFile extends CustomerPortalTestBase{
	
	public String USERNAME = null;
	public String EMAIL = null;
	public String USER_NAME = null;
	public String PASSWORD = null;
	public String INSTANCE_NAME = null;
	public String SCEmailId=null;
	public String contractNumbr="";
	public String Contracts=null;
	private String downloadFilePathFolder=System.getenv("USERPROFILE")+"\\Downloads\\";
	final String downloadFilePath=System.getenv("USERPROFILE")+"\\Downloads\\Rover_Test_Prod_x64_1_1.msp";
	final String partDownloadFilePath=downloadFilePath+".part";
	public List<WebElement> numberProductUpdateList;
	public List<WebElement> numberDownloadButtons;
	public int count=0;
	File downloadfile = new File(downloadFilePath);
	boolean downloadConfirmation;
	String downlodButtonhref="";
	 
	
	
	public Test_Verify_ProductUpdates_Save_UpdateFile() throws IOException {
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
			if (getEnvironment().equalsIgnoreCase("dev")) {
				USERNAME = testProperties.getConstant("USERNAME_DEV");				
				PASSWORD = testProperties.getConstant("PASSWORD_DEV");
			} else if (getEnvironment().equalsIgnoreCase("stg")) {
				USERNAME = testProperties.getConstant("USER_NAME_STG");
				PASSWORD = testProperties.getConstant("PASSWORD_STG");
				
			}
			loginAsMyAutodeskPortalUser(USERNAME,PASSWORD);	
					
		}
		GoToProductUpdatesPage();
		
		numberProductUpdateList=productUpdatePage.getMultipleWebElementsfromField("productUpdateListNodes");
//		numberDownloadButtons=productUpdatePage.getMultipleWebElementsfromField("prouctUpdateDownloadButtonList");
		
		ValidateDowloadButton();
		
		logoutMyAutodeskPortal();
		
	}
	public void ValidateDowloadButton() throws Exception {
		
		for(int i=0;i<numberProductUpdateList.size();i++){
			count=count+1;
			Util.sleep(2000);
			productUpdatePage.scrollIntoViewOfMetadataElement(numberProductUpdateList.get(i));
			mouseHover(numberProductUpdateList.get(i));
			String prodName=numberProductUpdateList.get(i).getText().trim();
			String productUpdateDownLoadBtn=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("prouctUpdateDownloadButtonList",prodName);
			
			//For revoked pords, no download btn is shown hence doing this way:
			if(!productUpdatePage.isFieldVisible(productUpdateDownLoadBtn)){
				//if not visible check if the prod is revoked otherwise fail the test
				List<WebElement> lstDrawerbtns=productUpdatePage.getMultipleWebElementsfromField("articlesUpdatesDrawerBtn");
				lstDrawerbtns.get(i).click();//expand the drawer and pull the if the status is revoked
				String getStatusOfProd=productUpdatePage.createFieldWithParsedFieldLocatorsTokens("status", prodName);
				if (!productUpdatePage.getValueFromGUI(getStatusOfProd).equalsIgnoreCase("Revoked")){
					EISTestBase.fail("No Download button is found for the Product update: "+prodName+" Hence failing the test");
				}
				Util.sleep(2000);
				//collpase it 
				lstDrawerbtns.get(i).click();//expand the drawer and pull the if the status is revoked
				Util.sleep(2000);
				continue;
			}
				if (productUpdatePage.getValueFromGUI(productUpdateDownLoadBtn).equalsIgnoreCase("Download")){
				
				//Validate TC232-Download button appears when we hover over update row item
					// if if-clause is getting in then cond is success so, no need to evaluate again, just mention true 
				assertTrue("Download Button is present for product "+count,true);				
				
				//Delete the old file if it exist
				deleteOldFiles();
				
				//Mouse hover on the download button and check whether URL in href
//				mouseHover(numberDownloadButtons.get(i));	//not rquired				
//				downlodButtonhref=numberDownloadButtons.get(i).getAttribute("href");
				
				//get the recent webelement i.e productUpdateDownloadBtn
				//The href attribute is available for the HTML tag 'a' not for span hence
				String downloadBtnLocators=productUpdatePage.getFieldLocators(productUpdateDownLoadBtn).get(0);//it contains only one getting the very first one
				downloadBtnLocators=downloadBtnLocators.substring(0, downloadBtnLocators.lastIndexOf("/"));
				//add the field to existing fieldData
				productUpdatePage.addNewFieldToExistingfieldMetadataList("newDownloadBtnLoc", "LINK##"+downloadBtnLocators);
				String href=productUpdatePage.getAttribute("newDownloadBtnLoc", "href");
				//validate the URL works fine 
				assertTrueWithFlags("The Download product update URL should be valid",checkDownloadurl(href));
				if(href!=null){
					downlodButtonhref=href;
				}else{
					EISTestBase.fail("Unable to get the url for the Product download button"+productUpdateDownLoadBtn);
				}
				
				//Validate TC278-To test the user is redirected to the appropriate URLs
				if (!downlodButtonhref.isEmpty()){
					assertTrue("URL which will be navigated after clicking on Download button is URL: "+downlodButtonhref,true);
				}else{
					EISTestBase.fail("href attribute is null for the download button.");
				}
				Util.printInfo("checked the url");
				Util.sleep(5000);
				//Click on download button 
//				numberDownloadButtons.get(i).click();
				productUpdatePage.isFieldVisible("newDownloadBtnLoc");
//				productUpdatePage.getCurrentWebElement().click();
				productUpdatePage.click("newDownloadBtnLoc");
				
				Util.printInfo("clicked the download button");
				Util.sleep(4000);
				downloadConfirmation=productUpdatePage.checkIfElementExistsInPage("downloadConfirmation",20);
				
				//Validate TC233-Clicking Download button redirects the user to appropriate page
				//Validate TC279-To test pop-up messages
				if (downloadConfirmation){
					Util.printInfo("asserting  download button");
					assertTrue("Download Confimation message is displayed after clicking on Download button. ",downloadConfirmation);
				}else{
					EISTestBase.fail("Download Confimation message is not displayed after clicking on Download button.");
				}
				
				Util.sleep(2000);
				
				if (!(getAppBrowser().equalsIgnoreCase("chrome"))){
					
							//Execute AutoIT file to click on save button
					//While running from Jenkins the user.dir is <src path>\build so to avoid this
				     String buildPath=System.getProperty("user.dir");
				     Util.printInfo("The auto it build file path is: "+buildPath);
				     String[] tempBuildpath=buildPath.split("\\\\");
				     if(tempBuildpath[tempBuildpath.length-1] ==tempBuildpath[tempBuildpath.length-2]){
				    	 Util.printInfo("Running from Jenkins, so truncate repeated build path");
				    	 buildPath=buildPath.substring(0,buildPath.lastIndexOf("\\"));
				    	 Util.printInfo("The Autoit file path is: "+buildPath);
				    	 
				     }else{
//				    	 buildPath=buildPath+"\\build";
				     }
				     if(!buildPath.contains("build"))
				     {
				    	 buildPath=buildPath+"\\build";
				     }
				     
			//	     Runtime.getRuntime().exec(buildPath+"\\PU_AutoITFile.exe "+getAppBrowser()+" "+"save"+" "+1);
//				     Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\PU_AutoITFile.exe "+getAppBrowser()+" "+"save"+" "+ 1);
				     Runtime.getRuntime().exec(buildPath+"\\PU_AutoITFile.exe "+getAppBrowser()+" "+"save"+" "+ 1);
				     Util.sleep(5000);
					//   Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\build\\PU_AutoITFile.exe "+getAppBrowser()+" "+"save"+" "+1);
							 //Wait until .part file disapears
						//     boolean bDownload=waitUntilPartDownloadFileExist();
							 
							 //Validate TC235-Download is local to the browser
							 //Validate TC277-To test the downloads are done locally
							 if(downloadfile.exists()){								 
								 assertTrue("File dowloaded sucessfully. File Path: "+downloadfile,true);
							 }else{
								 EISTestBase.fail("File Not found. File has been not downloaded");
							 }
				}
				
			
			}else{
				EISTestBase.fail("Download Button is not pressent for product :: "+count);
			}
			
		}
	}
	
	public boolean waitUntilPartDownloadFileExist(){
		boolean downloadComplete=false;
		int numTimes=0;
		/*if(getEnvironment().equalsIgnoreCase("dev")){			
			File partDownloadfile = new File(partDownloadFilePath);
			while (!downloadComplete || numTimes==10){
				if(partDownloadfile.exists()){
					Util.sleep(5000);
				}else{
					downloadComplete=true;
					Util.sleep(5000);
				}
				numTimes=numTimes+1;	
			}
		}else*/
		//for DEV,STG or PRD
		/*{*/
			/*File partDownloadfile = new File(downloadFilePathFolder);
			File[] myFiles=null;
			while (!downloadComplete || numTimes==10){
				Util.sleep(5000);	//pause for 5 sec
				myFiles=partDownloadfile.listFiles(new FilenameFilter() {				
					//the files will be always starting with Rover
					@Override
					public boolean accept(File dir, String name) {
						return name.startsWith("Rover");
					}
				});
				for(File myDownloadedFile: myFiles){
					downloadComplete=myDownloadedFile.exists();					
				}
				numTimes=numTimes+1;	
			}*/
			while (!downloadComplete || numTimes==10){
				Util.sleep(5000);	//pause for 5 sec
				downloadComplete=isFileDownloaded();
				numTimes=numTimes+1;	
			}
		/*}*/
		return downloadComplete;
		
	}
	
/*	public void mouseHover(WebElement element){
		
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
		Util.sleep(4000);
	}*/
	
	public void deleteOldFiles(){
		
	/*	if (getEnvironment().equalsIgnoreCase("dev")){
			if(downloadfile.exists()){
				Util.printInfo("Old file found. Deleting old file");
				downloadfile.delete();
			}
			File partDownloadfile = new File(partDownloadFilePath);
			if(partDownloadfile.exists()){
				partDownloadfile.delete();
			}
		}else{*/
			//ON DEV, STG or PRD			
			//IN STG  the file nmaes are dynamic so delete files matches with Rover as suffix
			
			File partDownloadfile = new File(downloadFilePathFolder);
			File[] myFiles=partDownloadfile.listFiles(new FilenameFilter() {
				
				//the files will be always starting with Rover
				@Override
				public boolean accept(File dir, String name) {
					return name.startsWith("Rover");
				}
			});		
			for(File oldDownloadFile: myFiles){
				oldDownloadFile.delete();
			}
		 }
		/*File partDownloadfile = new File(partDownloadFilePath);*/
		
/*		if(partDownloadfile.exists()){
			partDownloadfile.delete();
		}*/
//	}
	/**
	 * @Description: Gets the download file name without any extension
	 * @return
	 */
	public String getDownloadFileName() {
		String downloadHrefFilename=downlodButtonhref;
		int iPos=downloadHrefFilename.lastIndexOf("/");
		downloadHrefFilename=downloadHrefFilename.substring(iPos+1,downloadHrefFilename.length());
		//now truncate the extnesions
		iPos=downloadHrefFilename.lastIndexOf(".");
		downloadHrefFilename=downloadHrefFilename.substring(0,iPos);	//tweaks the file name
		return downloadHrefFilename;
			
	}
	/**
	 * Description: as the file url can end with .msp or exe hence find it either of them
	 * @return
	 * @throws Exception
	 */
	public boolean isFileDownloaded()  {
		
		File myFolder = new File(downloadFilePathFolder);
		File[] allFiles=myFolder.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File myFolder, String name) {				
				return (name.toLowerCase().endsWith(".msp")||name.toLowerCase().endsWith(".exe"));
			}
		});
		// i.e file got downloaded
		 if(allFiles.length>1){
			 return true;
		 }
		
		return false;	//default value
	}
	public boolean checkDownloadurl(String urlToCheck) throws Exception {		
		HttpClient	client = HttpClientBuilder.create().build();
		HttpGet request= new HttpGet(urlToCheck);
		HttpResponse response=client.execute(request);
		//now get the response headers : content type and content -length and see that link is working
		
		boolean checkURL=false;
		Header[] header=response.getAllHeaders();
		int j=0;
		for(Header myheader:header) {
			if (j<2)	//do this only for required headers
			 checkURL=false;
			/*System.out.println("the header: "+myheader.getName()+"  and the value is :"+myheader.getValue());*/
			if (myheader.getName().equalsIgnoreCase("Content-Length")) {
				if (Integer.valueOf(myheader.getValue()) >0){
					checkURL=true;
					j++;
				}
			}else if (myheader.getName().equalsIgnoreCase("Content-Type")) {
				if (myheader.getValue().equalsIgnoreCase("application/octet-stream")){
					checkURL=true;
					j++;

				} 
			}
		}
		return checkURL;
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
