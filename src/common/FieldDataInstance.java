package common;

import java.util.List;

/**
 * Representation of an instance of a FieldData object.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 * @see FieldData
 */
final class FieldDataInstance implements FieldDataInstance_ {
	private FieldData_ fieldData;
	private int instance;

	public FieldDataInstance(FieldData_ fieldData, int instance) {
		this.fieldData = fieldData;
		this.instance = instance;
	}

	public FieldDataInstance(FieldData_ fieldData) {
		this(fieldData, 1);
	}

	//Copy constructor.  Convenient (as an alternative to clone()), when
	//  other class's get and clone methods need a copy of this class
	public FieldDataInstance(FieldDataInstance_ aFieldDataInstance) {
		this(aFieldDataInstance.getFieldData(), aFieldDataInstance.getInstance());
	}

	@Override
	public Object clone() {
		FieldDataInstance_ cloned = null;

		try {
			cloned = ((FieldDataInstance_) super.clone());
		} catch (CloneNotSupportedException ce) {
			cloned = new FieldDataInstance(getFieldData(), getInstance());
		}

		return cloned;
	}

	@Override
	public FieldData_ getFieldData() {
		return new FieldData(fieldData);
	}

	@Override
	public int getInstance() {
		return instance;
	}

	@Override
	public String getFieldName() {
		return fieldData.getFieldName();
	}

	@Override
	public String getValue() {
		return fieldData.getValue();
	}

	@Override
	public void setValue(String value) {
		fieldData.setValue(value);
	}
	
	@Override
	public String getValueParm() {
		return fieldData.getValueParm();
	}

	@Override
	public void setValueParm(String valueParm) {
		fieldData.setValueParm(valueParm);
	}

	@Override
	public boolean isVerificationData() {
		return fieldData.isVerificationData();
	}

	@Override
	public EISConstants.ParameterizedVerificationDataValueType getParameterizedVerificationDataValueType() {
		return fieldData.getParameterizedVerificationDataValueType();
	}
	
	@Override
	public boolean isValueParmASet() {
		return fieldData.isValueParmASet();
	}

	@Override
	public List<String> getValueSet() {
		return fieldData.getValueSet();
	}
	
	@Override
	public int compareTo(FieldDataInstance_ anotherFieldDataInstance) {
		return instance - (anotherFieldDataInstance).getInstance();    
	}

	@Override
	public String toString() {
		return "FieldDataInstance [fieldData=" + fieldData.toString() + ", instance="
		+ instance + "]";
	}
}
