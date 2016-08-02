
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
 *         &lt;element name="UserRefData" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}UserRefDataType" minOccurs="0"/>
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
    "userRefData"
})
@XmlRootElement(name = "GetUserProfileRequest")
public class GetUserProfileRequest {

    @XmlElement(name = "UserRefData")
    protected UserRefDataType userRefData;

    /**
     * Gets the value of the userRefData property.
     * 
     * @return
     *     possible object is
     *     {@link UserRefDataType }
     *     
     */
    public UserRefDataType getUserRefData() {
        return userRefData;
    }

    /**
     * Sets the value of the userRefData property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserRefDataType }
     *     
     */
    public void setUserRefData(UserRefDataType value) {
        this.userRefData = value;
    }

}
