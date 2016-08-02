
package com.autodesk.schemas.business.commonv1;

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
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="PhoneNumber" type="{http://www.autodesk.com/schemas/Business/CommonV1.0}PhoneNumberRefType" minOccurs="0"/>
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
    "phoneNumber"
})
@XmlRootElement(name = "FormatPhoneNumberRequest")
public class FormatPhoneNumberRequest {

    @XmlElement(name = "PhoneNumber")
    protected PhoneNumberRefType phoneNumber;

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumberRefType }
     *     
     */
    public PhoneNumberRefType getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumberRefType }
     *     
     */
    public void setPhoneNumber(PhoneNumberRefType value) {
        this.phoneNumber = value;
    }

}
