
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactAccountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactAccountType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Contact" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactType" minOccurs="0"/>
 *         &lt;element name="ListOfAccount" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}ListOfAccountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactAccountType", propOrder = {
    "contact",
    "listOfAccount"
})
public class ContactAccountType {

    @XmlElementRef(name = "Contact", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ContactType> contact;
    @XmlElementRef(name = "ListOfAccount", namespace = "http://www.autodesk.com/schemas/Business/PartyV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ListOfAccountType> listOfAccount;

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ContactType }{@code >}
     *     
     */
    public JAXBElement<ContactType> getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ContactType }{@code >}
     *     
     */
    public void setContact(JAXBElement<ContactType> value) {
        this.contact = value;
    }

    /**
     * Gets the value of the listOfAccount property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ListOfAccountType }{@code >}
     *     
     */
    public JAXBElement<ListOfAccountType> getListOfAccount() {
        return listOfAccount;
    }

    /**
     * Sets the value of the listOfAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ListOfAccountType }{@code >}
     *     
     */
    public void setListOfAccount(JAXBElement<ListOfAccountType> value) {
        this.listOfAccount = value;
    }

}
