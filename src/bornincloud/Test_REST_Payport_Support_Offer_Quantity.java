package bornincloud;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * 
 * US2579	DEF: Payport: Support Offer Quantity DD(9/17) Testing (9/18)
 * Acceptance: Calculation of price based on quantity is incorrect
 * 
 * @author Denis Disciuc
 * @version 1.0.0
 */


public class Test_REST_Payport_Support_Offer_Quantity  extends BornInCloudTestBase {
	
	public Test_REST_Payport_Support_Offer_Quantity() throws IOException {
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public TestName tname = new TestName();
	
	@Test
	public void Test_REST_Payport_Support_Offer_Quantity_Method() throws Exception{
		System.out.println(" ---------- Method:" + tname.getMethodName() + " ---------- \n");
		String testdata = readJsonFromoffering(System.getProperty("testdatafile"));
		Map<String,String> testDataMap = parseTestData(testdata, tname.getMethodName());
		
		testt(testDataMap, testDataMap.get("offTable1").split("##")[0]);
		testt(testDataMap, testDataMap.get("offTable1").split("##")[1]);
	}
	
	public void testt(Map<String,String> testDataMap, String codeExKey) throws Exception{
		
		String[] sddress = testDataMap.get("offTable2").split("#");
		for(int i=0; i<sddress.length; i++){
			for(int j=1; j <= 5; j++){
				System.out.println("\n********************************************\n");
				String actualRequestJson=getStringAfterReplacingData(j, sddress[i], codeExKey, testDataMap.get("content"));
				/*debug*/ System.out.println("\n\rHTTP:" +  testDataMap.get("ippRequest").split("user")[0]+testDataMap.get("userId"));
				/*debug*/ System.out.println("request:" + actualRequestJson);
				HttpClient client = getClient(testDataMap.get("mutualAuthCert"), testDataMap.get("mutualAuthPass"));
				HttpResponse response = createEntitlement(client, testDataMap.get("ippRequest").split("user")[0], testDataMap.get("userId"), actualRequestJson, testDataMap.get("jsonMimetype"));
				
				compareStrings("Assert : Verify the Status Code", new Integer(response.getStatusLine().getStatusCode()).toString(), new Integer(HttpStatus.SC_CREATED).toString());
				compareStrings("Assert : Verify the Repsonse Format", testDataMap.get("jsonMimetype"), ContentType.getOrDefault(response.getEntity()).getMimeType());
				
				String actualjsondata = (readJsonFromResponse(response));
				/*debug*/ System.out.println("response:" + actualjsondata);
				validate(actualjsondata);
				client.getConnectionManager().shutdown();
			}
		}
	}
	
	public void validate(String actualjsondata){
		double subtotal = Double.parseDouble(takeValue(actualjsondata, "subtotal\":\"(.*?)\""));//subtotal = amount * quantity
		double tax = Double.parseDouble(takeValue(actualjsondata, "tax\":\"(.*?)\""));//tax = taxAmount * quantity
		double taxAmount = Double.parseDouble(takeValue(actualjsondata, "taxAmount\":\"(.*?)\""));//taxAmount = tapeSTATE + tapeCOUNTY
		double total = Double.parseDouble(takeValue(actualjsondata, "\"total\":\"(.*?)\""));//total = subtotal + tax
		
		double amount = Double.parseDouble(takeValue(actualjsondata, "amount\":\"(.*?)\""));
		int quantity = Integer.parseInt(takeValue(actualjsondata, "quantity\":(.*?),"));
		double tapeSTATE = Double.parseDouble(takeValue(actualjsondata, "type\":\"STATE\",\"amount\":\"(.*?)\""));
		String tempStr = takeValue(actualjsondata, "type\":\"COUNTY\",\"amount\":\"(.*?)\"");
		double tapeCOUNTY = (tempStr.equals("") ? 0 : Double.parseDouble(tempStr));
		
		tempStr = takeValue(actualjsondata, "type\":\"SPECIAL\",\"amount\":\"(.*?)\"");
		double tapeSPECIAL = (tempStr.equals("") ? 0 : Double.parseDouble(tempStr));
		
		tempStr = takeValue(actualjsondata, "type\":\"CITY\",\"amount\":\"(.*?)\"");
		double tapeCITY = (tempStr.equals("") ? 0 : Double.parseDouble(tempStr));
		
		/*debug*/ System.out.println("subtotal	" + subtotal);
		/*debug*/ System.out.println("tax	" + tax);
		/*debug*/ System.out.println("taxAmount	" + taxAmount);
		/*debug*/ System.out.println("total	" + total);
		/*debug*/ System.out.println("amount	" + amount);
		/*debug*/ System.out.println("quantity	" + quantity);
		/*debug*/ System.out.println("tapeSTATE	" + tapeSTATE);
		/*debug*/ System.out.println("tapeCOUNTY	" + tapeCOUNTY);
		/*debug*/ System.out.println("tapeSPECIAL	" + tapeSPECIAL);
		/*debug*/ System.out.println("tapeCITY	" + tapeCITY);
		
		
		compareObjects("Assert : Verify Subtotal, ", (float)subtotal, (float)(amount * quantity));
		compareObjects("Assert : Verify Tax, ", (float)tax, (float)(taxAmount * quantity));
		
		compareObjects("Assert : Verify TaxAmount, ", (float)taxAmount, (float)(tapeSTATE + tapeCOUNTY + tapeSPECIAL + tapeCITY));
		compareObjects("Assert : Verify Total, ", (float)total, (float)(subtotal + tax));
		compareObjects("Assert : Verify taxAmount is no null, ", true, ((float)taxAmount != 0.0f));
		
	}
	
	public String takeValue(String actualjsondata, String patteern){
		Pattern pattern = Pattern.compile(patteern);
		Matcher matcher = pattern.matcher(actualjsondata);

		String nameStr="";
		if(matcher.find())
		{
		    nameStr=matcher.group(1);
		}
		return nameStr;
	}
	
	public String getStringAfterReplacingData(int j, String sddress, String codeExKey, String string){
		/*debug*/ System.out.println("EXTERNAL_KEY	" + codeExKey.split(",")[1]);
		/*debug*/ System.out.println("TAX_CODE	" + codeExKey.split(",")[0]);
		/*debug*/ System.out.println("AMOUNT	" + codeExKey.split(",")[2]);
		/*debug*/ System.out.println("STREET_ADDRESS	" + "1 Main St");
		/*debug*/ System.out.println("CITY	" + sddress.split(",")[0]);
		/*debug*/ System.out.println("STATE_PROVINCE	" + sddress.split(",")[1]);
		/*debug*/ System.out.println("COUNTRY	" + sddress.split(",")[2]);
		/*debug*/ System.out.println("POSTAL_CODE	" + sddress.split(",")[3]);
		/*debug*/ System.out.println("QUANTITY	" + j +"");
		
		String temp1=string.replace("#EXTERNAL_KEY#", codeExKey.split(",")[1]);
		string=temp1.replace("#TAX_CODE#", codeExKey.split(",")[0]);
		temp1=string.replace("#STREET_ADDRESS#", "1 Main St");
		string=temp1.replace("#CITY#", sddress.split(",")[0]);
		temp1=string.replace("#STATE_PROVINCE#", sddress.split(",")[1]);
		string=temp1.replace("#COUNTRY#", sddress.split(",")[2]);
		temp1=string.replace("#POSTAL_CODE#", sddress.split(",")[3]);
		string=temp1.replace("#QUANTITY#", j +"");
		temp1=string.replace("#AMOUNT#", codeExKey.split(",")[2]);
		return temp1;
	}
	
}
