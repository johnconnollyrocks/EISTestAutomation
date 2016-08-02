package bornincloud;

/**
 * Representation of Test data for each test inside TestParameters Json Object for Bic
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public class TestParameters {
 
	private String testName;
	private String userId;
	private String expectedJsonData;
	private String expectedJsonDataRefund;
	private String contextId;
	private String content;
	private String httpUrl;
	private String offTable1;
	private String offTable2;
	private String ippURL;
	private String emailAddress;
	private String offeringId;
	private String country;
	private String lang;
	private String transactionId;
	private String contract;
	private String grantToken;
	private String subId;
	private String tempURL;
	public String getTempURL() {
		return tempURL;
	}
	public void setTempURL(String URL) {
		this.tempURL = URL;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	private String templateId;
	
	public String getOfferingId() {
		return offeringId;
	}
	public String getTestName() {
		return testName;
	}
	

	public String getUserId() {
		return userId;
	}
	
	
	public String getExpectedJsonData() {
		return expectedJsonData;
	}
	
	public String getExpectedJsonDataRefund() {
		return expectedJsonDataRefund;
	}

	public String getContextId() {
		return contextId;
	}

	public String getContent() {
		return content;
	}
	
	public String getHttpUrl() {
		return httpUrl;
	}
	
	public String getOffTable1() {
		return offTable1;
	}
	
	public String getOffTable2() {
		return offTable2;
	}
	
	public String getIppURL() {
		return ippURL;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getLang() {
		return lang;
	}
	public String getTransactionId() {
		return transactionId;
	}
	
	public String getContract() {
		return contract;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setExpectedJsonData(String expectedJsonData) {
		this.expectedJsonData = expectedJsonData;
	}
	public void setExpectedJsonDataRefund(String expectedJsonDataRefund) {
		this.expectedJsonDataRefund = expectedJsonDataRefund;
	}
	public void setContextId(String contextId) {
		this.contextId = contextId;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public void setOffTable1(String offTable1) {
		this.offTable1 = offTable1;
	}
	public void setOffTable2(String offTable2) {
		this.offTable2 = offTable2;
	}
	public void setIppURL(String ippURL) {
		this.ippURL = ippURL;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getGrantToken() {
		return grantToken;
	}
	public void setGrantToken(String grantToken) {
		this.grantToken = grantToken;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
		
		

}

