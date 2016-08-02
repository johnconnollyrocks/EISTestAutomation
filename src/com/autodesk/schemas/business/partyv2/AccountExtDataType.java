
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.autodesk.schemas.business.commonv1.ListOfAddressType;
import com.autodesk.schemas.business.commonv1.ListOfPhoneNumberType;


/**
 * <p>Java class for AccountExtDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountExtDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountCoreDataType">
 *       &lt;sequence>
 *         &lt;element name="AccountGroup" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="EmailAddress" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="HomePage" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="LanguageCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="PartnerType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Region" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="VATregistrationNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="InternalOrg" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="IndividualAccount" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="DataSource" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ExportControlNotes" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="PrimaryAccountCountry" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="DeDupToken" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="StandardizedName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="DQData" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}DQDataType" minOccurs="0"/>
 *         &lt;element name="DUNSData" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}DUNSDataType" minOccurs="0"/>
 *         &lt;element name="ListOfPhone" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}ListOfPhoneNumberType" minOccurs="0"/>
 *         &lt;element name="ListOfRole" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfRoleType" minOccurs="0"/>
 *         &lt;element name="OtherAddresses" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}ListOfAddressType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountExtDataType", propOrder = {
    "accountGroup",
    "currencyCode",
    "emailAddress",
    "homePage",
    "languageCode",
    "partnerType",
    "region",
    "vaTregistrationNumber",
    "internalOrg",
    "individualAccount",
    "dataSource",
    "exportControlNotes",
    "primaryAccountCountry",
    "deDupToken",
    "standardizedName",
    "dqData",
    "dunsData",
    "listOfPhone",
    "listOfRole",
    "otherAddresses"
})
@XmlSeeAlso({
    AccountType.class
})
public class AccountExtDataType
    extends AccountCoreDataType
{

    @XmlElementRef(name = "AccountGroup", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> accountGroup;
    @XmlElementRef(name = "CurrencyCode", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> currencyCode;
    @XmlElementRef(name = "EmailAddress", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> emailAddress;
    @XmlElementRef(name = "HomePage", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> homePage;
    @XmlElementRef(name = "LanguageCode", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> languageCode;
    @XmlElementRef(name = "PartnerType", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> partnerType;
    @XmlElementRef(name = "Region", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> region;
    @XmlElementRef(name = "VATregistrationNumber", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vaTregistrationNumber;
    @XmlElementRef(name = "InternalOrg", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> internalOrg;
    @XmlElementRef(name = "IndividualAccount", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> individualAccount;
    @XmlElementRef(name = "DataSource", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dataSource;
    @XmlElementRef(name = "ExportControlNotes", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> exportControlNotes;
    @XmlElementRef(name = "PrimaryAccountCountry", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> primaryAccountCountry;
    @XmlElementRef(name = "DeDupToken", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> deDupToken;
    @XmlElementRef(name = "StandardizedName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> standardizedName;
    @XmlElement(name = "DQData")
    protected DQDataType dqData;
    @XmlElementRef(name = "DUNSData", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<DUNSDataType> dunsData;
    @XmlElement(name = "ListOfPhone")
    protected ListOfPhoneNumberType listOfPhone;
    @XmlElement(name = "ListOfRole")
    protected ListOfRoleType listOfRole;
    @XmlElementRef(name = "OtherAddresses", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ListOfAddressType> otherAddresses;

    /**
     * Gets the value of the accountGroup property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAccountGroup() {
        return accountGroup;
    }

    /**
     * Sets the value of the accountGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAccountGroup(JAXBElement<String> value) {
        this.accountGroup = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCurrencyCode(JAXBElement<String> value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEmailAddress(JAXBElement<String> value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the homePage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHomePage() {
        return homePage;
    }

    /**
     * Sets the value of the homePage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHomePage(JAXBElement<String> value) {
        this.homePage = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLanguageCode(JAXBElement<String> value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the partnerType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPartnerType() {
        return partnerType;
    }

    /**
     * Sets the value of the partnerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPartnerType(JAXBElement<String> value) {
        this.partnerType = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRegion(JAXBElement<String> value) {
        this.region = value;
    }

    /**
     * Gets the value of the vaTregistrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVATregistrationNumber() {
        return vaTregistrationNumber;
    }

    /**
     * Sets the value of the vaTregistrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVATregistrationNumber(JAXBElement<String> value) {
        this.vaTregistrationNumber = value;
    }

    /**
     * Gets the value of the internalOrg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInternalOrg() {
        return internalOrg;
    }

    /**
     * Sets the value of the internalOrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInternalOrg(JAXBElement<String> value) {
        this.internalOrg = value;
    }

    /**
     * Gets the value of the individualAccount property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIndividualAccount() {
        return individualAccount;
    }

    /**
     * Sets the value of the individualAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIndividualAccount(JAXBElement<String> value) {
        this.individualAccount = value;
    }

    /**
     * Gets the value of the dataSource property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDataSource() {
        return dataSource;
    }

    /**
     * Sets the value of the dataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDataSource(JAXBElement<String> value) {
        this.dataSource = value;
    }

    /**
     * Gets the value of the exportControlNotes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getExportControlNotes() {
        return exportControlNotes;
    }

    /**
     * Sets the value of the exportControlNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setExportControlNotes(JAXBElement<String> value) {
        this.exportControlNotes = value;
    }

    /**
     * Gets the value of the primaryAccountCountry property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPrimaryAccountCountry() {
        return primaryAccountCountry;
    }

    /**
     * Sets the value of the primaryAccountCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPrimaryAccountCountry(JAXBElement<String> value) {
        this.primaryAccountCountry = value;
    }

    /**
     * Gets the value of the deDupToken property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDeDupToken() {
        return deDupToken;
    }

    /**
     * Sets the value of the deDupToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDeDupToken(JAXBElement<String> value) {
        this.deDupToken = value;
    }

    /**
     * Gets the value of the standardizedName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStandardizedName() {
        return standardizedName;
    }

    /**
     * Sets the value of the standardizedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStandardizedName(JAXBElement<String> value) {
        this.standardizedName = value;
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
     * Gets the value of the dunsData property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DUNSDataType }{@code >}
     *     
     */
    public JAXBElement<DUNSDataType> getDUNSData() {
        return dunsData;
    }

    /**
     * Sets the value of the dunsData property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DUNSDataType }{@code >}
     *     
     */
    public void setDUNSData(JAXBElement<DUNSDataType> value) {
        this.dunsData = value;
    }

    /**
     * Gets the value of the listOfPhone property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfPhoneNumberType }
     *     
     */
    public ListOfPhoneNumberType getListOfPhone() {
        return listOfPhone;
    }

    /**
     * Sets the value of the listOfPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfPhoneNumberType }
     *     
     */
    public void setListOfPhone(ListOfPhoneNumberType value) {
        this.listOfPhone = value;
    }

    /**
     * Gets the value of the listOfRole property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfRoleType }
     *     
     */
    public ListOfRoleType getListOfRole() {
        return listOfRole;
    }

    /**
     * Sets the value of the listOfRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfRoleType }
     *     
     */
    public void setListOfRole(ListOfRoleType value) {
        this.listOfRole = value;
    }

    /**
     * Gets the value of the otherAddresses property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ListOfAddressType }{@code >}
     *     
     */
    public JAXBElement<ListOfAddressType> getOtherAddresses() {
        return otherAddresses;
    }

    /**
     * Sets the value of the otherAddresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ListOfAddressType }{@code >}
     *     
     */
    public void setOtherAddresses(JAXBElement<ListOfAddressType> value) {
        this.otherAddresses = value;
    }

}
