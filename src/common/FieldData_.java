package common;

import java.util.List;

/**
 * Defines required methods for classes that define test data and verification data objects.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public interface FieldData_ {

  public abstract Object clone();

  public abstract String getFieldName();

  public abstract String getName();

  public abstract String getValue();

  public abstract void setValue(String value);

  public abstract String getValueParm();

  public abstract void setValueParm(String valueParm);
  
  public abstract boolean isVerificationData();

  public abstract EISConstants.ParameterizedVerificationDataValueType getParameterizedVerificationDataValueType();

  public abstract boolean isValueParmASet();

  public abstract List<String> getValueSet();

  public abstract String toString();
}