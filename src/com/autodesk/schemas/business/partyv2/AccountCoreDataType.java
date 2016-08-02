
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountCoreDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountCoreDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountBaseDataType">
 *       &lt;sequence>
 *         &lt;element name="AccountAlias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccountStatus" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="AccountType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ExportControlFlag" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ExportControlStatus" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="LocalLanguageName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountCoreDataType", propOrder = {
    "accountAlias",
    "accountStatus",
    "accountType",
    "exportControlFlag",
    "exportControlStatus",
    "localLanguageName"
})
@XmlSeeAlso({
    AccountExtDataType.class
})
public class AccountCoreDataType
    extends AccountBaseDataType
{

    @XmlElement(name = "AccountAlias")
    protected String accountAlias;
    @XmlElementRef(name = "AccountStatus", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> accountStatus;
    @XmlElementRef(name = "AccountType", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> accountType;
    @XmlElementRef(name = "ExportControlFlag", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> exportControlFlag;
    @XmlElementRef(name = "ExportControlStatus", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> exportControlStatus;
    @XmlElementRef(name = "LocalLanguageName", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> localLanguageName;

    /**
     * Gets the value of the accountAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountAlias() {
        return accountAlias;
    }

    /**
     * Sets the value of the accountAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountAlias(String value) {
        this.accountAlias = value;
    }

    /**
     * Gets the value of the accountStatus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAccountStatus() {
        return accountStatus;
    }

    /**
     * Sets the value of the accountStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAccountStatus(JAXBElement<String> value) {
        this.accountStatus = value;
    }

    /**
     * Gets the value of the accountType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAccountType() {
        return accountType;
    }

    /**
     * Sets the value of the accountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAccountType(JAXBElement<String> value) {
        this.accountType = value;
    }

    /**
     * Gets the value of the exportControlFlag property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getExportControlFlag() {
        return exportControlFlag;
    }

    /**
     * Sets the value of the exportControlFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setExportControlFlag(JAXBElement<String> value) {
        this.exportControlFlag = value;
    }

    /**
     * Gets the value of the exportControlStatus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getExportControlStatus() {
        return exportControlStatus;
    }

    /**
     * Sets the value of the exportControlStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setExportControlStatus(JAXBElement<String> value) {
        this.exportControlStatus = value;
    }

    /**
     * Gets the value of the localLanguageName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLocalLanguageName() {
        return localLanguageName;
    }

    /**
     * Sets the value of the localLanguageName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLocalLanguageName(JAXBElement<String> value) {
        this.localLanguageName = value;
    }

}
