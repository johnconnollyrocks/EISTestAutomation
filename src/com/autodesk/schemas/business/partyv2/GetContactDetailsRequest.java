
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
 *         &lt;element ref="{http://www.autodesk.com/schemas/Business/PartyV2.0}ContactRefData"/>
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
    "contactRefData"
})
@XmlRootElement(name = "GetContactDetailsRequest")
public class GetContactDetailsRequest {

    @XmlElement(name = "ContactRefData", required = true)
    protected ContactRefDataType contactRefData;

    /**
     * Gets the value of the contactRefData property.
     * 
     * @return
     *     possible object is
     *     {@link ContactRefDataType }
     *     
     */
    public ContactRefDataType getContactRefData() {
        return contactRefData;
    }

    /**
     * Sets the value of the contactRefData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactRefDataType }
     *     
     */
    public void setContactRefData(ContactRefDataType value) {
        this.contactRefData = value;
    }

}
