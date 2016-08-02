
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountContactType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountContactType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Account" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}AccountType" minOccurs="0"/>
 *         &lt;element name="ListOfContact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfContactType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountContactType", propOrder = {
    "account",
    "listOfContact"
})
public class AccountContactType {

    @XmlElementRef(name = "Account", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<AccountType> account;
    @XmlElementRef(name = "ListOfContact", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ListOfContactType> listOfContact;

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link AccountType }{@code >}
     *     
     */
    public JAXBElement<AccountType> getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link AccountType }{@code >}
     *     
     */
    public void setAccount(JAXBElement<AccountType> value) {
        this.account = value;
    }

    /**
     * Gets the value of the listOfContact property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ListOfContactType }{@code >}
     *     
     */
    public JAXBElement<ListOfContactType> getListOfContact() {
        return listOfContact;
    }

    /**
     * Sets the value of the listOfContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ListOfContactType }{@code >}
     *     
     */
    public void setListOfContact(JAXBElement<ListOfContactType> value) {
        this.listOfContact = value;
    }

}
