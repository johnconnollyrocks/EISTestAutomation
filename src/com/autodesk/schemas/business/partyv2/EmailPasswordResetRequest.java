
package com.autodesk.schemas.business.partyv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="PostPwdRstRedirectURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "guid",
    "postPwdRstRedirectURL"
})
@XmlRootElement(name = "EmailPasswordResetRequest")
public class EmailPasswordResetRequest {

    @XmlElement(name = "GUID", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String guid;
    @XmlElement(name = "PostPwdRstRedirectURL")
    protected String postPwdRstRedirectURL;

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUID() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUID(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the postPwdRstRedirectURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostPwdRstRedirectURL() {
        return postPwdRstRedirectURL;
    }

    /**
     * Sets the value of the postPwdRstRedirectURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostPwdRstRedirectURL(String value) {
        this.postPwdRstRedirectURL = value;
    }

}
