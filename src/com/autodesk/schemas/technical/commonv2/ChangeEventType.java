
package com.autodesk.schemas.technical.commonv2;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ChangeEventType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChangeEventType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EventId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ObjectName" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="KeyName" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="KeyValue" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="RetryCount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ProcessingMode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *               &lt;enumeration value="BATCH"/>
 *               &lt;enumeration value="REALTIME"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LastUpdateTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Response" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Result" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *                         &lt;enumeration value="ERROR"/>
 *                         &lt;enumeration value="SUCCESS"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ErrorInfo" type="{http://www.autodesk.com/schemas/Technical/CommonV2.0}MessageType" minOccurs="0"/>
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
@XmlType(name = "ChangeEventType", propOrder = {
    "eventId",
    "objectName",
    "keyName",
    "keyValue",
    "retryCount",
    "processingMode",
    "lastUpdateTimestamp",
    "response"
})
public class ChangeEventType {

    @XmlElement(name = "EventId")
    protected BigInteger eventId;
    @XmlElement(name = "ObjectName", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String objectName;
    @XmlElement(name = "KeyName", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String keyName;
    @XmlElement(name = "KeyValue", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String keyValue;
    @XmlElement(name = "RetryCount", defaultValue = "0")
    protected BigInteger retryCount;
    @XmlElement(name = "ProcessingMode", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String processingMode;
    @XmlElement(name = "LastUpdateTimestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdateTimestamp;
    @XmlElement(name = "Response")
    protected ChangeEventType.Response response;

    /**
     * Gets the value of the eventId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEventId() {
        return eventId;
    }

    /**
     * Sets the value of the eventId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEventId(BigInteger value) {
        this.eventId = value;
    }

    /**
     * Gets the value of the objectName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * Sets the value of the objectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectName(String value) {
        this.objectName = value;
    }

    /**
     * Gets the value of the keyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * Sets the value of the keyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyName(String value) {
        this.keyName = value;
    }

    /**
     * Gets the value of the keyValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyValue() {
        return keyValue;
    }

    /**
     * Sets the value of the keyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyValue(String value) {
        this.keyValue = value;
    }

    /**
     * Gets the value of the retryCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRetryCount() {
        return retryCount;
    }

    /**
     * Sets the value of the retryCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRetryCount(BigInteger value) {
        this.retryCount = value;
    }

    /**
     * Gets the value of the processingMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessingMode() {
        return processingMode;
    }

    /**
     * Sets the value of the processingMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessingMode(String value) {
        this.processingMode = value;
    }

    /**
     * Gets the value of the lastUpdateTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    /**
     * Sets the value of the lastUpdateTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdateTimestamp(XMLGregorianCalendar value) {
        this.lastUpdateTimestamp = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link ChangeEventType.Response }
     *     
     */
    public ChangeEventType.Response getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeEventType.Response }
     *     
     */
    public void setResponse(ChangeEventType.Response value) {
        this.response = value;
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
     *         &lt;element name="Result" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
     *               &lt;enumeration value="ERROR"/>
     *               &lt;enumeration value="SUCCESS"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ErrorInfo" type="{http://www.autodesk.com/schemas/Technical/CommonV2.0}MessageType" minOccurs="0"/>
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
        "result",
        "errorInfo"
    })
    public static class Response {

        @XmlElement(name = "Result")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        protected String result;
        @XmlElement(name = "ErrorInfo")
        protected MessageType errorInfo;

        /**
         * Gets the value of the result property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getResult() {
            return result;
        }

        /**
         * Sets the value of the result property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setResult(String value) {
            this.result = value;
        }

        /**
         * Gets the value of the errorInfo property.
         * 
         * @return
         *     possible object is
         *     {@link MessageType }
         *     
         */
        public MessageType getErrorInfo() {
            return errorInfo;
        }

        /**
         * Sets the value of the errorInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link MessageType }
         *     
         */
        public void setErrorInfo(MessageType value) {
            this.errorInfo = value;
        }

    }

}
