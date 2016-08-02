package cepfrontend;import java.io.IOException;import java.util.ArrayList;import java.util.List;import org.openqa.selenium.By;import org.openqa.selenium.WebElement;import common.Case.CaseType;import common.Case.CreateFrom;import common.EISTestBase;import common.Page_;import common.ReadExcel;import common.TestProperties;import common.Util;/** * Representation of features and functionality specific to the CEP application. *  * @author Nithya Somasundaram * @version 1.0.0 */public class CEPTestBase extends EISTestBase {	private static final String APP_NAME		= "cepfrontend";	private static final String APP_BASE_DIR	= "cepfrontend";	public static Page_ loginPage = null;	public static Page_ homePage = null;    //TODO  We need to figure out what is an auto user, do they use users on the setup page,    //  do they log in as an auto user and then as a partner user, etc.	private String cepUser = "";		//Define only CEP-specific windows.  SFDC-scope windows (such as mainWindow) are static	//  objects in the EISTestBase class		//Define CEP-specific Page objects that have no associated test properties. The pages	//  defined here are those that:	//    will never be referenced in a test properties file, AND	//    contain CEP-specific GUI elements	//Note that if a test DOES need to specify test properties for one of these pages	//  (e.g., search terms), it can create its own local version of the page, and pass	//  the pagePropertiesFilenameKey argument	CEPTestBase(String launchDriver) {		//TODO  Change to get APP_BASE_DIR from properties? (currently passed in as JVM arg)		 super(APP_NAME, APP_BASE_DIR,launchDriver);	    setup();    }		/**	 * Default constructor.  It calls the superclass constructor, passing the application's name and directory in the framework's source code hierarchy.	 * @throws IOException 	 */		CEPTestBase() throws IOException {		//TODO  Change to get APP_BASE_DIR from properties? (currently passed in as JVM arg)	    super(APP_NAME, APP_BASE_DIR);	    setup();    }	 protected void launchCEP(String url) {		 	    	 //Assumes a browser window is already open	    	open(url);	    		    				//Will have to do this every time we open a new window or pop-up,			//  because the locators are generated on the fly in WebDriver	    	mainWindow.setLocator(driver.getWindowHandle());	    	//Save the URL used to launch Salesforce, as sometimes the user wants to use it as a base for	    	//  navigating to another URL, perhaps one that refers to an object ID	    	setBaseURL(url);	    		    	windowMaximize();	    	String appName = getAppName();	    	String currentURL = getCurrentURL();		    Util.printInfo("Launched " + appName + " at '" + currentURL + "'");	    }	    	/**	 * Gets the CEP user name. (NOTE that it is not yet clear what constitutes an CEP user,	 * a partner user, an admin user, etc.)	 * @return The CEP user name	 */    final String getCEPUser() {		return cepUser;	}	@Override	public String toString() {		return getAppName() + " ["				+ "super=             " + super.toString() 				+ ", cepUser=     " + cepUser				+ "]";	}    	/**	 * Configures high-level CEP-specific objects.	 * @see #doSetup()	 */    @Override	protected final void setup() {    	super.setup();    	doSetup();    }    /**	 * Configures high-level CEP-specific objects&#58;<br>	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates CEP-specific Page objects<br>	 * &nbsp;&nbsp;&nbsp;&nbsp;instantiates CEP-specific Window objects	 * @see #createAppWindows()	 * @see #createAppPages()	 */    private final void doSetup() {    	setEnvironmentVariables();    	    	createAppWindows();    	    	createAppPages();    	//MUST be called after super.setup!!!    	//NOTE that CEPUser is the equivalent of a user that is found in the setup screen.    	//    The user that logs in to the app is known as the auto user.  Until we figure out    	//    whether that model applies, don't call this.    	//setCEPUser();    }    	/**	 * Instantiates CEP-specific Window objects.	 */    @Override	protected final void createAppWindows() {    	//Instantiate only CEP-specific windows.  SFDC-scope windows (such as mainWindow) are static    	//  objects in the EISTestBase class    }	/**	 * Instantiates CEP-specific Page objects.	 */	private final void createAppPages() {		//Instantiate Page objects that have no associated test properties		//Note that if a test DOES need to specify test properties for one of these pages		//  (e.g., search terms), it can create its own local version of the page, and pass		//  the pagePropertiesFilenameKey argument, OR create it here, by calling createPage		//  instead of createStaticPage		//Can also instantiate regular (i.e., with associated test properties) CEP-specific		//  Page objects here, but typically it is best for the test or utility methods to do that;		//  if we do it here we may end up creating Page objects that never get used.						loginPage = createPage(testProperties, "PAGE_LOGIN_PROPERTIES_FILE",				600);		homePage = createPage(testProperties, "PAGE_HOME_PROPERTIES_FILE", 600);    }		final CEP utilCreateSubscriptionRenewal(TestProperties utilProperties) throws Exception {		CEP subRenewal = utilCreateSubscriptionRenewalObject(utilProperties);		       	return subRenewal;    }			final CEP utilCreateSubscriptionRenewal()  throws Exception{	       	return utilCreateSubscriptionRenewal(testProperties);     	       	    }		final CEP utilCreateSubscriptionRenewalObject(TestProperties utilProperties) {		CEP cep  = null;		//Use longer pageRedrawDelay setting for slow pages 		Page_ login				= createPage(utilProperties, "PAGE_LOGIN_PROPERTIES_FILE", 600);		Page_ home					= createPage(utilProperties, "PAGE_HOME_PROPERTIES_FILE");				      	      	cep = new CEP(login, home);     	return cep;    }    /**	 * Logs in as the automation user, optionally launching Salesforce (default user is CEPConstants.AUTO_USERNAME).	 * @param launch the setting that determines whether to launch Salesforce before logging in	 * @return The automation user name	 */        /**	 * Logs in as the CEP user	 * @return void	 */ 	public void loginAsCEPUser() throws Exception{    			CEP cep = utilCreateSubscriptionRenewal();				Page_ loginPage = cep.getLoginPage();		Page_ homePage = cep.getHomePage();				loginPage.clickAndWaitForPopUpToOpen("signInButton");				WebElement frame =  driver.findElement(By.id("authFrame"));		driver.switchTo().frame(frame);		loginPage.populateField("userName", getAutoUserName());    	loginPage.populateField("password", getAutoPassword());    	loginPage.click("signInPortalButton");    	homePage.waitForFieldPresent("header");    		}        	/**	 * Selects the default CEP app from the SFDC app chooser.	 */    @Override    protected final void chooseApp() {//    	chooseApp(CEPConstants.DEFAULT_CEP_APP);    }           protected final void setEnvironmentVariables() {    	String jobName=testProperties.getConstant("JENKINS_JOB_NAME");    	switch (getEnvironment().trim().toUpperCase()) {			case "DEV":	{				if (!CEPConstants.BASE_URL_DEV.isEmpty()) {					setBaseURL(CEPConstants.BASE_URL_DEV);				}								setAutoUserName(CEPConstants.AUTO_USERNAME_DEV);				setAutoPassword(CEPConstants.AUTO_PASSWORD_DEV);								break;			}			case "STG":			default:	{				if (!CEPConstants.BASE_URL_STG.isEmpty()) {					setBaseURL(CEPConstants.BASE_URL_STG);				}								ArrayList<String> parameterizedJobs = EISTestBase.getParameterizedJobs();				Util.printInfo("parameterizedJobs----"+parameterizedJobs.get(0));				Util.printInfo("job name----"+jobName);				if(parameterizedJobs.contains(jobName)){										Util.printInfo("Inside parameterization----");					ReadExcel excel = new ReadExcel();					List loginDetails;					try {						loginDetails = excel.readExcel();						setAutoUserName((String) loginDetails.get(0));						setAutoPassword((String) loginDetails.get(1));					} catch (IOException e) {						// TODO Auto-generated catch block						e.printStackTrace();					}									}else{					Util.printInfo("Inside else of parameterization----");							setAutoUserName(CEPConstants.AUTO_USERNAME_STG);					setAutoPassword(CEPConstants.AUTO_PASSWORD_STG);				}			}    	}    }         protected CreateFrom getInterfaceType(String createFromConstantName) {    	String createFromString = "";		CreateFrom createFrom = null;				if (!createFromConstantName.trim().equalsIgnoreCase(CEPConstants.CEP_CREATE_FROM_ENUM_CONSTANT_NAME)) {			fail("The name of the constant that stores the name of a member of the SSConstants.CreateFrom enumerated type is incorrect; it is '" + createFromConstantName + "' but should be '" + CEPConstants.CEP_CREATE_FROM_ENUM_CONSTANT_NAME + "'");		}				createFromString = testProperties.getConstant(createFromConstantName);				try {			createFrom = CreateFrom.valueOf(createFromString.trim().toUpperCase());		} catch (IllegalArgumentException e) {			fail("The value supplied in the '" + createFromConstantName + "' constant ('" + createFromString + "') is not a member of the CEPConstants.CreateFrom enumerated type; valid values are: " + Util.valuesOfEnum(CreateFrom.class));		}				return createFrom;    }       protected CaseType getCaseType(String caseTypeConstantName) {    	String caseTypeString = "";		CaseType caseType = null;				if (!caseTypeConstantName.trim().equalsIgnoreCase(CEPConstants.CEP_CASE_TYPE_ENUM_CONSTANT_NAME)) {			fail("The name of the constant that stores the name of a member of the SSConstants.CaseType enumerated type is incorrect; it is '" + caseTypeConstantName + "' but should be '" + CEPConstants.CEP_CASE_TYPE_ENUM_CONSTANT_NAME + "'");		}				caseTypeString = testProperties.getConstant(caseTypeConstantName);				try {			caseType = CaseType.valueOf(caseTypeString.trim().toUpperCase());		} catch (IllegalArgumentException e) {			fail("The value supplied in the '" + caseTypeConstantName + "' constant ('" + caseTypeString + "') is not a member of the SSConstants.CaseType enumerated type; valid values are: " + Util.valuesOfEnum(CaseType.class));		}				return caseType;    }          public void findElementByXpath(String xpath)    {        	EISTestBase.findElementByXpathAndClick(xpath);    }  }