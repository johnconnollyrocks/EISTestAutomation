
package com.autodesk.schemas.business.partyv2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.autodesk.schemas.business.commonv1.ListOfPhoneNumberType;


/**
 * <p>Java class for ContactCoreDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactCoreDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactBaseDataType">
 *       &lt;sequence>
 *         &lt;element name="AuthenticationData" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}SecurityProfileType" minOccurs="0"/>
 *         &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="JobTitle" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Salutation" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Suffix" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ListOfPhone" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}ListOfPhoneNumberType" minOccurs="0"/>
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
@XmlType(name = "ContactCoreDataType", propOrder = {
    "authenticationData",
    "countryCode",
    "language",
    "jobTitle",
    "salutation",
    "status",
    "suffix",
    "listOfPhone",
    "listOfRole"
})
@XmlSeeAlso({
    ContactExtDataType.class
})
public class ContactCoreDataType
    extends ContactBaseDataType
{

    @XmlElement(name = "AuthenticationData")
    protected SecurityProfileType authenticationData;
    @XmlElementRef(name = "CountryCode", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> countryCode;
    @XmlElementRef(name = "Language", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> language;
    @XmlElementRef(name = "JobTitle", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobTitle;
    @XmlElementRef(name = "Salutation", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> salutation;
    @XmlElementRef(name = "Status", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> status;
    @XmlElement(name = "Suffix")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String suffix;
    @XmlElementRef(name = "ListOfPhone", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ListOfPhoneNumberType> listOfPhone;
    @XmlElement(name = "ListOfRole", nillable = true)
    protected List<ListOfRoleType> listOfRole;

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
     * Gets the value of the jobTitle property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the value of the jobTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setJobTitle(JAXBElement<String> value) {
        this.jobTitle = value;
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
     * Gets the value of the suffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
    }

    /**
     * Gets the value of the listOfPhone property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ListOfPhoneNumberType }{@code >}
     *     
     */
    public JAXBElement<ListOfPhoneNumberType> getListOfPhone() {
        return listOfPhone;
    }

    /**
     * Sets the value of the listOfPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ListOfPhoneNumberType }{@code >}
     *     
     */
    public void setListOfPhone(JAXBElement<ListOfPhoneNumberType> value) {
        this.listOfPhone = value;
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
