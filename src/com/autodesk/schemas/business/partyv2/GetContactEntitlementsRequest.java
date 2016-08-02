
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
 *         &lt;element name="XSLT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "contactRefData",
    "xslt"
})
@XmlRootElement(name = "GetContactEntitlementsRequest")
public class GetContactEntitlementsRequest {

    @XmlElement(name = "ContactRefData", required = true)
    protected ContactRefDataType contactRefData;
    @XmlElement(name = "XSLT")
    protected String xslt;

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

    /**
     * Gets the value of the xslt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXSLT() {
        return xslt;
    }

    /**
     * Sets the value of the xslt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXSLT(String value) {
        this.xslt = value;
    }

}
