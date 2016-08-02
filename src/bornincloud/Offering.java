package bornincloud;

import java.util.List;

/**
 * Representation of Offering Json Object for Bic
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public class Offering {
	    
   private String offeringId;
   private String offeringPLC;
   private String offeringLegalName;
   private String offeringShortName;
   private String offeringClass;
   private String offeringTemplate;
   private String offeringStartDate;
   private String offeringEndDate;
   private String offeringBillingRecurrence;
   private String offeringBillingCycle;
   private String offeringUsageType;
   private List<FeatureList> featureList;
      
    
   public String getOfferingId() {
       return offeringId;
   }
    public String getOfferingPLC() {
       return offeringPLC;
   }   
    public String getOfferingLegalName() {
        return offeringLegalName;
    }  
    public String getOfferingShortName() {
        return offeringShortName;
    } 
    public String getOfferingClass() {
        return offeringClass;
    }  
    public String getOfferingTemplate() {
        return offeringTemplate;
    }  
    public String getOfferingStartDate() {
        return offeringStartDate;
    }  
    public String getOfferingEndDate() {
        return offeringEndDate;
    }  
    public String getOfferingBillingRecurrence() {
        return offeringBillingRecurrence;
    }  
    public String getOfferingBillingCycle() {
        return offeringBillingCycle;
    }  
    public String getOfferingUsageType() {
        return offeringUsageType;
    }  
    public List<FeatureList> getFeatureList() {
        return featureList;
    }
}
