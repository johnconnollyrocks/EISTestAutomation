package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import com.eviware.soapui.support.StringUtils;

import bornincloud.BornInCloudTestBase;

import common.EISConstants.AlertResponseType;
import common.EISConstants.CountryCodeType;
import common.EISConstants.LanguageCodeType;
import common.exception.MetadataException;

//           We are handling properties using Ant JVM properties - see http://stackoverflow.com/questions/683764/passing-junit-command-line-parameters-in-eclipse
//           for a way to do it using ant or Maven.  Be sure that the solution we chose is not dependent on running from within Eclipse!
//           Also (better) see http://www.velocityreviews.com/forums/t133059-passing-arguments-to-junit-classes-from-ant.html
//           DEFINITELY LOOK AT http://ideoplex.com/id/372/setting-java-system-properties-with-ant
//               (http://today.java.net/pub/a/today/2003/09/12/individual-test-cases.html is good, too)

/**
 * Representation of features and functionality common to all EIS applications.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public abstract class EISTestBase {
	//May be modified by Util.failTest()
	//May need to be set in one of the JUnit annotated methods, esp. if we run test suites
	private static String testResult	= EISConstants.TEST_PASSED_MESSAGE_TEXT;
	
	private String appName				= "";
	private String appBaseDir			= "";
	private String launchDriver			= "";
	private static String appBrowser 	= "";
	private EISConstants.App app	 	= null;

	private static String currentUser	= "";
	private static String currentUserName= "";

	private String testName				= "";

	private String testPropertiesFilenameKey= "";
	
	private static String appDir		= "";
	private String testDataDir			= "";
	private String resourceDir			= "";
	static String commonResourceDir	= "";
	
	private ResourceBundle resBun		= null;
	private String resourceBundleName	= "";
	public static boolean testStatus	= true;

	private int defaultNumCurrencyDecimalDigits	= EISConstants.DEFAULT_NUM_CURRENCY_DECIMAL_DIGITS;
	private String defaultCurrencyFormat= "%,." + Integer.toString(defaultNumCurrencyDecimalDigits) + "f";
	
	//This is the default URL.  If a URL is not specified anywhere else, this is the value that
	//  will be passed to baseURL.  Will be set to a system property in the constructor.
	private String appServerBaseURL			= "";

	//Possible values are STG, DEV
	//TODO  Make it an enum type
	private String environment				= EISConstants.DEFAULT_ENVIRONMENT;
	
	TestRecorder Rec = new TestRecorder();
	
	//These will be set based on the value of the environment variable
	private String autoUserName				= "";
	private String autoPassword				= "";
	private String partnerUserName			= "";
	private String partnerPassword			= "";
	
	//The default URL that is used when launchSalesforce is called without an argument.
	//  It is also set by launchSalesforce to the URL that was passed to it.  It defaults
	//  to the value of appServerBaseURL (will be set in the constructor).
	private String baseURL					= "";
	private String basePartnerURL			= "";
	private String baseCustomerURL			= "";

	//Set by open(String url) to the URL that was passed to it.  It defaults to the value
	//  of appServerBaseURL (will be set in constructor)
	private static String currentURL				= "";
	private static String jenkinsJobName			= "";
	private String strLaunchDriver;
	
	private Properties testManifest 		= new Properties();
	private Properties pageManifest 		= new Properties();
	private Properties windowManifest		= new Properties();
	private Properties commonPageManifest 	= new Properties();
	private Properties commonWindowManifest	= new Properties();
	
	protected Properties sysProps			= new Properties(System.getProperties());
	public static HtmlReport report;
	
	
	//Define only SFDC-scope windows here.  App-specific windows are defined in the appropriate
	//  [app]TestBase class
	public static Window_ mainWindow		= null;
	public static Window_ lookupPopUp		= null;

	//Define SFDC-scope Page objects that have no associated test properties.  App-specific pages
	//  are defined in the appropriate [app]TestBase class.  The pages defined here are those that:
	//    will never be referenced in a test properties file, AND
	//    do not contain app-specific GUI elements, AND
	//    have as a container window one of the Window objects defined in this class
	public static Page_ commonPage				= null;
	public static Page_ lookupPopUpPage			= null;
	public static Page_ setupPage				= null;
	public static Page_ blackTabPage			= null;
	public static Page_ loginPage				= null;
	public static Page_ emailClientPage			= null;

	//Verification flags.  Verifications are by default done in a case-sensitive manner, and the verification
	//  is deemed to have passed when the actual and expected results match.  The user can override these
	//  rules by setting these flags before calling any method that starts with "verify" or any "assert with
	//  flags" methods.
	//NOTE that is up to the user to manage the flags!  The "verify" methods WILL NOT set or clear them!
	private static boolean verifyCaseSensitive	= true;
	private static boolean verifyMatch			= true;
	private static boolean verifyUnMatch        =false;
	
	//Determines whether the assertXxxx methods actually fail the test, or merely return the boolean result
	private static boolean ignoreFailedAsserts	= false;
	
	//Page.clickToSubmit uses this to determine whether or not to fail the test when it finds a page-scope
	//  error message
	private static boolean failOnSubmitError	= true;
	
	//When ignoreFailedAsserts==true, the assertXxxx methods will set this variable
	private static String assertMessage			= "";
	private static String errorReason			= "";
	
	private static int numAsserts				= 0;
	
	private static int defaultPageRedrawDelay = EISConstants.DEFAULT_PAGE_REDRAW_DELAY;

	private static int defaultPageWaitTimeout = EISConstants.DEFAULT_PAGE_WAIT_TIMEOUT;
	
	public static TestProperties testProperties;
	
	public static WebDriver driver = null;
	
//	public static EventFiringWebDriver driver =null;
	String environmentForReport = sysProps.getProperty("environment");
	
	public static WebDriverListerners eventListener=null; 
	 
	private static String chromepath = EISConstants.TEST_BASE_PATH;
	
	 static ArrayList<String> parameterizedJobs = new ArrayList<String>(Arrays.asList(
				"2019_CEP_Regression_FunctionalValidation_VerifyProdFltrParam")); 
	
	 public static ArrayList<String> getParameterizedJobs() {
		return parameterizedJobs;
	}
	public void setParameterizedJobs(ArrayList<String> parameterizedJobs) {
		this.parameterizedJobs = parameterizedJobs;
	}
	/**
	 * Class constructor specifying the application's name and directory in the framework's source code hierarchy.
	 * @param appName the human-readable name of the application
	 * @param appBaseDir the application's directory in the framework's source code hierarchy (e.g., its package name)
	 */
	public EISTestBase(){
		//assign the default values as well
		this.testName		= sysProps.getProperty("testName");
		       /*appBaseDir=	BornInCloudTestBase.getBICDir();*/	//Sai: Pls dont hardcode the appBasedir here it will cause other tests to fail
		       appBaseDir=	System.getProperty("appDir");
		       
		       if (appBaseDir==null) {
		    	   //for all portal tests, the appBasedir is assigned via jvm args
		    	   //assuming the default directory for Bic calls. this constructor will be called mostly for Pelican/BUUC/BIC
		    	   appBaseDir=	BornInCloudTestBase.getBICDir();
		       }
		    		  
				/*appDir				= EISConstants.TEST_ROOT_PATH + appBaseDir + File.separator;
				
				testDataDir			= appDir + EISConstants.TEST_DATA_DIR;
				resourceDir			= appDir + EISConstants.RESOURCE_DIR;
				
				testManifest		= Util.loadPropertiesFile(testDataDir + EISConstants.TEST_MANIFEST_PROPERTIES_FILE);
				pageManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);*/

		    	appDir				= EISConstants.TEST_ROOT_PATH + appBaseDir + File.separator;
				
				testDataDir			= appDir + EISConstants.TEST_DATA_DIR;
				resourceDir			= appDir + EISConstants.RESOURCE_DIR;
				
				testManifest		= Util.loadPropertiesFile(testDataDir + EISConstants.TEST_MANIFEST_PROPERTIES_FILE);
				pageManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
				windowManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);

				commonResourceDir 	= EISConstants.TEST_BASE_PATH + EISConstants.RESOURCE_DIR;

				commonPageManifest		= Util.loadPropertiesFile(commonResourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
				commonWindowManifest	= Util.loadPropertiesFile(commonResourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);
				try {
					Rec.StartRecording();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				environment=System.getProperty("environment");	//assign env here
				
				Properties sysp = System.getProperties();
				if(sysp.toString().contains("Win")){
				
				report =new HtmlReport("TestReport",environmentForReport,testName);
				}else {
					System.out.println("HTML report is not been created on MAC Platform");
				}
				
				
			
	}
	protected void setAppDir(String appDir) {
		appBaseDir=appDir;
	}
	
	/**
	 * @Description this is used for Localized tests
	 * @param appName
	 * @param appBaseDir
	 * @param launchDriver
	 * @param isLocalized
	 */
	
	public EISTestBase(String testName,String appBaseDir,String launchDriver,boolean isLocalized) {
		this.testName		= testName;    	
    	this.appName		= appBaseDir;
    	this.appBaseDir		= appBaseDir;
    	this.launchDriver	= launchDriver;
    	try {
			Rec.StartRecording();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	report =new HtmlReport(launchDriver,environmentForReport,testName);
   	

    	if (launchDriver.equals("firefox")) {
    		driver=new FirefoxDriver();
    		
    		//Commented the EventFiring webdriver code 
//    		driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);		
    		//wait is required to navigate successfully due to selenium defect
    		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    	}
    	else if(launchDriver.equals("chrome")){	
    		System.setProperty("webdriver.chrome.driver", EISConstants.TEST_BASE_PATH + "chromedriver.exe");
    		ChromeOptions chromeOpt= new ChromeOptions();
    		Map<String, Object> userprefs = new HashMap<String, Object>();
    		//Turns off multiple download warning
    		userprefs.put( "profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1);
    		//Turns off download prompt
    		userprefs.put("download.prompt_for_download", false);	//to avoid the multiple file download issue
    		chromeOpt.addArguments("test-type");    		
    		driver=new ChromeDriver(chromeOpt);    		
    		//Commented the EventFiring webdriver code 
//    	    driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);
    	}
    	else if(launchDriver.equals("ie")){	
    		System.setProperty("webdriver.ie.driver", EISConstants.TEST_BASE_PATH + "IEDriverServer.exe");
    		DesiredCapabilities capabilities = new DesiredCapabilities();
    		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
    		driver=new InternetExplorerDriver(capabilities);
    		//Commented the EventFiring webdriver code 
//        	driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);
    	}else if(launchDriver.equals("safari")){
    		
    		driver=new SafariDriver();
    		//Commented the EventFiring webdriver code 
//    		driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);
    		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    	}

    	appDir				= EISConstants.TEST_ROOT_PATH + appBaseDir + File.separator;
		
		testDataDir			= appDir + EISConstants.TEST_DATA_DIR;
		resourceDir			= appDir + EISConstants.RESOURCE_DIR;
				
		commonResourceDir 	= EISConstants.TEST_BASE_PATH + EISConstants.RESOURCE_DIR;		
		
	}
    public EISTestBase(String appName, String appBaseDir,String launchDriver) {    	
    	this.testName		= sysProps.getProperty("testName");    	
    	this.appName		= appName;
    	this.appBaseDir		= appBaseDir;
    	this.launchDriver	= launchDriver;
    	try {
			Rec.StartRecording();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	report =new HtmlReport(launchDriver,environmentForReport,testName);
    	
    	//The location of this code may change, based on how we specify the appServerBaseURL property for the test.
    	//  Do it here?  Or in one of the setup (or setUp) methods?  Or in the @Before method?
    	appServerBaseURL	= sysProps.getProperty("appServerBaseURL");
    	baseURL				= appServerBaseURL;
    	currentURL			= appServerBaseURL;
    	//Must do this before creating any Page or Window objects!!!!
    	
//driver		= new FirefoxDriver(new FirefoxBinary(new java.io.File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe")), new FirefoxProfile(new java.io.File("ccc")));
//driver		= new FirefoxDriver(new FirefoxBinary(new java.io.File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe")), new FirefoxProfile());
    	//driver = new FirefoxDriver();
    	
    	if (launchDriver.equals("firefox")) {
    		driver = new FirefoxDriver();
//    		//wait is required to navigate successfully due to selenium defect
    		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    	}
    	else if(launchDriver.equals("chrome")){	
    		System.setProperty("webdriver.chrome.driver", EISConstants.TEST_BASE_PATH + "chromedriver.exe");
    		ChromeOptions chromeOpt= new ChromeOptions();
    		chromeOpt.addArguments("test-type");
    		chromeOpt.addArguments("allow-running-insecure-content");
    		driver=new ChromeDriver(chromeOpt);   
    		driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);    
    	}
    	else if(launchDriver.equals("ie")){	
    		System.setProperty("webdriver.ie.driver", EISConstants.TEST_BASE_PATH + "IEDriverServer.exe");
    		DesiredCapabilities capabilities = new DesiredCapabilities();
    		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
        	driver = new InternetExplorerDriver(capabilities);
    		
    	}else if(launchDriver.equals("safari")){
    		driver = new SafariDriver();    		
    		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    	}

    	appDir				= EISConstants.TEST_ROOT_PATH + appBaseDir + File.separator;
		
		testDataDir			= appDir + EISConstants.TEST_DATA_DIR;
		resourceDir			= appDir + EISConstants.RESOURCE_DIR;
		
		testManifest		= Util.loadPropertiesFile(testDataDir + EISConstants.TEST_MANIFEST_PROPERTIES_FILE);
		pageManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
		windowManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);

		commonResourceDir 	= EISConstants.TEST_BASE_PATH + EISConstants.RESOURCE_DIR;

		commonPageManifest		= Util.loadPropertiesFile(commonResourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
		commonWindowManifest	= Util.loadPropertiesFile(commonResourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);

		//TODO  Experiment with screenshots.  This code takes the screenshot, but we need
		//  to figure out what to do with it.  We don't want to store huge numbers of files,
		//  and I am not sure we can directly attach it to the results email.
		//The answer is to write the screenshot to the workspace dir (as I have below).  Then use Jenkins'
		//  email attachment feature (at the bottom of the configure page).  I tried it, but couldn't get
		//  it to work...
		//File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    //try {
		//	FileUtils.copyFile(scrFile, new File("C:\\Jenkins\\workspace\\CRM_LM_POC3\\xxx.png"));
		//} catch (IOException e) {}
	    //
	    //fail("ddd");
    }
    public EISTestBase(String appName, String appBaseDir) {
    	this.testName		= sysProps.getProperty("testName");
    	this.appName		= appName;
    	this.appBaseDir		= appBaseDir;
    	report =new HtmlReport("TestReport",environmentForReport,testName);
    	//this.launchDriver	= launchDriver;
    	
    	//The location of this code may change, based on how we specify the appServerBaseURL property for the test.
    	//  Do it here?  Or in one of the setup (or setUp) methods?  Or in the @Before method?
    	appServerBaseURL	= sysProps.getProperty("appServerBaseURL");
    	baseURL				= appServerBaseURL;
    	
    	currentURL			= appServerBaseURL;
    	//Must do this before creating any Page or Window objects!!!!
    	DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
    	System.setProperty("webdriver.ie.driver", EISConstants.TEST_BASE_PATH + "IEDriverServer.exe");
    	driver = new InternetExplorerDriver(capabilities);
//    	driver = new EventFiringWebDriver(event);
//		eventListener = new WebDriverListerners();
//		driver.register(eventListener);
//    	System.setProperty("webdriver.chrome.driver", EISConstants.TEST_BASE_PATH + "chromedriver.exe");
//   		driver = new ChromeDriver();
 //   	driver = new FirefoxDriver();
  	   	//Don't want to do this here, because we want to call mainWindow.setLocator(driver.getWindowHandle())
    	//  immediately after it.  And at this point in the code, mainWindow does not yet exist
    	//driver.get(appServerBaseURL);
    	
    	//!!!  Spawn a new WebDriverBackedSelenium object only when it is needed.  And when we do it, ALWAYS get
    	//  the wrapped WebDriver implementation back, so that the Selenium and WebDriver objects are in synch -
    	//  window focus, location (URL), etc.  Also, when creating the Selenium object, do not use baseURL; use
    	//  the current URL instead.
    	//  NOTE that WebDriverBackedSelenium.getUnderlyingWebDriver() has been deprecated; use getWrappedDriver() instead
    	//
    	//Here is how to do it
    	//  Selenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
    	//  	[your marvelous code that uses the selenium object]
    	//  driver = ((WebDriverBackedSelenium) selenium).getWrappedDriver();
    	
    	appDir				= EISConstants.TEST_ROOT_PATH + appBaseDir + File.separator;
		
		testDataDir			= appDir + EISConstants.TEST_DATA_DIR;
		resourceDir			= appDir + EISConstants.RESOURCE_DIR;
		
		testManifest		= Util.loadPropertiesFile(testDataDir + EISConstants.TEST_MANIFEST_PROPERTIES_FILE);
		pageManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
		windowManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);

		commonResourceDir 	= EISConstants.TEST_BASE_PATH + EISConstants.RESOURCE_DIR;

		commonPageManifest		= Util.loadPropertiesFile(commonResourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
		commonWindowManifest	= Util.loadPropertiesFile(commonResourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);

		//TODO  Experiment with screenshots.  This code takes the screenshot, but we need
		//  to figure out what to do with it.  We don't want to store huge numbers of files,
		//  and I am not sure we can directly attach it to the results email.
		//The answer is to write the screenshot to the workspace dir (as I have below).  Then use Jenkins'
		//  email attachment feature (at the bottom of the configure page).  I tried it, but couldn't get
		//  it to work...
//		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//	    try {
//			FileUtils.copyFile(scrFile, new File("C:\\Jenkins\\workspace\\CRM_LM_POC3\\xxx.png"));
//		} catch (IOException e) {}
//	    
//	    fail("ddd");
    }
    
    public EISTestBase(String appName, String appBaseDir,String launchDriver, String appBrowser) {
    	this.testName		= sysProps.getProperty("testName");
    	this.appName		= appName;
    	this.appBaseDir		= appBaseDir;
    	this.launchDriver	= launchDriver;
    	this.appBrowser 	= appBrowser;//sysProps.getProperty("appBrowser");
    	Rec =new TestRecorder();
    	try {
			Rec.StartRecording();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	report =new HtmlReport(appBrowser,environmentForReport,testName);
    	
    	System.out.println("browser :::: "+appBrowser);;
    	//The location of this code may change, based on how we specify the appServerBaseURL property for the test.
    	//  Do it here?  Or in one of the setup (or setUp) methods?  Or in the @Before method?
    	appServerBaseURL	= sysProps.getProperty("appServerBaseURL");
    	baseURL				= appServerBaseURL;
    	currentURL			= appServerBaseURL;
    	//Must do this before creating any Page or Window objects!!!!	
    	//kill browsers before you launch.. While running Dev suite the Firefox browser wont be opened if the firefox browser
    	//on that machine did not close properly.
    	killProcess("firefox");	//dont kill other browsers
    	    	
    	if (appBrowser.equals("firefox")) {
    		driver = new FirefoxDriver();
//    		driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);
    		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    	}
    	else if(appBrowser.equals("chrome")){	
    		System.setProperty("webdriver.chrome.driver", EISConstants.TEST_BASE_PATH + "chromedriver.exe");
    		ChromeOptions chromeOpt = new ChromeOptions();
    		chromeOpt.addArguments("-allow-running-insecure-content");
    		chromeOpt.addArguments("test-type");
    		driver=new ChromeDriver(chromeOpt); 
    		Util.sleep(5000);	// to allow the jscript to run
    		/*driver = new ChromeDriver();*/
//    	    driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);
    	}
    	else if(appBrowser.equals("ie")){	
    		System.setProperty("webdriver.ie.driver", EISConstants.TEST_BASE_PATH + "IEDriverServer.exe");
    		DesiredCapabilities capabilities = new DesiredCapabilities();
    		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
    		
        	driver = new InternetExplorerDriver(capabilities);
//        	driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);
    	}else if(appBrowser.equals("safari")){
    		driver = new SafariDriver();
//    		driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);
    		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    	}
    	//added for PhantomJS driver -Sai: for Pelican : 
    	else if (appBrowser.equalsIgnoreCase("headless")) {
    		try {
				loginToPortalAsHeaderLess();
				
			} catch (Exception e) {}
    	}
    	
    	
//    	System.setProperty("webdriver.chrome.driver", EISConstants.TEST_BASE_PATH + "chromedriver.exe");
//   		driver = new ChromeDriver();
   	
    	//Don't want to do this here, because we want to call mainWindow.setLocator(driver.getWindowHandle())
    	//  immediately after it.  And at this point in the code, mainWindow does not yet exist
    	//driver.get(appServerBaseURL);
    	
    	//!!!  Spawn a new WebDriverBackedSelenium object only when it is needed.  And when we do it, ALWAYS get
    	//  the wrapped WebDriver implementation back, so that the Selenium and WebDriver objects are in synch -
    	//  window focus, location (URL), etc.  Also, when creating the Selenium object, do not use baseURL; use
    	//  the current URL instead.
    	//  NOTE that WebDriverBackedSelenium.getUnderlyingWebDriver() has been deprecated; use getWrappedDriver() instead
    	//
    	//Here is how to do it
    	//  Selenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
    	//  	[your marvelous code that uses the selenium object]
    	//  driver = ((WebDriverBackedSelenium) selenium).getWrappedDriver();
    	
    	appDir				= EISConstants.TEST_ROOT_PATH + appBaseDir + File.separator;
		
		testDataDir			= appDir + EISConstants.TEST_DATA_DIR;
		resourceDir			= appDir + EISConstants.RESOURCE_DIR;
		
		testManifest		= Util.loadPropertiesFile(testDataDir + EISConstants.TEST_MANIFEST_PROPERTIES_FILE);
		pageManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
		windowManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);

		commonResourceDir 	= EISConstants.TEST_BASE_PATH + EISConstants.RESOURCE_DIR;

		commonPageManifest		= Util.loadPropertiesFile(commonResourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
		commonWindowManifest	= Util.loadPropertiesFile(commonResourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);

		//TODO  Experiment with screenshots.  This code takes the screenshot, but we need
		//  to figure out what to do with it.  We don't want to store huge numbers of files,
		//  and I am not sure we can directly attach it to the results email.
		//The answer is to write the screenshot to the workspace dir (as I have below).  Then use Jenkins'
		//  email attachment feature (at the bottom of the configure page).  I tried it, but couldn't get
		//  it to work...
		//File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    //try {
		//	FileUtils.copyFile(scrFile, new File("C:\\Jenkins\\workspace\\CRM_LM_POC3\\xxx.png"));
		//} catch (IOException e) {}
	    //
	    //fail("ddd");
    }
    public EISTestBase(String appName, String appBaseDir,String launchDriver, String appBrowser,boolean setPreferences,boolean modifiedProfileWithExtensions,
    		String... prefKeyAndValue) {
    	this.testName		= sysProps.getProperty("testName");
    	this.appName		= appName;
    	this.appBaseDir		= appBaseDir;
    	this.launchDriver	= launchDriver;
    	this.appBrowser 	= appBrowser;//sysProps.getProperty("appBrowser");
    	report =new HtmlReport(appBrowser,environmentForReport,testName);
    	
    	System.out.println("browser :::: "+appBrowser);;
    	//The location of this code may change, based on how we specify the appServerBaseURL property for the test.
    	//  Do it here?  Or in one of the setup (or setUp) methods?  Or in the @Before method?
    	appServerBaseURL	= sysProps.getProperty("appServerBaseURL");
    	baseURL				= appServerBaseURL;
    	currentURL			= appServerBaseURL;
    	//Must do this before creating any Page or Window objects!!!!
    	
    	//if you would like to modify the driver and its preferences so set need to set here
    	//The caller needs to provide preferences in this order: 'browser.download.useDownloadDir','true'
    	/**
    	 * Call this when you wanted to set firefox preferences for the webdriver profile   	 */
    	if (appBrowser.equals("firefox")) {  		
    		
    		/*if any driver preferences need to set then do the following*/
    		DesiredCapabilities capabilities =null;
    		FirefoxProfile driverProfile=null;
    		if (setPreferences){   
    			try {
    				/*webdriverProfile.getProfile(Util.getTestRootDir()+"\\build\\selenium");*/
    				/*driverProfile= new FirefoxProfile();					*/
    				File ffProfie= new File(Util.getTestRootDir()+"\\build\\selenium");
    				driverProfile=new FirefoxProfile(ffProfie);
    				if (prefKeyAndValue!=null){
    					for(int i=0;i<prefKeyAndValue.length-1;i++){    
    						//for boolean val
    						if (prefKeyAndValue[i+1].equalsIgnoreCase("true") ||prefKeyAndValue[i+1].equalsIgnoreCase("false")){
    							driverProfile.setPreference(prefKeyAndValue[i], Boolean.valueOf(prefKeyAndValue[i+1]));
    						}
    						//for numeric values
    						else if (prefKeyAndValue[i+1].matches("\\d+")){
    							driverProfile.setPreference(prefKeyAndValue[i], Integer.valueOf(prefKeyAndValue[i+1]));
    						}
    						else {
    							driverProfile.setPreference(prefKeyAndValue[i], prefKeyAndValue[i+1]);
    						}
    						i++;	//explicitly increment here to read the next key
    					}
    				}
    				capabilities= new DesiredCapabilities();
    		    	capabilities.setBrowserName("firefox");
    		    	capabilities.setPlatform(Platform.ANY);
    		    	capabilities.setCapability(FirefoxDriver.PROFILE, driverProfile);
    		    	driver = new FirefoxDriver(capabilities);
    		    	
//    				driver = new EventFiringWebDriver(event);
//    				eventListener = new WebDriverListerners();
//    				driver.register(eventListener);		
    				//wait is required to navigate successfully due to selenium defect
    				driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    			}
    			catch (Exception e) {
    				e.printStackTrace();
    				Util.printError("Unable to instantiate the browser object");
    				throw new RuntimeException("Unable to instantiate the browser object");
    			}
     			
    			
    		}	
    		//If modified Firefox profile driver is needed then do the following
    		if (modifiedProfileWithExtensions){
    			try {					
    				driverProfile=getWebDriverPreferencesForFirefoxProfile();
    				capabilities= new DesiredCapabilities();
    		    	capabilities.setBrowserName("firefox");
    		    	capabilities.setPlatform(Platform.ANY);
    		    	capabilities.setCapability(FirefoxDriver.PROFILE, driverProfile);
    		    	driver = new FirefoxDriver(capabilities);
//    	    		driver = new EventFiringWebDriver(event);
//    	    		eventListener = new WebDriverListerners();
//    	    		driver.register(eventListener);		
    	    		//wait is required to navigate successfully due to selenium defect
    	    		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				} catch (Exception e) {
					Util.printError("Unable to set the Firefox profile driver preferences");
					e.printStackTrace();
				}
    		 }
    		else{  
    			if (!setPreferences) {
    			driver = new FirefoxDriver();
//        		driver = new EventFiringWebDriver(event);
//        		eventListener = new WebDriverListerners();
//        		driver.register(eventListener);		
        		//wait is required to navigate successfully due to selenium defect
        		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    			}
    		 }
    	 
    	}
    	else if(appBrowser.equals("chrome")){	
    		System.setProperty("webdriver.chrome.driver", EISConstants.TEST_BASE_PATH + "chromedriver.exe");
    		//Did not help need to find out another way
    		ChromeOptions chromeOpt= new ChromeOptions();
    		chromeOpt.addArguments("test-type");	//to get rid of 'you are using an unsupported command-line flag: --ignore-certifcate-errors. Stability and security will suffer.'    		
    		Map<String, Object> userprefs = new HashMap<String, Object>();
    		//Turns off multiple download warning
    		userprefs.put( "profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 3 );
    		//Turns off download prompt
    		userprefs.put("download.prompt_for_download", false);	//to avoid the multiple file download issue
    		driver=new ChromeDriver(chromeOpt);
    		/*driver = new ChromeDriver();*/
//    	    driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);
    		
    	}
    	else if(appBrowser.equals("ie")){	
    		System.setProperty("webdriver.ie.driver", EISConstants.TEST_BASE_PATH + "IEDriverServer.exe");
    		DesiredCapabilities capabilities = new DesiredCapabilities();
    		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
    		driver = new InternetExplorerDriver(capabilities);
//        	driver = new EventFiringWebDriver(event);
//    		eventListener = new WebDriverListerners();
//    		driver.register(eventListener);
    	}
//    	System.setProperty("webdriver.chrome.driver", EISConstants.TEST_BASE_PATH + "chromedriver.exe");
//   		driver = new ChromeDriver();
   	
    	//Don't want to do this here, because we want to call mainWindow.setLocator(driver.getWindowHandle())
    	//  immediately after it.  And at this point in the code, mainWindow does not yet exist
    	//driver.get(appServerBaseURL);
    	
    	//!!!  Spawn a new WebDriverBackedSelenium object only when it is needed.  And when we do it, ALWAYS get
    	//  the wrapped WebDriver implementation back, so that the Selenium and WebDriver objects are in synch -
    	//  window focus, location (URL), etc.  Also, when creating the Selenium object, do not use baseURL; use
    	//  the current URL instead.
    	//  NOTE that WebDriverBackedSelenium.getUnderlyingWebDriver() has been deprecated; use getWrappedDriver() instead
    	//
    	//Here is how to do it
    	//  Selenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
    	//  	[your marvelous code that uses the selenium object]
    	//  driver = ((WebDriverBackedSelenium) selenium).getWrappedDriver();
    	
    	appDir				= EISConstants.TEST_ROOT_PATH + appBaseDir + File.separator;
		
		testDataDir			= appDir + EISConstants.TEST_DATA_DIR;
		resourceDir			= appDir + EISConstants.RESOURCE_DIR;
		
		testManifest		= Util.loadPropertiesFile(testDataDir + EISConstants.TEST_MANIFEST_PROPERTIES_FILE);
		pageManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
		windowManifest		= Util.loadPropertiesFile(resourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);

		commonResourceDir 	= EISConstants.TEST_BASE_PATH + EISConstants.RESOURCE_DIR;

		commonPageManifest		= Util.loadPropertiesFile(commonResourceDir + EISConstants.PAGE_MANIFEST_PROPERTIES_FILE);
		commonWindowManifest	= Util.loadPropertiesFile(commonResourceDir + EISConstants.WINDOW_MANIFEST_PROPERTIES_FILE);

		//TODO  Experiment with screenshots.  This code takes the screenshot, but we need
		//  to figure out what to do with it.  We don't want to store huge numbers of files,
		//  and I am not sure we can directly attach it to the results email.
		//The answer is to write the screenshot to the workspace dir (as I have below).  Then use Jenkins'
		//  email attachment feature (at the bottom of the configure page).  I tried it, but couldn't get
		//  it to work...
		//File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    //try {
		//	FileUtils.copyFile(scrFile, new File("C:\\Jenkins\\workspace\\CRM_LM_POC3\\xxx.png"));
		//} catch (IOException e) {}
	    //
	    //fail("ddd");
    }
    
   
	/**
	 * Gets the test result.
	 * @return The test result (typically EISConstants.TEST_PASSED_MESSAGE_TEXT or EISConstants.TEST_FAILED_MESSAGE_TEXT)
	 */
    protected static String getTestResult() {
    	return testResult;
    }
    
	/**
	 * Sets the test result.
	 * @param testResult the test result (typically EISConstants.TEST_PASSED_MESSAGE_TEXT or EISConstants.TEST_FAILED_MESSAGE_TEXT)
	 */
    protected static void setTestResult(String testResult) {
    	EISTestBase.testResult = testResult;
    }

	/**
	 * Gets the application name.
	 * @return The human-readable name of the application
	 */
    protected String getAppName() {
    	return appName;
    }
    
	/**
	 * Gets the application's relative source code directory.
	 * @return The application's directory in the framework's source code hierarchy (e.g., its package name)
	 */
    protected String getAppBaseDir() {
    	return appBaseDir;
    }

	/**
	 * Gets the SFDC app token.
	 * @return A token that maps to an app in the SFDC app chooser
	 */
    protected EISConstants.App getApp() {
    	return app;
    }

	/**
	 * Sets the SFDC app token.
	 * @param app the token that maps to one of the apps in the SFDC app chooser
	 */
    protected void setApp(EISConstants.App app) {
    	this.app = app;
    }

	/**
	 * Gets the user name of the currently logged in user.
	 * @return The user name of the currently logged in user. The value that is used when logging in
	 */
    public static String getCurrentUser() {
		return currentUser;
	}

	/**
	 * Sets the user name of the currently logged in user.
	 * @param currentUser the user name of the currently logged in user. The value that is used when logging in
	 */
    protected void setCurrentUser(String currentUser) {
		EISTestBase.currentUser = currentUser;
	}

	/**
	 * Gets the name of the currently logged in user.
	 * @return The name of the currently logged in user (not the value that is used when logging
	 * 	in, but the user's name as displayed in the header panel). It is truncated to 16 characters because
	 *  in the UI only 16 characters are displayed, followed by ...
	 */
    public static String getCurrentUserName() {
		return currentUserName;
	}

	/**
	 * Sets the name of the currently logged in user.
	 * @param currentUserName the name of the currently logged in user (not the value that is used when logging
	 * 	in, but the user's name as displayed in the header panel). It is truncated to 16 characters because
	 *  in the UI only 16 characters are displayed, followed by ...
	 */
    protected void setCurrentUserName(String currentUserName) {
    	EISTestBase.currentUserName = Util.left(currentUserName, 16);
	}

	/**
	 * Gets the name of the test.
	 * @return The name of the test. Typically passed to the framework in a JVM property named testName
	 */
    protected String getTestName() {
    	return testName;
    }

	/**
	 * Gets the key used for looking up the test properties file.
	 * @return The key used for looking up the test properties file in the TestManifest.&nbsp;properties file
	 */
    protected String getTestPropertiesFilenameKey() {
    	return testPropertiesFilenameKey;
    }

	/**
	 * Gets the application's absolute source code directory.
	 * @return The application's absolute source code directory (EISConstants.TEST_ROOT_PATH + appBaseDir + File.separator)
	 * @see #getAppBaseDir()
	 */
    protected static String getAppDir() {
    	return appDir;
    }

	/**
	 * Gets the application test data directory
	 * @return The application's test data directory (appDir + EISConstants.TEST_DATA_DIR),
	 * which is where test properties files and the test manifest are located
	 * @see #getAppDir()
	 */
    protected String getTestDataDir() {
    	return testDataDir;
    }

	/**
	 * Gets the application resource directory.
	 * @return The application's resource directory (appDir + EISConstants.RESOURCE_DIR),
	 * which is where the application's page and window properties (metadata) and
	 * manifests are located
	 * @see #getAppDir()
	 */
    protected String getResourceDir() {
    	return resourceDir;
    }

	/**
	 * Gets the common resource directory.
	 * @return The common (application-independent) resource directory (EISConstants.TEST_BASE_PATH + EISConstants.RESOURCE_DIR),
	 * which is where the common (application-independent) page and window properties (metadata) and
	 * manifests are located
	 */
    protected String getCommonResourceDir() {
    	return commonResourceDir;
    }

    protected ResourceBundle getResourceBundle() {
    	return resBun;
    }

    protected void setResourceBundle(String bundleName) {
		resBun = ResourceBundle.getBundle(bundleName);
		resourceBundleName = bundleName;
    }

    protected String getResourceBundleName() {
    	return resourceBundleName;
    }
    
	/**
	 * Gets the default number of decimal digits for currency values.
	 * @return The default number of decimal digits for currency values (default is EISConstants.DEFAULT_NUM_CURRENCY_DECIMAL_DIGITS)
	 */
    protected int getDefaultNumCurrencyDecimalDigits() {
    	return defaultNumCurrencyDecimalDigits;
    }

	/**
	 * Gets the default format string for currency values.
	 * @return The default format string for currency values (default is "%,." + Integer.toString(defaultNumCurrencyDecimalDigits) + "f")
	 * @see #getDefaultNumCurrencyDecimalDigits()
	 */
    protected String getDefaultCurrencyFormat() {
    	return defaultCurrencyFormat;
    }

	/**
	 * Gets the passed-in base URL of the application under test.
	 * @return The passed-in base URL of the application under test.
	 * Typically passed to the framework in a JVM property named
	 * appServerBaseURL, or provided in the test properties file
	 */
    protected String getAppServerBaseURL() {
    	return appServerBaseURL;
    }

	/**
	 * Gets the base URL of the application under test.
	 * @return The base URL of the application under test. Defaults to
	 * appServerBaseURL, but is reset every time launchSalesforce is called
	 * @see #getAppServerBaseURL()
	 * @see #launchSalesforce()
	 */
    protected String getBaseURL() {
    	return baseURL;
    }

    protected void setBaseURL(String baseURL) {
    	this.baseURL = baseURL;
    }
	/**
	 * Gets the partner portal base URL of the application under test.
	 * @return The partner portal base URL of the application under test.
	 * @see #launchSalesforce()
	 */
    protected String getBasePartnerURL() {
    	return basePartnerURL;
    }

	/**
	 * @return the appBrowser
	 */
	public static String getAppBrowser() {
		return appBrowser;
	}
	/**
	 * @param appBrowser the appBrowser to set
	 */
	public void setAppBrowser(String appBrowser) {
		this.appBrowser = appBrowser;
	}


    
    protected void setBasePartnerURL(String basePartnerURL) {
    	this.basePartnerURL = basePartnerURL;
    }

	/**
	 * Gets the environment token passed to the test method.
	 * @return The environment token (STG or DEV).
	 */
    protected String getEnvironment() {
    	return environment;
    }
    
	public String getAutoUserName() {
		return autoUserName;
	}

	public void setAutoUserName(String autoUserName) {
		this.autoUserName = autoUserName;
	}

	public String getAutoPassword() {
		return autoPassword;
	}

	public void setAutoPassword(String autoPassword) {
		this.autoPassword = autoPassword;
	}

	public String getPartnerUserName() {
		return partnerUserName;
	}

	public void setPartnerUserName(String partnerUserName) {
		this.partnerUserName = partnerUserName;
	}

	public String getPartnerPassword() {
		return partnerPassword;
	}

	public void setPartnerPassword(String partnerPassword) {
		this.partnerPassword = partnerPassword;
	}

	/**
	 * Gets the current URL of the application under test.
	 * @return The current URL of the application under test. Set every
	 * time a new page is loaded
	 */
    protected String getCurrentURL() {
    	return currentURL;
    }

	/**
	 * Gets the test manifest.
	 * @return The test manifest
	 */
    protected Properties getTestManifest() {
		//This deep copy is necessary (Properties objects are mutable)
		Properties newTestManifest = new Properties();
	    Enumeration<?> keys;
	    String key;

	    keys = testManifest.keys();
	    while (keys.hasMoreElements()) {
	    	key = (String) keys.nextElement();
	    	newTestManifest.setProperty(key, testManifest.getProperty(key));
	    }

		return newTestManifest;
	}

	/**
	 * Gets the verifyCaseSensitive field.
	 * @return The setting that determines whether asserts of
	 * string equality will be done on a case-sensitive basis (default is true)
	 */
	public static boolean isVerifyCaseSensitive() {
		return verifyCaseSensitive;
	}

	/**
	 * Sets the verifyCaseSensitive field.
	 * @param verifyCaseSensitive the setting that determines whether asserts of
	 * string equality will be done on a case-sensitive basis (default is true)
	 * @return The previous setting
	 */
	public static boolean setVerifyCaseSensitive(boolean verifyCaseSensitive) {
		boolean initialSetting = EISTestBase.verifyCaseSensitive;
		
		EISTestBase.verifyCaseSensitive = verifyCaseSensitive;
		
		return initialSetting;
	}

	/**
	 * Gets the verifyMatch field.
	 * @return The setting that determines whether a false return
	 * from an assert method is deemed as a failed assert (default is true).
	 * Useful for negative testing
	 */
	public static boolean isVerifyMatch() {
		return verifyMatch;
	}

	/**
	 * Sets the verifyMatch field.
	 * @param verifyMatch the setting that determines whether a false return
	 * from an assert method is deemed as a failed assert (default is true).
	 * @return The previous setting.
	 * Useful for negative testing
	 */
	public static boolean  setVerifyMatch(boolean verifyMatch) {
		boolean initialSetting = EISTestBase.verifyMatch;
		
		EISTestBase.verifyMatch = verifyMatch;
		
		return initialSetting;
	}

	/**
	 * Gets the ignoreFailedAsserts field.
	 * @return The setting that determines whether a failed
	 * assert method should not fail the test (default is false).
	 * Useful for debugging test code
	 */
	public static boolean isIgnoreFailedAsserts() {
		return ignoreFailedAsserts;
	}

	/**
	 * Sets the ignoreFailedAsserts field.
	 * @param ignoreFailedAsserts the setting that determines whether a failed
	 * assert method should not fail the test (default is false).
	 * @return The previous setting.
	 * Useful for debugging test code
	 */
	public static boolean setIgnoreFailedAsserts(boolean ignoreFailedAsserts) {
		boolean initialSetting = EISTestBase.ignoreFailedAsserts;
		
		EISTestBase.ignoreFailedAsserts = ignoreFailedAsserts;
		
		return initialSetting;
	}

	/**
	 * Gets the failOnSubmitError field.
	 * @return The setting that determines whether the test should fail when
	 * a page-level error is found when a form is submitted (default is true).
	 */
	public static boolean isFailOnSubmitError() {
		return failOnSubmitError;
	}

	/**
	 * Sets the failOnSubmitError field.
	 * @param failOnSubmitError the setting that determines whether the test should fail when
	 * a page-level error is found when a form is submitted (default is true).
	 * @return The previous setting.
	 */
	public static boolean setFailOnSubmitError(boolean failOnSubmitError) {
		boolean initialSetting = EISTestBase.failOnSubmitError;
		
		EISTestBase.failOnSubmitError = failOnSubmitError;
		
		return initialSetting;
	}

	/**
	 * Gets the most recent assert message.
	 * @return The message generated by the most recent assert.
	 */
    public static String getAssertMessage() {
		return assertMessage;
    }

	/**
	 * Gets the number of asserts that have been performed
	 * @return The number of asserts that have been performed
	 */
    public int getNumAsserts() {
		return numAsserts;
    }
    
	/**
	 * Gets the default page redraw delay.
	 * @return The time (in milliseconds) to wait after a non-read interaction with
	 * a GUI element (default is EISConstants.DEFAULT_PAGE_REDRAW_DELAY)
	 */
	public static int getDefaultPageRedrawDelay () {
		return defaultPageRedrawDelay;
	}
	
	/**
	 * Sets the default page redraw delay.
	 * @param defaultPageRedrawDelay the time (in milliseconds) to wait after a non-read interaction with
	 * a GUI element (default is EISConstants.DEFAULT_PAGE_REDRAW_DELAY)
	 */
	public static void setDefaultPageRedrawDelay (int defaultPageRedrawDelay) {
		EISTestBase.defaultPageRedrawDelay = defaultPageRedrawDelay;
	}
	
	/**
	 * Gets the default page load timeout.
	 * @return The time (in milliseconds) to wait for a page to load after the precipitating
	 * event (default is EISConstants.DEFAULT_PAGE_WAIT_TIMEOUT)
	 */
	public static int getDefaultPageWaitTimeout () {
		return defaultPageWaitTimeout;
	}
	
	/**
	 * Sets the default page load timeout.
	 * @param defaultPageWaitTimeout the time (in milliseconds) to wait for a page to load
	 * after the precipitating event (default is EISConstants.DEFAULT_PAGE_WAIT_TIMEOUT)
	 */
	public static void setDefaultPageWaitTimeout (int defaultPageWaitTimeout) {
		EISTestBase.defaultPageWaitTimeout = defaultPageWaitTimeout;
	}
	
	/**
	 * Gets the name of the test properties file.
	 * @param testPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return The fully-qualified name of the test properties file
	 * @see #getTestPropertiesFilenameKey()
	 */
    protected String getTestPropertiesFilename(String testPropertiesFilenameKey) {
		return doGetPropertiesFilename(testDataDir, testManifest, testPropertiesFilenameKey);
    }

	/**
	 * Gets the name of one of the application's page properties (metadata) files.
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * application's page manifest
	 * @return The fully-qualified name of the page properties file
	 */
    protected String getPagePropertiesFilename(String pagePropertiesFilenameKey) {
		return doGetPropertiesFilename(resourceDir, pageManifest, pagePropertiesFilenameKey);
    }

	/**
	 * Gets the name of one of the common (application-independent) page properties (metadata) files.
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * common page manifest
	 * @return The fully-qualified name of the page properties file
	 */
    private String getCommonPagePropertiesFilename(String pagePropertiesFilenameKey) {
		return doGetPropertiesFilename(commonResourceDir, commonPageManifest, pagePropertiesFilenameKey);
    }

	/**
	 * Gets the name of one of the application's window properties (metadata) files.
	 * @param windowPropertiesFilenameKey the key used for looking up the window properties file in the
	 * application's window manifest
	 * @return The fully-qualified name of the window properties file
	 */
    protected String getWindowPropertiesFilename(String windowPropertiesFilenameKey) {
		return doGetPropertiesFilename(resourceDir, windowManifest, windowPropertiesFilenameKey);
    }

	/**
	 * Gets the name of one of the common (application-independent) window properties (metadata) files.
	 * @param windowPropertiesFilenameKey the key used for looking up the window properties file in the
	 * common window manifest
	 * @return The fully-qualified name of the window properties file
	 */
    private String getCommonWindowPropertiesFilename(String windowPropertiesFilenameKey) {
		return doGetPropertiesFilename(commonResourceDir, commonWindowManifest, windowPropertiesFilenameKey);
    }

	/**
	 * Gets the name of a properties (metadata) file.
	 * @param path the default location of the properties file (defaults to the location of the manifest file)
	 * @param manifest the manifest to search 
	 * @param propertiesFilenameKey the key used for looking up the properties file in the manifest
	 * @return The fully-qualified name of the properties file
	 */
    private String doGetPropertiesFilename(String path, Properties manifest, String propertiesFilenameKey) {
    	String filename = manifest.getProperty(propertiesFilenameKey.toUpperCase(), "");
    	String filePath= "";
    	
    	if (filename.isEmpty()) {
			fail("Error while opening properties file: the value returned from the manifest using the file name key '" + propertiesFilenameKey + "' is blank");
    	}
    	
    	filePath = path + filename;
    	
    	if (!Util.fileExists(filePath)) {
			fail("Error while opening properties file: the value returned from the manifest using the file name key '" + propertiesFilenameKey + "' refers to the invalid path '" + filePath + "'");
    	}

		return filePath;
    }
	
	public boolean getSilentMode() {
		return Util.silentMode;
	}
	
	public void setSilentMode(boolean silentMode) {
		Util.silentMode = silentMode;
	}
	
	public boolean getDebugMode() {
		return Util.debugMode;
	}
	
	public void setDebugMode(boolean debugMode) {
		Util.debugMode = debugMode;
	}
	    
	@Override
	public String toString() {
		return "EISTestBase ["
				+ "appName=                             " + appName
				+ ", appBaseDir=	                      " + appBaseDir
				+ ", app=	       		                  " + app
				+ ", currentUser=		                  " + currentUser
				+ ", currentUserName=	                  " + currentUserName
				+ ", testName=                            " + testName
				+ ", testPropertiesFilenameKey=           " + testPropertiesFilenameKey
				+ ", appDir=	                          " + appDir
				+ ", testDataDir=                         " + testDataDir
				+ ", resourceDir=                         " + resourceDir
				+ ", resourceBundleName=            	  " + resourceBundleName
				+ ", appServerBaseURL=                    " + appServerBaseURL
				+ ", baseURL=                             " + baseURL
				+ ", basePartnerURL=                      " + basePartnerURL
				+ ", currentURL=                          " + currentURL
				+ ", environment=                         " + environment
				+ ", defaultPageRedrawDelay=              " + defaultPageRedrawDelay
				+ ", defaultPageWaitTimeout=              " + defaultPageWaitTimeout
				+ ", testResult=                          " + testResult
				+ ", numAsserts=                          " + numAsserts
				+ "]";
	}
    
	/**
	 * Configures high-level application-independent fields and objects
	 * @see #setup(String testPropertiesFilenameKey)
	 */
    protected void setup() {
    	setup(getTestPropertiesFilenameKeyProperty());
    }

	/**
	 * Configures high-level application-independent fields and objects&#58;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;loads test properties<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates common Page objects<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates common Window objects<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;sets defaultCurrencyFormat<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;sets defaultPageWaitTimeout<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;sets the WebDriver object's implicit wait value (default is defaultPageWaitTimeout)
	 * @param testPropertiesFilenameKey the key used for looking up the test properties file in the
	 * application's test manifest
	 * @see #createTestProperties(String testPropertiesFilenameKey)
	 * @see #createCommonWindows()
	 * @see #createCommonPages()
	 * @see #setDefaultCurrencyFormat()
	 * @see #setDefaultPageWaitTimeout(int defaultPageWaitTimeout)
	 */
    private void setup(String testPropertiesFilenameKey) {
    	//Create the properties object for the test; this call MUST be made before any Page or SFDCObject
    	//  instances can be created, and before any utilities in [app]TestBase can be called
    	createTestProperties(testPropertiesFilenameKey);
    	
    	createCommonWindows();
    	
    	createCommonPages();

    	setDefaultCurrencyFormat();
    	//See comments at EISConstants.SELENIUM_PAGE_WAIT_TIMEOUT_OVERRIDE for why we are doing this
    	//if (Globals.get().getSeleniumPageWaitTimeout() < EISConstants.SELENIUM_PAGE_WAIT_TIMEOUT_OVERRIDE) {
    	//	Globals.get().setSeleniumPageWaitTimeout(EISConstants.SELENIUM_PAGE_WAIT_TIMEOUT_OVERRIDE);
    	//}
    	
    	int pageWaitTimeoutProperty = Integer.parseInt(sysProps.getProperty("pageWaitTimeout", "0"));
    	if (pageWaitTimeoutProperty > 0) {
    		defaultPageWaitTimeout = pageWaitTimeoutProperty;
    	}
    	
    	//Store it back into sysProps, in case we need it later
    	sysProps.setProperty("pageWaitTimeout", Integer.toString(defaultPageWaitTimeout));
    	sysProps.setProperty("appBrowser", this.appBrowser);
    	
    	String environment = sysProps.getProperty("environment");
    	if (!environment.isEmpty()) {
    		this.environment = environment.trim().toUpperCase();
    	}
    	
    	//Store it back into sysProps, in case we need it later
    	sysProps.setProperty("environment", this.environment);
    	
//    	driver.manage().timeouts().implicitlyWait(defaultPageWaitTimeout, TimeUnit.MILLISECONDS);
    }

   
    /**
	 * Writes test result to console, along with the name of the test, test metadata,
	 * and the number of asserts that were performed
	 * @param result the test result (typically EISConstants.TEST_PASSED_MESSAGE_TEXT or EISConstants.TEST_FAILED_MESSAGE_TEXT)
	 * @throws Exception 
	 * @see #getTestName()
	 */
    protected void finish(String result){
    	String jobName=null;
    	String testStatus;
    	String errorDesc = null;
       	String environment;
       	String iterationNo=null;
    	Properties dbPropertyFile;
    	String dbConfigFileName = "DbConfiguration_STG.properties";
    	dbPropertyFile	= Util.loadPropertiesFile(commonResourceDir + dbConfigFileName);
    	environment=getEnvironment();
    	
    	if (EISConstants.REPORT_TEST_STATUS_IS_FAIL){
    		EISTestBase.reortTestStatus(false);
    	}else{
    		EISTestBase.reortTestStatus(true);
    	}
    	//Util.printInfo("");
    	//Util.printInfo(result + ": " + getTestName() + "\n" + testProperties.getTestMetadata());    	
    	//Util.printInfo("");
    	Util.printInfo(EISConstants.NUM_ASSERTS_PREFIX + numAsserts);    	
    	Util.printInfo("");
    	
    	//Code to fetch Jenkins Job Name from testdata properties file
    	if (StringUtils.isNullOrEmpty(testProperties.getConstant("JENKINS_JOB_NAME"))){
    		jobName=testProperties.getConstant("JENKINS_JOB_NAME");
    		
    	}
    	String[] status = result.split(" ");    	 
    	if (StringUtils.isNullOrEmpty(jobName)){
    		Util.printInfo(result + ": " + getTestName() + "\n" +    	
    		    	"JobName:                       " + jobName + "\n" +
    		    	"Result:                        " + status[1]);	
    	}
    	else {
    		Util.printInfo(result + ": " + getTestName() + "\n" +
    		    	"Result:                        " + status[1]);
    	}
    	   	
    	
    	//Util.printInfo("JobName" + ": " +jobName);
    	//Util.printInfo("\n" + "Result" + ": " +status[1]); 
    	
/*    	Util.printInfo(result + ": " + getTestName() + "\n" +    	
    	"JobName:                       " + jobName + "\n" +
    	"Result:                        " + status[1]);
*/    	testStatus=status[1];
    	if (!getAssertMessage().equalsIgnoreCase("")){
    	Util.printInfo("Error Description----"+getAssertMessage());
    	errorDesc=getAssertMessage();
    	}
    	else if (!getErrorReason().equalsIgnoreCase("")){
    	Util.printInfo("Error Description----"+getErrorReason());
    	errorDesc=getErrorReason();
    	}
    	else Util.printInfo("No Errors");
    	
    	
    	if (StringUtils.isNullOrEmpty(jobName)){
    		Util.printInfo("DB updation is not required");
    	   
    	}
    }

	/**
	 * Writes test result to console, along with the name of the test, test metadata,
	 * and the number of asserts that were performed
	 * @see #finish(String result)
	 */
    protected void finish() {
    	if (EISConstants.SCREEN_RECORDING){
    		Rec.StopRecording();
    	}
    	if (!testStatus){
    		EISTestBase.failTest("Some Assertions are Failed in the Test.");
    	}
    	
    	finish(testResult);
    	if (EISConstants.GLOBAL_BROWSERS_CLOSE){    		
    		killProcess();
    	}
     }

	/**
	 * Sets the default format string for currency values (setting is "%,." + Integer.toString(defaultNumCurrencyDecimalDigits) + "f").
	 * @see #getDefaultNumCurrencyDecimalDigits()
	 */
    private void setDefaultCurrencyFormat() {
    	String numDigits = testProperties.getConstant(EISConstants.NUM_CURRENCY_DECIMAL_DIGITS_OVERRIDE_CONSTANT_NAME);
    	
    	if (!numDigits.isEmpty()) {
    		try {
    			defaultNumCurrencyDecimalDigits	= Integer.parseInt(numDigits);
				defaultCurrencyFormat = "%,." + Integer.toString(defaultNumCurrencyDecimalDigits) + "f";
				
				Util.printInfo("The format used in default verification of currency values has been changed to \"" + defaultCurrencyFormat + "\"");
			} catch (NumberFormatException nfe) {
				Util.printWarning("The test-scope constant '" + EISConstants.NUM_CURRENCY_DECIMAL_DIGITS_OVERRIDE_CONSTANT_NAME + "' does not contain a numeric value; the format used in default verification of currency values will remain \"" + defaultCurrencyFormat + "\"");
			}
    	}
    }
    
    //Subclasses will implement this method to create app-specific Window objects
	/**
	 * Instantiates application-specific Window objects.
	 */
    protected abstract void createAppWindows();
    
	/**
	 * Instantiates common (application-independent) Window objects&#58;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;mainWindow<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;lookupPopUp
	 */
    private void createCommonWindows() {
        //Instantiate all windows that are not specific to a particular app
    	//NOTE that it is not totally clear at this point (Oct 2010) which windows and pages
    	//  are app-specific and which are not
     	mainWindow	= createCommonWindow("WINDOW_MAIN_PROPERTIES_FILE");
    	lookupPopUp	= createCommonWindow("WINDOW_LOOKUP_POPUP_PROPERTIES_FILE");
    }
    
	/**
	 * Instantiates common (application-independent) Page objects&#58;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;commonPage<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;commonPortalPage<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;loginPage<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;setupPage<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;lookupPopUpPage<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;blackTabPage<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;emailClientPage
	 */
	private final void createCommonPages() {
        //Instantiate pages that are not specific to a particular app.  The pages instantiated
		//  here are those that will never be referenced in a test properties file
    	//NOTE that it is not totally clear at this point (Oct 2010) which windows and pages
    	//  are app-specific and which are not
		//
		//The lookup page will be referenced in Page.doPopulate when a field of type LOOKUP is
		//  referenced in a test properties file 
    	commonPage				= createCommonPage("PAGE_COMMON_PROPERTIES_FILE");
    	loginPage				= createCommonPage("PAGE_LOGIN_PROPERTIES_FILE");
    	setupPage				= createCommonPage("PAGE_SETUP_PROPERTIES_FILE");
    	lookupPopUpPage			= createCommonPage("PAGE_LOOKUP_POPUP_PROPERTIES_FILE", lookupPopUp.getPageRedrawDelay());
    	blackTabPage			= createCommonPage("PAGE_BLACK_TAB_PROPERTIES_FILE");
    	emailClientPage			= createCommonPage("PAGE_EMAIL_CLIENT_PROPERTIES_FILE");
    }

	/**
	 * Instantiates a TestProperties object using the specified test properties file
	 * @param testPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 */
	private final void createTestProperties(String testPropertiesFilenameKey) {
    	//We are setting the instance variable only when calling the TestProperties constructor from THIS method!
    	//
    	//  The reason is that the constructor can get called many times, for utilities and such.  The class
    	//  member in this class is used to store the key to the primary test properties file.  By design, we
    	//  arrived at this method through the setup() call chain.  Therefore the value of the property has
		//  not been changed.
    	this.testPropertiesFilenameKey = testPropertiesFilenameKey;
    	
    	testProperties = new TestProperties(getTestPropertiesFilename(testPropertiesFilenameKey), testDataDir, testManifest);
    }

	public TestProperties gettestProperties(String testPropertiesFilenameKey) {
		String propFile=getTestPropertiesFilenameKeyProperty();
		createTestProperties(propFile);
		return testProperties;
	}
	
	/**
	 * Gets (from system properties) the key used for looking up the test properties file in the test manifest.
	 * @return The key used for looking up the test properties file in the test manifest
	 */
    protected final String getTestPropertiesFilenameKeyProperty() {
    	final String notFound = "not found";
    	
    	String testPropertiesFilenameKeyProperty = sysProps.getProperty("testPropertiesFilenameKey", notFound);
    	
    	//Ensure that the value of the property (e.g., the file name) was found, and begins with a digit, letter, or underscore
    	//If the property is blank, it means that 
    	if (testPropertiesFilenameKeyProperty.equals(notFound)) {
    		fail("The testPropertiesFilenameKey property does not exist");
    	}
    	
    	if (!testPropertiesFilenameKeyProperty.matches("\\w.*")) {
    		fail("The value of the testPropertiesFilenameKey property ('" + testPropertiesFilenameKeyProperty + "') is invalid");
    	}
    	
		Util.printDebug("testPropertiesFilenameKey parameter = '" + testPropertiesFilenameKeyProperty + "'");
    	
    	return testPropertiesFilenameKeyProperty;
    }
    
	/**
	 * Instantiates an application-specific Window object. 
	 * @param windowPropertiesFilenameKey the key used for looking up the window properties file in the
	 * application's window manifest
	 * @return A Window object
	 */
    protected final Window_ createWindow(String windowPropertiesFilenameKey) {
//    	return new Window(driver, getWindowPropertiesFilename(windowPropertiesFilenameKey),event);
     	return new Window(driver, getWindowPropertiesFilename(windowPropertiesFilenameKey));
    }

	/**
	 * Instantiates a common (application-independent) Window object. 
	 * @param windowPropertiesFilenameKey the key used for looking up the window properties file in the
	 * common window manifest
	 * @return A Window object
	 */
    private final Window_ createCommonWindow(String windowPropertiesFilenameKey) {
//    	return new Window(driver, getCommonWindowPropertiesFilename(windowPropertiesFilenameKey),event);
       	return new Window(driver, getCommonWindowPropertiesFilename(windowPropertiesFilenameKey));
    }

	/**
	 * Instantiates an application-specific Page object. 
	 * @param testProperties a TestProperties object
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * application's page manifest
	 * @return A Page object
	 */
    protected final Page_ createPage(TestProperties testProperties, String pagePropertiesFilenameKey) {
//    	return new Page(driver, testProperties, getPagePropertiesFilename(pagePropertiesFilenameKey),event);
    	return new Page(driver, testProperties, getPagePropertiesFilename(pagePropertiesFilenameKey));
    }

	/**
	 * Instantiates an application-specific Page object with a specified page redraw delay. 
	 * @param testProperties a TestProperties object
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * application's page manifest
	 * @param pageRedrawDelay the time (in milliseconds) to wait after a non-read interaction with
	 * a GUI element
	 * @return A Page object
	 */
    protected final Page_ createPage(TestProperties testProperties, String pagePropertiesFilenameKey, int pageRedrawDelay) {
//    	return new Page(driver, testProperties, getPagePropertiesFilename(pagePropertiesFilenameKey), pageRedrawDelay,event);
    	return new Page(driver, testProperties, getPagePropertiesFilename(pagePropertiesFilenameKey), pageRedrawDelay);
    }

	/**
	 * Instantiates an application-specific Page object. 
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * application's page manifest
	 * @return A Page object
	 */
    protected final Page_ createPage(String pagePropertiesFilenameKey) {
    	return createPage(testProperties, pagePropertiesFilenameKey);
    }
    
   
	/**
	 * Instantiates an application-specific Page object with a specified page redraw delay. 
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * application's page manifest
	 * @param pageRedrawDelay the time (in milliseconds) to wait after a non-read interaction with
	 * a GUI element
	 * @return A Page object
	 */
    protected final Page_ createPage(String pagePropertiesFilenameKey, int pageRedrawDelay) {
    	return createPage(testProperties, pagePropertiesFilenameKey, pageRedrawDelay);
    }

    //The createCommonPageInstance methods are for creating app-independent Page objects for which test properties
    //  are specified.  The purpose is to enable page metadata properties to be defined as app-independent (and therefore
    //  stored in the "common" package), thereby saving on duplication of the page metadata, but allow specifying
    //  test data in test properties
    
	/**
	 * Instantiates a common (application-independent) Page object for a page for which test properties are specified. 
	 * @param testProperties a TestProperties object
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * common page manifest
	 * @return A Page object
	 */
    protected final Page_ createCommonPageInstance(TestProperties testProperties, String pagePropertiesFilenameKey) {
//    	return new Page(driver, testProperties, getCommonPagePropertiesFilename(pagePropertiesFilenameKey),event);
    	return new Page(driver, testProperties, getCommonPagePropertiesFilename(pagePropertiesFilenameKey));
    }

	/**
	 * Instantiates a common (application-independent) Page object for a page for which test properties are specified,
	 * with a specified page redraw delay. 
	 * @param testProperties a TestProperties object
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * common page manifest
	 * @param pageRedrawDelay the time (in milliseconds) to wait after a non-read interaction with
	 * a GUI element
	 * @return A Page object
	 */
    protected final Page_ createCommonPageInstance(TestProperties testProperties, String pagePropertiesFilenameKey, int pageRedrawDelay) {
//    	return new Page(driver, testProperties, getCommonPagePropertiesFilename(pagePropertiesFilenameKey), pageRedrawDelay,event);
    	return new Page(driver, testProperties, getCommonPagePropertiesFilename(pagePropertiesFilenameKey), pageRedrawDelay);
    }

	/**
	 * Instantiates a common (application-independent) Page object for a page for which test properties are specified. 
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * common page manifest
	 * @return A Page object
	 */
    protected final Page_ createCommonPageInstance(String pagePropertiesFilenameKey) {
    	return createCommonPageInstance(testProperties, pagePropertiesFilenameKey);
    }

	/**
	 * Instantiates a common (application-independent) Page object for a page for which test properties are specified,
	 * with a specified page redraw delay. 
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * common page manifest
	 * @param pageRedrawDelay the time (in milliseconds) to wait after a non-read interaction with
	 * a GUI element
	 * @return A Page object
	 */
    protected final Page_ createCommonPageInstance(String pagePropertiesFilenameKey, int pageRedrawDelay) {
    	return createCommonPageInstance(testProperties, pagePropertiesFilenameKey, pageRedrawDelay);
    }
    
    //The createCommonPage methods are for creating app-independent Page objects for which no test properties
    //  are specified, such as pages that have no form fields, OR pages that do have form fields, but for which
    //  the user does not specify test data in test properties

	/**
	 * Instantiates a common (application-independent) Page object for a page for which no test properties are specified. 
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * common page manifest
	 * @return A Page object
	 */
    protected final Page_ createCommonPage(String pagePropertiesFilenameKey) {
//    	return new Page(driver, getCommonPagePropertiesFilename(pagePropertiesFilenameKey),event);
    	return new Page(driver, getCommonPagePropertiesFilename(pagePropertiesFilenameKey));
    }

	/**
	 * Instantiates a common (application-independent) Page object for a page for which no test properties are specified,
	 * with a specified page redraw delay. 
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * common page manifest
	 * @param pageRedrawDelay the time (in milliseconds) to wait after a non-read interaction with
	 * a GUI element
	 * @return A Page object
	 */
	protected final Page_ createCommonPage(String pagePropertiesFilenameKey, int pageRedrawDelay) {
//    	return new Page(driver, getCommonPagePropertiesFilename(pagePropertiesFilenameKey), pageRedrawDelay,event);
    	return new Page(driver, getCommonPagePropertiesFilename(pagePropertiesFilenameKey), pageRedrawDelay);
    }

    //The createStaticPage methods are typically called by [app]TestBase methods, for creating app-specific
    //  Page objects for which no test properties are required, such as pages that have no form fields, OR
    //  pages that do have form fields, but for which the user does not specify test data in test properties

	/**
	 * Instantiates an application-specific Page object for a page for which no test properties are specified. 
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * application's page manifest
	 * @return A Page object
	 */
    protected final Page_ createStaticPage(String pagePropertiesFilenameKey) {
//    	return new Page(driver, getPagePropertiesFilename(pagePropertiesFilenameKey),event);
    	return new Page(driver, getPagePropertiesFilename(pagePropertiesFilenameKey));
    }

	/**
	 * Instantiates an application-specific Page object for a page for which no test properties are specified, 
	 * with a specified page redraw delay. 
	 * @param pagePropertiesFilenameKey the key used for looking up the page properties file in the
	 * application's page manifest
	 * @param pageRedrawDelay the time (in milliseconds) to wait after a non-read interaction with
	 * a GUI element
	 * @return A Page object
	 */
    protected final Page_ createStaticPage(String pagePropertiesFilenameKey, int pageRedrawDelay) {
//    	return new Page(driver, getPagePropertiesFilename(pagePropertiesFilenameKey), pageRedrawDelay,event);
    	return new Page(driver, getPagePropertiesFilename(pagePropertiesFilenameKey), pageRedrawDelay);
    }

    //These number formatting methods will be called a lot, so we want to make it as simple as possible
    //  for the test code author.  To that end, we will not require him to call Util.formatNum. (And we
    //  won't do the formatting here - we want that utility available in the Util class)

	/**
	 * Formats a number using a specified format string.
	 * @param number the number to format
	 * @param format the format to use
	 * @return A formatted number
	 */
    protected static final String formatNumber(double number, String format) {
    	return Util.formatNumber(number, format);
    }
    
	/**
	 * Formats a number using the default (EISConstants.DEFAULT_NUMBER_FORMAT) format string.
	 * @param number the number to format
	 * @return A formatted number
	 */
    protected static final String formatNumber(double number) {
    	return formatNumber(number, EISConstants.DEFAULT_NUMBER_FORMAT);
    }

	/**
	 * Formats a number using the specified number of decimal digits.
	 * @param number the number to format
	 * @param numDecimalDigits the number of decimal digits to include
	 * @return A formatted number
	 * @see #formatNumber(double number, String format)
	 */
    protected final String formatNumber(double number, int numDecimalDigits) {
    	return formatNumber(number, "%,." + Integer.toString(numDecimalDigits) + "f");
    }

	/**
	 * Formats a currency value using the default (defaultCurrencyFormat) format string.
	 * @param number the number to format
	 * @return A formatted currency value
	 * @see #getDefaultCurrencyFormat()
	 * @see #formatNumber(double number, String format)
	 */
    protected final String formatCurrency(double number) {
    	return formatNumber(number, defaultCurrencyFormat);
    }

	/**
	 * Formats a currency value using the specified number of decimal digits.
	 * @param number the number to format
	 * @param numDecimalDigits the number of decimal digits to include
	 * @return A formatted currency value
	 * @see #formatNumber(double number, String format)
	 */
    protected final String formatCurrency(double number, int numDecimalDigits) {
    	return formatNumber(number, "%,." + Integer.toString(numDecimalDigits) + "f");
    }
    
	/**
	 * Asserts the equality of two strings (actual and expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual value, typically "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyCaseSensitive()
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
    protected static boolean assertEqualsWithFlags(String pageName, String fieldName, String actual, String expected) {
    	boolean result = true;
		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values of the Field '" + fieldName;
		if (!pageName.isEmpty()) {
			message += "' on the Page '" + pageName;
		}
		message += "' should ";
		message += verifyMatch ? "match " : "not match ";
		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but do not" : ", but do";
		
		try {
			if (verifyCaseSensitive) {
				org.junit.Assert.assertTrue(message, (actual.equals(expected) == verifyMatch));
			} else {
				org.junit.Assert.assertTrue(message, (actual.equalsIgnoreCase(expected) == verifyMatch));
			}
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
    }

	/**
	 * Asserts the equality of two strings (actual and expected), using assertion flags without pageName
	 * @param fieldName the name of the field on the page
	 * @param actual the actual value, typically "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(String pageName, String fieldName, String actual, String expected)
	 */
    protected static boolean assertEquals(String fieldName, String actual, String expected) {
		return assertEqualsWithFlags("", fieldName, actual, expected);
    }
    
	/**
	 * Asserts the equality of two strings (actual and expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual value, typically "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(String pageName, String fieldName, String actual, String expected)
	 */
    protected static boolean assertEquals(String pageName, String fieldName, String actual, String expected) {
		return assertEqualsWithFlags(pageName, fieldName, actual, expected);
    }

	/**
	 * Asserts the equality of two strings (actual and expected), using assertion flags.
	 * @param actual the actual value, typically "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyCaseSensitive()
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
    protected static boolean assertEqualsWithFlags(String actual, String expected) {
    	boolean result = true;
		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values should ";
		
		message += verifyMatch ? "match " : "not match ";
		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but do not" : ", but do";
		
		try {
			if (verifyCaseSensitive) {
				org.junit.Assert.assertTrue(message, (actual.equals(expected) == verifyMatch));
			} else {
				org.junit.Assert.assertTrue(message, (actual.equalsIgnoreCase(expected) == verifyMatch));
			}
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
    }

	/**
	 * Asserts the equality of two strings (actual and expected), using assertion flags.
	 * @param actual the actual value, typically "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(String actual, String expected)
	 */
    protected static boolean assertEquals(String actual, String expected) {
		return assertEqualsWithFlags(actual, expected);
    }
    
	/**
	 * Asserts the equality of a string (actual) with any of an array of strings (expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual value, typically "scraped" from the field on the page
	 * @param expecteds an array of expected values, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyCaseSensitive()
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
    protected static boolean assertEqualsWithFlags(String pageName, String fieldName, String actual, String... expecteds) {
    	boolean found = false;
    	boolean result = false;
    	boolean ignoreFailedAssertsSave;
    	boolean verifyMatchSave;
		String message = "The actual ('" + actual + "') value of the Field '" + fieldName + "' on the Page '" + pageName + "' should ";
		
		message += verifyMatch ? "be found" : "not be found";
		message += " in the list of expected values (" + Arrays.toString(expecteds) + ") ";
		message += (verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)");
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but is not" : ", but is";
		
		//Save the value of ignoreFailedAsserts.  We will set it to true temporarily, so that when we call assertEqualsWithFlags(pageName, fieldName, actual, expected)
		//  in the loop it won't fail the test
		ignoreFailedAssertsSave = ignoreFailedAsserts;
		ignoreFailedAsserts = true;
		
		//Save the value of verifyMatch.  We will set it to true temporarily, because it's simpler to have assertEqualsWithFlags(pageName, fieldName, actual, expected)'s
		//  return value specify whether the string was actually found, and then deal with the verifyMatch logic here
		verifyMatchSave = verifyMatch;
		verifyMatch = true;
		
		for (String expected : expecteds) {
			found = assertEqualsWithFlags(pageName, fieldName, actual, expected);

			result = result || found;
			
			//Break when the string is found
			if (found) {
				break;
			}
		}
		
		//Get the original settings back, then use them to assess success or failure and whether to fail the test
		ignoreFailedAsserts = ignoreFailedAssertsSave;
		verifyMatch = verifyMatchSave;
		
		result = (result == verifyMatch);
		if (result) {
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} else {
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);

			if (!ignoreFailedAsserts) {
				fail(message);
			}
			
			assertMessage = message;
		}

		return result;
    }

	/**
	 * Asserts the equality of a string (actual) with any of an array of strings (expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual value, typically "scraped" from the field on the page
	 * @param expecteds an array of expected values, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(String pageName, String fieldName, String actual, String... expecteds)
	 */
    protected static boolean assertEquals(String pageName, String fieldName, String actual, String... expecteds) {
		return assertEqualsWithFlags(pageName, fieldName, actual, expecteds);
    }
    
	/**
	 * Asserts the equality of a string (actual) with any of an array of strings (expected), using assertion flags.
	 * @param actual the actual value, typically "scraped" from the field on the page
	 * @param expecteds an array of expected values, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyCaseSensitive()
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
    protected static boolean assertEqualsWithFlags(String actual, String... expecteds) {
    	boolean found = false;
    	boolean result = false;
    	boolean ignoreFailedAssertsSave;
    	boolean verifyMatchSave;
		String message = "The actual ('" + actual + "') value should ";
		
		message += verifyMatch ? "be found" : "not be found";
		message += " in the list of expected values (" + Arrays.toString(expecteds) + ") ";
		message += (verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)");
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but is not" : ", but is";
		
		//Save the value of ignoreFailedAsserts.  We will set it to true temporarily, so that when we call assertEqualsWithFlags(pageName, fieldName, actual, expected)
		//  in the loop it won't fail the test
		ignoreFailedAssertsSave = ignoreFailedAsserts;
		ignoreFailedAsserts = true;
		
		//Save the value of verifyMatch.  We will set it to true temporarily, because it's simpler to have assertEqualsWithFlags(actual, expected)'s
		//  return value specify whether the string was actually found, and then deal with the verifyMatch logic here
		verifyMatchSave = verifyMatch;
		verifyMatch = true;
		
		for (String expected : expecteds) {
			found = assertEqualsWithFlags(actual, expected);

			result = result || found;
			
			//Break when the string is found
			if (found) {
				break;
			}
		}
		
		//Get the original settings back, then use them to assess success or failure and whether to fail the test
		ignoreFailedAsserts = ignoreFailedAssertsSave;
		verifyMatch = verifyMatchSave;
		
		result = (result == verifyMatch);
		if (result) {
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} else {
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);

			if (!ignoreFailedAsserts) {
				fail(message);
			}
			
			assertMessage = message;
		}

		return result;
    }
   
	/**
	 * Asserts the equality of a string (actual) with any of an array of strings (expected), using assertion flags.
	 * @param actual the actual value, typically "scraped" from the field on the page
	 * @param expecteds an array of expected values, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(String actual, String... expecteds)
	 */
    protected static boolean assertEquals(String actual, String... expecteds) {
		return assertEqualsWithFlags(actual, expecteds);
    }
    
	/**
	 * Asserts the equality of a list of strings (actual) with a list of strings (expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actuals a list of actual values, typically "scraped" from the field on the page
	 * @param expecteds a list of expected values, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyCaseSensitive()
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
    protected static boolean assertEqualsWithFlags(String pageName, String fieldName, List<String> actuals, List<String> expecteds) {
    	boolean result = true;
		String message = "The actual ('" + actuals.toString() + "') and expected ('" + expecteds.toString() + "') lists of values of the Field '" + fieldName + "' on the Page '" + pageName + "' should ";
		
		message += verifyMatch ? "match " : "not match ";
		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but do not" : ", but do";

		try {
			if (verifyCaseSensitive) {
				org.junit.Assert.assertTrue(message, (actuals.equals(expecteds) == verifyMatch));
			} else {
				org.junit.Assert.assertTrue(message, (Util.listOfStringToUpperCase(actuals).equals(Util.listOfStringToUpperCase(expecteds)) == verifyMatch));
			}
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
    }
    
	/**
	 * Asserts the equality of a list of strings (actual) with a list of strings (expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actuals a list of actual values, typically "scraped" from the field on the page
	 * @param expecteds a list of expected values, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(String pageName, String fieldName, List actuals, List expecteds)
	 */
    protected static boolean assertEquals(String pageName, String fieldName, List<String> actuals, List<String> expecteds) {
		return assertEqualsWithFlags(pageName, fieldName, actuals, expecteds);
    }
    
	/**
	 * Asserts the equality of a list of strings (actual) with a list of strings (expected), using assertion flags.
	 * @param actuals a list of actual values, typically "scraped" from the field on the page
	 * @param expecteds a list of expected values, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyCaseSensitive()
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
     protected static boolean assertEqualsWithFlags(List<String> actuals, List<String> expecteds) {
    	boolean result = true;
		String message = "The actual ('" + actuals.toString() + "') and expected ('" + expecteds.toString() + "') lists of values should ";
		
		message += verifyMatch ? "match " : "not match ";
		message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but do not" : ", but do";

		try {
			if (verifyCaseSensitive) {
				org.junit.Assert.assertTrue(message, (actuals.equals(expecteds) == verifyMatch));
			} else {
				org.junit.Assert.assertTrue(message, (Util.listOfStringToUpperCase(actuals).equals(Util.listOfStringToUpperCase(expecteds)) == verifyMatch));
			}
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
    }
    
	/**
	 * Asserts the equality of a list of strings (actual) with a list of strings (expected), using assertion flags.
	 * @param actuals a list of actual values, typically "scraped" from the field on the page
	 * @param expecteds a list of expected values, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(List actuals, List expecteds)
	 */
    protected static boolean assertEquals(List<String> actuals, List<String> expecteds) {
		return assertEqualsWithFlags(actuals, expecteds);
    }
       
	/**
	 * Asserts the equality of two booleans (actual and expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertEqualsWithFlags(String pageName, String fieldName, boolean actual, boolean expected) {
    	boolean result = true;
		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values of the Field '" + fieldName + "' on the Page '" + pageName + "' should ";
		
		message += verifyMatch ? "match" : "not match";
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but do not" : ", but do";
		
		try {
			org.junit.Assert.assertTrue(message, (actual == expected) == verifyMatch);
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
    }
	
	/**
	 * Asserts the equality of two booleans (actual and expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(String pageName, String fieldName, boolean actual, boolean expected)
	 */
    protected static boolean assertEquals(String pageName, String fieldName, boolean actual, boolean expected) {
		return assertEqualsWithFlags(pageName, fieldName, actual, expected);
    }
    
	/**
	 * Asserts the equality of two ints (actual and expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertEqualsWithFlags(String pageName, String fieldName, int actual, int expected) {
    	boolean result = true;
		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values of the Field '" + fieldName + "' on the Page '" + pageName + "' should ";
		
		message += verifyMatch ? "match" : "not match";
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but do not" : ", but do";
		
		try {
			org.junit.Assert.assertTrue(message, (actual == expected) == verifyMatch);
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
    }
	
	/**
	 * Asserts the equality of two ints (actual and expected), using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(String pageName, String fieldName, int actual, int expected)
	 */
    protected static boolean assertEquals(String pageName, String fieldName, int actual, int expected) {
		return assertEqualsWithFlags(pageName, fieldName, actual, expected);
    }
    
	/**
	 * Asserts the equality of two booleans (actual and expected), using assertion flags.
	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertEqualsWithFlags(boolean actual, boolean expected) {
    	boolean result = true;
		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values should ";
		
		message += verifyMatch ? "match" : "not match";
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but do not" : ", but do";
		
		try {
			org.junit.Assert.assertTrue(message, (actual == expected) == verifyMatch);
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
    }
	
	/**
	 * Asserts the equality of two booleans (actual and expected), using assertion flags.
	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(boolean actual, boolean expected)
	 */
    protected static boolean assertEquals(boolean actual, boolean expected) {
		return assertEqualsWithFlags(actual, expected);
    }

	/**
	 * Asserts the equality of two ints (actual and expected), using assertion flags.
	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertEqualsWithFlags(int actual, int expected) {
    	boolean result = true;
		String message = "The actual ('" + actual + "') and expected ('" + expected + "') values should ";
		
		message += verifyMatch ? "match" : "not match";
		
		Util.printAssertingMessage(message);
		
		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += verifyMatch ? ", but do not" : ", but do";
		
		try {
			org.junit.Assert.assertTrue(message, (actual == expected) == verifyMatch);
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
    }
	
	/**
	 * Asserts the equality of two ints (actual and expected), using assertion flags.
	 * @param actual the actual value, typically derived from a value "scraped" from the field on the page
	 * @param expected the expected value, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertEqualsWithFlags(int actual, int expected)
	 */
    protected static boolean assertEquals(int actual, int expected) {
		return assertEqualsWithFlags(actual, expected);
    }
	    
	/**
	 * Asserts the existence of a GUI element, using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual existence of the field
	 * @param expected the expected existence of the field, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertFieldExistenceWithFlags(String pageName, String fieldName, boolean actual, boolean expected) {
    	boolean result = true;
		boolean contextExpected = (expected == verifyMatch);
		String message = "The Field '" + fieldName + "' on the Page '" + pageName + "' should ";

		//We are applying the same logic (in a slightly different manner) that we use in assertEqualsWithFlags,
		//  but we are doing it here instead of calling assertEqualsWithFlags(boolean actual, boolean expected)
		//  so that we can tailor the error message
		
		message += contextExpected ? "exist" : "not exist";

		Util.printAssertingMessage(message);

		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += contextExpected ? ", but does not" : ", but does";
		
		try {
			org.junit.Assert.assertTrue(message, (actual == contextExpected));
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
	}
	
	/**
	 * Asserts the existence of a GUI element, using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual existence of the field
	 * @param expected the expected existence of the field, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertFieldExistenceWithFlags(String pageName, String fieldName, boolean actual, boolean expected)
	 */
    protected static boolean assertFieldExistence(String pageName, String fieldName, boolean actual, boolean expected) {
		return assertFieldExistenceWithFlags(pageName, fieldName, actual, expected);
    }
	
	/**
	 * Asserts the visibility of a GUI element, using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual visibility of the field
	 * @param expected the expected visibility of the field, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertFieldVisibilityWithFlags(String pageName, String fieldName, boolean actual, boolean expected) {
    	boolean result = true;
		boolean contextExpected = (expected == verifyMatch);
		String message = "The Field '" + fieldName + "' on the Page '" + pageName + "' should ";

		//We are applying the same logic (in a slightly different manner) that we use in assertEqualsWithFlags,
		//  but we are doing it here instead of calling assertEqualsWithFlags(boolean actual, boolean expected)
		//  so that we can tailor the error message
		
		message += contextExpected ? "be visible" : "not be visible";

		Util.printAssertingMessage(message);

		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += contextExpected ? ", but is not" : ", but is";
		
		try {
			org.junit.Assert.assertTrue(message, (actual == contextExpected));
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
	}
	
	/**
	 * Asserts the visibility of a GUI element, using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual visibility of the field
	 * @param expected the expected visibility of the field, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertFieldVisibilityWithFlags(String pageName, String fieldName, boolean actual, boolean expected)
	 */
    public static boolean assertFieldVisibility(String pageName, String fieldName, boolean actual, boolean expected) {
		return assertFieldVisibilityWithFlags(pageName, fieldName, actual, expected);
    }

	/**
	 * Asserts the 'nullness' of a GUI element, using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual 'nullness' of the field
	 * @param expected the expected 'nullness' of the field, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertFieldNullnessWithFlags(String pageName, String fieldName, boolean actual, boolean expected) {
    	boolean result = true;
		boolean contextExpected = (expected == verifyMatch);
		String message = "The Field '" + fieldName + "' on the Page '" + pageName + "' should ";

		//We are applying the same logic (in a slightly different manner) that we use in assertEqualsWithFlags,
		//  but we are doing it here instead of calling assertEqualsWithFlags(boolean actual, boolean expected)
		//  so that we can tailor the error message
		
		message += contextExpected ? "be null" : "not be null";

		Util.printAssertingMessage(message);

		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += contextExpected ? ", but is not" : ", but is";
		
		try {
			org.junit.Assert.assertTrue(message, (actual == contextExpected));
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
	}
	
	/**
	 * Asserts the 'nullness' of a GUI element, using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param actual the actual 'nullness' of the field
	 * @param expected the expected 'nullness' of the field, as specified in the test properties file or set programmatically
	 * @return The assertion result
	 * @see #assertFieldNullnessWithFlags(String pageName, String fieldName, boolean actual, boolean expected)
	 */
    protected static boolean assertFieldNullness(String pageName, String fieldName, boolean actual, boolean expected) {
		return assertFieldNullnessWithFlags(pageName, fieldName, actual, expected);
    }
       
	/**
	 * Asserts the presence of errors at the field and/or page level on a page, using assertion flags.
	 * @param checkedElements a string containing the name(s) of the field(s) and/or the name of the page
	 * that were checked for errors 
	 * @param actual the actual absence of errors
	 * @param expected the expected absence of errors
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertErrorCheckWithFlags(String checkedElements, boolean actual, boolean expected) {
    	boolean result = true;
		boolean contextExpected = (expected == verifyMatch);
		String message = "The " + checkedElements + " should ";

		//We are applying the same logic (in a slightly different manner) that we use in assertEqualsWithFlags,
		//  but we are doing it here instead of calling assertEqualsWithFlags(boolean actual, boolean expected)
		//  so that we can tailor the error message

		message += contextExpected ? "not contain errors" : "contain one or more errors";

		Util.printAssertingMessage(message);

		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += contextExpected ? ", but does" : ", but does not";
		
		try {
			org.junit.Assert.assertTrue(message, (actual == contextExpected));
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
	}
	
	/**
	 * Asserts the presence of errors at the field and/or page level on a page, using assertion flags.
	 * @param checkedElements a string containing the name(s) of the field(s) and/or the name of the page
	 * that were checked for errors 
	 * @param actual the actual absence of errors
	 * @param expected the expected absence of errors
	 * @return The assertion result
	 * @see #assertErrorCheckWithFlags(String checkedElements, boolean actual, boolean expected)
	 */
    protected static boolean assertErrorCheck(String checkedElements, boolean actual, boolean expected) {
		return assertErrorCheckWithFlags(checkedElements, actual, expected);
    }
		
	/**
	 * Asserts the equality of a boolean with the verifyMatch flag, using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param assertingMessage a description of how the actual value was arrived at
	 * @param actual the actual value, typically set programmatically
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertTrueWithFlags(String pageName, String fieldName, String assertingMessage, boolean actual) {
    	boolean result = true;
		boolean contextExpected = (true == verifyMatch);
		String message = "The test '" + assertingMessage + "' on the Field '" + fieldName + "' on the Page '" + pageName + "' should be ";

		message += contextExpected ? "true" : "false";

		Util.printAssertingMessage(message);

		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += contextExpected ? ", but is false" : ", but is true";
		
		try {
			org.junit.Assert.assertTrue(message, actual == verifyMatch);
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
	}
	
	/**
	 * Asserts the equality of a boolean with the verifyMatch flag, using assertion flags.
	 * @param pageName the name of the relevant page
	 * @param fieldName the name of the field on the page
	 * @param assertingMessage a description of how the actual value was arrived at
	 * @param actual the actual value, typically set programmatically
	 * @return The assertion result
	 * @see #assertTrueWithFlags(String pageName, String fieldName, String assertingMessage, boolean actual)
	 */
    protected static boolean assertTrue(String pageName, String fieldName, String assertingMessage, boolean actual) {
		return assertTrueWithFlags(pageName, fieldName, assertingMessage, actual);
    }

    /**
	 * Asserts the equality of a boolean with the verifyMatch flag, using assertion flags.
	 * @param assertingMessage a description of how the actual value was arrived at
	 * @param actual the actual value, typically set programmatically
	 * @return The assertion result
	 * @see #isVerifyMatch()
	 * @see #isIgnoreFailedAsserts()
	 */
	protected static boolean assertTrueWithFlags(String assertingMessage, boolean actual) {
    	boolean result = true;
		boolean contextExpected = (true == verifyMatch);
		String message = "The test '" + assertingMessage + "' should be ";

		message += contextExpected ? "true" : "false";

		Util.printAssertingMessage(message);

		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += contextExpected ? ", but is false" : ", but is true";
		
		try {
			org.junit.Assert.assertTrue(message, actual == verifyMatch);
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
	}
	
	
	protected static boolean assertFalseWithFlags(String assertingMessage, boolean actual) {
    	boolean result = true;
		boolean contextExpected = (true == verifyUnMatch);
		String message = "The test '" + assertingMessage + "' should be ";

		message += contextExpected ? "true" : "false";
		

		Util.printAssertingMessage(message);

		message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
		message += contextExpected ? ", but is false" : ", but is true";
		
		try {
			org.junit.Assert.assertTrue(message, actual == verifyUnMatch);
			
			reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
		} catch (AssertionError e) {
			result = false;
			
			//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
			//  indeed fail!
			reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
			
			if (!ignoreFailedAsserts) {
				fail(e.getMessage());
			}
			
			assertMessage = e.getMessage();
		}
		
		return result;
	}
	
	/**
	 * Asserts the equality of a boolean with the verifyMatch flag, using assertion flags.
	 * @param assertingMessage a description of how the actual value was arrived at
	 * @param actual the actual value, typically set programmatically
	 * @return The assertion result
	 * @see #assertTrueWithFlags(String assertingMessage, boolean actual)
	 */
    protected static boolean assertTrue(String assertingMessage, boolean actual) {
		return assertTrueWithFlags(assertingMessage, actual);
    }
    
    protected static boolean assertFalse(String assertingMessage, boolean actual ){
    return assertFalseWithFlags(assertingMessage, actual);
}
    
    private static void reportAssertResult(String message) {
    	Util.printAssertResultMessage(message);
    	
    	numAsserts++;
    }
    
	//We provide this for the cases where the user wants to open a URL that is not necessarily associated
	//  with an instance of SFDCObject
	/**
	 * Opens a URL.
	 * @param url the URL to open
	 */
	public static void open(String url) {
    	//I have found that if the page is not "settled" before making the super.open()
    	//  call, the url never gets loaded.  Until a better solution is found (other
    	//  than calling Selenium.waitForPageToLoad()!), we'll just sleep
		//Util.sleep(2000);
		//Changed from 2000 to 500 after port to WebDriver, but may not be necessary at all
		Util.sleep(500);
		
		//Changed from driver.get(url) due to inconsistent behaviour
		driver.navigate().to(url);
		
		//Handling IE security certificate Issue
		if(appBrowser.equalsIgnoreCase("ie") && (!loginPage.isFieldPresent("loginButton"))){
//			clickOnContinueLinkInIE();
			driver.navigate().to("javascript:var overRideLink=document.getElementById('overridelink'); if (overRideLink){overRideLink.click();}");
//			driver.navigate().to("javascript:document.getElementById('overridelink').click()");

		}
//		driver.navigate().to("javascript:document.getElementById('overridelink').click()");
		//End - Handling IE security certificate Issue
//		Util.sleep(5000);
		driver.navigate().refresh();
		Util.sleep(5000);
		Util.printInfo("Opened '" + url + "'");
		
		currentURL = url;
		reportStep("Navigating to URL: ", url);
	}

	/**
	 * Launches specified website/url, sets the locator of the main window, and maximizes the browser window.
	 * @param url the URL to open
	 * @see #open(String url)
	 */
	protected final void launchPortal(String url) {
	   	//Assumes a browser window is already open
	   	open(url);
	   	
	   	//Deal with possible certificate error by clicking (occurs often after we open a partner portal)
	   	//  We can't do it using normal WebDriver calls, because WebDriver just cannot see the link!
	   	//NOTE that the AutoIT process we use to do this will sometimes open a new browser.  I don't
	   	//  know why, but I have observed that it occurs only in the cases where we don't encounter
	   	//  the certificate error.  So we sill attempt to determine whether the certificate error is
	   	//  present by looking for the login button.
	   	
	   	//Handling IE security certificate Issue
	   	Util.sleep(10000);
//	   	loginPage.waitForFieldVisible("loginButton",5000);
	   	if (!loginPage.isFieldPresent("loginButton")) {
		    	Util.printDebug("Handling possible web certificate error...");
		    	
				int exitCode = commonPage.clickLinkInIE(EISConstants.TEST_BASE_PATH + EISConstants.CLICK_LINK_IN_IE_PROCESS, url, EISConstants.CONTINUE_TO_WEBSITE_LINK_TEXT);
				if (exitCode != EISConstants.PROCESS_EXIT_CODE_SUCCESS) {
					Util.printWarning("Error when clicking link on web certificate error page after launching Salesforce: " + exitCode);
				}
				
				Util.printDebug("Handled possible web certificate error");
	   	}
	   	
		//Will have to do this every time we open a new window or pop-up,
		//  because the locators are generated on the fly in WebDriver
	   	mainWindow.setLocator(driver.getWindowHandle());
	   	windowMaximize();
	
		    Util.printInfo("Launched " + appName + " at '" + currentURL + "'");
   }
	
	/**
	 * Launches Salesforce, sets the locator of the main window, and maximizes the browser window.
	 * @param url the URL to open
	 * @see #open(String url)
	 */
	protected final void launchSalesforce(String url) {
		launchPortal(url);
	   	//Save the URL used to launch Salesforce, as sometimes the user wants to use it as a base for
	   	//  navigating to another URL, perhaps one that refers to an object ID
	   	baseURL = url;
    }

	/**
	 * Launches Salesforce, using the value of baseURL.
	 * @see #getBaseURL()
	 */
    protected final void launchSalesforce() {
    	launchSalesforce(baseURL);
    }

	/**
	 * Maximizes the browser window (note that the WebDriverBackedSelenium class is required,
	 * and that the windowMaximize() call does not work in the Firefox driver).
	 */
    public final void windowMaximize() {
    	//It appears that WebDriver does not support maximizing windows.  See:
    	//  http://stackoverflow.com/questions/3189430/how-do-i-maximize-the-browser-window-using-webdriver-selenium-2
    	//  http://www.readmespot.com/question/o/3189430/how-do-i-maximize-the-browser-window-using-webdriver--selenium-2--
    	//So, we'll use a WebDriverBackedSelenium object
    	//super.windowMaximize();
    	//NOTE that this does not work when using WebDriver's FirefoxDriver
//    	Vithyusha---Commented on 1/5/2014 as this is failing the tests showing Java Script error.
//    	Selenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
//    	selenium.windowMaximize();
//    	driver = ((WebDriverBackedSelenium) selenium).getWrappedDriver();
    	
    	//Vithyusha--Changed the code on 1/5/2014 as this is Working fine for IE,Firefox and Chrome 
    	driver.manage().window().maximize();
    }

	/**
	 * Executes an external process and returns the exit code.
	 * @param command the name of the external process
	 * @param timeoutSecondsProcessArg a timeout value (in seconds), which will be passed as an argument to the external process.&nbsp;
	 * This value is for possible use by the process itself, for timing its own actions.
	 * @param timeoutMultiplierProcessWait a multiplier used in conjunction with timeoutSecondsProcessArg to determine the maximum
	 * time to wait (in seconds) for the external process to return
	 * @param args additional args to pass to the external process
	 * @return The process exit code (or EISConstants.PROCESS_ERROR_EXIT_CODE if the process times out)
	 */
    protected static int execProcess(String command, int timeoutSecondsProcessArg, int timeoutMultiplierProcessWait, String... args) {
		//We'll apply a fudge factor to the product of the timeout values that were passed in, to account for possible waits/sleeps in the process
    	//final double PROCESS_WAIT_FUDGE_FACTOR = 1.5;
    	
    	Process process = null;
    	String[] cmdArray;
    	List<String> cmdList = new ArrayList<String>(Arrays.asList(args));
    	int exitCode;

    	cmdList.add(0, command);
    	cmdList.add(Integer.toString(timeoutSecondsProcessArg));
    	cmdArray = cmdList.toArray(new String[cmdList.size()]);
    	
    	//Using ProcessBuilder instead of Runtime might be a good idea (I have seen comments about Runtime being
    	//  unreliable in some contexts)
    	ProcessBuilder pb = new ProcessBuilder(cmdArray);
    	
    	try {
      		Util.printDebug("Starting execution of the " + command + " process with args: " + Arrays.toString(cmdArray));
      		
      		//It appears that the process is launched on a different thread
        	process = pb.start();
        	
        	//Here is a try using Runtime instead.  It does not fix the problem (not being able to launch an external
        	//  process from the Jenkins slave service).
			//process = Runtime.getRuntime().exec(cmdArray);
		} catch (IOException ioe) {
			fail("IOException thrown while executing the " + command + " process: " + ioe.getMessage());
		}

		//Wait for the process to finish, by attempting to get its exit value.  (We can't wait on the thread, because
		//  the process may be running an executable that is not thread aware.)
		exitCode = EISConstants.PROCESS_ERROR_EXIT_CODE;
	 	boolean ended = false;

		//int timeoutSecondsProcessWait = (int)(timeoutSecondsProcessArg * timeoutMultiplierProcessWait * PROCESS_WAIT_FUDGE_FACTOR);
		int timeoutSecondsProcessWait = (int)(timeoutSecondsProcessArg * timeoutMultiplierProcessWait);
	 	for (int i = 1; i <= timeoutSecondsProcessWait; i += 1) {
			Util.sleep(1000);
			
			try {
				exitCode = process.exitValue();

				Util.printDebug("Finished executing the " + command + " process: elapsed time was " + i + " seconds; exit code = " + exitCode);
				
				ended = true;

				break;
			//This exception is expected when the exitValue call is made before the process has ended
			} catch (IllegalThreadStateException e) {}
		}
		
		if (!ended) {
			Util.printDebug("The process did not exit within " + timeoutSecondsProcessWait + " seconds; killing it...");

			process.destroy();
		}

		//Sleep just a wee bit longer, just to ensure that we're good
		Util.sleep(1000);
		
    	return exitCode;
    }
    
	/**
	 * Selects an app from the SFDC app chooser.
	 * @param targetApp a token that maps to an app in the SFDC app chooser
	 */
/*    protected void chooseApp(EISConstants.App targetApp) {
    	String fieldName;
    	boolean changedApp = false;
    	
        if (!targetApp.equals(app)) {
	    	mainWindow.select();
	    	
	    	commonPage.click("appChooserButton");
			
			switch (targetApp) {
				case ADSK_SALES:			fieldName = "adskSalesAppLink"; break;
				case LEAD_MANAGEMENT:		fieldName = "leadManagementAppLink"; break;
				case ADSK_CALL_CENTER:		fieldName = "adskCallCenterAppLink"; break;
				default:					fieldName = "leadManagementAppLink"; break;
			}
			
			//If the link is not present, it means that targetApp is already selected.  This would
			//  be the case when targetApp was the active app the last time the application ran.
			//  We would still execute this code (inside the 'if (!targetApp.equals(app))' block)
			//  however, because app will be null the first time this method is called.)
			if (commonPage.isFieldPresent(fieldName)) {
				//We have gotten rid of clickAndWait(String fieldName) calls, so we have to specify
				//  something for which to wait - but what?
				//commonPage.clickAndWait(fieldName);
				commonPage.click(fieldName);

				changedApp = true;
			} else {
				fail("Error while choosing the " + getAppName() + " app '" + targetApp + "' from the app chooser: invalid app name");
			}
	
			if (changedApp || app == null) {
				//Util.printInfo("Selected the " + getAppName() + " app '" + targetApp + "'");
				Util.printInfo("Selected the '" + commonPage.getValueFromGUI("appChooserButtonLabel") + "' app");
				
				app = targetApp;
			}
    	}
    }
*/
    protected void chooseApp(EISConstants.App targetApp) {
    	String linkFieldName = "";
    	String labelFieldName = "";
    	boolean changedApp = false;
    	
        if (!targetApp.equals(app)) {
	    	mainWindow.select();
	    	
			switch (targetApp) {
				case ADSK_SALES:	{
					labelFieldName	= "adskSalesAppLabel";
					linkFieldName	= "adskSalesAppLink";
					break;
				}
				case LEAD_MANAGEMENT:	{
					labelFieldName	= "leadManagementAppLabel";
					linkFieldName	= "leadManagementAppLink";
					break;
				}
				case ADSK_CALL_CENTER:	{
					labelFieldName	= "adskCallCenterAppLabel";
					linkFieldName	= "adskCallCenterAppLink";
					break;
				}
				case UCM_CONSOLE:	{
					labelFieldName	= "ucmConsoleAppLabel";
					linkFieldName	= "ucmAppLink";
					break;
				}
				default:	{
					fail("Unhandled member of common.EISConstants.App enumerated type: " + targetApp);
				}
			}
			
			//If the ...Label field is present, it means that targetApp is already selected.  That would
			//  be the case when targetApp was the active app the last time the application was exited.
			//  We would still execute this code (inside the 'if (!targetApp.equals(app))' block) because
			//  app will be null the first time this method is called.
			if (!commonPage.isFieldPresent(labelFieldName)) {
		    	commonPage.click("appChooserButton");
				
				//commonPage.clickAndWait(fieldName);
				commonPage.clickAndWait(linkFieldName, labelFieldName);

				changedApp = true;
			}
	
			if (changedApp || app == null) {
				Util.printInfo("Selected '" + commonPage.getValueFromGUI("appChooserButton") + "' from the app chooser");
				
				app = targetApp;
			}
    	}
    }
        
	/**
	 * Selects a view from the SFDC view chooser.
	 * @param viewName the view to select
	 */
    protected void chooseView(String viewName) {
	    commonPage.populateField("viewChooser", viewName);
    }

	/**
	 * Clicks a tab in the browser.
	 * @param tabName the name of a Field object that references the tab to click
	 */
    protected void clickTab(String tabName) {
		//We have gotten rid of clickAndWait(String fieldName) calls, so we have to specify
		//  something for which to wait - but what?
    	//commonPage.clickAndWait(tabName);
    	commonPage.click(tabName);
    }
    
	/**
	 * Logs in to the application.
	 * @param userName the user name to use when logging in
	 * @param password the password to use when logging in
	 * @param launch whether to launch Salesforce first
	 * @return The current logged-in user
	 * @see #launchSalesforce()
	 */
    protected String login(String userName, String password, boolean launch) {
    	if (launch) {
    		launchSalesforce();
    	}
    	//launchSalesforce(baseURL);
    	mainWindow.select();
    	
    	loginPage.populateField("username", userName);
    	loginPage.populateField("password", password);

     	loginPage.clickToSubmit("loginButton");
     	
     	handleServerMaintenanceNotification();
    	
     	//Deal with possible pop-ups, such as Reminders
     	Util.sleep(2000);
     	if (loginPage.closeAllPopUps(mainWindow.getLocator())) {
        	disableReminders();
     	}

    	mainWindow.select();
    	
		currentUser = userName;
		
		//Be sure to call the setter instead of assigning the value directly, because
		//  we truncate the value in the setter
		setCurrentUserName(commonPage.getValueFromGUI("loggedInUserText"));

    	Util.printInfo("Logged in as the user '" + currentUser + "'");

    	//Don't do this!  Some tests expect to remain on the landing page!
    	//commonPage.click("tabHome");

    	return currentUser;
    }
   
    protected String loginForSSP(String userName, String password, boolean launch) {
    	if (launch) {
    		launchSalesforce();
    	}
    	
    	mainWindow.select();
    	
    	loginPage.populateField("username", userName);
    	loginPage.populateField("password", password);

     	loginPage.clickToSubmit("loginButton");
     	
     	handleServerMaintenanceNotification();
    	
     	//Deal with possible pop-ups, such as Reminders
     	Util.sleep(2000);
     	if (loginPage.closeAllPopUps(mainWindow.getLocator())) {
        	disableReminders();
     	}

    	mainWindow.select();
    	
		currentUser = userName;
	
    	Util.printInfo("Logged in as the user '" + currentUser + "'");

    	//Don't do this!  Some tests expect to remain on the landing page!
    	//commonPage.click("tabHome");

    	return currentUser;
    }
   
	/**
	 * Logs in to the application, after launching Salesforce.
	 * @param userName the user name to use when logging in
	 * @param password the password to use when logging in
	 * @return The current logged-in user
	 * @see #launchSalesforce()
	 */
    protected String login(String userName, String password) {
    	return login(userName, password, false);
    }

	/**
	 * Logs in to the email client (by opening EISConstants.EMAIL_CLIENT_URL and using EISConstants.EMAIL_CLIENT_USERNAME
	 * and EISConstants.EMAIL_CLIENT_PASSWORD as credentials) and opens the inbox (by opening EISConstants.EMAIL_CLIENT_INBOX_URL).
	 * @see #open(String url)
	 */
    protected void loginToEmailClient() {
		open(EISConstants.EMAIL_CLIENT_URL);
		emailClientPage.populateField("username", EISConstants.EMAIL_CLIENT_USERNAME);
		emailClientPage.populateField("password", EISConstants.EMAIL_CLIENT_PASSWORD);

		//emailClientPage.clickToSubmit("signInButton");
		emailClientPage.clickToSubmit("signInButton", "inboxLink");
		
		//Instead of clicking these objects, we'll go directly to the inbox using a link
		//emailClientPage.clickAndWait("hotmailLink");
		//emailClientPage.clickAndWait("inboxLink");
		open(EISConstants.EMAIL_CLIENT_INBOX_URL);
    }

	/**
	 * Detects and handles the standard SFDC server maintenance notification that may appear after logging in.
	 * @return Whether the notification was detected
	 */
    protected boolean handleServerMaintenanceNotification() {
    	boolean found = false;
    	
    	found = loginPage.isFieldPresent("serverMaintenanceNotification");
    	if (found) {
			//We have gotten rid of clickAndWait(String fieldName) calls, so we have to specify
			//  something for which to wait - but what?
    		//loginPage.clickAndWait("serverMaintenanceContinueLink");
    		loginPage.click("serverMaintenanceContinueLink");
      		
      		Util.printInfo("The server notification notification was detected, and has been dismissed");
    	}
    	
    	return found;
    }

	/**
	 * Disables user reminder pop-ups.
	 * @return Whether reminders had been enabled
	 */
    protected boolean disableReminders() {
    	boolean wereEnabled = false;    	
    	
    	mainWindow.select();
    	
       	//commonPage.clickAndWait("setupLink");
       	commonPage.click("setupLink");
      	setupPage.waitForFieldPresent("myPersonalInfoLink");
    	
      	//setupPage.clickAndWait("myPersonalInfoLink");
      	//setupPage.clickAndWait("remindersLink");
      	setupPage.clickAndWait("myPersonalInfoLink", "remindersLink");
      	setupPage.clickAndWait("remindersLink", "triggerAlertCheckBox");
      	
      	wereEnabled = setupPage.isChecked("triggerAlertCheckBox");
      	if (wereEnabled) {
      		setupPage.populateField("triggerAlertCheckBox", "off");
			//We have gotten rid of clickAndWait(String fieldName) calls, so we have to specify
			//  something for which to wait - but what?
           	//commonPage.clickAndWait("saveButton");
           	commonPage.click("saveButton");

           	commonPage.click("homeTab");
      		
      		Util.printInfo("User reminder alerts were enabled and have been disabled");
      	}
    	
    	return wereEnabled;
    }
        
	/**
	 * Logs out of the application.
	 */
    protected void logout() {
    	mainWindow.select();
    	
    	//See if we are "logged in as"
		if (commonPage.isFieldPresent("loggedInAsText")) {
			//We have gotten rid of clickAndWait(String fieldName) calls, so we have to specify
			//  something for which to wait - but what?
			//commonPage.clickAndWait("logoutLink");
			commonPage.click("logoutLink");
			
			Util.printDebug("While logging out of the application, found that the " + app + " user '" + getCurrentUser() + "' was logged in; logged out the user");

			//Wait for the user-related logout link to go away, so that we don't click it again
			//TODO  ***** JAB  We will do this more robustly after writing Page.waitForElementAbsent and Page.waitForElementPresent
	     	//Util.sleep(2000);
		}

		//See if we are logged in
		if (commonPage.isFieldPresent("logoutLink")) {
			//We have gotten rid of clickAndWait(String fieldName) calls, so we have to specify
			//  something for which to wait - but what?
			//commonPage.clickAndWait("logoutLink");
			commonPage.click("logoutLink");
		}
		
		//It may be that the test was automatically logged in by the framework, in which case currentUser would
		//  not be set; check for that
		if (!currentUser.isEmpty()) {
			Util.printInfo("Logged out the user '" + currentUser + "'");
		} else {
			Util.printInfo("Logged out the test user");
		}
		
		currentUser = "";
		
		app = null;
    }
    
	/**
	 * Decrypts a string using the provided key.
	 * @param cipherText the encrypted string
	 * @param key the the decryption key
	 * @return The decrypted (plaintext) string
	 */
    protected String decrypt(String cipherText, String key) {
        PasswordSecurityUtil enUtil = new PasswordSecurityUtil();
        
    	return enUtil.decryptString(cipherText, key);
    }

	/**
	 * Decrypts a string using the default key, which is stored in a constant in the test properties file&#59;
	 * EISConstants.DECRYPTION_KEY_CONSTANT_NAME contains the name of that constant.
	 * @param cipherText the encrypted string
	 * @return The decrypted (plaintext) string
	 */
    protected String decrypt(String cipherText) {
    	return decrypt(cipherText, testProperties.getConstant(EISConstants.DECRYPTION_KEY_CONSTANT_NAME));
    }

    //Subclasses will implement this method to choose an app-specific default app from the app chooser
	/**
	 * Selects the application-specific default app from the SFDC app chooser.
	 */
    protected abstract void chooseApp();

    protected abstract void setEnvironmentVariables();
    
	/**
	 * Fail the test by setting the test result to EISConstants.TEST_FAILED_MESSAGE_TEXT, printing
	 * a message to the console with the default prefix (EISConstants.TEST_FAILED_MESSAGE_PREFIX),
	 * and throwing a RuntimeException 
	 * @param message the message to print
	 * @see #setTestResult(String testResult)
	 * @see Util#printTestFailedMessage(String message)
	 */
   public static void fail(String message) {
		setTestResult(EISConstants.TEST_FAILED_MESSAGE_TEXT);
    	Util.printTestFailedMessage(message);
    	errorReason = message;
		throw new RuntimeException(message);
    }
    
	/**
	 * Fail the test by setting the test result to EISConstants.TEST_FAILED_MESSAGE_TEXT, printing
	 * a message to the console with the default prefix (EISConstants.TEST_FAILED_MESSAGE_PREFIX),
	 * and throwing a RuntimeException 
	 * @param message the message to print
	 * @see #fail(String message)
	 */
    public static void failTest(String message) {
    	fail(message);
    }

    /**
	 * Creates an SFDC Contact object by executing a workflow, using data in the specified TestProperties object.
	 * @param utilProperties a TestProperties object containing all data necessary to create an SFDC Contact object
	 * @return A Contact object representing a created SFDC Contact
	 * @see common.Contact#create()
	 */
    protected Contact utilCreateContact(TestProperties utilProperties) {
    	Contact contact = utilCreateContactObject(utilProperties);
    	contact.create();
       	
       	return contact;
    }
	
	/**
	 * Creates an SFDC Contact object by executing a workflow, using data in the specified TestProperties object.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return A Contact object representing a created SFDC Contact
	 * @see common.Contact#create()
	 */
    protected Contact utilCreateContact(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

       	return utilCreateContact(utilProperties);
    }
	
	/**
	 * Creates an SFDC Contact object by executing a workflow, using data in the default TestProperties object.
	 * @return A Contact object representing a created SFDC Contact
	 * @see common.Contact#create()
	 */
    protected Contact utilCreateContact() {
       	return utilCreateContact(testProperties);
    }
	
	/**
	 * Instantiates a Contact object, using data in the specified TestProperties object.  Does not execute the workflow
	 * to create an SFDC Contact.
	 * @param utilProperties a TestProperties object containing data necessary to create an Oppty object
	 * @return A Contact object
	 */
	protected Contact utilCreateContactObject(TestProperties utilProperties) {
		Page_ createContactRTPage	= createCommonPageInstance(utilProperties, "PAGE_COMMON_RECORD_TYPE_PROPERTIES_FILE");
		Page_ createContactPage		= createCommonPageInstance(utilProperties, "PAGE_CREATE_CONTACT_PROPERTIES_FILE");
      	Page_ viewContactPage		= createCommonPageInstance(utilProperties, "PAGE_VIEW_CONTACT_PROPERTIES_FILE");

      	Contact contact = new Contact(createContactRTPage, createContactPage, viewContactPage);

     	return contact;
    }

	/**
	 * Instantiates a Contact object, using data in the specified TestProperties object.  Does not execute the workflow
	 * to create an SFDC Contact.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return A Contact object
	 */
    protected Contact utilCreateContactObject(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

		return utilCreateContactObject(utilProperties);
    }
    
	/**
	 * Instantiates a Contact object, using data in the default TestProperties object.  Does not execute the workflow
	 * to create an SFDC Contact.
	 * @return A Contact object
	 */
    protected Contact utilCreateContactObject() {
		return utilCreateContactObject(testProperties);
    }
    
    /**
	 * Creates an SFDC Opportunity object by executing a workflow, using data in the specified TestProperties object.
	 * @param utilProperties a TestProperties object containing all data necessary to create an SFDC Opportunity object
	 * @return An Oppty object representing a created SFDC Opportunity
	 * @see common.Oppty#create()
	 */
    protected Oppty utilCreateOppty(TestProperties utilProperties) {
    	Oppty oppty = utilCreateOpptyObject(utilProperties);
       	oppty.create();
       	
       	return oppty;
    }
    protected Oppty utilCreatePartnerOppty(TestProperties utilProperties) {
    	Oppty oppty = utilCreateOpptyObject(utilProperties);
              	
       	return oppty;
    }

    protected Oppty utilCreateQuoteFromOppty(TestProperties utilProperties) {
    	Oppty oppty = utilCreateQuoteFromOpptyObject(utilProperties);
       	oppty.createQuoteFromOppty();
       	
       	return oppty;
    }

	/**
	 * Creates an SFDC Opportunity object by executing a workflow, using data in the specified TestProperties object.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return An Oppty object representing a created SFDC Opportunity
	 * @see common.Oppty#create()
	 */
    protected Oppty utilCreateOppty(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

       	return utilCreateOppty(utilProperties);
    }
	
	/**
	 * Creates an SFDC Opportunity object by executing a workflow, using data in the default TestProperties object.
	 * @return An Oppty object representing a created SFDC Opportunity
	 * @see common.Oppty#create()
	 */
    protected Oppty utilCreateOppty() {
       	return utilCreateOppty(testProperties);
    }
    protected Oppty utilCreatePartnerOppty() {
       	return utilCreatePartnerOppty(testProperties);
    }
    
    protected Oppty utilCreateQuoteFromOppty() {
       	return utilCreateQuoteFromOppty(testProperties);
    }
	
	
	/**
	 * Instantiates an Oppty object, using data in the specified TestProperties object.  Does not execute the workflow
	 * to create an SFDC Opportunity.
	 * @param utilProperties a TestProperties object containing data necessary to create an Oppty object
	 * @return An Oppty object
	 */
	protected Oppty utilCreateOpptyObject(TestProperties utilProperties) {
		Page_ createOpptyRTPage		= createCommonPageInstance(utilProperties, "PAGE_COMMON_RECORD_TYPE_PROPERTIES_FILE");
		Page_ createOpptyPage		= createCommonPageInstance(utilProperties, "PAGE_CREATE_OPPTY_PROPERTIES_FILE");
      	Page_ viewOpptyPage			= createCommonPageInstance(utilProperties, "PAGE_VIEW_OPPTY_PROPERTIES_FILE");
      	Page_ viewOpptyInPortalPage	= createCommonPageInstance(utilProperties, "PAGE_VIEW_OPPTY_IN_PORTAL_PROPERTIES_FILE");
      	Page_ addEditProductsPage	= createCommonPageInstance(utilProperties, "PAGE_ADD_EDIT_PRODUCTS_PROPERTIES_FILE");
      	Page_ addPartnerPage   		= createCommonPageInstance(utilProperties, "PAGE_ADD_PARTNER_PROPERTIES_FILE");
      	Page_ addCompetitorPage		= createCommonPageInstance(utilProperties, "PAGE_ADD_COMPETITOR_PROPERTIES_FILE");
      	Page_ addSalesTeamPage		= createCommonPageInstance(utilProperties, "PAGE_ADD_SALESTEAM_PROPERTIES_FILE");
      	Page_ createPartnerOpportunityPage	= createCommonPageInstance(utilProperties, "PAGE_CREATE_OPPTY_IN_PORTAL_PROPERTIES_FILE", 600);
      	Page_ portalLandingPage	= createCommonPage("PAGE_PORTAL_LANDING_PAGE_PROPERTIES_FILE");
      	Oppty oppty = new Oppty(createOpptyRTPage, createOpptyPage, viewOpptyPage, viewOpptyInPortalPage, addEditProductsPage, addPartnerPage, addCompetitorPage, addSalesTeamPage,createPartnerOpportunityPage,portalLandingPage);

     	return oppty;
    }

	protected Oppty utilCreateQuoteFromOpptyObject(TestProperties utilProperties) {
		Page_ createOpptyRTPage		= createCommonPageInstance(utilProperties, "PAGE_COMMON_RECORD_TYPE_PROPERTIES_FILE");
		Page_ createOpptyPage		= createCommonPageInstance(utilProperties, "PAGE_CREATE_OPPTY_PROPERTIES_FILE");
      	Page_ viewOpptyPage			= createCommonPageInstance(utilProperties, "PAGE_VIEW_OPPTY_PROPERTIES_FILE");
      	Page_ viewOpptyInPortalPage	= createCommonPageInstance(utilProperties, "PAGE_VIEW_OPPTY_IN_PORTAL_PROPERTIES_FILE");
      	Page_ addEditProductsPage	= createCommonPageInstance(utilProperties, "PAGE_ADD_EDIT_PRODUCTS_PROPERTIES_FILE");
      	Page_ addPartnerPage   		= createCommonPageInstance(utilProperties, "PAGE_ADD_PARTNER_PROPERTIES_FILE");
      	Page_ addCompetitorPage		= createCommonPageInstance(utilProperties, "PAGE_ADD_COMPETITOR_PROPERTIES_FILE");
      	Page_ addSalesTeamPage		= createCommonPageInstance(utilProperties, "PAGE_ADD_SALESTEAM_PROPERTIES_FILE");
      	Page_ createQuotePage		= createCommonPageInstance(utilProperties, "PAGE_CREATE_QUOTE_FROM_OPPTY_PROPERTIES_FILE");
      	Page_ viewQuotePage			= createCommonPageInstance(utilProperties, "PAGE_VIEW_QUOTE_PROPERTIES_FILE");
      	Page_ editQuotePage			= createCommonPageInstance(utilProperties, "PAGE_EDIT_QUOTE_PROPERTIES_FILE");
      	Page_ editQuoteProductsPage	= createCommonPageInstance(utilProperties, "PAGE_EDIT_QUOTE_PRODUCTS_PROPERTIES_FILE");
      	Page_ addNoteToQuotePage	= createCommonPageInstance(utilProperties, "PAGE_ADD_NOTE_TO_QUOTE_PROPERTIES_FILE");
      	Page_ createPDFToQuotePage	= createCommonPageInstance(utilProperties, "PAGE_CREATE_PDF_TO_QUOTE_PROPERTIES_FILE");
      	Page_ changeAutodeskEntityForQuotePage = createCommonPageInstance(utilProperties , "PAGE_CHANGE_AUTODESK_ENTITY_FOR_QUOTE_PROPERTIES_FILE");
      	Oppty oppty = new Oppty(createOpptyRTPage, createOpptyPage, viewOpptyPage, viewOpptyInPortalPage, addEditProductsPage, addPartnerPage, addCompetitorPage, addSalesTeamPage, createQuotePage, viewQuotePage, editQuotePage, editQuoteProductsPage, addNoteToQuotePage, createPDFToQuotePage, changeAutodeskEntityForQuotePage);

     	return oppty;
    }

	/**
	 * Instantiates an Oppty object, using data in the specified TestProperties object.  Does not execute the workflow
	 * to create an SFDC Opportunity.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return An Oppty object
	 */
    protected Oppty utilCreateOpptyObject(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

		return utilCreateOpptyObject(utilProperties);
    }
    
	/**
	 * Instantiates an Oppty object, using data in the default TestProperties object.  Does not execute the workflow
	 * to create an SFDC Opportunity.
	 * @return An Oppty object
	 */
    protected Oppty utilCreateOpptyObject() {
		return utilCreateOpptyObject(testProperties);
    }

    
    
    /**
	 * Creates an SFDC Account object by executing a workflow, using data in the specified TestProperties object.
	 * @param utilProperties a TestProperties object containing all data necessary to create an SFDC Account object
	 * @return An Account object representing a created SFDC Account
	 * @see common.Account#create()
	 */
    protected Account utilCreateAccount(TestProperties utilProperties) {
    	Account account = utilCreateAccountObject(utilProperties);
       	account.create();
       	
       	return account;
    }
	
    protected Account utilCreateAccountForOppty(TestProperties utilProperties) {
    	Account account = utilCreateAccountForOpptyObject(utilProperties);
       	account.createForOppty(account.getName());
       	
       	return account;
    }
    
    
	/**
	 * Creates an SFDC Account object by executing a workflow, using data in the specified TestProperties object.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return An Account object representing a created SFDC Account
	 * @see common.Account#create()
	 */
    protected Account utilCreateAccount(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

       	return utilCreateAccount(utilProperties);
    }
	
	/**
	 * Creates an SFDC Account object by executing a workflow, using data in the default TestProperties object.
	 * @return An Account object representing a created SFDC Account
	 * @see common.Account#create()
	 */
    protected Account utilCreateAccount() {
       	return utilCreateAccount(testProperties);
    }
    
    protected Account utilCreateAccountForOppty() {
       	return utilCreateAccountForOppty(testProperties);
    }
	
	/**
	 * Instantiates an Account object, using data in the specified TestProperties object.  Does not execute the workflow
	 * to create an SFDC Account.
	 * @param utilProperties a TestProperties object containing data necessary to create an Account object
	 * @return An Account object
	 */
	protected Account utilCreateAccountObject(TestProperties utilProperties) {
		Page_ createAccountRTPage	= createCommonPageInstance(utilProperties, "PAGE_COMMON_RECORD_TYPE_PROPERTIES_FILE");
		Page_ createAccountPage		= createCommonPageInstance(utilProperties, "PAGE_CREATE_ACCOUNT_PROPERTIES_FILE");
      	Page_ viewAccountPage		= createCommonPageInstance(utilProperties, "PAGE_VIEW_ACCOUNT_PROPERTIES_FILE");
      
      	Account account = new Account(createAccountRTPage, createAccountPage, viewAccountPage);

     	return account;
    }

	protected Account utilCreateAccountForOpptyObject(TestProperties utilProperties) {
		Page_ createAccountRTPage	= createCommonPageInstance(utilProperties, "PAGE_COMMON_RECORD_TYPE_PROPERTIES_FILE");
		Page_ createAccountPage		= createCommonPageInstance(utilProperties, "PAGE_CREATE_ACCOUNT_PROPERTIES_FILE");
      	Page_ viewAccountPage		= createCommonPageInstance(utilProperties, "PAGE_VIEW_ACCOUNT_PROPERTIES_FILE");
    	Page_ createPartnerOpportunityPage			= createCommonPageInstance(utilProperties, "PAGE_CREATE_OPPTY_IN_PORTAL_PROPERTIES_FILE");
      	Page_ createAccountFromAccountsLink			= createCommonPageInstance(utilProperties, "PAGE_CREATE_ACCOUNT_FROM_ACCOUNTS_LINK_PROPERTIES_FILE");
      	Account account = new Account(createAccountRTPage, createAccountPage, viewAccountPage,createPartnerOpportunityPage,createAccountFromAccountsLink);
     	return account;
    }

	
	/**
	 * Instantiates an Account object, using data in the specified TestProperties object.  Does not execute the workflow
	 * to create an SFDC Account.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return An Account object
	 */
    protected Account utilCreateAccountObject(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

		return utilCreateAccountObject(utilProperties);
    }
    
	/**
	 * Instantiates an Account object, using data in the default TestProperties object.  Does not execute the workflow
	 * to create an SFDC Account.
	 * @return An Account object
	 */
    protected Account utilCreateAccountObject() {
		return utilCreateAccountObject(testProperties);
    }

    
    
    /**
	 * Creates an SFDC Discount Approval Request object by executing a workflow, using data in the specified TestProperties object.
	 * @param utilProperties a TestProperties object containing all data necessary to create a Discount Approval Request
	 * @param oppty the Oppty object associated with this Discount Approval Request object
	 * @return A Discount Approval Request object representing a created SFDC Discount Approval Request
	 */
    protected final DiscountApprovalRequest utilCreateDiscountApprovalRequest(TestProperties utilProperties, Oppty oppty) {
    	DiscountApprovalRequest dar = utilCreateDiscountApprovalRequestObject(utilProperties);
       	oppty.createDiscountApprovalRequest(dar);
    	
       	return dar;
    }
	
	/**
	 * Creates an SFDC Discount Approval Request object by executing a workflow, using data in the specified TestProperties object.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @param oppty the Oppty object associated with this Discount Approval Request object
	 * @return A Discount Approval Request object representing a created SFDC Discount Approval Request
	 */
    protected final DiscountApprovalRequest utilCreateDiscountApprovalRequest(String utilPropertiesFilenameKey, Oppty oppty) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

       	return utilCreateDiscountApprovalRequest(utilProperties, oppty);
    }
	
	/**
	 * Creates an SFDC Discount Approval Request object by executing a workflow, using data in the default TestProperties object.
	 * @param oppty the Oppty object associated with this Discount Approval Request object
	 * @return A Discount Approval Request object representing a created SFDC Discount Approval Request
	 */
    protected final DiscountApprovalRequest utilCreateDiscountApprovalRequest(Oppty oppty) {
       	return utilCreateDiscountApprovalRequest(testProperties, oppty);
    }
	
	/**
	 * Instantiates a DiscountApprovalRequest object, using data in the specified TestProperties object.  Does not execute the workflow
	 * to create an SFDC Discount Approval Request.
	 * @param utilProperties a TestProperties object containing data necessary to create a DiscountApprovalRequest object
	 * @return A DiscountApprovalRequest object
	 */
	protected final DiscountApprovalRequest utilCreateDiscountApprovalRequestObject(TestProperties utilProperties) {
      	Page_ editDiscountApprovalRequestPage	= createCommonPageInstance(utilProperties, "PAGE_EDIT_DISCOUNT_APPROVAL_REQUEST_PROPERTIES_FILE");
      	Page_ viewDiscountApprovalRequestPage	= createCommonPageInstance(utilProperties, "PAGE_VIEW_DISCOUNT_APPROVAL_REQUEST_PROPERTIES_FILE");

      	DiscountApprovalRequest dar = new DiscountApprovalRequest(editDiscountApprovalRequestPage, viewDiscountApprovalRequestPage);

     	return dar;
    }

	/**
	 * Instantiates a DiscountApprovalRequest object, using data in the specified TestProperties object.  Does not execute the workflow
	 * to create an SFDC Discount Approval Request.
	 * @param utilPropertiesFilenameKey the key used for looking up the test properties file in the test manifest
	 * @return A DiscountApprovalRequest object
	 */
    protected final DiscountApprovalRequest utilCreateDiscountApprovalRequestObject(String utilPropertiesFilenameKey) {
    	TestProperties utilProperties = new TestProperties(getTestPropertiesFilename(utilPropertiesFilenameKey), getTestDataDir(), getTestManifest());

		return utilCreateDiscountApprovalRequestObject(utilProperties);
    }
    
	/**
	 * Instantiates a DiscountApprovalRequest object, using data in the default TestProperties object.  Does not execute the workflow
	 * to create an SFDC Discount Approval Request.
	 * @return A DiscountApprovalRequest object
	 */
    protected final DiscountApprovalRequest utilCreateDiscountApprovalRequestObject() {
		return utilCreateDiscountApprovalRequestObject(testProperties);
    }
        
	/**
	 * Saves the locators associated with Field objects contained in a Page_ object, and referenced in a collection
	 * of FieldData objects.
	 * @param page a Page_ object
	 * @param fieldDataRecords a list of FieldData objects.  The fieldName member of the FieldData objects is used
	 * to find the target Field objects
	 * @return A map keyed on Field.fieldName, containing Field locators 
	 */
    public static Map<String, List<String>> getReferencedFieldLocators(Page_ page, List<FieldData_> fieldDataRecords) {
		Map<String, List<String>> fieldLocatorsSaved = new HashMap<String, List<String>>();
		String fieldName;
		
		ListIterator<FieldData_> itr = fieldDataRecords.listIterator();
		while (itr.hasNext()) {
			fieldName = itr.next().getFieldName();
			
			fieldLocatorsSaved.put(fieldName, page.getFieldLocators(fieldName));
		}

		return fieldLocatorsSaved;
	}

	/**
	 * Parses the tokens contained in the locators associated with a collection of Field objects contained in a
	 * Page_ object, and referenced by a collection of FieldData objects.
	 * @param page a Page_ object
	 * @param fieldDataRecords a list of FieldData objects.  The fieldName member of the FieldData objects is used
	 * to find the target Field objects
	 * @param value the value with which to replace the tokens
	 * @see common.Page#parseFieldLocatorsTokens(String fieldName, String value)
	 */
    public static void parseReferencedFieldLocators(Page_ page, List<FieldData_> fieldDataRecords, String value) {
		ListIterator<FieldData_> itr = fieldDataRecords.listIterator();
		while (itr.hasNext()) {
			page.parseFieldLocatorsTokens(itr.next().getFieldName(), value);
		}
	}

    public static boolean search(String searchTerm) {
    	mainWindow.select();
    	if(commonPage.checkFieldExistence("primaryTabSplitButton")) {
    		commonPage.click("primaryTabSplitButton");
        	commonPage.click("closePrimaryTabLink");
		}
//    	commonPage.clickAndWait("tabContacts", "newButton");
    	commonPage.populateField("searchBox", searchTerm);
    	//commonPage.clickToSubmit("searchButton");
    	commonPage.clickToSubmit("searchButton", "searchAgainButton");
    	
    	return(commonPage.isFieldPresent("noMatchesFoundMsg"));
    }

public static void swithToIframeWhereMyFieldExist(Page_ page, String field ){
		
		List<WebElement> framesList=null;
		try {
			framesList = commonPage.getMultipleWebElementsfromField("iframesList");
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<framesList.size();i++){
			String frameID=framesList.get(i).getAttribute("id");
			EISTestBase.driver.switchTo().frame(frameID);
			if (page.checkFieldExistence(field)){
				break;
			}else{
				EISTestBase.driver.switchTo().defaultContent();
			}
			
		}
		
	}
    
    public static void editUpdateUser(String [] userEmails , String userApprovalRequestEmailStatus){
    	boolean updateUser = false;
		for(String userEmail:userEmails){
			String userDisplayName = userEmail.split("\\@")[0].replace(".", " ").toLowerCase();	
			search(userEmail);
			
			int numberOfUsers = commonPage.getNumRowsInRelatedList("usersRelatedList");		
			for(int user=0;user<numberOfUsers;user++){
				if(commonPage.getRelatedListCell("nameInUsersRelatedList", user).toString().equalsIgnoreCase(userDisplayName)){					
				
				commonPage.clickLinkInRelatedList("nameInUsersRelatedList", user);
				
				commonPage.waitForFieldPresent("userDetail");
				commonPage.clickAndWait("userDetail" , "editButton");
				
				commonPage.clickAndWait("editButton" , "saveButton");
				if(commonPage.isFieldPresent("receiveApprovalEmails")){
					updateUser = true;					
				}
				else{
					updateUser = false;
				}
				
				if(updateUser){	
					commonPage.populateField("receiveApprovalEmails", userApprovalRequestEmailStatus);
					commonPage.clickAndWait("saveButton" , "editButton");
					Util.printInfo("Verifying the Changed value for 'Receive Approval Request Emails Status' field for the user " + userEmail);
					commonPage.verifyField("receiveApprovalRequestEmailsStatus", userApprovalRequestEmailStatus);
					break;
				}
				
				else{
					if(user<numberOfUsers){
					search(userEmail);
					}
					if(user==numberOfUsers){
					fail("Not able to find the 'Reveive Approval Request Email Status' field for the user" + user );
					}
				}
			}
		  }
		}		
    	
    }
    
    public static void editUpdateUserbyURL (String [] userUrls , String userApprovalRequestEmailStatus){
    	boolean updateUser = false;
    	
    	for(String userURL:userUrls){			
			driver.get(userURL);			
			Util.printInfo("Opened '" + userURL + "'");	
			Util.sleep(10000);
			//commonPage.waitForFieldPresent("userDetail", 20000);
			//commonPage.clickAndWait("userDetail" , "editButton", 10000000);
			//Util.printInfo("Opened UserDetailed page for " + userURL);	
			//commonPage.clickAndWait("editButton" , "saveButton", 10000000);
			/*int i = 1;
			updateUser = false;
			while (i < 3){
				if(commonPage.isFieldPresent("receiveApprovalEmails")){
					updateUser = true;	
					break;
				}
				else{
					Util.sleep(10000);
					i++;					
				}
			}						
			if(updateUser){	
				commonPage.populateField("receiveApprovalEmails", userApprovalRequestEmailStatus);
				commonPage.clickAndWait("saveButton" , "editButton");
				Util.printInfo("Updated the field to 'Receive Approval Request Emails Status' field for the user " + userURL);								
			}
			
			else{					
				Util.printInfo("The edit page did not load after trying for three wait times for " + userURL );					
			}			*/
			if(commonPage.isFieldPresent("useMatchedVersion")){
				commonPage.clickAndWait("useMatchedVersion", "editButton");
				Util.printInfo("Clicked on Use Matched Version button");
			}else{
				Util.printInfo("Use Matched Version button is not present");
			}
    	
		}
	}
    public static void editUpdateUserbyURLResubmitAccount (String [] userUrls , String userApprovalRequestEmailStatus){
    	boolean updateUser = false;
    	
    	for(String userURL:userUrls){			
			driver.get(userURL);			
			Util.printInfo("Opened '" + userURL + "'");	
			Util.sleep(10000);
			//commonPage.waitForFieldPresent("userDetail", 20000);
			//commonPage.clickAndWait("userDetail" , "editButton", 10000000);
			//Util.printInfo("Opened UserDetailed page for " + userURL);	
			//commonPage.clickAndWait("editButton" , "saveButton", 10000000);
			/*int i = 1;
			updateUser = false;
			while (i < 3){
				if(commonPage.isFieldPresent("receiveApprovalEmails")){
					updateUser = true;	
					break;
				}
				else{
					Util.sleep(10000);
					i++;					
				}
			}						
			if(updateUser){	
				commonPage.populateField("receiveApprovalEmails", userApprovalRequestEmailStatus);
				commonPage.clickAndWait("saveButton" , "editButton");
				Util.printInfo("Updated the field to 'Receive Approval Request Emails Status' field for the user " + userURL);								
			}
			
			else{					
				Util.printInfo("The edit page did not load after trying for three wait times for " + userURL );					
			}			*/
			if(commonPage.isFieldPresent("resubmitAccount")){
				commonPage.click("resubmitAccount");
				Util.printInfo("Clicked on Resubmit Account button");
				Util.sleep(5000);
				String alertMsg=commonPage.handleAlert(AlertResponseType.OK);
				Util.printInfo("MSG:"+alertMsg);	

			}else{
				Util.printInfo("Resubmit Account button is not present");
			}
    	
		}
	}
    
    
    public static void editUpdateUserbyURLOverrideTrillumProcess (String [] userUrls , String userApprovalRequestEmailStatus){
    	boolean updateUser = false;
    	
    	for(String userURL:userUrls){			
			driver.get(userURL);			
			Util.printInfo("Opened '" + userURL + "'");	
			Util.sleep(10000);
			//commonPage.waitForFieldPresent("userDetail", 20000);
			//commonPage.clickAndWait("userDetail" , "editButton", 10000000);
			//Util.printInfo("Opened UserDetailed page for " + userURL);	
			//commonPage.clickAndWait("editButton" , "saveButton", 10000000);
			/*int i = 1;
			updateUser = false;
			while (i < 3){
				if(commonPage.isFieldPresent("receiveApprovalEmails")){
					updateUser = true;	
					break;
				}
				else{
					Util.sleep(10000);
					i++;					
				}
			}						
			if(updateUser){	
				commonPage.populateField("receiveApprovalEmails", userApprovalRequestEmailStatus);
				commonPage.clickAndWait("saveButton" , "editButton");
				Util.printInfo("Updated the field to 'Receive Approval Request Emails Status' field for the user " + userURL);								
			}
			
			else{					
				Util.printInfo("The edit page did not load after trying for three wait times for " + userURL );					
			}			*/
			if(commonPage.isFieldPresent("overrideTrilliumProcess")){
				commonPage.click("overrideTrilliumProcess");
				Util.printInfo("Clicked on Override Trillium Process button");
				Util.sleep(5000);
				String alertMsg=commonPage.handleAlert(AlertResponseType.OK);
				Util.printInfo("MSG:"+alertMsg);	

			}else{
				Util.printInfo("Override Trillium Process button is not present");
			}
    	
		}
	}	
    
    
    public static void ViewUserAndExportAcctIDbyURL (String [] userUrls , String outputFileName) throws RowsExceededException, WriteException, BiffException, IOException{
    	boolean updateUser = false;
    	String accountCSN;
		String accountIDs = "";
		String accountID = null;
		TreeMap<String, String> map = new TreeMap<String, String>();

    	for(String userURL:userUrls){			
			driver.get(userURL);			
			Util.printInfo("Opened '" + userURL + "'");	
			Util.sleep(5000);
			//commonPage.waitForFieldPresent("userDetail", 20000);
			//commonPage.clickAndWait("userDetail" , "editButton", 10000000);
			//Util.printInfo("Opened UserDetailed page for " + userURL);	
			//commonPage.clickAndWait("editButton" , "saveButton", 10000000);
			/*int i = 1;
			updateUser = false;
			while (i < 3){
				if(commonPage.isFieldPresent("receiveApprovalEmails")){
					updateUser = true;	
					break;
				}
				else{
					Util.sleep(10000);
					i++;					
				}
			}						
			if(updateUser){	
				commonPage.populateField("receiveApprovalEmails", userApprovalRequestEmailStatus);
				commonPage.clickAndWait("saveButton" , "editButton");
				Util.printInfo("Updated the field to 'Receive Approval Request Emails Status' field for the user " + userURL);								
			}
			
			else{					
				Util.printInfo("The edit page did not load after trying for three wait times for " + userURL );					
			}	
			
					*/
			
			if(commonPage.isFieldPresent("CSNInAccountsPanel")){
				accountCSN = commonPage.getValueFromGUI("CSNInAccountsPanel");
				accountID = userURL.split("https://cs12.salesforce.com/")[1];
				
				if(accountCSN.isEmpty()){
					Util.printInfo("Account CSN doesn't exist for the account id " + accountID );										
					if(accountIDs.isEmpty()){
						accountIDs = accountID;						 
					}
					else{
						accountIDs = accountIDs.concat(";").concat(accountID);									
					}							
				}				
				else{
					Util.printInfo("Account CSN exists for the user id : " + accountID + "Account CSN IS : " + accountCSN);
				}
			}
    	
		}
    	map.put("Account IDs which doesn't have Account CSN", accountIDs);
    	Util.writeToExcel(outputFileName, map);
    }
   
    public static void switchDriverToFrame(int frameNumber) {
    	//NOTE that the caller must call mainWindow.select() in order to point the driver back to the main window!!!
    	try {
    		driver.switchTo().frame(frameNumber);
    	} catch (NoSuchFrameException e) {
    		fail("While attempting to switch to frame number " + frameNumber + ", got NoSuchFrameException");
    	}
    }

    public static void emptyRecycleBin(boolean isEmptyYourBin) {
    	String messageChunk = isEmptyYourBin ? "your" : "your organization's";
    	mainWindow.select();
    	
    	commonPage.click("recycleBin");
    	
    	if (isEmptyYourBin) {
    		commonPage.click("emptyYourRecycleBinButton");
    	} else {
    		commonPage.click("emptyYourOrgsRecycleBinButton");
    	}

    	Util.printInfo("Emptied " + messageChunk + " recycle bin");
    }

    public static void emptyRecycleBin() {
    	emptyRecycleBin(true);
    }

    //DEBUG Will this work?
    protected String loginAsAutoUser(boolean launch) {
    	login(getAutoUserName(), getAutoPassword(), launch);
    	
    	return getCurrentUser();
    }

	protected String getLocaleString(String languageCodeConstantName, String countryCodeConstantName) {
		String localeString = "";
		LanguageCodeType languageCode = null;
		String constantString;
		
		if (!languageCodeConstantName.trim().equalsIgnoreCase(EISConstants.LANGUAGE_CODE_ENUM_CONSTANT_NAME)) {
			fail("The name of the constant that stores the name of a member of the EISConstants.LanguageCodeType enumerated type is incorrect; it is '" + languageCodeConstantName + "' but should be '" + EISConstants.LANGUAGE_CODE_ENUM_CONSTANT_NAME + "'");
		}
		
		constantString = testProperties.getConstant(languageCodeConstantName).trim().toLowerCase();

		try {
			languageCode = LanguageCodeType.valueOf(constantString);
		} catch (IllegalArgumentException e) {
			fail("The value supplied in the '" + languageCodeConstantName + "' constant ('" + constantString + "') is not a member of the EISConstants.LanguageCodeType enumerated type; valid values are: " + Util.valuesOfEnum(LanguageCodeType.class));
		}
		
		localeString += "_" + constantString;
		
		if (EISConstants.LANGUAGE_CODES_REQUIRING_COUNTRY_CODE.contains(languageCode)) {
			if (!countryCodeConstantName.trim().equalsIgnoreCase(EISConstants.COUNTRY_CODE_ENUM_CONSTANT_NAME)) {
				fail("The name of the constant that stores the name of a member of the EISConstants.CountryCodeType enumerated type is incorrect; it is '" + countryCodeConstantName + "' but should be '" + EISConstants.COUNTRY_CODE_ENUM_CONSTANT_NAME + "'");
			}
			
			constantString = testProperties.getConstant(countryCodeConstantName).trim().toUpperCase();

			try {
				CountryCodeType.valueOf(constantString);
			} catch (IllegalArgumentException e) {
				fail("The value supplied in the '" + countryCodeConstantName + "' constant ('" + constantString + "') is not a member of the EISConstants.CountryCodeType enumerated type; valid values are: " + Util.valuesOfEnum(CountryCodeType.class));
			}
			
			localeString += "_" + constantString;			
		}
				
		return localeString;
	}

	protected String getLocaleString(String languageCodeConstantName) {
		return getLocaleString(languageCodeConstantName, "");
	}

	protected String getLocaleString() {
		return getLocaleString(EISConstants.LANGUAGE_CODE_ENUM_CONSTANT_NAME, "");
	}		

	protected String getLocalizedString(String key) {
		String value = null;
		
		try {
			value = resBun.getString(key).trim();
		} catch (MissingResourceException e) {
			fail("The key '" + key + "' was not found in the resource bundle " + resourceBundleName);
		}
		
		return value;
	}
	
	public static String getErrorReason() {
		return errorReason;
	}
	public static void setErrorReason(String errorReason) {
		EISTestBase.errorReason = errorReason;
	}

	 public static void findElementByXpathAndClick(String xpath) 
	    {
	    
	    	try{
	    	WebElement webElement = driver.findElement(By.xpath(xpath));
	    	/*Util.printMessage("webElement------"+webElement);*/
	    	driver.switchTo().activeElement();
			if(!xpath.equalsIgnoreCase("//table[normalize-space(@class)='ui-cep-grid-table table table-bordered']"))
				driver.findElement(By.xpath(xpath)).click();
			
	    	}
	    	catch (Exception e)
	    	{
	    		String[] webDriverErrorMsg = e.getMessage().split("Command duration");
	    		
	    		fail(webDriverErrorMsg[0]);
	    		
	    	}
	    }
	 /**
	  * @Description Kills the specific browser
	  * @param browserName
	  */
	 public  void killProcess(String browserName) {

			final String KILL = "taskkill /F /IM ";
			String iexploreBrowserName = "iexplore.exe"; //IE process
			String chromeBrowserName = "chrome.exe"; //IE process
			String firefoxBrowserName = "firefox.exe"; //IE process
			String iexploreDriverName = "IEDriverServer.exe"; //IE process
			String chromeDriverName = "chromedriver.exe"; //IE process
			//do this only when the flag is set. otherwise on user machine it closes all the opened windows
			if (EISConstants.GLOBAL_BROWSERS_CLOSE) {
				try {
					
					if(browserName.equalsIgnoreCase("chrome")){
						Runtime.getRuntime().exec(KILL + chromeBrowserName);
						Util.sleep(3000);
						Runtime.getRuntime().exec(KILL + chromeDriverName);
						Util.sleep(3000);
					}
					else if(browserName.equalsIgnoreCase("ie")){
						Runtime.getRuntime().exec(KILL + iexploreBrowserName);
						Util.sleep(3000);
						Runtime.getRuntime().exec(KILL + iexploreDriverName);
						Util.sleep(3000);
					}
					else if(browserName.equalsIgnoreCase("firefox")){
						Runtime.getRuntime().exec(KILL + firefoxBrowserName);
						Util.sleep(3000);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				Util.sleep(3000); //Allow OS to kill the process
			}
	 }

	 public  void killProcess() {

			final String KILL = "taskkill /F /IM ";
			String iexploreBrowserName = "iexplore.exe"; //IE process
			String chromeBrowserName = "chrome.exe"; //IE process
			String firefoxBrowserName = "firefox.exe"; //IE process
			String iexploreDriverName = "IEDriverServer.exe"; //IE process
			String chromeDriverName = "chromedriver.exe"; //IE process
			try {
				
				if(getAppBrowser().equalsIgnoreCase("chrome")){
					Runtime.getRuntime().exec(KILL + chromeBrowserName);
					Util.sleep(3000);
					Runtime.getRuntime().exec(KILL + chromeDriverName);
					Util.sleep(3000);
				}
				else if(getAppBrowser().equalsIgnoreCase("ie")){
					Runtime.getRuntime().exec(KILL + iexploreBrowserName);
					Util.sleep(3000);
					Runtime.getRuntime().exec(KILL + iexploreDriverName);
					Util.sleep(3000);
				}
				else if(getAppBrowser().equalsIgnoreCase("firefox")){
					Runtime.getRuntime().exec(KILL + firefoxBrowserName);
					Util.sleep(3000);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			Util.sleep(3000); //Allow OS to kill the process

		}
	 
	 protected final boolean verifySfdcUserGeo(String expectedGeo) throws InterruptedException {
		 
		 //goto My Profile
		 driver.findElement(By.xpath("//div[@id='userNavButton']")).click();
		 driver.findElement(By.xpath("//a[@title='My Profile']")).click();
		 synchronized (driver) {driver.wait(3000);}
		 
		 //goto User Detail Page
		 driver.findElement(By.xpath("//a[@id='moderatorMutton']")).click();
		 driver.findElement(By.xpath("//a[@title='User Detail']")).click();
		 
		 //get Value from User Detail Page and verify
		 String foundGeo = driver.findElement(By.xpath("//td[text()=\"User's Geo\"]//following-sibling::td[1]")).getText();
		 return assertEquals("userGeo", foundGeo, expectedGeo);
	 }
	public static String getBrowserVersion() {
		// TODO Auto-generated method stub
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		String browserVersion = cap.getVersion();
		return " " +browserName +" " +browserVersion;
	}
	public void loginToGmail (String strUsername, String strPassword)
	{
		//Goto gmail page
		open("http://mail.google.com/");
		
		//log out if already logged in to any account
		if(emailClientPage.getLocation().contains("inbox")) {
			logoutOfGmail();
		}
		Util.printInfo("Launched Gmail at http://mail.google.com/");
		
		//If redirected to help page, go to Sign In page
		if(emailClientPage.isFieldPresent("gotoSignInPage")) 
			{emailClientPage.clickAndWait("gotoSignInPage", "username");}
		
		//Enter credentials. uncheck cookies
		emailClientPage.populateField("username", strUsername);
		emailClientPage.populateField("password", strPassword);
		emailClientPage.uncheck("cookieCheckbox");
		emailClientPage.clickAndWait("signInButton", "searchBar");
		
		Util.printInfo("Logged Into " + getGmailUser());
	}

	public void logoutOfGmail ()
	{
		String currentUser = getGmailUser();
		emailClientPage.click("signOutButton");
		Util.printInfo("Logged Out of " + currentUser);
	}
	 public String getGmailUser()
		{
		   	String textAttribute = (getAppBrowser().equalsIgnoreCase("firefox"))? "textContent" : "innerText";
	    	List <WebElement> eleUserNameElementList = new ArrayList<WebElement>();
	    	int numTries = 0;
	    	while (numTries++ < 10) {
	    		eleUserNameElementList = driver.findElements(By.xpath("//a[contains(text(),'Account')]//parent::div//preceding-sibling::div[not(contains(text(),'@'))][not(a)]"));
	    		if(numTries%3==0) {driver.navigate().refresh(); Util.sleep(1000); }
	    		if(!eleUserNameElementList.isEmpty()) {break;}
	    		Util.sleep(1000);
	    	}
	    	
	    	try {
	    		return eleUserNameElementList.get(0).getAttribute(textAttribute);
	    	} catch (IndexOutOfBoundsException e) {
	        	throw new IndexOutOfBoundsException("Cannot find GUI element 'gmailUserName' : " + e.getMessage());
	    	}
			
	    	
		}
	 private boolean doesGmailExist (String strSearch) throws InterruptedException
		{
			//search for mail
			emailClientPage.populateField("searchBar", strSearch);
			Util.printInfo("Searching for " + strSearch);
			emailClientPage.clickAndWait("searchButton", "firstMailItem", 10);
			synchronized(driver){driver.wait(5000);} //clickAndWait not waiting at all; So even if email has been received, method is failing the first check for "firstMailItem"

			//check for mail at intervals of 2 sec, for 10 secs.
			int waitTime = 0;
			while (!(emailClientPage.isFieldPresent("firstMailItem")) && waitTime < 10)
			{
				Util.printDebug("Email not yet received. Trying again after 2 seconds");
				synchronized(driver){driver.wait(2000);}
				emailClientPage.populateField("searchBar", strSearch);
				emailClientPage.click("searchButton");
				waitTime +=2;
			}
			return emailClientPage.isFieldPresent("firstMailItem");
		}
		
		public boolean verifyGmailExists (String strSearch) throws InterruptedException
		{
			return assertTrue("Verify Email "+strSearch+" exists", doesGmailExist(strSearch));
		}
		
		public void openFirstGmail ()
		{
			if(emailClientPage.isFieldPresent("firstMailItem")) {	
				emailClientPage.click("firstMailItem");
			}
		}
		
		public void replyToOpenedGmail (String strMailText) throws InterruptedException
		{
			replyToOpenedGmail (strMailText, null);
		}
		
		public void replyToOpenedGmail (String strMailText, String strSecondLine) throws InterruptedException
		{
			//clicks reply
			emailClientPage.click("replyButton");
			synchronized(driver){driver.wait(5000);};
			
			//send first line
			Actions action = new Actions(driver);
			action.sendKeys(strMailText).perform();
			/*for(int i=0;i<strMailText.length();i++) { 
				action.sendKeys(String.valueOf(strMailText.charAt(i))).perform(); }*/
			
			//Send second line if exists
			if(strSecondLine != null) 
			{
				synchronized(driver){driver.wait(2000);};
				action.sendKeys(Keys.RETURN).perform();
				synchronized(driver){driver.wait(2000);};
				action.sendKeys(strSecondLine).perform();
				synchronized(driver){driver.wait(10000);};
				/*for(int i=0;i<strSecondLine.length();i++) { 
					action.sendKeys(String.valueOf(strSecondLine.charAt(i))).perform(); }*/
			}
			
			//send message
			synchronized(driver){driver.wait(10000);};
			emailClientPage.clickAndWait("sendButton", "messageIsSentAlert");
			
			//check that message is sent
			synchronized(driver){driver.wait(5000);};
			emailClientPage.verifyFieldExists("messageIsSentAlert");
			
			//check that message sent is a valid response by verifying lack of new email response
			String strEmailSubject = emailClientPage.getValueFromGUI("emailSubjectLine");
			boolean isMailValid = true;
			synchronized(driver){driver.wait(5000);};
			isMailValid = !(doesGmailExist("is:Unread from:(Email Approval) subject:(Re: "+strEmailSubject+")"));
			
			//if mail exists double check to ensure it's not an older unread message
			if (!isMailValid)
			{
				openFirstGmail();
				if (driver.findElements(By.xpath("//span[contains(text(),'0 minutes ago')]")).isEmpty()) {
					isMailValid=true;}
				else {Util.printDebug("'Workflow Not Processed' email received at " + driver.findElement(By.xpath("//span[contains(text(),'0 minutes ago')]")).getText());}
			}
			
			//assert
			assertTrueWithFlags("Valid email response has been sent", isMailValid);
			
		}

		
		
		
		public boolean verifyGmailTemplate (String strEmailSubject, List<String> listEmailBodyItems)
		{
			
			boolean verifyMail = true; 
			
			//check subject is as expected, else return false
			verifyMail = verifyMail && emailClientPage.verifyField("emailSubjectLine", strEmailSubject);
			if (!verifyMail) { return verifyMail; }
			
			//get text of email
			String foundEmailBody =	driver.findElements(By.xpath("//div[@style='overflow: hidden;']")).get(0).getText();
			Util.printDebug("Found Email Text: \n" + foundEmailBody + "\n\n");
			
			//verify that all items are present in email text
			Util.debugMode=true;
			Util.printDebug("Items to be found: ");
			for (String eachItem : listEmailBodyItems)
			{
				verifyMail = verifyMail && foundEmailBody.contains(eachItem);
				Util.printDebug("\t" + eachItem + "; Result: " + String.valueOf(verifyMail));
				if (!verifyMail) { break;}
			}
			Util.debugMode=false;
			
			//assert
			return assertTrueWithFlags("Email Body should contain provided values from Template", verifyMail);		
		}
		
		
		public String getStringFromListOfStrings (List<String> listOfStrings)
		{
			String strMessage = "";
			for (String eachString : listOfStrings) { strMessage += eachString + ", "; }
			strMessage = strMessage.substring(0, strMessage.lastIndexOf(','));
			return strMessage+'.';
		}
		 
		/**
		 * Description : This is used to verify the list of expected values with actuals. checkEachExpValue is used to verify each of the values in exp against the actual.
		 * Otherwise call regular method assertEqualsWithFlags(actuals,expecteds); which does the straight checks of both lists
		 * @param actuals
		 * @param expecteds
		 * @param checkEachExpectedVal
		 * @return
		 */
		
		protected boolean assertEqualsWithFlags(List<String> actuals, List<String> expecteds,boolean checkEachExpectedVal) {			
	    	boolean result = false;
	    	if (!checkEachExpectedVal){
	    		assertEqualsWithFlags(actuals,expecteds);
	    	}
			String message = "The actual ('" + actuals.toString() + "') and expected should be in lists of values ('" + expecteds.toString() + "') ";
			
			message += verifyMatch ? "exists " : "not exists ";
			message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
			
			Util.printAssertingMessage(message);
			
			message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
			message += verifyMatch ? ", but do not" : ", but do";

			try {
				
					//do the 1 to 1 check with expected and actual list
					int i=0;
					boolean foundMatch=false;
					for(String myexpStr: expecteds){						
						for (String myactStr:actuals){
							if (myexpStr.equalsIgnoreCase(myactStr)){
								foundMatch=true;
								i++;	//The increment is used to check how many matched with expected list
								break;
							}
						}
					}
						if (foundMatch && i==expecteds.size()){
							reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);								
							return true;
						}
				
			} catch (AssertionError e) {
				result = false;
				
				//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
				//  indeed fail!
				reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
				
				if (!ignoreFailedAsserts) {
					fail(e.getMessage());
				}
				
				assertMessage = e.getMessage();
			}
			
			return result;
	    }
		/*
		 * Description : This is used to verify the list of expected values with actuals. checkEachExpValue is used to verify each of the values in exp against the actual.
		 * Otherwise call regular method assertEqualsWithFlags(actuals,expecteds); which does the straight checks of both lists
		 * @param actuals
		 * @param expecteds
		 * @param checkEachExpectedVal
		 * @return
		 */
		
		protected boolean assertEqualsWithFlags(Map<?, ?> actuals, Map<?,?> expecteds) {			
	    	boolean result = false;
	    	
			String message = "The actual ('" + actuals.toString() + "') and expected should be  ('" + expecteds.toString() + "') ";
			
			message += verifyMatch ? "exists " : "not exists ";
			message += verifyCaseSensitive ? "(case-sensitive match)" : "(case-insensitive match)";
			
			Util.printAssertingMessage(message);
			
			message = EISConstants.ASSERTION_FAILURE_PREFIX + message;
			message += verifyMatch ? ", but do not" : ", but do";

			try {
				 
					//do the 1 to 1 check with expected and actual list
					Assert.assertEquals(expecteds, actuals);
					reportAssertResult(EISConstants.ASSERTION_PASSED_TEXT);
					return true;
			} catch (AssertionError e) {
				result = false;
				
				//We print the assert failed message even if ignoreFailedAsserts is true, because the assert did
				//  indeed fail!
				reportAssertResult(EISConstants.ASSERTION_FAILED_TEXT);
				
				if (!ignoreFailedAsserts) {
					fail(e.getMessage());
				}
				
				assertMessage = e.getMessage();
			}
			
			return result;
	    }
	public static String getCallerClassNameWithOutPackage()  {
		boolean foundTheClassName=false;
		//Find out who is calling this method
		/*String[] getCallerInfo = new String[2];	//get the most recent info from the Stack
		 */			String testClassname=null;
		 try {

			 StackTraceElement[] stackTrace= Thread.currentThread().getStackTrace();
			 for(int i=0;i<stackTrace.length;i++){
				 String myClassname=stackTrace[i].getClassName();
				 if (myClassname.contains("Test_Verify")){
					 //get the Only the class name i.e method name
					 foundTheClassName=true;
					 //the very first one in the array
					 testClassname=myClassname;					
					 break;	

				 }
			 }
			 if(!foundTheClassName){
				 throw new RuntimeException("Unable to get the class name of the test case");
			 }

		 } catch (Exception e) {
			 throw new RuntimeException("Exception occurred while fetching the class name of the test case");
		 }
		 return testClassname;
	}			



	public static boolean isClassNameATestClass(String className) {
		//get the Test name without any package		
		String newClazzname= getCallingClassNameWithOutPackage(className);
		if (newClazzname.equalsIgnoreCase("Test")){
			return true;			
		}
		return false;
	} 

	public static String getCallingClassNameWithOutPackage(String className) {
		//get the Test name without any package
		int lastPeriod= className.lastIndexOf(".");
		String newClazzname= className.substring(lastPeriod+1);
		return newClazzname;
	} 
	/**
	 * @Description Get the time stamp as String only
	 * @return
	 * @throws Exception
	 */
	public static String getTimeStampAsString() throws Exception {
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss");			
		Date myDate= new Date();
		Util.printDebug("The Datetime is :"+dateFormat.format(myDate));
		return dateFormat.format(myDate);
	}
	/**
	 * @Description : This is used to capture network traffic, though it is a temp solution but works as expected
	 * @throws Exception
	 */
	public FirefoxProfile getWebDriverPreferencesForFirefoxProfile() throws Exception {
		/*This is required only for Firefox browser*/		
		
		File firebugXPI = new File(Util.getTestRootDir()+"\\tools\\firebug-2.0b8.xpi");
		File netExportXPI = new File(Util.getTestRootDir()+"\\tools\\netExport-0.9b6.xpi");
		//Clear/ delete capture network traffic folder
		File deleteCaptureNetWorkFolder= new File( Util.getTestRootDir()+"\\logs\\CaptureNetworkTraffic");
		if(deleteCaptureNetWorkFolder.exists()){
			String[]entries = deleteCaptureNetWorkFolder.list();
			for(String s: entries){
				File currentFile = new File(deleteCaptureNetWorkFolder.getPath(),s);
				currentFile.delete();
			}
			deleteCaptureNetWorkFolder.delete();
		}		
		
		FirefoxProfile profile = new FirefoxProfile();
		profile.addExtension(firebugXPI);
		profile.addExtension(netExportXPI);		
		//Setting Firebug preferences
    	profile.setPreference("extensions.firebug.currentVersion", "2.0");
    	profile.setPreference("extensions.firebug.addonBarOpened", false);
    	profile.setPreference("extensions.firebug.console.enableSites", true);
    	profile.setPreference("extensions.firebug.script.enableSites", true);
    	profile.setPreference("extensions.firebug.net.enableSites", true);
    	profile.setPreference("extensions.firebug.previousPlacement", 1);
    	profile.setPreference("extensions.firebug.allPagesActivation", "on");
    	profile.setPreference("extensions.firebug.onByDefault", false);
    	profile.setPreference("extensions.firebug.defaultPanelName", "net");
    	
    	// Setting netExport preferences
    	profile.setPreference("extensions.firebug.netexport.alwaysEnableAutoExport", true);
    	profile.setPreference("extensions.firebug.netexport.autoExportToFile", true);
    	profile.setPreference("extensions.firebug.netexport.Automation", true);
    	profile.setPreference("extensions.firebug.netexport.showPreview", false);
    	//capture the traffic logs in report
    	profile.setPreference("extensions.firebug.netexport.defaultLogDir", Util.getTestRootDir()+"\\logs\\CaptureNetworkTraffic");
    /*	DesiredCapabilities capabilities = new DesiredCapabilities();
    	capabilities.setBrowserName("firefox");
    	capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
    	capabilities.setCapability(FirefoxDriver.PROFILE, profile);*/
    	return profile;
	}
	public static String getWebElementName(WebElement element){
		String elementName="";
		String temp=element.getTagName();
		try{
			switch (temp){
			case "span":{
				elementName=element.getText(); 
				break;
			}
			case "a":{
				 elementName=element.getText(); 
				  if(elementName.isEmpty()){
					  elementName=element.getAttribute("href");
				  }
				  if(elementName.isEmpty()){
					  elementName=element.getAttribute("id");
				  }
				  break;
			}
			case "input":{
				elementName=element.getText(); 
				  if(elementName.isEmpty()){
					  String elementType=element.getAttribute("type"); 
					  if (elementType.equalsIgnoreCase("checkbox")){
						  elementName=element.getAttribute("name"); 
					  }else{
						  elementName=element.getAttribute("id");
					  }
				  }
				  break;
			}
			case "button":{
				 elementName=element.getText();
				 if(elementName.isEmpty()){
					 elementName=element.getAttribute("name");
				  }
				  if(elementName.isEmpty()){
					  elementName=element.getAttribute("type");
				  }
				  if(elementName.isEmpty()){
					  elementName=element.getAttribute("id");
				  }
				  if(elementName.isEmpty()){
					  elementName=element.getAttribute("class");
				  }
				  break;
			}
			case "div":{
				elementName=element.getAttribute("class");
				break;
			}
			case "select":{
				 elementName=element.getAttribute("id");
				 break;
			}
			}
			
		}catch(NullPointerException np){
			elementName="";
		}
		return elementName;
	}
	public static void reportSelectOption(WebElement element, String value){
		reportStep("Select item from web list '"+EISTestBase.getWebElementName(element)+"'", value);
	}
	public static void reportButtonClick(WebElement element){
		if (element.getTagName().equalsIgnoreCase("a"))
		{
			reportStep("Clicking on link '"+EISTestBase.getWebElementName(element)+"'","");
		}else{
			reportStep("Clicking on button '"+EISTestBase.getWebElementName(element)+"'","");
		}
	}
	public static void reportStep(String textToPrint, String dataToPrint){
		report.reportStep(textToPrint,dataToPrint);
	}
	public static void reportValidation(String validationText, String status){
		report.reportValidation(validationText, status);
	}
	public static void reortTestStatus(boolean status){
		report.reportTestStatus(status);
	}
	
	
	/**
	 * @Description Instantiates the Selenium headlesss Phantom driver object here and sets the capabilities to point to the phantomjsdriver.exe 
	 * @throws Exception
	 */
	public void loginToPortalAsHeaderLess() throws Exception{
		/**
		 * Using Phantom js driver to launch the portal and get the grant token
		 */
		try {
			DesiredCapabilities capa= new DesiredCapabilities();
			capa.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, Util.getTestRootDir()+"\\build\\phantomjs.exe");
			driver= new PhantomJSDriver(capa);
			

		} catch (Exception driverEx) {
			Util.printError("Unable to instantiate the Phantom driver object. Please check the phantomjs.exe location exists in the build folder of EISAutomation");			
			driverEx.printStackTrace();
		}

	}

	public void initializeWebDriver(String appBrowser)  {
		if (appBrowser.equals("firefox")) {
    		driver = new FirefoxDriver();
//    		//wait is required to navigate successfully due to selenium defect
    		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    	}
    	else if(appBrowser.equals("chrome")){	
    		System.setProperty("webdriver.chrome.driver", EISConstants.TEST_BASE_PATH + "chromedriver.exe");
    		ChromeOptions chromeOpt= new ChromeOptions();
    		chromeOpt.addArguments("test-type");
    		chromeOpt.addArguments("allow-running-insecure-content");
    		driver=new ChromeDriver(chromeOpt);   
    		driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);    
    	}
    	else if(appBrowser.equals("ie")){	
    		System.setProperty("webdriver.ie.driver", EISConstants.TEST_BASE_PATH + "IEDriverServer.exe");
    		DesiredCapabilities capabilities = new DesiredCapabilities();
    		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
        	driver = new InternetExplorerDriver(capabilities);
    		
    	}else if(appBrowser.equals("safari")){
    		driver = new SafariDriver();    		
    		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    	}
	}
	/**

     * @Description set the environment 

     * @param environment

     */

    public void setEnvironment(String environment) {

            this.environment = environment;

    }


}


