package bornincloud;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * Representation of json object for Test Data
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class TestData {
	 
	private String httpRequest;
	private String httpRequest_Offering;
	private String jsonMimeType;
	private String mutualAuthCert;
	private String mutualAuthPass;
	private String mutualAuthCertBuuc;
	private String mutualAuthPassBuuc;
	private String buucRequest;
	private String ippRequest;
	private String payPortRequest;
	private String ccurl;
	private List<TestParameters> testParameters;
    
	
	public void setTestParameters(List<TestParameters> testParameters) {
		this.testParameters = testParameters;
	}

	public String getHttpRequest() {
	   return httpRequest;
	   
	} 
	
	public String getHttpRequest_Offering() {
		return httpRequest_Offering;
	}
	
	public String getJsonMimeType() {
	    return jsonMimeType;
	} 
	public String getMutualAuthCert() {
	    return mutualAuthCert;
	} 
	public String getMutualAuthCertBuuc() {
	    return mutualAuthCertBuuc;
	} 
	public void setHttpRequest(String httpRequest) {
		this.httpRequest = httpRequest;
	}

	public void setHttpRequest_Offering(String httpRequest_Offering) {
		this.httpRequest_Offering = httpRequest_Offering;
	}

	public void setJsonMimeType(String jsonMimeType) {
		this.jsonMimeType = jsonMimeType;
	}

	public void setMutualAuthCert(String mutualAuthCert) {
		this.mutualAuthCert = mutualAuthCert;
	}

	public void setMutualAuthPass(String mutualAuthPass) {
		this.mutualAuthPass = mutualAuthPass;
	}
	public void setMutualAuthCertBuuc(String mutualAuthCertBuuc) {
		this.mutualAuthCertBuuc = mutualAuthCertBuuc;
	}

	public void setMutualAuthPassBuuc(String mutualAuthPassBuuc) {
		this.mutualAuthPassBuuc = mutualAuthPassBuuc;
	}

	public void setBuucRequest(String buucRequest) {
		this.buucRequest = buucRequest;
	}

	public void setIppRequest(String ippRequest) {
		this.ippRequest = ippRequest;
	}
	
	public void setPayPortRequest(String payPortRequest) {
		this.payPortRequest = payPortRequest;
	}
	
	public void setCcurl(String ccurl) {
		this.ccurl = ccurl;
	}

	public String getMutualAuthPass() {
	    return mutualAuthPass;
	} 
	public String getMutualAuthPassBuuc() {
	    return mutualAuthPassBuuc;
	} 
	public List<TestParameters> getTestParameters() {
	    return testParameters;
	}
	
	public String getBuucRequest() {
	    return buucRequest;
	} 
	public String getIppRequest() {
	    return ippRequest;
	} 
	
	public String getPayPortRequest() {
		return payPortRequest;
	} 
	
	public String getCcurl() {
	    return ccurl;
	} 
	
}

