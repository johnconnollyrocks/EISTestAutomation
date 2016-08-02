
package com.autodesk.schemas.technical.commonv2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EventName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Source" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ListOfTargets" type="{http://www.autodesk.com/schemas/Technical/CommonV2.0}ListOfTargetAppType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestType", propOrder = {
    "eventName",
    "requestNumber",
    "sessionId",
    "source",
    "listOfTargets"
})
public class RequestType {

    @XmlElementRef(name = "EventName", namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> eventName;
    @XmlElementRef(name = "RequestNumber", namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> requestNumber;
    @XmlElementRef(name = "SessionId", namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sessionId;
    @XmlElement(name = "Source", required = true)
    protected String source;
    @XmlElementRef(name = "ListOfTargets", namespace = "http://www.autodesk.com/schemas/Technical/CommonV2.0", type = JAXBElement.class, required = false)
    protected JAXBElement<ListOfTargetAppType> listOfTargets;

    /**
     * Gets the value of the eventName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEventName() {
        return eventName;
    }

    /**
     * Sets the value of the eventName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEventName(JAXBElement<String> value) {
        this.eventName = value;
    }

    /**
     * Gets the value of the requestNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRequestNumber() {
        return requestNumber;
    }

    /**
     * Sets the value of the requestNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRequestNumber(JAXBElement<String> value) {
        this.requestNumber = value;
    }

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSessionId(JAXBElement<String> value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the listOfTargets property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ListOfTargetAppType }{@code >}
     *     
     */
    public JAXBElement<ListOfTargetAppType> getListOfTargets() {
        return listOfTargets;
    }

    /**
     * Sets the value of the listOfTargets property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ListOfTargetAppType }{@code >}
     *     
     */
    public void setListOfTargets(JAXBElement<ListOfTargetAppType> value) {
        this.listOfTargets = value;
    }

}
