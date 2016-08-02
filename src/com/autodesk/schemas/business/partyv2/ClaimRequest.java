
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
 *         &lt;element name="ProductLineCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Resource" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "productLineCode",
    "resource"
})
@XmlRootElement(name = "ClaimRequest")
public class ClaimRequest {

    @XmlElement(name = "SerialNum")
    protected String serialNum;
    @XmlElement(name = "ProductLineCode")
    protected String productLineCode;
    @XmlElement(name = "Resource", required = true)
    protected String resource;

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

}
