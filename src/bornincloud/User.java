package bornincloud;

import java.util.List;

/**
 * Representation of json response of rest api for Bic
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public class User {
	    
   public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public void setEntitlements(List<Entitlements> entitlements) {
		this.entitlements = entitlements;
	}
private String statusCode;
   private String statusMessage;
   private List<Entitlements> entitlements;
    
   public String getStatusCode() {
       return statusCode;
   }
    public String getStatusMessage() {
       return statusMessage;
   }    
    public List<Entitlements> getEntitlements() {
        return entitlements;
    }
}
