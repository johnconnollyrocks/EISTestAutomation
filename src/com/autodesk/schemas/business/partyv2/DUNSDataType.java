
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DUNSDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DUNSDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccountDUNSNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CompanyDUNSNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="DomesticUltimateBusinessName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="DomesticUltimateDUNSNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="GlobalUltimateBusinessName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="GlobalUltimateDUNSNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ParentDUNSNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DUNSDataType", propOrder = {
    "accountDUNSNumber",
    "companyDUNSNumber",
    "domesticUltimateBusinessName",
    "domesticUltimateDUNSNumber",
    "globalUltimateBusinessName",
    "globalUltimateDUNSNumber",
    "parentDUNSNumber"
})
public class DUNSDataType {

    @XmlElementRef(name = "AccountDUNSNumber", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> accountDUNSNumber;
    @XmlElementRef(name = "CompanyDUNSNumber", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> companyDUNSNumber;
    @XmlElementRef(name = "DomesticUltimateBusinessName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> domesticUltimateBusinessName;
    @XmlElementRef(name = "DomesticUltimateDUNSNumber", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> domesticUltimateDUNSNumber;
    @XmlElementRef(name = "GlobalUltimateBusinessName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> globalUltimateBusinessName;
    @XmlElementRef(name = "GlobalUltimateDUNSNumber", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> globalUltimateDUNSNumber;
    @XmlElementRef(name = "ParentDUNSNumber", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> parentDUNSNumber;

    /**
     * Gets the value of the accountDUNSNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAccountDUNSNumber() {
        return accountDUNSNumber;
    }

    /**
     * Sets the value of the accountDUNSNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAccountDUNSNumber(JAXBElement<String> value) {
        this.accountDUNSNumber = value;
    }

    /**
     * Gets the value of the companyDUNSNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCompanyDUNSNumber() {
        return companyDUNSNumber;
    }

    /**
     * Sets the value of the companyDUNSNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCompanyDUNSNumber(JAXBElement<String> value) {
        this.companyDUNSNumber = value;
    }

    /**
     * Gets the value of the domesticUltimateBusinessName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDomesticUltimateBusinessName() {
        return domesticUltimateBusinessName;
    }

    /**
     * Sets the value of the domesticUltimateBusinessName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDomesticUltimateBusinessName(JAXBElement<String> value) {
        this.domesticUltimateBusinessName = value;
    }

    /**
     * Gets the value of the domesticUltimateDUNSNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDomesticUltimateDUNSNumber() {
        return domesticUltimateDUNSNumber;
    }

    /**
     * Sets the value of the domesticUltimateDUNSNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDomesticUltimateDUNSNumber(JAXBElement<String> value) {
        this.domesticUltimateDUNSNumber = value;
    }

    /**
     * Gets the value of the globalUltimateBusinessName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGlobalUltimateBusinessName() {
        return globalUltimateBusinessName;
    }

    /**
     * Sets the value of the globalUltimateBusinessName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGlobalUltimateBusinessName(JAXBElement<String> value) {
        this.globalUltimateBusinessName = value;
    }

    /**
     * Gets the value of the globalUltimateDUNSNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGlobalUltimateDUNSNumber() {
        return globalUltimateDUNSNumber;
    }

    /**
     * Sets the value of the globalUltimateDUNSNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGlobalUltimateDUNSNumber(JAXBElement<String> value) {
        this.globalUltimateDUNSNumber = value;
    }

    /**
     * Gets the value of the parentDUNSNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getParentDUNSNumber() {
        return parentDUNSNumber;
    }

    /**
     * Sets the value of the parentDUNSNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setParentDUNSNumber(JAXBElement<String> value) {
        this.parentDUNSNumber = value;
    }

}
