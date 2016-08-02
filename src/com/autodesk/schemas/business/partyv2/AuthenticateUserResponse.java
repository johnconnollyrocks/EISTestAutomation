
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
 *         &lt;element name="Session" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}SessionType" minOccurs="0"/>
 *         &lt;element ref="{http://www.autodesk.com/schemas/Business/PartyV2.0}User"/>
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
    "session",
    "user"
})
@XmlRootElement(name = "AuthenticateUserResponse")
public class AuthenticateUserResponse {

    @XmlElement(name = "Session")
    protected SessionType session;
    @XmlElement(name = "User", required = true)
    protected UserType user;

    /**
     * Gets the value of the session property.
     * 
     * @return
     *     possible object is
     *     {@link SessionType }
     *     
     */
    public SessionType getSession() {
        return session;
    }

    /**
     * Sets the value of the session property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionType }
     *     
     */
    public void setSession(SessionType value) {
        this.session = value;
    }

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

}
