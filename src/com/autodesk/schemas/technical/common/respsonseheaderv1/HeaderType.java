
package com.autodesk.schemas.technical.common.respsonseheaderv1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for HeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RespondingSystem" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CorrelationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ProcessingServer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ProcessingProcessId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MaxDataLatency" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Properties" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;any processContents='lax' maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderType", propOrder = {
    "respondingSystem",
    "properties"
})
public class HeaderType {

    @XmlElement(name = "RespondingSystem")
    protected HeaderType.RespondingSystem respondingSystem;
    @XmlElement(name = "Properties")
    protected HeaderType.Properties properties;

    /**
     * Gets the value of the respondingSystem property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType.RespondingSystem }
     *     
     */
    public HeaderType.RespondingSystem getRespondingSystem() {
        return respondingSystem;
    }

    /**
     * Sets the value of the respondingSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType.RespondingSystem }
     *     
     */
    public void setRespondingSystem(HeaderType.RespondingSystem value) {
        this.respondingSystem = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType.Properties }
     *     
     */
    public HeaderType.Properties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType.Properties }
     *     
     */
    public void setProperties(HeaderType.Properties value) {
        this.properties = value;
    }


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
     *         &lt;any processContents='lax' maxOccurs="unbounded" minOccurs="0"/>
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
        "any"
    })
    public static class Properties {

        @XmlAnyElement(lax = true)
        protected List<Object> any;

        /**
         * Gets the value of the any property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the any property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAny().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Object }
         * {@link Element }
         * 
         * 
         */
        public List<Object> getAny() {
            if (any == null) {
                any = new ArrayList<Object>();
            }
            return this.any;
        }

    }


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
     *         &lt;element name="CorrelationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ProcessingServer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ProcessingProcessId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MaxDataLatency" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
        "correlationID",
        "processingServer",
        "processingProcessId",
        "maxDataLatency"
    })
    public static class RespondingSystem {

        @XmlElement(name = "CorrelationID")
        protected String correlationID;
        @XmlElement(name = "ProcessingServer")
        protected String processingServer;
        @XmlElement(name = "ProcessingProcessId")
        protected String processingProcessId;
        @XmlElement(name = "MaxDataLatency")
        protected BigInteger maxDataLatency;

        /**
         * Gets the value of the correlationID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCorrelationID() {
            return correlationID;
        }

        /**
         * Sets the value of the correlationID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCorrelationID(String value) {
            this.correlationID = value;
        }

        /**
         * Gets the value of the processingServer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProcessingServer() {
            return processingServer;
        }

        /**
         * Sets the value of the processingServer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProcessingServer(String value) {
            this.processingServer = value;
        }

        /**
         * Gets the value of the processingProcessId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProcessingProcessId() {
            return processingProcessId;
        }

        /**
         * Sets the value of the processingProcessId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProcessingProcessId(String value) {
            this.processingProcessId = value;
        }

        /**
         * Gets the value of the maxDataLatency property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMaxDataLatency() {
            return maxDataLatency;
        }

        /**
         * Sets the value of the maxDataLatency property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMaxDataLatency(BigInteger value) {
            this.maxDataLatency = value;
        }

    }

}
