
package com.autodesk.schemas.business.partyv2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for UserCoreDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserCoreDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}UserBaseDataType">
 *       &lt;sequence>
 *         &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="NewPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OldPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="LastLogin" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Salutation" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="StatusReason" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="PasswordExpired" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *               &lt;whiteSpace value="collapse"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ListOfSecurityQA" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfSecurityQAType" minOccurs="0"/>
 *         &lt;element name="ListOfRole" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfRoleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserCoreDataType", propOrder = {
    "countryCode",
    "createdDate",
    "newPassword",
    "oldPassword",
    "language",
    "lastLogin",
    "salutation",
    "status",
    "statusReason",
    "passwordExpired",
    "listOfSecurityQA",
    "listOfRole"
})
public class UserCoreDataType
    extends UserBaseDataType
{

    @XmlElementRef(name = "CountryCode", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> countryCode;
    @XmlElement(name = "CreatedDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar createdDate;
    @XmlElementRef(name = "NewPassword", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> newPassword;
    @XmlElementRef(name = "OldPassword", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> oldPassword;
    @XmlElementRef(name = "Language", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> language;
    @XmlElement(name = "LastLogin")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastLogin;
    @XmlElementRef(name = "Salutation", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> salutation;
    @XmlElementRef(name = "Status", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> status;
    @XmlElementRef(name = "StatusReason", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> statusReason;
    @XmlElementRef(name = "PasswordExpired", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> passwordExpired;
    @XmlElementRef(name = "ListOfSecurityQA", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ListOfSecurityQAType> listOfSecurityQA;
    @XmlElement(name = "ListOfRole", nillable = true)
    protected List<ListOfRoleType> listOfRole;

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCountryCode(JAXBElement<String> value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the newPassword property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the value of the newPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNewPassword(JAXBElement<String> value) {
        this.newPassword = value;
    }

    /**
     * Gets the value of the oldPassword property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOldPassword() {
        return oldPassword;
    }

    /**
     * Sets the value of the oldPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOldPassword(JAXBElement<String> value) {
        this.oldPassword = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLanguage(JAXBElement<String> value) {
        this.language = value;
    }

    /**
     * Gets the value of the lastLogin property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets the value of the lastLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastLogin(XMLGregorianCalendar value) {
        this.lastLogin = value;
    }

    /**
     * Gets the value of the salutation property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSalutation() {
        return salutation;
    }

    /**
     * Sets the value of the salutation property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSalutation(JAXBElement<String> value) {
        this.salutation = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatus(JAXBElement<String> value) {
        this.status = value;
    }

    /**
     * Gets the value of the statusReason property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatusReason() {
        return statusReason;
    }

    /**
     * Sets the value of the statusReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatusReason(JAXBElement<String> value) {
        this.statusReason = value;
    }

    /**
     * Gets the value of the passwordExpired property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getPasswordExpired() {
        return passwordExpired;
    }

    /**
     * Sets the value of the passwordExpired property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setPasswordExpired(JAXBElement<Boolean> value) {
        this.passwordExpired = value;
    }

    /**
     * Gets the value of the listOfSecurityQA property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ListOfSecurityQAType }{@code >}
     *     
     */
    public JAXBElement<ListOfSecurityQAType> getListOfSecurityQA() {
        return listOfSecurityQA;
    }

    /**
     * Sets the value of the listOfSecurityQA property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ListOfSecurityQAType }{@code >}
     *     
     */
    public void setListOfSecurityQA(JAXBElement<ListOfSecurityQAType> value) {
        this.listOfSecurityQA = value;
    }

    /**
     * Gets the value of the listOfRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listOfRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListOfRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListOfRoleType }
     * 
     * 
     */
    public List<ListOfRoleType> getListOfRole() {
        if (listOfRole == null) {
            listOfRole = new ArrayList<ListOfRoleType>();
        }
        return this.listOfRole;
    }

}
