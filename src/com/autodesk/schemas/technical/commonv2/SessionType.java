
package com.autodesk.schemas.technical.commonv2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Session Info
 * 
 * <p>Java class for SessionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SessionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SessionId" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SessionSeqNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="SessionCreatedTime" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="InUseFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SessionType", propOrder = {
    "sessionId",
    "sessionSeqNumber",
    "sessionCreatedTime",
    "inUseFlag"
})
public class SessionType {

    @XmlElement(name = "SessionId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String sessionId;
    @XmlElement(name = "SessionSeqNumber", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String sessionSeqNumber;
    @XmlElement(name = "SessionCreatedTime", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String sessionCreatedTime;
    @XmlElement(name = "InUseFlag")
    protected boolean inUseFlag;

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the sessionSeqNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionSeqNumber() {
        return sessionSeqNumber;
    }

    /**
     * Sets the value of the sessionSeqNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionSeqNumber(String value) {
        this.sessionSeqNumber = value;
    }

    /**
     * Gets the value of the sessionCreatedTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionCreatedTime() {
        return sessionCreatedTime;
    }

    /**
     * Sets the value of the sessionCreatedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionCreatedTime(String value) {
        this.sessionCreatedTime = value;
    }

    /**
     * Gets the value of the inUseFlag property.
     * 
     */
    public boolean isInUseFlag() {
        return inUseFlag;
    }

    /**
     * Sets the value of the inUseFlag property.
     * 
     */
    public void setInUseFlag(boolean value) {
        this.inUseFlag = value;
    }

}
