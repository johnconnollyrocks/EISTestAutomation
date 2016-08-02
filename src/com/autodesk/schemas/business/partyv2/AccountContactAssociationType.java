
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AccountContactAssociationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountContactAssociationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Account" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountRefDataType" minOccurs="0"/>
 *         &lt;element name="Contact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactRefDataType" minOccurs="0"/>
 *         &lt;element name="Operation" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *               &lt;enumeration value="Add"/>
 *               &lt;enumeration value="Delete"/>
 *               &lt;enumeration value="Update"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LastUpdateTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountContactAssociationType", propOrder = {
    "account",
    "contact",
    "operation",
    "lastUpdateTimestamp"
})
public class AccountContactAssociationType {

    @XmlElement(name = "Account")
    protected AccountRefDataType account;
    @XmlElement(name = "Contact")
    protected ContactRefDataType contact;
    @XmlElement(name = "Operation")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String operation;
    @XmlElement(name = "LastUpdateTimestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdateTimestamp;

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link AccountRefDataType }
     *     
     */
    public AccountRefDataType getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountRefDataType }
     *     
     */
    public void setAccount(AccountRefDataType value) {
        this.account = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactRefDataType }
     *     
     */
    public ContactRefDataType getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactRefDataType }
     *     
     */
    public void setContact(ContactRefDataType value) {
        this.contact = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Gets the value of the lastUpdateTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    /**
     * Sets the value of the lastUpdateTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdateTimestamp(XMLGregorianCalendar value) {
        this.lastUpdateTimestamp = value;
    }

}
