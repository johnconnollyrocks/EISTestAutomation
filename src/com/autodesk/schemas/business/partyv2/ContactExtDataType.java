
package com.autodesk.schemas.business.partyv2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.autodesk.schemas.business.commonv1.ListOfAddressType;


/**
 * <p>Java class for ContactExtDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactExtDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactCoreDataType">
 *       &lt;sequence>
 *         &lt;element name="DedupToken" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DQData" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}DQDataType" minOccurs="0"/>
 *         &lt;element name="Industry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusReason" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SuppressAllCalls" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressAllEmails" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressAllFaxes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressAllMailings" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressMarketingOptions" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SuppressSurvey" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ContactSource" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="LastLoginDate" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ListOfAddress" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}ListOfAddressType" minOccurs="0"/>
 *         &lt;element name="ListOfExternalAppStatus" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ExternalAppStatus" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
 *                           &lt;attribute name="appName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfPreference" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Preference" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfProfession" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="profession" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
 *                           &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListOfRelatedAccount" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RelatedAccount" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountBaseDataType">
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AssignedSerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactExtDataType", propOrder = {
    "dedupToken",
    "department",
    "dqData",
    "industry",
    "statusReason",
    "suppressAllCalls",
    "suppressAllEmails",
    "suppressAllFaxes",
    "suppressAllMailings",
    "suppressMarketingOptions",
    "suppressSurvey",
    "contactSource",
    "createDate",
    "lastLoginDate",
    "listOfAddress",
    "listOfExternalAppStatus",
    "listOfPreference",
    "listOfProfession",
    "listOfRelatedAccount",
    "assignedSerialNumber"
})
@XmlSeeAlso({
    ContactType.class
})
public class ContactExtDataType
    extends ContactCoreDataType
{

    @XmlElement(name = "DedupToken")
    protected List<ContactExtDataType.DedupToken> dedupToken;
    @XmlElementRef(name = "Department", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> department;
    @XmlElement(name = "DQData")
    protected DQDataType dqData;
    @XmlElementRef(name = "Industry", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> industry;
    @XmlElementRef(name = "StatusReason", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> statusReason;
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
    @XmlElementRef(name = "ContactSource", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> contactSource;
    @XmlElementRef(name = "CreateDate", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> createDate;
    @XmlElementRef(name = "LastLoginDate", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastLoginDate;
    @XmlElementRef(name = "ListOfAddress", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ListOfAddressType> listOfAddress;
    @XmlElement(name = "ListOfExternalAppStatus")
    protected ContactExtDataType.ListOfExternalAppStatus listOfExternalAppStatus;
    @XmlElement(name = "ListOfPreference")
    protected ContactExtDataType.ListOfPreference listOfPreference;
    @XmlElement(name = "ListOfProfession")
    protected ContactExtDataType.ListOfProfession listOfProfession;
    @XmlElementRef(name = "ListOfRelatedAccount", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ContactExtDataType.ListOfRelatedAccount> listOfRelatedAccount;
    @XmlElementRef(name = "AssignedSerialNumber", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> assignedSerialNumber;

    /**
     * Gets the value of the dedupToken property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dedupToken property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDedupToken().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactExtDataType.DedupToken }
     * 
     * 
     */
    public List<ContactExtDataType.DedupToken> getDedupToken() {
        if (dedupToken == null) {
            dedupToken = new ArrayList<ContactExtDataType.DedupToken>();
        }
        return this.dedupToken;
    }

    /**
     * Gets the value of the department property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDepartment() {
        return department;
    }

    /**
     * Sets the value of the department property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDepartment(JAXBElement<String> value) {
        this.department = value;
    }

    /**
     * Gets the value of the dqData property.
     * 
     * @return
     *     possible object is
     *     {@link DQDataType }
     *     
     */
    public DQDataType getDQData() {
        return dqData;
    }

    /**
     * Sets the value of the dqData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DQDataType }
     *     
     */
    public void setDQData(DQDataType value) {
        this.dqData = value;
    }

    /**
     * Gets the value of the industry property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIndustry() {
        return industry;
    }

    /**
     * Sets the value of the industry property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIndustry(JAXBElement<String> value) {
        this.industry = value;
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
     * Gets the value of the contactSource property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContactSource() {
        return contactSource;
    }

    /**
     * Sets the value of the contactSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContactSource(JAXBElement<String> value) {
        this.contactSource = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCreateDate(JAXBElement<String> value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the lastLoginDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * Sets the value of the lastLoginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLastLoginDate(JAXBElement<String> value) {
        this.lastLoginDate = value;
    }

    /**
     * Gets the value of the listOfAddress property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ListOfAddressType }{@code >}
     *     
     */
    public JAXBElement<ListOfAddressType> getListOfAddress() {
        return listOfAddress;
    }

    /**
     * Sets the value of the listOfAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ListOfAddressType }{@code >}
     *     
     */
    public void setListOfAddress(JAXBElement<ListOfAddressType> value) {
        this.listOfAddress = value;
    }

    /**
     * Gets the value of the listOfExternalAppStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ContactExtDataType.ListOfExternalAppStatus }
     *     
     */
    public ContactExtDataType.ListOfExternalAppStatus getListOfExternalAppStatus() {
        return listOfExternalAppStatus;
    }

    /**
     * Sets the value of the listOfExternalAppStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactExtDataType.ListOfExternalAppStatus }
     *     
     */
    public void setListOfExternalAppStatus(ContactExtDataType.ListOfExternalAppStatus value) {
        this.listOfExternalAppStatus = value;
    }

    /**
     * Gets the value of the listOfPreference property.
     * 
     * @return
     *     possible object is
     *     {@link ContactExtDataType.ListOfPreference }
     *     
     */
    public ContactExtDataType.ListOfPreference getListOfPreference() {
        return listOfPreference;
    }

    /**
     * Sets the value of the listOfPreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactExtDataType.ListOfPreference }
     *     
     */
    public void setListOfPreference(ContactExtDataType.ListOfPreference value) {
        this.listOfPreference = value;
    }

    /**
     * Gets the value of the listOfProfession property.
     * 
     * @return
     *     possible object is
     *     {@link ContactExtDataType.ListOfProfession }
     *     
     */
    public ContactExtDataType.ListOfProfession getListOfProfession() {
        return listOfProfession;
    }

    /**
     * Sets the value of the listOfProfession property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactExtDataType.ListOfProfession }
     *     
     */
    public void setListOfProfession(ContactExtDataType.ListOfProfession value) {
        this.listOfProfession = value;
    }

    /**
     * Gets the value of the listOfRelatedAccount property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ContactExtDataType.ListOfRelatedAccount }{@code >}
     *     
     */
    public JAXBElement<ContactExtDataType.ListOfRelatedAccount> getListOfRelatedAccount() {
        return listOfRelatedAccount;
    }

    /**
     * Sets the value of the listOfRelatedAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ContactExtDataType.ListOfRelatedAccount }{@code >}
     *     
     */
    public void setListOfRelatedAccount(JAXBElement<ContactExtDataType.ListOfRelatedAccount> value) {
        this.listOfRelatedAccount = value;
    }

    /**
     * Gets the value of the assignedSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAssignedSerialNumber() {
        return assignedSerialNumber;
    }

    /**
     * Sets the value of the assignedSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAssignedSerialNumber(JAXBElement<String> value) {
        this.assignedSerialNumber = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DedupToken {

        @XmlAttribute(name = "type")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String type;

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ExternalAppStatus" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
     *                 &lt;attribute name="appName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "externalAppStatus"
    })
    public static class ListOfExternalAppStatus {

        @XmlElement(name = "ExternalAppStatus")
        protected List<ContactExtDataType.ListOfExternalAppStatus.ExternalAppStatus> externalAppStatus;

        /**
         * Gets the value of the externalAppStatus property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the externalAppStatus property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExternalAppStatus().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContactExtDataType.ListOfExternalAppStatus.ExternalAppStatus }
         * 
         * 
         */
        public List<ContactExtDataType.ListOfExternalAppStatus.ExternalAppStatus> getExternalAppStatus() {
            if (externalAppStatus == null) {
                externalAppStatus = new ArrayList<ContactExtDataType.ListOfExternalAppStatus.ExternalAppStatus>();
            }
            return this.externalAppStatus;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
         *       &lt;attribute name="appName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class ExternalAppStatus {

            @XmlValue
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String value;
            @XmlAttribute(name = "appName")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String appName;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the appName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAppName() {
                return appName;
            }

            /**
             * Sets the value of the appName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAppName(String value) {
                this.appName = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Preference" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "preference"
    })
    public static class ListOfPreference {

        @XmlElement(name = "Preference")
        protected List<ContactExtDataType.ListOfPreference.Preference> preference;

        /**
         * Gets the value of the preference property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the preference property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPreference().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContactExtDataType.ListOfPreference.Preference }
         * 
         * 
         */
        public List<ContactExtDataType.ListOfPreference.Preference> getPreference() {
            if (preference == null) {
                preference = new ArrayList<ContactExtDataType.ListOfPreference.Preference>();
            }
            return this.preference;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Preference {

            @XmlValue
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String value;
            @XmlAttribute(name = "type")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String type;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the type property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="profession" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
     *                 &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "profession"
    })
    public static class ListOfProfession {

        protected List<ContactExtDataType.ListOfProfession.Profession> profession;

        /**
         * Gets the value of the profession property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the profession property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProfession().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContactExtDataType.ListOfProfession.Profession }
         * 
         * 
         */
        public List<ContactExtDataType.ListOfProfession.Profession> getProfession() {
            if (profession == null) {
                profession = new ArrayList<ContactExtDataType.ListOfProfession.Profession>();
            }
            return this.profession;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>normalizedString">
         *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Profession {

            @XmlValue
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String value;
            @XmlAttribute(name = "code")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String code;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the code property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCode() {
                return code;
            }

            /**
             * Sets the value of the code property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCode(String value) {
                this.code = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RelatedAccount" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountBaseDataType">
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "relatedAccount"
    })
    public static class ListOfRelatedAccount {

        @XmlElement(name = "RelatedAccount")
        protected List<ContactExtDataType.ListOfRelatedAccount.RelatedAccount> relatedAccount;

        /**
         * Gets the value of the relatedAccount property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the relatedAccount property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRelatedAccount().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContactExtDataType.ListOfRelatedAccount.RelatedAccount }
         * 
         * 
         */
        public List<ContactExtDataType.ListOfRelatedAccount.RelatedAccount> getRelatedAccount() {
            if (relatedAccount == null) {
                relatedAccount = new ArrayList<ContactExtDataType.ListOfRelatedAccount.RelatedAccount>();
            }
            return this.relatedAccount;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountBaseDataType">
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class RelatedAccount
            extends AccountBaseDataType
        {

            @XmlAttribute(name = "type")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String type;

            /**
             * Gets the value of the type property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
            }

        }

    }

}
