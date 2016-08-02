
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
 *         &lt;element name="UserProfile" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}UserCoreDataType" minOccurs="0"/>
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
    "userProfile"
})
@XmlRootElement(name = "GetUserProfileResponse")
public class GetUserProfileResponse {

    @XmlElement(name = "UserProfile")
    protected UserCoreDataType userProfile;

    /**
     * Gets the value of the userProfile property.
     * 
     * @return
     *     possible object is
     *     {@link UserCoreDataType }
     *     
     */
    public UserCoreDataType getUserProfile() {
        return userProfile;
    }

    /**
     * Sets the value of the userProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserCoreDataType }
     *     
     */
    public void setUserProfile(UserCoreDataType value) {
        this.userProfile = value;
    }

}
