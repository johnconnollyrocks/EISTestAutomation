
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
 *         &lt;element name="PartnerUser" type="{http://www.autodesk.com/schemas/Business/PartyV2.0}PartnerUserType" minOccurs="0"/>
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
    "partnerUser"
})
@XmlRootElement(name = "CreatePartnerUserRequest")
public class CreatePartnerUserRequest {

    @XmlElement(name = "PartnerUser")
    protected PartnerUserType partnerUser;

    /**
     * Gets the value of the partnerUser property.
     * 
     * @return
     *     possible object is
     *     {@link PartnerUserType }
     *     
     */
    public PartnerUserType getPartnerUser() {
        return partnerUser;
    }

    /**
     * Sets the value of the partnerUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartnerUserType }
     *     
     */
    public void setPartnerUser(PartnerUserType value) {
        this.partnerUser = value;
    }

}
