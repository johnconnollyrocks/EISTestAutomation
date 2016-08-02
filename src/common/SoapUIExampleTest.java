package common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.eviware.soapui.SoapUI;
import com.eviware.soapui.StandaloneSoapUICore;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProjectPro;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.settings.SSLSettings;

public class SoapUIExampleTest {
	public File projectFile;
	public WsdlProjectPro projectPro;
	

	//SET KEYSTORE FOR WSDL
	private void setKeystore (String wsdlWithPath, String env)
	{
		//For UserService.wsdl DEV keystore is assigned, whether the environment is STG or DEV 
		if (wsdlWithPath.equalsIgnoreCase("GetAgreementBySerialNumber.wsdl") && (env.equalsIgnoreCase("DEV"))){
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE, "AutodeskInternal_DEVQA_exp2016.p12");
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE_PASSWORD, "Adsk2016");
			
		}
		
		else if (wsdlWithPath.equalsIgnoreCase("PartyService.wsdl") && env.equalsIgnoreCase("STG"))
		{	
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE, System.getProperty("user.dir")+"\\DataPower-Portal2.p12");
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE_PASSWORD, "Adsk2016" );
		}
		
		else if (env.equalsIgnoreCase("STG") && !(wsdlWithPath.equalsIgnoreCase("UserService.wsdl") ))
		{	
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE, "cepstg.pfx");
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE_PASSWORD, "yWPjYIfQjVEwHhhjYohKTR3f");
		}
		 
		else if (wsdlWithPath.equalsIgnoreCase("UserService.wsdl") || (env.equalsIgnoreCase("DEV")))
		{
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE, System.getProperty("user.dir")+"\\DataPower-Portal2.p12");
			SoapUI.getSettings().setString( SSLSettings.KEYSTORE_PASSWORD, "Adsk2016" );
		}
		
		//For any other case; ie. env is not STG or DEV and wsdl is also not UserService.wsdl; no keystore will be assigned..
	}
	

	// SETUP
	public void setUp (String strToBeAppeneded, String wsdlWithPath, String env) 
	{
		String soapProjectName = null;
		try 
		{
			File wsdlFile = new File(wsdlWithPath);
			String fileName  = wsdlFile.getName();
			if(fileName.contains("wsdl")){
			soapProjectName = fileName.substring(0, fileName.indexOf(".wsdl"))+"_"+strToBeAppeneded+"-soapui-project.xml";
			}
			
			if(fileName.contains("xml")){
			soapProjectName = fileName.substring(0, fileName.indexOf(".xml"))+"_"+strToBeAppeneded+"-soapui-project.xml";
			}	

			projectFile = new File(soapProjectName);
			SoapUI.setSoapUICore(new StandaloneSoapUICore(true));
			
			setKeystore(wsdlWithPath, env);
			
			projectPro = new WsdlProjectPro(projectFile.getAbsolutePath());
//			projectPro = new WsdlProjectPro(projectPro);
			WsdlInterface[] wsdls = WsdlImporter.importWsdl(projectPro,System.getProperty("user.dir")+"\\"+wsdlWithPath);
//			WsdlInterface[] wsdls = WsdlImporter.importWsdl(projectPro,System.getProperty("user.dir")+"\\"+wsdlWithPath);
			System.out.println("WSDL length:"+wsdls.length);
			
			for (int j = 0; j < wsdls.length; j++)
			{
				WsdlInterface wsdl = wsdls[j];
				String soapVersion = wsdl.getSoapVersion().toString();
				int c = wsdl.getOperationCount();
				String reqContent = "";
				for (int i = 0; i < c; i++)
				{
					
					WsdlOperation op = wsdl.getOperationAt(i);
					String opName = op.getName();
					System.out.println("Op Name:"+opName);
					reqContent = op.createRequest(true);
					WsdlRequest req = op.addNewRequest("Req_" + soapVersion+ "_" + opName);
					System.out.println("Req_" + soapVersion+ "_" + opName);
					req.setRequestContent(reqContent);
				}
			}
			projectPro.saveIn(projectFile);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	
	private String getSoapResult (String methodName, String requestProperties, String endPointURL) 
	{
		String result = "";
		try
		{
			SoapUI.setSoapUICore(new StandaloneSoapUICore(true));
			int c = projectPro.getInterfaceCount();
			for(int i=0;i<c;i++)
			{
				WsdlInterface wsdl = (WsdlInterface) projectPro.getInterfaceAt(i);
				String soapVersion = wsdl.getSoapVersion().toString();
				int opc = wsdl.getOperationCount();
				
				for(int j=0; j<opc; j++)
				{
					WsdlOperation op = wsdl.getOperationAt(j);
					String opName = op.getName();
					if(opName.equals(methodName))
					{
						WsdlRequest req = op.getRequestByName("Req_"+soapVersion+"_"+opName);
						req.setRequestContent(requestProperties);
						req.setEndpoint(endPointURL);
						result = getXMLResponseToRequest(req);
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	private String getXMLResponseToRequest(WsdlRequest request) throws SubmitException
	{
		Response response = getResponseToRequest(request);
		return response.getContentAsXml();
	}
	
	private Response getResponseToRequest(WsdlRequest request) throws SubmitException
	{
		WsdlSubmitContext wsdlSubmitContext = new WsdlSubmitContext(request);
		WsdlSubmit<?> submit = (WsdlSubmit<?>) request.submit(wsdlSubmitContext, false);
		return submit.getResponse();
	}
	

	
	public String getResponseForSoapRequest (
			String strToBeAppeneded,
			String methodName,
			String wsdlWithPath,
			String requestProperties,
			String endPointURL,
			String env)
	{
		setUp(strToBeAppeneded, wsdlWithPath, env);
		return getSoapResult(methodName, requestProperties, endPointURL);
		
	}
	
	
	// Method gloss for Convergent Charging 
	public String getSoapRequestForChargeEvent(
			String strToBeAppeneded,
			String methodName,
			String wsdlWithPath,
			String requestProperties,
			String endPointURL,
			String env) 
	{
		String result = getResponseForSoapRequest (strToBeAppeneded, methodName, wsdlWithPath, requestProperties, endPointURL, env);
		return result;
	}
	
		
	public String getCurrentDate(){
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date yesterday = cal.getTime();
        
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        return (sdf.format(yesterday));
	}

	public String ReplaceGUIDRequest(String request,String userEmail)
	{	
		return request.replace("<Email>?</Email>", "<Email>"+userEmail+"</Email>");
	}
	
	public String ReplaceRequest(String request,String email)
	{	
		return ReplaceGUIDRequest (request, email);
	}
}
