package bornincloud;

/**
 * Representation of Entitlements inside the Json response of BIC REST Calls
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public class Entitlements {
    
   private String userId;
   private String entitlementId;
   private String contextId;
   private String entitlementStartDate;
   private String entitlementEndDate;   
   private String offeringId;   
   private String offeringLegalName;
   private String offeringTemplateId;   
   private String parentFeatureId;
   private String featureId;
   private String featureName;
   private String featureType;   
   private String featureLicensingModel;
   private String remainingTrialDays;   
   private Boolean active;
   private String dateLastModified;   
   
   public String getUserId(){
	   return userId;
   }
   public String getEntitlementId(){
	   return entitlementId;
   }
   public String getContextId(){
	   return contextId;
   }
   public String getEntitlementStartDate(){
	   return entitlementStartDate;
   }
   public String getEntitlementEndDate(){
	   return entitlementEndDate;
   }
   public String getOfferingId(){
	   return offeringId;
   }
   public String getOfferingLegalName(){
	   return offeringLegalName;
   }
   public String getOfferingTemplateId(){
	   return offeringTemplateId;
   }
   public String getParentFeatureId(){
	   return parentFeatureId;
   }
   public String getFeatureId(){
	   return featureId;
   }
   public String getFeatureName(){
	   return featureName;
   }
   public String getFeatureType(){
	   return featureType;
   }
   public String getFeatureLicensingModel(){
	   return featureLicensingModel;
   }
   public String getRemainingTrialDays(){
	   return remainingTrialDays;
   }
   public Boolean getactive(){
	   return active;
   }
   public String getDateLastModified(){
	   return dateLastModified;
   }
}
