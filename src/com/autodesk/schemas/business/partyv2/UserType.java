
package com.autodesk.schemas.business.partyv2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactRefDataType">
 *       &lt;sequence>
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="MiddleName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="AlternateFirstName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="AlternateLastName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="AuthenticationData" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}SecurityProfileType" minOccurs="0"/>
 *         &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ListOfRole" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfRoleType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SuppressAllCalls" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressAllEmails" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressAllFaxes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressAllMailings" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressMarketingOptions" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressSurvey" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UserType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
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
@XmlType(name = "UserType", propOrder = {
    "firstName",
    "lastName",
    "middleName",
    "alternateFirstName",
    "alternateLastName",
    "authenticationData",
    "countryCode",
    "language",
    "status",
    "listOfRole",
    "suppressAllCalls",
    "suppressAllEmails",
    "suppressAllFaxes",
    "suppressAllMailings",
    "suppressMarketingOptions",
    "suppressSurvey",
    "userType"
})
@XmlSeeAlso({
    PartnerUserType.class
})
public class UserType
    extends ContactRefDataType
{

    @XmlElementRef(name = "FirstName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> firstName;
    @XmlElementRef(name = "LastName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastName;
    @XmlElementRef(name = "MiddleName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> middleName;
    @XmlElementRef(name = "AlternateFirstName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> alternateFirstName;
    @XmlElementRef(name = "AlternateLastName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> alternateLastName;
    @XmlElement(name = "AuthenticationData")
    protected SecurityProfileType authenticationData;
    @XmlElementRef(name = "CountryCode", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> countryCode;
    @XmlElementRef(name = "Language", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> language;
    @XmlElementRef(name = "Status", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> status;
    @XmlElement(name = "ListOfRole", nillable = true)
    protected List<ListOfRoleType> listOfRole;
    @XmlElementRef(name = "SuppressAllCalls", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> suppressAllCalls;
    @XmlElementRef(name = "SuppressAllEmails", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> suppressAllEmails;
    @XmlElementRef(name = "SuppressAllFaxes", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> suppressAllFaxes;
    @XmlElementRef(name = "SuppressAllMailings", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> suppressAllMailings;
    @XmlElementRef(name = "SuppressMarketingOptions", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> suppressMarketingOptions;
    @XmlElementRef(name = "SuppressSurvey", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> suppressSurvey;
    @XmlElementRef(name = "UserType", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> userType;
    @XmlAttribute(name = "isEmp")
    protected Boolean isEmp;
    @XmlAttribute(name = "suppressCleanse")
    protected Boolean suppressCleanse;

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
     * Gets the value of the authenticationData property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityProfileType }
     *     
     */
    public SecurityProfileType getAuthenticationData() {
        return authenticationData;
    }

    /**
     * Sets the value of the authenticationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityProfileType }
     *     
     */
    public void setAuthenticationData(SecurityProfileType value) {
        this.authenticationData = value;
    }

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

    /**
     * Gets the value of the suppressAllCalls property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getSuppressAllCalls() {
        return suppressAllCalls;
    }

    /**
     * Sets the value of the suppressAllCalls property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setSuppressAllCalls(JAXBElement<Boolean> value) {
        this.suppressAllCalls = value;
    }

    /**
     * Gets the value of the suppressAllEmails property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getSuppressAllEmails() {
        return suppressAllEmails;
    }

    /**
     * Sets the value of the suppressAllEmails property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setSuppressAllEmails(JAXBElement<Boolean> value) {
        this.suppressAllEmails = value;
    }

    /**
     * Gets the value of the suppressAllFaxes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getSuppressAllFaxes() {
        return suppressAllFaxes;
    }

    /**
     * Sets the value of the suppressAllFaxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setSuppressAllFaxes(JAXBElement<Boolean> value) {
        this.suppressAllFaxes = value;
    }

    /**
     * Gets the value of the suppressAllMailings property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getSuppressAllMailings() {
        return suppressAllMailings;
    }

    /**
     * Sets the value of the suppressAllMailings property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setSuppressAllMailings(JAXBElement<Boolean> value) {
        this.suppressAllMailings = value;
    }

    /**
     * Gets the value of the suppressMarketingOptions property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getSuppressMarketingOptions() {
        return suppressMarketingOptions;
    }

    /**
     * Sets the value of the suppressMarketingOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setSuppressMarketingOptions(JAXBElement<Boolean> value) {
        this.suppressMarketingOptions = value;
    }

    /**
     * Gets the value of the suppressSurvey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getSuppressSurvey() {
        return suppressSurvey;
    }

    /**
     * Sets the value of the suppressSurvey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setSuppressSurvey(JAXBElement<Boolean> value) {
        this.suppressSurvey = value;
    }

    /**
     * Gets the value of the userType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUserType(JAXBElement<String> value) {
        this.userType = value;
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
