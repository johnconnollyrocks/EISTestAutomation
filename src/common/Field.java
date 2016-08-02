package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import common.exception.MetadataException;

/**
 * Representation of a field (input or read-only) on a page in an SFDC application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class Field implements Cloneable {
	//Field-level metadata, from the page properties file
	private String name;															//required (no default)
	private EISConstants.FieldType type;											//required (no default)
	private List<String> locators = new ArrayList<String>();						//required (no default)
	private EISConstants.RequirednessLevel requirednessLevel = EISConstants.RequirednessLevel.OPTIONAL;	//optional (defaults to EISConstants.RequirednessLevel.OPTIONAL)
	private List<String> messageLocators = new ArrayList<String>();					//optional (defaults to empty)

	//These are set in parseLocators()
	private boolean isLocatorParameterized	= false;
	private EISConstants.ParameterizedLocatorType parameterizedLocatorType = null;
	private List<List<String>> locatorParmsSets = new ArrayList<List<String>>();
	
	private String metadataString;

	public Field(String fieldName, String metadataString) throws MetadataException {
		//We won't fail the test here (by calling EISTestBase.failTest) because the caller
		//  of this constructor (probably a method in Page) will be able to add some
		//  information in the exception message - let's propagate exceptions
		this.name		= fieldName.trim();
		this.metadataString	= metadataString.trim();

		setMetadata();
		
		parseLocators();
	}

	public Field(String fieldName, EISConstants.FieldType type, List<String> locators, EISConstants.RequirednessLevel requirednessLevel, List<String> messageLocators) throws MetadataException {
		this.name			= fieldName.trim();
		this.type				= type;
		this.locators			= locators;
		this.requirednessLevel	= requirednessLevel;
		this.messageLocators	= messageLocators;
		this.metadataString		= "";
		
		parseLocators();
	}
	
	//Copy constructor.  Convenient (as an alternative to clone()), when
	//  other class's get and clone methods need a copy of this class.
	//  Note that even thought the primary constructors throw an exception,
	//  the exception can never occur because the input to this is an
	//  already existing copy, and this class does not have any objects
	//  that can be set by instances.  However, callers still need to
	//  trap it
	public Field(Field aField) throws MetadataException {
		//this(aField.getName(), aField.getMetadataString());
		this(aField.name, aField.getMetadataString());

		this.locators					= aField.getLocators();
		this.messageLocators			= aField.getMessageLocators();
		this.isLocatorParameterized		= aField.isLocatorParameterized();
		this.parameterizedLocatorType	= aField.getParameterizedLocatorType();
		this.locatorParmsSets			= aField.getLocatorParmsSets();
	}

	//This clone method needs to do a deep copy, because many callers that need a real copy
	//  won't call the copy constructor, because they would have to trap the exception it throws
	@Override
	public Object clone() {
		Field cloned = null;

		//The super call does not throw an exception
		try {
			cloned = ((Field) super.clone());

			cloned.locators			= this.getLocators();
			cloned.messageLocators	= this.getMessageLocators();
			cloned.locatorParmsSets	= this.getLocatorParmsSets();
		} catch (CloneNotSupportedException ce) {}

		return cloned;
	}

	public String getName() {
		return name;
	}

	public String getMetadataString() {
		return metadataString;
	}

	public EISConstants.FieldType getType() {
		return type;
	}

	protected List<String> getLocators() {
		return new ArrayList<String>(locators);
	}

	protected void setLocators(List<String> locators) {
		this.locators = locators;
	}

	public EISConstants.RequirednessLevel getRequirednessLevel() {
		return requirednessLevel;
	}

	public List<String> getMessageLocators() {
		return new ArrayList<String>(messageLocators);
	}

	public boolean isLocatorParameterized() {
		return isLocatorParameterized;
	}
	
	public EISConstants.ParameterizedLocatorType getParameterizedLocatorType() {
		return parameterizedLocatorType;
	}

	public List<List<String>> getLocatorParmsSets() {
		return new ArrayList<List<String>>(locatorParmsSets);
	}

	@Override
	public String toString() {
		return "Field ["
				+ "name=               " + name
				+ ", type=                    " + type
				+ ", locators=                " + locators
				+ ", requirednessLevel=       " + requirednessLevel
				+ ", messageLocators=         " + messageLocators
				+ ", isLocatorParameterized=  " + isLocatorParameterized
				+ ", parameterizedLocatorType=" + parameterizedLocatorType
				+ ", locatorParmsSets=        " + locatorParmsSets
				+ ", metadataString=          " + metadataString
				+ "]";
	}

	private void setMetadata() throws MetadataException {
		String[] properties;

		properties = metadataString.split(EISConstants.PROPERTY_TYPE_DELIM);

		if (properties.length < 2) {
			throw new MetadataException("The metadata for the field '" + name + "' does not contain the minimum number of properties - values for the 'type' and 'locator' properties are required");
		}

		try {
			type = EISConstants.FieldType.valueOf(properties[0].trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new MetadataException("The value supplied for the property 'type' for the field '" + name + "' is not a member of the EISConstants.FieldType enumerated type; valid values are: " + Util.valuesOfEnum(EISConstants.FieldType.class), e);
		}

		if (!properties[1].isEmpty()) {
			locators = Arrays.asList(properties[1].split(EISConstants.PROPERTY_INSTANCE_DELIM));
			locators = Util.listOfStringTrim(locators);
		} else {
			throw new MetadataException("The property 'locators' for the field '" + name + "' is empty");
		}

		if (properties.length == 2) {
			requirednessLevel = EISConstants.RequirednessLevel.OPTIONAL;
		} else {
			if (properties[2].isEmpty()) {
				requirednessLevel = EISConstants.RequirednessLevel.OPTIONAL;
			} else {
				try {
					requirednessLevel = EISConstants.RequirednessLevel.valueOf(properties[2].trim().toUpperCase());
				} catch (IllegalArgumentException e) {
					throw new MetadataException("The value supplied for the property 'requirednessLevel' for the field '" + name + "' is not a member of the EISConstants.RequirednessLevel enumerated type; valid values are: " + Util.valuesOfEnum(EISConstants.RequirednessLevel.class), e);
				}
			}

			if (properties.length == 4) {
				if (!properties[3].isEmpty()) {
					messageLocators = Arrays.asList(properties[3].split(EISConstants.PROPERTY_INSTANCE_DELIM));
					messageLocators = Util.listOfStringTrim(messageLocators);
				}
			}
		}
	}
	
	private void parseLocators() throws MetadataException {
		//findParameterizedLocator() also sets the instance variable parameterizedLocatorType
		isLocatorParameterized = findParameterizedLocator();

		if (isLocatorParameterized) {
			int numExpectedParms;
			int maxAllowedParmsSets = Integer.MAX_VALUE;
			
			//We allow only one locator of type TABLE_CELL_LOOKUP, because one of its parameters is the name of another
			//  field, whose locators are used to drill into the table.  Because that referenced field can itself have
			//  multiple locators that describe possible locations of the table, it does not make sense for this field
			//  to have more than one TABLE_CELL_LOOKUP locator - it would mean that it is attempting to look up in
			//  more than one table, as opposed to one table whose location may vary.
			switch (parameterizedLocatorType) {
				case INFO_PANEL_LOOKUP:	{
					numExpectedParms = 2;
					break;
				}
				case TABLE_CELL_LOOKUP:	{
					numExpectedParms = 2;
					maxAllowedParmsSets = 1;
					break;
				}
				case RELATED_LIST_CELL_LOOKUP:	{
					numExpectedParms = 2;
					break;
				}
				default:	{
					throw new MetadataException("Unhandled member of common.EISConstants.ParameterizedLocatorType enumerated type: " + parameterizedLocatorType);
				}
			}
			
			String regexTerm = ".*" + EISConstants.PARAMETERS_REGEX_TERM;
			boolean isParameterizedLocator = false;
			boolean isTargetParameterizedLocator = false;
			List<String> locatorParms = new ArrayList<String>();
			String locator;
			String temp;
			EISConstants.ParameterizedLocatorType thisParameterizedLocatorType = null;
			List<String> rejects = new ArrayList<String>();
			
			ListIterator<String> itr = locators.listIterator();
			while (itr.hasNext()) {
				locator = itr.next();
				
				isParameterizedLocator = false;
				isTargetParameterizedLocator = false;

				if (locator.matches(regexTerm)) {
	    			//We now have text that matches the regex term, but that text is not necessarily a set 
	    			//  of parms; it could be part of an xpath.  We'll grab the text to the left of the
	    			//  parms start delimiter, and see if it is of the type EISConstants.ParameterizedLocatorType
	
					temp = Util.left(locator, locator.indexOf(EISConstants.PARAMETERS_START_DELIM)).trim();
	
					//We're not throwing an exception here, because it is possible that a non-parameterized
					//  locator contains characters that match regexTerm
	    			try {
	    				thisParameterizedLocatorType = EISConstants.ParameterizedLocatorType.valueOf(temp.trim().toUpperCase());
	    				
	    				isParameterizedLocator = true;
	    			} catch (IllegalArgumentException e) {}
	
	    			if ((isParameterizedLocator) && (thisParameterizedLocatorType == parameterizedLocatorType)) {
	    				isTargetParameterizedLocator = true;

	    				//Get the parms
		    			temp = Util.getField(locator, EISConstants.PARAMETERS_START_DELIM, EISConstants.PARAMETERS_END_DELIM);
		    			locatorParms = Arrays.asList(temp.split(EISConstants.PARAMETER_DELIM));
		    			
						if (locatorParms.size() != numExpectedParms) {
		    				isLocatorParameterized = false;
							
		    				throw new MetadataException("The parameterized locator type is '" + parameterizedLocatorType + "', but the number of locator parameters is not " + numExpectedParms);
						}
						
						//There are limits to the number of certain parameterized locators that are allowed.
						//  For example, we allow only one locator of TABLE_CELL_LOOKUP type.  If we encounter
						//  more, we won't break from the loop; we'll add them to the rejects list for error
						//  reporting
						if (locatorParmsSets.size() < maxAllowedParmsSets) {
							locatorParms = Util.listOfStringTrim(locatorParms);
							locatorParmsSets.add(locatorParms);
						} else {
							//Cause the extraneous locator to be added to the rejects list
							isTargetParameterizedLocator = false;
						}
	    			}
	    		}
				
    			if ((!isParameterizedLocator) || (!isTargetParameterizedLocator)) {
    				rejects.add(locator);
    			}
				
	    	}
			
			if (!rejects.isEmpty()) {
				Util.printWarning("The first parameterized locator found was of type " + parameterizedLocatorType.toString() + "; but these locators (non-parameterized, of a parameterized type not the same as the first, and/or beyond the maximum allowed) were also found, and were discarded: " + rejects.toString());
			}
			
			locators.clear();
		}
	}
	
	private boolean findParameterizedLocator() {
		boolean foundParameterizedLocator = false;
		String regexTerm = ".*" + EISConstants.PARAMETERS_REGEX_TERM;
		String locator;
		String temp;

		ListIterator<String> itr = locators.listIterator();
		while (itr.hasNext()) {
			locator = itr.next();
			
    		if (locator.matches(regexTerm)) {
    			//We now have text that matches the regex term, but that text is not necessarily a set 
    			//  of parms; it could be part of an xpath.  We'll grab the text to the left of the
    			//  parms start delimiter, and see if it is of the type EISConstants.ParameterizedLocatorType

				temp = Util.left(locator, locator.indexOf(EISConstants.PARAMETERS_START_DELIM)).trim();

				//We're not throwing an exception here, because it is possible that a non-parameterized
				//  locator contains characters that match regexTerm
    			try {
    				parameterizedLocatorType = EISConstants.ParameterizedLocatorType.valueOf(temp.trim().toUpperCase());
    				
    				foundParameterizedLocator = true;
    			} catch (IllegalArgumentException e) {}

    			if (foundParameterizedLocator) {
    				break;
    			}
    		}
		}
		
		return foundParameterizedLocator;
	}
}
