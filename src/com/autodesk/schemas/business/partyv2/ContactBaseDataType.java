
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactBaseDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactBaseDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactRefDataType">
 *       &lt;sequence>
 *         &lt;element name="AlternateFirstName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="AlternateLastName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="MiddleName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="PrimaryAccount" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountBaseDataType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isEmp" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="suppressCleanse" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactBaseDataType", propOrder = {
    "alternateFirstName",
    "alternateLastName",
    "firstName",
    "lastName",
    "middleName",
    "primaryAccount"
})
@XmlSeeAlso({
    ContactCoreDataType.class
})
public class ContactBaseDataType
    extends ContactRefDataType
{

    @XmlElementRef(name = "AlternateFirstName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> alternateFirstName;
    @XmlElementRef(name = "AlternateLastName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> alternateLastName;
    @XmlElementRef(name = "FirstName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> firstName;
    @XmlElementRef(name = "LastName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastName;
    @XmlElementRef(name = "MiddleName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> middleName;
    @XmlElementRef(name = "PrimaryAccount", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<AccountBaseDataType> primaryAccount;
    @XmlAttribute(name = "isEmp")
    protected Boolean isEmp;
    @XmlAttribute(name = "suppressCleanse")
    protected Boolean suppressCleanse;

    /**
     * Gets the value of the alternateFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAlternateFirstName() {
        return alternateFirstName;
    }

    /**
     * Sets the value of the alternateFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAlternateFirstName(JAXBElement<String> value) {
        this.alternateFirstName = value;
    }

    /**
     * Gets the value of the alternateLastName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAlternateLastName() {
        return alternateLastName;
    }

    /**
     * Sets the value of the alternateLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAlternateLastName(JAXBElement<String> value) {
        this.alternateLastName = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFirstName(JAXBElement<String> value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLastName(JAXBElement<String> value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMiddleName(JAXBElement<String> value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the primaryAccount property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link AccountBaseDataType }{@code >}
     *     
     */
    public JAXBElement<AccountBaseDataType> getPrimaryAccount() {
        return primaryAccount;
    }

    /**
     * Sets the value of the primaryAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link AccountBaseDataType }{@code >}
     *     
     */
    public void setPrimaryAccount(JAXBElement<AccountBaseDataType> value) {
        this.primaryAccount = value;
    }

    /**
     * Gets the value of the isEmp property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsEmp() {
        return isEmp;
    }

    /**
     * Sets the value of the isEmp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsEmp(Boolean value) {
        this.isEmp = value;
    }

    /**
     * Gets the value of the suppressCleanse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuppressCleanse() {
        return suppressCleanse;
    }

    /**
     * Sets the value of the suppressCleanse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuppressCleanse(Boolean value) {
        this.suppressCleanse = value;
    }

}
