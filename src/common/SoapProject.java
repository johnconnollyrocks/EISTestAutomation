/** ===========================================
 *   DESCRIPTION OF METHODS
 *  ===========================================
 *  -------------------------------------------
 *  PRIVATE METHODS
 *  -------------------------------------------
 *  	private void setKeystore (String wsdlWithPath, String env)
 *  	private String getSoapResult (String methodName, String requestProperties, String endPointURL)
 *  
 *  
 *  
 *  -------------------------------------------
 *  PUBLIC METHODS
 *  -------------------------------------------
 *  	public void setUp (String strToBeAppeneded, String wsdlWithPath, String env)
 *  
 * 
 */

package common;

import java.io.File;

import org.testng.xml.dom.DomXmlParser;

import common.DOMXmlParser;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.StandaloneSoapUICore;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.iface.Interface;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.settings.SSLSettings;

public class SoapProject 
{
	private WsdlProject project;
	private WsdlRequest request;
	private WsdlOperation operation;
	private DOMXmlParser dom = new DOMXmlParser();
	
	 // ===================
	 //  Constructors 
	 //	=================== 
	
	/** 
	 * simple constructor call initializes project object of the soapProject object
	 */
	public SoapProject() {
		try {project = new WsdlProject();} 
		catch (Exception e) {e.printStackTrace();}
		SoapUI.setSoapUICore(new StandaloneSoapUICore(true));
	}
	
	/** constructor call for running on jenkins while creating project object with file and no keystore values
	 *  @param importPath	path of import file ((Name is enough if stored in build folder)
	 */
	public SoapProject (String importPath){
		this (importPath, false);
	}
	
	/** constructor call for running on local machine while creating project object with file and no keystore values
	 * 	@param importPath		path of import file ((Name is enough if stored in build folder)
	 * 	@param local			true if running on local 
	 */
	public SoapProject (String importPath, boolean local) {
		this (importPath, "", "", local);
	}	
	
	/** constructor call for creating project object with xml or wsdl file
	 *  Keystore value can be set before or after importProject / importWsdl
	 * 	@param importPath		path of import file (Name is enough if stored in build folder)
	 * 	@param KeyStore			Keystore value
	 * 	@param KeyStorePwd		Keystore password
	 */
	public SoapProject (String importPath, String KeyStore, String KeyStorePwd) {
		this (importPath, KeyStore, KeyStorePwd, false);
	}
	
	/** constructor call for running on local machine while creating project object with file and requiring keystore values
	 * 	@param importPath		path of import file (Name is enough if stored in build folder)
	 * 	@param KeyStore			Keystore value
	 * 	@param KeyStorePwd		Keystore password
	 * 	@param local			true if running on local 
	 */
	public SoapProject (String importPath, String KeyStore, String KeyStorePwd, boolean local) {
		String finalPath = (local) ? "build//"+importPath : importPath;
		KeyStore = (local) ? "build//"+KeyStore : KeyStore;
		SoapUI.setSoapUICore(new StandaloneSoapUICore(true));
		System.out.println("Setting: " + KeyStore + " : " + KeyStorePwd);
		setKeystoreValues(KeyStore, KeyStorePwd);
		try {
			
			if (finalPath.endsWith(".xml")) {
				importProject(finalPath);
			}			
			else if (finalPath.endsWith(".wsdl")) {
				importWsdl(finalPath);
			}
			else throw new IllegalArgumentException("The path ("+ finalPath +") must belong to a .xml file or a .wsdl file only");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	 // =========================================
	 //  Private Called from inside Constructors 
	 //	========================================== 
	
	
	/** import existing project to create project object in soap object
	 *  global project variable is initialized to the created project object
	 *  @param projectPath
	 *  @return WsdlProject object
	 */
	private WsdlProject importProject (String projectPath) 
	{ 
		try {
			if (projectPath.endsWith(".xml")) {
				project = new WsdlProject (projectPath);
			}
			else throw new IllegalArgumentException("To import Project the path ("+ projectPath +") must belong to a .xml file.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return project;
	}
	
	/** import wsdl file to create project object in soap object
	 *  global project variable is initialized to the created project object
	 *  @param wsdlPath
	 *  @return WsdlProject object
	 */
	private WsdlProject importWsdl (String wsdlPath) 
	{
		try {
			if (wsdlPath.endsWith(".wsdl")) {
				String wsdlName  = new File(wsdlPath).getName();
				String soapProjectName = wsdlName.replace(".wsdl","-soapui-project.xml");
				
				project = new WsdlProject(new File(soapProjectName).getAbsolutePath());
				WsdlImporter.importWsdl(project, System.getProperty("user.dir") + "\\" + wsdlPath);
			}
			else throw new IllegalArgumentException("To import WSDL the path ("+ wsdlPath +") must belong to a .wsdl file.");
		}
		catch (Exception e)
		{
			Util.printError(e.toString());
		}
		return project;
	}

	/** Set Keystore values from testProperties file if Keystore values are defined
	 *  Can be set before or after creating project as long as set before asking for response
	 *  @param keyStore
	 *  @param keyStorePwd
	 */
	public void setKeystoreValues(String keyStore, String keyStorePwd)
	{
		if((!keyStore.isEmpty() || !keyStore.equalsIgnoreCase("build//")) && !keyStorePwd.isEmpty())
		{
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE, keyStore);
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE_PASSWORD, keyStorePwd);
			Util.printInfo("Keystore Set... ");
		}
	}
	
	/** Get Keystore values set in current SoapUI (StandaloneCore) object
	 *  Displays keystore and password as INFO
	 */
	public void getKeystoreValues()
	{
		Util.printInfo("Keystore: " + SoapUI.getSettings().getString( SSLSettings.KEYSTORE, "") + " : " + SoapUI.getSettings().getString( SSLSettings.KEYSTORE_PASSWORD, ""));
	}
	
	
	
	 // ====================================
	 //  Get Request and Response Functions 
	 //	==================================== 
	
	/** Selects the specified Request for future manipulation
	 * 	@param uniqueOperationOrRequestName		Unique identifier for Request 
	 * 	@return getRequestContent()				Request XML as String
	 */
	public String selectRequest (String uniqueOperationOrRequestName)
	{
		request = getRequest(uniqueOperationOrRequestName);
		if (request == null) {return "";}
		return request.getRequestContent();
	}
	
	/** Selects specified Operation for future manipulation
	 *  @param 	uniqueOperationName		Operation Name or Path in format 'InterfaceName>OperationName'
	 *  @return	true if operation is successfully selected; false otherwise
	 */
	public boolean selectOperation (String uniqueOperationName)
	{
		operation = getExactOperation(uniqueOperationName);
		if (operation == null) {return false;}
		return true;
	}
	
	/** Selects Request under Selected Operation by index
	 *  @param index
	 *  @return	request content As String
	 */
	public String selectRequestAt (int index)
	{
		request = operation.getRequestAt(index);
		return request.getRequestContent();
	}
	
	/** Creates new Request under existant Operation
	 *  @param parentOperation		Operation under which to create new request
	 *  @param xmlValueOfRequest		Complete Request content in XML format
	 *  @return	true if requestContent is successfully set to non empty value
	 */
	public boolean createNewRequest(String parentOperation, String xmlValueOfRequest)
	{
		if(!selectOperation(parentOperation)){return false;}
		return createNewRequest (xmlValueOfRequest);
	}
	
	/** Creates new Request under selected Operation
	 *  @param xmlValueOfRequest		Complete Request content in XML format
	 *  @return	true if requestContent is successfully set to non empty value
	 */
	public boolean createNewRequest(String xmlValueOfRequest)
	{
		request = operation.addNewRequest("NewRequest");
		request.setRequestContent(xmlValueOfRequest);
		return (!getRequestContent().isEmpty());
	}
	
	/** Returns Request content of Selected/Global request object in SoapProject object
	 *  @return		XML content of Request
	 */
	public String getRequestContent () 	{
		return request.getRequestContent();
	}
	
	/** Gets the response for the selected / global Request object
	 * 	@return			XML Response As String 
	 */
	public String getResponse()
	{
		return getResponse(request);
	}
	
	/** Returns the Actual request Object specified
	 *  @param uniqueNameOrPath
	 *  @return WsdlRequest object
	 */
	private WsdlRequest getRequest (String uniqueNameOrPath)
	{
		String path = getExactRequestPath(uniqueNameOrPath);
		if (path.isEmpty()) { return null; }
		String [] pathNames = path.split(">");
		return (WsdlRequest) project.getInterfaceByName(pathNames[0]).getOperationByName(pathNames[1]).getRequestByName(pathNames[2]);
	}
	
	/** Returns WsdlOperation object 
	 *  @param UniqueNameOrPath
	 *  @return WsdlOperation
	 */
	private WsdlOperation getExactOperation(String UniqueNameOrPath)
	{
		String [] pathNames = UniqueNameOrPath.split(">");
		String returnName = "";
		int numFound = 0;
		int numSplit = pathNames.length;
		
		try {
			if(numSplit == 2) {returnName = UniqueNameOrPath; numFound++;}
			else if(numSplit == 1) {
				for (Interface eachInterface : project.getInterfaceList()) {
					try {
						if (eachInterface.getOperationByName(UniqueNameOrPath) != null) {
							returnName = eachInterface.getName() + ">" + UniqueNameOrPath;
							numFound++; }
					} catch (NullPointerException npe) {}
			}	}
			else throw new IllegalArgumentException("Provided Path ('" + UniqueNameOrPath + "') cannot have more specifications than Interface > Operation");
		
			
			if (numFound == 0) throw new IllegalArgumentException("Unable to find Operation at the provided path '" + UniqueNameOrPath + "'. Path not existant or Insuffient information provided.");
			else if (numFound > 1) throw new IllegalArgumentException("Unable to find Operation at the provided path '" + UniqueNameOrPath + "'. More than 1 Operation of same name in Project.");
		} 
		catch (IllegalArgumentException e) {returnName = ""; e.printStackTrace();}
		
		String [] finalPathNames = returnName.split(">");
		if(returnName.isEmpty()) {return null;}
		else return (WsdlOperation) project.getInterfaceByName(finalPathNames[0]).getOperationByName(finalPathNames[1]);
	}
	
	/** Returns exact 3 part Request Path
	 *  @param UniqueNameOrPath
	 *  @return Path of Request As String (Interface>Operation>Request)
	 */
	private String getExactRequestPath(String UniqueNameOrPath)
	{
		String [] pathNames = UniqueNameOrPath.split(">");
		String returnName = "";
		int numFound = 0;
		int numSplit = pathNames.length;
		
		try {
			if(numSplit == 3) {returnName = UniqueNameOrPath; numFound++;}
			else if(numSplit == 2) {
				// UniqueNameOrPath ([0] > [1]) > Request 1
				try {
					if (project.getInterfaceByName(pathNames[0]).getOperationByName(pathNames[1]).getRequestCount() == 1) {
						returnName = UniqueNameOrPath + ">" + project.getInterfaceByName(pathNames[0]).getOperationByName(pathNames[1]).getRequestAt(0).getName();
						numFound++; }
				} catch (NullPointerException npe) {}
				// Interface > UniqueNameOrPath ([0] > [1])
				for (Interface eachInterface : project.getInterfaceList()) {
					try {
						if (eachInterface.getOperationByName(pathNames[0]).getRequestByName(pathNames[1]) != null) {
							returnName = eachInterface.getName() + ">" + UniqueNameOrPath;
							numFound++; }
					} catch (NullPointerException npe) {}
			}	}
			else if(numSplit == 1) {
				for (Interface eachInterface : project.getInterfaceList()) {
					//UniqueNameOrPath (Operation) > Request 1
					try {
						if (eachInterface.getOperationByName(UniqueNameOrPath).getRequestList().size() == 1) {
							returnName = eachInterface.getName() + ">" + UniqueNameOrPath + ">" + eachInterface.getOperationByName(UniqueNameOrPath).getRequestAt(0).getName();
							numFound++; }
					} catch (NullPointerException npe) {}
					
					//Something > UniqueNameOrPath (Request)
					for (Operation eachOperation : eachInterface.getOperationList()) {
						if (eachOperation.getRequestByName(UniqueNameOrPath) != null) {
							returnName = eachInterface.getName() + ">" + eachOperation.getName() + ">" + UniqueNameOrPath;
							numFound++;
			}	}	}	}
			else throw new IllegalArgumentException("Provided Path ('" + UniqueNameOrPath + "') cannot have more specifications than Interface > Operation > Request");
		
			
			if (numFound == 0) throw new IllegalArgumentException("Unable to find Request at the provided path '" + UniqueNameOrPath + "'. Path not existant or Insuffient information provided.");
			else if (numFound > 1) throw new IllegalArgumentException("Unable to find Request at the provided path '" + UniqueNameOrPath + "'. Path insufficiently unique.");
		} 
		catch (IllegalArgumentException e) {returnName = ""; e.printStackTrace();}
		return returnName;
	}
	
	/**	Returns response for Request parameter passed
	 * @param request
	 * @return xmlResponse As String
	 */
	private String getResponse(WsdlRequest request)
	{
		String xmlResponse = null;
		try
		{
			WsdlSubmitContext wsdlSubmitContext = new WsdlSubmitContext(request);
			WsdlSubmit<?> submit = (WsdlSubmit<?>) request.submit(wsdlSubmitContext, false);
			xmlResponse = submit.getResponse().getContentAsXml();
		}
		catch (Exception e) { e.printStackTrace(); }
		return xmlResponse;
	}
	
	
	 // =========================================
	 //  Display Heirarchy Functions 
	 //	========================================== 
	
	/**	Print heirarchy as seen in project below item passed as parameter
	 * 	@param thisItem 		can be project, interface or operation. 
	 * 							(Request can be passed, but request has no children
	 */
	public void printChildren (ModelItem thisItem) {	
		printChildren(thisItem, 0);
	}
	
	private void printChildren (ModelItem thisItem, int count)
	{
		for (ModelItem aChild : thisItem.getChildren())
		{
			for (int i=0;i<count;i++) {System.out.print("    ");}
			System.out.println(aChild.getName());
			printChildren (aChild, count+1);
		}
	}
	
	 // ===============================================
	 //  Editing / Getting info from Request / Response
	 //	===============================================
	
	/** Returns value of specified Node in specified XML
	 *  @param nodeName			Must include number in addition to name eg. "0:SerialNumber" not "SerialNumber"
	 *  @param xmlAsString		The xml in which to look for nodeName
	 *  @return noeValue	As String
	 */
	public String getNodeValue (String nodeName, String xmlAsString)
	{
		String nodeValue = null;
		String nodeStart = "<ns" +nodeName+ ">";
		String nodeEnd = "</ns" +nodeName+ ">";
		try
			{nodeValue = xmlAsString.substring(xmlAsString.indexOf(nodeStart)+nodeStart.length(), xmlAsString.indexOf(nodeEnd));}
		catch (Exception e)
			{Util.printError("Node ns" + nodeName + " not present in xml" );}
		
		return nodeValue;
	}
	
	/**	Replaces specified node with specified value in the soap/global request object of the SoapProject object  
	 * 	@param nodeName		Must include number in addition to name eg. "0:SerialNumber" not "SerialNumber"
	 * 	@param nodeValue	Value to be set
	 * 	@return				Updated Request Content
	 */
	public String setNodeValue (String nodeName, String nodeValue)
	{	
		String requestContent = getRequestContent();
		String oldNodeCode = "<ns" +nodeName+ ">.*</ns" +nodeName+ ">";
		String newNodeCode = "<ns" +nodeName+ ">" +nodeValue+ "</ns" +nodeName+ ">";
		request.setRequestContent(requestContent.replaceFirst(oldNodeCode, newNodeCode));
		return request.getRequestContent();
	}
	
	/** Sets endpoint URL for the request object of the instance of SoapProject object
	 *  @param endPointURL
	 */
	public void setEndpoint (String endPointURL)
	{
		request.setEndpoint(endPointURL);
	}
	
	/** gets endpoint URL for the request object of the instance of SoapProject object
	 *  @param display		boolean true if need endpoint to be displayed in format "INFO: *** [timestamp] Endpoint URL: [endpoint URL]
	 *  @return endPointUrl As String
	 */
	public String getEndpoint (boolean display)
	{
		String endPoint = request.getEndpoint();
		if(display) {Util.printInfo("Endpoint URL: " + endPoint);}
		return endPoint;
	}
	
	/** gets endpoint URL for the request object of the instance of SoapProject object
	 *  @return endPointUrl As String
	 */
	public String getEndpoint() {
		return getEndpoint(false);
	}
}
