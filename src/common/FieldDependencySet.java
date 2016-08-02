package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representation of a set of prerequisite and dependent Field objects, used for enforcing
 * the order in which Field objects are accessible on a page.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
final class FieldDependencySet {
	private String prerequisiteField;
	private List<String> dependentFields = new ArrayList<String>();
	
	public FieldDependencySet(String prerequisiteField, List<String> dependentFields) {
		this.prerequisiteField	= prerequisiteField;
		//this.dependentFields 	= Util.listOfStringTrim(dependentFields);
		this.dependentFields 	= new ArrayList<String>(Util.listOfStringTrim(dependentFields));
	}

	public FieldDependencySet(String prerequisiteField, String[] dependentFields) {
		this(prerequisiteField, Arrays.asList(dependentFields));
	}

	public FieldDependencySet(String prerequisiteField, String dependentFields) {
		this(prerequisiteField, dependentFields.split(EISConstants.FIELD_DEPENDENCY_DELIM));
	}

	//Copy constructor.  Convenient (as an alternative to clone()), when
	//  other class's get and clone methods need a copy of this class
	public FieldDependencySet(FieldDependencySet aFieldDependencySet) {
		this(aFieldDependencySet.getPrerequisiteField(), aFieldDependencySet.getDependentFields());
	}

	@Override
	public Object clone() {
		FieldDependencySet cloned = null;
		
		try {
			cloned = ((FieldDependencySet) super.clone());
		} catch (CloneNotSupportedException ce) {
			cloned = new FieldDependencySet(prerequisiteField, new ArrayList<String>(dependentFields));
		}

		return cloned;
	}

	public String getPrerequisiteField() {
		return prerequisiteField;
	}

	public List<String> getDependentFields() {
		return new ArrayList<String>(dependentFields);
	}

	public void setDependentFields(List<String> dependentFields) {
		this.dependentFields = new ArrayList<String>(dependentFields);
	}

	@Override
	public String toString() {
		return "FieldDependencySet [prerequisiteField=" + prerequisiteField
				+ ", dependentFields=" + dependentFields + "]";
	}
}
