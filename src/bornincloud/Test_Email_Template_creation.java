package bornincloud;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.*;

import javax.mail.*;
import javax.mail.search.FlagTerm;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test class - Need add new name
 * 
 * US1967	Webservice for Email Creation dd 8/11
 * 
 * @author Denis Disciuc
 * @version 1.0.0
 */
public class Test_Email_Template_creation extends BornInCloudTestBase {
	private String connectionMail ="";
	private String connectionPass ="";
	private String fromName = "";
	private String userID="";
	private String templateID ="";
	private String addressFrom="";
	private String toName="";
	private String replayname="";
	private String ccaddress1="";
	private String ccaddress2="";
	private String[] template_ID = null;
	private Map<String, String> testDataMap = null;
	private Map<String, String> inputs;
	private Map<String, String> outputs;
	private Folder inbox = null;
	
	public Test_Email_Template_creation() throws IOException {
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Before
	public void oneTimeSetUp() throws IOException {
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		testDataMap = parseTestData(testdata, tname.getMethodName());
		inputs = new HashMap<String, String>();
		userID = testDataMap.get("userId").split("##")[0];
		connectionMail = testDataMap.get("userId").split("##")[1];
		connectionPass =testDataMap.get("userId").split("##")[2];
		fromName = testDataMap.get("userId").split("##")[3];
		template_ID = testDataMap.get("templateId").split(",");
	 }
	 
	@Test
	public void Test_Email_Template_creation_Method() throws Exception{
		deleteMessages(createReadConnection());
		//deleteMessages(createReadConnection());
		sendMails();
		verifyData();
	}
	
	@Test
	public void Test_Email_Template_creation_with_UserID_Method() throws Exception{
		deleteMessages(createReadConnection());
		//deleteMessages(createReadConnection());
		sendMails();
		verifyData();
	}
	
	private void sendMails() throws Exception{
		for(int i = 0; i< template_ID.length; i++){
			Thread.sleep(2000);
			String actualRequestJson=getStringAfterReplacingData(testDataMap.get("content"), template_ID[i].split("#")[0]);
			System.out.println("#" + i + " Template:"+ template_ID[i].split("#")[0] + " Request: " +actualRequestJson );
			inputs.put(templateID, addressFrom + "@autodesk.com##"+ 
									  toName +" <"+ connectionMail +">##"+ 
									  replayname +"@company.com##" + 
									  ccaddress1 +"@example.com##" + 
									  ccaddress2 +"@example.com##" +
									  template_ID[i].split("#")[1] +"##" +
									  template_ID[i].split("#")[2]
					   );
			HttpResponse response=null;
			int count = 0;
			do {
				HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
				Thread.sleep(5000);
				response = createEntitlement(client, testDataMap.get("tempURL"), "", actualRequestJson , testDataMap.get("jsonMimetype"));
				count++;
				client.getConnectionManager().shutdown();
			} while (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK && count < 10);
		
			compareStrings("Assert : Sent mail. Verify the Status Code", new Integer(response.getStatusLine().getStatusCode()).toString(), new Integer(HttpStatus.SC_OK).toString());
			    
			compareStrings("Assert : Sent mail. Verify the Repsonse Format", testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
		
			String actualjsondata = (readJsonFromResponse(response));
	    	containStrings("Assert : Sent mail. Verifying the Json Response from Rest Create Service", actualjsondata, testDataMap.get("expectedjsondata").replaceAll(" ", ""));
	    	
		    System.out.println("");
		}
	}
	
	public void verifyData() throws Exception{
		 Thread.sleep(5000);
			waitMails();
	        try {
	        	    outputs = new HashMap<String, String>();
	        		Message message[] = createReadConnection();
	        		for(int i = 0; i<=message.length-1 ;i++)
	                {
	        		   outputs.put(message[i].getFrom()[0].toString().split(" ")[1] , 
								   message[i].getFrom()[0].toString().split("[<>]")[1] + "##" + 
								   message[i].getRecipients(Message.RecipientType.TO)[0].toString() + "##" +
								   message[i].getReplyTo()[0].toString() + "##" +
								   message[i].getRecipients(Message.RecipientType.CC)[0].toString() + "##" +
								   message[i].getRecipients(Message.RecipientType.CC)[1].toString() + "##" +
								   message[i].getContent().toString().replaceAll("<.*?>", "").
																	  replaceAll("\\t", "").
																	  replaceAll("\\r\\n", "") + "##" +
								   message[i].getSubject().toString()
								   );
	                }
	        	    
	       		 String missedTemplate = "";
	       		 Iterator<Entry<String, String>> entries3 = inputs.entrySet().iterator();
		   		 while (entries3.hasNext()) {
		   			 Entry<String, String> entry = entries3.next();
		   			 
		   			if(!outputs.containsKey(entry.getKey())){
		   				missedTemplate += entry.getKey() + ",";
		   				continue;
		   			}
		   			String[] inputsTemp = entry.getValue().split("##");
		   			String[] ouputsTemp = outputs.get(entry.getKey()).split("##");
		   			System.out.println("Template:"+ entry.getKey() + "\n");
		   			
		   			compareStrings("Assert : Read mail. Verify the FROM address", inputsTemp[0].toLowerCase(), ouputsTemp[0].toLowerCase());
		   			
		   			compareStrings("Assert : Read mail. Verify the TO address", inputsTemp[1].toLowerCase(), ouputsTemp[1].toLowerCase());
		   			
		   			compareStrings("Assert : Read mail. Verify the REPLAY TO address", inputsTemp[2].toLowerCase(), ouputsTemp[2].toLowerCase());
		   			
		   			compareStrings("Assert : Read mail. Verify the CC 1 address", inputsTemp[3].toLowerCase(), ouputsTemp[3].toLowerCase());
		   			
		   			compareStrings("Assert : Read mail. Verify the CC 2 address", inputsTemp[4], ouputsTemp[4]);
		   			
		   			containStrings("Assert : Read mail. body caption", ouputsTemp[5], inputsTemp[5]);
		   			
		   			containStrings("Assert : Read mail. Mail header caption", ouputsTemp[6], inputsTemp[6]);
		   		 }
		   		containStrings("Check if all templates tested", "", missedTemplate);
		   		
	        } catch (Exception mex) {
	            mex.printStackTrace();
	        }
	}

	public String getStringAfterReplacingData(String string, String template_ID){
		templateID=template_ID;
		addressFrom=getUniqueString(6);
		replayname=getUniqueString(6);
		ccaddress1=getUniqueString(6);
		ccaddress2=getUniqueString(6);
		
		if(string.contains("#USERID#"))
			toName = fromName;
		else
			toName = getUniqueString(6);
		
		String temp1=string.replace("#ADDRESS#", addressFrom);
		string=temp1.replace("#CCADDRESS1#", ccaddress1);
		temp1=string.replace("#REPLAYNAME#", replayname);
		string=temp1.replace("#TEMPLATEID#", templateID);
		temp1=string.replace("#CCADDRESS2#", ccaddress2);
		string=temp1.replace("#TONAME#", toName);
		temp1=string.replace("#NAME#", templateID);
		string=temp1.replace("#USERID#", userID);
		return string;
	}
	
	public Message[] createReadConnection(){
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Message message[]=null;
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.secureserver.net", connectionMail, connectionPass);
            inbox = store.getFolder("Webservice for Email Creation");
            inbox.open(Folder.READ_ONLY);
            FlagTerm ft=new FlagTerm(new Flags(Flags.Flag.RECENT),false);
            message=inbox.search(ft);
            
        } catch (Exception mex) {
            mex.printStackTrace();
        }
        return message;
	}
	
	 public void deleteMessages(Message[] message) {
         try {
         	 for (Message msg : message) {
 				msg.setFlag(Flags.Flag.DELETED, true);
 			}
         	inbox.close(true);
         } catch (MessagingException e) {
             e.printStackTrace();
         }
     }
	 
	 private boolean waitMails() throws Exception{
		 int count =0;
		 do {
			 Thread.sleep(5000);
			 count++;
		 } while (createReadConnection().length != inputs.size() && count < 10);
		 
		 return count >= 10;
	 }
}
