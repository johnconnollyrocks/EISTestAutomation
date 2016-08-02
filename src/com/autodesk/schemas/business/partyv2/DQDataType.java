
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.autodesk.schemas.business.commonv1.AddressType;


/**
 * <p>Java class for DQDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DQDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CleansedAccountAlias" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CleansedHouseNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CleansedLocalLanguageName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CleansedName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CleansedPOBoxNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CleanserReviewCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="MatchData" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="MatchPattern" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CleansedAddress" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}AddressType" minOccurs="0"/>
 *         &lt;element name="CleansedFirstName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CleansedLastName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CleansedMiddleName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CleansedTitle" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DQDataType", propOrder = {
    "cleansedAccountAlias",
    "cleansedHouseNumber",
    "cleansedLocalLanguageName",
    "cleansedName",
    "cleansedPOBoxNumber",
    "cleanserReviewCode",
    "matchData",
    "matchPattern",
    "cleansedAddress",
    "cleansedFirstName",
    "cleansedLastName",
    "cleansedMiddleName",
    "cleansedTitle"
})
public class DQDataType {

    @XmlElement(name = "CleansedAccountAlias")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleansedAccountAlias;
    @XmlElement(name = "CleansedHouseNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleansedHouseNumber;
    @XmlElement(name = "CleansedLocalLanguageName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleansedLocalLanguageName;
    @XmlElement(name = "CleansedName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleansedName;
    @XmlElement(name = "CleansedPOBoxNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleansedPOBoxNumber;
    @XmlElement(name = "CleanserReviewCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleanserReviewCode;
    @XmlElement(name = "MatchData")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String matchData;
    @XmlElement(name = "MatchPattern")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String matchPattern;
    @XmlElement(name = "CleansedAddress")
    protected AddressType cleansedAddress;
    @XmlElement(name = "CleansedFirstName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleansedFirstName;
    @XmlElement(name = "CleansedLastName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleansedLastName;
    @XmlElement(name = "CleansedMiddleName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleansedMiddleName;
    @XmlElement(name = "CleansedTitle")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cleansedTitle;

    /**
     * Gets the value of the cleansedAccountAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleansedAccountAlias() {
        return cleansedAccountAlias;
    }

    /**
     * Sets the value of the cleansedAccountAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleansedAccountAlias(String value) {
        this.cleansedAccountAlias = value;
    }

    /**
     * Gets the value of the cleansedHouseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleansedHouseNumber() {
        return cleansedHouseNumber;
    }

    /**
     * Sets the value of the cleansedHouseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleansedHouseNumber(String value) {
        this.cleansedHouseNumber = value;
    }

    /**
     * Gets the value of the cleansedLocalLanguageName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleansedLocalLanguageName() {
        return cleansedLocalLanguageName;
    }

    /**
     * Sets the value of the cleansedLocalLanguageName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleansedLocalLanguageName(String value) {
        this.cleansedLocalLanguageName = value;
    }

    /**
     * Gets the value of the cleansedName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleansedName() {
        return cleansedName;
    }

    /**
     * Sets the value of the cleansedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleansedName(String value) {
        this.cleansedName = value;
    }

    /**
     * Gets the value of the cleansedPOBoxNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleansedPOBoxNumber() {
        return cleansedPOBoxNumber;
    }

    /**
     * Sets the value of the cleansedPOBoxNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleansedPOBoxNumber(String value) {
        this.cleansedPOBoxNumber = value;
    }

    /**
     * Gets the value of the cleanserReviewCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleanserReviewCode() {
        return cleanserReviewCode;
    }

    /**
     * Sets the value of the cleanserReviewCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleanserReviewCode(String value) {
        this.cleanserReviewCode = value;
    }

    /**
     * Gets the value of the matchData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatchData() {
        return matchData;
    }

    /**
     * Sets the value of the matchData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatchData(String value) {
        this.matchData = value;
    }

    /**
     * Gets the value of the matchPattern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatchPattern() {
        return matchPattern;
    }

    /**
     * Sets the value of the matchPattern property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatchPattern(String value) {
        this.matchPattern = value;
    }

    /**
     * Gets the value of the cleansedAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getCleansedAddress() {
        return cleansedAddress;
    }

    /**
     * Sets the value of the cleansedAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setCleansedAddress(AddressType value) {
        this.cleansedAddress = value;
    }

    /**
     * Gets the value of the cleansedFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleansedFirstName() {
        return cleansedFirstName;
    }

    /**
     * Sets the value of the cleansedFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleansedFirstName(String value) {
        this.cleansedFirstName = value;
    }

    /**
     * Gets the value of the cleansedLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleansedLastName() {
        return cleansedLastName;
    }

    /**
     * Sets the value of the cleansedLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleansedLastName(String value) {
        this.cleansedLastName = value;
    }

    /**
     * Gets the value of the cleansedMiddleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleansedMiddleName() {
        return cleansedMiddleName;
    }

    /**
     * Sets the value of the cleansedMiddleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleansedMiddleName(String value) {
        this.cleansedMiddleName = value;
    }

    /**
     * Gets the value of the cleansedTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleansedTitle() {
        return cleansedTitle;
    }

    /**
     * Sets the value of the cleansedTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleansedTitle(String value) {
        this.cleansedTitle = value;
    }

}
