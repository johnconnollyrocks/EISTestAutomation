package common;

import java.util.List;

/**
 * Defines required methods for classes that define instances of FieldData_ objects.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 * @see FieldData_
 */
public interface FieldDataInstance_ extends Comparable<FieldDataInstance_>{

  public abstract Object clone();

  public abstract FieldData_ getFieldData();

  public abstract int getInstance();

  public abstract String getFieldName();

  public abstract String getValue();

  public abstract void setValue(String value);

  public abstract String getValueParm();

  public abstract void setValueParm(String valueParm);

  public abstract boolean isVerificationData();

  public abstract EISConstants.ParameterizedVerificationDataValueType getParameterizedVerificationDataValueType();

  public abstract boolean isValueParmASet();

  public abstract List<String> getValueSet();

  public abstract int compareTo(FieldDataInstance_ anotherFieldDataInstance);

  public abstract String toString();

}