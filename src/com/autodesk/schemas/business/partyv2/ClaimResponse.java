
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
 *         &lt;element name="SerialNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductLineCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Resource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthorizationStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthorizationDecisionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthorizationDecisionMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthorizationExpiryTimestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthorizationLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "serialNum",
    "contractNumber",
    "productLineCode",
    "resource",
    "authorizationStatus",
    "authorizationDecisionCode",
    "authorizationDecisionMsg",
    "authorizationExpiryTimestamp",
    "authorizationLevel"
})
@XmlRootElement(name = "ClaimResponse")
public class ClaimResponse {

    @XmlElement(name = "SerialNum")
    protected String serialNum;
    @XmlElement(name = "ContractNumber")
    protected String contractNumber;
    @XmlElement(name = "ProductLineCode")
    protected String productLineCode;
    @XmlElement(name = "Resource")
    protected String resource;
    @XmlElement(name = "AuthorizationStatus")
    protected String authorizationStatus;
    @XmlElement(name = "AuthorizationDecisionCode")
    protected String authorizationDecisionCode;
    @XmlElement(name = "AuthorizationDecisionMsg")
    protected String authorizationDecisionMsg;
    @XmlElement(name = "AuthorizationExpiryTimestamp")
    protected String authorizationExpiryTimestamp;
    @XmlElement(name = "AuthorizationLevel")
    protected String authorizationLevel;

    /**
     * Gets the value of the serialNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * Sets the value of the serialNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNum(String value) {
        this.serialNum = value;
    }

    /**
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

    /**
     * Gets the value of the productLineCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductLineCode() {
        return productLineCode;
    }

    /**
     * Sets the value of the productLineCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductLineCode(String value) {
        this.productLineCode = value;
    }

    /**
     * Gets the value of the resource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResource() {
        return resource;
    }

    /**
     * Sets the value of the resource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResource(String value) {
        this.resource = value;
    }

    /**
     * Gets the value of the authorizationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    /**
     * Sets the value of the authorizationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationStatus(String value) {
        this.authorizationStatus = value;
    }

    /**
     * Gets the value of the authorizationDecisionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationDecisionCode() {
        return authorizationDecisionCode;
    }

    /**
     * Sets the value of the authorizationDecisionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationDecisionCode(String value) {
        this.authorizationDecisionCode = value;
    }

    /**
     * Gets the value of the authorizationDecisionMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationDecisionMsg() {
        return authorizationDecisionMsg;
    }

    /**
     * Sets the value of the authorizationDecisionMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationDecisionMsg(String value) {
        this.authorizationDecisionMsg = value;
    }

    /**
     * Gets the value of the authorizationExpiryTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationExpiryTimestamp() {
        return authorizationExpiryTimestamp;
    }

    /**
     * Sets the value of the authorizationExpiryTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationExpiryTimestamp(String value) {
        this.authorizationExpiryTimestamp = value;
    }

    /**
     * Gets the value of the authorizationLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationLevel() {
        return authorizationLevel;
    }

    /**
     * Sets the value of the authorizationLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationLevel(String value) {
        this.authorizationLevel = value;
    }

}
