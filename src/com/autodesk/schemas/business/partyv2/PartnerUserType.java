
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartnerUserType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartnerUserType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}UserType">
 *       &lt;sequence>
 *         &lt;element name="ListOfRelatedAccount" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfAccountType" minOccurs="0"/>
 *         &lt;element name="ListOfPartnerProfiles" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfPartnerProfileType" minOccurs="0"/>
 *         &lt;element name="PartnerPosition" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="isDelegatedAdmin" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PrimaryAccount" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountBaseDataType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartnerUserType", propOrder = {
    "listOfRelatedAccount",
    "listOfPartnerProfiles",
    "partnerPosition",
    "isDelegatedAdmin",
    "primaryAccount"
})
public class PartnerUserType
    extends UserType
{

    @XmlElement(name = "ListOfRelatedAccount")
    protected ListOfAccountType listOfRelatedAccount;
    @XmlElement(name = "ListOfPartnerProfiles")
    protected ListOfPartnerProfileType listOfPartnerProfiles;
    @XmlElementRef(name = "PartnerPosition", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> partnerPosition;
    @XmlElementRef(name = "isDelegatedAdmin", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> isDelegatedAdmin;
    @XmlElementRef(name = "PrimaryAccount", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<AccountBaseDataType> primaryAccount;

    /**
     * Gets the value of the listOfRelatedAccount property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfAccountType }
     *     
     */
    public ListOfAccountType getListOfRelatedAccount() {
        return listOfRelatedAccount;
    }

    /**
     * Sets the value of the listOfRelatedAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfAccountType }
     *     
     */
    public void setListOfRelatedAccount(ListOfAccountType value) {
        this.listOfRelatedAccount = value;
    }

    /**
     * Gets the value of the listOfPartnerProfiles property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfPartnerProfileType }
     *     
     */
    public ListOfPartnerProfileType getListOfPartnerProfiles() {
        return listOfPartnerProfiles;
    }

    /**
     * Sets the value of the listOfPartnerProfiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfPartnerProfileType }
     *     
     */
    public void setListOfPartnerProfiles(ListOfPartnerProfileType value) {
        this.listOfPartnerProfiles = value;
    }

    /**
     * Gets the value of the partnerPosition property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPartnerPosition() {
        return partnerPosition;
    }

    /**
     * Sets the value of the partnerPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPartnerPosition(JAXBElement<String> value) {
        this.partnerPosition = value;
    }

    /**
     * Gets the value of the isDelegatedAdmin property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsDelegatedAdmin() {
        return isDelegatedAdmin;
    }

    /**
     * Sets the value of the isDelegatedAdmin property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsDelegatedAdmin(JAXBElement<Boolean> value) {
        this.isDelegatedAdmin = value;
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

}
