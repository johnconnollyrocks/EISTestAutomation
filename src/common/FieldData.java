package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representation of a test data or verification data element.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class FieldData implements Cloneable, FieldData_ {
	private String fieldName;	//required (no default)
	private String value;		//required (no default)

	//These are set in parseVerificationDataValue().  Verification methods will make use of this data when
	//  generating expected results 
	private boolean isVerificationData = false;
	private EISConstants.ParameterizedVerificationDataValueType parameterizedVerificationDataValueType;
	private String valueParm = "";
	private boolean isValueParmASet = false;

	//This is set in parseValueSet().  Verification methods will make use of this data when
	//  generating expected results 
	private List<String> valueSet = new ArrayList<String>();

	public FieldData(String fieldName, String value) {
		this.fieldName	= fieldName.trim();
		this.value		= value.trim();

		parseVerificationDataValue();
	}

	//Copy constructor.  Convenient (as an alternative to clone()), when
	//  other class's get and clone methods need a copy of this class
	//  (but not necessary here because this class does not contain
	//  mutable objects)
	public FieldData(FieldData_ aFieldData) {
		this(aFieldData.getFieldName(), aFieldData.getValue());

		this.isVerificationData						= aFieldData.isVerificationData();
		this.parameterizedVerificationDataValueType	= aFieldData.getParameterizedVerificationDataValueType();
		this.valueParm								= aFieldData.getValueParm();
		this.isValueParmASet						= aFieldData.isValueParmASet();
		this.valueSet								= aFieldData.getValueSet();
	}

	@Override
	public Object clone() {
		FieldData_ cloned = null;

		//The super call does not throw an exception
		try {
			cloned = ((FieldData_) super.clone());
		} catch (CloneNotSupportedException ce) {}

		return cloned;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String getName() {
		return getFieldName();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValueParm() {
		return valueParm;
	}

	@Override
	public void setValueParm(String valueParm) {
		this.valueParm =  valueParm;
	}

	@Override
	public boolean isVerificationData() {
		return isVerificationData;
	}

	@Override
	public EISConstants.ParameterizedVerificationDataValueType getParameterizedVerificationDataValueType() {
		return parameterizedVerificationDataValueType;
	}

	@Override
	public boolean isValueParmASet() {
		return isValueParmASet;
	}

	@Override
	public List<String> getValueSet() {
		return new ArrayList<String>(valueSet);
	}

	@Override
	public String toString() {
		return "FieldData ["
		+ "fieldName=                              " + fieldName
		+ ", isVerificationData=                     " + isVerificationData
		+ ", parameterizedVerificationDataValueType= " + parameterizedVerificationDataValueType
		+ ", valueParm=                              " + valueParm
		+ ", value=                                  " + value
		+ ", isValueParmASet=                     	 " + isValueParmASet
		+ ", valueSet=                          	 " + valueSet
		+ "]";
	}

/*	private void parseVerificationDataValue() {
		String regexTerm = ".*" + EISConstants.PARAMETERS_REGEX_TERM;
		String tempType;

		isVerificationData = false;

		if (value.matches(regexTerm)) {
			//We now have text that matches the regex term, but that text is not necessarily a parm type
			//  and a delimited value for verification; it could be part of a "pure value" that just
			//  happens to match the regex.  We'll grab the text to the left of the parms start delimiter,
			//  and see if it is of the type EISConstants.ParameterizedVerificationDataValueType

			tempType = Util.left(value, value.indexOf(EISConstants.PARAMETERS_START_DELIM)).trim().toUpperCase();

			//We're not throwing an exception here, because it is possible that a non-parameterized
			//  value contains characters that match regexTerm
			try {
				parameterizedVerificationDataValueType = EISConstants.ParameterizedVerificationDataValueType.valueOf(tempType);

				isVerificationData = true;
			} catch (IllegalArgumentException e) {}

			if (isVerificationData) {
				//Get the value parm
				valueParm = Util.getField(value, EISConstants.PARAMETERS_START_DELIM, EISConstants.PARAMETERS_END_DELIM);

				if ((parameterizedVerificationDataValueType == EISConstants.ParameterizedVerificationDataValueType.VERIFY_CONSTANT) && (valueParm.isEmpty())) {
					Util.printWarning("The value property of the Field '" + fieldName + "' contains the " + parameterizedVerificationDataValueType + " token, but a constant name was not provided");
				}
			}
		}
	}
*/
	private void parseVerificationDataValue() {
		String regexTerm = ".*" + EISConstants.PARAMETERS_REGEX_TERM;
		String tempType;

		isVerificationData = false;

		if (value.matches(regexTerm)) {
			//We now have text that matches the regex term, but that text is not necessarily a parm type
			//  and a delimited value for verification; it could be part of a "pure value" that just
			//  happens to match the regex.  We'll grab the text to the left of the parms start delimiter,
			//  and see if it is of the type EISConstants.ParameterizedVerificationDataValueType

			tempType = Util.left(value, value.indexOf(EISConstants.PARAMETERS_START_DELIM)).trim().toUpperCase();

			//We're not throwing an exception here, because it is possible that a non-parameterized
			//  value contains characters that match regexTerm
			try {
				parameterizedVerificationDataValueType = EISConstants.ParameterizedVerificationDataValueType.valueOf(tempType);

				isVerificationData = true;
			} catch (IllegalArgumentException e) {}

			if (isVerificationData) {
				//Get the value parm
				valueParm = Util.getField(value, EISConstants.PARAMETERS_START_DELIM, EISConstants.PARAMETERS_END_DELIM);

				switch (parameterizedVerificationDataValueType) {
					case VERIFY_CONSTANT:	{
						if (valueParm.isEmpty()) {
							Util.printWarning("The value property of the Field '" + fieldName + "' contains the " + parameterizedVerificationDataValueType + " token, but a constant name was not provided");
						}
						
						break;
					}
					case VERIFY_VALUES:
					case VERIFY_VALUE_SET:	{
						isValueParmASet = true;
						valueSet = Util.listOfStringTrim(Arrays.asList(valueParm.split(EISConstants.PARAMETER_DELIM)));
						break;
					}
					//It's okay that we are not handling all members of the common.EISConstants.ParameterizedVerificationDataValueType enumerated type
					default:
				}
			}
		}
	}
}
