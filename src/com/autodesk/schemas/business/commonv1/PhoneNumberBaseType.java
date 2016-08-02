
package com.autodesk.schemas.business.commonv1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PhoneNumberBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PhoneNumberBaseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/CommonV1.0}PhoneNumberRefType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="CallingCountryCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="DisplayFormat" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="E164Format" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Extension" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="IsValidNumber" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="NationalNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="NumberType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Region" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhoneNumberBaseType", propOrder = {
    "callingCountryCode",
    "displayFormat",
    "e164Format",
    "extension",
    "isValidNumber",
    "nationalNumber",
    "numberType",
    "region"
})
public class PhoneNumberBaseType
    extends PhoneNumberRefType
{

    @XmlElement(name = "CallingCountryCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String callingCountryCode;
    @XmlElement(name = "DisplayFormat")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String displayFormat;
    @XmlElement(name = "E164Format")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String e164Format;
    @XmlElement(name = "Extension")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String extension;
    @XmlElement(name = "IsValidNumber")
    protected Boolean isValidNumber;
    @XmlElement(name = "NationalNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String nationalNumber;
    @XmlElement(name = "NumberType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String numberType;
    @XmlElement(name = "Region")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String region;

    /**
     * Gets the value of the callingCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallingCountryCode() {
        return callingCountryCode;
    }

    /**
     * Sets the value of the callingCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallingCountryCode(String value) {
        this.callingCountryCode = value;
    }

    /**
     * Gets the value of the displayFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayFormat() {
        return displayFormat;
    }

    /**
     * Sets the value of the displayFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayFormat(String value) {
        this.displayFormat = value;
    }

    /**
     * Gets the value of the e164Format property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE164Format() {
        return e164Format;
    }

    /**
     * Sets the value of the e164Format property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE164Format(String value) {
        this.e164Format = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtension(String value) {
        this.extension = value;
    }

    /**
     * Gets the value of the isValidNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsValidNumber() {
        return isValidNumber;
    }

    /**
     * Sets the value of the isValidNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsValidNumber(Boolean value) {
        this.isValidNumber = value;
    }

    /**
     * Gets the value of the nationalNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationalNumber() {
        return nationalNumber;
    }

    /**
     * Sets the value of the nationalNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationalNumber(String value) {
        this.nationalNumber = value;
    }

    /**
     * Gets the value of the numberType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberType() {
        return numberType;
    }

    /**
     * Sets the value of the numberType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberType(String value) {
        this.numberType = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

}
