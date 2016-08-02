package bornincloud;

/**
 * Representation of FeatureList inside Offering Json Object for Bic
 * 
 * @author Brijesh Chavda
 * @version 1.0.0
 */
public class FeatureList {

	private String offeringId;
	private String featureId;
	private String featureName;
	private String featureType;
	private String featureNumDays;
	private String featureLicensingModel;
	private String parentFeatureId;
	private Boolean featureAddToEntsTable;	
	private String qty;

	public String getOfferingId() {
		return offeringId;
	}

	public String getFeatureId() {
		return featureId;
	}

	public String getFeatureName() {
		return featureName;
	}

	public String getFeatureType() {
		return featureType;
	}

	public String getFeatureNumDays() {
		return featureNumDays;
	}

	public String getFeatureLicensingModel() {
		return featureLicensingModel;
	}

	public String getParentFeatureId() {
		return parentFeatureId;
	}

	public Boolean getFeatureAddToEntsTable() {
		return featureAddToEntsTable;
	}
	public String getQty() {
		return qty;
	}

}
