
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactProfile"/>
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
    "contactProfile"
})
@XmlRootElement(name = "NotifyContactRequest")
public class NotifyContactRequest {

    @XmlElement(name = "ContactProfile", required = true)
    protected ContactCoreDataType contactProfile;

    /**
     * Gets the value of the contactProfile property.
     * 
     * @return
     *     possible object is
     *     {@link ContactCoreDataType }
     *     
     */
    public ContactCoreDataType getContactProfile() {
        return contactProfile;
    }

    /**
     * Sets the value of the contactProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactCoreDataType }
     *     
     */
    public void setContactProfile(ContactCoreDataType value) {
        this.contactProfile = value;
    }

}
