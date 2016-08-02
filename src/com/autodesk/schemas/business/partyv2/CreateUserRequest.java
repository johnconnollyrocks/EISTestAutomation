
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
 *         &lt;element ref="{http://www.autodesk.com/schemas/Business/PartyV2.0}User"/>
 *         &lt;element name="SuppressOutgoingEmail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
    "user",
    "suppressOutgoingEmail"
})
@XmlRootElement(name = "CreateUserRequest")
public class CreateUserRequest {

    @XmlElement(name = "User", required = true)
    protected UserType user;
    @XmlElement(name = "SuppressOutgoingEmail")
    protected Boolean suppressOutgoingEmail;

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link UserType }
     *     
     */
    public UserType getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserType }
     *     
     */
    public void setUser(UserType value) {
        this.user = value;
    }

    /**
     * Gets the value of the suppressOutgoingEmail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuppressOutgoingEmail() {
        return suppressOutgoingEmail;
    }

    /**
     * Sets the value of the suppressOutgoingEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuppressOutgoingEmail(Boolean value) {
        this.suppressOutgoingEmail = value;
    }

}
