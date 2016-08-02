
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.autodesk.schemas.technical.commonv2.RequestType;


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
 *         &lt;element ref="{http://www.autodesk.com/schemas/Business/PartyV2.0}MergeContact"/>
 *         &lt;element name="Request" type="{http://www.autodesk.com/schemas/Technical/CommonV2.0}RequestType"/>
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
    "mergeContact",
    "request"
})
@XmlRootElement(name = "MergeContactRequest")
public class MergeContactRequest {

    @XmlElement(name = "MergeContact", required = true)
    protected MergeContact mergeContact;
    @XmlElement(name = "Request", required = true)
    protected RequestType request;

    /**
     * Gets the value of the mergeContact property.
     * 
     * @return
     *     possible object is
     *     {@link MergeContact }
     *     
     */
    public MergeContact getMergeContact() {
        return mergeContact;
    }

    /**
     * Sets the value of the mergeContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link MergeContact }
     *     
     */
    public void setMergeContact(MergeContact value) {
        this.mergeContact = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RequestType }
     *     
     */
    public RequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestType }
     *     
     */
    public void setRequest(RequestType value) {
        this.request = value;
    }

}
